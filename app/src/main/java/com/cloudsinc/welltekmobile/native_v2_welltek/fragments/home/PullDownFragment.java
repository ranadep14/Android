package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.github.lzyzsd.circleprogress.DonutProgress;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity.bottom_menu_bar;


/**
 * This class used to display Outdoor Weather data on Mobile.
 *
 * @author  Jaid Shaikh
 * @version 1.0
 * @since   2018-1-9
 */


public class PullDownFragment extends Fragment {
    View v;
    Context mcontext;
    DonutProgress outdoor_aqi_graph;
    @BindView(R.id.outdoor_descp)
    TextView outdoor_descp;
    @Nullable
    @BindView(R.id.txt_humidity_weather)
    TextView txt_humidity;
    @Nullable
    @BindView(R.id.txt_cur_weather)
    TextView txt_cur_weather;
    @BindView(R.id.activity_main_home)RelativeLayout activity_main_home;
    @BindView(R.id.seek_sun_move)
    SeekBar seek_sun_move;
    @Nullable
    @BindView(R.id.txt_temp_weather)
    TextView txt_temp_weather;
    @BindView(R.id.txt_v1)
    TextView txt_v1;
    @BindView(R.id.no_txt)
    TextView no_txt;
    @BindView(R.id.top_lyt) LinearLayout top_lyt;
    @BindView(R.id.lyt_data) LinearLayout lyt_data;
    @Nullable
    @BindView(R.id.simpleChronometer)
    Chronometer simpleChronometer;

    @BindView(R.id.prog) ProgressBar prog;
    @BindView(R.id.v1)View v1;
    @BindView(R.id.vv2) View v2;
    @BindView(R.id.vv3) View v3;
    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
    @BindView(R.id.img_sun_circle)ImageView img_sun_circle;
    @BindView(R.id.img_btn_pull_up_panel)ImageView img_btn_pull_up_panel;

