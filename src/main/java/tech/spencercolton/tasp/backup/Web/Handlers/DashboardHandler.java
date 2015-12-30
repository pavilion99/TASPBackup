package tech.spencercolton.tasp.backup.Web.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import tech.spencercolton.tasp.backup.TASPBackup;

import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Spencer Colton
 */
public class DashboardHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange c) {
        try {
            URL s = TASPBackup.class.getResource("web/index.html");
            Path p = Paths.get(s.toURI());
            String response = new String(Files.readAllBytes(p), "UTF8");
            c.sendResponseHeaders(200, response.length());
            OutputStream os = c.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
