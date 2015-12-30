package tech.spencercolton.tasp.backup.Util;

import java.io.File;

/**
 * @author Spencer Colton
 */
public interface BackupDestination {

    boolean uploadFile(File f, String remoteDir);

}
