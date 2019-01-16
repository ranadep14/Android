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

public class Fav_kicthen extends Fragment {

    public static Fav_kicthen newInstance() {
        return new Fav_kicthen();
    }

    @BindView(R.id.txt_room_title)TextView txt_room_title;
    @BindView(R.id.img_cook)ImageView img_cook;
    @BindView(R.id.img_comfort)ImageView img_comfort;
    @BindView(R.id.img_dinner)ImageView img_dinner;


    @BindView(R.id.txt_cook_active_reset)TextView txt_cook_active_reset;
    @BindView(R.id.txt_dinner_active_reset)TextView txt_dinner_active_reset;
    @BindView(R.id.txt_comfort_active_deactive)TextView txt_comfort_active_deactive;
    @BindView(R.id.img_room_type)ImageView img_room_type;

    boolean flag_dinner=false,flag_watch_movie=false,flag_cook=false,flag_mid_day=false,flag_comfort=false, flag_entertain =false,flag_read=false,flag_leave_home=false;
    private View v;
    Context mcontext;


    String COMFORT="comfort",LEAVE="leav",PREPARE="prepare",MID_DAY="mid_day",ENTERTAIN="entertain",DINNER="dinner",COOK="cook",WATCHMOVIE="watchmovie",READ="read";
    HashMap<String,String> room_fav=new HashMap<>();
    String room_id="";
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
        v=inflater.inflate(R.layout.fragment_fav_kitchen, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);
        room_fav.put(READ,"16");
        room_fav.put(PREPARE,"12");
        room_fav.put(WATCHMOVIE,"15");
        room_fav.put(LEAVE,"11");
        room_fav.put(COMFORT,"10");
        room_fav.put(ENTERTAIN,"17");
        room_fav.put(DINNER,"13");
        room_fav.put(MID_DAY,"18");
        room_fav.put(COOK,"14");

        txt_room_title.setText(getArguments().getString("CML_TITLE"));
        room_id=getArguments().getString("CML_ID");
        img_room_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_kitchen_type));

        // //Toast.makeText(getActivity(), ""+getArguments().getString("CML_ROOM_TYPE"), Toast.LENGTH_SHORT).show();
        setSubcriber();
        setVisiblity();
        return v;
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



    @OnClick(R.id.img_dinner)
    public void img_dinner()
    {
        if(!flag_dinner) {
            flag_dinner=true;
            FavoritesOperations.addRoomFav(room_fav.get(DINNER));
            img_dinner.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
        }
        else
        {
            flag_dinner=false;
            FavoritesOperations.deleteRoomFav(room_fav.get(DINNER));
            img_dinner.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.heart_outline_pink));
        }
    }
    @OnClick(R.id.img_cook)
    public void img_cook()
    {
        if(!flag_cook) {
            flag_cook=true;
            FavoritesOperations.addRoomFav(room_fav.get(COOK));
            img_cook.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
        }
        else
        {
            flag_cook=false;
            FavoritesOperations.deleteRoomFav(room_fav.get(COOK));
            img_cook.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.heart_outline_pink));
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




    @Nullable
    @OnClick({R.id.lin_comfort, R.id.lin_cook, R.id.lin_dinner_party})
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.lin_comfort:
                changeActiveStatus(txt_comfort_active_deactive);
                break;
            case R.id.lin_cook:
                changeActiveStatus(txt_cook_active_reset);
                break;
            case R.id.lin_dinner_party:
                changeActiveStatus(txt_dinner_active_reset);
                break;

        }
    }


    public void changeActiveStatus(TextView txt_select)
    {


        txt_comfort_active_deactive.setText("ACTIVE");
        txt_cook_active_reset.setText("ACTIVE");
        txt_dinner_active_reset.setText("ACTIVE");
        txt_select.setText("RESET");
        txt_select.setVisibility(View.VISIBLE);
    }
    public void setVisiblity()
    {

        txt_comfort_active_deactive.setVisibility(View.INVISIBLE);
        txt_dinner_active_reset.setVisibility(View.INVISIBLE);
        txt_cook_active_reset.setVisibility(View.INVISIBLE);

    }
    public void setFavoritesRoom(String scene_id)
    {

        switch (scene_id)
        {
            case "10":

                flag_comfort=true;
                img_comfort.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                break;
            case "14":
                flag_cook=true;
                img_cook.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                break;
            case "13":
                flag_dinner =true;
                img_dinner.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                break;

        }
    }


}