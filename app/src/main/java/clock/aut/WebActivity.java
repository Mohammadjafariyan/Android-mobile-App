package clock.aut;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moham.testandroidapp.R;

import org.apache.http.util.EncodingUtils;

import base.BaseActivity;
import config.setting.SettingActivity;
import service.CallbackListener;
import service.GPSClockService;
import service.base.MyGlobal;

public class WebActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private WebView webView;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    intent = new Intent(WebActivity.this
                            , ClockActivity.class);
                    startActivityForResult(intent, 6);

                    return true;
                case R.id.navigation_dashboard:
                    intent = new Intent(WebActivity.this
                            , DashboardActivity.class);
                    startActivityForResult(intent, 6);
                    return true;
                case R.id.navigation_notifications:
                    //  mTextMessage.setText(R.string.title_notifications);
                    intent = new Intent(WebActivity.this
                            , SettingActivity.class);
                    startActivityForResult(intent, 7);
                    return true;
                case R.id.navigation_web:

                    return true;
            }
            return false;
        }
    };
    private ProgressBar progressBar;

    private void initPullButton() {
        SwipeRefreshLayout pullToRefresh = findViewById(R.id.web_app_bar_web);
        pullToRefresh.setDistanceToTriggerSync(3);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();

                progressBar.setVisibility(View.VISIBLE);



                pullToRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar= (ProgressBar)findViewById(R.id.web_progress);

        initPullButton();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.webnavigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_web);


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        webView = (WebView) findViewById(R.id.webView);

        if(SingleTon.getInstance().getWebViewState()==null){


            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setGeolocationEnabled(true);
            webView.getSettings().setSupportMultipleWindows(true); // This forces ChromeClient enabled.


            //webView.getSettings().;
            webView.setWebChromeClient(new ChromeCallback());  //HERE IS THE MAIN CHANGE
            webView.setWebViewClient(new Callback() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return false;
                }
            });
            String url = MyGlobal.serverBaseUrlMobile;

       /* AAWebViewModel vm=new AAWebViewModel();
        Gson gson=new Gson();
        String json=gson.toJson(vm);*/

            String token="token="+SingleTon.getInstance().getToken();

            webView.postUrl(url,EncodingUtils.getBytes(token, "UTF-8"));
        }else{

            webView.restoreState(SingleTon.getInstance().getWebViewState());
            progressBar.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        webView.saveState(bundle);
        SingleTon.getInstance().setWebViewState(bundle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        progressBar.setVisibility(View.VISIBLE);

        if (id == R.id.nav_gps_locations) {

            final Location[] location = {null};

            GPSClockService gpsService=new GPSClockService(WebActivity.this,this);
            try {
                 gpsService.clockInAttempt(new CallbackListener() {
                    @Override
                    public void OnResult(Object o) {
                        location[0] = (Location) o;
                        Toast.makeText(getApplicationContext(), "با موفقیت دیتکت شد", Toast.LENGTH_LONG).show();
                        SingleTon.getInstance().setLocation(location[0]);
                        String url=MyGlobal.serverBase + "/Mobile/Workplaces/UserWorkplaceInMapByToken"+"?lat="+location[0].getLatitude()+"&lng="+location[0].getLongitude()+"&token="+SingleTon.getInstance().getToken();
                        webView.loadUrl(url);
                        //finish();
                    }

                    @Override
                    public void OnError(String msg) {
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
                        Intent intent = new Intent();
                        intent.putExtra("error", msg);
                        setResult(Activity.RESULT_CANCELED, intent);
                        finish();
                        return;
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("خطا","مکان یابی توسط دستگاه انجام نشد لذا مکان شما در نقشه نمایش داده نخواهد شد",WebActivity.this);
                webView.loadUrl(MyGlobal.serverBase + "/Mobile/Workplaces/GetDataTable");
            }

            // Handle the camera action
        } else if (id == R.id.nav_personnel_username_password) {
            webView.loadUrl(MyGlobal.serverBase + "/Mobile/workplacePersonnel/GetDataTable");

        } else if (id == R.id.nav_punch_forget) {
            webView.loadUrl(MyGlobal.serverBase + "/Absence/ManualAttendance/GetDataTable");

        } else if (id == R.id.nav_worked_hours) {
            webView.loadUrl(MyGlobal.serverBase + "/Absence/PersonnelTaradodInfo/Index");

        } else if (id == R.id.nav_worked_hours_details) {
            webView.loadUrl(MyGlobal.serverBase + "/Absence/PersonnelTaradodInfo/GetAllPersonnelTotalSummaryTimesheetView");

        } else if (id == R.id.nav_obligated_range) {
            webView.loadUrl(MyGlobal.serverBase + "/Absence/ObligatedRanges/GetDataTable");
        } else if (id == R.id.nav_personnel) {
            webView.loadUrl(MyGlobal.serverBase + "/Absence/Personnel/GetDataTable");
        } else if (id == R.id.nav_workgroup) {
            webView.loadUrl(MyGlobal.serverBase + "/Absence/WorkGroup/GetDataTable");
        } else if (id == R.id.nav_obligated_range_workgroup) {
            webView.loadUrl(MyGlobal.serverBase + "/Absence/WorkGroupObligatedRange/GetDataTable");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setVisiblity(){
        progressBar.setVisibility(View.VISIBLE);

    }

    class ChromeCallback extends WebChromeClient {  //HERE IS THE MAIN CHANGE.
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }
        //@Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }


        public void onPageFinished(WebView view, String url) {
            // do your stuff here
            progressBar.setVisibility(View.INVISIBLE);
        }

    }


    class Callback extends WebViewClient {  //HERE IS THE MAIN CHANGE.
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }


        public void onPageFinished(WebView view, String url) {
            // do your stuff here
            progressBar.setVisibility(View.INVISIBLE);
        }

    }
}
