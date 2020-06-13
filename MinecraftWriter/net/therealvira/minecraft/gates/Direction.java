package net.therealvira.minecraft.gates;

import com.sun.source.tree.BreakTree;
import net.morbz.minecraft.blocks.states.Facing5State;

public enum Direction {
    North(0),
    East(1),
    South(2),
    West(3);

    Direction(int i) {
        rotations = i;
    }

    public final int rotations;
    public Facing5State convert(){
        return switch (this) {
            case East -> Facing5State.EAST;
            case South -> Facing5State.SOUTH;
            case West -> Facing5State.WEST;
            default -> Facing5State.NORTH;
        };
    }

    public Direction invert(){
        return switch (this) {
            case East -> West;
            case South -> North;
            case West -> East;
            default -> South;
        };
    }

    public static Direction convert (Facing5State facing5State){
        return switch (facing5State) {
            case EAST -> East;
            case SOUTH -> South;
            case WEST -> West;
            default -> North;
        };
    }
}
