package com.cloudsinc.welltekmobile.native_v2_welltek.models;

/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 19/7/17.
 */

public class Blind {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




    public String getFav_id() {
        return fav_id;
    }

    public void setFav_id(String fav_id) {
        this.fav_id = fav_id;
    }

    String fav_id;

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

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    String device_id;


    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title;

    int point;

    boolean state=false;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;

    }

    String type;




}
