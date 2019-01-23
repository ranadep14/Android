package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.filters;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.CustomDialogShow;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ScreenOrientation;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity.bottom_menu_bar;

/**
 * The FiltersFragment is for displaying filter expiry date percentage
 *
 * @author  Jaid Shaikh
 * @version 1.0
 * @since   2017-11-8
 */

public class FiltersFragment extends Fragment {
    public static FiltersFragment newInstance() {
        return new FiltersFragment();
    }

    private View v;
    private int cout = 182;
    private int waterdayscount;
    private int airdayscount;
    private JSONObject airJsonObject;
    private String TAG=FiltersFragment.this.getClass().getSimpleName();
    private Tracker mTracker;
    private App application;

   // TextView txtwaterDayCount;
  //
   @BindView(R.id.txt_water_days)TextView txtwaterDayCount;
    @BindView(R.id.txt_air_days)TextView txtairDayCount;
    @BindView(R.id.txt_air_title)TextView txt_air_title;
    @BindView(R.id.txt_water_title)TextView txt_water_title;



    @BindView(R.id.txt_water_txt)TextView no_water_filter;
    @BindView(R.id.txt_air_txt)TextView no_air_filter;
    private JSONObject waterJsonObject;

    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private Observer<String> myObserver;
    private Observable<String> mobservable;
    @BindView(R.id.water_progress_bar)RelativeLayout water_progress_bar;
    @BindView(R.id.air_progress_bar)RelativeLayout air_progress_bar;

    @BindView(R.id.prog_water_filter)DonutProgress waterdonutProgress;
    @BindView(R.id.prog_air_filter)DonutProgress airdonutProgress;
    @BindView(R.id.lyt_linn_water_filter)LinearLayout lyt_linn_water_filter;
    @BindView(R.id.lyt_linn_air_filter)LinearLayout lyt_linn_air_filter;
    @BindView(R.id.txt_no_air)TextView txt_no_air;
    @BindView(R.id.txt_no_water)TextView txt_no_water;
    @BindView(R.id.no_filters)TextView no_filters;
    @Nullable
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @Nullable @BindView(R.id.img_back)ImageView img_back;



