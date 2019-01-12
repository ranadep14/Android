package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;

import java.util.Calendar;

@SuppressLint("Registered")
public class Demo extends AppCompatActivity {
  //  NonSwipeableViewPager viewPager;
    //ScheduleSliderAdapter scheduleSliderAdapter;
    Button previousDayNav,nextDayNav,todayNav;
    String yesterday,today,tomorrow;
    int dayincrementer=0;
    Calendar calendar;
    Calendar todaysCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
    }
}
