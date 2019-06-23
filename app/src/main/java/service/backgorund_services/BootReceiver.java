package service.backgorund_services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class BootReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        intent = new Intent(context, ShakeService.class);
        context.startService(intent);
    }
}



