package service.models;

public class MyLocation {

    private float accuracy;
    private double latitude;
    private double longitude;
    private float speed;
    private long time;

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}


