package tech.spencercolton.tasp.backup.Web;

import com.sun.net.httpserver.HttpServer;
import lombok.Getter;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author Spencer Colton
 */
public class Server {

    @Getter
    private final HttpServer server;

    public Server() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(8080), 0);

    }

    private void createContexts(HttpServer s) {
        s.cre
    }

}
