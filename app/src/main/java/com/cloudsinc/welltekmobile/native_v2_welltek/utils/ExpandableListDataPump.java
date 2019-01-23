package com.cloudsinc.welltekmobile.native_v2_welltek.utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        List<String> home = new ArrayList<String>();
        home.add("Wi-Fi & Network Settings");
        home.add("Time & Date");
        home.add("Location");
        List<String> dev_setting = new ArrayList<String>();
        List<String> notification = new ArrayList<String>();
        List<String> profile = new ArrayList<String>();
        List<String> privacy = new ArrayList<String>();
        List<String> logout = new ArrayList<String>();
        //logout action
        expandableListDetail.put("Home Settings", home);
        expandableListDetail.put("Device Settings", dev_setting);
        expandableListDetail.put("Notification Settings", notification);
        expandableListDetail.put("Profile Settings", profile);
        expandableListDetail.put("Privacy Policy", privacy);
        expandableListDetail.put("Legal", logout);
        return expandableListDetail;
    }
}
