package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.progressbarfragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.OutdoorAirQualityMain_new;
import com.github.lzyzsd.circleprogress.DonutProgress;

import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.OutdoorAirQualityMain_new.no_Result;


/**
 * A simple {@link Fragment} subclass.
 */
public class NO2frag extends Fragment {
    Button back;
    private View v;
    private TextView tv;
    private DonutProgress no2;
    String n="NO\u2082";
    private TextView txt_desc;

    public static NO2frag newInstance() {
        return new NO2frag();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_no2_new, container,
                false);
        no2= v.findViewById(R.id.NOprog);
        tv= v.findViewById(R.id.textView_status);
        txt_desc= v.findViewById(R.id.txt_desc);
        no2.setText(n);
        int score=(int)no_Result;
        no2.setProgress(score);
        back= v.findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OutdoorAirQualityMain_new.frame_layout_co2.setVisibility(View.GONE);

            }
        }); setColorStatus(tv,no2,score);
        txt_desc.setMovementMethod(new ScrollingMovementMethod());
        return v;
    }


    public void setColorStatus(final TextView status, final DonutProgress v, int score)
    {
        int result=score;

        if (score >= 1 && score <= 39) {
            status.setText("Poor");
            v.setFinishedStrokeColor(Color.rgb(246,103,101));


        }else if (score >= 40 && score <= 59) {
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

        }
        else if (score >= 90 && score <= 100) {
            status.setText("Excellent");
            v.setFinishedStrokeColor(Color.rgb(68,255,233));

        }



    }

}
