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

    @Getter
    private StatusCode status;

    public JobStatus(int total) {
        this.currentFile = "No current file.";
        this.total = total;
        this.current = 0;
        this.status = StatusCode.WAITING;
    }

    public void update(int current, String s) {
        if(current == total)
            this.status = StatusCode.DONE;
        if(this.status == StatusCode.WAITING)
            this.status = StatusCode.RUNNING;
        this.current = current;
        this.currentFile = s;
    }

    public float getPercent() {
        return (float)current / (float)total;
    }

    public enum StatusCode {

        WAITING,
        RUNNING,
        DONE;

    }

}
