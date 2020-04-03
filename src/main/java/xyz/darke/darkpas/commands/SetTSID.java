package xyz.darke.darkpas.commands;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.darke.darkpas.data.PlayerData;

public class SetTSID {

    public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, PlayerData playerData) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        if (args.length == 0) {
            return false;
        }

        if (args.length == 1) {
            // Set ID for Self
            if (player != null) {
                playerData.addUserInfo(player, args[0]);
                Bukkit.broadcastMessage(String.format("%s changed their Teamspeak ID!", player.getDisplayName()));
                return true;
            } else {
                return false;
            }

        } else if (args.length == 2) {
            // Set ID for other
            if (player == null) {
                // Console send command
                Player playerToSet = Bukkit.getPlayer(args[1]);
                if (playerToSet == null) {
                    return false;
                }
                playerData.addUserInfo(playerToSet, args[0]);
                Bukkit.broadcastMessage(String.format("%s's Teamspeak ID was changed!", playerToSet.getDisplayName()));
                return true;
            } else {
                // Player sent command
                if (player.isOp()) {
                    Player playerToSet = Bukkit.getPlayer(args[1]);
                    if (playerToSet == null) {
                        return false;
                    }
                    playerData.addUserInfo(playerToSet, args[0]);
                    Bukkit.broadcastMessage(String.format("%s changed %s's Teamspeak ID!", player.getDisplayName(), playerToSet.getDisplayName()));
                    return true;
                } else {
                    player.sendMessage(Color.RED + "You must be op'ed to set someone else's TSID");
                }
            }
        }
        return false;
    }

}
