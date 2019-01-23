package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.MyViewPagerAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.RoomListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Room;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.ROOMS;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.ZONES;
/**
 * This class containing functionality related to displaying roomlist
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class RoomsMainFragment extends Fragment {
    public static RoomsMainFragment newInstance() {
        return new RoomsMainFragment();
    }
    @BindView(R.id.lin_room_lights)LinearLayout lin_room_light;
    @BindView(R.id.lin_room_blinds)LinearLayout lin_room_blinds;
    @BindView(R.id.lin_room_audio)LinearLayout lin_room_audio;
    @BindView(R.id.txt_room_title)TextView txt_room_title;
    @BindView(R.id.txt_room_type)TextView txt_room_type;
    @BindView(R.id.txt_id)TextView txt_room_id;
    @BindView(R.id.rel_no_data)RelativeLayout rel_no_data;
    @BindView(R.id.txt_no_rooms_available)TextView txt_no_rooms_available;
    @BindView(R.id.lin_single_room)LinearLayout lin_single_room;
    @BindView(R.id.grid_room)GridView grid_room;
    @BindView(R.id.img_single_room_back)ImageView img_single_room_back;
    TextView[] dots;
    Bundle bundle,bundle2,bundle3;
    private View v;
    private MyViewPagerAdapter myViewPagerAdapter;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    private ArrayList<Room> arr_room_list=new ArrayList<>();
    Context mcontext;
    private Tracker mTracker;

    RoomListAdapter roomListAdapter;
    String TAG=RoomsMainFragment.this.getClass().getSimpleName();
    int whole_blind_count=0,whole_light_count=0,whole_music_count=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
        v=inflater.inflate(R.layout.activity_rooms_main, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);
        if (!checkConfiguration()) {
            Logs.info(TAG,"Error in Mobile Activites Fragment");
        }
        App application = (App)getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();
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
            }
            @Override
            public void onNext(String string) {
                 Logs.info(TAG+"_WhereIm","InRoomListDisplay");
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
                    Room room = new Room();
                    room.setRoom_id(json.getString("CML_ID"));
                    room.setRoom_title(json.getString("CML_TITLE"));
                    room.setRoom_type(json.getString("CML_ROOM_TYPE"));
                    if (json.has("HAS_MUSIC")) room.setCnt_audios(json.getInt("HAS_MUSIC"));
                    if (json.has("HAS_BLIND")) room.setCnt_blinds(json.getInt("HAS_BLIND"));
                    if (json.has("HAS_LIGHT")) room.setCnt_lights(json.getInt("HAS_LIGHT"));
                    if(json.has("DAWN_RUNNING")) room.setDawn_state(json.getBoolean("DAWN_RUNNING"));
                    arr_room_list.add(room);
                }

                if (arr_room_list.size() == 1) {
                    rel_no_data.setVisibility(View.GONE);
                    grid_room.setVisibility(View.GONE);
                    lin_single_room.setVisibility(View.VISIBLE);
                    txt_room_id.setText(arr_room_list.get(0).getRoom_id());
                    txt_room_title.setText(arr_room_list.get(0).getRoom_title());
                    txt_room_type.setText(arr_room_list.get(0).getRoom_type());
                     Logs.info(TAG+"_RoomLightDevice", "" + arr_room_list.get(0).getCnt_lights());
                    if (arr_room_list.get(0).getCnt_lights() > 0)
                        lin_room_light.setVisibility(View.VISIBLE);
                    else lin_room_light.setVisibility(View.GONE);
                    if (arr_room_list.get(0).getCnt_blinds() > 0)
                        lin_room_blinds.setVisibility(View.VISIBLE);
                    else lin_room_blinds.setVisibility(View.GONE);
                    if (arr_room_list.get(0).getCnt_audios() > 0)
                        lin_room_audio.setVisibility(View.VISIBLE);
                    else lin_room_audio.setVisibility(View.GONE);
                    roomListAdapter.setRoomBackImage(img_single_room_back, arr_room_list.get(0).getRoom_type());
                } else if (arr_room_list.size() > 1) {
                    grid_room.setVisibility(View.VISIBLE);
                    lin_single_room.setVisibility(View.GONE);
                    rel_no_data.setVisibility(View.GONE);
                       if(roomListAdapter==null) {
                           roomListAdapter = new RoomListAdapter(mcontext, arr_room_list, RoomsMainFragment.newInstance());
                           grid_room.setAdapter(roomListAdapter);
                       }
                       else
                       {
                           roomListAdapter.reload(arr_room_list);
                           roomListAdapter.notifyDataSetChanged();
                       }
                    grid_room.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            TextView txt_id = view.findViewById(R.id.txt_id);
                            TextView txt_room_title = view.findViewById(R.id.txt_room_title);
                            TextView txt_room_type = view.findViewById(R.id.txt_room_type);
                            TextView txt_no_device = view.findViewById(R.id.txt_no_device);
                            TextView txt_dawn_state = view.findViewById(R.id.txt_dawn_state);
                             Logs.info(TAG+"_RoomDevice", txt_no_device.getText().toString());
                            if (txt_no_device.getText().toString().equals("false")) {
                                //CustomDialogShow.dispDialogWithOK(mcontext, "No Devices Available");
                            } else {
                                Room room = new Room();
                                room.setRoom_id(txt_id.getText().toString());
                                room.setRoom_title(txt_room_title.getText().toString());
                                room.setRoom_type(txt_room_type.getText().toString());
                                room.setDawn_state(Boolean.parseBoolean(txt_dawn_state.getText().toString()));
                                room.setCnt_audios(whole_music_count);
                                room.setCnt_blinds(whole_blind_count);
                                room.setCnt_lights(whole_light_count);
                                App.setRoom(room);
                                Bundle bundle = new Bundle();
                                bundle.putString("CML_ID", txt_id.getText().toString());
                                bundle.putString("CML_TITLE", txt_room_title.getText().toString());
                                bundle.putString("CML_ROOM_TYPE", txt_room_type.getText().toString());
                                Fragment fragment = RoomDevicesFragment.newInstance();
                                fragment.setArguments(bundle);
                                ReplaceFragment.replaceFragment(RoomsMainFragment.this, R.id.frm_elemenmain_container, fragment);
                                 Logs.info(TAG+"_Room_Id", "" + txt_id.getText().toString());
                            }
                        }
                    });
                }
            }
        }
        catch (Exception ex)
        {
             Logs.info(TAG+"_RoomTabError",""+ex.getMessage());
        }
        // return resultarr;
    }
    @OnClick(R.id.rel_single_room)
    public void rel_single_room(View view)
    {
        if((whole_light_count+whole_blind_count+whole_music_count)>0) {
            TextView txt_id = view.findViewById(R.id.txt_id);
            TextView txt_room_title = view.findViewById(R.id.txt_room_title);
            TextView txt_room_type = view.findViewById(R.id.txt_room_type);
            Room room = new Room();
            room.setRoom_id(txt_id.getText().toString());
            room.setRoom_title(txt_room_title.getText().toString());
            room.setRoom_type(txt_room_type.getText().toString());
            room.setCnt_lights(whole_light_count);
            room.setCnt_audios(whole_music_count);
            room.setCnt_blinds(whole_blind_count);
            App.setRoom(room);
            Bundle bundle = new Bundle();
            bundle.putString("CML_ID", txt_id.getText().toString());
            bundle.putString("CML_TITLE", txt_room_title.getText().toString());
            bundle.putString("CML_ROOM_TYPE", txt_room_type.getText().toString());
            Fragment fragment = RoomDevicesFragment.newInstance();
            fragment.setArguments(bundle);
            ReplaceFragment.replaceFragment(RoomsMainFragment.this, R.id.frm_elemenmain_container, fragment);
            Logs.info(TAG + "_Room_Id", "" + txt_id.getText().toString());
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
        Logs.info(TAG,"I am in RoomMainFragemnt ondestroy");
        System.gc();
        grid_room.removeAllViewsInLayout();
    }
    private void sendScreenImageName() {
        String name = "Device Main Fragment";
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