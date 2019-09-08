import java.io.IOException;

import net.morbz.minecraft.blocks.DoorBlock;
import net.morbz.minecraft.blocks.Material;
import net.morbz.minecraft.blocks.SimpleBlock;
import net.morbz.minecraft.blocks.DoorBlock.DoorMaterial;
import net.morbz.minecraft.blocks.DoorBlock.HingeSide;
import net.morbz.minecraft.blocks.states.Facing4State;
import net.morbz.minecraft.level.FlatGenerator;
import net.morbz.minecraft.level.GameType;
import net.morbz.minecraft.level.IGenerator;
import net.morbz.minecraft.level.Level;
import net.morbz.minecraft.world.DefaultLayers;
import net.morbz.minecraft.world.World;

public class Main {
    public static void main(String[] args) throws IOException {
        String graphFile = args[0];

        DefaultLayers layers = new DefaultLayers();
        layers.setLayer(0, Material.BEDROCK);
        layers.setLayers(1, 20, Material.MELON_BLOCK);

        // Create the internal Minecraft world generator.
        // We use a flat generator. We do this to make sure that the whole world will be paved
        // with melons and not just the part we generated.
        IGenerator generator = new FlatGenerator(layers);

        // Create the level configuration.
        // We set the mode to creative creative mode and name our world. We also set the spawn point
        // in the middle of our glass structure.
        Level level = new Level("", generator);
        level.setGameType(GameType.CREATIVE);
        level.setSpawnPoint(50, 0, 50);

        // Now we create the world. This is where we can set our own blocks.
        World world = new World(level, layers);



        // Now we create the door. It consists of 2 blocks, that's why we can't use a SimpleBlock
        // here.
        world.setBlock(50, 51, 50, DoorBlock.makeLower(DoorMaterial.OAK, Facing4State.EAST, false));
        world.setBlock(50, 52, 50, DoorBlock.makeUpper(DoorMaterial.OAK, HingeSide.LEFT));

        // Everything's set up so we're going to save the world.
        world.save();
    }
}