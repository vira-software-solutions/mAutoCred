package net.therealvira.minecraft.blocks;

import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.blocks.Material;

public class Target implements IBlock {
    @Override
    public int getBlockId() {
        Material material = Material.MATERIALS.get("TARGET");
        return material.getValue();
    }

    @Override
    public byte getBlockData() {
        return 0;
    }

    @Override
    public int getTransparency() {
        return 0;
    }
}
