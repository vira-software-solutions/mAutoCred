use std::rc::Rc;
use std::cell::Cell;

#[derive(Debug, Copy, Clone, PartialEq)]
pub struct Position {
    pub x: i32,
    pub y: i32,
}

impl std::ops::Add<Position> for Position {
    type Output = Position;
    fn add(self, rhs: Position) -> Position {
        Position { x: self.x + rhs.x, y: self.y + rhs.y }
    }
}

impl Position {
    fn manhattan_distance(&self, to: &Self) -> i32 {
        (self.x - to.x).abs() + (self.y - to.y).abs()
    }
}

#[derive(Debug, Copy, Clone)]
pub struct Size {
    pub width: i32,
    pub height: i32,
}

#[derive(Debug, Copy, Clone)]
pub enum Rotation {
    Horizontal,
    Vertical,
}

#[derive(Debug, Copy, Clone)]
pub enum Direction {
    Up,
    Down,
    Left,
    Right,
}

#[derive(Debug, Copy, Clone)]
pub struct InOut {
    pub relative_pos: Position,
    pub direction: Direction,
}

type Output = InOut;
type Input = InOut;

#[derive(Debug, Clone)]
pub enum Component {
    Empty {
        pos: Position,
    },
    Redstone {
        pos: Position,
    },
    Bridge {
        // defines the starting point of the bridge
        pos: Position,
        rot: Direction,
        // no size, bridge is always 1x3 (or 3x1 respectively)
    },
    Gate {
        // defines the top left point of the gate
        pos: Position,
        rot: Rotation,
        size: Size,
        inputs: Vec<Input>,
        outputs: Vec<Output>,
        gate_type: &'static str,
    },
}

#[derive(Debug, Clone)]
pub struct Map {
    pub components: Vec<Component>,
    pub size: Size,
}

impl Map {
    pub fn new(cs: &[Component]) -> Map {
        let mut size = Size { width: 0, height: 0 };
        let mut components: Vec<Component> = Vec::new();
        // calculate map size by most bottom-right component
        for c in cs {
            let pos = match c {
                Component::Redstone { pos } => *pos,
                Component::Bridge { pos, rot: _ } => *pos,
                Component::Gate { pos, rot: _, size: _, inputs: _, outputs: _, gate_type: _ } => *pos,
                Component::Empty { pos } => *pos,
            };
            if pos.x + 1 > size.width {
                size.width = pos.x + 1
            }
            if pos.y + 1 > size.height {
                size.height = pos.y + 1
            }
        }

        for y in 0..size.width {
            for x in 0..size.height {
                let mut found = false;
                for c in cs {
                    let pos = match c {
                        Component::Redstone { pos } => *pos,
                        Component::Bridge { pos, rot: _ } => *pos,
                        Component::Gate { pos, rot: _, size: _, inputs: _, outputs: _, gate_type: _ } => *pos,
                        Component::Empty { pos } => Position { x: -1, y: -1 },
                    };
                    if pos.x == x && pos.y == y {
                        components.push(c.clone());
                        found = true;
                        break;
                    }
                }
                if !found {
                    components.push(Component::Empty { pos: Position { x, y } });
                }
            }
        }

        Map {
            components,
            size,
        }
    }

    pub fn component_by_pos(&self, pos: Position) -> Option<Component> {
        let mut x = pos.x;
        let mut y = pos.y;

        while x < 0 {
            x += self.size.width;
            y -= 1;
        }
        if y < 0 {
            return None;
        }

        let idx = (x + self.size.width * y) as usize;
        if idx >= self.components.len() {
            return None;
        }

        Some(self.components[idx].clone())
    }

    pub fn placeable_pos(&self, pos: Position, exception: Option<Direction>) -> bool {
        if pos.x < 0 || pos.y < 0 ||
           pos.x >= self.size.width || pos.y >= self.size.height {
               return false;
        }

        for x in -1..=1i32 {
            for y in -1..=1i32 {
                // diagonals don't matter
                if x.abs() == y.abs() {
                    continue;
                }

                if let Some(e) = exception {
                    match e {
                        Direction::Up => {
                            if y == -1 {
                                continue;
                            }
                        },
                        Direction::Down => {
                            if y == 1 {
                                continue;
                            }
                        },
                        Direction::Left => {
                            if x == -1 {
                                continue;
                            }
                        },
                        Direction::Right => {
                            if x == 1 {
                                continue;
                            }
                        }
                    }
                }

                let offset = Position { x, y };
                let pos_offset = pos + offset;
                let idx = pos_offset.x + self.size.width * pos_offset.y;

                if idx < 0 || idx as usize >= self.components.len() {
                    continue;
                }

                match self.components[idx as usize] {
                    Component::Empty { pos } => (),
                    _ => {
                        return false;
                    }
                }
            }
        }

        true
    }

    pub fn apply_path(&mut self, path: Path) {
        if let Some(ref path) = path.path {
            for node in path {
                let idx = (node.pos.x + self.size.width * node.pos.y) as usize;
                if idx >= self.components.len() {
                    panic!("cannot apply path outside of map size");
                }

                match self.components[idx] {
                    Component::Empty { pos } => (),
                    _ => panic!("cannot apply path over existing components")
                }

                if let Some(bridge) = node.bridge {
                    self.components[idx] = Component::Bridge {
                        pos: node.pos,
                        rot: bridge,
                    }
                } else {
                    self.components[idx] = Component::Redstone {
                        pos: node.pos,
                    }
                }
            }
        }
    }
}

