package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.indoorairquality;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.D3data;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.CustomHvacZoneAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.D3screen;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.HomeHeathByZoneFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Room;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Zone;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.TimeFormatChange;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;
import com.github.lzyzsd.circleprogress.DonutProgress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import static com.google.android.gms.internal.zzagz.runOnUiThread;

public class IndoorTabletnew extends DialogFragment  {

    private static final String TAG = IndoorTabletnew.class.getName();

    String hub_subcription;

    private String zone_type;
    public static String room_color="";
    private boolean userIsInteracting=false;
    private String new_zone_id="";
    boolean zoneselection=false;

    private Timer timer_sun=null;
    private TimerTask tsk_sun=null;

    public static IndoorTabletnew newInstance() {
        return new IndoorTabletnew();
    }

    String[] spinner_values = new String[] { "All Sensors (Average)" };
    ArrayList<String> newObj = new ArrayList<String>(Arrays.asList(spinner_values));


    String zone_title = "";
    final List<String> zoneList = new ArrayList<>(Arrays.asList(spinner_values));

    Double co2_value,pm25_vaalue,pm10_vlaue,tvoc_value=0.0;
    double iaqscore = 0;

    Spinner spinner;
    ViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter;
    ImageButton leftNav,rightNav;
    FrameLayout viewPagerContainer;
    ArrayList<Zone> ssidList=new ArrayList<>();
    public static int new_score_co2=0;
    public static String new_color_co2="";

    public static int new_score_pm2=0;
    public static String new_color_pm2="";

    public static int new_score_pm10=0;
    public static String new_color_pm10="";

    public static int new_score_tvoc=0;

    String sensor_mac_id="";
    String sensor_name="";

    @BindView(R.id.close_nav)ImageView close_nav;
    @BindView(R.id.histoty_d3)Button button1;


    private View v;
    Context mcontext;
    Activity mactivity;
    String hub_type="";


    @BindView(R.id.txt_aqi_status)TextView txt_quality;
    @BindView(R.id.co2score)TextView co2_text;
    @BindView(R.id.pm2_5score)TextView pm2_5text;
    @BindView(R.id.pm10_score)TextView pm10_text;
    @BindView(R.id.tvoc_score)TextView tvoc_score;
    @BindView(R.id.tvoc_text_descp)TextView tvoc_text_descp;
    @BindView(R.id.simpleChronometer)
    Chronometer simpleChronometer;


    @BindView(R.id.score_no_data)
    TextView score_no_data;
    @BindView(R.id.mid)
    LinearLayout mid;
    private Observable<String> mobservable;
    private ArrayList<Room> arr_room_list = new ArrayList<>();
    private Observer<String> myObserver;
    @BindView(R.id.aqi_graph)
    DonutProgress aqi_graph;
    @BindView(R.id.pm10prog)
    DonutProgress pm10prog;
    @BindView(R.id.pm2prog)
    DonutProgress pm2prog;
    @BindView(R.id.COprog)DonutProgress COprog;
    @BindView(R.id.tvocprog)DonutProgress tvocprog;
    @BindView(R.id.txt_time)
    TextView txt_time;
    @BindView(R.id.lin_activity_main)LinearLayout lin_activity_main;
    String name="All Sensors";
    CustomSpinnerAdapter adapter;
    ArrayList<String> arr_sensor_id=new ArrayList<>();
    ArrayList<Zone> arr_zone=new ArrayList<>();
    ArrayList<String> zone_id=new ArrayList<>();
    private Double PM2percenage, PM10percenage, COpercenage, TVOCper;
    private Double PM2value, PM10value, COvalue;
    ArrayAdapter<Zone> spinnerArrayAdapter;
    String list_data_type="";
    private String c="CO\u2082";
    D3data d3 = new D3data();
    D3screen indoor1 = new D3screen();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_indoor_air_quality_new, container,false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.getDialog().setCanceledOnTouchOutside(true);

        mcontext = v.getContext();
        mactivity = getActivity();
        ButterKnife.bind(this, v);
        UserInteractionSleep.siboSleepChecking(mcontext);
        timer_sun = new Timer();
        startClockTime();

