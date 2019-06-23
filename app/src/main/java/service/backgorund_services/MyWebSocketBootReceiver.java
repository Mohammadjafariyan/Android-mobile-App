package service.backgorund_services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyWebSocketBootReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        intent = new Intent(context, MySocketService.class);
        context.startService(intent);
    }
}
