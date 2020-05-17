package com.bb.paytminsider.ui.fragments.fragmentviewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bb.paytminsider.repository.EventRepository;
import com.bb.paytminsider.room.model.Event;

import java.util.List;

import javax.inject.Inject;

public class EventSubscriptionDetailViewModel extends ViewModel {

    private EventRepository eventRepository;

    @Inject
    public EventSubscriptionDetailViewModel(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
    /*
    Polls local cache for fetching all events current user is subscribed to
     */
    public LiveData<List<Event>> getAllSubscribedEventsForUser() {
        return eventRepository.getAllSubscribedEvents();
    }

    /*
    delete a particular subscribed event from table when user selects unsubscribe option
     */

    public void deleteFromEventUser(Event event) {
        eventRepository.deleteEventForUser(event);
    }

}
