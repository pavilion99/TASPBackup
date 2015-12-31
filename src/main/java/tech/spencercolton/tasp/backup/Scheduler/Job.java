package tech.spencercolton.tasp.backup.Scheduler;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import tech.spencercolton.tasp.backup.Enums.BackupDestinationType;
import tech.spencercolton.tasp.backup.Enums.BackupType;
import tech.spencercolton.tasp.backup.TASPBackup;
import tech.spencercolton.tasp.backup.Util.BackupDestination;
import tech.spencercolton.tasp.backup.Util.FTP;
import tech.spencercolton.tasp.backup.Util.JobStatus;
import tech.spencercolton.tasp.backup.Util.Local;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;

/**
 * @author Spencer Colton
 */
public class Job extends BukkitRunnable {

    @Getter
    private BackupType type;

    @Getter
    private BackupDestinationType dest;

    private BackupDestination d;

    private List<File> fileList;

    @Getter
    private UUID projUUID;

    @Getter
    private JobStatus status;

    @Getter
    private static List<Job> jobs = new ArrayList<>();

    public Job(BackupType type, @NotNull BackupDestination b) {
        this.type = type;
        if(b instanceof FTP) {
            this.dest = BackupDestinationType.FTP;
        } else if(b instanceof Local) {
            this.dest = BackupDestinationType.LOCAL;
        } else {
            this.dest = null;
        }
        this.d = b;

        switch(this.type) {
            case ALL: {
                fileList = getAllFilesList();
                break;
            }
            case PLUGIN_DATA: {
                fileList = getPluginDataFilesList();
                break;
            }
        }

        this.projUUID = UUID.randomUUID();

        this.runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("TASPBackup"));
        status = new JobStatus(fileList.size());
        jobs.add(this);
    }

    @Override
    public void run() {
        for(int i = 0; i < fileList.size(); i++) {
            Path p = TASPBackup.getServerDir().toPath().relativize(fileList.get(i).toPath());
            String dir = "";
            this.status.update(i + 1, p.toString());
            if(p.getParent() != null)
                dir = p.getParent().toString();
            else
                dir = "";

            if(!this.d.uploadFile(fileList.get(i), dir))
                System.out.println("Warning: couldn't backup file " + fileList.get(i).toString());
        }
        System.out.println("Backup completed! Backed up " + this.status.getTotal() + " files.");
    }

    private List<File> getAllFilesList() {
        File f = TASPBackup.getServerDir();
        final List<File> files = new ArrayList<>();
        try {
            Predicate<Path> p;
            if(this.dest == BackupDestinationType.LOCAL) {
                p = pg -> !Local.isInSubDirectory(new File(TASPBackup.getMasterBackupDir()), pg.toFile());
            } else {
                p = pg -> true;
            }
            Files.walk(Paths.get(f.getAbsolutePath())).filter(Files::isRegularFile).filter(p).forEach(h -> files.add(h.toFile()));
            return files;
        } catch(IOException e) {
            return null;
        }
    }

    private List<File> getPluginDataFilesList() {
        File f = TASPBackup.getServerDir();
        final List<File> files = new ArrayList<>();
        try {
            Predicate<Path> p;
            if(this.dest == BackupDestinationType.LOCAL) {
                p = pg -> !Local.isInSubDirectory(new File(TASPBackup.getMasterBackupDir()), pg.toFile());
            } else {
                p = pg -> true;
            }
            Files.walk(Paths.get(f.getAbsolutePath())).filter(Files::isRegularFile).filter(p).filter(fz -> Local.isInSubDirectory(new File(TASPBackup.getServerDir(), "plugins"), fz.toFile())).filter(pz -> !pz.toString().toLowerCase().endsWith(".jar")).forEach(h -> files.add(h.toFile()));
            return files;
        } catch(IOException e) {
            return null;
        }

    }

    public static int getActiveJobs() {
        int i = 0;
        for(Job j : jobs) {
            if(j.getStatus().getStatus() != JobStatus.StatusCode.DONE)
                i++;
        }
        return i;
    }


}
