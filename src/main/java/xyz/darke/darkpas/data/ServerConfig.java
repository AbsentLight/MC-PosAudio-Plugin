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

    private int cutoffDistance;                 // Distance in blocks after which a player cannot be heard
    private int attenuationCoefficient;         // Rate at which volume drops off (after safe-zone)
    private int safeZoneSize;                   // Range where players broadcast at full volume

    private boolean unregisteredCanBroadcast;   // If players aren't registered, do they get heard

    public ServerConfig () {
        this.cutoffDistance = 64;
        this.attenuationCoefficient = 5;
        this.safeZoneSize = 16;
        this.unregisteredCanBroadcast = true;

        buildServerConfigFromDisk();
    }

    public String getConfigJson() {
        return String.format("{\"cutoffDistance\":%d," +
                             "\"attenuationCoefficient\":%d," +
                             "\"safeZoneSize\":%d," +
                             "\"unregisteredCanBroadcast\":%s}",
                this.cutoffDistance, this.attenuationCoefficient, this.safeZoneSize,
                this.unregisteredCanBroadcast ? "true" : "false");
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

        try {
            this.cutoffDistance = ((Double) configItems.get("cutoffDistance")).intValue();
            this.attenuationCoefficient = ((Double) configItems.get("attenuationCoefficient")).intValue();
            this.safeZoneSize = ((Double) configItems.get("safeZoneSize")).intValue();
            this.unregisteredCanBroadcast = (boolean) configItems.get("unregisteredCanBroadcast");
        } catch (Exception e) {
            DarkPAS.log(Level.WARNING, "Failed to parse config data from disk");
        }
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

    public int getCutoffDistance() {
        return cutoffDistance;
    }

    public int getAttenuationCoefficient() {
        return attenuationCoefficient;
    }

    public int getSafeZoneSize() {
        return safeZoneSize;
    }

    public void setCutoffDistance(int cutoffDistance) {
        this.cutoffDistance = cutoffDistance;
    }

    public void setAttenuationCoefficient(int attenuationCoefficient) {
        this.attenuationCoefficient = attenuationCoefficient;
    }

    public void setSafeZoneSize(int safeZoneSize) {
        this.safeZoneSize = safeZoneSize;
    }

    public boolean isUnregisteredCanBroadcast() {
        return unregisteredCanBroadcast;
    }

    public void setUnregisteredCanBroadcast(boolean unregisteredCanBroadcast) {
        this.unregisteredCanBroadcast = unregisteredCanBroadcast;
    }

}
