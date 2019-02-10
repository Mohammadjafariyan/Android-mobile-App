package service.models;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginViewModelResult extends  BaseViewModel {
    private boolean oneDeviceEnabled;
    private boolean loggedIn;
    private boolean notificationsEnabled;
    private boolean faceRecognation;

    public LoginViewModelResult() {

    }


    private boolean isAdmin;



    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }


    public boolean getOneDeviceEnabled() {
        return oneDeviceEnabled;
    }

    public void setOneDeviceEnabled(boolean oneDeviceEnabled) {
        this.oneDeviceEnabled = oneDeviceEnabled;
    }

    public boolean getNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public boolean getFaceRecognation() {
        return faceRecognation;
    }

    public void setFaceRecognation(boolean faceRecognation) {
        this.faceRecognation = faceRecognation;
    }
}


