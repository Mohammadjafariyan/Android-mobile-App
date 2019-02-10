package service;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.OutputStream;

import clock.aut.SingleTon;
import mock.MockServer;
import service.base.MyGlobal;
import service.models.BaseViewModel;
import service.models.ObjectPostViewModel;
import service.models.VoidResultViewModel;

public abstract class BaseRepository {
    protected String baseurl = MyGlobal.serverBaseUrl;

    public static final boolean isMockServerEnabled = false;
    private MockServer mockServer = new MockServer();


    public void callurl(String url, ObjectPostViewModel o) throws Exception {
        String res = post(url, o);

        Gson gson = new Gson();
        VoidResultViewModel vm = gson.fromJson(res, VoidResultViewModel.class);

        if (!vm.isSuccess()) {
            throw new Exception(vm.getMessage());
        }
    }

    protected String post(String url, Object o) throws Exception {

        if (isMockServerEnabled) {

            return mockServer.dispach(url, o);
        }

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        String token = null;
        StringEntity params = null;
        if (o != null) {
            Gson gson = new Gson();
            String json = gson.toJson(o);
            params = new StringEntity(json);
            token = ((BaseViewModel) o).getToken();
        }

        if (params != null)
            httppost.setEntity(params);


        httppost.addHeader("Content-Type", " application/json; charset=utf-8");
        httppost.addHeader("token", token);
        Log.d("http client post set", url);


        HttpResponse response = httpclient.execute(httppost);
        Log.d("YourAsync", "Executed");


        Header[] headers = response.getHeaders("token");
        if (headers.length > 1) {
            findAndSetTokenInHeaders(headers);
        }

        if (response.getStatusLine().getStatusCode() == 200) {
            return EntityUtils.toString(response.getEntity());

        } else {
            throw new Exception(response.getStatusLine().getReasonPhrase());
        }

    }

    private void findAndSetTokenInHeaders(Header[] headers) {

        Header tokenHeader = null;
        for (Header h : headers) {
            if (h.getName().equals("token"))
                tokenHeader = h;
        }

        if (tokenHeader != null) {
            SingleTon.getInstance().setToken(tokenHeader.getValue());
        }

    }
}
