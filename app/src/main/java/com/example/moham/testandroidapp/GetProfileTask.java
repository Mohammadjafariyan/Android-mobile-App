package com.example.moham.testandroidapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import clock.aut.SingleTon;
import service.LoginRepository;
import service.ProfileClient;
import service.base.MyGlobal;
import service.models.ApplicationUser;
import service.models.LoginViewModelResult;

public class GetProfileTask extends AsyncTask<Void, Void, Boolean> {

    private final LoginActivity loginActivity;

    private LoginResultCallback callback;

    GetProfileTask ( LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        ProfileClient client = new ProfileClient(MyGlobal.serverBaseUrlApi);
        ApplicationUser model = null;
        try {
            model = client.Get();
        } catch (Exception e) {
            e.printStackTrace();

            Handler handler = new Handler(loginActivity.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    Toast.makeText(loginActivity, " خطای ورود به سیستم " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }

        if (model == null) {
            return false;

        } else {
         return true;

        }

        // TODO: register the new account here.
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        loginActivity. mAuthTask = null;
        loginActivity.showProgress(false);

        if (success) {


            loginActivity.startActivity(new Intent(loginActivity, clock.aut.ClockActivity.class));

        } else {
            Toast.makeText(loginActivity, "در دریافت اطلاعات پرفایل مشکلی بوجود آمد", Toast.LENGTH_LONG).show();
            loginActivity.mEmailView.setError(loginActivity.getString(R.string.error_invalid_email));
            loginActivity.mPasswordView.setError(loginActivity.getString(R.string.error_incorrect_password));
            loginActivity.mPasswordView.requestFocus();
        }
    }

    @Override
    protected void onCancelled() {
        loginActivity.mAuthTask = null;
        loginActivity. showProgress(false);
    }
}
