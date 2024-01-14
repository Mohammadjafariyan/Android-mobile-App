// SplashActivity.java
package start;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;


import com.example.moham.testandroidapp.LoginActivity;
import com.example.moham.testandroidapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Assuming your font file is in the "assets" folder and named "custom_font.ttf"
            //  String fontPath = "fonts/iranyekanwebregular.ttf";
    /*    String fontPath = "fonts/iranyekanwebregular.ttf";
        Typeface customTypeface = Typeface.createFromAsset(getAssets(), fontPath);

        // Find the TextView you want to apply the custom font to
        TextView textView = findViewById(R.id.splashTitle);


        textView.setTypeface(customTypeface);
*/


        // Optional: Make the status bar transparent
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        // Delay for a few seconds and then start the main activity
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }, 3000); // Adjust the delay as needed
    }
}
