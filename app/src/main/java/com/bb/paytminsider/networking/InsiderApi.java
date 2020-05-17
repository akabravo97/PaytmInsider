package com.bb.paytminsider.networking;

import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.bb.paytminsider.room.model.Event;

import org.json.JSONException;

import java.util.List;

import javax.inject.Inject;

public class InsiderApi implements Api {
    private static final String TAG = "InsiderApi";
    private static final String INSIDER_API_BASE_URL = "https://api.insider.in/home?norm=1&filterBy=go-out";

    private RequestQueue requestQueue;

    @Inject
    public InsiderApi(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    /*
    Adds network request to volley request queue
     */

    @Override
    public void doEventsQuery(String city, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        Uri eventsUri = Uri.parse(INSIDER_API_BASE_URL).buildUpon().appendQueryParameter("city", city).build();
        requestQueue.add(new StringRequest(Request.Method.GET, eventsUri.toString(), responseListener, errorListener));
    }

    @Override
    public List<Event> parseEventsResponse(String eventJsonString) throws JSONException {
        return null;
    }
}
