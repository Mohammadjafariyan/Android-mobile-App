package service.models;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ClockInViewModel extends BaseViewModel {
    private List<ScanResult> scanResults;
    private List<MyLocation> location = new LinkedList<>();
    private String qRCodeContent;
    private byte[] bitmapdata;

    private Date datetime;


    public void setScanResults(List<ScanResult> scanResults) {
        this.scanResults = scanResults;


    }


    public List<ScanResult> getScanResults() {
        return scanResults;
    }

    public void setLocation(Location location) {
        MyLocation myLocation = new MyLocation();
        myLocation.setAccuracy(location.getAccuracy());
        myLocation.setLatitude(location.getLatitude());
        myLocation.setLongitude(location.getLongitude());
        myLocation.setSpeed(location.getSpeed());
        myLocation.setTime(location.getTime());

        this.location.clear();
        this.location.add(myLocation);
    }

    public MyLocation getLocation() {
        return this.location.isEmpty() ? null : this.location.get(0);
    }

    public void setqRCodeContent(String qRCodeContent) {
        this.qRCodeContent = qRCodeContent;
    }

    public String getqRCodeContent() {
        return qRCodeContent;
    }

    public void setImageView(ImageView imageView) {
        if (imageView != null) {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

            if (bitmap != null) {

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
                this.bitmapdata = bos.toByteArray();
            }
        }


    }


    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}


