package mock;

import android.graphics.Color;

import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;

import service.base.ClockType;
import service.models.ClockInViewModelResult;
import service.models.LoginViewModel;
import service.models.LoginViewModelResult;
import service.models.OfficeLocationViewModel;
import service.models.PersonnelClockStatusViewModel;
import service.models.UserClockTypeViewModel;
import service.models.VoidResultViewModel;

public class MockServer {


    public String dispach(String url, Object posted) {
        switch (url) {
            case "http://localhost:3000/login":
                LoginViewModel d = null;
                if (posted != null) {
                    d = (LoginViewModel) posted;
                }
                return GetLogin(d);
            case "http://localhost:3000/clockIn/1":
                return GetClockIn();
            case "http://localhost:3000/clockOut/1":
                break;
            case "http://localhost:3000/ClockTypeList":
                return getClockTypeList();
            case "http://localhost:3000/offices":
                return getOfficess();
            case "http://localhost:3000/savePersonImageUrl":
                return getVoidSuccessRsult();
            case "http://localhost:3000/saveSelectedWifiUrl":
                return getVoidSuccessRsult();
            case "http://localhost:3000/saveIsOneDeviceEnabledUrl":
                return getVoidSuccessRsult();
            case "http://localhost:3000/saveFaceRecognationUrl":
                return getVoidSuccessRsult();
            case "http://localhost:3000/saveNotificationsEnabledUrl":
                return getVoidFailtRsult();
            case "http://localhost:3000/PersonnelClockStatusByDateUrl":
                int i = 0;
                if (posted != null) {
                    i = (int) posted;
                }
                return getPersonnelItems(i);


        }

        return null;
    }

    private String getPersonnelItems(int i) {
        List<PersonnelClockStatusViewModel> vms=new LinkedList<>();

        vms.add(new PersonnelClockStatusViewModel
                ("محمد جعفریان","08:01:01","-", "وارد شده"  , Color.GREEN));
        vms.add(new PersonnelClockStatusViewModel("علی خوشزبان","08:31:01","-", "وارد شده" , Color.GREEN));
        vms.add(new PersonnelClockStatusViewModel("حسین علی اقدم","-","-",  "غایب"  , Color.RED));
        vms.add(new PersonnelClockStatusViewModel("اصغر علی بهزادی","09:01:04","10:20:30","وارد شده"  , Color.GREEN));
        vms.add(new PersonnelClockStatusViewModel("محمد علیزاده","08:01:50","-","خارج شده"  , Color.BLUE));

        Gson gson = new Gson();
        String json = gson.toJson(vms);

        return json;

    }

    private String getVoidSuccessRsult() {
        VoidResultViewModel vm = new VoidResultViewModel();
        vm.setSuccess(true);
        vm.setMessage("با موفقیت");

        Gson gson = new Gson();
        String json = gson.toJson(vm);

        return json;
    }

    private String getVoidFailtRsult() {
        VoidResultViewModel vm = new VoidResultViewModel();
        vm.setSuccess(false);
        vm.setMessage("در ثبت مشکلی بوجود آمد");

        Gson gson = new Gson();
        String json = gson.toJson(vm);

        return json;
    }

    private String getOfficess() {

        List<OfficeLocationViewModel> modelList = new LinkedList<>();

        modelList.add(new OfficeLocationViewModel("مرکزی", 36.515, 31.6515));
        modelList.add(new OfficeLocationViewModel("وابسته 1", 36.5915, 11.6515));
        modelList.add(new OfficeLocationViewModel("وابسته 2", 36.715, 61.6515));
        modelList.add(new OfficeLocationViewModel("دفتر ایروان", 46.515, 41.6515));
        modelList.add(new OfficeLocationViewModel("دفتر فروش", 26.515, 21.6515));
        modelList.add(new OfficeLocationViewModel("تولید", 34.515, 33.6515));

        Gson gson = new Gson();
        String json = gson.toJson(modelList);

        return json;
    }

    private String getClockTypeList() {
        UserClockTypeViewModel m = new UserClockTypeViewModel();
        m.setClockType(ClockType.GPS);
        m.setType(0);

        UserClockTypeViewModel m2 = new UserClockTypeViewModel();
        m2.setClockType(ClockType.CameraSelfie);
        m2.setType(1);

        UserClockTypeViewModel m3 = new UserClockTypeViewModel();
        m3.setClockType(ClockType.Wifi);
        m3.setType(3);

        UserClockTypeViewModel m4 = new UserClockTypeViewModel();
        m4.setClockType(ClockType.QRCode);
        m4.setType(2);


        List<UserClockTypeViewModel> models = new LinkedList<>();
        models.add(m);
        models.add(m2);
        models.add(m3);
        models.add(m4);


        Gson gson = new Gson();
        String json = gson.toJson(models);

        return json;
    }


    private String GetClockIn() {


        ClockInViewModelResult c = new ClockInViewModelResult();
        c.setMessage("با موفقیت در ساعت 3:45:50 وارد شدید");


        Gson gson = new Gson();
        String json = gson.toJson(c);

        return json;
    }

    private String GetLogin(LoginViewModel o) {

        LoginViewModelResult vm = new LoginViewModelResult();
        if (o.getUsername().equals("admin") && o.getPassword().equals("admin")) {
            vm.setSuccess(true);
            vm.setToken("slfjlkwenvjwioewiwjeoiwjf65bdf651g65d1f65g1f6d5");
            vm.setAdmin(true);
        } else if (o.getUsername().equals("user") && o.getPassword().equals("user")) {
            vm.setToken("slfjlkwenvjwioewiwjeoiwjf65bdf651g65d1f65g1f6d5");
            vm.setSuccess(true);
        } else if (o.getUsername().equals("null") && o.getPassword().equals("null")) {
            return null;
        } else {
            vm.setSuccess(false);
        }


        vm.setMessage("");

        Gson gson = new Gson();
        String json = gson.toJson(vm);

        return json;
    }

}
