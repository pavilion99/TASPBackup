package tech.spencercolton.tasp.backup.Web;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpExchange;
import tech.spencercolton.tasp.backup.TASPBackup;

/**
 * @author Spencer Colton
 */
public class Auth extends BasicAuthenticator {

    public Auth(String s) {
        super(s);
    }

    @Override
    public boolean checkCredentials(String usr, String pwd) {
        return usr.equals(TASPBackup.getHttpUsr()) && pwd.equals(TASPBackup.getHttpPass());
    }

}
