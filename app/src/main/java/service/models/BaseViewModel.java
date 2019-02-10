package service.models;

import clock.aut.SingleTon;

public abstract class BaseViewModel {

    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String token = SingleTon.getInstance().getToken();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
