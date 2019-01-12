package com.cloudsinc.welltekmobile.native_v2_welltek.airwaternotification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.LoginActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * This service containing functionality related to notify water and air notification
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class NotificationAlarmService extends Service
{
    private NotificationManager mManager;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    String TAG=NotificationAlarmService.this.getClass().getSimpleName();
    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
    }
    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);
        if(intent!=null)
        {
       //     //Toast.makeText(this, "------------notification arrives", Toast.LENGTH_SHORT).show();
             Logs.info(TAG+"_notification_data", "" + intent.getStringExtra("title")+"-----"+intent.getStringExtra("msg")+"----"+intent.getStringExtra("type")+"---"+intent.getStringExtra("date"));
            String title = intent.getStringExtra("title");
            String msg = intent.getStringExtra("msg");
            String type = intent.getStringExtra("type");
            String exp_date = intent.getStringExtra("exp_date");
            String notif_date = intent.getStringExtra("notif_date");
            String notif_time = intent.getStringExtra("notif_time");
             Logs.info(TAG+"_exp_date_notification",""+exp_date+"---------"+notif_date);
            mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
            int icon;
            if(type.equals("Water")) icon = R.drawable.ic_water_filter;
            else icon = R.drawable.ic_air_filter_whit;
            long when = System.currentTimeMillis();
            RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
            contentView.setImageViewResource(R.id.img_noti_id, icon);
            contentView.setTextViewText(R.id.txt_noti_title,title);
            long now = System.currentTimeMillis();
            long parsedMillis=0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat exp_sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                 parsedMillis = exp_sdf.parse(exp_date).getTime();
            } catch (ParseException e) {
                 Logs.error(TAG+"_NotificationAlarmSe",""+e.getMessage());
            }
            Logs.info(TAG+"parsemilli",""+parsedMillis);
            if(parsedMillis > now) {
                contentView.setTextViewText(R.id.txt_noti_description, "Your " + type + " Filter will be expire on " + exp_date);
            }
            else if(parsedMillis == now)
            {
                contentView.setTextViewText(R.id.txt_noti_description, "Your " + type + " Filter is expire on today");
            }
            else
            {
                contentView.setTextViewText(R.id.txt_noti_description, "Your " + type + " Filter was expire on " + exp_date);
            }
            Logs.info(TAG+"remoteview",""+parsedMillis);
            Intent notificationIntent = new Intent(this, LoginActivity.class);
            notificationIntent.putExtra("call_from","filter_notification");
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            android.support.v4.app.NotificationCompat.Builder mBuilder = new android.support.v4.app.NotificationCompat.Builder(
                    this)
                    .setSmallIcon(icon)
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setCustomContentView(contentView)
                    .setTicker("Filter Notification")
                    .setPriority(NotificationCompat.PRIORITY_MAX).setContentIntent(contentIntent);
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            long diff=parsedMillis-now;
             Logs.info(TAG+"_datedifferentce",""+diff+"time"+notif_time);
            try {
                Calendar c1 = Calendar.getInstance();
                c1.setTime(sdf.parse(notif_date));
                c1.set(Calendar.HOUR_OF_DAY,Integer.parseInt(notif_time));
                c1.set(Calendar.MINUTE,0);
                c1.set(Calendar.SECOND,0);
                Date expdate =c1.getTime() ;
                Calendar c = Calendar.getInstance();
                Date nowdate = c.getTime();
                 Logs.info(TAG+"_date_difference",expdate+"------------"+nowdate+"----------------------"+dateDifference(expdate,nowdate));
                  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                  {
                      int importance = NotificationManager.IMPORTANCE_HIGH;
                      NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                      notificationChannel.enableLights(true);
                      notificationChannel.setLightColor(Color.RED);
                      notificationChannel.enableVibration(true);
                      notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                      mManager.createNotificationChannel(notificationChannel);
                  }
                if (type.equals("Water")) mManager.notify(0, mBuilder.build());
                else mManager.notify(1, mBuilder.build());
            }
            catch (Exception ex)
            {
                 Logs.error(TAG+"_date_difference_error",""+ex.getMessage());
            }
        }
    }
    /**
     * get given date difference
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param startDate Date start date
     * @param endDate Date end date
     * @return long milisecond
     */
    private long dateDifference(Date startDate, Date endDate){
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();
        Logs.info(TAG+"_elapseDay",""+different);
        return different;
    }
    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}