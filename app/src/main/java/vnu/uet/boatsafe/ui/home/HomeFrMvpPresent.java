package vnu.uet.boatsafe.ui.home;


import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;

import vnu.uet.boatsafe.ui.base.MvpPresenter;

public interface HomeFrMvpPresent<V extends HomeFrMvpView> extends MvpPresenter<V> {
    void getCurrentLocation(FusedLocationProviderClient fusedLocationProviderClient);
    void onStartMoveMapCamera();
    void onCameraMapIdle(Context context, LatLng latLng);
}
