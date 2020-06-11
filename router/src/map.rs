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
        // defines the top left point of the bridge
        pos: Position,
        rot: Rotation,
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
        for c in cs {
            let pos = match c {
                Component::Redstone { pos } => *pos,
                Component::Bridge { pos, rot: _ } => *pos,
                Component::Gate { pos, rot: _, size: _, inputs: _, outputs: _, gate_type: _ } => *pos,
                Component::Empty { pos } => Position { x: 0, y: 0 },
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

    pub fn placeable_pos(&self, pos: Position) -> bool {
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
                let idx = (node.x + self.size.width * node.y) as usize;
                if idx >= self.components.len() {
                    eprintln!("x{} y{}", node.x, node.y);
                    panic!("cannot apply path outside of map size");
                }

                // match self.components[idx] {
                //     Component::Empty => (),
                //     _ => panic!("cannot apply path over existing components")
                // }

                self.components[idx] = Component::Redstone {
                    pos: *node,
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
    pub reverse: bool,
    path: Option<Vec<Position>>,
}

struct PathNode {
    pos: Position,
    predecessor: Option<Rc<PathNode>>,
    distance: Cell<i32>,
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
            reverse: false,
            path: None,
        }
    }

    // implements A* path finding algorithm
    // see: https://www.geeksforgeeks.org/a-search-algorithm
    // returns false if no path is possible
    pub fn pathfind(&mut self, map: &Map) -> bool {
        let to = if self.reverse { self.from } else { self.to };
        let from = if self.reverse { self.to } else { self.from };

        let mut closed: Vec<Rc<PathNode>> = Vec::new();
        let mut open = vec!(Rc::new(PathNode {
            pos: from,
            predecessor: None,
            distance: Cell::new(0),
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
                    let placeable = map.placeable_pos(pos);
                    if placeable {
                        successors.push(Rc::new(PathNode {
                            pos: pos,
                            predecessor: Some(q.clone()),
                            distance: Cell::new(to.manhattan_distance(&pos)),
                        }));
                    }
                }
            }

            for s in successors {
                if s.pos == to {
                    let mut path = Vec::new();
                    let mut cur = s.clone();
                    loop {
                        path.push(cur.pos);
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

                let h = to.manhattan_distance(&s.pos);
                s.distance.set(h + 1 + q.distance.get());

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
