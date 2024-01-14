package service;

import com.google.gson.Gson;

import service.models.LoginViewModel;
import service.models.LoginViewModelResult;

public class LoginRepository extends BaseRepository {


    private String loginUrl = "login/login";
    private String logoutUrl = "Login/Logout";

    public LoginRepository(String url) {
        this.baseurl = url;
    }


    private EncriptorService encriptorService = new EncriptorService();


    public LoginViewModelResult Login(String userName, String password) throws Exception {

        if (userName == null || userName == ""
                || password == null || password == "")
            throw new Exception("مقادیر ورودی اشتباه است ");


        LoginViewModel vm = new LoginViewModel(userName, password);
        Gson gson = new Gson();
        String json = gson.toJson(vm);

        String enc = encriptorService.encryptAndEncode(json);

        LoginViewModel forsend = new LoginViewModel();
        forsend.setEncoded(enc);

        String res = post(this.baseurl + "/" + loginUrl, forsend);

        LoginViewModelResult model;
        try {
            model= gson.fromJson(res, LoginViewModelResult.class);
        } catch (Exception e) {
            throw new Exception("اطلاعات بازگشتی از سرور اشتباه است و در تبدیل دیتا خطا بوجود آمد");
        }



        if(!model.isSuccess()){
            throw new Exception(model.getMessage());
        }

        return model;


    }


    public LoginViewModelResult Logout() throws Exception {


        Gson gson = new Gson();
        LoginViewModel forsend = new LoginViewModel();

        String res = post(this.baseurl + "/" + logoutUrl, forsend);

        LoginViewModelResult model;

        try {
            model= gson.fromJson(res, LoginViewModelResult.class);
        } catch (Exception e) {
            throw new Exception("اطلاعات بازگشتی از سرور اشتباه است و در تبدیل دیتا خطا بوجود آمد");
        }

        if(!model.isSuccess()){
            throw new Exception(model.getMessage());
        }

        return model;

    }


}
