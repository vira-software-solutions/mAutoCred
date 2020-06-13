import java.io.File;
import java.io.IOException;

import net.morbz.minecraft.blocks.Material;
import net.morbz.minecraft.blocks.states.Facing5State;
import net.morbz.minecraft.level.FlatGenerator;
import net.morbz.minecraft.level.GameType;
import net.morbz.minecraft.level.IGenerator;
import net.morbz.minecraft.level.Level;
import net.morbz.minecraft.world.DefaultLayers;
import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.blocks.PistonHead;
import net.therealvira.minecraft.blocks.StickyPiston;
import net.therealvira.minecraft.blocks.Vector3;
import net.therealvira.minecraft.gates.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class Main {
    public static void main(String[] args) throws IOException {
        World worldToGenerate;
        if (args.length == 0 || args[0].isEmpty()) {
            worldToGenerate = createDebugWorld();
        } else {
            worldToGenerate = createWorldFromJSON(args[0]);
        }

        if (worldToGenerate == null) {
            return;
        }

        // Everything's set up so we're going to save the world.
        worldToGenerate.save();
    }

    private static DefaultLayers generateDefaultLayers() {
        DefaultLayers layers = new DefaultLayers();
        layers.setLayer(0, Material.MATERIALS.get("BEDROCK"));
        layers.setLayer(1, Material.MATERIALS.get("CONCRETE"));

        return layers;
    }

    private static Level generateDefaultLevel(DefaultLayers layers, String worldname) {
        IGenerator generator = new FlatGenerator(layers);

        Level level = new Level(worldname, generator);
        level.setGameType(GameType.CREATIVE);
        level.setMapFeatures(false);

        level.setSpawnPoint(0, 3, 0);
        return level;
    }

    private static World createWorldFromJSON(String file) {
        File f = new File(file);
        if (!(f.exists() && !f.isDirectory() && f.canRead())) {
            System.out.println("ERROR; File does not exist or am unable to read from it...");
            return null;
        }

        DefaultLayers layers = generateDefaultLayers();
        Level level = generateDefaultLevel(layers, FilenameUtils.removeExtension(f.getName()));
        World world = new World(level, layers);

        // TODO: parse file -> need more information about file format and content.

        return world;
    }

    private static World createDebugWorld() {
        DefaultLayers layers = generateDefaultLayers();
        Level level = generateDefaultLevel(layers, "debug");
        World world = new World(level, layers);

        new Negate().initialize(new Vector3(0, 2, 10), Direction.North, world);
        new Negate().initialize(new Vector3(0, 2, 20), Direction.East, world);
        new Negate().initialize(new Vector3(0, 2, 30), Direction.South, world);
        new Negate().initialize(new Vector3(0, 2, 40), Direction.West, world);

        new Or().initialize(new Vector3(10, 2, 10), Direction.North, world);
        new Or().initialize(new Vector3(10, 2, 20), Direction.East, world);
        new Or().initialize(new Vector3(10, 2, 30), Direction.South, world);
        new Or().initialize(new Vector3(10, 2, 40), Direction.West, world);

        new And().initialize(new Vector3(20, 2, 10), Direction.North, world);
        new And().initialize(new Vector3(20, 2, 20), Direction.East, world);
        new And().initialize(new Vector3(20, 2, 30), Direction.South, world);
        new And().initialize(new Vector3(20, 2, 40), Direction.West, world);

        new Repeater().initialize(new Vector3(30, 2, 10), Direction.North, world);
        new Repeater().initialize(new Vector3(30, 2, 20), Direction.East, world);
        new Repeater().initialize(new Vector3(30, 2, 30), Direction.South, world);
        new Repeater().initialize(new Vector3(30, 2, 40), Direction.West, world);

        new Bridge().initialize(new Vector3(40, 2, 10), Direction.North, world);
        new Bridge().initialize(new Vector3(40, 2, 20), Direction.East, world);
        new Bridge().initialize(new Vector3(40, 2, 30), Direction.South, world);
        new Bridge().initialize(new Vector3(40, 2, 40), Direction.West, world);

        world.setBlock(new Vector3(-1, 2, -1), new PistonHead(true, Facing5State.SOUTH));
        world.setBlock(new Vector3(-3, 2, -1), new PistonHead(true, Facing5State.EAST));
        world.setBlock(new Vector3(-5, 2, -1), new PistonHead(true, Facing5State.WEST));
        world.setBlock(new Vector3(-7, 2, -1), new PistonHead(true, Facing5State.NORTH));

        world.setBlock(new Vector3(0, 10, 0), world.detectBlockAtPosition(new Vector3(0, 0, 0)));

        return world;
    }
}