package com.cloudsinc.welltekmobile.native_v2_welltek.utils;

import android.util.Log;

import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The FavoritesOperations is performing all favorite related operations
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */



public  class FavoritesOperations {


    /** this method is for emit delete call of favorite
     *
     * @param fav_id is favorite id
     */
    public static void deleteFav(String fav_id)
    {


        Log.e("Position","................"+fav_id);

        try
        {
            JSONObject dataObj=new JSONObject();


            dataObj.put("ACTIVE_STATUS","1");
            dataObj.put("CML_ID",fav_id);
            dataObj.put("Id",fav_id);
           if(!(fav_id.contains("light")||fav_id.contains("blind")||fav_id.contains("audio")))dataObj.put("KEY_VAL",fav_id);
           else dataObj.put("KEY_VAL",getWholeHomeKeyVal(fav_id));
            dataObj.put("roomId", "");
            dataObj.put("sceneId", "");
            dataObj.put("selected",false);


            JSONObject obj = new JSONObject();
            obj.put("data", dataObj);
            obj.put("type", "delete_favorites");
            Log.e("DeleteJsonObject",""+obj);
            App.getSocket().emit("action", obj);
        }
        catch (Exception ex)
        {
            Log.e("Error",ex.getMessage());
        }
    }

    /** this method is for emit delete call of scene room favorite
     *
     * @param scene_id is scene id
     */
    public static void deleteRoomFav(String scene_id)
    {


        Log.e("Position","scene_id................"+getFavoriteId(scene_id));

        try
        {
            JSONObject dataObj=new JSONObject();


            dataObj.put("ACTIVE_STATUS","1");
            dataObj.put("CML_ID",getFavoriteId(scene_id));
            dataObj.put("Id",getFavoriteId(scene_id));
            dataObj.put("KEY_VAL",getFavoriteId(scene_id));
            dataObj.put("roomId", App.getRoom().getRoom_id());
            dataObj.put("sceneId", "");
            dataObj.put("selected",false);


            JSONObject obj = new JSONObject();
            obj.put("data", dataObj);
            obj.put("type", "delete_favorites");
            Log.e("DeleteJsonObject",""+obj);
            App.getSocket().emit("action", obj);
        }
        catch (Exception ex)
        {
            Log.e("Error",ex.getMessage());
        }
    }

    /** this method is for emit add call of device favorite
     *
     * @param device_id is device_id
     */
    public static void addFav(String device_id)
    {
        try
        {
            String id="fav#"+ System.currentTimeMillis();
            JSONObject dataObj=new JSONObject();
            dataObj.put("ACTIVE_STATUS","1");
            dataObj.put("CML_ID",id);
            dataObj.put("Id",id);
            dataObj.put("KEY_VAL",id);
            dataObj.put("deviceId",device_id);
            dataObj.put("roomId", getRoomId(device_id));
            dataObj.put("roomTitle", getRoomTitle(getRoomId(device_id)));
            dataObj.put("selected",true);
            JSONObject obj = new JSONObject();
            obj.put("data", dataObj);
            obj.put("type", "add_to_favorites");
            Log.d("Add to Fav",""+obj);
            App.getSocket().emit("action", obj);
        }
        catch (Exception ex)
        {
            Log.e("Error",ex.getMessage());
        }
    }
    public static void addFavWholeHome(String device_id,String type)
    {
        try
        {
            JSONObject dataObj=new JSONObject();
            dataObj.put("ACTIVE_STATUS","1");
            dataObj.put("CML_ID",device_id);
            dataObj.put("Id",device_id);
            dataObj.put("KEY_VAL","fav#"+System.currentTimeMillis());
            dataObj.put("deviceId","");
            dataObj.put("roomId", "");
            dataObj.put("roomTitle", "Whole Home");
            dataObj.put("type",type);
            dataObj.put("selected",true);
            JSONObject obj = new JSONObject();
            obj.put("data", dataObj);
            obj.put("type", "add_to_favorites");
            Log.d("Add to Fav",""+obj);
            App.getSocket().emit("action", obj);
        }
        catch (Exception ex)
        {
            Log.e("Error",ex.getMessage());
        }
    }

