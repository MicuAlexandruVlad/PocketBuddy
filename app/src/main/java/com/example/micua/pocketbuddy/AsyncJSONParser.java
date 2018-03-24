package com.example.micua.pocketbuddy;

import android.util.Log;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class AsyncJSONParser {
    private String nextPageToken;
    private String Url;
    private List<ItineraryPlace> place;
    private API_KEYS api_keys;
    private boolean isParsingDone = false;

    public AsyncJSONParser(String Url, String nextPageToken){
        this.nextPageToken = nextPageToken;
        if (!nextPageToken.equalsIgnoreCase(""))
            this.Url = Url + "&pagetoken=" + nextPageToken;
        else
            this.Url = Url;
        this.place = new ArrayList<>();
        Log.d("Itinerary", this.Url + " async");
        this.api_keys = new API_KEYS();
        parseJSON(this.Url);
    }
// TODO: client does not enter onSuccess

    private void parseJSON(String Url) {
        if (!nextPageToken.equalsIgnoreCase("")) {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(Url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        if (response.isNull("next_page_token"))
                            nextPageToken = "";
                        else
                            nextPageToken = response.getString("next_page_token");
                        JSONArray results = response.getJSONArray("results");
                        Log.d("Itinerary", "places async " + results.length());
                        for (int i = 0; i < results.length(); i++) {
                            String reference;
                            JSONObject innerObj = results.getJSONObject(i);
                            if (innerObj.isNull("photos"))
                                continue;
                            else
                                reference = buildImageUrl(innerObj.getJSONArray("photos")
                                        .getJSONObject(0).getString("photo_reference"));
                            String rating;
                            if (innerObj.isNull("rating"))
                                continue;
                            else
                                rating = innerObj.getString("rating") + "";
                            boolean openNow = false;
                            if (innerObj.isNull("opening_hours"))
                                continue;
                            else
                                openNow = innerObj.getJSONObject("opening_hours").getBoolean("open_now");

                            String title;
                            if (innerObj.isNull("name"))
                                continue;
                            else
                                title = innerObj.getString("name");
                            String placeID;
                            if (innerObj.isNull("place_id"))
                                continue;
                            else
                                placeID = innerObj.getString("place_id");
                            String placeLat = innerObj.getJSONObject("geometry").getJSONObject("location").getString("lat");
                            String placeLong = innerObj.getJSONObject("geometry").getJSONObject("location").getString("lng");
                            Log.d("Itinerary", "placeID: " + placeID);
                            place.add(new ItineraryPlace(openNow, title, rating, "Expensive", reference, placeID, placeLat, placeLong));
                        }
                        isParsingDone = true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    isParsingDone = true;
                }
            });
        }
        else
            nextPageToken = "";

    }

    public boolean isParsingDone() {
        return isParsingDone;
    }

    private String buildImageUrl(String reference) {
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=600&photoreference=" + reference +
                "&key=" + api_keys.PLACES_WEB_API;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public List<ItineraryPlace> getPlace() {
        return place;
    }
}
