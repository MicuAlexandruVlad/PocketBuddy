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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout mainHolder;
    private ImageView settings;
    private ImageView listen;
    private EditText textToTranslateET, translatedTextET;
    private LinearGradient linearGradient;
    private GradientDrawable linearGradientDrawable;
    private int newStartColor = 0, newEndColor = 0, solidColor = 0;
    private boolean isSolid, isLinear;
    private OkHttpClient httpClient;
    private Request request;
    private String textToTranslate, languageToTranslate, country, countryFinal;
    private JSONObject mainJSON;
    public static final int SETTINGS_REQ_CODE = 2;
    public static final String YANDEX_API = "trnsl.1.1.20180310T112625Z.a83d96ee0de4703f.1c85a268e7ffd88926e8b5a9b571928fe81ff63d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        assert getSupportActionBar() != null;
        getSupportActionBar().hide();

        mainHolder = findViewById(R.id.main_holder);
        settings = findViewById(R.id.iv_settings);
        listen = findViewById(R.id.iv_mic);
        textToTranslateET = findViewById(R.id.et_text_to_translate);
        translatedTextET = findViewById(R.id.et_translated_text);

        mainHolder.setBackgroundResource(R.drawable.main_screen_bg);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(intent, SETTINGS_REQ_CODE);
            }
        });

        languageToTranslate = "ro";

        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textToTranslateET.getText().toString().equals(""))
                    Toast.makeText(MainActivity.this, "Enter some text to translate", Toast.LENGTH_SHORT).show();
                else {
                    textToTranslate = textToTranslateET.getText().toString();
                    establishConnection(textToTranslate, languageToTranslate);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTINGS_REQ_CODE && resultCode == RESULT_OK) {
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
        if (event.getSolidColor() != 0)
            solidColor = event.getSolidColor();
        System.out.println(solidColor);
    }

    @Subscribe
    public void onStartColorPickedEvent(ColorEvent event) {
        if (event.getStartColor() != 0)
            newStartColor = event.getStartColor();
        System.out.println(newStartColor);
    }

    @Subscribe
    public void onEndColorPickedEvent(ColorEvent event) {
        if (event.getEndColor() != 0)
            newEndColor = event.getEndColor();
        System.out.println(newEndColor);
    }

    private String buildTranslateUrl(String api, String textToTranslate, String languageToTranslate) {
        return "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + api + "&text=" +
                textToTranslate + "&lang=" + languageToTranslate + "&format=plain&options=1";
    }

    private void establishConnection(String textToTranslate, String languageToTranslate) {
        httpClient = new OkHttpClient();
        request = new Request.Builder()
                      .url(buildTranslateUrl(YANDEX_API, textToTranslate, languageToTranslate))
                      .build();
        fetchResponse();
    }

    private void fetchResponse() {
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful())
                    try {
                        mainJSON = new JSONObject(response.body().string());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    translatedTextET.setText(mainJSON.getJSONArray("text").getString(0));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        });
    }

    private void translateCountry(String country) {
        String language = "en";
        httpClient = new OkHttpClient();
        request = new Request.Builder()
                .url(buildTranslateUrl(YANDEX_API, country, language))
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful())
                    try {
                        mainJSON = new JSONObject(response.body().string());
                        countryFinal = mainJSON.getJSONArray("text").getString(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        });
    }
}