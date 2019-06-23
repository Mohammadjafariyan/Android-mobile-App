package base;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.moham.testandroidapp.LoginActivity;
import com.example.moham.testandroidapp.R;

import java.time.Clock;

import clock.aut.ClockActivity;
import clock.aut.GPSActivity;
import clock.aut.SingleTon;
import config.setting.SettingActivity;
import service.SettingsRepository;
import service.backgorund_services.ServiceTools;
import service.backgorund_services.ShakeService;
import service.base.MyGlobal;

public class BaseActivity extends AppCompatActivity {
    protected SharedPreferences sharedPref = null;
    protected boolean fromService=false;

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void ringtone() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    }

    public void registerServices(Class<?> cls) {
        // if user enabled this service from settings


        // if not Registered before ?
        if (ServiceTools.isServiceRunning(
                BaseActivity.this.getApplicationContext(),
                ShakeService.class) == false) {

            // register service
            Intent intent = new Intent(this, cls);
            startService(intent);
        } else {

            // register service
            Intent intent = new Intent(this, cls);
            startService(intent);
        }
    }

    public void unregisterServices(Class<?> cls) throws Exception {
        // if user enabled this service from settings
        // if  Registered before ?
        if (ServiceTools.isServiceRunning(
                BaseActivity.this.getApplicationContext(),
                cls) == true) {

            SensorManager sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);

            if (ShakeService.shakeListenerSingleton.getShakeListener() == null)
                throw new Exception("hakeListener not initialized");


            sensorMgr.unregisterListener(ShakeService.shakeListenerSingleton.getShakeListener());
        }
    }




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = BaseActivity.this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        accessControl();
    }

    protected void accessControl() {

        if (SingleTon.getInstance().getToken() == null) {
            logout();
        }
    }

    protected void logout() {


        sharedPref.edit().clear().commit();

        SingleTon.getInstance().setToken(null);
        SingleTon.getInstance().setAdmin(false);

        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        BaseActivity.this.startActivity(intent);
    }

    protected boolean haveNetworkConnection(Context context, String msg) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        if (haveConnectedWifi || haveConnectedMobile) {

            return true;
        } else {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void showAlert(String title, String body, Context context) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle(title)
                .setMessage(body)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    public static void sendNotification(String message, String title, Context context) {
        try {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("fromService",true);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Intent secondActivityIntent = new Intent(context, LoginActivity.class);
            secondActivityIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            secondActivityIntent.putExtra("fromService",true);
            PendingIntent secondActivityPendingIntent = PendingIntent.getActivity(context, 0, secondActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

           /* Intent thirdActivityIntent = new Intent(context, LoginActivity.class);
            PendingIntent thirdActivityPendingIntent = PendingIntent.getActivity(context, 0, thirdActivityIntent, PendingIntent.FLAG_CANCEL_CURRENT);
*/



            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentInfo(title)
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(Notification.PRIORITY_HIGH)
                    .addAction(R.drawable.mapbox_marker_icon_default, "کارت بزن", secondActivityPendingIntent)
                 //   .addAction(R.drawable.side_nav_bar, "لغو", thirdActivityPendingIntent)
                    .setAutoCancel(true)
                    .setColor(Color.parseColor("#E51997"))
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);


            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {

        if (intent != null) {
            fromService = intent.getBooleanExtra("fromService", false);
        } else {
        }
    }

}
