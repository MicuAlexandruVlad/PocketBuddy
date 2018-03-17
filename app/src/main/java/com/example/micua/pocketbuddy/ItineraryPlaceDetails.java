package com.example.micua.pocketbuddy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import developer.shivam.crescento.CrescentoImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ItineraryPlaceDetails extends AppCompatActivity {
    private TextView placeName, placePhone, placeAddress, websiteTV;
    private RatingBar ratingBar;
    private Intent intent;
    private API_KEYS api_keys;
    private String placeID, address, phoneNumber, internationalPhoneNumber, rating, name, websiteUrl;
    private CrescentoImageView banner;
    private double latitude, longitude;
    private ExpandableHeightListView reviewsList;
    private List<Review> reviews;
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_place_details);

        assert getSupportActionBar() != null;
        getSupportActionBar().hide();
        intent = getIntent();
        latitude = Double.parseDouble(intent.getStringExtra("place_latitude"));
        longitude = Double.parseDouble(intent.getStringExtra("place_longitude"));
        api_keys = new API_KEYS();
        reviews = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(this, R.layout.review_list_item, reviews);

        placeName = findViewById(R.id.tv_place_name);
        ratingBar = findViewById(R.id.place_rating);
        banner = findViewById(R.id.crescento_banner);
        placePhone = findViewById(R.id.tv_place_phone);
        placeAddress = findViewById(R.id.tv_place_address);
        websiteTV = findViewById(R.id.tv_place_website);
        reviewsList = findViewById(R.id.lv_reviews);

        reviewsList.setAdapter(reviewAdapter);
        reviewsList.setExpanded(true);

        reviewsList.setDivider(null);
        reviewsList.setDividerHeight(1);

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
                        websiteUrl = result.getString("website");
                        JSONArray reviewsJSON = result.getJSONArray("reviews");
                        for (int i = 0; i < reviewsJSON.length(); i++) {
                            final String authorName, authorRating, authorReview, profilePhotoUrl, authorUrl, time;
                            authorName = reviewsJSON.getJSONObject(i).getString("author_name");
                            authorRating = reviewsJSON.getJSONObject(i).getString("rating");
                            authorReview = reviewsJSON.getJSONObject(i).getString("text");
                            profilePhotoUrl = reviewsJSON.getJSONObject(i).getString("profile_photo_url");
                            authorUrl = reviewsJSON.getJSONObject(i).getString("author_url");
                            time = reviewsJSON.getJSONObject(i).getString("relative_time_description");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    reviews.add(new Review(authorName, authorUrl, profilePhotoUrl, authorRating, time, authorReview));
                                    reviewAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                        final String reference = photos.getJSONObject(randomIndex(photos.length() - 1))
                                .getString("photo_reference");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Picasso.get().load(buildImageUrl(reference)).into(banner);
                                placeName.setText(name);
                                ratingBar.setRating(Float.parseFloat(rating));
                                placePhone.setText(internationalPhoneNumber);
                                placeAddress.setText(address);
                                websiteTV.setText(websiteUrl);
                                //setListViewHeightBasedOnChildren(reviewsList);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        websiteTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(websiteUrl));
                startActivity(intent);
            }
        });

        placeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

        placePhone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                //TODO: initiate phone call
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
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&photoreference=" + reference +
                "&key=" + api_keys.PLACES_WEB_API;
    }
    //TODO: causes error when high is 0
    private int randomIndex(int High) {
        Random r = new Random();
        int Low = 0;
        if (High == 0)
            return 0;
        return r.nextInt(High-Low) + Low;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
