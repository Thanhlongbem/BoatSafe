package vnu.uet.boatsafe.ui.history;

import javax.inject.Inject;

import vnu.uet.boatsafe.data.DataManager;
import vnu.uet.boatsafe.ui.base.BasePresenter;
import io.reactivex.disposables.CompositeDisposable;

public class HistoryFrPresenter<V extends HistoryFrMvpView> extends BasePresenter<V> implements HistoryFrMvpPresent<V> {

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public HistoryFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }


}
