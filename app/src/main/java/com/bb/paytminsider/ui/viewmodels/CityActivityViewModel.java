package com.bb.paytminsider.ui.viewmodels;

import androidx.lifecycle.ViewModel;

import com.bb.paytminsider.repository.EventRepository;

import javax.inject.Inject;

public class CityActivityViewModel extends ViewModel {
    private static final String TAG = "CityActivityViewModel";

    private EventRepository eventRepository;

    @Inject
    public CityActivityViewModel(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /*
    Updates the preference of selected city
     */
    public void onCitySelected(String cityName) {
        eventRepository.setSelectedCity(cityName);
        eventRepository.setIsCitySelected(true);
    }

    /*
    Checks if city is already selected
     */

    public boolean isCitySelected() {
        return eventRepository.getIsCitySelected();
    }
}
