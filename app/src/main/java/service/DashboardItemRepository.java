package service;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import service.models.BaseViewModel;
import service.models.ObjectPostViewModel;
import service.models.OfficeLocationViewModel;
import service.models.PersonnelClockStatusViewModel;

public class DashboardItemRepository extends BaseRepository {
    private String PersonnelClockStatusByDateUrl = "DashboardItem/GetAll";

    public PersonnelClockStatusViewModel[] getPersonnelClockStatusByDate(long i) throws Exception {

        String res = post(this.baseurl + "/" + PersonnelClockStatusByDateUrl, new ObjectPostViewModel(i));

        Gson gson = new Gson();



        PersonnelClockStatusViewModel[] list = gson.fromJson(res, PersonnelClockStatusViewModel[].class);


        return list;
    }
}
