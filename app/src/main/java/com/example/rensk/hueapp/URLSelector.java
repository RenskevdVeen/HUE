package com.example.rensk.hueapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public class URLSelector extends AppCompatActivity {
    TextView selectUrlText;
    Button laButton;
    Button schoolEmuButton;
    Button thuisEmuButton;
    String selectedUrlString;
    URL selectedUrl;
    static URLSelector urlSelector;
    Switch urlsave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlselector);

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
               if (urlsave.isChecked()){
                   System.out.println( "Checked start saving");
               }else{

               }
            }
        });

        laButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedUrlString = "http://145.48.205.33/api/iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB";

                try {
                    selectedUrl = new URL(selectedUrlString);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                Intent mainIntent = new Intent(URLSelector.this, MainActivity.class);
                mainIntent.putExtra("URL", selectedUrlString);
                startActivity(mainIntent);
            }
        });

        schoolEmuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        thuisEmuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public static URLSelector getInstance()
    {
        if (urlSelector == null)
            urlSelector = new URLSelector();

        return urlSelector;
    }

    public URL getSelectedUrl() {
        return selectedUrl;
    }
}

