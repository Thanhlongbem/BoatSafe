package vnu.uet.boatsafe.ui.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import vnu.uet.boatsafe.R;
import vnu.uet.boatsafe.ui.main.MainActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class UiToolbarSearch extends AppBarLayout implements BaseWidget {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    private View v;
    private Callback callback;

    public UiToolbarSearch(Context context) {
        super(context);
        init(context, null, -1);
    }

    public UiToolbarSearch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, -1);
    }

    @Override
    public void init(Context context, AttributeSet attrs, int defStyleAttr) {
        v = LayoutInflater.from(context).inflate(R.layout.ui_toolbar_search, this, true);
        ButterKnife.bind(this, v);
    }

    @OnEditorAction(R.id.edtSearch)
    public boolean onActionDoneClick(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (TextUtils.isEmpty(edtSearch.getText().toString())){
                ((MainActivity)getContext()).showMessage("Vui lòng điền nội dung cần tìm!");
                return false;
            }
            if (callback != null) callback.onSearchRequest(edtSearch.getText().toString());
            return true;
        }
        return false;
    }

    @OnClick(R.id.imgBack)
    public void onViewClicked() {
        if (callback != null) callback.onBackClickListener();
    }

    public EditText getEdtSearch() {
        return edtSearch;
    }

    public interface Callback {
        void onSearchRequest(String query);
        void onBackClickListener();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
