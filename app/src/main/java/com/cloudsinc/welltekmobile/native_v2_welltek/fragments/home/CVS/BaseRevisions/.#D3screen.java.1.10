package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.D3data;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.TimeFormatChange;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.jjoe64.graphview.GraphView;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;

/**
 * This class used to display Outdoor air quality data  on Mobile.
 *
 * @author  Jaid Shaikh
 * @version 1.0
 * @since   2017-12-1
 */

public class D3screen extends DialogFragment{
    private LatLng latLng;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    private GoogleMap mMap;
    Marker currLocationMarker;


    Double latitude, longitude;

    public static String t11="";
    public static String t22="";
    private Tracker mTracker;


    public static D3screen newInstance() {
        return new D3screen();
    }


    private View v;
    Context mcontext;
    TabLayout tabLayout;

    @BindView(R.id.img_close)
    ImageView img_close;
    @BindView(R.id.txt_time)
    TextView txt_time;
 @BindView(R.id.zone_name)
    TextView zone_name;

 @BindView(R.id.lin_activity_main)LinearLayout lin_activity_main;

    final int[] ICONS = new int[]{
            R.drawable.ic_right_black,
            R.drawable.ic_right_black,
            R.drawable.ic_right_black,
            R.drawable.ic_right_black,
            R.drawable.ic_right_black,
            R.drawable.ic_right_black};

    @SuppressLint("StaticFieldLeak")
    public static FrameLayout  fragment_container_map, fragment_container_forecast,fragment_container_d3;
    private InputStream inputStream;
    private String webContent = "", part1 = "", part2 = "", part3 = "", completeData = "";
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    private GraphView graph;
    private ArrayList<Integer> breezometer_aqi_list = new ArrayList<>();
    private ArrayList<String> timeList = new ArrayList<>();
    int chooseColor=0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.setGraphData(null);
        if (!checkConfiguration()) {
            Logs.info("D3 screen","Error in D3 graph Fragment");
        }
        App application = (App)getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();
    }


    @SuppressLint("JavascriptInterface")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_d3_screen, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mcontext = v.getContext();
        ButterKnife.bind(this, v);

        tabLayout = v.findViewById(R.id.tabs);
        fragment_container_d3 = v.findViewById(R.id.fragment_container_d3);

        Bundle bundlee=App.getTemp_bundle();

      zone_name.setText(""+ bundlee.getString("zone_title"));


        setTime();
       Bundle bundle =new Bundle();
       bundle.putString("sensor_id","181");
       Fragment fragment= D3data.newInstance();
        fragment.setArguments(bundle);
        loadFragment(fragment);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment=null;
                Bundle bundle=new Bundle();
                App.setGraphData(null);
                switch (tab.getPosition()) {
                    case 0:

                        bundle.putString("sensor_id","181");
                        fragment= D3data.newInstance();
                        fragment.setArguments(bundle);
                        fragment_container_d3.setVisibility(View.VISIBLE);
                        Log.e("Tab1","181");
                      //  fragment_container_forecast.setVisibility(View.INVISIBLE);
                        break;

                    case 1:

                        bundle.putString("sensor_id","33");
                        fragment= D3data.newInstance();
                        fragment.setArguments(bundle);
                        fragment_container_d3.setVisibility(View.VISIBLE);
                        //  fragment_container_forecast.setVisibility(View.INVISIBLE);
                        Log.e("Tab1","33");

                        break;
                    case 2:

                        bundle.putString("sensor_id","34");
                        fragment= D3data.newInstance();
                        fragment.setArguments(bundle);
                        fragment_container_d3.setVisibility(View.VISIBLE);
                        Log.e("Tab1","34");

                        //  fragment_container_forecast.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        bundle.putString("sensor_id","64");
                        fragment= D3data.newInstance();
                        fragment.setArguments(bundle);
                        fragment_container_d3.setVisibility(View.VISIBLE);
                        Log.e("Tab1","64");

                        //  fragment_container_forecast.setVisibility(View.INVISIBLE);
                        break;
             /***********       case 4:
                        bundle.putString("sensor_id","122");
                        fragment= D3data.newInstance();
                        fragment.setArguments(bundle);
                        fragment_container_d3.setVisibility(View.VISIBLE);
                        fragment_container_d3.setVisibility(View.VISIBLE);
                        Log.e("Tab1","122");

                        //  fragment_container_forecast.setVisibility(View.INVISIBLE);
                        break; *************/
                    case 4:
                        bundle.putString("sensor_id","246");
                        fragment= D3data.newInstance();
                        fragment.setArguments(bundle);
                        fragment_container_d3.setVisibility(View.VISIBLE);
                        fragment_container_d3.setVisibility(View.VISIBLE);
                        Log.e("Tab1","246");

                        //  fragment_container_forecast.setVisibility(View.INVISIBLE);
                        break;
                    case 5:
                        bundle.putString("sensor_id","248");
                        fragment= D3data.newInstance();
                        fragment.setArguments(bundle);
                        fragment_container_d3.setVisibility(View.VISIBLE);
                        fragment_container_d3.setVisibility(View.VISIBLE);
                        Log.e("Tab1","248");

                        //  fragment_container_forecast.setVisibility(View.INVISIBLE);
                        break;
                }

                loadFragment(fragment);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        lin_activity_main.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                UserInteractionSleep.userInteract(mcontext);

                return false;
            }
        });



        return v;
    }




    @OnClick(R.id.img_close)
    public void close_nav() {
            this.dismiss();
    }

 @OnClick(R.id.current_d3)
    public void current_d3() {
            this.dismiss();
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
                time= TimeFormatChange.convert12(hr+":"+min);
            }
            String day_time=expdate.get(Calendar.DAY_OF_MONTH)+" , "+time;

            txt_time.setText(""+month_name.toUpperCase()+" "+day_time);
        }
        catch (Exception ex)
        {
            Log.e("HubTimeError",""+ex.getMessage());
        }
    }


    public void loadFragment(Fragment fragment)
    {
        FragmentTransaction transaction=getChildFragmentManager().beginTransaction().replace(R.id.fragment_container_d3, fragment).addToBackStack(null);
        transaction.commit();
    }




    @Override
    public void onResume() {
        super.onResume();
        Logs.info("OnResume", "------i am in onResume");
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        UserInteractionSleep.siboSleepChecking(mcontext);
    }
    @Override
    public void onStop() {
        super.onStop();
        Logs.info("OnStop", "------i am in onstop");
        UserInteractionSleep.stopHandler();
    }
    private void sendScreenImageName() {
        String name = "D3 screen Fragment";
      //  String username=new PrefManager(mcontext).getValue("User_Id");
        // [START screen_view_hit]
        Log.i("d3 graph", "Setting screen name: " + name);
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
            Log.w("D3 screen", "checkConfiguration", e);
            return false;
        }

        return true;
    }

}
