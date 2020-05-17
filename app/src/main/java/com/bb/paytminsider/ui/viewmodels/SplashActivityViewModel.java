package com.bb.paytminsider.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bb.paytminsider.repository.EventRepository;
import com.bb.paytminsider.utils.SingleLiveEvent;

import javax.inject.Inject;

public class SplashActivityViewModel extends ViewModel {
    private static final String TAG = "SplashActivityViewModel";
    private EventRepository eventRepository;

    @Inject
    public SplashActivityViewModel(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /*
      Invokes event repository to fetch latest events from network
     */

    public LiveData<String> fetchLatestEvents() {
        return eventRepository.fetchLatestEventsFromNetwork();
    }
    /*
    Inserts response from network to local cache
     */

    public void insertEvents(String response) {
        //eventRepository.clearEvents();
        eventRepository.insertLatestEvents(response);
    }

    /*
    Single shot live event to check if events were cached on start of Splash Activity
     */

    public SingleLiveEvent<Boolean> isEventsCached() {
        return eventRepository.isEventCached();
    }

}
