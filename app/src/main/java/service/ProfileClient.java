package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import service.models.ApiResult;
import service.models.ApplicationUser;
import service.models.CustomResultType;
import service.models.LoginViewModel;
import service.models.LoginViewModelResult;

public class ProfileClient extends BaseRepository {


    private String url = "Profile/Get";

    public ProfileClient(String url) {
        this.baseurl = url;
    }


    public ApplicationUser Get() throws Exception {

        Gson gson = new Gson();

        String res = get(this.baseurl + "/" + url);

        ApiResult<ApplicationUser> model = new ApiResult<ApplicationUser>();

        Type listType = new TypeToken< ApiResult<ApplicationUser>>() {}.getType();

        try {
            model= gson.fromJson(res, listType);
        } catch (Exception e) {
            throw new Exception("اطلاعات بازگشتی از سرور اشتباه است و در تبدیل دیتا خطا بوجود آمد");
        }



        if(model.getStatus()== CustomResultType.success){
            throw new Exception(model.getMessage());
        }

        return model.getResult();


    }





}
