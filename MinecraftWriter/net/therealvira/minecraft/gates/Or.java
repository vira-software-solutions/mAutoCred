package net.therealvira.minecraft.gates;

import net.morbz.minecraft.blocks.SimpleBlock;
import net.morbz.minecraft.blocks.states.Facing4State;
import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.blocks.Concrete;
import net.therealvira.minecraft.blocks.Repeater;

public class Or extends Gate {
    public Or(Gate i1, Gate i2) {
        super(new Gate[]{i1, i2});
    }

    @Override
    public void place(int x, int y, int z, World world) {
        placeRedstoneONBlock(x,y,z, world, Concrete.LIGHT_BLUE_CONCRETE);
        placeRedstoneONBlock(x+2,y,z, world, Concrete.LIGHT_BLUE_CONCRETE);

        world.setBlock(x,y,z+1, new Repeater(false, Facing4State.SOUTH, (byte) 1));
        world.setBlock(x+2,y,z+1, new Repeater(false, Facing4State.SOUTH, (byte) 1));

        world.setBlock(x,y,z+2, SimpleBlock.REDSTONE_WIRE);
        world.setBlock(x+1,y,z+2, SimpleBlock.REDSTONE_WIRE);
        world.setBlock(x+2,y,z+2, SimpleBlock.REDSTONE_WIRE);

        world.setBlock(x+1, y, z+3, SimpleBlock.REDSTONE_WIRE);
    }
}
