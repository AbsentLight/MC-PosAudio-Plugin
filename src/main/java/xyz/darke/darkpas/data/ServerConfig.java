package xyz.darke.darkpas.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.darke.darkpas.DarkPAS;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;

public class ServerConfig {

    private static final String filepath = "plugins/DarkPAS/config.json";

    public int getCutoffDistance() {
        return cutoffDistance;
    }

    public int getDropOffGradientCoefficient() {
        return dropOffGradientCoefficient;
    }

    public int getSafeZoneSize() {
        return safeZoneSize;
    }

    private int cutoffDistance;             // Distance in blocks after which a player cannot be heard
    private int dropOffGradientCoefficient; // Rate at which volume drops off (after safezone)
    private int safeZoneSize;               // Range where players broadcast at full volume

    public ServerConfig () {
        this.cutoffDistance = 64;
        this.dropOffGradientCoefficient = 5;
        this.safeZoneSize = 16;

        buildServerConfigFromDisk();
    }

    public String getConfigJson() {
        return String.format("{\"cutoffDistance\":%d,\"dropOffGradientCoefficient\":%d,\"safeZoneSize\":%d}",
                this.cutoffDistance, this.dropOffGradientCoefficient, this.safeZoneSize);
    }

    public void buildServerConfigFromDisk() {
        if (!new File(filepath).isFile()) {
            DarkPAS.log(Level.INFO, "Config wasn't found and will be created");
            this.writeServerConfigToDisk();
            return;
        } else {
            DarkPAS.log(Level.INFO, "Loading existing config");
        }

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Map configItems = Collections.emptyMap();
        try {
            FileReader reader = new FileReader(filepath);
            configItems = gson.fromJson(reader, Map.class);
            reader.close();
        } catch (Exception e) {
            DarkPAS.log(Level.WARNING, String.format("Failed to read %s from disk", filepath));
            e.printStackTrace();
        }

        this.cutoffDistance = ((Double) configItems.get("cutoffDistance")).intValue();
        this.dropOffGradientCoefficient = ((Double) configItems.get("dropOffGradientCoefficient")).intValue();
        this.safeZoneSize = ((Double) configItems.get("safeZoneSize")).intValue();

    }

    public void writeServerConfigToDisk() {
        try {
            FileWriter writer = new FileWriter(filepath);
            writer.write(this.getConfigJson());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            DarkPAS.log(Level.WARNING, String.format("Failed to write %s to disk", filepath));
            e.printStackTrace();
        }
    }
}
