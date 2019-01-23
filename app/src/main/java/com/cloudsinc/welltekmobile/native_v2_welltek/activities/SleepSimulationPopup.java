package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.SocketConnectionTest.SocketConnectionTest;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule.RemoveNotification;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.NotifyService_sleep;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.ScheduleClient;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.ScheduleClient_sleep;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ScreenOrientation;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.TimeFormatChange;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity.setNotificationOperations;
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
 * Created by developers on 24/1/18.
 */

public class SleepSimulationPopup extends AppCompatActivity {

    public int counter;
    @BindView(R.id.txt_sleep_timer)TextView txt_sleep_timer;

    @BindView(R.id.lyt_no_data)
    LinearLayout lyt_no_data;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.lyt_data)
    LinearLayout lyt_data;
    @BindView(R.id.txt_room_name)
    TextView txt_room_name;
    @BindView(R.id.no_text)
    TextView no_text;

    @BindView(R.id.btn_ok)Button btn_ok;
    @BindView(R.id.btn_cancel)Button btn_cancel;
    private boolean visibility_flag=false;
    int hr=0;
    int min=0;
    private String notif_type="";
    public String sleep_room_name="";
    int msg_id=0;
    String ns = Context.NOTIFICATION_SERVICE;
    boolean isNetwork=false;

    Fragment mfragment= null;
    private String sleep_id ="";
    public String sleep_room_id_selected="";
    JSONObject sleep_obj=new JSONObject();
    // final PrefManager spm = new PrefManager(getApplicationContext());

    private Observable<String> mobservable;
    private Observer<String> myObserver;
    private String TAG=SleepSimulationPopup.this.getClass().getSimpleName();
    private String time="";
    @BindView(R.id.img_gif_loading)RelativeLayout img_gif_loading;
    Runnable runnable;
    Handler handler;
    PrefManager spm;
    private  int notify_mode; //0 or 1
    private  int notify_id; // 3456
    private String sleep_obj_id; //sleep#2345677

    private ScheduleClient scheduleClient;
    private ScheduleClient_sleep sleep_scheduleClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleep_notification_screen);
        ScreenOrientation.setOrientation(this);
        ButterKnife.bind(this);
        UserInteractionSleep.siboSleepChecking(this);
        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();
        sleep_scheduleClient = new ScheduleClient_sleep(this);
        sleep_scheduleClient.doBindService();
        if (!isConnected(this)) {
            isNetwork=true;
            img_gif_loading.setVisibility(View.GONE);
            lyt_no_data.setVisibility(View.VISIBLE);
            no_text.setText("Please check your network connection and try again");

        }
        spm= new PrefManager(SleepSimulationPopup.this);
        setSubcriber();



        try {
            if (App.getSocket() == null) {
                final SocketConnectionTest sct = new SocketConnectionTest();
                Logs.info(TAG + "Sleep_Ip_address", "" + spm.getValue("ip_address"));
                if(!spm.getValue("ip_address").equals("No Record")) {
                    initializeDataBase();
                    sct.connect("http://" + spm.getValue("ip_address") + "/action", App.getSocket(), getApplicationContext());
                }
            } else {
                Observable.just("hub_connected").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(myObserver);
            }
            runnable = new Runnable() {
                @Override
                public void run() {
                    if (App.getSimulationData() == null) {
                        lyt_data.setVisibility(View.GONE);
                        img_gif_loading.setVisibility(View.GONE);
                        lyt_no_data.setVisibility(View.VISIBLE);
                        no_text.setText("No Data Available");
                    }

                }
            };

            handler = new Handler();
            handler.postDelayed(runnable, 10000);

        }catch (Exception e){

            Log.e("error",""+e.getMessage());
            img_gif_loading.setVisibility(View.GONE);
            no_text.setText("DARWIN is Offline. Try again");
            lyt_no_data.setVisibility(View.VISIBLE);        }
        onNewIntent(getIntent());


    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        UserInteractionSleep.userInteract(this);
    }
    @Override
    public void onNewIntent(Intent intent){
        Bundle extras = intent.getExtras();
        if(extras != null){
            notif_type=getIntent().getStringExtra("notif_type");
            sleep_obj_id=getIntent().getStringExtra("sleep_obj_id");

            msg_id = extras.getInt("NotificationMessage");
            notif_type=extras.getString("notif_type");
            notify_id=extras.getInt("notify_id");


            Log.i("onNewIntent_msg_id",""+msg_id);
            Log.i("onNewIntent_notif_type",""+notif_type);
            Log.i("onNewIntent_notify_id",""+notify_id);




        }

    }

    private void setSubcriber() {


        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext(""+App.getRoomData());
                        sub.onCompleted();
                    }
                }
        );



        myObserver = new Observer<String>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {



            }

            @Override
            public void onNext(String string) {


                Logs.info(TAG+"_WhereIm","InSleepDisplay");

                if(string.equals("hub_connected")) {
                    if (App.getSocket() != null) {
                        try {
                            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_sleep_simulations", new JSONObject()));
                            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_rooms", new JSONObject()));
                        } catch (Exception ex) {
                            Logs.error("Error_in_request", "" + ex.getMessage());
                        }
                    }
                }

                if(string.equals("rooms")) {
                    getSleepList();
                }
                if(string.equals("sleep_simulation_notification"))setNotificationOperations(string, SleepSimulationPopup.this,scheduleClient,sleep_scheduleClient);
                if(string.equals("sleep_popup_closed")||string.equals("sleep_notif_deleted")) {
                    SleepSimulationPopup.this.finish();
                    Intent j = new Intent(SleepSimulationPopup.this, MainActivity.class);
                    SleepSimulationPopup.this.startActivity(j);
                }

            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setSleepPopupSubcriber(s);


    }


    private void getSleepList() {

        try {


            if(App.getSleepSimulationData()!=null&&!sleep_obj_id.equals("")) {
                JSONArray jsonArrayy = App.getSleepSimulationData().getJSONArray("data");

                for (int i = 0; i < jsonArrayy.length(); i++) {
                    JSONObject jsonObject = jsonArrayy.getJSONObject(i);

                    try {

                        if (jsonObject.getString("CML_ID").equals(sleep_obj_id)) {
                            sleep_obj = jsonObject;
                            Log.i("sleep_obj", "" + sleep_obj);


                            if (new PrefManager(this).getValue("time_fomat").equals("1")) {
                                time = (jsonObject.getInt("hour") < 10 ? "0" + jsonObject.getInt("hour") : jsonObject.getInt("hour")) + ":" + (jsonObject.getInt("minutes") < 10 ? "0" + jsonObject.getInt("minutes") : jsonObject.getInt("minutes"));
                            } else {
                                time = TimeFormatChange.convert12(time = (jsonObject.getInt("hour") < 10 ? "0" + jsonObject.getInt("hour") : jsonObject.getInt("hour")) + ":" + (jsonObject.getInt("minutes") < 10 ? "0" + jsonObject.getInt("minutes") : jsonObject.getInt("minutes")));
                            }


                            sleep_room_id_selected = jsonObject.getString("room");

                            txt_sleep_timer.setText(time.toLowerCase());
                            min = jsonObject.getInt("minutes");
                            hr = jsonObject.getInt("hour");
                            visibility_flag = true;


                        }
                        else {
                            Log.i("Dawn_id","Not match");
                        }
                    }catch (Exception e){
                        Log.e("Exceptionrrrrrrrrrrr",""+e.getMessage());
                    }


                }


                if(App.getRoomData()!=null) {
                    try {


                        JSONArray jsonArray = App.getRoomData().getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            Log.i("InsideDawnpopup",""+sleep_room_id_selected);

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if (jsonObject.getString("CML_ID").equals(sleep_room_id_selected)) {
                                sleep_room_name = jsonObject.getString("CML_TITLE");
                                Log.i("NAmepopup",""+sleep_room_name);
                                txt_room_name.setText(""+sleep_room_name);
                            }
                        }
                    }
                    catch(Exception e){
                        Log.e("error_again",""+e.getMessage());
                    }
                } else {
                    Log.i("Sleep_id","SleepRoom_Data_Not match");
                }


                /////
                if(visibility_flag==true){


                    lyt_no_data.setVisibility(View.GONE);
                    // progress_bar.setVisibility(View.GONE);
                    img_gif_loading.setVisibility(View.GONE);
                    lyt_data.setVisibility(View.VISIBLE);
                }
                else
                {


                    // progress_bar.setVisibility(View.GONE);
                    img_gif_loading.setVisibility(View.GONE);
                    lyt_no_data.setVisibility(View.VISIBLE);

                }
            }
            else {

                lyt_data.setVisibility(View.GONE);
                img_gif_loading.setVisibility(View.GONE);
                //progress_bar.setVisibility(View.GONE);
                lyt_no_data.setVisibility(View.VISIBLE);
            }

        }
        catch (Exception ex)
        {
            Logs.info(TAG+"NotificationError",""+ex.getMessage());
        }

    }




    /************/
    @OnClick(R.id.btn_cancel)
    public void btn_cancel() {
        btn_cancel.setBackground(this.getResources().getDrawable(R.drawable.btn_fill_color_white));
        App.setCallfrom("sleep_notification");
        Intent i = new Intent(com.cloudsinc.welltekmobile.native_v2_welltek.activities.SleepSimulationPopup.this, MainActivity.class);
        startActivity(i);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        SleepSimulationPopup.this.finish();

    }
    @OnClick(R.id.btn_ok)
    public void btn_ok() {
        btn_ok.setBackground(this.getResources().getDrawable(R.drawable.btn_fill_color_white));
        Log.e("room_power", "obj");
        App.setCallfrom("sleep_notification");
        App.setCallfrom("stop_sleep_notification");

        JSONObject obj = new JSONObject();
        try {
            if (App.getSocket() != null) {

                JSONObject dataJson = new JSONObject();
                dataJson.put("state", false);
                dataJson.put("room", sleep_room_id_selected);
                dataJson.put("schedule", sleep_obj_id);


                obj.put("data", dataJson);
                obj.put("type", "room_power");

                Log.e("room_power", "" + obj);
                App.getSocket().emit("action", obj);

                Intent ii = new Intent(this, MainActivity.class);
                startActivity(ii);
                ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                SleepSimulationPopup.this.finish();
            } else {
                lyt_data.setVisibility(View.GONE);
                img_gif_loading.setVisibility(View.GONE);
                no_text.setText("DARWIN is Offline. Try again");
                lyt_no_data.setVisibility(View.VISIBLE);

            }
        } catch (JSONException e) {
            Log.e("stopSleepActions_Noti", "" + e.getMessage());
        }


    }


    @OnClick(R.id.btn_nodata_ok)
    public void btn_nodata_ok() {
        if(!isNetwork){
            NotificationManager nMgr = (NotificationManager) getApplication().getSystemService(ns);
            nMgr.cancel(msg_id);
        }



        SleepSimulationPopup.this.finish();

        Intent j = new Intent(this, MainActivity.class);
        startActivity(j);
    }

    @OnClick(R.id.btn_back)
    public void btn_back() {
        SleepSimulationPopup.this.finish();

        Intent j = new Intent(this, MainActivity.class);
        startActivity(j);
    }
    public void onBackPressed() {
        //super.onBackPressed();
        //create a dialog to ask yes no question whether or not the user wants to exit
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handler!=null) handler.removeCallbacks(runnable);

    }
    private void removeNotification() {

        ////Toast.makeText(this, "insideRemove", Toast.LENGTH_SHORT).show();
        RemoveNotification.removeSpecificSleepNotification(SleepSimulationPopup.this,sleep_obj_id);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Logs.info(TAG, "------i am in onResume");
        UserInteractionSleep.siboSleepChecking(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        Logs.info(TAG, "------i am in onstop");
        UserInteractionSleep.stopHandler();
    }

    public void initializeDataBase()
    {
        PrefManager spm=new PrefManager(this);
        if(spm.getValue("water_filter").equals("No Record"))spm.setValue("water_filter","1");
        if(spm.getValue("air_filter").equals("No Record"))spm.setValue("air_filter","1");
        try {
            JSONObject databaseobj=new JSONObject();
            databaseobj.put(ROOMS,new JSONObject());
            databaseobj.put(DEVICES_BY_ROOM,new JSONObject());
            databaseobj.put(ZONES  ,new JSONObject());
            databaseobj.put(PROVISIONAL_DEVICES  ,new JSONObject());
            databaseobj.put(PLAYLISTS  ,new JSONObject());
            databaseobj.put(GROUPS_BY_ROOM  ,new JSONObject());
            databaseobj.put(GROUPS  ,new JSONObject());
            databaseobj.put(FAVORITES  ,new JSONObject());
            databaseobj.put(ROOMS_DEVICES  ,new JSONObject());
            databaseobj.put(HVAC_PROVISIONED  ,new JSONObject());
            databaseobj.put(NETWORK_INFO  ,new JSONObject());
            databaseobj.put(DAWN_NOTIFICATION  ,new JSONObject());
            databaseobj.put(SLEEP_NOTIFICATION  ,new JSONObject());
            App.setDataStorageJson(databaseobj);
        }
        catch (Exception ex)
        {
            Logs.error("LoginActivity",""+ex.getMessage());
        }

    }
    // get Dawn Days

}