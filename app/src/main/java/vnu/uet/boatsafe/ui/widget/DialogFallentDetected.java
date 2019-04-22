package vnu.uet.boatsafe.ui.widget;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import vnu.uet.boatsafe.R;
import vnu.uet.boatsafe.data.prefs.Preference;

public class DialogFallentDetected extends Dialog {
    public static final String TAG = DialogFallentDetected.class.getCanonicalName();

    @BindView(R.id.imvIcon)
    ImageView imvIcon;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.btnComeBack)
    Button btnComeBack;
    @BindView(R.id.btnAccept)
    Button btnAccept;
    @BindView(R.id.tvCountDown)
    TextView tvCountDown;

    Unbinder unbinder;
    private Callback callback;
    int timer = 30;
    CountDownTimer count;

    public DialogFallentDetected(Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_fall_detected);
        unbinder = ButterKnife.bind(this);
        countDownTimer(30000);

    }








    @OnClick(R.id.btnAccept)
    public void onButtonAcceptClick(){
        if(callback != null){
            callback.onButtonPositiveClick(this, count);
        }
    }

    @OnClick(R.id.btnComeBack)
    public void onButtonComeBackClick(){
        callback.onBackClick(this);
        Preference.buildInstance(getContext()).setRunningState(true);
    }



    public interface Callback{
        void onButtonPositiveClick(DialogFallentDetected td, CountDownTimer count);
        void onBackClick(DialogFallentDetected td);
    }

    public DialogFallentDetected setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }


    public void countDownTimer(int time){
        count = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCountDown.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                tvCountDown.setText("0");
                /*String messageToSend = getContext().getString(R.string.title_location) + String.valueOf(Preference.buildInstance(getContext()).getLastLat()) + ", " + String.valueOf(Preference.buildInstance(getContext()).getLastLong());
                String number = "0916662195";
                SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null,null);*/

                //Sử dụng chức năng tự động gọi
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:0916662195"));
                getContext().startActivity(intent);
            }
        }.start();
    }
}
