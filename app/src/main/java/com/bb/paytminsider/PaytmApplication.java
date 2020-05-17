package com.bb.paytminsider;

import com.bb.paytminsider.dagger.component.DaggerAppComponent;
import com.bb.paytminsider.dagger.module.RoomModule;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class PaytmApplication extends DaggerApplication {
    /*
    Inject dagger dependency
     */
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).roomModule(new RoomModule(this)).build();
    }
}
