package service.models;

import android.widget.ImageView;

public class ObjectPostViewModel extends BaseViewModel {
    private Object obj;

    public ObjectPostViewModel(Object o) {
        this.obj=o;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
