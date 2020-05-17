package com.bb.paytminsider.dagger.module;

import com.bb.paytminsider.dagger.qualifiers.DiskExecutor;
import com.bb.paytminsider.dagger.qualifiers.MainExecutor;
import com.bb.paytminsider.dagger.qualifiers.NetworkExecutor;
import com.bb.paytminsider.dagger.scope.ApplicationScope;
import com.bb.paytminsider.executors.MainThreadExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;

@Module
public class ExecutorModule {

    @Provides
    @ApplicationScope
    @DiskExecutor
    Executor providesDiskExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @ApplicationScope
    @NetworkExecutor
    Executor providesNetworkExecutor() {
        return Executors.newFixedThreadPool(3);
    }

    @Provides
    @ApplicationScope
    @MainExecutor
    Executor providesMainExecutor() {
        return new MainThreadExecutor();
    }
}
