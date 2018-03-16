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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(buildPlacesUrl())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("Itinerary", "Failure getting data from server");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        JSONArray results = object.getJSONArray("results");
                        Log.d("Itinerary", buildPlacesUrl());
                        Log.d("Itinerary", results.length() + " places");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject innerObj = results.getJSONObject(i);
                            final String reference = buildImageUrl(innerObj.getJSONArray("photos")
                                    .getJSONObject(0).getString("photo_reference"));
                            final String rating = innerObj.getString("rating") + "";
                            final String description = innerObj.getString("vicinity");
                            final String title = innerObj.getString("name");
                            final String placeID = innerObj.getString("place_id");
                            Log.d("Itinerary", "placeID: " + placeID);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    places.add(new ItineraryPlace(description, title, rating, "Expensive", reference, placeID));
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
        System.out.println(link);
        return link;
    }

    private String buildImageUrl(String refference) {
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + refference +
                "&key=" + api_keys.PLACES_WEB_API;
    }


}
