package com.example.micua.pocketbuddy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NearbyEventAdapter extends ArrayAdapter<NearbyEvent> {

    public NearbyEventAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public NearbyEventAdapter(Context context, int resource, List<NearbyEvent> items) {
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.nearby_events_list_item, null);
        }

        NearbyEvent event = getItem(position);

        if (event != null) {
            ImageView poster = v.findViewById(R.id.iv_nearby_events_item_poster);
            TextView tv = v.findViewById(R.id.tv_nearby_events_item_title);

            if (tv != null)
                tv.setText(event.getName());
            if (poster != null)
                Picasso.get().load(event.getImgUrl()).into(poster);
        }

        return v;
    }
}
