package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.types;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.BlindsGroupListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.BlindsListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.HvacListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.LightGroupListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.LightListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.MusicListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.ElementsMainFragmentPortrait;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule.RemoveNotification;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Blind;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Hvac;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Light;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Sonos;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FavoritesOperations;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.ElementsMainFragmentPortrait.elements_tabs;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms.RoomDevicesMusicFragment.getRoomTitle;

/**
 * This class containing functionality related to displaying all devices according to selected device type .
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */

public class DevicesByType extends Fragment {

    public static DevicesByType newInstance() {
        return new DevicesByType();
    }

    private View view;


    @BindView(R.id.recycler_view)RecyclerView recycler_view;
    @BindView(R.id.group_recycler_view)RecyclerView group_recycler_view;
    @BindView(R.id.txt_no_devices)TextView txt_no_devices;
    private Observable<String> mobservable;
    private Observer<String> myObserver;

   Context mcontext;


    String str_type="";

    ArrayList<Sonos> musicArrlist =new ArrayList<>();
    ArrayList<Light> lightArrlist =new ArrayList<>();
    ArrayList<Light> grplightArrlist =new ArrayList<>();
    ArrayList<Hvac> hvacArrlist =new ArrayList<>();
    ArrayList<Blind> blindsArrlist =new ArrayList<>();
    ArrayList<Blind> grpblindsArrlist =new ArrayList<>();
    LightListAdapter lightListAdapter;
    LightGroupListAdapter lightGroupListAdapter;
    MusicListAdapter musicListAdapter;
    HvacListAdapter hvacListAdapter;
    BlindsListAdapter blindsListAdapter ;
    BlindsGroupListAdapter blindsGroupListAdapter;
    @BindView(R.id.rel_loading)
    RelativeLayout rel_loading;
    @BindView(R.id.txt_devices_type)TextView txt_devices_type;
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @Nullable @BindView(R.id.img_back)ImageView img_back;
    LinearLayoutManager mLayoutManager;
    static Dialog dawn_sleep_dialog;
    private static Typeface typeface;
    String TAG="DevicesType";
    static String dawn_sleep_id="";
    String room_id="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_devices_by_type, container, false);
        mcontext= view.getContext();
        ButterKnife.bind(this, view);
        img_back.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_left_arrow));

        str_type=""+getArguments().getString("DEVICE_TYPE");
        txt_fragment_title.setText(str_type);
        txt_devices_type.setText(str_type);
        Log.e("Deivce_type",str_type);

        setSubcriber();
        if(App.getSocket()!=null) {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_provisioned",new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_groups", new JSONObject()));
        }


        return view;
    }

    @OnClick(R.id.img_back)
    public void img_back()
    {
        Bundle bundle= App.getTemp_bundle();
        bundle.putString("call_from","type");
        App.setTemp_bundle(bundle);
        ReplaceFragment.replaceFragment(DevicesByType.this, R.id.frm_main_container, ElementsMainFragmentPortrait.newInstance());
    }






    private void setSubcriber() {


        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext(""+App.getRoomData());
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

            @Override
            public void onNext(String string) {


                Log.e("WhereIm","InElemntType"+string);

                if (App.getProvisionalDevicesData() != null) {

                   if (getArguments().getString("DEVICE_TYPE").equals("Lights")) {
                       txt_no_devices.setText("It seems no Lights are present");
                        getLightState();
                        getLightGroupState();
                    } else if (getArguments().getString("DEVICE_TYPE").equals("Blinds")) {
                       txt_no_devices.setText("It seems no Blinds are present");
                        getBlindGroupState();
                        getBlindState();
                    } else if (getArguments().getString("DEVICE_TYPE").equals("Audio")) {
                       txt_no_devices.setText("It seems no Audio is present");
                       if(string.equals("Music")||string.equals("room_device")||string.equals("provisioned_devices")) getMusicState(string);
                    }

                    if(musicArrlist.size()>0||lightArrlist.size()>0||grplightArrlist.size()>0||blindsArrlist.size()>0||grpblindsArrlist.size()>0)
                    {
                        txt_no_devices.setVisibility(View.GONE);
                        rel_loading.setVisibility(View.GONE);
                        recycler_view.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        recycler_view.setVisibility(View.GONE);
                        txt_no_devices.setVisibility(View.VISIBLE);
                        rel_loading.setVisibility(View.GONE);
                    }


                }

            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);


    }





    private void getLightState()
    {


        lightArrlist.clear();
        try {

            Log.e("DeviceJsonnnnnnnnnnnnn",""+ App.getProvisionalDevicesData().getJSONArray("data"));
            JSONArray jsonArray = App.getProvisionalDevicesData().getJSONArray("data");

            ArrayList<String> arrayList=new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Log.e("LightDevices",""+jsonObject);
                if (jsonObject.getString("type").equals("Lighting")) {
                    if (jsonObject.has("group")){
                        if (jsonObject.getString("group").equals("")) {

                            Log.e("WhereIM","ImInEmptygroup");
                            Log.e("Title",jsonObject.getString("CML_TITLE"));

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
                            l.setRoom_id(jsonObject.getString("room"));
                           if(!jsonObject.getString("room").equals("")) {
                               if(!arrayList.contains(""+jsonObject.getString("CML_ID"))) {
                                   arrayList.add("" + jsonObject.getString("CML_ID"));
                                   lightArrlist.add(l);
                               }
                           }


                        }

                    }
                    else {
                        Log.e("WhereIM","LightRoom");
                        Light l = new Light();
                        if(jsonObject.has("CML_BRIGHTNESS"))l.setBrightness(jsonObject.getInt("CML_BRIGHTNESS"));
                        l.setId(jsonObject.getString("CML_ID"));
                        l.setLigh_state(jsonObject.getBoolean("CML_POWER"));
                        l.setTitle(jsonObject.getString("CML_TITLE"));
                        l.setScene_id(jsonObject.getString("CML_SCENE_ID"));
                        if(jsonObject.has("CML_STATUS"))l.setStatus(jsonObject.getBoolean("CML_STATUS"));
                        l.setFav_id(FavoritesOperations.getFavId(jsonObject.getString("CML_ID")));
                        l.setRoom_title(getRoomTitle(jsonObject.getString("room")));
                        l.setRoom_id(jsonObject.getString("room"));
                        l.setType("ind_linght");
                        if(!jsonObject.getString("room").equals("")) {
                            if(!arrayList.contains(""+jsonObject.getString("CML_ID"))) {
                                arrayList.add("" + jsonObject.getString("CML_ID"));
                                lightArrlist.add(l);
                            }
                        }
                    }



                }
            }



        }
        catch (Exception ex){
            Log.e("LightStateError",""+ex.getMessage());}

        Log.e("ListSize", "" + lightArrlist.size());
        if (lightArrlist.size() > 0) {


            if(lightListAdapter==null) {
                lightListAdapter = new LightListAdapter(mcontext, lightArrlist, getActivity().getSupportFragmentManager(), "", "device_type");
                recycler_view.setNestedScrollingEnabled(false);
                recycler_view.invalidate();
                recycler_view.setHasFixedSize(true);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(layoutManager);
                recycler_view.setAdapter(lightListAdapter);

            }
            else
            {
                lightListAdapter.reload(lightArrlist);
                lightListAdapter.notifyDataSetChanged();
            }

        }
    }

    private void getMusicState(String str_action)
    {

        musicArrlist.clear();
        try {

            Log.e("dddddddddddddddddddd",""+ App.getProvisionalDevicesData());
            JSONArray jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
            ArrayList<String> arrayList=new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);


                if (jsonObject.getString("type").equals("Music")) {
                    Log.e("MusicJson",""+jsonObject);
                    Sonos sonos=new Sonos();
                    sonos.setSonos_id(jsonObject.getString("Id"));
                    sonos.setSonos_song_play_pause_state(jsonObject.getString("CML_STATE"));
                    sonos.setSonos_volume(jsonObject.getInt("CML_VOLUME"));
                    sonos.setDevice_id(jsonObject.getString("CML_ID"));
                    sonos.setDevice_title(jsonObject.getString("CML_TITLE"));
                    sonos.setFav_id(FavoritesOperations.getFavId(jsonObject.getString("CML_ID")));
                    sonos.setRoom_title(getRoomTitle(jsonObject.getString("room")));
                    sonos.setRoom_id(jsonObject.getString("room"));
                    if(jsonObject.has("mute_status")) sonos.setMute_flag(jsonObject.getBoolean("mute_status"));
                    if(jsonObject.has("CURRENT_TRACK")) {
                        JSONObject current_track_json = jsonObject.getJSONObject("CURRENT_TRACK");
                        if(current_track_json.has("albumArtURL")) sonos.setSonos_album_art("" + current_track_json.getString("albumArtURL"));
                        if(current_track_json.has("title")) sonos.setSonos_track_title("" + current_track_json.getString("title"));
                        else sonos.setSonos_track_title("Song title");
                        if(current_track_json.has("artist")) sonos.setSonos_artist_name("" + current_track_json.getString("artist"));
                        else sonos.setSonos_artist_name("Artist name");
                        try {
                            Logs.info(TAG,"*********************"+current_track_json.getInt("queuePosition")+"*******************"+jsonObject.getJSONObject("SONOS_PLAYLIST").getInt("queueLength"));
                            if (current_track_json.getInt("queuePosition") == jsonObject.getJSONObject("SONOS_PLAYLIST").getInt("queueLength")) {
                                sonos.setNext_flag(true);
                            }
                            else
                            {
                                sonos.setNext_flag(false);
                            }
                            if (current_track_json.getInt("queuePosition") == 1) {
                                sonos.setPrev_flag(true);
                            }
                            else
                            {
                                sonos.setPrev_flag(false);
                            }
                        }
                        catch (Exception ex)
                        {
                            Logs.error(TAG,"nextprev disable"+ex.getMessage());
                        }
                    }
                    else {
                        sonos.setSonos_track_title(mcontext.getResources().getString(R.string.song_title));
                        sonos.setSonos_artist_name(mcontext.getResources().getString(R.string.artist_name));
                    }


                    if(jsonObject.getString("CML_PLAYMODE").equals("NORMAL"))
                    {
                        sonos.setShuffle_flag(false);
                        sonos.setRepeat_flag(false);

                    }
                    else if (jsonObject.getString("CML_PLAYMODE").equals("SHUFFLE"))
                    {

                        sonos.setShuffle_flag(true);
                        sonos.setRepeat_flag(true);
                    }
                    else if (jsonObject.getString("CML_PLAYMODE").equals("REPEAT_ALL"))
                    {
                        sonos.setShuffle_flag(false);
                        sonos.setRepeat_flag(true);

                    }
                    else if (jsonObject.getString("CML_PLAYMODE").equals("SHUFFLE_NOREPEAT"))
                    {
                        sonos.setShuffle_flag(true);
                        sonos.setRepeat_flag(false);
                    }




                    if(!jsonObject.getString("room").equals("")) {
                        if(!arrayList.contains(""+jsonObject.getString("Id"))) {
                            arrayList.add("" + jsonObject.getString("Id"));
                            musicArrlist.add(sonos);
                        }
                    }


                }



            }


        }
        catch (Exception ex){
            Log.e(TAG,""+ex.getMessage());}

        Log.e(TAG, "musiclistsize" + musicArrlist.size());
        if (musicArrlist.size() > 0) {
            musicListAdapter = new MusicListAdapter(mcontext,musicArrlist,getActivity().getSupportFragmentManager(),DevicesByType.this,"device_type");
            Log.e(TAG, "********musiclistsize" + musicArrlist.size());
            recycler_view.setNestedScrollingEnabled(false);
            recycler_view.invalidate();
            recycler_view.setHasFixedSize(true);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false);
            recycler_view.setLayoutManager(layoutManager);
            recycler_view.setAdapter(musicListAdapter);


        }
    }
    private void getHVACState()
    {
        hvacArrlist.clear();
        try {


            JSONArray jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("type").equals("HVAC")) {

                    Log.e("HVacJson",""+jsonObject);
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
            Log.e("HvacStateError",ex.getMessage());}
        Log.e(TAG, "hvaclistsize" + hvacArrlist.size());
        if (hvacArrlist.size() > 0) {

            hvacListAdapter = new HvacListAdapter(mcontext,hvacArrlist);
            recycler_view.setAdapter(hvacListAdapter);
            recycler_view.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(mcontext);
            recycler_view.setLayoutManager(mLayoutManager);


        }
    }

    public void getLightGroupState()
    {

        grplightArrlist.clear();

        try {
            JSONArray jsonArray = App.getGroupJson().getJSONArray("data");
            ArrayList<String> arrayList=new ArrayList();
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if (jsonObject.has("groupType")) {

                    if (jsonObject.getString("groupType").equals("Lighting"))
                    {


                        Log.e("RoomTitle", jsonObject.getString("CML_TITLE"));
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
                        l.setRoom_title(getRoomTitle(jsonObject.getString("room")));
                        l.setRoom_id(jsonObject.getString("room"));
                        Log.e("rrrrrrrrrrrrrrrrr",jsonObject.getString("room")+"----"+getRoomTitle(jsonObject.getString("room")));

                        l.setType("group");
                        if(!jsonObject.getString("room").equals("")) {
                            if(!arrayList.contains(""+jsonObject.getString("CML_ID"))) {
                                arrayList.add("" + jsonObject.getString("CML_ID"));
                                grplightArrlist.add(l);
                            }
                        }
                    }



                }

            }


            Log.e("LightGroupData",""+grplightArrlist.size());

        }
        catch (Exception ex)
        {
            Log.e("GroupSate",""+ex.getMessage());
        }

        if (grplightArrlist.size() > 0) {
            Log.e("WhereIm","Beforeadater");
            try {


                mLayoutManager = new LinearLayoutManager(getActivity());

                if(lightGroupListAdapter==null) {
                lightGroupListAdapter = new LightGroupListAdapter(mcontext,grplightArrlist,getActivity().getSupportFragmentManager(),"","device_type");
                 group_recycler_view.setNestedScrollingEnabled(false);
                 group_recycler_view.invalidate();
                 group_recycler_view.setHasFixedSize(true);
                 final LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false);
                 group_recycler_view.setLayoutManager(layoutManager);
                 group_recycler_view.setAdapter(lightGroupListAdapter);
                }
                else
                {
                    lightGroupListAdapter.reload(grplightArrlist);
                    lightGroupListAdapter.notifyDataSetChanged();
                }
                // lightGroupListAdapter.swap(grplightArrlist);

            }
            catch (Exception ex)
            {
                Log.e("AdapetrError",ex.getMessage());
            }

        }



    }


    public void getBlindGroupState()
    {
        grpblindsArrlist.clear();
        try {
            JSONArray jsonArray = App.getGroupJson().getJSONArray("data");
            ArrayList<String> arrayList=new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.has("groupType")) {
                    if (jsonObject.getString("groupType").equals("Blinds")) {


                        Log.e("RoomTitle", jsonObject.getString("groupType") + "" + jsonObject.getString("CML_TITLE"));
                        Blind blind = new Blind();
                        if (jsonObject.has("CML_SET_POINT")) {
                            if(!jsonObject.get("CML_SET_POINT").toString().equals("null")) blind.setPoint(jsonObject.getInt("CML_SET_POINT"));
                        }
                        if (jsonObject.has("CML_POWER"))
                            blind.setState(jsonObject.getBoolean("CML_POWER"));
                        blind.setId(jsonObject.getString("Id"));
                        blind.setTitle(jsonObject.getString("CML_TITLE"));
                        blind.setFav_id(FavoritesOperations.getFavId(jsonObject.getString("CML_ID")));
                        blind.setRoom_title(getRoomTitle(jsonObject.getString("room")));
                        blind.setRoom_id(jsonObject.getString("room"));
                        blind.setType("group");
                        if(!jsonObject.getString("room").equals("")) {
                            if (!arrayList.contains("" + jsonObject.getString("CML_ID"))) {
                                arrayList.add("" + jsonObject.getString("CML_ID"));
                                grpblindsArrlist.add(blind);
                            }
                        }
                    }
                }
            }


            if (grpblindsArrlist.size() > 0) {


                blindsGroupListAdapter = new BlindsGroupListAdapter(mcontext,grpblindsArrlist);
                group_recycler_view.setNestedScrollingEnabled(false);
                group_recycler_view.invalidate();
                group_recycler_view.setHasFixedSize(true);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false);
                group_recycler_view.setLayoutManager(layoutManager);
                group_recycler_view.setAdapter(blindsGroupListAdapter);

                //blindsGroupListAdapter.swap(grpblindsArrlist);
                Log.e("WhereIm","blindafteradater");


            }
        }
        catch (Exception ex)
        {
            Log.e("BlindGroupError",""+ex.getMessage());
        }
    }
    public void getBlindState()
    {
        blindsArrlist.clear();


        try {


            JSONArray jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
            ArrayList<String> arrayList=new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);


                if (jsonObject.getString("type").equals("Blinds"))
                {

                    Log.e("WhereIM","InBlinds"+jsonObject);
                    if (jsonObject.has("group")) {
                        if (jsonObject.getString("group").equals("")) {

                            Blind blind = new Blind();
                            blind.setId("" + jsonObject.getString("Id"));
                            blind.setDevice_id("" + jsonObject.getString("CML_ID"));
                            if (jsonObject.has("CML_SET_POINT")) {

                                if(!jsonObject.get("CML_SET_POINT").toString().equals("null")) blind.setPoint(jsonObject.getInt("CML_SET_POINT"));
                            }
                            if (jsonObject.has("CML_TITLE")) blind.setTitle(jsonObject.getString("CML_TITLE"));
                            if (jsonObject.has("CML_POWER")) blind.setState(jsonObject.getBoolean("CML_POWER"));
                            blind.setFav_id(FavoritesOperations.getFavId(jsonObject.getString("CML_ID")));
                            blind.setRoom_title(getRoomTitle(jsonObject.getString("room")));
                            blind.setRoom_id(jsonObject.getString("room"));
                            blind.setType("ind");

                            if(!jsonObject.getString("room").equals("")) {
                                if(!arrayList.contains(""+jsonObject.getString("CML_ID"))) {
                                    arrayList.add("" + jsonObject.getString("CML_ID"));
                                    blindsArrlist.add(blind);
                                }
                            }
                        }
                    }
                    else
                    {
                        Log.e("WhereIM","InBlinds");
                        Blind blind = new Blind();
                        blind.setId("" + jsonObject.getString("Id"));
                        if (jsonObject.has("CML_SET_POINT")) {

                            if(!jsonObject.get("CML_SET_POINT").toString().equals("null")) blind.setPoint(jsonObject.getInt("CML_SET_POINT"));
                        }
                        if (jsonObject.has("CML_TITLE")) blind.setTitle(jsonObject.getString("CML_TITLE"));
                        if (jsonObject.has("CML_POWER")) blind.setState(jsonObject.getBoolean("CML_POWER"));
                        blind.setFav_id(FavoritesOperations.getFavId(jsonObject.getString("CML_ID")));
                        blind.setRoom_title(getRoomTitle(jsonObject.getString("room")));
                        blind.setRoom_id(jsonObject.getString("room"));
                        blind.setType("ind");
                        if(!jsonObject.getString("room").equals("")) {
                            if(!arrayList.contains(""+jsonObject.getString("CML_ID"))) {
                                arrayList.add("" + jsonObject.getString("CML_ID"));
                                blindsArrlist.add(blind);
                            }
                        }
                    }
                }

            }
            Log.e(TAG,""+blindsArrlist.size());

        }
        catch (Exception ex){
            Log.e("Error",""+ex.getMessage());}

        if (blindsArrlist.size() > 0) {


            blindsListAdapter=new BlindsListAdapter(mcontext,blindsArrlist);
            recycler_view.setNestedScrollingEnabled(false);
            recycler_view.invalidate();
            recycler_view.setHasFixedSize(true);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false);
            recycler_view.setLayoutManager(layoutManager);
            recycler_view.setAdapter(blindsListAdapter);


        }

    }

    @Override
    public void onResume() {
        super.onResume();
        elements_tabs.setVisibility(View.GONE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow);


    }

    @Override
    public void onStop() {
        super.onStop();
        elements_tabs.setVisibility(View.VISIBLE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }


    public static boolean checkDawnSleep(String room_id,Context mcontext) {
        boolean flag = false;
        Logs.info("DeviceByType","room_id"+room_id);
        if (checkDawnSleepRunning(room_id,"dawn",mcontext)) {
            showDialog(mcontext.getResources().getString(R.string.dawn_is_running),"dawn",mcontext,room_id);
            flag = true;
        }
        if (checkDawnSleepRunning(room_id,"sleep",mcontext)) {
            showDialog(dawn_sleep_id.contains("prepare")?mcontext.getResources().getString(R.string.prepare_for_sleep_is_running):mcontext.getResources().getString(R.string.sleep_is_running),"sleep",mcontext,room_id);
            flag = true;
        }
        return flag;
    }

    public static boolean checkDawnSleepRunning(String room_id,String type,Context mcontext)
    {
        boolean b=false;
        try
        {
            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject json = jsonArray.getJSONObject(i);
                if(json.getString("CML_ID").equals(room_id)) {
                    if (type.equals("dawn") && json.getBoolean("DAWN_RUNNING")) {
                        dawn_sleep_id = json.getString("DAWN_ID");
                        b = true;
                        break;
                    }
                    if (type.equals("sleep") && json.getBoolean("SLEEP_RUNNING")) {
                        dawn_sleep_id = json.getString("SLEEP_ID");
                        b = true;
                        break;
                    }
                }


            }

        }
        catch (Exception ex)
        {
            Logs.error("DeviceByType","dawn_sleep_check_error"+ex.getMessage());
        }
        return  b;
    }

    private static Typeface setFontForDialog(Context mcontext)
    {

        typeface = Typeface.createFromAsset(mcontext.getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        return typeface;
    }

    public static void showDialog(String msg,final String type,final Context mcontext,final String room_id)
    {
        if(dawn_sleep_dialog!=null)
        {
            if(dawn_sleep_dialog.isShowing())
            {
                dawn_sleep_dialog.dismiss();
            }
        }
        dawn_sleep_dialog=new Dialog(mcontext);
        if(dawn_sleep_dialog.isShowing())dawn_sleep_dialog.dismiss();
        dawn_sleep_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dawn_sleep_dialog.setContentView(R.layout.general_dialog);
        dawn_sleep_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dawn_sleep_dialog.setCanceledOnTouchOutside(false);
        TextView txtmsgstr= dawn_sleep_dialog.findViewById(R.id.txt_msg);
        txtmsgstr.setTypeface(setFontForDialog(mcontext));
        txtmsgstr.setText(""+msg);
        Button btnretry= dawn_sleep_dialog.findViewById(R.id.btn_retry);
        btnretry.setTypeface(setFontForDialog(mcontext));
        btnretry.setText("Cancel");
        Button btncancle= dawn_sleep_dialog.findViewById(R.id.btncancle);
        btncancle.setVisibility(View.VISIBLE);
        btncancle.setText("OK");
        btncancle.setTypeface(setFontForDialog(mcontext));
        btnretry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dawn_sleep_dialog!=null) dawn_sleep_dialog.dismiss();
                dawn_sleep_dialog=null;
            }
        });
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    JSONObject dataJsonObject=getSimulatuonObject(type,room_id);
                    //  jsonObject.put("status",true);
                    App.getSocket().emit("action", type.equals("dawn")? JsonObjectCreater.getJsonObject("stop_dawn_simulation", dataJsonObject):JsonObjectCreater.getJsonObject("stop_sleep_simulation", dataJsonObject));
                    RemoveNotification.removeSpecificDawnNotification(mcontext,dataJsonObject.getString("CML_ID"));
                }
                catch (Exception ex){}
                dawn_sleep_dialog.dismiss();
            }
        });
        dawn_sleep_dialog.show();

    }

    public static JSONObject getSimulatuonObject(String type,String room_id)
    {


        JSONObject simulation_json=null;
        try {
            JSONArray jsonArray = App.getAllSimulationData().getJSONArray("data");
            Logs.info("DeviceByType"+"_getAllSimulationDataaaa",""+jsonArray);

            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if(type.equals("dawn")) {
                    if(jsonObject.has("CML_ID")) {
                        if (dawn_sleep_id.equals(jsonObject.getString("CML_ID"))) {

                            simulation_json = jsonObject;

                        }
                    }
                }
                if (type.equals("sleep")) {

                    String sleep_id=dawn_sleep_id;
                    Logs.info("DeviceByType"+"_Sleep_id",""+sleep_id);
                    if(sleep_id.contains("prepare"))
                    {
                        JSONObject datajson=new JSONObject();
                        datajson.put("Id",sleep_id);
                        datajson.put("room",room_id);
                        simulation_json=datajson;
                    }
                    else
                    {
                        if(jsonObject.has("CML_ID")) {
                            if (sleep_id.equals(jsonObject.getString("CML_ID"))) {

                                simulation_json = jsonObject;

                            }
                        }
                    }
                }
            }

        }
        catch (Exception ex){
            Logs.info("DeviceByType"+"_RoomDawnError","------------------------"+ex.getMessage());
        }

        return simulation_json;
    }

}