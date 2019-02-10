package config.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moham.testandroidapp.LoginActivity;
import com.example.moham.testandroidapp.R;

import java.time.Clock;
import java.util.Set;

import base.BaseActivity;
import clock.aut.ClockActivity;
import clock.aut.DashboardActivity;
import clock.aut.SingleTon;
import service.OfficeLocationService;
import service.SettingsRepository;

public class SettingActivity extends BaseActivity {

    private SettingsRepository settingsService = new SettingsRepository();
    private TextView mTextMessage;
    private SettingsRepository settingsRepository = new SettingsRepository();
    private OfficeLocationService officeLocationService = new
            OfficeLocationService();

    String[] mobileArray = {"محل های کار GPS", "تشخیص چهره", "انتخاب Wifi دفاتر"};
    String[] arrayForUser = {"تنظیمات برای پرسنل نمایش داده نمی شود "};


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    intent = new Intent(SettingActivity.this
                            , ClockActivity.class);
                    startActivityForResult(intent, 6);

                    return true;
                case R.id.navigation_dashboard:
                    intent = new Intent(SettingActivity.this
                            , DashboardActivity.class);
                    startActivityForResult(intent, 6);
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };
    private Switch oneDeviceEnabledSwitch;
    private Button outButton;
    private Switch faceRecognation;
    private Switch notificationsEnabled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("تنظیمات");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.rnavigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_notifications);
        oneDeviceEnabledSwitch = (Switch) findViewById(R.id.oneDeviceEnabled);
        faceRecognation = (Switch) findViewById(R.id.faceRecognation);
        notificationsEnabled = (Switch) findViewById(R.id.notificationsEnabled);

        oneDeviceEnabledSwitch.setChecked(SingleTon.getInstance().getOneDeviceEnabled());
        faceRecognation.setChecked(SingleTon.getInstance().getFaceRecognation());
        notificationsEnabled.setChecked(SingleTon.getInstance().getNotificationsEnabled());


        String[] arr = null;
        if (SingleTon.getInstance().isAdmin()) {
            arr = mobileArray;
        } else {
            arr = arrayForUser;
        }


        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.office_locations_layout, R.id.message, arr);
        ListView listView = (ListView) findViewById(R.id.listView);

        if (SingleTon.getInstance().isAdmin()) {

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    if (!haveNetworkConnection(SettingActivity.this, "به اینترنت متصل نیستید")) {
                        return;
                    }

                    if (position == 0) {
                        /*Intent intent = new Intent(SettingActivity.this
                                , OfficeLocationActivity.class);

                        intent.putExtra("type", "gps");
                        startActivityForResult(intent, 0);*/

                        Toast.makeText(SettingActivity.this, "این گزینه فقط در وبسایت امکان پذیر است", Toast.LENGTH_LONG).show();

                    } else if (position == 1) {
                        Intent intent = new Intent(SettingActivity.this
                                , FaceConfigActivity.class);

                        startActivityForResult(intent, 1);

                    } else if (position == 2) {

                        Intent intent = new Intent(SettingActivity.this
                                , WifiConfigActivity.class);

                        intent.putExtra("type", "wifi");
                        startActivityForResult(intent, 2);
                    }

                }
            });

            initSwiches();


        } else {

            oneDeviceEnabledSwitch.setVisibility(View.INVISIBLE);
            notificationsEnabled.setVisibility(View.INVISIBLE);
            faceRecognation.setVisibility(View.INVISIBLE);

            //  showAlert("خطا", "فقط کاربران ادمین شرکت ها اچازه ورود به بخش تنظیمات دارند", ClockActivity.this);
        }


        outButton = (Button) findViewById(R.id.logoutButton);

        outButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logout();
            }
        });
        listView.setAdapter(adapter);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OfficeLocationsListActivity.SelfieCamera_Activity && resultCode == Activity.RESULT_OK) {

            FaceImageConfigTask task = new FaceImageConfigTask();
            task.execute((Void) null);

        } else if (requestCode == OfficeLocationsListActivity.WifiConfig_Activity && resultCode == Activity.RESULT_OK) {

            WifiConfigTask task = new WifiConfigTask();
            task.execute((Void) null);

        } else {
            Toast.makeText(SettingActivity.this, "اشکالی در دریافت عکس یا ثبت wifi شما بوجود آمد لطفا مجددا تلاش نمایید", Toast.LENGTH_LONG).show();
        }


    }

    private void initSwiches() {
        oneDeviceEnabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (!haveNetworkConnection(SettingActivity.this, "به اینترنت متصل نیستید")) {
                    oneDeviceEnabledSwitch.setChecked(!isChecked);
                    return;
                }
                saveIsOneDeviceEnabledTask(isChecked);
            }
        });


        faceRecognation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (!haveNetworkConnection(SettingActivity.this, "به اینترنت متصل نیستید")) {
                    faceRecognation.setChecked(!isChecked);
                    return;
                }

                saveFaceRecognationTask(isChecked);


            }
        });


        notificationsEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (!haveNetworkConnection(SettingActivity.this, "به اینترنت متصل نیستید")) {
                    faceRecognation.setChecked(!isChecked);
                    return;
                }

                saveNotificationsEnabledTask(isChecked);

              /*  try {
                    SingleTon.getInstance().setNotificationsEnabled(isChecked);
                    settingsRepository.saveNotificationsEnabled(isChecked);
                } catch (Exception e) {
                    SingleTon.getInstance().setNotificationsEnabled(!isChecked);
                    Toast.makeText(SettingActivity.this, "بدلایلی تغییر انجام نشد", Toast.LENGTH_LONG).show();
                }*/
            }
        });
    }

    private void saveNotificationsEnabledTask(boolean isChecked) {
        SingleTon.getInstance().setNotificationsEnabled(isChecked);

        NotificationsEnabledTask task = new NotificationsEnabledTask();
        task.execute((Void) null);
    }

    private void saveFaceRecognationTask(boolean isChecked) {
        SingleTon.getInstance().setFaceRecognation(isChecked);

        FaceRecognationTask task = new FaceRecognationTask();
        task.execute((Void) null);
    }

    private void saveIsOneDeviceEnabledTask(boolean isChecked) {
        SingleTon.getInstance().setOneDeviceEnabled(isChecked);

        IsOneDeviceEnabledTask task = new IsOneDeviceEnabledTask();
        task.execute((Void) null);
    }

    class NotificationsEnabledTask extends  GenericTask{

        protected void doPost() throws Exception {
            settingsRepository.saveNotificationsEnabled(SingleTon.getInstance().getNotificationsEnabled());
        }
        protected void revertPost() {
            SingleTon.getInstance().setNotificationsEnabled(!SingleTon.getInstance().getNotificationsEnabled());
        }

    }

    class FaceRecognationTask extends  GenericTask  {
        protected void doPost() throws Exception {
            settingsRepository.saveFaceRecognation(SingleTon.getInstance().getFaceRecognation());
        }
        protected void revertPost() {
            SingleTon.getInstance().setFaceRecognation(!SingleTon.getInstance().getFaceRecognation());
        }

    }

    class IsOneDeviceEnabledTask extends GenericTask {

        protected void doPost() throws Exception {
            settingsRepository.saveIsOneDeviceEnabled(SingleTon.getInstance().getOneDeviceEnabled());
        }
        protected void revertPost() {
            SingleTon.getInstance().setOneDeviceEnabled(!SingleTon.getInstance().getOneDeviceEnabled());
        }


    }



    class WifiConfigTask extends GenericTask {

        protected void doPost() throws Exception {
            settingsService.saveSelectedWifi(SingleTon.getInstance().getSelectedScanedResult());
        }

        protected void revertPost() {
            //  SingleTon.getInstance().setOneDeviceEnabled(!SingleTon.getInstance().getOneDeviceEnabled());
        }


    }

    class FaceImageConfigTask extends GenericTask {

        protected void doPost() throws Exception { settingsService.savePersonImage(SingleTon.getInstance().getImageView());
        }

        protected void revertPost() {
            //  SingleTon.getInstance().setOneDeviceEnabled(!SingleTon.getInstance().getOneDeviceEnabled());
        }

    }





    public class GenericTask extends AsyncTask<Void, Void, Boolean> {


        protected void onResult() {
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (aBoolean) {
                onResult();
            } else {

                Handler handler = new Handler(SettingActivity.this.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(SettingActivity.this, "برای خروج دوباره دگمه back را لمس نمایید", Toast.LENGTH_SHORT).show();
                    }
                });
            }
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


}