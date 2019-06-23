package service.socketServices;

import android.content.Context;

import com.google.gson.Gson;

import base.BaseActivity;

public class UserClockListener implements ISocketListener {
    private final Context context;
    private SocketService socketService;
    public final static String UniqName="UserClockListener";


    public UserClockListener(SocketService socketService, Context context) {
        this.socketService = socketService;
        this.context = context;
    }

    @Override
    public String getUniqName() {
        return UniqName;
    }

    @Override
    public void onReceive(Object... o) {

        String json = (String) o[0];

        Gson gson = new Gson();
        MySocketText model = gson.fromJson(json, MySocketText.class);

        BaseActivity.sendNotification(model.getObject(), "گزارش خلاصه وضعیت ", this.context);

    }

    @Override
    public void setSocketService(SocketService socketService) {
        this.socketService = socketService;
    }
}


