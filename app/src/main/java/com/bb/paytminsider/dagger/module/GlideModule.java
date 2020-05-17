package com.bb.paytminsider.dagger.module;

import android.app.Application;

import com.bb.paytminsider.R;
import com.bb.paytminsider.dagger.scope.ApplicationScope;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import dagger.Module;
import dagger.Provides;

@Module
public class GlideModule {

    @ApplicationScope
    @Provides
    static RequestOptions providesRequestOption(){
        return RequestOptions.placeholderOf(R.drawable.progress_animation).override(640,400).error(R.drawable.imageloadingerror);
    }

    @ApplicationScope
    @Provides
    static RequestManager providesRequestManager(Application application, RequestOptions requestOptions){
        return Glide.with(application).setDefaultRequestOptions(requestOptions);
    }
}
