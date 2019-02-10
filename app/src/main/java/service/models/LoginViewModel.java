package service.models;

public class LoginViewModel extends  BaseViewModel {

    private String username;
    private String password;
    private String encoded;

    public LoginViewModel() {
    }

    public LoginViewModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEncoded(String encoded) {
        this.encoded = encoded;
    }

    public String getEncoded() {
        return encoded;
    }
}
