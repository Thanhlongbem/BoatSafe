

package vnu.uet.boatsafe.ui.base;

import javax.inject.Inject;


import io.reactivex.disposables.CompositeDisposable;
import vnu.uet.boatsafe.data.DataManager;


public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private static final String TAG = BasePresenter.class.getSimpleName();
    private final DataManager mDataManager;
    private final CompositeDisposable mCompositeDisposable;

    private V mMvpView;

    @Inject
    public BasePresenter(DataManager dataManager,CompositeDisposable compositeDisposable) {
        this.mCompositeDisposable = compositeDisposable;
        this.mDataManager = dataManager;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mCompositeDisposable.dispose();
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }


    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
