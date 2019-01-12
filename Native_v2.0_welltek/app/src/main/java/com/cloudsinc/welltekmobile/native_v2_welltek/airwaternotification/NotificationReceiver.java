package com.cloudsinc.welltekmobile.native_v2_welltek.airwaternotification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
/**
 * This notification receiver this receiver is for water and air filter notification
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class NotificationReceiver extends BroadcastReceiver
{
    String TAG=NotificationReceiver.this.getClass().getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle=intent.getExtras();
        Logs.info(TAG+"notification_data",""+bundle);
        if(bundle!=null) {
            String title = bundle.getString("title");
            String msg = bundle.getString("msg");
            String type = bundle.getString("type");
            String date = bundle.getString("exp_date");
            String notif_date = bundle.getString("notif_date");
            String notif_time = bundle.getString("notif_time");
            Intent service1 = new Intent(context, NotificationAlarmService.class);
            service1.putExtra("title", title);
            service1.putExtra("msg", msg);
            service1.putExtra("type", type);
            service1.putExtra("exp_date", date);
            service1.putExtra("notif_date", notif_date);
            service1.putExtra("notif_time", notif_time);
            context.startService(service1);
        }
    }
}
