package com.cloudsinc.welltekmobile.native_v2_welltek.models;

/**
 * Created by developers on 21/12/17.
 */

public class Playlist {

    String playlist_album_art;
    String playlist_id;

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

    boolean isLoad;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type="";
    public String getPlaylist_album_art() {
        return playlist_album_art;
    }

    public void setPlaylist_album_art(String playlist_album_art) {
        this.playlist_album_art = playlist_album_art;
    }

    public String getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(String playlist_id) {
        this.playlist_id = playlist_id;
    }

    public String getPlaylist_title() {
        return playlist_title;
    }

    public void setPlaylist_title(String playlist_title) {
        this.playlist_title = playlist_title;
    }

    public String getPlaylist_type() {
        return playlist_type;
    }

    public void setPlaylist_type(String playlist_type) {
        this.playlist_type = playlist_type;
    }

    String playlist_title;
    String playlist_type;
}
