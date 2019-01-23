package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.HomeScreenGifsFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.indoorairquality.IndoorAirQualityViewFragment;

/**
 * Created by developers on 16/12/17.
 */

public class HomeScreenViewPagerAdapter extends FragmentPagerAdapter {

    public HomeScreenViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                HomeScreenGifsFragment homeScreenGifsFragment=new HomeScreenGifsFragment();
                return homeScreenGifsFragment;

            case 1:

            IndoorAirQualityViewFragment indoorAirQualityViewFragment=new IndoorAirQualityViewFragment();
            return indoorAirQualityViewFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
