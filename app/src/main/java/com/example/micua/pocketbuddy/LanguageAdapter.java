package com.example.micua.pocketbuddy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class LanguageAdapter extends ArrayAdapter<Language>{
    public LanguageAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public LanguageAdapter(Context context, int resource, List<Language> items) {
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.lang_item, null);
        }

        Language l = getItem(position);

        if (l != null) {
            TextView tv = v.findViewById(R.id.tv_list_language);

            if (tv != null)
                tv.setText(l.getLang());
        }

        return v;
    }
}
