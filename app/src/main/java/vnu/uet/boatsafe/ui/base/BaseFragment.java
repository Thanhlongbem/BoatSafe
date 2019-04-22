package vnu.uet.boatsafe.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Unbinder;
import vnu.uet.boatsafe.di.component.ActivityComponent;
import vnu.uet.boatsafe.di.component.DaggerActivityComponent;
import vnu.uet.boatsafe.utils.CommonUtils;
import vnu.uet.boatsafe.utils.event.EventMessage;

public abstract class BaseFragment extends Fragment implements MvpView {

    private View root;
    private BaseActivity activity;
    private Unbinder unbinder;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(configView(), container, false);
        initCreatedView(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view,savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.activity = activity;
            activity.onFragmentAttached();
        }
    }


    @Override
    public void showLoading() {
        getBaseActivity().runOnUiThread(()-> {
            hideLoading();
            progressDialog = CommonUtils.showLoadingDialog(this.getContext());
        });

    }

    @Override
    public void hideLoading() {
        getBaseActivity().runOnUiThread(()-> {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.cancel();
            }
        });

    }

    @Override
    public void showMessage(String message) {

        if (activity != null) {
            activity.showMessage(message);
        }
    }

    @Override
    public void showMessage(int resId) {
        if (activity != null) {
            activity.showMessage(resId);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        if (activity != null) {
            return activity.isNetworkConnected();
        }
        return false;
    }

    @Override
    public void hideKeyboard() {
        if (activity != null) {
            activity.hideKeyboard();
        }
    }

    public ActivityComponent getActivityComponent() {
        if (activity != null) {
            return activity.getActivityComponent();
        }
        return null;
    }

    public DaggerActivityComponent.Builder getBuilderActivityComponent() {
        if (activity != null) {
            return activity.getBuilderActivityComponent();
        }
        return null;
    }


    public BaseActivity getBaseActivity() {
        return activity;
    }

    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    /**
     *
     * @return view của fragment
     */
    protected abstract int configView();

    /**
     * Xử lý logic
     */
    protected abstract void init(View v,Bundle savedInstanceState);

    protected abstract void initCreatedView(View v);

    @Override
    public void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageReceive(EventMessage event) {

    }

}
