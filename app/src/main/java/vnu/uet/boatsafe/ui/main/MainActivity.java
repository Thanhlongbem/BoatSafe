package vnu.uet.boatsafe.ui.main;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Locale;

import javax.inject.Inject;

import io.realm.Realm;
import vnu.uet.boatsafe.R;
import vnu.uet.boatsafe.data.prefs.Preference;
import vnu.uet.boatsafe.ui.base.BaseActivity;
import vnu.uet.boatsafe.ui.history.HistoryFragment;
import vnu.uet.boatsafe.ui.home.HomeFragment;
import vnu.uet.boatsafe.ui.info.InfoFragment;
import vnu.uet.boatsafe.utils.AppUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView, BottomNavigationView.OnNavigationItemSelectedListener,  BottomNavigationView.OnNavigationItemReselectedListener {

    @Inject
    MainMvpPresenter<MainMvpView> presenter;

    @BindView(R.id.mainBottomNavView)
    BottomNavigationView mainBottomNavView;
    @BindView(R.id.vpListFragment)
    FrameLayout mainFvpListFragmentrame;

    private Locale myLocale;


    @Override
    protected void onBeforeConfigView() {

    }

    @Override
    protected int configView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        Realm.init(getApplicationContext());

        if(Preference.buildInstance(getApplicationContext()).getLanguageState() == 1){
            setLocale("en");
        }

        mainBottomNavView.setOnNavigationItemSelectedListener(this);
        mainBottomNavView.setOnNavigationItemReselectedListener(this);

        //Replace HomeFragment
        AppUtils.replaceFragmentToActivity(getSupportFragmentManager(), HomeFragment.newInstance(), R.id.vpListFragment, false, HomeFragment.TAG);
        //Permission

        if (!hasGalleryPermission()) {
            askForGalleryPermission();
        }
    }

    public void setLocale(String localeName){
        myLocale = new Locale(localeName);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = myLocale;
        resources.updateConfiguration(configuration, displayMetrics);
        Intent refresh = new Intent(getApplicationContext(), MainActivity.class);
        refresh.putExtra("en", localeName);
        startActivity(refresh);
        finish();
        startActivity(getIntent());
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionListener:
                if (!(getSupportFragmentManager().findFragmentById(R.id.vpListFragment) instanceof HomeFragment)) {
                    AppUtils.replaceFragmentToActivity(getSupportFragmentManager(), HomeFragment.newInstance(), R.id.vpListFragment, false, HomeFragment.TAG);
                }
                return true;
            case R.id.actionCategory:
                if (!(getSupportFragmentManager().findFragmentById(R.id.vpListFragment) instanceof HistoryFragment)) {
                    AppUtils.replaceFragmentToActivity(getSupportFragmentManager(), HistoryFragment.newInstance(), R.id.vpListFragment, false, HistoryFragment.TAG);
                }
                return true;
            case R.id.actionInfo:
                if (!(getSupportFragmentManager().findFragmentById(R.id.vpListFragment) instanceof InfoFragment)) {
                    AppUtils.replaceFragmentToActivity(getSupportFragmentManager(), InfoFragment.newInstance(), R.id.vpListFragment, false, InfoFragment.TAG);
                }
                return true;
            default:
                return false;
        }
    }


    @Override
    public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.actionListener:
                if (!(getSupportFragmentManager().findFragmentById(R.id.vpListFragment) instanceof HomeFragment)) {
                    AppUtils.popToFragmentIndex(getSupportFragmentManager(), 0);
                }
                break;
            case R.id.actionCategory:
                if (!(getSupportFragmentManager().findFragmentById(R.id.vpListFragment) instanceof HistoryFragment)) {
                    AppUtils.popToFragmentIndex(getSupportFragmentManager(), 0);
                }
                break;
            case R.id.actionInfo:
                if (!(getSupportFragmentManager().findFragmentById(R.id.vpListFragment) instanceof InfoFragment)) {
                    AppUtils.popToFragmentIndex(getSupportFragmentManager(), 0);
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }


    private static final int REQUEST_CODE_READ_PERMISSION = 2209;

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_READ_PERMISSION: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    // Download effect asset

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private boolean hasGalleryPermission() {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return true;
        }else {
            return false;
        }
    }

    private void askForGalleryPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.WRITE_EXTERNAL_STORAGE },
                REQUEST_CODE_READ_PERMISSION);
    }
}
