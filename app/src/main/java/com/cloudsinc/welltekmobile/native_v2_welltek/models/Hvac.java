package com.cloudsinc.welltekmobile.native_v2_welltek.models;

/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 19/7/17.
 */

public class Hvac {
    String id;

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }


    public int getLower_point() {
        return lower_point;
    }

    public void setLower_point(int lower_point) {
        this.lower_point = lower_point;
    }

    public int getHight_point() {
        return hight_point;
    }

    public void setHight_point(int hight_point) {
        this.hight_point = hight_point;
    }

    int lower_point=0;
    int hight_point=0;
    int point=10;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public int getHvac_point() {
        return hvac_point;
    }

    public void setHvac_point(int hvac_point) {
        this.hvac_point = hvac_point;
    }

    int hvac_point;

    String title;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type;

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    int brightness;

    public boolean isLigh_state() {
        return ligh_state;
    }

    public void setLigh_state(boolean ligh_state) {
        this.ligh_state = ligh_state;
    }

    boolean ligh_state;
}
