package com.cloudsinc.welltekmobile.native_v2_welltek.utils;
import android.util.Log;
import org.json.JSONObject;
/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 10/5/17.
 */
public class JsonObjectCreater {
    public static JSONObject getJsonObject(String type, JSONObject jsonObject)
    {
        System.out.println("---------------datamap");
        try {
            JSONObject resultJsonObject = new JSONObject();
            resultJsonObject.put("data", jsonObject);
            resultJsonObject.put("type",type);
            Log.e("emit_json_object",""+resultJsonObject);
            return resultJsonObject;
        }
        catch (Exception ex){
            Log.e("Error","----"+ex.getMessage());
        }
        return null;
    }
}
