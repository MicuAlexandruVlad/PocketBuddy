package com.example.micua.pocketbuddy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.loopj.android.http.*;

public class Itinerary extends AppCompatActivity {

    private String destinationName, destinationID, startDate, endDate;
    private Intent intent;
    private double latitude, longitude;
    private API_KEYS api_keys;
    private ImageView banner;
    private List<ItineraryPlace> places;
    private ListView placesList;
    private ItineraryPlaceAdapter adapter;
    private int numActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        assert getSupportActionBar() != null;
        getSupportActionBar().hide();

        intent = getIntent();
        getDataFromParent(intent);
        api_keys = new API_KEYS();
        placesList = findViewById(R.id.lv_places);


        places = new ArrayList<>();
        adapter = new ItineraryPlaceAdapter(Itinerary.this, R.layout.places_list_item, places);


        placesList.setAdapter(adapter);
        placesList.setDivider(null);
        placesList.setDividerHeight(0);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(buildPlacesUrl(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Itinerary", response.toString());
                try {
                    JSONArray results = response.getJSONArray("results");
                    Log.d("Itinerary", "places " + results.length());
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
                        String description;
                        if (innerObj.isNull("vicinity"))
                            continue;
                        else
                            description = innerObj.getString("vicinity");
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
                        places.add(new ItineraryPlace(description, title, rating, "Expensive", reference, placeID, placeLat, placeLong));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



        placesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Itinerary.this, ItineraryPlaceDetails.class);
                assert adapter.getItem(i) != null;
                intent.putExtra("place_name", adapter.getItem(i).getTitle());
                intent.putExtra("place_rating", adapter.getItem(i).getRating());
                intent.putExtra("place_id", adapter.getItem(i).getPlaceID());
                intent.putExtra("place_latitude", adapter.getItem(i).getLatitude());
                intent.putExtra("place_longitude", adapter.getItem(i).getLongitude());
                startActivity(intent);
            }
        });

    }

    private void getDataFromParent(Intent intent) {
        destinationName = intent.getStringExtra("destination");
        destinationID = intent.getStringExtra("destination_id");
        startDate = intent.getStringExtra("start_date");
        endDate = intent.getStringExtra("end_date");
        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);
        numActivities = intent.getIntExtra("num_activities", 0);
        Log.d("Itinerary", latitude + "");
        Log.d("Itinerary", longitude + "");
    }

    private String buildPlacesUrl() {
        String link;
        String location = latitude + "," + longitude;
        String type = "art_gallery";
        link = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=" + api_keys.PLACES_WEB_API +
                "&location=" + location + "&radius=5000&rankby=prominence&type=" + type;
        Log.d("Itinerary", link);
        return link;
    }

    private String buildImageUrl(String refference) {
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + refference +
                "&key=" + api_keys.PLACES_WEB_API;
    }

    // TODO: method shows zero results in app but shows at least 20 in either browser or postman
    private String buildSearchUrl() {
        String link1;
        link1 = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=tourist+London&key=AIzaSyBF8xl2OQx4nsxXiIKostXetiQETgYVRsw";
        Log.d("Itinerary", link1);
        return link1;
    }


}
