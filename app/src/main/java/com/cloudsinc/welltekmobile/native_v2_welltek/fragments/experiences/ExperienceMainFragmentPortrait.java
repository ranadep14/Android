package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.experirences_activities.ActivitiesFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule.new_WakeRoomLanding;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.HomeHeathByZoneFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;


/**
 * The ExperienceMainFragment is for user can apply different scenes on different rooms.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */

public class ExperienceMainFragmentPortrait extends Fragment {

    public static ExperienceMainFragmentPortrait newInstance() {
        return new ExperienceMainFragmentPortrait();
    }
    private View v;
    @SuppressLint("StaticFieldLeak")
    public static LinearLayout experience_tabs;


    @BindView(R.id.view_exp_select)View view_exp_select;
    @BindView(R.id.view_schedule_select)View view_schedule_select;
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;

    @BindView(R.id.lyt_rel_act)RelativeLayout lyt_rel_act;
    @BindView(R.id.lyt_rel_schedule)RelativeLayout lyt_rel_schedule;
    String TAG=ExperienceMainFragmentPortrait.this.getClass().getName();

    Context mcontext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.activity_experince_main_portrait, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);
        txt_fragment_title.setText("Experiences");
        experience_tabs= v.findViewById(R.id.experience_tabs);
        experience_tabs.setVisibility(View.VISIBLE);
        view_exp_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_selected_color));
        MainActivity.bottom_menu_bar.setVisibility(View.GONE);
        ReplaceFragment.replaceFragment(this, R.id.frm_expriencesmain_container, ActivitiesFragment.newInstance());

        setDefaultColor();
      if(App.getCallfrom().equals("emptyschedule")) {
          view_schedule_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_selected_color));
          ReplaceFragment.replaceFragment(this, R.id.frm_expriencesmain_container, new_WakeRoomLanding.newInstance());
          lyt_rel_schedule.setEnabled(false);
          lyt_rel_act.setEnabled(true);
          lyt_rel_act.setAlpha(0.5f);
          lyt_rel_schedule.setAlpha(1f);
      }
      else
      {
          view_exp_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_selected_color));
          ReplaceFragment.replaceFragment(this, R.id.frm_expriencesmain_container, ActivitiesFragment.newInstance());
          lyt_rel_act.setEnabled(false);
          lyt_rel_schedule.setEnabled(true);
          lyt_rel_schedule.setAlpha(0.5f);
          lyt_rel_act.setAlpha(1f);
      }

        return v;
    }

    @OnClick(R.id.img_back)
    public void img_back()
    {
        ReplaceFragment.replaceFragment(ExperienceMainFragmentPortrait.this, R.id.frm_main_container, HomeHeathByZoneFragment.newInstance());
    }


    @Optional
    @OnClick({R.id.lyt_rel_act, R.id.lyt_rel_schedule})
    public void onClick(View view) {
        setDefaultColor();
        Fragment mfragment=null;
        switch (view.getId())
        {
            case R.id.lyt_rel_act:
                mfragment= ActivitiesFragment.newInstance();
                view_exp_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_selected_color));
                lyt_rel_act.setEnabled(false);
                lyt_rel_schedule.setEnabled(true);
                lyt_rel_schedule.setAlpha(0.5f);
                lyt_rel_act.setAlpha(1f);
                break;
            case R.id.lyt_rel_schedule:
                mfragment = new_WakeRoomLanding.newInstance();
                view_schedule_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_selected_color));
                lyt_rel_schedule.setEnabled(false);
                lyt_rel_act.setEnabled(true);
                lyt_rel_act.setAlpha(0.5f);
                lyt_rel_schedule.setAlpha(1f);
                break;

        }
        ReplaceFragment.replaceFragment(this, R.id.frm_expriencesmain_container,mfragment);
    }

    public void setDefaultColor()
    {
        view_exp_select.setBackgroundColor(this.getResources().getColor(R.color.tab_bg_color));
        view_schedule_select.setBackgroundColor(this.getResources().getColor(R.color.tab_bg_color));

    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white);


    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }


}