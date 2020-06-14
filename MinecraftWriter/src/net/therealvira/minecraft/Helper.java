package net.therealvira.minecraft;

import net.morbz.minecraft.blocks.Material;
import net.morbz.minecraft.level.FlatGenerator;
import net.morbz.minecraft.level.GameType;
import net.morbz.minecraft.level.IGenerator;
import net.morbz.minecraft.level.Level;
import net.morbz.minecraft.world.DefaultLayers;
import net.morbz.minecraft.world.World;
import net.therealvira.dto.Gate;
import net.therealvira.dto.Line;
import net.therealvira.minecraft.gates.GateFactory;

import java.util.Arrays;

public final class Helper {
    public static <T> T convertInstanceOfObject(Object o, Class<T> clazz) {
        try {
            return clazz.cast(o);
        } catch(ClassCastException e) {
            return null;
        }
    }

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static World convert(net.therealvira.dto.World toCopy){
        DefaultLayers layers = generateDefaultLayers();
        Level level = generateDefaultLevel(layers, toCopy.getName());
        World world = new World(level, layers);

        for (Gate gate : toCopy.getGates()){
            GateFactory.placeGate(gate, world);
        }

        for(Line line : toCopy.getLines()){
            GateFactory.placeLine(line, world);
        }

        return world;
    }

    public static DefaultLayers generateDefaultLayers() {
        DefaultLayers layers = new DefaultLayers();
        layers.setLayer(0, Material.MATERIALS.get("BEDROCK"));
        layers.setLayer(1, Material.MATERIALS.get("CONCRETE"));

        return layers;
    }

    public static Level generateDefaultLevel(DefaultLayers layers, String worldname) {
        IGenerator generator = new FlatGenerator(layers);

        Level level = new Level(worldname, generator);
        level.setGameType(GameType.CREATIVE);
        level.setMapFeatures(false);

        level.setSpawnPoint(0, 3, 0);
        return level;
    }
}
