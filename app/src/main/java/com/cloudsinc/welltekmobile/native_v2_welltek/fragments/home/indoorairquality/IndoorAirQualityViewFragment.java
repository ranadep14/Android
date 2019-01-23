package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.indoorairquality;


import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;


public class IndoorAirQualityViewFragment extends Fragment {
    public static IndoorAirQualityViewFragment newInstance() {
        return new IndoorAirQualityViewFragment();
    }
    double iaqscore = 0;
    private int inddor_color = 0x6EEAEA;
    @BindView(R.id.indoor_aqi_graph)
    DonutProgress indoor_aqi_graph;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    private Boolean CML_AQI_FLAG=false;
    private Tracker mTracker;


    @BindView(R.id.txt_air_quality)   TextView txt_air_quality;
    @BindView(R.id.txt_no_indoor)   TextView txt_no_indoor;
    @Nullable
    @BindView(R.id.txt_indoor_quality)
    TextView txt_quality;

   View v;
   Context mcontext;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_indoor_air_quality_view, container, false);
        mcontext = v.getContext();
        ButterKnife.bind(this, v);
        if (!checkConfiguration()) {
            Logs.info("Indoor Mobile","Error in Mobile Indoor Fragment");
        }
        App application = (App)getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();
        if(App.getSocket()!=null) {
            try {
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_sensors_provisioned", new JSONObject()));
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_zones", new JSONObject()));
            } catch(Exception e){
                Log.e("Indoor AQ error",e.getMessage());
                }
                }

                setSubcriber();
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

                Log.e("WhereIm",""+this.getClass().getName()+"----"+string);
                long original_score = Math.round(getIAQScore());
                long score=(100-original_score);
                if (score == 0) {

                    txt_no_indoor.setVisibility(View.VISIBLE);
                    txt_no_indoor.setText("No Data Available");
                    indoor_aqi_graph.setVisibility(View.GONE);
                    indoor_aqi_graph.setVisibility(View.GONE);
                }
                if(original_score>0) {
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

                    }}else {
                    txt_quality.setText("");
                    indoor_aqi_graph.setText("");


                    indoor_aqi_graph.setVisibility(View.GONE);
                    txt_no_indoor.setVisibility(View.VISIBLE);
                    txt_no_indoor.setText("No Data Available");

                }

                Log.e("Response_zones", "" + string);


            }

        };
        Subcriber s = new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setDialog_subcriber(s);


    }

    private double getIAQScore() {

        double tvoc = 0;
        try {

                if (App.getZoneJson() != null) {
                    JSONArray jsonArray = App.getZoneJson().getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.e("SelectedJson", "" + jsonObject);

                        if (jsonObject.getString("CML_TITLE").equals("Whole Home")) {
                            int score = jsonObject.getInt("CML_AQI_SCORE");
                            Log.e("SelectedRoomJson", "" + jsonObject);

                            if (jsonObject.getInt("CML_AQI_FLAG") == 0 )//&& sensor_present==true
                            {
                                if(score == 0){
                                    txt_no_indoor.setVisibility(View.VISIBLE);
                                    txt_no_indoor.setText("Loading Data");
                                    indoor_aqi_graph.setVisibility(View.GONE);
                                    CML_AQI_FLAG = true;
                                }
                                else {
                                    txt_no_indoor.setVisibility(View.VISIBLE);
                                    txt_no_indoor.setText("Loading Data");
                                    indoor_aqi_graph.setVisibility(View.GONE);
                                    CML_AQI_FLAG = true;
                                }
                            } else if (jsonObject.getInt("CML_AQI_FLAG") == 1 && score > 0) {
                                iaqscore = jsonObject.getDouble("CML_AQI_SCORE");
                                Log.e("IAQSCOre", "" + iaqscore);
                                CML_AQI_FLAG = false;
                            } else if (jsonObject.getInt("CML_AQI_FLAG") == 2) {
                                txt_no_indoor.setVisibility(View.VISIBLE);
                                txt_no_indoor.setText("No Data Available");
                                indoor_aqi_graph.setVisibility(View.GONE);
                                CML_AQI_FLAG = false;
                            }

                        }

                }

                if (iaqscore == 0) {
                    indoor_aqi_graph.setText("0");
                    indoor_aqi_graph.setProgress(0);
                }
            }
        } catch (Exception ex) {
            Log.e("IAQ SCORe", ex.getMessage());
        }

        return iaqscore;
    }
    private void sendScreenImageName() {
        String name = "Indoor Mobile Fragment";
       // String username=new PrefManager(mcontext).getValue("User_Id");
        // [START screen_view_hit]
        Log.i("Indoor Mobile", "Setting screen name: " + name);
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
            Log.w("Indoor mobile", "checkConfiguration", e);
            return false;
        }

        return true;
    }
}
