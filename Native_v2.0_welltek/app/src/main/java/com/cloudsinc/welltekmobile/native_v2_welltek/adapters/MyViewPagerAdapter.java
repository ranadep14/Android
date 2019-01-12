package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by developers on 18/11/17.
 */

public class MyViewPagerAdapter extends FragmentStatePagerAdapter {


    FragmentManager fragmentManager;
    ArrayList<Fragment> layouts;
    public MyViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        if (fm.getFragments() != null) {
            fm.getFragments().clear();
        }
        fragmentManager=fm;
        layouts=fragments;

        Log.e("FagmentCount",""+layouts.size());
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    @Override
    public Fragment getItem(int position) {
        return layouts.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        if (position >= getCount()) fragmentManager.beginTransaction().remove((Fragment) object).commit();
    }

    @Override
    public int getCount() {
        return layouts.size();
    }



}
