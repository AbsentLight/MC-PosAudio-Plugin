package xyz.darke.darkpas;

import com.sun.net.httpserver.HttpServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.darke.darkpas.commands.DebugCommand;
import xyz.darke.darkpas.commands.SetTSID;
import xyz.darke.darkpas.data.PlayerData;
import xyz.darke.darkpas.requesthandlers.DebugRequestHandler;
import xyz.darke.darkpas.requesthandlers.RequestEndpointRequestHandler;
import xyz.darke.darkpas.requesthandlers.RootRequestHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DarkPAS extends JavaPlugin {

    protected final PlayerData playerData = new PlayerData();

    protected HttpServer webServer;
    public final Logger logger = Logger.getLogger("Minecraft");
    private int portNumber = 9000;
    private boolean hasPort = false;
    private final RootRequestHandler rootHandler = new RootRequestHandler(playerData);
    private final RequestEndpointRequestHandler endpointHandler = new RequestEndpointRequestHandler(playerData);
    private final DebugRequestHandler debugHandler = new DebugRequestHandler(playerData);

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Read configuration from file

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
        return false;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        webServer.stop(0);
    }
}
