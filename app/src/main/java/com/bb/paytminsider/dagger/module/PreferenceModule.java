package com.bb.paytminsider.dagger.module;

import com.bb.paytminsider.Constants;
import com.bb.paytminsider.dagger.qualifiers.PreferenceFileName;
import com.bb.paytminsider.dagger.scope.ApplicationScope;
import com.bb.paytminsider.preferences.AppPreferenceHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PreferenceModule {

    @Provides
    @ApplicationScope
    @PreferenceFileName
    String providePreferenceFileName(){
        return Constants.PREFERENCE_FILE_NAME;
    }
}
