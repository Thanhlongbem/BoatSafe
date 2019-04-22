package vnu.uet.boatsafe.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Objects;

import vnu.uet.boatsafe.service.location.LocationService;
import vnu.uet.boatsafe.service.log.LoggerManager;
import vnu.uet.boatsafe.service.sensor.AccSensorService;

public class BoatSafeService extends Service {
    private boolean running = false;
    private AccSensorService mSensorService;





    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (running) {
            return START_STICKY;
        }
        running = true;

        initService();

        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initService(){
        LoggerManager.getInstances(this).open();
        mSensorService = new AccSensorService(this);
        mSensorService.initSensor();

    }

    @Override
    public void onDestroy(){
        mSensorService.stopSensorService();
    }




    private void startLocationService(){
        if(!isServiceRunning(LocationService.class)){
            getApplicationContext().startService(new Intent(getApplicationContext(), LocationService.class));
        }
    }

    private void stopLocationService(){
        if(isServiceRunning(LocationService.class)){
            Objects.requireNonNull(getApplicationContext()).stopService(new Intent(getApplicationContext(), LocationService.class));
        }
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getApplication().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
