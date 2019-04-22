package vnu.uet.boatsafe.models;

public class Addr {
    private double lat;
    private double lng;
    private String addr;

    public Addr(double lat, double lng, String addr) {
        this.lat = lat;
        this.lng = lng;
        this.addr = addr;
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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
