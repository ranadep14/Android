package com.cloudsinc.welltekmobile.native_v2_welltek.models;


/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 27/5/17.
 */

public class Device {
    private String device_title="";
    private String device_type="";
    private String device_id="";


    public String getAssign_room() {
        return assign_room;
    }

    public void setAssign_room(String assign_room) {
        this.assign_room = assign_room;
    }

    private String assign_room="";
    public void setDevice_title(String device_title)
    {
        this.device_title=device_title;
    }
    public String getDevice_title()
    {
        return device_title;
    }
    public void setDevice_type(String device_type)
    {
        this.device_type=device_type;
    }
    public String getDevice_type()
    {
        return device_type;
    }
    public void setDevice_id(String device_id)
    {
        this.device_id=device_id;
    }
    public String getDevice_id()
    {
        return device_id;
    }

}
