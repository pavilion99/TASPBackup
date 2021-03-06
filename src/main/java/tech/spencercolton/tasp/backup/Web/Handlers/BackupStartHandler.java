package tech.spencercolton.tasp.backup.Web.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import tech.spencercolton.tasp.backup.TASPBackup;

/**
 * @author Spencer Colton
 */
public class BackupStartHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange e) {
        TASPBackup.startBackup(TASPBackup.getBackupType());
        try {
            e.sendResponseHeaders(205, 0);
            e.close();
        } catch(Exception ignored) {

        }
    }

}
