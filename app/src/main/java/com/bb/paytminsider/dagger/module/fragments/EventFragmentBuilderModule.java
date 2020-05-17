package com.bb.paytminsider.dagger.module.fragments;

import com.bb.paytminsider.ui.fragments.eventsfragment.EventDetailsFragment;
import com.bb.paytminsider.ui.fragments.eventsfragment.EventsFragment;
import com.bb.paytminsider.ui.fragments.eventsfragment.EventsSubscriptionFragment;
import com.bb.paytminsider.ui.fragments.eventsfragment.SlideshowFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class EventFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract EventsFragment contributesEventFragment();

    @ContributesAndroidInjector
    abstract SlideshowFragment contributesSlideshowFragment();

    @ContributesAndroidInjector
    abstract EventDetailsFragment contributesEventDetailsFragment();

    @ContributesAndroidInjector
    abstract EventsSubscriptionFragment contributeEventSubscriptionFragment();
}
