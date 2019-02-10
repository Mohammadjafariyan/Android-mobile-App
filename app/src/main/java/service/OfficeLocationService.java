package service;

import java.io.IOException;
import java.util.List;

import service.models.ObjectPostViewModel;
import service.models.OfficeLocationViewModel;

public class OfficeLocationService extends BaseRepository {
    private String officeLocationsUrl = "OfficeLocations/GetAll";

    public List<OfficeLocationViewModel> getAll() throws Exception {
        String res = post(this.baseurl + "/" + officeLocationsUrl, new ObjectPostViewModel(null));

        OfficeLocationViewModel model = new OfficeLocationViewModel();

        List<OfficeLocationViewModel> modelList = model.parseJson(res);


        return modelList;
    }
}
