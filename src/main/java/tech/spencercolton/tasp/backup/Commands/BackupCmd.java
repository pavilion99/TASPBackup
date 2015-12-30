package tech.spencercolton.tasp.backup.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import lombok.Getter;
import tech.spencercolton.tasp.backup.Enums.BackupType;
import tech.spencercolton.tasp.backup.Scheduler.Job;
import tech.spencercolton.tasp.backup.TASPBackup;
import tech.spencercolton.tasp.backup.Util.Local;

import java.io.File;
import java.io.IOException;

/**
 * @author Spencer Colton
 */
public class BackupCmd implements CommandExecutor {

    @Getter
    private final String syntax = "/backup";

    @Getter
    public static final String name = "backup";

    @Getter
    private final String consoleSyntax = syntax;

    @Getter
    private final String permission = "tasp.backup.start";

    @Override
    public boolean onCommand(CommandSender sender, Command c, String label, String... args) {
        TASPBackup.startBackup();
        return true;
    }

}
