package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.airwaternotification.NotificationReceiver;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.AlarmUtils;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.SwitchTrackChange;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
/**
 * The NotificationSettingFragment is for water and air filter notification set or unset
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class NotificationSettingFragment extends Fragment {
    public static NotificationSettingFragment newInstance() {
        return new NotificationSettingFragment();
    }
    View view;
    @BindView(R.id.txt_no_data)TextView txt_no_data;
    @BindView(R.id.lin_air_filter)LinearLayout lin_air_filter;
    @BindView(R.id.lin_water_filter)LinearLayout lin_water_filter;
    @BindView(R.id.swt_water_allow)Switch swt_water_allow;
    @BindView(R.id.swt_air_allow)Switch swt_air_allow;
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @BindView(R.id.rel_loading)RelativeLayout rel_loading;
    String TAG=NotificationSettingFragment.this.getClass().getName();
    @BindView(R.id.img_back)ImageView img_back;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    PrefManager prefManager;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    Context mcontext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification_setting, container, false);
        mcontext=view.getContext();
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();
        ButterKnife.bind(this,view);
        txt_fragment_title.setText("Notification Settings");
        prefManager=new PrefManager(mcontext);
        if(new PrefManager(mcontext).getValue("air_filter").equals("1")) swt_air_allow.setChecked(true);
        else swt_air_allow.setChecked(false);
        if(new PrefManager(mcontext).getValue("water_filter").equals("1")) swt_water_allow.setChecked(true);
        else swt_water_allow.setChecked(false);
        SwitchTrackChange.changeTrackColor(mcontext,swt_water_allow);
        SwitchTrackChange.changeTrackColor(mcontext,swt_air_allow);
        swt_air_allow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SwitchTrackChange.changeTrackColor(mcontext,swt_air_allow);
                if(isChecked) new PrefManager(mcontext).setValue("air_filter","1");
                else new PrefManager(mcontext).setValue("air_filter","0");
                emitNotification("Air");
                if(!isChecked) {
                    Intent myIntent = new Intent(mcontext, NotificationReceiver.class);
                    AlarmUtils.cancelAllAlarms(mcontext, myIntent, 2);
                }
               /*else
               {
                   try {
                       Intent myIntent = new Intent(mcontext, NotificationReceiver.class);
                       Calendar expdate = Calendar.getInstance();
                       expdate.setTime(sdf.parse("2018-03-03T10:00:00.000Z"));
                       myIntent.putExtra("title", "Air" + " Filter Notication");
                       myIntent.putExtra("msg", "");
                       myIntent.putExtra("type", "Air");
                       myIntent.putExtra("date", "");
                       String id = 2 + "" + createID() + "" + 0;
                        Logs.info(TAG+"_NotificationId", "" + id);
                       AlarmUtils.addAlarm(mcontext, myIntent, Integer.parseInt(id), expdate);
                   }
                   catch (Exception ex)
                   {
                        Logs.info(TAG+"_NotificationSetError",""+ex.getMessage());
                   }
               }*/
            }
        });
        swt_water_allow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) new PrefManager(mcontext).setValue("water_filter","1");
                else new PrefManager(mcontext).setValue("water_filter","0");
                SwitchTrackChange.changeTrackColor(mcontext,swt_water_allow);
                emitNotification("Water");
                if(!isChecked) {
                    Intent myIntent = new Intent(mcontext, NotificationReceiver.class);
                    AlarmUtils.cancelAllAlarms(mcontext, myIntent, 1);
                }
            }
        });

        FontUtility.setAppFont(mContainer, mFont);
        getWaterAndAirDevice();
        setSubcriber();
        return view;
    }
    public static void emitNotification(String type)
    {
        try {
            Log.i("emit_zzzzzzzzzzzzz", ""+type );

            JSONObject obj = new JSONObject();
            obj.put("data", new JSONObject().put("type", new JSONArray().put(type)));
            obj.put("type", "get_notification");
            Logs.info("NotificationSettingFragemnt"+"_SimulationRequest", "" + obj);
            App.getSocket().emit("action", obj);
        }
        catch (Exception ex){
            ex.printStackTrace();
            Log.e("Notification_Error_",""+ex.getMessage());
        }
    }
    public void getWaterAndAirDevice()
    {
        Bundle bundle=new Bundle();
        boolean water=false,air=false;
        try {
            JSONArray jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
            if(!jsonArray.toString().equals("[]")) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getString("type").equals("Water")) {
                        if (!jsonObject.getString("room").equals("")) {
                            Logs.info(TAG+"_Zone_id", jsonObject.getString("room"));
                            bundle.putString("water_zone_id", jsonObject.getString("room"));
                            App.setTemp_bundle(bundle);
                            water = true;
                        }
                    }
                    if (jsonObject.getString("type").equals("Air")) {
                        if (!jsonObject.getString("room").equals("")) {
                            air = true;
                            bundle.putString("air_zone_id", jsonObject.getString("room"));
                            App.setTemp_bundle(bundle);
                        }
                    }
                    if (air) {
                        lin_air_filter.setVisibility(View.VISIBLE);
                        rel_loading.setVisibility(View.GONE);
                        txt_no_data.setVisibility(View.GONE);
                    }
                    if (water) {
                        lin_water_filter.setVisibility(View.VISIBLE);
                        rel_loading.setVisibility(View.GONE);
                        txt_no_data.setVisibility(View.GONE);
                    }
                    if (!air && !water) {
                        lin_water_filter.setVisibility(View.GONE);
                        lin_air_filter.setVisibility(View.GONE);
                        rel_loading.setVisibility(View.GONE);
                        txt_no_data.setVisibility(View.VISIBLE);
                    }
                }
            }
            else
            {
                lin_water_filter.setVisibility(View.GONE);
                lin_air_filter.setVisibility(View.GONE);
                rel_loading.setVisibility(View.GONE);
                txt_no_data.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception ex)
        {
            Logs.error(TAG+"_WaterAirError",ex.getMessage());
        }
    }
    private void setSubcriber() {
        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("" + App.getRoomData());
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
                if(App.getProvisionalDevicesData()!=null)
                {
                    getWaterAndAirDevice();
                }
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);
    }
    public static int createID(){
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(now));
        return id;
    }
    @OnClick(R.id.img_back)
    public void img_back()
    {
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(NotificationSettingFragment.this,R.id.frm_filter_main_container, SettingMainFragment.newInstance());
        else ReplaceFragment.replaceFragment(NotificationSettingFragment.this, R.id.frm_main_container, SettingMainFragment.newInstance());
    }
}