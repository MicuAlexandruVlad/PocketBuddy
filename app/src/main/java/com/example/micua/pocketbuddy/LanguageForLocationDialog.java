package com.example.micua.pocketbuddy;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class LanguageForLocationDialog extends Dialog{
    private String country, selectedLanguage;
    private CountryCodes countryCodes;
    private LanguageAdapter adapter;
    private List<Language> languages;
    private Language language;
    private ListView langsList;

    public LanguageForLocationDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_language_for_location);

        langsList = findViewById(R.id.lv_languages);
        languages = new ArrayList<>();

        countryCodes = new CountryCodes();
        String[] suggestedLangs = countryCodes.getLangsForCountry(country);
        makeLangArray(suggestedLangs);
        adapter = new LanguageAdapter(getContext(), R.layout.dialog_language_for_location, languages);
        langsList.setAdapter(adapter);

        langsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedLanguage = languages.get(i).getLang();
                dismiss();
            }
        });
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private void makeLangArray(String[] langs) {
        for (int i = 0; i < langs.length; i++) {
            languages.add(new Language(langs[i]));
        }
    }

    public String getSelectedLanguage() {
        return selectedLanguage;
    }
}
