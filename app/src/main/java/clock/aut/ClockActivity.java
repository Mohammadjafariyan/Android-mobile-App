package clock.aut;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moham.testandroidapp.R;
import com.github.nkzawa.socketio.client.Socket;
import com.instacart.library.truetime.TrueTime;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.List;

import base.BaseActivity;
import config.setting.SettingActivity;
import service.ClockRepository;
import service.base.ClockType;
import service.base.MyGlobal;
import service.models.ClockInViewModelResult;
import service.models.UserClockTypeViewModel;
import service.other.SocketCommunication;

public class ClockActivity extends BaseActivity {

    private static final int GPS_Activity = 1;
    private static final int Camera_Activity = 2;
    private static final int Wifi_Activity = 3;
    private static final int QRCode_Activity = 4;
    private static final int Setting_Activity = 5;
    private static final int Web_Activity = 7;
    private static final int Dashboard_Activity = 6;
    private TextView mTextMessage;
    private TextView timeTextMessage;
    private Button button;

    String time_not_update_msg = "سرویس ساعت جهانی آپدیت نشد بنابراین امکان استفاده از سیستم با ساعت اشتباه وجود ندارد";

    private SocketCommunication socketCommunication;

    private ClockRepository clockRepository = new ClockRepository();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    //  mTextMessage.setText(R.string.title_dashboard);
                    intent = new Intent(ClockActivity.this
                            , DashboardActivity.class);
                    startActivityForResult(intent, Dashboard_Activity);

                    return true;
                case R.id.navigation_notifications:

                    //  mTextMessage.setText(R.string.title_notifications);
                    intent = new Intent(ClockActivity.this
                            , SettingActivity.class);
                    startActivityForResult(intent, Setting_Activity);
                    return true;
                case R.id.navigation_web:

