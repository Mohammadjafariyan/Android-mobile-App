package service;

import android.app.Activity;
import android.content.Context;
import android.location.Location;

import service.base.IClockType;
import service.other.GPSTracker;

public class GPSClockService implements IClockType {

    private GPSTracker gPSTracker;
    private Boolean isSuccess = false;


    public GPSClockService(Context context, Activity activity) {
        gPSTracker = new GPSTracker(context, activity);
    }


    @Override
    public Object clockInAttempt() throws Exception {

        Location location = gPSTracker.getLocation();

        isSuccess = true;

        return location;

    }

    @Override
    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
