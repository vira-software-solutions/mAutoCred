package net.therealvira.minecraft.gates;

import net.morbz.minecraft.blocks.Material;
import net.morbz.minecraft.blocks.states.Facing4State;
import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.blocks.Concrete;
import net.therealvira.minecraft.blocks.Glowstone;
import net.therealvira.minecraft.blocks.Repeater;
import net.therealvira.minecraft.blocks.Vector3;

public class Or extends Gate {
    public Or() {
        super(Concrete.LIGHT_BLUE_CONCRETE);
    }

    @Override
    protected void place(Vector3 position, Direction direction, World world) {
        placeRedstoneONBlock(position, world, this.BlockSheme);
        placeRedstoneONBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X + 1, position.Y + 1, position.Z), position, direction), world, new Glowstone());
        placeRedstoneONBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X + 2, position.Y, position.Z), position, direction), world, this.BlockSheme);
        placeRedstoneONBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X + 1, position.Y + 1, position.Z + 1), position, direction), world, this.BlockSheme);
    }
}
