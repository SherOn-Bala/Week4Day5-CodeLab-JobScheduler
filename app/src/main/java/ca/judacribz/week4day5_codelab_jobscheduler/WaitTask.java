package ca.judacribz.week4day5_codelab_jobscheduler;

import android.os.AsyncTask;

class WaitTask extends AsyncTask<Void, Void, Boolean> {

    OnTaskCompleteCallback onTaskCompleteCallback;

    interface OnTaskCompleteCallback {
        void onTaskCompleted(boolean success);
    }

    WaitTask(OnTaskCompleteCallback context) {
        onTaskCompleteCallback = context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        onTaskCompleteCallback.onTaskCompleted(success);
    }
}
