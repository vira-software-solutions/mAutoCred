use crate::map;
use crate::graphics;

use ggez::{Context, GameResult};
use ggez::graphics::{draw, window, DrawParam, Color};
use std::f32::consts::PI;

const TEXTURE_SIZE: i32 = 16;

pub trait Drawable {
    fn draw(&self, ctx: &mut Context, assets: &graphics::Assets, m: &map::Map, offset: graphics::Point2) -> GameResult;
}

impl std::ops::Add<graphics::Point2> for map::Position {
    type Output = graphics::Point2;
    fn add(self, rhs: graphics::Point2) -> graphics::Point2 {
        graphics::Point2::new(rhs.x + self.x as f32, rhs.y + self.y as f32)
    }
}

impl std::ops::Mul<graphics::Vector2> for map::Position {
    type Output = Self;
    fn mul(self, rhs: graphics::Vector2) -> Self {
        map::Position {
            x: (self.x as f32 * rhs.x) as i32,
            y: (self.y as f32 * rhs.y) as i32,
        }
    }
}

fn get_default_drawparam(ctx: &Context, m: &map::Map, pos: map::Position, offset: graphics::Point2, color: Color) -> (DrawParam, graphics::Vector2, map::Position) {
    // calculate screen position and scale
    let screen_size = window(ctx).get_inner_size().unwrap();
    let screen_x = screen_size.width;
    let screen_y = screen_size.height;
    let map_scale_x = screen_x as f32 / m.size.width as f32;
    let map_scale_y = (screen_y - 20.0) as f32 / m.size.height as f32;
    let scale = graphics::Vector2::new(map_scale_x, map_scale_y);

    let pos = pos * scale;
    let scale = scale / TEXTURE_SIZE as f32;
    let pos = pos + map::Position {
        x: (TEXTURE_SIZE as f32*scale.x*0.5) as i32,
        y: (TEXTURE_SIZE as f32*scale.y*0.5) as i32,
    };

    (DrawParam::default().dest(pos + offset).color(color).scale(scale).offset(graphics::Point2::new(0.5, 0.5)), scale, pos)
}

