package tech.spencercolton.tasp.backup.Util;

import lombok.Getter;

/**
 * @author Spencer Colton
 */
public class JobStatus {

    @Getter
    private int total, current;

    @Getter
    private String currentFile;

    public JobStatus(int total) {
        this.currentFile = "No current file.";
        this.total = total;
        this.current = 0;
    }

    public void update(int current, String s) {
        this.current = current;
        this.currentFile = s;
    }

    public float getPercent() {
        return (float)current / (float)total;
    }

}
