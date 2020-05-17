package com.bb.paytminsider.executors;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

public class MainThreadExecutor implements Executor {
    Handler mainHandler = new Handler(Looper.getMainLooper());

    /*
    Helper method to execute piece of code on MainThread
     */

    @Override
    public void execute(Runnable command) {
        mainHandler.post(command);
    }
}
