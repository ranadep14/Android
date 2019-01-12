package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 8/6/17.
 * @purpose  - USed for Add SONOS devices to rooms
 */
public class SettingAddDevices extends Fragment {
    public static SettingAddDevices newInstance() {
        return new SettingAddDevices();
    }

    View view;
    @BindView(R.id.lyt_sonosadd)LinearLayout sonosAdd;
    @BindView(R.id.lyt_smartfilter)LinearLayout filter;
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting_add_devices, container, false);
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();
        ButterKnife.bind(this,view);
        txt_fragment_title.setText("Add Devices");
        FontUtility.setAppFont(mContainer, mFont);


        return view;
    }
    @OnClick(R.id.lyt_sonosadd)
    public void AddSonos()
    {
        App.setTemp_bundle(new Bundle());
        ReplaceFragment.replaceFragment(SettingAddDevices.this,R.id.frm_filter_main_container, AddSonosFragment.newInstance());
    }

    @OnClick(R.id.img_back)
    public void img_back()
    {
        ReplaceFragment.replaceFragment(SettingAddDevices.this,R.id.frm_filter_main_container, DeviceSettingFragment.newInstance());
    }

    @OnClick(R.id.lyt_smartfilter)
    public void AddFilter()
    {
        ReplaceFragment.replaceFragment(SettingAddDevices.this,R.id.frm_filter_main_container, AddFilterFragment.newInstance());
    }

}
