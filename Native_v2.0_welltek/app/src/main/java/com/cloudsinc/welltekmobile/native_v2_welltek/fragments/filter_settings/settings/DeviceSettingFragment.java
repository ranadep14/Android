package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.CustomDeviceListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Device;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
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
 * The DeviceSettingFragment is for assigning,remove devices from zone,room
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class DeviceSettingFragment extends Fragment {
    public static DeviceSettingFragment newInstance() {
        return new DeviceSettingFragment();
    }
    Observable<String> mobservable;
    Observer<String> myObserver;
    ArrayList<Device> arr_devices_list=new ArrayList<>();
    Context mcontext;
    View view;
    @BindView(R.id.lst_devices_setting)ListView lst_devices_setting;
    @BindView(R.id.txt_no_device)TextView txt_no_device;
    @BindView(R.id.img_add_devices)ImageView img_add_devices;
    @BindView(R.id.rel_loading) RelativeLayout rel_loading;
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @BindView(R.id.img_back)ImageView img_back;
    @BindView(R.id.rel_no_data)RelativeLayout rel_no_data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_device_setting,container, false);
        ButterKnife.bind(this,view);
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();
        FontUtility.setAppFont(mContainer, mFont);
        txt_fragment_title.setText("Device Settings");
        mcontext=view.getContext();



        img_add_devices.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ReplaceFragment.replaceFragment(DeviceSettingFragment.this,R.id.frm_filter_main_container, SettingAddDevices.newInstance());

            }
        });


        getDevices("provisioned_devices");


        setSubcriber();
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
                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
            }
            @Override
            public void onNext(String string) {
                Log.e("WhereIm","InRoomListDisplay");

               getDevices(string);

            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);
    }
    @OnClick(R.id.img_back)
    public void img_back()
    {
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(DeviceSettingFragment.this, R.id.frm_filter_main_container, SettingMainFragment.newInstance());
        else ReplaceFragment.replaceFragment(DeviceSettingFragment.this, R.id.frm_main_container, SettingMainFragment.newInstance());
    }


    public void getDevices(String string)
    {
        if(string.equals("provisioned_devices")) {
            try {
                JSONObject jsonObject = App.getProvisionalDevicesData();
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                if (!jsonArray.toString().equals("[]")) {
                    arr_devices_list.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Log.e("ProvisionDevices", "" + jsonObject1);
                        String device_type = jsonObject1.getString("type");
                        Device device = new Device();
                        device.setDevice_id(jsonObject1.getString("CML_ID"));
                        device.setDevice_type(device_type);
                        device.setDevice_title(jsonObject1.getString("CML_TITLE"));
                        if (device_type.equals("Air") || device_type.equals("Water")) {
                            if (jsonObject1.has("zone")) {
                                if (!jsonObject1.getString("zone").equals("")) {
                                    device.setAssign_room(jsonObject1.getString("zone"));
                                } else {
                                    device.setAssign_room("");
                                }
                            } else {
                                if (jsonObject1.has("room")) {
                                    if (!jsonObject1.getString("room").equals("")) {
                                        device.setAssign_room(jsonObject1.getString("room"));
                                    } else {
                                        device.setAssign_room("");
                                    }
                                }
                            }
                        } else {
                            if (jsonObject1.has("room")) {
                                if (!jsonObject1.getString("room").equals("")) {
                                    device.setAssign_room(jsonObject1.getString("room"));
                                } else {
                                    device.setAssign_room("");
                                }
                            }
                        }
                        boolean flag = false;
                        if (jsonObject1.has("Id")) {
                            Log.e("iiiiiiiddddddddddd", "" + jsonObject1.getString("Id"));
                            if (jsonObject1.getString("Id").contains("Airsmarthvac_zone"))
                                flag = true;
                            if (jsonObject1.getString("Id").contains("Bonairehvac_zone"))
                                flag = true;
                        }
                        if (!flag) arr_devices_list.add(device);
                    }
                    lst_devices_setting.setVisibility(View.VISIBLE);
                    txt_no_device.setVisibility(View.GONE);
                    rel_no_data.setVisibility(View.GONE);
                } else {
                    rel_loading.setVisibility(View.GONE);
                    lst_devices_setting.setVisibility(View.GONE);
                    txt_no_device.setVisibility(View.VISIBLE);
                    rel_no_data.setVisibility(View.VISIBLE);
                }
                CustomDeviceListAdapter customRoomItemAdapter = new CustomDeviceListAdapter(mcontext, arr_devices_list);
                lst_devices_setting.setAdapter(customRoomItemAdapter);
                lst_devices_setting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView txt_devices_title = view.findViewById(R.id.txt_devices_title);
                        TextView txt_devices_type = view.findViewById(R.id.txt_devices_type);
                        TextView txt_device_id = view.findViewById(R.id.txt_devices_id);
                        TextView txt_assind_room = view.findViewById(R.id.txt_assind_room);
                        Device device = new Device();
                        device.setDevice_id("" + txt_device_id.getText().toString());
                        device.setDevice_title("" + txt_devices_title.getText().toString());
                        device.setDevice_type("" + txt_devices_type.getText().toString());
                        device.setAssign_room("" + txt_assind_room.getText().toString());
                        App.setTemp_bundle(new Bundle());
                        App.setDevice(device);
                        Log.e("WhereIM", "IMINDeviceSetting");
                        if (App.isOrientationFlag())
                            ReplaceFragment.replaceFragment(DeviceSettingFragment.this, R.id.frm_filter_main_container, SettingDevicesLights.newInstance());
                        else
                            ReplaceFragment.replaceFragment(DeviceSettingFragment.this, R.id.frm_main_container, SettingDevicesLights.newInstance());
                    }
                });
            } catch (Exception ex) {
                Log.e("Setting_devices", "" + ex.getMessage());
            }
        }
    }
    @Override
    public void onDestroy() {
        Log.e("JsonDesitro","aaaaaaaaaaaaaaaaa");
        //  App.clearAppJSon();
        super.onDestroy();
        view = null;
        System.gc();
        lst_devices_setting.removeAllViewsInLayout();
    }
}
