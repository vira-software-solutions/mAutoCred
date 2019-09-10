package net.therealvira.minecraft.gates;

import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.blocks.Material;
import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.blocks.Concrete;
import net.therealvira.minecraft.blocks.Vector3;

public final class RedstoneConnector {
    private RedstoneConnector() {

    }

    public static void InitializeConnections(IOcomConnection[] COMs, World world) {
        for (IOcomConnection com :
                COMs) {
            PlaceConnection(com.O.OutputLocations[com.Ocom],
                    com.I.InputLocations[com.Icom],
                    Concrete.YELLOW_CONCRETE,
                    world);
        }
    }

    public static void PlaceConnection(Vector3 from, Vector3 to, IBlock block, World world) {
        var step = CalculateConnectionStep(from, to);
        while (!step.equals(Vector3.ZERO)) {
            from = Vector3.Add(from, step);
            Gate.placeRedstoneONBlock(from, world, block);
            step = CalculateConnectionStep(from, to);
        }
    }

    /*
        Calculates the next possible step to place a connection point on.
     */
    private static Vector3 CalculateConnectionStep(Vector3 from, Vector3 to) {
        if (from.equals(to)) {
            return Vector3.ZERO;
        }

        var xDist = Math.abs(from.X-to.X);
        var yDist = Math.abs(from.Y-to.Y);
        var zDist = Math.abs(from.Z-to.Z);

        if(xDist>yDist&&xDist>zDist){
            return new Vector3(ComputeStep(from.X, to.X),0,0);
        }
        else if(yDist>zDist){
            return new Vector3(0, ComputeStep(from.Y, to.Y), 0);
        }
        else{
            return new Vector3(0,0,ComputeStep(from.Z, to.Z));
        }
    }

    private static int ComputeStep(int current, int goal) {
        if (current == goal) {
            return 0;
        }

        if (current < goal) {
            return 1;
        }

        return -1;
    }
}