package com.cloudsinc.welltekmobile.native_v2_welltek.models;

/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 15/7/17.
 */

public class Sound {
    private String sound_title;
    private String sound_id;
    private String sound_type;
    private String sonos_song_play_pause_state;
    public void setSound_title(String room_title)
    {
        this.sound_title=room_title;
    }

    public String getSound_type() {
        return sound_type;
    }

    public void setSound_type(String sound_type) {
        this.sound_type = sound_type;
    }

    public String getSound_title()
    {
        return sound_title;
    }
    public void setSound_id(String sound_id)
    {
        this.sound_id = sound_id;
    }
    public String getSound_id()
    {
        return sound_id;
    }
    public String getSonos_song_play_pause_state() {
        return sonos_song_play_pause_state;
    }

    public void setSonos_song_play_pause_state(String sonos_song_play_pause_state) {
        this.sonos_song_play_pause_state = sonos_song_play_pause_state;
    }
}
