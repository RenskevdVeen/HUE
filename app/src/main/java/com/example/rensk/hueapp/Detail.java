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
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Detail extends AppCompatActivity  {
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
    HueApiManager apiManager;



    TextView on;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = getIntent();
        Light lights1 = (Light) intent.getSerializableExtra("LIGHT_OBJECT");

        name = findViewById(R.id.name_id);
        name.setText(lights1.getLightnum());
        brightness = findViewById(R.id.brightnessId);
        brightness.setText(R.string.brightness);
        brightnessvalueid = findViewById(R.id.brightnessvalue_id);
        on = findViewById(R.id.onoff_id);
        if (lights1.getAan().equals("true")){
            on.setText(R.string.on);
        }else{
            on.setText(R.string.off);
        }
        brightnessvalueid.setText(String.valueOf(brightnessvalue));
        seekbarbrightness = findViewById(R.id.seekbarbrightness);
        seekbarbrightness.setProgress(lights1.getBrightness());
        brightnessvalueid.setText(String.valueOf(lights1.getBrightness()+1));
        seekbarbrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                brightnessvalue = i*254/100;
                brightnessvalueid.setText(String.valueOf(brightnessvalue + 1));
                System.out.println("seekbar value"+ brightnessvalue);

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
        seekBarSaturation.setProgress(lights1.getSaturation());
        saturationvalueid.setText(String.valueOf(lights1.getSaturation()+ 1));
        seekBarSaturation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                saturationvalue = i*254/100;
                saturationvalueid.setText(String.valueOf((saturationvalue + 1)));
                System.out.println("Seekbar saturation value"+ saturationvalue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //TESTCODE VAN SANDER
        connectedId = (TextView) findViewById(R.id.connectedId);
        resultId = (TextView) findViewById(R.id.resultId);
        testBrightnessId = (Button) findViewById(R.id.testBrightnessId);
        testBrightnessId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HTTPAsyncTask().execute("http://192.168.2.4/api/8144c280f65aa9c4749a7657ae82b1b");
            }
        });
                checkNetworkConnection();
    }


    //TESTCODE VAN SANDER


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


    //Interne klasse voor het oversturen van dingen over het netwerk.
    //Is een aparte thread dus voorkomt GUI freezes.
    private class HTTPAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                try {
                    return HttpPost();
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            resultId.setText(result);
        }

        private String HttpPost() throws IOException, JSONException {
            String result = "";

            URL url = new URL("http://192.168.2.4/api/8144c280f65aa9c4749a7657ae82b1b");

            // 1. create HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            // 2. build JSON object
            JSONObject jsonObject = buidJsonObject();

            // 3. add JSON content to POST request body
            setPostRequestContent(conn, jsonObject);

            // 4. make POST request to the given URL
            conn.connect();

            // 5. return response message
            return conn.getResponseMessage() + "";

        }
        private JSONObject buidJsonObject() throws JSONException {

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("lights", 254);


            return jsonObject;
        }
        private void setPostRequestContent(HttpURLConnection conn,
                                           JSONObject jsonObject) throws IOException {

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonObject.toString());
            Log.i(MainActivity.class.toString(), jsonObject.toString());
            writer.flush();
            writer.close();
            os.close();
        }
        }
    }


