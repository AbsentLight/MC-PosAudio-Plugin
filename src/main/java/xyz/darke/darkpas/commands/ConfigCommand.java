package xyz.darke.darkpas.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import xyz.darke.darkpas.DarkPAS;
import xyz.darke.darkpas.data.PlayerData;
import xyz.darke.darkpas.data.ServerConfig;

import java.util.*;
import java.util.logging.Level;

public class ConfigCommand implements TabExecutor {

    private static final String[] configurableParams = new String[] {
            "cutoffDistance",
            "attenuationCoefficient",
            "safeZoneSize",
            "unregisteredCanBroadcast",
            "refreshRate"
    };


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (!sender.isOp()) {
                return false;
            }
        }

        if (args.length == 0) {
            sender.sendMessage(DarkPAS.serverConfig.getConfigJson());
            return true;
        }

        if (args.length == 1) {
            switch (args[0]) {
                case "cutoffDistance":
                    sender.sendMessage(Integer.toString(DarkPAS.serverConfig.getCutoffDistance()));
                    break;
                case "attenuationCoefficient":
                    sender.sendMessage(Integer.toString(DarkPAS.serverConfig.getAttenuationCoefficient()));
                    break;
                case "safeZoneSize":
                    sender.sendMessage(Integer.toString(DarkPAS.serverConfig.getSafeZoneSize()));
                    break;
                case "unregisteredCanBroadcast":
                    sender.sendMessage(DarkPAS.serverConfig.isUnregisteredCanBroadcast() ? "true" : "false");
                    break;
                case "refreshRate":
                    sender.sendMessage(Integer.toString(DarkPAS.serverConfig.getRefreshRate()));
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + String.format("Unknown argument %s", args[0]));
                    return false;
            }
            return true;
        }

        if (args.length == 2) {
            boolean success = false;
            switch (args[0]) {
                case "cutoffDistance":
                    if (DarkPAS.serverConfig.setCutoffDistance(Integer.parseInt(args[1]))) {
                        success = true;
                    }
                    break;
                case "attenuationCoefficient":
                    if (DarkPAS.serverConfig.setAttenuationCoefficient(Integer.parseInt(args[1]))) {
                        success = true;
                    }
                    break;
                case "safeZoneSize":
                    if (DarkPAS.serverConfig.setSafeZoneSize(Integer.parseInt(args[1]))) {
                        success = true;
                    }
                    break;
                case "unregisteredCanBroadcast":
                    if (args[1].equalsIgnoreCase("true")) {
                        DarkPAS.serverConfig.setUnregisteredCanBroadcast(true);
                        success = true;
                    } else if (args[1].equalsIgnoreCase("false")) {
                        DarkPAS.serverConfig.setUnregisteredCanBroadcast(false);
                        success = true;
                    }
                    break;
                case "refreshRate":
                    if (DarkPAS.serverConfig.setRefreshRate(Integer.parseInt(args[1]))) {
                        success = true;
                    }
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + String.format("Unknown argument %s", args[0]));
                    return false;
            }

            if (success) {
                sender.sendMessage(ChatColor.GREEN + "" +  ChatColor.ITALIC + "Config updated!");
                DarkPAS.playerData.invalidateConfig();
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "" +  ChatColor.ITALIC + "Failed to update config!");
                return false;
            }
        }

        sender.sendMessage("/dpas <SafeZoneSize|AttenuationCoefficient|CutoffDistance|unregisteredBroadcast> <...>");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            if (!args[0].equals("")) {
                for (String param : configurableParams) {
                    if (param.startsWith(args[0])) {
                        suggestions.add(param);
                    }
                }
            } else {
                suggestions = Arrays.asList(configurableParams);
            }
            Collections.sort(suggestions);
            return suggestions;
        }

        if (args.length == 2) {
            if (args[0].equals("unregisteredCanBroadcast")) {
                suggestions = Arrays.asList(new String[] {"true", "false"});
            }
        }

        return suggestions;
    }
}
