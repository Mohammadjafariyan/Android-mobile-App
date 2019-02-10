package service.models;

public class PersonnelClockStatusViewModel extends BaseViewModel {
    private long id;
    private long personnelId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(long personnelId) {
        this.personnelId = personnelId;
    }

    private String name;
    private String lastClockIn;
    private String lastClockOut;
    private String status;
    private int color;
    private String imageUrl;

    public PersonnelClockStatusViewModel(String name, String lastClockIn, String lastClockOut, String status
    , int color) {
        this.name = name;
        this.lastClockIn = lastClockIn;
        this.lastClockOut = lastClockOut;
        this.status = status;
this.color=color;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastClockIn() {
        return lastClockIn;
    }

    public void setLastClockIn(String lastClockIn) {
        this.lastClockIn = lastClockIn;
    }

    public String getLastClockOut() {
        return lastClockOut;
    }

    public void setLastClockOut(String lastClockOut) {
        this.lastClockOut = lastClockOut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
