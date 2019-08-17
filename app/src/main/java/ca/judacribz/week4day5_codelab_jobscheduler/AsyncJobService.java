package ca.judacribz.week4day5_codelab_jobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class AsyncJobService extends JobService implements WaitTask.OnTaskCompleteCallback {

    private static final String TAG = AsyncJobService.class.getSimpleName();

    private WaitTask waitTask;
    private JobParameters jobParameters;
    private boolean reschedule = false;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        this.jobParameters = jobParameters;

        waitTask = new WaitTask(this);
        waitTask.execute();

        Log.d(TAG, String.format(
                Locale.US,
                "onStartJob: %s",
                reschedule ? "RESTARTING TASK" : "STARTING TASK"
        ));

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (waitTask != null) {
            waitTask.cancel(true);
        }

        reschedule = true;
        Toast.makeText(getApplicationContext(), "Job Failed", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStopJob: TASK STOPPED");

        return true;
    }

    @Override
    public void onTaskCompleted(boolean success) {
        jobFinished(jobParameters, !success);
        reschedule = false;
        Log.d(TAG, "onStopJob: TASK COMPLETE");
    }
}
