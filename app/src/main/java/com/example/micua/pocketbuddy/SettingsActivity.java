package com.example.micua.pocketbuddy;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class SettingsActivity extends AppCompatActivity {

    private RelativeLayout gradientHolder, solidHolder, solidPickHolder;
    private TextView solidTV, gradientTV;
    private RadioButton gradientRB, solidRB;
    private LinearLayout gradientPickHolder;
    private Button solidColorPick, startColor, endColor;
    private int newStartColor, newEndColor, solidColor;
    private Intent returnIntent;

    //TODO: make location switch functional, save settings

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        assert getSupportActionBar() != null;
        getSupportActionBar().hide();

        returnIntent = new Intent();

        gradientHolder = findViewById(R.id.rl_gradient_holder);
        solidHolder = findViewById(R.id.rl_solid_holder);
        solidTV = findViewById(R.id.tv_solid);
        gradientTV = findViewById(R.id.tv_linear_gradient);
        gradientRB = findViewById(R.id.rb_linear_gradient);
        solidRB = findViewById(R.id.rb_solid);
        gradientPickHolder = findViewById(R.id.ll_gradient_color_picker);
        solidColorPick = findViewById(R.id.btn_solid_color_launch);
        startColor = findViewById(R.id.btn_start_color_launch);
        endColor = findViewById(R.id.btn_end_color_launch);
        solidPickHolder = findViewById(R.id.rl_solid_color_picker);

        gradientPickHolder.setVisibility(View.GONE);
        solidPickHolder.setVisibility(View.GONE);

        solidColorPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ColorPickerDialog colorPickerDialog = new ColorPickerDialog(SettingsActivity.this);
                colorPickerDialog.create();
                colorPickerDialog.show();
                colorPickerDialog.setSolid(true);

            }
        });

        startColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ColorPickerDialog colorPickerDialog = new ColorPickerDialog(SettingsActivity.this);
                colorPickerDialog.create();
                colorPickerDialog.show();
                colorPickerDialog.setStart(true);
            }
        });

        endColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ColorPickerDialog colorPickerDialog = new ColorPickerDialog(SettingsActivity.this);
                colorPickerDialog.create();
                colorPickerDialog.show();
                colorPickerDialog.setEnd(true);
            }
        });
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
                if (b) {
                    solidRB.setChecked(false);
                    gradientPickHolder.setVisibility(View.VISIBLE);
                    solidPickHolder.setVisibility(View.GONE);
                }
            }
        });

        solidRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    gradientRB.setChecked(false);
                    gradientPickHolder.setVisibility(View.GONE);
                    solidPickHolder.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (solidRB.isChecked()) {
            returnIntent.putExtra("solid", "solid");
            returnIntent.putExtra("linear", " ");
        }
        if (gradientRB.isChecked()) {
            returnIntent.putExtra("linear", "linear");
            returnIntent.putExtra("solid", " ");
        }
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}