package vnu.uet.boatsafe.ui.main;

import android.content.Context;

import vnu.uet.boatsafe.di.PerActivity;
import vnu.uet.boatsafe.ui.base.MvpPresenter;

@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

}
