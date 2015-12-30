package tech.spencercolton.tasp.backup.Web.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import tech.spencercolton.tasp.backup.TASPBackup;

import java.io.OutputStream;
import java.util.Scanner;

/**
 * @author Spencer Colton
 */
public class DashboardHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange c) {
        try {
            String response = new Scanner(TASPBackup.class.getResourceAsStream("/web/index.html"), "UTF-8").useDelimiter("\\A").next();

            Document d = Jsoup.parse(response);
            d.getElementById("backup-status").html("All Is Normal");

            response = d.toString();

            c.sendResponseHeaders(200, response.length());
            OutputStream os = c.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
