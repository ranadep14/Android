package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.experirences_activities;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.experirences_activities.ExperienceActivityControl;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
public class Activities_FavoritesFragment extends Fragment {
    private View lyt;
    public static Activities_FavoritesFragment newInstance() {
        return new Activities_FavoritesFragment();
    }
    @BindView(R.id.txt_cooking_active_deactive)TextView txt_cooking_active_deactive;
    @BindView(R.id.txt_prepare_active_deactive)TextView txt_prepare_active_deactive;
    @BindView(R.id.txt_egerise_active_deactive)TextView txt_egerise_active_deactive;
    @BindView(R.id.txt_relax_active_deactive)TextView txt_relax_active_deactive;
    @BindView(R.id.txt_comfort_active_deactive)TextView txt_comfort_active_deactive;
    @BindView(R.id.demo_txt_comfort_active_deactive)TextView demo_txt_comfort_active_deactive;
    @BindView(R.id.demo_txt_dawn_active_deactive)TextView demo_txt_dawn_active_deactive;
    @BindView(R.id.demo_txt_sleep_active_deactive)TextView demo_txt_sleep_active_deactive;


    @BindView(R.id.rel_prepare)RelativeLayout rel_prepare;
    @BindView(R.id.rel_cooking)RelativeLayout rel_cooking;
    @BindView(R.id.rel_energise)RelativeLayout rel_energise;
    @BindView(R.id.rel_relax)RelativeLayout rel_relax;
    @BindView(R.id.rel_comfort)RelativeLayout rel_comfort;
    @BindView(R.id.demo_rel_comfort)RelativeLayout demo_rel_comfort;
    @BindView(R.id.demo_rel_sleep)RelativeLayout demo_rel_dawn_comfort;
    @BindView(R.id.demo_rel_dawn)RelativeLayout demo_rel__sleep_comfort;

    @BindView(R.id.demo_scene_lyt)LinearLayout demo_scene_lyt;
    @BindView(R.id.lyt_dawn_demo)RelativeLayout lyt_dawn_demo;
    @BindView(R.id.lyt_sleep_demo)RelativeLayout lyt_sleep_demo;
    @BindView(R.id.lyt_circadian_demo)RelativeLayout lyt_circadian_demo;


    @BindView(R.id.cir_cnt)TextView cir_cnt;
    @BindView(R.id.demo_cir_cnt)TextView demo_cir_cnt;
    @BindView(R.id.sleep_cnt)TextView sleep_cnt;
    @BindView(R.id.eng_cnt)TextView eng_cnt;
    @BindView(R.id.relax_cnt)TextView rlax_cnt;
    @BindView(R.id.cooking_cnt)TextView cooking_cnt;

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
        v=inflater.inflate(R.layout.fragment_activites_favorites, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);
        if(!new PrefManager(mcontext).getValue("demo_scene").equals("false")) {
            new PrefManager(mcontext).setValue("demo_scene", "true");
        }

        lyt= v.findViewById(R.id.lytmainfont);
        final Typeface mFont = Typeface.createFromAsset(v.getContext().getAssets(),
                "fonts/SF-UI-Text-Regular.otf");
        final ViewGroup mContainer = (ViewGroup) lyt.getRootView();
        FontUtility.setAppFont(mContainer, mFont);
        room_fav.put(READ,"16");
        room_fav.put(PREPARE,"12");
        room_fav.put(WATCHMOVIE,"15");
        room_fav.put(LEAVE,"11");
        room_fav.put(COMFORT,"10");
        room_fav.put(DINNER,"13");

