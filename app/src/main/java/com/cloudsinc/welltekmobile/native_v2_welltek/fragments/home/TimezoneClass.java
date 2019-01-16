package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home;

import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by developers on 7/2/18.
 */

public class TimezoneClass {

    public static String getTimezone(String timezone){

        String displayFormat = "";
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone(timezone));

        int hour12 = calendar.get(Calendar.HOUR); // 0..11
        int minutes = calendar.get(Calendar.MINUTE); // 0..59
        int seconds = calendar.get(Calendar.SECOND); // 0..59
        boolean am = calendar.get(Calendar.AM_PM) == Calendar.AM;
        int month = calendar.get(Calendar.MONTH);

        Log.i("hour: ", String.valueOf(hour12));
        Log.i("minutes: ", String.valueOf(minutes));
        Log.i("seconds: ", String.valueOf(seconds));
        Log.i("month: ", String.valueOf(month));

        switch (month){

            case 0:
                displayFormat = "January";
                break;
            case 1:
                displayFormat = "February";
                break;
            case 2:
                displayFormat = "March";
                break;
            case 3:
                displayFormat = "April";
                break;
            case 4:
                displayFormat = "May";
                break;
            case 5:
                displayFormat = "June";
                break;
            case 6:
                displayFormat = "July";
                break;
            case 7:
                displayFormat = "August";
                break;
            case 8:
                displayFormat = "September";
                break;
            case 9:
                displayFormat = "October";
                break;
            case 10:
                displayFormat = "November";
                break;
            case 11:
                displayFormat = "December";
                break;
        }

        displayFormat = displayFormat + " " + String.valueOf(hour12) + ":"
                + String.valueOf(minutes);

        if(am == true) {

            Log.i("am/pm: ", "am");
            displayFormat = displayFormat + " am";
        }
        else {

            Log.i("am/pm: ", "pm");
            displayFormat = displayFormat + " pm";
        }

        return displayFormat;
    }
}