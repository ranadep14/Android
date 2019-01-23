package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.ExperiencesFavorites;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FavoritesOperations;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.PROVISIONAL_DEVICES;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.ROOMS_DEVICES;
/**
 * This class containing functionality related to displaying experiences as favorite list and perform action on it.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 *
 */
public class ExperiencesFavoritesListAdapter extends RecyclerView.Adapter<ExperiencesFavoritesListAdapter.ViewHolder> {
    private ArrayList<ExperiencesFavorites> arrayList;
    private Context mcontext;
    private JSONArray roomJsonArray;
    private Fragment mfrag;
    private String TAG=ExperiencesFavoritesListAdapter.this.getClass().getName();
    private FragmentManager mfm;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView txt_exp_fav_title;
        TextView txt_rooms,txt_active_deactive;
        ImageView img_add_fav,ic_climate, ic_purification,ic_circadian,ic_cook,ic_music,ic_blind,ic_light;
        LinearLayout lin_active_deactive;
        RelativeLayout rel_selection;
        RelativeLayout rel_scene_background;
        public ViewHolder(View v) {
            super(v);
            this.txt_exp_fav_title = v.findViewById(R.id.txt_exp_fav_title);
            this.txt_rooms = v.findViewById(R.id.txt_rooms);
            this.txt_active_deactive = v.findViewById(R.id.txt_active_deactive);
            this.lin_active_deactive = v.findViewById(R.id.lin_active_deactive);
            this.img_add_fav = v.findViewById(R.id.img_add_fav);
            this.ic_blind = v.findViewById(R.id.ic_blind);
            this.ic_circadian = v.findViewById(R.id.ic_circadian);
            this.ic_climate = v.findViewById(R.id.ic_climate);
            this.ic_cook = v.findViewById(R.id.ic_cook);
            this.ic_light = v.findViewById(R.id.ic_light);
            this.ic_music = v.findViewById(R.id.ic_music);
            this.ic_purification = v.findViewById(R.id.ic_porification);
            this.rel_selection = v.findViewById(R.id.rel_selection);
            this.rel_scene_background = v.findViewById(R.id.rel_scene_background);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public ExperiencesFavoritesListAdapter(Fragment mfrag, Context context, ArrayList<ExperiencesFavorites> myDataset, FragmentManager fm) {
        arrayList = myDataset;
        this.mfrag=mfrag;
        mcontext=context;
        mfm=fm;
        roomJsonArray=new JSONArray();
         Logs.info(TAG+"_LightistSIze",""+arrayList.size());
    }
    public void reload(ArrayList<ExperiencesFavorites> myDataset)
    {
        this.arrayList=myDataset;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public ExperiencesFavoritesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_experiences_favorite_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        int room_count=roomcount(arrayList.get(position).getScene_id());
         Logs.info(TAG+"_RoomCount",""+room_count);
          setSceneIcons(arrayList.get(position).getScene_id(),holder.ic_light,holder.ic_music,holder.ic_cook,holder.ic_climate,holder.ic_circadian,holder.ic_blind,holder.ic_purification);
        if(arrayList.get(position).getRoom_id()!=""&&arrayList.get(position).getScene_id()!="") {
            holder.txt_exp_fav_title.setText(FavoritesListAdapter.getRoomTitle(arrayList.get(position).getRoom_id()));
            holder.txt_rooms.setVisibility(View.VISIBLE);
            holder.txt_rooms.setText(getSceneTitle(arrayList.get(position).getScene_id(),holder.rel_scene_background));
        }
        else
        {
            holder.txt_exp_fav_title.setText(getSceneTitle(arrayList.get(position).getScene_id(),holder.rel_scene_background));
            holder.txt_rooms.setVisibility(View.VISIBLE);
           if(room_count==0)holder.txt_rooms.setText(arrayList.get(position).getScene_id().equals("7")?"Kitchen Only\n":"");
            else if(room_count==1) holder.txt_rooms.setText((arrayList.get(position).getScene_id().equals("7")?"Kitchen Only\n":"")+getSingleRoom(arrayList.get(position).getScene_id()));
            else holder.txt_rooms.setText((arrayList.get(position).getScene_id().equals("7")?"Kitchen Only\n":"")+room_count + " Rooms");
        }
        if(arrayList.get(position).getRoom_id()!=""&&arrayList.get(position).getScene_id()!="") {
           if(checkSingleRoomSceneActive(arrayList.get(position).getScene_id(),arrayList.get(position).getRoom_id()))
           {
               setVisiblity(holder.txt_active_deactive,holder.rel_selection);
           }
           else
           {
               holder.txt_active_deactive.setText(arrayList.get(position).getScene_id().equals("1")?"RESET":"");
               holder.rel_selection.setVisibility(View.GONE);
           }
        }
        else
        {
            if (room_count > 0) {
                setVisiblity(holder.txt_active_deactive,holder.rel_selection);
            } else {
                holder.txt_active_deactive.setText(arrayList.get(position).getScene_id().equals("1")?"RESET":"");
                holder.rel_selection.setVisibility(View.GONE);
            }
        }
        holder.img_add_fav.setTag(position);
        holder.img_add_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                FavoritesOperations.deleteFav(arrayList.get(pos).getFav_id());
            }
        });
        holder.lin_active_deactive.setTag(position);
        holder.lin_active_deactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                int room_count=roomcount(arrayList.get(pos).getScene_id());
                 Logs.info(TAG+"_Room_Count",""+room_count);
                if(arrayList.get(pos).getRoom_id()!=""&&arrayList.get(pos).getScene_id()!="")
                {
                    if(checkSingleRoomSceneActive(arrayList.get(pos).getScene_id(),arrayList.get(pos).getRoom_id())) {
                        activateSingleScene(arrayList.get(pos).getScene_id(),arrayList.get(pos).getRoom_id(), false);
                    }
                    else
                    {
                        activateSingleScene(arrayList.get(pos).getScene_id(),arrayList.get(pos).getRoom_id(), true);
                    }
                }
                else
                {
                    if(room_count>0) {
                        activateScene(arrayList.get(pos).getScene_id(), false);
                    }
                    else
                    {
                        activateScene(arrayList.get(pos).getScene_id(), true);
                    }
                }
            }
        });
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    /**
     * maintain state of experiences
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param  txt_select Textview
     * @param rel_select Relativelayout
     */
    public void setVisiblity(TextView txt_select,RelativeLayout rel_select)
    {
        txt_select.setText("ACTIVE");
        txt_select.setVisibility(View.VISIBLE);
        rel_select.setVisibility(View.VISIBLE);
    }
    /**
     * set scene title
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     */
    private String getSceneTitle(String scene_id,RelativeLayout relativeLayout)
    {
        String str_scene_title="";
        switch (scene_id)
        {
            case "1":
                str_scene_title=""+mcontext.getResources().getString(R.string.activites_comfort);
                relativeLayout.setBackground(mcontext.getResources().getDrawable(R.drawable.darwin_comfort));
                break;
            case "6":
                str_scene_title=""+mcontext.getResources().getString(R.string.activites_prepare);
                relativeLayout.setBackground(mcontext.getResources().getDrawable(R.drawable.sleep_comfort));
                break;
            case "2":
                str_scene_title=""+mcontext.getResources().getString(R.string.activites_relax);
                relativeLayout.setBackground(mcontext.getResources().getDrawable(R.drawable.relax_comfort));
                break;
            case "7":
                str_scene_title=""+mcontext.getResources().getString(R.string.activites_cooking);
                relativeLayout.setBackground(mcontext.getResources().getDrawable(R.drawable.cooking_comfort));
                break;
            case "3":
                str_scene_title=""+mcontext.getResources().getString(R.string.activites_energise);
                relativeLayout.setBackground(mcontext.getResources().getDrawable(R.drawable.energize_comfort));
                break;
            case "A":
                str_scene_title=""+mcontext.getResources().getString(R.string.activites_circadian_demo);
                relativeLayout.setBackground(mcontext.getResources().getDrawable(R.drawable.darwin_comfort));
                break;
        }
        return str_scene_title;
    }
    /**
     * returns room count
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param senceid string
     * @return int
     */
    private int roomcount(String senceid)
    {
        int count=0;
        try
        {
            Iterator<String> keys = App.getDataStorageJson().getJSONObject(ROOMS_DEVICES).keys();
            while (keys.hasNext()) {
                JSONObject jsonObject=App.getDataStorageJson().getJSONObject(ROOMS_DEVICES).getJSONObject(keys.next());
                if(jsonObject.getString("CML_SCENE_ID").equals(senceid))
                {
                     Logs.info(TAG+"_aaaaaaaaaaaa",""+jsonObject);
                    ++count;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return count;
    }
    /**
     * returns room title from scene id
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param senceid String
     * @return String
     */
    private String getSingleRoom(String senceid)
    {
        String room_title="";
        try
        {
            JSONArray jsonArray=App.getRoomDeviceData().getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_SCENE_ID").equals(senceid))
                {
                     Logs.info(TAG+"_aaaaaaaaaaaa",""+jsonObject);
                    room_title=jsonObject.getString("CML_TITLE");
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return room_title;
    }
    /**
     * check given scene is applied on room or not
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param room_id String
     * @param senceid String
     * @return boolean
     *
     */
    public boolean checkSingleRoomSceneActive(String senceid,String room_id)
    {
        boolean check_flag=false;
        try
        {
            JSONArray jsonArray=App.getRoomDeviceData().getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_SCENE_ID").equals(senceid)&&jsonObject.getString("CML_ID").equals(room_id))
                {
                     Logs.info(TAG+"_aaaaaaaaaaaa",""+jsonObject);
                    check_flag=true;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return check_flag;
    }
    /**
     * emit call for activate scene
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param scene_id String
     * @param active_deactive boolean
     *
     */
    public void activateScene(String scene_id,boolean active_deactive)
    {
        try {
             Logs.info(TAG+"_WhereIm","IM in SCene");
            JSONObject jsonObject = new JSONObject();
            if(active_deactive) {
                if(!scene_id.equals("7")) {
                    try {
                        JSONObject dataJson = new JSONObject();
                        dataJson.put("scene", "" + scene_id);
                        dataJson.put("home", "all");
                        JSONObject obj = new JSONObject();
                        obj.put("data", dataJson);
                        obj.put("type", "trigger_scene");
                        Logs.info(TAG + "_scene_light", "" + obj);
                        App.getSocket().emit("action", obj);
                    } catch (Exception ex) {
                        Log.e("EEEError", "" + ex.getMessage());
                    }
                }
                else
                {
                        jsonObject.put("selectedArr",getAllRoomArray(scene_id));
                        jsonObject.put("deselectedArr", new JSONArray());
                        jsonObject.put("scene", ""+scene_id);
                        JSONObject resultJsonObject = new JSONObject();
                        resultJsonObject.put("data", jsonObject);
                        resultJsonObject.put("type", "activate_scene");
                        System.out.println("activate_sceneeeeeeeeeeeeeeeeeeeee" + resultJsonObject);
                        App.getSocket().emit("action", resultJsonObject);
                }
            }
            else {
                jsonObject.put("selectedArr", new JSONArray());
                jsonObject.put("deselectedArr", getRoomArray(scene_id));
                jsonObject.put("scene", "" + scene_id);
                JSONObject resultJsonObject = new JSONObject();
                resultJsonObject.put("data", jsonObject);
                resultJsonObject.put("type", "activate_scene");
                System.out.println("activate_sceneeeeeeeeeeeeeeeeeeeee" + resultJsonObject);
                App.getSocket().emit("action", resultJsonObject);
            }
        } catch (Exception ex) {
             Logs.error(TAG+"_Error", "" + ex.getMessage());
        }
    }
    /**
     * activate scene on perticulaer room
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param active_deactive string
     * @param room_id string
     * @param scene_id boolean
     */
    public void activateSingleScene(String scene_id,String room_id,boolean active_deactive)
    {
        try {
             Logs.info(TAG+"_WhereIm","IM in singleSCene");
            JSONObject jsonObject = new JSONObject();
            if(active_deactive) {
                jsonObject.put("selectedArr",new JSONArray().put(room_id));
                jsonObject.put("deselectedArr", new JSONArray());
            }
            else
            {
                jsonObject.put("selectedArr",new JSONArray());
                jsonObject.put("deselectedArr",new JSONArray().put(room_id));
            }
            jsonObject.put("scene", ""+scene_id);
            JSONObject resultJsonObject = new JSONObject();
            resultJsonObject.put("data", jsonObject);
            resultJsonObject.put("type", "activate_scene");
            System.out.println("activate_sceneeeeeeeeeeeeeeeeeeeee" + resultJsonObject);
            App.getSocket().emit("action", resultJsonObject);
        } catch (Exception ex) {
             Logs.error(TAG+"_Error", "" + ex.getMessage());
        }
    }
    /**
     * set scene icons on activate and deactivate
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param scene_id string
     * @param ic_blind Imageview
     * @param ic_circadian Imageview
     * @param ic_climate Imageview
     * @param ic_cook Imageview
     * @param ic_light Imageview
     * @param ic_music Imageview
     * @param ic_purification Imageview
     */
    public void setSceneIcons(String scene_id,ImageView ic_light,ImageView ic_music,ImageView ic_cook,ImageView ic_climate,ImageView ic_circadian,ImageView ic_blind,ImageView ic_purification)
    {
        setInvisible(ic_light,ic_music,ic_cook,ic_climate,ic_circadian,ic_blind,ic_purification);
        switch (scene_id) {
            case "1":
                ic_circadian.setVisibility(View.VISIBLE);
                ic_purification.setVisibility(View.VISIBLE);
                break;
            case "6":
                ic_light.setVisibility(View.VISIBLE);
                ic_climate.setVisibility(View.VISIBLE);
                ic_music.setVisibility(View.VISIBLE);
                ic_blind.setVisibility(View.VISIBLE);
                break;
            case "2":
                ic_light.setVisibility(View.VISIBLE);
                ic_purification.setVisibility(View.VISIBLE);
                break;
            case "7":
                ic_light.setVisibility(View.VISIBLE);
                ic_cook.setVisibility(View.VISIBLE);
                break;
            case "3":
                ic_light.setVisibility(View.VISIBLE);
                ic_purification.setVisibility(View.VISIBLE);
                break;
            case "A":
                ic_circadian.setVisibility(View.VISIBLE);
                ic_purification.setVisibility(View.VISIBLE);
                break;
        }
    }
    /**
     * change visibility of given componets
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param ic_purification Imageview
     * @param ic_music Imageview
     * @param ic_light Imageview
     * @param ic_climate Imageview
     * @param ic_circadian Imageview
     * @param ic_blind Imageview
     * @param ic_cook Imageview
     *
     */
    public void setInvisible(ImageView ic_light,ImageView ic_music,ImageView ic_cook,ImageView ic_climate,ImageView ic_circadian,ImageView ic_blind,ImageView ic_purification)
    {
        ic_light.setVisibility(View.GONE);
        ic_music.setVisibility(View.GONE);
        ic_cook.setVisibility(View.GONE);
        ic_climate.setVisibility(View.GONE);
        ic_circadian.setVisibility(View.GONE);
        ic_blind.setVisibility(View.GONE);
        ic_purification.setVisibility(View.GONE);
    }
    public JSONArray getRoomArray(String scene_id)
    {
        JSONArray roomJsonArray=new JSONArray();
        try
        {
            JSONArray jsonArray=App.getRoomDeviceData().getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getInt("CML_SCENE_ID")==Integer.parseInt(scene_id))
                {
                    roomJsonArray.put(jsonObject.getString("CML_ID"));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return roomJsonArray;
    }
    public JSONArray getAllRoomArray(String scene_id)
    {
        ArrayList<String> arr_scene_wise_room=new ArrayList<>();
        try {
            InputStream is = mcontext.getAssets().open("scene_wise_room.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonarr = new String(buffer, "UTF-8");
             Logs.info(TAG+"_Scenesjson", scene_id+"-----"+jsonarr);
            JSONArray jsonArray = new JSONArray(jsonarr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("scene_id").equals(scene_id)) {
                    JSONArray roomJsonArray=jsonObject.getJSONArray("room_type");
                    for(int j=0;j<roomJsonArray.length();j++) {
                        arr_scene_wise_room.add(roomJsonArray.getString(j));
                    }
                }
            }
        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_ExperienceAllRoom",""+ex.getMessage());
        }
        JSONArray roomJsonArray=new JSONArray();
        try
        {
            JSONArray jsonArray=App.getRoomDeviceData().getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(arr_scene_wise_room.contains(jsonObject.getString("CML_ROOM_TYPE")))
                {
                    roomJsonArray.put(jsonObject.getString("CML_ID"));
                }
            }
        }
        catch(Exception e)
        {
             Logs.error(TAG+"_ExperienceAllRoom",""+e.getMessage());
        }
        return roomJsonArray;
    }
}
