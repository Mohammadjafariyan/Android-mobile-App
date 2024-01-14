package start;

import android.app.Application;
import android.graphics.Typeface;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Load your custom font
      /*  Typeface customTypeface = Typeface.createFromAsset(getAssets(), "fonts/iranyekanwebregular.ttf");

        // Apply the custom font globally
        CustomTypefaceManager.getInstance().setCustomTypeface(customTypeface);*/
    }
}

