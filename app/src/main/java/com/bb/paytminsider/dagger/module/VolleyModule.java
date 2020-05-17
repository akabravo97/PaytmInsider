package com.bb.paytminsider.dagger.module;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bb.paytminsider.dagger.scope.ApplicationScope;
import com.bb.paytminsider.networking.Api;
import com.bb.paytminsider.networking.InsiderApi;

import dagger.Module;
import dagger.Provides;

@Module
public class VolleyModule {
    @Provides
    @ApplicationScope
    public RequestQueue provideRequestQueue(Application application) {
        return Volley.newRequestQueue(application);
    }

    @Provides
    @ApplicationScope
    public Api providesInsiderApi(RequestQueue requestQueue) {
        return new InsiderApi(requestQueue);
    }
}
