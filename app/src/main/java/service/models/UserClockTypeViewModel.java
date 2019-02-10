package service.models;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import service.base.ClockType;

public class UserClockTypeViewModel  extends  BaseViewModel{
    private int type;
    private ClockType clockType;
    private int order;

    public UserClockTypeViewModel(String res) {

    }

    public UserClockTypeViewModel() {

    }

    public List<UserClockTypeViewModel> parseJson(String res)  {
        Gson g = new Gson();
        UserClockTypeViewModel[] arr= g.fromJson(res,UserClockTypeViewModel[].class);

        return Arrays.asList(arr);
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ClockType getClockType() {
        return clockType;
    }

    public void setClockType(ClockType clockType) {
        this.clockType = clockType;
    }
}
