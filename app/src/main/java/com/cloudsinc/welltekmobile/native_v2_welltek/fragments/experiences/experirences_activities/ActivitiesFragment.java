package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.experirences_activities;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FavoritesOperations;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by developers on 19/12/17.
 */

public class ActivitiesFragment extends Fragment implements View.OnClickListener{
    View v;
    Context mcontext;
    String[] all_rooms_name;
    String[] rooms_slected;
    final int color = 0x993684D8;
    final Drawable drawable = new ColorDrawable(color);
    String call_back;
    String TAG=ActivitiesFragment.this.getClass().getName();
    static int room_count=0;
    StringBuilder sb;
    boolean demo_scene=false;
    boolean demo_scene_visibility=false;
    LinearLayout act1,act2,act3,act4,act5;

    @BindView(R.id.img_comfort)ImageView img_comfort;
    @BindView(R.id.img_prepare_for_sleep)ImageView img_prepare_for_sleep;
    @BindView(R.id.img_energize)ImageView img_energize;
    @BindView(R.id.img_circadian_demo)ImageView img_circadian_demo;
    @BindView(R.id.img_relax)ImageView img_relax;
    @BindView(R.id.img_cooking)ImageView img_cooking;



    @BindView(R.id.txt_cooking_active_deactive)TextView txt_cooking_active_deactive;
    @BindView(R.id.txt_circadian_demo_active_deactive)TextView txt_circadian_demo_active_deactive;
    @BindView(R.id.txt_prepare_active_deactive)TextView txt_prepare_active_deactive;
    @BindView(R.id.txt_egerise_active_deactive)TextView txt_egerise_active_deactive;
    @BindView(R.id.txt_relax_active_deactive)TextView txt_relax_active_deactive;
    @BindView(R.id.txt_comfort_active_deactive)TextView txt_comfort_active_deactive;
    @BindView(R.id.txt_dawn_demo_active_deactive)TextView txt_dawn_active_deactive;
    @BindView(R.id.txt_display_demo_active_deactive)TextView txt_display_active_deactive; // display demo
    @BindView(R.id.txt_sleep_demo_active_deactive)TextView txt_sleep_active_deactive;


    @BindView(R.id.rel_prepare)RelativeLayout rel_prepare;
    @BindView(R.id.rel_cooking)RelativeLayout rel_cooking;
    @BindView(R.id.rel_circadian_demo)RelativeLayout rel_circadian_demo;
    @BindView(R.id.rel_dawn_demo)RelativeLayout demo_rel_dawn_comfort;
    @BindView(R.id.rel_display_demo)RelativeLayout demo_rel_display_comfort;
    @BindView(R.id.rel_sleep_demo)RelativeLayout demo_rel__sleep_comfort;
    @BindView(R.id.rel_energise)RelativeLayout rel_energise;
    @BindView(R.id.rel_relax)RelativeLayout rel_relax;
    @BindView(R.id.rel_comfort)RelativeLayout rel_comfort;
    //@BindView(R.id.rel_dawn_demo)RelativeLayout rel_dawn_comfort;
  //  @BindView(R.id.rel_sleep_demo)RelativeLayout rel_sleep_comfort;

    @BindView(R.id.lyt_dawn_demo)RelativeLayout lyt_dawn_demo;
    @BindView(R.id.lyt_display_demo)RelativeLayout lyt_display_demo;
    @BindView(R.id.lyt_sleep_demo)RelativeLayout lyt_sleep_demo;
    @BindView(R.id.lyt_circadian_demo)RelativeLayout lyt_circadian_demo;


