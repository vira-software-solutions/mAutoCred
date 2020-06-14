package net.therealvira.minecraft.blocks;

import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.blocks.Material;
import net.morbz.minecraft.blocks.states.Facing5State;

public class StickyPiston implements IBlock {

    private boolean isActive;
    private Facing5State facing;

    public StickyPiston(boolean isActive, Facing5State facing) {
        this.isActive = isActive;
        this.facing = facing;
    }

    @Override
    public int getBlockId() {
        Material material = Material.MATERIALS.get("STICKY_PISTON");
        return material.getValue();
    }

    @Override
    public byte getBlockData() {
        // Facing direction
        byte data = 0;
        switch (facing) {
            case EAST:
                data = 5;
                break;
            case NORTH:
                data = 2;
                break;
            case SOUTH:
                data = 3;
                break;
            case UP:
                data = 1;
                break;
            case WEST:
                data = 4;
                break;
        }
        return (byte)(data | (isActive ? 0x8 : 0));
    }

    @Override
    public int getTransparency() {
        return 1;
    }

}
