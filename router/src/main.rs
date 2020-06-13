#![allow(dead_code)]
#![allow(unused_variables)]
#![feature(vec_remove_item)]

mod drawable;
mod graphics;
mod map;

use std::thread;
use std::sync::Arc;
use std::sync::mpsc::channel;

fn main() {
    let (state_tx, state_rx) = channel();
    let (map_tx, map_rx) = channel();
    let graphics_handle = thread::spawn(move || {
        graphics::graphics_init(state_rx, map_rx);
    });

    let mut components = Vec::new();

    for i in 0..=63i32 {
        components.push(map::Component::Redstone {
            pos: map::Position {
                x: i,
                y: 6,
            }
        });
    }

    for i in 0..=63i32 {
        components.push(map::Component::Redstone {
            pos: map::Position {
                x: 9,
                y: i,
            }
        });
    }

    let mut map = map::Map::new(&components);
    let m = Arc::new(map.clone());
    map_tx.send(m.clone()).expect("failed to send map to thread");
    state_tx.send("Example map loaded!".to_owned()).expect("failed to send state to thread");

    let mut p = map::Path::new(
        map::Position {
            x: 2,
            y: 2,
        },
        map::Position {
            x: 40,
            y: 51,
        }, 0);

    let res = p.pathfind(&map);

    if res {
        map.apply_path(p);

        let m = Arc::new(map.clone());
        map_tx.send(m.clone()).expect("failed to send map to thread");
        state_tx.send("Pathfinding test map loaded!".to_owned()).expect("failed to send state to thread");
    } else {
        state_tx.send("Pathfinding failed!".to_owned()).expect("failed to send state to thread");
    }

    graphics_handle.join().unwrap();
}