    @BindView(R.id.lin_comfort_svg)LinearLayout lin_comfort_svg;
    @BindView(R.id.lin_cook_svg)LinearLayout lin_cook_svg;
    @BindView(R.id.lin_eng_svg)LinearLayout lin_eng_svg;
    @BindView(R.id.lin_relax_svg)LinearLayout lin_relax_svg;
    @BindView(R.id.lin_sleep_svg)LinearLayout lin_sleep_svg;
    @BindView(R.id.lin_circadian_demo)LinearLayout lin_circadian_demo;
    @BindView(R.id.lin_dawn_demo_svg)LinearLayout lin_dawn_demo_svg;
    @BindView(R.id.lin_display_demo_svg)LinearLayout lin_display_demo_svg;
    @BindView(R.id.lin_sleep_demo_svg)LinearLayout lin_sleep_demo_svg;

    @BindView(R.id.txt_comfort_rooms)TextView txt_comfort_rooms;
    @BindView(R.id.txt_prepare_for_sleep_rooms)TextView txt_prepare_for_sleep_rooms;
    @BindView(R.id.txt_energise_rooms)TextView txt_energise_rooms;
    @BindView(R.id.txt_relax_rooms)TextView txt_relax_rooms;
    @BindView(R.id.txt_cooking_rooms)TextView txt_cooking_rooms;
    @BindView(R.id.txt_disp_rooms)TextView txt_disp_rooms;






    static String COMFORT="1",SLEEP="6",RELAX="2",COOK="7",CIRCADIAN_DEMO="A",ENERGISE="3",DAWN_DEMO="B",SLEEP_DEMO="C";




    public static ActivitiesFragment newInstance() {
        return new ActivitiesFragment();
    }



    boolean flag_comfort=false,flag_prepare_for_sleep=false,flag_energize=false,flag_relax=false,flag_cooking=false,flag_circadian_demo=false,flag_dawn_demo=false,flag_sleep_demo=false;

    private Tracker mTracker;

