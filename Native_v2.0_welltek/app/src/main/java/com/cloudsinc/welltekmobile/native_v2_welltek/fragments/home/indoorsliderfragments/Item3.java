package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.indoorsliderfragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.indoorairquality.IndoorAirQualityMain;
import com.github.lzyzsd.circleprogress.DonutProgress;


/**
 * A simple {@link Fragment} subclass.
 */
public class Item3 extends Fragment implements View.OnClickListener{

    private View v;
    private ImageView b1;
    private DonutProgress pm10prog;
    private TextView pm10_text;
    private int PM10percenage =0;
    private int PM10value =0;

    public static Item3 newInstance() {
        return new Item3();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_item3_new, container, false);
        pm10_text= v.findViewById(R.id.pm10_text);
        pm10prog= v.findViewById(R.id.pm10prog);
        PM10percenage = IndoorAirQualityMain.new_score_pm10;
       // PM10percenage = 100-PM10value;
        pm10prog.setProgress(PM10percenage);
        Log.i("Color",""+IndoorAirQualityMain.new_color_pm10);
       // pm10prog.setFinishedStrokeColor(IndoorAirQualityMain.new_color_pm10);

        b1= v.findViewById(R.id.close_nav);
        b1.setOnClickListener(this);
        progpm10();
        return v;

    }
    @Override
    public void onClick(View v) {
        if(v==b1)
        {
            getActivity().finish();
        }

    }

    private void progpm10() {


        if (PM10percenage > 0 && PM10percenage <= 39) {
            //   score_no_data.setText("SCORE");
            pm10_text.setText("Poor");
            pm10prog.setFinishedStrokeColor(Color.rgb(246, 103, 101));


        } else if (PM10percenage >= 40 && PM10percenage <= 59) {
            pm10_text.setText("Moderate");
            pm10prog.setFinishedStrokeColor(Color.rgb(229,197,0));


        } else if (PM10percenage >= 60 && PM10percenage <= 69) {
            pm10_text.setText("Moderate");
            pm10prog.setFinishedStrokeColor(Color.rgb(246, 103, 101));

        } else if (PM10percenage >= 70 && PM10percenage <= 79) {
            pm10_text.setText("Moderate");
            pm10prog.setFinishedStrokeColor(Color.rgb(229, 197, 0));

        } else if (PM10percenage >= 80 && PM10percenage <= 89) {

            pm10_text.setText("Good");
            pm10prog.setFinishedStrokeColor(Color.rgb(68,255,233));


        } else if (PM10percenage >= 90 && PM10percenage <= 100) {
            pm10_text.setText("Excellent");
            pm10prog.setFinishedStrokeColor(Color.rgb(68, 255, 233));

        }
    }

}
