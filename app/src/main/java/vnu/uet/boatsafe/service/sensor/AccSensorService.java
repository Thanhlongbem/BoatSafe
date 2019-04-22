package vnu.uet.boatsafe.service.sensor;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import com.kircherelectronics.fsensor.filter.averaging.LowPassFilter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import vnu.uet.boatsafe.data.prefs.Preference;
import vnu.uet.boatsafe.models.DetectFallEventBus;
import vnu.uet.boatsafe.models.LocationEventBus;
import vnu.uet.boatsafe.models.OrientationEventBus;
import vnu.uet.boatsafe.service.log.LoggerManager;
import vnu.uet.boatsafe.ui.widget.FallenDialog;

public class AccSensorService implements SensorEventListener {
    public static final double GRAVITY = 9.81;

    private Context mContext;
    private SensorManager mSensorManager;
    private Sensor mSensorAccelerometer;
    private Sensor mSensorOrientation;
    LowPassFilter lowPassFilter;


    //Orientation
    float[] inR = new float[16];
    float[] I = new float[16];
    float[] gravity = new float[3];
    float[] geomag = new float[3];
    float[] orientVals = new float[3];
    double azimuth = 0;
    double pitch = 0;
    double roll = 0;

    List<Sensor> sensor;
    private double vx;
    private double vy;
    private double vz;
    private long lastTime;
    private double timeInterval;
    private ArrayList<Float> listABuffer = new ArrayList<>();

    LowPassFilter kFilter;


    public AccSensorService (Context context) {
        this.mContext = context;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //if(event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)return;


        switch (event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                for (int i = 0; i< 3; i++){
                    gravity[i] =  event.values[i];
                }
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                for (int i = 0; i < 3; i++){
                    geomag[i] = event.values[i];
                }
                break;
        }

        if(Preference.buildInstance(mContext).getRunningState()){
            if(gravity != null && geomag != null){
                boolean success = SensorManager.getRotationMatrix(inR, I, gravity, geomag);

                SensorManager.getOrientation(inR, orientVals);
                azimuth = Math.toDegrees(orientVals[0]);
            /*pitch = Math.toDegrees(orientVals[1]);
            roll = Math.toDegrees(orientVals[2]);*/
                float a = (float) Math.sqrt(gravity[0]*gravity[0] + gravity[1]*gravity[1] + gravity[2]*gravity[2]);
                String data = String.valueOf(gravity[0] + " " + gravity[1] + " " + gravity[2] + " " + a);

                //Lọc tín hiệu
                float[] arr = kFilter.filter(new float[]{
                        gravity[0],
                        gravity[1],
                        gravity[2]
                });

                float aaa = (float) Math.sqrt(arr[0]*arr[0] + arr[1]*arr[1] + arr[2]*arr[2]);

                String dataFilter = String.valueOf(arr[0]) + " " + String.valueOf(arr[1]) + " " + String.valueOf(arr[2]) + " " + String.valueOf(aaa);
                LoggerManager.getInstances(mContext).writeACCLogger(dataFilter);
                LoggerManager.getInstances(mContext).writeOrientation(String.valueOf(azimuth));

                if(aaa >= 12.5){
                    Preference.buildInstance(mContext).setRunningState(false);
                    Intent dialogIntent = new Intent(mContext, FallenDialog.class);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(dialogIntent);
                }

                //LoggerManager.getInstances(mContext).writeACCLogger(data);
                //Toast.makeText(mContext, "dU LIEU THU DUOC ----" + String.valueOf(azimuth), Toast.LENGTH_LONG).show();
                if(Preference.buildInstance(mContext).getRunningState()){
                    EventBus.getDefault().postSticky(new OrientationEventBus(arr[0], arr[1], arr[2], aaa, azimuth));
                }else {
                    return;
                }
            }
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void initSensor() {
        lowPassFilter = new LowPassFilter();
        lowPassFilter.setTimeConstant(0.1f);//Thiết lập tần số cắt
        lastTime = System.currentTimeMillis();
        timeInterval = 0;
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        kFilter = new LowPassFilter();
        kFilter.setTimeConstant(0.15f);

        if (mSensorAccelerometer != null) {
            mSensorManager.registerListener(this, mSensorAccelerometer,
                    20000); // f = 50Hz
        }

        if(mSensorOrientation != null){
            mSensorManager.registerListener(this, mSensorOrientation,
                    20000);
        }
    }

    public void stopSensorService() {
        mSensorManager.unregisterListener(this);
    }

}
