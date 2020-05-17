package com.bb.paytminsider.ui.fragments.callbacks;

import com.bb.paytminsider.room.model.Event;

public interface EventsActivityFragmentsCallbak {
    void triggerEventDetailsFragment(Event event);
    void backButtonPressedInEventDetailsFragment();
    void triggerSlideShowFragment(int visibility);
}