    @BindView(R.id.img_sun_move)
    ImageView img_sun_move;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    private int breezometer_aqi=0;
    double[] y_cordinate_potrait=  {97.86,97.23,95.93,94.94,93.43,91.66,90.41,89.21,88.68,87.70,87.25,87.38,87.28,87.83,88.15,89.09,89.86,91.61,93.22,94.59,96.30,96.92,97.81,97.10};
    float x_condiante=0;
    int cout=0;
    public static PullDownFragment newInstance() {
        return new PullDownFragment();
    }
    HashMap<String,Integer> mob_weather_map=new HashMap<>();
    boolean day_flag=false;
    static String sun_rise_time="",sun_set_time="";
    String TAG=this.getClass().getSimpleName();
    String strDefaultTimeZone="";
    int image_count=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.pull_down_fragment, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);
        startSunAnimation();

        mob_weather_map.put("1",R.mipmap.img_mob_clear_cloudy_down);
        mob_weather_map.put("16",R.mipmap.img_mob_fog_low_down);
        mob_weather_map.put("30",R.mipmap.img_mob_heavy_rain_down);
        mob_weather_map.put("19",R.mipmap.img_mob_mostly_cloudy_down);
        mob_weather_map.put("22",R.mipmap.img_mob_overcast_down);
        mob_weather_map.put("23",R.mipmap.img_mob_overcast_rain_down);
        mob_weather_map.put("7",R.mipmap.img_mob_partly_cloudy_down);
        mob_weather_map.put("24",R.mipmap.img_mob_overcast_snow_down);

        outdoor_aqi_graph= v.findViewById(R.id.outdoor_aqi_graph);
        outdoor_aqi_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottom_menu_bar.setVisibility(View.GONE);
                Fragment fragment=new OutdoorAirQualityMain();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("OutdoorAirQualityMain");
                fragmentTransaction.replace(R.id.frm_main_container,fragment).commit();
            }
        });
        img_btn_pull_up_panel.setOnTouchListener(new ImageView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_UP:
                        // Disallow ScrollView to intercept touch events.
                        bottom_menu_bar.setVisibility(View.GONE);
                        HomeHeathByZoneFragment pullupFragment = new HomeHeathByZoneFragment();
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom, R.anim.enter_from_top, R.anim.exit_to_top);
                        fragmentTransaction.replace(R.id.frm_main_container, pullupFragment);
                        fragmentTransaction.addToBackStack("pullDownFragment");
                        fragmentTransaction.commit();
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }


                v.onTouchEvent(event);

                return true;
            }
        });
        setSubcriber();
        setSunPosition();
        if(App.getSocket()!=null) {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_weather_condition", new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_info", new JSONObject()));
        }


        return v;
    }

    public void setSunPosition() {


        try {


            strDefaultTimeZone = App.getHubInfo().getJSONArray("data").getJSONObject(0).getString("CML_TIMEZONE");//defaultTimeZone.getDisplayName(false, TimeZone.SHORT);
            TimeZone tz = TimeZone.getTimeZone(strDefaultTimeZone);
            Calendar expdate = Calendar.getInstance(tz);
            int c_hr=expdate.get(Calendar.HOUR_OF_DAY);
            int min=expdate.get(Calendar.MINUTE);

            String str_hour=c_hr<10?"0"+c_hr:""+c_hr;
            String str_min=min<10?"0"+min:""+min;

            if(isTimeBetweenTwoTime(str_hour+":"+str_min)) {
                day_flag=true;
                activity_main_home.setBackground(getActivity().getResources().getDrawable(R.drawable.home_main_background_pull_down_mob));



            } else {

                day_flag=false;
                activity_main_home.setBackground(getActivity().getResources().getDrawable(R.drawable.mob_pull_down_night));
                txt_humidity.setTextColor(getActivity().getResources().getColor(R.color.white));
                txt_temp_weather.setTextColor(getActivity().getResources().getColor(R.color.white));
                txt_cur_weather.setTextColor(getActivity().getResources().getColor(R.color.white));
                no_txt.setTextColor(getActivity().getResources().getColor(R.color.white));
                img_btn_pull_up_panel.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_down_arr_white));
                txt_v1.setTextColor(getActivity().getResources().getColor(R.color.white));
                v1.setBackgroundColor(mcontext.getResources().getColor(R.color.white));
                v2.setBackgroundColor(mcontext.getResources().getColor(R.color.white));
                v3.setBackgroundColor(mcontext.getResources().getColor(R.color.white));
                outdoor_descp.setTextColor(getActivity().getResources().getColor(R.color.white));
                outdoor_aqi_graph.setTextColor(getActivity().getResources().getColor(R.color.white));


            }

            DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
            int width = activity_main_home.getWidth();
            int height = activity_main_home.getHeight();
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxx"+c_hr+"xxxxxxxxxxxxxxx"+width+"xxxxxxxxxxx"+y_cordinate_potrait[c_hr]+"******"+height+"******"+img_sun_circle.getWidth()+"********"+img_sun_circle.getHeight());
            System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhh"+c_hr+"hhhhhhh"+(((c_hr+1)*(width/24))- 0.5f * img_sun_circle.getWidth())+"hhhh"+(((App.isOrientationFlag()?y_cordinate_potrait[c_hr]:y_cordinate_potrait[c_hr])*height/100)- 0.5f * img_sun_circle.getHeight()));
            img_sun_circle.setBackground(day_flag?mcontext.getResources().getDrawable(R.drawable.sun_animation):mcontext.getResources().getDrawable(R.drawable.sun_orange_animation));
            img_sun_circle.setX(((c_hr+1)*(width/24))- 0.5f * img_sun_circle.getWidth());
            img_sun_circle.setY((float) (((App.isOrientationFlag()?y_cordinate_potrait[c_hr]:y_cordinate_potrait[c_hr])*height/100)- 0.5f * img_sun_circle.getHeight()));
            img_sun_circle.setVisibility(View.VISIBLE);
            setSunColor(str_hour,str_min);
            System.out.println("ppppppppppp"+width+"ppppp"+height+"pppppppppppppppppppppppppppp"+1*(width/2)+"ppppppppppppppp"+(((81.61*height)/100)- 0.5f * img_btn_pull_up_panel.getHeight()));
            img_btn_pull_up_panel.setX((float) ((width/2)- 0.5f * img_btn_pull_up_panel.getWidth()));
            img_btn_pull_up_panel.setY((float) (((81.61*height)/100)- 0.5f * img_btn_pull_up_panel.getHeight()));
            img_btn_pull_up_panel.setVisibility(View.VISIBLE);
        } catch (Exception ex)
        {
            Log.e("TimeError",""+ex.getMessage());
        }
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

                System.out.println("onNext string: " + string);
                if (string.equals("hub_soft_resetted")) {
                    prog.setVisibility(View.GONE);
                    lyt_data.setVisibility(View.GONE);
                    top_lyt.setVisibility(View.GONE);
                    outdoor_aqi_graph.setVisibility(View.GONE);
                    no_txt.setVisibility(View.VISIBLE);
                    no_txt.setText("No Data Available");
                }

                try {


                    if (string.equals("info")) {
                        simpleChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                            @Override
                            public void onChronometerTick(Chronometer chronometer) {

                                setSunPosition();
                                setWeatherImages();


                            }
                        });
                        simpleChronometer.setBase(SystemClock.elapsedRealtime());
                        simpleChronometer.start();
                    }

                    if (string.equals("weather_data")) {
                        setWeatherImages();
                    }


                    if (App.getweather_condition() != null) {

                        no_txt.setVisibility(View.GONE);
                        prog.setVisibility(View.GONE);

                        JSONObject jsonObject = App.getweather_condition();
                        JSONArray jsonArrayyy = jsonObject.getJSONArray("data");
                        Log.e("tweather ", "" + jsonArrayyy);

                        if(jsonArrayyy.length()>0) {
                            boolean weather_data_flag = false;
                            boolean bqi_data_flag = false;

                            for (int i = 0; i < jsonArrayyy.length(); i++) {
                                JSONObject jsonObject1 = jsonArrayyy.getJSONObject(i);
                                if (jsonObject1.getString("type").equals("weather")) {
                                    weather_data_flag = true;

                                    // JSONObject json = jsonArrayyy.getJSONObject(0);
                                    JSONObject status = jsonObject1.getJSONObject("temperature");
                                    int stat1 = (int) Math.round(status.getDouble("value"));

                                    lyt_data.setVisibility(View.VISIBLE);
                                    top_lyt.setVisibility(View.VISIBLE);
                                    outdoor_aqi_graph.setVisibility(View.VISIBLE);
                                    txt_temp_weather.setText("" + stat1 + "\u00B0");

                                    int humidity = jsonObject1.getInt("relative_humidity");
                                    txt_humidity.setText("" + humidity + "% \n Humidity");
                                    String curr_weather = jsonObject1.getString("weather_text");
                                    txt_cur_weather.setText(getResult(curr_weather));
                                }
                                if (jsonObject1.getString("type").equals("baqi")) {
                                    //  JSONObject jsonObjectt = jsonArrayyy.getJSONObject(1);
                                    bqi_data_flag = true;
                                    String outdoor_descp_txt = jsonObject1.getString("breezometer_description");
                                    outdoor_descp.setVisibility(View.VISIBLE);
                                    // outdoor_descp.setText(getResult(outdoor_descp_txt));
                                    breezometer_aqi = jsonObject1.getInt("breezometer_aqi");
                                    outdoor_aqi_graph.setVisibility(View.VISIBLE);
                                    outdoor_aqi_graph.setProgress(breezometer_aqi);
                                    outdoor_aqi_graph.setText("" + breezometer_aqi);
                                    outdoor_aqi_graph.setStartingDegree(270);

                                    if (breezometer_aqi >= 1 && breezometer_aqi <= 39) {
                                        //   score_no_data.setText("SCORE");
                                        outdoor_descp.setText("Poor");
                                        outdoor_aqi_graph.setFinishedStrokeColor(Color.rgb(246, 103, 101));
                                    } else if (breezometer_aqi >= 40 && breezometer_aqi <= 59) {
                                        outdoor_descp.setText("Moderate");
                                        outdoor_aqi_graph.setFinishedStrokeColor(Color.rgb(229,197,0));


                                    } else if (breezometer_aqi >= 60 && breezometer_aqi <= 69) {
                                        outdoor_descp.setText("Moderate");
                                        outdoor_aqi_graph.setFinishedStrokeColor(Color.rgb(229,197,0));

                                    } else if (breezometer_aqi >= 70 && breezometer_aqi <= 79) {
                                        outdoor_descp.setText("Moderate");
                                        outdoor_aqi_graph.setFinishedStrokeColor(Color.rgb(229,197,0));

                                    } else if (breezometer_aqi >= 80 && breezometer_aqi <= 89) {
                                        outdoor_descp.setText("Good");
                                        outdoor_aqi_graph.setFinishedStrokeColor(Color.rgb(68,255,233));


                                    } else if (breezometer_aqi >= 90 && breezometer_aqi <= 100) {
                                        outdoor_descp.setText("Excellent");
                                        outdoor_aqi_graph.setFinishedStrokeColor(Color.rgb(68, 255, 233));

                                    }
                                }

                            }


                            if (!weather_data_flag) {
                                txt_cur_weather.setText("No Data Available ");
                                txt_cur_weather.setVisibility(View.VISIBLE);
                            }
                            if (!bqi_data_flag) {
                                outdoor_aqi_graph.setVisibility(View.GONE);

                                outdoor_descp.setText("No Data Available");
                                outdoor_descp.setVisibility(View.VISIBLE);


                            }
                        }

                        else {
                            prog.setVisibility(View.GONE);
                            lyt_data.setVisibility(View.GONE);
                            top_lyt.setVisibility(View.GONE);
                            outdoor_aqi_graph.setVisibility(View.GONE);
                            no_txt.setVisibility(View.VISIBLE);
                            no_txt.setText("No Data Available");
                        }
                    } else {
                        outdoor_descp.setVisibility(View.GONE);
                        txt_cur_weather.setVisibility(View.GONE);
                        prog.setVisibility(View.GONE);
                        lyt_data.setVisibility(View.GONE);
                        top_lyt.setVisibility(View.GONE);
                        outdoor_aqi_graph.setVisibility(View.GONE);
                        no_txt.setVisibility(View.VISIBLE);
                        no_txt.setText("No Data Available");
                    }

                }
                catch (Exception e){
                    Log.e("eeeeeeee",""+e.getMessage());
                }




            }

        };
        Subcriber s = new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);


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


    @OnClick(R.id.btn_next)
    public void btn_next()
    {


        if(cout==23) {
            cout = 0;
            x_condiante = 0;
        }
        int width = activity_main_home.getWidth();
        int height = activity_main_home.getHeight();
        x_condiante+=width/24;
        if(cout<24) {

            //Toast.makeText(mcontext, "********"+x_condiante+"*********"+y_cordinate_potrait[cout], Toast.LENGTH_SHORT).show();
            img_sun_circle.setY((float) (((y_cordinate_potrait[cout]) * height / 100) - 0.5f * img_sun_circle.getHeight()));
            cout+=1;
        }
        img_sun_circle.setX(x_condiante- 0.5f * img_sun_circle.getWidth());
        setSunColor(""+cout,""+0);

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
                            int iconCode = jsonObject.getInt("icon_code");
                            Logs.info(TAG, "______________________________" + iconCode);
                            if (day_flag) {
                                switch ("" + iconCode) {
                                    case "1":
                                    case "2":
                                    case "3":
                                    case "4":
                                    case "5":
                                    case "6":
                                        activity_main_home.setBackground(getActivity().getResources().getDrawable(R.mipmap.img_mob_clear_cloudy_down));
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
                                        activity_main_home.setBackground(getActivity().getResources().getDrawable( R.mipmap.img_mob_partly_cloudy_down));
                                        break;
                                    case "16":
                                    case "17":
                                    case "18":
                                        activity_main_home.setBackground(getActivity().getResources().getDrawable( R.mipmap.img_mob_fog_low_down));
                                        break;
                                    case "19":
                                    case "20":
                                    case "21":
                                        activity_main_home.setBackground(getActivity().getResources().getDrawable(R.mipmap.img_mob_mostly_cloudy_down));
                                        break;
                                    case "22":
                                    case "35":
                                        activity_main_home.setBackground(getActivity().getResources().getDrawable(R.mipmap.img_mob_overcast_down));
                                        break;
                                    case "23":
                                    case "25":
                                    case "27":
                                        activity_main_home.setBackground(getActivity().getResources().getDrawable(R.mipmap.img_mob_overcast_rain_down));
                                        break;
                                    case "24":
                                    case "29":
                                    case "26":
                                    case "32":
                                    case "34":
                                        activity_main_home.setBackground(getActivity().getResources().getDrawable(R.mipmap.img_mob_overcast_snow_down));
                                        break;
                                    case "30":
                                    case "31":
                                    case "33":
                                    case "28":
                                        activity_main_home.setBackground(getActivity().getResources().getDrawable(R.mipmap.img_mob_heavy_rain_down));
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

    public  boolean isTimeBetweenTwoTime(String currentTime) throws ParseException {

        System.out.println("**********************"+currentTime);
        boolean valid = false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat month_date = new SimpleDateFormat("HH:mm");
        TimeZone tz = TimeZone.getTimeZone(strDefaultTimeZone);
        month_date.setTimeZone(tz);
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

    public void setSunColor(String hour,String min)
    {

        try {

            InputStream is = getActivity().getAssets().open("kelvin_to_color.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonarr = new String(buffer, "UTF-8");
            JSONArray kelvinColorJsonArray = new JSONArray(jsonarr);

            int bestDifference=10000;
            int bestIndex=0;
            int bestminute=0;
            for (int i = 0; i < kelvinColorJsonArray.length(); i++)
            {
                JSONObject jsonObject= kelvinColorJsonArray.getJSONObject(i);
                int minute = jsonObject.getInt("minute");

                int difference = Math.abs(((Integer.parseInt(hour)*60)+Integer.parseInt(min)) - minute);
                Logs.info(TAG,"--------------bestdifference"+difference+"-----"+minute);
                if (difference < bestDifference) {
                    bestDifference = difference;
                    bestIndex = i;
                    bestminute=minute;
                }
            }

            if(bestIndex!=0)bestIndex=bestIndex-1;
            Logs.info(TAG,"--------------index"+bestIndex+"-----bestvalue"+bestminute+"-----hour"+hour+"-----minute"+min);
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

    @OnClick(R.id.btn_change_backround)
    public void btn_change_backround()
    {

        Drawable img_arr[]={
                getActivity().getResources().getDrawable(App.isOrientationFlag() ? R.mipmap.img_tab_overcast : R.mipmap.img_mob_overcast_down),
                getActivity().getResources().getDrawable(App.isOrientationFlag() ? R.mipmap.img_tab_mostly_cloudy : R.mipmap.img_mob_mostly_cloudy_down),
                getActivity().getResources().getDrawable(App.isOrientationFlag() ? R.mipmap.img_tab_fog_low : R.mipmap.img_mob_fog_low_down),
                getActivity().getResources().getDrawable(App.isOrientationFlag() ? R.mipmap.img_tab_partly_cloudy : R.mipmap.img_mob_partly_cloudy_down),
                getActivity().getResources().getDrawable(App.isOrientationFlag() ? R.mipmap.img_tab_clear_cloudless : R.mipmap.img_mob_clear_cloudy_down),
                getActivity().getResources().getDrawable(App.isOrientationFlag() ? R.mipmap.img_tab_overcast_rain : R.mipmap.img_mob_overcast_rain_down),
                getActivity().getResources().getDrawable(App.isOrientationFlag() ? R.mipmap.img_tab_heavy_rain : R.mipmap.img_mob_heavy_rain_down),
                getActivity().getResources().getDrawable(App.isOrientationFlag() ? R.mipmap.img_tab_overcast_snow : R.mipmap.img_mob_overcast_snow_down)

        };


        if(image_count==img_arr.length-1) image_count=0;
        if(image_count<img_arr.length) {

            activity_main_home.setBackground(img_arr[image_count]);
            image_count++;

        }



    }
}