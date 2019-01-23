package com.cloudsinc.welltekmobile.native_v2_welltek.dialogs;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.CustomMusicPlaylistAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Sonos;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;


public class MusicPlaylistDialog extends DialogFragment {
    TextView txt_save,txt_cancle;
    private Observable<String> mobservable;
    private Observer<String> myObserver;



    boolean playListState=false;
    String TAG=MusicPlaylistDialog.this.getClass().getName();

    Context mcontext;
    ArrayList<Sonos> sonosArrayList=new ArrayList<>();

    @BindView(R.id.lstplaylist)ListView lstplaylist;
    @BindView(R.id.txt_no_playlist)TextView txt_no_playlist;

    String device_id="";
    @BindView(R.id.circular_progress_bar)ProgressBar circular_progress_bar;

    String call_from;
    CustomMusicPlaylistAdapter customMusicPlaylistAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.custom_musicplaylist_dialog, container,
                false);
        mcontext=rootView.getContext();
        ButterKnife.bind(this,rootView);
       device_id=getArguments().getString("device_id");
        call_from=getArguments().getString("call_from");


         Logs.info(TAG+"_Device_id",device_id);
        final Typeface mFont = Typeface.createFromAsset(rootView.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) rootView.getRootView();


        FontUtility.setAppFont(mContainer, mFont);

        sonosArrayList.clear();

        //  Logs.info(TAG+"_ClassName",getClass().getName());
        App.setCallfrom("MusicDetailFragment");
        try {
            JSONObject obj = new JSONObject();
            obj.put("data", device_id);
            obj.put("type", "get_playlists");
             Logs.info(TAG+"_SimulationRequest", "" + obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

      /*  Sonos s=new Sonos();
        s.setSonos_track_title("xfbfbfdb");
        s.setSonos_id("fxbfdbfdb");
        s.setSonos_uri("fbdfbdfbdb");
       s.setSonos_album_art("dfbgfnfgn");
         s.setSelect_flag(true);
        sonosArrayList.add(s);

        Sonos s1=new Sonos();
        s1.setSonos_track_title("xfbfbfdb");
        s1.setSonos_id("fxbfdbfdb");
        s1.setSonos_uri("fbdfbdfbdb");
        s1.setSonos_album_art("dfbgfnfgn");
        s1.setSelect_flag(true);
        sonosArrayList.add(s1);

        customMusicPlaylistAdapter = new CustomMusicPlaylistAdapter(getActivity(),sonosArrayList, device_id);
        lstplaylist.setAdapter(customMusicPlaylistAdapter);
        lstplaylist.setVisibility(View.VISIBLE);
        txt_no_playlist.setVisibility(View.GONE);
        circular_progress_bar.setVisibility(View.GONE);*/
        setSubcriber();

        return rootView;
    }

    @OnClick(R.id.img_close)
    public void img_close()
    {
        this.dismiss();
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

            }

            @Override
            public void onNext(String string) {
                sonosArrayList.clear();

                if(!playListState) {
                    playListState=true;
                    getPlaylistState();
                     Logs.info(TAG+"_arraylistsize",""+sonosArrayList.size());
                    if(sonosArrayList.size()>0) {
                        customMusicPlaylistAdapter = new CustomMusicPlaylistAdapter(getActivity(),  sonosArrayList, device_id,MusicPlaylistDialog.this,call_from);
                        lstplaylist.setAdapter(customMusicPlaylistAdapter);
                        lstplaylist.setVisibility(View.VISIBLE);
                        txt_no_playlist.setVisibility(View.GONE);
                        circular_progress_bar.setVisibility(View.GONE);
                    }
                    else
                    {
                        txt_no_playlist.setVisibility(View.VISIBLE);
                        circular_progress_bar.setVisibility(View.GONE);

                    }
                }



            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setMusic_playlist_subcriber(s);


    }

    public void getPlaylistState()
    {
        try {
            JSONArray jsonArray = App.getMusicPlaylistJson().getJSONArray("data");
             Logs.info(TAG+"_PlayListArray",""+jsonArray);
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
             Logs.error(TAG+"_PlayListError",ex.getMessage());
        }
    }






}

