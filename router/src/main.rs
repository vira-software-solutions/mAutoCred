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
    eprintln!("Initializing graphics context...");
    let (state_tx, state_rx) = channel();
    let (map_tx, map_rx) = channel();
    let graphics_handle = thread::spawn(move || {
        graphics::graphics_init(state_rx, map_rx);
    });
    eprintln!("Graphics thread started!");

    eprintln!("Creating example map...");
    let components = vec!(
        map::Component::Redstone {
            pos: map::Position {
                x: 0,
                y: 0,
            }
        },
        map::Component::Redstone {
            pos: map::Position {
                x: 15,
                y: 15,
            }
        },
        map::Component::Redstone {
            pos: map::Position {
                x: 2,
                y: 13,
            }
        },
        map::Component::Redstone {
            pos: map::Position {
                x: 3,
                y: 12,
            }
        },
        map::Component::Redstone {
            pos: map::Position {
                x: 6,
                y: 9,
            }
        },
        map::Component::Redstone {
            pos: map::Position {
                x: 7,
                y: 8,
            }
        },
        map::Component::Redstone {
            pos: map::Position {
                x: 8,
                y: 7,
            }
        },
        map::Component::Redstone {
            pos: map::Position {
                x: 9,
                y: 6,
            }
        },
        map::Component::Redstone {
            pos: map::Position {
                x: 10,
                y: 5,
            }
        },
        map::Component::Redstone {
            pos: map::Position {
                x: 11,
                y: 4,
            }
        },
        map::Component::Redstone {
            pos: map::Position {
                x: 12,
                y: 3,
            }
        },
        map::Component::Redstone {
            pos: map::Position {
                x: 13,
                y: 2,
            }
        },
    );

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
            x: 13,
            y: 13,
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
    eprintln!("Done, goodbye!");
}
