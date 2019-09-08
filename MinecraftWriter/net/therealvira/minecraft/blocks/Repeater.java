package net.therealvira.minecraft.blocks;

import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.blocks.Material;
import net.morbz.minecraft.blocks.states.Facing4State;

public class Repeater implements IBlock {
    private boolean powered;
    private Facing4State facing;
    private byte delay;

    public Repeater(boolean powered, Facing4State facing, byte delay){
        this.powered = powered;
        this.facing = facing;
        this.delay = delay;
    }

    @Override
    public byte getBlockId() {
        Material material = powered?Material.POWERED_REPEATER:Material.UNPOWERED_REPEATER;
        return (byte)material.getValue();
    }

    @Override
    public byte getBlockData() {
        // Facing direction
        byte data = 0;
        switch(facing) {
            case EAST:
                data = 1;
                break;
            case WEST:
                data = 3;
                break;
            case SOUTH:
                data = 2;
                break;
            case NORTH:
                data = 0;
                break;
        }
        return (byte) (data+4*(delay-1));
    }

    @Override
    public int getTransparency() {
        return 1;
    }
}
