package com.bb.paytminsider.networking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import javax.inject.Inject;

public class NetworkHelper {

    private static final String TAG = "NetworkHelper";
    private InsiderApi insiderApi;

    MutableLiveData<String> responseEventsLiveData;

    @Inject
    public NetworkHelper(InsiderApi insiderApi) {
        this.insiderApi = insiderApi;
        responseEventsLiveData = new MutableLiveData<>();
    }

    /*
    Calls API interface to fetch data for selected city
    If NetworkError occurs, prefixes message with ERROR to filter out in response
     */
    public LiveData<String> getLatestEventsResponse(String city) {
        insiderApi.doEventsQuery(city, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                responseEventsLiveData.setValue(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    responseEventsLiveData.setValue("ERROR:Can't connect to internet.");
                } else if (error instanceof AuthFailureError) {
                    responseEventsLiveData.setValue("ERROR:Unknown error occured. Please try after sometime.");
                } else if (error instanceof ServerError) {
                    responseEventsLiveData.setValue("ERROR:Unknown error occured. Please try after sometime");
                } else if (error instanceof TimeoutError) {
                    responseEventsLiveData.setValue("ERROR:Request timed out.");
                }
            }
        });
        return responseEventsLiveData;
    }
}
