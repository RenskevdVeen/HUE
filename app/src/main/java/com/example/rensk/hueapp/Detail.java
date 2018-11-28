package com.example.rensk.hueapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
    TextView color;
    ImageView colorimage;
    TextView on;
    Bitmap bitmap;
    int redValue,blueValue,greenValue;
    int pixel;
    int hue;
    float[] huevalue = new float[3];
    Switch onoffswitch;
    boolean switchValue;

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
        System.out.println(selectedLight.getBrightness());
        seekbarbrightness.setProgress((selectedLight.getBrightness()*100/254));

        System.out.println((selectedLight.getBrightness()*100/254));
        brightnessvalueid.setText(String.valueOf(selectedLight.getBrightness() + 1));
        seekbarbrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                brightnessvalue = i * 254 / 100;
                brightnessvalueid.setText(String.valueOf(brightnessvalue + 1));
                System.out.println("seekbar value" + brightnessvalue);
                sendJSON();

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
        seekBarSaturation.setProgress(selectedLight.getSaturation()*100/254);
        saturationvalueid.setText(String.valueOf(selectedLight.getSaturation() + 1));
        seekBarSaturation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                saturationvalue = i * 254 / 100;
                saturationvalueid.setText(String.valueOf((saturationvalue + 1)));
                System.out.println("Seekbar saturation value" + saturationvalue);
                sendJSON();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        color = findViewById(R.id.color_id);
        color.setText(R.string.color);
        colorimage = (ImageView) findViewById(R.id.color_picker_id);
        BitmapDrawable bitmapDrawable = (BitmapDrawable)colorimage.getDrawable();
        bitmap = bitmapDrawable.getBitmap();

        colorimage.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //int color = bitmap.getPixel(event.getX(),event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        if (((int)event.getX()>=1 &&(int)event.getY()>=1)&& (int)event.getX()<bitmap.getWidth() && (int) event.getY()<bitmap.getHeight())
                        {
                        pixel = bitmap.getPixel((int)event.getX(),(int)event.getY());

                            System.out.println(pixel);
                            redValue = Color.red(pixel);
                            blueValue = Color.blue(pixel);
                            greenValue = Color.green(pixel);
                            Color.RGBToHSV(redValue, greenValue, blueValue, huevalue);
                            hue = (int) (huevalue[0] * 182.04);
                            System.out.println("Red" + redValue + " blue" + blueValue + " Green " + greenValue + " Color" + pixel);
                            System.out.println(hue);
                            sendJSON();
                            break;
                        }else{
                            System.out.println("pixel is 0");
                        }
                }

                return true;
            }
        });


        onoffswitch = findViewById(R.id.switchdetail_id);
        if(selectedLight.getAan().equals("true")){
            //on.setText(R.string.on);
            onoffswitch.setChecked(true);
        }else{
            onoffswitch.setChecked(false);
        }
        onoffswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (onoffswitch.isChecked()){
                    on.setText(R.string.on);
                    switchValue = true;
                    sendJSON();

                }else{
                    on.setText(R.string.off);
                    switchValue= false;
                    sendJSON();
                }
            }
        });



        connectedId = (TextView) findViewById(R.id.connectedId);
        resultId = (TextView) findViewById(R.id.resultId);
        testBrightnessId = (Button) findViewById(R.id.testBrightnessId);
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
                    jsonParam.put("on", switchValue);
                    jsonParam.put("bri", brightnessvalue);
                    jsonParam.put("hue", hue);
                    jsonParam.put("sat", saturationvalue);
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



