package net.therealvira.minecraft.gates;

import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.blocks.SimpleBlock;
import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.blocks.SimpleBlockAdvanced;
import net.therealvira.minecraft.blocks.Vector3;

public abstract class Gate {
    final Vector3[] InputLocations;
    final Vector3[] OutputLocations;

    protected final IBlock BlockSheme;
    private final NETcon[] NETcons;

    Gate(NETcon[] NETs, int numberOfOutputs, IBlock blockSheme)
    {
        NETcons = NETs;
        InputLocations = new Vector3[NETs.length];
        OutputLocations = new Vector3[numberOfOutputs];
        BlockSheme = blockSheme;
    }

    /*
        Decleares NET ports.
        This method will get called before "place".
     */
    protected abstract void declareIOports(Vector3 position);

    /*
        Places this gate inside a world.
     */
    protected abstract void place(Vector3 position, World world);

    private void connectNETs(World world){
        for (int i=0;i<NETcons.length;i++){
            if(NETcons[i]==null){
                continue;
            }

            RedstoneConnector.PlaceConnection(
                    NETcons[i].NET.OutputLocations[NETcons[i].NetNum],
                    InputLocations[i],
                    world);
        }
    }

    private Vector3 Position;
    private final Object muteX = new Object();

    public final Gate initialize(Vector3 position, World world){
        synchronized (muteX){
            if(this.Position!=null){
                return null;
            }
            Position = position;
            declareIOports(position);
            place(Position, world);
            connectNETs(world);
        }

        return this;
    }

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
