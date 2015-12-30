package tech.spencercolton.tasp.backup.Enums;

import lombok.Getter;

/**
 * @author Spencer Colton
 */
public enum ConnectFailureReason {

    LOGIN_REJECTED("Username and password were rejected by the FTP server."),
    UNKNOWN_HOST("The specified host could not be found."),
    NO_SERVER("The specified host was found, but no FTP server was found to be running on it.  Try checking open ports, firewall settings, etc.");

    @Getter
    private String message;

    ConnectFailureReason(String message) {
        this.message = message;
    }

}
