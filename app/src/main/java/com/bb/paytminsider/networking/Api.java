package com.bb.paytminsider.networking;

import com.android.volley.Response;
import com.bb.paytminsider.room.model.Event;

import org.json.JSONException;

import java.util.List;

public interface Api {
    /*
    Query on network to fetch latest events from API
     */
    void doEventsQuery(String query,
                      Response.Listener<String> responseListener,
                      Response.ErrorListener errorListener);
    List<Event> parseEventsResponse(String eventJsonString) throws JSONException;
}
