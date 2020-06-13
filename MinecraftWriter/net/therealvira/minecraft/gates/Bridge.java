package net.therealvira.minecraft.gates;

import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.blocks.Concrete;
import net.therealvira.minecraft.blocks.Vector3;

public class Bridge extends Gate {
    public Bridge() {
        super(Concrete.GREEN_CONCRETE);
    }

    @Override
    protected void place(Vector3 position, Direction direction, World world) {
        Vector3 relativePoint = new Vector3(position.X, position.Y, position.Z + 1);

        placeRedstoneONBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X, position.Y + 1, position.Z), relativePoint, direction), world, this.BlockSheme);
        placeRedstoneONBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X, position.Y + 2, position.Z + 1), relativePoint, direction), world, this.BlockSheme);
        placeRedstoneONBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X, position.Y + 1, position.Z + 2), relativePoint, direction), world, this.BlockSheme);

        placeRedstoneONBlock(relativePoint, world, this.BlockSheme);
    }
}
