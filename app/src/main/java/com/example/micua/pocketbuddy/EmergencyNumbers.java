package com.example.micua.pocketbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EmergencyNumbers extends AppCompatActivity {
    @BindView(R.id.rl_fire)
    RelativeLayout fire;
    @BindView(R.id.rl_hospital)
    RelativeLayout hospital;
    @BindView(R.id.rl_general_emergency)
    RelativeLayout emergency;
    @BindView(R.id.rl_police)
    RelativeLayout police;
    @BindView(R.id.rl_loading_panel)
    RelativeLayout loadingPanel;
    @BindView(R.id.tv_emergency)
    TextView policeTV;
    @BindView(R.id.tv_emergency1)
    TextView hospitalTV;
    @BindView(R.id.tv_emergency2)
    TextView fireTV;
    @BindView(R.id.tv_emergency3)
    TextView emergencyTV;

    private EmergencyNumber emergencyNumber;
    public static final String YANDEX_API = "trnsl.1.1.20180310T112625Z.a83d96ee0de4703f.1c85a268e7ffd88926e8b5a9b571928fe81ff63d";
    private String countryFinal, data = "", generalNum;
    public static final String EMERGENCY = "emergencynumbers";
    private Map<String, String> numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_numbers);

        ButterKnife.bind(this);
        assert getSupportActionBar() != null;
        getSupportActionBar().hide();

        emergencyNumber = new EmergencyNumber();
        numbers = emergencyNumber.getNumbers();

        police.setVisibility(View.GONE);
        hospital.setVisibility(View.GONE);
        emergency.setVisibility(View.GONE);
        fire.setVisibility(View.GONE);
        loadingPanel.setVisibility(View.VISIBLE);

        translateCountry(getApplicationContext().getResources().getConfiguration().locale.getDisplayCountry());
    }

    private void translateCountry(String country) {
        String langTo = "en";
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
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
                        JSONObject mainJSON = new JSONObject(response.body().string());
                        countryFinal = mainJSON.getJSONArray("text").getString(0);
                        String key;
                        for (Map.Entry<String, String> entry : numbers.entrySet()) {
                            key = entry.getKey();
                            if (key.equalsIgnoreCase(countryFinal))
                                data = entry.getValue();
                        }
                        final String policeNum;
                        final String hospitalNum;
                        generalNum = null;
                        final String fireNum;
                        policeNum = data.split(",")[0].split(":")[1];
                        hospitalNum = data.split(",")[1].split(":")[1];
                        fireNum = data.split(",")[2].split(":")[1];
                        if (policeNum.equalsIgnoreCase(hospitalNum) && hospitalNum.equalsIgnoreCase(fireNum))
                            generalNum = policeNum;
                        Log.d(EMERGENCY, "country name: " + countryFinal);
                        Log.d(EMERGENCY, "data " + data);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!policeNum.equalsIgnoreCase("none")) {
                                    policeTV.setText(policeNum);
                                    police.setVisibility(View.VISIBLE);
                                }
                                if (!fireNum.equalsIgnoreCase("none")) {
                                    fireTV.setText(fireNum);
                                    fire.setVisibility(View.VISIBLE);
                                }
                                if (!hospitalNum.equalsIgnoreCase("none")) {
                                    hospitalTV.setText(hospitalNum);
                                    hospital.setVisibility(View.VISIBLE);
                                }
                                if (generalNum.equalsIgnoreCase(hospitalNum)) {
                                    emergencyTV.setText(generalNum);
                                    police.setVisibility(View.GONE);
                                    hospital.setVisibility(View.GONE);
                                    emergency.setVisibility(View.VISIBLE);
                                    fire.setVisibility(View.GONE);
                                }
                                loadingPanel.setVisibility(View.GONE);
                            }
                        });
                } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        });
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
}
