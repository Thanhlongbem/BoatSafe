

package vnu.uet.boatsafe.di.module;

import android.app.Service;

import dagger.Module;

/**
 * Created by janisharali on 01/02/17.
 */

@Module
public class ServiceModule {

    private final Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }
}
