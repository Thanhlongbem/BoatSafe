package vnu.uet.boatsafe.service.location;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;
import vnu.uet.boatsafe.data.prefs.Preference;
import vnu.uet.boatsafe.models.LocationEventBus;
import vnu.uet.boatsafe.service.log.LoggerManager;

public class LocationService extends Service {
    public static final int TIME_GET_LOCATION = 5000;

    double lastLat = 0;
    double lastLong = 0;
    private Context context;
    private double speed = 0;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private boolean stateLocation = true;
    private Timer timerLoca;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LoggerManager.getInstances(this).open();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        timerLoca = new Timer();
        if(Preference.buildInstance(getApplicationContext()).getRunningState()){
            timerLoca.schedule(new TimerTask() {
                @SuppressLint({"MissingPermission", "NewApi"})
                @Override
                public void run() {
                    if(Preference.buildInstance(getApplicationContext()).getRunningState()){
                        EventBus.getDefault().postSticky(new LocationEventBus(stateLocation));
                    }else {
                        return;
                    }
                }
            }, 0, 5000);
        }else {
            if(timerLoca != null){
                timerLoca.cancel();
            }
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
