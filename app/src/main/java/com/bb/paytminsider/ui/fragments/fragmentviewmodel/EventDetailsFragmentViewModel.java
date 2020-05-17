package com.bb.paytminsider.ui.fragments.fragmentviewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bb.paytminsider.repository.EventRepository;
import com.bb.paytminsider.room.model.Event;
import com.bb.paytminsider.room.model.EventUser;

import java.util.List;

import javax.inject.Inject;

public class EventDetailsFragmentViewModel extends ViewModel {
    private static final String TAG = "EventDetailsFragmentVie";

    private EventRepository eventRepository;

    private MutableLiveData<Boolean> alarmStatus;

    @Inject
    public EventDetailsFragmentViewModel(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        alarmStatus = new MutableLiveData<>();
    }

    /*
    Updates local cache based on command recieved from Button
     */
    public void handleUserSubscriptionForEvent(Event event, HANDLE_SUBSCRIPTION handleSubscription) {
        if (handleSubscription == HANDLE_SUBSCRIPTION.DELETE_EVENT) {
            eventRepository.deleteEventForUser(event);
        } else {
            eventRepository.insertEventUser(event);
        }
    }

    /*
    Check if user is already subscribed to event
     */

    public LiveData<List<EventUser>> isUserSubscribedTOEvent(Event event) {
        return eventRepository.getUserSubscriptionForEvent(event);
    }

    public LiveData<Boolean> isAlarmSet() {
        return alarmStatus;
    }

    public enum HANDLE_SUBSCRIPTION {DELETE_EVENT, SUBSCRIBE_EVENT}
}
