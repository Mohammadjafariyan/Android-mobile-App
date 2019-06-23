package service.backgorund_services;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/* Boolean isServiceRunning = ServiceTools.isServiceRunning(
         MainActivity.this.getApplicationContext(),
         BackgroundIntentService.class);*/
public class ServiceTools {
 private static String LOG_TAG = ServiceTools.class.getName();

 public static boolean isServiceRunning(Context context, Class<?> serviceClass){
     final ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
     final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

     for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
        // Log.d(Constants.TAG, String.format("Service:%s", runningServiceInfo.service.getClassName()));
         if (runningServiceInfo.service.getClassName().equals(serviceClass.getName())){
             return true;
         }
     }
     return false;
 }
}
