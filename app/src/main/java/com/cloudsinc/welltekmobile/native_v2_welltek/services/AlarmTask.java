package com.cloudsinc.welltekmobile.native_v2_welltek.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;

import java.util.Calendar;


class AlarmTask implements Runnable {
	// The date selected for the alarm
	private final Calendar date;
	// The android system alarm manager
	private final AlarmManager am;
	// Your context to retrieve the alarm manager from
	private final Context context;
	private final String dawn_id;
	private final String dawn_room_name;
	private final String notif_type;
	private final int  id;
	private final int  notif_day_name;
	private final int  mode;
	JSONArray notif_day = new JSONArray();


	public AlarmTask(Context context, Calendar date,String dawn_id, String dawn_room_name, String notif_type,int id,int notif_day_name,int mode) {
		this.context = context;
		this.am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		this.date = date;
		this.dawn_id=dawn_id;
		this.id=id;
		this.mode=mode;
		this.notif_day_name=notif_day_name;
		this.notif_type=notif_type;
		this.dawn_room_name=dawn_room_name;
		this.notif_day=notif_day;

	}

	@Override
	public void run() {
		long time_to_set;
		int day_today;
		int diff;
		int multiply_int;
		Log.i("AlarmTask_passedDate","run(): "+date.toString());

		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,date.get(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE,date.get(Calendar.MINUTE));
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		calendar.set(Calendar.DAY_OF_WEEK,notif_day_name);
		System.out.println("cccccccccccccccccc"+dawn_id);
		Log.i("Intent_Details","dawn_id"+dawn_id);
		Log.i("Intent_Details","Notify_Calender"+date);
		Log.i("Intent_Details","id"+id);
		Log.i("Intent_Details","notif_day_name"+notif_day_name);
		Log.i("Intent_Details","dawn_room_name"+dawn_room_name);
		Log.i("Intent_Details","notif_type"+notif_type);
		Log.i("Intent_Details","mode"+mode);
		Log.i("Intent_Details","notif_Hrs"+calendar.get(Calendar.HOUR_OF_DAY));
		Log.i("Intent_Details","notif_Min"+calendar.get(Calendar.MINUTE));

		Log.i("AlarmTask_CalenderDate","run(): "+calendar.toString());

		// Request to start are service when the alarm date is upon us
		// We don't start an activity as we just want to pop up a FilterNotification into the system bar not a full activity
		Intent intent = new Intent(context, NotifyService.class);
		intent.putExtra(NotifyService.INTENT_NOTIFY, true);
		intent.putExtra("dawn_id", dawn_id);
		intent.putExtra("Notify_Calender",date);
		intent.putExtra("id", id);
		intent.putExtra("notif_day_name", notif_day_name);
		intent.putExtra("dawn_room_name", dawn_room_name);
		intent.putExtra("notif_type", notif_type);
		intent.putExtra("mode", mode);
		intent.putExtra("notif_Hrs",calendar.get(Calendar.HOUR_OF_DAY));
		intent.putExtra("notif_Min",calendar.get(Calendar.MINUTE));


		PendingIntent pendingIntent = PendingIntent.getService(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//		pendingIntent.getCreatorUid()
		Log.i("AlarmTaskIntent",""+id);



		if((System.currentTimeMillis()-calendar.getTimeInMillis()>0)){
			day_today=7;
			Log.i("AlarmTask_day_today",""+day_today);
		}else{
			day_today=0;
			Log.i("AlarmTask_else_dayToday",""+day_today);

		}

		Log.i("AlarmTask_Cal.DayOfWeek",""+calendar.get(Calendar.DAY_OF_WEEK));
		Log.i("AlarmTask_notif_dayname",""+notif_day_name);

		if(calendar.get(Calendar.DAY_OF_WEEK)==notif_day_name){
			time_to_set = calendar.getTimeInMillis() + (86400000 * day_today);

		}else {

			diff=notif_day_name-Calendar.DAY_OF_WEEK;

			if(diff>0){
				multiply_int=diff;


			}else {
				multiply_int=7+diff;
			}
			Log.i("Multily",""+multiply_int);



			time_to_set = calendar.getTimeInMillis() + (86400000 * multiply_int);
		}
		Log.i("AlaramTask_calenderS",""+calendar.toString());
		Log.i("AlaramTask_calender",""+calendar);

		Log.i("TimeToSet",""+time_to_set);
		am.setExact(AlarmManager.RTC_WAKEUP,time_to_set,pendingIntent);





//

	}


}
