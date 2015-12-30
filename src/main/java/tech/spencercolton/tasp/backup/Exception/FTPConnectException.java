package tech.spencercolton.tasp.backup.Exception;

import lombok.Getter;
import tech.spencercolton.tasp.backup.Enums.ConnectFailureReason;

/**
 * @author Spencer Colton
 */
public class FTPConnectException extends Exception {

    @Getter
    private final ConnectFailureReason c;

    public FTPConnectException(ConnectFailureReason c) {
        super();
        this.c = c;
    }

    public String getMessage() {
        return c.getMessage();
    }

}
