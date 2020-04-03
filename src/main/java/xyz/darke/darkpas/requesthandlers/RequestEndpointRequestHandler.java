package xyz.darke.darkpas.requesthandlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import xyz.darke.darkpas.data.PlayerData;

import java.io.OutputStream;
import java.util.Map;

public class RequestEndpointRequestHandler extends AbstractRequestHandler {

    public RequestEndpointRequestHandler(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(HttpExchange exchange) {

        try {
            Map<String, String> queryItems = parseQuery(exchange.getRequestURI().getQuery());

            String tsid = queryItems.get("id");
            String response = null;
            if (tsid != null) {
                response = playerData.getPlayerRelativePositions(tsid);
            } else {
                System.out.println("tsid null");
            }
            if (response == null) {
                System.out.println("Response null");
                response = "{}";
            }

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
