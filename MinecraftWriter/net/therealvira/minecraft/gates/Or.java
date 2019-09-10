package net.therealvira.minecraft.gates;

import net.morbz.minecraft.blocks.SimpleBlock;
import net.morbz.minecraft.blocks.states.Facing4State;
import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.blocks.Concrete;
import net.therealvira.minecraft.blocks.Repeater;
import net.therealvira.minecraft.blocks.Vector3;

public class Or extends Gate {
    public Or() {
        super(2, 1);
    }

    @Override
    protected void place(Vector3 position, World world) {
        // Declare input and output locations
        declareIOports(position);

        //Place blocks
        placeRedstoneONBlock(position, world, Concrete.LIGHT_BLUE_CONCRETE);
        placeRedstoneONBlock(this.InputLocations[1], world, Concrete.LIGHT_BLUE_CONCRETE);

        world.setBlock(new Vector3(position.X,position.Y,position.Z+1), new Repeater(false, Facing4State.SOUTH, (byte) 1));
        world.setBlock(new Vector3(position.X+2,position.Y,position.Z+1), new Repeater(false, Facing4State.SOUTH, (byte) 1));

        world.setBlock(new Vector3(position.X,position.Y,position.Z+2), SimpleBlock.REDSTONE_WIRE);
        world.setBlock(new Vector3(position.X+1,position.Y,position.Z+2), SimpleBlock.REDSTONE_WIRE);
        world.setBlock(new Vector3(position.X+2,position.Y,position.Z+2), SimpleBlock.REDSTONE_WIRE);

        world.setBlock(this.OutputLocations[0], SimpleBlock.REDSTONE_WIRE);
    }

    @Override
    protected void declareIOports(Vector3 position) {
        this.InputLocations[0]=position;
        this.InputLocations[1]=new Vector3(position.X+2,position.Y,position.Z);

        this.OutputLocations[0]=new Vector3(position.X+1,position.Y,position.Z+3);
    }
}
