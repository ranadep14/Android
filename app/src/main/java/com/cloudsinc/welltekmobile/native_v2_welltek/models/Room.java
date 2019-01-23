package com.cloudsinc.welltekmobile.native_v2_welltek.models;

/**
 * Created by developers on 27/5/17.
 */

public class Room {
   private String room_title="";
    private String room_id="";
    private String room_type="";
    String fav_id="";

    public String getFav_id() {
        return fav_id;
    }

    public void setFav_id(String fav_id) {
        this.fav_id = fav_id;
    }
    public int getCnt_lights() {
        return cnt_lights;
    }

    public void setCnt_lights(int cnt_lights) {
        this.cnt_lights = cnt_lights;
    }

    public int getCnt_blinds() {
        return cnt_blinds;
    }

    public void setCnt_blinds(int cnt_blinds) {
        this.cnt_blinds = cnt_blinds;
    }

    public int getCnt_audios() {
        return cnt_audios;
    }

    public void setCnt_audios(int cnt_audios) {
        this.cnt_audios = cnt_audios;
    }

    private int cnt_lights=0;
    private int cnt_blinds=0;
    private int cnt_audios=0;

    public boolean isDawn_state() {
        return dawn_state;
    }

    public void setDawn_state(boolean dawn_state) {
        this.dawn_state = dawn_state;
    }

    boolean dawn_state=false;
    public String getRoom_scene() {
        return room_scene;
    }

    public void setRoom_scene(String room_scene) {
        this.room_scene = room_scene;
    }

    private String room_scene="";
    public void setRoom_title(String room_title)
    {
        this.room_title=room_title;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getRoom_title()
    {
        return room_title;
    }
    public void setRoom_id(String room_id)
    {
        this.room_id=room_id;
    }
    public String getRoom_id()
    {
        return room_id;
    }

}
