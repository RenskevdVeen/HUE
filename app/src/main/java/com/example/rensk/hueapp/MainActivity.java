package com.example.rensk.hueapp;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HueListener, Serializable {

    HueApiManager apiManager;
    RecyclerView recyclerView;
    ArrayList<Light> lights = new ArrayList<>();;
    HueAdapter hueAdapter;
    TextView name;
    Button all;
    static String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name_txt_id);
        name.setText(R.string.hue);

        all = findViewById(R.id.button_all_id);
        all.setText(R.string.all);

        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);

        hueAdapter = new HueAdapter(lights);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(hueAdapter);
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });
        Intent intent = getIntent();
        url = intent.getSerializableExtra("URL").toString();


        apiManager = new HueApiManager(getApplicationContext(), this);
        apiManager.getHue();

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AllDetail.class);
                intent.putExtra("LIGHT_LIST", (Serializable) lights);
                System.out.println(lights.toString());
                startActivity(intent);
            }
        });

        hueAdapter.setOnItemClickListener(new HueAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(getApplicationContext(), Detail.class);
                Light selectedLight = lights.get(position);
                intent.putExtra("LIGHT_OBJECT", (Serializable) selectedLight);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });
    }

    private void refreshData() {
        lights.removeAll(lights);
        apiManager.getHue();
        hueAdapter.notifyDataSetChanged();
        hueAdapter = new HueAdapter(lights);
        recyclerView.setAdapter(hueAdapter);
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
