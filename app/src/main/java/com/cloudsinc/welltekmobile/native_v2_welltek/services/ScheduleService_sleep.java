package com.cloudsinc.welltekmobile.native_v2_welltek.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;


public class ScheduleService_sleep extends Service {

	/**
	 * Class for clients to access
	 */
	public class ServiceBinder extends Binder {
		ScheduleService_sleep getService() {
			return ScheduleService_sleep.this;
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("WaterAirScheduleService", "Received start id " + startId + ": " + intent);

		// We want this service to continue running until it is explicitly stopped, so return sticky.
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	// This is the object that receives interactions from clients. See
	private final IBinder mBinder = new ServiceBinder();

	/**
	 * Show an alarm for a certain date when the alarm is called it will pop up a FilterNotification
	 */
	public void setAlarm(Calendar c, String sleep_id, String sleep_room_name, String notif_type, int id, int notif_day_name,int mode) {
		// This starts a new thread to set the alarm
		// You want to push off your tasks onto a new thread to free up the UI to carry on responding
		new AlarmTask_sleep(this, c,sleep_id,sleep_room_name,notif_type,id,notif_day_name,mode).run();
	}
}