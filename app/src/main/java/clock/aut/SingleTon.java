package clock.aut;

import service.models.SharedData;

public class SingleTon {

    private static SharedData mInstance;

    public static synchronized SharedData getInstance() {
        if (null == mInstance) {
            mInstance = new SharedData();
        }
        return mInstance;
    }
}
