package vnu.uet.boatsafe.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import javax.inject.Named;

import vnu.uet.boatsafe.di.ActivityContext;
import vnu.uet.boatsafe.di.PerActivity;
import vnu.uet.boatsafe.ui.history.HistoryAdapter;
import vnu.uet.boatsafe.ui.history.HistoryFrMvpPresent;
import vnu.uet.boatsafe.ui.history.HistoryFrMvpView;
import vnu.uet.boatsafe.ui.history.HistoryFrPresenter;
import vnu.uet.boatsafe.ui.home.HomeFrMvpPresent;
import vnu.uet.boatsafe.ui.home.HomeFrMvpView;
import vnu.uet.boatsafe.ui.home.HomeFrPresenter;
import vnu.uet.boatsafe.ui.info.InfoFrMvpPresent;
import vnu.uet.boatsafe.ui.info.InfoFrMvpView;
import vnu.uet.boatsafe.ui.info.InfoFrPresenter;
import vnu.uet.boatsafe.ui.main.MainMvpPresenter;
import vnu.uet.boatsafe.ui.main.MainMvpView;
import vnu.uet.boatsafe.ui.main.MainPresenter;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }


    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return activity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    HomeFrMvpPresent<HomeFrMvpView> provideHomeFrPresent(HomeFrPresenter<HomeFrMvpView> presenter) {
        return presenter;
    }


    @Provides
    @Named("vertical")
    LinearLayoutManager provideVerticalLinearLayoutManager() {
        return new LinearLayoutManager(activity.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    @Named("2Column")
    GridLayoutManager provideGrid2LayoutManager() {
        return new GridLayoutManager(activity.getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    @Named("3Column")
    GridLayoutManager provideGrid3LayoutManager() {
        return new GridLayoutManager(activity.getApplicationContext(), 3, LinearLayoutManager.VERTICAL, false);
    }


    @Provides
    HistoryFrMvpPresent<HistoryFrMvpView> provideCateFrMvpPresent(HistoryFrPresenter<HistoryFrMvpView> presenter) {
        return presenter;
    }



    @Provides
    InfoFrMvpPresent<InfoFrMvpView> provideSettingFrMvpPresent(InfoFrPresenter<InfoFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    HistoryAdapter provideHistoryAdapter() {
        return new HistoryAdapter(null);
    }

}
