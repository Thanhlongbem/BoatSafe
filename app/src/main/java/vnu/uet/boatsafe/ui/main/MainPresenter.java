package vnu.uet.boatsafe.ui.main;


import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;
import vnu.uet.boatsafe.data.DataManager;
import vnu.uet.boatsafe.ui.base.BasePresenter;

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    @Inject
    public MainPresenter(DataManager dataManager,CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }


}