        if(new PrefManager(mcontext).getValue("demo_scene").equals("true")) {

            demo_scene_lyt.setVisibility(View.VISIBLE);
            lyt_sleep_demo.setVisibility(View.VISIBLE);
            lyt_dawn_demo.setVisibility(View.VISIBLE);
            lyt_circadian_demo.setVisibility(View.VISIBLE);
        }
            if(App.getSocket()!=null) {
        try{
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_rooms", new JSONObject().put("type","Lighting")));
        } catch (JSONException e) {
            e.printStackTrace();
        } }
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
                setDefaultState();
                setFavoritesRoom();
                setCount();
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);
    }
    public void setFavoritesRoom()
    {
        try {
            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.has("CML_SCENE_ID")) setRoomSelection(jsonObject.getString("CML_SCENE_ID"));
            }
        }
        catch (Exception ex)
        {
            Log.e("WholeHomeSceneError",""+ex.getMessage());
        }
    }
    public void setRoomSelection(String scene_id)
    {
        switch (scene_id)
        {
            case "1":
                //darwin comfort
                setVisiblity(txt_comfort_active_deactive,rel_comfort);
                break;
            case "6":
                //prepare for sleep
                setVisiblity(txt_prepare_active_deactive,rel_prepare);
                break;
            case "3":
                //energize
                setVisiblity(txt_egerise_active_deactive,rel_energise);
                break;
            case "2":
                //relax
                setVisiblity(txt_relax_active_deactive,rel_relax);
                break;
            case "7":
                //cooking
                setVisiblity(txt_cooking_active_deactive,rel_cooking);
                break;
            case "A":
                //Circadian Demo
                setVisiblity(demo_txt_comfort_active_deactive,demo_rel_comfort);
                break;
            case "B":
                //Circadian Demo
                setVisiblity(demo_txt_dawn_active_deactive,demo_rel__sleep_comfort);
                break;
             case "C":
                //Circadian Demo
                setVisiblity(demo_txt_sleep_active_deactive,demo_rel_dawn_comfort);
                break;
        }
    }
    @Nullable
    @OnClick({R.id.lin_prepare, R.id.lin_cooking, R.id.lin_energise, R.id.lin_relax,R.id.lin_comfort,R.id.demo_lin_comfort,R.id.demo_lin_sleep,R.id.lin_dawn})
    public void onClick(View v)
    {
        App.setRoom_ids(new ArrayList<String>());
        App.setDeselected_room_ids(new ArrayList<String>());
       // ExperiencesRoomSelectionDialog experiencesRoomSelectionDialog=new ExperiencesRoomSelectionDialog();
       ExperienceActivityControl experiencesRoomSelectionDialog=new ExperienceActivityControl();
        Bundle bundle=new Bundle();
        switch (v.getId())
        {
            case R.id.lin_energise:
                experiencesRoomSelectionDialog.show(getFragmentManager(),"RoomDialog");
                bundle.putString("scene_id","3");
                bundle.putString("scene_title","Energise");
                bundle.putString("img_scenes","eng_two");
                experiencesRoomSelectionDialog.setArguments(bundle);
                break;
            case R.id.lin_cooking:
                experiencesRoomSelectionDialog.show(getFragmentManager(),"RoomDialog");
                bundle.putString("scene_id","7");
                bundle.putString("scene_title","Cooking");
                bundle.putString("img_scenes","cook_two");
                experiencesRoomSelectionDialog.setArguments(bundle);
                break;
            case R.id.lin_prepare:
                experiencesRoomSelectionDialog.show(getFragmentManager(),"RoomDialog");
                bundle.putString("scene_id","6");
                bundle.putString("scene_title","Prepare for Sleep");
                experiencesRoomSelectionDialog.setArguments(bundle);
                bundle.putString("img_scenes","sleep_four");
                break;
            case R.id.lin_relax:
                experiencesRoomSelectionDialog.show(getFragmentManager(),"RoomDialog");
                bundle.putString("img_scenes","relax_two");
                bundle.putString("scene_id","2");
                bundle.putString("scene_title","Relax");
                experiencesRoomSelectionDialog.setArguments(bundle);
                break;
            case R.id.lin_comfort:
                experiencesRoomSelectionDialog.show(getFragmentManager(),"RoomDialog");
                bundle.putString("img_scenes","darwin_two");
                bundle.putString("scene_id","1");
                bundle.putString("scene_title","Darwin Comfort");
                experiencesRoomSelectionDialog.setArguments(bundle);
                break;
            case R.id.demo_lin_comfort:
                experiencesRoomSelectionDialog.show(getFragmentManager(),"RoomDialog");
                bundle.putString("img_scenes","darwin_two");
                bundle.putString("scene_id","A");
                bundle.putString("scene_title","Circadian Demo");
                experiencesRoomSelectionDialog.setArguments(bundle);
                break;
             case R.id.lin_dawn:
                experiencesRoomSelectionDialog.show(getFragmentManager(),"RoomDialog");
                bundle.putString("img_scenes","demo_dawn_four");
                bundle.putString("scene_id","B");
                bundle.putString("scene_title","Dawn Demo");
                experiencesRoomSelectionDialog.setArguments(bundle);
                break;

            case R.id.demo_lin_sleep:
                experiencesRoomSelectionDialog.show(getFragmentManager(),"RoomDialog");
                bundle.putString("scene_id","C");
                bundle.putString("scene_title","Sleep Demo");
                experiencesRoomSelectionDialog.setArguments(bundle);
                bundle.putString("img_scenes","sleep_demo_four");
                break;
        }
    }
    public void changeActiveStatus(TextView txt_select)
    {
        txt_comfort_active_deactive.setText("RESET");
        txt_prepare_active_deactive.setText("ACTIVE");
        txt_cooking_active_deactive.setText("ACTIVE");
        txt_egerise_active_deactive.setText("ACTIVE");
        txt_relax_active_deactive.setText("ACTIVE");
        txt_select.setText("RESET");
        txt_select.setVisibility(View.VISIBLE);
    }
    public void setVisiblity(TextView txt_select,RelativeLayout rel_select)
    {
        txt_select.setText("ACTIVE");
        txt_select.setVisibility(View.VISIBLE);
        rel_select.setVisibility(View.VISIBLE);
    }
    public void setDefaultState()
    {
        txt_prepare_active_deactive.setVisibility(View.INVISIBLE);
        txt_egerise_active_deactive.setVisibility(View.INVISIBLE);
        txt_relax_active_deactive.setVisibility(View.INVISIBLE);
        txt_cooking_active_deactive.setVisibility(View.INVISIBLE);
        demo_txt_dawn_active_deactive.setVisibility(View.INVISIBLE);
        demo_txt_sleep_active_deactive.setVisibility(View.INVISIBLE);


        rel_comfort.setVisibility(View.GONE);
        demo_rel_comfort.setVisibility(View.GONE);
        demo_rel_dawn_comfort.setVisibility(View.GONE);
        demo_rel__sleep_comfort.setVisibility(View.GONE);
        rel_cooking.setVisibility(View.GONE);
        rel_energise.setVisibility(View.GONE);
        rel_prepare.setVisibility(View.GONE);
        rel_relax.setVisibility(View.GONE);
    }
    public int roomcount(Integer senceid)
    {
        int count=0;
        try
        {
            JSONArray jsonArray=App.getRoomData().getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getInt("CML_SCENE_ID")==senceid)
                {
                    Log.e("aaaaaaaaaaaa",""+jsonObject);
                    count++;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return count;
    }


    public void setCount(){
        try {
            Integer a[] = {1, 6, 3, 2, 7};
            TextView[] txt_count = {cir_cnt,sleep_cnt,eng_cnt,rlax_cnt,cooking_cnt};
            for (int i = 0; i < a.length; i++) {
                int room_count = 0;
                room_count = roomcount(a[i]);
                Logs.info("_aaaaaaaaaaaa", "Scene_id" + a[i] + "count" + room_count);
                if (room_count == 0) {
                    txt_count[i].setText("");
                    if(a[i]==7)txt_count[i].setText(""+mcontext.getResources().getString(R.string.kitchen_only));
                } else if (room_count == 1) {
                    txt_count[i].setText(roomName(a[i]));
                } else  if (  (a[i] == 1) && room_count == App.getRoomDeviceData().getJSONArray("data").length()) {

                    if (a[i] == 1) txt_count[i].setText("All rooms");
                    else txt_count[i].setText("" + room_count + " rooms");

                }else {
                    txt_count[i].setText("" + room_count + " rooms");
                }

              /*  if(a[i]==1){
                    if (room_count == 0) {
                        txt_count[i].setText("");
                    }
                   else if (room_count == 1) {
                        txt_count[i].setText(roomName(a[i]));
                    }
                    else if (  room_count > 1 && room_count == App.getRoomDeviceData().getJSONArray("data").length()) {
                        txt_count[i].setText("All rooms");
                    }
                    else {
                        txt_count[i].setText("" + room_count + " rooms");
                    }
                }
                else {
                    if (room_count == 0) {
                        txt_count[i].setText("");
                        if(a[i]==7)txt_count[i].setText(""+mcontext.getResources().getString(R.string.kitchen_only));
                    } else if (room_count == 1) {
                        txt_count[i].setText(roomName(a[i]));
                    }
                    else {
                        txt_count[i].setText("" + room_count + " rooms");
                    }
                }*/



            }
        }
        catch (Exception ex)
        {
            Logs.info("_RoomCountError",""+ex.getMessage());
        }
    }

    public String roomName(Integer sid)
    {
        String r_name="";
        try
        {
            JSONArray jsonArray=App.getRoomData().getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getInt("CML_SCENE_ID")==sid)
                {
                    r_name=jsonObject.getString("CML_TITLE");
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return r_name;
    }
}