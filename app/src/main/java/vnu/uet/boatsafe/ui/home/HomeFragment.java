package vnu.uet.boatsafe.ui.home;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;
import javax.inject.Named;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vnu.uet.boatsafe.R;
import vnu.uet.boatsafe.data.prefs.Preference;
import vnu.uet.boatsafe.di.component.ActivityComponent;
import vnu.uet.boatsafe.models.Addr;

import vnu.uet.boatsafe.models.LocationEventBus;
import vnu.uet.boatsafe.models.OrientationEventBus;
import vnu.uet.boatsafe.service.BoatSafeService;
import vnu.uet.boatsafe.service.location.LocationService;
import vnu.uet.boatsafe.service.log.LoggerManager;
import vnu.uet.boatsafe.ui.base.BaseFragment;
import vnu.uet.boatsafe.ui.location.GPSTracker;
import vnu.uet.boatsafe.ui.widget.TurnOffOnDialog;
import vnu.uet.boatsafe.utils.AppConstants;
import vnu.uet.boatsafe.utils.AppUtils;

public class HomeFragment extends BaseFragment implements HomeFrMvpView, OnMapReadyCallback , GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener,  AppUtils.PermissionCallBack, TurnOffOnDialog.Callback {

    public static final String TAG = HomeFragment.class.getSimpleName();

    @Inject
    HomeFrMvpPresent<HomeFrMvpView> presenter;
    @Inject
    @Named("vertical")
    LinearLayoutManager linearLayoutManager;