    boolean darwin_flag=false;
    private Observable<String> mobservable;
    private Observer<String> myObserver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_experiences_activities, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);
        if (!checkConfiguration()) {
            Logs.info(TAG,"Error in Mobile Activites Fragment");
        }
        App application = (App)getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();
        if(!new PrefManager(mcontext).getValue("demo_scene").equals("false")) {
            new PrefManager(mcontext).setValue("demo_scene", "true");
        }
        App.setCallfrom("ActivitiesFragment");
        if(new PrefManager(mcontext).getValue("demo_scene").equals("true")) {
            lyt_sleep_demo.setVisibility(View.VISIBLE);
            lyt_dawn_demo.setVisibility(View.VISIBLE);
            lyt_circadian_demo.setVisibility(View.VISIBLE);
            lyt_display_demo.setVisibility(View.VISIBLE); //dispaly demo
        }
        if(App.getTemp_bundle()!=null){
            if (App.getTemp_bundle().containsKey("Demo_scene")){
                demo_scene=true;
                demo_scene_visibility=true;
            }
        }

        if(App.getSocket()!=null) {
            try {
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_favorites", new JSONObject()));
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_rooms_by_device", new JSONObject().put("type", "Lighting")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setCount();
        setDefaultState();
        setSceneActiveDeavtive();
        setSceneFav();

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


                 Logs.info(TAG+"_Response_String",string);
                setCount();
                setDefaultState();
                setSceneActiveDeavtive();
                setSceneFav();


            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);


    }

    @OnClick(R.id.img_comfort)
    public void img_comfort()
    {
        if(!flag_comfort) {

            FavoritesOperations.addSceneFav(COMFORT);
        }else
        {
            FavoritesOperations.deleteFav(FavoritesOperations.getSceneFavId(COMFORT));
        }
    }

    @OnClick(R.id.img_prepare_for_sleep)
    public void img_prepare_for_sleep()
    {
        if(!flag_prepare_for_sleep) {

            FavoritesOperations.addSceneFav(SLEEP);
        }
        else
        {
            FavoritesOperations.deleteFav(FavoritesOperations.getSceneFavId(SLEEP));
        }
    }

    @OnClick(R.id.img_energize)
    public void img_energize()
    {
        if(!flag_energize) {

            FavoritesOperations.addSceneFav(ENERGISE);
        }
        else
        {

            FavoritesOperations.deleteFav(FavoritesOperations.getSceneFavId(ENERGISE));
        }
    }

    @OnClick(R.id.img_relax)
    public void img_relax()
    {
        if(!flag_relax) {

            FavoritesOperations.addSceneFav(RELAX);
        }
        else
        {

            FavoritesOperations.deleteFav(FavoritesOperations.getSceneFavId(RELAX));
        }
    }

    @OnClick(R.id.img_cooking)
    public void img_cooking()
    {
        if(!flag_cooking) {

            FavoritesOperations.addSceneFav(COOK);
        }
        else
        {

            FavoritesOperations.deleteFav(FavoritesOperations.getSceneFavId(COOK));

        }
    }

 @OnClick(R.id.img_circadian_demo)
    public void img_circadian_demo()
    {
        if(!flag_circadian_demo) {

            FavoritesOperations.addSceneFav(CIRCADIAN_DEMO);
        }
        else
        {

            FavoritesOperations.deleteFav(FavoritesOperations.getSceneFavId(CIRCADIAN_DEMO));

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white);


    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }






    public int roomcount(Integer senceid)
    {
        int count=0;

        try
        {
            JSONArray jsonArray=App.getRoomDeviceData().getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getInt("CML_SCENE_ID")==senceid)
                {
                     Logs.info(TAG+"_aaaaaaaaaaaa",""+jsonObject);

                    ++count;

                }

            }
            Log.i("Countttttttttttttttt",""+count);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        return count;
    }
    public void setCount(){
        try {
            Integer a[] = {1, 6, 3, 2, 7,8};
            TextView[] txt_count = {txt_comfort_rooms, txt_prepare_for_sleep_rooms, txt_energise_rooms, txt_relax_rooms, txt_cooking_rooms,txt_disp_rooms};
            for (int i = 0; i < a.length; i++) {
                int room_count = 0;
                room_count = roomcount(a[i]);
                 Logs.info(TAG+"_aaaaaaaaaaaa", "Scene_id" + a[i] + "count" + room_count);
                if (room_count == 0) {
                    txt_count[i].setText("");
                    if(a[i]==7)txt_count[i].setText("");
                } else if (room_count == 1) {
                    txt_count[i].setText(roomName(a[i]));
                } else  if (room_count == App.getRoomDeviceData().getJSONArray("data").length()) {

                    if(a[i]==1)  txt_count[i].setText("All rooms");
                    else    txt_count[i].setText("" + room_count + " rooms");

                }else {
                    txt_count[i].setText("" + room_count + " rooms");
                }
            }
        }
        catch (Exception ex)
        {
             Logs.info(TAG+"_RoomCountError",""+ex.getMessage());
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

    public void setSceneActiveDeavtive()
    {

        try {

            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            darwin_flag=false;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.has("CML_SCENE_ID")) setRoomSelection(jsonObject.getString("CML_SCENE_ID"));
            }
            if(darwin_flag)txt_comfort_active_deactive.setText("ACTIVE");
            else txt_comfort_active_deactive.setText("RESET");
        }
        catch (Exception ex)
        {
             Logs.info(TAG+"_WholeHomeSceneError",""+ex.getMessage());
        }

    }
    public void setRoomSelection(String scene_id)
    {
         Logs.info(TAG+"_scene_id",""+scene_id);


        switch (scene_id)
        {
            case "1":
                //darwin comfort
                darwin_flag=true;
                setVisiblity(txt_comfort_active_deactive,rel_comfort,lin_comfort_svg);

                break;
            case "6":
                //prepare for sleep
                setVisiblity(txt_prepare_active_deactive,rel_prepare,lin_sleep_svg);

                break;
            case "3":
                //energize
                setVisiblity(txt_egerise_active_deactive,rel_energise,lin_eng_svg);

                break;
            case "2":
                //relax
                setVisiblity(txt_relax_active_deactive,rel_relax,lin_relax_svg);

                break;
            case "7":
                //cooking
                setVisiblity(txt_cooking_active_deactive,rel_cooking,lin_cook_svg);

                break;
            case "A":
                //circadian
                setVisiblity(txt_circadian_demo_active_deactive,rel_circadian_demo,lin_circadian_demo);

                break;
                case "B":
                setVisiblity(txt_dawn_active_deactive,demo_rel_dawn_comfort,lin_dawn_demo_svg);

                break;
                case "C":
                setVisiblity(txt_sleep_active_deactive,demo_rel__sleep_comfort,lin_sleep_demo_svg);

                break;
            case "8":
                setVisiblity(txt_display_active_deactive,demo_rel_display_comfort,lin_display_demo_svg);
                break;


        }

    }


    public void setSceneFav()
    {

        try {

            JSONArray jsonArray = App.getFavData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(!jsonObject.has("roomId")&&!jsonObject.has("deviceId"))
                {
                    makeSceneFav(jsonObject.getString("sceneId"));
                }
            }
        }
        catch (Exception ex)
        {
             Logs.info(TAG+"_WholeHomeSceneError",""+ex.getMessage());
        }

    }


    public void makeSceneFav(String scene_id)
    {
        switch (scene_id)
        {
            case "1":
                flag_comfort=true;
                img_comfort.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                break;
            case "2":
                flag_relax=true;
                img_relax.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                break;
            case "6":
                flag_prepare_for_sleep=true;
                img_prepare_for_sleep.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                break;
            case "7":
                flag_cooking=true;
                img_cooking.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                break;
            case "3":
                flag_energize=true;
                img_energize.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                break;
          /*  case "A":
                flag_circadian_demo=true;
                img_circadian_demo.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                break;*/

        }
    }




    public void setVisiblity(TextView txt_select,RelativeLayout rel_select,LinearLayout lin_svg)
    {



        txt_select.setText("ACTIVE");
        txt_select.setVisibility(View.VISIBLE);
        rel_select.setVisibility(View.VISIBLE);
        lin_svg.setAlpha(1.0f);

    }


    @Nullable
    @OnClick({R.id.lin_prepare, R.id.lin_cooking, R.id.lin_energise, R.id.lin_relax,R.id.lin_comfort,R.id.lin_circadian_demo,R.id.lin_sleep_demo,R.id.lin_dawn_demo,R.id.lin_display})
    public void onClick(View v)
    {

        App.setRoom_ids(new ArrayList<String>());
        App.setDeselected_room_ids(new ArrayList<String>());
        Fragment mfragment = ExperienceActivityControl.newInstance();
        Bundle bundle=new Bundle();
        switch (v.getId())
        {
            case R.id.lin_energise:
                bundle.putString("scene_id","3");
                bundle.putString("scene_title","Energise");
                bundle.putString("img_scenes","eng_two");

                break;
            case R.id.lin_cooking:
                bundle.putString("scene_id","7");
                bundle.putString("scene_title","Cooking");
                bundle.putString("img_scenes","cook_two");
                break;
            case R.id.lin_prepare:
                bundle.putString("scene_id","6");
                bundle.putString("scene_title","Prepare for Sleep");
                bundle.putString("img_scenes","sleep_four");
                break;
            case R.id.lin_relax:

                bundle.putString("img_scenes","relax_two");
                bundle.putString("scene_id","2");
                bundle.putString("scene_title","Relax");
                break;
            case R.id.lin_comfort:

                bundle.putString("img_scenes","darwin_two");
                bundle.putString("scene_id","1");
                bundle.putString("scene_title","Darwin Comfort");


                break;
            case R.id.lin_circadian_demo:

                bundle.putString("img_scenes","darwin_two");
                bundle.putString("scene_id","A");
                bundle.putString("scene_title","Circadian Demo");
                break;
                 case R.id.lin_dawn_demo:

                bundle.putString("img_scenes","demo_dawn_four");
                bundle.putString("scene_id","B");
                bundle.putString("scene_title","Dawn Demo");
                break;
                 case R.id.lin_sleep_demo:

                bundle.putString("img_scenes","sleep_four");
                bundle.putString("scene_id","C");
                bundle.putString("scene_title","Sleep Demo");
                break;

            case R.id.lin_display:

                bundle.putString("img_scenes","display_demo_four");
                bundle.putString("scene_id","8");
                bundle.putString("scene_title","Display Demo");
                break;

        }

        mfragment.setArguments(bundle);
        FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
        transaction1.replace(R.id.frm_main_container, mfragment);
        transaction1.addToBackStack("fragmentShowControl");
        transaction1.commit();
    }

    public void setDefaultState()
    {


        flag_energize=false;flag_cooking=false;flag_relax=false;flag_comfort=false;flag_prepare_for_sleep=false;flag_circadian_demo=false;
        img_comfort.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
        img_cooking.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
        img_energize.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
        img_circadian_demo.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
        img_prepare_for_sleep.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
        img_relax.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));

        txt_prepare_active_deactive.setVisibility(View.INVISIBLE);
        txt_egerise_active_deactive.setVisibility(View.INVISIBLE);
        txt_relax_active_deactive.setVisibility(View.INVISIBLE);
        txt_cooking_active_deactive.setVisibility(View.INVISIBLE);
        txt_circadian_demo_active_deactive.setVisibility(View.INVISIBLE);
        txt_dawn_active_deactive.setVisibility(View.INVISIBLE);
        txt_sleep_active_deactive.setVisibility(View.INVISIBLE);

        rel_comfort.setVisibility(View.GONE);
        rel_cooking.setVisibility(View.GONE);
        rel_circadian_demo.setVisibility(View.GONE);
        rel_energise.setVisibility(View.GONE);
        rel_prepare.setVisibility(View.GONE);
        rel_relax.setVisibility(View.GONE);

        lin_comfort_svg.setAlpha(0.5f);
        lin_cook_svg.setAlpha(0.5f);
        lin_eng_svg.setAlpha(0.5f);
        lin_relax_svg.setAlpha(0.5f);
        lin_sleep_demo_svg.setAlpha(0.5f);

        lin_sleep_demo_svg.setAlpha(0.5f);
        lin_dawn_demo_svg.setAlpha(0.5f);

        demo_rel_dawn_comfort.setVisibility(View.GONE);
        demo_rel__sleep_comfort.setVisibility(View.GONE);
    }




    public void activeScene(String scene_id)
    {
        try {
            JSONObject jsonObject = new JSONObject();
           // jsonObject.put("selectedArr", getSelectedRoomArray());
           // jsonObject.put("deselectedArr", getDeselectedRoomArray());
            jsonObject.put("scene", scene_id);
            JSONObject resultJsonObject = new JSONObject();
            resultJsonObject.put("data", jsonObject);
            resultJsonObject.put("type", "activate_scene");
            App.getSocket().emit("action", resultJsonObject);
        } catch (Exception ex) {
             Logs.info(TAG+"_Error", "" + ex.getMessage());
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }
    private void sendScreenImageName() {
        String name = "Mobile Scene Activities Fragment";
        String username=new PrefManager(mcontext).getValue("User_Id");

        // [START screen_view_hit]
        Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName(""+username+"~" + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
    }

    private boolean checkConfiguration() {
        XmlResourceParser parser = getResources().getXml(R.xml.global_tracker);

        boolean foundTag = false;
        try {
            while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlResourceParser.START_TAG) {
                    String tagName = parser.getName();
                    String nameAttr = parser.getAttributeValue(null, "name");

                    foundTag = "string".equals(tagName) && "ga_trackingId".equals(nameAttr);
                }

                if (parser.getEventType() == XmlResourceParser.TEXT) {
                    if (foundTag && parser.getText().contains("REPLACE_ME")) {
                        return false;
                    }
                }

                parser.next();
            }
        } catch (Exception e) {
            Log.w(TAG, "checkConfiguration", e);
            return false;
        }

        return true;
    }
}
