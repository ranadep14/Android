package com.cloudsinc.welltekmobile.native_v2_welltek.models;

import java.util.ArrayList;

/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V4 on 28/8/17.
 */

public class Sleepsim {
   private String time;
    private String days;
    private String sim_id;
    private boolean switch_state;
    private ArrayList<Integer> arr_days=new ArrayList<>();
    public void set_time(String time)
    {
        this.time=time;
    }

    public ArrayList<Integer> getArr_days() {
        return arr_days;
    }

    public void setArr_days(ArrayList<Integer> arr_days) {
        this.arr_days = arr_days;
    }

    public String get_time()
    {
        return time;
    }

    public void set_sim_id(String sim_id)
    {
        this.sim_id=sim_id;
    }
    public String get_sim_id()
    {
        return sim_id;
    }
    public void set_days(String days)
    {
        this.days=days;
    }
    public String get_days()
    {
        return days;
    }
    public boolean get_switch_state()
    {
        return switch_state;
    }
    public void set_switch_state(boolean switch_state)
    {
        this.switch_state=switch_state;
    }

    /*public int getSimType() {
        return  simtype;
    }

    public void setSimType(String room_title)
    {
        this.room_title=room_title;
    }
*/
}
