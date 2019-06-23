package service.socketServices;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SocketService {


    public List<ISocketListener> socketListeners = new LinkedList<>();


    public SocketService(ISocketListener... listeners) {
        socketListeners = Arrays.asList(listeners);
        for (int i = 0; i < socketListeners.size(); i++) {
            socketListeners.get(i).setSocketService(this);
        }
    }

    public static void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }

    public void init() throws URISyntaxException {

        setTimeout(() -> dataReseive(new String[]{"UserClockListener",new FakeUserClockSocketProvider().getText()}), 7000);

        SocketHolder.getSocket().on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {


            }

        }).on(Socket.EVENT_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String data = (String) args[0];

                dataReseive(args);

            }
        }).on(Socket.EVENT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Exception err = (Exception) args[0];
            }
        });
        SocketHolder.getSocket().connect();
    }


    private void dataReseive(Object[] args) {
        String data = (String) args[1];
        for (int i = 0; i < socketListeners.size(); i++) {
            ISocketListener listener = socketListeners.get(i);
            if (listener.getUniqName() == data)
                listener.onReceive(data);
        }
    }


}
