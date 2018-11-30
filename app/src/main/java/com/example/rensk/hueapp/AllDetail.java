package com.example.rensk.hueapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AllDetail extends AppCompatActivity {
    TextView nameall;
    TextView onall;
    TextView brightnessAll;
    TextView brightnessValueAll;
    SeekBar brightnessSeekbarAll;
    TextView saturationAll;
    TextView saturationValueAll;
    SeekBar saturationSeekbarAll;
    TextView colorAll;
    Bitmap bitmap;
    int redValue,blueValue,greenValue;
    int pixel;
    int hue;
    float[] huevalue = new float[3];
    int brightnessvalueallint;
    int saturationvalueAllInt;
    ImageView colorimage;
    Switch onoffallswitch;
    URL url;
    boolean switchValue;
    TextView connectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        nameall = findViewById(R.id.all_name_id);
        nameall.setText(R.string.all);

        onall = findViewById(R.id.onoffall_id);
        onall.setText("");

        brightnessAll = findViewById(R.id.brightnessAll_id);
        brightnessAll.setText(R.string.brightness);
        brightnessValueAll = findViewById(R.id.brightnessallvalue_id);
        brightnessValueAll.setText("");
        brightnessSeekbarAll = findViewById(R.id.seekbarbrightnessall);
        brightnessSeekbarAll.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                brightnessvalueallint = i * 254 / 100;
                brightnessValueAll.setText(String.valueOf(brightnessvalueallint + 1));
                System.out.println("seekbar value all" + brightnessvalueallint);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        saturationAll = findViewById(R.id.saturationall_id);
        saturationAll.setText(R.string.saturation);

        saturationValueAll = findViewById(R.id.saturationvalue_id);
        saturationValueAll.setText("");

        saturationSeekbarAll = findViewById(R.id.seekbarsaturationall_id);
        saturationSeekbarAll.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
               saturationvalueAllInt= i * 254 / 100;
                saturationValueAll.setText(String.valueOf(saturationvalueAllInt + 1));
                System.out.println("seekbar value all" + saturationvalueAllInt);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        colorAll = findViewById(R.id.colorAll_id);
        colorAll.setText(R.string.color);


        colorimage = (ImageView) findViewById(R.id.color_picker_ALl_id);
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
                            //colorpicked.setBackgroundColor(Color.rgb(redValue, greenValue, blueValue));
                            Color.RGBToHSV(redValue, greenValue, blueValue, huevalue);
                            hue = (int) (huevalue[0] * 182.04);
                            System.out.println("Red" + redValue + " blue" + blueValue + " Green " + greenValue + " Color" + pixel);
                            System.out.println(hue);

                            break;
                        }else{
                            System.out.println("pixel is 0");
                        }
                }

                return true;
            }
        });

        onoffallswitch = findViewById(R.id.switchall_id);
        onoffallswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (onoffallswitch.isChecked()){
                    onall.setText(R.string.on);
                    switchValue = true;
                    sendJSON();
                }else{
                    onall.setText(R.string.off);
                    switchValue= false;
                    sendJSON();

                }
            }
        });
        connectedId =  findViewById(R.id.connectedallId);

        checkNetworkConnection();

    }
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

    public void sendJSON() {
        try {
            for (int i = 1; i < 10; i++) {
                if (i == 4) continue;
                url = new URL(MainActivity.url + "/lights/" + i + "/state");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                try {
                    jsonParam.put("on", switchValue);
                    jsonParam.put("bri", brightnessvalueallint);
                    jsonParam.put("hue", hue);
                    jsonParam.put("sat", saturationvalueAllInt);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i("JSON", jsonParam.toString());
                Log.i("URL", url.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(jsonParam.toString());

                os.flush();
                os.close();

                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG", conn.getResponseMessage());

                conn.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
