package net.therealvira.minecraft.gates;

import net.morbz.minecraft.world.World;
import net.therealvira.dto.Vector3;
import net.therealvira.minecraft.blocks.Concrete;

public class Line extends Gate {
    public Line() {
        super(Concrete.WHITE_CONCRETE);
    }

    public Line(int lenght) {
        super(Concrete.WHITE_CONCRETE);
        this.length = lenght;
    }

    private int length;

    @Override
    protected void place(Vector3 position, Direction direction, World world) {
        for (int i = 0; i < length; i++) {
            placeRedstoneONBlock(rotatePositionRelativeToAnotherPosition(new Vector3(position.X, position.Y, position.Z - i), position, direction), world, this.BlockSheme);
        }
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
