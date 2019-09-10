package net.therealvira.minecraft.gates;

import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.blocks.SimpleBlock;
import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.blocks.SimpleBlockAdvanced;
import net.therealvira.minecraft.blocks.Vector3;

public abstract class Gate {
    final Vector3[] InputLocations;
    final Vector3[] OutputLocations;

    Gate(int numberOfInputs, int numberOfOutputs)
    {
        InputLocations = new Vector3[numberOfInputs];
        OutputLocations = new Vector3[numberOfOutputs];
    }

    private Vector3 Position;
    private Object muteX = new Object();

    public final Gate initialize(Vector3 position, World world){
        synchronized (muteX){
            if(this.Position!=null){
                return null;
            }
            Position = position;
            place(Position, world);
        }

        return this;
    }

    /*
        Places a gate inside a world.
     */
    protected abstract void place(Vector3 position, World world);

    protected abstract void declareIOports(Vector3 position);

    static void placeRedstoneONBlock(Vector3 position, World world, IBlock block){
        world.setBlock(new Vector3(position.X,position.Y-1,position.Z), block);
        world.setBlock(position, SimpleBlock.REDSTONE_WIRE);
    }

    static void placeTorchOnBlock(Vector3 position, World world, IBlock block){
        world.setBlock(position, block);
        world.setBlock(new Vector3(position.X,position.Y+1,position.Z), SimpleBlockAdvanced.STANDING_REDSTONE_TORCH);
    }

    static void placeTorchAtBlock(Vector3 position, World world, IBlock block){
        world.setBlock(position, block);
        world.setBlock(new Vector3(position.X,position.Y,position.Z+1), SimpleBlockAdvanced.REDSTONE_TORCH);
    }
}
