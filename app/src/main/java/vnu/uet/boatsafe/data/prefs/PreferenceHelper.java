package vnu.uet.boatsafe.data.prefs;


public interface PreferenceHelper {

    void setExpire(boolean expire);

    boolean getExpire();

    void setRunningState(boolean runningState);

    boolean getRunningState();

    void setLastLat(double lastLat);

    double getLastLat();

    void setLastLong(double lastLong);

    double getLastLong();

    void setLanguageState(int state);

    int getLanguageState();
}
