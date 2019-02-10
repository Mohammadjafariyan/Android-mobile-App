package clock.aut;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moham.testandroidapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import base.BaseActivity;
import config.setting.WifiConfigActivity;

import static android.content.Intent.FLAG_ACTIVITY_FORWARD_RESULT;
import static android.content.Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP;

public class WifiActivity extends BaseActivity {
    private WifiManager wifi;
    private ListView lv;
    ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;
    String ITEM_KEY = "key";
    protected List<ScanResult> scanResults;


    protected View mProgressView;

    protected ListView avilablewifiList;
    protected LinkedList<String> strings;
    protected ScanResult selectedScanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        setTitle("اطلاعات Wifi");
        initWifi();
    }

    private void initWifi() {

        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled() == false) {
            Toast.makeText(getApplicationContext(), "تکنولوژی wifi شما خاموش است در حال روشن کردن ...", Toast.LENGTH_LONG).show();
            wifi.setWifiEnabled(true);
        }


            BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                List<ScanResult> results = wifi.getScanResults();
                int size = results.size();
                unregisterReceiver(this);
                //finishActivity(FLAG_ACTIVITY_FORWARD_RESULT );

                SingleTon.getInstance().setScanResults(results);

                scanResults = results;

                setFinish();


                TextView wifiNotfoundTextView = (TextView) findViewById(R.id.wifiNotfoundTextView);
                if (wifiNotfoundTextView != null) {
                    wifiNotfoundTextView.setText("");
                }

            }


            @Override
            public IBinder peekService(Context myContext, Intent service) {
                return super.peekService(myContext, service);
            }
        };

        registerReceiver(broadcastReceiver
                , new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        // wifi.startScan();


    }

    protected void setFinish() {

        //convertToList();

        setResult(Activity.RESULT_OK);
        finish();


    }


    public void convertToList() {

        strings = new LinkedList<>();
        for (ScanResult vm : scanResults) {
            strings.add(vm.SSID);
        }

        String[] str = strings.toArray(new String[strings.size()]);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.office_locations_layout, R.id.message, str);

        avilablewifiList = (ListView) findViewById(getListViewIdName());
        avilablewifiList.setAdapter(adapter);




        avilablewifiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                mProgressView.setVisibility(View.INVISIBLE);


                if (!haveNetworkConnection(WifiActivity.this, "به اینترنت متصل نیستید")) {
                    return;
                }

                // selected item
                String selected = (String) avilablewifiList.getItemAtPosition(position);

                String name = strings.get(strings.indexOf(selected));


                ScanResult scanResult = findByNameInResults(name);

                selectedScanResult = scanResult;

                for (int i = 0; i < avilablewifiList.getChildCount(); i++) {
                    if (position == i) {
                        ((TextView) avilablewifiList.getChildAt(i)).setTextColor(Color.BLUE);
                    } else {
                        ((TextView) avilablewifiList.getChildAt(i)).setTextColor(Color.TRANSPARENT);
                    }
                }

                setResult(RESULT_OK);
                finish();

            }
        });
    }

    protected int getListViewIdName() {
        return R.id.avilablewifiList;
    }

    private ScanResult findByNameInResults(String name) {
        for (ScanResult sr : scanResults) {
            if (sr.SSID == name)
                return sr;

        }
        try {
            throw new Exception("wifi انتخاب شده یافت نشد");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(WifiActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }


}
