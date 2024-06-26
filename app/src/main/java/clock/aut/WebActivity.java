package clock.aut;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.moham.testandroidapp.MyWebViewFragment;
import com.bulutsoft.attendance.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

import service.base.MyGlobal;

public class WebActivity extends AppCompatActivity
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
              /*  case R.id.navigation_dashboard:
                    intent = new Intent(WebActivity.this
                            , DashboardActivity.class);
                    startActivityForResult(intent, 6);
                    return true;
                case R.id.navigation_notifications:
                    //  mTextMessage.setText(R.string.title_notifications);
                    intent = new Intent(WebActivity.this
                            , SettingActivity.class);
                    startActivityForResult(intent, 7);
                    return true;*/
                case R.id.navigation_web:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_web);

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar= (ProgressBar)findViewById(R.id.web_progress);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.webnavigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_web);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
*/
/*

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new Callback());  //HERE IS THE MAIN CHANGE
        //  String url="http://demo.sobhansystems.ir/mobile/web/index";
        String url = MyGlobal.serverBaseUrlMobile;

       */
/* AAWebViewModel vm=new AAWebViewModel();
        Gson gson=new Gson();
        String json=gson.toJson(vm);*//*


       String token="token="+SingleTon.getInstance().getToken();

        webView.postUrl(url,EncodingUtils.getBytes(token, "UTF-8"));

*/

        // Get FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Create and add the fragment dynamically
        MyWebViewFragment myFragment =new  MyWebViewFragment(this,true);

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, myFragment)
                .commit();
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


        if (id == R.id.nav_gps_locations) {
            webView.loadUrl(MyGlobal.serverBase + "/Mobile/Workplaces/GetDataTable");
            // Handle the camera action
        } else if (id == R.id.nav_personnel_username_password) {
            webView.loadUrl(MyGlobal.serverBase + "/Mobile/workplacePersonnel/GetDataTable");

        } else if (id == R.id.nav_punch_forget) {
            webView.loadUrl(MyGlobal.serverBase + "/Mobile/ManualAttendance/GetDataTable");

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

      /*  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        return true;
    }

    public void setVisiblity(){

    }

    class Callback extends WebViewClient {  //HERE IS THE MAIN CHANGE.

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url,getHeadersWithJwt());
            return true;
        }

        private Map<String, String> getHeadersWithJwt() {
            Map<String, String> headers = new HashMap<>();
            String token = SingleTon.getInstance().getToken();
            headers.put("Authorization", "Bearer " + token);
            return headers;
        }

        public void onPageFinished(WebView view, String url) {
            // do your stuff here
        }

    }
}
