package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.all_experiences;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FavoritesOperations;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;


/**
 * Created by developers on 17/5/17.
 */

public class Fav_bedroom extends Fragment {

    public static Fav_bedroom newInstance() {
        return new Fav_bedroom();
    }

    @BindView(R.id.txt_room_title)TextView txt_room_title;


    @BindView(R.id.txt_watch_tv_active_deactive)TextView txt_watch_tv_active_deactive;
    @BindView(R.id.txt_prepare_active_deactive)TextView txt_prepare_active_deactive;
    @BindView(R.id.txt_read_active_deactive)TextView txt_read_active_deactive;
    @BindView(R.id.txt_leave_active_deactive)TextView txt_leave_active_deactive;
    @BindView(R.id.txt_comfort_active_deactive)TextView txt_comfort_active_deactive;





    @BindView(R.id.img_watch_movie)ImageView img_watch_movie;
    @BindView(R.id.img_comfort)ImageView img_comfort;
    @BindView(R.id.img_prepare)ImageView img_prepare;
    @BindView(R.id.img_read)ImageView img_read;
    @BindView(R.id.img_leave_home)ImageView img_leave_home;
    @BindView(R.id.img_room_type)ImageView img_room_type;

    boolean flag_watch_movie=false,flag_comfort=false,flag_prepare=false,flag_read=false,flag_leave_home=false;
    private View v;
    Context mcontext;


    String COMFORT="comfort",LEAVE="leav",PREPARE="prepare",DINNER="dinner",COOK="cook",WATCHMOVIE="watchmovie",READ="read";
    HashMap<String,String> room_fav=new HashMap<>();
    String room_id="";
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
         v=inflater.inflate(R.layout.fragment_fav_bedroom, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);
        room_fav.put(READ,"16");
        room_fav.put(PREPARE,"12");
        room_fav.put(WATCHMOVIE,"15");
        room_fav.put(LEAVE,"11");
        room_fav.put(COMFORT,"10");
        room_fav.put(DINNER,"13");

