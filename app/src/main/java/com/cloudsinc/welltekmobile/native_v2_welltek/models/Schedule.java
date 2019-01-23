package com.cloudsinc.welltekmobile.native_v2_welltek.models;

import android.graphics.drawable.Drawable;

/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 27/5/17.
 */

public class Schedule {
   String shedule_id,shedule_time,shedule_title,shedule_desc;
    Drawable shedule_img;

    public void setShedule_id(String shedule_id) {
        this.shedule_id = shedule_id;
    }

    public void setShedule_time(String shedule_time) {
        this.shedule_time = shedule_time;
    }

    public Drawable getShedule_img() {
        return shedule_img;
    }

    public void setShedule_img(Drawable shedule_img) {
        this.shedule_img = shedule_img;
    }

    public void setShedule_title(String shedule_title) {
        this.shedule_title = shedule_title;

    }


    public void setShedule_desc(String shedule_desc) {
        this.shedule_desc = shedule_desc;
    }

    public String getShedule_id() {
        return shedule_id;
    }

    public String getShedule_time() {
        return shedule_time;
    }

    public String getShedule_title() {
        return shedule_title;
    }

    public String getShedule_desc() {
        return shedule_desc;
    }
}
