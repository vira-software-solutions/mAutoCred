import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.morbz.minecraft.world.World;
import net.therealvira.minecraft.Helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        World worldToGenerate;
        if (args.length == 0 || args[0].isEmpty()) {
            worldToGenerate = createWorldFromJSON("./Example/exmaple0.json");
        } else {
            worldToGenerate = createWorldFromJSON(args[0]);
        }

        if (worldToGenerate == null) {
            return;
        }

        // Everything's set up so we're going to save the world.
        worldToGenerate.save();
    }

    private static World createWorldFromJSON(String file) {
        File f = new File(file);
        if (!(f.exists() && !f.isDirectory() && f.canRead())) {
            System.out.println("ERROR; File does not exist or am unable to read from it...");
            return null;
        }

        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        World world = null;

        try {
            System.out.println("Reading from file...");
            world = Helper.convert(gson.fromJson(new FileReader(f), net.therealvira.dto.World.class));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return world;
    }
}