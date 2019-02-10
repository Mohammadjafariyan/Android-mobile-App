package service.models;

import java.util.List;

public class OfficeLocationViewModel  extends  BaseViewModel{
    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    private  double lng;
    private  double lat;
    private String name;
    private int id;

    public OfficeLocationViewModel(String res) {

    }

    public OfficeLocationViewModel() {

    }

    public OfficeLocationViewModel(String name, double lat, double lng) {
        this.name=name;
        this.lat=lat;
        this.lng=lng;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OfficeLocationViewModel> parseJson(String res) {
        return null;
    }
}


