package com.example.rensk.hueapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class URLSelector extends AppCompatActivity {
    TextView selectUrlText;
    Button laButton;
    Button schoolEmuButton;
    Button thuisEmuButton;
    String selectedUrl;
    static URLSelector urlSelector;

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

        laButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedUrl = "http://145.48.205.33/api/iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB";
                Intent mainIntent = new Intent(URLSelector.this, MainActivity.class);
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

    public String getSelectedUrl() {
        return selectedUrl;
    }
}

