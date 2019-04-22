package vnu.uet.boatsafe.ui.home;



import android.annotation.SuppressLint;
import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;
import vnu.uet.boatsafe.data.DataManager;
import vnu.uet.boatsafe.models.Addr;
import vnu.uet.boatsafe.ui.base.BaseActivity;
import vnu.uet.boatsafe.ui.base.BasePresenter;
import io.reactivex.disposables.CompositeDisposable;
import vnu.uet.boatsafe.utils.CommonUtils;

public class HomeFrPresenter<V extends HomeFrMvpView> extends BasePresenter<V> implements HomeFrMvpPresent<V> {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private Timer timerGetAddr;


    @Inject
    public HomeFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }


    @SuppressLint("MissingPermission")
    @Override
    public void getCurrentLocation(FusedLocationProviderClient fusedLocationProviderClient) {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> getMvpView().onGotCurrentLocation(location))
                .addOnFailureListener(e -> getMvpView().onFailGotAddr());
    }

    @Override
    public void onStartMoveMapCamera() {
        if (timerGetAddr != null) timerGetAddr.cancel();
    }

    @Override
    public void onCameraMapIdle(Context context, LatLng latLng) {
        timerGetAddr = new Timer();
        timerGetAddr.schedule(new TimerTask() {
            @Override
            public void run() {
                String addr = CommonUtils.getAddrLatLng(context, latLng.latitude, latLng.longitude);
                ((BaseActivity) context).runOnUiThread(() -> {
                    getMvpView().onGotAddr(new Addr(latLng.latitude, latLng.longitude, addr));
                });

            }
        }, 300);
    }
}
