package xyz.darke.darkpas;

import com.sun.net.httpserver.HttpServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.darke.darkpas.commands.ConfigCommand;
import xyz.darke.darkpas.commands.DebugCommand;
import xyz.darke.darkpas.commands.SetTSID;
import xyz.darke.darkpas.data.PlayerData;
import xyz.darke.darkpas.data.ServerConfig;
import xyz.darke.darkpas.requesthandlers.ConfigEndpointRequestHandler;
import xyz.darke.darkpas.requesthandlers.DebugRequestHandler;
import xyz.darke.darkpas.requesthandlers.RequestEndpointRequestHandler;
import xyz.darke.darkpas.requesthandlers.RootRequestHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DarkPAS extends JavaPlugin {

    public final PlayerData playerData = new PlayerData();
    public static ServerConfig serverConfig;

    protected HttpServer webServer;
    private static final Logger logger = Bukkit.getLogger();
    private int portNumber = 9000;
    private boolean hasPort = false;
    private final RootRequestHandler rootHandler = new RootRequestHandler(this);
    private final RequestEndpointRequestHandler endpointHandler = new RequestEndpointRequestHandler(this);
    private final DebugRequestHandler debugHandler = new DebugRequestHandler(this);
    private final ConfigEndpointRequestHandler configHandler = new ConfigEndpointRequestHandler(this);

    @Override
    public void onEnable() {
        // Plugin startup logic

        serverConfig = new ServerConfig();

        // Read configuration from file
        setupPluginFolder();
        playerData.loadPlayerIDMapCache();

        while (!hasPort && portNumber < 9010) {
            try {
                webServer = HttpServer.create(new InetSocketAddress(portNumber), 0);
                hasPort = true;
            } catch (IOException err) {
                this.logger.log(Level.WARNING, err.toString());
                portNumber++;

            }
        }
        if (!hasPort) {
            throw new RuntimeException(String.format("Failed to bind to port %s for communications!", portNumber - 1));
        }

        webServer.createContext("/", rootHandler);
        webServer.createContext("/request", endpointHandler);
        webServer.createContext("/debug", debugHandler);
        webServer.createContext("/config", configHandler);
        webServer.setExecutor(null);
        webServer.start();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setTSID")) {
            return SetTSID.onCommand(sender, cmd, label, args, playerData);
        }

        if (cmd.getName().equalsIgnoreCase("debug")) {
            return DebugCommand.onCommand(sender, cmd, label, args, playerData);
        }

        if (cmd.getName().equalsIgnoreCase("dpas")) {
            return ConfigCommand.onCommand(sender, cmd, label, args, serverConfig);
        }
        return false;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        playerData.writePlayerIDMapCache();
        serverConfig.writeServerConfigToDisk();
        webServer.stop(0);
    }

    public void setupPluginFolder() {
        File file = new File("plugins/DarkPAS");
        boolean folderWasCreated = file.mkdir();
        if (folderWasCreated) {
            this.log(Level.INFO, "Plugin folder was created");
        } else {
            this.log(Level.INFO, "Existing plugin folder was found");
        }
    }

    public static void log(Level level, String msg) {
        String prefix = "[DarkPAS] ";
        logger.log(level, prefix + msg);
    }
}
