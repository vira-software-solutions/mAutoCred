use crate::map;
use crate::drawable::Drawable;

use std::path;
use std::sync::Arc;
use std::sync::mpsc::Receiver;

use ggez::{Context, ContextBuilder, GameResult};
use ggez::graphics::{self, Image, Color, Font, Text, DrawParam};
use ggez::event::{self, EventHandler, KeyCode, KeyMods};
use ggez::conf::{NumSamples, WindowSetup, WindowMode};

use ggez::nalgebra as na;

pub type Point2 = na::Point2<f32>;
pub type Vector2 = na::Vector2<f32>;

pub fn graphics_init(update_state_recv: Receiver<String>, map_recv: Receiver<Arc<map::Map>>) {
    let (mut ctx, mut event_loop) = ContextBuilder::new(
            "mAutoCred Routing Visualization",
            "Stefan Reiter")
        .window_setup(WindowSetup {
            title: "mAutoCred router".to_owned(),
            vsync: false,
            samples: NumSamples::Zero,
            icon: "".to_owned(),
            srgb: true,
        })
        .add_resource_path(path::PathBuf::from("img"))
        // .add_resource_path(path::PathBuf::from("../img"))
        .build()
        .expect("error: could not create ezgg visualization context");

    let win_width = 1000;
    let win_height = 1000;
    graphics::set_mode(&mut ctx, WindowMode::default().dimensions(win_width as f32, win_height as f32)).unwrap();
    graphics::set_screen_coordinates(&mut ctx, graphics::Rect::new_i32(0, 0, win_width, win_height)).unwrap();

    let mut game = RouterGame::new(&mut ctx, update_state_recv, map_recv);
    if let Err(e) = event::run(&mut ctx, &mut event_loop, &mut game) {
        eprintln!("error occured during visualization: {}", e)
    }
}

pub struct Assets {
    pub font: Font,

    pub color_background: Color,
    pub color_redstone: Color,
    pub color_bridge: Color,
    pub color_gate: Color,
    pub color_debug: Color,

    pub redstone_dust_dot: Image,
    pub redstone_dust_line0: Image,
    pub redstone_repeater: Image,
}

impl Assets {
    pub fn new(ctx: &mut Context) -> Self {
        Assets {
            font: Font::new(ctx, "/SourceCodePro-Regular.ttf").unwrap(),

            color_background: Color::from_rgb(230, 230, 230),
            color_redstone: Color::from_rgb(180, 0, 0),
            color_bridge: Color::from_rgb(0, 255, 40),
            color_gate: Color::from_rgb(0, 0, 0),
            color_debug: Color::from_rgba(0, 0, 200, 80),

            redstone_dust_dot: Image::new(ctx, "/redstone_dust_dot.png").unwrap(),
            redstone_dust_line0: Image::new(ctx, "/redstone_dust_line0.png").unwrap(),
            redstone_repeater: Image::new(ctx, "/repeater_on.png").unwrap(),
        }
    }
}

struct RouterGame {
    assets: Assets,

    update_state_recv: Receiver<String>,
    state: String,

    map_recv: Receiver<Arc<map::Map>>,
    current_map: Option<Arc<map::Map>>,
}

impl RouterGame {
    pub fn new(ctx: &mut Context, update_state_recv: Receiver<String>, map_recv: Receiver<Arc<map::Map>>) -> Self {
        RouterGame {
            assets: Assets::new(ctx),

            update_state_recv,
            state: "Initializing...".to_owned(),

            map_recv,
            current_map: None,
        }
    }
}

impl EventHandler for RouterGame {
    fn update(&mut self, _ctx: &mut Context) -> GameResult<()> {
        if let Ok(result) = self.update_state_recv.try_recv() {
            self.state = result;
        }

        if let Ok(result) = self.map_recv.try_recv() {
            self.current_map = Some(result);
        }

        Ok(())
    }

    fn draw(&mut self, ctx: &mut Context) -> GameResult<()> {
        graphics::clear(ctx, self.assets.color_background);


        if let Some(map) = &self.current_map {
            let offset = Point2::new(0.0, 20.0);

            for c in &map.components {
                c.draw(ctx, &self.assets, &map, offset)?;
            }

            for c in &map.ports {
                let (p, scale, _) = crate::drawable::get_default_drawparam(ctx, map, *c.1, offset, graphics::BLACK);
                let text = Text::new((c.0.to_owned(), self.assets.font, 12.0 * scale.x));
                graphics::draw(ctx, &text, p)?;
            }
        }

        let state_text = Text::new((self.state.clone(), self.assets.font, 18.0));
        let state_param = DrawParam::default().dest(Point2::new(2.0, 2.0)).color(graphics::BLACK);
        graphics::draw(ctx, &state_text, state_param)?;

        graphics::present(ctx)
    }

    fn key_down_event(&mut self, ctx: &mut Context, _key: KeyCode, _mods: KeyMods, _: bool) {
        event::quit(ctx);
    }
}
