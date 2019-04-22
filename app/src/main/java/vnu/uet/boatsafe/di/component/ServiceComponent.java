

package vnu.uet.boatsafe.di.component;

import dagger.Component;
import vnu.uet.boatsafe.di.PerService;
import vnu.uet.boatsafe.di.module.ServiceModule;

/**
 * Created by janisharali on 01/02/17.
 */

@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
}
