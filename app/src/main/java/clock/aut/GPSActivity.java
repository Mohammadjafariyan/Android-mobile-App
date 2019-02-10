package clock.aut;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.moham.testandroidapp.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import base.BaseActivity;
import service.base.ClockType;
import service.base.ClockTypeFactory;
import service.base.IClockType;

import static android.content.Intent.FLAG_ACTIVITY_FORWARD_RESULT;
import static android.content.Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP;
import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class GPSActivity extends BaseActivity {

    private ClockTypeFactory clockTypeFactory = new ClockTypeFactory(GPSActivity.this, this);
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mapbox.getInstance(this, "pk.eyJ1IjoibW9oYW1tYWRqYWZhcml5YW43IiwiYSI6ImNqcXRqenkyczBha2k0M281NjQ0amVlNWwifQ.xD69Nt5VabUT8dwmlTOdWQ");
        setContentView(R.layout.activity_gps);
        setTitle("مکان یابی");

        clockInAttempt();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_GRANTED) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)
                        || permission.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {

                    } else {
                    }
                }
            }
        }


    }


    private void clockInAttempt() {
        IClockType clock = null;
        Location location= null;
        try {

            Toast.makeText(getApplicationContext(), "در حال خواندن GPS", Toast.LENGTH_LONG).show();

            clock = clockTypeFactory.GetClockType(ClockType.GPS);
             location = (Location) clock.clockInAttempt();
            if (!clock.isSuccess()) {
                Toast.makeText(getApplicationContext(),  clock.getMessage(),Toast.LENGTH_LONG);


                Intent intent= new Intent();
                intent.putExtra("error", clock.getMessage());
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
                return;
            }

            setResult(Activity.RESULT_OK);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),  e.getMessage(),Toast.LENGTH_LONG);

            Intent intent= new Intent();
            intent.putExtra("error", e.getMessage());
            setResult(Activity.RESULT_CANCELED, intent);

            finish();
            return;
        }
        Toast.makeText(getApplicationContext(), "با موفقیت دیتکت شد", Toast.LENGTH_LONG).show();
        SingleTon.getInstance().setLocation(location);

        finish();

        // showAlert("پیغام", "با موفقیت دیتکت شد");

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
