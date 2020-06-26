#![allow(dead_code)]
#![allow(unused_variables)]
#![feature(vec_remove_item)]

mod drawable;
mod graphics;
mod map;
mod serialize;

use std::env;
use std::thread;
use std::process;
use std::sync::Arc;
use std::sync::mpsc::channel;

fn print_help() {
    eprintln!("Usage: router <input.json>");
    process::exit(1);
}

fn main() {
    let (state_tx, state_rx) = channel();
    let (map_tx, map_rx) = channel();
    let graphics_handle = thread::spawn(move || {
        graphics::graphics_init(state_rx, map_rx);
    });

    let mut arg_count = 0;
    let mut path = "".to_owned();
    for argument in env::args() {
        path = argument;
        arg_count += 1;
    }

    if arg_count != 2 {
        print_help();
    }

    state_tx.send("Processing input...".to_owned()).expect("failed to send state to thread");

    let val = serialize::read_input(path);
    let map = Arc::new(serialize::create_standard_map(val));

    map_tx.send(map.clone()).expect("failed to send map to thread");
    state_tx.send("Map created! Press any key to close...".to_owned()).expect("failed to send state to thread");

    graphics_handle.join().unwrap();

    println!("{}", serialize::write_output(&*map));
}

