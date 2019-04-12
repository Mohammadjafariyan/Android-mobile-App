package service;

import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

public class WebSocketListener extends okhttp3.WebSocketListener {

    private static final int NORMAL_CLOSURE_STATUS = 1000;
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
       // webSocket.send("Hello, it's SSaurel !");
      //  webSocket.send("What's up ?");
     //   webSocket.send(ByteString.decodeHex("deadbeef"));
       // webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
    }
    @Override
    public void onMessage(WebSocket webSocket, String text) {
    }
    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
    }
    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
    }
    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
    }
}
   /* public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }*/