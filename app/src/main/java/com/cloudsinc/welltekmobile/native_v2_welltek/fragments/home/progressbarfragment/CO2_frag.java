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

import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.OutdoorAirQualityMain_new.co_Result;


/**
 * A simple {@link Fragment} subclass.
 */
public class CO2_frag extends Fragment {

    Button back;
    private View v;
    String c="CO";
    DonutProgress COprog;
    TextView tv;
    LinearLayout main_lyt;
    private TextView txt_desc;
    public static CO2_frag newInstance() {
        return new CO2_frag();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_co2_new, container,
                false);
        back= v.findViewById(R.id.backbutton);
        tv= v.findViewById(R.id.textView_status);
         COprog= v.findViewById(R.id.COprog);
        main_lyt= v.findViewById(R.id.main_lyt);
        txt_desc= v.findViewById(R.id.txt_desc);
        COprog.setText(c);
        int a=(int)co_Result;

        COprog.setProgress(a);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OutdoorAirQualityMain_new.frame_layout_co2.setVisibility(View.GONE);

            }
        });

        main_lyt.setClickable(false);
        setColorStatus(tv,COprog,co_Result);
        txt_desc.setMovementMethod(new ScrollingMovementMethod());
        return v;
    }


    public void setColorStatus(final TextView status, final DonutProgress v, double score)
    {
        double result=score;

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