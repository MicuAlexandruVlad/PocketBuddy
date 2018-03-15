package com.example.micua.pocketbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.Calendar;

public class ItineraryPlanner extends AppCompatActivity {

    private Button startPlanning;
    private EditText startDateET, endDateET;
    private PlaceAutocompleteFragment destinationF;
    private String startDate, endDate;
    private String destinationName, destinationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_planner);

        assert getSupportActionBar() != null;
        getSupportActionBar().hide();

        startDateET = findViewById(R.id.et_starting_date);
        endDateET = findViewById(R.id.et_ending_date);
        startPlanning = findViewById(R.id.btn_start_planing);
        destinationF = (PlaceAutocompleteFragment) getFragmentManager()
                .findFragmentById(R.id.f_destination_autocomplete);

        destinationF.setHint("Where to?");
        destinationF.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                destinationName = place.getName().toString();
                destinationID = place.getId();
                Log.d("place", "Place: " + place.getName());
                Log.d("place", "Place: " + place.getAttributions());
                Log.d("place", "Place: " + place.getPlaceTypes());
                Log.d("place", "Place: " + place.getRating());
                Log.d("place", "Place: " + place.getWebsiteUri());
                Log.d("place", "Place: " + place.getPriceLevel());
                Log.d("place", "Place: " + place.getId());
            }

            @Override
            public void onError(Status status) {

            }
        });

        startDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarDatePickerDialogFragment cdb = new CalendarDatePickerDialogFragment();
                cdb.setFirstDayOfWeek(Calendar.SUNDAY);
                cdb.setDoneText("Ok");
                cdb.setThemeDark();
                cdb.setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                        startDate = dayOfMonth + "/" + monthOfYear + "/" + year;
                        startDateET.setText(startDate);
                    }
                });
                cdb.show(getSupportFragmentManager(), "date_picker_name_1");

            }
        });

        endDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarDatePickerDialogFragment cdb = new CalendarDatePickerDialogFragment();
                cdb.setFirstDayOfWeek(Calendar.SUNDAY);
                cdb.setDoneText("Ok");
                cdb.setThemeDark();
                cdb.setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                        endDate = dayOfMonth + "/" + monthOfYear + "/" + year;
                        endDateET.setText(endDate);
                    }
                });
                cdb.show(getSupportFragmentManager(), "date_picker_name_2");
            }
        });

        startPlanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItineraryPlanner.this, Itinerary.class);
                intent.putExtra("destination", destinationName);
                intent.putExtra("start_date", startDate);
                intent.putExtra("end_date", endDate);
                intent.putExtra("destination_id", destinationID);
                startActivity(intent);
            }
        });
    }
}
