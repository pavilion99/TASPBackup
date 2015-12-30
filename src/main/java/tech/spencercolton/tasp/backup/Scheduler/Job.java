package tech.spencercolton.tasp.backup.Scheduler;

import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;
import tech.spencercolton.tasp.backup.Enums.BackupType;
import tech.spencercolton.tasp.backup.TASPBackup;
import tech.spencercolton.tasp.backup.Util.BackupDestination;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class Job extends BukkitRunnable {

    @Getter
    private BackupType type;


    public Job(BackupType type, BackupDestination b) {
        this.type = type;
    }

    @Override
    public void run() {
        switch(this.type) {
            case ALL: {
                List<File> fs = getAllFilesList();

                break;
            }
            case CONFIG: {

            }
            case WORLDS: {

            }
            case PLUGIN_DATA: {

            }
        }
    }

    private List<File> getAllFilesList() {
        File f = TASPBackup.getServerDir();
        final List<File> files = new ArrayList<>();
        try {
            Files.walk(Paths.get(f.getAbsolutePath())).filter(Files::isRegularFile).forEach(p -> files.add(p.toFile()));
            return files;
        } catch(IOException e) {
            return null;
        }
    }

}
