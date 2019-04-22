package vnu.uet.boatsafe.models;

import java.util.List;

public class History {
    String date;
    String time;
    float distance;
    public List<Location> m_locationData;

    public History(String date, String time, float distance, List<Location> locationData) {
        this.date = date;
        this.time = time;
        this.distance = distance;
        m_locationData = locationData;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
