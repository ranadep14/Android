package com.cloudsinc.welltekmobile.native_v2_welltek.utils;

/*
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

*/
/**
 * Created by developers on 9/7/18.
 *//*


public class DateAxisValueFormatter implements IAxisValueFormatter
{
    private String[] mValues;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

    public DateAxisValueFormatter(String[] values) {
        this.mValues = values; }

    //@override
    public String getFormattedValue(float value, AxisBase axis) {
        String str_date="";
        if( App.getHubInfo()!=null) {
            try {
                Logs.error(this.getClass().getSimpleName(), "ttttttttttttttttt" + value);
                JSONArray jsonArray = App.getHubInfo().getJSONArray("data");
                String strDefaultTimeZone = jsonArray.getJSONObject(0).getString("CML_TIMEZONE");//defaultTimeZone.getDisplayName(false, TimeZone.SHORT);
                Calendar calendar=Calendar.getInstance(TimeZone.getTimeZone(strDefaultTimeZone));
                calendar.setTimeInMillis((long) value);
                str_date=calendar.get(Calendar.DAY_OF_MONTH)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.YEAR);
                Logs.error(this.getClass().getSimpleName(), "xxxxxxxxxxxxxxxxx" + str_date);
            } catch (Exception ex) {
                Logs.error(this.getClass().getSimpleName(), "ccccccccccccccccccccccccccccc" + ex.getMessage());
            }
        }
            Logs.error(this.getClass().getSimpleName(),"-----------"+sdf.format(new Date((long)value)));
                return str_date;
    }
}



*/

        import com.github.mikephil.charting.components.AxisBase;
        import com.github.mikephil.charting.formatter.IAxisValueFormatter;

        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.Locale;

/**
 * Created by Abhishek on 08/06/17.
 */
public class DateAxisValueFormatter implements IAxisValueFormatter {

    private DateFormat mDataFormat;
    private Date mDate;

    public DateAxisValueFormatter() {
     //   this.referenceTimestamp = referenceTimestamp;
        this.mDataFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        this.mDate = new Date();
    }

    /**
     * Called when a value from an axis is to be formatted
     * before being drawn. For performance reasons, avoid excessive calculations
     * and memory allocations inside this method.
     */

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // convertedTimestamp = originalTimestamp - referenceTimestamp
        long convertedTimestamp = (long) value;

        // Retrieve original timestamp
        long originalTimestamp =  convertedTimestamp;

        // Convert timestamp to hour:minute
        return getDate(originalTimestamp);
    }

    private String getDate(long timestamp) {
        try {
            mDate.setTime(timestamp * 1000);
            return mDataFormat.format(mDate);
        } catch (Exception ex) {
            return "xx";
        }
    }
}
