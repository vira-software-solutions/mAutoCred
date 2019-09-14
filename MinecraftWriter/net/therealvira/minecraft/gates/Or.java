package net.therealvira.minecraft.gates;

import net.morbz.minecraft.blocks.states.Facing4State;
import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.blocks.Concrete;
import net.therealvira.minecraft.blocks.Repeater;
import net.therealvira.minecraft.blocks.Vector3;

public class Or extends Gate {
    public Or(NETcon net1, NETcon net2) {
        super(new NETcon[]{net1, net2}, 1, Concrete.LIGHT_BLUE_CONCRETE);
    }

    @Override
    protected void place(Vector3 position, World world) {
        placeRedstoneONBlock(position, world, this.BlockSheme);
        placeRedstoneONBlock(this.InputLocations[1], world, this.BlockSheme);

        world.setBlock(new Vector3(position.X,position.Y,position.Z+1), new Repeater(false, Facing4State.SOUTH, (byte) 1));
        world.setBlock(new Vector3(position.X+2,position.Y,position.Z+1), new Repeater(false, Facing4State.SOUTH, (byte) 1));

        placeRedstoneONBlock(new Vector3(position.X,position.Y,position.Z+2), world,  this.BlockSheme);
        placeRedstoneONBlock(new Vector3(position.X+1,position.Y,position.Z+2), world,  this.BlockSheme);
        placeRedstoneONBlock(new Vector3(position.X+2,position.Y,position.Z+2), world,  this.BlockSheme);

        placeRedstoneONBlock(this.OutputLocations[0], world, this.BlockSheme);
    }

    @Override
    protected void declareIOports(Vector3 position) {
        this.InputLocations[0]=position;
        this.InputLocations[1]=new Vector3(position.X+2,position.Y,position.Z);

        this.OutputLocations[0]=new Vector3(position.X+1,position.Y,position.Z+3);
    }
}
