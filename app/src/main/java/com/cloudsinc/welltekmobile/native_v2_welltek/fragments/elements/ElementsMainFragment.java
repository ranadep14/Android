package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.favorites.FavoritesMainFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms.RoomsMainFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
/**
 * This class containing functionality related to displaying container for room display fragment
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class ElementsMainFragment extends Fragment {
    public static ElementsMainFragment newInstance() {
        return new ElementsMainFragment();
    }
    private View v;
    @BindView(R.id.view_fav_select)View view_fav_select;
    @BindView(R.id.view_rooms_select)View view_rooms_select;
    @BindView(R.id.view_type_select)View view_type_select;
    @BindView(R.id.rel_fav)RelativeLayout rel_fav;
    @BindView(R.id.rel_rooms)RelativeLayout rel_rooms;
    @BindView(R.id.rel_types)RelativeLayout rel_types;
    @BindView(R.id.txt_type_heading)TextView txt_type_heading;
    @BindView(R.id.txt_fav_heading)TextView txt_fav_heading;
    @BindView(R.id.txt_room_heading)TextView txt_room_heading;
    String TAG=ElementsMainFragment.this.getClass().getName();
    Context mcontext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
         v=inflater.inflate(R.layout.activity_elements_main, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);
        App.setMusic_playlist_subcriber(null);
        ReplaceFragment.replaceFragment(this,R.id.frm_elemenmain_container,FavoritesMainFragment.newInstance());
        return v;
    }
    @Optional
    @OnClick({R.id.rel_types, R.id.rel_rooms, R.id.rel_fav})
    public void onClick(View view) {
        setDefaultColor();
        Fragment mfragment=null;
        switch (view.getId())
        {
            case R.id.rel_fav:
                mfragment= FavoritesMainFragment.newInstance();
                txt_fav_heading.setTextColor(mcontext.getResources().getColor(R.color.white));
                view_fav_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_color));
                break;
            case R.id.rel_rooms:
                mfragment= RoomsMainFragment.newInstance();
                view_rooms_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_color));
                txt_room_heading.setTextColor(mcontext.getResources().getColor(R.color.white));
                break;
            case R.id.rel_types:
                view_type_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_color));
                txt_type_heading.setTextColor(mcontext.getResources().getColor(R.color.white));
                break;
        }
        FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
        transaction.remove(RoomsMainFragment.newInstance()).commit();
        ReplaceFragment.replaceFragment(this,R.id.frm_elemenmain_container,mfragment);
    }
    public void setDefaultColor()
    {
        txt_type_heading.setTextColor(mcontext.getResources().getColor(R.color.light_white));
        txt_room_heading.setTextColor(mcontext.getResources().getColor(R.color.light_white));
        txt_fav_heading.setTextColor(mcontext.getResources().getColor(R.color.light_white));
        view_fav_select.setBackgroundColor(this.getResources().getColor(R.color.tab_bg_color));
        view_rooms_select.setBackgroundColor(this.getResources().getColor(R.color.tab_bg_color));
        view_type_select.setBackgroundColor(this.getResources().getColor(R.color.tab_bg_color));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }
}