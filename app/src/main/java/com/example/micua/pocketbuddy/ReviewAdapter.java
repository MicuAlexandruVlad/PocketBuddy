package com.example.micua.pocketbuddy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class ReviewAdapter extends ArrayAdapter<Review> {

    public ReviewAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ReviewAdapter(Context context, int resource, List<Review> items) {
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.review_list_item, null);
        }

        Review review = getItem(position);

        if (review != null) {
            ImageView profile = v.findViewById(R.id.author_profile_pic);
            TextView name = v.findViewById(R.id.tv_author_name);
            TextView time = v.findViewById(R.id.tv_time);
            TextView reviewTV = v.findViewById(R.id.tv_author_review);
            RatingBar ratingBar = v.findViewById(R.id.author_rating);

            if (profile != null)
                Picasso.get().load(review.getProfilePhotoUrl()).into(profile);
            if (name != null)
                name.setText(review.getAuthorName());
            if (time != null)
                time.setText(review.getTime());
            if (reviewTV != null)
                reviewTV.setText(review.getText());
            if (ratingBar != null)
                ratingBar.setRating(Float.parseFloat(review.getRating()));
        }

        return v;
    }
}
