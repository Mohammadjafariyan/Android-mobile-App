package service.backgorund_services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.IBinder;

import java.util.List;

public class ShakeService extends Service {
    private SensorManager sensorMgr;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);

        shakeListenerSingleton.make(this);
        shakeListenerSingleton.getShakeListener().setOnShakeListener(new OnShakeListenerImp(this));

        // sensorMgr.unregisterListener();

        sensorMgr.registerListener(shakeListenerSingleton.getShakeListener(),
                SensorManager.SENSOR_ACCELEROMETER,
                SensorManager.SENSOR_DELAY_GAME);

        return START_STICKY ;//super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);


        ShakeListener sl = new ShakeListener(this);
        sl.setOnShakeListener(new OnShakeListenerImp(this));


        sensorMgr.registerListener(sl,
                SensorManager.SENSOR_ACCELEROMETER,
                SensorManager.SENSOR_DELAY_GAME);

        return null;
    }


/*
    if(shaked) {
        Intent intent = new Intent(getApplicationContext(), ActivityThatShouldBeLaunchedAfterShake.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }*/

    public static class shakeListenerSingleton {
        private static ShakeListener shakeListener;

        public static void make(Context context) {
            shakeListener = new ShakeListener(context);
        }

        public static ShakeListener getShakeListener() {
            return shakeListener;
        }

        public static void setShakeListener(ShakeListener shakeListener) {
            shakeListenerSingleton.shakeListener = shakeListener;
        }
    }
}


