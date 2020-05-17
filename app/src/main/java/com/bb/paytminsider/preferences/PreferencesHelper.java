package com.bb.paytminsider.preferences;

public interface PreferencesHelper {
    String getSelectedCity();
    void setSelectedCity(String city);
    void setIsCitySelected(boolean selected);
    boolean isCitySelected();
}
