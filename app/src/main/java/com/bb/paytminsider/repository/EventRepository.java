package com.bb.paytminsider.repository;

import android.os.Build;

import androidx.lifecycle.LiveData;

import com.bb.paytminsider.executors.ApplicationExecutor;
import com.bb.paytminsider.networking.NetworkHelper;
import com.bb.paytminsider.preferences.AppPreferenceHelper;
import com.bb.paytminsider.room.EventDatabase;
import com.bb.paytminsider.room.model.Event;
import com.bb.paytminsider.room.model.EventUser;
import com.bb.paytminsider.utils.JSONUtils;
import com.bb.paytminsider.utils.SingleLiveEvent;

import java.util.List;

import javax.inject.Inject;

public class EventRepository {
    private static final String TAG = "EventRepository";

    private NetworkHelper networkHelper;
    private AppPreferenceHelper appPreferenceHelper;
    private EventDatabase eventDatabase;
    private ApplicationExecutor applicationExecutor;
    private SingleLiveEvent<Boolean> isEventsCached;

    @Inject
    public EventRepository(NetworkHelper networkHelper, AppPreferenceHelper appPreferenceHelper, EventDatabase eventDatabase, ApplicationExecutor applicationExecutor) {
        this.networkHelper = networkHelper;
        this.appPreferenceHelper = appPreferenceHelper;
        this.eventDatabase = eventDatabase;
        this.applicationExecutor = applicationExecutor;


        isEventsCached = new SingleLiveEvent<>();
    }

    /*
    All the method names are self explainatary :)
     */

    public LiveData<List<Event>> fetchLatestEventsFromCache() {
        return eventDatabase.eventsDao().getAllEventsByPopularityScoreAsc();
    }

    public LiveData<String> fetchLatestEventsFromNetwork() {
        return networkHelper.getLatestEventsResponse(getSelectedCity());
    }

    public LiveData<List<Event>> fetchtTrendingEventsFromCache() {
        return eventDatabase.eventsDao().getTrendingEvents();
    }

    public void deleteEventForUser(Event event) {
        applicationExecutor.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                eventDatabase.eventUserDao().deleteEventForUser(event.getId());
            }
        });
    }

    public void deleteAllUnsubscribedEvent() {
        applicationExecutor.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                eventDatabase.eventsDao().deleteAllUnsubscribedEvent();
            }
        });
    }

    public LiveData<List<Event>> getAllSubscribedEvents() {
        return eventDatabase.eventsDao().getAllSubscribedEvents();
    }


    public void insertLatestEvents(String response) {
        List<Event> eventList = JSONUtils.parseResponse(response);
        for (Event event : eventList) {
            applicationExecutor.getDiskIO().execute(new Runnable() {
                @Override
                public void run() {
                    eventDatabase.eventsDao().insert(event);
                }
            });
        }
    }

    public void setIsCitySelected(boolean selected) {
        appPreferenceHelper.setIsCitySelected(selected);
    }

    public LiveData<List<Event>> getAllEventsByTypeAndStartTimeDesc(String type) {
        return eventDatabase.eventsDao().getAllEventsByTypeAndStartTimeDesc(type);
    }

    public LiveData<List<EventUser>> getUserSubscriptionForEvent(Event event) {
        return eventDatabase.eventUserDao().isUserSubscribedToEvent(event.getId());
    }

    public void insertEventUser(Event event) {
        applicationExecutor.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                EventUser eventUser = new EventUser(event.getId(), Build.USER);
                eventDatabase.eventUserDao().insertEventUser(eventUser);
            }
        });
    }

    public LiveData<List<Event>> getAllEventsByTypeAndStartTimeAsc(String type) {
        return eventDatabase.eventsDao().getAllEventsByTypeAndStartTimeAsc(type);
    }

    public LiveData<List<Event>> getAllEventsByTypeAndPriceDesc(String type) {
        return eventDatabase.eventsDao().getAllEventsByTypeAndPriceDesc(type);
    }

    public LiveData<List<Event>> getAllEventsByTypeAndPriceAsc(String type) {
        return eventDatabase.eventsDao().getAllEventsByTypeAndPriceAsc(type);
    }

    public LiveData<List<Event>> getAllEventsByTypeAndPopularityScoreDesc(String type) {
        return eventDatabase.eventsDao().getAllEventsByTypeAndPopularityScoreDesc(type);
    }

    public LiveData<List<Event>> getAllEventsByTypeAndPopularityScoreAsc(String type) {
        return eventDatabase.eventsDao().getAllEventsByTypeAndPopularityScoreAsc(type);
    }

    public boolean getIsCitySelected() {
        return appPreferenceHelper.isCitySelected();
    }

    public String getSelectedCity() {
        return appPreferenceHelper.getSelectedCity();
    }

    public void setSelectedCity(String city) {
        appPreferenceHelper.setSelectedCity(city);
    }

    public SingleLiveEvent<Boolean> isEventCached() {
        applicationExecutor.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                isEventsCached.postValue(eventDatabase.eventsDao().getTotalInsertedEvents() > 0);
            }
        });

        return isEventsCached;
    }
}