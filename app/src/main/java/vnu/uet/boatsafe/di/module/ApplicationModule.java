package vnu.uet.boatsafe.di.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import vnu.uet.boatsafe.R;
import vnu.uet.boatsafe.data.AppDataManager;
import vnu.uet.boatsafe.data.DataManager;
import vnu.uet.boatsafe.data.network.NetworkBuilder;
import vnu.uet.boatsafe.data.prefs.Preference;
import vnu.uet.boatsafe.data.prefs.PreferenceHelper;
import vnu.uet.boatsafe.di.ApplicationContext;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() { return application; }

    @Provides
    Application provideApplication() { return application; }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig
                .Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
    @Provides
    @Singleton
    Gson provideGson() {
        return NetworkBuilder.provideGson();
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }


    @Provides
    @Singleton
    PreferenceHelper providePreferencesHelper(Preference appPreferencesHelper) {
        return appPreferencesHelper;
    }

}