impl Drawable for map::Component {
    fn draw(&self, ctx: &mut Context, assets: &graphics::Assets, m: &map::Map, offset: graphics::Point2) -> GameResult {
        match self {
            map::Component::Redstone { pos } => {
                // calculate redstone dust variant
                let mut n_left = false;
                let mut n_right = false;
                let mut n_top = false;
                let mut n_bottom = false;

                let is_redstone = |comp| {
                    match comp {
                        map::Component::Redstone { pos: _ } => true,
                        _ => false
                    }
                };

                if let Some(c) = m.component_by_pos(map::Position { x: pos.x + 1, y: pos.y }) {
                    n_right = is_redstone(c);
                }
                if let Some(c) = m.component_by_pos(map::Position { x: pos.x - 1, y: pos.y }) {
                    n_left = is_redstone(c);
                }
                if let Some(c) = m.component_by_pos(map::Position { x: pos.x, y: pos.y + 1 }) {
                    n_bottom = is_redstone(c);
                }
                if let Some(c) = m.component_by_pos(map::Position { x: pos.x, y: pos.y - 1 }) {
                    n_top = is_redstone(c);
                }

                let (p, scale, pos) = get_default_drawparam(&ctx, &m, *pos, offset, assets.color_redstone);

                if n_left || n_right || n_top || n_bottom {
                    if n_top || n_bottom {
                        draw(ctx, &assets.redstone_dust_line0, p)?;
                    }
                    if n_left || n_right {
                        // rotate 90°
                        let p = p.scale(graphics::Vector2::new(scale.y, scale.x)).rotation(PI/2.0).dest(pos + offset);
                        draw(ctx, &assets.redstone_dust_line0, p)?;
                    }
                } else {
                    draw(ctx, &assets.redstone_dust_dot, p)?;
                }

                Ok(())
            },
            map::Component::Repeater { pos, rot } => {
                let (p, scale, pos) = get_default_drawparam(&ctx, &m, *pos, offset, ggez::graphics::WHITE);
                let p = match rot {
                    map::Direction::Up => {
                        p
                    },
                    map::Direction::Down => {
                        p.rotation(PI)
                    },
                    map::Direction::Right => {
                        p.scale(graphics::Vector2::new(scale.y, scale.x)).rotation(PI/2.0).dest(pos + offset)
                    },
                    map::Direction::Left => {
                        p.scale(graphics::Vector2::new(scale.y, scale.x)).rotation(PI*1.5).dest(pos + offset)
                    }
                };

                draw(ctx, &assets.redstone_repeater, p)
            },
            map::Component::Bridge { pos, rot } => {
                let (p, scale, pos) = get_default_drawparam(&ctx, &m, *pos, offset, assets.color_bridge);
                let p = match rot {
                    map::Direction::Up | map::Direction::Down => {
                        p
                    },
                    map::Direction::Left | map::Direction::Right => {
                        p.scale(graphics::Vector2::new(scale.y, scale.x)).rotation(PI/2.0).dest(pos + offset)
                    }
                };

                match rot {
                    map::Direction::Up => {
                        draw(ctx, &assets.redstone_dust_line0, p)?;
                        draw(ctx, &assets.redstone_dust_line0, p.dest(pos + map::Position { x: 0, y: -(TEXTURE_SIZE as f32 * scale.y) as i32 } + offset))?;
                        draw(ctx, &assets.redstone_dust_line0, p.dest(pos + map::Position { x: 0, y: -(2.0 * TEXTURE_SIZE as f32 * scale.y) as i32 } + offset))?;
                    },
                    map::Direction::Down => {
                        draw(ctx, &assets.redstone_dust_line0, p)?;
                        draw(ctx, &assets.redstone_dust_line0, p.dest(pos + map::Position { x: 0, y: (TEXTURE_SIZE as f32 * scale.y) as i32 } + offset))?;
                        draw(ctx, &assets.redstone_dust_line0, p.dest(pos + map::Position { x: 0, y: (2.0 * TEXTURE_SIZE as f32 * scale.y) as i32 } + offset))?;
                    },
                    map::Direction::Left => {
                        draw(ctx, &assets.redstone_dust_line0, p)?;
                        draw(ctx, &assets.redstone_dust_line0, p.dest(pos + map::Position { x: -(TEXTURE_SIZE as f32 * scale.x) as i32, y: 0 } + offset))?;
                        draw(ctx, &assets.redstone_dust_line0, p.dest(pos + map::Position { x: -(2.0 * TEXTURE_SIZE as f32 * scale.x) as i32, y: 0 } + offset))?;
                    },
                    map::Direction::Right => {
                        draw(ctx, &assets.redstone_dust_line0, p)?;
                        draw(ctx, &assets.redstone_dust_line0, p.dest(pos + map::Position { x: (TEXTURE_SIZE as f32 * scale.x) as i32, y: 0 } + offset))?;
                        draw(ctx, &assets.redstone_dust_line0, p.dest(pos + map::Position { x: (2.0 * TEXTURE_SIZE as f32 * scale.x) as i32, y: 0 } + offset))?;
                    }
                }

                Ok(())
            },
            map::Component::Gate { pos, rot, size, gate_type, inputs, outputs } => {
                let rect = ggez::graphics::Mesh::new_rectangle(
                    ctx,
                    ggez::graphics::DrawMode::stroke(3.0),
                    ggez::graphics::Rect::new_i32(-TEXTURE_SIZE/2, -TEXTURE_SIZE/2, size.width * TEXTURE_SIZE, size.height * TEXTURE_SIZE),
                    assets.color_gate
                )?;

                let (p, scale, mappos) = get_default_drawparam(&ctx, &m, *pos, offset, ggez::graphics::WHITE);

                let p = match rot {
                    map::Direction::Up | map::Direction::Down => {
                        p.rotation(PI/2.0).dest(mappos + map::Position { x: (TEXTURE_SIZE as f32 * scale.x * (size.height-1) as f32) as i32, y: -(TEXTURE_SIZE as f32 * scale.y * (size.width-1) as f32) as i32 } + offset)
                    },
                    _ => p
                };

                draw(ctx, &rect, p)?;

                let (pt, scale, mappos) = get_default_drawparam(&ctx, &m, *pos, offset, ggez::graphics::BLACK);
                let pt = match rot {
                    map::Direction::Up | map::Direction::Down => {
                        pt.dest(mappos + map::Position { x: 0, y: -(TEXTURE_SIZE as f32 * scale.x * 0.8) as i32 } + offset)
                    },
                    _ => pt
                };
                let text = ggez::graphics::Text::new((*gate_type, assets.font, 16.0 * scale.x));
                draw(ctx, &text, pt)?;

                Ok(())
            },
            map::Component::Empty { pos } => {
                // debug view
                if m.placeable_pos(*pos, None) {
                    let (p, _, _) = get_default_drawparam(&ctx, &m, *pos, offset, assets.color_debug);
                    draw(ctx, &assets.redstone_dust_dot, p)?;
                }

                Ok(())
            },
        }
    }
}
