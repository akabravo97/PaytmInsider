package com.bb.paytminsider.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bb.paytminsider.repository.EventRepository;

import javax.inject.Inject;

public class EventActivityViewModel extends ViewModel {

    private EventRepository eventRepository;

    @Inject
    public EventActivityViewModel(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void onCitySelected(String cityName) {
        eventRepository.setSelectedCity(cityName);
        eventRepository.setIsCitySelected(true);
    }

    public LiveData<String> fetchLatestEvents() {
        return eventRepository.fetchLatestEventsFromNetwork();
    }

    public void deleteAllUnsubscribedEvent() {
        eventRepository.deleteAllUnsubscribedEvent();
    }

    public void insertEvents(String response) {
        eventRepository.insertLatestEvents(response);
    }
}
