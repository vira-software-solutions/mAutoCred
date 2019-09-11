package net.therealvira.minecraft.blocks;

import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.blocks.Material;

public class Redstone_Wire implements IBlock {
    private Byte power;

    public Redstone_Wire(Byte power){
        this.power = power;
    }

    @Override
    public int getBlockId() {
        Material material = Material.REDSTONE_WIRE;
        return material.getValue();
    }

    @Override
    public byte getBlockData() {
        return power;
    }

    @Override
    public int getTransparency() {
        return 1;
    }
}
