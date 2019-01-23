package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.exprFavorites;

import android.content.Context;
import android.os.Bundle;
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
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;


public class FavoritesExprFragment extends Fragment {
    public static FavoritesExprFragment newInstance() {
        return new FavoritesExprFragment();
    }

    private View v;


    String TAG="FilterFragment";
    Context mcontext;
    @BindView(R.id.txt_comfort_room_count)TextView txt_comfort_room_count;
    @BindView(R.id.txt_prepare_room_count)TextView txt_prepare_room_count;
    @BindView(R.id.txt_read_room_count)TextView txt_read_room_count;
    @BindView(R.id.txt_leave_room_count)TextView txt_leave_room_count;
    @BindView(R.id.txt_watch_room_count)TextView txt_watch_room_count;
    @BindView(R.id.txt_cook_room)TextView txt_cook_room;
    @BindView(R.id.txt_dinner_party_room)TextView txt_dinner_party_room;


    @BindView(R.id.img_comfort)ImageView img_comfort;
    @BindView(R.id.img_prepare)ImageView img_prepare;
    @BindView(R.id.img_watch_movie)ImageView img_watch_movie;
    @BindView(R.id.img_cook)ImageView img_cook;
    @BindView(R.id.img_read)ImageView img_read;
    @BindView(R.id.img_leave_home)ImageView img_leave_home;
    @BindView(R.id.img_dinner)ImageView img_dinner;
    @BindView(R.id.img_entertain)ImageView img_entertain;
    @BindView(R.id.img_mid_break)ImageView img_mid_break;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    String MID_DAY="mid_day",ENTERTAIN="entertain",COMFORT="comfort",LEAVE="leav",PREPARE="prepare",DINNER="dinner",COOK="cook",WATCHMOVIE="watchmovie",READ="read";

    HashMap<String,String> room_fav=new HashMap<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
        v=inflater.inflate(R.layout.fragment_favorites, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);
        room_fav.put(READ,"16");
        room_fav.put(PREPARE,"12");
        room_fav.put(WATCHMOVIE,"15");
        room_fav.put(LEAVE,"11");
        room_fav.put(COMFORT,"10");
        room_fav.put(COOK,"14");
        room_fav.put(DINNER,"13");
        room_fav.put(MID_DAY,"17");
        room_fav.put(ENTERTAIN,"18");

        if(App.getSocket()!=null)App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_favorites", new JSONObject()));

        setSubcriber();
        return v;
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
        App.setCurrentSubcriber(s);


    }



    public void setValues()
    {
        int entertain_count=0,mid_day_count=0,read_count=0,comfort_count=0,prepare_count=0,watch_count=0,cook_count=0,leave_count=0,dinnar_count=0;



        try {


            JSONArray jsonArray = App.getFavData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);


                if(!jsonObject.getString("sceneId").equals("")) {


                    Log.e("SCene_id",""+jsonObject.getString("sceneId"));
                    if (jsonObject.getString("sceneId").equals(room_fav.get(COMFORT))) {
                        comfort_count++;
                    } else if (jsonObject.getString("sceneId").equals(room_fav.get(PREPARE))) {
                        prepare_count++;
                    } else if (jsonObject.getString("sceneId").equals(room_fav.get(COOK))) {
                        cook_count++;
                    } else if (jsonObject.getString("sceneId").equals(room_fav.get(WATCHMOVIE))) {
                        watch_count++;
                    } else if (jsonObject.getString("sceneId").equals(room_fav.get(READ))) {
                        read_count++;
                    } else if (jsonObject.getString("sceneId").equals(room_fav.get(DINNER))) {
                        dinnar_count++;
                    } else if (jsonObject.getString("sceneId").equals(room_fav.get(LEAVE))) {
                        leave_count++;
                    }else if (jsonObject.getString("sceneId").equals(room_fav.get(ENTERTAIN))) {
                        entertain_count++;
                    }else if (jsonObject.getString("sceneId").equals(room_fav.get(MID_DAY))) {
                        mid_day_count++;
                    }
                }
            }
            txt_comfort_room_count.setText(""+comfort_count+" rooms");
            txt_prepare_room_count.setText(""+prepare_count+" rooms");
            txt_read_room_count.setText(""+read_count+" rooms");
            txt_leave_room_count.setText(""+leave_count+" rooms");
            txt_watch_room_count.setText(""+watch_count+" rooms");
            txt_cook_room.setText(""+cook_count+" rooms");
            txt_dinner_party_room.setText(""+dinnar_count+" rooms");

            if(comfort_count>0)img_comfort.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
            else img_comfort.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.heart_outline_pink));

            if(prepare_count>0)img_prepare.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
            else img_prepare.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.heart_outline_pink));

            if(watch_count>0)img_watch_movie.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
            else img_watch_movie.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.heart_outline_pink));

            if(leave_count>0)img_leave_home.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
            else img_leave_home.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.heart_outline_pink));

            if(dinnar_count>0)img_dinner.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
            else img_dinner.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.heart_outline_pink));

            if(read_count>0)img_read.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
            else img_read.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.heart_outline_pink));

            if(cook_count>0)img_cook.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
            else img_cook.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.heart_outline_pink));

            if(entertain_count>0)img_entertain.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
            else img_entertain.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.heart_outline_pink));

            if(mid_day_count>0)img_mid_break.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
            else img_mid_break.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.heart_outline_pink));
        }
        catch (Exception ex){Log.e("Error",ex.getMessage());}




    }





}
