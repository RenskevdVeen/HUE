package com.example.rensk.hueapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
                    if(selectedUrlString != null || doesURLExist() == false) {
                        Intent mainIntent = new Intent(URLSelector.this, MainActivity.class);
                        mainIntent.putExtra("URL", selectedUrlString);
                        startActivity(mainIntent);
                    }
                    else{
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.urlSelectorId), "kan geen verbinding maken.", 1000);
                        snackbar.show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        schoolEmuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //selectedUrlString = "http://145.48.205.33/api/iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB";
                try {
                    if(selectedUrlString != null || doesURLExist() == false) {
                        Intent mainIntent = new Intent(URLSelector.this, MainActivity.class);
                        mainIntent.putExtra("URL", selectedUrlString);
                        startActivity(mainIntent);
                    }
                    else{
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.urlSelectorId), "kan geen verbinding maken.", 1000);
                        snackbar.show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thuisEmuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedUrlString = "";
                try {
                    if(selectedUrlString != null || doesURLExist() == false) {
                        Intent mainIntent = new Intent(URLSelector.this, MainActivity.class);
                        mainIntent.putExtra("URL", selectedUrlString);
                        startActivity(mainIntent);
                    }
                    else{
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.urlSelectorId), "kan geen verbinding maken.", 1000);
                        snackbar.show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public boolean doesURLExist() throws IOException
    {
        // We want to check the current URL
        selectedUrl = new URL(selectedUrlString);
        HttpURLConnection.setFollowRedirects(false);

        HttpURLConnection httpURLConnection = (HttpURLConnection) selectedUrl.openConnection();

        // We don't need to get data
        httpURLConnection.setRequestMethod("HEAD");

        // Some websites don't like programmatic access so pretend to be a browser
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
        int responseCode = httpURLConnection.getResponseCode();

        // We only accept response code 200
        return responseCode == HttpURLConnection.HTTP_OK;
    }
}

