package tech.spencercolton.tasp.backup;

import jdk.internal.util.xml.impl.Input;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tech.spencercolton.tasp.backup.Commands.BackupCmd;
import tech.spencercolton.tasp.backup.Enums.BackupDestinationType;
import tech.spencercolton.tasp.backup.Enums.BackupType;
import tech.spencercolton.tasp.backup.Exception.FTPConnectException;
import tech.spencercolton.tasp.backup.Scheduler.Job;
import tech.spencercolton.tasp.backup.Util.BackupDestination;
import tech.spencercolton.tasp.backup.Util.FTP;
import tech.spencercolton.tasp.backup.Util.Local;
import tech.spencercolton.tasp.backup.Web.Server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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

    private static Server s;

    @Getter
    private static String httpUsr, httpPass;

    @Override
    public void onEnable() {
        serverDir = new File(new File(".").getAbsolutePath());
        this.getCommand("backup").setExecutor(new BackupCmd());
        this.saveDefaultConfig();

        this.getConfig();

        backupDestinationType = BackupDestinationType.valueOf(this.getConfig().getString("backup-destination").toUpperCase());
        backupType = BackupType.valueOf(this.getConfig().getString("backup-type").toUpperCase());

        ftpHost = this.getConfig().getString("ftp-host");
        ftpUser = this.getConfig().getString("ftp-user");
        ftpPass = this.getConfig().getString("ftp-pass");

        httpUsr = this.getConfig().getString("http-user");
        httpPass = this.getConfig().getString("http-pass");

        if(backupDestinationType == BackupDestinationType.LOCAL) {
            if (this.getConfig().getString("backup-dir-type").equalsIgnoreCase("relative"))
                masterBackupDir = new File(masterBackupDir, this.getConfig().getString("backup-dir")).getAbsolutePath();
            else if (this.getConfig().getString("backup-dir-type").equalsIgnoreCase("absolute"))
                masterBackupDir = new File(this.getConfig().getString("backup-dir")).getAbsolutePath();
            new File(masterBackupDir).mkdirs();
        } else if (backupDestinationType == BackupDestinationType.FTP) {
            masterBackupDir = this.getConfig().getString("ftp-backup-dir");
        }

        try {
            s = new Server();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        s.getServer().stop(0);
    }

    public static void startBackup(BackupType bz) {
        Bukkit.getLogger().info("Starting backup for this server...");
        Bukkit.getLogger().info("Backup type is " + bz.toString());
        Bukkit.getLogger().info("Backup location is " + backupDestinationType.toString());
        if(backupDestinationType == BackupDestinationType.FTP) {
            Bukkit.getLogger().info("FTP Details: Server -  " + ftpHost + "; Username - " + ftpUser);
        }
        BackupDestination bd;
        switch(backupDestinationType) {
            case FTP: {
                try {
                    bd = new FTP(ftpHost, ftpUser, ftpPass);
                } catch (FTPConnectException e) {
                    // TODO Handle the exception better
                    e.printStackTrace();
                    return;
                }
                break;
            }
            case LOCAL: {
                try {
                    bd = new Local(new File(masterBackupDir));
                } catch (IOException e) {
                    // TODO Handle the exception better
                    e.printStackTrace();
                    return;
                }
                break;
            }
            default: {
                return;
            }
        }
        new Job(bz, bd);
    }

}
