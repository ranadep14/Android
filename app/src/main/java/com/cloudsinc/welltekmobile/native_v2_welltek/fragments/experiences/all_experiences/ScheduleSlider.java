package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.all_experiences;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.NonSwipeableViewPager;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.ScheduleSliderAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;

public class ScheduleSlider extends Fragment {
    public static ScheduleSlider newInstance() {
        return new ScheduleSlider();
    }
    NonSwipeableViewPager viewPager;
    ScheduleSliderAdapter scheduleSliderAdapter;
    Button previousDayNav,nextDayNav,todayNav;
    String yesterday,today,tomorrow;
    int dayincrementer=0;
    Calendar calendar;
    Calendar todaysCalendar;
    private View v;
    Context mcontext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
        v=inflater.inflate(R.layout.activity_schedule_slider, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);
        Calendar tempdate = Calendar.getInstance();
        Date date=tempdate.getTime();
        String day=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());


        viewPager= v.findViewById(R.id.scheduleViewPager);
        scheduleSliderAdapter=new ScheduleSliderAdapter(getActivity().getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(6);
        viewPager.setAdapter(scheduleSliderAdapter);

        previousDayNav = v.findViewById(R.id.left_nav);
        todayNav = v.findViewById(R.id.today);
        nextDayNav = v.findViewById(R.id.right_nav);

        Calendar tempdateNV = Calendar.getInstance();
        tempdateNV.add(Calendar.DAY_OF_YEAR,1);
        Date dateNV=tempdateNV.getTime();
        String dayN=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dateNV.getTime());
        nextDayNav.setText(dayN);


        previousDayNav.setVisibility(View.GONE);

        previousDayNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tab = viewPager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    viewPager.setCurrentItem(tab,true);
                } else if (tab == 0) {
                    viewPager.setCurrentItem(tab,true);
                }
            }
        });

        nextDayNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tab = viewPager.getCurrentItem();
                tab++;
                viewPager.setCurrentItem(tab,true);
            }
        });



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Calendar tempdate = Calendar.getInstance();
                Date date=tempdate.getTime();
                String day=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
                updatePreviousButtonName(position-1,day);
                updateTodayButtonName(position,day);
                updateNextButtonName(position+1,day);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
return v;

    }

    private void updatePreviousButtonName(int position, String day) {
        previousDayNav.setVisibility(View.VISIBLE);
        Calendar tempdateP = Calendar.getInstance();
        tempdateP.add(Calendar.DAY_OF_YEAR, position);
        Date dateP=tempdateP.getTime();
        String dayP=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dateP.getTime());
        Log.e("stateChange2",String.valueOf(position)+" "+dayP);
    if (position==-1){
        previousDayNav.setVisibility(View.INVISIBLE);

    }else {
        /*if (dayP.contentEquals(day)) {
            previousDayNav.setText("Today");
        } else */{
            previousDayNav.setText(dayP);
        }
    }

    }

    private void updateTodayButtonName(int position, String day) {
        Calendar tempdateT = Calendar.getInstance();
        tempdateT.add(Calendar.DAY_OF_YEAR, position);
        Date dateT=tempdateT.getTime();
        String dayT=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dateT.getTime());
        Log.e("stateChange2",String.valueOf(position)+" "+dayT);

        if (dayT.contentEquals(day)){
            todayNav.setText("Today");
        }else {
            todayNav.setText(dayT);

        }

    }

    private void updateNextButtonName(int position, String day) {
        nextDayNav.setVisibility(View.VISIBLE);

        Calendar tempdateN = Calendar.getInstance();
        tempdateN.add(Calendar.DAY_OF_YEAR, position);
        Date dateN=tempdateN.getTime();
        String dayN=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dateN.getTime());
        Log.e("stateChange2",String.valueOf(position)+" "+dayN);

        if (position==7){
            nextDayNav.setVisibility(View.INVISIBLE);
        }else {


        /*if (dayN.contentEquals(day)){
            nextDayNav.setText("");
        }else */{
            nextDayNav.setText(dayN);

        }
        }


    }






}
