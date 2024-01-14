package com.example.moham.testandroidapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;

import java.io.IOException;
import java.util.List;

import clock.aut.SingleTon;
import service.CommonRepository;
import service.models.UserClockTypeViewModel;

public class UserClockTypeListTask extends AsyncTask<Void, Void, Boolean> {

    private CommonRepository commonRepository;
    private LoginActivity loginActivity;

    public UserClockTypeListTask(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }

    public void showAlert(String title, String body) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(loginActivity, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(loginActivity);
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

    @Override
    protected Boolean doInBackground(Void... voids) {

        commonRepository = new CommonRepository();
        List<UserClockTypeViewModel> models = null;
        try {
            models = commonRepository.getUserClockTypeList();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
           // showAlert("خطا", e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println(e.getMessage());
         //   showAlert("خطا", e.getMessage());
            return false;
        }

        SingleTon.getInstance().setUserClockTypes(models);

        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        loginActivity.mAuthTask = null;
        loginActivity.showProgress(false);

        if (success) {

            //finish();
            loginActivity.startActivity(new Intent(loginActivity, clock.aut.ClockActivity.class));
        } else {

            Handler handler = new Handler(loginActivity.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    showAlert("خطا", "در دریافت روش های ثبت ساعت از سرور خطایی بوجود آمد");
                }
            });
        }
    }
}
