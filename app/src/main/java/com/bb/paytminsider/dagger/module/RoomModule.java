package com.bb.paytminsider.dagger.module;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bb.paytminsider.dagger.scope.ApplicationScope;
import com.bb.paytminsider.room.EventDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {
    private EventDatabase eventDatabase;

    public RoomModule(Application application) {
        this.eventDatabase = Room.databaseBuilder(application, EventDatabase.class, "event_database").fallbackToDestructiveMigration().build();
    }

    @Provides
    @ApplicationScope
    EventDatabase providesEventDataBase() {
        return eventDatabase;
    }
}
