package com.cloudsinc.welltekmobile.native_v2_welltek.airwaternotification;
/**
 * Created by developers on 2/3/18.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * @author nikhil
 *         <p/>
 *         Broadcast reciever, starts when the device gets starts.
 *         Start your repeating alarm here.
 */
public class DeviceBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
           /* if(App.getSocket()==null)
            {
                final SocketConnectionTest sct=new SocketConnectionTest();
               // sct.connect("http://10.14.15.118:3001", App.getSocket(),context);
                // sct.connect("http://192.168.1.30:3001", App.getSocket(),getApplicationContext());
                sct.connect("http://"+new PrefManager(context).getValue("ip_address")+":3001",App.getSocket(),context);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("SocketConnectingError","sfsggrgrg"+App.getSocket());
                    if(App.getSocket()!=null) {
                        emitNotification("Water");
                        emitNotification("Air");
                    }
                }
            }, 800);*/
        }
    }
}
