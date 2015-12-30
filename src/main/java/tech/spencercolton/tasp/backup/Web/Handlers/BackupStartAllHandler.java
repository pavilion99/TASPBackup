package tech.spencercolton.tasp.backup.Web.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import tech.spencercolton.tasp.backup.Enums.BackupType;
import tech.spencercolton.tasp.backup.TASPBackup;

/**
 * @author Spencer Colton
 */
public class BackupStartAllHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange e) {
        TASPBackup.startBackup(BackupType.ALL);
        try {
            e.sendResponseHeaders(205, 0);
            e.close();
        } catch(Exception ignored) {

        }
    }

}
