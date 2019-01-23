package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.DawnPlaylistAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Sonos;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Sound;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
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


/**
 * Created by developers on 17/5/17.
 */

public class Wake_PlaylistSound extends DialogFragment {
    private ArrayList<Sound> arr_sound_list=new ArrayList<>();
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    // @BindView(R.id.frm_home)FrameLayout frm_home;
    public String sound;
    TextView txt_cancle,txt_save;
    View view;
    String roomId,soundId;
    Context mcontext;
    JSONObject jsonObject=new JSONObject();
    boolean playListState=false;

    @BindView(R.id.list_sound_mob)
    ListView lstplaylist;

    @BindView(R.id.no_data_txt)TextView no_data_txt;
    @BindView(R.id.btn_apply)Button btn_apply;

    @BindView(R.id.rb_playlist)RadioButton rb_playlist;
    SonosPlaylistFragment selectSound=new SonosPlaylistFragment();
    ArrayList<Sonos> sonosArrayList=new ArrayList<>();
    DawnPlaylistAdapter customMusicPlaylistAdapter;

    String device_id="";
    int soundState[];
    int boxState[];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wake_playlist, container,
                false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        mcontext = view.getContext();

        ButterKnife.bind(this, view);
        sonosArrayList.clear();
        getMusicState();
     //   device_id=getArguments().getString("device_id");
        //device_id=CustomDawnSoundAdapter_new.device_id;


        try {

            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_devices_by_room", getSelectedRoomJson()));
           /*App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_devices_by_room", new JSONObject().put("Id",""+Add_custmise_wake.room_id)));*/


            JSONObject obj=new JSONObject();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("Id",device_id);
            obj.put("data", jsonObject);
            obj.put("type", "get_playlists");
            Log.e("SonosplaylistRequest", "" + obj);
            App.getSocket().emit("action", obj);


        } catch (Exception ex) {
            Log.e("ErorrrrWakePlaylist",""+ex.getMessage());
            ex.printStackTrace();
        }
        setSubcriber();

        return view;



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
                    System.out.println("rrrrrrrrrrrrrrrrr"+resultedjsonObject);
                }
            }
        }
        catch (Exception ex){
            Logs.error("Wake_Playlist"+"_RoomStateError",""+ex.getMessage());}
        return resultedjsonObject;
    }

    private void setSubcriber() {
        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        // sub.onNext(""+App.getSoundData());
                        sub.onNext(""+App.getSound());
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


                Log.e("SoundData","sssssssssssssss"+string);
                sonosArrayList.clear();


                    getPlaylistState();
                    if(sonosArrayList.size()>0) {
                        no_data_txt.setVisibility(View.GONE);
                        btn_apply.setVisibility(View.VISIBLE);
                        lstplaylist.setVisibility(View.VISIBLE);
                        customMusicPlaylistAdapter = new DawnPlaylistAdapter(getActivity(),  sonosArrayList, device_id,Wake_PlaylistSound.this);
                        lstplaylist.setAdapter(customMusicPlaylistAdapter);
                        lstplaylist.setVisibility(View.VISIBLE);

                    }
                    else
                    {
                        lstplaylist.setVisibility(View.GONE);
                        no_data_txt.setVisibility(View.VISIBLE);
                        btn_apply.setVisibility(View.GONE);

                    }
                }




        };



        Log.e("IMINSub","hhhhhhhhh");
        Subcriber s = new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);

        //  }


    }


    public void getPlaylistState()
    {
        try {
            JSONArray jsonArray = App.getMusicPlaylistJson().getJSONArray("data");
            Log.e("dawnPlayListArray",""+jsonArray);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Sonos s=new Sonos();
                s.setSonos_track_title(jsonObject.getString("CML_TITLE"));
                s.setSonos_id(jsonObject.getString("Id"));
                s.setSonos_uri(jsonObject.getString("Id"));
                if(jsonObject.has("Album_Art")) s.setSonos_album_art(jsonObject.getString("Album_Art"));
                if(jsonObject.has("selectedFlag")) s.setSelect_flag(jsonObject.getBoolean("selectedFlag"));
                sonosArrayList.add(s);

            }
        }
        catch (Exception ex)
        {
            Log.e("dawnPlayListError",ex.getMessage());
        }
    }
    @OnClick(R.id.img_close)
    public void img_close()
    {
        this.dismiss();
    }


    @OnClick(R.id.btn_apply)
    public void btn_apply()
    {

        Bundle bundle= App.getTemp_bundle();
        bundle.putBoolean("has_track",true);
        App.setTemp_bundle(bundle);

        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(Wake_PlaylistSound.this,R.id.frm_expriencesmain_container, Add_custmise_wake.newInstance());
        else  ReplaceFragment.replaceFragment(Wake_PlaylistSound.this,R.id.frm_main_container, Add_custmise_wake.newInstance());

        this.dismiss();
    }
    @OnClick(R.id.rb_playlist)
    public void rb_playlist() {

      /*  device_id=getArguments().getString("device_id");
        call_from=getArguments().getString("call_from");

        FragmentManager fm = getActivity().getSupportFragmentManager();
        MusicPlaylistDialog dFragment = new MusicPlaylistDialog();
        // Show DialogFragment
        dFragment.show(fm,"Light");
 */    //   selectSound.show(getFragmentManager(),"SonosTrackDialog");

    }

    private ArrayList<Sound> getSounddData()
    {
        try {
            JSONObject jsonObject = App.getSoundData();
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            arr_sound_list.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                Sound sound=new Sound();
                String soundname="";

                soundname= getSoundTitle(jsonObject1.getString("CML_TITLE"));
                System.out.println("soundname"+soundname);

                sound.setSound_id(jsonObject1.getString("CML_ID"));
                sound.setSound_title( jsonObject1.getString("CML_TITLE"));

                arr_sound_list.add(sound);

            }
        }
        catch (Exception ex){
            Log.e("jsonerror","------------------------"+ex.getMessage());
        }
        return arr_sound_list;

    }

    private String getSoundTitle(String sound_id)
    {
        String sound_title="";
        try {


            JSONArray jsonArray = App.getSoundData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("CML_ID").equals(sound_id)) {
                    sound_title = jsonObject.getString("CML_TITLE");
                    //soundId=jsonObject.getString("CML_ID");
                    ////Toast.makeText(getActivity(), ""+sound_id, Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception ex){Log.e("Error",ex.getMessage());}

        return sound_title;

    }

    private void getMusicState() {


        try {

            Logs.info("_MusicJson_dddddddddddddd", "" + App.getDevicesByRoomData());
            JSONArray jsonArray = App.getDevicesByRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);


                if (jsonObject.getString("type").equals("Music")) {
                    Logs.info("_MusicJson", "" + jsonObject);
                    Sonos sonos = new Sonos();
                    sonos.setSonos_id(jsonObject.getString("Id"));
                    device_id=jsonObject.getString("Id");
                  //  //Toast.makeText(mcontext, ""+device_id, Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception ex){
            Logs.error("Error in DawnSonos",""+ex.getMessage());
        }
    }
}