    Context mcontext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_filters, container, false);
        mcontext=v.getContext();
        ScreenOrientation.setOrientation(getActivity());
        bottom_menu_bar.setVisibility(View.GONE);
        ButterKnife.bind(this,v);

        if (!checkConfiguration()) {
            Logs.info(TAG,"Error in FilterFragment");
        }
        application = (App)getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();
        if(App.getSocket()!=null) {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_provisioned", new JSONObject()));
        }
        setSubcriber();
        if(!App.isOrientationFlag()) {
            img_back.setImageDrawable(FiltersFragment.this.getResources().getDrawable(R.drawable.ic_left_arrow));
            txt_fragment_title.setText("Filters");
        }
        return v;
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
                getWaterAndAirFilterObject();

            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);


    }

    public void getWaterDayCount() {

        try {
            JSONObject jsonObject = waterJsonObject;

            Logs.error(TAG, "WaterExpery date" + jsonObject.getString("expiryDate"));
            String dt = jsonObject.getString("expiryDate");//airjsonObject
            Calendar expdate = Calendar.getInstance();
            expdate.setTime(sdf.parse(dt));

            String idt = jsonObject.getString("installationDate");//airjsonObject
            Calendar instadate = Calendar.getInstance();
            instadate.setTime(sdf.parse(idt));
            instadate.set(Calendar.HOUR, 0);
            instadate.set(Calendar.MINUTE, 0);
            instadate.set(Calendar.SECOND, 0);


            Calendar currentDate = Calendar.getInstance();

            currentDate.set(Calendar.HOUR, 0);
            currentDate.set(Calendar.MINUTE, 0);
            currentDate.set(Calendar.SECOND, 0);
            waterdayscount =  (printDifference(instadate.getTime(), currentDate.getTime()));

            Logs.info(TAG+"water_cout",""+waterdayscount);
            if (waterdayscount <= 0) {
                txtwaterDayCount.setText("182 days");
                waterdonutProgress.setProgress(100);
                waterdonutProgress.setText("100%");

                waterdonutProgress.setFinishedStrokeColor(Color.rgb(68,255,233));

            }
            if (waterdayscount >= 182) {
                txtwaterDayCount.setText(""+(waterdayscount-182)+" days");
                waterdonutProgress.setText("0%");
                no_water_filter.setText("EXPIRED SINCE ");
                waterdonutProgress.setProgress(100);
                waterdonutProgress.setFinishedStrokeColor(Color.rgb(246,103,101));

            }


 else {
                        int watercnt = (182 - waterdayscount);

                        txtwaterDayCount.setText("" + watercnt + " days");
                         float waternew=(watercnt * 100)/ 182f;
                int waterper = Math.round(waternew);

                        if (waterdayscount > 0) {

                            no_water_filter.setText("TIME REMAINING ");
                            waterdonutProgress.setText("" + Math.round(waterper) + "%");

                            waterdonutProgress.setProgress(Math.round(waterper));





                    if (watercnt >= 1 && watercnt <= 46) {
                        //   waterdayscount_no_data.setText("SCORE");
                        waterdonutProgress.setFinishedStrokeColor((mcontext.getResources().getColor(R.color.filterred)));

                    } else if (watercnt >= 47 && watercnt <= 91) {
                        waterdonutProgress.setFinishedStrokeColor((mcontext.getResources().getColor(R.color.filteryellow)));


                    } else if (watercnt >= 92 && watercnt <= 182) {
                        waterdonutProgress.setFinishedStrokeColor((mcontext.getResources().getColor(R.color.dialer_selected_color)));


                    }

                    if (waterdayscount == 0) {
                    }
                }
            }

            App.setWaterRemainingDays(printDifference(currentDate.getTime(), expdate.getTime()));
        } catch (Exception ex) {
            Logs.error(TAG,"WaterDayCount_error: "+ex.getMessage());
        }


    }
    public void getAirDayCount() {



        try {
            JSONObject jsonObject=airJsonObject;

            Logs.info(TAG,"AirExpery date"+jsonObject.getString("expiryDate"));


            String install = jsonObject.getString("installationDate");
            Calendar installdt = Calendar.getInstance();
            installdt.setTime(sdf.parse(install));
            installdt.set(Calendar.HOUR, 0);
            installdt.set(Calendar.MINUTE, 0);
            installdt.set(Calendar.SECOND, 0);

            Calendar currentDate = Calendar.getInstance();
            currentDate.set(Calendar.HOUR, 0);
            currentDate.set(Calendar.MINUTE, 0);
            currentDate.set(Calendar.SECOND, 0);
            airdayscount=(printDifference(installdt.getTime(),currentDate.getTime()));


            if(airdayscount<=0)
            {
                txtairDayCount.setText("365 days");
                airdonutProgress.setText("100%");
                airdonutProgress.setProgress(100);
                airdonutProgress.setFinishedStrokeColor(Color.rgb(68,255,233));

            }

            if(airdayscount>=365){

                txtairDayCount.setText(""+(airdayscount-365)+" days");
                airdonutProgress.setText("0%");
                no_air_filter.setText("EXPIRED SINCE ");
                airdonutProgress.setProgress(100);
                airdonutProgress.setFinishedStrokeColor(Color.rgb(246,103,101));

            }
            else {
                int aircnt = (365 - airdayscount);

                txtairDayCount.setText("" + aircnt + " days");
                float airnew=(aircnt * 100) / 365f ;
                int airper = Math.round(airnew);

                if (airdayscount > 0) {

                    no_air_filter.setText("TIME REMAINING ");
                    airdonutProgress.setText("" + Math.round(airper) + "%");

                    airdonutProgress.setProgress(Math.round(airper));
                    if (aircnt >= 1 && aircnt <= 93) {
                        airdonutProgress.setFinishedStrokeColor((mcontext.getResources().getColor(R.color.filterred)));

                    } else if (aircnt >= 94 && aircnt <= 184) {
                        airdonutProgress.setFinishedStrokeColor((mcontext.getResources().getColor(R.color.filteryellow)));


                    } else if (aircnt >= 185 && aircnt <= 365) {
                        airdonutProgress.setFinishedStrokeColor((mcontext.getResources().getColor(R.color.dialer_selected_color)));


                    }


                }
            }


        }
        catch (Exception ex){
            Logs.error(TAG,"Error_AirDayCount: "+ex.getMessage());
        }


    }



    private int printDifference(Date startDate, Date endDate){


        long different = endDate.getTime() - startDate.getTime();
        int elapsedDays = (int) (different / (24 * 60 * 60 * 1000));


        return elapsedDays;

    }



    private void getWaterAndAirFilterObject() {
        boolean water_flag=false,air_flag=false;
        try {
            JSONObject jsonObject = App.getProvisionalDevicesData();

            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray.length() == 0) {
                no_filters.setVisibility(View.VISIBLE);
                lyt_linn_air_filter.setVisibility(View.GONE);
                lyt_linn_water_filter.setVisibility(View.GONE);
                waterdonutProgress.setProgress(0);
                water_progress_bar.setVisibility(View.GONE);
                air_progress_bar.setVisibility(View.GONE);

                airdonutProgress.setProgress(0);

            } else {


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    if (jsonObject1.getString("type").equals("Water")) {

                        if (!jsonObject1.getString("room").equals("")) {
                            water_flag = true;

                            waterJsonObject = jsonObject1;
                            txt_water_title.setText("" + jsonObject1.getString("CML_TITLE"));
                        } else {
                            water_flag = false;

                        }
                    }

                    if (jsonObject1.getString("type").equals("Air")) {

                        if (!jsonObject1.getString("room").equals("")) {
                            air_flag = true;

                            airJsonObject = jsonObject1;
                            txt_air_title.setText("" + jsonObject1.getString("CML_TITLE"));
                        } else {
                            air_flag = false;

                        }
                    }



                }

                if (water_flag) {

                    txt_no_water.setVisibility(View.GONE);
                    water_progress_bar.setVisibility(View.GONE);
                    lyt_linn_water_filter.setVisibility(View.VISIBLE);
                    getWaterDayCount();
                } else {
                    lyt_linn_water_filter.setVisibility(View.GONE);
                    waterdonutProgress.setProgress(0);
                    lyt_linn_water_filter.setVisibility(View.GONE);
                    txt_no_water.setVisibility(View.VISIBLE);
                    water_progress_bar.setVisibility(View.GONE);
                }



                if (air_flag) {
                    no_filters.setVisibility(View.GONE);

                    txt_no_air.setVisibility(View.GONE);
                    air_progress_bar.setVisibility(View.GONE);
                    lyt_linn_air_filter.setVisibility(View.VISIBLE);
                    getAirDayCount();
                } else {
                    no_filters.setVisibility(View.GONE);

                    lyt_linn_air_filter.setVisibility(View.GONE);
                    airdonutProgress.setProgress(0);
                    txt_no_air.setVisibility(View.VISIBLE);
                    air_progress_bar.setVisibility(View.GONE);
                }


                if (water_flag) {
                    no_filters.setVisibility(View.GONE);

                    txt_no_water.setVisibility(View.GONE);
                    water_progress_bar.setVisibility(View.GONE);
                    lyt_linn_water_filter.setVisibility(View.VISIBLE);
                    getWaterDayCount();
                } else {
                    no_filters.setVisibility(View.GONE);
                    lyt_linn_water_filter.setVisibility(View.GONE);
                    waterdonutProgress.setProgress(0);
                    txt_no_water.setVisibility(View.VISIBLE);
                    water_progress_bar.setVisibility(View.GONE);
                }

                if (air_flag == false && water_flag == false) {
                    water_progress_bar.setVisibility(View.GONE);
                    air_progress_bar.setVisibility(View.GONE);
                    lyt_linn_water_filter.setVisibility(View.GONE);
                    lyt_linn_air_filter.setVisibility(View.GONE);
                    txt_no_air.setVisibility(View.GONE);
                    txt_no_water.setVisibility(View.GONE);
                    no_filters.setVisibility(View.VISIBLE);
                }
            }

        }


        catch(Exception ex) {
            Logs.error(TAG,"Filters_Rendering_error"+ex.getMessage());

        }
    }

    @OnClick(R.id.btn_water_purchase)
    public void btn_oder_filter() {
        App.trackEvent("Button Click", "btn_water_purchase Click", "Water Filter Purchase Btn");
        CustomDialogShow.dispDialogWithOK(getActivity(), "Sorry, the online store is not currently available!");
//Handling App Crash in Google Analytics
    /**    Runnable r = new Runnable() {
            @Override
            public void run() {
                int answer = 12 / 0;
            }
        };

        Handler h = new Handler();
        h.postDelayed(r, 1500);

**/
    }

    @OnClick(R.id.btn_air_purchase)
    public void btn_oder_air_filter() {
        CustomDialogShow.dispDialogWithOK(getActivity(), "Sorry, the online store is not currently available!");

        // Handling Button Click in Google Analytics


        App.trackEvent("Button Click", "btn_air_purchase Click", "Air Filter Purchase Btn");


    }
    @Optional
    @OnClick(R.id.img_back)
    public void img_back()
    {
        MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
// Handling Exception in Google Analytics
        try {
            String name = null;
            if (name.equals("JD")) {
                /* Never comes here as it throws null pointer exception */
            }
        } catch (Exception e) {
            // Tracking exception
          // App application = (App)getActivity().getApplication();
           // mTracker = application.getDefaultTracker();



            Log.e(TAG, "Exception: " + e.getMessage());
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        if(!App.isOrientationFlag()) ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

    }

    @Override
    public void onStop() {
        super.onStop();
        if(!App.isOrientationFlag())  ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    private void sendScreenImageName() {
        String name = "Filters Fragment";
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