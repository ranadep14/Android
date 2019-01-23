package com.cloudsinc.welltekmobile.native_v2_welltek.utils;
import android.content.Context;
import android.util.Log;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import org.json.JSONObject;
/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 4/8/17.
 */
public class HomeHelthCall {
    public static void getHomeHelth(Context mcontext)
    {
        try {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_provisioned", new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_favorites", new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_simulations", new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_sleep_simulations", new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_dawn_simulations",new JSONObject()));

            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_groups", new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_rooms_by_device", new JSONObject().put("type", "Lighting")));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_rooms", new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_zones", new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_hvac", new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_network_info", new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_nearby_wifis", new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_info",new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_hvac_zone_provisioned",new JSONObject()));
              JSONObject obj = new JSONObject();
            obj.put("data", new JSONObject().put("room", ""));
            obj.put("type", "get_sensor_reading");
            Log.e("Sensor_Data", "" + obj);
            App.getSocket().emit("action", obj);
            JSONObject obj1 = new JSONObject();
             obj1.put("data", new JSONObject().put("notifier_email",new PrefManager(mcontext).getValue("User_Id")));
           //  obj1.put("data", new JSONObject().put("notifier_email","adam.sandler@mstorm.com"));
            obj1.put("type", "get_filter_Notification");
            Log.e("Notification_get_call", "" + obj1);
            App.getSocket().emit("action", obj1);
        }
        catch (Exception ex)
        {
            Log.e("HomeHelthCallError",""+ex.getMessage());
        }
    }
}
