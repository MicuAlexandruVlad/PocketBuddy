package com.example.micua.pocketbuddy;

import android.content.Intent;
import android.os.AsyncTask;
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
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

        AsyncJSONParser jsonParser = new AsyncJSONParser(buildSearchUrl(), nextPage);
        jsonParser.execute();

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

    //Functional
    class AsyncJSONParser extends AsyncTask<Void, Void, Void>{
        String url;
        String token;
        String title, imgReference, rating, placeID, placeLat, placeLong;
        boolean openNow;

        public AsyncJSONParser(String url, String token) {
            this.token = token;
            Log.d("Itinerary", "AsyncTask token: " + token);
            if (!token.equalsIgnoreCase(""))
                this.url = url + "&pagetoken=" + token;
            else
                this.url = url;
            Log.d("Itinerary", "AsyncTask url: " + this.url);
        }
        //TODO: json parsing needs optimization; it skips way too many items in json if a field is missing
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("Itinerary", "AsyncTask start");
                try {
                    URL url = new URL(this.url);
                    Log.d("Itinerary", "AsyncTask url2: " + url);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder builder = new StringBuilder();

                    String inputString;
                    while ((inputString = bufferedReader.readLine()) != null) {
                        builder.append(inputString);
                    }
                    JSONObject response = new JSONObject(builder.toString());
                    Log.d("Itinerary", "AsyncTask response: " + response.toString());
                    if (response.isNull("next_page_token"))
                        token = "";
                    else
                        token = response.getString("next_page_token");
                    JSONArray results = response.getJSONArray("results");
                    Log.d("Itinerary", "places async " + results.length());
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject innerObj = results.getJSONObject(i);
                        if (innerObj.isNull("photos"))
                            continue;
                        else
                            imgReference = buildImageUrl(innerObj.getJSONArray("photos")
                                    .getJSONObject(0).getString("photo_reference"));
                        if (innerObj.isNull("rating"))
                            continue;
                        else
                            rating = innerObj.getString("rating") + "";
                        openNow = false;
                        if (innerObj.isNull("opening_hours"))
                            continue;
                        else
                            openNow = innerObj.getJSONObject("opening_hours").getBoolean("open_now");
                        if (innerObj.isNull("name"))
                            continue;
                        else
                            title = innerObj.getString("name");
                        if (innerObj.isNull("place_id"))
                            continue;
                        else
                            placeID = innerObj.getString("place_id");
                        placeLat = innerObj.getJSONObject("geometry").getJSONObject("location").getString("lat");
                        placeLong = innerObj.getJSONObject("geometry").getJSONObject("location").getString("lng");
                        places.add(new ItineraryPlace(openNow, title, rating, "Expensive", imgReference, placeID, placeLat, placeLong));
                        Log.d("Itinerary", "placeID: " + placeID);
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            nextPage = token;
            Log.d("Itinerary", "Next page token: " + nextPage);
            loadingAnimation.setVisibility(View.GONE);
            randomize.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
            Log.d("Itinerary", "AsyncTask stop");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!nextPage.equalsIgnoreCase(""))
                new AsyncJSONParser(buildSearchUrl(), token).execute();
        }
    }
}
