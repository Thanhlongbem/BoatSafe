package vnu.uet.boatsafe.models;

import io.realm.RealmObject;

public class CurrentLocation{
    private double Lat;
    private double Long;

    public CurrentLocation(double lat, double aLong) {
        Lat = lat;
        Long = aLong;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLong() {
        return Long;
    }

    public void setLong(double aLong) {
        Long = aLong;
    }
}
