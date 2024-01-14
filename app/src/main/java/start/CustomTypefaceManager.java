package start;

import android.graphics.Typeface;

public class CustomTypefaceManager {

    private static CustomTypefaceManager instance;
    private Typeface customTypeface;

    private CustomTypefaceManager() {
        // private constructor to prevent instantiation
    }

    public static synchronized CustomTypefaceManager getInstance() {
        if (instance == null) {
            instance = new CustomTypefaceManager();
        }
        return instance;
    }

    public void setCustomTypeface(Typeface customTypeface) {
        this.customTypeface = customTypeface;
    }

    public Typeface getCustomTypeface() {
        return customTypeface;
    }
}
