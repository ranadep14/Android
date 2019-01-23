package com.cloudsinc.welltekmobile.native_v2_welltek.utils;

import android.util.Log;

import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;

import org.json.JSONObject;

/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 4/8/17.
 */

public class ProvisionDevicesCall {
    public static void getProvisional()
    {
        try {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_zones", new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_provisioned",new JSONObject()));

        }
        catch (Exception ex)
        {
            Log.e("Error",ex.getMessage());
        }
    }
}
