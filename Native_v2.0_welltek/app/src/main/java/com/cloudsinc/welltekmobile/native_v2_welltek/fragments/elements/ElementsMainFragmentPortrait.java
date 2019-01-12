package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms.RoomsMainFragmentPortrait;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.types.TypesMainFragmentPortrait;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.HomeHeathByZoneFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
/**
 * The ElementsMainFragmentPortrait is for control actual devices like light,blinds,music.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class ElementsMainFragmentPortrait extends Fragment {
    public static ElementsMainFragmentPortrait newInstance() {
        return new ElementsMainFragmentPortrait();
    }
    @SuppressLint("StaticFieldLeak")
    public static LinearLayout elements_tabs;
    private View v;
    @BindView(R.id.view_fav_select)View view_fav_select;
    @BindView(R.id.view_rooms_select)View view_rooms_select;
    @BindView(R.id.view_type_select)View view_type_select;
    @BindView(R.id.txt_type_heading)TextView txt_type_heading;
    @BindView(R.id.txt_room_heading)TextView txt_room_heading;
    @BindView(R.id.rel_fav)RelativeLayout rel_fav;
    @BindView(R.id.rel_rooms)RelativeLayout rel_rooms;
    @BindView(R.id.rel_types)RelativeLayout rel_types;
    @BindView(R.id.txt_fragment_title) TextView txt_fragment_title;
    String TAG=ElementsMainFragmentPortrait.this.getClass().getName();
    Context mcontext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
         v=inflater.inflate(R.layout.activity_elements_main, container, false);
        MainActivity.bottom_menu_bar.setVisibility(View.GONE);
        Logs.info(TAG, "Im here");
        mcontext=v.getContext();
        ButterKnife.bind(this,v);
        txt_fragment_title.setText("Elements");
        elements_tabs= v.findViewById(R.id.elements_tabs);
        elements_tabs.setVisibility(View.VISIBLE);
        setDefaultColor();
        if(App.getTemp_bundle()!=null) {
            if (App.getTemp_bundle().getString("call_from").equals("type")) {
                view_type_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_color));
                txt_type_heading.setTextColor(mcontext.getResources().getColor(R.color.white));
                ReplaceFragment.replaceFragment(ElementsMainFragmentPortrait.this, R.id.frm_elemenmain_container, TypesMainFragmentPortrait.newInstance());
            } else {
                txt_room_heading.setTextColor(mcontext.getResources().getColor(R.color.white));
                view_rooms_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_color));
                ReplaceFragment.replaceFragment(ElementsMainFragmentPortrait.this, R.id.frm_elemenmain_container, RoomsMainFragmentPortrait.newInstance());
            }
        }
        return v;
    }
    @OnClick(R.id.img_back)
    public void img_back()
    {
        ReplaceFragment.replaceFragment(ElementsMainFragmentPortrait.this, R.id.frm_main_container, HomeHeathByZoneFragment.newInstance());
    }
    @Optional
    @OnClick({R.id.rel_types, R.id.rel_rooms, R.id.rel_fav})
    public void onClick(View view) {
        setDefaultColor();
        Fragment mfragment=null;
        switch (view.getId())
        {
            case R.id.rel_rooms:
                mfragment= RoomsMainFragmentPortrait.newInstance();
                view_rooms_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_color));
                txt_room_heading.setTextColor(mcontext.getResources().getColor(R.color.white));
                break;
            case R.id.rel_types:
               /* mfragment= TypesMainFragment.newInstance();*/
                mfragment= TypesMainFragmentPortrait.newInstance();
                view_type_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_color));
                txt_type_heading.setTextColor(mcontext.getResources().getColor(R.color.white));
                break;
        }
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frm_elemenmain_container,mfragment);
        fragmentTransaction.commit();
    }
    public void setDefaultColor()
    {
        txt_type_heading.setTextColor(mcontext.getResources().getColor(R.color.light_white));
        txt_room_heading.setTextColor(mcontext.getResources().getColor(R.color.light_white));
        view_fav_select.setBackgroundColor(this.getResources().getColor(R.color.tab_bg_color));
        view_rooms_select.setBackgroundColor(this.getResources().getColor(R.color.tab_bg_color));
        view_type_select.setBackgroundColor(this.getResources().getColor(R.color.tab_bg_color));
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
}