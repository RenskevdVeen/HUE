package com.example.rensk.hueapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class URLSelector extends AppCompatActivity {
    TextView selectUrlText;
    Button laButton;
    Button schoolEmuButton;
    Button thuisEmuButton;
    String selectedUrlString;
    Switch urlsave;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean saveChecked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlselector);


        //Shared preferences
        sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(sharedPreferences.contains("saveURL")){
            if(sharedPreferences.getBoolean("saveSetting", true)){
                Intent mainIntent = new Intent(URLSelector.this, MainActivity.class);
                mainIntent.putExtra("URL", sharedPreferences.getString("saveURL", null));
                startActivity(mainIntent);
                //this.finish();
            }
        }
        saveChecked = false;

    selectUrlText = findViewById(R.id.pleaseSelectURlTextId);
        selectUrlText.setText("Klik op het netwerk dat je wilt gebruiken.");

        laButton = findViewById(R.id.laSelectId);
        laButton.setText("Lampen in LA");
        schoolEmuButton = findViewById(R.id.schoolEmuSelectId);
        schoolEmuButton.setText("Lampen van emulator op school");
        thuisEmuButton = findViewById(R.id.thuisEmuSelectId);
        thuisEmuButton.setText("Lampen van emulator thuis");

        urlsave = findViewById(R.id.urlswitch);
        urlsave.setText(R.string.savedata);
        urlsave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if(urlsave.isChecked()){
                        saveChecked = true;
                    }
                    else saveChecked = false;
               }
        });

        laButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedUrlString = "http://145.48.205.33/api/iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB";
                        if(saveChecked){
                            editor.clear();
                            String saveurl = selectedUrlString;
                            editor.putString("saveURL", saveurl);
                            editor.putBoolean("saveSetting", true);
                            editor.apply();
                        }
                        Intent mainIntent = new Intent(URLSelector.this, MainActivity.class);
                        mainIntent.putExtra("URL", selectedUrlString);
                        startActivity(mainIntent);
                    }
        });

        schoolEmuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedUrlString = "http://192.168.42.31/api/iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB";
                if(saveChecked){
                    editor.clear();
                    String saveurl = selectedUrlString;
                    editor.putString("saveURL", saveurl);
                    editor.putBoolean("saveSetting", true);
                    editor.apply();
                }
                Intent mainIntent = new Intent(URLSelector.this, MainActivity.class);
                mainIntent.putExtra("URL", selectedUrlString);
                startActivity(mainIntent);
            }
        });

        thuisEmuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                    selectedUrlString = "http://192.168.2.4/api/iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB";
                if(saveChecked){
                    editor.clear();
                    String saveurl = selectedUrlString;
                    editor.putString("saveURL", saveurl);
                    editor.putBoolean("saveSetting", true);
                    editor.apply();
                }
                    Intent mainIntent = new Intent(URLSelector.this, MainActivity.class);
                    mainIntent.putExtra("URL", selectedUrlString);
                    startActivity(mainIntent);
                }
        });
    }



}

