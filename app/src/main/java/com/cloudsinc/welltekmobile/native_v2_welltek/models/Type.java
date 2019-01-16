package com.cloudsinc.welltekmobile.native_v2_welltek.models;

/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 27/5/17.
 */

public class Type {


    private String room_title;


    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    private String device_id;

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    private int brightness;
    private boolean switch_state;

    public String getScene_id() {
        return scene_id;
    }

    public void setScene_id(String scene_id) {
        this.scene_id = scene_id;
    }

    private String scene_id;

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    private String device_type;
    public String getScence_title() {
        return scence_title;
    }

    public void setScence_title(String scence_title) {
        this.scence_title = scence_title;
    }

    private String scence_title;

    public String getDevice_title() {
        return device_title;
    }

    public void setDevice_title(String device_title) {
        this.device_title = device_title;
    }

    private String device_title;


    public void setRoom_title(String room_title)
    {
        this.room_title=room_title;
    }


    public String getRoom_title()
    {
        return room_title;
    }


    public String getFav_id() {
        return fav_id;
    }

    public void setFav_id(String fav_id) {
        this.fav_id = fav_id;
    }




    public boolean get_switch_state()
    {
        return switch_state;
    }
    public void set_switch_state(boolean switch_state)
    {
        this.switch_state=switch_state;
    }





    private String fav_id;

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }




    public void setFav_status(boolean fav_status) {
        this.fav_status = fav_status;
    }

    private String room_id;

    public String getType() {
        return fav_type;
    }

    public void setFav_type(String fav_type) {
        this.fav_type = fav_type;
    }

    private String fav_type;
    private boolean fav_status;




}