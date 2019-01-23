package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.MyViewPagerAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Zone;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
/**
 * Created by developers on 17/5/17.
 */
public class HomeMainFragment extends Fragment {
    public static HomeMainFragment newInstance() {
        return new HomeMainFragment();
    }
    static String lati = "18.5081124";
    static String logi="73.7939685";
    @BindView(R.id.rel_no_data)RelativeLayout rel_no_data;
    @BindView(R.id.rel_home_health_tab)RelativeLayout rel_home_health_tab;
    @BindView(R.id.txt_no_rooms_available)TextView txt_no_rooms_available;
    @BindView(R.id.circular_progress_bar)ProgressBar circular_progress_bar;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    private MyViewPagerAdapter myViewPagerAdapter;
    @BindView(R.id.room_pager)ViewPager room_pager;
    Context mcontext;
    TextView[] dots;
    @BindView(R.id.layoutDots)LinearLayout dotsLayout;
    ArrayList<Fragment> layouts=new ArrayList<>();
    ArrayList<String> arr_sensor_id=new ArrayList<>();
    ArrayList<String> zone_id=new ArrayList<>();
    String hub_subcription;
    Bundle bundle,bundle2,bundle3;
    String TAG=HomeMainFragment.this.getClass().getName();
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
        v=inflater.inflate(R.layout.activity_home_main, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);
        MainActivity.bottom_menu_bar.setVisibility(View.VISIBLE);
         Logs.info(TAG+"_WhereIM","HomeMainFragment"+ App.getHubInfo());
        //callSensorAndHvac();
        App.clearSubscriber();
        App.clearFavSubscriber();
        setSubcriber();
        if (App.getSocket()!=null) {
            try {
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_hvac_zone_provisioned", new JSONObject()));
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_sensors_provisioned", new JSONObject()));
            } catch (Exception ex) {
                Logs.error(TAG+"_WhereIM","HomeMainFragment"+ ex.getMessage());
            }
        }
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
                 Logs.info(TAG+"_Responce_jhvjh",""+string);
                  if(string.equals("sensors_provisioned") )getZones();
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setHome_thermostat_subcriber(s);
    }
    public void callSensorAndHvac()
    {
        try {
            if (App.getHubInfo() != null) {
                hub_subcription = App.getHubInfo().getJSONArray("data").getJSONObject(0).getString("CML_HUB_SUBSCRIPTION");
                 Logs.info(TAG+"_hub_subcription",hub_subcription);
                if(hub_subcription.equals("Basic")) {
                    if (App.getSocket() != null) {
                        App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_hvac_zone_provisioned", new JSONObject()));
                        App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_sensors_provisioned", new JSONObject()));
                    }
                }
            }
        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_hub_info_error",ex.getMessage());
        }
    }
    public void getZones() {
        try {
            hub_subcription = App.getHubInfo().getJSONArray("data").getJSONObject(0).getString("CML_HUB_SUBSCRIPTION");
             Logs.info(TAG+"_Subscription",hub_subcription);
            if (hub_subcription.equals("Basic")) {
                setHomeHealthByZone();
            } else {
                setHomeHelthZone();
            }
        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_SubcriptionError",ex.getMessage());
        }
    }
    public void setHomeHealthByZone()
    {
         Logs.info(TAG+"_WhereIm","InsetHomeHealthByZone");
        layouts.clear();
        arr_sensor_id.clear();
        zone_id.clear();
        bundle = new Bundle();
        bundle.putString("CML_ID", "hvac");
        bundle.putString("CML_TITLE", "Whole Home");
        zone_id.add("hvac");
        HomeHeathByZoneFragment homehelthbyzone = HomeHeathByZoneFragment.newInstance();
        homehelthbyzone.setArguments(bundle);
        layouts.add(homehelthbyzone);
        try
        {
            if (App.getSensorsProvisionedJson() != null) {
                JSONArray jsonArray = App.getSensorsProvisionedJson().getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    arr_sensor_id.add(jsonObject.getString("room"));
                }
            }
        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_SensorReadError",""+ex.getMessage());
        }
         Logs.info(TAG+"_SensorData",arr_sensor_id.size()+"----"+ App.getSensorsProvisionedJson());
         Logs.info(TAG+"_HvacData",arr_sensor_id.size()+"----"+ App.getHvacZoneProvisionedJson());
        try {
            if (arr_sensor_id.size() > 0) {
                if (App.getHvacZoneProvisionedJson() != null) {
                    JSONArray jsonArray = App.getHvacZoneProvisionedJson().getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                      JSONObject jsonObject=jsonArray.getJSONObject(i);
                        if(arr_sensor_id.contains(jsonObject.getString("CML_ID")))
                        {
                            bundle = new Bundle();
                            bundle.putString("CML_ID", jsonObject.getString("CML_ID"));
                            bundle.putString("CML_TITLE", jsonObject.getString("CML_TITLE"));
                             Logs.info(TAG+"_hhhhhhhhhhhhHvac_id",jsonObject.getString("CML_ID"));
                            zone_id.add(jsonObject.getString("CML_ID"));
                            HomeHeathByZoneFragment homehelthbyzone1 = HomeHeathByZoneFragment.newInstance();
                            homehelthbyzone1.setArguments(bundle);
                            layouts.add(homehelthbyzone1);
                        }
                    }
                }
            }
             Logs.info(TAG+"_LayoutSize",""+layouts.size());
        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_HvacReadError",ex.getMessage());
        }
        if (layouts.size() > 0) {
            rel_no_data.setVisibility(View.GONE);
            rel_home_health_tab.setVisibility(View.VISIBLE);
        } else {
            rel_no_data.setVisibility(View.VISIBLE);
            rel_home_health_tab.setVisibility(View.GONE);
            txt_no_rooms_available.setVisibility(View.VISIBLE);
            circular_progress_bar.setVisibility(View.GONE);
        }
        addBottomDots(0);
        myViewPagerAdapter = new MyViewPagerAdapter(getActivity().getSupportFragmentManager(), layouts);
        room_pager.setAdapter(null);
        room_pager.setAdapter(myViewPagerAdapter);
        room_pager.setSaveFromParentEnabled(false);
        room_pager.addOnPageChangeListener(viewPagerPageChangeListener);
        requestForSensorByZone(0);
    }
    public void setHomeHelthZone()
    {
         Logs.info(TAG+"_HVacProvisined", "" + App.getZoneJson());
        try {
            JSONObject jsonObject = App.getZoneJson().getJSONArray("data").getJSONObject(0);
            layouts.clear();
            bundle = new Bundle();
            bundle.putString("CML_ID", jsonObject.getString("CML_ID"));
            bundle.putString("CML_TITLE", jsonObject.getString("CML_TITLE"));
            HomeHeathByZoneFragment homehelthbyzone = HomeHeathByZoneFragment.newInstance();
            homehelthbyzone.setArguments(bundle);
            layouts.add(homehelthbyzone);
            if (layouts.size() > 0) {
                rel_no_data.setVisibility(View.GONE);
                rel_home_health_tab.setVisibility(View.VISIBLE);
            } else {
                rel_no_data.setVisibility(View.VISIBLE);
                rel_home_health_tab.setVisibility(View.GONE);
                txt_no_rooms_available.setVisibility(View.VISIBLE);
                circular_progress_bar.setVisibility(View.GONE);
            }
            addBottomDots(0);
            myViewPagerAdapter = new MyViewPagerAdapter(getActivity().getSupportFragmentManager(), layouts);
            room_pager.setAdapter(null);
            room_pager.setAdapter(myViewPagerAdapter);
            room_pager.setSaveFromParentEnabled(false);
            room_pager.addOnPageChangeListener(viewPagerPageChangeListener);
            //requestForDevicesByRoom(0);
        } catch (Exception ex) {
             Logs.error(TAG+"_RoomTabError", "" + ex.getMessage());
        }
    }
    final ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
             Logs.info(TAG+"_onPageSelected","-------"+position);
            addBottomDots(position);
            requestForSensorByZone(position);
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
             Logs.info(TAG+"_onPageScrolled","-------"+arg0+"-----"+"------"+arg1+"-----"+arg2);
        }
        @Override
        public void onPageScrollStateChanged(int arg0) {
             Logs.info(TAG+"_eScrollStateChanged","-------"+arg0);
        }
    };
    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.size()];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(mcontext);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(mcontext.getResources().getColor(R.color.dot_dark_screen1));
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[currentPage].setTextColor(mcontext.getResources().getColor(R.color.dot_light_screen1));
    }
    public void requestForSensorByZone(int pos)
    {
        Zone zone=new Zone();
        zone.setZone_id(zone_id.get(pos));
        App.setZone(zone);
        App.setCurrentSubcriber(App.getArrsubcriber(pos));
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
}