package service.base;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import service.models.UserClockTypeViewModel;

public class MyGlobal {
   // public final static String serverBaseUrl = "http://localhost:3000";
    public final static String serverBaseUrl = "http://demo.sobhansystems.ir/api/";

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


