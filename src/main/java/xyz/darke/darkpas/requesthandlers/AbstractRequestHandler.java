package xyz.darke.darkpas.requesthandlers;

import com.sun.net.httpserver.HttpHandler;
import xyz.darke.darkpas.DarkPAS;
import xyz.darke.darkpas.data.PlayerData;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRequestHandler implements HttpHandler {

    protected final DarkPAS main;

    public AbstractRequestHandler(DarkPAS main) {
        this.main = main;
    }

    public static Map<String, String> parseQuery(String queryString) {

        Map<String, String> queryItems = new HashMap<>();

        for (String q : queryString.split("&")) {
            String[] i = q.split("=", 2);
            if (i.length == 2) {
                queryItems.put(i[0], i[1]);
            }
        }

        return queryItems;
    }

}
