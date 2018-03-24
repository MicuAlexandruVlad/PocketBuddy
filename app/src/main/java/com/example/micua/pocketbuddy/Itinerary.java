package com.example.micua.pocketbuddy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.loopj.android.http.*;

public class Itinerary extends AppCompatActivity {

    private String destinationName, destinationID, startDate, endDate, openNow, nextPage = "";
    private RelativeLayout loadingAnimation;
    private Intent intent;
    private double latitude, longitude;
    private boolean isNearbySearch, isTextSearch;
    private API_KEYS api_keys;
    private ImageView banner;
    private Button randomize;
    private List<ItineraryPlace> places;
    private List<Integer> randomNumbers;
    private ExpandableHeightListView placesLV;
    private ItineraryPlaceAdapter adapter;
    private int numActivities;
    private AsyncJSONParser asyncJSONParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        assert getSupportActionBar() != null;
        getSupportActionBar().hide();

        intent = getIntent();
        getDataFromParent(intent);
        api_keys = new API_KEYS();
        randomize = findViewById(R.id.btn_randomize_itinerary);
        loadingAnimation = findViewById(R.id.loading_panel);
        placesLV = findViewById(R.id.lv_places);
        loadingAnimation.setVisibility(View.VISIBLE);

        randomize.setVisibility(View.GONE);

        places = new ArrayList<>();
        adapter = new ItineraryPlaceAdapter(Itinerary.this, R.layout.places_list_item, places);
        placesLV.setAdapter(adapter);
        placesLV.setDivider(null);
        placesLV.setDividerHeight(0);
        placesLV.setExpanded(true);

        randomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });

        //TODO: make itinerary for separate days

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://maps.googleapis.com/maps/api/place/textsearch/json?query=tourist+attractions+in+Paris&key=AIzaSyAIxhOps5bZL5qE6J5abGn3JLPyaHoSS_M&pagetoken=CpQCBQEAAGzsSHWxTowwEcImYZ2la0oFspH6YMxPoSblC7-SWU2C_4wJLo20mmuIN58FpJmq-d8lvn-_bs1gPkSX_XmbMXN6QFco0phcTAX5vJlYLi-A0xPAoHYOuLE8fB_poYJUxzv9eUkFBHM1qf_o5PoHtB5IA5auvWXVzSiZYvLF869nmngQoVFFH88SjWnTqKn1o2cPK_tiHShlEbox10gwMw-MenoKB_INJKFNnZwzVXf0VZw2AICAIiiz7-vdhSk2kP0-uLRYcLeObbj9nB3UeEIKdni12-FYJdO71zc-U4Ul_SJgfYSCcnT4XN8-RhUYtFYND9F0fnOx3ij-MNnX2sQvrP65-dNVEen7yRcv-cIXEhAVRgtVc15CEnOnI087KZ3QGhT8dDzYowACMTKzrkMvn8_5_u5cKg", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Itinerary", response.toString());
                try {
                    if (response.isNull("next_page_token"))
                        nextPage = "";
                    else
                        nextPage = response.getString("next_page_token");
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
                        boolean openNow = false;
                        if (isTextSearch) {
                            if (innerObj.isNull("opening_hours"))
                                continue;
                            else
                                openNow = innerObj.getJSONObject("opening_hours").getBoolean("open_now");
                        }
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
                        places.add(new ItineraryPlace(openNow, title, rating, "Expensive", reference, placeID, placeLat, placeLong));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadingAnimation.setVisibility(View.GONE);
                                randomize.setVisibility(View.VISIBLE);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

        /**asyncJSONParser = new AsyncJSONParser(buildSearchUrl(), nextPage);
        while (true) {
            Log.d("Itinerary", "Loop");
            if (asyncJSONParser.isParsingDone()) {
                Log.d("Itinerary", "Loop ended");
                break;
            }
        }
        places.addAll(asyncJSONParser.getPlace());
        adapter.notifyDataSetChanged();
        nextPage = asyncJSONParser.getNextPageToken();
        if (asyncJSONParser.getNextPageToken().equalsIgnoreCase("")) {
            Log.d("Itinerary", "Loop ended");
        }**/

        placesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Itinerary.this, ItineraryPlaceDetails.class);
                assert adapter.getItem(i) != null;
                intent.putExtra("place_name", adapter.getItem(i).getTitle());
                intent.putExtra("place_rating", adapter.getItem(i).getRating());
                intent.putExtra("place_id", adapter.getItem(i).getPlaceID());
                intent.putExtra("place_latitude", adapter.getItem(i).getLatitude());
                intent.putExtra("place_longitude", adapter.getItem(i).getLongitude());
                intent.putExtra("place_open_now", adapter.getItem(i).getOpenNow());
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
        isNearbySearch = true;
        isTextSearch = false;
        String link;
        String location = latitude + "," + longitude;
        String type = "art_gallery";
        link = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=" + api_keys.PLACES_WEB_API +
                "&location=" + location + "&radius=5000&rankby=prominence&type=" + type;
        Log.d("Itinerary", link);
        return link;
    }

    private String buildImageUrl(String reference) {
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=600&photoreference=" + reference +
                "&key=" + api_keys.PLACES_WEB_API;
    }

    private String buildSearchUrl() {
        isNearbySearch = false;
        isTextSearch = true;
        String link1;
        String formattedLocation = destinationName.replace(" ", "+");
        link1 = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=tourist+attractions+in+" +
                formattedLocation + "&key=" + api_keys.PLACES_WEB_API;
        Log.d("Itinerary", link1);
        return link1;
    }

    private ArrayList<Integer> getRandom(int max) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return numbers;
    }
}
