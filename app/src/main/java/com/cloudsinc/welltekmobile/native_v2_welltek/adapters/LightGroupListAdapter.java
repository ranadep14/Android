package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 * This class containing functionality related to displaying light group list and perform action on it.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class LightGroupListAdapter extends RecyclerView.Adapter<LightGroupListAdapter.ViewHolder> {
    private ArrayList<Light> arrayList;
    private Context mcontext;
    boolean scene_state=false;
    boolean cir_flag[],relax_flag[],eng_flag[];
    private boolean favflag[];
    boolean dawn_state = false;
    String room_type;
    FragmentManager mfm;
    String TAG=LightGroupListAdapter.this.getClass().getName();
    String call_from;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
         TextView txt_type;
         TextView txt_id ;
         TextView txt_title ;
         TextView txt_room_title ;
         SeekBar seek_light_brightness ;
         SeekBar seek_group_brightness ;
         Switch group_on_off ;
         Switch group_on_off_dummy ;
         Switch light_on_off ;
         ImageView img_indiactor ;
         ImageView img_option ;
         RelativeLayout rel_visiblity ;
         LinearLayout lin_light ;
         ImageView img_circadian ;
          ImageView  img_eng ;
         ImageView  img_relax ;
        ImageView  img_add_fav;
        public ViewHolder(View v) {
            super(v);
              txt_type = v.findViewById(R.id.txt_type); // title
            txt_room_title = v.findViewById(R.id.txt_room_title); // title
              txt_id = v.findViewById(R.id.txt_id); // title
              txt_title = v.findViewById(R.id.txt_title); // artist name
              seek_light_brightness = v.findViewById(R.id.seek_light_brightness); // artist name
              seek_group_brightness = v.findViewById(R.id.seek_group_brightness);
              group_on_off = v.findViewById(R.id.group_on_off); // artist name
              group_on_off_dummy = v.findViewById(R.id.group_on_off_dummy); // artist name
              light_on_off = v.findViewById(R.id.light_on_off); // artist name
              img_indiactor = v.findViewById(R.id.img_indiactor);
              img_option = v.findViewById(R.id.img_option);
            img_add_fav = v.findViewById(R.id.img_add_fav);
            rel_visiblity = v.findViewById(R.id.rel_visiblity);
            lin_light = v.findViewById(R.id.lin_light);
              img_circadian = v.findViewById(R.id.img_circadian);
                img_eng = v.findViewById(R.id.img_eng);
               img_relax = v.findViewById(R.id.img_relax);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public LightGroupListAdapter(Context context, ArrayList<Light> myDataset, FragmentManager fm, String mroom_type,String call_from) {
        arrayList = myDataset;
        mcontext=context;
        room_type=mroom_type;
        this.call_from=call_from;
        favflag =new boolean[arrayList.size()];
        for (int i=0;i<arrayList.size();i++)
        {
            favflag[i]=false;
        }
        mfm=fm;
         Logs.info(TAG+"_BlindGroupSize",""+arrayList.size());
    }
    public void reload(ArrayList<Light> myDataset)
    {
        this.arrayList=myDataset;
        favflag =new boolean[arrayList.size()];
        for (int i=0;i<arrayList.size();i++)
        {
            favflag[i]=false;
        }
    }
    // Create new views (invoked by the layout manager)
    @Override
    public LightGroupListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_light_group_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Light light = arrayList.get(position);
        cir_flag=new boolean[arrayList.size()];
        relax_flag=new boolean[arrayList.size()];
        eng_flag=new boolean[arrayList.size()];
        for (int i=0;i<arrayList.size();i++)
        {
            cir_flag[i]=false;
            relax_flag[i]=false;
            eng_flag[i]=false;
        }
        boolean check_light_state_flag=false,check_light_status_flag=false;
         Logs.info(TAG+"_Light", "" + light.getId());
         Logs.info(TAG+"_GroupLightTitleeeeee", "" + light.getTitle()+"state"+light.isLigh_state()+"---"+"----------------"+light.getScene_id()+"-----------"+light.getType()+"---");
        holder.txt_title.setText(light.getTitle());
        holder.txt_id.setText(light.getId());
        holder.txt_type.setText(light.getType());
        holder.img_circadian.setTag(position);
        holder.img_eng.setTag(position);
        holder.img_relax.setTag(position);
        holder.group_on_off_dummy.setOnCheckedChangeListener(null);
        holder.group_on_off.setVisibility(View.VISIBLE);
        holder.light_on_off.setVisibility(View.GONE);
        holder.seek_light_brightness.setVisibility(View.GONE);
        holder.seek_group_brightness.setVisibility(View.VISIBLE);
        holder.seek_group_brightness.setTag(position);
        holder.img_add_fav.setTag(position);
        holder.txt_room_title.setText(light.getRoom_title());
        if(call_from.equals("regular"))holder.txt_room_title.setVisibility(View.GONE);
        else holder.txt_room_title.setVisibility(View.VISIBLE);
         Logs.info(TAG+"_GroupSwtState", "" + holder.group_on_off.isChecked());
        if (holder.group_on_off.isChecked() != light.isLigh_state()) {
            holder.group_on_off.setChecked(light.isLigh_state());
            holder.group_on_off_dummy.setChecked(light.isLigh_state());
        }
        SwitchTrackChange.changeTrackColor(mcontext,holder.group_on_off);
        if(!light.isLigh_state()) {
            check_light_state_flag=false;
            holder.img_option.setColorFilter(mcontext.getResources().getColor(R.color.fragment_blue_light), PorterDuff.Mode.SRC_IN);
            holder.img_option.setEnabled(false);
        }
        else {
            check_light_state_flag=true;
            holder.img_option.setColorFilter(mcontext.getResources().getColor(R.color.familyname_select_color), PorterDuff.Mode.SRC_IN);
        }
        if(arrayList.get(position).getFav_id().equals(""))
        {
            favflag[position]=false;
            holder.img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
        }
        else
        {
            favflag[position]=true;
            holder.img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
        }
        if (!light.isLigh_state()) {
            holder.seek_group_brightness.setProgress(0);
        } else {
            holder.seek_group_brightness.setProgress(light.getBrightness());
        }
        holder.img_indiactor.setVisibility(View.GONE);
        if(arrayList.get(position).isStatus())
        {
            check_light_status_flag=true;
            holder.rel_visiblity.setVisibility(View.GONE);
            disableAllViews(holder.lin_light,true);
        }
        else
        {
            check_light_status_flag=false;
            holder.rel_visiblity.setVisibility(View.VISIBLE);
            disableAllViews(holder.lin_light,false);
        }
        if(check_light_status_flag)
        {
            holder.img_option.setEnabled(true);
        }
        else
        {
            holder.img_option.setEnabled(false);
        }
        holder.seek_group_brightness.setTag(position);
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
                int pos =(int)seekBar.getTag();
                if(!checkDawnSleep(getRoomId(arrayList.get(pos).getId()),mcontext)) {
                    setGroupBrightness(holder.txt_id.getText().toString(), seekBar.getProgress());
                }
            }
        });
        holder.img_option.setTag(position);
        holder.img_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                if(!checkDawnSleep(getRoomId(arrayList.get(pos).getId()),mcontext)) {
                    SceneDialog dFragment = new SceneDialog();
                    Logs.info(TAG + "_Scene_Id", arrayList.get(pos).getScene_id());
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "light");
                    App.setCallfrom("room_devices");
                    bundle.putString("scene_id", arrayList.get(pos).getScene_id());
                    bundle.putString("device_id", arrayList.get(pos).getId());
                    bundle.putString("room_type", room_type);
                    dFragment.setArguments(bundle);
                    dFragment.show(mfm, "individual");
                }
            }
        });
        holder.group_on_off_dummy.setTag(position);
        holder.group_on_off_dummy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos =(int)buttonView.getTag();
                if(!checkDawnSleep(getRoomId(arrayList.get(pos).getId()),mcontext)) {
                    holder.group_on_off_dummy.setChecked(!isChecked);
                    //   if(displayAlertForDawn()) {
                    Logs.info(TAG + "_WhereIm", "InGroupONOff");
                    // group_on_off.setChecked(!isChecked);
                    String id = holder.txt_id.getText().toString();
                    Logs.info(TAG + "_group_id", "" + id);
                    setGroupState(id, isChecked);
                }
                buttonView.setChecked(!isChecked);
                SwitchTrackChange.changeTrackColor(mcontext, holder.group_on_off_dummy);
            }
        });
        holder.img_circadian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(displayAlertForDawn()) {
                 Logs.info(TAG+"_WhereIm","InCircadianClick");
                int pos = (Integer) v.getTag();
                setDefaultScene(holder.img_circadian, holder.img_relax, holder.img_eng);
                if (cir_flag[pos] == false) {
                    if (displayDialog(arrayList.get(pos).isLigh_state(), holder.txt_id.getText().toString(), 1)) {
                        holder.img_circadian.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_circadian_red));
                        cir_flag[pos] = true;
                    }
                } else {
                    haltScence(holder.txt_id.getText().toString(), "1");
                    holder.img_circadian.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_circadian_white));
                }
                //}
            }
        });
        holder.img_eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  if (displayAlertForDawn()) {
                 Logs.info(TAG+"_WhereIm","InEnerClick");
                int pos = (Integer) v.getTag();
                setDefaultScene(holder.img_circadian, holder.img_relax, holder.img_eng);
                if (eng_flag[pos] == false) {
                    if (displayDialog(arrayList.get(pos).isLigh_state(), holder.txt_id.getText().toString(), 3)) {
                        holder.img_eng.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_energize_red));
                        eng_flag[pos] = true;
                    }
                } else {
                    haltScence(holder.txt_id.getText().toString(), "3");
                    holder.img_eng.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_energize_white));
                }
                // }
            }
        });
        holder.img_relax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  if(displayAlertForDawn()) {
                 Logs.info(TAG+"_WhereIm","InRelaxClick");
                int pos = (Integer) v.getTag();
                setDefaultScene(holder.img_circadian, holder.img_relax, holder.img_eng);
                if (relax_flag[pos] == false) {
                    if (displayDialog(arrayList.get(pos).isLigh_state(), holder.txt_id.getText().toString(), 2)) {
                        holder.img_relax.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_relax_red));
                        relax_flag[pos] = true;
                    }
                } else {
                    haltScence(holder.txt_id.getText().toString(), "2");
                    holder.img_relax.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_relax));
                }
                // }
            }
        });
        holder.img_add_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                if(favflag[pos])
                {
                    FavoritesOperations.deleteFav(arrayList.get(pos).getFav_id());
                    holder.img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
                }
                else
                {
                    FavoritesOperations.addFav(arrayList.get(pos).getId());
                    favflag[pos]=true;
                    holder.img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
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
     * apply scene on given device
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param id string device id
     * @param scene_id string scene id
     */
    private void triggerScence(String id,String scene_id)
    {
        try {
            JSONObject dataJson=new JSONObject();
            dataJson.put("deviceId",id);
            dataJson.put("scene",scene_id);
            JSONObject obj = new JSONObject();
            obj.put("data",dataJson);
            obj.put("type", "trigger_light");
             Logs.info(TAG+"_scene_light",""+obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * halt scene on given device
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param id int device id.
     * @param scene_id scene_id
     */
    private void haltScence(String id,String scene_id)
    {
        try {
            JSONObject dataJson=new JSONObject();
            dataJson.put("deviceId",id);
            dataJson.put("scene",scene_id);
            JSONObject obj = new JSONObject();
            obj.put("data",dataJson);
            obj.put("type", "halt_light");
             Logs.info(TAG+"_scene_light",""+obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * set brightness of light group
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param id string group_id
     * @param bright int brightness
     */
    private void setGroupBrightness(String id,int bright)
    {
        try {
            JSONObject dataJson=new JSONObject();
            dataJson.put("brightness",bright);
            dataJson.put("group",id);
            JSONObject obj = new JSONObject();
            obj.put("data",dataJson);
            obj.put("type", "group_brightness");
             Logs.info(TAG+"_group_brighness_request",""+obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * set group state ON/OFF
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param state boolean state true/false
     * @param id string group_id
     */
    private void setGroupState(String id,boolean state)
    {
        try {
            JSONObject dataJson=new JSONObject();
            dataJson.put("state",state);
            //  dataJson.put("group",id);
            dataJson.put("group",getGroupJson(id));
            JSONObject obj = new JSONObject();
            obj.put("data", dataJson);
            obj.put("type", "group_power");
             Logs.info(TAG+"_group_state_request",""+obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * get group json by id
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param id int image id
     * @return  JSONObject
     */
    private JSONObject getGroupJson(String id)
    {
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
             Logs.error(TAG+"_actualLighExcception",""+ ex.getMessage());
        }
        return grjsonObject;
    }
    /**
     * set image to imagview
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param img_circadian imageview
     * @param img_relax Imageview
     * @param img_eng Imageview
     */
    private void setDefaultScene(ImageView img_circadian,ImageView img_relax,ImageView img_eng){
        //  cir_flag=false;relax_flag=false;eng_flag=false;
        img_circadian.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_circadian_white));
        img_relax.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_relax));
        img_eng.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_energize_white));
    }
    /**
     * dislay scene dialog
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param light_state boolean light state
     * @param id string group_id
     * @param scene_id int scene_id
     */
    private boolean displayDialog(final boolean light_state,final String id,final int scene_id)
    {
        if(light_state)
        {
            triggerScence(id,""+scene_id);
            scene_state = true;
        }
        else {
            final AlertDialog dialog = new AlertDialog.Builder(mcontext)
                    .setView(R.layout.custom_dialog)
                    .create();
            dialog.setCanceledOnTouchOutside(false);
            View v = dialog.getWindow().getDecorView();
            v.setBackgroundResource(android.R.color.transparent);
            dialog.show();
            TextView msgt = dialog.findViewById(R.id.title);
            msgt.setVisibility(View.GONE);
            TextView msg = dialog.findViewById(R.id.msg);
            assert msg != null;
            msg.setText("Triggering scene will turn ON the lights, Do you want to continue? ");
            Button diagButtonOK = dialog.findViewById(R.id.customDialogOk);
            diagButtonOK.setText("Yes");
            diagButtonOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    triggerScence(id,""+scene_id);
                    scene_state = true;
                    dialog.dismiss();
                }
            });
            Button customDialogCancel = dialog.findViewById(R.id.customDialogCancel);
            customDialogCancel.setText("No");
            customDialogCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    scene_state = false;
                    dialog.dismiss();
                }
            });
        }
        return scene_state;
    }
    /**
     * enable and disable given view
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param v View view for disable
     * @param f boolean enable/disable
     */
    private void disableAllViews(View v,boolean f){
        v.setEnabled(f);
        if(v instanceof ViewGroup){
            for (int i = 0; i < ((ViewGroup)v).getChildCount();  i++) {
                View view = ((ViewGroup)v).getChildAt(i);
                disableAllViews(view,f);
            }
        }
    }
}
