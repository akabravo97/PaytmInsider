package com.bb.paytminsider.utils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.bb.paytminsider.room.model.Event;

import java.util.List;

/*
DiffUtil to check for difference in Events List, so that instead of notifying reccycler view of complete list
it will just notify at particular position where item was changed.
Improves performance and reduces flickering
 */

public class EventSubscriptionDiffUtil extends DiffUtil.Callback {

    private List<Event> oldEvents;
    private List<Event> newEvents;

    public EventSubscriptionDiffUtil(List<Event> oldEvents, List<Event> newEvents) {
        this.oldEvents = oldEvents;
        this.newEvents = newEvents;
    }

    @Override
    public int getOldListSize() {
        return oldEvents.size();
    }

    @Override
    public int getNewListSize() {
        return newEvents.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldEvents.get(oldItemPosition).getId().equals(newEvents.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldEvents.get(oldItemPosition).getId().equals(newEvents.get(newItemPosition).getId());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
