package config.setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.moham.testandroidapp.R;

import base.BaseActivity;

public class OfficeLocationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_location);
        setTitle("مکان محل های کاری");


        //int id=(int)getIntent().getExtras().get("id");
     //   if(id!=0){
     //       readAndInit();
     //   }
        
      //  initMap();


    }

    private void initMap() {
    }

    private void readAndInit() {
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.putExtra("requestCode", requestCode);
        super.startActivityForResult(intent, requestCode);
    }
}
