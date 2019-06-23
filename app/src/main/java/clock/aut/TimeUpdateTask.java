package clock.aut;

import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.instacart.library.truetime.TrueTime;

import java.io.IOException;

public class TimeUpdateTask extends AsyncTask<Void, Void, Boolean> {

    private ClockActivity clockActivity;

   /* public TimeUpdateTask(ClockActivity clockActivity) {
        this.clockActivity = clockActivity;
    }*/

    private boolean isInitialized;

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        /*if(aBoolean){
            return;
        }*/

        isInitialized = aBoolean;
        if (clockActivity != null) {
            clockActivity.showTimeUpdateMessage(isInitialized);
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            TrueTime.build().initialize();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public void setClockActivity(ClockActivity clockActivity) {
        this.clockActivity = clockActivity;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }
}
