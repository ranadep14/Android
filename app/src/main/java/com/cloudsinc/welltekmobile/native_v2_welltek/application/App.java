package com.cloudsinc.welltekmobile.native_v2_welltek.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Device;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Light;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.New_RoomDawn;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Room;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Sound;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Zone;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import io.socket.client.Socket;
import rx.Subscription;

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
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.SLEEP_NOTIFICATION;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.ZONES;
/**
 * This application class work as temperory storage all setter/getter methods
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
@SuppressWarnings("ALL")
public class App extends Application {

    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    public static ArrayList<String> getList_seleted_package() {
        return list_seleted_package;
    }

    public static void setList_seleted_package(ArrayList<String> list_seleted_package) {
        App.list_seleted_package = list_seleted_package;
    }

    private static ArrayList<String> list_seleted_package;

    private static int frame_id;
    public static SharedPreferences preferences;
    public static JSONArray getHub_ids() {
        return hub_ids;
    }
    private static Subcriber dawnPopupSubcriber;
    private static Subcriber sleepPopupSubcriber;
    public static void setHub_ids(JSONArray hub_ids) {
        App.hub_ids = hub_ids;
    }
    private static JSONArray hub_ids;
    public static boolean isTime_zone() {
        return time_zone;
    }
    public static void setTime_zone(boolean time_zone) {
        App.time_zone = time_zone;
    }
    private static boolean time_zone=false;
    public static boolean isChange_password() {
        return change_password;
    }
    public static void setChange_password(boolean change_password) {
        App.change_password = change_password;
    }
    public static Subcriber getDawnPopupSubcriber() {
        return dawnPopupSubcriber;
    }
    public static void setDawnPopupSubcriber(Subcriber dawnPopupSubcriber) {
        App.dawnPopupSubcriber = dawnPopupSubcriber;
    }
    public static Subcriber getSleepPopupSubcriber() {
        return sleepPopupSubcriber;
    }
    public static void setSleepPopupSubcriber(Subcriber sleepPopupSubcriber) {
        App.sleepPopupSubcriber = sleepPopupSubcriber;
    }
    private static boolean change_password=false;
    public static boolean isHub_not_available() {
        return hub_not_available;
    }
    public static void setHub_not_available(boolean hub_not_available) {
        App.hub_not_available = hub_not_available;
    }
    private static boolean hub_not_available=false;
    public static ArrayList<String> getRoom_ids() {
        return room_ids;
    }
    public static void setRoom_ids(ArrayList<String> room_ids) {
        App.room_ids = room_ids;
    }
    private static ArrayList<String> room_ids;
    public static ArrayList<String> getDeselected_room_ids() {
        return deselected_room_ids;
    }
    public static void setDeselected_room_ids(ArrayList<String> deselected_room_ids) {
        App.deselected_room_ids = deselected_room_ids;
    }
    private static ArrayList<String> deselected_room_ids;
    public static int getPosition() {
        return position;
    }
    public static void setPosition(int position) {
        App.position = position;
    }
    private  static int position=-1;
    public static int getAdd_wake_position() {
        return add_wake_position;
    }
    public static void setAdd_wake_position(int add_wake_position) {
        App.add_wake_position = add_wake_position;
    }
    private  static int add_wake_position;
    public static String getSsid_name() {
        return ssid_name;
    }
    public static void setSsid_name(String ssid_name) {
        App.ssid_name = ssid_name;
    }
    public static JSONObject getChangePassJson() {
        return changePassJson;
    }
    public static void setChangePassJson(JSONObject changePassJson) {
        App.changePassJson = changePassJson;
    }
    public static boolean isAuthanticate() {
        return authanticate;
    }
    public static void setAuthanticate(boolean authanticate) {
        App.authanticate = authanticate;
    }
    public static boolean authanticate;
    private  static String ssid_name;
    public static int getPlayListIndex() {
        return playListIndex;
    }
    public static void setPlayListIndex(int playListIndex) {
        App.playListIndex = playListIndex;
    }
    private  static int playListIndex;
    private static Socket mloginsocket;
    public static boolean isConnect_to_sonos() {
        return connect_to_sonos;
    }
    public static void setConnect_to_sonos(boolean connect_to_sonos) {
        App.connect_to_sonos = connect_to_sonos;
    }
    public static boolean isNotificationSet() {
        return notificationSet;
    }
    public static void setNotificationSet(boolean notificationSet) {
        App.notificationSet = notificationSet;
    }
    private static boolean notificationSet=false;
    public static boolean isFlag_connection_lost() {
        return App.flag_connection_lost;
    }
    public static void setFlag_connection_lost(boolean flag_connection_lost) {
        App.flag_connection_lost = flag_connection_lost;
    }
    private static boolean flag_connection_lost;
    public static boolean isFlag_main_activity_state() {
        return App.flag_main_activity_state;
    }
    public static void setFlag_main_activity_state(boolean flag_main_activity_state) {
        App.flag_main_activity_state = flag_main_activity_state;
    }
    private static boolean flag_main_activity_state;
    public static boolean isFlag_connection_lost_activity_state() {
        return flag_connection_lost_activity_state;
    }
    public static void setFlag_connection_lost_activity_state(boolean flag_connection_lost_activity_state) {
        App.flag_connection_lost_activity_state = flag_connection_lost_activity_state;
    }
    private static boolean flag_connection_lost_activity_state;
    public static boolean isNo_internet() {
        return no_internet;
    }
    public static void setNo_internet(boolean no_internet) {
        App.no_internet = no_internet;
    }
    public static boolean no_internet;
    public static HashMap<String, List<Light>> getLightArrayList() {
        return lightArrayList;
    }
    public static void setLightArrayList(HashMap<String, List<Light>> lightArrayList) {
        App.lightArrayList = lightArrayList;
    }
    public static HashMap<String, List<Light>> lightArrayList=new HashMap<String, List<Light>>();
    public static ArrayList<String> getLightRoomArrayList() {
        return lightRoomArrayList;
    }
    public static void setLightRoomArrayList(ArrayList<String> lightRoomArrayList) {
        App.lightRoomArrayList = lightRoomArrayList;
    }
    public static ArrayList<String> lightRoomArrayList=new ArrayList<>();
    private static boolean connect_to_sonos;
    private String loginstate;
    private String ip_address;
    private String device_name;
    private static boolean flag_upload_prof;
    private static Sound sound;
    public static String getNotification_name() {
        return notification_name;
    }
    public static void setNotification_name(String mnotification_name) {
        notification_name = mnotification_name;
    }
    private static String notification_name;
    public static String getDevice_id() {
        return device_id;
    }
    public static void setDevice_id(String mdevice_id) {
        device_id = mdevice_id;
    }
    private static String device_id;
    public static String getServer_url() {
        return server_url;
    }
    public static void setServer_url(String server_url) {
        App.server_url = server_url;
    }
    private static String server_url;
    public static Context getDialogcontext() {
        return dialogcontext;
    }
    public static void setDialogcontext(Context dialogcontext) {
        App.dialogcontext = dialogcontext;
    }
    private static Context dialogcontext;
    private static Socket msocket=null;
    private static Subscription msubcription;
    private static Subcriber subcriber;
    public static Subcriber getActive_button_subcriber() {
        return active_button_subcriber;
    }
    public static void setActive_button_subcriber(Subcriber active_button_subcriber) {
        App.active_button_subcriber = active_button_subcriber;
    }
    private static Subcriber active_button_subcriber;
    public static Subcriber getTypesubcriber() {
        return typesubcriber;
    }
    public static void setTypesubcriber(Subcriber typesubcriber) {
        App.typesubcriber = typesubcriber;
    }
    private static Subcriber typesubcriber;
    public static Subcriber getHubsubcriber() {
        return hubsubcriber;
    }
    public static void setHubsubcriber(Subcriber hubsubcriber) {
        App.hubsubcriber = hubsubcriber;
    }
    public static Subcriber getMusic_playlist_subcriber() {
        return music_playlist_subcriber;
    }
    public static void setMusic_playlist_subcriber(Subcriber music_playlist_subcriber) {
        App.music_playlist_subcriber = music_playlist_subcriber;
    }
    private static Subcriber music_playlist_subcriber;
    public static Subcriber getRoom_subcriber() {
        return room_subcriber;
    }
    public static void setRoom_subcriber(Subcriber room_subcriber) {
        App.room_subcriber = room_subcriber;
    }
    public static Subcriber getDawn_subcriber() {
        return dawn_subcriber;
    }
    public static void setDawn_subcriber(Subcriber dawn_subcriber) {
        App.dawn_subcriber = dawn_subcriber;
    }
    private static Subcriber room_subcriber;
    private static Subcriber dawn_subcriber;
    public static Subcriber getHome_thermostat_subcriber() {
        return home_thermostat_subcriber;
    }
    public static void setHome_thermostat_subcriber(Subcriber home_thermostat_subcriber) {
        App.home_thermostat_subcriber = home_thermostat_subcriber;
    }
    private static Subcriber home_thermostat_subcriber;
    public static Subcriber getMain_activity_subcriber() {
        return main_activity_subcriber;
    }
    public static void setMain_activity_subcriber(Subcriber main_activity_subcriber) {
        App.main_activity_subcriber = main_activity_subcriber;
    }
    private static Subcriber main_activity_subcriber;
    public static Subcriber getFav_room_subcriber() {
        return fav_room_subcriber;
    }
    public static void setFav_room_subcriber(Subcriber fav_room_subcriber) {
        App.fav_room_subcriber = fav_room_subcriber;
    }
    private static Subcriber fav_room_subcriber;
    public static Subcriber getDialog_subcriber() {
        return dialog_subcriber;
    }
    public static void setDialog_subcriber(Subcriber dialog_subcriber) {
        App.dialog_subcriber = dialog_subcriber;
    }
    private static Subcriber dialog_subcriber;
    private static ArrayList<Subcriber> msubcriber=new ArrayList<>();
    public static ArrayList<Subcriber> getFav_subscriber() {
        return fav_subscriber;
    }
    public static void addFav_subscriber(Subcriber fav_subscriber) {
        App.fav_subscriber .add(fav_subscriber);
    }
    public static void clearFavSubscriber()
    {
        App.fav_subscriber.clear();
    }
    private static ArrayList<Subcriber> fav_subscriber=new ArrayList<>();
    public static Subcriber getArrsubcriber(int pos) {
        return msubcriber.get(pos);
    }
    public static void addArrsubcriber(Subcriber subcriber) {
        App.msubcriber.add(subcriber);
        Log.e("SubcriberSize",""+App.msubcriber.size());
    }
    public static void clearSubscriber()
    {
        App.msubcriber.clear();
    }
    private static Subcriber[] arrsubcriber;
    public static boolean isApplication_state() {
        return application_state;
    }
    public static void setApplication_state(boolean application_state) {
        App.application_state = application_state;
    }
    public static boolean application_state;
    private static boolean MySim;
    public static boolean isNetwork_flag() {
        return network_flag;
    }
    public static void setNetwork_flag(boolean network_flag) {
        App.network_flag = network_flag;
    }
    private static boolean network_flag;
    public static boolean isSetDateTime() {
        return setDateTime;
    }
    public static void setSetDateTime(boolean setDateTime) {
        App.setDateTime = setDateTime;
    }
    private static boolean setDateTime=false;
    public static boolean isHr24() {
        return hr24;
    }
    public static void setHr24(boolean hr24) {
        App.hr24 = hr24;
    }
    private static boolean hr24;
    public static boolean isSetStaticIp() {
        return setStaticIp;
    }
    public static void setSetStaticIp(boolean setStaticIp) {
        App.setStaticIp = setStaticIp;
    }
    private static boolean setStaticIp;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    public static boolean isClouzerConnect() {
        return ClouzerConnect;
    }
    public static void setClouzerConnect(boolean clouzerConnect) {
        ClouzerConnect = clouzerConnect;
    }

    public static String getKelvin_data() {
        return kelvin_data;
    }

    public static void setKelvin_data(String kelvin_data) {
        App.kelvin_data = kelvin_data;
    }

    private  static String kelvin_data="";
    private static boolean ClouzerConnect;
    public static boolean isDateTime() {
        return DateTime;
    }
    public static void setDateTime(boolean dateTime) {
        DateTime = dateTime;
    }
    private static boolean DateTime;
    public static boolean isWifi_setup_save() {
        return wifi_setup_save;
    }
    public static void setWifi_setup_save(boolean wifi_setup_save) {
        App.wifi_setup_save = wifi_setup_save;
    }
    private static boolean wifi_setup_save;
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            sAnalytics = GoogleAnalytics.getInstance(this);


            InputStream is = getAssets().open("kelvin_to_color.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            setKelvin_data(new String(buffer, "UTF-8"));
        }
        catch (Exception ex){}

        //  BlurKit.init(this);
        //  Fabric.with(this, new Crashlytics());
        preferences=getSharedPreferences("notif_type",MODE_PRIVATE);
        preferences.edit().putInt("Sohan", 123);
        preferences.edit().commit();
        int a = preferences.getInt("Sohan", -1);
        Log.i("iiiiiiiiii_dawn_ser", "" + a);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                handleUncaughtException(thread, ex);
            }
        });


        if ( isExternalStorageWritable() ) {

            File appDirectory = new File( Environment.getExternalStorageDirectory() + "/MyPersonalAppFolder" );
            File logDirectory = new File( appDirectory + "/log" );
            File logFile = new File( logDirectory, "logcat" + System.currentTimeMillis() + ".txt" );

            // create app folder
            if ( !appDirectory.exists() ) {
                appDirectory.mkdir();
            }

            // create log folder
            if ( !logDirectory.exists() ) {
                logDirectory.mkdir();
            }

            // clear the previous logcat and then write the new one to the file
            try {
                Process process = Runtime.getRuntime().exec("logcat -c");
                process = Runtime.getRuntime().exec("logcat -f " + logFile);
            } catch ( IOException e ) {
                e.printStackTrace();
            }

        } else if ( isExternalStorageReadable() ) {
            // only readable
        } else {
            // not accessible
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals( state ) ) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals( state ) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals( state ) ) {
            return true;
        }
        return false;


    }
    static synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }
    public static void trackEvent(String category, String action, String label) {
        Tracker t = getDefaultTracker();

        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }
    public void handleUncaughtException (Thread thread, Throwable e)
    {
        try {
            ////Toast.makeText(getApplicationContext(), "inside handleUncaughtException", Toast.LENGTH_SHORT).show();
            String stackTrace = Log.getStackTraceString(e);
            String message = e.getMessage();
            PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int versionNumber = pinfo.versionCode;
            String versionName = pinfo.versionName;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"jaid.shaikh@nciportal.com", "nikhil.vharamble@nciportal.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "v2.0_native Crash log file" );
            intent.putExtra(Intent.EXTRA_TEXT, "For version no."+versionNumber+" And version name "+versionName+"\n\n"+stackTrace);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application
            startActivity(intent);
        }
        catch (Exception ex)
        {
            Logs.error(getClass().getSimpleName(),"----"+ex.getMessage());
        }
    }
    public static JSONObject getCurrentMusicPlayListJson() {
        return currentMusicPlayListJson;
    }
    public static void setCurrentMusicPlayListJson(JSONObject currentMusicPlayListJson) {
        App.currentMusicPlayListJson = currentMusicPlayListJson;
    }
    public static boolean isMySim() {
        return MySim;
    }
    public static void setMySim(boolean mySim) {
        MySim = mySim;
    }
    public static JSONObject getSsidJson() {
        return ssidJson;
    }
    public static void setSsidJson(JSONObject ssidJson) {
        App.ssidJson = ssidJson;
    }
    public static JSONObject getUnassignedJson() {
        return unassignedJson;
    }
    public static void setUnassignedJson(JSONObject unassignedJson) {
        App.unassignedJson = unassignedJson;
    }
    public static JSONObject getDateTimeJson() {
        return dateTimeJson;
    }
    public static void setDateTimeJson(JSONObject dateTimeJson) {
        App.dateTimeJson = dateTimeJson;
    }
    public static JSONObject getCurrenMusicJson() {
        return currenMusicJson;
    }
    public static void setCurrenMusicJson(JSONObject currenMusicJson) {
        App.currenMusicJson = currenMusicJson;
    }
    public static JSONObject getNetworkInfoJson() {
        return getData(NETWORK_INFO);
    }
    public static void setNetworkInfoJson(JSONObject networkInfoJson) {
        App.networkInfoJson = networkInfoJson;
    }
    public static JSONObject getZoneJson() {
        return getData(ZONES);
    }
    public static void setZoneJson(JSONObject zoneJson) {
        App.zoneJson = zoneJson;
    }
    public static JSONObject getMusicPlaylistJson() {
        return getData(PLAYLISTS);
    }
    public static void setMusicPlaylistJson(JSONObject musicPlaylistJson) {
        App.musicPlaylistJson = musicPlaylistJson;
    }
    public static JSONObject getMusicQueJson() {
        return musicQueJson;
    }
    public static void setMusicQueJson(JSONObject musicQueJson) {
        App.musicQueJson = musicQueJson;
    }
    public static JSONObject getMusicStateJson() {
        return musicStateJson;
    }
    public static void setMusicStateJson(JSONObject musicStateJson) {
        App.musicStateJson = musicStateJson;
    }
    public static JSONObject getUpdatedDeviceJson() {
        return updatedDeviceJson;
    }
    public static void setUpdatedDeviceJson(JSONObject updatedDeviceJson) {
        App.updatedDeviceJson = updatedDeviceJson;
    }
    public static JSONObject getGroupJson() {
        return getData(GROUPS);
    }
    public static void setGroupJson(JSONObject groupJson) {
        App.groupJson = groupJson;
    }
    public static JSONObject getNotifierlistJson() {
        return notifierlistJson;
    }
    public static void setNotifierlistJson(JSONObject notifierlistJson) {
        App.notifierlistJson = notifierlistJson;
    }
    public static JSONObject getHvacZoneProvisionedJson() {
        return getData(HVAC_PROVISIONED);
    }
    public static void setHvacZoneProvisionedJson(JSONObject hvacZoneProvisionedJson) {
        App.hvacZoneProvisionedJson = hvacZoneProvisionedJson;
    }
    static  JSONObject hvacZoneProvisionedJson;
    public static JSONObject getSensorsProvisionedJson() {
        return sensorsProvisionedJson;
    }
    public static void setSensorsProvisionedJson(JSONObject sensorsProvisionedJson) {
        App.sensorsProvisionedJson = sensorsProvisionedJson;
    }
    static JSONObject sensorsProvisionedJson;
    static JSONObject defaultSoundJsonObject;
    static JSONObject provionalDevicesJsonObject;
    static JSONObject changePassJson;
    public static JSONObject getLoginEmitJson() {
        return loginEmitJson;
    }
    public static void setLoginEmitJson(JSONObject loginEmitJson) {
        App.loginEmitJson = loginEmitJson;
    }
    static JSONObject loginEmitJson;
    public static JSONObject getWaterFiterNotifJson() {
        return waterFiterNotifJson;
    }
    public static void setWaterFiterNotifJson(JSONObject waterFiterNotifJson) {
        App.waterFiterNotifJson = waterFiterNotifJson;
    }
    public static JSONObject getAirFiterNotifJson() {
        return airFiterNotifJson;
    }

    public static JSONObject getDawnNotifJson() {
        return getData(DAWN_NOTIFICATION);
    }

    public static JSONObject getSleepNotifJson() {
        return getData(SLEEP_NOTIFICATION);
    }
    public static void setAirFiterNotifJson(JSONObject airFiterNotifJson) {
        App.airFiterNotifJson = airFiterNotifJson;
    }
    static JSONObject dawnNotifJson;
    static JSONObject sleepNotifJson;
    static JSONObject waterFiterNotifJson;
    static JSONObject airFiterNotifJson;
    static JSONObject hubConnectionJsonObject;
    static JSONObject currentMusicPlayListJson;
    static JSONObject deletedSimulationJson;
    static JSONObject deletedSleepSimulationJson;
    static JSONObject sleep_closed;


    public static JSONObject get_sleep_popup_closed() {
        return sleep_closed;
    }
    public static void set_sleep_popup_closed(JSONObject set_sleep_closed) {
        App.sleep_closed = set_sleep_closed;
    }

    public static JSONObject getDeletedSimulationJson() {
        return deletedSimulationJson;
    }
    public static void setDeletedSimulationJson(JSONObject deletedSimulationJson) {
        App.deletedSimulationJson = deletedSimulationJson;
    }
    public static JSONObject getDeletedSleepSimulationJson() {
        return deletedSleepSimulationJson;
    }
    public static void setDeletedSleepSimulationJson(JSONObject deletedSleepSimulationJson) {
        App.deletedSleepSimulationJson = deletedSleepSimulationJson;
    }
    public static JSONObject getEmailJson() {
        return emailJson;
    }
    public static void setEmailJson(JSONObject emailJson) {
        App.emailJson = emailJson;
    }
    static JSONObject emailJson;
    static JSONObject updatedDeviceJson;
    static JSONObject notificationJson;
    static JSONObject notifierlistJson;
    static JSONObject soundJsonObject;
    static JSONObject groupJson;
    static JSONObject devicesByRoomJsonObject;
    static JSONObject musicStateJson;
    static JSONObject musicQueJson;
    public static HashMap<String, String> getDefault_song_hash() {
        return default_song_hash;
    }
    public static void setDefault_song_hash(HashMap<String, String> default_song_hash) {
        App.default_song_hash = default_song_hash;
    }
    static HashMap<String,String> default_song_hash=new HashMap<>();
    public static JSONObject getHomeMuusicQueJson() {
        return homeMuusicQueJson;
    }
    public static void setHomeMuusicQueJson(JSONObject homeMuusicQueJson) {
        App.homeMuusicQueJson = homeMuusicQueJson;
    }
    static JSONObject homeMuusicQueJson;
    public static JSONObject getGroupByRoomJsonObject() {
        return getData(GROUPS_BY_ROOM);
    }
    public static void setGroupByRoomJsonObject(JSONObject groupByRoomJsonObject) {
        App.groupByRoomJsonObject = groupByRoomJsonObject;
    }
    static JSONObject groupByRoomJsonObject;
    static JSONObject musicPlaylistJson;
    public static JSONObject getRadioStationlistJson() {
        return radioStationlistJson;
    }
    public static void setRadioStationlistJson(JSONObject radioStationlistJson) {
        App.radioStationlistJson = radioStationlistJson;
    }
    static JSONObject radioStationlistJson;
    static JSONObject zoneJson;
    static JSONObject networkInfoJson;
    static JSONObject currenMusicJson;
    static JSONObject dateTimeJson;
    static JSONObject unassignedJson;
    static JSONObject ssidJson;
    static JSONObject musicPlayListJson;
    static JSONObject favJObject;
    static JSONObject selectedDeviceJson;
    static JSONObject loginJsonObject;
    static JSONObject locationJson;
    static JSONObject hubInfoJson;
    static JSONObject musicDataJsonObject;
    public static JSONObject getHub_installation_projess_json() {
        return App.hub_installation_projess_json;
    }
    public static void setHub_installation_projess_json(JSONObject hub_installation_projess_json) {
        App.hub_installation_projess_json = hub_installation_projess_json;
    }
    static JSONObject hub_installation_projess_json;
    static JSONObject simulationJsonObject;
    static JSONObject graphJsonObject;
    static JSONObject notifyJsonObject;
    static JSONObject roomJsonObject;
    static JSONObject sensorJsonObject;
    static JSONObject sleepJsonObject;
    static JSONObject DawnAwakeObject;
    static JSONObject DawnSnoozeObject;
    static JSONObject Current_actionJson;
    static JSONObject weather_conditionJson;
    static JSONObject breezometer_dataJson;
    static JSONObject allsimulationJsonObject;
    static JSONObject roomDeviceJsonObject;

    public static JSONObject getPackageDetailsJsonObject() {
        return packageDetailsJsonObject;
    }

    public static void setPackageDetailsJsonObject(JSONObject packageDetailsJsonObject) {
        App.packageDetailsJsonObject = packageDetailsJsonObject;
    }

    static JSONObject packageDetailsJsonObject;
    //static  JSONObject loginJsonObject,locationJson,hubInfoJson,provionalDevicesJsonObject,devicesByRoomJsonObject,musicDataJsonObject,favJObject,simulationJsonObject,roomJsonObject,sensorJsonObject;
    public static boolean music_change=false;
    public static void setResetJson()
    {
        dateTimeJson=null;
        currentMusicPlayListJson=null;
        musicPlayListJson=null;
        favJObject=null;
        selectedDeviceJson=null;
        hubConnectionJsonObject=null;
        loginJsonObject=null;
        locationJson=null;
        hubInfoJson=null;
        provionalDevicesJsonObject=null;
        devicesByRoomJsonObject=null;
        musicDataJsonObject=null;
        simulationJsonObject=null;
        roomJsonObject=null;
        sensorJsonObject=null;
        sleepJsonObject=null;
        breezometer_dataJson=null;
        weather_conditionJson=null;
        Current_actionJson=null;
        hvacZoneProvisionedJson=null;
        zoneJson=null;
    }
    public static boolean isMusic_change() {
        return music_change;
    }
    public static void setMusic_change(boolean music_change) {
        App.music_change = music_change;
    }
    public static JSONObject getSelectedDeviceJson() {
        return selectedDeviceJson;
    }
    public static void setSelectedDeviceJson(JSONObject selectedDeviceJson) {
        App.selectedDeviceJson = selectedDeviceJson;
    }
    public static boolean isFlag_upload_prof() {
        return flag_upload_prof;
    }
    public static void setFlag_upload_prof(boolean flag_upload_prof) {
        App.flag_upload_prof = flag_upload_prof;
    }
    private static ArrayList<Subcriber> subcriberArrayList=new ArrayList<>();
    public static Room getRoom() {
        return room==null?new Room():room;
    }
    public static void setRoom(Room room) {
        App.room = room;
    }
    public static New_RoomDawn getDawnroom() {
        return dawnroom;
    }
    public static void setDawnroom(New_RoomDawn dawnroom) {
        App.dawnroom = dawnroom;
    }
    public static New_RoomDawn getnewDawnroom() {
        return newdawnroom;
    }
    public static void setnewDawnroom(New_RoomDawn newdawnroom) {
        App.newdawnroom = newdawnroom;
    }
    private static Room room=new Room();
    private static New_RoomDawn dawnroom;
    private static New_RoomDawn newdawnroom;
    public static Zone getZone() {
        return zone;
    }
    public static void setZone(Zone zone) {
        App.zone = zone;
    }
    private static Zone zone;
    private static Device device;
    private static int airRemainingDays,waterRemainingDays;
    private static Fragment cfragment;
    private static Fragment fragment;
    private static Bundle temp_bundle=null;
    public static boolean isSound_test_flag() {
        return sound_test_flag;
    }
    public static void setSound_test_flag(boolean sound_test_flag) {
        App.sound_test_flag = sound_test_flag;
    }
    private static boolean sound_test_flag=false;
    public static Bundle getTemp_bundle() {
        return temp_bundle;
    }
    public static void setTemp_bundle(Bundle temp_bundle) {
        App.temp_bundle = temp_bundle;
    }
    private static String username;
    public static String getSigned_user() {
        return signed_user;
    }
    public static void setSigned_user(String signed_user) {
        App.signed_user = signed_user;
    }
    private static String signed_user="";
    public static String getCallfrom() {
        return callfrom;
    }
    public static void setCallfrom(String callfrom) {
        App.callfrom = callfrom;
    }
    private static String callfrom ="";
    /**********************view pager***************************/
    public static void setFrame_id(int mframe_id)
    {
        frame_id=mframe_id;
    }
    public static int getFrme_id()
    {
        return frame_id;
    }
    public static void setCurrentFragment(Fragment fragment)
    {
        cfragment=fragment;
    }
    public static Fragment getCurrentFragment()
    {
        return cfragment;
    }
    /**************************login socket connection************************/
    public static void setUsername(String username)
    {
        App.username=username;
    }
    public String getUsername()
    {
        return username;
    }
    public  static  Socket getLoginSocket() {
        return mloginsocket;
    }
    public static void setLoginSocket(Socket loginsocket) {
        mloginsocket = loginsocket;
    }
    public static void setSocket(Socket msocket1)
    {
        msocket=msocket1;
    }
    public static Socket getSocket()
    {
        return msocket;
    }
    public void setErrorMessage(String loginstate)
    {
        this.loginstate=loginstate;
    }
    public  String getErrorMessage()
    {
        return loginstate;
    }
    public static Context getContextForDialog() {
        return dialogcontext;
    }
    public void setContextForDialog(Context dialogcontext) {
        App.dialogcontext = dialogcontext;
    }
    public static void setSubcription(Subscription subcription)
    {
        msubcription=subcription;
    }
    public static Subscription getSubcription()
    {
        return msubcription;
    }
    /* public static void addSubcriber(Subcriber subcriber)
     {
         subcriberArrayList.add(subcriber);
     }
     public static Subcriber getSubcriber(int pos)
     {
         return subcriberArrayList.get(pos);
     }*/
    public static boolean isOrientationFlag() {
        return orientationFlag;
    }
    public static void setOrientationFlag(boolean orientationFlag) {
        App.orientationFlag = orientationFlag;
    }
    public static void setDefaultSoundData(JSONObject msoundJsonObject)
    {
        defaultSoundJsonObject =msoundJsonObject;
    }
    private static boolean orientationFlag=false;
    public static void setCurrentSubcriber(Subcriber msubcriber)
    {
        subcriber=msubcriber;
    }
    public static Subcriber getCurrentSubcriber()
    {
        return subcriber;
    }
    public static void setRoomData(JSONObject mroomJsonObject)
    {
        roomJsonObject=mroomJsonObject;
    }
    public static JSONObject getRoomData()
    {
        return getData(ROOMS);
    }
    public static JSONObject getSoundData()
    {
        return defaultSoundJsonObject;
    }
    /*public static void setTriggedData(JSONObject triggedJsonObject)
    {
        simulationTriggedObject=triggedJsonObject;
    }
    public static JSONObject getTriggedData()
    {
        return simulationTriggedObject;
    }*/
    public static void setNotificationData(JSONObject notiJsonObject)
    {
        notifyJsonObject=notiJsonObject;
    }
    public static JSONObject getNotificationData()
    {
        return notifyJsonObject;
    }
    public static void setSimulationData(JSONObject msimulationJsonObject)
    {
        simulationJsonObject=msimulationJsonObject;
    }
    public static JSONObject getSimulationData()
    {
        return simulationJsonObject;
    }
    ////////
    public static void setGraphData(JSONObject getJsonObject)
    {
        graphJsonObject=getJsonObject;
    }
    public static JSONObject getGraphData()
    {
        return graphJsonObject;
    }
    public static void setMusicData(JSONObject mmusicDataJsonObject)
    {
        musicDataJsonObject=mmusicDataJsonObject;
    }
    public static JSONObject getMusicData()
    {
        return musicDataJsonObject;
    }
    public static void setProvisionalDevicesData(JSONObject mprovionalDevicesJsonObject)
    {
        provionalDevicesJsonObject=mprovionalDevicesJsonObject;
    }
    public static JSONObject getProvisionalDevicesData()
    {
        return getData(PROVISIONAL_DEVICES);
    }
    public static JSONObject getProvisionalDevicesJson()
    {
        try {
            return App.getDataStorageJson().getJSONObject(PROVISIONAL_DEVICES);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void setDevicesByRoomData(JSONObject mdevicesByRoomJsonObject)
    {
        devicesByRoomJsonObject=mdevicesByRoomJsonObject;
    }
    public static JSONObject getDevicesByRoomData()
    {
        return getData(DEVICES_BY_ROOM);
    }
    public static JSONObject getDevicesByRoomJson() throws JSONException {
        return App.getDataStorageJson().getJSONObject(DEVICES_BY_ROOM);
    }
    public static void setLoginData(JSONObject mloginJsonObject)
    {
        loginJsonObject=mloginJsonObject;
    }
    public static JSONObject getLoginData()
    {
        return loginJsonObject;
    }
    public static boolean isNotiSet() {
        return notiSet;
    }
    public static void setNotiSet(boolean notiSet) {
        App.notiSet = notiSet;
    }
    public static  boolean notiSet=false;
    public static JSONObject getNotificationJson() {
        return notificationJson;
    }
    public static void setNotificationJson(JSONObject notificationJson) {
        App.notificationJson = notificationJson;
    }
    public static void setSound(Sound msound)
    {
        sound=msound;
    }
    public static Sound getSound()
    {
        return sound;
    }
    public static void setAirRemainingDays(int mairRemainingDays)
    {
        airRemainingDays=mairRemainingDays;
    }
    public static int getAirRemainingDays()
    {
        return airRemainingDays;
    }
    public static void setWaterRemainingDays(int mwaterRemainingDays)
    {
        waterRemainingDays=mwaterRemainingDays;
    }
    public static int getWaterRemainingDays()
    {
        return waterRemainingDays;
    }
    public static void setHubConnectionData(JSONObject mhubConnectionJsonObject)
    {
        hubConnectionJsonObject=mhubConnectionJsonObject;
    }
    public static JSONObject getHubConnectionData()
    {
        return hubConnectionJsonObject;
    }
    public static void setDevice(Device mdevice)
    {
        device=mdevice;
    }
    public static Device getDevice()
    {
        return device==null?new Device():device;
    }
    public static void setSensorsData(JSONObject msensorJsonObject)
    {
        sensorJsonObject=msensorJsonObject;
    }
    public static JSONObject getSensorsData()
    {
        return sensorJsonObject;
    }
    public static void setHubInfo(JSONObject mhubInfoJson)
    {
        hubInfoJson=mhubInfoJson;
    }
    public static JSONObject getHubInfo()
    {
        return hubInfoJson;
    }
    public static void setLocationInFo(JSONObject mlocationJson)
    {
        locationJson=mlocationJson;
    }
    public static JSONObject getLocationInfo()
    {
        return locationJson;
    }
    public static void setFragment(Fragment mfragment)
    {
        fragment=mfragment;
    }
    public static Fragment getFragment()
    {
        return fragment;
    }
    public static void setFavData(JSONObject favJsonObject)
    {
        favJObject=favJsonObject;
    }
    public static JSONObject getFavData()
    {
        return getData(FAVORITES);
    }
    public static void clearAppJSon()
    {
        soundJsonObject=null;
        groupJson =null;musicStateJson=null;musicQueJson=null;musicPlaylistJson=null;zoneJson=null;networkInfoJson=null;currenMusicJson=null;dateTimeJson=null;unassignedJson=null;ssidJson=null;currentMusicPlayListJson=null;musicPlayListJson=null;favJObject=null;selectedDeviceJson=null;hubConnectionJsonObject=null;loginJsonObject=null;locationJson=null;hubInfoJson=null;provionalDevicesJsonObject=null;devicesByRoomJsonObject=null;musicDataJsonObject=null;simulationJsonObject=null;roomJsonObject=null;sensorJsonObject=null;
    }
    public static void setSleepSimulationData(JSONObject msimulationJsonObject)
    {
        sleepJsonObject=msimulationJsonObject;
    }
    public static JSONObject getSleepSimulationData() {
        return  sleepJsonObject;
    }

    public static void setDawnAwakeData(JSONObject DawnAwakeJsonObject)
    {
        DawnAwakeObject=DawnAwakeJsonObject;
    }
    public static JSONObject getDawnAwakeData() {
        return  DawnAwakeObject;
    }

    public static void setDawnSnoozeData(JSONObject DawnSnoozeJsonObject)
    {
        DawnSnoozeObject=DawnSnoozeJsonObject;
    }
    public static JSONObject getDawnSnoozeData() {
        return  DawnSnoozeObject;
    }


    public static void setRoomDeviceData(JSONObject mroomJsonObject)
    {
        roomDeviceJsonObject=mroomJsonObject;
    }
    public static JSONObject getRoomDeviceData()
    {
        return getData(ROOMS_DEVICES);
    }
    public static void setAllSimulationData(JSONObject msimulationJsonObject)
    {
        allsimulationJsonObject=msimulationJsonObject;
    }
    public static JSONObject getAllSimulationData()
    {
        return allsimulationJsonObject;
    }
    public static Subcriber hubsubcriber;
    public static void setcurrent_actions(JSONObject mCurrent_actionJson)
    {
        Current_actionJson=mCurrent_actionJson;
    }
    public static JSONObject getcurrent_actions()
    {
        return Current_actionJson;
    }
    public static void setbreezometer_data(JSONObject mbreezometerJson)
    {
        breezometer_dataJson=mbreezometerJson;
    }
    public static JSONObject getbreezometer_data()
    {
        return breezometer_dataJson;
    }
    public static void setweather_condition(JSONObject mWeatherJson)
    {
        weather_conditionJson=mWeatherJson;
    }
    public static JSONObject getweather_condition()
    {
        return weather_conditionJson;
    }

    public static JSONObject getDataStorageJson() {
        return dataStorageJson;
    }

    public static void setDataStorageJson(JSONObject dataStorageJson) {
        App.dataStorageJson = dataStorageJson;
    }

    private static JSONObject dataStorageJson;

    private static JSONObject getData(String type)
    {    JSONArray jsonArray = new JSONArray();
        try {

            Iterator<String> keys = App.getDataStorageJson().getJSONObject(type).keys();
            while (keys.hasNext()) {
                jsonArray.put(App.getDataStorageJson().getJSONObject(type).getJSONObject(keys.next()));
            }
            return new JSONObject().put("data",jsonArray);
        }
        catch (Exception ex)
        {
            Logs.error("AppClass",""+ex.getMessage());
        }
        return null;
    }


}