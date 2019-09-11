import java.io.File;
import java.io.IOException;

import net.morbz.minecraft.blocks.Material;
import net.morbz.minecraft.level.FlatGenerator;
import net.morbz.minecraft.level.GameType;
import net.morbz.minecraft.level.IGenerator;
import net.morbz.minecraft.level.Level;
import net.morbz.minecraft.world.DefaultLayers;
import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.blocks.Vector3;
import net.therealvira.minecraft.gates.*;
import org.apache.commons.io.FileUtils;

public class Main {
    public static void main(String[] args) throws IOException {
        // String graphFile = args[0];
        // String worldName = args[1];

        DefaultLayers layers = new DefaultLayers();
        layers.setLayer(0, Material.BEDROCK);
        layers.setLayer(1, Material.MELON_BLOCK);

        IGenerator generator = new FlatGenerator(layers);

        Level level = new Level("test", generator);
        level.setGameType(GameType.CREATIVE);
        level.setSpawnPoint(0, 0, 3);

        World world = new World(level, layers);

        var n = new Negate(null).initialize(new Vector3(0,2,0), world);
        var o = new Or(null, new NETcon(n, 0)).initialize(new Vector3(5,2,5), world);

        /// DEBUG!!
        final File SOURCE = new File("E:\\Minecraft\\mAutoCred\\worlds\\test");
        final File DESTINATION = new File("C:\\Users\\Vira\\AppData\\Roaming\\.minecraft\\saves\\test");
        if (SOURCE.exists()){
            FileUtils.deleteDirectory(SOURCE);
        }

        if (DESTINATION.exists()){
            FileUtils.deleteDirectory(DESTINATION);
        }

        // Everything's set up so we're going to save the world.
        world.save();

        FileUtils.copyDirectory(SOURCE, DESTINATION);
    }
}