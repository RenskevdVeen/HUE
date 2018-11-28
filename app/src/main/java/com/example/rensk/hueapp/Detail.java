package com.example.rensk.hueapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;

public class Detail extends AppCompatActivity {
    TextView connectedId;
    TextView resultId;
    Button testBrightnessId;
    TextView name;
    TextView brightness;
    SeekBar seekbarbrightness;
    TextView brightnessvalueid;
    int brightnessvalue;
    TextView saturation;
    TextView saturationvalueid;
    SeekBar seekBarSaturation;
    int saturationvalue;
    TextView on;
    Light selectedLight;
    URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = getIntent();
        selectedLight = (Light) intent.getSerializableExtra("LIGHT_OBJECT");
        String[] lampNumber = selectedLight.getLightnum().split("");
        int fullLampNumber = Integer.valueOf(lampNumber[2]) + checkLampNumber();
        try {
            url = new URL("http://145.48.205.33/api/iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB/lights/" + fullLampNumber + "/state");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        name = findViewById(R.id.name_id);
        name.setText(selectedLight.getLightnum());
        brightness = findViewById(R.id.brightnessId);
        brightness.setText(R.string.brightness);
        brightnessvalueid = findViewById(R.id.brightnessvalue_id);
        on = findViewById(R.id.onoff_id);
        if (selectedLight.getAan().equals("true")) {
            on.setText(R.string.on);
        } else {
            on.setText(R.string.off);
        }
        brightnessvalueid.setText(String.valueOf(brightnessvalue));
        seekbarbrightness = findViewById(R.id.seekbarbrightness);
        seekbarbrightness.setProgress(selectedLight.getBrightness());
        brightnessvalueid.setText(String.valueOf(selectedLight.getBrightness() + 1));
        seekbarbrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                brightnessvalue = i * 254 / 100;
                brightnessvalueid.setText(String.valueOf(brightnessvalue + 1));
                System.out.println("seekbar value" + brightnessvalue);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        saturation = findViewById(R.id.saturation_id);
        saturation.setText(R.string.saturation);
        saturationvalueid = findViewById(R.id.saturationvalue_id);
        seekBarSaturation = findViewById(R.id.seekbarsaturation_id);
        seekBarSaturation.setProgress(selectedLight.getSaturation());
        saturationvalueid.setText(String.valueOf(selectedLight.getSaturation() + 1));
        seekBarSaturation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                saturationvalue = i * 254 / 100;
                saturationvalueid.setText(String.valueOf((saturationvalue + 1)));
                System.out.println("Seekbar saturation value" + saturationvalue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        connectedId = (TextView) findViewById(R.id.connectedId);
        resultId = (TextView) findViewById(R.id.resultId);
        resultId.setText(url.toString());
        testBrightnessId = (Button) findViewById(R.id.testBrightnessId);
        testBrightnessId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

                thread.start();
            }
        });
        checkNetworkConnection();
    }

    //Checkt of er netwerk verbinding is
    public boolean checkNetworkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean isConnected = false;
        if (networkInfo != null && (isConnected = networkInfo.isConnected())) {
            connectedId.setText("Connected: " + networkInfo.getTypeName());
        } else {
            connectedId.setText("Not Connected!!!");
        }
        return isConnected;
    }

        public int checkLampNumber() {
            char lampLetter = selectedLight.getLightnum().charAt(0);
            int lampSerie = 0;
            switch (lampLetter) {
                case 'A':
                    lampSerie = 0;
                    break;
                case 'B':
                    lampSerie = 3;
                    break;
                case 'C':
                    lampSerie = 6;
                    break;
            }
            return lampSerie;
        }

        public void sendJSON() {
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                try {
                    jsonParam.put("on", false);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i("JSON", jsonParam.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(jsonParam.toString());

                os.flush();
                os.close();

                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG", conn.getResponseMessage());

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



