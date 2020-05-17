package com.bb.paytminsider.dagger.viewmodel;

import androidx.lifecycle.ViewModel;

import com.bb.paytminsider.ui.viewmodels.EventActivityViewModel;
import com.bb.paytminsider.ui.fragments.fragmentviewmodel.EventDetailsFragmentViewModel;
import com.bb.paytminsider.ui.fragments.fragmentviewmodel.EventSubscriptionDetailViewModel;
import com.bb.paytminsider.ui.fragments.fragmentviewmodel.EventsFragmentViewModel;
import com.bb.paytminsider.ui.fragments.fragmentviewmodel.SlideshowFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class EventViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(EventActivityViewModel.class)
    public abstract ViewModel bindEventViewModel(EventActivityViewModel eventActivityViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EventsFragmentViewModel.class)
    public abstract ViewModel bindsEventsFragmentViewModel(EventsFragmentViewModel eventsFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SlideshowFragmentViewModel.class)
    public abstract ViewModel bindsSlideshowFragmentViewModel(SlideshowFragmentViewModel slideshowFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EventDetailsFragmentViewModel.class)
    public abstract ViewModel bindsEventDetailsFragmentViewModel(EventDetailsFragmentViewModel eventDetailsFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EventSubscriptionDetailViewModel.class)
    public abstract ViewModel bindEventSubscriptionDetailViewModel(EventSubscriptionDetailViewModel eventSubscriptionDetailViewModel);
}
