package com.cloudsinc.welltekmobile.native_v2_welltek.receivers;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
/**
 * This class containing functionality playstore referrer code
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class ReferrerCatcher extends BroadcastReceiver {
    String referrer="";
    @Override
    public void onReceive(Context context, Intent intent) {
        referrer = "";
        Bundle extras = intent.getExtras();
        if(extras != null){
            referrer = extras.getString("referrer");
        }
        Log.w("REFERRER","Referer is: "+referrer);
    }
}
