package com.cloudsinc.welltekmobile.native_v2_welltek.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.cloudsinc.welltekmobile.native_v2_welltek.activities.ConnectionLostActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.LoginActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule.RemoveNotification;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.NotifyService;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.NotifyService_sleep;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;

import java.util.Iterator;
import java.util.Map;

import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.listeners.SocketListeners.cancleNotification;
import static com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager.PREF_NAME;
/**
 * Created by developers on 27/11/18.
 */
public class LogoutFunctionality {

    private PrefManager pref;
    private String TAG=this.getClass().getSimpleName();
    public void logoutAction(Activity activity)
    {
        pref=new PrefManager(activity);
        if (App.getSocket()!=null)
        {
            App.getSocket().off();
            App.setSocket(null);
        }
        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        deleteDawnSleepNotification(activity,"all");
        cancleNotification(activity,"Air");
        cancleNotification(activity,"Water");
        pref.setValue("jwt_token","No Record");
        pref.setValue("state_serial","No Record");
        pref.setValue("hub_serial","No Record");
        pref.setValue("Simulation_info","No Record");
        pref.setValue("connectin_to_hub","No Record");
        pref.setValue("ip_address","No Record");
        pref.setValue("clouzer_user","No Record");
        activity.finishAffinity();
        Intent i=new Intent(activity,LoginActivity.class);
        activity.startActivity(i);
    }
    public void deleteDawnSleepNotification(Context activity,String action) {

        SharedPreferences prefs = activity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        Iterator iter = prefs.getAll().entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry pair = (Map.Entry)iter.next();
            Logs.info(this.getClass().getSimpleName(),"key............"+pair.getKey()+"valu......."+pair.getValue());
            if(pair.getKey().toString().contains("dawn#")&&(action.equals("all")||action.equals("dawn")))
            {
                try {
                    AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
                    Intent myIntent = new Intent(activity, NotifyService.class);
                    PendingIntent pendingIntent = PendingIntent.getService(activity, (int) pair.getValue(), myIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.cancel(pendingIntent);
                    new PrefManager(activity).removeKey(pair.getKey().toString());
                }
                catch (Exception ex)
                {
                    Logs.error(TAG,""+ex.getMessage());
                }
            }
            if(pair.getKey().toString().contains("sleep#")&&(action.equals("all")||action.contains("sleep")))
            {
                try {
                    AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
                    NotificationManager nMgr = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
                    nMgr.cancel( (int) pair.getValue());
                    Intent myIntent1 = new Intent(activity, NotifyService_sleep.class);
                    PendingIntent pendingIntent1 = PendingIntent.getService(activity, (int) pair.getValue(), myIntent1,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.cancel(pendingIntent1);
                    new PrefManager(activity).removeKey(pair.getKey().toString());
                }
                catch (Exception ex)
                {
                    Logs.error(TAG,""+ex.getMessage());
                }
            }
        }

    }
}
