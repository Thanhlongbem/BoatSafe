package vnu.uet.boatsafe.ui.base;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

import butterknife.Unbinder;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import vnu.uet.boatsafe.MainApp;
import vnu.uet.boatsafe.di.component.ActivityComponent;
import vnu.uet.boatsafe.di.component.DaggerActivityComponent;
import vnu.uet.boatsafe.di.module.ActivityModule;
import vnu.uet.boatsafe.utils.CommonUtils;
import vnu.uet.boatsafe.utils.KeyboardUtils;
import vnu.uet.boatsafe.utils.NetworkUtils;
import vnu.uet.boatsafe.utils.event.EventMessage;

public abstract class BaseActivity extends AppCompatActivity implements MvpView, BaseFragment.Callback {

    private DaggerActivityComponent.Builder builderActivityComponent;
    private ActivityComponent activityComponent;
    private Unbinder unbinder;
    private ProgressDialog progressDialog;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        builderActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((MainApp) getApplication()).getComponent());
        activityComponent = builderActivityComponent.build();
        onBeforeConfigView();
        setContentView(configView());
        init();
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    public DaggerActivityComponent.Builder getBuilderActivityComponent() {
        return builderActivityComponent;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLoading() {
        runOnUiThread(() -> {
            hideLoading();
            progressDialog = CommonUtils.showLoadingDialog(this);
        });

    }

    @Override
    public void hideLoading() {
        runOnUiThread(() -> {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.cancel();
            }
        });

    }

    @Override
    public void showMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(int resId) {
        showMessage(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(this);
    }

    @Override
    public void hideKeyboard() {
        KeyboardUtils.hideSoftInput(this);
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    @Override
    protected void onDestroy() {

        if (unbinder != null) {
            unbinder.unbind();
        }

        super.onDestroy();
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageReceive(EventMessage event) {
    }

    protected abstract void onBeforeConfigView();

    /**
     * @return layout của activity
     */
    protected abstract int configView();

    /**
     * Xử lý logic
     */
    protected abstract void init();


}
