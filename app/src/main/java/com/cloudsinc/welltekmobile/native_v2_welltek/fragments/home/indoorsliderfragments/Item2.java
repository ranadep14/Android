package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.indoorsliderfragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class Item2 extends Fragment  implements View.OnClickListener{

    private View v;
    private ImageView b1;
    private DonutProgress pm2prog;
    private TextView pm2_5text;
    private int PM2value =0;
    private int PM2percenage =0;

    public static Item2 newInstance() {
        return new Item2();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =inflater.inflate(R.layout.fragment_element2_new, container, false);
        pm2_5text= v.findViewById(R.id.pm2_5text);
        pm2prog= v.findViewById(R.id.pm2prog);
        PM2percenage = IndoorAirQualityMain.new_score_pm2;
       // PM2percenage = 100-PM2value;
        pm2prog.setProgress(PM2percenage);
       // pm2prog.setFinishedStrokeColor(IndoorAirQualityMain.new_color_pm2);

        b1= v.findViewById(R.id.close_nav);
        b1.setOnClickListener(this);
        progpm25();
        return v;
    }
    @Override
    public void onClick(View v) {
        if(v==b1)
        {
            getActivity().finish();
        }

    }

    private void progpm25() {


        if (PM2percenage >0 && PM2percenage <= 39) {
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

}
