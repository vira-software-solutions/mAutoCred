import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import net.morbz.minecraft.blocks.Material;
import net.morbz.minecraft.level.FlatGenerator;
import net.morbz.minecraft.level.GameType;
import net.morbz.minecraft.level.IGenerator;
import net.morbz.minecraft.level.Level;
import net.morbz.minecraft.world.DefaultLayers;
import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.gates.And;
import net.therealvira.minecraft.gates.Negate;
import net.therealvira.minecraft.gates.Or;
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

        (new Or(null, null)).place(0,2,0, world);


        /// DEBUG!!

        final File ORIGIN = new File("E:\\Minecraft\\mAutoCred\\worlds\\test");
        final File MASTER = new File("C:\\Users\\Vira\\AppData\\Roaming\\.minecraft\\saves\\test");
        if (ORIGIN.exists()){
            FileUtils.deleteDirectory(ORIGIN);
        }

        if (MASTER.exists()){
            FileUtils.deleteDirectory(MASTER);
        }

        // Everything's set up so we're going to save the world.
        world.save();

        FileUtils.copyDirectory(ORIGIN, MASTER);
    }
}