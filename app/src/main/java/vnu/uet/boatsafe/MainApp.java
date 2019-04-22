package vnu.uet.boatsafe;

import android.app.Application;


import com.google.gson.Gson;

import vnu.uet.boatsafe.di.component.ApplicationComponent;
import vnu.uet.boatsafe.di.component.DaggerApplicationComponent;
import vnu.uet.boatsafe.di.module.ApplicationModule;
import javax.inject.Inject;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import vnu.uet.boatsafe.utils.AppLogger;

public class MainApp extends Application{

    private static MainApp instance;

    @Inject
    CalligraphyConfig calligraphyConfig;
    @Inject
    Gson gson;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        //Fabric.with(this, new Crashlytics());

        instance = this;


        applicationComponent =  DaggerApplicationComponent
                                .builder()
                                .applicationModule(new ApplicationModule(this))
                                .build();

        applicationComponent.inject(this);

        AppLogger.init();

        CalligraphyConfig.initDefault(calligraphyConfig);

    }

    public static MainApp newInstance() {

        return instance;
    }

    public Gson getGson() {
        return gson;
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }
}
