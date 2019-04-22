package vnu.uet.boatsafe.ui.info;

import android.content.Context;

import javax.inject.Inject;

import vnu.uet.boatsafe.data.DataManager;
import vnu.uet.boatsafe.data.network.NetworkBuilder;
import vnu.uet.boatsafe.ui.base.BasePresenter;
import vnu.uet.boatsafe.utils.rx.RxUtil;
import io.reactivex.disposables.CompositeDisposable;

public class InfoFrPresenter<V extends InfoFrMvpView> extends BasePresenter<V> implements InfoFrMvpPresent<V> {


    @Inject
    public InfoFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }


}
