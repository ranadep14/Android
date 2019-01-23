package com.cloudsinc.welltekmobile.native_v2_welltek.utils;

import android.util.Log;

import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Room;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Sound;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 12/5/17.
 */

public class FilterJsonArray {

    private static ArrayList<Object> resultarr=new ArrayList();
    public static Object getFirlterByTag(String tag, String cml_id) throws JSONException {
        resultarr=new ArrayList();

        switch (tag)
        {
            case "Room_list":

                System.out.println("==================="+ App.getRoomData());

                if(App.getRoomData()!=null)
                {
                    getArrayList(App.getRoomData().getJSONArray("data"));
                }

                return resultarr;
            case "Sound_list":

                System.out.println("==================="+ App.getRoomData());

                if(App.getSoundData()!=null)
                {
                    getSoundArrayList(App.getSoundData().getJSONArray("data"));
                }

                return resultarr;

        }
        return null;
    }

    public static void getArrayList(JSONArray jsonArray)
    {

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject json = jsonArray.getJSONObject(i);
                    System.out.println("\n==================" + json);
                    Room chooseRoom = new Room();
                    chooseRoom.setRoom_id(json.getString("CML_ID"));
                    chooseRoom.setRoom_title(json.getString("CML_TITLE"));
                    chooseRoom.setRoom_type(json.getString("CML_ROOM_TYPE"));
                   if(json.has("DAWN_RUNNING")) chooseRoom.setDawn_state(json.getBoolean("DAWN_RUNNING"));
                    if(json.has("CML_SCENE_ID")) chooseRoom.setRoom_scene(json.getString("CML_SCENE_ID"));
                    resultarr.add(chooseRoom);
                } catch (Exception ex) {
                    Log.e("Error", ex.getMessage());
                }
            }

       // return resultarr;
    }

    public static void getSoundArrayList(JSONArray jsonArray) {

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject json = jsonArray.getJSONObject(i);
                System.out.println("\n==================" + json);
                Sound sound = new Sound();
                sound.setSound_id(json.getString("CML_ID"));
                sound.setSound_title(json.getString("CML_TITLE"));
                // sound.setSound_type(json.getString("CML_ROOM_TYPE"));
                resultarr.add(sound);
            } catch (Exception ex) {
                Log.e("Error", ex.getMessage());
            }
        }
    }


}
