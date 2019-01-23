package com.cloudsinc.welltekmobile.native_v2_welltek.utils;

import android.app.Activity;
import android.app.smdt.SmdtManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;

import com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
/**
 * Created by developers on 24/10/18.
 */
public class UserInteractionSleep {

    String TAG=this.getClass().getSimpleName();
    static Runnable runnable2;
    static Handler handler2;
    private static Observable<String> mobservable;
    private static Observer<String> myObserver;

    public static void userInteract(final Context mcontext)
    {

            Logs.info("UserInteractionSleep","----in user interaction"+mcontext.getClass().getSimpleName());
            stopHandler();//stop first and then start
            startHandler(mcontext);

    }


    public static void siboSleepChecking(final Context mcontext)
    {
        Logs.info("UserInteractionSleep","----in user check"+mcontext.getClass().getSimpleName());
        stopHandler();//stop first and then start
        startHandler(mcontext);
    }
    public static void stopHandler() {
        Logs.info("UserInteractionSleep","----in user stop");
        if(handler2!=null) {
            handler2.removeCallbacks(runnable2);
            handler2=null;
        }
    }
    public static void startHandler(final Context mcontext) {
        try {
            if(handler2==null) {
                runnable2 = new Runnable() {
                    @Override
                    public void run() {
                        if (App.getMain_activity_subcriber() != null) {
                            Observer<String> myObserver  = App.getMain_activity_subcriber().getObserver();
                            App.setSubcription(Observable.just("lock_app").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(myObserver));
                        }
                    }
                };
                handler2 = new Handler();
            }

           } catch (Exception ex) {
               Logs.error("UserInteractionSleep", "------------sibo texting exception" + ex.getMessage());
        }

       if(handler2!=null) handler2.postDelayed(runnable2, 5*60*1000); //for 5 minutes
    }
}
