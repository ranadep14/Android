package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.experirences_activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.ActivitesRoomListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.ExperienceMainFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.ExperienceMainFragmentPortrait;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Room;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FavoritesOperations;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.ExperienceMainFragmentPortrait.experience_tabs;

/**
 * Created by developers on 20/12/17.
 */

public class ExperienceActivityControl extends DialogFragment {
    String str_action="";
    private View v;
    Context mcontext;
    Observable<String> mobservable;
    Observer<String> myObserver;
    private Tracker mTracker;


    Observable<String> activeobservable;
    Observer<String> activeObserver;


    ArrayList<Room> arr_room_list=new ArrayList<>();
    ArrayList<String> arr_room_list_for_deslect=new ArrayList<>();
    String scene_img="";
    LinearLayoutManager mLayoutManager;
    @BindView(R.id.list_add_rooms)RecyclerView listview;
    @BindView(R.id.no_text)
    TextView notext;
    @BindView(R.id.desc_text) TextView desc_text;
    @BindView(R.id.txt_scene_title) TextView txt_scene_title;

    @BindView(R.id.rel_loading)RelativeLayout rel_loading;
    @BindView(R.id.divider)View divider;

    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2) ImageView img2;
    @BindView(R.id.img3) ImageView img3;
    @BindView(R.id.img4) ImageView img4;
    @BindView(R.id.img5) ImageView img5;
    @BindView(R.id.img6) ImageView img6;
    @BindView(R.id.img7) ImageView img7;
    @Nullable
    @BindView(R.id.img_back)ImageView img_back;



    String TAG=ExperienceActivityControl.this.getClass().getSimpleName();
    @BindView(R.id.btn_add) Button btn_add;
    ActivitesRoomListAdapter customRoomItemAdapter;
    @Nullable
    @BindView(R.id.txt_fragment_title) TextView txt_fragment_title;
    @BindView(R.id.lin_main)
    LinearLayout lin_main;
    JSONArray roomTypeJsonArray;
    public static ExperienceActivityControl newInstance() {
        return new ExperienceActivityControl();
    }
    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_experiences_activities_control, container, false);
        UserInteractionSleep.siboSleepChecking(v.getContext());
        if(App.isOrientationFlag()==true){
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
         /*  int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
           int height = getResources().getDimensionPixelSize(R.dimen.popup_height);*/

            int width = 1500;
            int height = 700;


            getDialog().getWindow().setLayout(width, height);
        }
        mcontext=v.getContext();
        ButterKnife.bind(this,v);

        if (!checkConfiguration()) {
            Logs.info(TAG,"Error in Activities SelectRoom ListFragrgment");
        }
        App application = (App)getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();
        lin_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                UserInteractionSleep.userInteract(mcontext);
                return false;
            }
        });

        if(!App.isOrientationFlag()) {
            img_back.setImageDrawable(mcontext.getDrawable(R.drawable.ic_left_arrow));
            txt_fragment_title.setText("" + getArguments().getString("scene_title"));
        }

        txt_scene_title.setText("Activities: "+""+getArguments().getString("scene_title"));
        scene_img=getArguments().getString("img_scenes");
        if(scene_img=="darwin_two"){
            desc_text.setText("An ambiance designed to make you feel Comfort mood");

            defaultvisibility();
            img7.setVisibility(View.VISIBLE);
            img6.setVisibility(View.VISIBLE);

        }
        else if(scene_img=="eng_two") {
            desc_text.setText("An ambiance designed to make you feel more energised");

            defaultvisibility();
            img1.setVisibility(View.VISIBLE);
            img6.setVisibility(View.VISIBLE);
        }
        else if(scene_img=="cook_two") {
            desc_text.setText("An ambiance designed to create an cooking Environment");

            defaultvisibility();
            img1.setVisibility(View.VISIBLE);
            img5.setVisibility(View.VISIBLE);

        }
        else if(scene_img=="relax_two") {
            desc_text.setText("An ambiance designed to make you feel Relaxed");

            defaultvisibility();
            img1.setVisibility(View.VISIBLE);
            img6.setVisibility(View.VISIBLE);
        }

        else if(scene_img=="sleep_four") {
            desc_text.setText("An ambiance designed to take you in sweet dreams");

            defaultvisibility();
            img1.setVisibility(View.VISIBLE);
            img4.setVisibility(View.VISIBLE);
            img3.setVisibility(View.VISIBLE);
            img2.setVisibility(View.VISIBLE);
        }

        else if(scene_img=="demo_dawn_four") {
            desc_text.setText("An ambiance designed to make you feel Comfort mood");

            defaultvisibility();
            img1.setVisibility(View.VISIBLE);
            img4.setVisibility(View.VISIBLE);
            img3.setVisibility(View.VISIBLE);
            img2.setVisibility(View.VISIBLE);
        }

        else if(scene_img=="sleep_demo_four") {
            desc_text.setText("An ambiance designed to take you in sweet dreams");
            defaultvisibility();
            img1.setVisibility(View.VISIBLE);
            img4.setVisibility(View.VISIBLE);
            img3.setVisibility(View.VISIBLE);
            img2.setVisibility(View.VISIBLE);
        }

        // Dispaly demo
        else if(scene_img=="display_demo_four") {
            desc_text.setText("An ambiance designed to make you feel Comfort mood");
            defaultvisibility();
            img1.setVisibility(View.VISIBLE);
            img4.setVisibility(View.VISIBLE);
            img3.setVisibility(View.VISIBLE);
            img2.setVisibility(View.VISIBLE);
        }


        updateUI("rooms_by_device");

        setSubcriber();
        setActiveSubcriber();
        return v;
    }



    @Override
    public void onResume() {
        super.onResume();
        UserInteractionSleep.siboSleepChecking(mcontext);
        if(!App.isOrientationFlag()) {
            experience_tabs.setVisibility(View.GONE);
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow);
        }

    }



    @Override
    public void onStop() {
        super.onStop();
        UserInteractionSleep.stopHandler();
        if (!App.isOrientationFlag()) {
            experience_tabs.setVisibility(View.VISIBLE);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
    }

    public void setAddVisibleHide()
    {

        Logs.info(TAG+"_WHEREIM","InAddVISIBLEHide");
        if(App.getRoom_ids().size()>0 )
        {
            btn_add.setAlpha(1f);
            btn_add.setEnabled(true);
        }
        else
        {
            btn_add.setAlpha(0.5f);
            btn_add.setEnabled(false);
        }
        //   }

    }


    @OnClick(R.id.btn_add)
    public void btn_add() {

            try {

                if(App.getRoom_ids().contains("selected_all") && getArguments().getString("scene_id") != "7")
                {
                    try {

                        JSONObject dataJson = new JSONObject();
                        dataJson.put("scene", ""+getArguments().getString("scene_id"));
                        dataJson.put("home", "all");


                        JSONObject obj = new JSONObject();
                        obj.put("data", dataJson);
                        obj.put("type", "trigger_scene");
                        Logs.info(TAG + "_scene_light", "" + obj);
                        App.getSocket().emit("action", obj);

                    } catch (Exception ex) {
                        Log.e("EEEError", "" + ex.getMessage());

                    }
                }
                else if(getArguments().getString("scene_id") == "7")
                {
                        selectEachRoom();
                }
                else
                {
                    selectEachRoom();
                }
            } catch (Exception ex) {
                Log.e("Error", "" + ex.getMessage());
            }

        if(App.isOrientationFlag()) {
            ReplaceFragment.replaceFragment(this, R.id.frm_main_container, ExperienceMainFragment.newInstance());

            App.setCurrentSubcriber(App.getCurrentSubcriber());
            this.dismiss();

        }
        else{
            ReplaceFragment.replaceFragment(this, R.id.frm_main_container,ExperienceMainFragmentPortrait.newInstance());
        }
    }



    public JSONArray getRoomIdJsonArray()
    {
        JSONArray jsonArray = new JSONArray();
        try {

            ArrayList<String> room_ids = App.getRoom_ids();
            for (int i = 0; i < room_ids.size(); i++) {
                jsonArray.put(new JSONObject().put("roomId", room_ids.get(i)));
            }
        }
        catch (Exception ex)
        {
            Logs.error(TAG+"_RoomIdError",""+ex.getMessage());
        }
        return jsonArray;
    }

    private void selectEachRoom() {

        try {
        JSONObject jsonObject = new JSONObject();
        if (!getSelectedRoomArray().toString().equals("[]")) {

                jsonObject.put("selectedArr", getSelectedRoomArray());
        }
        else jsonObject.put("selectedArr", getRoomBySceneId());
        jsonObject.put("scene", "" + getArguments().getString("scene_id"));
        jsonObject.put("selectedArr", getSelectedRoomArray());

        if (getArguments().getString("scene_id") == "A" || getArguments().getString("scene_id") == "B" || getArguments().getString("scene_id") == "C") {
            jsonObject.put("deselectedArr", new JSONArray());
        } else {
            jsonObject.put("deselectedArr", getDeselectedRoomArray());
        }
        JSONObject resultJsonObject = new JSONObject();
        resultJsonObject.put("data", jsonObject);
        resultJsonObject.put("type", "activate_scene");
            Logs.info(TAG + "_scene_light", "" + resultJsonObject);
        App.getSocket().emit("action", resultJsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONArray getRoomBySceneId()
    {

        JSONArray jsonArray = new JSONArray();
        return jsonArray;
    }

    private void defaultvisibility() {
        img7.setVisibility(View.GONE);
        img6.setVisibility(View.GONE);
        img5.setVisibility(View.GONE);
        img4.setVisibility(View.GONE);
        img3.setVisibility(View.GONE);
        img2.setVisibility(View.GONE);
        img1.setVisibility(View.GONE);
    }



    private void setActiveSubcriber() {


        activeobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("");
                        sub.onCompleted();
                    }
                }
        );
        activeObserver = new Observer<String>() {

            @Override
            public void onCompleted() {
                setAddVisibleHide();
            }

            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onNext(String string) {

            }
        };

        Subcriber s = new Subcriber();
        s.setObservable(activeobservable);
        s.setObserver(activeObserver);
        App.setActive_button_subcriber(s);
    }

    private void setSubcriber() {


        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("");
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


                Logs.info(TAG+"_SImulationDATA","rrrrrrrrrrrrrrrrrr"+string);
                updateUI(string);


            }
        };



        Logs.info(TAG+"_IMINSub","hhhhhhhhh");
        Subcriber s = new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);
        //  }
    }
    @Optional
    @OnClick(R.id.img_close)
    public void img_close()
    {
        this.dismiss();
    }



    @Optional
    @OnClick(R.id.img_back)
    public void img_back()
    {
        ReplaceFragment.replaceFragment(this, R.id.frm_main_container,ExperienceMainFragmentPortrait.newInstance());
    }


    private void updateUI(String response_string)
    {
        if(response_string.equals("rooms_by_device")) {

            try {

                if (getRoomItem().size() > 0) {
                    // rel_no_data.setVisibility(View.GONE);
                    notext.setVisibility(View.GONE);
                    rel_loading.setVisibility(View.GONE);
                    divider.setVisibility(View.GONE);
                    listview.setVisibility(View.VISIBLE);
                    btn_add.setVisibility(View.VISIBLE);
                    arr_room_list = getRoomItem();



                    if(customRoomItemAdapter==null) {
                        customRoomItemAdapter = new ActivitesRoomListAdapter(mcontext, arr_room_list, getArguments().getString("scene_id"));
                        listview.setAdapter(customRoomItemAdapter);
                        mLayoutManager = new LinearLayoutManager(getActivity());
                        listview.setLayoutManager(mLayoutManager);
                    }
                    else
                    {
                        customRoomItemAdapter.reload(arr_room_list);
                        customRoomItemAdapter.notifyDataSetChanged();
                    }
                    btn_add.setVisibility(View.VISIBLE);


                } else {

                    listview.setVisibility(View.GONE);
                    rel_loading.setVisibility(View.GONE);
                    btn_add.setVisibility(View.GONE);

                        /*rel_no_data.setVisibility(View.VISIBLE);
                        frm_home.setVisibility(View.GONE);*/
                    notext.setVisibility(View.VISIBLE);
                    divider.setVisibility(View.VISIBLE);
                    notext.setText("No Rooms Available");
                    //circular_progress_bar.setVisibility(View.GONE);
                }

            } catch (Exception ex) {
                Logs.error(TAG+"_Error", "" + ex.getMessage());
            }
        }
    }

    private ArrayList<Room> getRoomItem() {


        String jsonarr = null;


        try {
            InputStream is = mcontext.getAssets().open("scene_wise_room.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonarr = new String(buffer, "UTF-8");
            Logs.info(TAG+"_Scenesjson",jsonarr);

            JSONArray jsonArray=new JSONArray(jsonarr);
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("scene_id").equals(getArguments().getString("scene_id")))
                {
                    roomTypeJsonArray =jsonObject.getJSONArray("room_type");
                    Logs.info(TAG+"_roomTypeArray",""+roomTypeJsonArray);
                }
            }



            JSONObject jsonObject = App.getRoomDeviceData();
            JSONArray roomjsonArray = jsonObject.getJSONArray("data");


            if(getArguments().getString("scene_id").equals("7")?getKitchenCount(roomjsonArray):roomjsonArray.length()>0) {
                Logs.info(TAG+"_getRoomDeviceData", "" + jsonObject);
                arr_room_list.clear();
                arr_room_list_for_deslect.clear();
                if(!(getArguments().getString("scene_id")=="B" || getArguments().getString("scene_id")=="C")) {
                    Room room1 = new Room();

                    room1.setRoom_id("0");
                    room1.setRoom_title("SELECT ALL");
                    room1.setFav_id("SelectAll");
                    room1.setRoom_scene(""+getSceneId());
                    arr_room_list.add(room1);
                }


                for (int i = 0; i < roomjsonArray.length(); i++) {
                    JSONObject jsonObject1 = roomjsonArray.getJSONObject(i);
                    if (checkRoomType(jsonObject1.getString("CML_ROOM_TYPE"))) {
                        String roomname = "";
                        Room room = new Room();
                        roomname = jsonObject1.getString("CML_TITLE");
                        room.setRoom_id(jsonObject1.getString("CML_ID"));
                        room.setRoom_title("" + roomname);
                        room.setFav_id(FavoritesOperations.getFavId(jsonObject1.getString("CML_ID"),getArguments().getString("scene_id")));
                        arr_room_list.add(room);
                        arr_room_list_for_deslect.add(jsonObject1.getString("CML_ID"));
                    } else {
                        // no_text_avilable.setVisibility(View.VISIBLE);
                    }

                }
            }
            else {
                rel_loading.setVisibility(View.GONE);
                notext.setVisibility(View.VISIBLE);
                notext.setText( "No Rooms are available");
            }

        } catch (Exception ex) {
            Logs.info(TAG+"_jsonerror", "------------------------" + ex.getMessage());
        }

        return arr_room_list;

    }

    public JSONArray getSelectedRoomArray()
    {
        JSONArray jsonArray = new JSONArray();
        try {

            ArrayList<String> room_ids = App.getRoom_ids();
            for (int i = 0; i < room_ids.size(); i++) {
                jsonArray.put(""+ room_ids.get(i));


            }
        }
        catch (Exception ex)
        {
            Logs.error(TAG+"_RoomIdError",""+ex.getMessage());
        }
        return jsonArray;
    }

    private JSONArray getDeselectedRoomArray() {
        JSONArray jsonArray = new JSONArray();
        try {

            ArrayList<String> room_ids = App.getDeselected_room_ids();


            for (int i = 0; i < room_ids.size(); i++) {
                jsonArray.put(""+ room_ids.get(i));

            }
            Logs.info(TAG+"_Arrrays....",arr_room_list_for_deslect+"------");

        }
        catch (Exception ex)
        {
            Logs.error(TAG+"_RoomIdError",""+ex.getMessage());
        }
        return jsonArray;

    }

    public boolean getKitchenCount(JSONArray jsonArray)
    {
        boolean b=false;
        try {
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_ROOM_TYPE").equals("Kitchen"))
                {
                    b=true;
                }
            }
        }
        catch (Exception ex)
        {
            Logs.error(TAG,"kitech_count_error"+ex.getMessage());
        }
        return b;
    }



    public boolean checkRoomType(String room_type)
    {

        boolean checkFlag=false;

        Logs.info(TAG+"_RoomType",""+room_type);

        try {
            for (int i = 0; i < roomTypeJsonArray.length(); i++) {
                if (room_type.equals(roomTypeJsonArray.getString(i))) {
                    checkFlag=true;
                }
            }
        }
        catch (Exception ex)
        {
            Logs.error(TAG+"_RoomCheckError",""+ex.getMessage());
        }
        return checkFlag;
    }


    public int getSceneId()
    {

        int count=1;
        try {


            JSONArray jsonArray = App.getRoomDeviceData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("CML_SCENE_ID").equals(getArguments().getString("scene_id"))) {
                    count++;
                }
            }
        }
        catch (Exception ex){ Logs.error(TAG+"_FError",ex.getMessage());}
        return count;

    }
    private void sendScreenImageName() {
        String name = "Activities SelectRoom List Fragment";
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