

package vnu.uet.boatsafe.data;


import android.content.Context;
import javax.inject.Inject;
import javax.inject.Singleton;
import vnu.uet.boatsafe.data.prefs.Preference;
import vnu.uet.boatsafe.di.ApplicationContext;

@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = AppDataManager.class.getSimpleName();

    private final Context mContext;
    private final Preference mPreferencesHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          Preference preferencesHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
    }

    @Override
    public void setExpire(boolean expire) {
        mPreferencesHelper.setExpire(expire);
    }

    @Override
    public boolean getExpire() {
        return mPreferencesHelper.getExpire();
    }

    @Override
    public void setRunningState(boolean runningState) {
        mPreferencesHelper.setRunningState(runningState);
    }

    @Override
    public boolean getRunningState() {
        return mPreferencesHelper.getRunningState();
    }

    @Override
    public void setLastLat(double lastLat) {
        mPreferencesHelper.setLastLat(lastLat);
    }

    @Override
    public double getLastLat() {
        return mPreferencesHelper.getLastLat();
    }

    @Override
    public void setLastLong(double lastLong) {
        mPreferencesHelper.setLastLong(lastLong);
    }

    @Override
    public double getLastLong() {
        return mPreferencesHelper.getLastLong();
    }

    @Override
    public void setLanguageState(int state) {
        mPreferencesHelper.setLanguageState(state);
    }

    @Override
    public int getLanguageState() {
        return mPreferencesHelper.getLanguageState();
    }
}
