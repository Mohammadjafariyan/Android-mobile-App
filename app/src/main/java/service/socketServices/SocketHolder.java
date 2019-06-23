package service.socketServices;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class SocketHolder {
    private static Socket socket;

    public static Socket getSocket() throws URISyntaxException {
        if(socket==null){
            socket = IO.socket("http://localhost");
        }
        return socket;
    }

    public static void setSocket(Socket socket) {
        SocketHolder.socket = socket;
    }



}
