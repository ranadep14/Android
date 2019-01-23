package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.CustomSonosDawnSoundAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Sound;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FilterJsonArray;

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
 * Created by Jaid Shaikh on 17/5/17.
 */

public class SonosPlaylistFragment extends DialogFragment {

    private String device_id="";

    public static SonosPlaylistFragment newInstance() {
        return new SonosPlaylistFragment();
    }
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
    @BindView(R.id.play_list_sound)
    ListView list_sound;
    @BindView(R.id.rb_playlist)RadioButton rb_playlist;
    @BindView(R.id.no_text)TextView no_text;
    @BindView(R.id.btn_apply)TextView btn_apply;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sonos_playlist, container,
                false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        ButterKnife.bind(this, view);
        device_id=App.getDevice().getDevice_id();


        setSubcriber();
        if(App.getSocket()!=null) {
            try {
                JSONObject obj = new JSONObject();
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("Id",device_id);
                obj.put("data", jsonObject);
                obj.put("type", "get_playlists");
                Log.e("get_playlists_Request", "" + obj);
                App.getSocket().emit("action", obj);

               /*******Radio***
                JSONObject obj1 = new JSONObject();
                obj1.put("data", new JSONObject().put("Id",device_id));
                obj1.put("type", "get_radio_playlist");
                Log.e("get_playlistsRequest", "" + obj1);
                App.getSocket().emit("action", obj1);*/

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return view;

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
                    if(getSounddData().size()==0) {
                        no_text.setVisibility(View.VISIBLE);
                        btn_apply.setVisibility(View.GONE);
                    }
                    System.out.println("soundsize"+getSounddData().size());
                    if(getSounddData().size()>0) {
                        no_text.setVisibility(View.GONE);
                        btn_apply.setVisibility(View.VISIBLE);

                        // rel_no_data.setVisibility(View.GONE);
                        list_sound.setVisibility(View.VISIBLE);
                        arr_sound_list=(ArrayList<Sound>) FilterJsonArray.getFirlterByTag("Sound_list", "");
                        CustomSonosDawnSoundAdapter soundAdapter = new CustomSonosDawnSoundAdapter(getActivity(), arr_sound_list,SonosPlaylistFragment.this);
                        list_sound.setAdapter(soundAdapter);


                        list_sound.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                            }
                        });
                    }

                    else
                    {
                        no_text.setVisibility(View.VISIBLE);
                        btn_apply.setVisibility(View.GONE);

                       // //Toast.makeText(getActivity(), "No Sound Available", Toast.LENGTH_SHORT).show();
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
        this.dismiss();
    }


    @OnClick(R.id.btn_apply)
    public void btn_apply()
    {

        Bundle bundle= App.getTemp_bundle();
        bundle.putBoolean("has_track",true);
        App.setTemp_bundle(bundle);
        this.dismiss();
    }
    @OnClick(R.id.rb_playlist)
    public void rb_playlist() {


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
                                   }
            }
        }
        catch (Exception ex){Log.e("Error",ex.getMessage());}

        return sound_title;

    }
}