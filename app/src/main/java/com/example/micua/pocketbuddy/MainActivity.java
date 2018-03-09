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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {
    private ImageView listen, settings;
    private LinearGradient linearGradient;
    private RelativeLayout mainHolder;
    private GradientDrawable linearGradientDrawable;
    private int newStartColor = 0, newEndColor = 0, solidColor = 0;
    private boolean isSolid, isLinear;
    public static final int SETTINGS_REQ_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        assert getSupportActionBar() != null;
        getSupportActionBar().hide();

        mainHolder = findViewById(R.id.main_holder);
        mainHolder.setBackgroundResource(R.drawable.main_screen_bg);

        listen = findViewById(R.id.iv_mic);
        settings = findViewById(R.id.iv_settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(intent, SETTINGS_REQ_CODE);
            }
        });


        // TODO: set colors
        //gradientDrawable.setColors(new int[] {Color.parseColor("#F2CFC2"), Color.parseColor("#541342")});



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTINGS_REQ_CODE) {
            if (data.getStringExtra("solid").equals("solid"))
                mainHolder.setBackgroundColor(solidColor);
            else
                if (data.getStringExtra("linear").equals("linear"))
                    setLinearBackground();
        }
    }

    private void setLinearBackground() {
        linearGradientDrawable = new GradientDrawable();
        linearGradientDrawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        linearGradientDrawable.setShape(GradientDrawable.RECTANGLE);
        linearGradientDrawable.setCornerRadius(0f);
        linearGradientDrawable.setColors(new int[] {newStartColor, newEndColor});
        mainHolder.setBackground(linearGradientDrawable);
    }

    @Subscribe
    public void onSolidColorPickedEvent(ColorEvent event) {
        solidColor = event.getSolidColor();
    }

    @Subscribe
    public void onStartColorPickedEvent(ColorEvent event) {
        newStartColor = event.getStartColor();
    }

    @Subscribe
    public void onEndColorPickedEvent(ColorEvent event) {
        newEndColor = event.getEndColor();
    }
}