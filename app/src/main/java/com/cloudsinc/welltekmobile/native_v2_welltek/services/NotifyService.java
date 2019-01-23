package com.cloudsinc.welltekmobile.native_v2_welltek.services;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.DawnSimulationPopup;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import java.util.Calendar;
/**
 * The NotifyService is a background service used for scheduling Dawn Notification
 *
 * @author  Jaid Shaikh
 * @version 1.0
 */
public class NotifyService extends Service{
    PrefManager spm;
    private ScheduleClient scheduleClient;
    private String dawn_type_id;
    private  Calendar notify_date;
    private  int notify_day;
    private  int notify_id;
    private  int notify_Hrs;
    private  int notify_Min;
    private String notif_type;
    private String dawn_obj_id;
    private String dawn_room_name;
    int importance = NotificationManager.IMPORTANCE_HIGH;

    public class ServiceBinder extends Binder {
        NotifyService getService() {
            return NotifyService.this;
        }
    }
    // Unique id to identify the FilterNotification.
    public int dawn_NOTIFICATION_id ;
    int dawn_mode;
    public static final String NOTIFICATION_CHANNEL_ID="com.cloudsinc.welltekmobile_native";
    // Name of an intent extra we can use to identify if this service was started to create a FilterNotification
    public static final String INTENT_NOTIFY = "com.cloudsinc.welltekmobile";
    // The system FilterNotification manager
    private NotificationManager mNM;
    @Override
    public void onCreate() {
        Log.i("NotifyService", "onCreate()");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        spm=new PrefManager(this.getApplicationContext());
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        dawn_mode=intent.getIntExtra("mode",-1);
        notify_Hrs=intent.getIntExtra("notif_Hrs",-1);
        notify_Min=intent.getIntExtra("notif_Min",-1);
        notify_day=intent.getIntExtra("notif_day_name",-1);
        notify_id=intent.getIntExtra("id",-1);
        notif_type=intent.getStringExtra("notif_type");
        dawn_room_name=intent.getStringExtra("dawn_room_name");
        Log.i("Testtttt_calender_day",""+notify_day);
        Log.i("Testtttt_mode",""+dawn_mode);
        Log.i("Testtttt_type",""+notif_type);
        Log.i("Testtttt_room",""+dawn_room_name);
        Log.i("Testtttt","notif_typeeee"+""+intent.getIntExtra("notif_day_name",-1)+""+""+intent.getStringExtra("notif_type"));
        dawn_obj_id=intent.getStringExtra("dawn_id");
        System.out.println("ccccccccccccccccccaaaaaaaaaaaaaaaaaa"+dawn_obj_id);
        Log.i("Testttt_dawn_obj_id",""+dawn_obj_id);
        dawn_type_id=intent.getStringExtra("notif_type").toString();
        Log.i("Testttttt",""+dawn_type_id);
        int dawn_id = new PrefManager(this.getApplicationContext()).getValueDawn("" + intent.getStringExtra("dawn_id")+"A"+""+intent.getIntExtra("notif_day_name",-1));
        int dawn_id1 =new PrefManager(this.getApplicationContext()).getValueDawn("" +intent.getStringExtra("dawn_id") + "B"+""+intent.getIntExtra("notif_day_name",-1));
        Boolean type_of_notif = (intent.getStringExtra("notif_type").equals("A"));
        Log.i("Typeeeeeee",""+type_of_notif);
        Log.i("Testttt_notif_type",""+intent.getStringExtra("notif_type"));
        Log.i("Testttt_dawn_NOTIF_id",""+dawn_NOTIFICATION_id);
        if(dawn_type_id.equals("A")){
            dawn_NOTIFICATION_id=dawn_id; //dawn#5464646546  A678
            Log.i("onStartCmdiiiiiii_id_A",""+dawn_NOTIFICATION_id);
        }else if(dawn_type_id.equals("B")){
            mNM.cancel(dawn_id);
            dawn_NOTIFICATION_id=dawn_id1;
            Log.i("onStartCmdiiiiiii_id_B",""+dawn_NOTIFICATION_id);
        }else {
            Log.i("onStartCmdiii_Unwanted",""+dawn_NOTIFICATION_id);
        }
        Log.i("onStartCmdiiiiid_passed",""+dawn_NOTIFICATION_id);
        if (intent.getBooleanExtra(INTENT_NOTIFY, false))
            showNotification(dawn_obj_id, intent.getStringExtra("dawn_room_name"),dawn_NOTIFICATION_id,notif_type,notify_id,notify_day,dawn_mode);
        return START_NOT_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    // This is the object that receives interactions from clients
    private final IBinder mBinder = new ServiceBinder();
    /**
     * Creates a FilterNotification and shows it in the OS drag-down status bar
     */
    private void showNotification(String dawn_id, String dawn_room_name,  int dawn_NOTIFICATION_id,String dawn_type_id,int notify_id,int notify_day,int notify_mode) {
        int notify_id_pass=notify_id;
        int notify_day_pass=notify_day;
        int notify_mode_pass=notify_mode;
        String dawn_type_pass=dawn_type_id;
        String room_name=dawn_room_name;

        CharSequence title = "Dawn Simulation";
        // This is the icon to use on the FilterNotification
        int icon = R.drawable.notification_logo_photo;
        // This is the scrolling text of the FilterNotification
        CharSequence text = ""+room_name;
        // What time to show on the FilterNotification
        long time = System.currentTimeMillis();
        Notification notification = new Notification(icon, text, time);
        PackageManager pm = getApplicationContext().getPackageManager();
        PrefManager spm = new PrefManager(getApplicationContext());
        spm.setValue("Simulation_Set", "1");
        spm.setValue("dawn_id_notif",""+dawn_id);
        Log.i("dawn_id_notif",""+dawn_id);
        Intent intenttt = new Intent(getApplicationContext(), DawnSimulationPopup.class);
        intenttt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.i("Intentt_Details","dawn_NOTIFICATION_id"+dawn_NOTIFICATION_id);
        Log.i("Intentt_Details","dawn_id"+dawn_id);
        Log.i("Intentt_Details","dawn_obj_id"+dawn_obj_id);
        Log.i("Intentt_Details","notify_id"+notify_id);
        Log.i("Intentt_Details","notif_type"+notif_type);
        Log.i("Intentt_Details","notif_type"+notif_type);
        Log.i("Intentt_Details","notif_room_name"+room_name);
        intenttt.putExtra("NotificationMessage", dawn_NOTIFICATION_id);//value :5677
        intenttt.putExtra("dawn_id", dawn_id);//key
        intenttt.putExtra("dawn_obj_id", dawn_obj_id);
        intenttt.putExtra("notify_id",notify_id);
        intenttt.putExtra("notif_type",dawn_type_id);
        intenttt.putExtra("dawn_room_name",room_name);
        Log.e("NotificationMsg_param",""+dawn_NOTIFICATION_id+dawn_id+notify_id+dawn_type_id);
        ///////////

        Intent deactivate_Alarm = new Intent(this, MainActivity.class);
        deactivate_Alarm.setAction("deactivate");
        deactivate_Alarm.putExtra("toastMessage", "deactivate");
        deactivate_Alarm.putExtra("dawn_obj_id", dawn_obj_id);

        PendingIntent deactivate_Dawn = PendingIntent.getActivity(getApplicationContext(), notify_id, deactivate_Alarm, 0);

        Intent snooze_Alarm = new Intent(this, MainActivity.class);
        snooze_Alarm.setAction("snooze");
        snooze_Alarm.putExtra("toastMessage", "snooze");
        snooze_Alarm.putExtra("dawn_obj_id", dawn_obj_id);

        PendingIntent snooze_Dawn = PendingIntent.getActivity(getApplicationContext(), notify_id, snooze_Alarm, 0);

        Intent awake_Alarm = new Intent(this, MainActivity.class);
        awake_Alarm.setAction("awake");
        awake_Alarm.putExtra("toastMessage", "awake");
        awake_Alarm.putExtra("dawn_obj_id", dawn_obj_id);
        PendingIntent awake_Dawn = PendingIntent.getActivity(getApplicationContext(), notify_id, awake_Alarm, 0);



        /////////////
        // Notification Builder Pending Intent
        PendingIntent contentIntent =
                PendingIntent.getActivity(getApplicationContext(), notify_id, intenttt, 0);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this,NOTIFICATION_CHANNEL_ID);
        notification = builder.setContentIntent(contentIntent)
                .setSmallIcon(icon).setTicker(text).setWhen(time)
                .setAutoCancel(true).setContentTitle(title)
                .setContentText(text)
                .setSound(soundUri)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_MAX)
                .addAction(R.drawable.ic_snooze_black_24dp, "Snooze", snooze_Dawn)
                .addAction(R.drawable.ic_alarm_on_black_24dp, "Awake", awake_Dawn)
                .addAction(R.drawable.ic_alarm_off_black_24dp, "Sleep", deactivate_Dawn)
                .setChannelId(NOTIFICATION_CHANNEL_ID).build();
        //Adding channel id for Oreo and above
        startActivity(intenttt);





        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Channel One", importance);
            mNM.createNotificationChannel(notificationChannel);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GRAY);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        }
//Rescheduling for next repeating day
        try {
            if (dawn_mode == 1) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, notify_Hrs);
                calendar.set(Calendar.MINUTE, notify_Min);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                scheduleClient = new ScheduleClient(this.getBaseContext());
                scheduleClient.doBindService();
                scheduleClient.setAlarmForNotification(calendar, "" + dawn_NOTIFICATION_id, "" + dawn_room_name, dawn_type_pass, notify_id_pass, notify_day_pass, notify_mode_pass);
            } else {
                Log.i("Notify_service", "Non-repeating");
            }
            mNM.notify(dawn_NOTIFICATION_id, notification);
        }catch (Exception e){
            Log.e("Error_NotifyService",""+e.getMessage());
        }

    }
}
