package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.indoorairquality;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.indoorsliderfragments.Item1;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.indoorsliderfragments.Item2;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.indoorsliderfragments.Item3;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.indoorsliderfragments.Item4;

/**
 * Created by developers on 11/4/17.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);

    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return Item1.newInstance();

            case 1:
                return Item2.newInstance();

            case 2:
               return Item3.newInstance();
            case 3:
                return Item4.newInstance();


        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
