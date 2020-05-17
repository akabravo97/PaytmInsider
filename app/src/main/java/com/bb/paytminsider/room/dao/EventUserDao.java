package com.bb.paytminsider.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.bb.paytminsider.room.model.Event;
import com.bb.paytminsider.room.model.EventUser;

import java.util.List;

@Dao
public interface EventUserDao {
    @Transaction
    @Query("SELECT * FROM event_user WHERE fkEvent = :fkEvent")
    LiveData<List<EventUser>> isUserSubscribedToEvent(String fkEvent);

    @Transaction
    @Query("SELECT * FROM event_user")
    LiveData<List<EventUser>> getAllEventsForUser();

    @Insert
    void insertEventUser(EventUser eventUser);

    @Query("DELETE FROM event_user WHERE fkEvent = :fkEvent")
    void deleteEventForUser(String fkEvent);
}
