package com.example.rensk.hueapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HueListener {

    HueApiManager apiManager;
    RecyclerView recyclerView;
    ArrayList<Light> lights = new ArrayList<>();;
    HueAdapter hueAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);

            hueAdapter = new HueAdapter(lights);



        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));



        recyclerView.setAdapter(hueAdapter);


        apiManager = new HueApiManager(getApplicationContext(), this);
        apiManager.getHue();

        hueAdapter.setOnItemClickListener(new HueAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(getApplicationContext(), Detail.class);
                Light lights1 =lights.get(position);
                intent.putExtra("LIGHT_OBJECT", (Serializable) lights1);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });
    }

    @Override
    public void onLightsAvailable(Light light) {
        lights.add(light);
        System.out.println("Aantal lampen"+ lights.size());
        hueAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLightsError(String err) {Log.d("", ""); }
}
