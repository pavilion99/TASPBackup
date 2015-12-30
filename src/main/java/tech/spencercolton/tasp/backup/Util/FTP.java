package tech.spencercolton.tasp.backup.Util;

import lombok.Getter;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import tech.spencercolton.tasp.backup.Enums.ConnectFailureReason;
import tech.spencercolton.tasp.backup.Exception.FTPConnectException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;

/**
 * @author Spencer Colton
 */
public class FTP implements BackupDestination {

    private FTPClient ftp;

    public FTP(String host, String username, String password) throws FTPConnectException {
        this.ftp = new FTPClient();
        try {
            ftp.connect(InetAddress.getByName(host));

            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                throw new FTPConnectException(ConnectFailureReason.NO_SERVER);
            }
            if(ftp.login(username, password)) {
                ftp.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
                ftp.enterLocalPassiveMode();
            } else {
                throw new FTPConnectException(ConnectFailureReason.LOGIN_REJECTED);
            }
        } catch(IOException e) {
            throw new FTPConnectException(ConnectFailureReason.UNKNOWN_HOST);
        }
    }

    @Override
    public boolean uploadFile(File f, String remoteDir) {
        try (InputStream i = new FileInputStream(f)) {
            return this.ftp.storeFile(remoteDir + File.separator + f.getName(), i);
        } catch (Exception e) {
            return false;
        }
    }

}
