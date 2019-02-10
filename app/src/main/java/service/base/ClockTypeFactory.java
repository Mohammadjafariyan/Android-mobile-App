package service.base;

import android.app.Activity;
import android.content.Context;

import service.CameraService;
import service.GPSClockService;

public class ClockTypeFactory {


    private final Context mContext;
    private final Activity mActivity;

    public ClockTypeFactory(Context context, Activity activity) {
        this.mContext = context;
        this.mActivity=activity;
    }



    public IClockType GetClockType(ClockType type) throws Exception {

        switch (type){
            case GPS:
                return new GPSClockService(mContext,mActivity);
            case CameraSelfie:
                return new CameraService(mContext,mActivity);
        }

        throw new Exception("GetClockType not Recognized");
    }
}
