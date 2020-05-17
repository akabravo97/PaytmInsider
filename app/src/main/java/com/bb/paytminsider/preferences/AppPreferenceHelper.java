package com.bb.paytminsider.preferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.bb.paytminsider.dagger.qualifiers.PreferenceFileName;

import javax.inject.Inject;

public class AppPreferenceHelper implements PreferencesHelper {
    private static final String SELECTED_CITY = "SELECTED_CITY";
    private static final String IS_CITY_SELECTED = "IS_CITY_SELECTED";
    private final SharedPreferences sharedPreferences;

    @Inject
    public AppPreferenceHelper(Application application, @PreferenceFileName String fileName) {
        sharedPreferences = application.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }
    /*
    Holds preferences related I/O
     */

    /*
    Feteches user selected city
     */
    @Override
    public String getSelectedCity() {
        return sharedPreferences.getString(SELECTED_CITY, "mumbai");
    }

    @Override
    public void setSelectedCity(String city) {
        sharedPreferences.edit().putString(SELECTED_CITY, city).commit();
    }

    @Override
    public void setIsCitySelected(boolean selected) {
        sharedPreferences.edit().putBoolean(IS_CITY_SELECTED, selected).commit();
    }

    @Override
    public boolean isCitySelected() {
        return sharedPreferences.getBoolean(IS_CITY_SELECTED, false);
    }
}
