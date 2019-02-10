package service.other;


import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import service.base.MyCallback;

public class CameraController {

    private Context context;

    private boolean hasCamera;

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    private Camera camera;
    private int cameraId;
    private MyCallback myCallback;
    private boolean error = false;

    public CameraController(Context c, MyCallback myCallback) {

        this.myCallback = myCallback;

        context = c.getApplicationContext();

        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            cameraId = getFrontCameraId();

            if (cameraId != -1) {
                hasCamera = true;
            } else {
                hasCamera = false;
            }
        } else {
            hasCamera = false;
        }
    }


    public boolean hasCamera() {
        return hasCamera;
    }

    public void getCameraInstance() {
        camera = null;
        camera = Camera.open(cameraId);
        prepareCamera();
    }

    public void takePicture() {
        if (camera!=null) {
            camera.takePicture(null, null, mPicture);
        }
    }

    public void releaseCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    private int getFrontCameraId() {
        int camId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo ci = new Camera.CameraInfo();

        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, ci);
            if (ci.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                camId = i;
            }
        }

        return camId;
    }

    private void prepareCamera() {
        SurfaceView view = new SurfaceView(context);

        try {
            camera.setPreviewDisplay(view.getHolder());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        camera.startPreview();

        Camera.Parameters params = camera.getParameters();
        params.setJpegQuality(100);

        camera.setParameters(params);
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile();

            if (pictureFile == null) {
                Log.d("TEST", "Error creating media file, check storage permissions");
                return;
            }

            try {
                Log.d("TEST", "File created");
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();

                myCallback.Callback(fos);
            } catch (FileNotFoundException e) {
                error = true;
                releaseCamera();

                Log.d("TEST", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("TEST", "Error accessing file: " + e.getMessage());
                releaseCamera();
                error = true;
            } catch (Exception e) {
                error = true;
                releaseCamera();
                e.printStackTrace();
            }
        }
    };

    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
