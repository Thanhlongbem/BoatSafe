package vnu.uet.boatsafe.ui.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import vnu.uet.boatsafe.R;

public class TurnOffOnDialog extends BottomSheetDialogFragment {
    public static final String TAG = TurnOffOnDialog.class.getCanonicalName();

    Unbinder unbinder;
    private Callback callback;


    public static TurnOffOnDialog newInstance(){
        TurnOffOnDialog fragment = new TurnOffOnDialog();
        Bundle arg  = new Bundle();
        fragment.setArguments(arg);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_turn_on_off_state, container,
                false);
        // get the views and attach the listener
        getDialog().getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        unbinder = ButterKnife.bind(this, view);



        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.btnAccept)
    public void onButtonAcceptClick(){
        if(callback != null){
            callback.onButtonPositiveClick(this);
        }
    }

    @OnClick(R.id.btnComeBack)
    public void onButtonComeBackClick(){
        if(callback != null){
            this.dismiss();
        }
    }

    public interface Callback{
        void onButtonPositiveClick(TurnOffOnDialog td);
    }

    public TurnOffOnDialog setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }
}
