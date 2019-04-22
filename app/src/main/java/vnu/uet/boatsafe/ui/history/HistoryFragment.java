package vnu.uet.boatsafe.ui.history;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import vnu.uet.boatsafe.R;
import vnu.uet.boatsafe.di.component.ActivityComponent;
import vnu.uet.boatsafe.models.History;
import vnu.uet.boatsafe.models.Location;
import vnu.uet.boatsafe.ui.base.BaseFragment;
import vnu.uet.boatsafe.ui.widget.UiToolbarHome;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryFragment extends BaseFragment implements HistoryFrMvpView{

    public static final String TAG = HistoryFragment.class.getSimpleName();
    private List<History> histories;

    @Inject
    HistoryFrMvpPresent<HistoryFrMvpView> presenter;
    @Inject
    @Named("vertical")
    LinearLayoutManager linearLayoutManager;

    @Inject
    HistoryAdapter historyAdapter;

    @BindView(R.id.rcvHistory)
    RecyclerView rcvHistory;
    @BindView(R.id.toolbar)
    UiToolbarHome toolBar;


    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_all_cate;
    }

    @Override
    protected void init(View v,Bundle savedInstanceState) {
        toolBar.setNameFragment(getString(R.string.title_history));
        List<Location> locations = new ArrayList<>();
        locations.add(new Location("10h20AM", 20.564722, 106.605399));
        locations.add(new Location("1h40PM", 20.257685, 106.583035));
        histories = new ArrayList<>();
        histories.add(new History("03/03/2019", "1h20p",  150, locations));
        histories.add(new History("04/03/2019", "35p",  55, locations));

        historyAdapter = new HistoryAdapter(histories);
        rcvHistory.setLayoutManager(linearLayoutManager);
        rcvHistory.setHasFixedSize(true);
        rcvHistory.setAdapter(historyAdapter);

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
}
