package tech.spencercolton.tasp.backup;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import tech.spencercolton.tasp.backup.Commands.BackupCmd;
import tech.spencercolton.tasp.backup.Enums.BackupDestinationType;
import tech.spencercolton.tasp.backup.Enums.BackupType;
import tech.spencercolton.tasp.backup.Exception.FTPConnectException;
import tech.spencercolton.tasp.backup.Scheduler.Job;
import tech.spencercolton.tasp.backup.Util.BackupDestination;
import tech.spencercolton.tasp.backup.Util.FTP;
import tech.spencercolton.tasp.backup.Util.Local;

import java.io.File;
import java.io.IOException;

/**
 * @author Spencer Colton
 */
public class TASPBackup extends JavaPlugin {

    @Getter
    private static File serverDir;

    @Getter
    private static BackupType backupType;

    @Getter
    private static BackupDestinationType backupDestinationType;

    @Getter
    private static String masterBackupDir;

    private static String ftpHost, ftpUser, ftpPass;

    @Override
    public void onEnable() {
        serverDir = new File(new File(".").getAbsolutePath());
        this.getCommand("backup").setExecutor(new BackupCmd());
        this.saveDefaultConfig();

        this.getConfig();

        backupDestinationType = BackupDestinationType.valueOf(this.getConfig().getString("backup-destination").toUpperCase());
        backupType = BackupType.valueOf(this.getConfig().getString("backup-type").toUpperCase());

        if(backupDestinationType == BackupDestinationType.LOCAL) {
            if (this.getConfig().getString("backup-dir-type").equalsIgnoreCase("relative"))
                masterBackupDir = new File(masterBackupDir, this.getConfig().getString("backup-dir")).getAbsolutePath();
            else if (this.getConfig().getString("backup-dir-type").equalsIgnoreCase("absolute"))
                masterBackupDir = new File(this.getConfig().getString("backup-dir")).getAbsolutePath();
            new File(masterBackupDir).mkdirs();
        } else if (backupDestinationType == BackupDestinationType.FTP) {
            masterBackupDir = this.getConfig().getString("ftp-backup-dir");
        }

    }

    @Override
    public void onDisable() {

    }

    public static void startBackup() {
        BackupDestination bd;
        switch(backupDestinationType) {
            case FTP: {
                try {
                    bd = new FTP(ftpHost, ftpUser, ftpPass);
                } catch (FTPConnectException e) {
                    // TODO Handle the exception better
                    return;
                }
                break;
            }
            case LOCAL: {
                try {
                    bd = new Local(new File(masterBackupDir));
                } catch (IOException e) {
                    // TODO Handle the exception better
                    return;
                }
                break;
            }
            default: {
                return;
            }
        }
        new Job(backupType, bd);

    }

}
