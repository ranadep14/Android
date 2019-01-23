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
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.CustomChoosZoneListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.SettingDevicesLights;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Zone;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
/**
 * This class containing functionality related to displaying zones assing to sensor or rooms.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class DeviceSettingSelectZone extends Fragment {
    public static DeviceSettingSelectZone newInstance() {
        return new DeviceSettingSelectZone();
    }
    private View view;
    private Context mcontext;
    private ListView listview;
    private App app;
    Observable<String> mobservable;
    Observer<String> myObserver;
    String device_type="";
    TextView txt_cancle;
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    String TAG=DeviceSettingSelectZone.this.getClass().getName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_device_setting_select_room, container, false);
        mcontext=view.getContext();
        ButterKnife.bind(this,view);
        txt_fragment_title.setText("Device Settings");
        device_type=App.getTemp_bundle().getString("device_type");
         Logs.info(TAG+"_Device_typeee",device_type);
        /*TextView txt_tab_title=(TextView) getActivity().findViewById(R.id.txt_tab_title);
        txt_tab_title.setText("Zones");*/
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();
        FontUtility.setAppFont(mContainer, mFont);
       // getActivity().findViewById(R.id.txt_save).setVisibility(View.GONE);
        if(App.getSocket()!=null) {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_zones", new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_hvac_zone_provisioned", new JSONObject()));
        }
        app=(App)mcontext.getApplicationContext();
         listview = view.findViewById(R.id.lst_devices_setting_room);
        setSubcriber();
        return view;
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
                try {
                    CustomChoosZoneListAdapter customRoomItemAdapter = new CustomChoosZoneListAdapter(mcontext, getZone());
                    listview.setAdapter(customRoomItemAdapter);
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String str_id =((TextView)view.findViewById(R.id.txt_id)).getText().toString();
                             Logs.info(TAG+"_Room",str_id);
                            Bundle bundle= App.getTemp_bundle();
                            bundle.putString("zone_id",str_id);
                            bundle.putBoolean("select_flag",true);
                            App.setTemp_bundle(bundle);
                            if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(DeviceSettingSelectZone.this,R.id.frm_filter_main_container,SettingDevicesLights.newInstance());
                            else ReplaceFragment.replaceFragment(DeviceSettingSelectZone.this, R.id.frm_main_container, SettingDevicesLights.newInstance());
                        }
                    });
                }
                catch (Exception ex){
                     Logs.error(TAG+"_Error",""+ex.getMessage());}
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);
    }
    public  ArrayList<Zone>  getZone()
    {
         ArrayList<Zone> arr_zone_list=new ArrayList<>();
        arr_zone_list.clear();
        try {
            JSONArray jsonArray=null;
            if(device_type.equals("sensor"))    jsonArray = App.getHvacZoneProvisionedJson().getJSONArray("data");
             else jsonArray = App.getZoneJson().getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                Zone z=new Zone();
                z.setZone_id(jsonObject.getString("CML_ID"));
                z.setZone_title(jsonObject.getString("CML_TITLE"));
                arr_zone_list.add(z);
            }
        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_ZoneError",ex.getMessage());
        }
        return arr_zone_list;
    }
    @OnClick(R.id.img_back)
    public void img_back()
    {
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(DeviceSettingSelectZone.this,R.id.frm_filter_main_container,SettingDevicesLights.newInstance());
        else ReplaceFragment.replaceFragment(DeviceSettingSelectZone.this, R.id.frm_main_container, SettingDevicesLights.newInstance());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.gc();
        listview.removeAllViewsInLayout();
    }
}
