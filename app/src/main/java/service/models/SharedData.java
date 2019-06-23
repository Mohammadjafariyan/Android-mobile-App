package service.models;

import android.location.Location;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.widget.ImageView;

import com.instacart.library.truetime.TrueTime;

import java.util.List;

import clock.aut.TimeUpdateTask;
import service.base.ClockType;

public class SharedData {

    private String token;
    private Location location;
    private ImageView imageView;

    private String qRCodeContent;

    private List<ScanResult> scanResults;
    private List<UserClockTypeViewModel> userClockTypes;
    private boolean admin;
    private ScanResult selectedScanedResult;
    private boolean oneDeviceEnabled;
    private boolean faceRecognation;
    private boolean notificationsEnabled;
    private String message;
    private boolean success;
    private String clockLastMessage;
    private boolean loggedIn;
    private Bundle webViewState;
    private TimeUpdateTask timeUpdateTask;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public List<ScanResult> getScanResults() {
        return scanResults;
    }

    public void setScanResults(List<ScanResult> scanResults) {
        this.scanResults = scanResults;
    }


    public String getqRCodeContent() {
        return qRCodeContent;
    }

    public void setqRCodeContent(String qRCodeContent) {
        this.qRCodeContent = qRCodeContent;
    }

    public boolean isOk() {

        if (oneDeviceEnabled) {
            if (qRCodeContent == null)
                return false;
            return true;
        }


        for (int i = 0; i < userClockTypes.size(); i++) {

            ClockType c=userClockTypes.get(i).getClockType();
            switch (c) {
                case GPS:
                    if (location == null)
                        return false;
                    break;
                case Wifi:
                    if (scanResults == null)
                        return false;
                    break;
                case QRCode:
                    if (qRCodeContent == null)
                        return false;
                    break;
                case CameraSelfie:
                    if (imageView == null)
                        return false;
                    break;
            }

        }
        return true;
    }



    public void setUserClockTypes(List<UserClockTypeViewModel> userClockTypes) {
        this.userClockTypes = userClockTypes;
    }

    public List<UserClockTypeViewModel> getUserClockTypes() {
        return userClockTypes;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setSelectedScanedResult(ScanResult selectedScanedResult) {
        this.selectedScanedResult = selectedScanedResult;
    }

    public ScanResult getSelectedScanedResult() {
        return selectedScanedResult;
    }


    public void setOneDeviceEnabled(boolean oneDeviceEnabled) {
        this.oneDeviceEnabled = oneDeviceEnabled;
    }

    public boolean getOneDeviceEnabled() {
        return oneDeviceEnabled;
    }

    public void setFaceRecognation(boolean faceRecognation) {
        this.faceRecognation = faceRecognation;
    }

    public boolean getFaceRecognation() {
        return faceRecognation;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public boolean getNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setClockLastMessage(String clockLastMessage) {
        this.clockLastMessage = clockLastMessage;
    }

    public String getClockLastMessage() {
        return clockLastMessage;
    }


    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public void setWebViewState(Bundle webViewState) {
        this.webViewState = webViewState;
    }

    public Bundle getWebViewState() {
        return webViewState;
    }

    public boolean isClockUpdate() {
        return TrueTime.isInitialized();
    }

    public void setTimeUpdateTask(TimeUpdateTask timeUpdateTask) {
        this.timeUpdateTask = timeUpdateTask;
    }

    public TimeUpdateTask getTimeUpdateTask() {
        return timeUpdateTask;
    }
}
