package tech.spencercolton.tasp.backup.Web.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import tech.spencercolton.tasp.backup.TASPBackup;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * @author Spencer Colton
 */
public class DashboardHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange c) {
        try {
            InputStream s = TASPBackup.class.getResourceAsStream("/web/index.html");
            String response = new Scanner(s, "UTF-8").useDelimiter("\\A").next();
            s.close();
            c.sendResponseHeaders(200, response.length());
            OutputStream os = c.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
