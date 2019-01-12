package com.cloudsinc.welltekmobile.native_v2_welltek.services;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;

public class ScheduleClient_sleep {

	// The hook into our service
	private ScheduleService_sleep mBoundService;
	// The context to start the service in
	private Context mContext;
	// A flag if we are connected to the service or not
	private boolean mIsBound;

	public ScheduleClient_sleep(Context context) {
		mContext = context;
	}

	/**
	 * Call this to connect your activity to your service
	 */
	public void doBindService() {
		// Establish a connection with our service
		mContext.bindService(new Intent(mContext, ScheduleService_sleep.class), mConnection, Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	/**
	 * When you attempt to connect to the service, this connection will be called with the result.
	 * If we have successfully connected we instantiate our service object so that we can call methods on it.
	 */
	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			// This is called when the connection with our service has been established,
			// giving us the service object we can use to interact with our service.
			mBoundService = ((ScheduleService_sleep.ServiceBinder) service).getService();
		}

		public void onServiceDisconnected(ComponentName className) {
			mBoundService = null;
		}
	};

	/**
	 * Tell our service to set an alarm for the given date
	 * @param c a date to set the FilterNotification for
	 */
	public void setAlarmForNotification(Calendar c, String sleep_id, String sleep_room_name, String notif_type, int id, int notif_day_name,int sleep_mode){
		Log.i("ScheduleClient","mIsBound"+mIsBound+"  "+mBoundService);
		try{


			mBoundService.setAlarm(c,sleep_id,sleep_room_name, notif_type,id,notif_day_name, sleep_mode);
		}catch (Exception e){
			Log.e("ScheuleClientSetAlarm"+e.getMessage(),""+e.getStackTrace());
		}
	}

	/**
	 * When you have finished with the service call this method to stop it
	 * releasing your connection and resources
	 */
	public void doUnbindService() {
		if (mIsBound) {
			// Detach our existing connection.
			mContext.unbindService(mConnection);
			mIsBound = false;
		}
	}
}