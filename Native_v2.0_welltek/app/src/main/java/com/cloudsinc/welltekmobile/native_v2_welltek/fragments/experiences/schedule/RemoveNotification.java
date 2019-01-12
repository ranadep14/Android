package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.NotifyService;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.NotifyService_sleep;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RemoveNotification {

    private Context mcontext;
    private PrefManager spm;
    private PrefManager pref;
    private String TAG = RemoveNotification.this.getClass().getSimpleName();
    private static String ns = Context.NOTIFICATION_SERVICE;

    public static void removeAllDawnNotification(Context mcontext) {
        String dawn_sim_id = "";


        AlarmManager alarmManager = (AlarmManager) mcontext.getSystemService(Context.ALARM_SERVICE);
        NotificationManager nMgr = (NotificationManager) mcontext.getSystemService(ns);
        if(App.getSimulationData()!=null) {
            try {

                JSONArray jsonArray = App.getSimulationData().getJSONArray("data");
                Log.i("LogoutDeletegetDawnNtf", "" + jsonArray);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    dawn_sim_id = jsonObject.getString("CML_ID");

                    Logs.info("RemoveNotification", "LogoutDelete_dawn_simulation_id" + dawn_sim_id);


                    for (int day = 1; day <= 7; day++) {


                        int notif_day_name = day;

                        int dawn_id1 = new PrefManager(mcontext).getValueDawn(dawn_sim_id + "A" + "" + notif_day_name);

                        Log.i("LogoutDelete******-30  ", "" + dawn_id1);

                        nMgr.cancel(dawn_id1);
                        Intent myIntent = new Intent(mcontext, NotifyService.class);

                        PendingIntent pendingIntent = PendingIntent.getService(mcontext, dawn_id1, myIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmManager.cancel(pendingIntent);
                        int dawn_id2 = new PrefManager(mcontext).getValueDawn(dawn_sim_id + "B" + "" + notif_day_name);

                        Log.i("LogoutDelete******-30  ", "" + dawn_id2);

                        nMgr.cancel(dawn_id2);
                        Intent myIntent2 = new Intent(mcontext, NotifyService.class);

                        PendingIntent pendingIntent2 = PendingIntent.getService(mcontext, dawn_id2, myIntent2,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmManager.cancel(pendingIntent2);

                        new PrefManager(mcontext).removeKey(dawn_sim_id + "A" + "" + notif_day_name);
                        new PrefManager(mcontext).removeKey(dawn_sim_id + "B" + "" + notif_day_name);
                    }

                }

            } catch (JSONException ex) {
                Log.e("Logouteeeeerrrrrrrrr", "" + ex.getMessage());
            }
        }

    }
    public static void removeSpecificDawnNotification(Context mcontext, String dawn_obj_id){


        AlarmManager alarmManager = (AlarmManager) mcontext.getSystemService(Context.ALARM_SERVICE);
        NotificationManager nMgr = (NotificationManager) mcontext.getSystemService(ns);
        JSONArray notif_day = new JSONArray();
        Log.i("DawnPDeleteBtnSleepID",""+dawn_obj_id);
        try {
            JSONArray jsonArray = App.getSimulationData().getJSONArray("data");
            JSONObject resultedJsonObject=null;
            for (int i = 0; i <jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_ID").equals(dawn_obj_id))
                {
                    notif_day=getDawnNotifDay(dawn_obj_id);
                }
            }
            Logs.info("RemoveNotification","delete_dawn_simulation_id"+dawn_obj_id);
//Remove -30
            for(int day=0;day<notif_day.length();day++){
                int notif_day_int=0;
                notif_day_int=notif_day.getInt(day);
                int notif_day_name=notif_day_int+1;
                int dawn_id1 = new PrefManager(mcontext).getValueDawn(""+dawn_obj_id + "A"+""+notif_day_name);
                Log.i("Delete*********-30  ",""+dawn_id1);
                nMgr.cancel(dawn_id1);
                Intent myIntent = new Intent(mcontext, NotifyService.class);
                PendingIntent pendingIntent = PendingIntent.getService(mcontext, dawn_id1, myIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.cancel(pendingIntent);
                new PrefManager(mcontext).removeKey(""+dawn_obj_id + "A"+""+notif_day_name);
            }
//Remove -2
            for(int day=0;day<notif_day.length();day++) {
                int notif_day_int=0;
                notif_day_int=notif_day.getInt(day);
                int notif_day_name=notif_day_int+1;
                int dawn_id = new PrefManager(mcontext).getValueDawn(""+dawn_obj_id + "B"+""+notif_day_name);
                Log.i("Delete*********-2  ",""+dawn_id);
                nMgr.cancel(dawn_id);
                Intent myIntent1 = new Intent(mcontext, NotifyService.class);
                PendingIntent pendingIntent1 = PendingIntent.getService(mcontext, dawn_id, myIntent1,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.cancel(pendingIntent1);
                new PrefManager(mcontext).removeKey(""+dawn_obj_id+ "B"+""+notif_day_name);
            }
            ////Toast.makeText(this, "insideRemoveAllRemoved", Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Log.e("eeeeerrrrrrrrrDawnPopup",""+ex.getMessage());
        }
    }

    public static JSONArray getDawnNotifDay(String dawn_id) {
        String dawn_id_selected=dawn_id;
        JSONArray notif_day = new JSONArray();
        try {
            if (App.getSimulationData() != null && !dawn_id_selected.equals("")) {
                JSONArray jsonArrayy = App.getSimulationData().getJSONArray("data");
                for (int i = 0; i < jsonArrayy.length(); i++) {
                    JSONObject jsonObject = jsonArrayy.getJSONObject(i);
                    if (jsonObject.getString("CML_ID").equals(dawn_id_selected)) {
                        //  dawn_obj = jsonObject;
                        notif_day=jsonObject.getJSONArray("days");
                        ////Toast.makeText(this, "insideRemoveDays", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        catch(Exception e){
            Log.e("RemoveNotification","error_mainAity_DayArray"+e.getMessage());
        }
        return notif_day;
    }

    public static void removeAllSleepNotification(Context mcontext) {
        try {

            String sleep_sim_id="";

            AlarmManager alarmManager = (AlarmManager) mcontext.getSystemService(Context.ALARM_SERVICE);
            NotificationManager nMgr = (NotificationManager) mcontext.getSystemService(ns);
            if(App.getSleepSimulationData()!=null) {
                try {
//Change here ******************
                    JSONArray jsonArray = App.getSleepSimulationData().getJSONArray("data");
                    Log.i("LogouteletegetSleepNtf", "" + jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        sleep_sim_id = jsonObject.getString("CML_ID");

                        Logs.info("RemoveNotification", "Logoutdelete_Sleep_simulation_id" + sleep_sim_id);


                        //Remove +15 1539592111359

                        for (int day = 1; day <= 7; day++) {


                            int notif_day_name = day;

                            int dawn_id1 = new PrefManager(mcontext).getValueSleep(sleep_sim_id + "C" + "" + notif_day_name);

                            Log.i("LogoutDelete******+15  ", "" + dawn_id1);

                            nMgr.cancel(dawn_id1);
                            Intent myIntent = new Intent(mcontext, NotifyService_sleep.class);

                            PendingIntent pendingIntent = PendingIntent.getService(mcontext, dawn_id1, myIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);

                            alarmManager.cancel(pendingIntent);
                            new PrefManager(mcontext).removeKey(sleep_sim_id + "C" + "" + notif_day_name);
                            Log.i("JDDDDDDSleepDelete: " + sleep_sim_id + "C" + "" + notif_day_name, "***** " + dawn_id1);


                        }
                    }

                } catch (JSONException ex) {
                    Log.e("LogouteeeeerrrrrrrSleep", "" + ex.getMessage());
                }
            }

        }catch (Exception e) {
            Log.e("LogoutNotificationSleep",""+e.getMessage());
        }
    }
    public static void removeSpecificSleepNotification(Context mcontext, String sleep_obj_id) {
        AlarmManager alarmManager = (AlarmManager) mcontext.getSystemService(Context.ALARM_SERVICE);
        NotificationManager nMgr = (NotificationManager) mcontext.getSystemService(ns);
        Log.i("SleepPDeleteBtnSleepID",""+sleep_obj_id);
        JSONArray notif_day = new JSONArray();
        try {

            JSONArray jsonArray = App.getSleepSimulationData().getJSONArray("data");
            for (int i = 0; i <jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_ID").equals(sleep_obj_id))
                {

                    notif_day=getSleepNotifDay(sleep_obj_id);

                }
            }

            Log.e("SleepPdelete_Sleep_sim",""+sleep_obj_id);


            if(notif_day.length()==0)notif_day=new JSONArray("[0,1,2,3,4,5,6]");
            System.out.println("ddddddddddddddddd"+notif_day);
            for(int day=0;day<notif_day.length();day++){


                int notif_day_int=0;

                notif_day_int=notif_day.getInt(day);
                int notif_day_name=notif_day_int+1;


                int dawn_id1 =new PrefManager(mcontext).getValueDawn(sleep_obj_id + "C"+""+notif_day_name);

                Log.i("SleepPDelete*******+15 ",""+dawn_id1);

                nMgr.cancel(dawn_id1);
                Intent myIntent = new Intent(mcontext, NotifyService_sleep.class);

                PendingIntent pendingIntent = PendingIntent.getService(mcontext, dawn_id1, myIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
//                Log.i("PendingIntent-30",""+pendingIntent.describeContents());

                alarmManager.cancel(pendingIntent);

                new PrefManager(mcontext).removeKey(sleep_obj_id + "C"+""+notif_day_name);
                ////Toast.makeText(this, "insideRemoveAllRemoved", Toast.LENGTH_SHORT).show();


            }


        }catch (Exception ex){
            Log.e("SleepPeeeeerrrrrrrrr",""+ex.getMessage());
        }




    }
    public static JSONArray getSleepNotifDay(String dawn_id) {

        String sleep_id_selected=dawn_id;

        JSONArray notif_day = new JSONArray();



        try {

            System.out.println("SleepP*********************" + sleep_id_selected);
            if (App.getSleepSimulationData() != null && !sleep_id_selected.equals("")) {
                JSONArray jsonArrayy = App.getSleepSimulationData().getJSONArray("data");
                Log.i("SleepPjsonArayy", "" + jsonArrayy);

                for (int i = 0; i < jsonArrayy.length(); i++) {
                    JSONObject jsonObject = jsonArrayy.getJSONObject(i);


                    if (jsonObject.getString("CML_ID").equals(sleep_id_selected)) {
                        //  dawn_obj = jsonObject;
                        notif_day=jsonObject.getJSONArray("days");
                        ////Toast.makeText(this, "insideRemoveDays", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }
        catch(Exception e){
            Log.e("SleepPerror_DayArray",""+e.getMessage());
        }
        return notif_day;
    }


}