        spinner = v.findViewById(R.id.spinner);
        simpleChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                setTime();

            }
        });
        simpleChronometer.start();

        App.setPosition(-1);
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                userIsInteracting=true;
                return false;
            }
        });

        if (App.getSocket()!=null) {
            try {

                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_date_time", new JSONObject()));
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_sensors_provisioned", new JSONObject()));
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_zones", new JSONObject()));
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_info", new JSONObject()));
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_hvac_zone_provisioned", new JSONObject()));
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_rooms", new JSONObject()));


            } catch (Exception ex) {
                System.out.println("Indoor AQ error");
            }
        }



        try {
            hub_type = App.getHubInfo().getJSONArray("data").getJSONObject(0).getString("CML_HUB_SUBSCRIPTION");
        }
        catch (Exception ex)
        {
            Log.e("Hub_type",""+ex.toString());
        }

        if(hub_type.equals("Basic"))
        {

            Log.e("hvac_flag",""+checkHVAC());
            if(checkHVAC())
            {
                zone_type="hvac_zone";
                list_data_type="hvac_sensor_zones";

            }
            else
            {
                zone_type="zone";
                list_data_type="sensor_zones";
            }

        }
        else {
            list_data_type="sensor_rooms";
        }

        /***************/

      /*  COprog.setOnClickListener(this);
        pm2prog.setOnClickListener(this);
        pm10prog.setOnClickListener(this);*/

        viewPagerContainer= v.findViewById(R.id.viewPagercontainer);
        viewPagerContainer.setVisibility(View.GONE);

        viewPager= v.findViewById(R.id.viewPager);
        sectionsPagerAdapter=new SectionsPagerAdapter(getChildFragmentManager());
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(sectionsPagerAdapter);
        leftNav = v.findViewById(R.id.left_nav);
        rightNav = v.findViewById(R.id.right_nav);
        //  closeNav = (ImageButton)v.findViewById(R.id.close_nav);
        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tab = viewPager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    viewPager.setCurrentItem(tab,true);
                } else if (tab == 0) {
                    viewPager.setCurrentItem(tab,true);
                }
            }
        });
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tab = viewPager.getCurrentItem();
                tab++;
                viewPager.setCurrentItem(tab,true);
            }
        });
        button1.setVisibility(View.GONE);
        setSubcriber();

        lin_activity_main.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                UserInteractionSleep.userInteract(mcontext);

                return false;
            }
        });



        return v;
    }

    public boolean checkHVAC()
    {
        boolean flag=false;
        try {
            JSONArray jsonArray=App.getProvisionalDevicesData().getJSONArray("data");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("type").equals("HVAC")) {
                    flag=true;
                }

            }

        }
        catch (Exception ex)
        {
            Log.e("HVACErrror",""+ex.getMessage());
        }

        return  flag;
    }

    private void getZoneName() {


        try
        {
            Log.e("list_data_type",""+list_data_type);
            ssidList.clear();
            final Zone zone=new Zone();
            zone.setZone_title("All Monitors");
            zone.setZone_score(HomeHeathByZoneFragment.indoor_aqi_score);
            ssidList.add(zone);
            switch (list_data_type) {
                case "sensor_rooms":

                    if (App.getRoomData() != null) {
                        JSONObject jsonObjectroom = App.getRoomData();
                        JSONArray jsonArrayroom = jsonObjectroom.getJSONArray("data");
                        Log.d(TAG, "Array_getRoomDeviceData : " + jsonArrayroom);

                        for (int j = 0; j < jsonArrayroom.length(); j++) {
                            JSONObject room = jsonArrayroom.getJSONObject(j);
                            if(check_sensor(""+room.getString("CML_ID"))) {
                                final Zone s = new Zone();
                                s.setZone_title("" + room.getString("CML_TITLE"));
                                s.setZone_id("" + room.getString("CML_ID"));
                                s.setZone_score((100-room.getInt("CML_AQI_SCORE")));
                                //room_color=room.getInt("CML_AQI_SCORE");

                                ssidList.add(s);
                                //getAllValues("" + room.getString("CML_ID"));
                            }

                            Log.e("SSIDList_room", "" + ssidList);
                        }
                    }
                    break;
                case "hvac_sensor_zones":


                    if(App.getHvacZoneProvisionedJson()!=null&&checkHVAC()) {
                        JSONArray NzonesjsonArray = App.getHvacZoneProvisionedJson().getJSONArray("data");
                        for (int j = 0; j < NzonesjsonArray.length(); j++) {
                            JSONObject NzonesjsonObject = NzonesjsonArray.getJSONObject(j);
                            // if (NzonesjsonObject.getString("CML_ID").equals(str_room_id)) {
                            Log.e("device_id_litht", "Got.....HVACzone");

                            //  }
                            if(check_sensor(""+NzonesjsonObject.getString("CML_ID"))) {


                                final Zone s = new Zone();
                                s.setZone_title("" + NzonesjsonObject.getString("CML_TITLE"));
                                s.setZone_id("" + NzonesjsonObject.getString("CML_ID"));
                                s.setZone_score((100-NzonesjsonObject.getInt("CML_AQI_SCORE")));

                                //getAllValues(""+NzonesjsonObject.getString(""+NzonesjsonObject.getString("CML_ID")));
                                ssidList.add(s);
                            }

                            Log.e("SSIDList_hvac", "" + ssidList);

                        }

                    }
                    break;
                case "sensor_zones":
                    try {
                        if(App.getZoneJson()!=null&&!checkHVAC()) {
                            JSONArray NzonesjsonArray = App.getZoneJson().getJSONArray("data");
                            Log.i("SensorReadErrorJJDD",""+NzonesjsonArray);
                            for (int j = 0; j < NzonesjsonArray.length(); j++) {
                                JSONObject NzonesjsonObject = NzonesjsonArray.getJSONObject(j);
                                Log.e("device_id_litht", "Got.....Szone");
                                if(check_sensor(""+NzonesjsonObject.getString("CML_ID"))) {
                                    final Zone s = new Zone();
                                    Log.e("SSIDList_hvacZone", ""+NzonesjsonObject.getString("CML_ID"));

                                    s.setZone_title("" + NzonesjsonObject.getString("CML_TITLE"));
                                    s.setZone_id("" + NzonesjsonObject.getString("CML_ID"));
                                    s.setZone_score((100-NzonesjsonObject.getInt("CML_AQI_SCORE")));

                                    ssidList.add(s);
                                    // getAllValues("" + NzonesjsonObject.getString("CML_ID"));
                                }

                                Log.e("SSIDList_hvac", "" + ssidList.get(j));

                            }

                        }

                    }catch (Exception e){
                        Log.e("SensorReadErrorJD",""+e.getMessage());
                    }

                    break;
            }

            spinner.setOnItemSelectedListener(null);
            CustomHvacZoneAdapter adapter = new CustomHvacZoneAdapter(mcontext, ssidList);
            spinner.setAdapter(adapter);
            if(App.getPosition()!=-1)
            {
                spinner.setSelection(App.getPosition(),false);
            }
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tvoc_value=0.0;
                    App.setPosition(position);
                    getSensorId(ssidList.get(position).getZone_id());
                    if(userIsInteracting) {
                        try {
                            if(position==0){
                               // Toast.makeText(mcontext, "postion 0", Toast.LENGTH_SHORT).show();
                                getWholeIAQScore();
                                getAllWholeHomeValues(""+ssidList.get(position).getZone_id());
                                zoneselection=false;
                                //getAllValues();
                                button1.setVisibility(View.GONE);

                            }
                            else {
                                JSONObject obj = new JSONObject();
                                obj.put("data", "" + ssidList.get(position).getZone_id());
                                obj.put("type", "get_sensor_reading");
                                Log.e("SensorReadingRequest", "" + obj);
                                App.getSocket().emit("action", obj);
                                new_zone_id = ssidList.get(position).getZone_id();
                                zoneselection=true;
                                onSelectedItem(""+new_zone_id);
                                //      getHvacScore();
                                button1.setVisibility(View.VISIBLE);

                                zone_title=ssidList.get(position).getZone_title();

                            }
                        } catch (JSONException e) {
                            Log.e("Error in emit", "" + e.getMessage());
                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    button1.setEnabled(false);
                    button1.setAlpha(0.5f);
                }
            });

        }
        catch (Exception ex)
        {
            Log.e("SensorReadError",""+ex.getMessage());
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




                getZoneName();



                if(zoneselection==true) {
                    onSelectedItem(new_zone_id);
                }
                else {
                    getWholeIAQScore();
                    getAllWholeHomeValues(""+getWholeHome());
                    Log.e("Responce_zones", "" + string);
                }

            }


        };
        Subcriber s = new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);


    }

    private String getWholeHome() {

        String homeID="";

        try {
            JSONArray jsonArray = App.getZoneJson().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);


                if (jsonObject.getString("CML_TITLE").equals("Whole Home")) {
                    Log.e("Pro sensor data", "" + jsonObject);
                    homeID=jsonObject.getString("CML_ID");
                }
            }
        }catch (JSONException e){
            Log.e("Exception",""+e.getMessage());
        }

        return  homeID;

    }

    public void onSelectedItem(String new_zone_id) {
        String zone=new_zone_id;
        long original_score = Math.round(getZoneIAQScore(new_zone_id));
        //getAllValues(zone);
        long score=100-original_score;
        if (score < 0) {

            score_no_data.setVisibility(View.VISIBLE);
            score_no_data.setText("No Data Available");
            aqi_graph.setVisibility(View.GONE);
            aqi_graph.setVisibility(View.GONE);
        }
        if(original_score>=0) {
            getAllValues(""+zone);
            aqi_graph.setVisibility(View.VISIBLE);
            aqi_graph.setText("" + score);
            aqi_graph.setStartingDegree(270);
            aqi_graph.setProgress(score);
            if (score >= 0 && score <= 39) {
                //   score_no_data.setText("SCORE");
                txt_quality.setVisibility(View.VISIBLE);
                txt_quality.setText("Poor");
                aqi_graph  .setFinishedStrokeColor(Color.rgb(246,103,101));


            } else if (score >= 40 && score <= 59) {
                txt_quality.setText("Moderate");
                aqi_graph.setFinishedStrokeColor(Color.rgb(229,197,0));


            } else if (score >= 60 && score <= 69) {
                txt_quality.setText("Moderate");
                aqi_graph.setFinishedStrokeColor(Color.rgb(229,197,0));

            } else if (score >= 70 && score <= 79) {
                txt_quality.setText("Moderate");
                aqi_graph.setFinishedStrokeColor(Color.rgb(229,197,0));

            } else if (score >= 80 && score <= 89) {

                txt_quality.setText("Good");
                aqi_graph.setFinishedStrokeColor(Color.rgb(68,255,233));


            }
            else if (score >= 90 && score <= 100) {
                txt_quality.setText("Excellent");
                aqi_graph.setFinishedStrokeColor(Color.rgb(68,255,233));

            }}else {
            txt_quality.setText("");
            aqi_graph.setText("");

            mid.setVisibility(View.GONE);
            button1.setVisibility(View.GONE);
            score_no_data.setVisibility(View.VISIBLE);
            score_no_data.setText("No data available");

        }


    }


    @OnClick(R.id.histoty_d3)
    public void histoty_d3()
    {

        Bundle bundle=new Bundle();
        bundle.remove("mac_id");
        // bundle.putString("mac_id","7664c437-4152-4899-8a4d-8bbe0b2a0f47");
        // bundle.putString("mac_id","53fade90-ea49-4744-8287-b82e566f8978");
        bundle.putString("mac_id",""+sensor_mac_id);
        bundle.putString("zone_title",""+zone_title);
        Log.e("sensor_mac_id",""+bundle);
        App.setTemp_bundle(bundle);


        //   d3.show(getFragmentManager(),"indoorDialog");
        indoor1.show(getFragmentManager(),"indoorDialog");
    }



    private void getAllWholeHomeValues(String id) {
        try {
            JSONArray jsonArray = App.getSensorsData().getJSONArray("data");
            Log.i("gd",""+jsonArray);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
               // if (jsonObject.getString("CML_TITLE").equals("Whole Home")) {
                    // if(jsonObject.has("room")&& jsonObject.getString("room").equals(id)) {
                    Log.i("room###: "+jsonObject.getString("room"),"  id:  "+id);
                    if (jsonObject.has("CML_TYPE") && jsonObject.getString("CML_TYPE").equals("air#carbon_dioxide")) {
                        Log.e("Pro sensor data", "" + jsonObject);
                        co2_value = jsonObject.getDouble("CML_VALUE");
                        Log.e("co2_value", "" + co2_value);
                        progco2();

                    }
                    if (jsonObject.has("CML_TYPE") && jsonObject.getString("sensor_type").equals("33")) {

                        pm25_vaalue = jsonObject.getDouble("CML_VALUE");
                        Log.e("pm25_vaalue", "" + pm25_vaalue);

                        progpm25();
                    }
                    if (jsonObject.has("CML_TYPE") && jsonObject.getString("CML_TYPE").equals("air#pm10")) {

                        pm10_vlaue = jsonObject.getDouble("CML_VALUE");
                        Log.e("pm10_vlaue", "" + pm10_vlaue);

                        progpm10();
                    }

                    if (jsonObject.has("CML_TYPE") && jsonObject.getString("CML_TYPE").equals("air#tvoc")) {

                        //  tvoc_value = jsonObject.getDouble("CML_TVOC");
                        tvoc_value = jsonObject.getDouble("CML_VALUE");
                        Log.e("tvoc_value", "" + tvoc_value);
                        progtvoc();

                    }
                }
                // progtvoc();

            // }


        } catch (Exception ex) {
            Log.e("IAQ SCORe", ex.getMessage());
        }
    }
    private void getAllValues(String id) {
        try {
            JSONArray jsonArray = App.getSensorsData().getJSONArray("data");
            Log.i("gd",""+jsonArray);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (!jsonObject.getString("CML_TITLE").equals("Whole Home")) {
                // if(jsonObject.has("room")&& jsonObject.getString("room").equals(id)) {
                Log.i("room###: "+jsonObject.getString("room"),"  id:  "+id);
                if (jsonObject.has("CML_TYPE") && jsonObject.getString("CML_TYPE").equals("air#carbon_dioxide")) {
                    Log.e("Pro sensor data", "" + jsonObject);
                    co2_value = jsonObject.getDouble("CML_VALUE");
                    Log.e("co2_value", "" + co2_value);
                    progco2();

                }
                if (jsonObject.has("CML_TYPE") && jsonObject.getString("sensor_type").equals("33")) {

                    pm25_vaalue = jsonObject.getDouble("CML_VALUE");
                    Log.e("pm25_vaalue", "" + pm25_vaalue);

                    progpm25();
                }
                if (jsonObject.has("CML_TYPE") && jsonObject.getString("CML_TYPE").equals("air#pm10")) {

                    pm10_vlaue = jsonObject.getDouble("CML_VALUE");
                    Log.e("pm10_vlaue", "" + pm10_vlaue);

                    progpm10();
                }

                if (jsonObject.has("CML_TYPE") && jsonObject.getString("CML_TYPE").equals("air#tvoc")) {

                    //  tvoc_value = jsonObject.getDouble("CML_TVOC");
                    tvoc_value = jsonObject.getDouble("CML_VALUE");
                    Log.e("tvoc_value", "" + tvoc_value);
                    progtvoc();

                }
            }
            // progtvoc();
             }
            // }


        } catch (Exception ex) {
            Log.e("IAQ SCORe", ex.getMessage());
        }

    }
    private void progtvoc() {

        int a=  (int) Math.round(tvoc_value);
        tvoc_score.setText(""+a);
        new_score_tvoc=a;
        if(new_score_tvoc==0){
            tvoc_text_descp.setText("No Data");
        }
        if(new_score_tvoc>0 && new_score_tvoc<=200){
            tvoc_text_descp.setText("Low");
        }
        else if(new_score_tvoc>=201 && new_score_tvoc<=500){
            tvoc_text_descp.setText("Medium");
        }
        else if(new_score_tvoc>=501){
            tvoc_text_descp.setText("High");
        }



    }

    private void progpm10() {



        Double _percent = (pm10_vlaue / 150);

        PM10value = _percent * 100;


        PM10percenage = Math.abs(100-PM10value);
        Log.e("air#pm10", "" + PM10percenage);
        // new_score_pm10=PM10value.intValue();
        new_score_pm10=PM10percenage.intValue();

        //pm10prog.setProgress(PM10value.intValue());
        pm10prog.setProgress(PM10percenage.intValue());


        if (PM10percenage >= 1 && PM10percenage <= 39) {
            //   score_no_data.setText("SCORE");
            pm10_text.setText("Poor");
            pm10prog.setFinishedStrokeColor(Color.rgb(246,103,101));


        } else if (PM10percenage >= 40 && PM10percenage <= 59) {
            pm10_text.setText("Moderate");
            pm10prog.setFinishedStrokeColor(Color.rgb(229,197,0));


        } else if (PM10percenage >= 60 && PM10percenage <= 69) {
            pm10_text.setText("Moderate");
            pm10prog.setFinishedStrokeColor(Color.rgb(229,197,0));

        } else if (PM10percenage >= 70 && PM10percenage <= 79) {
            pm10_text.setText("Moderate");
            pm10prog.setFinishedStrokeColor(Color.rgb(229,197,0));

        } else if (PM10percenage >= 80 && PM10percenage <= 89) {

            pm10_text.setText("Good");
            pm10prog.setFinishedStrokeColor(Color.rgb(68,255,233));


        }
        else if (PM10percenage >= 90 && PM10percenage <= 100) {
            pm10_text.setText("Excellent");
            pm10prog.setFinishedStrokeColor(Color.rgb(68,255,233));

        }





    }

    private void progpm25() {

        // pm2prog.setProgress(pm25_vaalue);
        //    pm2prog.setText("" +  Math.round(pm25_vaalue));
        Double _percent = (pm25_vaalue / 41);
        PM2value = _percent * 100;


        PM2percenage = Math.abs(100-PM2value);
        Log.e("air#pm25", "" + PM2percenage);
        //  new_score_pm2=PM2value.intValue();
        new_score_pm2=PM2percenage.intValue();

        // pm2prog.setProgress(PM2value.intValue());
        pm2prog.setProgress(PM2percenage.intValue());

        if (PM2percenage >= 1 && PM2percenage <= 39) {
            //   score_no_data.setText("SCORE");
            pm2_5text.setText("Poor");
            pm2prog.setFinishedStrokeColor(Color.rgb(246,103,101));


        } else if (PM2percenage >= 40 && PM2percenage <= 59) {
            pm2_5text.setText("Moderate");
            pm2prog.setFinishedStrokeColor(Color.rgb(229,197,0));


        } else if (PM2percenage >= 60 && PM2percenage <= 69) {
            pm2_5text.setText("Moderate");
            pm2prog.setFinishedStrokeColor(Color.rgb(229,197,0));

        } else if (PM2percenage >= 70 && PM2percenage <= 79) {
            pm2_5text.setText("Moderate");
            pm2prog.setFinishedStrokeColor(Color.rgb(229,197,0));

        } else if (PM2percenage >= 80 && PM2percenage <= 89) {

            pm2_5text.setText("Good");
            pm2prog.setFinishedStrokeColor(Color.rgb(68,255,233));


        }
        else if (PM2percenage >= 90 && PM2percenage <= 100) {
            pm2_5text.setText("Excellent");
            pm2prog.setFinishedStrokeColor(Color.rgb(68,255,233));

        }


    }

    private void progco2() {

        Double _percent = (co2_value / 5000);
        COvalue= _percent * 100;
        Log.e("air#carbon_dioxide", "" + COpercenage);



        COpercenage = Math.abs(100-COvalue);
        //COprog.setProgress(COvalue.intValue());
        COprog.setProgress(COpercenage.intValue());
        // new_score_co2=COvalue.intValue();
        new_score_co2=COpercenage.intValue();


        if (COpercenage >= 1 && COpercenage <= 39) {
            //   score_no_data.setText("SCORE");
            co2_text.setText("Poor");
            COprog.setFinishedStrokeColor(Color.rgb(246,103,101));


        } else if (COpercenage >= 40 && COpercenage <= 59) {
            co2_text.setText("Moderate");
            COprog.setFinishedStrokeColor(Color.rgb(229,197,0));


        } else if (COpercenage >= 60 && COpercenage <= 69) {
            co2_text.setText("Moderate");
            COprog.setFinishedStrokeColor(Color.rgb(229,197,0));

        } else if (COpercenage >= 70 && COpercenage <= 79) {
            co2_text.setText("Moderate");
            COprog.setFinishedStrokeColor(Color.rgb(229,197,0));

        } else if (COpercenage >= 80 && COpercenage <= 89) {

            co2_text.setText("Good");
            COprog.setFinishedStrokeColor(Color.rgb(68,255,233));


        }
        else if (COpercenage >= 90 && COpercenage <= 100) {
            co2_text.setText("Excellent");
            COprog.setFinishedStrokeColor(Color.rgb(68,255,233));

        }
    }


    private double getWholeIAQScore() {
        int wholeiaqvalue=0;

        double tvoc = 0;
        try {
            JSONArray jsonArray = App.getZoneJson().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);


                if (jsonObject.getString("CML_TITLE").equals("Whole Home")) {
                    Log.e("Pro sensor data", "" + jsonObject);
                    iaqscore = jsonObject.getDouble("CML_AQI_SCORE");
                    //working here
                    getAllWholeHomeValues(""+jsonObject.getString("CML_ID"));
                    Log.e("IAQSCOre", "" + iaqscore);
                    if(jsonObject.has("CML_TVOC")) {
                        tvoc = jsonObject.getDouble("CML_TVOC");
                    }

                }

            }
            int a=  (int) Math.round(tvoc);

            tvoc_score.setText(""+a);
            new_score_tvoc=a;
            if(new_score_tvoc==0){
                tvoc_text_descp.setText("No Data");
            }


            if (iaqscore == 0) {
                aqi_graph.setText("0");
                aqi_graph.setProgress(0);
            }



            long original_score = Math.round(iaqscore);
            long score = 100-original_score;
            if (score == 0) {

                score_no_data.setVisibility(View.VISIBLE);
                score_no_data.setText("No Data Available");
                aqi_graph.setVisibility(View.GONE);
                aqi_graph.setVisibility(View.GONE);
            }
            if (original_score > 0) {
                aqi_graph.setVisibility(View.VISIBLE);
                aqi_graph.setText("" + score);
                aqi_graph.setStartingDegree(270);
                aqi_graph.setProgress(score);
                if (score >= 1 && score <= 39) {
                    //   score_no_data.setText("SCORE");
                    txt_quality.setVisibility(View.VISIBLE);
                    txt_quality.setText("Poor");
                    aqi_graph.setFinishedStrokeColor(Color.rgb(246, 103, 101));
                    room_color = "RED";

                }  else if (score >= 40 && score <= 59) {
                    txt_quality.setText("Moderate");
                    aqi_graph.setFinishedStrokeColor(Color.rgb(229,197,0));
                    room_color = "RED";

                } else if (score >= 60 && score <= 69) {
                    txt_quality.setText("Moderate");
                    aqi_graph.setFinishedStrokeColor(Color.rgb(229,197,0));
                    room_color = "RED";
                } else if (score >= 70 && score <= 79) {
                    txt_quality.setText("Moderate");
                    aqi_graph.setFinishedStrokeColor(Color.rgb(229,197,0));
                    room_color = "Yellow";
                } else if (score >= 80 && score <= 89) {

                    txt_quality.setText("Good");
                    aqi_graph.setFinishedStrokeColor(Color.rgb(68,255,233));
                    room_color = "Yellow";

                } else if (score >= 90 && score <= 100) {
                    txt_quality.setText("Excellent");
                    aqi_graph.setFinishedStrokeColor(Color.rgb(68, 255, 233));
                    room_color = "Green";
                }
            } else {
                txt_quality.setText("");
                aqi_graph.setText("");

                mid.setVisibility(View.GONE);
                button1.setVisibility(View.GONE);
                score_no_data.setVisibility(View.VISIBLE);
                score_no_data.setText("No data available");

            }


        } catch (Exception ex) {
            Log.e("IAQ SCORe", ex.getMessage());
        }


        return wholeiaqvalue;
    }
    private double getIAQScore() {

        double tvoc = 0;
        try {
            JSONArray jsonArray = App.getZoneJson().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);


                if (jsonObject.getString("CML_TITLE").equals("Whole Home")) {
                    Log.e("Pro sensor data", "" + jsonObject);
                    iaqscore = jsonObject.getDouble("CML_AQI_SCORE");
                    //working here
                    getAllValues(""+jsonObject.getString("CML_ID"));
                    Log.e("IAQSCOre", "" + iaqscore);
                    if(jsonObject.has("CML_TVOC")) {
                        tvoc = jsonObject.getDouble("CML_TVOC");
                    }

                }

            }
            int a=  (int) Math.round(tvoc);

            tvoc_score.setText(""+a);
            new_score_tvoc=a;
            if(new_score_tvoc==0){
                tvoc_text_descp.setText("No Data");
            }


            if (iaqscore == 0) {
                aqi_graph.setText("0");
                aqi_graph.setProgress(0);
            }



            long original_score = Math.round(iaqscore);
            long score = 100-original_score;
            if (score == 0) {

                score_no_data.setVisibility(View.VISIBLE);
                score_no_data.setText("No Data Available");
                aqi_graph.setVisibility(View.GONE);
                aqi_graph.setVisibility(View.GONE);
            }
            if (original_score > 0) {
                aqi_graph.setVisibility(View.VISIBLE);
                aqi_graph.setText("" + score);
                aqi_graph.setStartingDegree(270);
                aqi_graph.setProgress(score);
                if (score >= 1 && score <= 39) {
                    //   score_no_data.setText("SCORE");
                    txt_quality.setVisibility(View.VISIBLE);
                    txt_quality.setText("Poor");
                    aqi_graph.setFinishedStrokeColor(Color.rgb(246, 103, 101));
                    room_color = "RED";

                }  else if (score >= 40 && score <= 59) {
                    txt_quality.setText("Moderate");
                    aqi_graph.setFinishedStrokeColor(Color.rgb(229,197,0));
                    room_color = "RED";

                } else if (score >= 60 && score <= 69) {
                    txt_quality.setText("Moderate");
                    aqi_graph.setFinishedStrokeColor(Color.rgb(229,197,0));
                    room_color = "RED";
                } else if (score >= 70 && score <= 79) {
                    txt_quality.setText("Moderate");
                    aqi_graph.setFinishedStrokeColor(Color.rgb(229,197,0));
                    room_color = "Yellow";
                } else if (score >= 80 && score <= 89) {

                    txt_quality.setText("Good");
                    aqi_graph.setFinishedStrokeColor(Color.rgb(68,255,233));
                    room_color = "Yellow";

                } else if (score >= 90 && score <= 100) {
                    txt_quality.setText("Excellent");
                    aqi_graph.setFinishedStrokeColor(Color.rgb(68, 255, 233));
                    room_color = "Green";
                }
            } else {
                txt_quality.setText("");
                aqi_graph.setText("");

                mid.setVisibility(View.GONE);
                button1.setVisibility(View.GONE);
                score_no_data.setVisibility(View.VISIBLE);
                score_no_data.setText("No data available");

            }


        } catch (Exception ex) {
            Log.e("IAQ SCORe", ex.getMessage());
        }

        return iaqscore;
    }
    private double getZoneIAQScore(String zone) {


        new_zone_id=zone;
        double tvoc = 0;
        try {

            switch (list_data_type) {
                case "sensor_zones":
                    JSONArray jsonArray = App.getZoneJson().getJSONArray("data");
                    Log.e("newgetZoneIAQScore", "" + jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if(jsonObject.getString("CML_ID").equals(new_zone_id))
                        {
                            iaqscore = jsonObject.getDouble("CML_AQI_SCORE");
                            //getAllValues(new_zone_id);
                           /* Bundle bundle=new Bundle();
                            bundle.remove("sensor_name_d3");
                            bundle.putString("sensor_name_d3",""+sensor_name);

                            Log.e("sensor_name",""+bundle);
                            App.setTemp_bundle(bundle);*/

                            Log.e("IAQSCOrezones", "" + iaqscore);
                        }
                    }



                    break;
                case "sensor_rooms":
                    JSONArray jsonArrayroom = App.getRoomData().getJSONArray("data");
                    Log.e("newgetZoneIAQScore", "" + jsonArrayroom);
                    for (int i = 0; i < jsonArrayroom.length(); i++) {
                        JSONObject jsonObject = jsonArrayroom.getJSONObject(i);
                        // if(jsonObject.getString("CML_ID").equals(new_zone_id)) {
                        iaqscore = jsonObject.getDouble("CML_AQI_SCORE");
                        //  getAllValues(new_zone_id);
                        Log.e("IAQSCOrerooms", "" + iaqscore);
                        // }
                    }



                    break;
                case "hvac_sensor_zones":
                    JSONArray jsonArrayhvac = App.getHvacZoneProvisionedJson().getJSONArray("data");
                    Log.e("newgetZoneIAQScore", "" + jsonArrayhvac);
                    for (int i = 0; i < jsonArrayhvac.length(); i++) {
                        JSONObject jsonObject = jsonArrayhvac.getJSONObject(i);
                        //  if(jsonObject.getString("CML_ID").equals(new_zone_id)) {
                        iaqscore = jsonObject.getDouble("CML_AQI_SCORE");
                        //  getAllValues(new_zone_id);
                        Log.e("IAQSCOrehvac", "" + iaqscore);
                        //  }
                    }



                    break;
            }


            if (iaqscore == 0) {
                aqi_graph.setText("0");
                aqi_graph.setProgress(0);
            }
        } catch (Exception ex) {
            Log.e("IAQ SCORe", ex.getMessage());
        }

        return iaqscore;
    }






    @OnClick(R.id.close_nav)
    public void close_nav()
    {
        this.dismiss();
    }

    @OnClick(R.id.close_nav_btn)
    public void close_nav_btn()
    {
        viewPager.setVisibility(View.GONE);
        viewPagerContainer.setVisibility(View.GONE);
    }


    @Override
    public void onStart() {
        super.onStart();

        // safety check
        if (getDialog() == null) {
            return;
        }

        if(App.isOrientationFlag()) {
            int dialogHeight, dialogWidth; // specify a value here
            dialogHeight = 800;
            dialogWidth = 800;
            // else  dialogHeight=800;

            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }

        // ... other stuff you want to do in your onStart() method
    }

    public void setTime()
    {
        try
        {


            String strDefaultTimeZone = App.getHubInfo().getJSONArray("data").getJSONObject(0).getString("CML_TIMEZONE");//defaultTimeZone.getDisplayName(false, TimeZone.SHORT);
            TimeZone tz = TimeZone.getTimeZone(strDefaultTimeZone);
            Calendar expdate = Calendar.getInstance(tz);
            Log.e("data2","----"+TimeZone.getDefault());
            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            String month_name = month_date.format(expdate.getTime());
            String time="";

            String hr="",min="";
            if(expdate.get(Calendar.HOUR_OF_DAY)<9) hr="0"+expdate.get(Calendar.HOUR_OF_DAY);
            else hr=""+expdate.get(Calendar.HOUR_OF_DAY);

            if(expdate.get(Calendar.MINUTE)<9) min="0"+expdate.get(Calendar.MINUTE);
            else min=""+expdate.get(Calendar.MINUTE);

            if(new PrefManager(mcontext).getValue("time_fomat").equals("1"))
            {
                time=hr+":"+min;
            }
            else
            {
                time=TimeFormatChange.convert12(hr+":"+min);
            }
            String day_time=expdate.get(Calendar.DAY_OF_MONTH)+" , "+time;

            txt_time.setText(""+month_name.toUpperCase()+" "+day_time);
        }
        catch (Exception ex)
        {
            Log.e("HubTimeError",""+ex.getMessage());
        }
    }

    @OnClick(R.id.COprog)
    public void COprog()
    {
        viewPager.setVisibility(View.VISIBLE);
        viewPagerContainer.setVisibility(View.VISIBLE);
        viewPager.setCurrentItem(0,true);
    }

    @OnClick(R.id.pm2prog)
    public void pm2prog()
    {
        viewPager.setVisibility(View.VISIBLE);
        viewPagerContainer.setVisibility(View.VISIBLE);
        viewPager.setCurrentItem(1,true);
    }

    @OnClick(R.id.pm10prog)
    public void pm10prog()
    {
        viewPager.setVisibility(View.VISIBLE);
        viewPagerContainer.setVisibility(View.VISIBLE);
        viewPager.setCurrentItem(2,true);
    }

    @OnClick(R.id.tvocprog)
    public void tvocprog()
    {
        viewPager.setVisibility(View.VISIBLE);
        viewPagerContainer.setVisibility(View.VISIBLE);
        viewPager.setCurrentItem(3,true);
    }
    private boolean check_sensor(String cml_id){

        boolean flag=false;
        try {
            JSONArray jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.getString("type").equals("Sensor"))
                {
                    if(jsonObject.has("room")) {
                        if (jsonObject.getString("room").equals(cml_id)) flag=true;
                    }
                    if(jsonObject.has("zone")) {
                        if (jsonObject.getString("zone").equals(cml_id)) flag=true;
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Log.e("SensorCheckError",""+ex.getMessage());
        }
        return  flag;

    }
    public void getSensorId(String zone_id)
    {
        try {
            JSONArray jsonArray = App.getSensorsProvisionedJson().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.getString("type").equals("Sensor"))
                {
                    if(jsonObject.has("room")) {
                        if (jsonObject.getString("room").equals(zone_id)) {
                            sensor_mac_id = jsonObject.getString("Id");

                            break;
                        }
                    }
                    if(jsonObject.has("zone")) {
                        if (jsonObject.getString("zone").equals(zone_id)) {
                            sensor_mac_id = jsonObject.getString("Id");
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Log.e("SensorCheckError",""+ex.getMessage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Logs.info(TAG, "------i am in onResume");
        UserInteractionSleep.siboSleepChecking(mcontext);
    }
    @Override
    public void onStop() {
        super.onStop();
        Logs.info(TAG, "------i am in onstop");
        UserInteractionSleep.stopHandler();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(timer_sun!=null){
            timer_sun.cancel();
            timer_sun = null;
        }
    }

    private void startClockTime()
    {
        if (tsk_sun == null) {
            tsk_sun = new TimerTask() {
                @Override
                public void run() {
                    Log.i("Time", "-----------------------");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            setTime();

                        }
                    });

                }
            };
            timer_sun.schedule(tsk_sun, 0, 1000);
        }
    }

}