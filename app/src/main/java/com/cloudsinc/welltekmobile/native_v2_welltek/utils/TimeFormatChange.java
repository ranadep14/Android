package com.cloudsinc.welltekmobile.native_v2_welltek.utils;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V4 on 26/8/17.
 */
public class TimeFormatChange {
    public static String convert24(String time)
    {
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
            SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
            Date date = parseFormat.parse(convertTime(time));
            return displayFormat.format(date);
        }
        catch (Exception ex){
            Log.e("24TimeError",ex.getMessage());}
        return "";
    }
    public static String convert12(String time)
    {
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm");
            Date date = parseFormat.parse(convertTime(time));
            return displayFormat.format(date);
        }
        catch (Exception ex){
            Log.e("12TimeError",ex.getMessage());}
        return "";
    }
    public static String convertTime(String time)
    {
        String[] arr_time=time.split(":");
        String hr=arr_time[0];
        String min=arr_time[1];
        if(Integer.parseInt(arr_time[0])<10)
        {
            hr="0"+arr_time[0];
        }
        if(Integer.parseInt(arr_time[1])<10)
        {
            min="0"+arr_time[1];
        }
        return  hr+":"+min;
    }
    public static Date getDateInTimeZone(Date currentDate, String timeZoneId) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
        Date localDateTime = new Date(currentDate.getTime() + timeZone.getOffset(currentDate.getTime()));
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(localDateTime.getTime());
        if (timeZone.useDaylightTime()) {
            // time zone uses Daylight Saving
            cal.add(Calendar.MILLISECOND, timeZone.getDSTSavings() * -1);// in milliseconds
        }
        return cal.getTime();
    }
}
