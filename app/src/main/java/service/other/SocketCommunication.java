package service.other;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import service.models.BaseViewModel;

public class SocketCommunication {

    public static final String socketUrl = "";

    private Socket mSocket;

    public void init() throws URISyntaxException {
        mSocket = IO.socket(socketUrl);
    }

    public void connect() throws URISyntaxException {
        mSocket.connect();
    }

    public void disconnect() throws URISyntaxException {
        mSocket.disconnect();
    }

    public void sendMessage(BaseViewModel vm) throws URISyntaxException {
        mSocket.send(vm);
    }

    public void listen(String event, Emitter.Listener listener) throws URISyntaxException {
        mSocket.on(event, listener);
    }

    public void off(String event, Emitter.Listener listener) throws URISyntaxException {
        mSocket.off(event, listener);
    }


}
