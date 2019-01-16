package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.D3data;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.CustomZoneListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.ElementsRoomListAdapterPortrait;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.HomeScreenViewPagerAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.ThermostatDialog;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.indoorairquality.IndoorAirQualityMain;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Zone;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.TimeFormatChange;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity.bottom_menu_bar;
import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity.progress;
import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity.startAnimationProgress;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.ZONES;
import static com.google.android.gms.internal.zzagz.runOnUiThread;
/**
 * The HomeHeathByZoneFragment is a home page.
 *
 * @author  nikhil vharamble and jaid shaikh
 * @version 1.0
 * @since   2017-11-8
 */
public class HomeHeathByZoneFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Tracker mTracker;
    private boolean gif_flag=false;
    private boolean sensor_present=false;
    public static HomeHeathByZoneFragment newInstance() {
        return new HomeHeathByZoneFragment();
    }
    @Nullable
    @BindView(R.id.txt_air_quality)
    TextView txt_air_quality;
    @Nullable
    @BindView(R.id.txt_indoor_quality)
    TextView txt_quality;
    @Nullable
    @BindView(R.id.outdoor_descp)
    TextView outdoor_descp;
    @Nullable
    @BindView(R.id.txt_humidity_weather)
    TextView txt_humidity;
    @Nullable
    @BindView(R.id.txt_cur_weather)
    TextView txt_cur_weather;
    @Nullable
    @BindView(R.id.txt_temp_weather)
    TextView txt_temp_weather;
    @Nullable
    @BindView(R.id.txt_current_action)
    TextView txt_current_action;
    @BindView(R.id.txt_current_wheather)
    TextView txt_current_wheather;
    @BindView(R.id.txt_out_air_quality)
    TextView txt_out_air_quality;
    @Nullable
    @BindView(R.id.txt_comfortrange)
    TextView txt_comfortrange;
    @Nullable
    @BindView(R.id.txt_temp)
    TextView txt_temp;
    @Nullable
    @BindView(R.id.txt_degree_sign)
    TextView txt_degree_sign;
    @BindView(R.id.web_view_gif)
    pl.droidsonroids.gif.GifImageView webView;
    @Nullable
    @BindView(R.id.donut_progress)
    DonutProgress donut_progress;
    @Nullable
    @BindView(R.id.rel_disable)
    RelativeLayout rel_disable;
    @Nullable
    @BindView(R.id.img_right)
    ImageView img_right;
    @Nullable
    @BindView(R.id.img_left)
    ImageView img_left;
    @Nullable
    @BindView(R.id.txt_comfort_title)
    TextView txt_comfort_title;
    @BindView(R.id.txt_no_action)
    TextView txt_no_action;
    @BindView(R.id.txt_no_indoor)
    TextView txt_no_indoor;
    @Nullable
    @BindView(R.id.img_meter_indicator)
    ImageView img_meter_indicator;
    @BindView(R.id.img_hvac_power)
    ImageView img_hvac_power;
    private Boolean CML_AQI_FLAG=false;
    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
    private IndoorAirQualityMain indoor = new IndoorAirQualityMain();
    D3screen indoor1 = new D3screen();
    D3data d3 = new D3data();
    private OutdoorAirQualityMain_new outdoor = new OutdoorAirQualityMain_new();
    private boolean state=false;
    int cnt=0;
    String scene_id[]={"1","3","2","7"};
    private Observable<String>mobservable;
    private Observer<String> myObserver;
    private int breezometer_aqi;
    public static int breezometer_aqi_score=0;
    public static int indoor_aqi_score=0;
    public static double au_co=0;
    public static double au_no2=0;
    public static double au_o3=0;
    public static double au_pm2=0;
    public static double au_pm10=0;
    public static double au_so=0;
    @Nullable
    @BindView(R.id.img_thermostat_point)
    ImageView img_thermostat_point;
    @Nullable
    @BindView(R.id.img_thermostat_desire_temp)
    ImageView img_thermostat_desire_temp;
    @Nullable
    @BindView(R.id.outdoor_aqi_graph)
    DonutProgress outdoor_aqi_graph;
    @Nullable
    @BindView(R.id.indoor_aqi_graph)
    DonutProgress indoor_aqi_graph;
    @BindView(R.id.lines)LinearLayout lines;
    @BindView(R.id.current_line)View current_line;
    @BindView(R.id.outdoor_line)View outdoor_line;
    @BindView(R.id.img_sun_circle)ImageView img_sun_circle;
    int current_temp = 10;
    double current_sensor_temp = 10;
    int lower_point = 0;
    int cout=0,image_count=0;
    float x_condiante=0;
    int highest_point = 0;
    private Timer timer,timer_sun,timer_hr,timer1;
    TimerTask tsk=null,tsk1=null,tsk_sun=null,tsk_hr=null;
    private Timer timer_gif;
    private boolean sun_timer_flag=false,timer_gif_flag=false;
    // double[] y_cordinate_land={49.75,48.41,45.75,42.00,37.00,32.66,28.91,25.16,23.66,21.83,21.08, 20.70,21.16,22.00,22.90,25.83,28.25,32.00,36.25,40.83,44.90,47.91,49.50,50.00};
    double[] y_cordinate_land={49.75,48.41,45.75,42.00,37.30,32.76,28.91,25.56,23.66,21.93,21.08, 20.70,21.16,22.00,23.00,25.83,28.35,32.00,36.25,40.83,44.90,47.91,49.50,50.00};
    double[] y_cordinate_potrait=  {22.18,21.90,21.00,19.84,18.20,16.70,14.80,14.06,13.20,12.66,12.55, 12.02,12.20,12.42,13.22,13.75,14.70,16.00,17.30,19.65,20.46,21.29,21.82,21.62};
    private int cnt_co2 = 0, cnt_pm2 = 0, cnt_pm10 = 0, cnt_tvoc = 0;
    private Double[] dbl_co2 = new Double[10];
    private Double[] dbl_pm10 = new Double[10];
    private Double[] dbl_pm2 = new Double[10];
    private Double[] dbl_tvoc = new Double[10];
    int hvac_point=0,desire_temp=0;
    String str_type="";
    String str_mode="";
    String str_fan_speed="";
    int str_air_type=0;
    String str_room_temp="-1";
    String manufaturer="";
    static String strDefaultTimeZone="";
    private View v;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    private String comfort_raneg;
    private int inddor_color = 0x6EEAEA;
    private boolean clickable = true;
    private static double latitude, longitude;
    public static int home_tvoc=0;
    JSONArray bonairTypeJsonArray=new JSONArray();
    private Handler handler;
    private HashMap<String, Boolean> map = new HashMap<>();
    private int i = 0;
    private  boolean temp_flag=false,circadian_flag=false,air_flag=false,water_flag=false,audio_flag=false,purification_state=true,desire_temp_flag=false;
    ArrayList<Zone> arr_zone_title=new ArrayList<>();
    @Nullable @BindView(R.id.spn_zone)Spinner spn_zone;
    boolean bonair_status=true;
    boolean day_flag=false;
    int position=0;
    String device_id="";
    private int count_for_flag=0;
    private boolean hvac_flag = false;
    private boolean hvac_power = false;
    String hvac_id="";
    int humidity=0;
    private Context mcontext;
    @Nullable
    @BindView(R.id.img_mode)
    ImageView img_mode;
    @Nullable
    @BindView(R.id.txt_zone_name)
    TextView txt_zone_name;
    @BindView(R.id.gif_title)
    TextView gif_title;
    @Nullable
    @BindView(R.id.rel_thermostat)
    RelativeLayout rel_thermostat;
    @Nullable
    @BindView(R.id.rel_thermostat_control_panel)
    RelativeLayout rel_thermostat_control_panel;
    @Nullable
    @BindView(R.id.simpleChronometer)
    Chronometer simpleChronometer;
    @Nullable
    @BindView(R.id.activity_main_home)
    ImageView img_main_home;
    @BindView(R.id.rel_lyt)
    RelativeLayout rel_lyt;
    @Nullable
    @BindView(R.id.rel_no_data)
    RelativeLayout rel_no_data;
    @Nullable
    @BindView(R.id.img_btn_pull_down_panel)
    ImageView img_btn_pull_down_panel;
    @Nullable
    @BindView(R.id.img_btn_open_nav_drawer)
    ImageView img_btn_open_nav_drawer;
    @Nullable
    @BindView(R.id.txt_time)
    TextView txt_time;
    @BindView(R.id.view1)pl.droidsonroids.gif.GifImageView view1;
    @BindView(R.id.view2)pl.droidsonroids.gif.GifImageView view2;
    @BindView(R.id.view3)pl.droidsonroids.gif.GifImageView view3;
    @BindView(R.id.view4)pl.droidsonroids.gif.GifImageView view4i;
    @BindView(R.id.txt_no_hvac)TextView txt_no_hvac;
    @BindView(R.id.current_action_prog_bar)ProgressBar current_action_prog_bar;
    @Nullable   @BindView(R.id.current_weather_prog_bar)ProgressBar current_weather_prog_bar;
    @Nullable    @BindView(R.id.outdoor_prog_bar)ProgressBar outdoor_prog_bar;
    @Nullable   @BindView(R.id.indoor_prog_bar)ProgressBar indoor_prog_bar;
    private  String TAG=HomeHeathByZoneFragment.this.getClass().getSimpleName();
    boolean heat_mode_flag = false, cool_mode_flag = false, cool_auto_flag = false, hvac_state = false;
    private ViewPager home_health_view_pager;
    HomeScreenViewPagerAdapter homeScreenViewPagerAdapter;
    TabLayout dotsIndicator;
    static String sun_rise_time="",sun_set_time="";
    private int iconCode=0;
    HashMap<String[],Integer> tab_weather_map=new HashMap<>();
    int c_hr=0;
    HashMap<String[],Integer> mob_weather_map=new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home_heath_by_zone, container, false);
        mcontext = v.getContext();
        ButterKnife.bind(this, v);

        if (!checkConfiguration()) {
            Logs.info(TAG,"Error in FilterFragment");
        }
        App application = (App)getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();
        handler = new Handler();
        startSunAnimation();
        final float scale = getResources().getDisplayMetrics().density;
        final int dpi_scale = getResources().getDisplayMetrics().densityDpi;
        timer = new Timer();
        timer_sun = new Timer();
        timer_hr = new Timer();
        timer1 = new Timer();
        timer_gif = new Timer();
        startSunMovement();
        //startHr();
        System.out.println("scale= " + scale + "scaleDpi= " + dpi_scale);
        gifrotation();
        txt_humidity.setText("");
        txt_zone_name.setText("Home Ecosystem");
        indoor_aqi_graph.setFinishedStrokeColor(inddor_color);
        if (!App.isOrientationFlag()) {
            bottom_menu_bar.setVisibility(View.VISIBLE);
            home_health_view_pager = v.findViewById(R.id.home_health_view_pager);
            homeScreenViewPagerAdapter = new HomeScreenViewPagerAdapter(getChildFragmentManager());
            home_health_view_pager.setAdapter(homeScreenViewPagerAdapter);
            sendScreenImageName();
            dotsIndicator = v.findViewById(R.id.sliding_dots_indicator);
            dotsIndicator.setupWithViewPager(home_health_view_pager, true);
            img_btn_open_nav_drawer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
                }
            });
            img_btn_pull_down_panel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottom_menu_bar.setVisibility(View.GONE);
                    PullDownFragment pullDownFragment = new PullDownFragment();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_top, R.anim.exit_to_top, R.anim.enter_from_bottom, R.anim.exit_to_bottom);
                    fragmentTransaction.replace(R.id.frm_main_container, pullDownFragment);
                    fragmentTransaction.addToBackStack("pullDownFragment");
                    fragmentTransaction.commit();
                }
            });
            img_btn_pull_down_panel.setOnTouchListener(new ImageView.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            // Disallow ScrollView to intercept touch events.
                            bottom_menu_bar.setVisibility(View.GONE);
                            PullDownFragment pullDownFragment = new PullDownFragment();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.setCustomAnimations(R.anim.enter_from_top, R.anim.exit_to_top, R.anim.enter_from_bottom, R.anim.exit_to_bottom);
                            fragmentTransaction.replace(R.id.frm_main_container, pullDownFragment);
                            fragmentTransaction.addToBackStack("pullDownFragment");
                            fragmentTransaction.commit();
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                            break;
                    }
                    // Handle ListView touch events.
                    v.onTouchEvent(event);
                    return true;
                }
            });
        }
        else {
Log.i("HomeHealth","Inside Tablet View") ;
sendScreenImageName();
        }
        humiditySync(true);
        if(App.getPosition()==-1)App.setPosition(0);
        // getSetPoint(App.getPosition());*/
        //setTime();
        startAnimationProgress(100);
        setSubcriber();
        setTime();
        setSunPosotion();
        try {
            if (App.getSocket() != null) {
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_weather_condition", new JSONObject()));
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_current_actions", new JSONObject()));
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_date_time", new JSONObject()));
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_provisioned", new JSONObject()));
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_zones", new JSONObject()));
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_info",new JSONObject()));
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_hvac_zone_provisioned",new JSONObject()));
                // startTest();
            }
        } catch (Exception e) {
            Logs.error(TAG,"Error in request: "+e.getMessage());
        }
        return v;
    }
    private void setSubcriber() {
        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("" + App.getRoomData());
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
                if (string.equals("HVAC") || string.equals("hvac_zone_provisioned")) {
                    getHumidity();
                    getZoneTitle();
                    getSetPoint(App.getPosition());
                }
                if (string.equals("hub_soft_resetted")) {
                    ReplaceFragment.replaceFragment(HomeHeathByZoneFragment.this, R.id.frm_main_container, HomeHeathByZoneFragment.newInstance());
                }
                if (string.equals("info")) {
                    sun_timer_flag = true;
                    setTime();
                    startAnimationProgress(100);
                    setSunPosotion();
                }
                if (string.equals("weather_data")) {
                    setWeatherImages();
                    setWeatherData();
                }

/**********************/
                try {
                    if(App.getcurrent_actions()!=null) {
                        gif_flag=true;
                        JSONArray jsonArray = App.getcurrent_actions().getJSONArray("data");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        current_action_prog_bar.setVisibility(View.GONE);
                        rel_lyt.setVisibility(View.VISIBLE);
                        txt_no_action.setVisibility(View.GONE);
                        lines.setVisibility(View.VISIBLE);
                        gif_title.setVisibility(View.VISIBLE);
                        int visible_cnt = 0;
                        circadian_flag = false;
                        audio_flag = false;
                        air_flag = false;
                        water_flag = false;
                        if (jsonObject.has("circadian_status") && jsonObject.getBoolean("circadian_status") == true) {
                            circadian_flag = true;
                            view1.setVisibility(View.VISIBLE);
                        } else {
                            view1.setVisibility(View.GONE);
                            visible_cnt++;
                        }
                        if (jsonObject.has("air_status") && jsonObject.getBoolean("air_status") == true) {
                            view3.setVisibility(View.VISIBLE);
                            air_flag = true;
                        } else {
                            view3.setVisibility(View.GONE);
                            visible_cnt++;
                        }
                        if (jsonObject.has("water_status") && jsonObject.getBoolean("water_status") == true) {
                            view2.setVisibility(View.VISIBLE);
                            water_flag = true;
                        } else {
                            view2.setVisibility(View.GONE);
                            visible_cnt++;
                        }
                        if (jsonObject.has("sound_status") && jsonObject.getBoolean("sound_status") == true) {
                            audio_flag = true;
                            view4i.setVisibility(View.VISIBLE);
                        } else {
                            view4i.setVisibility(View.GONE);
                            visible_cnt++;
                        }
                        if (visible_cnt == 4) {
                            current_action_prog_bar.setVisibility(View.GONE);
                            txt_no_action.setVisibility(View.VISIBLE);
                            rel_lyt.setVisibility(View.GONE);
                        }
                    }
                    else {
                        current_action_prog_bar.setVisibility(View.GONE);
                        txt_no_action.setVisibility(View.VISIBLE);
                        rel_lyt.setVisibility(View.GONE);
                        lines.setVisibility(View.GONE);
                        gif_title.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    {
                        Logs.error( TAG,"Current_action Error: " + e.getMessage());
                    }
                }
                /***********/
                getgeocoordinates();
                if(string.equals("sensor_reading")) {
                    calculatePercentage();
                }
                if(string.equals("zones")) {
                    setIndoordata();
                }
            }
        };
        Subcriber s = new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);
    }

    private void setIndoordata() {
        long original_score = Math.round(getIAQScore());
        long score=100-original_score;
        indoor_aqi_score= (int) score;
        if(original_score==0){
            txt_quality.setText("");
            indoor_prog_bar.setVisibility(View.GONE);
            txt_no_indoor.setVisibility(View.VISIBLE);
            txt_no_indoor.setText("No Data Available");
            indoor_aqi_graph.setVisibility(View.GONE);
        }
        if(original_score>0) {
            indoor_prog_bar.setVisibility(View.GONE);
            txt_no_indoor.setVisibility(View.GONE);
            indoor_aqi_graph.setVisibility(View.VISIBLE);
            indoor_aqi_graph.setText("" + score);
            indoor_aqi_graph.setStartingDegree(270);
            indoor_aqi_graph.setProgress(score);
            if (score >= 1 && score <= 39) {
                //   score_no_data.setText("SCORE");
                txt_quality.setVisibility(View.VISIBLE);
                txt_quality.setText("Poor");
                indoor_aqi_graph.setFinishedStrokeColor(Color.rgb(246,103,101));
            }  else if (score >= 40 && score <= 59) {
                txt_quality.setText("Moderate");
                indoor_aqi_graph.setFinishedStrokeColor(Color.rgb(229,197,0));
            } else if (score >= 60 && score <= 69) {
                txt_quality.setText("Moderate");
                indoor_aqi_graph.setFinishedStrokeColor(Color.rgb(229,197,0));
            } else if (score >= 70 && score <= 79) {
                txt_quality.setText("Moderate");
                indoor_aqi_graph.setFinishedStrokeColor(Color.rgb(229,197,0));
            } else if (score >= 80 && score <= 89) {
                txt_quality.setText("Good");
                indoor_aqi_graph.setFinishedStrokeColor(Color.rgb(68,255,233));
            }
            else if (score >= 90 && score <= 100) {
                txt_quality.setText("Excellent");
                indoor_aqi_graph.setFinishedStrokeColor(Color.rgb(68,255,233));
            }
        }
        else {
            getIAQScore();
        }
    }

    private void setWeatherData() {
        try {
            if (App.getweather_condition() != null) {
                JSONObject jsonObject = App.getweather_condition();
                JSONArray jsonArrayyy = jsonObject.getJSONArray("data");
                boolean weather_data_flag = false;
                boolean bqi_data_flag = false;
                for (int i = 0; i < jsonArrayyy.length(); i++) {
                    JSONObject jsonObject1 = jsonArrayyy.getJSONObject(i);
                    if (jsonObject1.getString("type").equals("weather")) {
                        current_weather_prog_bar.setVisibility(View.GONE);
                        weather_data_flag = true;
                        //   JSONObject json = jsonArrayyy.getJSONObject(0);
                        JSONObject status = jsonObject1.getJSONObject("temperature");
                        int stat1 = (int) Math.round(status.getDouble("value"));
                        txt_temp_weather.setVisibility(View.VISIBLE);
                        txt_temp_weather.setText("" + stat1 + "\u00B0");
                        int humidity = jsonObject1.getInt("relative_humidity");
                        txt_humidity.setVisibility(View.VISIBLE);
                        txt_humidity.setText("" + humidity + "%  Humidity");
                        String curr_weather = jsonObject1.getString("weather_text");
                        txt_cur_weather.setVisibility(View.VISIBLE);
                        txt_cur_weather.setText(getResult(curr_weather));
                    }
                    if (jsonObject1.getString("type").equals("baqi")) {
                        bqi_data_flag = true;
                        outdoor_descp.setVisibility(View.VISIBLE);
                        breezometer_aqi = jsonObject1.getInt("breezometer_aqi");
                        outdoor_aqi_graph.setVisibility(View.VISIBLE);
                        outdoor_aqi_graph.setProgress(breezometer_aqi);
                        outdoor_aqi_graph.setText("" + breezometer_aqi);
                        outdoor_aqi_graph.setStartingDegree(270);
                        if (breezometer_aqi >= 0 && breezometer_aqi <= 39) {
                            //   score_no_data.setText("SCORE");
                            outdoor_descp.setText("Poor");
                            outdoor_aqi_graph.setFinishedStrokeColor(Color.rgb(246, 103, 101));
                        } else if (breezometer_aqi >= 40 && breezometer_aqi <= 59) {
                            outdoor_descp.setText("Moderate");
                            outdoor_aqi_graph.setFinishedStrokeColor(Color.rgb(229, 197, 0));
                        } else if (breezometer_aqi >= 60 && breezometer_aqi <= 69) {
                            outdoor_descp.setText("Moderate");
                            outdoor_aqi_graph.setFinishedStrokeColor(Color.rgb(229, 197, 0));
                        } else if (breezometer_aqi >= 70 && breezometer_aqi <= 79) {
                            outdoor_descp.setText("Moderate");
                            outdoor_aqi_graph.setFinishedStrokeColor(Color.rgb(229, 197, 0));
                        } else if (breezometer_aqi >= 80 && breezometer_aqi <= 89) {
                            outdoor_descp.setText("Good");
                            outdoor_aqi_graph.setFinishedStrokeColor(Color.rgb(68, 255, 233));
                        } else if (breezometer_aqi >= 90 && breezometer_aqi <= 100) {
                            outdoor_descp.setText("Excellent");
                            outdoor_aqi_graph.setFinishedStrokeColor(Color.rgb(68, 255, 233));
                        }
                        breezometer_aqi_score = breezometer_aqi;
                        au_co = jsonObject1.getJSONObject("pollutants").getJSONObject("co").getDouble("concentration");
                        au_no2 = jsonObject1.getJSONObject("pollutants").getJSONObject("no2").getDouble("concentration");
                        au_o3 = jsonObject1.getJSONObject("pollutants").getJSONObject("o3").getDouble("concentration");
                        au_pm2 = jsonObject1.getJSONObject("pollutants").getJSONObject("pm25").getDouble("concentration");
                        au_pm10 = jsonObject1.getJSONObject("pollutants").getJSONObject("pm10").getDouble("concentration");
                        au_so = jsonObject1.getJSONObject("pollutants").getJSONObject("so2").getDouble("concentration");

                    }
                }
                if (!weather_data_flag) {
                    current_weather_prog_bar.setVisibility(View.GONE);
                    txt_cur_weather.setText("No Data Available ");
                    txt_cur_weather.setVisibility(View.VISIBLE);
                }
                if (!bqi_data_flag) {
                    outdoor_prog_bar.setVisibility(View.GONE);
                    outdoor_descp.setText("No Data Available");
                    outdoor_descp.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            Logs.error(TAG, "weather_error:" + e.getMessage());
        }
    }

    private void calculatePercentage()
    {
        try {
            JSONArray jsonArray = App.getSensorsData().getJSONArray("data");
            for (int i = 0; i <jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if (jsonObject.has("CML_TYPE")&&jsonObject.has("CML_VALUE")) {
                    getPercentage(jsonObject.getString("CML_TYPE"), jsonObject.getString("CML_VALUE"));
                }
            }
            if((cnt_tvoc+cnt_co2+cnt_pm2+cnt_pm10)<0) {
                indoor_aqi_graph.setProgress(0);
                indoor_aqi_graph.setVisibility(View.GONE);
            }
        }
        catch (Exception ex){
            Logs.error(TAG,"Percentage"+ex.getMessage());}
    }
    private void getPercentage(String CML_Type, String CML_VALUE)
    {
        switch(CML_Type) {
            case "air#carbon_dioxide":
                dbl_co2[cnt_co2]=Double.parseDouble(CML_VALUE);
                cnt_co2++;
                break;
            case "air#pm2.5":
                dbl_pm2[cnt_pm2]=Double.parseDouble(CML_VALUE);
                cnt_pm2++;
                break;
            case "air#pm10":
                dbl_pm10[cnt_pm10]=Double.parseDouble(CML_VALUE);
                cnt_pm10++;
                break;
            case "air#tvoc":
                dbl_tvoc[cnt_tvoc]=Double.parseDouble(CML_VALUE);
                cnt_tvoc++;
                break;
        }
    }
    private double getIAQScore()
    {
        double iaqscore=0;
        try {
            if (App.getZoneJson()!=null) {
                JSONArray jsonArray = App.getZoneJson().getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getString("CML_TITLE").equals("Whole Home")) {
                        if(jsonObject.has("CML_AQI_SCORE")) {
                            int score = jsonObject.getInt("CML_AQI_SCORE");
                            if (jsonObject.getInt("CML_AQI_FLAG") == 0 )//&& sensor_present==true
                            {
                                indoor_prog_bar.setVisibility(View.GONE);
                                txt_no_indoor.setVisibility(View.VISIBLE);
                                txt_no_indoor.setText("Loading Data");
                                indoor_aqi_graph.setVisibility(View.GONE);
                                CML_AQI_FLAG = true;
                            }  if (jsonObject.getInt("CML_AQI_FLAG") == 1 && score > 0) {
                                indoor_prog_bar.setVisibility(View.GONE);
                                iaqscore = jsonObject.getDouble("CML_AQI_SCORE");
                                Log.e("IAQSCOre", "" + iaqscore);
                                CML_AQI_FLAG = false;
                            }  if (jsonObject.getInt("CML_AQI_FLAG") == 2) {
                                indoor_prog_bar.setVisibility(View.GONE);
                                txt_no_indoor.setVisibility(View.VISIBLE);
                                txt_no_indoor.setText("No Data Available");
                                indoor_aqi_graph.setVisibility(View.GONE);
                                CML_AQI_FLAG = false;
                            }
                        }
                        else
                        {
                            indoor_prog_bar.setVisibility(View.GONE);
                            txt_no_indoor.setVisibility(View.VISIBLE);
                            txt_no_indoor.setText("No Data Available");
                            indoor_aqi_graph.setVisibility(View.GONE);
                        }
                    }
                }
            }
            else {
                indoor_prog_bar.setVisibility(View.GONE);
                txt_no_indoor.setVisibility(View.VISIBLE);
                txt_no_indoor.setText("No Data Available");
                indoor_aqi_graph.setVisibility(View.GONE);
            }
        }
        catch (Exception ex){
            Logs.error(TAG,"error_getZoneJson"+ex.getMessage());}
        return iaqscore;
    }
    private void startHvacTempRotation()
    {
        if(current_temp!=desire_temp) {
            // This timer task will be executed every 1 sec.
            if(tsk==null)
            {
                tsk= new TimerTask() {
                    @Override
                    public void run() {
                        changeText();
                    }
                };
                timer.schedule(tsk, 0, 2000);
            }
        }
    }
    private void startTest()
    {
        tsk1= new TimerTask() {
            @Override
            public void run() {
                try {
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_info", new JSONObject()));
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_current_actions", new JSONObject()));
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_date_time", new JSONObject()));
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_weather_condition", new JSONObject()));
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_provisioned", new JSONObject()));
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_zones", new JSONObject()));
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_hvac_zone_provisioned", new JSONObject()));
                     App.getSocket().emit("action", new JSONObject("{\"data\":{\"state\":"+state+",\"group\":{\"Id\":\"group#1534487569052\",\"CML_ID\":\"group#1534487569052\",\"KEY_VAL\":\"group#1534487569052\",\"CML_TITLE\":\"Mygroup\",\"CML_SELECTED\":false,\"CML_SCENE_ID\":\"1\",\"KEY_TYPE\":\"TSK\",\"SUB_KEY_TYPE\":\"TSK_PROP_DEV_ORG\",\"CREATED_BY\":\"android.dev@darwin.com\",\"CREATED_ON\":\"2018-08-17T06:32:49.052Z\",\"room\":\"room#1533097574226\",\"LAST_MODIFIED_BY\":\"android.dev@darwin.com\",\"LAST_MODIFIED_ON\":\"2018-08-17T06:32:49.052Z\",\"ACTION_ARRAY\":[\"add_group\"],\"CML_CATEGORY\":\"1\",\"CML_TASK_SUBTYPE\":71,\"ACTIVE_STATUS\":\"1\",\"SYNC_PENDING_STATUS\":\"1\",\"CML_ACCEPTED\":\"1\",\"type\":\"GROUP\",\"groupType\":\"Lighting\",\"scenes\":[\"1\",\"3\",\"2\",\"5\",\"6\"],\"devices\":[\"device#1534487506710_294\",\"device#1534487506710_117\"],\"meta\":{\"revision\":0,\"created\":1534487569097,\"version\":0},\"$loki\":1,\"CML_STATUS\":true,\"CML_POWER\":true,\"CML_BRIGHTNESS\":100}},\"type\":\"group_power\"}"));
                    System.out.println("**************"+new JSONObject("{\"data\":{\"deviceId\":\"group#1534487569052\",\"scene\":"+scene_id[cnt]+"},\"type\":\"trigger_light\"}"));
                    // App.getSocket().emit("action", new JSONObject("{\"data\":{\"deviceId\":\"group#1534487569052\",\"scene\":'"+scene_id[cnt]+"'},\"type\":\"trigger_light\"}"));
                    App.getSocket().emit("action", new JSONObject("{\"data\":{\"scene\":'"+scene_id[cnt]+"',\"home\":\"all\"},\"type\":\"trigger_scene\"}"));
                    cnt++;
                    if(cnt==3)cnt=0;
                    state=!state;
                }
                catch (Exception ex)
                {
                    Logs.error(TAG,"error"+ex.getMessage());
                }
            }
        };
        timer1.schedule(tsk1, 0, 2000);
    }
    private void gifrotation()
    {
        try {
            view1.setBackgroundColor(mcontext.getResources().getColor(R.color.darwin_dsp_text_color_black_light));
            view2.setBackgroundColor(mcontext.getResources().getColor(R.color.darwin_dsp_text_color_black_light));
            view3.setBackgroundColor(mcontext.getResources().getColor(R.color.darwin_dsp_text_color_black_light));
            view4i.setBackgroundColor(mcontext.getResources().getColor(R.color.darwin_dsp_text_color_black_light));
            // This timer task will be executed every 1 sec.
            timer_gif.schedule(new TimerTask() {
                @Override
                public void run() {
                    timer_gif_flag = true;
                    final boolean[] flag_arr = {circadian_flag, water_flag, air_flag, audio_flag};
                    final int[] str_gif_url = {R.drawable.new_circadian_nine, R.drawable.new_water_nine, R.drawable.new_air_nine, R.drawable.new_audio_nine};
                    final int[] str_gif_loading = {R.drawable.new_gif, R.drawable.new_gif, R.drawable.new_gif, R.drawable.new_gif};
                    final String[] str_gif_title = {"Circadian", "Water Purification", "Air Purification", "Sound"};
                    final pl.droidsonroids.gif.GifImageView[] view_gif = {view1, view2, view3, view4i};
                    if (count_for_flag > 3) {
                        count_for_flag = 0;
                        runOnUiThread(new Runnable() {
                            public void run() {
                                view1.setBackgroundColor(mcontext.getResources().getColor(R.color.darwin_dsp_text_color_black_light));
                                view2.setBackgroundColor(mcontext.getResources().getColor(R.color.darwin_dsp_text_color_black_light));
                                view3.setBackgroundColor(mcontext.getResources().getColor(R.color.darwin_dsp_text_color_black_light));
                                view4i.setBackgroundColor(mcontext.getResources().getColor(R.color.darwin_dsp_text_color_black_light));
                                view1.setImageResource(0);
                                view2.setImageResource(0);
                                view3.setImageResource(0);
                                view4i.setImageResource(0);
                            }
                        });
                    }
                    if (flag_arr[count_for_flag]) {
                        changeGif(str_gif_url[count_for_flag], str_gif_title[count_for_flag], view_gif[count_for_flag], str_gif_loading[count_for_flag]);
                    }
                    count_for_flag++;
                }
            }, 0, 2100);
        }catch (Exception ex){
            Logs.error(TAG,"error_gif_rotation: "+ex.getMessage());
        }
    }
    public void changeText()
    {
        runOnUiThread(new Runnable() {
            public void run() {
                if(!temp_flag) {
                    temp_flag=true;
                    txt_comfort_title.setText("DESIRED TEMPERATURE");
                    txt_comfortrange.setText("" + desire_temp+"\u00B0");
                    txt_comfortrange.setVisibility(View.VISIBLE);
                }
                else
                {
                    temp_flag=false;
                    if(desire_temp_flag)
                    {
                        txt_comfort_title.setText(""+humidity+"%  Humidity");
                        txt_comfortrange.setVisibility(View.GONE);
                    }
                    else {
                        txt_comfort_title.setText("COMFORT RANGE");
                        txt_comfortrange.setText("" + comfort_raneg);
                        txt_comfortrange.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
    public void changeGif(final int url, final String title, final GifImageView v, final int lurl)
    {
        runOnUiThread(new Runnable() {
            public void run() {
                webView.setLayoutParams(new RelativeLayout.LayoutParams(250, 250));
                Drawable d = webView.getDrawable();
                Drawable d1 = v.getDrawable();
                if (d instanceof GifDrawable) {
                    ((GifDrawable) d).recycle();
                }
                if (d1 instanceof GifDrawable) {
                    ((GifDrawable) d1).recycle();
                }
                webView.setImageResource(url);
                gif_title.setVisibility(View.VISIBLE);
                gif_title.setText(title);
                v.setImageResource(lurl);
            }
        });
    }
    private void getgeocoordinates()
    {
        try {
            JSONArray jsonArray = App.getHubInfo().getJSONArray("data");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String lt=jsonObject.getString("CML_LATITUDE");
            String lg=jsonObject.getString("CML_LONGITUDE");
            latitude=Double.parseDouble(""+lt);
            longitude=Double.parseDouble(""+lg);
        }
        catch (Exception ex){
            Log.e("LocationError",""+ex.getMessage());}
    }
    private String getResult(String str) {
        String[] words = str.split(" ");
        StringBuilder ret = new StringBuilder();
        for(int i = 0; i < words.length; i++) {
            ret.append(Character.toUpperCase(words[i].charAt(0)));
            ret.append(words[i].substring(1));
            if(i < words.length - 1) {
                ret.append(' ');
            }
        }
        return ret.toString();
    }
    @Optional @OnClick(R.id.img_right)
    public void img_increase() {
        if(!desire_temp_flag) {
            if (highest_point < 32) {
                txt_temp.setText("" + current_temp + "");
                setHvacPoint(current_sensor_temp, "increment");
            }
        }
        else
        {
            if (desire_temp < 32) {
                setPoint(desire_temp+1);
            }
        }
    }
    @Optional @OnClick(R.id.img_left)
    public void img_decrease()
    {
        if(!desire_temp_flag) {
            if (lower_point > 10) {
                txt_temp.setText("" + current_temp + "");
                setHvacPoint(current_sensor_temp, "decrement");
            }
        }else
        {
            if (desire_temp > 10) {
                setPoint(desire_temp-1);
            }
        }
    }
    public void setPoint(int point)
    {
        try
        {
            JSONObject obj = new JSONObject();
            obj.put("data", new JSONObject().put("CML_SET_POINT",point).put("CML_LOWER_POINT",lower_point).put("CML_HIGHEST_POINT",highest_point).put("Id",hvac_id).put("manufacturer",manufaturer).put("CML_ROOM_TEMPERATURE",str_room_temp));
            obj.put("type", "set_hvac_setpoint");
            App.getSocket().emit("action", obj);
            //  App.getSocket().emit("trigger_dawn_simulation", JsonObjectCreater.getJsonObject("trigger_dawn_simulation", obj));
        }
        catch (Exception ex){
            Logs.info(TAG+"_SimulationRequestError",ex.getMessage());
        }
    }
    public void getSetPoint(int position)
    {
        if(position==-1)position=0;
        String flag_state="off";
        cool_mode_flag = false;heat_mode_flag=false;
        try {
            JSONObject hvac_jsonobject=App.getHvacZoneProvisionedJson();
            if(hvac_jsonobject!=null) {
                if (hvac_jsonobject.getJSONArray("data").toString().equals("[]") || arr_zone_title.size() == 0) {
                    rel_no_data.setVisibility(View.VISIBLE);
                } else {
                    JSONArray jsonArray = hvac_jsonobject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (arr_zone_title.get(position).getZone_id().equals(jsonObject.getString("Id"))) {
                            hvac_id = jsonObject.getString("Id");
                            manufaturer = jsonObject.getString("manufacturer");
                            if (jsonObject.has("CML_SET_POINT"))
                                desire_temp = (int) jsonObject.getDouble("CML_SET_POINT");
                            if (jsonObject.has("CML_STATE"))
                                flag_state = jsonObject.getString("CML_STATE");
                            if (jsonObject.has("CML_HIGHEST_POINT"))
                                highest_point = jsonObject.getInt("CML_HIGHEST_POINT");
                            if (jsonObject.has("CML_AIR_TYPE"))
                                str_air_type = jsonObject.getInt("CML_AIR_TYPE");
                            if (jsonObject.has("CML_LOWER_POINT"))
                                lower_point = jsonObject.getInt("CML_LOWER_POINT");
                            if (jsonObject.has("CML_TYPE"))
                                str_type = jsonObject.getString("CML_TYPE");
                            if (jsonObject.has("CML_MODE"))
                                str_mode = jsonObject.getString("CML_MODE");
                            if (jsonObject.has("CML_FAN_SPEED"))
                                str_fan_speed = jsonObject.getString("CML_FAN_SPEED");
                            if (jsonObject.has("CML_ROOM_TEMPERATURE"))
                                str_room_temp = jsonObject.getString("CML_ROOM_TEMPERATURE");
                            if (jsonObject.has("CML_PURIFICATION_STATE"))
                                purification_state = jsonObject.getBoolean("CML_PURIFICATION_STATE");
                            if (jsonObject.has("CML_STATUS"))
                                bonair_status = jsonObject.getBoolean("CML_STATUS");
                            if (jsonObject.has("CML_BONAIRE_TYPE"))
                                bonairTypeJsonArray = jsonObject.getJSONArray("CML_BONAIRE_TYPE");
                            startHvacTempRotation();
                        }
                        // str_mode=jsonObject.getString("CML_MODE");
                    }
                }
                if(bonairTypeJsonArray.length()>0)
                {
                    ArrayList<String> arrayList=new ArrayList<>();
                    for (int i=0;i<bonairTypeJsonArray.length();i++)
                    {
                        arrayList.add(bonairTypeJsonArray.getString(i));
                    }
                    if(arrayList.contains("heat")&&!arrayList.contains("cool"))
                    {
                        desire_temp_flag=true;
                        donut_progress.setVisibility(View.GONE);
                        img_thermostat_desire_temp.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        desire_temp_flag=false;
                        donut_progress.setVisibility(View.VISIBLE);
                        img_thermostat_desire_temp.setVisibility(View.GONE);
                    }
                }
                else
                {
                    desire_temp_flag=false;
                    donut_progress.setVisibility(View.VISIBLE);
                    img_thermostat_desire_temp.setVisibility(View.GONE);
                }
                current_temp = (int) Math.round(Double.parseDouble(str_room_temp));
                setRotator();
                if (current_temp >= 0&&current_temp<=99) {
                    if (current_temp < 10) {
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) txt_temp.getLayoutParams();
                        params.setMargins(0, 0, 0, 0);
                        txt_temp.setLayoutParams(params);
                    }
                    txt_temp.setText("" + current_temp);
                    if (App.isOrientationFlag()) txt_temp.setTextSize(100f);
                    else txt_temp.setTextSize(60f);
                    img_mode.setVisibility(View.VISIBLE);
                    txt_degree_sign.setVisibility(View.VISIBLE);
                    img_thermostat_point.setVisibility(View.VISIBLE);
                } else {
                    ViewGroup.MarginLayoutParams params =
                            (ViewGroup.MarginLayoutParams) txt_temp.getLayoutParams();
                    params.leftMargin = 90 - 100;   // -100
                    txt_temp.setLayoutParams(params);
                    txt_temp.setText("NA");
                    if (App.isOrientationFlag()) txt_temp.setTextSize(80f);
                    else txt_temp.setTextSize(60f);
                    txt_degree_sign.setVisibility(View.GONE);
                    img_thermostat_point.setVisibility(View.GONE);
                }
                if(current_temp>=0&&current_temp<=40)
                {
                    img_thermostat_point.setVisibility(View.VISIBLE);
                }
                else
                {
                    img_thermostat_point.setVisibility(View.GONE);
                }
                Logs.info(TAG + "_Hvac_info", highest_point + "----" + lower_point + "----" + bonair_status + "-----" + flag_state);
                if (lower_point == 0) lower_point = 19;
                if (highest_point == 0) highest_point = 22;
                if (str_type.equals("heat")) {
                    img_mode.setVisibility(View.VISIBLE);
                    heat_mode_flag = true;
                    img_mode.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heating_pink));
                    if (flag_state.equals("on"))
                        img_mode.setColorFilter(mcontext.getResources().getColor(R.color.red_notification), PorterDuff.Mode.SRC_IN);
                }
                if (str_type.equals("cool")) {
                    img_mode.setVisibility(View.VISIBLE);
                    cool_mode_flag = true;
                    img_mode.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_cooling_blue));
                    if (flag_state.equals("on"))
                        img_mode.setColorFilter(mcontext.getResources().getColor(R.color.sub_actionbar_back), PorterDuff.Mode.SRC_IN);
                }
                if (str_type.equals("none")) {
                    cool_auto_flag = true;
                    cool_mode_flag = true;
                    heat_mode_flag = true;
                    img_mode.setVisibility(View.GONE);
                }
                if(desire_temp_flag)
                {
                    if(desire_temp>current_temp)
                    {
                        img_mode.setVisibility(View.VISIBLE);
                        img_mode.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heating_pink));
                    }
                    else
                    {
                        img_mode.setVisibility(View.GONE);
                    }
                }
                Logs.info(TAG + "_Hvac_highest_lowest", highest_point + "------" + lower_point + "------" + hvac_point);
                comfort_raneg = "" + lower_point + "\u00B0 - " + highest_point + "\u00B0";
                if (flag_state.equals("on")) {
                    hvac_state = true;
                    disableAllViews(rel_thermostat_control_panel, true);
                    setThermostatOnOffColor(R.color.white);
                    img_left.setColorFilter(mcontext.getResources().getColor(R.color.dialer_color), PorterDuff.Mode.SRC_IN);
                    img_right.setColorFilter(mcontext.getResources().getColor(R.color.dialer_color), PorterDuff.Mode.SRC_IN);
                    img_thermostat_point.setAlpha(1f);
                    donut_progress.setAlpha(1f);
                    img_mode.setAlpha(1f);
                } else {
                    hvac_state = false;
                    disableAllViews(rel_thermostat_control_panel, false);
                    setThermostatOnOffColor(R.color.fragment_blue_light);
                    img_left.setColorFilter(mcontext.getResources().getColor(R.color.fragment_blue_light), PorterDuff.Mode.SRC_IN);
                    img_right.setColorFilter(mcontext.getResources().getColor(R.color.fragment_blue_light), PorterDuff.Mode.SRC_IN);
                    img_mode.setAlpha(0.5f);
                    img_thermostat_point.setAlpha(0.5f);
                    donut_progress.setAlpha(0.5f);
                }
                if (!bonair_status) {
                    disableAllViews(rel_thermostat_control_panel, false);
                    setThermostatOnOffColor(R.color.fragment_blue_light);
                    img_left.setColorFilter(mcontext.getResources().getColor(R.color.fragment_blue_light), PorterDuff.Mode.SRC_IN);
                    img_right.setColorFilter(mcontext.getResources().getColor(R.color.fragment_blue_light), PorterDuff.Mode.SRC_IN);
                    img_mode.setAlpha(0.5f);
                    img_thermostat_point.setAlpha(0.5f);
                    donut_progress.setAlpha(0.5f);
                }
                if(!bonair_status)
                {
                    disableAllViews(rel_thermostat_control_panel,false);
                    setThermostatOnOffColor(R.color.fragment_blue_light);
                    img_right.setColorFilter(mcontext.getResources().getColor(R.color.fragment_blue_light), PorterDuff.Mode.SRC_IN);
                    img_left.setColorFilter(mcontext.getResources().getColor(R.color.fragment_blue_light), PorterDuff.Mode.SRC_IN);
                    img_mode.setAlpha(0.5f);
                    img_thermostat_point.setAlpha(0.5f);
                    donut_progress.setAlpha(0.5f);
                    img_hvac_power.setAlpha(0.5f);
                    img_hvac_power.setClickable(false);
                }
                else
                {
                    img_hvac_power.setAlpha(1f);
                    img_hvac_power.setClickable(true);
                    if(hvac_state)
                    {
                        disableAllViews(rel_thermostat_control_panel,true);
                        setThermostatOnOffColor(R.color.white);
                        img_left.setColorFilter(mcontext.getResources().getColor(R.color.dialer_color), PorterDuff.Mode.SRC_IN);
                        img_right.setColorFilter(mcontext.getResources().getColor(R.color.dialer_color), PorterDuff.Mode.SRC_IN);
                        img_thermostat_point.setAlpha(1f);
                        donut_progress.setAlpha(1f);
                        img_mode.setAlpha(1f);
                    }
                }
                if (highest_point > lower_point) {
                    double progress;
                    if (App.isOrientationFlag()) progress = (highest_point - lower_point) * 1.87777;
                    else progress = (highest_point - lower_point) * 0.45;
                    if (progress == 0) donut_progress.setProgress(0.3f);
                    else donut_progress.setProgress((float) progress);
                    Logs.info(TAG + "_ComforttBand", "" + progress);
                    setComfortRotation(lower_point);
                }
            }
            else {
                hvac_flag=false;
            }
            Logs.info(TAG+"_hvac_flag",""+hvac_flag);
            if(hvac_flag)
            {
                rel_thermostat.setVisibility(View.VISIBLE);
                rel_no_data.setVisibility(View.GONE);
            }
            else
            {
                rel_thermostat.setVisibility(View.GONE);
                rel_no_data.setVisibility(View.VISIBLE);
            }
        }
        catch(Exception ex)
        {
            Logs.info(TAG+"_SetPointError", "" + ex.getMessage());
        }
    }
    public void setHvacPoint(double value,String str_action)
    {
        try
        {
            JSONObject obj = new JSONObject();
            if(lower_point<10) lower_point=10;
            if(highest_point>32) highest_point=32;
            if(str_action.equals("increment")) obj.put("data", new JSONObject().put("CML_LOWER_POINT",lower_point+1).put("CML_HIGHEST_POINT",highest_point+1).put("CML_SET_POINT",value).put("Id",hvac_id).put("manufacturer",manufaturer).put("CML_ROOM_TEMPERATURE",str_room_temp));
            if(str_action.equals("decrement")) obj.put("data", new JSONObject().put("CML_LOWER_POINT",lower_point-1).put("CML_HIGHEST_POINT",highest_point-1).put("CML_SET_POINT",value).put("Id",hvac_id).put("manufacturer",manufaturer).put("CML_ROOM_TEMPERATURE",str_room_temp));
            obj.put("type", "set_comfort_band");
            App.getSocket().emit("action", obj);
        }
        catch (Exception ex){
            System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            Logs.info(TAG+"_SimulationRequestError",""+ex.getMessage());
        }
    }
    private void setRotator() {
        double degree = current_temp*6.73;
        img_thermostat_point.setRotation((float) degree);
        degree = desire_temp*6.73;
        img_thermostat_desire_temp.setRotation((float) degree);
    }
    public void setSunPosotion()
    {
        try {
            // if(App.getDateTimeJson()!=null)startAnimationProgress(60);
             progress.dismiss();
            strDefaultTimeZone = App.getHubInfo().getJSONArray("data").getJSONObject(0).getString("CML_TIMEZONE");//defaultTimeZone.getDisplayName(false, TimeZone.SHORT);
            TimeZone tz = TimeZone.getTimeZone(strDefaultTimeZone);
            Calendar expdate = Calendar.getInstance(tz);
            final int c_hr=expdate.get(Calendar.HOUR_OF_DAY);
            final int min=expdate.get(Calendar.MINUTE);
            System.out.println("ccccccccccccccccccccccccccccccvvvvvvvvvvvvvvvvv"+c_hr+"ccccccccccccccccccccccccccc"+strDefaultTimeZone);
            final   String str_hour=c_hr<10?"0"+c_hr:""+c_hr;
            final String str_min=min<10?"0"+min:""+min;
            if(isTimeBetweenTwoTime(str_hour+":"+str_min) ){
                day_flag=true;
                if(iconCode<=0) {
                    if (App.isOrientationFlag()) {
                        setImage(mcontext, R.drawable.home_baground_day, img_main_home);//home_day_bg
                    }
                    else {
                        setImage(mcontext,R.drawable.home_main_background_day_mob,img_main_home);//home_day_bg
                        img_btn_pull_down_panel.setImageResource(R.drawable.ic_down_arr);
                        img_btn_open_nav_drawer.setImageResource(R.drawable.ic_cogwheel);
                    }
                }
                outdoor_descp.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                outdoor_line.setBackgroundColor(getActivity().getResources().getColor(R.color.black));
                current_line.setBackgroundColor(getActivity().getResources().getColor(R.color.black));
                txt_current_wheather.setTextColor(getActivity().getResources().getColor(R.color.black));
                txt_cur_weather.setTextColor(getActivity().getResources().getColor(R.color.black));
                txt_out_air_quality.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                outdoor_descp.setTextColor(getActivity().getResources().getColor(R.color.black));
                txt_temp_weather.setTextColor(getActivity().getResources().getColor(R.color.black));
                outdoor_aqi_graph.setTextColor(getActivity().getResources().getColor(R.color.black));
                txt_humidity.setTextColor(getActivity().getResources().getColor(R.color.black));
                txt_current_action.setTextColor(getActivity().getResources().getColor(R.color.white));
                gif_title.setTextColor(getActivity().getResources().getColor(R.color.white));
                txt_no_action.setTextColor(getActivity().getResources().getColor(R.color.white));
                outdoor_aqi_graph.setUnfinishedStrokeColor(getActivity().getResources().getColor(R.color.white));
                txt_quality.setTextColor(getActivity().getResources().getColor(R.color.white));
                txt_no_indoor.setTextColor(getActivity().getResources().getColor(R.color.white));
                txt_air_quality.setTextColor(getActivity().getResources().getColor(R.color.white));
                txt_zone_name.setTextColor(getActivity().getResources().getColor(R.color.white));
                txt_time.setTextColor(getActivity().getResources().getColor(R.color.white));
                txt_no_hvac.setAlpha(1f);
                txt_no_hvac.setTextColor(getActivity().getResources().getColor(R.color.white));
            }
            else
            {
                day_flag=false;
                outdoor_line.setBackgroundColor(getActivity().getResources().getColor(R.color.off_white_text));
                current_line.setBackgroundColor(getActivity().getResources().getColor(R.color.off_white_text));
                txt_current_wheather.setTextColor(getActivity().getResources().getColor(R.color.off_white_text));
                txt_cur_weather.setTextColor(getActivity().getResources().getColor(R.color.off_white_text));
                txt_temp_weather.setTextColor(getActivity().getResources().getColor(R.color.off_white_text));
                txt_humidity.setTextColor(getActivity().getResources().getColor(R.color.off_white_text));
                txt_out_air_quality.setTextColor(getActivity().getResources().getColor(R.color.off_white_text));
                outdoor_descp.setTextColor(getActivity().getResources().getColor(R.color.off_white_text));
                outdoor_aqi_graph.setTextColor(getActivity().getResources().getColor(R.color.off_white_text));
                txt_current_action.setTextColor(getActivity().getResources().getColor(R.color.off_white_text));
                gif_title.setTextColor(getActivity().getResources().getColor(R.color.off_white_text));
                txt_no_action.setTextColor(getActivity().getResources().getColor(R.color.off_white_text));
                outdoor_aqi_graph.setUnfinishedStrokeColor(getActivity().getResources().getColor(R.color.off_white_text));
                txt_quality.setTextColor(getActivity().getResources().getColor(R.color.off_white_text));
                txt_no_indoor.setTextColor(getActivity().getResources().getColor(R.color.off_white_text));
                txt_air_quality.setTextColor(getActivity().getResources().getColor(R.color.off_white_text));
                txt_zone_name.setTextColor(getActivity().getResources().getColor(R.color.off_white_text));
                txt_time.setTextColor(getActivity().getResources().getColor(R.color.off_white_text));
                txt_no_hvac.setAlpha(0.7f);
                txt_no_hvac.setTextColor(getActivity().getResources().getColor(R.color.off_white_text));
                System.out.println("cccccccccccccccccccccccc");
                if(App.isOrientationFlag()){
                    //img_main_home.setImageResource(R.drawable.home_background_night);
                    setImage(mcontext,R.drawable.home_background_night,img_main_home);//home_day_bg
                }
                else {
                    //img_main_home.setImageResource(R.drawable.home_main_background_night_mob);
                    setImage(mcontext,R.drawable.home_main_background_night_mob,img_main_home);//home_day_bg
                    img_btn_pull_down_panel.setImageResource(R.drawable.ic_down_arr_white);
                    img_btn_open_nav_drawer.setImageResource(R.drawable.ic_cogwheel_white);
                }
            }
                        int width = img_main_home.getWidth();
                        int height = img_main_home.getHeight();
                        img_sun_circle.setBackground(day_flag ? mcontext.getResources().getDrawable(R.drawable.sun_animation) : mcontext.getResources().getDrawable(R.drawable.sun_orange_animation));
                        setSunColor(str_hour, str_min);
                        img_sun_circle.setY((float) (((App.isOrientationFlag() ? y_cordinate_land[c_hr] : y_cordinate_potrait[c_hr]) * height / 100) - 0.5f * img_sun_circle.getHeight()));
                        img_sun_circle.setX(((c_hr + 1) * (width / 24)) - 0.5f * img_sun_circle.getWidth());
                        img_sun_circle.setVisibility(View.VISIBLE);






        }
        catch (Exception ex)
        {
            Log.e("TimeError",""+ex.getMessage());
        }
    }
    public void setSunColor(String hour,String min)
    {
        try {
            String jsonarr = App.getKelvin_data();
            JSONArray kelvinColorJsonArray = new JSONArray(jsonarr);
            int bestDifference=10000;
            int bestIndex=0;
            int bestminute=0;
            for (int i = 0; i < kelvinColorJsonArray.length(); i++)
            {
                JSONObject jsonObject= kelvinColorJsonArray.getJSONObject(i);
                int minute = jsonObject.getInt("minute");
                int difference = Math.abs(((Integer.parseInt(hour)*60)+Integer.parseInt(min)) - minute);
                if (difference < bestDifference) {
                    bestDifference = difference;
                    bestIndex = i;
                    bestminute=minute;
                }
            }
            if(bestIndex!=0)bestIndex=bestIndex-1;
            GradientDrawable background = (GradientDrawable) img_sun_circle.getBackground();
            JSONObject jsonObject=kelvinColorJsonArray.getJSONObject(bestIndex);
            JSONArray jsonArray=jsonObject.getJSONArray("color_code");
            background.setColors(new int[]{
                    Color.rgb(jsonArray.getInt(0), jsonArray.getInt(1), jsonArray.getInt(2)),
                    Color.rgb(jsonArray.getInt(0), jsonArray.getInt(1), jsonArray.getInt(2)),
                    mcontext.getResources().getColor(R.color.while_transparent)
            });
            background.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        }
        catch (Exception ex)
        {
            Logs.error(TAG,"------"+ex.getMessage());
        }
    }

    @Optional @OnClick(R.id.outdoor_aqi_graph)
    public void outdoor_aqi_graph()
    {
        clickable = false;
        // onOneClick(v);
        outdoor.show(getFragmentManager(),"OutdoorDialog");   outdoor_aqi_graph.setClickable(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                outdoor_aqi_graph.setClickable(true);
                // mButton.setEnabled(true);
            }
        }, 2000); // 2 seconds
    }
    @Optional @OnClick(R.id.indoor_aqi_graph)
    public void indoor_aqi_graph()
    {
        indoor.show(getFragmentManager(),"indoorDialog");
        indoor_aqi_graph.setClickable(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                indoor_aqi_graph.setClickable(true);
                // mButton.setEnabled(true);
            }
        }, 2000); // 2 seconds
    }
    public void setTime()
    {
        if( App.getHubInfo()!=null) {
            try
            {
                JSONArray jsonArray= App.getHubInfo().getJSONArray("data");
                String strDefaultTimeZone =jsonArray.getJSONObject(0).getString("CML_TIMEZONE");//defaultTimeZone.getDisplayName(false, TimeZone.SHORT);
                TimeZone tz = TimeZone.getTimeZone(strDefaultTimeZone);
                Calendar expdate = Calendar.getInstance(tz);
                SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                String month_name = month_date.format(expdate.getTime());
                String time = "";
                String hr = "", min = "";
                if (expdate.get(Calendar.HOUR_OF_DAY) < 9)
                    hr = "0" + expdate.get(Calendar.HOUR_OF_DAY);
                else hr = "" + expdate.get(Calendar.HOUR_OF_DAY);
                if (expdate.get(Calendar.MINUTE) < 9) min = "0" + expdate.get(Calendar.MINUTE);
                else min = "" + expdate.get(Calendar.MINUTE);
                if (new PrefManager(mcontext).getValue("time_fomat").equals("1")) {
                    time = hr + ":" + min;
                } else {
                    time = TimeFormatChange.convert12(hr + ":" + min);
                }
                String day_time = expdate.get(Calendar.DAY_OF_MONTH) + " | " + time;
                txt_time.setText("" + month_name.toUpperCase() + " " + day_time);
            }
            catch (Exception ex)
            {
                Logs.error(TAG,"HubTimeError:"+ex.getMessage());
            }
        }
    }
    public void hvacOnOff(boolean onoffstate)
    {
        App.setCallfrom("hvac");
        try
        {
            JSONObject obj = new JSONObject();
            if(onoffstate) obj.put("data", new JSONObject().put("CML_STATE", "on").put("Id", hvac_id).put("manufacturer",manufaturer));
            else obj.put("data", new JSONObject().put("CML_STATE", "off").put("Id", hvac_id).put("manufacturer",manufaturer));
            obj.put("type", "hvac_power");
            App.getSocket().emit("action", obj);
            //  App.getSocket().emit("trigger_dawn_simulation", JsonObjectCreater.getJsonObject("trigger_dawn_simulation", obj));
        }
        catch (Exception ex){

            Logs.info(TAG+"_SimulationRequestError",""+ex.getMessage());
        }
    }
    public void disableAllViews(View v,boolean f){
        v.setEnabled(f);
        if(v instanceof ViewGroup){
            for (int i = 0; i < ((ViewGroup)v).getChildCount();  i++) {
                View view = ((ViewGroup)v).getChildAt(i);
                disableAllViews(view,f);
            }
        }
    }
    private void setComfortRotation(int lower_point) {
        double degree = lower_point*6.735;
        donut_progress.setRotation((float)degree);
    }
    public void setThermostatOnOffColor(int color_id)
    {
        img_meter_indicator.setColorFilter(mcontext.getResources().getColor(color_id), PorterDuff.Mode.SRC_IN);
        txt_comfort_title.setTextColor(mcontext.getResources().getColor(color_id));
        txt_comfortrange.setTextColor(mcontext.getResources().getColor(color_id));
    }
    @Override
    public void onResume() {
        super.onResume();
        timer_gif.cancel();
        timer_gif = new Timer();
        gifrotation();
        timer.cancel();
        timer = new Timer();
        changeText();
    }
    @Override
    public void onStop() {
        super.onStop();
        timer_gif.cancel();
        if (!App.isOrientationFlag()){
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }else {
            timer.cancel();
        }
    }
    public void getZoneTitle()
    {
        try
        {
            arr_zone_title.clear();
            JSONArray jsonArray=App.getHvacZoneProvisionedJson().getJSONArray("data");
            if (App.getHvacZoneProvisionedJson().getJSONArray("data").toString().equals("[]"))
            {
                hvac_flag = false;
            }
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("manufacturer").equals("Bonaire")) {
                    Zone zone = new Zone();
                    zone.setZone_id(jsonObject.getString("CML_ID"));
                    zone.setZone_title(jsonObject.getString("CML_TITLE"));
                    arr_zone_title.add(zone);
                }
            }
            if(arr_zone_title.size()>0) {
                CustomZoneListAdapter customZoneListAdapter = new CustomZoneListAdapter(mcontext, arr_zone_title);
                spn_zone.setAdapter(customZoneListAdapter);
                if (App.getPosition() == -1) App.setPosition(0);
                if(App.getPosition()<arr_zone_title.size()) spn_zone.setSelection(App.getPosition(), false);
                spn_zone.setOnItemSelectedListener(this);
                hvac_flag = true;
            }
            else
            {
                hvac_flag = false;
            }
        }
        catch (Exception ex)
        {
            Logs.info(TAG+"_ZoneTitleError",""+ex.getMessage());
        }
    }
    @Optional @OnClick(R.id.img_hvac_power)
    public void img_hvac_power()
    {
        if(hvac_state) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("hvac_flag", hvac_state);
            bundle.putBoolean("heat_flag", heat_mode_flag);
            bundle.putBoolean("cool_flag", cool_mode_flag);
            bundle.putString("hvac_id", hvac_id);
            bundle.putString("fan_speed",str_fan_speed );
            bundle.putString("mode",str_mode );
            bundle.putString("type",str_type );
            bundle.putInt("air_type",str_air_type );
            bundle.putInt("highest_point",highest_point );
            bundle.putInt("lower_point",lower_point );
            bundle.putString("dialogtype", "bonair_hvac");
            bundle.putString("manufaturer", manufaturer);
            bundle.putString("hvac_mode_type", desire_temp_flag?"heater":"");
            ThermostatDialog dialog = new ThermostatDialog();
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "Thermostat_Dialog");
        }
        else
        {
            hvacOnOff(true);
        }
    }
    public void getHumidity()
    {
        String hvac_name="";
        int int_mode=192;
        try {
            Iterator<String> keys = App.getDataStorageJson().getJSONObject(ZONES).keys();
            while (keys.hasNext()) {
                JSONObject jsonObject=App.getDataStorageJson().getJSONObject(ZONES).getJSONObject(keys.next());
                if(jsonObject.getString("CML_TITLE").equals("Whole Home"))
                {
                    //hvac_name=jsonObject.getString("CML_TITLE");
                    humidity=(int)Math.round(jsonObject.getDouble("CML_RELATIVE_HUMIDITY"));
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
        Logs.info(TAG,"I m in destroy");
        v.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        Drawable d = webView.getDrawable();
        if (d instanceof GifDrawable) {
            ((GifDrawable) d).recycle();
        }
        final pl.droidsonroids.gif.GifImageView[] view_gif = {view1, view2, view3, view4i};
        for (int i=0;i<view_gif.length;i++) {
            Drawable d1 = view_gif[i].getDrawable();
            if (d1 instanceof GifDrawable) {
                ((GifDrawable) d1).recycle();
            }
        }
        humiditySync(false);
        if(timer_sun!=null){
            timer_sun.cancel();
            timer_sun = null;
        }
        if(timer_hr!=null){
            timer_hr.cancel();
            timer_hr = null;
        }
        System.gc();
        System.runFinalization();
        Runtime.getRuntime().gc();
        if(App.isOrientationFlag()) {
            if (home_health_view_pager != null) home_health_view_pager.removeAllViewsInLayout();
            spn_zone.removeAllViewsInLayout();
        }

    }
    public void humiditySync(boolean b)
    {
        String status="";
        if(b)status="start";
        else status="stop";
        if (App.getSocket() != null) {
            try {
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("data_points", new JSONObject().put("status", status)));
            }
            catch (Exception ex)
            {
                Logs.info(TAG+"_humidity_sync_error",""+ex.getMessage());
            }
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
        this.position=position1;
        Logs.info(TAG+"_ItemSelectPosition",""+position);
        device_id=arr_zone_title.get(position).getZone_id();
        App.setPosition(position);
        getSetPoint(position);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    public void startSunAnimation()
    {
        final ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(img_sun_circle, "scaleX",0.9f);
        final ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(img_sun_circle, "scaleY", 0.9f);
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(img_sun_circle, "alpha",  1f, .3f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(img_sun_circle, "alpha", .3f, 1f);
        scaleDownX.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDownX.setRepeatMode(ObjectAnimator.RESTART);
        scaleDownY.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDownY.setRepeatMode(ObjectAnimator.RESTART);
        fadeOut.setDuration(1000);
        fadeIn.setDuration(1000);
        scaleDownX.setDuration(1000);
        scaleDownY.setDuration(1000);
        final AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY);
        //scaleDown.play(fadeIn).after(fadeOut);
        scaleDown.start();
    }
    @OnClick(R.id.btn_next)
    public void btn_next()
    {
        if(cout==23) {
            cout = 0;
            x_condiante = 0;
        }
        int width = img_main_home.getWidth();
        int height = img_main_home.getHeight();
        x_condiante+=width/24;
        System.out.println("***********"+height);
        if(cout<24) {
            //Toast.makeText(mcontext, "********"+x_condiante+"*********"+y_cordinate_land[cout]+"*******************"+(float) (((App.isOrientationFlag() ? y_cordinate_land[cout] : y_cordinate_potrait[cout]) * (height / 100))- 0.5f * img_sun_circle.getHeight() ), Toast.LENGTH_SHORT).show();
            img_sun_circle.setY((float) (((App.isOrientationFlag()?y_cordinate_land[cout]:y_cordinate_potrait[cout])*height/100)- 0.5f * img_sun_circle.getHeight()));
            img_sun_circle.setX(((cout+1)*(width/24))- 0.5f * img_sun_circle.getWidth());
            img_sun_circle.setVisibility(View.VISIBLE);
            cout+=1;
        }
        setSunColor(""+cout,""+0);
        // //Toast.makeText(mcontext, "hour---"+cout, Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.btn_change_backround)
    public void btn_change_backround()
    {
        Drawable img_arr[]={
                getActivity().getResources().getDrawable(App.isOrientationFlag() ? R.mipmap.img_tab_overcast : R.mipmap.img_mob_overcast),
                getActivity().getResources().getDrawable(App.isOrientationFlag() ? R.mipmap.img_tab_mostly_cloudy : R.mipmap.img_mob_mostly_cloudy),
                getActivity().getResources().getDrawable(App.isOrientationFlag() ? R.mipmap.img_tab_fog_low : R.mipmap.img_mob_fog_low),
                getActivity().getResources().getDrawable(App.isOrientationFlag() ? R.mipmap.img_tab_partly_cloudy : R.mipmap.img_mob_partly_cloudy),
                getActivity().getResources().getDrawable(App.isOrientationFlag() ? R.mipmap.img_tab_clear_cloudless : R.mipmap.img_mob_clear_cloud),
                getActivity().getResources().getDrawable(App.isOrientationFlag() ? R.mipmap.img_tab_overcast_rain : R.mipmap.img_mob_overcast_rain),
                getActivity().getResources().getDrawable(App.isOrientationFlag() ? R.mipmap.img_tab_heavy_rain : R.mipmap.img_mob_heavy_rain),
                getActivity().getResources().getDrawable(App.isOrientationFlag() ? R.mipmap.img_tab_overcast_snow : R.mipmap.img_mob_overcas_snow)
        };
        if(image_count==img_arr.length-1) image_count=0;
        if(image_count<img_arr.length) {
            img_main_home.setBackground(img_arr[image_count]);
            image_count++;
        }
    }
    public static boolean isTimeBetweenTwoTime(String currentTime) throws ParseException {
        boolean valid = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat month_date = new SimpleDateFormat("HH:mm");
        TimeZone tz = TimeZone.getTimeZone(strDefaultTimeZone);
        month_date.setTimeZone(tz);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar expdate = Calendar.getInstance();
        if(!sun_rise_time.equals("")) expdate.setTime(sdf.parse(sun_rise_time));
        Date dateCompareOne = parseDate(sun_rise_time.equals("")?"06:30":month_date.format(expdate.getTime()));
        if(!sun_set_time.equals("")) expdate.setTime(sdf.parse(sun_set_time));
        Date dateCompareTwo = parseDate(sun_set_time.equals("")?"19:00":month_date.format(expdate.getTime()));
        Date date = parseDate(currentTime);
        valid = dateCompareOne.before(date) && dateCompareTwo.after(date);
        return valid;
    }
    private static Date parseDate(String date) {
        final String inputFormat = "HH:mm";
        SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);
        try {
            return inputParser.parse(date);
        } catch (ParseException e) {
            return new Date(0);
        }
    }
    public void setWeatherImages()
    {

        try {
            if (App.getweather_condition() != null) {
                JSONArray jsonArayyy = App.getweather_condition().getJSONArray("data");
                if(jsonArayyy.length()>0)
                {
                    for(int i=0;i<jsonArayyy.length();i++) {
                        JSONObject jsonObject=jsonArayyy.getJSONObject(i);
                        if(jsonObject.getString("type").equals("dailyForecast")) {
                            sun_rise_time=jsonObject.getJSONObject("sun").getString("sunrise_time");
                            sun_set_time=jsonObject.getJSONObject("sun").getString("sunset_time");
                        }
                        if(jsonObject.getString("type").equals("weather")) {
                            iconCode = jsonObject.getInt("icon_code");
                            if (day_flag) {
                                switch ("" + iconCode) {
                                    case "1":
                                    case "2":
                                    case "3":
                                    case "4":
                                    case "5":
                                    case "6":
                                        setImage(mcontext,App.isOrientationFlag() ? R.mipmap.img_tab_clear_cloudless : R.mipmap.img_mob_clear_cloud,img_main_home);//home_day_bg
                                        //img_main_home.setImageResource(App.isOrientationFlag() ? R.mipmap.img_tab_clear_cloudless : R.mipmap.img_mob_clear_cloud);
                                        break;
                                    case "7":
                                    case "8":
                                    case "9":
                                    case "10":
                                    case "11":
                                    case "12":
                                    case "13":
                                    case "14":
                                    case "15":
                                        setImage(mcontext,App.isOrientationFlag() ? R.mipmap.img_tab_partly_cloudy : R.mipmap.img_mob_partly_cloudy,img_main_home);//home_day_bg
                                        //img_main_home.setImageResource(App.isOrientationFlag() ? R.mipmap.img_tab_partly_cloudy : R.mipmap.img_mob_partly_cloudy);
                                        break;
                                    case "16":
                                    case "17":
                                    case "18":
                                        setImage(mcontext,App.isOrientationFlag() ? R.mipmap.img_tab_fog_low : R.mipmap.img_mob_fog_low,img_main_home);//home_day_bg
                                        //img_main_home.setImageResource(App.isOrientationFlag() ? R.mipmap.img_tab_fog_low : R.mipmap.img_mob_fog_low);
                                        break;
                                    case "19":
                                    case "20":
                                    case "21":
                                        setImage(mcontext,App.isOrientationFlag() ? R.mipmap.img_tab_mostly_cloudy : R.mipmap.img_mob_mostly_cloudy,img_main_home);//home_day_bg
                                        //img_main_home.setImageResource(App.isOrientationFlag() ? R.mipmap.img_tab_mostly_cloudy : R.mipmap.img_mob_mostly_cloudy);
                                        break;
                                    case "22":
                                    case "35":
                                        setImage(mcontext,App.isOrientationFlag() ? R.mipmap.img_tab_overcast : R.mipmap.img_mob_overcast,img_main_home);//home_day_bg
                                        //img_main_home.setImageResource(App.isOrientationFlag() ? R.mipmap.img_tab_mostly_cloudy : R.mipmap.img_mob_mostly_cloudy);
                                        break;
                                    case "23":
                                    case "25":
                                    case "27":
                                        setImage(mcontext,App.isOrientationFlag() ? R.mipmap.img_tab_overcast_rain : R.mipmap.img_mob_overcast_rain,img_main_home);//home_day_bg
                                        //img_main_home.setImageResource(App.isOrientationFlag() ? R.mipmap.img_tab_overcast_rain : R.mipmap.img_mob_overcast_rain);
                                        break;
                                    case "24":
                                    case "29":
                                    case "26":
                                    case "32":
                                    case "34":
                                        setImage(mcontext,App.isOrientationFlag() ? R.mipmap.img_tab_overcast_snow : R.mipmap.img_mob_overcas_snow,img_main_home);//home_day_bg
                                        //img_main_home.setImageResource(App.isOrientationFlag() ? R.mipmap.img_tab_overcast_snow : R.mipmap.img_mob_overcas_snow);
                                        break;
                                    case "30":
                                    case "31":
                                    case "33":
                                    case "28":
                                        setImage(mcontext,App.isOrientationFlag() ? R.mipmap.img_tab_heavy_rain : R.mipmap.img_mob_heavy_rain,img_main_home);//home_day_bg
                                        //img_main_home.setImageResource(App.isOrientationFlag() ? R.mipmap.img_tab_heavy_rain : R.mipmap.img_mob_heavy_rain);
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Logs.error(TAG,"wetherimageseterror"+ex.getMessage());
        }
    }

    public void setImage(Context mcontext,int img_id,ImageView imageView)
    {
        /*if(App.isOrientationFlag()) {
            Picasso.with(mcontext)
                    .load(img_id)
                    .placeholder(day_flag?R.drawable.home_baground_day:R.drawable.home_background_night)
                    .noFade()
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(imageView);
        }
        else
        {*/
            ElementsRoomListAdapterPortrait.setImage(mcontext,img_id,imageView,TAG);
       // }
    }
    public void startSunMovement()
    {
        if (tsk_sun == null) {
            tsk_sun = new TimerTask() {
                @Override
                public void run() {
                    if(sun_timer_flag) {
                        Log.e("sun_movement", "-----------------------");
                        runOnUiThread(new Runnable() {
                            public void run() {
                                setSunPosotion();
                                setTime();

                            }
                        });

                    }
                }
            };
            timer_sun.schedule(tsk_sun, 0, 1000);
        }
    }
    public void startHr()
    {
        if (tsk_hr == null) {
            tsk_hr = new TimerTask() {
                @Override
                public void run() {
                    c_hr++;
                }
            };
            timer_hr.schedule(tsk_hr, 0, 10000);
        }
    }

    private void sendScreenImageName() {
        String name = "Home Fragment";
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