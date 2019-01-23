package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Item2 extends Fragment implements View.OnClickListener {

    Button setSleep;
    FrameLayout leftWakeUpTimeBackground,rightBedTimeBackground;



    public Item2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_item2, container, false);
        setSleep= v.findViewById(R.id.setSleep);
        leftWakeUpTimeBackground= v.findViewById(R.id.leftWakeUpTimeBackground);
        rightBedTimeBackground= v.findViewById(R.id.rightBedTimeBackground);

        leftWakeUpTimeBackground.setVisibility(View.VISIBLE);
        rightBedTimeBackground.setVisibility(View.VISIBLE);

        setSleep.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {


    }
}
