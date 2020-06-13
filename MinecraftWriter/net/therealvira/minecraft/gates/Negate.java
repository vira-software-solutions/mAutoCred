package net.therealvira.minecraft.gates;

import net.morbz.minecraft.blocks.RedstoneTorchBlock;
import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.blocks.Concrete;
import net.therealvira.minecraft.blocks.SimpleBlockAdvanced;
import net.therealvira.minecraft.blocks.Vector3;

public class Negate extends Gate {

    public Negate() {
        super(Concrete.GRAY_CONCRETE);
    }

    @Override
    protected void place(Vector3 position, Direction direction, World world) {
        placeRedstoneONBlock(position, world, this.BlockSheme);
        world.setBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X, position.Y, position.Z + 1), position, direction), this.BlockSheme);
        world.setBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X, position.Y, position.Z + 2), position, direction), new RedstoneTorchBlock(true, direction.invert().convert()));
    }
}
