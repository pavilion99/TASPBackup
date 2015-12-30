package tech.spencercolton.tasp.backup.Web;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpExchange;

/**
 * @author Spencer Colton
 */
public class Auth extends Authenticator {

    @Override
    public Authenticator.Result authenticate(HttpExchange exch) {
        return new Authenticator.Failure(504);
    }

}
