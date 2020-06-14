package net.therealvira.minecraft.blocks;

import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.blocks.Material;

public enum Concrete implements IBlock {
    WHITE_CONCRETE(0),
    ORANGE_CONCRETE(1),
    MAGENTA_CONCRETE(2),
    LIGHT_BLUE_CONCRETE(3),
    YELLOW_CONCRETE(4),
    LIME_CONCRETE(5),
    PINK_CONCRETE(6),
    GRAY_CONCRETE(7),
    LIGHT_GRAY_CONCRETE(8),
    CYAN_CONCRETE(9),
    PURPLE_CONCRETE(10),
    BLUE_CONCRETE(11),
    BROWN_CONCRETE(12),
    GREEN_CONCRETE(13),
    RED_CONCRETE(14),
    BLACK_CONCRETE(15);

    private int value;

    Concrete(int value){this.value = value;}

    @Override
    public int getBlockId() {
        return Material.MATERIALS.get("CONCRETE").getValue();
    }

    @Override
    public byte getBlockData() {
        return (byte)value;
    }

    @Override
    public int getTransparency() {
        return 0;
    }

}
