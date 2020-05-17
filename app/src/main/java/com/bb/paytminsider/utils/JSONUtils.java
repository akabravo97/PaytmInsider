package com.bb.paytminsider.utils;

import com.bb.paytminsider.room.model.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
Converts Network response json from String to List<Event>
 */

public class JSONUtils {
    public static List<Event> parseResponse(String response) {
        List<Event> eventList = new ArrayList<>();
        JSONArray popularArray;
        JSONArray featuredArray;
        try {

            JSONObject parent = new JSONObject(response);
            popularArray = parent.getJSONArray("popular");
            featuredArray = parent.getJSONArray("featured");
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("JSON Parsing failed");
        }

        for (int i = 0; i < featuredArray.length(); i++) {
            try {
                JSONObject jsonEvent = featuredArray.getJSONObject(i);
                String id = jsonEvent.getString("_id");
                long startTime = jsonEvent.getLong("min_show_start_time");
                String name = jsonEvent.getString("name");
                String type = "Featured";
                String image = jsonEvent.getString("horizontal_cover_image");
                String city = jsonEvent.getString("city");
                long price = jsonEvent.getLong("min_price");
                double popularityScore = jsonEvent.getDouble("popularity_score");
                String venueName = jsonEvent.getString("venue_name");
                String venueDate = jsonEvent.getString("venue_date_string");

                JSONObject categoryObject = jsonEvent.getJSONObject("category_id");
                String category = categoryObject.getString("name");

                Event event = new Event(startTime, name, type, image, city, price, popularityScore, venueName, venueDate, category);
                event.setId(id);
                eventList.add(event);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < popularArray.length(); i++) {
            try {
                JSONObject jsonEvent = popularArray.getJSONObject(i);
                String id = jsonEvent.getString("_id");
                long startTime = jsonEvent.getLong("min_show_start_time");
                String name = jsonEvent.getString("name");
                String type = "Popular";
                String image = jsonEvent.getString("horizontal_cover_image");
                String city = jsonEvent.getString("city");
                long price = jsonEvent.getLong("min_price");
                double popularityScore = jsonEvent.getDouble("popularity_score");
                String venueName = jsonEvent.getString("venue_name");
                String venueDate = jsonEvent.getString("venue_date_string");

                JSONObject categoryObject = jsonEvent.getJSONObject("category_id");
                String category = categoryObject.getString("name");

                Event event = new Event(startTime, name, type, image, city, price, popularityScore, venueName, venueDate, category);
                event.setId(id);
                eventList.add(event);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return eventList;
    }
}
