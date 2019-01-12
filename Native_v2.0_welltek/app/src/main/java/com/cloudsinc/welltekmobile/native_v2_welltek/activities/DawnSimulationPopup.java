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
import android.widget.Toast;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.SocketConnectionTest.SocketConnectionTest;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.ExperienceMainFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.ExperienceMainFragmentPortrait;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule.RemoveNotification;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.NotifyService;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.ScheduleClient;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.ScheduleClient_sleep;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.HomeHelthCall;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ScreenOrientation;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.TimeFormatChange;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Calendar;
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
 * Dawn pop up screen with Awake, Snooze and Sleep
 *
 * @author  Jaid Shaikh
 * @version 1.0
 */
public class DawnSimulationPopup extends AppCompatActivity {

    @BindView(R.id.txt_dawn_timer)TextView txt_dawn_timer;
    private String notif_type=""; //A
    JSONObject dawn_obj=new JSONObject();
    public String dawn_room_id_selected="";//room#12345
    public String dawn_room_name="";//Bedroom
    boolean isNetwork=false;
    String ns = Context.NOTIFICATION_SERVICE;
    @BindView(R.id.img_gif_loading)RelativeLayout img_gif_loading;
    private String time="";
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    private String TAG=DawnSimulationPopup.this.getClass().getSimpleName();
    Runnable runnable;
    Handler handler;
    @BindView(R.id.lyt_no_data)
    LinearLayout lyt_no_data;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.lyt_data)
    LinearLayout lyt_data;
    @BindView(R.id.no_text)
    TextView no_text;
    @BindView(R.id.txt_room_name)
    TextView txt_room_name;
    @BindView(R.id.btn_Snooze)
    Button btn_Snooze;
    @BindView(R.id.btn_Deactivate)
    Button btn_Deactivate;
    @BindView(R.id.btn_Awake)
    Button btn_Awake;
    private boolean visibility_flag=false;
    int hr=0;
    int min=0;
    int msg_id=0;
    PrefManager spm;
    private  int notify_mode; //0 or 1
    private  int notify_id; // 3456
    private String dawn_obj_id; //dawn#2345677
    private ScheduleClient scheduleClient;
    private ScheduleClient_sleep sleep_scheduleClient;
    String result="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dawn_notification_screen);
        UserInteractionSleep.siboSleepChecking(this);
        ScreenOrientation.setOrientation(this);
        ButterKnife.bind(this);
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
        spm= new PrefManager(DawnSimulationPopup.this);
        setSubcriber();
        try {
            if (App.getSocket() == null) {
                final SocketConnectionTest sct = new SocketConnectionTest();
                Logs.info(TAG + "DawnPopup_Ip_address", "" + spm.getValue("ip_address"));
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
                    if (App.getSocket() == null) {


                        DawnSimulationPopup.this.finish();
                        Intent i = new Intent(DawnSimulationPopup.this, ConnectionLostActivity.class);
                        DawnSimulationPopup.this.startActivity(i);
                    }

                }
            };



            if (isConnected(this)) {
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
            }
            handler = new Handler();
            handler.postDelayed(runnable, 10000);
        }catch (Exception e){
            Log.e("error",""+e.getMessage());
            //////Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        onNewIntent(getIntent());
    }
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
    @Override
    public void onNewIntent(Intent intent){
        Bundle extras = intent.getExtras();
        if(extras != null) {

            notif_type = getIntent().getStringExtra("notif_type");
            dawn_obj_id = getIntent().getStringExtra("dawn_obj_id");

            msg_id = extras.getInt("NotificationMessage");
            notif_type = extras.getString("notif_type");
            notify_id = extras.getInt("notify_id");


            result = intent.getStringExtra("toastMessage");
            Log.i("Toast: ", "" + result);

        }
    }
    private void setSubcriber() {
        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("");
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
                Logs.info(TAG+"_WhereIm","InDawnSimulationPopupDisplay"+string);
                if(string.equals("hub_connected"))
                {
                    if (App.getSocket()!=null) {
                        try {

                            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_dawn_simulations", new JSONObject()));
                            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_rooms", new JSONObject()));

                        } catch (Exception ex) {
                            Logs.error("Error_in_request",""+ex.getMessage());
                        }
                    }
                }

                if(string.equals("rooms")) {
                    getDawnList();
                }
                if(string.equals("dawn_simulation_notification"))setNotificationOperations(string, DawnSimulationPopup.this,scheduleClient,sleep_scheduleClient);
                if(string.equals("dawn_sim_done_awake")||string.equals("dawn_notif_deleted")||string.equals("dawn_sim_done_sleep_again")||string.equals("dawn_sim_done_snooze")) {
                    DawnSimulationPopup.this.finish();
                    Intent j = new Intent(DawnSimulationPopup.this, MainActivity.class);
                    DawnSimulationPopup.this.startActivity(j);
                }

            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setDawnPopupSubcriber(s);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handler!=null) handler.removeCallbacks(runnable);
    }
    private void getDawnList() {
        try {
            if(App.getSimulationData()!=null && !dawn_obj_id.equals("")) {
                JSONArray jsonArrayy = App.getSimulationData().getJSONArray("data");
                for (int i = 0; i < jsonArrayy.length(); i++) {
                    JSONObject jsonObject = jsonArrayy.getJSONObject(i);


                    try {
                        if (jsonObject.getString("CML_ID").equals(dawn_obj_id)) {
                            dawn_obj = jsonObject;
                            if (new PrefManager(this).getValue("time_fomat").equals("1")) {
                                time = (jsonObject.getInt("hour") < 10 ? "0" + jsonObject.getInt("hour") : jsonObject.getInt("hour")) + ":" + (jsonObject.getInt("minutes") < 10 ? "0" + jsonObject.getInt("minutes") : jsonObject.getInt("minutes"));
                            } else {
                                time = TimeFormatChange.convert12(time = (jsonObject.getInt("hour") < 10 ? "0" + jsonObject.getInt("hour") : jsonObject.getInt("hour")) + ":" + (jsonObject.getInt("minutes") < 10 ? "0" + jsonObject.getInt("minutes") : jsonObject.getInt("minutes")));
                            }
                            dawn_room_id_selected = jsonObject.getString("room");
                            txt_dawn_timer.setText(time.toLowerCase());
                            min = jsonObject.getInt("minutes");
                            hr = jsonObject.getInt("hour");
                            visibility_flag = true;
                        }
                        else {
                           // visibility_flag = false;
                            Log.i("Dawn_id","Not match");
                        }
                    }catch (Exception e){
                        Log.e("Exceptionrrrrrrrrrrr",""+e.getMessage());
                    }
                }
                if(App.getRoomData()!=null) {
                    try {
                        JSONArray jsonArray = App.getRoomData().getJSONArray("data");
                        Log.e("Data",""+jsonArray);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.i("InsideDawnpopup",""+dawn_room_id_selected);
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if (jsonObject.getString("CML_ID").equals(dawn_room_id_selected)) {
                                dawn_room_name = jsonObject.getString("CML_TITLE");
                                Log.i("NAmepopup",""+dawn_room_name);
                                txt_room_name.setText(""+dawn_room_name);
                            }
                        }
                    }
                    catch(Exception e){
                        Log.e("error_again",""+e.getMessage());
                    }
                } else {
                    Log.i("Dawn_id","Room_Data_Not match");
                    lyt_data.setVisibility(View.GONE);
                    // progress_bar.setVisibility(View.GONE);
                    img_gif_loading.setVisibility(View.GONE);
                    lyt_no_data.setVisibility(View.VISIBLE);
                    no_text.setText("No Rooms Available ");

                }
                /////
                if(visibility_flag){
                    lyt_no_data.setVisibility(View.GONE);
                    // progress_bar.setVisibility(View.GONE);
                    img_gif_loading.setVisibility(View.GONE);
                    lyt_data.setVisibility(View.VISIBLE);
                }
                else
                {
                    lyt_data.setVisibility(View.GONE);
                    // progress_bar.setVisibility(View.GONE);
                    img_gif_loading.setVisibility(View.GONE);
                    lyt_no_data.setVisibility(View.VISIBLE);
                    no_text.setText("Selected Dawn simulation seems to be deleted or updated");

                }
            }
            else {
                lyt_data.setVisibility(View.GONE);
                img_gif_loading.setVisibility(View.GONE);
                //progress_bar.setVisibility(View.GONE);
                lyt_no_data.setVisibility(View.VISIBLE);
                no_text.setText("Selected Dawn simulation seems to be deleted or updated");
            }
        }
        catch (Exception ex)
        {
            Logs.info(TAG+"NotificationError",""+ex.getMessage());
        }
    }
    @OnClick(R.id.btn_Deactivate)
    public void btn_Deactivate() {

        deactivateDawnSin();

    }



    @OnClick(R.id.btn_Awake)
    public void btn_Awake() {

        awakeDawnSim();
    }



    @OnClick(R.id.btn_Snooze)
    public void btn_Snooze() {
       snoozeDawnSim();
    }


    @OnClick(R.id.btn_ok)
    public void btn_ok() {
        if(!isNetwork){
            NotificationManager nMgr = (NotificationManager) getApplication().getSystemService(ns);
            nMgr.cancel(msg_id);
        }
        DawnSimulationPopup.this.finish();
        Intent j = new Intent(this, MainActivity.class);
        //  j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        DawnSimulationPopup.this.startActivity(j);
    }
    @OnClick(R.id.btn_back)
    public void btn_back() {
        DawnSimulationPopup.this.finish();
        Intent j = new Intent(this, MainActivity.class);
        // j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        DawnSimulationPopup.this.startActivity(j);
    }
    public void onBackPressed() {
    }

    private void deactivateDawnSin() {

        btn_Deactivate.setBackground(this.getResources().getDrawable(R.drawable.btn_fill_color_white));
        App.setCallfrom("stop_dawn_notification");
        JSONObject obj = new JSONObject();
        try {
            if (App.getSocket()!=null) {
                obj.put("data", dawn_obj);
                obj.put("type", "sleep_again");
                Logs.info(TAG,"......"+obj);
                App.getSocket().emit("action", obj);
            }
        } catch (JSONException e) {
            Log.e("Notificaton_sleep_again",""+e.getMessage());
        }
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        DawnSimulationPopup.this.finish();
    }

    private void awakeDawnSim() {


        btn_Awake.setBackground(this.getResources().getDrawable(R.drawable.btn_fill_color_white));
        App.setCallfrom("stop_dawn_notification");
        JSONObject obj = new JSONObject();
        try {
            if (App.getSocket()!=null) {
                obj.put("data", dawn_obj);
                obj.put("type", "awake");
                Logs.info(TAG,"......"+obj);
                App.getSocket().emit("action", obj);
            }
            else {
                //////Toast.makeText(this, "No Socket to stop_dawn_simulation", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Log.e("Notificaton_stop_dawn",""+e.getMessage());
        }
    }

    private void snoozeDawnSim() {

        btn_Snooze.setBackground(this.getResources().getDrawable(R.drawable.btn_fill_color_white));
        App.setCallfrom("snooze_dawn_notification");
        JSONObject obj = new JSONObject();
        try {
            if (App.getSocket()!=null) {
                obj.put("data", dawn_obj);
                obj.put("type", "snooze_dawn_simulation");
                Logs.info(TAG,"......"+obj);
                App.getSocket().emit("action", obj);
            }
            else {
                //Toast.makeText(this, "No Socket to snooze_dawn_simulation", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Log.e("Notificaton_snooze_dawn",""+e.getMessage());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        UserInteractionSleep.siboSleepChecking(this);
        if(App.getSocket()!=null)
        {

            if(!App.getSocket().connected())
            {
                App.setCallfrom("MainActivity");
                DawnSimulationPopup.this.finish();
                Intent i = new Intent(DawnSimulationPopup.this, ConnectionLostActivity.class);
                DawnSimulationPopup.this.startActivity(i);
            }

        }
    }



    @Override
    protected void onStop() {
        super.onStop();
        Logs.info(TAG, "------i am in onstop");
        UserInteractionSleep.stopHandler();
    }
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        UserInteractionSleep.userInteract(this);
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

}