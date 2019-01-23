package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;


/**
 * Created by developers on 17/5/17.
 */

public class UnderConstructionFragment extends Fragment {

    public static UnderConstructionFragment newInstance() {
        return new UnderConstructionFragment();
    }







    private View v;


    Context mcontext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
        v=inflater.inflate(R.layout.fragment_under_construction, container, false);






        return v;
    }





}