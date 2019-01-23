package com.cloudsinc.welltekmobile.native_v2_welltek.models;

/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 19/7/17.
 */

public class Light {
    String id;

    public String getRoom_title() {
        return room_title;
    }

    public void setRoom_title(String room_title) {
        this.room_title = room_title;
    }

    String room_title="";

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    String room_id="";

    public String getFav_id() {
        return fav_id;
    }

    public void setFav_id(String fav_id) {
        this.fav_id = fav_id;
    }

    String fav_id;

    public String getScene_id() {
        return scene_id;
    }

    public void setScene_id(String scene_id) {
        this.scene_id = scene_id;
    }

    String scene_id;
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


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    boolean status=false;

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

    int brightness=0;

    public boolean isLigh_state() {
        return ligh_state;
    }

    public void setLigh_state(boolean ligh_state) {
        this.ligh_state = ligh_state;
    }

    boolean ligh_state=false;
}
