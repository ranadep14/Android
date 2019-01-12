package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.BlindsGroupListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.BlindsListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.HvacListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.LightGroupListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.LightListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.MusicListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.SceneDialog;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.WholeHomeSceneDialog;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.ElementsMainFragmentPortrait;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule.RemoveNotification;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Blind;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Device;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Hvac;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Light;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Sonos;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FavoritesOperations;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.SwitchTrackChange;
import com.wonderkiln.blurkit.BlurLayout;
import com.xw.repo.BubbleSeekBar;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import static android.view.View.VISIBLE;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.ElementsMainFragmentPortrait.elements_tabs;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms.RoomDevicesMusicFragment.getRoomTitle;
/**
 * This class containing functionality related to displaying devices under selected room on portrait screen
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class RoomDevicesFragmentPortrait extends Fragment implements CompoundButton.OnCheckedChangeListener {
    public static RoomDevicesFragmentPortrait newInstance() {
        return new RoomDevicesFragmentPortrait();
    }
    private View view;
    @BindView(R.id.lin_dawn_running)LinearLayout lin_dawn_running;
    @BindView(R.id.blurLayout)BlurLayout blurLayout;
    @BindView(R.id.lin_sleep_running)LinearLayout lin_sleep_running;
    @Nullable @BindView(R.id.lin_light_list)LinearLayout lin_light_list;
    @Nullable @BindView(R.id.lin_light_group_list)LinearLayout lin_light_group_list;
    @Nullable @BindView(R.id.lin_hvac_list)LinearLayout lin_hvac_list;
    @Nullable @BindView(R.id.lin_music_list)LinearLayout lin_music_list;
    @Nullable @BindView(R.id.lin_blind_list)LinearLayout lin_blind_list;
    @Nullable @BindView(R.id.lin_blind_group_list)LinearLayout lin_blind_group_list;
    @Nullable @BindView(R.id.rec_lights)RecyclerView rec_lights;
    @Nullable @BindView(R.id.rec_blinds)RecyclerView rec_blinds;
    @Nullable @BindView(R.id.rec_audio)RecyclerView rec_music;
    @Nullable @BindView(R.id.rec_blinds_group)RecyclerView rec_blinds_group;
    @Nullable @BindView(R.id.rec_lights_group)RecyclerView rec_lights_group;
    @Nullable @BindView(R.id.rec_hvac)RecyclerView rec_hvac;
    @Nullable @BindView(R.id.txt_room_title)TextView txt_room_title;
    @Nullable @BindView(R.id.img_room_type)ImageView img_room_type;
    @Nullable @BindView(R.id.rel_whole_home)RelativeLayout rel_whole_home;
    @Nullable @BindView(R.id.txt_room_id)TextView txt_room_id;
    @Nullable @BindView(R.id.txt_room_type)TextView txt_room_type;
    @Nullable @BindView(R.id.lin_device_container)LinearLayout lin_device_container;
    @Nullable @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @Nullable @BindView(R.id.img_back)ImageView img_back;
    @BindView(R.id.lin_whole_home_blind)LinearLayout lin_whole_home_blind;
    @BindView(R.id.lin_whole_home_music)LinearLayout lin_whole_home_music;
    @BindView(R.id.lin_whole_home_light)LinearLayout lin_whole_home_light;
    @BindView(R.id.lin_whole_home_light_offline)LinearLayout lin_whole_home_light_offline;
    String TAG=RoomDevicesFragmentPortrait.this.getClass().getSimpleName();
    LightListAdapter lightListAdapter;
    LightGroupListAdapter lightGroupListAdapter;
    MusicListAdapter musicListAdapter;
    HvacListAdapter hvacListAdapter;
    BlindsListAdapter blindsListAdapter ;
    BlindsGroupListAdapter blindsGroupListAdapter;
    LinearLayoutManager mLayoutManager;
    @Nullable @BindView(R.id.circular_whole_home_on_off)ProgressBar circular_whole_home_on_off;
    @Nullable @BindView(R.id.txt_no_devices)TextView txt_no_devices;
    @Nullable @BindView(R.id.scroll_container)NestedScrollView scroll_container;
    @BindView(R.id.rel_visiblity)RelativeLayout rel_visiblity;
    @BindView(R.id.btn_whole_close)Button btn_whole_close;
    @BindView(R.id.btn_whole_open)Button btn_whole_open;
    @BindView(R.id.btn_whole_stop)Button btn_whole_stop;
    @BindView(R.id.img_whole_light_add_fav)ImageView img_whole_light_add_fav;
    @BindView(R.id.img_whole_blind_add_fav)ImageView img_whole_blind_add_fav;
    @BindView(R.id.img_whole_music_add_fav)ImageView img_whole_music_add_fav;
    @BindView(R.id.img_whole_playlist)ImageView img_whole_playlist;
    @BindView(R.id.txt_whole_music_title)TextView txt_whole_music_title;
    @BindView(R.id.txt_whole_muisc_artist_name)TextView txt_whole_muisc_artist_name;
    @BindView(R.id.img_whole_album_image)ImageView img_whole_album_image;
    @BindView(R.id.img_whole_music_play_pause)ImageView img_whole_music_play_pause;
    @BindView(R.id.img_whole_music_prev)ImageView img_whole_music_prev;
    @BindView(R.id.img_whole_music_next)ImageView img_whole_music_next;
    @BindView(R.id.img_option)ImageView img_option;
    @BindView(R.id.whole_img_option)ImageView whole_img_option;
    @BindView(R.id.txt_sleep_desc)TextView txt_sleep_desc;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    private boolean roomLight=false,roomBlind=false,roomMusic=false,groupLight=false,groupBlind=false,sceneFlag=false,whole_play_pause_flag=false;
    boolean add_fav_whole_light=false,add_fav_whole_blind=false,add_fav_whole_music=false;
    boolean whole_home_flag=false;
    ArrayList<Sonos> musicArrlist =new ArrayList<>();
    ArrayList<Light> lightArrlist =new ArrayList<>();
    ArrayList<Light> grplightArrlist =new ArrayList<>();
    ArrayList<Hvac> hvacArrlist =new ArrayList<>();
    ArrayList<Blind> blindsArrlist =new ArrayList<>();
    ArrayList<Blind> grpblindsArrlist =new ArrayList<>();
    Context mcontext;
    String room_id="";
    boolean all_light_fav=false;
    boolean flag_disable_all_views_for_dawn =false;
    boolean flag_disable_all_views_for_sleep =false,check_whole_light_state_flag=false,check_whole_light_status_flag=false;
    @BindView(R.id.whole_on_off)Switch whole_on_off;
    @BindView(R.id.rel_loading) RelativeLayout rel_loading;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
        view =inflater.inflate(R.layout.activity_rooms_one, container, false);
        mcontext= view.getContext();
        ButterKnife.bind(this, view);
        App.setDevicesByRoomData(null);
        App.setGroupByRoomJsonObject(null);
        App.setMusic_playlist_subcriber(null);
        img_back.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_left_arrow));
        txt_room_title.setText(App.getRoom().getRoom_title());
         Logs.info(TAG+"_WhereIM",""+TAG+""+App.getRoom().getRoom_title()+"------"+App.getRoom().getCnt_audios()+"----------"+App.getRoom().getCnt_lights()+"-------"+App.getRoom().getCnt_blinds());
        // setAdapeter();
        txt_room_id.setText(App.getRoom().getRoom_id());
        txt_room_type.setText(App.getRoom().getRoom_type());
        room_id=txt_room_id.getText().toString();
        SwitchTrackChange.changeTrackColor(mcontext,whole_on_off);
        if(App.getRoom().getRoom_title().equals("Whole Home"))
        {
            img_room_type.setVisibility(View.GONE);
            rel_whole_home.setVisibility(View.VISIBLE);
            txt_fragment_title.setText("Whole Home");
            rel_loading.setVisibility(View.GONE);
            scroll_container.setVisibility(View.GONE);
            lin_whole_home_blind.setVisibility(App.getRoom().getCnt_blinds()>0?View.VISIBLE:View.GONE);
            lin_whole_home_music.setVisibility(App.getRoom().getCnt_audios()>0?View.VISIBLE:View.GONE);
            lin_whole_home_light.setVisibility(App.getRoom().getCnt_lights()>0?View.VISIBLE:View.GONE);
        }
        else
        {
            checkDawnState();
            checkSleepState();
            disableAllViews();
            txt_fragment_title.setText("Rooms");
            scroll_container.setVisibility(View.VISIBLE);
            lin_whole_home_blind.setVisibility(View.GONE);
            lin_whole_home_music.setVisibility(View.GONE);
            lin_whole_home_light.setVisibility(View.GONE);
        }
        if(!App.getRoom().getRoom_title().equals("Whole Home")) {
        if( App.getSocket()!=null) {
            try {
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_default_dawn_sounds", new JSONObject()));
                if (!App.getRoom().getRoom_id().equals("whole_home")) {
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_groups_by_room", new JSONObject().put("Id", App.getRoom().getRoom_id())));
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_devices_by_room", getSelectedRoomJson()));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        }
        else
        {
            whole_home_flag=true;
            rel_loading.setVisibility(View.GONE);
            setDefaultWholeHomeValue();
            setDefaultWholeHomeFavoriteState();
            setWholeCurrentMusic();
        }
        setTypeImage(App.getRoom().getRoom_type());
        setSubcriber();
        return view;
    }
    public void setTypeImage(String type)
    {
        switch (type)
        {
            case "Bedroom":
                img_room_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_bedroom_type));
                break;
            case "Living Room":
                img_room_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_living_room_type));
                break;
            case "Bathroom":
                img_room_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_bedroom_type));
                break;
            case "Outdoor":
                img_room_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_bedroom_type));
                break;
            case "Kitchen":
                img_room_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_kitchen_type));
                break;
            case "whole_home":
                img_room_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.bg_whole_home));
                break;
            case "Family Room":
                img_room_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_bedroom_type));
                break;
            case "Office":
                img_room_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_bedroom_type));
                break;
        }
    }
    public void changePower(String room_id,boolean state)
    {
        try {
            JSONObject dataJson=new JSONObject();
            dataJson.put("state",state);
            dataJson.put("room",room_id);
            JSONObject obj = new JSONObject();
            obj.put("data", dataJson);
            obj.put("type", "room_power");
             Logs.info(TAG+"_light_state_request",""+obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void changeBrightness(int value,String room_id)
    {
        try {
            JSONObject dataJson=new JSONObject();
            dataJson.put("brightness",value);
            dataJson.put("room",room_id);
            JSONObject obj = new JSONObject();
            obj.put("data",dataJson);
            obj.put("type", "room_brightness");
             Logs.info(TAG+"_light_brighness_request",""+obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void setAllLightValues(String str_id)
    {
        int brightness=0;
        boolean state=false;
        try {
            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_ID").equals(str_id))
                {
                    if(jsonObject.has("CML_POWER"))state=jsonObject.getBoolean("CML_POWER");
                    if(jsonObject.has("CML_BRIGHTNESS")) brightness=jsonObject.getInt("CML_BRIGHTNESS");
                }
            }
        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_AllLightError",""+ex.getMessage());
        }
    }
    private void setSubcriber() {
        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("");
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
            }
            @SuppressLint("NewApi")
            @Override
            public void onNext(String string) {
                Logs.info(TAG + "_SubcriberResponse", "" + string + "");
                if(string.equals("default_dawn_sounds"))
                {
                    setDefaultSongHashMap();
                }
                if (!whole_home_flag) {
                    if(string.equals("groups_by_room"))
                    {
                        groupLight = false;
                        groupBlind = false;
                        if(App.getGroupByRoomJsonObject()!=null)
                        {
                            getLightGroupState();
                            getBlindGroupState();
                        }
                    }
                    if (string.equals("room_device")) {
                        roomLight = false;
                        roomMusic = false;
                        roomBlind = false;
                        if (App.getDevicesByRoomData() != null) {
                            getBlindState();
                            getLightState();
                            getMusicState();
                        }
                        getFavState();
                    }
                    checkDawnState();
                    checkSleepState();
                    if (string.equals("Lighting")) {
                        roomLight = false;
                        getLightState();
                    }
                    if (string.equals("Music")) {
                        roomLight = false;
                        getMusicState();
                    }
                    if (string.equals("Blinds")) {
                        roomBlind = false;
                        getBlindState();
                    }
                    if (string.equals("GroupLighting")) {
                        groupLight = false;
                        getLightGroupState();
                    }
                    if (string.equals("GroupBlinds")) {
                        groupBlind = false;
                        getBlindGroupState();
                    }
                    try {
                        Logs.info(TAG + "_DeviceData", "" + App.getDevicesByRoomData().getJSONArray("data"));
                        if (App.getDevicesByRoomData().getJSONArray("data").toString().equals("[]")) {
                            view.findViewById(R.id.rel_loading).setVisibility(View.GONE);
                            txt_no_devices.setVisibility(VISIBLE);
                            lin_device_container.setVisibility(View.GONE);
                        } else {
                            int cnt_sensor = 0;
                            JSONArray jsonArray = App.getDevicesByRoomData().getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if (jsonObject.getString("type").equals("Sensor")) {
                                    cnt_sensor++;
                                }
                                if (jsonObject.getString("type").equals("HVAC")) {
                                    cnt_sensor++;
                                }
                            }
                            if (cnt_sensor == jsonArray.length()) {
                                view.findViewById(R.id.rel_loading).setVisibility(View.GONE);
                                txt_no_devices.setVisibility(VISIBLE);
                            }
                        }
                    } catch (Exception e) {
                        Logs.error(TAG + "_Eeeeeeeeeeee", "" + e.getMessage());
                    }
                    System.out.println();
                    if (roomLight || groupLight || roomMusic || roomBlind || groupBlind) {
                        lin_device_container.setVisibility(VISIBLE);
                        rel_loading.setVisibility(View.GONE);
                        txt_no_devices.setVisibility(View.GONE);
                    }
                }
                else
                {
                    setDefaultWholeHomeFavoriteState();
                    if(string.contains("Whole Home")) {
                      setDefaultWholeHomeValue();
                      setWholeCurrentMusic();
                }
             }
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);
    }
    public void getFavState()
    {
        try
        {
            JSONArray jsonArray = App.getFavData().getJSONArray("data");
             Logs.info(TAG+"_FavoriteData",txt_room_id.getText().toString()+"----"+App.getFavData());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.has("deviceId")) {
                    if (jsonObject.getString("deviceId").equals(txt_room_id.getText().toString())) {
                        all_light_fav = true;
                    }
                }
            }
        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_Fav_All_Exception",ex.getMessage());
        }
    }
    private void getLightState()
    {
        lightArrlist.clear();
        try {
             Logs.info(TAG+"_DeviceJsonnnnnnnnnnnnn",""+ App.getDevicesByRoomData().getJSONArray("data"));
            JSONArray jsonArray = App.getDevicesByRoomData().getJSONArray("data");
            ArrayList<String> arrayList=new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                 Logs.info(TAG+"_LightDevices",""+jsonObject);
                if (jsonObject.getString("type").equals("Lighting")) {
                    if (jsonObject.has("group")){
                        if (jsonObject.getString("group").equals("")) {
                             Logs.info(TAG+"_WhereIM","ImInEmptygroup");
                             Logs.info(TAG+"_Title",jsonObject.getString("CML_TITLE"));
                            roomLight = true;
                            Light l = new Light();
                            if(jsonObject.has("CML_BRIGHTNESS"))l.setBrightness(jsonObject.getInt("CML_BRIGHTNESS"));
                            l.setId(jsonObject.getString("CML_ID"));
                            l.setLigh_state(jsonObject.getBoolean("CML_POWER"));
                            l.setTitle(jsonObject.getString("CML_TITLE"));
                            l.setScene_id(jsonObject.getString("CML_SCENE_ID"));
                            if(jsonObject.has("CML_STATUS"))l.setStatus(jsonObject.getBoolean("CML_STATUS"));
                            l.setType("ind_linght");
                            l.setFav_id(FavoritesOperations.getFavId(jsonObject.getString("CML_ID")));
                            l.setRoom_title(getRoomTitle(jsonObject.getString("room")));
                            if(!arrayList.contains(""+jsonObject.getString("CML_ID"))) {
                                arrayList.add("" + jsonObject.getString("CML_ID"));
                                lightArrlist.add(l);
                            }
                        }
                    }
                    else {
                         Logs.info(TAG+"_WhereIM","LightRoom");
                        Light l = new Light();
                        if(jsonObject.has("CML_BRIGHTNESS"))l.setBrightness(jsonObject.getInt("CML_BRIGHTNESS"));
                        l.setId(jsonObject.getString("CML_ID"));
                        l.setLigh_state(jsonObject.getBoolean("CML_POWER"));
                        l.setTitle(jsonObject.getString("CML_TITLE"));
                        l.setScene_id(jsonObject.getString("CML_SCENE_ID"));
                        if(jsonObject.has("CML_STATUS"))l.setStatus(jsonObject.getBoolean("CML_STATUS"));
                        l.setFav_id(FavoritesOperations.getFavId(jsonObject.getString("CML_ID")));
                        l.setRoom_title(getRoomTitle(jsonObject.getString("room")));
                        l.setType("ind_linght");
                        if(!arrayList.contains(""+jsonObject.getString("CML_ID"))) {
                            arrayList.add("" + jsonObject.getString("CML_ID"));
                            lightArrlist.add(l);
                            roomLight = true;
                        }
                    }
                }
            }
            setAllLightValues(room_id);
        }
        catch (Exception ex){
             Logs.error(TAG+"_LightStateError",""+ex.getMessage());}
         Logs.info(TAG+"_ListSize", "" + lightArrlist.size());
        if (lightArrlist.size() > 0) {
            lin_light_list.setVisibility(View.VISIBLE);
            if(lightListAdapter==null)
             {
                 lightListAdapter = new LightListAdapter(mcontext,lightArrlist,getActivity().getSupportFragmentManager(),txt_room_type.getText().toString(),"regular");
                 rec_lights.setNestedScrollingEnabled(false);
                 rec_lights.invalidate();
                 rec_lights.setHasFixedSize(true);
                 final LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false);
                 rec_lights.setLayoutManager(layoutManager);
                 rec_lights.setAdapter(lightListAdapter);
             }
             else
             {
              lightListAdapter.reload(lightArrlist);
              lightListAdapter.notifyDataSetChanged();
             }
            setAllLightValues(room_id);
        } else {
            lin_light_list.setVisibility(View.GONE);
        }
    }
    private void getMusicState()
    {
        musicArrlist.clear();
        try {
             Logs.info(TAG+"_dddddddddddddddddddd",""+ App.getDevicesByRoomData());
            JSONArray jsonArray = App.getDevicesByRoomData().getJSONArray("data");
            ArrayList<String> arrayList=new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("type").equals("Music")) {
                     Logs.info(TAG+"_MusicJson",""+jsonObject);
                    Sonos sonos=new Sonos();
                    sonos.setSonos_id(jsonObject.getString("Id"));
                    sonos.setSonos_song_play_pause_state(jsonObject.getString("CML_STATE"));
                    sonos.setSonos_volume(jsonObject.getInt("CML_VOLUME"));
                    sonos.setDevice_id(jsonObject.getString("CML_ID"));
                    sonos.setDevice_title(jsonObject.getString("CML_TITLE"));
                    sonos.setRoom_title(getRoomTitle(jsonObject.getString("room")));
                    sonos.setFav_id(FavoritesOperations.getFavId(jsonObject.getString("CML_ID")));
                    if(jsonObject.has("mute_status")) sonos.setMute_flag(jsonObject.getBoolean("mute_status"));
                    try {
                        if (jsonObject.has("CURRENT_TRACK")) {
                            JSONObject current_track_json = jsonObject.getJSONObject("CURRENT_TRACK");
                            if (current_track_json.has("albumArtURL"))
                                sonos.setSonos_album_art("" + current_track_json.getString("albumArtURL"));
                            if (current_track_json.has("title"))
                                sonos.setSonos_track_title("" + current_track_json.getString("title"));
                            else sonos.setSonos_track_title("Song title");
                            if (current_track_json.has("artist"))
                                sonos.setSonos_artist_name("" + current_track_json.getString("artist"));
                            else sonos.setSonos_artist_name("Artist name");
                            if (jsonObject.getJSONObject("SONOS_PLAYLIST").has("queueLength") && current_track_json.has("queuePosition")) {
                                Logs.info(TAG, "*********************" + current_track_json.getInt("queuePosition") + "*******************" + jsonObject.getJSONObject("SONOS_PLAYLIST").getInt("queueLength"));
                                if (jsonObject.has("SONOS_PLAYLIST")) {
                                    if (current_track_json.getInt("queuePosition") == jsonObject.getJSONObject("SONOS_PLAYLIST").getInt("queueLength")) {
                                        sonos.setNext_flag(true);
                                    } else {
                                        sonos.setNext_flag(false);
                                    }
                                }
                                if (current_track_json.getInt("queuePosition") == 1) {
                                    sonos.setPrev_flag(true);
                                } else {
                                    sonos.setPrev_flag(false);
                                }
                            }
                        } else {
                            sonos.setSonos_track_title(mcontext.getResources().getString(R.string.song_title));
                            sonos.setSonos_artist_name(mcontext.getResources().getString(R.string.artist_name));
                        }
                    }
                     catch (Exception ex)
                    {
                        Logs.error(TAG,"musicseterror"+ex.getMessage());
                    }
                   if(jsonObject.has("CML_PLAYMODE")) {
                       if (jsonObject.getString("CML_PLAYMODE").equals("NORMAL")) {
                           sonos.setShuffle_flag(false);
                           sonos.setRepeat_flag(false);
                       } else if (jsonObject.getString("CML_PLAYMODE").equals("SHUFFLE")) {
                           sonos.setShuffle_flag(true);
                           sonos.setRepeat_flag(true);
                       } else if (jsonObject.getString("CML_PLAYMODE").equals("REPEAT_ALL")) {
                           sonos.setShuffle_flag(false);
                           sonos.setRepeat_flag(true);
                       } else if (jsonObject.getString("CML_PLAYMODE").equals("SHUFFLE_NOREPEAT")) {
                           sonos.setShuffle_flag(true);
                           sonos.setRepeat_flag(false);
                       }
                   }
                    if (!arrayList.contains("" + jsonObject.getString("CML_ID"))) {
                        arrayList.add("" + jsonObject.getString("CML_ID"));
                        musicArrlist.add(sonos);
                    }
                }
            }
            Log.e(TAG,""+musicArrlist.size());
            if(musicArrlist.size()>0)
            {
                roomMusic=true;
            }
        }
        catch (Exception ex){
            Logs.error(TAG,""+ex.getMessage());}
        Logs.info(TAG, "musiclistsize" + musicArrlist.size());
        if (musicArrlist.size() > 0) {
            lin_music_list.setVisibility(View.VISIBLE);
            musicListAdapter = new MusicListAdapter(mcontext,musicArrlist,getActivity().getSupportFragmentManager(),RoomDevicesFragmentPortrait.this,"room_devices");
            Log.e(TAG, "********musiclistsize" + musicArrlist.size());
            rec_music.setAdapter(musicListAdapter);
            rec_music.setNestedScrollingEnabled(false);
            rec_music.invalidate();
            rec_music.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(mcontext);
            rec_music.setLayoutManager(mLayoutManager);
        } else {
            lin_music_list.setVisibility(View.GONE);
        }
    }
    private void getHVACState()
    {
        hvacArrlist.clear();
        try {
            JSONArray jsonArray = App.getDevicesByRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("type").equals("HVAC")) {
                     Logs.info(TAG+"_HVacJson",""+jsonObject);
                    Hvac hvac=new Hvac();
                    hvac.setTitle(jsonObject.getString("CML_TITLE"));
                    hvac.setId(jsonObject.getString("Id"));
                    if(jsonObject.has("CML_SET_POINT")) hvac.setPoint(jsonObject.getInt("CML_SET_POINT"));
                    if(jsonObject.has("CML_HIGHEST_POINT")) hvac.setHight_point(jsonObject.getInt("CML_HIGHEST_POINT"));
                    if(jsonObject.has("CML_LOWER_POINT")) hvac.setLower_point(jsonObject.getInt("CML_LOWER_POINT"));
                    //  else hvac.setHvac_point();
                    hvacArrlist.add(hvac);
                }
            }
        }
        catch (Exception ex){
             Logs.error(TAG+"_HvacStateError",ex.getMessage());}
        Logs.info(TAG, "hvaclistsize" + hvacArrlist.size());
        if (hvacArrlist.size() > 0) {
            lin_hvac_list.setVisibility(View.VISIBLE);
            hvacListAdapter = new HvacListAdapter(mcontext,hvacArrlist);
            rec_hvac.setAdapter(hvacListAdapter);
            rec_hvac.setNestedScrollingEnabled(false);
            rec_hvac.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(mcontext);
            rec_hvac.setLayoutManager(mLayoutManager);
        } else {
            lin_hvac_list.setVisibility(View.GONE);
        }
    }
    public void getLightGroupState()
    {
        grplightArrlist.clear();
        try {
            ArrayList<String> arrayList=new ArrayList();
            JSONArray jsonArray = App.getGroupByRoomJsonObject().getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if (jsonObject.has("groupType")) {
                    if (jsonObject.getString("groupType").equals("Lighting"))
                    {
                         Logs.info(TAG+"_RoomTitle", jsonObject.getString("CML_TITLE"));
                        Light l = new Light();
                        if (jsonObject.has("CML_BRIGHTNESS"))
                            l.setBrightness(jsonObject.getInt("CML_BRIGHTNESS"));
                        if (jsonObject.has("CML_POWER"))
                            l.setLigh_state(jsonObject.getBoolean("CML_POWER"));
                        l.setId(jsonObject.getString("Id"));
                        l.setTitle(jsonObject.getString("CML_TITLE"));
                        if (jsonObject.has("CML_SCENE_ID")) l.setScene_id(jsonObject.getString("CML_SCENE_ID"));
                        l.setFav_id(FavoritesOperations.getFavId(jsonObject.getString("CML_ID")));
                        if(jsonObject.has("CML_STATUS"))l.setStatus(jsonObject.getBoolean("CML_STATUS"));
                         Logs.info(TAG+"_rrrrrrrrrrrrrrrrr",jsonObject.getString("room")+"----"+getRoomTitle(jsonObject.getString("room")));
                        l.setRoom_title(getRoomTitle(jsonObject.getString("room")));
                        l.setType("group");
                        if(!arrayList.contains(""+jsonObject.getString("CML_ID"))) {
                            arrayList.add("" + jsonObject.getString("CML_ID"));
                            grplightArrlist.add(l);
                        }
                    }
                }
            }
            if(grplightArrlist.size()>0) {
                groupLight = true;
                //lin_all_lights.setVisibility(View.VISIBLE);
                setAllLightValues(room_id);
            }
             Logs.info(TAG+"_LightGroupData",""+grplightArrlist.size());
        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_GroupSate",""+ex.getMessage());
        }
        if (grplightArrlist.size() > 0) {
             Logs.info(TAG+"_WhereIm","Beforeadater");
            try {
                lin_light_group_list.setVisibility(View.VISIBLE);
                mLayoutManager = new LinearLayoutManager(getActivity());
                lightGroupListAdapter = new LightGroupListAdapter(mcontext,grplightArrlist,getActivity().getSupportFragmentManager(),txt_room_type.getText().toString(),"regular");
                rec_lights_group.setAdapter(lightGroupListAdapter);
                rec_lights_group.setNestedScrollingEnabled(false);
                rec_lights_group.setLayoutManager(mLayoutManager);
                // lightGroupListAdapter.swap(grplightArrlist);
            }
            catch (Exception ex)
            {
                 Logs.error(TAG+"_AdapetrError",ex.getMessage());
            }
        } else {
            lin_light_group_list.setVisibility(View.GONE);
        }
    }
    @SuppressLint("NewApi")
    public void getBlindGroupState()
    {
        grpblindsArrlist.clear();
        try {
            ArrayList<String> arrayList=new ArrayList();
            JSONArray jsonArray = App.getGroupByRoomJsonObject().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.has("groupType")) {
                    if (jsonObject.getString("groupType").equals("Blinds")) {
                         Logs.info(TAG+"_RoomTitle", jsonObject.getString("groupType") + "" + jsonObject.getString("CML_TITLE"));
                        Blind blind = new Blind();
                        if (jsonObject.has("CML_SET_POINT")) {
                            if (!jsonObject.get("CML_SET_POINT").toString().equals("null")) blind.setPoint(jsonObject.getInt("CML_SET_POINT"));
                        }
                        if (jsonObject.has("CML_POWER"))
                            blind.setState(jsonObject.getBoolean("CML_POWER"));
                        blind.setId(jsonObject.getString("Id"));
                        blind.setTitle(jsonObject.getString("CML_TITLE"));
                        blind.setFav_id(FavoritesOperations.getFavId(jsonObject.getString("CML_ID")));
                        blind.setType("group");
                        if(!arrayList.contains(""+jsonObject.getString("CML_ID"))) {
                            arrayList.add("" + jsonObject.getString("CML_ID"));
                            grpblindsArrlist.add(blind);
                        }
                    }
                }
            }
            if(grpblindsArrlist.size()>0) {
                roomBlind = true;
            }
            if (grpblindsArrlist.size() > 0) {
                mLayoutManager = new LinearLayoutManager(getActivity());
                blindsGroupListAdapter = new BlindsGroupListAdapter(mcontext,grpblindsArrlist);
                rec_blinds_group.setAdapter(blindsGroupListAdapter);
                rec_blinds_group.setNestedScrollingEnabled(false);
                rec_blinds_group.setLayoutManager(mLayoutManager);
                rec_blinds_group.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        BubbleSeekBar seek_blind= v.findViewById(R.id.seek_blind);
                        seek_blind.correctOffsetWhenContainerOnScrolling();
                    }
                });
                //blindsGroupListAdapter.swap(grpblindsArrlist);
                 Logs.info(TAG+"_WhereIm","blindafteradater");
                lin_blind_group_list.setVisibility(View.VISIBLE);
            } else {
                lin_blind_group_list.setVisibility(View.GONE);
            }
        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_BlindGroupError",""+ex.getMessage());
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getBlindState()
    {
        blindsArrlist.clear();
        try {
            JSONArray jsonArray = App.getDevicesByRoomData().getJSONArray("data");
            ArrayList<String> arrayList=new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("type").equals("Blinds"))
                {
                     Logs.info(TAG+"_WhereIM","InBlinds"+jsonObject);
                    if (jsonObject.has("group")) {
                        if (jsonObject.getString("group").equals("")) {
                            roomBlind = true;
                            Blind blind = new Blind();
                            blind.setId("" + jsonObject.getString("Id"));
                            blind.setDevice_id("" + jsonObject.getString("CML_ID"));
                            if (jsonObject.has("CML_SET_POINT")) {
                                if (!jsonObject.get("CML_SET_POINT").toString().equals("null")) blind.setPoint(jsonObject.getInt("CML_SET_POINT"));
                            }
                            if (jsonObject.has("CML_TITLE")) blind.setTitle(jsonObject.getString("CML_TITLE"));
                            if (jsonObject.has("CML_POWER")) blind.setState(jsonObject.getBoolean("CML_POWER"));
                            blind.setFav_id(FavoritesOperations.getFavId(jsonObject.getString("CML_ID")));
                            blind.setType("ind");
                            if(!arrayList.contains(""+jsonObject.getString("CML_ID"))) {
                                arrayList.add("" + jsonObject.getString("CML_ID"));
                                blindsArrlist.add(blind);
                            }
                        }
                    }
                    else
                    {
                         Logs.info(TAG+"_WhereIM","InBlinds");
                        roomBlind = true;
                        Blind blind = new Blind();
                        blind.setId("" + jsonObject.getString("Id"));
                        if (jsonObject.has("CML_SET_POINT")) {
                            if (!jsonObject.get("CML_SET_POINT").toString().equals("null"))blind.setPoint(jsonObject.getInt("CML_SET_POINT"));
                        }
                        if (jsonObject.has("CML_TITLE")) blind.setTitle(jsonObject.getString("CML_TITLE"));
                        if (jsonObject.has("CML_POWER")) blind.setState(jsonObject.getBoolean("CML_POWER"));
                        blind.setFav_id(FavoritesOperations.getFavId(jsonObject.getString("CML_ID")));
                        blind.setType("ind");
                        if(!arrayList.contains(""+jsonObject.getString("CML_ID"))) {
                            arrayList.add("" + jsonObject.getString("CML_ID"));
                            blindsArrlist.add(blind);
                        }
                    }
                }
            }
            Log.e(TAG,""+blindsArrlist.size());
            if(blindsArrlist.size()>0)
            {
                roomBlind=true;
            }
        }
        catch (Exception ex){
             Logs.error(TAG+"_Error",""+ex.getMessage());}
        if (blindsArrlist.size() > 0) {
            lin_blind_list.setVisibility(View.VISIBLE);
            rec_blinds.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(mcontext);
            rec_blinds.setLayoutManager(mLayoutManager);
            blindsListAdapter=new BlindsListAdapter(mcontext,blindsArrlist);
            rec_blinds.setAdapter(blindsListAdapter);
            rec_blinds.setNestedScrollingEnabled(false);
            rec_blinds.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    BubbleSeekBar seek_blind= v.findViewById(R.id.seek_blind);
                    seek_blind.correctOffsetWhenContainerOnScrolling();
                }
            });
        } else {
            lin_blind_list.setVisibility(View.GONE);
        }
    }
     @Optional @OnClick(R.id.img_all_fav)
    public void img_all_fav()
    {
        if(all_light_fav)
        {
            FavoritesOperations.deleteFav(FavoritesOperations.getFavId(txt_room_id.getText().toString()));
        }
        else
        {
            FavoritesOperations.addFav(txt_room_id.getText().toString());
            all_light_fav=true;
        }
    }
    private JSONObject getSelectedRoomJson()
    {
        JSONObject resultedjsonObject=null;
        try {
            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("CML_ID").equals(App.getRoom().getRoom_id())) {
                    resultedjsonObject=jsonObject;
                }
            }
        }
        catch (Exception ex){
             Logs.error(TAG+"_RoomStateError",""+ex.getMessage());}
        return resultedjsonObject;
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId()==R.id.whole_on_off) {
            circular_whole_home_on_off.setVisibility(VISIBLE);
            whole_on_off.setVisibility(View.GONE);
            try {
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_power", new JSONObject().put("type", "Lighting").put("state", isChecked)));
            } catch (Exception ex) {
                Logs.error(TAG, "----------" + ex.getMessage());
            }
            whole_on_off.setChecked(!whole_on_off.isChecked());
            SwitchTrackChange.changeTrackColor(mcontext, whole_on_off);
        }
    }
    @Optional @OnClick(R.id.img_option)
    public void img_option()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Bundle bundle=new Bundle();
        bundle.putString("type","all_lights");
        bundle.putString("device_id",""+txt_room_id.getText().toString());
        bundle.putString("room_type",""+txt_room_type.getText().toString());
        bundle.putString("scene_id",""+getRoomSceneId(txt_room_id.getText().toString()));
        SceneDialog dFragment = new SceneDialog();
        dFragment.setArguments(bundle);
        dFragment.show(fm,"Light");
    }
    public String getRoomSceneId(String id)
    {
        String scene_id="";
        try {
            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("CML_ID").equals(id)) {
                    scene_id = jsonObject.getString("CML_SCENE_ID");
                }
            }
        } catch (Exception ex) {
             Logs.error(TAG+"_actualLighExcception",""+ ex.getMessage());
        }
        return scene_id;
    }
    public void checkDawnState()
    {
        if(getDawnState(room_id))
        {
            lin_dawn_running.setVisibility(View.VISIBLE);
            flag_disable_all_views_for_dawn =true;
        }
        else
        {
            lin_dawn_running.setVisibility(View.GONE);
            flag_disable_all_views_for_dawn =false;
        }
        blurLayout.setVisibility((flag_disable_all_views_for_dawn||flag_disable_all_views_for_sleep)?View.VISIBLE:View.GONE);
    }
    public void disableAllViews(){
        disableRecyclerview(rec_music);
        disableRecyclerview(rec_blinds);
        disableRecyclerview(rec_lights);
        disableRecyclerview(rec_lights_group);
        disableRecyclerview(rec_blinds_group);
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
    public void disableRecyclerview(RecyclerView rec)
    {
        rec.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return flag_disable_all_views_for_dawn||flag_disable_all_views_for_sleep;
            }
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
        scroll_container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return flag_disable_all_views_for_dawn||flag_disable_all_views_for_sleep;
            }
        });
    }
    public void checkSleepState()
    {
        if(getSleepState(room_id))
        {
            lin_sleep_running.setVisibility(View.VISIBLE);
            flag_disable_all_views_for_sleep=true;
        }
        else
        {
            lin_sleep_running.setVisibility(View.GONE);
            flag_disable_all_views_for_sleep=false;
        }
        blurLayout.setVisibility((flag_disable_all_views_for_dawn||flag_disable_all_views_for_sleep)?View.VISIBLE:View.GONE);
    }
    public boolean getDawnState(String room_id)
    {
        boolean b=false;
        try {
            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_ID").equals(room_id))
                {
                    if (jsonObject.has("DAWN_RUNNING"))b=jsonObject.getBoolean("DAWN_RUNNING");
                }
            }
        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_DawnRunninError",ex.getMessage());
        }
        return  b;
    }
    public boolean getSleepState(String room_id)
    {
        boolean b=false;
        try {
            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_ID").equals(room_id))
                {
                    if(jsonObject.has("SLEEP_RUNNING"))
                    {
                        b=jsonObject.getBoolean("SLEEP_RUNNING");
                        txt_sleep_desc.setText(jsonObject.getString("SLEEP_ID").contains("prepare")?mcontext.getResources().getString(R.string.prepare_for_sleep_is_running):mcontext.getResources().getString(R.string.sleep_is_running));
                    }
                }
            }
        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_DawnRunninError",ex.getMessage());
        }
        return  b;
    }
    @OnClick(R.id.btn_dawn_cancle)
    public void btn_dawn_cancle()
    {
        ReplaceFragment.replaceFragment(this,R.id.frm_main_container,ElementsMainFragmentPortrait.newInstance());
    }
    @OnClick(R.id.btn_sleep_cancle)
    public void btn_sleep_cancle()
    {
        ReplaceFragment.replaceFragment(this,R.id.frm_main_container,ElementsMainFragmentPortrait.newInstance());
    }
    @OnClick(R.id.btn_dawn_confirm)
    public void btn_dawn_confirm()
    {
        try {
            JSONObject dataJsonObject=getSimulatuonObject("dawn");
            //  jsonObject.put("status",true);
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("stop_dawn_simulation", dataJsonObject));
            RemoveNotification.removeSpecificDawnNotification(mcontext,dataJsonObject.getString("CML_ID"));
        }
        catch (Exception ex){}
    }
    @OnClick(R.id.btn_sleep_confirm)
    public void btn_sleep_confirm()
    {
         Logs.info(TAG+"_WhereIM","IMInSleepConfirm");
        try {
            JSONObject dataJsonObject=getSimulatuonObject("sleep");
            //  jsonObject.put("status",true);
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("stop_sleep_simulation", dataJsonObject));
            RemoveNotification.removeSpecificSleepNotification(mcontext,dataJsonObject.getString("CML_ID"));
        }
        catch (Exception ex){
             Logs.error(TAG+"_Sleep_halt_error",""+ex.getMessage());
        }
    }
    private JSONObject getSimulatuonObject(String type)
    {
        JSONObject simulation_json=null;
        try {
            JSONArray jsonArray = App.getAllSimulationData().getJSONArray("data");
             Logs.info(TAG+"_getAllSimulationDataaaa",""+jsonArray);
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(type.equals("dawn")) {
                    if (getDawnId().equals(jsonObject.getString("CML_ID"))) {
                        simulation_json = jsonObject;
                    }
                }
                if (type.equals("sleep")) {
                    String sleep_id=getSleepId();
                     Logs.info(TAG+"_Sleep_id",""+sleep_id);
                    if(sleep_id.contains("prepare"))
                    {
                        JSONObject datajson=new JSONObject();
                        datajson.put("Id",sleep_id);
                        datajson.put("room",room_id);
                        simulation_json=datajson;
                    }
                    else
                    {
                        if (sleep_id.equals(jsonObject.getString("CML_ID"))) {
                            simulation_json = jsonObject;
                        }
                    }
                }
            }
        }
        catch (Exception ex){
             Logs.info(TAG+"_RoomDawnError","------------------------"+ex.getMessage());
        }
        return simulation_json;
    }
    public String getDawnId()
    {
        String string_id="";
        try {
            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
             Logs.info(TAG+"_getAllSimulationDataaaa",""+jsonArray);
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (room_id.equals(jsonObject.getString("CML_ID"))) {
                    if(jsonObject.has("DAWN_ID")) string_id= jsonObject.getString("DAWN_ID");
                }
            }
        }
        catch (Exception ex){
             Logs.error(TAG+"_RoomDawnError","------------------------"+ex.getMessage());
        }
        return string_id;
    }
    public String getSleepId()
    {
        String string_id="";
        try {
            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
             Logs.info(TAG+"_getAllSimulationDataaaa",""+jsonArray+"----"+room_id);
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (room_id.equals(jsonObject.getString("CML_ID"))) {
                    if(jsonObject.has("SLEEP_ID")) string_id= jsonObject.getString("SLEEP_ID");
                }
            }
        }
        catch (Exception ex){
             Logs.error(TAG+"_RoomDawnError","------------------------"+ex.getMessage());
        }
        return string_id;
    }
    @OnClick(R.id.img_back)
    public void img_back()
    {
        if(!(flag_disable_all_views_for_sleep||flag_disable_all_views_for_dawn)) {
            ReplaceFragment.replaceFragment(RoomDevicesFragmentPortrait.this, R.id.frm_main_container, ElementsMainFragmentPortrait.newInstance());
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        elements_tabs.setVisibility(View.GONE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow);
    }
    @Override
    public void onStop() {
        super.onStop();
        elements_tabs.setVisibility(View.VISIBLE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    public void setDefaultWholeHomeValue()
    {
        try {
            JSONArray jsonArray = App.getZoneJson().getJSONArray("data");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_TITLE").equals("Whole Home")) {
                    Logs.info(TAG + "_HomeZoneObject", "" + jsonObject);
                    // Light value set
                    if (jsonObject.has("CML_LIGHT_POWER")) {
                        whole_on_off.setOnCheckedChangeListener(null);
                        whole_on_off.setChecked(jsonObject.getBoolean("CML_LIGHT_POWER"));
                        whole_on_off.setOnCheckedChangeListener(this);
                        circular_whole_home_on_off.setVisibility(View.GONE);
                        whole_on_off.setVisibility(View.VISIBLE);
                        SwitchTrackChange.changeTrackColor(mcontext, whole_on_off);
                    }
                    if (jsonObject.has("CML_STATUS")) {
                        rel_visiblity.setVisibility(jsonObject.getBoolean("CML_STATUS") ? View.GONE : View.VISIBLE);
                        disableAllViews(lin_whole_home_light_offline, jsonObject.getBoolean("CML_STATUS"));
                        check_whole_light_status_flag= jsonObject.getBoolean("CML_STATUS");
                    }
                    if(!jsonObject.getBoolean("CML_LIGHT_POWER")) {
                        check_whole_light_state_flag = false;
                        whole_img_option.setColorFilter(mcontext.getResources().getColor(R.color.fragment_blue_light), PorterDuff.Mode.SRC_IN);
                    }
                    else {
                        check_whole_light_state_flag = true;
                        whole_img_option.setColorFilter(mcontext.getResources().getColor(R.color.familyname_select_color), PorterDuff.Mode.SRC_IN);
                    }
                    if(check_whole_light_state_flag&&check_whole_light_status_flag)
                    {
                        whole_img_option.setEnabled(true);
                    }
                    else
                    {
                        whole_img_option.setEnabled(false);
                    }
                    whole_img_option.setEnabled(true);
                    // Blind value set
                    if (jsonObject.has("CML_BLIND_POWER")) {
                        if (jsonObject.getBoolean("CML_BLIND_POWER")) {
                            setButtonSelection(btn_whole_open, btn_whole_open,btn_whole_close);
                        } else {
                            setButtonSelection(btn_whole_close, btn_whole_open, btn_whole_close);
                        }
                    }
                    // Music value set
                    if(jsonObject.has("CML_STATE")) {
                        if (jsonObject.getString("CML_STATE").equals("playing")) {
                            whole_play_pause_flag = true;
                            img_whole_music_play_pause.setImageResource(R.drawable.ic_music_pause_blue);
                        } else {
                            whole_play_pause_flag = false;
                            img_whole_music_play_pause.setImageResource(R.drawable.ic_playlist);
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Logs.info(TAG+"_SetAQIError",""+ex.getMessage());
        }
    }
    @OnClick(R.id.img_whole_music_prev)
    public void img_whole_music_prev()
    {
        try {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_previous_song", new JSONObject()));
        }
        catch (Exception ex)
        {
            Logs.error(TAG,"----------"+ex.getMessage());
        }
    }
    @OnClick(R.id.img_whole_music_play_pause)
    public void img_whole_music_play_pause()
    {
        try {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject(whole_play_pause_flag?"home_pause_song":"home_play_song", new JSONObject()));
        }
        catch (Exception ex)
        {
            Logs.error(TAG,"----------"+ex.getMessage());
        }
    }
    @OnClick(R.id.img_whole_music_next)
    public void img_whole_music_next()
    {
        try {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_next_song", new JSONObject()));
        }
        catch (Exception ex)
        {
            Logs.error(TAG,"----------"+ex.getMessage());
        }
    }
    @OnClick(R.id.whole_img_option)
    public void   whole_img_option()
    {
        WholeHomeSceneDialog dFragment = new WholeHomeSceneDialog();
        dFragment.show(getFragmentManager(),"individual");
    }
    @OnClick(R.id.btn_whole_stop)
    public void   btn_whole_stop()
    {
        try {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_stopblind", new JSONObject()));
        }
        catch (Exception ex)
        {
            Logs.error(TAG,"----------"+ex.getMessage());
        }
    }
    @OnClick(R.id.btn_whole_close)
    public void   btn_whole_close()
    {
        try {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_power", new JSONObject().put("type","Blinds").put("state",false)));
        }
        catch (Exception ex)
        {
            Logs.error(TAG,"----------"+ex.getMessage());
        }
    }
    @OnClick(R.id.btn_whole_open)
    public void   btn_whole_open()
    {
        try {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_power", new JSONObject().put("type","Blinds").put("state",true)));
        }
        catch (Exception ex)
        {
            Logs.error(TAG,"----------"+ex.getMessage());
        }
    }
    public void setButtonSelection(Button btn_selection, Button btn_open, Button btn_close)
    {
        btn_open.setBackground(mcontext.getResources().getDrawable(R.drawable.btn_border_white));
        btn_close.setBackground(mcontext.getResources().getDrawable(R.drawable.btn_border_white));
        btn_selection.setBackground(mcontext.getResources().getDrawable(R.drawable.btn_fill_color));
    }
    @OnClick(R.id.img_whole_light_add_fav)
    public void   img_whole_light_add_fav()
    {
        if(add_fav_whole_light)
        {
            FavoritesOperations.deleteFav(""+FavoritesOperations.getWholeHomeFavId("allLight"));
            img_whole_light_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
        }
        else
        {
            FavoritesOperations.addFavWholeHome("light#"+System.currentTimeMillis(),"allLight");
            add_fav_whole_light=true;
            img_whole_light_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
        }
    }
    @OnClick(R.id.img_whole_blind_add_fav)
    public void   img_whole_blind_add_fav()
    {
        if(add_fav_whole_blind)
        {
            FavoritesOperations.deleteFav(""+FavoritesOperations.getWholeHomeFavId("allBlind"));
            img_whole_blind_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
        }
        else
        {
            FavoritesOperations.addFavWholeHome("blind#"+System.currentTimeMillis(),"allBlind");
            add_fav_whole_blind=true;
            img_whole_blind_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
        }
    }
    @OnClick(R.id.img_whole_music_add_fav)
    public void   img_whole_music_add_fav()
    {
        if(add_fav_whole_music)
        {
            FavoritesOperations.deleteFav(""+FavoritesOperations.getWholeHomeFavId("allAudio"));
            img_whole_music_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
        }
        else
        {
            FavoritesOperations.addFavWholeHome("audio#"+System.currentTimeMillis(),"allAudio");
            add_fav_whole_music=true;
            img_whole_music_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
        }
    }
    @OnClick(R.id.img_whole_playlist)
    public void img_whole_playlist()
    {
        Device device=new Device();
        device.setDevice_id("whole_home");
        App.setDevice(device);
        Bundle bundle=new Bundle();
        bundle.putString("call_from","room_devices");
        App.setTemp_bundle(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frm_main_container, RoomDevicesMusicFragment.newInstance());
        fragmentTransaction.addToBackStack("RoomDevicesMusicFragment");
        fragmentTransaction.commit();
    }
    public void setDefaultWholeHomeFavoriteState()
    {
        try {
            add_fav_whole_light=false;
            add_fav_whole_blind=false;
            add_fav_whole_music=false;
            img_whole_light_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
            img_whole_blind_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
            img_whole_music_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
            JSONArray jsonArray = App.getFavData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.has("type")) {
                    if (jsonObject.getString("type").equals("allLight")) {
                        add_fav_whole_light=true;
                        img_whole_light_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                    }
                    if (jsonObject.getString("type").equals("allBlind")) {
                        add_fav_whole_blind=true;
                        img_whole_blind_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                    }
                    if (jsonObject.getString("type").equals("allAudio")) {
                        add_fav_whole_music=true;
                        img_whole_music_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                    }
                }
            }
        }
        catch (Exception ex){Log.e("Error",ex.getMessage());}
    }
    public void setWholeCurrentMusic()
    {
        boolean current_music=false;
        try {
            JSONArray jsonArray = App.getZoneJson().getJSONArray("data");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_TITLE").equals("Whole Home"))
                {
                    if(jsonObject.has("CURRENT_TRACK")) {
                        JSONObject current_track_json = jsonObject.getJSONObject("CURRENT_TRACK");
                        String artist_name=current_track_json.getString("artist");
                        txt_whole_muisc_artist_name.setText(artist_name.equals("null")?"":artist_name);
                        if(current_track_json.has("title")) {
                            disableMusicControls(true);
                            txt_whole_music_title.setText(current_track_json.getString("title"));
                            txt_whole_muisc_artist_name.setText(artist_name.equals("null")?"":artist_name);
                        }
                        else
                        {
                            disableMusicControls(false);
                            txt_whole_music_title.setText(mcontext.getResources().getString(R.string.play_song_text));
                            txt_whole_music_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                        }
                        if(current_track_json.has("albumArtURL")) {
                            Glide.with(mcontext).load(current_track_json.getString("albumArtURL"))
                                    .thumbnail(Glide.with(mcontext).load(R.drawable.ic_playlist_default_white))
                                    .error(R.drawable.ic_playlist_default_white)
                                    .override(50, 50)
                                    .dontAnimate()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(img_whole_album_image);
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Logs.info(TAG+"_PlayListError",ex.getMessage());
        }
    }
    public void disableMusicControls(boolean b)
    {
        img_whole_music_prev.setAlpha(!b?0.5f:1f);
        img_whole_music_next.setAlpha(!b?0.5f:1f);
        img_whole_music_play_pause.setAlpha(!b?0.5f:1f);
        img_whole_music_prev.setEnabled(b);
        img_whole_music_next.setEnabled(b);
        img_whole_music_play_pause.setEnabled(b);
    }
    public void setDefaultSongHashMap()
    {
        HashMap<String,String> default_song_hash=new HashMap<>();
        try {
            if (App.getSoundData() != null) {
                JSONArray jsonArray = App.getSoundData().getJSONArray("data");
                Logs.info(TAG + "_defaultsoundArray", "" + jsonArray);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    default_song_hash.put(jsonObject.getString("CML_TITLE"),jsonObject.getString("CML_TITLE").replace(".m4a","").replace("_"," ").replace(".mp3",""));
                }
                App.setDefault_song_hash(default_song_hash);
            }
        }
        catch (Exception ex)
        {
            Logs.error(TAG,"-------------"+ex.getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.gc();
        rec_lights.removeAllViewsInLayout();
        rec_blinds.removeAllViewsInLayout();
        rec_blinds_group.removeAllViewsInLayout();
        rec_hvac.removeAllViewsInLayout();
        rec_music.removeAllViewsInLayout();
        rec_lights_group.removeAllViewsInLayout();
    }
}