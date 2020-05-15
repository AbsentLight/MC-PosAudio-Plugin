package xyz.darke.darkpas.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.darke.darkpas.DarkPAS;
import xyz.darke.darkpas.util.MathUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerData {

    private static final String filepath = "plugins/DarkPAS/player_data.json";

    public Map<UUID, String> data = new HashMap<>();
    public Map<String, UUID> reverse = new HashMap<>();

    public void addUserInfo(Player player, String tsID) {
        if (data.containsKey(player.getUniqueId())) {
            String oldTsID = data.get(player.getUniqueId());
            reverse.remove(oldTsID);
        }

        data.put(player.getUniqueId(), tsID);
        reverse.put(tsID, player.getUniqueId());
    }

    public void addUserInfo(UUID uuid, String tsID) {
        if (data.containsKey(uuid)) {
            String oldTsID = data.get(uuid);
            reverse.remove(oldTsID);
        }

        data.put(uuid, tsID);
        reverse.put(tsID, uuid);
    }

    public String getPlayerPositions() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        Collection<? extends Player> playerList = Bukkit.getOnlinePlayers();

        Map<String, double[]> tsIDPosLookup = new HashMap<>();

        for (Player player : playerList) {
            String tsID = data.get(player.getUniqueId());
            Location playerLocation = player.getLocation();

            double[] location = new double[3];
            location[0] = playerLocation.getX();
            location[1] = playerLocation.getY();
            location[2] = playerLocation.getZ();

            tsIDPosLookup.put(tsID, location);
        }

        if (tsIDPosLookup.size() > 0) {
            return objectMapper.writeValueAsString(tsIDPosLookup);
        } else {
            return "{}";
        }

    }

    public String getPlayerRelativePositions(String playerTsID) throws JsonProcessingException {
        if (playerTsID == null) {
            System.out.println("ID Null");
            return null;
        }

        Player player = Bukkit.getPlayer(reverse.get(playerTsID));

        if (player == null) {
            System.out.println("reverse player null");
            return null;
        }

        Collection<? extends Player> playerList = Bukkit.getOnlinePlayers();

        Map<String, double[]> tsIDPosLookup = new HashMap<>();

        Location playerLocation = player.getLocation();
        tsIDPosLookup.put(playerTsID, new double[]{0.0, 0.0, 0.0, 0.0});

        for (Player rPlayer : playerList) {
            if (rPlayer.getUniqueId() == player.getUniqueId()) {
                continue;
            }

            final String rTsID = data.get(rPlayer.getUniqueId());
            final Location rPlayerLocation = rPlayer.getLocation();

            double[]  playerVec = new double[]{ playerLocation.getX(),  playerLocation.getY(),  playerLocation.getZ()};
            double[] rPlayerVec = new double[]{rPlayerLocation.getX(), rPlayerLocation.getY(), rPlayerLocation.getZ()};

            rPlayerVec = MathUtil.normalise3D(playerVec, rPlayerVec);

            if (MathUtil.vectorMagnitude(rPlayerVec) > DarkPAS.serverConfig.getCutoffDistance()) {
                int x = DarkPAS.serverConfig.getCutoffDistance() * 2;
                tsIDPosLookup.put(rTsID, new double[]{x,x,x});
            } else {

                double[][] playerRotMatrix = MathUtil.formRotationMatrix(playerLocation.getYaw(), playerLocation.getPitch());
                double[][] rPlayerRelPos   = MathUtil.matrixMult(MathUtil.colVectorToMatrix(rPlayerVec), playerRotMatrix);


                double[] location = new double[3];
                location[0] = rPlayerRelPos[0][0];
                location[1] = rPlayerRelPos[0][1];
                location[2] = rPlayerRelPos[0][2];
                tsIDPosLookup.put(rTsID, location);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        if (tsIDPosLookup.size() > 0) {
            return objectMapper.writeValueAsString(tsIDPosLookup);
        } else {
            return "{}";
        }
    }

    public void loadPlayerIDMapCache() {

        if (!new File(filepath).isFile()) {
            DarkPAS.log(Level.INFO, "Player Data file wasn't found and will be created");
            this.purgePlayerIDMapCache();
            return;
        } else {
            DarkPAS.log(Level.INFO, "Existing Player Data found");
        }

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Map<String,String> playerIDMap = Collections.emptyMap();
        try {
            FileReader reader = new FileReader(filepath);
            playerIDMap = gson.fromJson(reader, Map.class);
            reader.close();
        } catch (Exception e) {
            DarkPAS.log(Level.WARNING, String.format("Failed to read %s from disk", filepath));
            e.printStackTrace();
        }

        for (String key : playerIDMap.keySet()) {
            this.addUserInfo(UUID.fromString(key), playerIDMap.get(key));
        }
    }

    public void writePlayerIDMapCache() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            FileWriter writer = new FileWriter(filepath);
            gson.toJson(this.data, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            DarkPAS.log(Level.WARNING, String.format("Failed to write %s to disk", filepath));
            e.printStackTrace();
        }
    }

    public void purgePlayerIDMapCache() {
        try {
            FileWriter writer = new FileWriter(filepath);
            writer.write("{}");
            writer.flush();
            writer.close();
        } catch (Exception e) {
            DarkPAS.log(Level.WARNING, String.format("Failed to write %s to disk", filepath));
            e.printStackTrace();
        }
    }
}
