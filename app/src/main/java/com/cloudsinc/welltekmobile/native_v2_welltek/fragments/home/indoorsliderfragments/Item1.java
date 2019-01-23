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
public class Item1 extends Fragment implements View.OnClickListener {


    private View v;
    private ImageView b1;
    private TextView co2_text;
    private DonutProgress COprog;
    private int COpercenage=0;
    private int COvalue=0;
    private String c="CO\u2082";


    public static Item1 newInstance() {
        return new Item1();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_element1_new, container,
                false);
        co2_text= v.findViewById(R.id.co2_text);
        b1= v.findViewById(R.id.close_nav);
        COprog= v.findViewById(R.id.COprog);
        COpercenage= IndoorAirQualityMain.new_score_co2;
        //COpercenage= 100-COvalue;
        COprog.setProgress(COpercenage);
        COprog.setText(c);
        b1.setOnClickListener(this);
        progco2();
        return v;
    }



    @Override
    public void onClick(View v) {
        if(v==b1)
        {
            getActivity().finish();
        }

    }

    private void progco2() {

        if (COpercenage >0 && COpercenage <= 39) {
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
}
