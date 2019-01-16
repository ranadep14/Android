package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.progressbarfragment.CO2_frag;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.progressbarfragment.NO2frag;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.progressbarfragment.O3frag;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.progressbarfragment.PM10frag;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.progressbarfragment.PM2frag;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.progressbarfragment.SO2frag;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.smooth_line_chart.SmoothLineChart;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.TimeFormatChange;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

/**
 * This class used to display Outdoor air quality data and map on Tablet.
 *
 * @author  Jaid Shaikh
 * @version 1.0
 * @since   2017-12-28`
 */

public class OutdoorAirQualityMain_new extends DialogFragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener {


    private Context mcontext;
    private int chooseColor=0;
    private int cColor=0;

    private int co_lowerLimit=0;
    private int co_upperLimit=0;
    private double co_diff;
    public static double co_Result=0;

    private int no_lowerLimit=0;
    private int no_upperLimit=0;
    private double no_diff;
    public static double no_Result=0;

    private int o3_lowerLimit=0;
    private int o3_upperLimit=0;
    private double o3_diff;
    public static double o3_Result=0;

    private int pm2_lowerLimit=0;
    private int pm2_upperLimit=0;
    private double pm2_diff;
    public static double pm2_Result=0;

    private int pm10_lowerLimit=0;
    private int pm10_upperLimit=0;
    private double pm10_diff;
    public static double pm10_Result=0;

    private int so_lowerLimit=0;
    private int so_upperLimit=0;
    private double so_diff;
    public static double so_Result=0;
    public static String t1="";
    public static String t2="";
    static String strDefaultTimeZone="";
    private TimerTask tsk_sun;
    private Timer timer_sun=null;



    public static OutdoorAirQualityMain_new newInstance() {
        return new OutdoorAirQualityMain_new();
    }

    static Double lati = 18.5081124;
    static Double logi = 73.7939685;

    Double latitude, longitude;

    private InputStream inputStream;
    private String webContent = "", part1 = "", part2 = "", part3 = "", completeData = "";


    private String c="CO";
    private String n="NO\u2082";
    private String s="SO\u2082";
    private String o="O₃";
    private DonutProgress CO2, NO2, O3, PM2_5, PM10, SO2;
    private GoogleMap mMap;
    private WebView tampilWeb;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    @BindView(R.id.close_nav)
    ImageView close_nav;
    @BindView(R.id.lyt_popullant)
    RelativeLayout lyt_popullant;
    @BindView(R.id.click_populant)
    LinearLayout click_populant;
    private MapView mMapView;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    @BindView(R.id.simpleChronometer)
    Chronometer simpleChronometer;
    @BindView(R.id.outdoor_time)
    TextView txt_time;

    @BindView(R.id.co2_status)TextView co2_status;
    @BindView(R.id.no2_status)TextView no2_status;
    @BindView(R.id.o3_status)TextView o3_status;
    @BindView(R.id.pm2_status)TextView pm2_status;
    @BindView(R.id.pm10_status)TextView pm10_status;
    @BindView(R.id.so2_status)TextView so2_status;

    @BindView(R.id.activity_main)LinearLayout lin_activity_main;

    LatLng latLng;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    private GraphView graph;
    Marker currLocationMarker;

    private TextView start;
    private TextView end;
    private Tracker mTracker;

    private double aup_co=0;
    private double aup_no2=0;
    private double aup_o3=0;
    private double aup_pm25=0;
    private double aup_pm10=0;
    private double aup_so2=0;
    private View v;
    private ArrayList<Integer> breezometer_aqi_list = new ArrayList<>();
    private ArrayList<String> timeList = new ArrayList<>();
    FragmentTransaction fragmentTransaction, fragmentTransaction2, fragmentTransaction3;
    @SuppressLint("StaticFieldLeak")
    public static FrameLayout frame_layout_co2, frame_layout_no2, frame_layout_o3;
    private String TAG=OutdoorAirQualityMain_new.this.getClass().getSimpleName();





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (!checkConfiguration()) {
                Logs.info(TAG,"Error in Outdoor Tablet Fragment");
            }
            App application = (App)getActivity().getApplication();
            mTracker = application.getDefaultTracker();
            sendScreenImageName();

