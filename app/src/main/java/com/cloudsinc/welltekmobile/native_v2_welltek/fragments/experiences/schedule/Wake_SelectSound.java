package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.CustomDawnSoundAdapter_new;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
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

public class Wake_SelectSound extends DialogFragment {

    private static final String TAG = Wake_SelectSound.class.getName();

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
    @BindView(R.id.list_sound)
    GridView list_sound;
    CustomDawnSoundAdapter_new soundAdapter;
    SonosPlaylistFragment selectSound=new SonosPlaylistFragment();
    String device_id="";
    String call_from;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wake_select_sound, container,
                false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        mcontext = view.getContext();

        ButterKnife.bind(this, view);



        setSubcriber();
        try {



              App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_devices_by_room", getSelectedRoomJson()));

            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_default_dawn_sounds", new JSONObject()));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"Error in emit"+e.getMessage());
        }
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
                }
            }
        }
        catch (Exception ex){
            Logs.error("WakeS_RoomStateError",""+ex.getMessage());}
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

                try {
                    System.out.println("soundsize"+getSounddData().size());
                    if(getSounddData().size()>0) {

                        // rel_no_data.setVisibility(View.GONE);
                        list_sound.setVisibility(View.VISIBLE);

                        if(string.equals("Music")&&!App.isSound_test_flag())
                        {
                            soundAdapter = new CustomDawnSoundAdapter_new(getActivity(), arr_sound_list, Wake_SelectSound.this);
                            //soundAdapter = new CustomDawnSoundAdapter(getActivity(), arr_sound_list, Wake_SelectSound.this);
                            list_sound.setAdapter(soundAdapter);
                        }
                        else {

                            if (soundAdapter == null) {
                                soundAdapter = new CustomDawnSoundAdapter_new(getActivity(), arr_sound_list, Wake_SelectSound.this);
                                //soundAdapter = new CustomDawnSoundAdapter(getActivity(), arr_sound_list, Wake_SelectSound.this);
                                list_sound.setAdapter(soundAdapter);
                            } else {
                                soundAdapter.reload(arr_sound_list);
                                soundAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    else
                    {
                      //  //Toast.makeText(getActivity(), "No Sound Available", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception ex){Log.e("Error",""+ex.getMessage());}
            }
        };



        Log.e("IMINSub","hhhhhhhhh");
        Subcriber s = new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);

        //  }


    }
    @OnClick(R.id.img_close)
    public void img_close()
    {
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(Wake_SelectSound.this,R.id.frm_expriencesmain_container, Add_custmise_wake.newInstance());
        else  ReplaceFragment.replaceFragment(Wake_SelectSound.this,R.id.frm_main_container, Add_custmise_wake.newInstance());

        this.dismiss();
    }


    @OnClick(R.id.btn_apply)
    public void btn_apply()
    {

        Bundle bundle= App.getTemp_bundle();
        bundle.putBoolean("has_track",true);
        bundle.putString("applied_dawn_sound",""+App.getTemp_bundle().getString("default_sound"));
        App.setTemp_bundle(bundle);
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(Wake_SelectSound.this,R.id.frm_expriencesmain_container, Add_custmise_wake.newInstance());
        else  ReplaceFragment.replaceFragment(Wake_SelectSound.this,R.id.frm_main_container, Add_custmise_wake.newInstance());


        this.dismiss();
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
            final Sound sound=new Sound();
            sound.setSound_id("6");
            sound.setSound_title("SONOS Playlist");
            arr_sound_list.add(sound);
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

}