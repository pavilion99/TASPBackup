package tech.spencercolton.tasp.backup.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Spencer Colton
 */
public class Local implements BackupDestination {

    private File backupDir;

    public Local(File backupDir) throws IOException {
        if(backupDir.isDirectory())
            this.backupDir = backupDir;
        else
            throw new IOException("The backup location specified is not a directory.");
    }

    @Override
    public boolean uploadFile(File f, String remoteDir) {
        try (InputStream i = new FileInputStream(f)){
            Files.copy(i, Paths.get(backupDir.getPath() + File.separator + remoteDir));
            return true;
        } catch(IOException e) {
            return false;
        }
    }

}
