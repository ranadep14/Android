package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.airwaternotification.NotificationReceiver;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.CustomDialogShow;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.PinInputDialog;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.TimeZoneDialog;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.SocketConnectionTest.SocketConnectionTest;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.climate.ClimateByZoneFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.discover.DiscoverMainFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.ElementsMainFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.ElementsMainFragmentPortrait;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.ExperienceMainFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.ExperienceMainFragmentPortrait;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.all_experiences.Activities_FavoritesFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.favorites.FavoriteMenuMainFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.Filter_setting_MainFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.filters.FiltersFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.PrivacyPolicy;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.SettingMainFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.HomeHeathByZoneFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.NotifyService;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.ScheduleClient;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.ScheduleClient_sleep;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.HomeHelthCall;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.LogoutFunctionality;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.PackageOperations;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.PassowrdEncryptDecrypt;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ScreenOrientation;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;
import com.google.android.gms.analytics.Tracker;

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule.RemoveNotification.removeSpecificSleepNotification;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.NotificationSettingFragment.emitNotification;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.listeners.SocketListeners.cancleNotification;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.DARWIN_MOBILE;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.DARWIN_TABLET;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.SET;
/**
 * The MainActivity is lauching point of activities where all fragemnt will render
 *
 * @author  nikhil vharamble and jaid shaikh
 * @version 1.0
 * @since   2017-11-8
 */

public class MainActivity extends BaseActivity implements FragmentManager.OnBackStackChangedListener {



    public static boolean isAppWentToBg = false;
    final static int RQS_1 = 1;
    public static boolean isWindowFocused = false;



    public static boolean isBackPressed = false;
    @Nullable @BindView(R.id.lin_climate)LinearLayout lin_climate;
    @Nullable @BindView(R.id.lin_home)LinearLayout lin_home;
    @Nullable @BindView(R.id.lin_discover)LinearLayout lin_discover;
    @Nullable @BindView(R.id.lin_elements)LinearLayout lin_elements;
    @Nullable @BindView(R.id.lin_experiences)LinearLayout lin_experiences;
    @SuppressLint("StaticFieldLeak")
    public static MainActivity instance = null;
    @Nullable @BindView(R.id.lyt_family_name)LinearLayout lyt_family_name;
    @Nullable @BindView(R.id.txt_family_name)TextView txt_family_name;
    @Nullable @BindView(R.id.txt_family_initial)TextView txt_family_initial;
    @Nullable @BindView(R.id.rel_family_inisiatl)RelativeLayout rel_family_inisiatl;

    @Nullable @BindView(R.id.txt_whole_home_light)TextView txt_whole_home_light;
    @Nullable @BindView(R.id.txt_tab_climate)TextView txt_tab_climate;
    @Nullable @BindView(R.id.txt_tab_elements)TextView txt_tab_elements;
    @Nullable @BindView(R.id.txt_tab_experince)TextView txt_tab_experince;
    @Nullable @BindView(R.id.txt_tab_home)TextView txt_tab_home;
    @Nullable @BindView(R.id.txt_tab_discover)TextView txt_tab_discover;
    @Nullable @BindView(R.id.img_tab_experinces)ImageView img_tab_experinces;
    @Nullable @BindView(R.id.img_tab_elements)ImageView img_tab_elements;
    @Nullable @BindView(R.id.img_tab_home)ImageView img_tab_home;
    @Nullable @BindView(R.id.img_whole_home_light)ImageView img_whole_home_light;
    @Nullable @BindView(R.id.img_tab_discover)ImageView img_tab_discover;
    @Nullable @BindView(R.id.img_tab_climate)ImageView img_tab_climate;
    @Nullable @BindView(R.id.nav_view)NavigationView nav_view;
    @Nullable @BindView(R.id.lin_activity_main)LinearLayout lin_activity_main;
    DefaultArtifactVersion minBetaVersion = new DefaultArtifactVersion("0.1.1");
    DefaultArtifactVersion minProdVersion = new DefaultArtifactVersion("0.0.92");


    private String TAG=MainActivity.this.getClass().getSimpleName();

    String newString;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    boolean noti_flag=false;
    @SuppressLint("StaticFieldLeak")
    public static LinearLayout bottom_menu_bar;
    private View lyt;
    private String modelName = "", manufacturerName = "";
    @SuppressLint("StaticFieldLeak")
    public static DrawerLayout drawerLayout;


    JSONObject dawn_obj=new JSONObject();
    JSONObject sleep_obj=new JSONObject();
    private String dawn_id="";
    private String sleep_room_id="";

    Runnable runnable,login_runnable;
    Handler handler,login_handler;
    public static Dialog progress;
    Dialog save_location;
    @SuppressLint("StaticFieldLeak")
    public static TextView txt_progress_placeholder,txt_progress;
    PrefManager spm;
    Dialog intd;
    Dialog intd1;
    Dialog intd2;
    PinInputDialog intd_pin =null;

    TextView valid_user_name,valid_password;
    Button btn_signin;
    Runnable runnable1;
    Handler handler1;
    Runnable runnable2;
    Handler handler2;
    boolean home_screen_flag=false;
    LinearLayout mMainLayout;
    private  ScheduleClient scheduleClient;
    private  ScheduleClient_sleep sleep_scheduleClient;
    AlertDialog dialog = null;
    private String ns = Context.NOTIFICATION_SERVICE,str_email="",str_password="";
    boolean flag_passward=false;
    String notification_result="";
    String sleep_obj_id="";
    String dawn_obj_id="";
    boolean whole_light_flag=false;
    int c_hr=0;
    private Tracker mTracker;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ScreenOrientation.setOrientation(this);
        UserInteractionSleep.siboSleepChecking(this);
        Logs.info("" + getClass().getSimpleName(), "In Main Activity"+App.getCallfrom());
        ButterKnife.bind(this);
        hideSystemUI(this);
        lyt = findViewById(R.id.lytmainfont);
        drawerLayout = findViewById(R.id.drawer_layout);
        instance = this;
        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();
        sleep_scheduleClient = new ScheduleClient_sleep(this);
        sleep_scheduleClient.doBindService();
        Fragment mfragment = null;