pub struct Path {
    pub from: Position,
    pub to: Position,
    pub order: i32,
    pub replace: bool,
    path: Option<Vec<MaybeBridge>>,
}

struct MaybeBridge {
    pos: Position,
    bridge: Option<Direction>,
}

struct PathNode {
    pos: Position,
    predecessor: Option<Rc<PathNode>>,
    distance: Cell<i32>,
    is_bridge: Option<Direction>,
}

impl PartialEq for PathNode {
    fn eq(&self, other: &Self) -> bool {
        self.pos == other.pos
    }
}

impl Path {
    pub fn new(from: Position, to: Position, order: i32) -> Self {
        Self {
            from,
            to,
            order,
            replace: false,
            path: None,
        }
    }

    // implements A* path finding algorithm
    // see: https://www.geeksforgeeks.org/a-search-algorithm
    // returns false if no path is possible
    pub fn pathfind(&mut self, map: &Map) -> bool {
        let mut closed: Vec<Rc<PathNode>> = Vec::new();
        let mut open = vec!(Rc::new(PathNode {
            pos: self.from,
            predecessor: None,
            distance: Cell::new(0),
            is_bridge: None,
        }));

        while open.len() > 0 {
            let mut q = &open[0];
            let mut qidx = 0;
            for i in 0..open.len() {
                let qi = &open[i];
                if qi.distance < q.distance {
                    q = qi;
                    qidx = i;
                }
            }

            let q = open.remove(qidx);

            let mut successors = Vec::new();
            for x in -1..=1i32 {
                for y in -1..=1i32 {
                    // only allow horizontal and vertical movement
                    // (no diagonal redstone)
                    if (x == 0 && y == 0) || x.abs() == y.abs() {
                        continue;
                    }

                    let offset = Position { x, y };
                    let pos = q.pos + offset;
                    let placeable = map.placeable_pos(pos, None);
                    if placeable {
                        successors.push(Rc::new(PathNode {
                            pos: pos,
                            predecessor: Some(q.clone()),
                            distance: Cell::new(self.to.manhattan_distance(&pos)),
                            is_bridge: None,
                        }));
                    }
                }
            }

            // generate bridge successors
            for x in -1..=1i32 {
                for y in -1..=1i32 {
                    if (x == 0 && y == 0) || x.abs() == y.abs() {
                        continue;
                    }

                    let dir = if y == -1 {
                        Direction::Up
                    } else if y == 1 {
                        Direction::Down
                    } else if x == -1 {
                        Direction::Left
                    } else {
                        Direction::Right
                    };

                    let op_dir = if y == -1 {
                        Direction::Down
                    } else if y == 1 {
                        Direction::Up
                    } else if x == -1 {
                        Direction::Right
                    } else {
                        Direction::Left
                    };

                    // e e r e e
                    // ^ p r p
                    let offset = Position { x, y };
                    let offset2 = Position { x: x * 2, y: y * 2 };
                    let offset3 = Position { x: x * 3, y: y * 3 };
                    let pos = q.pos + offset;
                    let pos2 = q.pos + offset2;
                    let pos3 = q.pos + offset3;

                    let redstone = match map.component_by_pos(pos2) {
                        Some(c) => match c {
                            Component::Redstone { pos } => true,
                            _ => false
                        },
                        None => false
                    };

                    let placeable = redstone &&
                        map.placeable_pos(pos, Some(dir)) &&
                        map.placeable_pos(pos3, Some(op_dir));

                    if placeable {
                        successors.push(Rc::new(PathNode {
                            pos: pos3,
                            predecessor: Some(q.clone()),
                            distance: Cell::new(self.to.manhattan_distance(&pos3)),
                            is_bridge: Some(op_dir),
                        }));
                    }
                }
            }


            for s in successors {
                if s.pos == self.to {
                    // Done, calculate path from end to start
                    let mut path = Vec::new();
                    let mut cur = s.clone();
                    loop {
                        let ins = MaybeBridge {
                            pos: cur.pos,
                            bridge: cur.is_bridge,
                        };
                        path.push(ins);

                        match &cur.predecessor {
                            Some(p) => {
                                cur = p.clone();
                            },
                            None => {
                                break;
                            }
                        }
                    }
                    self.path = Some(path);
                    return true;
                }

                let cost = if let Some(_) = s.is_bridge {
                    3
                } else {
                    1
                };

                let h = self.to.manhattan_distance(&s.pos);
                s.distance.set(h + cost + q.distance.get());

                let mut has = false;
                let mut to_remove = None;
                for i in &open {
                    if i.pos == s.pos && i.distance <= s.distance {
                        if self.replace && i.distance == s.distance {
                            to_remove = Some(i.clone());
                        } else {
                            has = true;
                        }
                        break;
                    }
                }

                if let Some(to_remove) = to_remove {
                    open.remove_item(&to_remove);
                }

                for i in &closed {
                    if i.pos == s.pos && i.distance <= s.distance {
                        has = true;
                        break;
                    }
                }
                if has {
                    continue;
                }

                open.push(s.clone());
            }

            closed.push(q.clone());
        }

        false
    }
}
