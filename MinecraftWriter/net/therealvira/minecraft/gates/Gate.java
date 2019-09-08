package net.therealvira.minecraft.gates;

import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.blocks.SimpleBlock;
import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.blocks.SimpleBlockAdvanced;

public abstract class Gate {
    private Gate[] InputGates;

    public Gate(Gate[] inputGates)
    {
        InputGates = inputGates;
    }

    /*
        Places a gate inside a world.
     */
    public abstract void place(int x, int y, int z, World world);

    public static final void placeRedstoneONBlock(int x, int y, int z, World world, IBlock block){
        world.setBlock(x,y-1,z, block);
        world.setBlock(x,y,z, SimpleBlock.REDSTONE_WIRE);
    }

    public static final void placeTorchOnBlock(int x, int y, int z, World world, IBlock block){
        world.setBlock(x,y,z, block);
        world.setBlock(x,y+1,z, SimpleBlockAdvanced.STANDING_REDSTONE_TORCH);
    }

    public static final void placeTorchAtBlock(int x, int y, int z, World world, IBlock block){
        world.setBlock(x,y,z, block);
        world.setBlock(x,y,z+1, SimpleBlockAdvanced.REDSTONE_TORCH);
    }
}
