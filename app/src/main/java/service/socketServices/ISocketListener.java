package service.socketServices;

public interface ISocketListener {
    public String getUniqName();
    public void onReceive(Object... o);

    void setSocketService(SocketService socketService);
}
