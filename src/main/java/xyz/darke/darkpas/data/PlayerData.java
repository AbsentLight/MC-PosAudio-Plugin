package xyz.darke.darkpas.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.darke.darkpas.util.MathUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData {
    public final Map<UUID, String> data = new HashMap<>();
    public final Map<String, UUID> reverse = new HashMap<>();

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

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

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

            // Distance between player and rel-center on x-z
            double distance2D = MathUtil.distance2D(rPlayerLocation.getX(),
                    rPlayerLocation.getZ(),
                    playerLocation.getX(),
                    playerLocation.getZ());

            // Angle between player and rel-center on x-z
            double angle2D = MathUtil.angle2D(rPlayerLocation.getX(),
                    rPlayerLocation.getZ(),
                    playerLocation.getX(),
                    playerLocation.getZ(),
                    playerLocation.getYaw() * (Math.PI / 180));

            double[] location = new double[3];
            location[0] = distance2D * Math.sin(angle2D);
            location[1] = rPlayerLocation.getY() - playerLocation.getY();
            location[2] = distance2D * Math.cos(angle2D);

            tsIDPosLookup.put(rTsID, location);
        }

        if (tsIDPosLookup.size() > 0) {
            return objectMapper.writeValueAsString(tsIDPosLookup);
        } else {
            return "{}";
        }
    }


}
