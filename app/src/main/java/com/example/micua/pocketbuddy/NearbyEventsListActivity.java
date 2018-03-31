package com.example.micua.pocketbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class NearbyEventsListActivity extends AppCompatActivity {
    @BindView(R.id.lv_nearby_events)
    ListView nearbyEventsLV;
    @BindView(R.id.rl_loading_panel_nearby_events)
    RelativeLayout loadingPanel;

    private List<NearbyEvent> nearbyEvents;
    private NearbyEventAdapter adapter;
    public static final String TAG = "nearbyevents";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_events_list);

        ButterKnife.bind(this);

        assert getSupportActionBar() != null;
        getSupportActionBar().hide();

        loadingPanel.setVisibility(View.VISIBLE);

        nearbyEvents = new ArrayList<>();
        adapter = new NearbyEventAdapter(NearbyEventsListActivity.this,
                R.layout.activity_nearby_events_list, nearbyEvents);
        nearbyEventsLV.setAdapter(adapter);
        nearbyEventsLV.setDivider(null);
        nearbyEventsLV.setDividerHeight(0);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(buildEventsUrl(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d(TAG,"num events: " + response.getJSONObject("pagination")
                            .getString("object_count"));
                    JSONArray events = response.getJSONArray("events");
                    String name = "", description = "", startDate = "", endDate = "", ticketsUrl = "", dateCreated = "",
                            capacity = "", currency = "", status = "", logoId = "", venueId = "", organizerId = "", categoryId = "",
                            resourceUri = "", seriesId = "", imgUrl = "", edgeColor = "";
                    boolean isFree = false;
                    for (int i = 0; i < 20; i++) {
                        if (!events.getJSONObject(i).isNull("name"))
                            name = events.getJSONObject(i).getJSONObject("name").getString("text");
                        if (!events.getJSONObject(i).isNull("description"))
                            description = events.getJSONObject(i).getJSONObject("description").getString("text");
                        if (!events.getJSONObject(i).isNull("start"))
                            startDate = events.getJSONObject(i).getJSONObject("start").getString("local");
                        if (!events.getJSONObject(i).isNull("end"))
                            endDate = events.getJSONObject(i).getJSONObject("end").getString("local");
                        if (!events.getJSONObject(i).isNull("url"))
                            ticketsUrl = events.getJSONObject(i).getString("url");
                        if (!events.getJSONObject(i).isNull("created"))
                            dateCreated = events.getJSONObject(i).getString("created");
                        if (!events.getJSONObject(i).isNull("capacity"))
                            capacity = events.getJSONObject(i).getString("capacity");
                        if (!events.getJSONObject(i).isNull("currency"))
                            currency = events.getJSONObject(i).getString("currency");
                        if (!events.getJSONObject(i).isNull("status"))
                            status = events.getJSONObject(i).getString("status");
                        if (!events.getJSONObject(i).isNull("logo_id"))
                            logoId = events.getJSONObject(i).getString("logo_id");
                        if (!events.getJSONObject(i).isNull("venue_id"))
                            venueId = events.getJSONObject(i).getString("venue_id");
                        if (!events.getJSONObject(i).isNull("organizer_id"))
                            organizerId = events.getJSONObject(i).getString("organizer_id");
                        if (!events.getJSONObject(i).isNull("category_id"))
                            categoryId = events.getJSONObject(i).getString("category_id");
                        if (!events.getJSONObject(i).isNull("resource_uri"))
                            resourceUri = events.getJSONObject(i).getString("resource_uri");
                        if (!events.getJSONObject(i).isNull("series_id"))
                            seriesId = events.getJSONObject(i).getString("series_id");
                        if (!events.getJSONObject(i).isNull("logo"))
                            imgUrl = events.getJSONObject(i).getJSONObject("logo").getJSONObject("original").getString("url");
                        if (!events.getJSONObject(i).isNull("logo"))
                            edgeColor = events.getJSONObject(i).getJSONObject("logo").getString("edge_color");
                        if (!events.getJSONObject(i).isNull("is_free"))
                            isFree = events.getJSONObject(i).getBoolean("is_free");
                        NearbyEvent nearbyEvent = new NearbyEvent();
                        nearbyEvent.setCapacity(capacity);
                        nearbyEvent.setCategoryId(categoryId);
                        nearbyEvent.setCurrency(currency);
                        nearbyEvent.setDateCreated(dateCreated);
                        nearbyEvent.setName(name);
                        nearbyEvent.setDescription(description);
                        nearbyEvent.setLocalStartDateTime(startDate);
                        nearbyEvent.setLocalEndDateTime(endDate);
                        nearbyEvent.setTicketsUrl(ticketsUrl);
                        nearbyEvent.setStatus(status);
                        nearbyEvent.setLogoId(logoId);
                        nearbyEvent.setVenueId(venueId);
                        nearbyEvent.setOrganizerId(organizerId);
                        nearbyEvent.setResourceUri(resourceUri);
                        nearbyEvent.setSeriesId(seriesId);
                        nearbyEvent.setImgUrl(imgUrl);
                        nearbyEvent.setEdgeColor(edgeColor);
                        nearbyEvent.setFree(isFree);
                        nearbyEvents.add(nearbyEvent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        loadingPanel.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private String buildEventsUrl() {
        return "https://www.eventbriteapi.com/v3/events/search/?q=\"events in Bucharest\"&token=TXZ76O3QRHPWUQGFLTKO";
    }
}
