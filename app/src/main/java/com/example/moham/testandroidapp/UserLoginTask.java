package com.example.moham.testandroidapp;

import android.os.AsyncTask;
import android.os.Handler;
import android.webkit.CookieManager;
import android.widget.Toast;

import com.bulutsoft.attendance.R;

import clock.aut.SingleTon;
import service.LoginRepository;
import service.base.MyGlobal;
import service.models.LoginViewModelResult;

public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

    private final String mEmail;
    private final String mPassword;
    private final LoginActivity loginActivity;

    private LoginResultCallback callback;

    UserLoginTask(String email, String password , LoginResultCallback callback, LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        mEmail = email;
        mPassword = password;
        this.callback=callback;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        LoginRepository repository = new LoginRepository(MyGlobal.serverBaseUrlApi);
        LoginViewModelResult model = null;
        try {
            model = repository.Login(mEmail, mPassword);
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
            if (model.isSuccess()) {


                callback.onLoginSuccess(mEmail,mPassword);




                SingleTon.getInstance().setToken(model.getToken());
                SingleTon.getInstance().setAdmin(model.isAdmin());
                SingleTon.getInstance().setOneDeviceEnabled(model.getOneDeviceEnabled());
                SingleTon.getInstance().setNotificationsEnabled(model.getNotificationsEnabled());
                SingleTon.getInstance().setFaceRecognation(model.getFaceRecognation());
                SingleTon.getInstance().setClockLastMessage(model.getMessage());

                // Set the JWT as a cookie for the WebView
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setCookie(MyGlobal.serverBase, "jwt=" + model.getToken());

                //   if (rememberMeCheckbox.isChecked()) {

                 /*   sharedPref.edit().putString("username", mEmail)
                            .putString("password", mPassword)
                            .putBoolean("rememberMeCheckbox", true).commit();*/

                //  }


                return true;
            } else {
                callback.onLoginFailure("Login failed. Please check your credentials.");

                return false;
            }

        }

        // TODO: register the new account here.
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        loginActivity. mAuthTask = null;
        loginActivity.showProgress(false);

        if (success) {


            loginActivity.showProgress(true);
            GetProfileTask task = new GetProfileTask(loginActivity);
            task.execute((Void) null);


        } else {
            Toast.makeText(loginActivity, "نام کاربری یا رمز عبور اشتباه است", Toast.LENGTH_LONG).show();
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
