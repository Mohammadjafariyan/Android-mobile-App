/*
package service.Tests;

import com.google.gson.Gson;

import org.junit.Test;

import service.EncriptorService;
import service.models.LoginViewModel;

import static org.junit.Assert.*;
public class EncriptorServiceTests {

    @Test
    public void modeltext() throws Exception {
        EncriptorService d=new EncriptorService();

        LoginViewModel vm=new LoginViewModel("admin","admin");
        Gson gson=new Gson();
        String json=gson.toJson(vm);



        String enc=  d.encryptAndEncode(json);
        assert  d.decodeAndDecrypt(enc).equals(json);
    }


    @Test
    public void inovokeTest() throws Exception {
        EncriptorService d=new EncriptorService();

      String admin=  d.encryptAndEncode("admin");
      assert  d.decodeAndDecrypt(admin).equals("admin");
    }

}
*/
