package net.therealvira.minecraft.gates;

import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.blocks.Concrete;
import net.therealvira.minecraft.blocks.Vector3;

public class Negate extends Gate {

    public Negate(NETcon net1){
        super(new NETcon[]{net1}, 1, Concrete.GRAY_CONCRETE);
    }

    @Override
    protected void place(Vector3 position, World world) {
        placeRedstoneONBlock(position,world, this.BlockSheme);
        placeTorchAtBlock(new Vector3(position.X,position.Y,position.Z+1), world, this.BlockSheme);
        placeRedstoneONBlock(this.OutputLocations[0],world, this.BlockSheme);
    }

    @Override
    protected void declareIOports(Vector3 position) {
        this.InputLocations[0]=position;
        this.OutputLocations[0]=new Vector3(position.X,position.Y,position.Z+3);
    }
}
