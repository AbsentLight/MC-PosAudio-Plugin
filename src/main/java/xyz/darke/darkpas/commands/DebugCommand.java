package xyz.darke.darkpas.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.darke.darkpas.data.PlayerData;

import java.util.UUID;

public class DebugCommand {
    public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, PlayerData playerData) {
        if (sender instanceof Player) {
            if (sender.isOp()) {
                execute(playerData);
                return true;
            }
        } else {
            execute(playerData);
            return true;
        }

        return false;
    }

    public static void execute(PlayerData playerData) {
        playerData.addUserInfo(UUID.fromString("43ea2147-20a5-481d-b915-c11e50eedbe9"), "XrwhZkGd1RFrnU78oyFDeR855bo=");
        playerData.addUserInfo(UUID.fromString("d23c424a-0949-44ab-ade9-22dcb6ad305a"), "PBsFn2py2qykScnQeRVXukVmiPI=");
    }

}
