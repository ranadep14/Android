package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.device_settings;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.CustomChooseItemListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.SettingDevicesLights;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Room;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
/**
 * This class containing functionality related to displaying rooms for assign to device.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class DeviceSettingSelectRoom extends Fragment {
    public static DeviceSettingSelectRoom newInstance() {
        return new DeviceSettingSelectRoom();
    }
    private View view;
    private Context mcontext;
    private ListView listview;
    private TextView txt_cancle;
    private App app;
    Observable<String> mobservable;
    Observer<String> myObserver;
    private ArrayList<Room> arr_room_list=new ArrayList<>();
    String TAG=DeviceSettingSelectRoom.this.getClass().getName();
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_device_setting_select_room, container, false);
        mcontext=view.getContext();
        ButterKnife.bind(this,view);
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();
        txt_fragment_title.setText("Device Settings");
        FontUtility.setAppFont(mContainer, mFont);
        app=(App)mcontext.getApplicationContext();
        listview = view.findViewById(R.id.lst_devices_setting_room);
        try {
            getRooms();
            setSubcriber();
        }
        catch (Exception ex){
             Logs.error(TAG+"_Error",""+ex.getMessage());}
        return view;
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
                getRooms();
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);
    }
    public void getRooms()
    {
        String jsonarr = null;
        try {
            InputStream is = mcontext.getAssets().open("scenes_ids.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonarr = new String(buffer, "UTF-8");
             Logs.info(TAG+"_Scenesjson", jsonarr);
            JSONArray jsonArray = new JSONArray(jsonarr);
            JSONArray room_data_json_arr = App.getRoomData().getJSONArray("data");
            for (int j = 0; j < room_data_json_arr.length(); j++) {
                JSONObject jsonObject1 = room_data_json_arr.getJSONObject(j);
                Room chooseRoom = new Room();
                chooseRoom.setRoom_id(jsonObject1.getString("CML_ID"));
                chooseRoom.setRoom_title(jsonObject1.getString("CML_TITLE"));
                chooseRoom.setRoom_type(jsonObject1.getString("CML_ROOM_TYPE"));
                if (jsonObject1.has("CML_SCENE_ID"))
                    chooseRoom.setRoom_scene(jsonObject1.getString("CML_SCENE_ID"));
                if(!getRoomFlag(jsonObject1.getString("CML_TITLE"))) {
                    arr_room_list.add(chooseRoom);
                }
            }
           /* for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                JSONArray device_type_json_arr=jsonObject.getJSONArray("type");
                for(int k=0;k<device_type_json_arr.length();k++) {
                    String str_device_type=(String) device_type_json_arr.get(k);
                     Logs.info(TAG+"_device_type",str_device_type+" "+device_type);
                    if (str_device_type.equals(device_type)) {
                        JSONArray room_type_arr = jsonObject.getJSONArray("roomType");
                        for (int a=0;a<room_type_arr.length();a++) {
                            String str_room_type=(String) room_type_arr.get(a);
                             Logs.info(TAG+"_room_type",""+str_room_type);
                             for (int j = 0; j < room_data_json_arr.length(); j++) {
                                JSONObject jsonObject1 = room_data_json_arr.getJSONObject(j);
                                if (jsonObject1.getString("CML_ROOM_TYPE").equals(str_room_type)) {
                                    App.getTemp_bundle().putString("room_title",jsonObject1.getString("CML_TITLE"));
                                    Room chooseRoom = new Room();
                                    chooseRoom.setRoom_id(jsonObject1.getString("CML_ID"));
                                    chooseRoom.setRoom_title(jsonObject1.getString("CML_TITLE"));
                                    chooseRoom.setRoom_type(jsonObject1.getString("CML_ROOM_TYPE"));
                                    if (jsonObject1.has("CML_SCENE_ID"))
                                        chooseRoom.setRoom_scene(jsonObject1.getString("CML_SCENE_ID"));
                                    if(!getRoomFlag(jsonObject1.getString("CML_TITLE"))) {
                                        arr_room_list.add(chooseRoom);
                                    }
                                }
                            }
                        }
                    }
                }
            }*/
            CustomChooseItemListAdapter customRoomItemAdapter = new CustomChooseItemListAdapter(mcontext, arr_room_list);
            listview.setAdapter(customRoomItemAdapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String str_id =((TextView)view.findViewById(R.id.txt_id)).getText().toString();
                     Logs.info(TAG+"_Room",str_id);
                    Bundle bundle= App.getTemp_bundle();
                    bundle.putString("room_id",str_id);
                    bundle.putBoolean("select_flag",true);
                    App.setTemp_bundle(bundle);
                        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(DeviceSettingSelectRoom.this,R.id.frm_filter_main_container,SettingDevicesLights.newInstance());
                        else ReplaceFragment.replaceFragment(DeviceSettingSelectRoom.this, R.id.frm_main_container, SettingDevicesLights.newInstance());
                }
            });
        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_JsonError",ex.getMessage());
        }
    }
    public boolean getRoomFlag(String room_name)
    {
        boolean roomFlag=false;
        for(int i=0;i<arr_room_list.size();i++)
        {
            Room room=arr_room_list.get(i);
            if(room.getRoom_title().equals(room_name))
            {
                roomFlag=true;
            }
        }
        return roomFlag;
    }
    @OnClick(R.id.img_back)
    public void img_back()
    {
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(DeviceSettingSelectRoom.this,R.id.frm_filter_main_container,SettingDevicesLights.newInstance());
        else ReplaceFragment.replaceFragment(DeviceSettingSelectRoom.this, R.id.frm_main_container, SettingDevicesLights.newInstance());
    }
}
