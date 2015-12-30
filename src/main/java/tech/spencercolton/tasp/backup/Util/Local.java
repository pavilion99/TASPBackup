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
            String year = Integer.toString(c.get(Calendar.YEAR));
            String month = Integer.toString(c.get(Calendar.MONTH) + 1);
            if(Integer.parseInt(month) < 10)
                month = "0" + month;
            String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
            if(Integer.parseInt(day) < 10)
                day = "0" + day;
            String hour = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
            if(Integer.parseInt(hour) < 10)
                hour = "0" + hour;
            String minute = Integer.toString(c.get(Calendar.MINUTE));
            if(Integer.parseInt(minute) < 10)
                minute = "0" + minute;
            String seconds = Integer.toString(c.get(Calendar.SECOND));
            if(Integer.parseInt(seconds) < 10)
                seconds = "0" + seconds;
            this.thisBackupDir = new File(backupDir, "backup_" + year + month + day + hour + minute + seconds);
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
