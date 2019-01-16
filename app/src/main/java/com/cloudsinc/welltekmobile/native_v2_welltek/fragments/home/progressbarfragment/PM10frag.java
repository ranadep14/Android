package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.progressbarfragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.OutdoorAirQualityMain_new;
import com.github.lzyzsd.circleprogress.DonutProgress;

import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.OutdoorAirQualityMain_new.pm10_Result;


/**
 * A simple {@link Fragment} subclass.
 */
public class PM10frag extends Fragment {

    Button back;
    TextView t1;
    TextView t2;
    private View v;
    private DonutProgress pm10;
    private LinearLayout main_lyt_pm10;

    public static PM10frag newInstance() {
        return new PM10frag();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_pm10_new, container,
                false);
        t1= v.findViewById(R.id.textView_status);
        t2= v.findViewById(R.id.txt_desc);
        pm10= v.findViewById(R.id.PM10prog);
        main_lyt_pm10= v.findViewById(R.id.main_lyt_pm10);
        main_lyt_pm10.setOnClickListener(null);
        main_lyt_pm10.setClickable(false);
        main_lyt_pm10.setEnabled(false);
        int score=(int)pm10_Result;
        pm10.setProgress(score);
        t2.setMovementMethod(new ScrollingMovementMethod());
        back= v.findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OutdoorAirQualityMain_new.frame_layout_co2.setVisibility(View.GONE);

            }
        });
        setColorStatus(t1,pm10,score);
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
