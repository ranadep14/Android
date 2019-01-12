package com.cloudsinc.welltekmobile.native_v2_welltek.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;

import rx.Observable;
import rx.Observer;

/**
 * Created by developers on 17/9/18.
 */

public class SheduleNotificationOnBoot extends Service {

    String TAG=""+this.getClass().getSimpleName();
    PrefManager spm;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logs.info(TAG,"i m in start service");
        //Toast.makeText(this, "i m in start service", Toast.LENGTH_SHORT).show();
        try {
            spm = new PrefManager(this);
        }
        catch (Exception ex)
        {
            Logs.error(TAG,"error........."+ex.getMessage());
        }
        //TODO do something useful

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        //Toast.makeText(this, "i m in on bind", Toast.LENGTH_SHORT).show();
        return null;
    }

}