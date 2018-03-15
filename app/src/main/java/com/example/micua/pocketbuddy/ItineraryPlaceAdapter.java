package com.example.micua.pocketbuddy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ItineraryPlaceAdapter extends ArrayAdapter<ItineraryPlace> {
    public ItineraryPlaceAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ItineraryPlaceAdapter(Context context, int resource, List<ItineraryPlace> items) {
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.places_list_item, null);
        }

        ItineraryPlace place = getItem(position);

        if (place != null) {
            TextView title = v.findViewById(R.id.tv_title);
            TextView pricing = v.findViewById(R.id.tv_pricing);
            TextView rating = v.findViewById(R.id.tv_rating);
            TextView description = v.findViewById(R.id.tv_description);
            ImageView banner = v.findViewById(R.id.iv_banner);

            if (title != null)
                title.setText(place.getTitle());
            if (pricing != null)
                pricing.setText(place.getPricing());
            if (rating != null)
                rating.setText(place.getRating());
            if (description != null)
                description.setText(place.getDescription());
            if (banner != null)
                Picasso.get().load(place.getImgUrl()).into(banner);
        }

        return v;
    }
}