package com.example.rensk.hueapp;

public interface HueListener {
    public void onLightsAvailable(Light light);
    public void onLightsError(String err);
}
