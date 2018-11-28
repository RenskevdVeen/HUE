package com.example.rensk.hueapp;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    ImageView colorpicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_detail);

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

        colorpicked = findViewById(R.id.colorpickedAll_id);
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
                            colorpicked.setBackgroundColor(Color.rgb(redValue, greenValue, blueValue));
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

                }else{
                    onall.setText(R.string.off);

                }
            }
        });





    }
}
