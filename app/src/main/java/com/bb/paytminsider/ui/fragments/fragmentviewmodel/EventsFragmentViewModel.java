package com.bb.paytminsider.ui.fragments.fragmentviewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.bb.paytminsider.repository.EventRepository;
import com.bb.paytminsider.room.model.Event;

import java.util.List;

import javax.inject.Inject;

public class EventsFragmentViewModel extends ViewModel {

    private static final String TAG = "EventsFragmentViewModel";

    private EventRepository eventRepository;

    private MediatorLiveData<List<Event>> eventMutableLiveData;

    private String eventType = "Popular";
    private String methodType = "Popularity";
    private String sortType = "Descending";


    @Inject
    public EventsFragmentViewModel(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        eventMutableLiveData = new MediatorLiveData<>();
    }

    /*
    Get All events from local database
     */

    public LiveData<List<Event>> getLatestEventsFromLocalDatabase() {
        return eventMutableLiveData;
    }

    /*
    Updates card layout as specified by Spinners. Polls from local cache.
     */
    public void updateEventCardsOrder() {
        if (methodType.equals("Ascending")) {
            if (sortType.equals("Popularity")) {
                eventMutableLiveData.addSource(eventRepository.getAllEventsByTypeAndPopularityScoreAsc(eventType), new Observer<List<Event>>() {
                    @Override
                    public void onChanged(List<Event> events) {
                        eventMutableLiveData.postValue(events);
                    }
                });

            } else if (sortType.equals("Date")) {

                eventMutableLiveData.addSource(eventRepository.getAllEventsByTypeAndStartTimeAsc(eventType), new Observer<List<Event>>() {
                    @Override
                    public void onChanged(List<Event> events) {
                        eventMutableLiveData.postValue(events);
                    }
                });
            } else if (sortType.equals("Price")) {

                eventMutableLiveData.addSource(eventRepository.getAllEventsByTypeAndPriceAsc(eventType), new Observer<List<Event>>() {
                    @Override
                    public void onChanged(List<Event> events) {
                        eventMutableLiveData.postValue(events);
                    }
                });
            }
        } else {
            if (sortType.equals("Popularity")) {

                eventMutableLiveData.addSource(eventRepository.getAllEventsByTypeAndPopularityScoreDesc(eventType), new Observer<List<Event>>() {
                    @Override
                    public void onChanged(List<Event> events) {
                        eventMutableLiveData.postValue(events);
                    }
                });
            } else if (sortType.equals("Date")) {

                eventMutableLiveData.addSource(eventRepository.getAllEventsByTypeAndStartTimeDesc(eventType), new Observer<List<Event>>() {
                    @Override
                    public void onChanged(List<Event> events) {
                        eventMutableLiveData.postValue(events);
                    }
                });
            } else if (sortType.equals("Price")) {

                eventMutableLiveData.addSource(eventRepository.getAllEventsByTypeAndPriceDesc(eventType), new Observer<List<Event>>() {
                    @Override
                    public void onChanged(List<Event> events) {
                        eventMutableLiveData.postValue(events);
                    }
                });
            } else {

                eventMutableLiveData.addSource(eventRepository.getAllEventsByTypeAndPopularityScoreDesc(eventType), new Observer<List<Event>>() {
                    @Override
                    public void onChanged(List<Event> events) {
                        eventMutableLiveData.postValue(events);
                    }
                });
            }
        }
    }

    /*
    Caches selected values from spinner inside this ViewModel to prevent reduantant polling of local cache in case like device rotation
    As ViewModel lifecycle outlives Activity lifecycle
     */

    public void updateSpinnerMethodType(String methodType) {
        this.methodType = methodType;
    }

    public void updateSpinnerEventType(String eventType) {
        this.eventType = eventType;
    }

    public void updateSpinnerSortType(String sortType) {
        this.sortType = sortType;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
