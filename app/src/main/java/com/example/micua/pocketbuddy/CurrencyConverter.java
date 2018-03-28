package com.example.micua.pocketbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CurrencyConverter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter_list);

        assert getSupportActionBar() != null;
        getSupportActionBar().hide();
    }
}
