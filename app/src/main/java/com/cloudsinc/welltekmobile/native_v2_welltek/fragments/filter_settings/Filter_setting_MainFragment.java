package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.filters.FiltersFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.SettingMainFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity.bottom_menu_bar;


/**
 * The Filter_setting_MainFragment is for contains three tabs filters,setting,legal
 *
 * @author  jaid shaikh
 * @version 1.0
 * @since   2017-11-8
 */

public class Filter_setting_MainFragment extends Fragment {

    public static Filter_setting_MainFragment newInstance() {
        return new Filter_setting_MainFragment();
    }




    private View v;


    @BindView(R.id.view_filter_select)View view_filter_select;
    @BindView(R.id.view_setting_select)View view_setting_select;
    @BindView(R.id.view_legal_select)View view_legal_select;
    @BindView(R.id.lyt_rel_filters)RelativeLayout lyt_rel_filters;
    @BindView(R.id.lyt_rel_setting)RelativeLayout lyt_rel_setting;
    @BindView(R.id.lyt_rel_legal)RelativeLayout lyt_rel_legal;


    String TAG="FilterFragment";
    Context mcontext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
         v=inflater.inflate(R.layout.activity_filter_setting_main, container, false);
        mcontext=v.getContext();
        bottom_menu_bar.setVisibility(View.GONE);
        ButterKnife.bind(this,v);
        ReplaceFragment.replaceFragment(this,R.id.frm_filter_main_container, FiltersFragment.newInstance());
        lyt_rel_filters.setAlpha(1f);
        lyt_rel_setting.setAlpha(0.5f);
        lyt_rel_legal.setAlpha(0.5f);
        return v;
    }


    @Optional
    @OnClick({R.id.lyt_rel_filters, R.id.lyt_rel_setting, R.id.lyt_rel_legal})
    public void onClick(View view) {
        setDefaultColor();
        Fragment mfragment=null;
        switch (view.getId())
        {
            case R.id.lyt_rel_filters:
                mfragment= FiltersFragment.newInstance();
                view_filter_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_selected_color));
                lyt_rel_filters.setEnabled(false);
                lyt_rel_setting.setEnabled(true);
                lyt_rel_legal.setEnabled(true);
                lyt_rel_filters.setAlpha(1f);
                lyt_rel_setting.setAlpha(0.5f);
                lyt_rel_legal.setAlpha(0.5f);
                break;
            case R.id.lyt_rel_setting:
                mfragment= SettingMainFragment.newInstance();
                view_setting_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_selected_color));
                view_setting_select.setEnabled(false);
                lyt_rel_filters.setEnabled(true);
                lyt_rel_legal.setEnabled(true);
                lyt_rel_filters.setAlpha(0.5f);
                lyt_rel_setting.setAlpha(1f);
                lyt_rel_legal.setAlpha(0.5f);
                break;
            case R.id.lyt_rel_legal:
                mfragment= LegalFragent.newInstance();
                view_legal_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_selected_color));
                view_legal_select.setEnabled(false);
                lyt_rel_filters.setEnabled(true);
                lyt_rel_setting.setEnabled(true);
                lyt_rel_filters.setAlpha(0.5f);
                lyt_rel_setting.setAlpha(0.5f);
                lyt_rel_legal.setAlpha(1f);
                break;

        }
        ReplaceFragment.replaceFragment(this,R.id.frm_filter_main_container,mfragment);
    }

    public void setDefaultColor()
    {
        view_filter_select.setEnabled(true);
        view_setting_select.setEnabled(true);
        view_legal_select.setEnabled(true);
        view_filter_select.setBackgroundColor(this.getResources().getColor(R.color.tab_bg_color));
        view_setting_select.setBackgroundColor(this.getResources().getColor(R.color.tab_bg_color));
        view_legal_select.setBackgroundColor(this.getResources().getColor(R.color.tab_bg_color));

    }


}