package vnu.uet.boatsafe.di.component;

import vnu.uet.boatsafe.di.PerActivity;
import vnu.uet.boatsafe.di.module.ActivityModule;
import vnu.uet.boatsafe.ui.history.HistoryFragment;
import vnu.uet.boatsafe.ui.main.MainActivity;
import vnu.uet.boatsafe.ui.home.HomeFragment;
import vnu.uet.boatsafe.ui.info.InfoFragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(HomeFragment homeFragment);
    void inject(HistoryFragment historyFragment);
    void inject(InfoFragment settingFragment);

}
