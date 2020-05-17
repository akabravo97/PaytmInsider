package com.bb.paytminsider.room.model;

public class EventCardData {
    String imageUrl;
    String date;
    String price;
    String category;

    public EventCardData(String imageUrl, String date, String price, String category) {
        this.imageUrl = imageUrl;
        this.date = date;
        this.price = price;
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
