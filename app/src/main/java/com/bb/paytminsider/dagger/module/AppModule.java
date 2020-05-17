package com.bb.paytminsider.dagger.module;

import com.bb.paytminsider.dagger.scope.ApplicationScope;
import com.bb.paytminsider.executors.ApplicationExecutor;
import com.bb.paytminsider.networking.NetworkHelper;
import com.bb.paytminsider.preferences.AppPreferenceHelper;
import com.bb.paytminsider.repository.EventRepository;
import com.bb.paytminsider.room.EventDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides
    @ApplicationScope
    EventRepository providesEventRepository(NetworkHelper networkHelper, AppPreferenceHelper appPreferenceHelper, EventDatabase eventDatabase, ApplicationExecutor applicationExecutor) {
        return new EventRepository(networkHelper, appPreferenceHelper, eventDatabase,applicationExecutor);
    }
}
