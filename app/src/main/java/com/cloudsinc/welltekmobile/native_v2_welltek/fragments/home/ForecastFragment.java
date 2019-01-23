package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.smooth_line_chart.SmoothLineChart;

import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.OutdoorAirQualityMain.t11;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.OutdoorAirQualityMain.t22;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.OutdoorAirQualityMain_new.t1;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.OutdoorAirQualityMain_new.t2;


public class ForecastFragment extends Fragment {
    int chooseColor=0;
    TextView start;
    TextView end;

    public ForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_forecast, container, false);


        //Code for smooth-graph is below

        SmoothLineChart chart = rootView.findViewById(R.id.smoothChart);
        start =rootView.findViewById(R.id.starttime);
        end =rootView.findViewById(R.id.endtime);


        // add below code to change the color of graph curve

        chooseColor=10;
        if (chooseColor<10){
            chart.setCurveColor(Color.GREEN);
        }else if (chooseColor<20 & chooseColor>=+10){
            chart.setCurveColor(Color.YELLOW);
        }else if (chooseColor>=20){
            chart.setCurveColor(Color.RED);
        }

        if(App.isOrientationFlag()==true) {
            if(t1==""&&t2==""){
                start.setText("3:40");
                end.setText("15:40" );
            }
            else {
                start.setText("" + t1);
                end.setText("" + t2);
            }
        }
        else {
            start.setText("" + t11);
            end.setText("" + t22
            );
        }
        return rootView;
    }

}