                    //  mTextMessage.setText(R.string.title_notifications);
                    intent = new Intent(ClockActivity.this
                            , WebActivity.class);
                    startActivityForResult(intent, Web_Activity);
                    return true;


            }
            return false;
        }
    };
    private View mProgressView;
    private ImageView takenPicture;
    private MyVectorClock vectorAnalogClock;
    private TextView msgText;
    private Button refresh_clock;


    @Override
    protected void onDestroy() {
        super.onDestroy();

        /*if (socketCommunication != null) {
            try {
                socketCommunication.disconnect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        setTitle("ثبت ساعت");

        msgText = (TextView) findViewById(R.id.MsgText);
        refresh_clock = (Button) findViewById(R.id.refresh_clock);
        refresh_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime();
            }
        });





        // fragment = (View) findViewById(R.id.fragment);


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);

        setCurrentTime(false);

        initButton();

        // initSocketCommunication();

        mProgressView = findViewById(R.id.clock_progress);


        fromService = getIntent().getBooleanExtra("fromService", false);
        if (fromService) {
            clockOnclickHelper();
        }
        showTimeUpdateMessage(SingleTon.getInstance().getTimeUpdateTask().isInitialized());

    }

    private void updateTime() {
        TimeUpdateTask task = new TimeUpdateTask();
        task.execute((Void) null);
    }


    private void initSocketCommunication() {


        if (SingleTon.getInstance().getNotificationsEnabled()) {

            if (!haveNetworkConnection(ClockActivity.this, "به اینترنت متصل نیستید و امکان روشن کردن اگاه سازی ها وجود ندارد")) {
                return;
            }

            socketCommunication = new SocketCommunication();

            try {
                socketCommunication.init();
                socketCommunication.connect();
                socketCommunication.listen(Socket.EVENT_MESSAGE, null);

            } catch (URISyntaxException e) {
                e.printStackTrace();
                Toast.makeText(ClockActivity.this, "اتصال سوکت جهت آگاه سازی ها انجام نشد", Toast.LENGTH_LONG).show();
            }


        }
    }

    private void initButton() {
        button = (Button) findViewById(R.id.email_sign_in_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clockOnclickHelper();
            }
        });


    }

    private void clockOnclickHelper() {

        if (!SingleTon.getInstance().isAdmin()) {
            // اگر ادمین نیست و از یک دستگاه فعالا ست
            if (SingleTon.getInstance().getOneDeviceEnabled()) {
                Toast.makeText(ClockActivity.this, "استفاده از یک دستگاه فعال است و فقط میتوانید از دستگاه ادمین ، ساعت زنی نمایید ", Toast.LENGTH_LONG).show();
                return;
            }
        }


        if (!SingleTon.getInstance().getLoggedIn()) {
            mProgressView.setVisibility(View.VISIBLE);
            runNext(0, Activity.RESULT_OK, SingleTon.getInstance().getOneDeviceEnabled());
        } else {
            clockOut();
        }
    }


    public void runNext(int mycode, int resultCode, boolean isOneDevice) {
        UserClockTypeViewModel selected = null;

        Class activityClass = null;
        if (isOneDevice) {
            selected = new UserClockTypeViewModel();
            selected.setClockType(ClockType.QRCode);
            selected.setType(ClockType.QRCode.ordinal());
        } else {
            List<UserClockTypeViewModel> userClockTypes = SingleTon.getInstance().getUserClockTypes();
            MyGlobal.sort(userClockTypes);


            if (mycode >= userClockTypes.size()) {
                return;
            }


            if (mycode == 0) {
                selected = userClockTypes.get(0);
            } else {
                // GET PREVIEWS
                selected = userClockTypes.get(mycode);
            }

            mycode++;


            if (selected == null) {
                return;
            }
        }


        selected.setClockType(ClockType.values()[selected.getType()]);

        switch (selected.getClockType()) {
            case GPS:
                activityClass = GPSActivity.class;
                break;
            case Wifi:
                activityClass = WifiActivity.class;
                break;
            case CameraSelfie:
                activityClass = CameraActivity.class;
                break;
            case QRCode:
                activityClass = QRCodeActivity.class;
                break;
            default:
                try {
                    throw new Exception("هیچ نوع اکتیویتی یافت نشد");
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("خطا", e.getMessage(), ClockActivity.this);


                    return;
                }
        }


        if (resultCode == Activity.RESULT_OK) {

            Intent intent = new Intent(ClockActivity.this
                    , activityClass);
            startActivityForResult(intent, mycode);
        } else {
            //   showAlert("خطا", "مرحله قبلی موفق نبود لطفا دوباره تلاش نمایید");
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        backPressCounter = 0;
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_home);

        if (resultCode == Activity.RESULT_CANCELED && data != null && data.getExtras().containsKey("error")) {
            showAlert("خطا", data.getExtras().get("error").toString(), ClockActivity.this);
        }


        if (requestCode == Setting_Activity) {
            return;
        }

        if (SingleTon.getInstance().getOneDeviceEnabled()) {
            clockIn();
        } else {
            runNext(requestCode, resultCode, SingleTon.getInstance().getOneDeviceEnabled());
            clockIn();
        }


      /*  if (hasCameraActivity() && requestCode == GPS_Activity && resultCode == Activity.RESULT_OK) {
            Intent intent = new Intent(ClockActivity.this
                    , CameraActivity.class);
            startActivityForResult(intent, Camera_Activity);
        }
        if (hasWifiActivity() && requestCode == Camera_Activity && resultCode == Activity.RESULT_OK) {
            Intent intent = new Intent(ClockActivity.this
                    , WifiActivity.class);
            startActivityForResult(intent, Wifi_Activity);
        }

        if (hasQRCodeActivity() && requestCode == Wifi_Activity && resultCode == Activity.RESULT_OK) {
            Intent intent = new Intent(ClockActivity.this
                    , QRCodeActivity.class);
            startActivityForResult(intent, QRCode_Activity);
        }*/


    }

    private boolean hasQRCodeActivity() {
        return false;
    }

    private boolean hasWifiActivity() {
        return false;
    }

    private boolean hasCameraActivity() {
        return false;
    }

    public void clockOut() {

        ClockInTask clockInTask = new ClockInTask();
        clockInTask.execute((Void) null);
       /* ClockInViewModelResult clock = null;
        try {
            clock = clockRepository.ClockOut();

            timeTextMessage = (TextView) findViewById(R.id.textView5);
            timeTextMessage.setText(clock.getMessage());
            timeTextMessage.setTextColor(Color.BLACK);

        } catch (Exception e) {
            e.printStackTrace();
            timeTextMessage = (TextView) findViewById(R.id.textView5);
            timeTextMessage.setText(e.getMessage());
            timeTextMessage.setTextColor(Color.RED);
        }*/
    }

    public void clockIn() {
        if (SingleTon.getInstance().isClockUpdate()) {
            ClockInTask clockInTask = new ClockInTask();
            clockInTask.execute((Void) null);
        } else {
            Toast.makeText(ClockActivity.this, time_not_update_msg, Toast.LENGTH_LONG).show();
        }
    }

    public void clockInPost() {

        if (SingleTon.getInstance().getLoggedIn() == false) {
            // login
            if (SingleTon.getInstance().isOk() == false) {
//not ok
                return;
            }
        }

        ClockInViewModelResult clock = null;
        try {
            clock = clockRepository.ClockIn();

            SingleTon.getInstance().setMessage(clock.getMessage());
            SingleTon.getInstance().setSuccess(clock.isSuccess());
            SingleTon.getInstance().setClockLastMessage(clock.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            SingleTon.getInstance().setMessage(e.getMessage());
        }

    }

    private void setCurrentTime(boolean setColor) {
// init time :
        timeTextMessage = (TextView) findViewById(R.id.textView5);


        if (SingleTon.getInstance().getClockLastMessage() == null) {
            // String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            timeTextMessage.setText("-");

            if (setColor) {
                timeTextMessage.setTextColor(Color.GREEN);
            }
        } else {
            if (SingleTon.getInstance().getSuccess()) {
                timeTextMessage.setText(SingleTon.getInstance().getClockLastMessage());
                timeTextMessage.setTextColor(Color.GREEN);
            } else {
            }
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private int backPressCounter = 0;

    @Override
    public void onBackPressed() {

        if (backPressCounter >= 1) {
            backPressCounter = 0;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            backPressCounter++;
            Toast.makeText(ClockActivity.this, "برای خروج دوباره دگمه back را لمس نمایید", Toast.LENGTH_SHORT).show();
        }

    }

    public void showTimeUpdateMessage(boolean isInitialized) {


        if (isInitialized == false) {
            if (vectorAnalogClock != null)
                vectorAnalogClock.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
            Toast.makeText(ClockActivity.this, time_not_update_msg, Toast.LENGTH_LONG).show();
            msgText.setTextColor(Color.RED);
            msgText.setText(time_not_update_msg);
            msgText.setTextSize(18);
            refresh_clock.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.VISIBLE);

            refresh_clock.setVisibility(View.GONE);
            setAnalogueClock();
            vectorAnalogClock.setVisibility(View.VISIBLE);

        }

    }


    class ClockInTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mProgressView.setVisibility(View.GONE);

            if (aBoolean) {
                timeTextMessage = (TextView) findViewById(R.id.textView5);

                if (SingleTon.getInstance().getSuccess()) {
                    timeTextMessage.setText(SingleTon.getInstance().getMessage());
                    timeTextMessage.setTextColor(Color.GREEN);


                   // boolean isloggedIn = SingleTon.getInstance().getLoggedIn();
                    SingleTon.getInstance().setLoggedIn(!SingleTon.getInstance().getLoggedIn());
                    ringtone();

                    //    if (isloggedIn)
                    setTakenPicture();

                } else {
                    ringtone();
                    Toast.makeText(ClockActivity.this, SingleTon.getInstance().getMessage(), Toast.LENGTH_SHORT).show();
                }


            } else {

                Handler handler = new Handler(ClockActivity.this.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(ClockActivity.this, "برای خروج دوباره دگمه back را لمس نمایید", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                clockInPost();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }


            return true;
        }
    }

    private void setAnalogueClock() {
        vectorAnalogClock = findViewById(R.id.clock);

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(TrueTime.now());
        // calendar.add(Calendar.HOUR,-2);
        //customization
        vectorAnalogClock.setCalendar(calendar)
                .setDiameterInDp(400.0f)
                .setOpacity(1.0f)
                .setShowSeconds(true)
                .setColor(Color.BLUE);
    }

    private void setTakenPicture() {

        if (SingleTon.getInstance().getImageView() == null) {
            return;
        }

        vectorAnalogClock.setVisibility(View.GONE);

        takenPicture = (ImageView) findViewById(R.id.takenImage);


        Bitmap image = ((BitmapDrawable) SingleTon.getInstance().getImageView().getDrawable()).getBitmap();

        takenPicture.setImageBitmap(image);
        takenPicture.setVisibility(View.VISIBLE);
        takenPicture.setMaxHeight(500);
    }

}

