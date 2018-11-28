package com.example.rensk.hueapp;

public interface HueListener {
    void onLightsAvailable(Light light);
    void onLightsError(String err);
}
