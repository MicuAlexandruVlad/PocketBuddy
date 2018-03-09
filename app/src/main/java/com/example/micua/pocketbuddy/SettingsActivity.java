package com.example.micua.pocketbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private RelativeLayout gradientHolder = findViewById(R.id.rl_gradient_holder);
    private RelativeLayout solidHolder = findViewById(R.id.rl_solid_holder);
    private TextView solidTV = findViewById(R.id.tv_solid);
    private TextView gradientTV = findViewById(R.id.tv_linear_gradient);
    private RadioButton gradientRB = findViewById(R.id.rb_linear_gradient);
    private RadioButton solidRB = findViewById(R.id.rb_solid);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        assert getSupportActionBar() != null;
        getSupportActionBar().hide();

        setChecks();
    }

    private void setChecks() {
        gradientHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gradientRB.setChecked(true);
            }
        });

        solidHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solidRB.setChecked(true);
            }
        });

        gradientRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    solidRB.setChecked(false);
            }
        });

        solidRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    gradientRB.setChecked(false);
            }
        });
    }
}