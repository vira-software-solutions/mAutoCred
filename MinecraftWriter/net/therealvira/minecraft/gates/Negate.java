package net.therealvira.minecraft.gates;

import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.blocks.Concrete;
import net.therealvira.minecraft.blocks.Vector3;

public class Negate extends Gate {

    public Negate(){
        super(1, 1);
    }

    private static final IBlock GRAY_BLOCK = Concrete.GRAY_CONCRETE;

    @Override
    protected void place(Vector3 position, World world) {
        // Declare input and output locations
        declareIOports(position);

        // Place blocks
        placeRedstoneONBlock(position,world, GRAY_BLOCK);
        placeTorchAtBlock(new Vector3(position.X,position.Y,position.Z+1), world, GRAY_BLOCK);
        placeRedstoneONBlock(this.OutputLocations[0],world, GRAY_BLOCK);
    }

    @Override
    protected void declareIOports(Vector3 position) {
        this.InputLocations[0]=position;
        this.OutputLocations[0]=new Vector3(position.X,position.Y,position.Z+3);
    }
}
