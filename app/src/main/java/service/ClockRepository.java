package service;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Date;

import clock.aut.SingleTon;
import service.models.ClockInViewModel;
import service.models.ClockInViewModelResult;

public class ClockRepository extends BaseRepository {
    private String clockInUrl = "Clock/clockIn";
    private String clockOutUrl = "Clock/ClockOut";

    public ClockInViewModelResult ClockIn() throws Exception {

        ClockInViewModel clock = new ClockInViewModel();
        clock.setDatetime(new Date());
        clock.setImageView(SingleTon.getInstance().getImageView());
        clock.setLocation(SingleTon.getInstance().getLocation());
        clock.setqRCodeContent(SingleTon.getInstance().getqRCodeContent());
        clock.setScanResults(SingleTon.getInstance().getScanResults());

        String res = post(this.baseurl + "/" + clockInUrl,clock);

        Gson gson=new Gson();
        ClockInViewModelResult model =  gson.fromJson(res,ClockInViewModelResult.class);


        return model;

    }

    public ClockInViewModelResult ClockOut() throws Exception {

        ClockInViewModel vm=new ClockInViewModel();

        vm.setScanResults(SingleTon.getInstance().getScanResults());
        vm.setLocation(SingleTon.getInstance().getLocation());
        vm.setqRCodeContent(SingleTon.getInstance().getqRCodeContent());
        vm.setImageView(SingleTon.getInstance().getImageView());


        String res = post(this.baseurl + "/" + clockOutUrl,new ClockInViewModel());

        ClockInViewModelResult model = new ClockInViewModelResult(res);

        return model;

    }
}
