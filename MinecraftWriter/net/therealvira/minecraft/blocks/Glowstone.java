package net.therealvira.minecraft.blocks;

import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.blocks.Material;

public class Glowstone implements IBlock {
    @Override
    public int getBlockId() {
        return Material.MATERIALS.get("GLOWSTONE").getValue();
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
