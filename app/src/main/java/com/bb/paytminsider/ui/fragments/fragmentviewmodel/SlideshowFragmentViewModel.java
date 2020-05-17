package com.bb.paytminsider.ui.fragments.fragmentviewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bb.paytminsider.repository.EventRepository;
import com.bb.paytminsider.room.model.Event;

import java.util.List;

import javax.inject.Inject;

public class SlideshowFragmentViewModel extends ViewModel {

    private EventRepository eventRepository;

    @Inject
    public SlideshowFragmentViewModel(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /*
    Fetches top 5 popular events from local cache to set ViewPager slideshow
     */

    public LiveData<List<Event>> fetchPopularEventsFromCache() {
        return eventRepository.fetchtTrendingEventsFromCache();
    }

}
