package com.cloudsinc.welltekmobile.native_v2_welltek.utils;

import android.content.Context;

import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;
/**
 * Created by developers on 21/12/18.
 */
public class PackageOperations {

    String TAG=""+this.getClass().getSimpleName();
    public boolean checkFeatures(Context mcontext,String feature_for_check)
    {
        boolean b=false;
        try {
            InputStream is = mcontext.getAssets().open("pivot_packaging.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String str_jsonarr = new String(buffer, "UTF-8");
            Logs.info(TAG + "_Scenesjson", str_jsonarr);
            JSONArray jsonArray = new JSONArray(str_jsonarr);
            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                if(App.getList_seleted_package().contains(jsonObject1.getString("PACKAGE_ID")))
                {
                    Logs.info(TAG + "_seletedpackage_json",""+ jsonObject1);
                    List<String> lst_feature_list=new Gson().fromJson(""+jsonObject1.getJSONArray("PACKAGE_FEATURES"),new TypeToken<List<String>>(){}.getType());
                    if(lst_feature_list.contains(feature_for_check))
                    {
                        Logs.info(TAG + "_package_features",""+ jsonObject1);
                        b=true;
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Logs.error(TAG,"-------"+ex.getMessage());
        }
        return b;
    }
}
