package xyz.darke.darkpas.requesthandlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import xyz.darke.darkpas.DarkPAS;
import xyz.darke.darkpas.data.PlayerData;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class ConfigEndpointRequestHandler extends AbstractRequestHandler {

    public ConfigEndpointRequestHandler(DarkPAS main) {
        super(main);
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {

            String response = main.serverConfig.getConfigJson();

            Headers headers = exchange.getResponseHeaders();
            headers.add("content-type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            exchange.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
