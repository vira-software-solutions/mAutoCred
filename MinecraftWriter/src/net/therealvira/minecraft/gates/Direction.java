package net.therealvira.minecraft.gates;

import net.morbz.minecraft.blocks.states.Facing5State;

public enum Direction {
    NORTH(0),
    EAST(1),
    SOUTH(2),
    WEST(3);

    Direction(int i) {
        rotations = i;
    }

    public final int rotations;
    public Facing5State convert(){
        switch (this) {
            case EAST: return Facing5State.EAST;
            case SOUTH: return  Facing5State.SOUTH;
            case WEST: return Facing5State.WEST;
            default: return  Facing5State.NORTH;
        }
    }

    public Direction invert(){
        switch (this) {
            case EAST: return WEST;
            case SOUTH: return NORTH;
            case WEST: return EAST;
            default: return SOUTH;
        }
    }

    public static Direction convert (Facing5State facing5State){
        switch (facing5State) {
            case EAST: return EAST;
            case SOUTH: return SOUTH;
            case WEST: return WEST;
            default: return NORTH;
        }
    }
}
