package tech.spencercolton.tasp.backup;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * @author Spencer Colton
 */
public class TASPBackup extends JavaPlugin {

    @Getter
    private static File serverDir;

    @Override
    public void onEnable() {
        serverDir = new File(new File(".").getAbsolutePath());
    }

    @Override
    public void onDisable() {

    }

}
