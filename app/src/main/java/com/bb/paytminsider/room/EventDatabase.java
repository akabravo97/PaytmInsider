package com.bb.paytminsider.room;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.bb.paytminsider.room.dao.EventUserDao;
import com.bb.paytminsider.room.dao.EventsDao;
import com.bb.paytminsider.room.model.Event;
import com.bb.paytminsider.room.model.EventUser;

@Database(entities = {Event.class,EventUser.class}, version = 1, exportSchema = false)
public abstract class EventDatabase extends RoomDatabase {

    public abstract EventUserDao eventUserDao();
    public abstract EventsDao eventsDao();

    public EventDatabase() {

    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
