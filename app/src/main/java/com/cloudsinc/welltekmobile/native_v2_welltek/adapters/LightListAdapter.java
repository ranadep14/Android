package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.SceneDialog;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Light;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FavoritesOperations;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.SwitchTrackChange;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.types.DevicesByType.checkDawnSleep;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.FavoritesOperations.getRoomId;
/**
 * This class containing functionality related to displaying light devices list
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class LightListAdapter extends RecyclerView.Adapter<LightListAdapter.ViewHolder> {
    private ArrayList<Light> arrayList;
    Context mcontext;
    FragmentManager mfm;
    String TAG = LightListAdapter.this.getClass().getSimpleName();
    String room_type, call_from;
    Dialog dawn_sleep_dialog;
    private static Typeface typeface;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    private boolean favflag[];
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView txt_type;
        TextView txt_id;
        TextView txt_title;
        TextView txt_room_title;
        SeekBar seek_light_brightness;
        SeekBar seek_group_brightness;
        RelativeLayout rel_visiblity;
        LinearLayout lin_light;
        Switch light_on_off;
        Switch light_on_off_dummy;
        Switch group_on_off;
        ImageView img_indiactor;
        ImageView img_option;
        RelativeLayout lay_group;
        ImageView img_circadian;
        ImageView img_eng;
        ImageView img_relax;
        ImageView img_add_fav;
        String call_from;
        public ViewHolder(View v) {
            super(v);
            txt_type = v.findViewById(R.id.txt_type); // title
            img_option = v.findViewById(R.id.img_option); // title
            txt_id = v.findViewById(R.id.txt_id); // title
            txt_title = v.findViewById(R.id.txt_title); // artist name
            txt_room_title = v.findViewById(R.id.txt_room_title); // artist name
            seek_light_brightness = v.findViewById(R.id.seek_light_brightness); // artist name
            seek_group_brightness = v.findViewById(R.id.seek_group_brightness);
            light_on_off = v.findViewById(R.id.light_on_off); // artist name
            light_on_off_dummy = v.findViewById(R.id.light_on_off_dummy); // artist name
            group_on_off = v.findViewById(R.id.group_on_off); // artist name
            img_indiactor = v.findViewById(R.id.img_indiactor);
            img_add_fav = v.findViewById(R.id.img_add_fav);
            lay_group = v.findViewById(R.id.lay_group);
            rel_visiblity = v.findViewById(R.id.rel_visiblity);
            lin_light = v.findViewById(R.id.lin_light);
            img_circadian = v.findViewById(R.id.img_circadian);
            img_eng = v.findViewById(R.id.img_eng);
            img_relax = v.findViewById(R.id.img_relax);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public LightListAdapter(Context context, ArrayList<Light> myDataset, FragmentManager fm, String mroom_type, String call_from) {
        mcontext = context;
        mfm = fm;
        arrayList = myDataset;
        room_type = mroom_type;
        this.call_from = call_from;
        favflag = new boolean[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            favflag[i] = false;
        }
        Logs.info(TAG + "_LightistSIze", "" + arrayList.size());
    }
    public void reload(ArrayList<Light> myDataset) {
        this.arrayList = myDataset;
        favflag = new boolean[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            favflag[i] = false;
        }
    }
    // Create new views (invoked by the layout manager)
    @Override
    public LightListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(mcontext)
                .inflate(R.layout.custom_light_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Light light = arrayList.get(position);
        Logs.info(TAG + "_Light", "" + light.getId());
        Logs.info(TAG + "_LightTitleeeeeeeeeee", "" + light.isStatus() + "" + light.getTitle() + "state" + light.isLigh_state() + "---" + light.getType() + "---");
        holder.txt_title.setText(light.getTitle());
        holder.txt_id.setText(light.getId());
        holder.txt_type.setText(light.getType());
        holder.txt_room_title.setText(light.getRoom_title());
        if (call_from.equals("regular")) holder.txt_room_title.setVisibility(View.GONE);
        else holder.txt_room_title.setVisibility(View.VISIBLE);
        holder.light_on_off_dummy.setOnCheckedChangeListener(null);
        holder.group_on_off.setOnCheckedChangeListener(null);
        holder.txt_title.setText(arrayList.get(position).getTitle());
        holder.img_add_fav.setTag(position);
        boolean check_light_state_flag = false, check_light_status_flag = false;
        if (arrayList.get(position).getFav_id().equals("")) {
            favflag[position] = false;
            holder.img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
        } else {
            favflag[position] = true;
            holder.img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
        }
        if (light.getType().equals("group")) {
            holder.group_on_off.setVisibility(View.VISIBLE);
            holder.seek_light_brightness.setVisibility(View.GONE);
            holder.seek_group_brightness.setVisibility(View.VISIBLE);
            holder.seek_group_brightness.setTag(position);
            Logs.info(TAG + "_GroupSwtState", "" + holder.group_on_off.isChecked());
            holder.group_on_off.setChecked(light.isLigh_state());
            // setDraggable(seek_group_brightness, !light.isLigh_state());
            if (!light.isLigh_state()) {
                setDefaultScene(holder.img_circadian, holder.img_relax, holder.img_eng);
                holder.seek_group_brightness.setProgress(0);
            } else {
                holder.seek_group_brightness.setProgress(light.getBrightness());
            }
            setScence(light.getScene_id(), holder.img_circadian, holder.img_relax, holder.img_eng);
            holder.img_indiactor.setVisibility(View.GONE);
            holder.lay_group.setVisibility(View.GONE);/////////////////group visible
        }
        if (light.getType().equals("ind_linght")) {
            holder.seek_light_brightness.setTag(position);
            setScence(light.getScene_id(), holder.img_circadian, holder.img_relax, holder.img_eng);
            holder.seek_light_brightness.setVisibility(View.VISIBLE);
            holder.seek_group_brightness.setVisibility(View.GONE);
            holder.light_on_off.setVisibility(View.VISIBLE);
            holder.group_on_off.setVisibility(View.GONE);
            // setDraggable(seek_light_brightness, !light.isLigh_state());
            Logs.info(TAG + "_LightSwtState", "" + holder.light_on_off.isChecked());
            if (holder.light_on_off.isChecked() != light.isLigh_state()) {
                holder.light_on_off.setChecked(light.isLigh_state());
                holder.light_on_off_dummy.setChecked(light.isLigh_state());
            }
            SwitchTrackChange.changeTrackColor(mcontext, holder.light_on_off);
            SwitchTrackChange.changeTrackColor(mcontext, holder.group_on_off);
            if (!light.isLigh_state()) {
                holder.img_option.setColorFilter(mcontext.getResources().getColor(R.color.fragment_blue_light), PorterDuff.Mode.SRC_IN);
                check_light_state_flag = false;
            } else {
                holder.img_option.setColorFilter(mcontext.getResources().getColor(R.color.familyname_select_color), PorterDuff.Mode.SRC_IN);
                check_light_state_flag = true;
            }
            if (!light.isLigh_state()) {
                setDefaultScene(holder.img_circadian, holder.img_relax, holder.img_eng);
                holder.seek_light_brightness.setProgress(0);
            } else {
                holder.seek_light_brightness.setProgress(light.getBrightness());
            }
            holder.img_indiactor.setVisibility(View.GONE);
            holder.lay_group.setVisibility(View.GONE);
        }
        if (arrayList.get(position).isStatus()) {
            holder.rel_visiblity.setVisibility(View.GONE);
            disableAllViews(holder.lin_light, true);
            check_light_status_flag = true;
        } else {
            holder.rel_visiblity.setVisibility(View.VISIBLE);
            disableAllViews(holder.lin_light, false);
            check_light_status_flag = false;
        }
        if (check_light_status_flag) {
            holder.img_option.setEnabled(true);
        } else {
            holder.img_option.setEnabled(false);
        }
        holder.img_option.setTag(position);
        holder.img_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                if(!checkDawnSleep(getRoomId(arrayList.get(pos).getId()),mcontext)) {
                    SceneDialog dFragment = new SceneDialog();
                    Logs.info(TAG + "_Scene_Id", arrayList.get(pos).getScene_id());
                    App.setCallfrom("room_devices");
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "light");
                    bundle.putString("scene_id", arrayList.get(pos).getScene_id());
                    bundle.putString("device_id", arrayList.get(pos).getId());
                    bundle.putString("room_type", room_type);
                    bundle.putString("call_from", call_from);
                    dFragment.setArguments(bundle);
                    dFragment.show(mfm, "individual");
                }
            }
        });
        holder.seek_group_brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // seekBar.setThumb(getThumb(seekBar.getProgress(),thumbView));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // if(displayAlertForDawn()) {
                setGroupBrightness(holder.txt_id.getText().toString(), seekBar.getProgress());
                /*}
                else
                {
                    int pos=(int)seek_group_brightness.getTag();
                    seek_group_brightness.setProgress(lightArrayList.get(pos).getBrightness());
                }*/
            }
        });
        holder.img_add_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                if (favflag[pos]) {
                    FavoritesOperations.deleteFav(arrayList.get(pos).getFav_id());
                    holder.img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
                } else {
                    FavoritesOperations.addFav(arrayList.get(pos).getId());
                    favflag[pos] = true;
                    holder.img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                }
            }
        });
        holder.seek_light_brightness.setTag(position);
        holder.seek_light_brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //  seekBar.setThumb(getThumb(seekBar.getProgress(),thumbView));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int pos =(int)seekBar.getTag();
                if(!checkDawnSleep(getRoomId(arrayList.get(pos).getId()),mcontext)) {
                    setLightBrightness(holder.txt_id.getText().toString(), seekBar.getProgress());
                }
            }
        });
        holder.light_on_off_dummy.setTag(position);
        holder.light_on_off_dummy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos =(int)buttonView.getTag();
                if(!checkDawnSleep(getRoomId(arrayList.get(pos).getId()),mcontext))
                {
                    Logs.info(TAG + "_WhereIm", "InLightONOff");
                    String id = holder.txt_id.getText().toString();
                    Logs.info(TAG + "_Light_id", "" + id);
                    setLightState(id, isChecked);
                }
                holder.light_on_off_dummy.setChecked(!isChecked);
                SwitchTrackChange.changeTrackColor(mcontext, holder.light_on_off_dummy);
            }
        });
        holder.group_on_off.setTag(position);
        holder.group_on_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos =(int)buttonView.getTag();
                if(!checkDawnSleep(getRoomId(arrayList.get(pos).getId()),mcontext)) {
                    holder.group_on_off.setChecked(!isChecked);
                    Logs.info(TAG + "_WhereIm", "InGroupONOff");
                    String id = holder.txt_id.getText().toString();
                    Logs.info(TAG + "_group_id", "" + id);
                    setGroupState(id, isChecked);
                }
            }
        });
      /*  expandTouchArea(holder.lin_light, holder.light_on_off, 100);
        expandTouchArea(holder.lin_light, holder.group_on_off, 100);*/
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    /**
    /**
     * set light group brightness
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param id string group id
     * @param bright int brighness
     */
    public void setGroupBrightness(String id, int bright) {
        try {
            JSONObject dataJson = new JSONObject();
            dataJson.put("brightness", bright);
            dataJson.put("group", getGroupJson(id));
            JSONObject obj = new JSONObject();
            obj.put("data", dataJson);
            obj.put("type", "group_brightness");
            Logs.info(TAG + "_group_brighness_request", "" + obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     *set light  brightness
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param id string light id
     * @param bright int
     */
    public void setLightBrightness(String id, int bright) {
        try {
            JSONObject dataJson = new JSONObject();
            dataJson.put("brightness", bright);
            dataJson.put("light", getLighJson(id));
            JSONObject obj = new JSONObject();
            obj.put("data", dataJson);
            obj.put("type", "light_brightness");
            Logs.info(TAG + "_light_brighness_request", "" + obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * change group of light state ON/OFF
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param id string group_id
     * @param state boolean state ON/OFF
     */
    public void setGroupState(String id, boolean state) {
        try {
            JSONObject dataJson = new JSONObject();
            dataJson.put("state", state);
            //  dataJson.put("group",id);
            dataJson.put("group", getGroupJson(id));
            JSONObject obj = new JSONObject();
            obj.put("data", dataJson);
            obj.put("type", "group_power");
            Logs.info(TAG + "_group_state_request", "" + obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     *change light state ON/OFF
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param id int light id
     * @param state boolean ON/OFF
     */
    public void setLightState(String id, boolean state) {
        try {
            Logs.info(TAG + "_LightPosition", "" + getLighJson(id) + "----" + id);
            JSONObject dataJson = new JSONObject();
            dataJson.put("state", state);
            dataJson.put("light", getLighJson(id));
            JSONObject obj = new JSONObject();
            obj.put("data", dataJson);
            obj.put("type", "light_power");
            Logs.info(TAG + "_light_state_request", "" + obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * get light json from light id
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param id string light id
     */
    public JSONObject getLighJson(String id) {
        JSONObject lightjsonObject = null;
        JSONArray jsonArray = null;
        try {
            if (call_from.equals("regular"))
                jsonArray = App.getDevicesByRoomData().getJSONArray("data");
            else jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("CML_ID").equals(id)) {
                    lightjsonObject = jsonObject;
                }
            }
        } catch (Exception ex) {
            Logs.error(TAG + "_actualLighExcception", "" + ex.getMessage());
        }
        return lightjsonObject;
    }
    /**
     * getgroup json from group id
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param id int group id
     */
    private JSONObject getGroupJson(String id) {
        JSONObject grjsonObject = null;
        try {
            JSONArray jsonArray = App.getGroupJson().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("CML_ID").equals(id)) {
                    grjsonObject = jsonObject;
                }
            }
        } catch (Exception ex) {
            Logs.error(TAG + "_actualLighExcception", "" + ex.getMessage());
        }
        return grjsonObject;
    }
    /**
     * set default scence/clear all scene
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param img_circadian Imageview
     * @param img_eng Imageview
     * @param img_relax Imageview
     */
    private void setDefaultScene(ImageView img_circadian, ImageView img_relax, ImageView img_eng) {
        //  cir_flag=false;relax_flag=false;eng_flag=false;
        img_circadian.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_circadian_white));
        img_relax.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_relax));
        img_eng.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_energize_white));
    }
    /**
     * set scene on given light
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param scene_id string scene id
     * @param img_circadian Imageview
     * @param img_relax Imageview
     * @param img_eng Imageview
     */
    private void setScence(String scene_id, ImageView img_circadian, ImageView img_relax, ImageView img_eng) {
        if (scene_id != null) {
            switch (scene_id) {
                case "1":
                    img_circadian.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_circadian_red));
                    //cir_flag[(int)img_circadian.getTag()]=true;
                    break;
                case "2":
                    img_relax.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_relax_red));
                    // relax_flag[(int)img_relax.getTag()]=true;
                    break;
                case "3":
                    // img_eng.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_energize_red));
                    // eng_flag[(int)img_eng.getTag()]=true;
                    break;
            }
        }
    }
    /**
     * disable and enable given view
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param v view for diasable/enable
     * @param f boolean for diasable/enable
     */
    public void disableAllViews(View v, boolean f) {
        v.setEnabled(f);
        if (v instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
                View view = ((ViewGroup) v).getChildAt(i);
                disableAllViews(view, f);
            }
        }
    }
}