    @BindView(R.id.txtAddr)
    EditText txtAddr;
    @BindView(R.id.fbStart)
    FloatingActionButton fbStart;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.tvSpeed)
    TextView tvSpeed;
    @BindView(R.id.tvDistance)
    TextView tvDistance;


    private GoogleMap ggMap;
    private SupportMapFragment ggMapFr;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private vnu.uet.boatsafe.models.Location currentLocation;
    private Addr currentAddr;


    double lastLat = 0;
    double lastLong = 0;
    private Context context;
    private Timer timer;
    private TimerTask updateTimerTask;
    private double speed = 0;
    private double distance = 0;
    private TurnOffOnDialog turnOffOnDialog;


    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_home;
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void init(View v, Bundle savedInstanceState) {
        updateCurentLocation();




        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        ggMapFr = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        ggMapFr.getMapAsync(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!EventBus.getDefault().hasSubscriberForEvent(LocationEventBus.class)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void initCreatedView(View v) {
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnbinder(ButterKnife.bind(this, v));
            presenter.onAttach(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!EventBus.getDefault().hasSubscriberForEvent(LocationEventBus.class)){
            EventBus.getDefault().register(this);
        }

        if (Preference.buildInstance(getContext()).getRunningState()){//Đang lấy dữ liệu rồi
            fbStart.setImageResource(R.drawable.pause);
        }else {
            fbStart.setImageResource(R.drawable.ic_start);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(LocationEventBus eventBus){
        updateCurentLocation();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(OrientationEventBus eventBus){
        LoggerManager.getInstances(getContext()).writeOrientation(String.valueOf(eventBus.getAx()) + " " + String.valueOf(eventBus.getAy()) + " " + String.valueOf(eventBus.getAz()) + " " + String.valueOf(eventBus.getA()) + " " + String.valueOf(eventBus.getAzimuth()));
    }

    private void updateCurentLocation(){
        LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE );
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if ( !statusOfGPS){
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
            GPSTracker gps = new GPSTracker(getContext());
            if(gps.canGetLocation()){
                currentLocation = new vnu.uet.boatsafe.models.Location();
                currentLocation.setLat(gps.getLatitude());
                currentLocation.setLng(gps.getLongitude());
                speed = gps.getSpeed();
                mapFocus(currentLocation.getLat(), currentLocation.getLng(), Preference.buildInstance(getContext()).getLastLat(), Preference.buildInstance(getContext()).getLastLong());
                Preference.buildInstance(getContext()).setLastLat(gps.getLatitude());
                Preference.buildInstance(getContext()).setLastLong(gps.getLongitude());
                Toast.makeText(getContext(), getString(R.string.title_location_is_updated), Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getContext(), getString(R.string.title_is_not_update_location), Toast.LENGTH_LONG).show();
            }
        }else{
            GPSTracker gps = new GPSTracker(getContext());
            if(gps.canGetLocation()){
                currentLocation = new vnu.uet.boatsafe.models.Location();
                currentLocation.setLat(gps.getLatitude());
                currentLocation.setLng(gps.getLongitude());
                mapFocus(currentLocation.getLat(), currentLocation.getLng(), Preference.buildInstance(getContext()).getLastLat(), Preference.buildInstance(getContext()).getLastLong());
                Preference.buildInstance(getContext()).setLastLat(gps.getLatitude());
                Preference.buildInstance(getContext()).setLastLong(gps.getLongitude());
                Toast.makeText(getContext(), getString(R.string.title_location_is_updated), Toast.LENGTH_LONG).show();
                speed = gps.getSpeed();
            }else {
                Toast.makeText(getContext(), getString(R.string.title_is_not_update_location), Toast.LENGTH_LONG).show();
            }
        }

        DecimalFormat df = new DecimalFormat("#.000000");

        if(currentLocation != null){
            tvLocation.setText(String.valueOf(df.format(currentLocation.getLat())) + " : " + String.valueOf(df.format(currentLocation.getLng())));
            tvSpeed.setText(String.valueOf(speed) + " km/h");
            tvDistance.setText(String.valueOf(distance + " km"));
        }

    }



    @OnClick(R.id.fbStart)
    public void onStartClick(){
        if(isServiceRunning(BoatSafeService.class) && isServiceRunning(LocationService.class)){//Đang lấy dữ liệu rồi
            turnOffOnDialog = TurnOffOnDialog.newInstance();
            turnOffOnDialog.setCallback(this).show(getActivity().getSupportFragmentManager(), TurnOffOnDialog.TAG);
        }else {
            startBoatSafeService();
            startLocationService();
            fbStart.setImageResource(R.drawable.pause);
            Preference.buildInstance(getContext()).setRunningState(true);
        }
    }


    private void startBoatSafeService(){
        if (!isServiceRunning(BoatSafeService.class)) {
            Objects.requireNonNull(getActivity()).startService(new Intent(getActivity(), BoatSafeService.class));
            Toast.makeText(getContext(), "start", Toast.LENGTH_LONG).show();
        }
    }

    private void stopBoatSafeService(){
        if (isServiceRunning(BoatSafeService.class)) {
            Objects.requireNonNull(getActivity()).stopService(new Intent(getActivity(), BoatSafeService.class));
            Toast.makeText(getContext(), "End", Toast.LENGTH_LONG).show();
        }
    }

    private void startLocationService(){
        if(!isServiceRunning(LocationService.class)){
            getActivity().startService(new Intent(getActivity(), LocationService.class));
        }
    }

    private void stopLocationService(){
        if(isServiceRunning(LocationService.class)){
            Objects.requireNonNull(getActivity()).stopService(new Intent(getActivity(), LocationService.class));
        }
    }



    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }



    @SuppressLint({"MissingPermission", "SetTextI18n"})
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.ggMap = googleMap;

        if(currentLocation != null){
            ggMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLat(), currentLocation.getLng()), 14f));
            txtAddr.setText(String.valueOf(currentLocation.getLat()) + " : " + String.valueOf(currentLocation.getLng()));
        }

        ggMap.setOnCameraIdleListener(this);
        ggMap.setOnCameraMoveStartedListener(this);

        ggMap.getUiSettings().setMyLocationButtonEnabled(false);
        ggMap.getUiSettings().setMapToolbarEnabled(false);

        if(currentLocation == null){
            if (AppUtils.isPermisstionLocationGrant(getContext())) {
                ggMap.setMyLocationEnabled(true);
                presenter.getCurrentLocation(fusedLocationProviderClient);
            } else {
                AppUtils.requestPermission(getActivity(), AppConstants.LOCATION_PERMISSION, this);
            }
        }
    }



    @Override
    public void onGotCurrentLocation(Location location) {
        if (location != null) {
            presenter.onCameraMapIdle(getContext(), new LatLng(location.getLatitude(), location.getLongitude()));
            ggMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15f));
        }
    }

    @Override
    public void onFailGotAddr() {

    }

    @Override
    public void onGotAddr(Addr addr) {
        this.currentAddr = addr;
        if (txtAddr != null) {
            txtAddr.setText(addr.getAddr());
        }
    }

    @Override
    public void onCameraIdle() {
        presenter.onCameraMapIdle(getContext(), ggMap.getCameraPosition().target);
    }

    @Override
    public void onCameraMoveStarted(int i) {
        txtAddr.setText("");
        presenter.onStartMoveMapCamera();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        if (report.areAllPermissionsGranted()) {
            ggMap.setMyLocationEnabled(true);
            presenter.getCurrentLocation(fusedLocationProviderClient);
        }
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
        token.continuePermissionRequest();
    }

    @SuppressLint("MissingPermission")
    public void mapFocus(double newLat, double newLong, double oldLat, double oldLong){
        if(ggMap != null){
            LatLng prev = new LatLng(oldLat, oldLong);
            LatLng cur = new LatLng(newLat, newLong);

            ggMap.setMyLocationEnabled(true);
            ggMap.getUiSettings().setZoomControlsEnabled(false);

            LatLng point = new LatLng(newLat, newLong);

            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_maker_blue);

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(point);
            markerOptions.icon(icon);

            Polyline polyline = ggMap.addPolyline(new PolylineOptions().add(prev, cur).width(5).color(Color.RED));

        }
    }

    @Override
    public void onButtonPositiveClick(TurnOffOnDialog td) {
        stopBoatSafeService();
        stopLocationService();
        fbStart.setImageResource(R.drawable.ic_start);
        Preference.buildInstance(getContext()).setRunningState(false);
        td.dismiss();
    }
}
