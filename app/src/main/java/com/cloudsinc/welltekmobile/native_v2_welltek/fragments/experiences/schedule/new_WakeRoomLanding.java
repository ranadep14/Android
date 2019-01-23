package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.CustomDawnRoomListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.New_RoomDawn;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;


/**
 * Created by Jaid Shaikh on 13/04/18.
 * purpose : Displaying Room wise all simulations list
 **/
public class new_WakeRoomLanding extends Fragment {


    private int getRoomlenght=0;

    public static new_WakeRoomLanding newInstance() {
        return new new_WakeRoomLanding();
    }

    private static final String TAG = new_WakeRoomLanding.class.getName();
    View view;
    Context mcontext;
    RecyclerView listview;
    App app;
    Observable<String> mobservable;
    Observer<String> myObserver;
    @BindView(R.id.notext)TextView notext;
    @BindView(R.id.rel_loading)RelativeLayout rel_loading;

    private Tracker mTracker;

    CustomDawnRoomListAdapter ca;
    List<New_RoomDawn> newRoomDawnList=new ArrayList<>();
    LinearLayoutManager llm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wake_rooms, container, false);

        mcontext=view.getContext();
        ButterKnife.bind(this, view);

        app=(App)mcontext.getApplicationContext();

        if (!checkConfiguration()) {
            Logs.info(TAG,"Error in AllSimulationListFragment");
        }
       // App application = (App)getActivity().getApplication();
        mTracker = app.getDefaultTracker();
        sendScreenImageName();

        listview = view.findViewById(R.id.list_add_rooms);
        notext.setVisibility(View.GONE);


        try {
            if(App.getSocket()!=null) {
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_rooms_by_device", new JSONObject().put("type", "Lighting")));
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_simulations", new JSONObject()));
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_dawn_simulations", new JSONObject()));
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_sleep_simulations", new JSONObject()));

          }

        } catch (JSONException e) {
                Log.e(TAG,"emiting_calls : "+e.getMessage() );
        }
       // getRoomNames();
        getSetData();
        setSubcriber();

        return view;
    }

    private void setSubcriber() {

        try {
            Log.d(TAG,"DeviceData"+App.getDevicesByRoomData().getJSONArray("data"));
            if (App.getRoomData().getJSONArray("data").toString().equals("[]")) {
                notext.setVisibility(View.VISIBLE);
            }


        } catch (Exception e) {
            Log.e(TAG,"data_getRoomData : "+e.getMessage());
        }
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

            //    getRoomNames();
                Log.i(TAG,"Subscriber String: "+string);
                getSetData();
            }
        };




        Subcriber s = new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);

    }

    private void getSetData() {

        try {
            getRoomNames();


            getRoomlenght=getRoomNames().size();

            if (getRoomlenght > 0) {
                rel_loading.setVisibility(View.GONE);
                notext.setVisibility(View.GONE);
                listview.setVisibility(View.VISIBLE);


                if (ca == null) {
                    ca = new CustomDawnRoomListAdapter(mcontext, getRoomNames(), new_WakeRoomLanding.this);
                    listview.setAdapter(ca);
                    llm = new LinearLayoutManager(getActivity());
                    listview.setLayoutManager(llm);
                    ca.notifyDataSetChanged();
                }   else {
                    // ca.reload(newRoomDawnList);
                    ca.notifyDataSetChanged();
                }
            } else {
                listview.setVisibility(View.GONE);
                rel_loading.setVisibility(View.GONE);
                notext.setVisibility(View.VISIBLE);
                notext.setText("It seems no Bedroom is present to set the Wake and Sleep simulations");
            }

            /***************/

        }catch (Exception ex){
            Log.e(TAG,"Error unable to set list"+ex.getMessage());}
    }


    private List<New_RoomDawn> getRoomNames() {
       List<New_RoomDawn> newRoomDawnList = new ArrayList<>();

        try {

            JSONObject jsonObject = App.getRoomDeviceData();
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            Log.d(TAG,"Array_getRoomDeviceData : "+jsonArray);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String roomname = "";
                if (jsonObject1.getString("CML_ROOM_TYPE").equals("Bedroom")) {
                    New_RoomDawn room=new New_RoomDawn();
                    roomname = jsonObject1.getString("CML_TITLE");
                    room.setRoom_id(jsonObject1.getString("CML_ID"));
                    room.setRoom_title(""+roomname);
                    room.setCnt_audios(jsonObject1.getInt("HAS_MUSIC"));
                    room.setCnt_blinds(jsonObject1.getInt("HAS_BLIND"));
                    newRoomDawnList.add(room);

                }

            }

        }
        catch (Exception ex){
            Log.e(TAG,"Unable to get Room  device data : "+ex.getMessage());
        }

        return newRoomDawnList;
    }



    @Override
    public void onResume() {
        super.onResume();
        if(!App.isOrientationFlag()) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Experiences");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if(!App.isOrientationFlag()) {

            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logs.info(TAG,"I am in All Simulation Fragemnt ondestroy");
        System.gc();
        listview.removeAllViewsInLayout();
    }
    private void sendScreenImageName() {
        String name = "All simulation List Fragment";
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