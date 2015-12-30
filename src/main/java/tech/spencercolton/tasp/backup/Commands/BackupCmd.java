package tech.spencercolton.tasp.backup.Commands;

import org.bukkit.command.CommandSender;
import lombok.Getter;
import tech.spencercolton.tasp.Commands.TASPCommand;

/**
 * @author Spencer Colton
 */
public class BackupCmd extends TASPCommand {

    @Getter
    private final String syntax = "/backup";

    @Getter
    public static final String name = "backup";

    @Getter
    private final String consoleSyntax = syntax;

    @Getter
    private final String permission = "tasp.backup.start";

    @Override
    public void execute(CommandSender sender, String[] args) {

    }

}
