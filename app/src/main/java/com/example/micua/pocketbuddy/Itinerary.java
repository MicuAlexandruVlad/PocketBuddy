package com.example.micua.pocketbuddy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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

    private String destinationName, destinationID, startDate, endDate;
    private RelativeLayout loadingAnimation;
    private Intent intent;
    private double latitude, longitude;
    private API_KEYS api_keys;
    private ImageView banner;
    private Button randomize;
    private List<ItineraryPlace> places, places1, places2, places3, places4, places5, places6, places7,
                places8, places9, places10, places11, places12, places13, places14, places15;
    private List<Integer> randomNumbers;
    private ExpandableHeightListView placesDay1, placesDay2, placesDay3, placesDay4, placesDay5, placesDay6,
            placesDay7, placesDay8, placesDay9, placesDay10, placesDay11, placesDay12, placesDay13, placesDay14,
            placesDay15;
    private TextView placesDay1TV, placesDay2TV, placesDay3TV, placesDay4TV, placesDay5TV, placesDay6TV,
            placesDay7TV, placesDay8TV, placesDay9TV, placesDay10TV, placesDay11TV, placesDay12TV, placesDay13TV, placesDay14TV,
            placesDay15TV;
    private ItineraryPlaceAdapter adapter1, adapter2, adapter3, adapter4, adapter5, adapter6, adapter7, adapter8,
            adapter9, adapter10, adapter11, adapter12, adapter13, adapter14, adapter15;
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
        placesDay1 = findViewById(R.id.lv_places_day_1);
        placesDay1TV = findViewById(R.id.tv_day_1);
        placesDay2 = findViewById(R.id.lv_places_day_2);
        placesDay2TV = findViewById(R.id.tv_day_2);
        placesDay3 = findViewById(R.id.lv_places_day_3);
        placesDay3TV = findViewById(R.id.tv_day_3);
        placesDay4 = findViewById(R.id.lv_places_day_4);
        placesDay4TV = findViewById(R.id.tv_day_4);
        placesDay5 = findViewById(R.id.lv_places_day_5);
        placesDay5TV = findViewById(R.id.tv_day_5);
        placesDay6 = findViewById(R.id.lv_places_day_6);
        placesDay6TV = findViewById(R.id.tv_day_6);
        placesDay7 = findViewById(R.id.lv_places_day_7);
        placesDay7TV = findViewById(R.id.tv_day_7);
        placesDay8 = findViewById(R.id.lv_places_day_8);
        placesDay8TV = findViewById(R.id.tv_day_8);
        placesDay9 = findViewById(R.id.lv_places_day_9);
        placesDay9TV = findViewById(R.id.tv_day_9);
        placesDay10 = findViewById(R.id.lv_places_day_10);
        placesDay10TV = findViewById(R.id.tv_day_10);
        placesDay11 = findViewById(R.id.lv_places_day_11);
        placesDay11TV = findViewById(R.id.tv_day_11);
        placesDay12 = findViewById(R.id.lv_places_day_12);
        placesDay12TV = findViewById(R.id.tv_day_12);
        placesDay13 = findViewById(R.id.lv_places_day_13);
        placesDay13TV = findViewById(R.id.tv_day_13);
        placesDay14 = findViewById(R.id.lv_places_day_14);
        placesDay14TV = findViewById(R.id.tv_day_14);
        placesDay15 = findViewById(R.id.lv_places_day_15);
        placesDay15TV = findViewById(R.id.tv_day_15);
        randomize = findViewById(R.id.btn_randomize_itinerary);
        loadingAnimation = findViewById(R.id.loading_panel);

        loadingAnimation.setVisibility(View.VISIBLE);
        initLists();
        initAdapters();

        setListsDivider();
        setListsVisibility();

        randomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });

        //TODO: make itinerary for separate days

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(buildSearchUrl(destinationName, ""), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Itinerary", response.toString());
                try {
                    String nextPage = response.getString("next_page_token");
                    JSONArray results = response.getJSONArray("results");
                    //randomNumbers = getRandom(results.length());
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

                    }
                    distributeData();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setListsVisibility();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        placesDay1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Itinerary.this, ItineraryPlaceDetails.class);
                assert adapter1.getItem(i) != null;
                intent.putExtra("place_name", adapter1.getItem(i).getTitle());
                intent.putExtra("place_rating", adapter1.getItem(i).getRating());
                intent.putExtra("place_id", adapter1.getItem(i).getPlaceID());
                intent.putExtra("place_latitude", adapter1.getItem(i).getLatitude());
                intent.putExtra("place_longitude", adapter1.getItem(i).getLongitude());
                startActivity(intent);
            }
        });

    }

    //TODO: fix data not showing in multiple lists

    private void initAdapters() {
        adapter1 = new ItineraryPlaceAdapter(Itinerary.this, R.layout.places_list_item, places1);
        adapter2 = new ItineraryPlaceAdapter(Itinerary.this, R.layout.places_list_item, places2);
        adapter3 = new ItineraryPlaceAdapter(Itinerary.this, R.layout.places_list_item, places3);
        adapter4 = new ItineraryPlaceAdapter(Itinerary.this, R.layout.places_list_item, places4);
        adapter5 = new ItineraryPlaceAdapter(Itinerary.this, R.layout.places_list_item, places5);
        adapter6 = new ItineraryPlaceAdapter(Itinerary.this, R.layout.places_list_item, places6);
        adapter7 = new ItineraryPlaceAdapter(Itinerary.this, R.layout.places_list_item, places7);
        adapter8 = new ItineraryPlaceAdapter(Itinerary.this, R.layout.places_list_item, places8);
        adapter9 = new ItineraryPlaceAdapter(Itinerary.this, R.layout.places_list_item, places9);
        adapter10 = new ItineraryPlaceAdapter(Itinerary.this, R.layout.places_list_item, places10);
        adapter11 = new ItineraryPlaceAdapter(Itinerary.this, R.layout.places_list_item, places11);
        adapter12 = new ItineraryPlaceAdapter(Itinerary.this, R.layout.places_list_item, places12);
        adapter13 = new ItineraryPlaceAdapter(Itinerary.this, R.layout.places_list_item, places13);
        adapter14 = new ItineraryPlaceAdapter(Itinerary.this, R.layout.places_list_item, places14);
        adapter15 = new ItineraryPlaceAdapter(Itinerary.this, R.layout.places_list_item, places15);
    }

    private void initLists() {
        places = new ArrayList<>();
        places1 = new ArrayList<>();
        places2 = new ArrayList<>();
        places3 = new ArrayList<>();
        places4 = new ArrayList<>();
        places5 = new ArrayList<>();
        places6 = new ArrayList<>();
        places7 = new ArrayList<>();
        places8 = new ArrayList<>();
        places9 = new ArrayList<>();
        places10 = new ArrayList<>();
        places11 = new ArrayList<>();
        places12 = new ArrayList<>();
        places13 = new ArrayList<>();
        places14 = new ArrayList<>();
        places15 = new ArrayList<>();
    }

    private void setListsVisibility() {
        if (places1.size() == 0)
            placesDay1.setVisibility(View.GONE);
        else {
            placesDay1.setVisibility(View.VISIBLE);
            placesDay1TV.setVisibility(View.VISIBLE);
            Log.d("Lists sizes", "Day 1: " + places1.size());
        }
        if (places2.size() == 0)
            placesDay2.setVisibility(View.GONE);
        else {
            placesDay2.setVisibility(View.VISIBLE);
            placesDay2TV.setVisibility(View.VISIBLE);
            Log.d("Lists sizes", "Day 2: " + places2.size());
        }
        if (places3.size() == 0)
            placesDay3.setVisibility(View.GONE);
        else{
            placesDay3.setVisibility(View.VISIBLE);
            placesDay3TV.setVisibility(View.VISIBLE);
            Log.d("Lists sizes", "Day 3: " + places3.size());
        }
        if (places4.size() == 0)
            placesDay4.setVisibility(View.GONE);
        else{
            placesDay4.setVisibility(View.VISIBLE);
            placesDay4TV.setVisibility(View.VISIBLE);
            Log.d("Lists sizes", "Day 4: " + places4.size());
        }
        if (places5.size() == 0)
            placesDay5.setVisibility(View.GONE);
        else{
            placesDay5.setVisibility(View.VISIBLE);
            placesDay5TV.setVisibility(View.VISIBLE);
        }
        if (places6.size() == 0)
            placesDay6.setVisibility(View.GONE);
        else{
            placesDay6.setVisibility(View.VISIBLE);
            placesDay6TV.setVisibility(View.VISIBLE);
        }
        if (places7.size() == 0)
            placesDay7.setVisibility(View.GONE);
        else{
            placesDay7.setVisibility(View.VISIBLE);
            placesDay7TV.setVisibility(View.VISIBLE);
        }
        if (places8.size() == 0)
            placesDay8.setVisibility(View.GONE);
        else{
            placesDay8.setVisibility(View.VISIBLE);
            placesDay8TV.setVisibility(View.VISIBLE);
        }
        if (places9.size() == 0)
            placesDay9.setVisibility(View.GONE);
        else{
            placesDay9.setVisibility(View.VISIBLE);
            placesDay9TV.setVisibility(View.VISIBLE);
        }
        if (places10.size() == 0)
            placesDay10.setVisibility(View.GONE);
        else{
            placesDay10.setVisibility(View.VISIBLE);
            placesDay10TV.setVisibility(View.VISIBLE);
        }
        if (places11.size() == 0)
            placesDay11.setVisibility(View.GONE);
        else{
            placesDay11.setVisibility(View.VISIBLE);
            placesDay11TV.setVisibility(View.VISIBLE);
        }
        if (places12.size() == 0)
            placesDay12.setVisibility(View.GONE);
        else{
            placesDay12.setVisibility(View.VISIBLE);
            placesDay12TV.setVisibility(View.VISIBLE);
        }
        if (places13.size() == 0)
            placesDay13.setVisibility(View.GONE);
        else{
            placesDay13.setVisibility(View.VISIBLE);
            placesDay13TV.setVisibility(View.VISIBLE);
        }
        if (places14.size() == 0)
            placesDay14.setVisibility(View.GONE);
        else{
            placesDay14.setVisibility(View.VISIBLE);
            placesDay14TV.setVisibility(View.VISIBLE);
        }
        if (places15.size() == 0)
            placesDay15.setVisibility(View.GONE);
        else{
            placesDay15.setVisibility(View.VISIBLE);
            placesDay15TV.setVisibility(View.VISIBLE);
        }
        loadingAnimation.setVisibility(View.GONE);
    }

    private void setListsDivider() {
        placesDay1.setAdapter(adapter1);
        placesDay1.setDivider(null);
        placesDay1.setDividerHeight(0);
        placesDay2.setAdapter(adapter2);
        placesDay2.setDivider(null);
        placesDay2.setDividerHeight(0);
        placesDay3.setAdapter(adapter3);
        placesDay3.setDivider(null);
        placesDay3.setDividerHeight(0);
        placesDay4.setAdapter(adapter4);
        placesDay4.setDivider(null);
        placesDay4.setDividerHeight(0);
        placesDay5.setAdapter(adapter5);
        placesDay5.setDivider(null);
        placesDay5.setDividerHeight(0);
        placesDay6.setAdapter(adapter6);
        placesDay6.setDivider(null);
        placesDay6.setDividerHeight(0);
        placesDay7.setAdapter(adapter7);
        placesDay7.setDivider(null);
        placesDay7.setDividerHeight(0);
        placesDay8.setAdapter(adapter8);
        placesDay8.setDivider(null);
        placesDay8.setDividerHeight(0);
        placesDay9.setAdapter(adapter9);
        placesDay9.setDivider(null);
        placesDay9.setDividerHeight(0);
        placesDay10.setAdapter(adapter10);
        placesDay10.setDivider(null);
        placesDay10.setDividerHeight(0);
        placesDay11.setAdapter(adapter11);
        placesDay11.setDivider(null);
        placesDay11.setDividerHeight(0);
        placesDay12.setAdapter(adapter12);
        placesDay12.setDivider(null);
        placesDay12.setDividerHeight(0);
        placesDay13.setAdapter(adapter13);
        placesDay13.setDivider(null);
        placesDay13.setDividerHeight(0);
        placesDay14.setAdapter(adapter14);
        placesDay14.setDivider(null);
        placesDay14.setDividerHeight(0);
        placesDay15.setAdapter(adapter15);
        placesDay15.setDivider(null);
        placesDay15.setDividerHeight(0);
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

    private String buildImageUrl(String reference) {
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=600&photoreference=" + reference +
                "&key=" + api_keys.PLACES_WEB_API;
    }

    private String buildSearchUrl(String placeName, String token) {
        String link1;
        String formattedLocation = placeName.replace(" ", "+");
        if (token.equalsIgnoreCase(""))
            link1 = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=tourist+attractions+in+" +
                    formattedLocation + "&key=" + api_keys.PLACES_WEB_API;
        else
            link1 = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=tourist+attractions+in+" +
                    formattedLocation + "&key=" + api_keys.PLACES_WEB_API + "&pagetoken=" + token;
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

    private void distributeData() {
        int numberOfPlaces = places.size();
        int i = 0;
        while (i < numberOfPlaces) {
            i = addPlaces(places1, i);
            i = addPlaces(places2, i);
            i = addPlaces(places3, i);
            i = addPlaces(places4, i);
            i = addPlaces(places5, i);
            i = addPlaces(places6, i);
            i = addPlaces(places7, i);
            i = addPlaces(places1, i);
            i = addPlaces(places8, i);
            i = addPlaces(places9, i);
            i = addPlaces(places10, i);
            i = addPlaces(places11, i);
            i = addPlaces(places12, i);
            i = addPlaces(places13, i);
            i = addPlaces(places14, i);
            i = addPlaces(places15, i);
        }
        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();
        adapter4.notifyDataSetChanged();
        adapter5.notifyDataSetChanged();
        adapter6.notifyDataSetChanged();
        adapter7.notifyDataSetChanged();
        adapter8.notifyDataSetChanged();
        adapter9.notifyDataSetChanged();
        adapter10.notifyDataSetChanged();
        adapter11.notifyDataSetChanged();
        adapter12.notifyDataSetChanged();
        adapter13.notifyDataSetChanged();
        adapter14.notifyDataSetChanged();
        adapter15.notifyDataSetChanged();

    }

    private int addPlaces(List<ItineraryPlace> placeList, int i) {
        int addedData = 0;
        while (addedData <= numActivities && i < places.size()) {
            placeList.add(new ItineraryPlace(places.get(i).getDescription(), places.get(i).getTitle(),
                    places.get(i).getRating(), places.get(i).getPricing(), places.get(i).getImgUrl(),
                    places.get(i).getPlaceID(), places.get(i).getLatitude(), places.get(i).getLongitude()));
            i++;
            addedData++;
        }
        return i;
    }
}
