package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.types;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;

import butterknife.ButterKnife;

import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.ElementsMainFragmentPortrait.elements_tabs;

/**
 * Created by developers on 30/12/17.
 */

public class ElementTypeAudio extends Fragment {

    public static ElementTypeAudio newInstance() {
        return new ElementTypeAudio();
    }





    private View view;

    Context mcontext;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
        view =inflater.inflate(R.layout.fragment_element_type_audio, container, false);
        mcontext= view.getContext();
        ButterKnife.bind(this, view);



        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        elements_tabs.setVisibility(View.GONE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getArguments().getString("DEVICE_TYPE"));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow);


    }

    @Override
    public void onStop() {
        super.onStop();
        elements_tabs.setVisibility(View.VISIBLE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

}