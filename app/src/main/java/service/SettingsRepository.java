package service;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.wifi.ScanResult;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.List;

import service.models.ObjectPostViewModel;
import service.models.OfficeLocationViewModel;
import service.models.VoidResultViewModel;

public class SettingsRepository extends BaseRepository {

    private String savePersonImageUrl = "settings/SavePersonImage";
    private String saveSelectedWifiUrl = "settings/SaveSelectedWifi";
    private String saveIsOneDeviceEnabledUrl = "settings/SaveIsOneDeviceEnabled";
    private String saveFaceRecognationUrl = "settings/SaveFaceRecognation";
    private String saveNotificationsEnabledUrl = "settings/SaveNotificationsEnabled";


    public void savePersonImage(ImageView imageView) throws Exception {
        if (imageView == null)
            throw new Exception("عکس گرفته شده نال است به عبارتی  هیچ عکسی گرفته نشده است");

        byte[] bitmapdata = new byte[0];
        if (imageView != null) {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

            if (bitmap != null) {

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
                bitmapdata = bos.toByteArray();
            }
        }


        callurl(this.baseurl + "/" + savePersonImageUrl, new ObjectPostViewModel(bitmapdata));
    }

    public void saveSelectedWifi(ScanResult scanResults) throws Exception {
        if (scanResults == null)
            throw new Exception("دستگاه وای فای انتخاب شده نال است هیچ دستگاهی انتخاب نشده است");

        callurl(this.baseurl + "/" + saveSelectedWifiUrl, new ObjectPostViewModel(scanResults));
    }

    public void saveIsOneDeviceEnabled(boolean isChecked) throws Exception {
        callurl(this.baseurl + "/" + saveIsOneDeviceEnabledUrl, new ObjectPostViewModel(isChecked));
    }

    public void saveFaceRecognation(boolean isChecked) throws Exception {
        callurl(this.baseurl + "/" + saveFaceRecognationUrl, new ObjectPostViewModel(isChecked));

    }

    public void saveNotificationsEnabled(boolean isChecked) throws Exception {
        callurl(this.baseurl + "/" + saveNotificationsEnabledUrl, new ObjectPostViewModel(isChecked));
    }


}
