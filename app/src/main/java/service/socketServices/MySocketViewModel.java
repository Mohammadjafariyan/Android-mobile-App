package service.socketServices;

public abstract class MySocketViewModel<T> {
    private T object;
    private MySocketType type;

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public MySocketType getType() {
        return type;
    }

    public void setType(MySocketType type) {
        this.type = type;
    }
}


