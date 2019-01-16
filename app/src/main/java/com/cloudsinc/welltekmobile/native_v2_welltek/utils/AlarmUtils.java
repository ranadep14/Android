package com.cloudsinc.welltekmobile.native_v2_welltek.utils;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlarmUtils {

    private static final String sTagAlarms = ":alarms";


    /**
     * Method that set notification on given date and time
     *
     * @Author nikhil vharamble
     * @Version  2.0.0
     * @Since 1-1-2018
     *
     */

    public static void addAlarm(Context context, Intent intent, int notificationId, Calendar calendar) {

        Logs.error("AlarmUtils","");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.e("WhereIm","ImInMarshmentllo");
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.e("WhereIm","ImInKitkat");

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            Log.e("WhereIm","ImInOther");

            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }



        Log.e("NotificationAddId",""+notificationId);
        saveAlarmId(context, notificationId);
    }

    public static void cancelAlarm(Context context, Intent intent, int notificationId) {
        Logs.info("AlarmUtlis","------------"+notificationId);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
        removeAlarmId(context, notificationId);
    }

    public static void cancelAllAlarms(Context context, Intent intent,int prefix) {
        for (int idAlarm : getAlarmIds(context)) {
            String str_id=""+idAlarm;
            Log.e("NotificationCancleId",""+str_id);
            if(prefix==Integer.parseInt(""+str_id.charAt(0))) cancelAlarm(context, intent, idAlarm);
        }
    }

    public static boolean hasAlarm(Context context, Intent intent, int notificationId) {
        return PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_NO_CREATE) != null;
    }

    private static void saveAlarmId(Context context, int id) {
        List<Integer> idsAlarms = getAlarmIds(context);

        if (idsAlarms.contains(id)) {
            return;
        }

        idsAlarms.add(id);

        saveIdsInPreferences(context, idsAlarms);
    }

    private static void removeAlarmId(Context context, int id) {
        List<Integer> idsAlarms = getAlarmIds(context);

        for (int i = 0; i < idsAlarms.size(); i++) {
            if (idsAlarms.get(i) == id)
                idsAlarms.remove(i);
        }

        saveIdsInPreferences(context, idsAlarms);
    }

    private static List<Integer> getAlarmIds(Context context) {
        List<Integer> ids = new ArrayList<>();
        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            JSONArray jsonArray2 = new JSONArray(prefs.getString(context.getPackageName() + sTagAlarms, "[]"));

            for (int i = 0; i < jsonArray2.length(); i++) {
                ids.add(jsonArray2.getInt(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ids;
    }

    private static void saveIdsInPreferences(Context context, List<Integer> lstIds) {
        JSONArray jsonArray = new JSONArray();
        for (Integer idAlarm : lstIds) {
            jsonArray.put(idAlarm);
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(context.getPackageName() + sTagAlarms, jsonArray.toString());

        editor.apply();
    }
}