package com.example.micua.pocketbuddy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private API_KEYS api_keys;
    private ImageView banner;
    private List<ItineraryPlace> places;
    private ListView placesList;
    private ItineraryPlaceAdapter adapter;

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
        adapter = new ItineraryPlaceAdapter(this, R.layout.places_list_item, places);

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
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject innerObj = results.getJSONObject(i);
                            String reference = buildImageUrl(innerObj.getJSONArray("photos")
                                    .getJSONObject(0).getString("photo_reference"));
                            String rating = innerObj.getString("rating") + "";
                            String description = innerObj.getString("vicinity");
                            String title = innerObj.getString("name");
                            places.add(new ItineraryPlace(description, title, rating, "Expensive", reference));
                            System.out.println(rating);
                            System.out.println(title);
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
            }
        });

        placesList.setAdapter(adapter);
    }

    private void getDataFromParent(Intent intent) {
        destinationName = intent.getStringExtra("destination");
        destinationID = intent.getStringExtra("destination_id");
        startDate = intent.getStringExtra("start_date");
        endDate = intent.getStringExtra("end_date");
    }

    private String buildPlacesUrl() {
        return "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyBF8xl2OQx4nsxXiIKostXetiQETgYVRsw&location=48.864716, 2.349014&radius=5000&rankby=prominence&type=night_club";
    }

    private String buildImageUrl(String refference) {
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + refference +
                "&key=" + api_keys.PLACES_WEB_API;
    }
}
