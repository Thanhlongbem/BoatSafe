package vnu.uet.boatsafe.ui.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vnu.uet.boatsafe.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UiToolbarBack extends AppBarLayout implements BaseWidget {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgShare)
    ImageView imgShare;
    private View v;

    private Callback callback;
    private OnShareClickListener onShareClickListener;

    public UiToolbarBack(Context context) {
        super(context);
        init(context, null, -1);
    }

    public UiToolbarBack(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, -1);
    }

    @Override
    public void init(Context context, AttributeSet attrs, int defStyleAttr) {
        v = LayoutInflater.from(context).inflate(R.layout.ui_toolbar_back, this, true);
        ButterKnife.bind(this, v);
    }

    @OnClick(R.id.imgBack)
    public void onViewClicked() {
        if (callback != null) callback.onBackPressListener();
    }

    @OnClick(R.id.imgBack)
    public void onShareClicked() {
        if (onShareClickListener != null) onShareClickListener.onShareClickClick();
    }

    public void setTitle(String title){
        txtTitle.setText(title);
    }

    public void switchShare(boolean enable){
        imgShare.setVisibility(enable ? VISIBLE : GONE);
    }

    public interface Callback{
        void onBackPressListener();
    }

    public interface  OnShareClickListener{
        void onShareClickClick();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setOnShareClickListener(OnShareClickListener onShareClickListener) {
        this.onShareClickListener = onShareClickListener;
    }
}
