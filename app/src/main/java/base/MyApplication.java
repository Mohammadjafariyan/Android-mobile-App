package base;

import clock.aut.SingleTon;
import clock.aut.TimeUpdateTask;

public class MyApplication extends  android.app.Application {


    @Override
    public void onCreate() {
        super.onCreate();
        updateTime();

    }
    private void updateTime() {
        TimeUpdateTask task = new TimeUpdateTask();
        SingleTon.getInstance().setTimeUpdateTask(task);
        task.execute((Void) null);
    }
}