    /** this method is for emit delete call of scene favorite
     *
     * @param scene_id is scene id
     */
    public static void addSceneFav(String scene_id)
    {
        try
        {
            String id="fav#"+ System.currentTimeMillis();
            JSONObject dataObj=new JSONObject();
            dataObj.put("ACTIVE_STATUS","1");
            dataObj.put("CML_ID",id);
            dataObj.put("Id",id);
            dataObj.put("KEY_VAL",id);
            dataObj.put("sceneId",scene_id);
            JSONObject obj = new JSONObject();
            obj.put("data", dataObj);
            obj.put("type", "add_to_favorites");
            Log.d("Add to Fav",""+obj);
            App.getSocket().emit("action", obj);
        }
        catch (Exception ex)
        {
            Log.e("Error",ex.getMessage());
        }
    }

    /** this method is for emit add call of room scene favorite
     *
     * @param room_id is roomid id
     * @param scene_id is scene id
     */

    public static void addSceneRoomFav(String room_id,String scene_id)
    {
        try
        {
            String id="fav#"+ System.currentTimeMillis();
            JSONObject dataObj=new JSONObject();
            dataObj.put("ACTIVE_STATUS","1");
            dataObj.put("CML_ID",id);
            dataObj.put("Id",id);
            dataObj.put("KEY_VAL",id);
            dataObj.put("roomId",room_id);
            dataObj.put("sceneId",scene_id);
            JSONObject obj = new JSONObject();
            obj.put("data", dataObj);
            obj.put("type", "add_to_favorites");
            Log.d("Add to Fav",""+obj);
            App.getSocket().emit("action", obj);
        }
        catch (Exception ex)
        {
            Log.e("Error",ex.getMessage());
        }
    }


    /** this method is for emit add call of room favorite
     *
     * @param scence_id is scene id
     */
    public static void addRoomFav(String scence_id)
    {
        try
        {
            String id="fav#"+ System.currentTimeMillis();
            JSONObject dataObj=new JSONObject();
            dataObj.put("ACTIVE_STATUS","1");
            dataObj.put("CML_ID",id);
            dataObj.put("Id",id);
            dataObj.put("KEY_VAL",id);
            dataObj.put("roomId",App.getRoom().getRoom_id());
            dataObj.put("roomTitle",getRoomTitle(App.getRoom().getRoom_id()));
            dataObj.put("deviceId","");
            dataObj.put("sceneId",""+scence_id);
            dataObj.put("sceneTitle", "");



            dataObj.put("selected",true);


            JSONObject obj = new JSONObject();
            obj.put("data", dataObj);
            obj.put("type", "add_to_favorites");
            Log.d("Add to Fav",""+obj);
            App.getSocket().emit("action", obj);
        }
        catch (Exception ex)
        {
            Log.e("Error",ex.getMessage());
        }
    }

