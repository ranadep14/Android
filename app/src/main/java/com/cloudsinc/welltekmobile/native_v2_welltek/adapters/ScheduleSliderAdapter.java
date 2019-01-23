package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule.Item2;

/**
 * Created by developers on 11/4/17.
 */
public class ScheduleSliderAdapter extends FragmentPagerAdapter {

    public ScheduleSliderAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);

    }


    @Override
    public Fragment getItem(int position) {
        Item2 item3=new Item2();
       /* switch (position){
            case 0: Item1 item1=new Item1();
                return item1;

            case 1:Item2 item2=new Item2();
                return item2;

        }*/
        return item3;
    }

    @Override
    public int getCount() {
        return 7;
    }


    public static String makeFragmentName(int viewPagerId, int index) {
        return "android:switcher:" + viewPagerId + ":" + index;
    }
}