        App application = (App)getApplication();
        mTracker = application.getDefaultTracker();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) registerReceiver(new NotificationReceiver(),new IntentFilter());
        App.setCurrentSubcriber(null);
        App.setMain_activity_subcriber(null);
        App.setHubsubcriber(null);
        setSubcriber();
        App.setFlag_main_activity_state(true);
        home_screen_flag=true;
        showDialog(MainActivity.this);
        startAnimationProgress(0);
        txt_progress_placeholder.setText(this.getString(R.string.after_hub_ip_connect_text));

        final Typeface mFont = Typeface.createFromAsset(getAssets(),
                "fonts/SF-UI-Text-Regular.otf");
        final ViewGroup mContainer = (ViewGroup) lyt.getRootView();

        FontUtility.setAppFont(mContainer, mFont);
        if(getIntent()!=null) notification_result=getIntent().getStringExtra("toastMessage");
        sleep_obj_id=getIntent().getStringExtra("sleep_obj_id");
        dawn_obj_id=getIntent().getStringExtra("dawn_obj_id");
        sleep_room_id=getIntent().getStringExtra("sleep_room_id");
        Logs.info(TAG,"reeeeeeeeeeeeeeeeeeeeeeeee"+notification_result+"eeeeeeeeeeeee"+sleep_obj_id+"eeeeeeeeeee"+sleep_room_id);
        if(notification_result!=null)dawnOperations(notification_result);

        if(App.getCallfrom().equals("demo_scene")){

            MainActivity.this.finish();
            FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
            transaction1.replace(R.id.frm_main_container, Activities_FavoritesFragment.newInstance());
            transaction1.addToBackStack(null);
            transaction1.commit();

        }



        spm = new PrefManager(MainActivity.this);
        lin_home.setBackgroundColor(this.getResources().getColor(R.color.tab_select_color));

        if (!App.isOrientationFlag()) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportFragmentManager().addOnBackStackChangedListener(this);
            shouldDisplayHomeUp();
        }

        bottom_menu_bar = findViewById(R.id.bottom_menu_bar);
        if (getIntent().hasExtra("call_from")) {
            noti_flag = true;
        }

        connectToHub();

        runnable = new Runnable() {
            @Override
            public void run() {
                if (App.getSocket() == null) {


                    MainActivity.this.finish();
                    Intent i = new Intent(MainActivity.this, ConnectionLostActivity.class);
                    MainActivity.this.startActivity(i);
                }

            }
        };

        handler = new Handler();
        handler.postDelayed(runnable, 15000);


        initializeDialog();
        Logs.info(TAG,"--------------------"+App.getCallfrom());
        if(!(App.getCallfrom().equals("HubConnection")||App.getCallfrom().equals("LoginActivity")||App.getCallfrom().equals("SignupActivity"))) {
            if(App.isOrientationFlag()) {
                App.setCallfrom("");
                if(!spm.getValue("pin_value").equals("No Record"))pinInputDialog();
            }
        }



        setWholeHomeValue();

    }

    public void initializeDialog()
    {
        save_location=new Dialog(this, R.style.DialogTheme);
        save_location.requestWindowFeature(Window.FEATURE_NO_TITLE);
        save_location.setContentView(R.layout.connection_lost);
        TextView txt_msg= save_location.findViewById(R.id.txt_msg);
        TextView txt_logout= save_location.findViewById(R.id.txt_logout);
        txt_logout.setPaintFlags(txt_logout.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Button btn_retry= save_location.findViewById(R.id.btn_retry);
        RelativeLayout rel_loading= save_location.findViewById(R.id.rel_loading);
        btn_retry.setVisibility(View.GONE);
        rel_loading.setVisibility(View.VISIBLE);

        txt_msg.setText(this.getResources().getString(R.string.save_location));
        txt_logout.setPaintFlags(txt_logout.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.getSocket()!=null)
                {
                    App.getSocket().off();
                    App.setSocket(null);
                }

                cancleNotification(MainActivity.this,"Air");
                cancleNotification(MainActivity.this,"Water");
                spm.setValue("jwt_token","No Record");
                spm.setValue("state_serial","No Record");
                spm.setValue("hub_serial","No Record");
                spm.setValue("Simulation_info","No Record");
                spm.setValue("connectin_to_hub","No Record");
                spm.setValue("ip_address","No Record");
                spm.setValue("clouzer_user","No Record");
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                MainActivity.this.startActivity(i);

            }
        });
        save_location.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // Prevent dialog close on back press button
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
    }

    @Optional
    @OnClick({R.id.lin_climate, R.id.lin_home, R.id.lin_discover, R.id.lin_elements,R.id.lin_experiences,R.id.lyt_family_name,R.id.lin_favorites})
    public void onClick(View view) {
        setDefaultColor();
        freeMemory();
        Fragment mfragment=null;
        String fav_fragment="";
        home_screen_flag=false;
        switch (view.getId())
        {
            case R.id.lin_climate:
                setSelection(lin_climate,txt_tab_climate,img_tab_climate);
                mfragment= ClimateByZoneFragment.newInstance();
                setDefaultHomeSelection();
                break;
            case R.id.lin_favorites:
                mfragment= FavoriteMenuMainFragment.newInstance();
                fav_fragment="fav_fragment";
                setDefaultHomeSelection();
                break;
            case R.id.lin_home:
                home_screen_flag=true;
                setSelection(lin_home,txt_tab_home,img_tab_home);
                mfragment= HomeHeathByZoneFragment.newInstance();
                bottom_menu_bar.setVisibility(View.VISIBLE);
                setDefaultHomeSelection();
                break;
            case R.id.lin_discover:
                setSelection(lin_discover,txt_tab_discover,img_tab_discover);
                mfragment= DiscoverMainFragment.newInstance();
                setDefaultHomeSelection();
                break;
            case R.id.lin_elements:
                setSelection(lin_elements,txt_tab_elements,img_tab_elements);
                Bundle bundle= new Bundle();
                bundle.putString("call_from","room");
                App.setTemp_bundle(bundle);
                if(App.isOrientationFlag()) mfragment= ElementsMainFragment.newInstance();
                else mfragment= ElementsMainFragmentPortrait.newInstance();

                setDefaultHomeSelection();
                break;
            case R.id.lin_experiences:
                setSelection(lin_experiences,txt_tab_experince,img_tab_experinces);
                if(App.isOrientationFlag()) mfragment= ExperienceMainFragment.newInstance();
                else mfragment= ExperienceMainFragmentPortrait.newInstance();
                setDefaultHomeSelection();

                break;
            case R.id.lyt_family_name:
                mfragment= Filter_setting_MainFragment.newInstance();
                rel_family_inisiatl.setBackground(getResources().getDrawable(R.drawable.circle_blue));
                lyt_family_name.setBackgroundColor(this.getResources().getColor(R.color.familyname_select_color));
                lyt_family_name.setEnabled(false);
                break;

        }
        if (fav_fragment.contentEquals(fav_fragment)){
            FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
            transaction1.replace(R.id.frm_main_container, mfragment);
            transaction1.addToBackStack(null);
            transaction1.commit();

        }else {
            FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
            transaction1.replace(R.id.frm_main_container, mfragment);
            transaction1.addToBackStack(null);
            transaction1.commit();

        }


        int currentOrientation=getResources().getConfiguration().orientation;
        if(currentOrientation == Configuration.ORIENTATION_PORTRAIT){
            reSetDefaultColorMenubar();
        }



    }


    @OnClick(R.id.lin_whole_home_light)
    public void lin_whole_home_light()
    {
        if(App.getSocket()!=null)
        {
            try {
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_power", new JSONObject().put("type", "Lighting").put("state", !whole_light_flag)));
            } catch (Exception ex) {
                Logs.error(TAG, "----------" + ex.getMessage());
            }
        }
    }


    public void setDefaultHomeSelection()
    {
        if(App.isOrientationFlag()) {
            rel_family_inisiatl.setBackground(getResources().getDrawable(R.drawable.circle_light_blue));
            lyt_family_name.setBackgroundColor(this.getResources().getColor(R.color.tab_family_name_color));
            lyt_family_name.setEnabled(true);
        }
    }
    private void reSetDefaultColorMenubar() {
        lin_experiences.setBackgroundColor(Color.TRANSPARENT);
        lin_elements.setBackgroundColor(Color.TRANSPARENT);
        lin_climate.setBackgroundColor(Color.TRANSPARENT);
        txt_tab_experince.setTextColor(Color.GRAY);
        txt_tab_elements.setTextColor(Color.GRAY);
        txt_tab_climate.setTextColor(Color.GRAY);

        img_tab_experinces.setColorFilter(this.getResources().getColor(R.color.menu_icon_color), PorterDuff.Mode.SRC_IN);
        img_tab_elements.setColorFilter(this.getResources().getColor(R.color.menu_icon_color), PorterDuff.Mode.SRC_IN);
        img_tab_climate.setColorFilter(this.getResources().getColor(R.color.menu_icon_color), PorterDuff.Mode.SRC_IN);

    }


    @Override
    public void finish() {
        super.finish();
        instance = null;
    }

    @Override
    protected void onStart() {

        if(!App.isOrientationFlag())getSupportActionBar().hide();
        applicationWillEnterForeground();

        super.onStart();

    }

    private void applicationWillEnterForeground() {
        if (isAppWentToBg) {
            App.setFlag_main_activity_state(true);
            isAppWentToBg = false;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handler!=null) handler.removeCallbacks(runnable);
        if(login_handler!=null) login_handler.removeCallbacks(login_runnable);
        if(handler1!=null) handler1.removeCallbacks(runnable1);
        System.gc();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logs.info(TAG,"------i am in onstop");
        UserInteractionSleep.stopHandler();
        applicationdidenterbackground();
    }

    public void applicationdidenterbackground() {
        if (!isWindowFocused) {

            isAppWentToBg = true;
            App.setFlag_main_activity_state(false);

        }
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        isWindowFocused = hasFocus;

        if (isBackPressed && !hasFocus) {
            isBackPressed = false;
            isWindowFocused = true;
        }

        hideSystemUI(this);


        super.onWindowFocusChanged(hasFocus);
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

                Logs.info(TAG+"WhereIM",""+string);
                if(string.equals("upgrade_package")) CustomDialogShow.dispDialogWithOK(MainActivity.this, getResources().getString(R.string.upgradepackage_msg));
                if(string.equals("upgrade_package_main")) dispAppCloseDialog(getResources().getString(R.string.upgradepackage_msg));


                if(string.contains("Whole Home"))
                {
                    setWholeHomeValue();
                }
                if(string.equals("lock_app"))
                {
                    if(App.isOrientationFlag()&&!spm.getValue("pin_value").equals("No Record")) pinInputDialog();
                }


                if(string.equals("hub_soft_resetted")) {
                    CustomDialogShow.dispDialogWithOK(MainActivity.this, getResources().getString(R.string.hub_softrest));
                }
                if(string.equals("saving_location"))
                {
                    showLocationSaveDialog();
                }


                if(string.equals("circadian_triggered"))
                {
                    UserInteractionSleep.siboSleepChecking(MainActivity.this);
                    checkCircadinaStatusCheck();
                }

                if(string.equals("hub_connected"))
                {

                    if(handler!=null) handler.removeCallbacks(runnable);
                    if(save_location!=null)
                    {
                        if(save_location.isShowing())
                        {
                            save_location.dismiss();
                        }
                    }
                }
                if(string.equals("hide_status_bar"))
                {
                    intd_pin=null;
                    hideSystemUI(MainActivity.this);
                }
                if(string.equals("hub_installation_process"))
                {
                    UserInteractionSleep.siboSleepChecking(MainActivity.this);
                    checkInstallerportalState();
                }

                if(string.equals("hub_connected")&&home_screen_flag)
                {


                    freeMemory();
                    startAnimationProgress(50);
                    txt_progress_placeholder.setText(MainActivity.this.getString(R.string.fetch_data_after_ip_connect));
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_provisioned", new JSONObject()));
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_weather_condition", new JSONObject()));
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_info", new JSONObject()));
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_rooms", new JSONObject()));
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_dawn_simulations", new JSONObject()));
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_sleep_simulations", new JSONObject()));
                    emitNotification("Water");
                    emitNotification("Air");
                    emitNotification("Dawn");
                    emitNotification("Sleep");
                    FragmentTransaction transaction1=getSupportFragmentManager().beginTransaction();
                    Fragment fragment;
                    fragment = HomeHeathByZoneFragment.newInstance();
                    transaction1.replace(R.id.frm_main_container, fragment);
                    transaction1.commitAllowingStateLoss();
                    setSelection(lin_home,txt_tab_home,img_tab_home);

                }

                setFamiltyName();
                if(string.equals("network_info")) {
                    UserInteractionSleep.siboSleepChecking(MainActivity.this);
                    checkHubVersion();
                }
                if(string.equals("info")) {


                    try {
                        JSONArray jsonArray = App.getHubInfo().getJSONArray("data");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        if(jsonObject.has("CML_SERIAL")) spm.setValue("hub_serial",jsonObject.getString("CML_SERIAL"));
                        // if(jsonObject.has("CML_PACKAGE_DETAILS"))setSelectedPackages();

                        if(jsonObject.has("CML_FLIC_KEY")) spm.setValue("flic_key",new String(Base64.encode(("Basic "+jsonObject.getString("CML_FLIC_KEY")).getBytes(), Base64.DEFAULT)));
                        System.out.println(jsonObject.getString("CML_ROLE")+"fffffffffffffffffff"+jsonObject.getString("CML_FLIC_KEY")+"ffffffffff"+ new String(Base64.encode(("Basic "+jsonObject.getString("CML_FLIC_KEY")).getBytes(), Base64.DEFAULT)));
                        if(jsonObject.has("CML_MESH"))
                        {
                            if(jsonObject.getBoolean("CML_MESH")) spm.setValue("hub_serial",jsonObject.getString("CML_MESH_ID"));
                        }

                        if(jsonObject.has("CML_ROLE"))
                        {
                            if(jsonObject.getString("CML_ROLE").equals("SLAVE"))
                            {
                                MainActivity.this.finish();
                                Intent intent=new Intent(MainActivity.this,ConnectionLostActivity.class);
                                startActivity(intent);
                                App.setSocket(null);
                            }
                        }
                        if ((jsonObject.has("demo_status"))&&jsonObject.getBoolean("demo_status")==true)
                        {

                            if(intd==null) {
                                intd = new Dialog(MainActivity.this, R.style.DialogTheme);
                                intd.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                intd.setContentView(R.layout.activity_demo_scene_popup);
                                RelativeLayout rel_main=intd.findViewById(R.id.rel_main);
                                rel_main.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View view, MotionEvent motionEvent) {
                                        UserInteractionSleep.userInteract(MainActivity.this);
                                        return false;
                                    }
                                });
                                intd.setCanceledOnTouchOutside(false);
                                intd.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                                intd.show();
                                intd.setCancelable(false);
                            }

                        }
                        else
                        {
                            if(intd!=null)
                            {
                                intd.dismiss();
                                intd=null;
                            }
                        }


                    } catch (Exception e) {
                        Logs.error(TAG,"-----------------"+e.getMessage());
                    }


                }

                if(string.equals("authentication_error_409"))
                {
                    if (App.getSocket()!=null)
                    {
                        App.getSocket().off();
                        App.setSocket(null);
                    }


        /*cancleNotification("Air");
        cancleNotification("Water");*/
                    spm.setValue("jwt_token","No Record");
                    spm.setValue("state_serial","No Record");
                    spm.setValue("hub_serial","No Record");
                    spm.setValue("Simulation_info","No Record");
                    spm.setValue("connectin_to_hub","No Record");
                    spm.setValue("ip_address","No Record");
                    spm.setValue("clouzer_user","No Record");
                    App.setCallfrom("authentication_error_409");
                    MainActivity.this.finishAffinity();
                    Intent i=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(i);
                }
                if(string.equals("authentication_error_401")) {

                    startAnimationProgress(30);
                    txt_progress_placeholder.setText(""+getResources().getString(R.string.authentication));
                    connectAndEmitLoginCall();

                }
                setNotificationOperations(string,MainActivity.this,scheduleClient,sleep_scheduleClient);


            }

        } ;
        Subcriber s = new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setMain_activity_subcriber(s);



    }
    /**********Get Running Status*********/
    private boolean getDawnState(String room_id,String dawn_id)
    {
        boolean b=false;
        try {
            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_ID").equals(room_id))
                {
                    if (jsonObject.has("DAWN_RUNNING")&&jsonObject.has("DAWN_ID") && jsonObject.getString("DAWN_ID").equals(dawn_id)) {
                        /*  b = dawn_id.equals("")?jsonObject.getBoolean("DAWN_RUNNING"):(jsonObject.getBoolean("DAWN_RUNNING")||jsonObject.getString("DAWN_ID").equals(dawn_id));*/

                        b=true;

                    }else {
                        b=false;
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Logs.error("MAinRoomDevicesFrag_DawnRunninError",""+ex.getMessage());
        }
        return  b;
    }




    public static void setNotificationOperations(String string,Context mcontext,ScheduleClient scheduleClient,ScheduleClient_sleep sleep_scheduleClient)
    {

        int dawn_mode;// 0 for non-repeat && 1 for repeate
        int dawn_mode_2;// 0 for non-repeat && 1 for repeate
        int repeat_no_one=0;
        int repeat_no_two=0;
        Random random1=new Random();
        int max=9000;
        int min=1000;
        int a=random1.nextInt(max-min+1)+min;

        Random random2=new Random();
        int max1=9000;
        int min1=1000;
        int ramdon_no=random1.nextInt(max1-min1+1)+min1;


        int dawn_NOTIFICATION_id = Math.abs(a);

        int Dawn_NOTIFICATION_id1=dawn_NOTIFICATION_id+1;
        int sleep_NOTIFICATION_id1=dawn_NOTIFICATION_id+1;
        int sleep_mode;// 0 for non-repeat && 1 for repeate

        PrefManager spm = new PrefManager(mcontext);

        if(string.equals("dawn_sim_done_awake") || string.equals("dawn_sim_done_sleep_again")|| string.equals("dawn_sim_done_snooze")) {
            try {

                String dawn_sim_id="";

                AlarmManager alarmManager = (AlarmManager) mcontext.getSystemService(Context.ALARM_SERVICE);
                NotificationManager nMgr = (NotificationManager) mcontext.getSystemService(Context.NOTIFICATION_SERVICE);
                try {

                    JSONArray jsonArray = App.getDawnAwakeData().getJSONArray("data");
                    Log.i("MainDeletegetDawnNtf", "" + jsonArray);
                    for (int i = 0; i <jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        dawn_sim_id=jsonObject.getString("Id");
                        Logs.info("","MainDdelete_dawn_simulation_id"+dawn_sim_id);
                    }


                    //Remove -30

                    for(int day=1;day<=7;day++){


                        int notif_day_name=day;

                        int dawn_id1 = new PrefManager(mcontext).getValueDawn(dawn_sim_id+ "A"+""+notif_day_name);

                        Log.i("MainDDelete******-30  ",""+dawn_id1);

                        nMgr.cancel(dawn_id1);
                        Intent myIntent = new Intent(mcontext, NotifyService.class);

                        PendingIntent pendingIntent = PendingIntent.getService(mcontext, dawn_id1, myIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmManager.cancel(pendingIntent);
                        int dawn_id2 = new PrefManager(mcontext).getValueDawn(dawn_sim_id+ "B"+""+notif_day_name);

                        Log.i("MainDDelete******-30  ",""+dawn_id2);

                        nMgr.cancel(dawn_id2);
                        Intent myIntent2 = new Intent(mcontext, NotifyService.class);

                        PendingIntent pendingIntent2 = PendingIntent.getService(mcontext, dawn_id2, myIntent2,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmManager.cancel(pendingIntent2);

                        new PrefManager(mcontext).removeKey(dawn_sim_id + "A"+""+notif_day_name);
                        new PrefManager(mcontext).removeKey(dawn_sim_id + "B"+""+notif_day_name);




                    }
//Remove -2

                }catch (JSONException ex){
                    Log.e("MainDeeeeerrrrrrrrr",""+ex.getMessage());
                }


            }catch (Exception e) {
                Log.e("MainDNotification_dawn",""+e.getMessage());
            }

        }


        if(string.equals("dawn_simulation_notification")||string.equals("dawn_notif_deleted")){
            new LogoutFunctionality().deleteDawnSleepNotification(mcontext,"dawn");
            String dawn_room_name="";

            String dawn_idd="";
            String simulation_room_id="";


            int notif_day_int;
            int notif_day_name;
            int dawn_hr = 0;
            int dawn_min =0;
            int actual_dawn_hr = 0;
            int actual_dawn_min =0;
            JSONArray notif_day = new JSONArray();

            try {
                JSONArray jsonArray = App.getDawnNotifJson().getJSONArray("data");
                Log.i("Result_sim_notif_Array",""+jsonArray);

                Calendar curr = Calendar.getInstance();
//Checking current current time for -30
                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                int c_hr=dateFormat.getCalendar().get(Calendar.HOUR_OF_DAY);
                int c_min=dateFormat.getCalendar().get(Calendar.MINUTE);
                curr.set(Calendar.HOUR_OF_DAY, c_hr);
                curr.set(Calendar.MINUTE, c_min-30);
                curr.set(Calendar.SECOND, 0);
                curr.get(Calendar.HOUR_OF_DAY);
                curr.get(Calendar.MINUTE);

                String CurrTimeT = curr.get(Calendar.HOUR_OF_DAY)+ ":" + curr.get(Calendar.MINUTE);
                Log.i("Dawn_Notif_CurrTime: ",""+CurrTimeT);



//checking current time for -2
                Calendar currr = Calendar.getInstance();
                DateFormat dateFormatt = new SimpleDateFormat("HH:mm");
                int c_hrr=dateFormatt.getCalendar().get(Calendar.HOUR_OF_DAY);
                int c_minn=dateFormatt.getCalendar().get(Calendar.MINUTE);

                currr.set(Calendar.HOUR_OF_DAY, c_hrr);
                currr.set(Calendar.MINUTE, c_minn-2);
                currr.set(Calendar.SECOND, 0);


                String CurrTimeN = currr.get(Calendar.HOUR_OF_DAY)+ ":" + currr.get(Calendar.MINUTE);
                Log.i("Dawn_Notif_CurrTime: ",""+CurrTimeN);



// itrating each obj of array
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.i("Result_sim_notif_obj", "" + jsonObject);

                    boolean notification_shedule_flag_30=false;


                    try {

                        if (jsonObject.getString("type").equals("Dawn")) {
                            simulation_room_id = jsonObject.getString("roomId");
                            dawn_idd = jsonObject.getString("Id");
                            Log.i("Dawn_Original_Time", "" + jsonObject.getInt("hour") + ":" + (jsonObject.getInt("minutes")));
                            //////Toast.makeText(MainActivity.this, "" + simulation_room_id, Toast.LENGTH_SHORT).show();
                            dawn_room_name = getRoomName(simulation_room_id);
                            // notif_day = getNotifDay(dawn_idd);i
                            Log.i("xxxxxxxxxxxx", "" + notif_day);

                            notif_day = jsonObject.getJSONArray("days");
                            dawn_hr=actual_dawn_hr = jsonObject.getInt("hour");
                            dawn_min =actual_dawn_min= jsonObject.getInt("minutes");
                            Calendar actual_calender = Calendar.getInstance();
                            actual_calender.set(Calendar.HOUR_OF_DAY, dawn_hr);
                            actual_calender.set(Calendar.MINUTE, dawn_min);
                            actual_calender.set(Calendar.SECOND, 0);
                            if (jsonObject.has("nextHr") && jsonObject.has("nextMin")) {
                                Log.i("Next", "Block");
                                dawn_hr = jsonObject.getInt("nextHr");
                                dawn_min = jsonObject.getInt("nextMin");
                                Logs.info("MainACtivity","dawn_id............"+dawn_idd+"............"+Calendar.getInstance().getTime()+".................."+actual_calender.getTime()+"difference............."+dateDifference(Calendar.getInstance().getTime(),actual_calender.getTime()));
                                if(dateDifference(Calendar.getInstance().getTime(),actual_calender.getTime())<1.8e+6) {
                                    notification_shedule_flag_30 =true;
                                }



                            }
                            Log.i("Dawnhr30", "" + dawn_hr);
                            Log.i("DawnMin30", "" + dawn_min);
                            Calendar c = Calendar.getInstance();
                            c.set(Calendar.HOUR_OF_DAY, dawn_hr);
                            c.set(Calendar.MINUTE, dawn_min);
                            c.set(Calendar.SECOND, 0);
                            c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) - 30);
                            String dawnTime = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
                            String id = jsonObject.getString("Id") + "A";
                            Log.i("*****A**ID" + id, "" + dawn_NOTIFICATION_id);
                            Log.i("Dawn-30", "" + dawnTime);
                            Log.i("Dawn-30", "" + c.get(Calendar.HOUR_OF_DAY));
                            Log.i("Dawn-30", "" + c.get(Calendar.MINUTE));
                            Log.i("Dawn-30", "" + dawnTime.toString());

                            int device_day=c.get(Calendar.DAY_OF_WEEK);
//adding multiple days
                            if(!notification_shedule_flag_30) {
                                if (notif_day.length() == 0) {
                                    dawn_mode = 0;
                                    notif_day_int = 0;
                                    System.out.println("ddddddddddddddddddddddddddd" + notif_day_int + "ddddddddd" + (device_day - 1) + notif_day_int);
                                    if (!(jsonObject.getBoolean("halted"))) {
                                        notif_day_name = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                                        repeat_no_one = (a + 30 * notif_day_name);
                                        dawn_NOTIFICATION_id = dawn_NOTIFICATION_id + repeat_no_one;
                                        String id2 = id + "" + notif_day_name;
                                        Log.i("*****A**ID_non-rep ", "" + id2);
                                        Log.i("*****A**Dawn_Day_-30NR", "" + dawn_NOTIFICATION_id);
                                        int dawn_id_from_storage = spm.getValueDawn(id2);
                                        scheduleClient.setAlarmForNotification(c, jsonObject.getString("Id"), "" + dawn_room_name, "A", dawn_id_from_storage == -1 ? dawn_NOTIFICATION_id : dawn_id_from_storage, notif_day_name, dawn_mode);
                                        if (dawn_id_from_storage == -1)
                                            spm.setValueDawn(id2, dawn_NOTIFICATION_id);
                                    }
                                } else {
                                    dawn_mode = 1;
                                    for (int day = 0; day < notif_day.length(); day++) {
                                        notif_day_int = 0;
                                        notif_day_int = notif_day.getInt(day);
                                        System.out.println("ddddddddddddddddddddddddddd" + notif_day_int + "ddddddddd" + (device_day - 1) + notif_day_int);
                                        if (!(notif_day_int == (device_day - 1) && jsonObject.getBoolean("halted"))) {
                                            repeat_no_one = (a + 30 * day);
                                            dawn_NOTIFICATION_id = dawn_NOTIFICATION_id + repeat_no_one;
                                            notif_day_int = notif_day.getInt(day);
                                            notif_day_name = notif_day_int + 1;
                                            Log.i("MainAt_notif_day_name", "" + notif_day_int);
                                            Log.i("MainAt_notif_day_name+1", "" + notif_day_name);
                                            String id2 = id + "" + notif_day_name;
                                            Log.i("*****A**ID ", "" + id2);
                                            Log.i("*****A**Dawn_Day_-30 ", "" + dawn_NOTIFICATION_id);
                                            int dawn_id_from_storage = spm.getValueDawn(id2);
                                            scheduleClient.setAlarmForNotification(c, jsonObject.getString("Id"), "" + dawn_room_name, "A", dawn_id_from_storage == -1 ? dawn_NOTIFICATION_id : dawn_id_from_storage, notif_day_name, dawn_mode);
                                            if (dawn_id_from_storage == -1)
                                                spm.setValueDawn(id2, dawn_NOTIFICATION_id);
                                        }
                                    }
                                }
                            }
/***set notification -2 status bar***/
                                   /* int hrr = jsonObject.getInt("hour");
                                    int minn = (jsonObject.getInt("minutes"));*/
                            Calendar cc = Calendar.getInstance();
                            cc.set(Calendar.HOUR_OF_DAY, dawn_hr);
                            cc.set(Calendar.MINUTE, dawn_min);
                            cc.set(Calendar.SECOND, 0);
                            cc.set(Calendar.MINUTE, cc.get(Calendar.MINUTE) - 2);
                            String id1 = jsonObject.getString("Id") + "B";
                            Log.i("*****B**ID" + id1, "" + Dawn_NOTIFICATION_id1);
                            String dawnTime1 = cc.get(Calendar.HOUR_OF_DAY) + ":" + cc.get(Calendar.MINUTE);
                            Log.i("Dawn-2", "" + dawnTime1);
                            Log.i("Dawn-2", "" + cc.get(Calendar.HOUR_OF_DAY));
                            Log.i("Dawn-2", "" + cc.get(Calendar.MINUTE));
                            Log.i("Dawn-2", "" + dawnTime1.toString());
                            //adding multiple days

                            if (notif_day.length() == 0) {
                                dawn_mode_2 = 0;
                                notif_day_int = 0;
                                if (!(jsonObject.getBoolean("halted"))) {
                                    notif_day_name = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                                    repeat_no_two = (ramdon_no + 2 * notif_day_name);
                                    Dawn_NOTIFICATION_id1 = Dawn_NOTIFICATION_id1 + repeat_no_two;
                                    String id2 = id1 + "" + notif_day_name;
                                    Log.i("*****B**ID*_Non-rep ", "" + id2);
                                    Log.i("*****B**ID*Dawn_Day-2NR", "" + Dawn_NOTIFICATION_id1);
                                    int dawn_id_from_storage = spm.getValueDawn(id2);
                                    scheduleClient.setAlarmForNotification(cc, jsonObject.getString("Id"), "" + dawn_room_name, "B", dawn_id_from_storage == -1 ? Dawn_NOTIFICATION_id1 : dawn_id_from_storage, notif_day_name, dawn_mode_2);
                                    if (dawn_id_from_storage == -1)
                                        spm.setValueDawn(id2, Dawn_NOTIFICATION_id1);
                                }
                            } else {
                                dawn_mode_2 = 1;
                                for (int day = 0; day < notif_day.length(); day++) {
                                    notif_day_int = 0;
                                    notif_day_int = notif_day.getInt(day);
                                    System.out.println("eeeeeeeeeeeeeeeeeee" + notif_day_int + "ddddddddd" + (device_day - 1));
                                    if (!(notif_day_int == (device_day - 1) && jsonObject.getBoolean("halted"))) {
                                        repeat_no_two = (ramdon_no + 2 * day);
                                        Dawn_NOTIFICATION_id1 = Dawn_NOTIFICATION_id1 + repeat_no_two;
                                        notif_day_int = notif_day.getInt(day);
                                        notif_day_name = notif_day_int + 1;
                                        Log.i("MainAt_notif_day_name", "" + notif_day_int);
                                        Log.i("MainAt_notif_day_name+1", "" + notif_day_name);
                                        String id2 = id1 + "" + notif_day_name;
                                        Log.i("*****B**ID* ", "" + id2);
                                        Log.i("*****B**ID*Dawn_Day_-2", "" + Dawn_NOTIFICATION_id1);
                                        int dawn_id_from_storage = spm.getValueDawn(id2);
                                        scheduleClient.setAlarmForNotification(cc, jsonObject.getString("Id"), "" + dawn_room_name, "B", dawn_id_from_storage == -1 ? Dawn_NOTIFICATION_id1 : dawn_id_from_storage, notif_day_name, dawn_mode_2);
                                        if (dawn_id_from_storage == -1)
                                            spm.setValueDawn(id2, Dawn_NOTIFICATION_id1);
                                    }
                                }
                            }
                        }



                    } catch (Exception ex) {
                        Log.e("MainActity_Result_error", "" + ex.getMessage());
                    }
                    ///////////////////////////////

                }
            } catch (Exception ex) {
                Log.e("Result_error", "" + ex.getMessage());
            }


        }

        if(string.equals("sleep_popup_closed"))
        {
            deleteSleepSpecificNotification(mcontext);

        }

        if(string.contains("sleep_simulation_notification")){

            JSONArray notif_day_sleep = new JSONArray();
            String Sleepsimulation_room_id="";
            int sleep_notif_day_name;
            String sleep_idd="";
            String sleep_room_name="";
            try {
                JSONArray jsonArray = App.getSleepNotifJson().getJSONArray("data");
                Log.i("SleepResult_sim_notif",""+jsonArray);
                System.out.println("ccccccccccccccccccccssssssssssssssssssss"+(string.split("aaaa")[1]));

                if(string.split("aaaa")[1].equals(SET)) {
                    new LogoutFunctionality().deleteDawnSleepNotification(mcontext, "sleep_notification");
                }
                else
                {
                    deleteSleepSpecificNotification(mcontext);
                }
                // itrating each obj of array
                for (int i = 0; i < jsonArray.length(); i++) {
                    try{
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.i("Result_sim_notif_obj",""+jsonObject);


                        if(jsonObject.getString("type").equals("Sleep")) {
                            Log.i("JDDDDDDSleep","jsonObj: "+jsonObject.toString());
                            sleep_idd=jsonObject.getString("Id");
                            Sleepsimulation_room_id = jsonObject.getString("roomId");

                            Log.i("Sleep_Original_Time",""+jsonObject.getString("roomId"));


                            sleep_room_name=getRoomName(Sleepsimulation_room_id);
                            // notif_day_sleep=getSleepNotifDay(sleep_idd);
                            notif_day_sleep=jsonObject.getJSONArray("days");

                            /*set notification +15 status bar**/

                            int sleep_hr = jsonObject.getInt("hour");
                            int sleep_min = (jsonObject.getInt("minutes") );

                            Log.i("SleepHr15",""+sleep_hr);
                            Log.i("SleepMin15",""+sleep_min);
                            Calendar c = Calendar.getInstance();
                            c.set(Calendar.HOUR_OF_DAY, sleep_hr);
                            c.set(Calendar.MINUTE, sleep_min);
                            c.set(Calendar.SECOND, 0);

                            c.set(Calendar.MINUTE,c.get(Calendar.MINUTE)+15);
                            String sleepTime_log = c.get(Calendar.HOUR_OF_DAY)+ ":" + c.get(Calendar.MINUTE);


                            String id = jsonObject.getString("Id") + "C";  //sleep#453453534C = id

                            Log.i("*****C**ID" + id, "" + sleep_NOTIFICATION_id1);


                            Log.i("Sleep+15",""+sleepTime_log);
                            Log.i("Sleep+15",""+c.get(Calendar.HOUR_OF_DAY));

                            Log.i("Sleep+15",""+c.get(Calendar.MINUTE));

                            Log.i("Sleep+16",""+sleepTime_log.toString());
                            Log.i("notif_day_sleep",""+notif_day_sleep.length());

//adding multiple days
                            Logs.info("MainActivity", "tttttttttttttttt" + dateDifference(Calendar.getInstance().getTime(), c.getTime()));
                            if (notif_day_sleep.length() == 0) {
                                if (!(jsonObject.getBoolean("halted"))) {

                                    sleep_mode = 0;
                                    sleep_notif_day_name = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                                    repeat_no_one = (a + 15 * sleep_notif_day_name);
                                    sleep_NOTIFICATION_id1 = sleep_NOTIFICATION_id1 + repeat_no_one;
                                    String sleep_notif_id2 = id + "" + sleep_notif_day_name;
                                    Log.i("*****C**ID_non-rep ", "" + sleep_notif_id2);
                                    Log.i("*****C**Sleep_Day_+15NR", "" + sleep_NOTIFICATION_id1);
                                    int sleep_id_from_storage = spm.getValueSleep(sleep_notif_id2);
                                    sleep_scheduleClient.setAlarmForNotification(c, jsonObject.getString("Id"), "" + sleep_room_name, "C", sleep_id_from_storage == -1 ? sleep_NOTIFICATION_id1 : sleep_id_from_storage, sleep_notif_day_name, sleep_mode);
                                    if (sleep_id_from_storage == -1)
                                        spm.setValueSleep(sleep_notif_id2, sleep_NOTIFICATION_id1);
                                }
                            } else {
                                sleep_mode = 1;
                                int device_day = c.get(Calendar.DAY_OF_WEEK);
                                for (int day = 0; day < notif_day_sleep.length(); day++) {
                                    int notif_sleep_day_int = 0;
                                    notif_sleep_day_int = notif_day_sleep.getInt(day);
                                    if (!(notif_sleep_day_int == (device_day - 1) && jsonObject.getBoolean("halted"))) {
                                        Logs.info("MainActivity", "tttttttttttttttt" + dateDifference(Calendar.getInstance().getTime(), c.getTime()));
                                        repeat_no_one = (ramdon_no + 15 * day);
                                        sleep_NOTIFICATION_id1 = sleep_NOTIFICATION_id1 + repeat_no_one;
                                        Log.i("*****C**SleepDayAry_+15", "" + day);
                                        sleep_notif_day_name = notif_sleep_day_int + 1;
                                        Log.i("*****C**Sleep_Day_+15 ", "" + sleep_notif_day_name);
                                        String sleep_notif_id2 = id + "" + sleep_notif_day_name;
                                        Log.i("*****C**ID ", "" + sleep_notif_id2);
                                        Log.i("*****C**Sleep_Day_+15 ", "" + sleep_NOTIFICATION_id1);
                                        int sleep_id_from_storage = spm.getValueSleep(sleep_notif_id2);
                                        sleep_scheduleClient.setAlarmForNotification(c, jsonObject.getString("Id"), "" + sleep_room_name, "C", sleep_id_from_storage == -1 ? sleep_NOTIFICATION_id1 : sleep_id_from_storage, sleep_notif_day_name, sleep_mode);
                                        if (sleep_id_from_storage == -1)
                                            spm.setValueSleep(sleep_notif_id2, sleep_NOTIFICATION_id1);
                                        Log.i("JDDDDDDSleepCre: " + sleep_notif_id2, "***** " + sleep_NOTIFICATION_id1 + "##########" + "" + sleep_room_name);
                                    }
                                }
                            }




                        }


                    } catch (Exception ex) {
                        Log.e("jdddddResult_error",""+ex.getMessage());
                    }
                }
            } catch (Exception ex) {
                Log.e("jdddddResult_errorrrrr",""+ex.getMessage());

            }

        }


    }


    /*****Get Dawn Room Name********/

    public static String getRoomName(String room_id)
    {
        String room_title="";
        try {
            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("CML_ID").equals(room_id)) {
                    room_title = jsonObject.getString("CML_TITLE");
                }
            }
        }
        catch (Exception ex){Log.e("Error_getRoomTitle",ex.getMessage());}
        return room_title;
    }


    public void setFamiltyName()
    {
        try
        {
            JSONObject jsonObject=App.getHubInfo().getJSONArray("data").getJSONObject(0);

            if (jsonObject.has("CML_FAMILY_NAME")) {
                String family_name = App.getHubInfo().getJSONArray("data").getJSONObject(0).getString("CML_FAMILY_NAME");
                if (family_name.equals("null")) {
                    family_name="Darwin";
                }
                txt_family_name.setText("" + family_name);
                String family_initial=""+family_name.charAt(0);

                if(!App.isOrientationFlag()) {
                    ((TextView) nav_view.findViewById(R.id.txt_family_name)).setText("" + family_name);
                    ((TextView) nav_view.findViewById(R.id.txt_family_initial)).setText("" + family_initial.toUpperCase());
                }
                else
                {
                    txt_family_initial.setText("" + family_initial.toUpperCase() );
                }

            }

        }
        catch (Exception ex)
        {
            Logs.error(TAG+"_FamilyNameError",""+ex.getMessage());
        }
    }


    public void setSelection(LinearLayout lin_selection,TextView txt_selection,ImageView img_selection)
    {
        if(App.isOrientationFlag()) lin_selection.setEnabled(false);
        //  lyt_family_name.setBackgroundColor(this.getResources().getColor(R.color.tab_select_color));
        lin_selection.setBackgroundColor(this.getResources().getColor(R.color.tab_select_color));
        txt_selection.setTextColor(this.getResources().getColor(R.color.dialer_selected_color));
        img_selection.setColorFilter(this.getResources().getColor(R.color.dialer_selected_color), PorterDuff.Mode.SRC_IN);
    }


    public void setDefault(View v)
    {
        if(App.isOrientationFlag())v.setEnabled(true);
        v.setBackgroundColor(this.getResources().getColor(R.color.tab_bg_color));
        if(v instanceof ViewGroup){

            for (int i = 0; i < ((ViewGroup)v).getChildCount();  i++) {
                View view = ((ViewGroup)v).getChildAt(i);

                if(view instanceof TextView)
                {
                    ((TextView) view).setTextColor(this.getResources().getColor(R.color.white));
                }
                if(view instanceof RelativeLayout)
                {
                    View view1 = ((ViewGroup)view).getChildAt(0);
                    ((ImageView) view1).setColorFilter(this.getResources().getColor(R.color.text_color_gray), PorterDuff.Mode.SRC_IN);
                }
            }

        }
    }

    public void setDefaultColor()
    {
        setDefault(lin_climate);
        setDefault(lin_home);
        setDefault(lin_elements);
        setDefault(lin_experiences);
        setDefault(lin_discover);
    }


    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        UserInteractionSleep.userInteract(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logs.info(TAG,"where im.....in on new intenet");
        notification_result = intent.getStringExtra("toastMessage");
        sleep_obj_id=intent.getStringExtra("sleep_obj_id");
        dawn_obj_id = intent.getStringExtra("dawn_obj_id");
        sleep_room_id = intent.getStringExtra("sleep_room_id");
        Logs.info(TAG, "reeeeeeeeeeeeeeeeeeeeeeeee" + notification_result + "eeeeeeeeeee" + dawn_obj_id+"eeeeeeeeeeee"+sleep_obj_id+"eeeeeeeeeeeee"+sleep_room_id);
        if (notification_result != null) {
            dawnOperations(notification_result);
            notification_result="";
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logs.info(TAG,"where im.....onresume");


        hideSystemUI(this);
        if (App.getSocket() != null) {
            if (!App.getSocket().connected()) {
                MainActivity.this.finish();
                Intent i = new Intent(MainActivity.this, ConnectionLostActivity.class);
                MainActivity.this.startActivity(i);
            } else {
                HomeHelthCall.getHomeHelth(this);
            }
        }

    }

    @OnClick({R.id.txt_filters, R.id.txt_setting, R.id.txt_supports, R.id.txt_privacy,R.id.txt_discover})
    public void onNavigationItemSelected(View view) {

        freeMemory();
        home_screen_flag=false;
        Fragment fragment=null;
        switch (view.getId())
        {
            case R.id.txt_filters:
                fragment = FiltersFragment.newInstance();

                break;
            case R.id.txt_discover:
                fragment= DiscoverMainFragment.newInstance();
                break;
            case R.id.txt_setting:
                fragment = SettingMainFragment.newInstance();
                break;
            case R.id.txt_supports:
                fragment= PrivacyPolicy.newInstance();
                break;
            case R.id.txt_privacy:
                fragment= PrivacyPolicy.newInstance();
                break;
        }

        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.replace(R.id.frm_main_container, fragment);
        transaction1.addToBackStack(null);
        transaction1.commit();

        drawerLayout.closeDrawer(GravityCompat.START);

    }

    private void shouldDisplayHomeUp() {

        boolean canback = getSupportFragmentManager().getBackStackEntryCount()>0;
        if(getSupportActionBar() != null)   getSupportActionBar().setDisplayHomeAsUpEnabled(canback);

    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }
    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }



    public void checkTimeZone()
    {
        try
        {
            JSONArray jsonArray = App.getHubInfo().getJSONArray("data");
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            boolean flag_time=false;
            String time_zone[]=TimeZone.getDefault().getAvailableIDs(TimeZone.getDefault().getOffset(Calendar.DATE));
            System.out.println("*****************"+time_zone.length);
            for (String aTime_zone : time_zone) {
                System.out.println("*****************" + aTime_zone + "******" + jsonObject.getString("CML_TIMEZONE"));
                if (aTime_zone.equals(jsonObject.getString("CML_TIMEZONE"))) {
                    System.out.println("******************************************" + aTime_zone + "------" + jsonObject.getString("CML_TIMEZONE"));
                    flag_time = true;
                    break;
                }

            }
            Logs.error(TAG+"_Timezoneeee",""+ flag_time);
            if(!flag_time)
            {
                Bundle bundle=new Bundle();
                bundle.putString("dialg_type","time");
                TimeZoneDialog dFragment = new TimeZoneDialog();
                dFragment.setCancelable(false);
                dFragment.setArguments(bundle);
                dFragment.show(getSupportFragmentManager(),"");

            }


        }
        catch (Exception ex)
        {
            Logs.error(TAG+"_TimezoneError",""+ex.getMessage());
        }


    }

    public void checkHubVersion()
    {
        try
        {

            boolean flag_hub_version_check=false;

            JSONArray jsonArray = App.getNetworkInfoJson().getJSONArray("data");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String hubversion=jsonObject.getString("CML_HUB");


            if(hubversion.contains("beta")) {
                DefaultArtifactVersion hubCurrentversion = new DefaultArtifactVersion(hubversion.split("-")[0]);
                System.out.println("hhhhhhhhhhhhhhhhhhhhhh"+hubversion.split("-")[0]);
                if(hubCurrentversion.compareTo(minBetaVersion)<0)
                {
                    flag_hub_version_check=false;
                    Logs.info("betaHubVersionStatus","false");
                }
                else
                {
                    flag_hub_version_check=true;
                    Logs.info("betaHubVersionStatus","true");
                }

            }
            else {
                DefaultArtifactVersion hubCurrentversion = new DefaultArtifactVersion(hubversion);
                if(hubCurrentversion.compareTo(minProdVersion)<0)
                {
                    flag_hub_version_check=false;
                    Logs.info("prodHubVersionStatus","false");
                }
                else
                {
                    flag_hub_version_check=true;
                    Logs.info("prodHubVersionStatus","true");
                }

            }

            if(hubversion.equals("0.0.0"))flag_hub_version_check=true;

            if(!flag_hub_version_check)
            {
                dispAppCloseDialog(getResources().getString(R.string.hub_version_not_match));
            }
            else
            {
                if (!App.isTime_zone()) {
                    App.setTime_zone(true);
                    checkTimeZone();
                }
            }


        }
        catch (Exception ex)
        {
            Logs.error(TAG+"_TimezoneError",""+ex.getMessage());
        }


    }

    public void dispAppCloseDialog(String msg)
    {
        final Dialog intd = new Dialog(MainActivity.this, R.style.DialogTheme);
        intd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        intd.setContentView(R.layout.dialog_timezone);
        intd.setCanceledOnTouchOutside(false);
        TextView txt_msg = intd.findViewById(R.id.txt_msg);
        txt_msg.setText(msg);
        Button btnretry = intd.findViewById(R.id.btn_ok);
        btnretry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.finish();

            }
        });
        intd.show();
    }

    public void pinInputDialog()
    {
        if(intd_pin ==null) {
            intd_pin =new PinInputDialog();
            intd_pin.setCancelable(false);
            intd_pin.show(getFragmentManager(),TAG);

        }
    }
    public void dawnOperations(String action)
    {
        JSONObject obj = new JSONObject();
        try {
            if (App.getSocket()!=null) {
                obj.put("data", getDawnList());
                switch (action)
                {
                    case "deactivate":
                        obj.put("type", "sleep_again");
                        break;
                    case "awake":
                        obj.put("type", "awake");
                        break;
                    case "snooze":
                        obj.put("type", "snooze_dawn_simulation");
                        break;
                    case "sleep_ok":
                        JSONObject dataJson = new JSONObject();
                        dataJson.put("state", false);
                        dataJson.put("room", sleep_room_id);
                        dataJson.put("schedule", sleep_obj_id);
                        obj.put("data", dataJson);
                        obj.put("type", "room_power");
                        Log.e("room_power", "" + obj);
                        App.getSocket().emit("action", obj);
                        break;
                }

                Logs.info(TAG,"dawn_notification_call......"+obj);
                App.getSocket().emit("action", obj);
            }
        } catch (JSONException e) {
            Log.e("Notificaton_sleep_again",""+e.getMessage());
        }
    }
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void setEdittextEnable(EditText edt_text, boolean flag)
    {
        if(flag)
        {
            edt_text.setAlpha(1);
        }
        else
        {
            edt_text.setAlpha(0.5f);
        }
    }
    public void setButtonEnable(String msg)
    {
        if(flag_passward)
        {
            valid_user_name.setVisibility(View.GONE);
            valid_password.setVisibility(View.GONE);
            btn_signin.setTextColor(getResources().getColor(R.color.white));
            btn_signin.setBackground(getResources().getDrawable(R.drawable.button_background_style));
        }
        else
        {
            valid_user_name.setVisibility(View.VISIBLE);
            valid_password.setVisibility(View.VISIBLE);
            btn_signin.setTextColor(getResources().getColor(R.color.light_gray));
            btn_signin.setBackground(getResources().getDrawable(R.drawable.btn_border_gray));
        }
    }
    public void checkInstallerportalState()
    {

        try {

            JSONObject jsonObject = App.getHub_installation_projess_json().getJSONArray("data").getJSONObject(0);
            Logs.info(TAG,"installerportalstatus------------------------------------"+jsonObject);

            if (jsonObject.getBoolean("PortalFlag")) {
                if(intd2==null) {
                    try {
                        intd2 = new Dialog(MainActivity.this, R.style.DialogTheme);
                        intd2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        intd2.setContentView(R.layout.activity_demo_scene_popup);
                        RelativeLayout rel_main=intd2.findViewById(R.id.rel_main);
                        rel_main.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                UserInteractionSleep.userInteract(MainActivity.this);
                                return false;
                            }
                        });
                        TextView txt_dialog_title = intd2.findViewById(R.id.txt_dialog_title);
                        txt_dialog_title.setText("" + getResources().getString(R.string.darwing_installtion_msg));
                        intd2.setCanceledOnTouchOutside(false);
                        intd2.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        intd2.setCancelable(false);
                        intd2.show();

                    }
                    catch (Exception ex)
                    {
                        Logs.info(TAG,"---------------"+ex.getMessage());
                    }
                }
            } else {
                if(intd2!=null)
                {
                    if(intd2.isShowing())
                    {
                        intd2.dismiss();
                        intd2=null;
                    }
                }
            }

        }
        catch(Exception ex)
        {
            Logs.error(TAG, "----------------" + ex.getMessage());
        }


    }
    public void checkCircadinaStatusCheck()
    {

        try {

            if(intd1==null) {
                intd1 = new Dialog(MainActivity.this, R.style.DialogTheme);
                intd1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                intd1.setContentView(R.layout.circadian_dialog);
                RelativeLayout rel_main= intd1.findViewById(R.id.rel_main);
                rel_main.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        UserInteractionSleep.userInteract(MainActivity.this);
                        return false;
                    }
                });
                TextView txt_dialog_title= intd1.findViewById(R.id.txt_dialog_title);
                txt_dialog_title.setText(""+getResources().getString(R.string.circadian_trigger_msg));
                intd1.setCanceledOnTouchOutside(false);
                intd1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                intd1.show();
                intd1.setCancelable(false);
                runnable1 = new Runnable() {
                    @Override
                    public void run() {

                        if(intd1!=null) {
                            if (intd1.isShowing()) {
                                intd1.dismiss();
                                intd1=null;
                            }
                        }
                    }
                };
                handler1 = new Handler();
                handler1.postDelayed(runnable1, 5000);
            }


        }
        catch(Exception ex)
        {
            Logs.error(TAG, "----------------" + ex.getMessage());
        }


    }


    public static void deleteSleepSpecificNotification(Context mcontext)
    {
        try {
            Object object = App.getDawnAwakeData().get("data");
            JSONObject jsonObject;

            if(object instanceof JSONObject)
            {
                jsonObject=(JSONObject)object;
            }
            else
            {
                jsonObject=((JSONArray)object).getJSONObject(0);
            }

            removeSpecificSleepNotification(mcontext,jsonObject.has("CML_ID")?jsonObject.getString("CML_ID"):jsonObject.getString("Id"));
            Log.i("MainDeletegetDawnNtf", "" + jsonObject);

        }
        catch (Exception ex)
        {
            Logs.error("MainActivity","------"+ex.getMessage());
        }
    }

    public static boolean getPurification()
    {
        boolean state=false;
        try
        {
            if(App.getProvisionalDevicesData()!=null)
            {
                JSONArray jsonArray=App.getProvisionalDevicesData().getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    if(jsonObject.has("CML_PLANT_TYPE")) {
                        if (jsonObject.getString("CML_PLANT_TYPE").equals("hvacPlant")) {
                            if (jsonObject.has("CML_PURIFICATION_STATE"))
                                state = jsonObject.getBoolean("CML_PURIFICATION_STATE");
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Logs.error("ManufatureExcpion",""+ex.getMessage());
        }
        return state;
    }
    public void disableAllViews(View v,boolean f){
        v.setEnabled(f);
        if(v instanceof ViewGroup){

            for (int i = 0; i < ((ViewGroup)v).getChildCount();  i++) {
                View view = ((ViewGroup)v).getChildAt(i);
                disableAllViews(view,f);
            }
        }
    }

    @Optional
    @OnClick(R.id.close_nav)
    public void close_nav()
    {
        freeMemory();
        MainActivity.drawerLayout.closeDrawer(Gravity.LEFT);

        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.replace(R.id.frm_main_container, HomeHeathByZoneFragment.newInstance());
        transaction1.addToBackStack(null);
        transaction1.commit();


    }


    public static void showDialog(Activity mactivity)
    {
        progress=new Dialog(mactivity,R.style.ProgressDialogTheme);
        progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress.setContentView(R.layout.custom_dialog_progress_percentage);
        txt_progress=progress.findViewById(R.id.txt_progress);
        txt_progress_placeholder=progress.findViewById(R.id.txt_progress_placeholder);
        txt_progress_placeholder.setText(mactivity.getString(R.string.after_login_emit_text));
        startAnimationProgress(0);
        progress.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // Prevent dialog close on back press button
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
        progress.show();

    }

    public static void startAnimationProgress(int progress_int){

        ValueAnimator animator = ValueAnimator.ofInt(Integer.parseInt(txt_progress.getText().toString().replace("%","")), progress_int);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                txt_progress.setText(animation.getAnimatedValue().toString()+"%");
            }
        });
        animator.start();
    }


    public void connectAndEmitLoginCall()
    {


        System.out.println("loginhub********************"+spm.getValue("ip_address"));
        if(!spm.getValue("ip_address").equals("No Record")) {

            try {
                IO.Options options = new IO.Options();
                //   options.secure = true;
                options.forceNew = true;
                options.reconnection = true;
                final Socket msocket = IO.socket("http://" + spm.getValue("ip_address") + "/login", options);
                msocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        try {

                            System.out.println("loginhub********************************connected");

                            JSONArray action_array = new JSONArray();
                            action_array.put(0, "HUB_LOGIN");
                            JSONObject obj = new JSONObject();
                            obj.put("ACTION_ARRAY", action_array);
                            obj.put("clzlogin", true);
                            obj.put("hubId", spm.getValue("hub_serial"));
                            obj.put("username", spm.getValue("User_Id"));
                            obj.put("password", PassowrdEncryptDecrypt.encrypt(spm.getValue("password")));
                            Logs.info("LoginCallFromMainActivity", spm.getValue("password") + "" + obj + "----------------" + obj);
                            msocket.emit("LOGIN", obj);


                        } catch (Exception ex) {
                            Log.e(TAG, "---" + ex.getMessage());
                        }
                    }
                }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        System.out.println("loginhub********************************connect_error"+args[0]);
                    }
                }).on(Socket.EVENT_ERROR, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        System.out.println("loginhub********************************error"+args[0]);
                    }
                }).on("LoginResponce", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        System.out.println("loginhub********************************error"+args[0]);
                        try {
                            JSONObject jsonObject = new JSONObject("" + args[0]);
                            JSONArray userJarr = jsonObject.getJSONArray("ACTION_ARRAY");
                            if("ERROR".equals(userJarr.getString(0))) {

                                MainActivity.this.finish();
                                Intent i = new Intent(MainActivity.this, ConnectionLostActivity.class);
                                MainActivity.this.startActivity(i);
                            }
                            if ("HUB_LOGIN".equals(userJarr.getString(0))) {
                                spm.setValue("jwt_token", jsonObject.getString("jwtToken"));
                                connectToHub();
                            }
                        }
                        catch (Exception ex)
                        {
                            Logs.error("LoginResponceError",""+ex.getMessage());
                        }
                    }
                });
                msocket.connect();
            }
            catch (Exception ex)
            {
                Logs.info(TAG,"--------"+ex.getMessage());
            }
        }


    }

    public void connectToHub()
    {
        if (App.getSocket() == null) {
            final SocketConnectionTest sct = new SocketConnectionTest();
            Logs.info(TAG + "_Ip_address", "" + spm.getValue("ip_address")+"----------------"+spm.getValue("jwt_token"));
            if(!spm.getValue("ip_address").equals("No Record")) {
                sct.connect("http://" + spm.getValue("ip_address") + "/action", App.getSocket(), getApplicationContext());
            }
            // sct.connect("http://192.168.1.14/action", App.getSocket(), getApplicationContext());
        } else {
            Observable.just("hub_connected").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(myObserver);
        }
    }


    public void showLocationSaveDialog()
    {

        if(!save_location.isShowing()) {
            WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
            lWindowParams.copyFrom(progress.getWindow().getAttributes());
            lWindowParams.width = WindowManager.LayoutParams.FILL_PARENT; // this is where the magic happens
            lWindowParams.height = WindowManager.LayoutParams.FILL_PARENT;
            save_location.show();// I was told to call show first I am not sure if this it to cause layout to happen so that we can override width?
            save_location.getWindow().setAttributes(lWindowParams);
        }

    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom()) ) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }







    public static  void hideSystemUI(Activity mactivity) {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = mactivity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


    public void freeMemory(){
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }


    private static long dateDifference(Date startDate, Date endDate){
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();
        Logs.info("MainActivity"+"_elapseDay",""+different);
        return different;
    }

    private JSONObject getDawnList() {
        JSONObject jsonObject=new JSONObject();
        try {
            if (App.getSimulationData() != null && !dawn_obj_id.equals("")) {
                JSONArray jsonArrayy = App.getSimulationData().getJSONArray("data");
                for (int i = 0; i < jsonArrayy.length(); i++) {
                    JSONObject jsonObject1 = jsonArrayy.getJSONObject(i);
                    if (jsonObject1.getString("CML_ID").equals(dawn_obj_id)) {
                        jsonObject=jsonObject1;
                    }
                }
            }
        } catch (Exception ex) {
        }
        return jsonObject;
    }


    private void setWholeHomeValue()
    {
        try {
            JSONArray jsonArray = App.getZoneJson().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("CML_TITLE").equals("Whole Home")) {
                    if (jsonObject.has("CML_LIGHT_POWER")) {
                        whole_light_flag=jsonObject.getBoolean("CML_LIGHT_POWER");
                        txt_whole_home_light.setTextColor(whole_light_flag?getResources().getColor(R.color.dialer_selected_color):getResources().getColor(R.color.white));
                        img_whole_home_light.setColorFilter(whole_light_flag?this.getResources().getColor(R.color.dialer_selected_color):this.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
                        txt_whole_home_light.setText(whole_light_flag?this.getResources().getString(R.string.on):this.getResources().getString(R.string.off));

                    }
                }
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG,"whole home state error"+ex.getMessage());
        }
    }


    public void setSelectedPackages()
    {
        ArrayList<String> selected_packages=new ArrayList<>();
        try
        {
            JSONArray jsonArray=App.getHubInfo().getJSONArray("data").getJSONObject(0).getJSONArray("CML_PACKAGE_DETAILS");
            for (int i=0;i<jsonArray.length();i++)
            {
                selected_packages.add(jsonArray.getJSONObject(i).getString("CML_PKG_ID"));
            }
        }
        catch (Exception ex)
        {
            Logs.error(TAG,"-------"+ex.getMessage());
        }
        App.setList_seleted_package(selected_packages);
        if (!new PackageOperations().checkFeatures(this, App.isOrientationFlag()?DARWIN_TABLET:DARWIN_MOBILE))  App.setSubcription(Observable.just("upgrade_package_main").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe( App.getMain_activity_subcriber().getObserver()));

    }




}