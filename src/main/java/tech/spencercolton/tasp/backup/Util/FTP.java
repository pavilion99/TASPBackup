package tech.spencercolton.tasp.backup.Util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import tech.spencercolton.tasp.backup.Enums.ConnectFailureReason;
import tech.spencercolton.tasp.backup.Exception.FTPConnectException;
import tech.spencercolton.tasp.backup.TASPBackup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Calendar;

/**
 * @author Spencer Colton
 */
public class FTP implements BackupDestination {

    private FTPClient ftp;
    private String backupDir;

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

                Calendar c = Calendar.getInstance();
                String year = Integer.toString(c.get(Calendar.YEAR));
                String month = Integer.toString(c.get(Calendar.MONTH) + 1);
                if(Integer.parseInt(month) < 10)
                    month = "0" + month;
                String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
                if(Integer.parseInt(day) < 10)
                    day = "0" + day;
                String hour = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
                if(Integer.parseInt(hour) < 10)
                    hour = "0" + hour;
                String minute = Integer.toString(c.get(Calendar.MINUTE));
                if(Integer.parseInt(minute) < 10)
                    minute = "0" + minute;
                String seconds = Integer.toString(c.get(Calendar.SECOND));
                if(Integer.parseInt(seconds) < 10)
                    seconds = "0" + seconds;
                this.backupDir = "backup_" + year + month + day + hour + minute + seconds;
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
            if (!this.ftp.changeWorkingDirectory(File.separator + TASPBackup.getMasterBackupDir() + File.separator + this.backupDir + File.separator + remoteDir)) {
                this.ftp.makeDirectory(File.separator + TASPBackup.getMasterBackupDir() + File.separator + this.backupDir + File.separator + remoteDir);
                this.ftp.changeWorkingDirectory(File.separator + TASPBackup.getMasterBackupDir() + File.separator + this.backupDir + File.separator + remoteDir);
            }
            return this.ftp.storeFile(f.getName(), i);
        } catch (Exception e) {
            return false;
        }
    }

}
