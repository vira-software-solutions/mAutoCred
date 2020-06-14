package net.therealvira.minecraft.gates;


import net.morbz.minecraft.world.World;

import java.lang.reflect.InvocationTargetException;

public class GateFactory {
    public static void placeGate(net.therealvira.dto.Gate gate, World world) {
        getGateFromName(gate.getType()).initialize(gate.getPosition(), gate.getDirection(), world);
    }

    public static void placeLine(net.therealvira.dto.Line line, World world) {
        Line toplace = new Line();
        toplace.setLength(line.getLength());
        toplace.initialize(line.getPosition(), line.getDirection(), world);
    }

    private static Gate getGateFromName(String name) {
        try {
            return (Gate) Class.forName("net.therealvira.minecraft.gates." + name.toUpperCase()).getConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            System.out.println("ERROR; Gate was not found: " + name);
            e.printStackTrace();
            return null;
        }
    }
}
