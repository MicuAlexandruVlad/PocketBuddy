package com.example.micua.pocketbuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private ImageView listen, settings;
    private LinearGradient linearGradient;
    private GradientDrawable gradientDrawable;
    private String newStartColor, newEndColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assert getSupportActionBar() != null;
        getSupportActionBar().hide();

        final RelativeLayout mainHolder = findViewById(R.id.main_holder);
        mainHolder.setBackgroundResource(R.drawable.main_screen_bg);

        listen = findViewById(R.id.iv_mic);
        settings = findViewById(R.id.iv_settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        gradientDrawable = new GradientDrawable();
        gradientDrawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        // TODO: set colors
        //gradientDrawable.setColors(new int[] {Color.parseColor("#F2CFC2"), Color.parseColor("#541342")});
        gradientDrawable.setCornerRadius(0f);




    }
}