        txt_room_title.setText(getArguments().getString("CML_TITLE"));
        room_id=getArguments().getString("CML_ID");
        img_room_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_bedroom_type));

        // //Toast.makeText(getActivity(), ""+getArguments().getString("CML_ROOM_TYPE"), Toast.LENGTH_SHORT).show();
        setSubcriber();

        setVisiblity();
        return v;
    }


    @OnClick(R.id.img_leave_home)
    public void img_leave_home()
    {
        if(!flag_leave_home) {
            FavoritesOperations.addRoomFav(room_fav.get(LEAVE));
            img_leave_home.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
        }
        else
        {
            FavoritesOperations.deleteRoomFav(room_fav.get(LEAVE));
            img_leave_home.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.heart_outline_pink));
        }
    }

    @OnClick(R.id.img_watch_movie)
    public void img_watch_movie()
    {
        if(!flag_watch_movie) {
            FavoritesOperations.addRoomFav(room_fav.get(WATCHMOVIE));
            img_watch_movie.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
        }
        else
        {
            FavoritesOperations.deleteRoomFav(room_fav.get(WATCHMOVIE));
            img_watch_movie.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.heart_outline_pink));
        }
    }

    @OnClick(R.id.img_comfort)
    public void img_comfort()
    {
        if(!flag_comfort) {
            flag_comfort=true;
            FavoritesOperations.addRoomFav(room_fav.get(COMFORT));
            img_comfort.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
        }
        else
        {
            flag_comfort=false;
            FavoritesOperations.deleteRoomFav(room_fav.get(COMFORT));
            img_comfort.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.heart_outline_pink));
        }
    }

    @OnClick(R.id.img_prepare)
    public void img_prepare()
    {
        if(!flag_prepare) {
            flag_prepare=true;
            FavoritesOperations.addRoomFav(room_fav.get(PREPARE));
            img_prepare.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
        }
        else
        {
            flag_prepare=false;
            FavoritesOperations.deleteRoomFav(room_fav.get(PREPARE));
            img_prepare.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.heart_outline_pink));
        }
    }

    @OnClick(R.id.img_read)
    public void img_read()
    {
        if(!flag_read) {
            flag_prepare=true;
            FavoritesOperations.addRoomFav(room_fav.get(READ));
            img_read.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
        }
        else
        {
            flag_prepare=false;
            FavoritesOperations.deleteRoomFav(room_fav.get(READ));
            img_read.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.heart_outline_pink));
        }
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

                Log.e("Response_String",string);
               setValues();

            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.addFav_subscriber(s);


    }


    public void setValues()
    {

        try {


            JSONArray jsonArray = App.getFavData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);


                Log.e("FavoritesJson", "" + jsonObject);

                if (jsonObject.getString("roomId").equals(App.getRoom().getRoom_id())) {
                    if (jsonObject.has("sceneId")) {
                        if (jsonObject.getString("sceneId") != "") {
                            setFavoritesRoom(jsonObject.getString("sceneId"));
                        }
                    }
                }





            }
        }
        catch (Exception ex){
            Log.e("HvacStateError",ex.getMessage());}
    }


    public void setFavoritesRoom(String scene_id)
    {

        switch (scene_id)
        {
            case "10":

                flag_comfort=true;
                img_comfort.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                break;
            case "16":
                flag_read=true;
                img_read.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                break;
            case "12":
                flag_prepare=true;
                img_prepare.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                break;
            case "15":

                flag_watch_movie=true;
                img_watch_movie.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                break;
            case "11":

                flag_leave_home=true;
                img_leave_home.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                break;
        }
    }


    @Nullable
    @OnClick({R.id.lin_read, R.id.lin_watch_tv, R.id.lin_prepare, R.id.lin_leave_home,R.id.lin_comfort})
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.lin_read:
                changeActiveStatus(txt_read_active_deactive);
         //   { data: { home : 'all', scene :'' }, type: 'trigger_scene' }

                try{
        /*          JSONArray jarray=new JSONArray();
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("home", App.getRoom().getRoom_id());
                jsonObject.put("scene", "1");
                JSONObject resultJsonObject = new JSONObject();
                resultJsonObject.put("data", jsonObject);
               jarray.put(0,jsonObject);
               resultJsonObject.put("type", "trigger_scene");
                System.out.println("JARRAYeeeeeeeeeeeeeeee" + resultJsonObject);
                App.getSocket().emit("action", jarray);*/
                    JSONArray jarray=new JSONArray();
                    JSONObject jsonObject = new JSONObject();
                    JSONObject jObject = new JSONObject();
                    jObject.put("roomId",App.getRoom().getRoom_id());
                    jarray.put(0,jObject);
                    jsonObject.put("home", jarray);
                    jsonObject.put("scene", "1");
                    JSONObject resultJsonObject = new JSONObject();
                    resultJsonObject.put("data", jsonObject);
                    resultJsonObject.put("type", "trigger_scene");
                    App.getSocket().emit("action", resultJsonObject);

/***************All Lights***
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("home", "all");
                    jsonObject.put("scene", "1");
                    JSONObject resultJsonObject = new JSONObject();
                    resultJsonObject.put("data", jsonObject);
                    resultJsonObject.put("type", "trigger_scene");
                    System.out.println("JARRAYeeeeeeeeeeeeeeee" + resultJsonObject);
                    App.getSocket().emit("action", resultJsonObject);
                ***********/
        } catch (Exception ex) {
        Log.e("Error", "" + ex.getMessage());
    }
                break;
            case R.id.lin_watch_tv:
                changeActiveStatus(txt_watch_tv_active_deactive);
                break;
            case R.id.lin_prepare:
                changeActiveStatus(txt_prepare_active_deactive);
                break;
            case R.id.lin_leave_home:
                changeActiveStatus(txt_leave_active_deactive);
                break;
            case R.id.lin_comfort:
                changeActiveStatus(txt_comfort_active_deactive);
                break;
        }
    }


    public void changeActiveStatus(TextView txt_select)
    {


        txt_comfort_active_deactive.setText("ACTIVE");
        txt_prepare_active_deactive.setText("ACTIVE");
        txt_watch_tv_active_deactive.setText("ACTIVE");
        txt_read_active_deactive.setText("ACTIVE");
        txt_leave_active_deactive.setText("ACTIVE");
        txt_select.setText("RESET");
        txt_select.setVisibility(View.VISIBLE);
    }

    public void setVisiblity()
    {

        txt_comfort_active_deactive.setVisibility(View.INVISIBLE);
        txt_prepare_active_deactive.setVisibility(View.INVISIBLE);
        txt_read_active_deactive.setVisibility(View.INVISIBLE);
        txt_leave_active_deactive.setVisibility(View.INVISIBLE);
        txt_watch_tv_active_deactive.setVisibility(View.INVISIBLE);

    }

}