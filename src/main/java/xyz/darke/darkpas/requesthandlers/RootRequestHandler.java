package xyz.darke.darkpas.requesthandlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import xyz.darke.darkpas.DarkPAS;
import xyz.darke.darkpas.data.PlayerData;

import java.io.IOException;
import java.io.OutputStream;

public class RootRequestHandler extends AbstractRequestHandler {

    public RootRequestHandler(DarkPAS main) {
        super(main);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = main.playerData.getPlayerPositions();
        Headers headers = exchange.getResponseHeaders();
        headers.add("content-type", "application/json");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
        exchange.close();
    }
}
