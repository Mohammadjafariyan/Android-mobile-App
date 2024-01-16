package service.base;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import service.models.UserClockTypeViewModel;

public class MyGlobal {
   // public final static String serverBaseUrlApi = "http://localhost:3000";
   public final static String serverBaseUrlApi = "http://10.0.2.2:9000/api";
    public final static String serverBaseUrlMobile = "http://10.0.2.2:9000/mobile/web/index";
    public final static String serverBase = "http://10.0.2.2:9000";

    public static void sort(List<UserClockTypeViewModel> userClockTypes) {

        Collections.sort(userClockTypes, new Comparator<UserClockTypeViewModel>() {
            @Override
            public int compare(UserClockTypeViewModel o1, UserClockTypeViewModel o2) {
                if (o1.getOrder() > o2.getOrder())
                    return 1;
                else if (o1.getOrder() < o2.getOrder())
                    return -1;
                else
                    return 0;
            }
        });

    }
}


