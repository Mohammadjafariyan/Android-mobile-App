package clock.aut;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moham.testandroidapp.LoginActivity;
import com.example.moham.testandroidapp.R;

import java.io.IOException;
import java.time.Clock;
import java.util.Arrays;
import java.util.List;

import base.BaseActivity;
import config.setting.FaceConfigActivity;
import config.setting.OfficeLocationActivity;
import config.setting.SettingActivity;
import config.setting.WifiConfigActivity;
import service.DashboardItemRepository;
import service.models.PersonnelClockStatusViewModel;

public class DashboardActivity extends BaseActivity {


    private DashboardItemAdapter dashboardItemAdapter;
    private DashboardItemRepository dashboardItemRepository = new DashboardItemRepository();
    private ListView dashboardItemslistView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //  mTextMessage.setText(R.string.title_notifications);
                    intent = new Intent(DashboardActivity.this
                            , ClockActivity.class);
                    startActivityForResult(intent, 6);
                    return true;
            /*    case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:

                    //  mTextMessage.setText(R.string.title_notifications);
                    intent = new Intent(DashboardActivity.this
                            , SettingActivity.class);
                    startActivityForResult(intent, 5);
                    return true;*/
                case R.id.navigation_web:

                    //  mTextMessage.setText(R.string.title_notifications);
                    intent = new Intent(DashboardActivity.this
                            , WebActivity.class);
                    startActivityForResult(intent, 7);
                    return true;
            }
            return false;
        }
    };
    private PersonnelClockStatusViewModel[] models;
    private Button showInMapButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setTitle("داشبورد");


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.dashboardBottomMenu);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        navigation.setSelectedItemId(R.id.navigation_dashboard);

        progressBar = (ProgressBar) findViewById(R.id.dashboard_progress);




        initButton();

        readdataTask();


        hideIfUserisNotAdmin();


        initPullButton();
    }

    private void initPullButton() {
        SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                readdataTask();
                pullToRefresh.setRefreshing(false);
            }
        });
    }


    public void doneProgress(){
        progressBar.setVisibility(View.GONE );
    }

    private void hideIfUserisNotAdmin() {
        if (!SingleTon.getInstance().isAdmin()) {
            showInMapButton.setVisibility(View.INVISIBLE);
        }
    }

    private void initButton() {

        showInMapButton = (Button) findViewById(R.id.showInMapButton);
        showInMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this
                        , PersonnelInMapActivity.class);
                startActivityForResult(intent, 7);
            }
        });
    }


    private void readdataTask() {

        progressBar.setVisibility(View.VISIBLE );
        getPersonnelClockStatusByDateTask task = new getPersonnelClockStatusByDateTask();
        task.execute((Void) null);
    }

    private void initViewList() {

        dashboardItemslistView = (ListView) findViewById(R.id.dashbaordItemsListVew);


        try {

            DashboardItemAdapter adapter = new DashboardItemAdapter(DashboardActivity.this, models);

            dashboardItemslistView.setAdapter(adapter);


            dashboardItemslistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    if (!haveNetworkConnection(DashboardActivity.this,
                            "به اینترنت متصل نیستید")) {
                        return;
                    }


                    Intent intent = new Intent(DashboardActivity.this
                            , PersonnelDetailActivity.class);


                    PersonnelClockStatusViewModel model = models[position];
                    intent.putExtra("PersonnelId", model.getPersonnelId());
                    startActivityForResult(intent, 5);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(DashboardActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    class getPersonnelClockStatusByDateTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (aBoolean) {
                initViewList();
                doneProgress();
            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                models = dashboardItemRepository.getPersonnelClockStatusByDate(0);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }


            return true;
        }
    }
}



