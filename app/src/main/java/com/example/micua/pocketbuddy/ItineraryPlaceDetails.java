package com.example.micua.pocketbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import developer.shivam.crescento.CrescentoImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ItineraryPlaceDetails extends AppCompatActivity {
    private TextView placeName, placeRating, placePhone, placeAddress;
    private Intent intent;
    private API_KEYS api_keys;
    private String placeID, address, phoneNumber, internationalPhoneNumber, rating, name, website;
    private CrescentoImageView banner;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_place_details);

        assert getSupportActionBar() != null;
        getSupportActionBar().hide();
        intent = getIntent();
        api_keys = new API_KEYS();

        placeName = findViewById(R.id.tv_place_name);
        placeRating = findViewById(R.id.tv_place_rating);
        banner = findViewById(R.id.crescento_banner);
        placePhone = findViewById(R.id.tv_place_phone);
        placeAddress = findViewById(R.id.tv_place_address);

        placeID = intent.getStringExtra("place_id");
        Log.d("ItineraryPlaceDetails", buildPlaceDetailsUrl(placeID));

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(buildPlaceDetailsUrl(placeID))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("ItineraryPlaceDetails", "Failure connecting to web page");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONObject result = jsonObject.getJSONObject("result");
                        JSONArray photos = result.getJSONArray("photos");
                        address = result.getString("formatted_address");
                        phoneNumber = result.getString("formatted_phone_number");
                        internationalPhoneNumber = result.getString("international_phone_number");
                        name = result.getString("name");
                        rating = result.getString("rating");
                        website = result.getString("website");
                        final String reference = photos.getJSONObject(randomIndex(photos.length() - 1))
                                .getString("photo_reference");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Picasso.get().load(buildImageUrl(reference)).into(banner);
                                placeName.setText(name);
                                placeRating.setText(rating);
                                placePhone.setText(internationalPhoneNumber);
                                placeAddress.setText(address);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private String buildPlaceDetailsUrl(String placeId) {
        String link;
        link = "https://maps.googleapis.com/maps/api/place/details/json?key=" + api_keys.PLACES_WEB_API +
                "&placeid=" + placeId + "&language=en";
        Log.d("Itinerary", link);
        return link;
    }

    private String buildImageUrl(String reference) {
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=600&photoreference=" + reference +
                "&key=" + api_keys.PLACES_WEB_API;
    }

    private int randomIndex(int High) {
        Random r = new Random();
        int Low = 0;
        return r.nextInt(High-Low) + Low;
    }
}
