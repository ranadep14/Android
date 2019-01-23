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
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.SleepSimulationPopup;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class NotifyService_sleep extends Service{

	PrefManager spm;
	private ScheduleClient scheduleClient;
	private String sleep_type_id;
	private  int notify_day;
	private  int notify_id;
	private  int notify_Hrs;
	private  int notify_Min;
	private String notif_type;
	private String sleep_obj_id;
	public class ServiceBinder extends Binder {
		NotifyService_sleep getService() {
			return NotifyService_sleep.this;
		}
	}

	// Unique id to identify the FilterNotification.
	public static int sleep_NOTIFICATION_id ;
	int sleep_mode;
	public static final String NOTIFICATION_CHANNEL_ID="100001";

	// Name of an intent extra we can use to identify if this service was started to create a FilterNotification
	public static final String INTENT_NOTIFY = "com.cloudsinc.welltekmobile";
	// The system FilterNotification manager
	private NotificationManager mNM;




	@Override
	public void onCreate() {
		Log.i("NotifyServiceSleep", "onCreate()");
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		spm=new PrefManager(this.getApplicationContext());


	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {


		sleep_mode=intent.getIntExtra("mode",-1);
		notify_Hrs=intent.getIntExtra("notif_Hrs",-1);
		notify_Min=intent.getIntExtra("notif_Min",-1);
		notify_day=intent.getIntExtra("notif_day_name",-1);
		notify_id=intent.getIntExtra("id",-1);
		notif_type=intent.getStringExtra("notif_type");
		Log.i("Testtttt_calender_day",""+notify_day);
		Log.i("Testtttt_mode",""+sleep_mode);
		Log.i("Testtttt_type",""+notif_type);
		Log.i("Testtttt","notif_typeeee"+""+intent.getIntExtra("notif_day_name",-1)+""+""+intent.getStringExtra("notif_type"));
		sleep_obj_id=intent.getStringExtra("sleep_id");
		System.out.println("Sleepccccccccccccccaaaaaaaaaaaaaaaaaa"+sleep_obj_id);
		Log.i("Testttt_sleep_obj_id",""+sleep_obj_id);
		sleep_type_id=intent.getStringExtra("notif_type").toString();
		Log.i("Testttttt",""+sleep_type_id);
		int sleep_id = new PrefManager(this.getApplicationContext()).getValueSleep
				("" + intent.getStringExtra("sleep_id")+"C"+""+intent.getIntExtra("notif_day_name",-1));


		Log.i("Testttt_notif_type",""+intent.getStringExtra("notif_type"));
		Log.i("Testttt_Sleep_NOTIF_id",""+sleep_NOTIFICATION_id);
		if(sleep_type_id.equals("C")){
			sleep_NOTIFICATION_id=sleep_id; //sleep#5464646546  A678
			Log.i("onStartCmdiiiiiii_id_C",""+sleep_NOTIFICATION_id);
		}else {
			Log.i("onStartCmdiiS_Unwanted",""+sleep_NOTIFICATION_id);
		}
		Log.i("onStartCmdiiiiid_passed",""+sleep_NOTIFICATION_id);
		if (intent.getBooleanExtra(INTENT_NOTIFY, false))
			showNotification(sleep_obj_id, intent.getStringExtra("sleep_room_name"),sleep_NOTIFICATION_id,notif_type,notify_id,notify_day,sleep_mode);


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

	private void showNotification(String sleep_id, String sleep_room_name,  int sleep_NOTIFICATION_id,String sleep_type_id,int notify_id,int notify_day,int notify_mode) {
		int notify_id_pass = notify_id;
		int notify_day_pass = notify_day;
		int notify_mode_pass = notify_mode;
		String sleep_type_pass = sleep_type_id;
		String room_name = sleep_room_name;
		Bundle bundle = App.getTemp_bundle();
		CharSequence title = "Sleep Simulation";
		// This is the icon to use on the FilterNotification
		int icon = R.drawable.notification_logo_photo;
		// This is the scrolling text of the FilterNotification
		CharSequence text = "" + room_name;
		// What time to show on the FilterNotification
		long time = System.currentTimeMillis();
		Notification notification = new Notification(icon, text, time);
		PackageManager pm = getApplicationContext().getPackageManager();
		PrefManager spm = new PrefManager(getApplicationContext());
		Log.i("sleep_id_notif", "" + sleep_id);
		Intent intenttt = new Intent(getApplicationContext(), SleepSimulationPopup.class);
		intenttt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Log.i("Intentt_Details", "sleep_NOTIFICATION_id" + sleep_NOTIFICATION_id);
		Log.i("Intentt_Details", "sleep_id" + sleep_id);
		Log.i("Intentt_Details", "sleep_obj_id" + sleep_obj_id);
		Log.i("Intentt_Details", "notify_id" + notify_id);
		Log.i("Intentt_Details", "notif_type" + notif_type);
		Log.i("Intentt_Details", "notif_type" + notif_type);
		intenttt.putExtra("NotificationMessage", sleep_NOTIFICATION_id);//value :5677
		intenttt.putExtra("sleep_id", sleep_id);//key
		intenttt.putExtra("sleep_obj_id", sleep_obj_id);
		intenttt.putExtra("notify_id", notify_id);
		intenttt.putExtra("notif_type", sleep_type_id);
		Log.e("NotificationMsg_param", "" + sleep_NOTIFICATION_id + sleep_id + notify_id + sleep_type_id);
		/*intenttt.putExtra("STRING_I_NEED", "open_Add_Wake");
		App.setCallfrom("open_Add_Wake");*/
		Intent sleep_ok = new Intent(this, MainActivity.class);
		sleep_ok.setAction("snooze");
		sleep_ok.putExtra("toastMessage", "sleep_ok");
		sleep_ok.putExtra("sleep_obj_id", sleep_obj_id);
		sleep_ok.putExtra("sleep_room_id", getSleepRoomId());
		PendingIntent slee_ok = PendingIntent.getActivity(getApplicationContext(), notify_id, sleep_ok, 0);
		Intent sleep_cancle = new Intent(this, MainActivity.class);
		sleep_cancle.setAction("snooze");
		sleep_cancle.putExtra("toastMessage", "sleep_cancle");
		sleep_cancle.putExtra("sleep_obj_id", sleep_obj_id);
		sleep_cancle.putExtra("sleep_room_id", getSleepRoomId());
		PendingIntent slee_cancle = PendingIntent.getActivity(getApplicationContext(), notify_id, sleep_cancle, 0);
		PendingIntent contentIntent =
				PendingIntent.getActivity(getApplicationContext(), notify_id, intenttt, 0);
		startActivity(intenttt);
		Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this, NOTIFICATION_CHANNEL_ID);
		builder.setChannelId(NOTIFICATION_CHANNEL_ID);
		notification = builder.setContentIntent(contentIntent)
				.setSmallIcon(icon).setTicker(text).setWhen(time)
				.setAutoCancel(true).setContentTitle(title)
				.setContentText(text)
				.setSound(soundUri)
				.setDefaults(Notification.DEFAULT_ALL)
				.setPriority(Notification.PRIORITY_MAX)
				.addAction(R.drawable.ic_alarm_off_black_24dp, "OK", slee_ok)
				.addAction(R.drawable.ic_alarm_off_black_24dp, "Cancel", slee_cancle)
/*          .addAction(R.drawable.ic_popup_reminder, "Snooze", contentIntent)
            .addAction(R.drawable.ic_dialog_alert, "Deactivate", contentIntent)
            .addAction(R.drawable.ic_dialog_alert, "I'm Awake", contentIntent)
*/
				.build();
		//Adding channel id for Oreo and above
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
			int importance = NotificationManager.IMPORTANCE_HIGH;
			NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
			notificationChannel.enableLights(true);
			notificationChannel.setLightColor(Color.GRAY);
			notificationChannel.enableVibration(true);
			notificationChannel.setShowBadge(true);
			notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
			notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
			assert mNM != null;
			builder.setChannelId(NOTIFICATION_CHANNEL_ID);
			mNM.createNotificationChannel(notificationChannel);
		}
//Rescheduling for next repeating day
		try {
			if (sleep_mode == 1) {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, notify_Hrs);
				calendar.set(Calendar.MINUTE, notify_Min);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				scheduleClient = new ScheduleClient(this.getBaseContext());
				scheduleClient.doBindService();
				scheduleClient.setAlarmForNotification(calendar, "" + sleep_NOTIFICATION_id, "" + room_name, sleep_type_pass, notify_id_pass, notify_day_pass, notify_mode_pass);
			} else {
				Log.i("Sleep_Notify_service", "Non-repeating");
			}
			mNM.notify(sleep_NOTIFICATION_id, notification);
		} catch (Exception e) {
			Log.e("Error_NotifyService", "" + e.getMessage());
		}
	}
	 private String getSleepRoomId() {
		String room_id="";
		try {
			if(App.getSleepSimulationData()!=null&&!sleep_obj_id.equals("")) {
				JSONArray jsonArrayy = App.getSleepSimulationData().getJSONArray("data");
				for (int i = 0; i < jsonArrayy.length(); i++) {
					JSONObject jsonObject = jsonArrayy.getJSONObject(i);
					if (jsonObject.getString("CML_ID").equals(sleep_obj_id)) {
						room_id = jsonObject.getString("room");
					}
				}
			}
		}
		catch (Exception ex)
		{
			Logs.error("NotifyService_Sleep","sleep_room_id....."+ex.getMessage());
		}
		return room_id;
	}


}