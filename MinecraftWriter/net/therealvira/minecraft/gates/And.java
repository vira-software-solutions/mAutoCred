package net.therealvira.minecraft.gates;

import net.morbz.minecraft.blocks.RedstoneTorchBlock;
import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.blocks.Concrete;
import net.therealvira.minecraft.blocks.SimpleBlockAdvanced;
import net.therealvira.minecraft.blocks.Vector3;

public class And extends Gate {
    public And() {
        super(Concrete.LIME_CONCRETE);
    }

    @Override
    protected void place(Vector3 position, Direction direction, World world) {
        placeRedstoneONBlock(position, world, this.BlockSheme);
        placeRedstoneONBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X + 2, position.Y, position.Z), position, direction), world, this.BlockSheme);

        world.setBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X, position.Y, position.Z + 1), position, direction), this.BlockSheme);
        placeRedstoneONBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X + 1, position.Y + 1, position.Z + 1), position, direction), world, this.BlockSheme);
        world.setBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X + 2, position.Y, position.Z + 1), position, direction), this.BlockSheme);

        world.setBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X, position.Y + 1, position.Z + 1), position, direction), SimpleBlockAdvanced.STANDING_REDSTONE_TORCH);
        world.setBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X + 2, position.Y + 1, position.Z + 1), position, direction), SimpleBlockAdvanced.STANDING_REDSTONE_TORCH);

        world.setBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X + 1, position.Y, position.Z + 2), position, direction), new RedstoneTorchBlock(false, direction.invert().convert()));
    }
}
