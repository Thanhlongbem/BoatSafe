package vnu.uet.boatsafe.models;

public class Location {
    private String time;
    private double lat;
    private double lng;

    public Location(){

    }

    public Location(String Time,double Lat, double Lng){
        time = Time;
        lat = Lat;
        lng = Lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
