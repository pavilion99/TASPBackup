package tech.spencercolton.tasp.backup.Web.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import tech.spencercolton.tasp.backup.Scheduler.Job;
import tech.spencercolton.tasp.backup.TASPBackup;
import tech.spencercolton.tasp.backup.Util.JobStatus;

import java.io.OutputStream;
import java.util.Scanner;

/**
 * @author Spencer Colton
 */
public class DashboardHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange c) {
        try {
            String response = new Scanner(TASPBackup.class.getResourceAsStream("/web/index.html"), "UTF-8").useDelimiter("\\A").next();

            Document d = Jsoup.parse(response);

            int i = Job.getActiveJobs();
            if(i == 0)
                d.getElementById("job-status").html("<tr><th colspan=\"5\" style=\"text-align:center;font-weight:bold;\">No Running Jobs</th></tr>");
            else
                d.getElementById("job-status").html("<tr><th colspan=\"5\" style=\"text-align:center;font-weight:bold;\">All Jobs Status</th></tr>"
                + "<tr><th>Dest</th><th>Type</th><th>Status</th><th>Progress</th><th>State</th></tr>");

            String jobs = "";

            for(Job j : Job.getJobs()) {
                if(j.getStatus().getStatus() == JobStatus.StatusCode.DONE)
                    continue;
                JobStatus s = j.getStatus();
                int cur = s.getCurrent();
                int max = s.getTotal();
                String type = j.getType().toString();
                String dest = j.getDest().toString();
                jobs += "<tr><td>" + dest + "</td><td>" + type + "</td><td>" + cur + " of " + max + "</td><td><progress value=\"" + cur + "\" max=\"" + max + "\"></progress></td><td>" + s.getStatus().toString() + "</td></tr>";
            }

            d.getElementById("running-jobs").html(jobs);
            String g = d.toString();

            c.sendResponseHeaders(200, g.length());
            OutputStream os = c.getResponseBody();
            os.write(g.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
