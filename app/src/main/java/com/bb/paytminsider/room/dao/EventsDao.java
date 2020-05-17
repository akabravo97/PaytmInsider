package com.bb.paytminsider.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bb.paytminsider.room.model.Event;

import java.util.List;

@Dao
public interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Event event);

    @Update
    void update(Event event);

    @Delete
    void delete(Event event);

    @Query("DELETE FROM event_table WHERE id NOT IN(SELECT fkEvent FROM event_user)")
    void deleteAllUnsubscribedEvent();


    @Query("SELECT * FROM event_table ORDER BY popularityScore")
    LiveData<List<Event>> getAllEventsByPopularityScoreAsc();

    @Query("SELECT * FROM event_table ORDER BY popularityScore DESC LIMIT 5")
    LiveData<List<Event>> getTrendingEvents();

    @Query("SELECT * FROM event_table WHERE type = :type ORDER BY startTime DESC")
    LiveData<List<Event>> getAllEventsByTypeAndStartTimeDesc(String type);

    @Query("SELECT * FROM event_table WHERE type = :type ORDER BY startTime")
    LiveData<List<Event>> getAllEventsByTypeAndStartTimeAsc(String type);

    @Query("SELECT * FROM event_table WHERE type = :type ORDER BY price DESC")
    LiveData<List<Event>> getAllEventsByTypeAndPriceDesc(String type);

    @Query("SELECT * FROM event_table WHERE type = :type ORDER BY price")
    LiveData<List<Event>> getAllEventsByTypeAndPriceAsc(String type);

    @Query("SELECT * FROM event_table WHERE type = :type ORDER BY popularityScore DESC")
    LiveData<List<Event>> getAllEventsByTypeAndPopularityScoreDesc(String type);

    @Query("SELECT * FROM event_table WHERE type = :type ORDER BY popularityScore")
    LiveData<List<Event>> getAllEventsByTypeAndPopularityScoreAsc(String type);

    @Query("SELECT * FROM event_table WHERE id IN(SELECT fkEvent FROM event_user)")
    LiveData<List<Event>> getAllSubscribedEvents();

    @Query("SELECT COUNT(*) FROM event_table")
    int getTotalInsertedEvents();
}
