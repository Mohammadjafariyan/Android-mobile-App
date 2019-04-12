package service;

import android.location.Location;
import android.widget.Toast;

import clock.aut.SingleTon;

public interface CallbackListener {

     void OnResult(Object o);

     void OnError(String msg);
}
