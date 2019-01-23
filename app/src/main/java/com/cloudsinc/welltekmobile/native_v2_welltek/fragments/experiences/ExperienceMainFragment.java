package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.all_experiences.Activities_FavoritesFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule.new_WakeRoomLanding;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * The ExperienceMainFragment is for user can apply different scenes on different rooms.
 *
 * @author  jaid shaikh
 * @version 1.0
 * @since   2017-11-8
 */

public class ExperienceMainFragment extends Fragment {

    public static ExperienceMainFragment newInstance() {
        return new ExperienceMainFragment();
    }
    private View v;


    @BindView(R.id.view_exp_select)View view_exp_select;
    @BindView(R.id.view_schedule_select)View view_schedule_select;

    @BindView(R.id.lyt_rel_exp)RelativeLayout lyt_rel_exp;
    @BindView(R.id.lyt_rel_schedule)RelativeLayout lyt_rel_schedule;


    String TAG="ExperiencesFragment";
    Context mcontext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
        v=inflater.inflate(R.layout.activity_experince_main, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);

        ReplaceFragment.replaceFragment(ExperienceMainFragment.this, R.id.frm_expriencesmain_container, Activities_FavoritesFragment.newInstance());
        lyt_rel_schedule.setAlpha(0.5f);
        lyt_rel_exp.setAlpha(1f);

     /*   if (App.getCallfrom().equals("dawn_notification")){
            ReplaceFragment.replaceFragment(ExperienceMainFragment.this,R.id.frm_expriencesmain_container, new_WakeRoomLanding.newInstance());

        }
        else {
            ReplaceFragment.replaceFragment(ExperienceMainFragment.this, R.id.frm_expriencesmain_container, Activities_FavoritesFragment.newInstance());
        }*/
      return v;
    }


    @Optional
    @OnClick({R.id.lyt_rel_exp, R.id.lyt_rel_schedule})
    public void onClick(View view) {
        setDefaultColor();
        Fragment mfragment=null;
        switch (view.getId())
        {
            case R.id.lyt_rel_exp:
                mfragment= Activities_FavoritesFragment.newInstance();
                view_exp_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_selected_color));
                lyt_rel_exp.setEnabled(false);
                lyt_rel_schedule.setEnabled(true);
                lyt_rel_schedule.setAlpha(0.5f);
                lyt_rel_exp.setAlpha(1f);

                break;
            case R.id.lyt_rel_schedule:
                mfragment= new_WakeRoomLanding.newInstance();
                view_schedule_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_selected_color));
                lyt_rel_schedule.setEnabled(false);
                lyt_rel_exp.setEnabled(true);
                lyt_rel_exp.setAlpha(0.5f);
                lyt_rel_schedule.setAlpha(1f);

                break;

        }
        ReplaceFragment.replaceFragment(this,R.id.frm_expriencesmain_container,mfragment);
    }

    public void setDefaultColor()
    {
        view_exp_select.setEnabled(true);
        view_schedule_select.setEnabled(true);
        view_exp_select.setBackgroundColor(this.getResources().getColor(R.color.tab_bg_color));
        view_schedule_select.setBackgroundColor(this.getResources().getColor(R.color.tab_bg_color));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }
}