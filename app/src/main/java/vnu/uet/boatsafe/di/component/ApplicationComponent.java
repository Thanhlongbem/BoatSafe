package vnu.uet.boatsafe.di.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import vnu.uet.boatsafe.MainApp;
import vnu.uet.boatsafe.data.DataManager;
import vnu.uet.boatsafe.di.ApplicationContext;
import vnu.uet.boatsafe.di.module.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainApp app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}