            getLocationInfo();


            inputStream = getActivity().getAssets().open("map_marker.html");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            webContent = new String(buffer);
            Logs.info(TAG,"contents: "+ webContent);

        } catch (IOException e) {

            Logs.error(TAG,"exception: "+ e.toString());
        }
        timer_sun = new Timer();
        startClockTime();
        part1 = webContent.substring(0, webContent.indexOf("center"));

        part2 = webContent.substring(webContent.indexOf("zoom"),
                webContent.indexOf("L.marker"));

        part3 = webContent.substring(webContent.indexOf("var breezometerTiles"));

        completeData = part1 +
                "center: [" + latitude + "," + (longitude+0.0785) + "]," +
                part2 +
                "L.marker([" + latitude + "," + longitude + "]).addTo(map);" +
                part3;


    }



    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.outdoor_airquality_main_new, container,
                false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.getDialog().setCanceledOnTouchOutside(true);
        ButterKnife.bind(this, v);
        mcontext = v.getContext();
        click_populant.setClickable(false);
        lyt_popullant.setClickable(false);
        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            Logs.error(TAG+"Address Map", "Could not initialize google play"+e.getMessage());
        }


        simpleChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                setTime();

            }
        });
        simpleChronometer.start();
        UserInteractionSleep.siboSleepChecking(mcontext);

        App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_breezometer_data", new JSONObject()));
        setSubcriber();



        aup_co= HomeHeathByZoneFragment.au_co;

        aup_no2= HomeHeathByZoneFragment.au_no2;
        aup_pm25= HomeHeathByZoneFragment.au_pm2;
        aup_o3= HomeHeathByZoneFragment.au_o3;
        aup_pm10= HomeHeathByZoneFragment.au_pm10;
        aup_so2= HomeHeathByZoneFragment.au_so;


        CO2 = v.findViewById(R.id.prog_co2);
        CO2.setOnClickListener(this);

        CO2.setText(c);
        int a=(int)getCO2value();
        CO2.setProgress( a);

        NO2 = v.findViewById(R.id.prog_no2);
        NO2.setOnClickListener(this);
        NO2.setText(n);
        int b=(int)getNO2value();
        NO2.setProgress( b);

        O3 = v.findViewById(R.id.prog_o3);
        O3.setOnClickListener(this);

        O3.setText(o);
        int c=(int)getO3value();
        O3.setProgress( c);

        PM2_5 = v.findViewById(R.id.prog_pm2);
        PM2_5.setOnClickListener(this);
        int d=(int)getpm25value();
        PM2_5.setProgress( d);

        PM10 = v.findViewById(R.id.prog_pm10);
        PM10.setOnClickListener(this);
        int f=(int)getpm10value();
        PM10.setProgress( f);


        SO2 = v.findViewById(R.id.prog_so2);
        SO2.setOnClickListener(this);
        SO2.setText(s);
        int e=(int)getSO2value();
        SO2.setProgress( e);


        tampilWeb = v.findViewById(R.id.leaflet);
        tampilWeb.addJavascriptInterface(new WebAppInterface(getActivity()), "Android");
        tampilWeb.getSettings().setJavaScriptEnabled(true);
        tampilWeb.getSettings().setBuiltInZoomControls(false);
        tampilWeb.setWebViewClient(new WebViewClient());
        tampilWeb.setBackgroundColor(Color.TRANSPARENT);

        tampilWeb.loadData(completeData, "text/html", null);


        graph = v.findViewById(R.id.graph);


        // Progress Fragment Coding here
        frame_layout_co2 = v.findViewById(R.id.fragment_container_co2);
        frame_layout_co2.setClickable(false);
        frame_layout_co2.setOnClickListener(null);
        frame_layout_no2 = v.findViewById(R.id.fragment_container_no2);
        frame_layout_o3 = v.findViewById(R.id.fragment_container_o3);


        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction2 = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction3 = getActivity().getSupportFragmentManager().beginTransaction();





        tampilWeb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                tampilWeb.setEnabled(false);
                return true;
            }
        });


        SmoothLineChart chart = v.findViewById(R.id.smoothChart);
        start =v.findViewById(R.id.starttime);
        end =v.findViewById(R.id.endtime);


        // add below code to change the color of graph curve


        return v;
    }



    private double getCO2value() {


        double val_result=0;
        if(aup_co>=0 && aup_co<=4586){
            co_lowerLimit=0;
            co_upperLimit=4586;
            co_diff =75.6;
            int   firstValue = 4586 - 0;
            double   secondValue = aup_co - 0 ;
            val_result = (secondValue * co_diff)/firstValue;
        }
        else if(aup_co>4586 && aup_co<=9171) {
            co_lowerLimit = 4586;
            co_upperLimit = 9171;
            co_diff =72;
            int   firstValue = 9171 - 4586;
            double secondValue = aup_co - 4586 ;
            val_result = (secondValue * co_diff)/firstValue;
            val_result = val_result + 75.6;
        }

        else if(aup_co>9171 && aup_co<=13101) {
            co_lowerLimit = 9171;
            co_upperLimit = 13101;
            co_diff =72;
            int  firstValue = 13101-9171;
            double  secondValue = aup_co - 9171 ;
            val_result = (secondValue * co_diff)/firstValue;
            val_result = val_result + 75.6 + 72;
        }
        else if(aup_co>13101 && aup_co<=17468) {
            co_lowerLimit = 13101;
            co_upperLimit = 17468;
            co_diff =72;
            int  firstValue = 17468-13101;
            double  secondValue = aup_co - 13101 ;
            val_result = (secondValue * co_diff)/firstValue;
            val_result = val_result + 75.6 + 72+72;
        }
        else if(aup_co>17468 && aup_co<=48785) {
            co_lowerLimit = 17468;
            co_upperLimit = 48785;
            co_diff =72;
            int  firstValue =48785-17468;
            double  secondValue = aup_co - 17468;
            val_result = (secondValue * co_diff)/firstValue;
            val_result = val_result + 75.6 + 72 + 72+72;
        }


        if(co_Result>100 ){
            co_Result=99;
        }
        if(co_Result==0 ){
            co_Result=100;
        }
        double ivalue = val_result * 100/360;
        ivalue = 100 - Math.round(ivalue);

        co_Result=ivalue;
        setColorStatus(co2_status,CO2,ivalue);




        lin_activity_main.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                UserInteractionSleep.userInteract(mcontext);

                return false;
            }
        });

        return ivalue;

    }


    private double getNO2value() {

        double val_result=0;
        if(aup_no2>=0 && aup_no2<=27){
            no_lowerLimit=0;
            no_upperLimit=27;
            no_diff =75.6;
            int   firstValue = 27 - 0;
            double   secondValue = aup_no2 - 0 ;
             val_result = (secondValue * no_diff)/firstValue;
            //no_Result=79+getNOfinal();
        }
        else if(aup_no2>27 && aup_no2<=53) {
            no_lowerLimit = 27;
            no_upperLimit = 53;
            no_diff =72;
            int   firstValue = 53 - 27;
            double secondValue = aup_no2 - 27 ;
             val_result = (secondValue * no_diff)/firstValue;
            val_result = val_result + 75.6;
        }

        else if(aup_no2>53 && aup_no2<=106) {
            no_lowerLimit = 53;
            no_upperLimit = 106;
            no_diff =72;
            int  firstValue = 106 - 53;
            double  secondValue = aup_no2 - 53 ;
             val_result = (secondValue * no_diff)/firstValue;
            val_result = val_result + 75.6 + 72;
        }
        else if(aup_no2>106 && aup_no2<=319) {
            no_lowerLimit = 106;
            no_upperLimit = 319;
            no_diff =72;
            int  firstValue = 319-106;
            double  secondValue = aup_no2 - 106 ;
             val_result = (secondValue * no_diff)/firstValue;
            val_result = val_result + 75.6 + 72+72;
        }
        else if(aup_no2>319 && aup_no2<=532) {
            no_lowerLimit = 319;
            no_upperLimit = 532;
            no_diff =72;
            int  firstValue = 532 - 319;
            double  secondValue = aup_no2 - 319 ;
             val_result = (secondValue * no_diff)/firstValue;
            val_result = val_result + 75.6 + 72 + 72+72;
        }

        if(no_Result>100){
            no_Result=99;
        }
        if(no_Result==0 ){
            no_Result=100;
        }


       double a =360 - val_result;
        double ivalue = val_result * 100/360;
        ivalue = 100 - Math.round(ivalue);
        no_Result=ivalue;
        setColorStatus(no2_status,NO2,ivalue);



        ////Toast.makeText(mcontext, "Result no_Result : "+no_Result, //Toast.LENGTH_SHORT).show();
        return ivalue;

    }



    private double getO3value() {

        double val_result=0;
        if(aup_o3>=0 && aup_o3<=28){
            o3_lowerLimit=0;
            o3_upperLimit=28;
            o3_diff =75.6;
            int   firstValue = 28 - 0;
            double   secondValue = aup_o3 - 0 ;
            val_result = (secondValue * o3_diff)/firstValue;
        }
        else if(aup_o3>28 && aup_o3<=56) {
            o3_lowerLimit = 28;
            o3_upperLimit = 56;
            o3_diff =72;
            int   firstValue = 56 - 28;
            double secondValue = aup_o3 - 28 ;
            val_result = (secondValue * o3_diff)/firstValue;
            val_result = val_result + 75.6;
        }

        else if(aup_o3>56 && aup_o3<=92) {
            o3_lowerLimit = 56;
            o3_upperLimit = 92;
            o3_diff =72;
            int  firstValue = 92 - 56;
            double  secondValue = aup_o3 - 56;
            val_result = (secondValue * o3_diff)/firstValue;
            val_result = val_result + 75.6 + 72;
        }
        else if(aup_o3>92 && aup_o3<=153) {
            o3_lowerLimit = 92;
            o3_upperLimit = 153;
            o3_diff =72;
            int  firstValue = 153-92;
            double  secondValue = aup_o3 - 92 ;
            val_result = (secondValue * o3_diff)/firstValue;
            val_result = val_result + 75.6 + 72+72;
        }
        else if(aup_o3>153 && aup_o3<=306) {
            o3_lowerLimit = 153;
            o3_upperLimit = 306;
            o3_diff =72;
            int  firstValue = 306-153;
            double  secondValue = aup_o3 -153 ;
            val_result = (secondValue * o3_diff)/firstValue;
            val_result = val_result + 75.6 + 72+72+72;
        }

        if(o3_Result>100){
            o3_Result=99;
        }


        double ivalue = val_result * 100/360;
        ivalue = 100 - Math.round(ivalue);

        o3_Result=ivalue;
        setColorStatus(o3_status,O3,ivalue);



        return ivalue;


    }



    private double getpm25value() {


        double val_result=0;
        if(aup_pm25>=0 && aup_pm25<=20){
            pm2_lowerLimit=0;
            pm2_upperLimit=20;
            pm2_diff =75.6;
            int   firstValue = 20 - 0;
            double   secondValue = aup_pm25 - 0 ;
            val_result = (secondValue * pm2_diff)/firstValue;
        }
        else if(aup_pm25>20 && aup_pm25<=40) {
            pm2_lowerLimit = 20;
            pm2_upperLimit = 40;
            pm2_diff =72;
            int   firstValue = 40 - 28;
            double secondValue = aup_pm25 - 20 ;
            val_result = (secondValue * pm2_diff)/firstValue;
            val_result = val_result + 75.6;
        }

        else if(aup_pm25>40 && aup_pm25<=65) {
            pm2_lowerLimit = 40;
            pm2_upperLimit = 65;
            pm2_diff =72;
            int  firstValue = 65 - 40;
            double  secondValue = aup_pm25 - 40 ;
            val_result = (secondValue * pm2_diff)/firstValue;
            val_result = val_result + 75.6 + 72;
        }
        else if(aup_pm25>65 && aup_pm25<=125) {
            pm2_lowerLimit = 65;
            pm2_upperLimit = 125;
            pm2_diff =72;
            int  firstValue = 125-65;
            double  secondValue = aup_pm25 - 65 ;
            val_result = (secondValue * pm2_diff)/firstValue;
            val_result = val_result + 75.6 + 72+72;
        }
        else if(aup_pm25>125 && aup_pm25<=500) {
            pm2_lowerLimit = 125;
            pm2_upperLimit = 500;
            pm2_diff =72;
            int  firstValue = 500 - 125;
            double  secondValue = aup_pm25 - 125 ;
            val_result = (secondValue * pm2_diff)/firstValue;
            val_result = val_result + 75.6 + 72 + 72+72;
        }

        if(pm2_Result>100){
            pm2_Result=99;
        }

        double ivalue = val_result * 100/360;
        ivalue = 100 - Math.round(ivalue);

        pm2_Result=ivalue;
        setColorStatus(pm2_status,PM2_5,ivalue);



        return ivalue;


    }


    private double getpm10value() {


        double val_result=0;
        if(aup_pm10>=0 && aup_pm10<=38){
            pm10_lowerLimit=0;
            pm10_upperLimit=38;
            pm10_diff =75.6;
            int   firstValue = 38 - 0;
            double   secondValue = aup_pm10 - 0 ;
            val_result = (secondValue * pm10_diff)/firstValue;
        }
        else if(aup_pm10>38&& aup_pm10<=75) {
            pm10_lowerLimit = 38;
            pm10_upperLimit = 75;
            pm10_diff =72;
            int  firstValue = 75 - 38;
            double  secondValue = aup_pm10 - 38 ;
            val_result = (secondValue * pm10_diff)/firstValue;
            val_result = val_result + 75.6;
        }

        else if(aup_pm10>75 && aup_pm10<=120) {
            pm10_lowerLimit =75;
            pm10_upperLimit = 120;
            pm10_diff =72;
            int  firstValue = 120-75;
            double  secondValue = aup_pm10 - 75 ;
            val_result = (secondValue *pm10_diff)/firstValue;
            val_result = val_result + 75.6 + 72;
        }
        else if(aup_pm10>120 && aup_pm10<=250) {
            pm10_lowerLimit = 120;
            pm10_upperLimit = 250;
            pm10_diff =72;
            int  firstValue = 250-120;
            double  secondValue = aup_pm10 - 120 ;
            val_result = (secondValue * pm10_diff)/firstValue;
            val_result = val_result + 75.6 + 72+72;
        }
        else if(aup_pm10>250 && aup_pm10<=600) {
            pm10_lowerLimit = 250;
            pm10_upperLimit = 600;
            pm10_diff =72;
            int  firstValue = 600 - 250;
            double  secondValue = aup_pm10 - 250 ;
            val_result = (secondValue * pm10_diff)/firstValue;
            val_result = val_result + 75.6 + 72 + 72+72;
        }

        if(pm10_Result>100){
            pm10_Result=99;
        }
        if(pm10_Result==0 ){
            pm10_Result=100;
        }

        double ivalue = val_result * 100/360;
        ivalue = 100 - Math.round(ivalue);

        pm10_Result=ivalue;
        setColorStatus(pm10_status,PM10,ivalue);



        return ivalue;


    }



    private double getSO2value() {


        double val_result=0;
        if(aup_so2>=0 && aup_so2<=19){
            so_lowerLimit=0;
            so_upperLimit=19;
            so_diff =75.6;
            int   firstValue = 19 - 0;
            double   secondValue = aup_so2 - 0 ;
            val_result = (secondValue * so_diff)/firstValue;
        }
        else if(aup_so2>19 && aup_so2<=38) {
            so_lowerLimit = 19;
            so_upperLimit = 38;
            so_diff =72;
            int   firstValue = 38 - 19;
            double secondValue = aup_so2 - 19 ;
            val_result = (secondValue * so_diff)/firstValue;
            val_result = val_result + 75.6;
        }

        else if(aup_so2>38 && aup_so2<=115) {
            so_lowerLimit = 38;
            so_upperLimit = 115;
            so_diff =72;
            int  firstValue = 115 - 38;
            double  secondValue = aup_so2 - 38 ;
            val_result = (secondValue * so_diff)/firstValue;
            val_result = val_result + 75.6 + 72;
        }
        else if(aup_so2>115 && aup_so2<=191) {
            so_lowerLimit = 115;
            so_upperLimit = 191;
            so_diff =72;
            int  firstValue = 191-115;
            double  secondValue = aup_so2 - 115 ;
            val_result = (secondValue * so_diff)/firstValue;
            val_result = val_result + 75.6 + 72+72;
        }
        else if(aup_so2>191 && aup_so2<=573) {
            so_lowerLimit = 191;
            so_upperLimit = 573;
            so_diff =72;
            int  firstValue = 573 - 191;
            double  secondValue = aup_so2 - 191 ;
            val_result = (secondValue * so_diff)/firstValue;
            val_result = val_result + 75.6 + 72 + 72+72;
        }

        if(so_Result==0 ){
            so_Result=100;
        }
        if(so_Result>100){
            so_Result=99;
        }

        double ivalue = val_result * 100/360;
        ivalue = 100 - Math.round(ivalue);

        so_Result=ivalue;
        setColorStatus(so2_status,SO2,ivalue);



        return ivalue;
    }





    public void setColorStatus(final TextView status, final DonutProgress v, double result) {
        double score=result;
        Logs.info(TAG,"score_pollutant_color: "+score);

        if (score >= 0 && score <= 39) {
            status.setText("Poor");
            v.setFinishedStrokeColor(Color.rgb(246, 103, 101));


        } else if (score >= 40 && score <= 59) {
            status.setText("Moderate");
            v.setFinishedStrokeColor(Color.rgb(229,197,0));


        } else if (score >= 60 && score <= 69) {
            status.setText("Moderate");
            v.setFinishedStrokeColor(Color.rgb(229,197,0));

        } else if (score >= 70 && score <= 79) {
            status.setText("Moderate");
            v.setFinishedStrokeColor(Color.rgb(229,197,0));

        } else if (score >= 80 && score <= 89) {

            status.setText("Good");
            v.setFinishedStrokeColor(Color.rgb(68,255,233));

        } else if (score >= 90 && score <= 100) {
            status.setText("Excellent");
            v.setFinishedStrokeColor(Color.rgb(68, 255, 233));

        }
    }



    private void DrawGraph(ArrayList<Integer> breezometer_aqi_list,
                           ArrayList<String> timeList) {
        SmoothLineChart chart = v.findViewById(R.id.smoothChart);
        cColor=( HomeHeathByZoneFragment.breezometer_aqi_score);

        float arr[] = new float[12];


        for(int i=0;i<breezometer_aqi_list.size();i++) {

            arr[i] = (float) breezometer_aqi_list.get(i) / 10;
            Logs.info("values: ", String.valueOf(arr[i]));


            chart.setData(new PointF[]{
                    new PointF(0, (float) breezometer_aqi_list.get(0) / 10), // values are bet 1 to 10
                    new PointF(1, (float) breezometer_aqi_list.get(1) / 10),
                    new PointF(2, (float) breezometer_aqi_list.get(2) / 10),
                    new PointF(3, (float) breezometer_aqi_list.get(3) / 10),
                    new PointF(4, (float) breezometer_aqi_list.get(4) / 10),
                    new PointF(5, (float) breezometer_aqi_list.get(5) / 10),
                    new PointF(6, (float) breezometer_aqi_list.get(6) / 10),
                    new PointF(7, (float) breezometer_aqi_list.get(7) / 10),
                    new PointF(8, (float) breezometer_aqi_list.get(8) / 10),
                    new PointF(9, (float) breezometer_aqi_list.get(9) / 10),
                    new PointF(10,(float) breezometer_aqi_list.get(10) / 10),
                    new PointF(11,(float) breezometer_aqi_list.get(11) / 10),

            });
chooseColor=breezometer_aqi_list.get(6);
            if (chooseColor>=0 && chooseColor <=19){
                chart.setCurveColor(Color.rgb(252, 68, 83));
            }else if (chooseColor>20 & chooseColor<=39){
                chart.setCurveColor(Color.rgb(246, 103, 101));
            }else if (chooseColor>40 && chooseColor<=59){
                chart.setCurveColor (Color.rgb(250, 225, 116));
            }else if (chooseColor>60 & chooseColor<=79){
                chart.setCurveColor(Color.rgb(115, 223, 159));
            }else if (chooseColor>80 & chooseColor<=100) {
                chart.setCurveColor(Color.rgb(49, 212, 172));
            }

            // graph.addSeries(series);
            graph.getGridLabelRenderer().setVerticalAxisTitle("SCORE");
            graph.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.CENTER);
            graph.setTitle("FORCASTED AIR QUALITY");


            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
            staticLabelsFormatter.setHorizontalLabels(new String[]{timeList.get(0),
                    timeList.get(1)});

            staticLabelsFormatter.setVerticalLabels(new String[]{"0", "", "", "", "", "100"});
            graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


        }


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

                try {
                    JSONArray jsonArrayy = App.getbreezometer_data().getJSONArray("data");
                    Logs.debug(TAG, "Breezometer_array: " + jsonArrayy);

                    for (int i = 0; i < jsonArrayy.length(); i++) {
                        JSONObject jsonObject = jsonArrayy.getJSONObject(i);

                        breezometer_aqi_list.add(
                                Integer.parseInt(
                                        jsonObject.getString("breezometer_aqi"))
                        );

                        if(i == 0){

                           String dataTime = jsonObject.getString("datetime");


                            strDefaultTimeZone = App.getHubInfo().getJSONArray("data").getJSONObject(0).getString("CML_TIMEZONE");

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
                            SimpleDateFormat month_date = new SimpleDateFormat("HH:mm");
                            TimeZone tz = TimeZone.getTimeZone(strDefaultTimeZone);
                            month_date.setTimeZone(tz);
                            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                            Calendar expdate = Calendar.getInstance();
                            expdate.setTime(sdf.parse(dataTime));
                         //   //Toast.makeText(mcontext, ""+month_date.format(expdate.getTime()), Toast.LENGTH_SHORT).show();
                            start.setText(""+month_date.format(expdate.getTime()));
                        }

                        if(i == jsonArrayy.length()-1){

                            String dataTime = jsonObject.getString("datetime");

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
                            SimpleDateFormat month_date = new SimpleDateFormat("HH:mm");
                            TimeZone tz = TimeZone.getTimeZone(strDefaultTimeZone);
                            month_date.setTimeZone(tz);
                            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                            Calendar expdate = Calendar.getInstance();
                            expdate.setTime(sdf.parse(dataTime));
                         //   //Toast.makeText(mcontext, ""+month_date.format(expdate.getTime()), Toast.LENGTH_SHORT).show();

                            end.setText(""+month_date.format(expdate.getTime()));
                        }
                    }

                    DrawGraph(breezometer_aqi_list, timeList);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Log.e("`",""+e.getMessage());
                }

            }

        };
        Subcriber s = new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);


    }
    @OnClick(R.id.close_nav)
    public void close_nav() {
        this.dismiss();
    }


    protected synchronized void buildGoogleApiClient() {
        //      ////Toast.makeText(getActivity(), "buildGoogleApiClient", //Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())

                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {

            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {

                    break;
                } else {
                    //   finish();
                    startActivity(getActivity().getIntent());
                }
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            latLng = new LatLng(18.5078838, 73.7964008);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker100));
            currLocationMarker = mMap.addMarker(markerOptions);
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //meLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) getActivity());

    }

    @Override
    public void onConnectionSuspended(int i) {
        //   ////Toast.makeText(getActivity(), "onConnectionSuspended", //Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }


    public class WebAppInterface {
        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /**
         * Get the value
         */
        @JavascriptInterface
        public Double getValue() {
            Double value = lati;
            return value;

        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.prog_co2:

                try {
                    click_populant.setClickable(false);
                    lyt_popullant.setClickable(false);
                    frame_layout_co2.setVisibility(View.VISIBLE);
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container_co2, CO2_frag.newInstance());
                    transaction.commit();

                } catch (IllegalStateException e) {

                }
                break;
            case R.id.prog_no2:
                try {
                    click_populant.setClickable(false);
                    lyt_popullant.setClickable(false);
                    frame_layout_co2.setVisibility(View.VISIBLE);
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container_co2, NO2frag.newInstance());
                    transaction.commit();

                } catch (IllegalStateException e) {

                }
                break;
            case R.id.prog_o3:
                try {
                    click_populant.setClickable(false);
                    lyt_popullant.setClickable(false);
                    frame_layout_co2.setVisibility(View.VISIBLE);
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container_co2, O3frag.newInstance());
                    transaction.commit();

                } catch (IllegalStateException e) {

                }
                break;
            case R.id.prog_pm2:
                try {
                    click_populant.setClickable(false);
                    lyt_popullant.setClickable(false);
                    frame_layout_co2.setVisibility(View.VISIBLE);
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container_co2, PM2frag.newInstance());
                    transaction.commit();

                } catch (IllegalStateException e) {

                }
                break;
            case R.id.prog_pm10:
                try {
                    click_populant.setClickable(false);
                    lyt_popullant.setClickable(false);
                    frame_layout_co2.setVisibility(View.VISIBLE);
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container_co2, PM10frag.newInstance());
                    transaction.commit();

                } catch (IllegalStateException e) {

                }
                break;
            case R.id.prog_so2:
                try {
                    click_populant.setClickable(false);
                    lyt_popullant.setClickable(false);
                    frame_layout_co2.setVisibility(View.VISIBLE);
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container_co2, SO2frag.newInstance());
                    transaction.commit();

                } catch (IllegalStateException e) {

                }
                break;
        }

    }


    public void getLocationInfo()
    {

        try {
            JSONArray jsonArray = App.getHubInfo().getJSONArray("data");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            latitude=jsonObject.getDouble("CML_LATITUDE");
            longitude=jsonObject.getDouble("CML_LONGITUDE");
        }
        catch (Exception ex){
            Log.e("LocationError",""+ex.getMessage());}


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
            Logs.error(TAG,"HubTimeError"+ex.getMessage());
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

    private void sendScreenImageName() {
        String name = "Outdoor Tablet Fragment";
      //  String username=new PrefManager(getActivity()).getValue("User_Id");
        // [START screen_view_hit]
        Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName("Darwin~" + name);
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
