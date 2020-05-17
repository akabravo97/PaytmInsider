package com.bb.paytminsider.room.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "event_user", foreignKeys = @ForeignKey(entity = Event.class, parentColumns = "id", childColumns = "fkEvent"))
public class EventUser {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String fkEvent;
    private String userName;

    public EventUser(String fkEvent, String userName) {
        this.fkEvent = fkEvent;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFkEvent() {
        return fkEvent;
    }

    public void setFkEvent(String fkEvent) {
        this.fkEvent = fkEvent;
    }
}
