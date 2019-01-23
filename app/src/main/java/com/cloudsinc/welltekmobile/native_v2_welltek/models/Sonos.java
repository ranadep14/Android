package com.cloudsinc.welltekmobile.native_v2_welltek.models;

/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 27/5/17.
 */

public class Sonos {


    public boolean isSelect_flag() {
        return select_flag;
    }

    public void setSelect_flag(boolean select_flag) {
        this.select_flag = select_flag;
    }

    public   boolean select_flag;
    public   boolean shuffle_flag;
    public   boolean next_flag;

    public boolean isNext_flag() {
        return next_flag;
    }

    public String room_id="";

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getRoom_title() {
        return room_title;
    }

    public void setRoom_title(String room_title) {
        this.room_title = room_title;
    }

    public String room_title="";
    public void setNext_flag(boolean next_flag) {
        this.next_flag = next_flag;
    }

    public boolean isPrev_flag() {
        return prev_flag;
    }

    public void setPrev_flag(boolean prev_flag) {
        this.prev_flag = prev_flag;
    }

    public   boolean prev_flag;

    public String getFav_id() {
        return fav_id;
    }

    public void setFav_id(String fav_id) {
        this.fav_id = fav_id;
    }

    String fav_id;

    public boolean isShuffle_flag() {
        return shuffle_flag;
    }

    public void setShuffle_flag(boolean shuffle_flag) {
        this.shuffle_flag = shuffle_flag;
    }

    public boolean isRepeat_flag() {
        return repeat_flag;
    }

    public void setRepeat_flag(boolean repeat_flag) {
        this.repeat_flag = repeat_flag;
    }

    public boolean isMute_flag() {
        return mute_flag;
    }

    public void setMute_flag(boolean mute_flag) {
        this.mute_flag = mute_flag;
    }

    public   boolean repeat_flag;
    public   boolean mute_flag;
    public int getSonos_volume() {
        return sonos_volume;
    }



    public void setSonos_volume(int sonos_volume) {
        this.sonos_volume = sonos_volume;
    }

    private int sonos_volume;

    public String getSonos_track_title() {
        return sonos_track_title;
    }

    public void setSonos_track_title(String sonos_track_title) {
        this.sonos_track_title = sonos_track_title;
    }

    public String getSonos_artist_name() {
        return sonos_artist_name;
    }

    public void setSonos_artist_name(String sonos_artist_name) {
        this.sonos_artist_name = sonos_artist_name;
    }

    public String getSonos_song_play_pause_state() {
        return sonos_song_play_pause_state;
    }

    public void setSonos_song_play_pause_state(String sonos_song_play_pause_state) {
        this.sonos_song_play_pause_state = sonos_song_play_pause_state;
    }

    private String sonos_track_title;
    private String sonos_artist_name;private String sonos_song_play_pause_state;


    public String getSonos_uri() {
        return sonos_uri;
    }

    public String getSonos_album_art() {
        return sonos_album_art;
    }

    public void setSonos_album_art(String sonos_album_art) {
        this.sonos_album_art = sonos_album_art;
    }

    private String sonos_album_art;

    public String getDevice_title() {
        return device_title;
    }

    public void setDevice_title(String device_title) {
        this.device_title = device_title;
    }

    private String device_title;

    public void setSonos_uri(String sonos_uri) {
        this.sonos_uri = sonos_uri;
    }

    private String sonos_uri;

    public String getSonos_id() {
        return sonos_id;
    }

    public void setSonos_id(String sonos_id) {
        this.sonos_id = sonos_id;
    }


    private String sonos_id;


    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    private String device_id;


}
