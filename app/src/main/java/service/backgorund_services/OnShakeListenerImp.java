package service.backgorund_services;

import android.content.Context;
import android.content.Intent;

import com.example.moham.testandroidapp.LoginActivity;

import base.BaseActivity;

public class OnShakeListenerImp implements ShakeListener.OnShakeListener{

    private final Context context;

    public OnShakeListenerImp(Context _context) {
        this.context=_context;
    }

    @Override
    public void onShake() {
      /*  Intent dialogIntent = new Intent(context, LoginActivity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.context.startActivity(dialogIntent);*/


        BaseActivity.sendNotification("جهت تردد لمس نمایید  ","ثبت ساعت ",this.context);

    }
}
