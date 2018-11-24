package com.example.rensk.hueapp;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HueApiManager {
    RequestQueue queue;
    HueListener listener;

    public HueApiManager (Context context, HueListener listener){
        this.queue = Volley.newRequestQueue(context);
        this.listener = listener;
    }

    public void getHue(){
        final String url = "http://192.168.2.4/api/8144c280f65aa9c4749a7657ae82b1b";
        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("VOLLEY_TAG", response.toString());

                            try {
                                for ( int i = 1; i<10; i++) {
                                    if (i == 4) continue;

                                    String lightnum = response.getJSONObject("lights").getJSONObject(i + "").getString("name");
                                    String onoff = response.getJSONObject("lights").getJSONObject(i + "").getJSONObject("state").getString("on");
                                    int brightness = response.getJSONObject("lights").getJSONObject(i + "").getJSONObject("state").getInt("bri");
                                    int hue = response.getJSONObject("lights").getJSONObject(i + "").getJSONObject("state").getInt("hue");
                                    int saturation = response.getJSONObject("lights").getJSONObject(i + "").getJSONObject("state").getInt("sat");



                                    Light light = new Light(lightnum, onoff, brightness, hue, saturation);
                                    listener.onLightsAvailable(light);


                                    //System.out.println("Kom ik hier");
                                    Log.d("LIGHTS", light.toString());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("LIGHTS", error.toString());
                    }
                }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

}
