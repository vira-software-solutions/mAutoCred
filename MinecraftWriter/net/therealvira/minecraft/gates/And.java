package net.therealvira.minecraft.gates;

import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.blocks.Concrete;
import net.therealvira.minecraft.blocks.SimpleBlockAdvanced;

public class And extends Gate {
    public And(Gate i1, Gate i2) {
        super(new Gate[]{i1, i2});
    }

    @Override
    public void place(int x, int y, int z, World world) {
        placeRedstoneONBlock(x,y,z, world, Concrete.LIME_CONCRETE);
        placeRedstoneONBlock(x+2,y,z, world, Concrete.LIME_CONCRETE);

        placeTorchOnBlock(x+2,y,z+1, world, Concrete.LIME_CONCRETE);
        placeRedstoneONBlock(x+1,y+1,z+1, world, Concrete.LIME_CONCRETE);
        placeTorchOnBlock(x,y,z+1, world, Concrete.LIME_CONCRETE);

        world.setBlock(x+1,y,z+1, SimpleBlockAdvanced.REDSTONE_TORCH);
        placeRedstoneONBlock(x+1,y,z+3, world, Concrete.LIME_CONCRETE);
    }
}
