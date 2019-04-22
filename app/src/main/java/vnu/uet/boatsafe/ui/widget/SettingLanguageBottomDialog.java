package vnu.uet.boatsafe.ui.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vnu.uet.boatsafe.R;
import vnu.uet.boatsafe.ui.base.OnItemClickListener;

public class SettingLanguageBottomDialog extends BottomSheetDialogFragment implements OnItemClickListener {

    public final static String TAG = SettingLanguageBottomDialog.class.getCanonicalName();
    private final static String ARG_TITLE = "ARG_TITLE";
    private final static String ARG_FILTER = "ARG_FILTER";

    Unbinder unbinder;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rcDictionary)
    RecyclerView rcDictionary;

    private String title;
    private List<String> filters;
    private FilterAdapter filterAdapter;
    private LinearLayoutManager linearLayoutManager;
    private OnItemClickListener onItemClickListener;
    private Callback callback;

    public static SettingLanguageBottomDialog newInstance(String title, ArrayList<String> filters) {
        SettingLanguageBottomDialog fragment = new SettingLanguageBottomDialog();
        Bundle arg = new Bundle();
        arg.putString(ARG_TITLE, title);
        arg.putStringArrayList(ARG_FILTER, filters);
        fragment.setArguments(arg);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ui_popup_filter, container,
                false);
        // get the views and attach the listener
        getDialog().getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = getArguments().getString(ARG_TITLE,null);
        filters = getArguments().getStringArrayList(ARG_FILTER);

        if (!TextUtils.isEmpty(title))
            tvTitle.setText(title);

        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        filterAdapter = new FilterAdapter(filters);
        filterAdapter.setOnItemClickListener(this);
        rcDictionary.setHasFixedSize(true);
        rcDictionary.setLayoutManager(linearLayoutManager);
        rcDictionary.setAdapter(filterAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(int position) {
        this.dismiss();
        if (callback != null){
            callback.onJustStringDialogClickListener(position);
        }
    }

    public interface Callback{
        void onJustStringDialogClickListener(int position);
    }

    public SettingLanguageBottomDialog setCallBack(Callback callBack){
        this.callback = callBack;
        return this;
    }
}
