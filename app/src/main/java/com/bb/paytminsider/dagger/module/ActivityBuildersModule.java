package com.bb.paytminsider.dagger.module;

import com.bb.paytminsider.ui.CityActivity;
import com.bb.paytminsider.ui.EventActivity;
import com.bb.paytminsider.ui.SplashActivity;
import com.bb.paytminsider.dagger.module.fragments.EventFragmentBuilderModule;
import com.bb.paytminsider.dagger.viewmodel.CityViewModelModule;
import com.bb.paytminsider.dagger.viewmodel.EventViewModelModule;
import com.bb.paytminsider.dagger.viewmodel.SplashViewModelModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = CityViewModelModule.class)
    abstract CityActivity contributeCityActivity();

    @ContributesAndroidInjector(modules = SplashViewModelModule.class)
    abstract SplashActivity contributeSplashActivity();

    @ContributesAndroidInjector(modules = {EventFragmentBuilderModule.class, EventViewModelModule.class})
    abstract EventActivity contributeEventActivity();
}
