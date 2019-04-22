package vnu.uet.boatsafe.ui.home;

import android.location.Location;

import vnu.uet.boatsafe.models.Addr;
import vnu.uet.boatsafe.ui.base.MvpView;

public interface HomeFrMvpView extends MvpView {
    void onGotCurrentLocation(Location location);
    void onFailGotAddr();
    void onGotAddr(Addr addr);
}
