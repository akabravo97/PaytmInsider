package com.bb.paytminsider.dagger.module.activity;

import com.bb.paytminsider.ui.fragments.adapters.EventSubscriptionRecyclerAdapter;
import com.bb.paytminsider.ui.fragments.adapters.EventsCardAdapter;
import com.bb.paytminsider.ui.fragments.adapters.SlideshowPageAdapter;
import com.bumptech.glide.RequestManager;

import dagger.Module;
import dagger.Provides;

@Module
public class EventsActivityModule {

    @Provides
    static SlideshowPageAdapter provideSlideShowAdapter(RequestManager requestManager) {
        return new SlideshowPageAdapter(requestManager);
    }

    @Provides
    static EventsCardAdapter provideAdapter(RequestManager requestManager) {
        return new EventsCardAdapter(requestManager);
    }

    @Provides
    EventSubscriptionRecyclerAdapter provideEventSubscriptionRecyclerAdapter(RequestManager requestManager) {
        return new EventSubscriptionRecyclerAdapter(requestManager);
    }
}
