package net.therealvira.minecraft.blocks;

import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.blocks.Material;

public class RedstoneBlock implements IBlock {
    @Override
    public int getBlockId() {
        return Material.MATERIALS.get("REDSTONE_BLOCK").getValue();
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
