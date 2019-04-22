package vnu.uet.boatsafe.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import vnu.uet.boatsafe.di.ApplicationContext;



public class Preference implements PreferenceHelper{

    private SharedPreferences sharedPreferencesAcc;
    private SharedPreferences sharedPreferencesSetting;
    private String PREFS_ACCOUNT = "PREFS_ACCOUNT";
    private String PREFS_SETTING = "PREFS_SETTING";
    private static Preference instance;

    private String EXPIRE_APP = "EXPIRE_APP";
    private String IS_RUNNING = "IS_RUNNING";
    private String KEY_LAT = "KEY_LAT";
    private String KEY_LONG = "KEY_LONG";
    private String KEY_LANGUAGE_STATE = "KEY_LANGUAGE_STATE";

    public static Preference buildInstance(Context context){

        if (instance == null) {
            instance = new Preference(context);
        }
        return instance;
    }


    @Inject
    public Preference(@ApplicationContext Context context) {
        sharedPreferencesAcc = context.getSharedPreferences(PREFS_ACCOUNT, Context.MODE_PRIVATE);
        sharedPreferencesSetting = context.getSharedPreferences(PREFS_SETTING, Context.MODE_PRIVATE);
    }

    @Override
    public void setExpire(boolean expire) {
        sharedPreferencesSetting.edit().putBoolean(EXPIRE_APP,expire).apply();
    }

    @Override
    public boolean getExpire() {
        return sharedPreferencesSetting.getBoolean(EXPIRE_APP,false);
    }

    @Override
    public void setRunningState(boolean runningState) {
        sharedPreferencesSetting.edit().putBoolean(IS_RUNNING, runningState).apply();
    }

    @Override
    public boolean getRunningState() {
        return sharedPreferencesSetting.getBoolean(IS_RUNNING, false);
    }

    @Override
    public void setLastLat(double lastLat) {
        sharedPreferencesSetting.edit().putFloat(KEY_LAT, (float) lastLat).apply();
    }

    @Override
    public double getLastLat() {
        return sharedPreferencesSetting.getFloat(KEY_LAT, 0);
    }

    @Override
    public void setLastLong(double lastLong) {
        sharedPreferencesSetting.edit().putFloat(KEY_LONG, (float) lastLong).apply();
    }

    @Override
    public double getLastLong() {
        return sharedPreferencesSetting.getFloat(KEY_LONG, 0);
    }

    @Override
    public void setLanguageState(int state) {
        sharedPreferencesSetting.edit().putInt(KEY_LANGUAGE_STATE, 0).apply();
    }

    @Override
    public int getLanguageState() {
        return sharedPreferencesAcc.getInt(KEY_LANGUAGE_STATE, 0);
    }


}
