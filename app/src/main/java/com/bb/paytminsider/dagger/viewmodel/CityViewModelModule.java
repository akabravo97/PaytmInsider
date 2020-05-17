package com.bb.paytminsider.dagger.viewmodel;

import androidx.lifecycle.ViewModel;

import com.bb.paytminsider.ui.viewmodels.CityActivityViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class CityViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CityActivityViewModel.class)
    public abstract ViewModel bindCityViewModel(CityActivityViewModel cityActivityViewModel);
}
