package tech.spencercolton.tasp.backup.Scheduler;

import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;
import tech.spencercolton.tasp.backup.Enums.BackupType;

/**
 * @author Spencer Colton
 */
public class Job extends BukkitRunnable {

    @Getter
    private BackupType type;

    public Job(BackupType type) {
        this.type = type;
    }

    @Override
    public void run() {
        switch(this.type) {
            case ALL: {

            }
            case CONFIG: {

            }
            case WORLDS: {

            }
            case PLUGIN_DATA: {

            }
        }
    }

}
