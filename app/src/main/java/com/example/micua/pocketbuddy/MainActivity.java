package com.example.micua.pocketbuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout mainHolder;
    private ImageView settings, listen, ttsFrom, ttsTo;
    private Spinner languageFrom, languageTo;
    private EditText textToTranslateET, translatedTextET;
    private LinearGradient linearGradient;
    private GradientDrawable linearGradientDrawable;
    private int newStartColor = 0, newEndColor = 0, solidColor = 0;
    private OkHttpClient httpClient;
    private Request request;
    private String textToTranslate, languageToTranslate, languageFromTranslate = "", country, countryFinal;
    private JSONObject mainJSON;
    public static final int SETTINGS_REQ_CODE = 2;
    public static final String YANDEX_API = "trnsl.1.1.20180310T112625Z.a83d96ee0de4703f.1c85a268e7ffd88926e8b5a9b571928fe81ff63d";
    private String[] languages, langCodes;
    private CountryCodes countryCodes;
    private CheckBox autoDetect;
    private ArrayAdapter<String> adapter;
    private Button trigger;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        countryCodes = new CountryCodes();
        languages = countryCodes.getLanguagesFinal();
        langCodes = countryCodes.getLangCodes();

        assert getSupportActionBar() != null;
        getSupportActionBar().hide();

        mainHolder = findViewById(R.id.main_holder);
        settings = findViewById(R.id.iv_settings);
        listen = findViewById(R.id.iv_mic);
        textToTranslateET = findViewById(R.id.et_text_to_translate);
        translatedTextET = findViewById(R.id.et_translated_text);
        languageFrom = findViewById(R.id.spinner_language_from);
        languageTo = findViewById(R.id.spinner_language_to);
        autoDetect = findViewById(R.id.cb_auto_detect);
        trigger = findViewById(R.id.trigger_lang);
        ttsFrom = findViewById(R.id.iv_tts_lang_from);

        adapter = new ArrayAdapter<>(this, R.layout.spinner_lang_item, R.id.tv_lang_spinner,
                languages);
        languageFrom.setAdapter(adapter);
        languageTo.setAdapter(adapter);

        //mainHolder.setBackgroundResource(R.drawable.main_screen_bg);
        translateCountry(getApplicationContext().getResources().getConfiguration().locale.getDisplayCountry());

        trigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LanguageForLocationDialog dialog = new LanguageForLocationDialog(MainActivity.this);
                dialog.setCountry(countryFinal);
                dialog.create();
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        languageToTranslate = getLangCode(dialog.getSelectedLanguage());
                        if (getLanguagePosition(dialog.getSelectedLanguage()) != -5)
                            languageTo.setSelection(getLanguagePosition(dialog.getSelectedLanguage()));
                        System.out.println(getLanguagePosition(dialog.getSelectedLanguage()));
                        System.out.println(dialog.getSelectedLanguage());
                    }
                });
            }
        });

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
                    establishConnection(textToTranslate, languageToTranslate, languageFromTranslate);
                }
            }
        });

        autoDetect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    languageFrom.setVisibility(View.GONE);
                    languageFromTranslate = "";
                }
                else {
                    languageFrom.setVisibility(View.VISIBLE);
                }
            }
        });

        languageFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                languageFromTranslate = getLangCode(adapter.getItem(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        languageTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                languageToTranslate = getLangCode(adapter.getItem(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    switch (getCodeForLanguage(languageFromTranslate)) {
                        case "English": tts.setLanguage(Locale.US);
                        case "Chinese": tts.setLanguage(Locale.CHINESE);
                        case "French": tts.setLanguage(Locale.FRENCH);
                        case "German": tts.setLanguage(Locale.GERMAN);
                        case "Italian": tts.setLanguage(Locale.ITALIAN);
                        case "Japanese": tts.setLanguage(Locale.JAPANESE);
                        case "Korean": tts.setLanguage(Locale.KOREAN);
                        default: tts.setLanguage(Locale.UK);
                    }
                }
            }
        });

        ttsFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ttsText = textToTranslateET.getText().toString();
                tts.speak(ttsText, TextToSpeech.QUEUE_FLUSH, null);
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

    private String buildTranslateUrl(String api, String textToTranslate, String languageToTranslate, String languageFromTranslate) {
        if (languageFromTranslate.equalsIgnoreCase("")) {
            String link = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + api + "&text=" +
                    textToTranslate + "&lang=" + languageToTranslate + "&format=plain&options=1";
            System.out.println(link);
            return link;
        }
        else {
            String link = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + api + "&text=" +
                    textToTranslate + "&lang=" + languageFromTranslate + "-" + languageToTranslate + "&format=plain&options=1";
            System.out.println(link);
            return link;
        }
    }

    private void establishConnection(String textToTranslate, String languageToTranslate, String languageFromTranslate) {
        httpClient = new OkHttpClient();
        request = new Request.Builder()
                      .url(buildTranslateUrl(YANDEX_API, textToTranslate, languageToTranslate, languageFromTranslate))
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

    private String getLangCode(String language) {
        for (int i = 0; i < languages.length; i++) {
            if (language.equalsIgnoreCase(languages[i]))
                return langCodes[i];
        }
        return "notFound";
    }

    private void translateCountry(String country) {
        String langTo = "en";
        httpClient = new OkHttpClient();
        request = new Request.Builder()
                .url(buildTranslateUrl(YANDEX_API, country, langTo, ""))
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

    private int getLanguagePosition(String lang) {
        int index = -5;
        for (int i = 0; i < languages.length; i++) {
            if (lang.equalsIgnoreCase(languages[i]))
                index = i;
        }
        return index;
    }

    private String getCodeForLanguage(String language) {
        String langCode = "";
        for (int i = 0; i < languages.length; i++) {
            if (language.equalsIgnoreCase(languages[i]))
                langCode = langCodes[i];
        }
        return langCode;
    }
}