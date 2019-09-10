package net.therealvira.minecraft.gates;

import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.blocks.Concrete;
import net.therealvira.minecraft.blocks.SimpleBlockAdvanced;
import net.therealvira.minecraft.blocks.Vector3;

public class And extends Gate {
    public And() {
        super( 2, 1);
    }

    @Override
    protected void place(Vector3 position, World world) {
        // Declare input and output locations
        declareIOports(position);

        // Place blocks
        placeRedstoneONBlock(position, world, Concrete.LIME_CONCRETE);
        placeRedstoneONBlock(this.InputLocations[1], world, Concrete.LIME_CONCRETE);

        placeTorchOnBlock(new Vector3(position.X+2,position.Y,position.Z+1), world, Concrete.LIME_CONCRETE);
        placeRedstoneONBlock(new Vector3(position.X+1,position.Y+1,position.Z+1), world, Concrete.LIME_CONCRETE);
        placeTorchOnBlock(new Vector3(position.X,position.Y,position.Z+1), world, Concrete.LIME_CONCRETE);

        world.setBlock(new Vector3(position.X+1,position.Y,position.Z+1), SimpleBlockAdvanced.REDSTONE_TORCH);
        placeRedstoneONBlock(this.OutputLocations[0], world, Concrete.LIME_CONCRETE);
    }

    @Override
    protected void declareIOports(Vector3 position) {
        this.InputLocations[0]=position;
        this.InputLocations[1]=new Vector3(position.X+2,position.Y,position.Z);

        this.OutputLocations[0]=new Vector3(position.X+1,position.Y,position.Z+3);
    }
}
