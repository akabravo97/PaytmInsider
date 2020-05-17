package com.bb.paytminsider.executors;

import com.bb.paytminsider.dagger.qualifiers.DiskExecutor;
import com.bb.paytminsider.dagger.qualifiers.MainExecutor;
import com.bb.paytminsider.dagger.qualifiers.NetworkExecutor;
import com.bb.paytminsider.dagger.scope.ApplicationScope;

import java.util.concurrent.Executor;

import javax.inject.Inject;

@ApplicationScope
public class ApplicationExecutor {
    private Executor diskIO;
    private Executor networkIO;
    private Executor mainExecutor;

    /*
    Returns Executor threads for Handling multithreaded operation in device
     */
    @Inject
    public ApplicationExecutor(@DiskExecutor Executor diskIO, @NetworkExecutor Executor networkIO, @MainExecutor Executor mainExecutor) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainExecutor = mainExecutor;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    public Executor getMainExecutor() {
        return mainExecutor;
    }
}
