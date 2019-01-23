package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.indoorsliderfragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.indoorairquality.IndoorAirQualityMain;
import com.github.lzyzsd.circleprogress.DonutProgress;


/**
 * A simple {@link Fragment} subclass.
 */
public class Item4 extends Fragment implements View.OnClickListener {

    private int tvocpercenage =0;
    private View v;
    private TextView tvoc_text;
    private DonutProgress tvocprog;
    ImageView b1;
    public static Item4 newInstance() {
        return new Item4();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_item4_new, container, false);
        b1= v.findViewById(R.id.close_nav);
        b1.setOnClickListener(this);
        tvoc_text= v.findViewById(R.id.tvoc_text);
        tvocprog= v.findViewById(R.id.tvocprog);
        tvocpercenage = IndoorAirQualityMain.new_score_tvoc;
      //  Toast.makeText(getContext(), ""+tvocpercenage, Toast.LENGTH_SHORT).show();
        // PM10percenage = 100-PM10value;


        //set score inside prog bar
        //tvocprog.setText(""+tvocpercenage);
        if(tvocpercenage==0){
            tvoc_text.setText("No Data Available");
        }
        if(tvocpercenage>0 && tvocpercenage<=200){
            tvoc_text.setText("Low");
        }
        else if(tvocpercenage>=201 && tvocpercenage<=500){
            tvoc_text.setText("Medium");
        }
        else if(tvocpercenage>=501){
            tvoc_text.setText("High");
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        if(v==b1)
        {
            getActivity().finish();
        }

    }
}
