package com.cloudsinc.welltekmobile.native_v2_welltek.receivers;



import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This broadcastreceiver trigger while network change
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class NetworkChangeReceiver extends BroadcastReceiver
{
    String TAG=this.getClass().getSimpleName();
    String devie_ssid="";
    String hub_ssid="no wifi";
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent)
    {
        try
        {
            if (isOnline(context)) {
                WifiManager wifiManager = (WifiManager) context.getSystemService (Context.WIFI_SERVICE);
                WifiInfo info = wifiManager.getConnectionInfo ();
                 devie_ssid  = info.getSSID();

                JSONArray jsonArray=App.getNetworkInfoJson().getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    if(jsonObject.has("CML_WIFI_DATA"))
                    {
                        hub_ssid=jsonObject.getJSONObject("CML_WIFI_DATA").has("CML_SSID")?jsonObject.getJSONObject("CML_WIFI_DATA").getString("CML_SSID"):"no wifi";
                    }
                }


            } else {
                if(!App.isNetwork_flag()) {
                   /* Intent i = new Intent(context.getApplicationContext(), MyAlertDialog.class);
                    i.putExtra("msg", "" + context.getResources().getString(R.string.network_change_str));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    Log.e(TAG, "Conectivity Failure !!! ");*/
                }
            }
        } catch (Exception e) {
            Log.e(TAG,""+e.getMessage());
        }
        finally {
            Log.e(TAG, "Online Connect Intenet "+hub_ssid+"----------------"+devie_ssid);
            String msg_title="";
            if(!devie_ssid.contains(hub_ssid)) {
                Log.e(TAG, "Online Connect Intenet "+hub_ssid+"----------------"+devie_ssid);
                if(!hub_ssid.equals("no wifi"))msg_title=context.getResources().getString(R.string.network_change_to_connect)+ " " +hub_ssid;
                else msg_title=context.getResources().getString(R.string.network_change_str) ;

                if(!App.isNetwork_flag()) {
                   /* Intent i = new Intent(context.getApplicationContext(), MyAlertDialog.class);
                    i.putExtra("msg", msg_title);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);*/
                }
            }
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
