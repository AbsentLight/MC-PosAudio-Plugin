package xyz.darke.darkpas.requesthandlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import xyz.darke.darkpas.DarkPAS;
import xyz.darke.darkpas.data.PlayerData;

import java.io.OutputStream;
import java.util.Map;
import java.util.logging.Level;

public class RequestEndpointRequestHandler extends AbstractRequestHandler {

    @Override
    public void handle(HttpExchange exchange) {

        try {
            Map<String, String> queryItems = parseQuery(exchange.getRequestURI().getQuery());

            String tsid = queryItems.get("id");
            if (tsid == null) {
                DarkPAS.log(Level.FINE, "RequestEndpointRequestHandler: TSID couldn't be extracted from request");
            }
            String response = DarkPAS.playerData.getPlayerRelativePositions(tsid);

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
