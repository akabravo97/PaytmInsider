package com.bb.paytminsider.dagger.viewmodel;

import androidx.lifecycle.ViewModel;

import com.bb.paytminsider.ui.viewmodels.SplashActivityViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class SplashViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SplashActivityViewModel.class)
    public abstract ViewModel bindSpashViewModel(SplashActivityViewModel splashActivityViewModel);
}
