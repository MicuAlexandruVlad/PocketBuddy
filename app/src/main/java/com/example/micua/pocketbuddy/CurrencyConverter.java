package com.example.micua.pocketbuddy;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CurrencyConverter extends AppCompatActivity {
    @BindView(R.id.tv_number_0) TextView number0;
    @BindView(R.id.tv_number_1) TextView number1;
    @BindView(R.id.tv_number_2) TextView number2;
    @BindView(R.id.tv_number_3) TextView number3;
    @BindView(R.id.tv_number_4) TextView number4;
    @BindView(R.id.tv_number_5) TextView number5;
    @BindView(R.id.tv_number_6) TextView number6;
    @BindView(R.id.tv_number_7) TextView number7;
    @BindView(R.id.tv_number_8) TextView number8;
    @BindView(R.id.tv_number_9) TextView number9;
    @BindView(R.id.tv_comma) TextView comma;
    @BindView(R.id.iv_delete) ImageView delete;
    @BindView(R.id.tv_amount_from) TextView amountFrom;
    @BindView(R.id.tv_amount_to) TextView amountTo;
    @BindView(R.id.spinner_currency_from) Spinner currencyFrom;
    @BindView(R.id.spinner_currency_to) Spinner currencyTo;
    @BindView(R.id.iv_exchange) ImageView exchange;
    public static final String TAG = "currency converter";

    private int amountFromValue;
    private String currencyISOFrom, currencyISOTo;
    private CurrencyCodes currencyCodes;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter_list);

        ButterKnife.bind(this);

        assert getSupportActionBar() != null;
        getSupportActionBar().hide();

        amountFrom.setText("");
        currencyCodes = new CurrencyCodes();
        final List<String> currencies = new ArrayList<>(currencyCodes.getCurrencies());

        adapter = new ArrayAdapter<>(this, R.layout.spinner_lang_item, R.id.tv_lang_spinner,
                currencies);
        currencyTo.setAdapter(adapter);
        currencyFrom.setAdapter(adapter);

        number0.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                switch (amountFrom.getText().toString()) {
                    case "":
                        amountFrom.setText("0");
                        break;
                    case "0":
                        doNothing();
                        break;
                    default:
                        amountFrom.setText(amountFrom.getText().toString() + "0");
                        break;
                }
            }
        });

        number1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                amountFrom.setText(amountFrom.getText().toString() + "1");
            }
        });

        number2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                amountFrom.setText(amountFrom.getText().toString() + "2");
            }
        });

        number3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                amountFrom.setText(amountFrom.getText().toString() + "3");
            }
        });

        number4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                amountFrom.setText(amountFrom.getText().toString() + "4");
            }
        });

        number5.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                amountFrom.setText(amountFrom.getText().toString() + "5");
            }
        });

        number6.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                amountFrom.setText(amountFrom.getText().toString() + "6");
            }
        });

        number7.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                amountFrom.setText(amountFrom.getText().toString() + "7");
            }
        });

        number8.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                amountFrom.setText(amountFrom.getText().toString() + "8");
            }
        });

        number9.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                amountFrom.setText(amountFrom.getText().toString() + "9");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amountFrom.getText().toString().equals(""))
                    doNothing();
                else {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < amountFrom.getText().toString().split("").length - 1; i++) {
                        builder.append(amountFrom.getText().toString().split("")[i]);
                    }
                    amountFrom.setText(builder.toString());
                }
            }
        });

        comma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amountFrom.setText(String.format("%s.", amountFrom.getText().toString()));
            }
        });

        currencyTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currencyISOTo = currencies.get(i).split(" - ")[1];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                doNothing();
            }
        });

        currencyFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currencyISOFrom = currencies.get(i).split(" - ")[1];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                doNothing();
            }
        });

        //TODO: fix too many decimals showing up after conversion
        exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(buildConversionUrl(currencyISOFrom, currencyISOTo))
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject obj = new JSONObject(response.body().string());
                                JSONObject innerObj = obj.getJSONObject(currencyISOFrom + "_" + currencyISOTo);
                                Log.d(TAG, currencyISOFrom + "_" + currencyISOTo);
                                String val = innerObj.getString("val");
                                Log.d(TAG, innerObj.toString());
                                Log.d(TAG, val);
                                float valFrom = Float.valueOf(amountFrom.getText().toString());
                                final float valTo = valFrom * Float.valueOf(val);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        amountTo.setText(String.format("%s", valTo));
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
            }
        });
    }

    private void doNothing(){}

    private String buildConversionUrl(String ISOFrom, String ISOTo) {
        String link = "https://free.currencyconverterapi.com/api/v5/convert?q=" + ISOFrom + "_" + ISOTo + "&compact=y";
        Log.d(TAG, link);
        return link;
    }

}