    /** this method is for get room title from room id
     *
     * @param room_id is scene id
     *  @return String room title
     */
    public static String getRoomTitle(String room_id)
    {
        String room_title="";
        try {


            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("CML_ID").equals(room_id)) {
                    room_title = jsonObject.getString("CML_TITLE");
                }
            }
        }
        catch (Exception ex){Log.e("Error",ex.getMessage());}
        return room_title;
    }

    /** this method is for get device title
     *
     * @param device_id is scene id
     *  @return String room title
     */
    public static String getDeviceTitle(String device_id)
    {
        String device_title="";
        try {


            JSONArray jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("CML_ID").equals(device_id)||jsonObject.getString("Id").equals(device_id)) {
                    device_title = jsonObject.getString("CML_TITLE");
                }
            }
        }
        catch (Exception ex){Log.e("Error",ex.getMessage());}
        return device_title;
    }


    /** this method is for get favorite id
     *
     * @param device_id is scene id
     *  @return String room title
     */
    public static String getFavId(String device_id)
    {
        String fav_id="";
        try {


            JSONArray jsonArray = App.getFavData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if(jsonObject.has("deviceId")) {
                    if (jsonObject.getString("deviceId").equals(device_id)) {
                        fav_id = jsonObject.getString("CML_ID");
                    }
                }
                if(jsonObject.has("roomId")) {
                    if (jsonObject.getString("roomId").equals(device_id)) {
                        fav_id = jsonObject.getString("CML_ID");
                    }
                }
            }
        }
        catch (Exception ex){Log.e("Error",ex.getMessage());}
        return fav_id;
    }
    public static String getWholeHomeFavId(String type)
    {
        String fav_id="";
        try {


            JSONArray jsonArray = App.getFavData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if(jsonObject.has("type")) {
                    if (jsonObject.getString("type").equals(type)) {
                        fav_id = jsonObject.getString("CML_ID");
                    }
                }
            }
        }
        catch (Exception ex){Log.e("Error",ex.getMessage());}
        return fav_id;
    }
    public static String getWholeHomeKeyVal(String fav_id)
    {
        String key_val="";
        try {


            JSONArray jsonArray = App.getFavData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if(jsonObject.has("CML_ID")) {
                    if (jsonObject.getString("CML_ID").equals(fav_id)) {
                        key_val = jsonObject.getString("KEY_VAL");
                    }
                }
            }
        }
        catch (Exception ex){Log.e("Error",ex.getMessage());}
        return key_val;
    }


    /** this method is for get favorite id
     *
     * @param room_id is room id
     *  @param scene_id is scene id
     *  @return String favorite id.
     */
    public static String getFavId(String room_id,String scene_id)
    {
        String fav_id="";
        try {


            JSONArray jsonArray = App.getFavData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);


                Log.e("room_and_scene_id",""+room_id+"/"+scene_id);
                if(jsonObject.has("roomId")&&jsonObject.has("sceneId")) {
                    if (jsonObject.getString("roomId").equals(room_id)&&jsonObject.getString("sceneId").equals(scene_id)) {
                        fav_id = jsonObject.getString("CML_ID");
                    }
                }
            }
        }
        catch (Exception ex){Log.e("Error",ex.getMessage());}
        return fav_id;
    }



    /** this method is for get room title from room id
     *
     *
     *  @param scene_id is scene id
     *  @return String scene favorite id.
     */
    public static String getSceneFavId(String scene_id)
    {
        String fav_id="";
        try {


            JSONArray jsonArray = App.getFavData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if(!jsonObject.has("deviceId")&&!jsonObject.has("roomId")) {

                    if(scene_id.equals(jsonObject.getString("sceneId")))
                    {
                        fav_id=jsonObject.getString("CML_ID");
                    }
                }

            }
        }
        catch (Exception ex){Log.e("Error",ex.getMessage());}
        return fav_id;
    }

    /** this method is for get favorite id from scene id
     *
     *
     *  @param scene_id is scene id
     *  @return String scene favorite id.
     */
    public static String getFavoriteId(String scene_id)
    {
        String fav_id="";
        try {


            JSONArray jsonArray = App.getFavData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("sceneId").equals(scene_id)) {
                    fav_id = jsonObject.getString("CML_ID");
                }
            }
        }
        catch (Exception ex){Log.e("Error",ex.getMessage());}
        return fav_id;
    }


    /** this method is for get room id from device id
     *
     *
     *  @param device_id is device id
     *  @return String room id.
     */
    public static String getRoomId(String device_id)
    {
        String roomId="";
        try {


            if(!device_id.contains("group")) {
                JSONArray jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (jsonObject.getString("CML_ID").equals(device_id)||jsonObject.getString("Id").equals(device_id)) {
                        roomId = jsonObject.getString("room");
                        break;
                    }
                }
            }
            else
            {
                JSONArray jsonArray = App.getGroupJson().getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (jsonObject.getString("CML_ID").equals(device_id)) {
                        roomId = jsonObject.getString("room");
                        break;
                    }
                }
            }
        }
        catch (Exception ex){Log.e("Error",ex.getMessage());}
        return roomId;
    }

}
