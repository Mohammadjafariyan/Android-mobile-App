package config.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bulutsoft.attendance.R;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import base.BaseActivity;
import clock.aut.SingleTon;
import service.OfficeLocationService;
import service.SettingsRepository;
import service.models.OfficeLocationViewModel;

public class OfficeLocationsListActivity extends BaseActivity {

    public static final int SelfieCamera_Activity = 1;
    public static final int WifiConfig_Activity = 2;
    private OfficeLocationService officeLocationService = new
            OfficeLocationService();
    private ListView listView;
    private LinkedList<String> strings;
    private List<OfficeLocationViewModel> modelList;
    private Button saveOrCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_locations_list);
        setTitle("محل های کاری");


        initOfficeLocations();

        initCreateNew();

    }

    private void initCreateNew() {
        saveOrCreateButton = (Button) findViewById(R.id.saveOrCreateNewOfficeLocation);
        saveOrCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!haveNetworkConnection(OfficeLocationsListActivity.this, "به اینترنت متصل نیستید")) {
                    return;
                }
                goToCreateNew(0);
            }
        });


    }

    private void initOfficeLocationsAfterpost() {

        strings = new LinkedList<>();
        for (OfficeLocationViewModel vm : modelList) {
            strings.add(vm.getName());
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_office_locations_list, (String[]) strings.toArray());

        listView = (ListView) findViewById(R.id.officeLocationsListView);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (!haveNetworkConnection(OfficeLocationsListActivity.this, "به اینترنت متصل نیستید")) {
                    return;
                }

                // selected item
                String selected = (String) listView.getItemAtPosition(position);

                OfficeLocationViewModel selectedModel = modelList.get(strings.indexOf(selected));

                goToCreateNew(selectedModel.getId());

            }
        });

    }

    private void initOfficeLocations() {


        InitLocationsTask task = new InitLocationsTask();
        task.execute((Void) null);
    }


    public void goToCreateNew(int id) {
        Intent intent = null;
        if (getIntent().getExtras().get("type").equals("wifi")) {

            intent = new Intent(OfficeLocationsListActivity.this
                    , WifiConfigActivity.class);

        } else if (getIntent().getExtras().get("type").equals("gps")) {

            intent = new Intent(OfficeLocationsListActivity.this
                    , OfficeLocationActivity.class);

        } else {
            try {
                throw new Exception("type is not recognized");
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(OfficeLocationsListActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        intent.putExtra("id", id);
        startActivityForResult(intent, id);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        initOfficeLocations();
    }



    public class GenericTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (aBoolean) {
                onResult();
            } else {

                Handler handler = new Handler(OfficeLocationsListActivity.this.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(OfficeLocationsListActivity.this, "برای خروج دوباره دگمه back را لمس نمایید", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        protected void onResult() {
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                doPost();
            } catch (Exception e) {
                e.printStackTrace();
                revertPost();
                return false;
            }


            return true;
        }

        protected void doPost() throws Exception {
        }

        protected void revertPost() {
        }
    }
    class InitLocationsTask extends GenericTask {

        protected void doPost() throws Exception {
            modelList = officeLocationService.getAll();
        }

        protected void onResult() {

            initOfficeLocationsAfterpost();
        }

        protected void revertPost() {
            //  SingleTon.getInstance().setOneDeviceEnabled(!SingleTon.getInstance().getOneDeviceEnabled());
        }

    }
}
