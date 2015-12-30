package tech.spencercolton.tasp.backup.Util;

import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

/**
 * @author Spencer Colton
 */
public class Local implements BackupDestination {

    private File thisBackupDir;

    public Local(File backupDir) throws IOException {
        if(backupDir.isDirectory()) {
            Calendar c = Calendar.getInstance();
            this.thisBackupDir = new File(backupDir, "backup_" + c.get(Calendar.YEAR) + "_" + c.get(Calendar.MONTH) + "_" + c.get(Calendar.DAY_OF_MONTH) + "_" + c.get(Calendar.HOUR_OF_DAY) + "_" + c.get(Calendar.MINUTE) + "_" + c.get(Calendar.SECOND));
            this.thisBackupDir.mkdirs();
        } else {
            throw new IOException("The backup location specified is not a directory.");
        }
    }

    @Override
    public boolean uploadFile(File f, String remoteDir) {
        try (InputStream i = new FileInputStream(f)){
            Path p = Paths.get(thisBackupDir.getPath() + File.separator + remoteDir);
            p.toFile().mkdirs();

            Files.copy(i, Paths.get(thisBackupDir.getPath() + File.separator + remoteDir + File.separator + f.getName()));
            i.close();
            return true;
        } catch(IOException e) {
            return false;
        }
    }

    public static boolean isInSubDirectory(File dir, File file) {
        if (file == null)
            return false;

        if (file.equals(dir))
            return true;

        return isInSubDirectory(dir, file.getParentFile());
    }

}
