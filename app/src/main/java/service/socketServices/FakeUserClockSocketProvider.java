package service.socketServices;

import com.google.gson.Gson;

public class FakeUserClockSocketProvider {


    public String getText() {

        MySocketText model = new MySocketText();

        model.setObject("15/35 نفر تاکنون ورود زده اند");
        model.setType(MySocketType.UserClock);

        Gson gson=new Gson();
        return  gson.toJson(model);

    }


}
