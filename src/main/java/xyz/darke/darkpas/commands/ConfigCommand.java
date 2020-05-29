package xyz.darke.darkpas.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.darke.darkpas.data.PlayerData;
import xyz.darke.darkpas.data.ServerConfig;

import java.awt.*;
import java.util.UUID;

public class ConfigCommand {
    public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, ServerConfig config) {
        if (sender instanceof Player) {
            if (sender.isOp()) {
                execute(sender, args, config);
                return true;
            }
        } else {

            return true;
        }
        execute(sender, args, config);
        return false;
    }

    public static void execute(CommandSender sender, String[] args, ServerConfig config) {
        if (args.length == 0) {
            sender.sendMessage(config.getConfigJson());
            return;
        }

        if (args.length == 3) {
            try {
                config.setSafeZoneSize(Integer.parseInt(args[0]));
                config.setAttenuationCoefficient(Integer.parseInt(args[1]));
                config.setCutoffDistance(Integer.parseInt(args[2]));
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Failed to set values!");
                return;
            }
            sender.sendMessage(ChatColor.GREEN + "" +  ChatColor.ITALIC + "Config updated!");
            return;
        }

        if (args.length == 4) {
            try {
                config.setSafeZoneSize(Integer.parseInt(args[0]));
                config.setAttenuationCoefficient(Integer.parseInt(args[1]));
                config.setCutoffDistance(Integer.parseInt(args[2]));

                if (args[3].equalsIgnoreCase("true")) {
                    config.setUnregisteredCanBroadcast(true);
                } else if (args[3].equalsIgnoreCase("false")) {
                    config.setUnregisteredCanBroadcast(false);
                } else {
                    config.setUnregisteredCanBroadcast(true);
                    sender.sendMessage(ChatColor.YELLOW + "Unknown value for <unregisteredBroadcast>, defaulting true");
                }
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Failed to set values!");
                return;
            }
            sender.sendMessage(ChatColor.GREEN + "" +  ChatColor.ITALIC + "Config updated!");
            return;
        }

        sender.sendMessage("/dpas <SafeZoneSize> <AttenuationCoefficient> <CutoffDistance> <unregisteredBroadcast>");
    }

}
