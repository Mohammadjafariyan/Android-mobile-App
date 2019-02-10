package config.setting;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moham.testandroidapp.R;

import java.io.IOException;
import java.util.LinkedList;

import clock.aut.SingleTon;
import clock.aut.WifiActivity;
import service.models.OfficeLocationViewModel;

public class WifiConfigActivity extends WifiActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_config);
        setTitle("تنظیم Wifi");

        initSaveButton();

        mProgressView = findViewById(R.id.wifi_progress);

        mProgressView.setVisibility(View.VISIBLE);
    }

    private void initSaveButton() {

        if (selectedScanResult != null) {
            SingleTon.getInstance().setSelectedScanedResult(selectedScanResult);
            setResult(Activity.RESULT_OK);
            finish();
        } else {
            Toast.makeText(WifiConfigActivity.this, "هیچ موردی انتخاب نشده است", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void setFinish() {
        convertToList();
    }


    protected int getListViewIdName() {
        return R.id.avilablewifiConfigList;
    }
}