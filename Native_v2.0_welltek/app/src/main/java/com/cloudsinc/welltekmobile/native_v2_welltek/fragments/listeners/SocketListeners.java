package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.listeners;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.ConnectionLostActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.airwaternotification.NotificationReceiver;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.ScheduleClient;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.AlarmUtils;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.HomeHelthCall;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.NotificationSettingFragment.emitNotification;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.DAWN_NOTIFICATION;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.DEVICES_BY_ROOM;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.FAVORITES;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.GROUPS;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.GROUPS_BY_ROOM;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.HVAC_PROVISIONED;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.NETWORK_INFO;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.PLAYLISTS;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.PROVISIONAL_DEVICES;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.ROOMS;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.ROOMS_DEVICES;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.SET;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.SLEEP_NOTIFICATION;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.UPDATE;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.ZONES;
/**
 * The SocketListeners is a listener to listener all socket event
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class SocketListeners implements Emitter.Listener {
    Socket socket;
    App app;
    String action="";
    Context mcotext;
    Socket msocket;
    PrefManager pref;
    ArrayList<String> scenesList=new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    private static ScheduleClient scheduleClient;
    String TAG="SocketListener";
    Observable<String> observable;
    Observer<String> observer;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    static SimpleDateFormat exp_sdf = new SimpleDateFormat("dd-MM-yyyy");
    static SimpleDateFormat date_time_sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    ArrayList<String> musiclist=new ArrayList<>();
    public SocketListeners(String action, Context mcontext, Socket msocket)
    {
        Logs.info(TAG+"_WhereIM","I m in Listener");
        app =(App)mcontext;
        this.action=action;
        mcotext=mcontext;
        this.msocket=msocket;
        pref=new PrefManager(mcotext);
        pref.setValue("hub_connect","0");
        scheduleClient = new ScheduleClient(mcontext);
        scheduleClient.doBindService();
    }
    @Override
    public void call(Object... args) {
        switch(action)
        {
            case "LOGIN":
                Logs.info(TAG+"_LoginCall","success");
                break;
            case "clientToServerSyncAck":
                Logs.info(TAG+"_Change Password","success");
                break;
            case "ERROR" :
                Logs.info(TAG+"_LoginCall","Error");
                break;
            case "LoginResponce" :
                bindUI("LoginResponce");
                break;
            case Socket.EVENT_CONNECT:
                System.out.println("Hub*******************Connect");
                System.out.println("socket-------------------------------connected");
                App.setFlag_connection_lost(false);
                App.setNetwork_flag(false);
                App.setSocket(msocket);
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_date_time",new JSONObject()));
                HomeHelthCall.getHomeHelth(mcotext);
                Logs.info(TAG+"_AplicationStateffffff",""+App.isFlag_main_activity_state()+"------"+App.isFlag_connection_lost_activity_state());
                bindUI("hub_connected");
                if(App.isFlag_connection_lost_activity_state()) {
                    if (pref.getValue("hub_connect").equals("1")) {
                        pref.setValue("hub_connect", "0");
                        Logs.info(TAG+"_Socket_Listener", "i m in listener");
                        closeConnectionLost();
                        Intent intent = new Intent(mcotext, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mcotext.startActivity(intent);
                    }
                    if (App.isSetStaticIp()) {
                        Logs.info(TAG+"_Socket_Listener", "i m in listener");
                        App.setSocket(null);
                        App.setSetStaticIp(false);
                        closeConnectionLost();
                        Intent intent = new Intent(mcotext, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mcotext.startActivity(intent);
                    }
                }
                break;
            case Socket.EVENT_DISCONNECT:
                System.out.println("Hub*******************Disconnect");
                Logs.info(TAG,"---------"+App.isFlag_connection_lost_activity_state()+"----------"+App.isFlag_main_activity_state()+"--------"+App.isFlag_connection_lost());
                Logs.info(TAG,"hub---------is disconnected----------"+args[0]);
                App.setCallfrom("SocketListener");
                pref.setValue("hub_connect","1");
                if (!App.isFlag_connection_lost()&&(App.isFlag_connection_lost_activity_state() || App.isFlag_main_activity_state())) {
                    Intent i = new Intent(mcotext, ConnectionLostActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    mcotext.startActivity(i);
                }
                else
                {
                    bindUI("saving_location");
                }
                break;
            case "connect_error":
                Logs.info(TAG+"_LoginCallSocke", "connect_error......."+args[0]);
                break;
            case Socket.EVENT_PING:
                System.out.println("Hub*******************PING");
                break;
            case Socket.EVENT_PONG:
                System.out.println("Hub*******************PONG");
                break;
            case Socket.EVENT_RECONNECT:
                System.out.println("Hub*******************ReConnect");
                break;
            case Socket.EVENT_ERROR:
                System.out.println("Hub*******************Error_"+args[0]);
                try {
                    bindUI("authentication_error_"+new JSONObject(""+args[0]).getString("code"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //CustomDialogShow.dispDialog(mcotext,MainActivity.class,"Connection Lost");
                break;
            case "authentication":
                System.out.println("Hub*******************Authantication_"+args[0]);
                //  CustomDialogShow.dispDialog(mcotext,MainActivity.class,"Connection Lost");
                break;
            case Socket.EVENT_MESSAGE:
                break;
            case "action":
                try
                {
                    JSONObject object = (JSONObject) args[0];
                    Logs.info(TAG+"_FResultJson",""+object);
                    Logs.info(TAG+"Action_type",""+object.getString("type"));
                    switch (object.getString("type")) {
                        case "room_added":
                            updateData(ROOMS,object,UPDATE);
                            bindUI("rooms");
                            break;
                        case "sim_notif_set":

                            App.setNotificationData(object);
                            bindUI("sim_notif_set");
                            JSONObject objjj = new JSONObject();
                            objjj.put("data", new JSONObject().put("type", new JSONArray().put("Dawn")));
                            objjj.put("type", "get_notification");
                            Logs.info("NotificationSettingFragemnt"+"_SimulationRequest", "" + objjj);
                            App.getSocket().emit("action", objjj);


                            JSONObject objjjj = new JSONObject();
                            objjjj.put("data", new JSONObject().put("type", new JSONArray().put("Sleep")));
                            objjjj.put("type", "get_notification");
                            Logs.info("NotificationSettingFragemnt"+"_SimulationRequest", "" + objjjj);
                            App.getSocket().emit("action", objjjj);

                            break;
                        case "room_deleted":
                            removeData(ROOMS,object);
                            bindUI("rooms");
                            break;
                        case "music_state":
                            App.setMusicStateJson(object);
                            if (!App.getCallfrom().equals("MusicDetailFragment")) {
                                if (App.getCurrentSubcriber() != null) {
                                    observable = App.getCurrentSubcriber().getObservable();
                                    observer = App.getCurrentSubcriber().getObserver();
                                    App.setSubcription(observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer));
                                }
                            }
                            break;
                        case "not_present_playList":
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("data", new JSONArray());
                            App.setMusicPlaylistJson(jsonObject);
                            bindUI("Music");
                            break;
                        case "radio_playlists":
                            App.setMusicPlaylistJson(object);
                            bindUI("Music");
                            break;
                        case "no_playlist_present":
                            App.setMusicPlaylistJson(object);
                            bindUI("playlist");
                            bindUI("home_playlists");
                            break;
                        case "groups":
                            updateData(GROUPS,object,SET);
                            bindUI("");
                            break;
                        case "groups_by_room":
                            updateData(GROUPS_BY_ROOM,object,SET);
                            updateData(GROUPS,object,UPDATE);
                            bindUI("groups_by_room");
                            break;
                        case "notification_set":
                            App.setNotificationSet(true);
                            bindUI("groups_by_room");
                            break;
                        case "played_next_song":
                            bindUI("played_next_song");
                            break;
                        case "device_deleted":
                            removeData(DEVICES_BY_ROOM,object);
                            removeData(PROVISIONAL_DEVICES,object);
                            bindUI("room_device");
                            bindUI("hvac_zone_provisioned");
                            break;
                        case "played_previous_song":
                            bindUI("played_previous_song");
                            break;
                        case "notification_get":
                            App.setNotificationJson(object);
                            break;
                        case "wifi_register_failed":
                            App.setWifi_setup_save(false);
                            //  App.setDateTimeJson(object);
                            bindUI("wifi_register");
                            break;
                        case "wifi_registered":
                            App.setWifi_setup_save(true);
                            //  App.setDateTimeJson(object);
                            bindUI("wifi_register");
                            break;
                        case "zones":
                            updateData(ZONES,object,SET);
                            bindUI("zones");
                        case "zone_updated":
                            updateData(ZONES,object,UPDATE);
                            bindUI("zones_"+object.getJSONArray("data").getJSONObject(0).getString("CML_TITLE"));
                            break;
                        case "home_updated":
                            updateData(ZONES,object,UPDATE);
                            bindUI("");
                            break;
                        case "hvac_zone_provisioned":
                            updateData(HVAC_PROVISIONED,object,SET);
                            bindUI("hvac_zone_provisioned");
                            break;
                        case "sensors_provisioned":
                            App.setSensorsProvisionedJson(object);
                            bindUI("sensors_provisioned");
                            break;
                        case "hub_date_time_set":
                            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_date_time", new JSONObject()));
                            break;
                        case "hub_Date_Time":
                            App.setDateTimeJson(object);
                            bindUI("hub_Date_Time");
                            break;
                        case "hub_Date_Time_change":
                            App.setDateTimeJson(object);
                            bindUI("hub_Date_Time");
                            break;
                        case "notifiers_list":
                            App.setNotifierlistJson(object);
                            bindUI("");
                            break;
                        case "network_info":
                            updateData(NETWORK_INFO,object,SET);
                            bindUI("");
                            bindUI("network_info");
                            break;
                        case "current_track":
                            if (!App.getCallfrom().equals("MusicDetailFragment")) {
                                App.setCurrenMusicJson(object);
                                if (App.getCurrentSubcriber() != null) {
                                    observable = App.getCurrentSubcriber().getObservable();
                                    observer = App.getCurrentSubcriber().getObserver();
                                    App.setSubcription(observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer));
                                }
                            }
                            break;
                        case "nearby_wifis":
                            App.setSsidJson(object);
                            bindUI("nearby_wifis");
                            break;
                        case "playlists":
                            updateData(PLAYLISTS,object,SET);
                            bindUI("playlist");
                            break;
                        case "home_playlists":
                            updateData(PLAYLISTS,object,SET);
                            bindUI("home_playlists");
                            break;
                        case "update_playlists":
                            setDefaultDeselectedFlag();
                            updateData(PLAYLISTS,object,UPDATE);
                            System.out.println("ooooooooooooooooooooooopppppppppp"+App.getDataStorageJson().getJSONObject(PLAYLISTS));
                            bindUI("playlist");
                            bindUI("home_playlists");
                            break;
                        case "soft_resetted":
                            App.setResetJson();
                            bindUI("hub_soft_resetted");
                            break;
                        case "unassigned_devices":
                            App.setUnassignedJson(object);
                            bindUI("");
                            break;
                        case "info":
                            App.setHubInfo(object);
                            bindUI("info");
                            break;
                        case "group_deleted":
                            removeData(GROUPS,object);
                            removeData(GROUPS_BY_ROOM,object);
                            bindUI("room_device");
                            break;
                        case "group_added":
                            updateData(GROUPS_BY_ROOM,object,UPDATE);
                            bindUI("room_device");
                            break;
                        case "group_updated":
                            updateData(GROUPS_BY_ROOM,object,UPDATE);
                            updateData(GROUPS,object,UPDATE);
                            bindUI("Group" + object.getJSONArray("data").getJSONObject(0).getString("groupType"));
                            break;
                        case "room_updated":
                            updateData(ROOMS,object,UPDATE);
                            updateData(ROOMS_DEVICES,object,UPDATE);
                            bindUI("device_updated");
                            bindUI("room_updated");
                            break;
                        case "email":
                            App.setEmailJson(object);
                            bindUI("email");
                            break;
                        case "device_updated":
                            updateData(DEVICES_BY_ROOM,object,UPDATE);
                            updateData(PROVISIONAL_DEVICES,object,UPDATE);
                            updateData(HVAC_PROVISIONED,object,UPDATE);
                            Object obj=object.get("data");
                            if(obj instanceof JSONArray) bindUI("" + object.getJSONArray("data").getJSONObject(0).getString("type"));
                            else bindUI("" + object.getJSONObject("data").getString("type"));
                            break;
                        case "device_unprovisioned":
                            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_provisioned", new JSONObject()));
                            break;
                        case "dawn_simulation_halted":
                        case "dawn_simulation_updated":
                        case "dawn_simulation_deleted":
                        case "dawn_simulation_triggered":
                        case "dawn_simulation_created":
                            JSONObject resultJsonObject = new JSONObject();
                            resultJsonObject.put("data",  new JSONObject());
                            resultJsonObject.put("type", "get_simulations");
                            App.getSocket().emit("action", resultJsonObject);
                            break;
                        case "hub_Set_DateTime":
                            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_info", new JSONObject()));
                            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_date_time", new JSONObject()));
                            break;
                        case "queue":
                            App.setMusicQueJson(object);
                            bindUI("queue");
                            bindUI("playlist");
                            break;
                        case "home_queue":
                            App.setHomeMuusicQueJson(object);
                            bindUI("home_queue");
                            bindUI("playlist");
                            break;
                        case "sensor_reading":
                            App.setSensorsData(object);
                            bindUI("sensor_reading");
                            break;
                        case "dawn_simulations":
                            App.setSimulationData(object);
                            bindUI("dawn_simulation");
                            break;
                        case "graph_data":
                            App.setGraphData(object);
                            bindUI("graph_data");
                            break;
                        case "dawn_notifications":
                            updateData(DAWN_NOTIFICATION,object,App.getDataStorageJson().getJSONObject(DAWN_NOTIFICATION).length()>0?UPDATE:SET);
                            bindUI("dawn_simulation_notification");
                            break;
                        case "sleep_notifications":
                            String action_string= App.getDataStorageJson().getJSONObject(SLEEP_NOTIFICATION).length()>0?UPDATE:SET;
                            App.setDawnAwakeData(object);
                            updateData(SLEEP_NOTIFICATION,object,action_string);
                            bindUI("sleep_simulation_notificationaaaa"+action_string);
                            break;
                        case "water_notif_deleted":
                            cancleNotification(mcotext,"Water");
                            break;
                        case "air_notif_deleted":
                            cancleNotification(mcotext,"Air");
                            break;
                        case "dawn_notif_deleted":
                            removeData(DAWN_NOTIFICATION,object);
                            bindUI("dawn_simulation_notification");
                            break;
                        case "sleep_notif_deleted":
                            removeData(SLEEP_NOTIFICATION,object);
                            bindUI("sleep_simulation_notificationaaaa"+UPDATE);
                            break;
                        case "air_notifications":
                            App.setAirFiterNotifJson(object);
                            if(new PrefManager(mcotext).getValue("air_filter").equals("1"))setNotification("Air");
                            else cancleNotification(mcotext,"Air");
                            bindUI("air_filter_notification");
                            break;
                        case "water_notifications":
                            App.setWaterFiterNotifJson(object);
                            if(new PrefManager(mcotext).getValue("water_filter").equals("1"))setNotification("Water");
                            else cancleNotification(mcotext,"Water");
                            bindUI("water_filter_notification");
                            break;
                        case "email_set":
                            App.setEmailJson(object);
                            bindUI("email_set");
                            bindUI("email");
                            break;
                        case "simulations":
                            App.setAllSimulationData(object);
                            bindUI("");
                            break;
                        case "sleep_simulations":
                            App.setSleepSimulationData(object);
                            bindUI("");
                            break;
                        case "dawn_sim_done_awake":
                            App.setDawnAwakeData(object);
                            bindUI("dawn_sim_done_awake");
                            break;

                        case "dawn_sim_done_sleep_again":
                            App.setDawnAwakeData(object);

                            bindUI("dawn_sim_done_sleep_again");
                            break;
                        case "dawn_sim_done_snooze":
                            App.setDawnAwakeData(object);

                            bindUI("dawn_sim_done_snooze");
                            break;

                        case "rooms":
                            System.out.println("roooooooooooooooooooooooomsiiiiiiiiiiiiiiiiiiiiiz"+object.getJSONArray("data").length());
                            updateData(ROOMS,object,SET);
                            bindUI("rooms");
                            break;
                        case "default_dawn_sounds":
                            App.setDefaultSoundData(object);
                            bindUI("default_dawn_sounds");
                            break;
                        case "devices_by_room":
                            updateData(DEVICES_BY_ROOM,object,SET);
                            bindUI("room_device");
                            break;
                        case "playmode_set":
                            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_devices_by_room", getSelectedRoomJson()));
                            break;
                        case "provisioned_devices":
                            updateData(PROVISIONAL_DEVICES,object,SET);
                            bindUI("provisioned_devices");
                            break;
                        case "hub_installation_process":
                            App.setHub_installation_projess_json(object);
                            bindUI("hub_installation_process");
                            break;
                        case "circadian_triggered":
                            bindUI("circadian_triggered");
                            break;
                        case "favorites_list":
                            updateData(FAVORITES,object,SET);
                            System.out.println("favoooooooooooritesiiiiiiiiiiiiiiiiiiiiiz"+object.getJSONArray("data").length());
                            bindUI("room_device");
                            bindUI("rooms_by_device");
                            bindUI("GroupLighting");
                            bindUI("zones");
                            bindUI("zones_"+object.getJSONArray("data").getJSONObject(0).getString("CML_TITLE"));
                            break;
                        case "favorite_updated":
                            updateData(FAVORITES,object,UPDATE);
                            bindUI("room_device");
                            bindUI("zones");
                            break;
                        case "favorites_created":
                        case "favorites_not_created":
                        case "favorites_deleted":
                        case "add_to_favorites":
                        case "delete_favorites":
                            JSONObject resultJsonObject1 = new JSONObject();
                            resultJsonObject1.put("data", new JSONObject());
                            resultJsonObject1.put("type", "get_favorites");
                            App.getSocket().emit("action", resultJsonObject1);
                            break;
                        case "hub_closed":
                            App.setTime_zone(false);
                            App.setFlag_connection_lost(true);
                            break;
                        case "sleep_simulation_halted":
                        case "sleep_simulation_updated":
                        case "sleep_simulation_deleted":
                        case "sleep_simulation_triggered":
                        case "sleep_simulation_created":
                            JSONObject sleepJsonObject = new JSONObject();
                            sleepJsonObject.put("data", new JSONObject());
                            sleepJsonObject.put("type", "get_simulations");
                            App.getSocket().emit("action", sleepJsonObject);
                            break;
                        case "rooms_by_device":
                            updateData(ROOMS_DEVICES,object,SET);
                            bindUI("rooms_by_device");
                            break;
                        case "current_actions":
                            App.setcurrent_actions(object);
                            bindUI("current_actions");
                            break;
                        case "weather_data":
                            App.setweather_condition(object);
                            bindUI("weather_data");
                            break;
                        case "breezometer_data":
                            App.setbreezometer_data(object);
                            bindUI("breezometer_data");
                            break;
                        case "LoginResponce":
                            Logs.info(TAG+"_LoginData",""+ args[0]);
                            App.setLoginData((JSONObject)args[0]);
                            break;
                        case "error":
                            Logs.info(TAG+"_LoginCall", "Error"+args[0]);
                            bindUI("error");
                            break;
                        case "ERROR":
                            Logs.info(TAG+"Eror_LoginCall", "Error"+args[0]);
                            bindUI("error");
                            break;
                        case "ErrorFromServer":
                            Logs.info(TAG+"_LoginCall", "Error"+args[0]);
                            try {
                                App.setChangePassJson(new JSONObject(""+args[0]));
                            } catch (JSONException e) {
                                Logs.error(TAG+"_ChangePassordError",""+e.getMessage());
                            }
                            bindUI("serverToClientIDBIncSync");
                            break;
                        case "serverToClientIDBIncSync":
                            Logs.info(TAG+"_CHangePassJson",""+ args[0]);
                            try {
                                App.setChangePassJson(new JSONObject(""+args[0]));
                            } catch (JSONException e) {
                                Logs.error(TAG+"_ChangePassordError",""+e.getMessage());
                            }
                            bindUI("serverToClientIDBIncSync");
                            break;
                        case "authenticated":
                            Logs.info(TAG+"_authenticated", "authontication");
                            App.setAuthanticate(true);
                            bindUI("authenticated");
                            break;


                        case "sleep_popup_closed":
                            App.setDawnAwakeData(object);
                            bindUI("sleep_popup_closed");
                            break;
                    }

                } catch (Exception ex) {
                    Logs.error(TAG+"_Error", ""+ex.getMessage());
                }
                break;
            default:
        }
    }
    private JSONObject getSelectedRoomJson()
    {
        String room_id= App.getRoom().getRoom_id();
        JSONObject resultedjsonObject=null;
        try {
            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("CML_ID").equals(room_id)) {
                    resultedjsonObject=jsonObject;
                }
            }
        }
        catch (Exception ex){
            Logs.error(TAG+"_RoomStateError",""+ex.getMessage());}
        return resultedjsonObject;
    }
    public void closeConnectionLost()
    {
        if(ConnectionLostActivity.instance != null) {
            try {
                ConnectionLostActivity.instance.finish();
            } catch (Exception e) {}
        }
    }
    public void bindUI(String response_string)
    {
        try {
            if (App.getMain_activity_subcriber() != null) {
                observable = App.getMain_activity_subcriber().getObservable();
                observer = App.getMain_activity_subcriber().getObserver();
                App.setSubcription(Observable.just(response_string).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer));
            }
            if (App.getTypesubcriber() != null) {
                observable = App.getTypesubcriber().getObservable();
                observer = App.getTypesubcriber().getObserver();
                App.setSubcription(Observable.just(response_string).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer));
            }
            if (App.getRoom_subcriber() != null) {
                observable = App.getRoom_subcriber().getObservable();
                observer = App.getRoom_subcriber().getObserver();
                App.setSubcription(Observable.just(response_string).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer));
            }
            if (App.getCurrentSubcriber() != null) {
                observable = App.getCurrentSubcriber().getObservable();
                observer = App.getCurrentSubcriber().getObserver();
                App.setSubcription(Observable.just(response_string).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer));
            }
            if (App.getDialog_subcriber() != null) {
                observable = App.getDialog_subcriber().getObservable();
                observer = App.getDialog_subcriber().getObserver();
                App.setSubcription(Observable.just(response_string).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer));
            }
            if (App.getHome_thermostat_subcriber() != null) {
                observable = App.getHome_thermostat_subcriber().getObservable();
                observer = App.getHome_thermostat_subcriber().getObserver();
                App.setSubcription(Observable.just(response_string).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer));
            }
            if (App.getFav_room_subcriber() != null) {
                observable = App.getFav_room_subcriber().getObservable();
                observer = App.getFav_room_subcriber().getObserver();
                App.setSubcription(Observable.just(response_string).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer));
            }
            if (App.getMusic_playlist_subcriber() != null) {
                observable = App.getMusic_playlist_subcriber().getObservable();
                observer = App.getMusic_playlist_subcriber().getObserver();
                App.setSubcription(Observable.just(response_string).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer));
            }
            if (App.getDawnPopupSubcriber() != null) {
                observable = App.getDawnPopupSubcriber().getObservable();
                observer = App.getDawnPopupSubcriber().getObserver();
                App.setSubcription(Observable.just(response_string).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer));
            }
            if (App.getSleepPopupSubcriber() != null) {
                observable = App.getSleepPopupSubcriber().getObservable();
                observer = App.getSleepPopupSubcriber().getObserver();
                App.setSubcription(Observable.just(response_string).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer));
            }
        }
        catch (Exception ex)
        {
            Logs.error(TAG+"_SubscriberError",ex.getMessage());
        }
    }

    private void removeData(String response_type, JSONObject jsonObject)
    {
        try {

            App.getDataStorageJson().getJSONObject(response_type).remove(jsonObject.getJSONArray("data").getJSONObject(0).has("CML_ID")?jsonObject.getJSONArray("data").getJSONObject(0).getString("CML_ID"):jsonObject.getJSONArray("data").getJSONObject(0).getString("Id"));
        }
        catch (Exception ex)
        {
            Logs.error(TAG,"remove_data_error"+ex.getMessage());
        }
    }


    /**
     * this method is for update database
     *
     * @author  nikhil vharamble
     * @version 67
     * @since   2018-10-10
     * @param response_type String response type like room updated,room,device
     * @param jsonObject jsonobject which goes for updation.
     */
    @SuppressLint("NewApi")
    private void updateData(String response_type, JSONObject jsonObject,String action)
    {
        try {
            if(action.equals(SET)) App.getDataStorageJson().put(response_type,new JSONObject());
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i<jsonArray.length();i++) {
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                if(response_type.equals(DEVICES_BY_ROOM)||response_type.equals(GROUPS_BY_ROOM)) {
                    if (App.getRoom().getRoom_id().equals(jsonObject1.getString("room"))){
                        App.getDataStorageJson().getJSONObject(response_type).put(jsonObject1.has("CML_ID") ? jsonObject1.getString("CML_ID") : jsonObject1.getString("Id"), jsonObject1);}
                }
                else
                {
                    if(response_type.equals(ROOMS_DEVICES)&&!action.equals(SET))
                    {
                        if(App.getDataStorageJson().getJSONObject(response_type).toString().contains(jsonObject1.getString("CML_ID")))App.getDataStorageJson().getJSONObject(response_type).put(jsonObject1.has("CML_ID") ? jsonObject1.getString("CML_ID") : jsonObject1.getString("Id"), jsonObject1);

                    }
                    else {
                        App.getDataStorageJson().getJSONObject(response_type).put(jsonObject1.has("CML_ID") ? jsonObject1.getString("CML_ID") : jsonObject1.getString("Id"), jsonObject1);
                    }
                }

            }
        }
        catch (Exception ex)
        {
            Logs.error(TAG,"update_data_error"+ex.getMessage());
        }

    }
    public int getPosition(String id,String data_name)
    {
        Logs.info(TAG+"_Data_name",data_name);
        int pos=-1;
        try {
            JSONArray jsonArray =null;
            if(data_name.equals("provisional"))jsonArray=App.getProvisionalDevicesData().getJSONArray("data");
            if(data_name.equals("favorite"))jsonArray=App.getFavData().getJSONArray("data");
            if(data_name.equals("devicebyroom"))jsonArray=App.getDevicesByRoomData().getJSONArray("data");
            if(data_name.equals("room_devices"))jsonArray=App.getRoomDeviceData().getJSONArray("data");
            if(data_name.equals("group"))jsonArray=App.getGroupJson().getJSONArray("data");
            if(data_name.equals("groupbyroom"))jsonArray=App.getGroupByRoomJsonObject().getJSONArray("data");
            if(data_name.equals("room"))jsonArray=App.getRoomData().getJSONArray("data");
            if(data_name.equals("zone"))jsonArray=App.getZoneJson().getJSONArray("data");
            if(data_name.equals("hvac_provision"))jsonArray=App.getHvacZoneProvisionedJson().getJSONArray("data");
            if(data_name.equals("playlist"))jsonArray=App.getMusicPlaylistJson().getJSONArray("data");
            if(data_name.equals("room_by_devices"))jsonArray=App.getRoomDeviceData().getJSONArray("data");
            if(!jsonArray.toString().equals("[]")) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Logs.info(TAG+"_updated_json",""+jsonObject);
                    if(jsonObject.has("CML_ID")) {
                        if (jsonObject.getString("CML_ID").equals(id)) {
                            pos = i;
                            break;
                        }
                    }
                    if(jsonObject.has("Id")) {
                        if (jsonObject.getString("Id").equals(id)) {
                            pos = i;
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Logs.error(TAG+"_PositionError",""+ex.getMessage());
        }
        return  pos;
    }


    public void setDefaultDeselectedFlag()
    {
        try {
            JSONArray resultedJsonArray=new JSONArray();
            JSONArray jsonArray = App.getMusicPlaylistJson().getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject updateJsonObject=jsonArray.getJSONObject(i);
                resultedJsonArray.put(updateJsonObject.put("selectedFlag",false));
            }
            System.out.println("ooooooooooooooooooooooo"+new JSONObject().put("data",resultedJsonArray));
            updateData(PLAYLISTS,new JSONObject().put("data",resultedJsonArray),SET);
        }
        catch (Exception ex)
        {
            Logs.error(TAG+"_UpdateJsonError",""+ex.getMessage());
        }
    }

    public  void setNotification(String type)
    {
        JSONArray jsonArray=null;
        int prefix_int=0;
        Intent myIntent = new Intent(mcotext, NotificationReceiver.class);
        try {
            if (type.equals("Water")) {
                prefix_int = 1;
                AlarmUtils.cancelAllAlarms(mcotext, myIntent, prefix_int);
                jsonArray = App.getWaterFiterNotifJson().getJSONArray("data");
            }
            if (type.equals("Air")) {
                prefix_int = 2;
                AlarmUtils.cancelAllAlarms(mcotext, myIntent, prefix_int);
                jsonArray = App.getAirFiterNotifJson().getJSONArray("data");
            }
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    Intent myIntent1 = new Intent(mcotext, NotificationReceiver.class);
                    Calendar notify_date = Calendar.getInstance();
                    notify_date.setTimeInMillis(System.currentTimeMillis());
                    notify_date.clear();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    notify_date.setTime(sdf.parse(/*"2018-07-13T14:04:00.000Z"*/jsonObject.getString("notif_date")));
                    notify_date.set(Calendar.HOUR_OF_DAY,jsonObject.getInt("notif_time"));
                    notify_date.set(Calendar.MINUTE,0);
                    notify_date.set(Calendar.SECOND,0);
                    Calendar expdate = Calendar.getInstance();
                    expdate.setTime(sdf.parse(getExperyDate(type)));
                    Logs.info("SocketListeneer_notificationtime", exp_sdf.format(notify_date.getTime()) + "-------" + expdate.get(Calendar.HOUR_OF_DAY) + ":" + expdate.get(Calendar.MINUTE) + ":" + expdate.get(Calendar.SECOND));
                    myIntent1.putExtra("title", type + " Filter Notication");
                    myIntent1.putExtra("msg", "");
                    myIntent1.putExtra("type", type);
                    myIntent1.putExtra("exp_date", "" + exp_sdf.format(expdate.getTime()));
                    myIntent1.putExtra("notif_date", "" + jsonObject.getString("notif_date"));
                    myIntent1.putExtra("notif_time", "" + jsonObject.getInt("notif_time"));
                    String id = prefix_int + "" + i;
                    Logs.info("SocketListener_NotificationId", "" + notify_date.after(Calendar.getInstance()));
                    if(notify_date.after(Calendar.getInstance()))AlarmUtils.addAlarm(mcotext, myIntent1, Integer.parseInt(id), notify_date);
                }
            }
            //   }
        }
        catch (Exception ex)
        {
            Logs.error("SocketListener_notificationseterror",""+ex.getMessage());
        }
    }
    public static void cancleNotification(Context mcotext,String type)
    {
        int prefix_int=-1;
        Intent myIntent = new Intent(mcotext, NotificationReceiver.class);
        if(type.equals("Water")) {
            prefix_int=1;
            AlarmUtils.cancelAllAlarms(mcotext,myIntent,prefix_int);
        }
        if(type.equals("Air")) {
            prefix_int=2;
            AlarmUtils.cancelAllAlarms(mcotext,myIntent,prefix_int);
        }
        if(type.equals("Dawn")) {
            prefix_int=3;
            AlarmUtils.cancelAllAlarms(mcotext,myIntent,prefix_int);
        }
        if(type.equals("Sleep")) {
            prefix_int=4;
            AlarmUtils.cancelAllAlarms(mcotext,myIntent,prefix_int);
        }
    }

    public static String getExperyDate(String type)
    {
        String exp_date="";
        try {
            if(App.getProvisionalDevicesData()!=null) {
                JSONArray jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getString("type").equals(type)) {
                        exp_date = jsonObject.getString("expiryDate");
                    }
                }
            }
        }
        catch (Exception ex){ Logs.error("SocketListener_Error",ex.getMessage());}
        return exp_date;
    }


}