package com.bb.paytminsider.dagger.component;

import android.app.Application;

import com.bb.paytminsider.PaytmApplication;
import com.bb.paytminsider.dagger.module.ActivityBuildersModule;
import com.bb.paytminsider.dagger.module.AppModule;
import com.bb.paytminsider.dagger.module.ExecutorModule;
import com.bb.paytminsider.dagger.module.GlideModule;
import com.bb.paytminsider.dagger.module.PreferenceModule;
import com.bb.paytminsider.dagger.module.RoomModule;
import com.bb.paytminsider.dagger.module.VolleyModule;
import com.bb.paytminsider.dagger.scope.ApplicationScope;
import com.bb.paytminsider.dagger.viewmodel.ViewModelFactoryModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/*
Dagger Component to inject Application Wide dependencies
 */

@ApplicationScope
@Component(modules = {AndroidSupportInjectionModule.class, ActivityBuildersModule.class, GlideModule.class
        , ViewModelFactoryModule.class, PreferenceModule.class, RoomModule.class, AppModule.class, ExecutorModule.class, VolleyModule.class})
public interface AppComponent extends AndroidInjector<PaytmApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        Builder roomModule(RoomModule roomModule);

        AppComponent build();
    }
}
