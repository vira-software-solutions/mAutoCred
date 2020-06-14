package net.therealvira.minecraft.gates;

import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.blocks.SimpleBlock;
import net.morbz.minecraft.world.World;
import net.therealvira.dto.Vector3;

public abstract class Gate {
    protected final IBlock BlockSheme;

    Gate(IBlock blockSheme) {
        BlockSheme = blockSheme;
    }

    /*
        Places this gate inside a world.
     */
    protected abstract void place(Vector3 position, Direction direction, World world);

    private Vector3 Position;
    private final Object muteX = new Object();

    public final Gate initialize(Vector3 position, Direction direction, World world) {
        synchronized (muteX) {
            if (this.Position != null) {
                return null;
            }
            Position = position;
            place(Position, direction, world);
        }

        return this;
    }

    static Vector3 rotatePositionRelativeToAnotherPosition(Vector3 position, Vector3 relative, Direction rotation) {
        double rad = Math.toRadians(rotation.rotations * 90);
        double sin = Math.sin(rad);
        double cos = Math.cos(rad);

        return new Vector3(
                (int) (relative.X + (position.X - relative.X) * cos - (position.Z - relative.Z) * sin),
                position.Y,
                (int) (relative.Z + (position.X - relative.X) * sin + (position.Z - relative.Z) * cos)
        );
    }

    static void placeRedstoneONBlock(Vector3 position, World world, IBlock block) {
        world.setBlock(new Vector3(position.X, position.Y - 1, position.Z), block);
        world.setBlock(position, SimpleBlock.REDSTONE_WIRE);
    }
}
