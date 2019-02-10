package service;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;

import service.base.IClockType;
import service.base.MyCallback;
import service.other.CameraController;
import service.other.GPSTracker;

public class CameraService implements IClockType {

    private final Context context;
    private final Activity activity;
    private CameraController cameraController;
    private GPSTracker gPSTracker;
    private Boolean isSuccess = false;
    private FileOutputStream resault;
    private  boolean success=false;


    public CameraService(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    public Object clockInAttempt() throws Exception {

        cameraController = new CameraController(context, new MyCallback() {
            @Override
            public void Callback(Object o)  {
                resault = (FileOutputStream) o;
                cameraController.releaseCamera();
            }
        });

        cameraController.getCameraInstance();

     //   cameraController.takePicture();

       /* while (resault == null && !cameraController.isError()) {

            Log.d("getting result", "getting resault in while");
        }*/
        success=true;
        return cameraController.getCamera();
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
