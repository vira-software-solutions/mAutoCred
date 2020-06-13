package net.therealvira.minecraft.gates;

import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.blocks.*;

public class Repeater extends Gate {
    public Repeater() {
        super(Concrete.RED_CONCRETE);
    }

    @Override
    protected void place(Vector3 position, Direction direction, World world) {
        Vector3 relativePoint = new Vector3(position.X, position.Y, position.Z + 1);

        world.setBlock(rotatePositionRelativeToAnotherPosition(position, relativePoint, direction), this.BlockSheme);
        world.setBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X, position.Y + 1, position.Z + 2), relativePoint, direction), new RedstoneBlock());

        world.setBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X, position.Y + 1, position.Z), relativePoint, direction), new StickyPiston(true, direction.invert().convert()));
        world.setBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X, position.Y + 1, position.Z + 1), relativePoint, direction), new PistonHead(true,  direction.invert().convert()));

        world.setBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X, position.Y, position.Z + 2), relativePoint, direction), new StickyPiston(true, direction.convert()));
        world.setBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X, position.Y, position.Z + 1), relativePoint, direction), new PistonHead(true, direction.convert()));
    }
}
