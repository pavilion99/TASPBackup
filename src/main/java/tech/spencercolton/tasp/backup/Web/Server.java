package tech.spencercolton.tasp.backup.Web;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import lombok.Getter;
import tech.spencercolton.tasp.backup.Web.Handlers.BackupStartHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class Server {

    @Getter
    private final HttpServer server;

    @Getter
    private final List<HttpContext> contexts = new ArrayList<>();

    public Server() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(8080), 0);

    }

    private void createContexts(HttpServer s) {
        HttpContext cxt = s.createContext("/startbackup", new BackupStartHandler());
        cxt.setAuthenticator(new Auth());

        contexts.add(cxt);
    }

}
