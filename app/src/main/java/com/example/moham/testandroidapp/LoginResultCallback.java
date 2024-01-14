package com.example.moham.testandroidapp;

public interface LoginResultCallback {
    void onLoginSuccess(String username,String password);
    void onLoginFailure(String errorMessage);
}
