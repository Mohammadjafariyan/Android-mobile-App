package clock.aut;

import android.content.SharedPreferences;
import android.hardware.camera2.CameraCharacteristics;
import android.util.JsonReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.TypeAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import service.models.SharedData;

public class SingleTon {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    private static SharedData mInstance;

    public static synchronized SharedData getInstance() {
        if (null == mInstance) {
            mInstance = new SharedData();
        }
        return mInstance;
    }

    public static void saveInstance(SharedPreferences sharedPref) {

        Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
                .generateNonExecutableJson()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(mInstance);
        sharedPref.edit().putString("mInstance", json).commit();
    }

    public static void loadInstance(SharedPreferences sharedPref) throws Exception {

        getInstance();

        if (sharedPref.contains("mInstance")) {
            String str = sharedPref.getString("mInstance", "");


            mInstance = objectMapper.readValue(str, SharedData.class);

        } else {
            throw new Exception("mInstance is null");
        }

    }


}


