package net.therealvira.minecraft.blocks;

import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.blocks.Material;
import net.morbz.minecraft.blocks.RedstoneTorchBlock;
import net.morbz.minecraft.blocks.SimpleBlock;
import net.morbz.minecraft.blocks.states.Facing5State;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

public enum SimpleBlockAdvanced implements IBlock {
    STANDING_REDSTONE_TORCH(new RedstoneTorchBlock(true, Facing5State.UP)),
    REDSTONE_TORCH(new RedstoneTorchBlock(true, Facing5State.SOUTH));

    private IBlock block;

    SimpleBlockAdvanced(IBlock block){
        this.block = block;
    }

    @Override
    public int getBlockId() {
        return block.getBlockId();
    }

    @Override
    public byte getBlockData() {
        return block.getBlockData();
    }

    @Override
    public int getTransparency() {
        return block.getTransparency();
    }
}
