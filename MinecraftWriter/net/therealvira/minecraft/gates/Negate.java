package net.therealvira.minecraft.gates;

import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.blocks.RedstoneTorchBlock;
import net.morbz.minecraft.blocks.states.Facing5State;
import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.blocks.Concrete;

public class Negate extends Gate {

    public Negate(Gate input){
        super(new Gate[]{input});
    }

    private static final IBlock GRAY_BLOCK = Concrete.GRAY_CONCRETE;

    @Override
    public void place(int x, int y, int z, World world) {
        placeRedstoneONBlock(x,y,z,world, GRAY_BLOCK);
        placeTorchAtBlock(x,y,z+1, world, GRAY_BLOCK);
        placeRedstoneONBlock(x,y,z+3,world, GRAY_BLOCK);
    }
}
