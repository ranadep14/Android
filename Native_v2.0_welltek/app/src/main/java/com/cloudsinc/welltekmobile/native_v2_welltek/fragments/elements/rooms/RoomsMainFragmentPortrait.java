package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.ElementsRoomListAdapterPortrait;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Room;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.PROVISIONAL_DEVICES;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.ROOMS;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.ZONES;
/**
 * Fragment for display all rooms in listview on mobile screen
 *
 * @author Nikhil Vharamble
 * @version 2.0.0
 * @since 2018-01-09
 */
public class RoomsMainFragmentPortrait extends Fragment {
    View v;
    Context mcontext;
    private ArrayList<Room> arr_room_list=new ArrayList<>();
    // Array of strings to store currencies
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    ElementsRoomListAdapterPortrait adapter;
    String TAG=RoomsMainFragmentPortrait.this.getClass().getSimpleName();
    @Nullable @BindView(R.id.rel_no_data)RelativeLayout rel_no_data;
    @Nullable @BindView(R.id.txt_no_rooms_available)TextView txt_no_rooms_available;
    @BindView(R.id.rel_loading) RelativeLayout rel_loading;
    @Nullable @BindView(R.id.elements_room_list)RecyclerView elements_room_list;
    int whole_blind_count=0,whole_light_count=0,whole_music_count=0;
    LinearLayoutManager mLayoutManager;
    public static RoomsMainFragmentPortrait newInstance() {
        return new RoomsMainFragmentPortrait();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.elements_rooms_main_portrait, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);
        Log.e(TAG,"Im here");
        if(App.getTemp_bundle()==null) App.setTemp_bundle(new Bundle());
        Bundle bundle= App.getTemp_bundle();
        bundle.putString("call_from","room");
        App.setTemp_bundle(bundle);
        App.clearSubscriber();
        App.clearFavSubscriber();
        setSubcriber();
        getWholeHomeDeviceCount();
        getRoomList();
        return v;
    }
    private void setSubcriber() {
        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext(""+App.getRoomData());
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
                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
            }
            @Override
            public void onNext(String string) {
                 Logs.info(TAG+"_WhereIm","InRoomListDisplay"+string);
                    getWholeHomeDeviceCount();
                    getRoomList();
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);
    }
    public void getRoomList()
    {
        try {
                arr_room_list.clear();
                Room whole_home = new Room();
                whole_home.setRoom_id("whole_home");
                whole_home.setRoom_title("Whole Home");
                whole_home.setRoom_type("whole_home");
                whole_home.setCnt_audios(whole_music_count>0?1:0);
                whole_home.setCnt_blinds(whole_blind_count>0?1:0);
                whole_home.setCnt_lights(whole_light_count>0?1:0);
                whole_home.setDawn_state(false);
                arr_room_list.add(whole_home);
            if(App.getDataStorageJson().getJSONObject(ROOMS)!=null) {
                Iterator<String> keys = App.getDataStorageJson().getJSONObject(ROOMS).keys();
                while (keys.hasNext()) {
                    JSONObject json=App.getDataStorageJson().getJSONObject(ROOMS).getJSONObject(keys.next());
                    System.out.println("\n==================" + json);
                    Room room = new Room();
                    room.setRoom_id(json.getString("CML_ID"));
                    room.setRoom_title(json.getString("CML_TITLE"));
                    room.setRoom_type(json.getString("CML_ROOM_TYPE"));
                    if (json.has("HAS_MUSIC")) room.setCnt_audios(json.getInt("HAS_MUSIC"));
                    if (json.has("HAS_BLIND")) room.setCnt_blinds(json.getInt("HAS_BLIND"));
                    if (json.has("HAS_LIGHT")) room.setCnt_lights(json.getInt("HAS_LIGHT"));
                    arr_room_list.add(room);
                }
                if (arr_room_list.size() > 0) {
                    elements_room_list.setVisibility(View.VISIBLE);
                    rel_no_data.setVisibility(View.GONE);
                    if(adapter==null) {
                        adapter = new ElementsRoomListAdapterPortrait(mcontext, arr_room_list, RoomsMainFragmentPortrait.newInstance(),getFragmentManager(),getActivity().getApplicationContext());
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false);
                        elements_room_list.setLayoutManager(layoutManager);
                        elements_room_list.setAdapter(adapter);
                    }
                    else
                    {
                        adapter.reload(arr_room_list);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    rel_no_data.setVisibility(View.VISIBLE);
                    txt_no_rooms_available.setVisibility(View.VISIBLE);
                    rel_loading.setVisibility(View.GONE);
                }
            }
        }
        catch (Exception ex)
        {
             Logs.info(TAG+"_RoomTabError",""+ex.getMessage());
        }
        // return resultarr;
    }
    @Override
    public void onResume() {
        super.onResume();
        if(!App.isOrientationFlag()) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
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
    public void getWholeHomeDeviceCount()
    {
        whole_blind_count=0;
        whole_light_count=0;
        whole_music_count=0;
        try {
            Iterator<String> keys = App.getDataStorageJson().getJSONObject(ZONES).keys();
            while (keys.hasNext()) {
                JSONObject jsonObject=App.getDataStorageJson().getJSONObject(ZONES).getJSONObject(keys.next());
                if(jsonObject.getString("CML_TITLE").equals("Whole Home"))
                {
                    Logs.info(TAG+"_HomeZoneObject",""+jsonObject);
                    //hvac_name=jsonObject.getString("CML_TITLE");
                   if(jsonObject.has("HAS_BLIND")) whole_blind_count=jsonObject.getInt("HAS_BLIND");
                   if(jsonObject.has("HAS_LIGHT")) whole_light_count=jsonObject.getInt("HAS_LIGHT");
                   if(jsonObject.has("HAS_MUSIC")) whole_music_count=jsonObject.getInt("HAS_MUSIC");
                }
            }
        }
        catch (Exception ex)
        {
            Logs.info(TAG+"_SetAQIError",""+ex.getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.gc();
        elements_room_list.removeAllViewsInLayout();
    }
}