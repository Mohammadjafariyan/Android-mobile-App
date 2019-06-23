package service.backgorund_services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.Arrays;

import clock.aut.SingleTon;
import service.socketServices.ISocketListener;
import service.socketServices.SocketService;
import service.socketServices.UserClockListener;

public class MySocketService extends Service {
    private SocketService socketService = null;


    @Override
    public IBinder onBind(Intent intent) {
        this.socketService = MySocketServiceSingleton.make(this);

        if (SingleTon.getInstance().isAdmin()) {
            try {
                socketService.init();
            } catch (URISyntaxException e) {
                e.printStackTrace();
                Toast.makeText(this, "خطای ارتباط آنلاین با سرور", Toast.LENGTH_LONG).show();
            }
        }


        return null;//super.onStartCommand(intent, flags, startId);
    }

    public static class MySocketServiceSingleton {
        private static SocketService socketService = null;


        public static void UnRegisterListener(String name) {
            for (int i = 0; i < socketService.socketListeners.size(); i++) {
                if (socketService.socketListeners.get(i).getUniqName() == name) {
                    socketService.socketListeners.remove(i);
                }
            }
        }

        public static void registerListener(String name,Context c) throws Exception {
            UnRegisterListener(name);
            if (name == UserClockListener.UniqName) {
                socketService.socketListeners.add(new UserClockListener(socketService, c));
                return;
            }
            throw new Exception("not supported");

        }

        public static SocketService make(Context context) {

            ISocketListener[] listeners = {

            };

            if (SingleTon.getInstance().getNotificationsEnabled()) {
                Arrays.asList(listeners).add(new UserClockListener(socketService, context));
            }

            return socketService = new SocketService(listeners);
        }

    }
}
