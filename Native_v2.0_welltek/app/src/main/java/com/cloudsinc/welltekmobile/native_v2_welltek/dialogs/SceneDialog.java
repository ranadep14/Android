package com.cloudsinc.welltekmobile.native_v2_welltek.dialogs;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Light;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FavoritesOperations;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.SwitchTrackChange;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms.RoomDevicesMusicFragment.getRoomTitle;
/**
 * This class containing functionality related to displaying display dialog fragment for scene
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class SceneDialog extends DialogFragment implements CompoundButton.OnCheckedChangeListener {
    private Observable<String> mobservable;
    private Observer<String> myObserver;
     @Nullable @BindView(R.id.img_relax)ImageView img_relax;
    @Nullable @BindView(R.id.img_cook)ImageView img_cook;
    @Nullable @BindView(R.id.img_circadian)ImageView img_circadian;
    @Nullable @BindView(R.id.img_energize)ImageView img_energize;
    @Nullable @BindView(R.id.lin_cook)LinearLayout lin_cook;
    @Nullable @BindView(R.id.btn_apply)Button btn_apply;
    @Nullable @BindView(R.id.light_on_off)Switch light_on_off;
    @Nullable @BindView(R.id.seek_light_brightness)SeekBar seek_light_brightness;
    @Nullable @BindView(R.id.txt_room_name)TextView txt_room_name;
    @Nullable @BindView(R.id.txt_title)TextView txt_title;
    @Nullable @BindView(R.id.img_add_fav)ImageView img_add_fav;
    @Nullable @BindView(R.id.lin_scene_selection)LinearLayout lin_scene_selection;
    @Nullable @BindView(R.id.lin_main)LinearLayout lin_main;
    @BindView(R.id.txt_cooking)TextView txt_cooking;
    @BindView(R.id.txt_circadian)TextView txt_circadian;
    @BindView(R.id.txt_energize)TextView txt_energize;
    @BindView(R.id.txt_relax)TextView txt_relax;
    String str_type="";
    String scene_id="";
    String device_id="";
    String room_type="";
    String room_id="";
    Context mcontext;
    List<String> wholeRoomLightList;
    HashMap<String, List<Light>> lightList;
    boolean fav_flag=false;
    View rootView;
    String TAG=SceneDialog.this.getClass().getSimpleName();
    boolean selected_flag=false;
    String applied_scene_id="-1";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.custom_scenes_dialog, container,
                false);
        UserInteractionSleep.siboSleepChecking(rootView.getContext());
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ButterKnife.bind(this,rootView);
        lin_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                UserInteractionSleep.userInteract(mcontext);
                return false;
            }
        });
        try {
            str_type = getArguments().getString("type");
            device_id = getArguments().getString("device_id");
             Logs.info(TAG+"_Room_type", "" + getArguments().getString("room_type"));
            room_type=getArguments().getString("room_type");
             Logs.info(TAG+"_device_id", "" + device_id);
            mcontext = rootView.getContext();
            //setScene();
        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_SceneError",""+ex.getMessage());
        }
        seek_light_brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //  seekBar.setThumb(getThumb(seekBar.getProgress(),thumbView));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setLightBrightness(device_id,seekBar.getProgress());
            }
        });
        setDefaultValues();
        setSubcriber();
        return rootView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       if(!App.isOrientationFlag()) setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }
    @Override
    public void onStart() {
        super.onStart();
        // safety check
        if (getDialog() == null) {
            return;
        }
        if(!App.isOrientationFlag()) {
            Dialog dialog = getDialog();
            if (dialog != null)
            {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setLayout(width, height);
                dialog.getWindow().setBackgroundDrawable(null);
            }
        }
        else
        {
            Dialog dialog = getDialog();
            if (dialog != null)
            {
                int width = (Build.MODEL.equals("QUAD-CORE R40 sc3826r") && Build.MANUFACTURER.equals("Allwinner"))||(Build.MODEL.equals("Q8919") && Build.MANUFACTURER.equals("SIBO"))?700:1000;
                int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);
                dialog.getWindow().setBackgroundDrawable(null);
            }
        }
        // ... other stuff you want to do in your onStart() method
    }
    @Optional @OnClick(R.id.img_close)
    public void img_close()
    {
        App.setCurrentSubcriber(App.getCurrentSubcriber());
        this.dismiss();
    }
    @Optional @OnClick(R.id.btn_cancel)
    public void btn_cancel()
    {
        App.setCurrentSubcriber(App.getCurrentSubcriber());
        this.dismiss();
    }
    public void setLightBrightness(String id,int bright)
    {
        try {
            JSONObject dataJson=new JSONObject();
            dataJson.put("brightness",bright);
            if(id.contains("group")) dataJson.put("group", id);
            else dataJson.put("light", getLighJson(id));
            JSONObject obj = new JSONObject();
            obj.put("data",dataJson);
            if(id.contains("group")) obj.put("type", "group_brightness");
            else obj.put("type", "light_brightness");
             Logs.info(TAG+"_light_brighness_request",""+obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void triggerScence(String tri)
    {
//
            try {
                if(!applied_scene_id.equals(scene_id)) {
                    JSONObject dataJson = new JSONObject();
                    dataJson.put("deviceId", getArguments().getString("device_id"));
                    dataJson.put("scene", scene_id);
                    JSONObject obj = new JSONObject();
                    obj.put("data", dataJson);
                    obj.put("type", "trigger_light");
                    Logs.info(TAG + "_scene_light", "" + obj);
                    App.getSocket().emit("action", obj);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }
    public void changePower(String device_id,boolean state)
    {
        try {
             Logs.info(TAG+"_LightPosition",""+getLighJson(device_id)+"----"+device_id);
            JSONObject dataJson=new JSONObject();
            dataJson.put("state",state);
            if(device_id.contains("group")) dataJson.put("group", getLighJson(device_id));
            else dataJson.put("light", getLighJson(device_id));
            JSONObject obj = new JSONObject();
            obj.put("data", dataJson);
            if(device_id.contains("group")) obj.put("type", "group_power");
            else obj.put("type", "light_power");
             Logs.info(TAG+"_light_state_request",""+obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public JSONObject getLighJson(String id)
    {
        JSONObject lightjsonObject = null;
        JSONArray jsonArray=null;
        try {
           jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
            if(id.contains("group"))jsonArray = App.getGroupJson().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("CML_ID").equals(id)) {
                    lightjsonObject = jsonObject;
                }
            }
        } catch (Exception ex) {
             Logs.info(TAG+"_actualLighExcception",""+ ex.getMessage());
        }
        return lightjsonObject;
    }
    private void setSubcriber() {
        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext(""+ App.getRoomData());
                        sub.onCompleted();
                    }
                }
        );
        myObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
            }
            @Override
            public void onNext(String string) {
                 Logs.info(TAG+"_WhereIm","InTypeMoreDialog"+string);
               if(device_id.contains("device")) {
                   if ((string.equals("Lighting") || string.equals("rooms_by_device"))&&!selected_flag)
                       setDefaultValues();
               }
               if(device_id.contains("group"))
               {
                  if( string.equals("GroupLighting")&&!selected_flag) setDefaultValues();
               }
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setDialog_subcriber(s);
    }
    @Optional @OnClick(R.id.btn_apply)
    public void btn_apply()
    {
       triggerScence(scene_id);
        App.setCurrentSubcriber(App.getCurrentSubcriber());
        dismiss();
    }
    @OnClick(R.id.img_add_fav)
    public void img_add_fav()
    {
        if(!fav_flag)
        {
            FavoritesOperations.addFav(device_id);
        }
        else
        {
            FavoritesOperations.deleteFav(FavoritesOperations.getFavId(device_id));
            if(App.getCallfrom().equals("favorite")) dismiss();
        }
    }
    @Optional
   @OnClick({R.id.img_circadian, R.id.img_relax, R.id.img_energize,R.id.img_cook})
    public void onClick(View view) {
        selected_flag=true;
        setDefault();
        switch (view.getId())
        {
            case R.id.img_circadian:
                scene_id="1";
                setSelection(img_circadian,txt_circadian);
                break;
            case R.id.img_energize:
                scene_id="3";
                setSelection(img_energize,txt_energize);
                break;
            case R.id.img_relax:
                scene_id="2";
                setSelection(img_relax,txt_relax);
                break;
            case R.id.img_cook:
                scene_id="7";
                setSelection(img_cook,txt_cooking);
                break;
        }
    }
    public void setSceneSelection(String scene_id)
    {
        setDefault();
        switch (scene_id)
        {
            case "1":
                setSelection(img_circadian,txt_circadian);
                break;
            case "3":
                setSelection(img_energize,txt_energize);
                break;
            case "2":
                setSelection(img_relax,txt_relax);
                break;
            case "7":
                setSelection(img_cook,txt_cooking);
                break;
        }
    }
    public void setSelection(ImageView img_selection,TextView txt_bottom_text_color) {
        img_selection.setColorFilter(this.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        img_selection.setBackground(this.getResources().getDrawable(R.drawable.blue_circle));
        txt_bottom_text_color.setTextColor(mcontext.getResources().getColor(R.color.white));
    }
    public void setDefault()
    {
        img_circadian.setColorFilter(this.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        img_energize.setColorFilter(this.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        img_relax.setColorFilter(this.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        img_cook.setColorFilter(this.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        txt_cooking.setTextColor(mcontext.getResources().getColor(R.color.light_white));
        txt_relax.setTextColor(mcontext.getResources().getColor(R.color.light_white));
        txt_energize.setTextColor(mcontext.getResources().getColor(R.color.light_white));
        txt_circadian.setTextColor(mcontext.getResources().getColor(R.color.light_white));
        img_circadian.setBackground(mcontext.getResources().getDrawable(R.drawable.blue_border_circle));
        img_energize.setBackground(mcontext.getResources().getDrawable(R.drawable.blue_border_circle));
        img_relax.setBackground(mcontext.getResources().getDrawable(R.drawable.blue_border_circle));
        img_cook.setBackground(mcontext.getResources().getDrawable(R.drawable.blue_border_circle));
    }
    public void setDefaultValues()
    {
        boolean state=false;
        int brightness=0;
        JSONArray jsonArray=null;
        String device_title="";
        try {
            if(getFavState())
            {
                fav_flag=true;
                img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
            }
            else
            {
                fav_flag=false;
                img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
            }
            if(App.isOrientationFlag()) {
                if(device_id.contains("group"))jsonArray = App.getGroupJson().getJSONArray("data");
                else jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
            }
            else {
                if(device_id.contains("group"))jsonArray = App.getGroupJson().getJSONArray("data");
                else jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
            }
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_ID").equals(device_id))
                {
                    if(jsonObject.has("CML_POWER"))state=jsonObject.getBoolean("CML_POWER");
                    if(jsonObject.has("CML_BRIGHTNESS")) brightness=jsonObject.getInt("CML_BRIGHTNESS");
                    if(jsonObject.has("CML_TITLE")) device_title=jsonObject.getString("CML_TITLE");
                    room_id = jsonObject.getString("room");
                    if(jsonObject.has("CML_SCENE_ID")) {
                        applied_scene_id=jsonObject.getString("CML_SCENE_ID");
                        setSceneSelection(jsonObject.getString("CML_SCENE_ID"));
                    }
                }
            }
            seek_light_brightness.setProgress(brightness);
            light_on_off.setOnCheckedChangeListener(null);
            light_on_off.setChecked(state);
            light_on_off.setOnCheckedChangeListener(this);
            txt_title.setText(device_title);
            txt_room_name.setText(getRoomTitle(room_id));
            if(!state)
            {
                lin_scene_selection.setAlpha(0.5f);
                disableAllViews(lin_scene_selection,state);
            }
            else
            {
                lin_scene_selection.setAlpha(1f);
                disableAllViews(lin_scene_selection,state);
            }
            lin_scene_selection.setAlpha(1f);
            disableAllViews(lin_scene_selection,true);
            if (room_type.equals("Kitchen")) {
                lin_cook.setVisibility(View.VISIBLE);
            } else {
                lin_cook.setVisibility(View.GONE);
            }
            SwitchTrackChange.changeTrackColor(mcontext,light_on_off);
        }
        catch (Exception ex)
        {
             Logs.info(TAG+"_AllLightError",""+ex.getMessage());
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        changePower(device_id,isChecked);
    }
    public void disableAllViews(View v,boolean f){
        v.setEnabled(f);
        if(v instanceof ViewGroup){
            for (int i = 0; i < ((ViewGroup)v).getChildCount();  i++) {
                View view = ((ViewGroup)v).getChildAt(i);
                disableAllViews(view,f);
            }
        }
    }
    public boolean getFavState()
    {
        boolean fav_flag=false;
        try
        {
            JSONArray jsonArray = App.getFavData().getJSONArray("data");
             Logs.info(TAG+"_FavoriteData",device_id+"----"+App.getFavData());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.has("deviceId")) {
                    if (jsonObject.getString("deviceId").equals(device_id)) {
                        fav_flag = true;
                    }
                }
            }
        }
        catch (Exception ex)
        {
             Logs.info(TAG+"_Fav_All_Exception",""+ex.getMessage());
        }
        return fav_flag;
    }

    @Override
    public void onResume() {
        super.onResume();
        Logs.info(TAG, "------i am in onResume");
        UserInteractionSleep.siboSleepChecking(mcontext);
    }
    @Override
    public void onStop() {
        super.onStop();
        Logs.info(TAG, "------i am in onstop");
        UserInteractionSleep.stopHandler();
    }
}
