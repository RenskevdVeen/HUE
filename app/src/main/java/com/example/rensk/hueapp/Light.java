package com.example.rensk.hueapp;

import java.io.Serializable;

public class Light implements Serializable {

    private String lightnum;
    private String aan;
    private int brightness;
    private int hue;
    private int saturation;

    public Light(String lightnum, String aan, int brightness, int hue, int saturation) {
        this.lightnum = lightnum;
        this.aan = aan;
        this.brightness = brightness;
        this.hue = hue;
        this.saturation = saturation;
    }

    public String getLightnum() {
        return lightnum;
    }

    public void setLightnum(String lightnum) {
        this.lightnum = lightnum;
    }

    public String getAan() {
        return aan;
    }

    public void setAan(String aan) {
        this.aan = aan;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }


    @Override
    public String toString() {
        return "Light{" +
                "lightnum='" + lightnum + '\'' +
                ", aan=" + aan +
                ", brightness=" + brightness +
                ", hue=" + hue +
                ", saturation=" + saturation +
                '}';
    }
}
