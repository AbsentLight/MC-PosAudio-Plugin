package xyz.darke.darkpas.requesthandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.net.httpserver.HttpExchange;
import xyz.darke.darkpas.DarkPAS;
import xyz.darke.darkpas.data.PlayerData;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DebugRequestHandler extends AbstractRequestHandler {

    @Override
    public void handle(HttpExchange exchange) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            List<String> responseData = new ArrayList<>();

            responseData.add(objectMapper.writeValueAsString(DarkPAS.playerData.data));
            responseData.add(objectMapper.writeValueAsString(DarkPAS.playerData.reverse));

            String response = String.join(" <br> ", responseData);

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
