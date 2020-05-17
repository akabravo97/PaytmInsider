package com.bb.paytminsider.room.model;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "event_table")
public class Event implements Serializable {
    @NonNull
    @PrimaryKey
    private String id;
    private long startTime;
    private String name;
    private String type;
    private String image;
    private String city;
    private long price;
    private double popularityScore;
    private String venueName;
    private String veneueDate;
    private String category;

    public Event(long startTime, String name, String type, String image, String city, long price, double popularityScore, String venueName, String veneueDate, String category) {
        this.startTime = startTime;
        this.name = name;
        this.type = type;
        this.image = image;
        this.city = city;
        this.price = price;
        this.popularityScore = popularityScore;
        this.venueName = venueName;
        this.veneueDate = veneueDate;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public double getPopularityScore() {
        return popularityScore;
    }

    public void setPopularityScore(double popularityScore) {
        this.popularityScore = popularityScore;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVeneueDate() {
        return veneueDate;
    }

    public void setVeneueDate(String veneueDate) {
        this.veneueDate = veneueDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
