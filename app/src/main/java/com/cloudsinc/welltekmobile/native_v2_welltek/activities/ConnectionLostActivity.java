package com.cloudsinc.welltekmobile.native_v2_welltek.activities;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.CustomDialogShow;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.SocketConnectionTest.SocketConnectionTest;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule.RemoveNotification;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.NotifyService_sleep;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.threads.UdpClientThread;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.LogoutFunctionality;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ScreenOrientation;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.LoginActivity.isConnected;
import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity.hideSystemUI;
import static com.cloudsinc.welltekmobile.native_v2_welltek.application.App.getSocket;
import static com.cloudsinc.welltekmobile.native_v2_welltek.application.App.isFlag_connection_lost;
import static com.cloudsinc.welltekmobile.native_v2_welltek.application.App.isHub_not_available;
import static com.cloudsinc.welltekmobile.native_v2_welltek.application.App.isNo_internet;
import static com.cloudsinc.welltekmobile.native_v2_welltek.application.App.isSetDateTime;
import static com.cloudsinc.welltekmobile.native_v2_welltek.application.App.isSetStaticIp;
import static com.cloudsinc.welltekmobile.native_v2_welltek.application.App.isWifi_setup_save;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.listeners.SocketListeners.cancleNotification;
public class ConnectionLostActivity extends BaseActivity {
    @BindView(R.id.btn_retry)Button btn_retry;
    @BindView(R.id.txt_msg)TextView txt_msg;
    @BindView(R.id.rel_loading)RelativeLayout rel_loading;
    @BindView(R.id.txt_logout)TextView txt_logout;
    @SuppressLint("StaticFieldLeak")
    public static ConnectionLostActivity instance = null;
    PrefManager spm ;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    private PrefManager pref;
    private View lyt;
    public static boolean isAppWentToBg = false;
    public static boolean isWindowFocused = false;
    public static boolean isMenuOpened = false;
    String TAG=ConnectionLostActivity.this.getClass().getSimpleName();
    public static boolean isBackPressed = false;
    public static Dialog progress;
    @SuppressLint("StaticFieldLeak")
    public static TextView txt_progress_placeholder,txt_progress;
    Runnable runnable;
    Handler handler;
    App app;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection_lost);
        UserInteractionSleep.siboSleepChecking(this);
        ScreenOrientation.setOrientation(this);
        spm = new PrefManager(ConnectionLostActivity.this);
        lyt= findViewById(R.id.lyt_lost);
        pref=new PrefManager(this);
        App.setHubConnectionData(null);
        App.setCurrentSubcriber(null);
        App.setHubsubcriber(null);
        App.setSocket(null);
        App.setFlag_connection_lost_activity_state(true);
        Logs.error(TAG+"_CallFrom",""+App.getCallfrom());
        final Typeface mFont = Typeface.createFromAsset(getAssets(),
                "fonts/SF-UI-Text-Regular.otf");
        final ViewGroup mContainer = (ViewGroup) lyt.getRootView();
        FontUtility.setAppFont(mContainer, mFont);
        ButterKnife.bind(this);
        rel_loading.setVisibility(View.GONE);
        txt_logout.setPaintFlags(txt_logout.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        if(isNo_internet())
        {
            App.setNo_internet(false);
            txt_msg.setText("Not connected to Internet");
            btn_retry.setVisibility(View.VISIBLE);
        }
        if(isWifi_setup_save())
        {
            txt_msg.setVisibility(View.GONE);
            btn_retry.setVisibility(View.GONE);
            PrefManager prefManager=new PrefManager(ConnectionLostActivity.this);
            prefManager.setValue("state_serial","No Record");
            prefManager.setValue("Simulation_info","No Record");
            Intent mainIntent = new Intent(ConnectionLostActivity.this, HubConnection.class);
            ConnectionLostActivity.this.startActivity(mainIntent);
        }
        if(isSetDateTime())
        {
            txt_msg.setText("Please wait.....");
            btn_retry.setVisibility(View.GONE);
        }
        if(isFlag_connection_lost())
        {
            rel_loading.setVisibility(View.VISIBLE);
            txt_msg.setText("Saving Location");
            btn_retry.setVisibility(View.GONE);
        }
        if(isHub_not_available())
        {
            App.setHub_not_available(false);
            txt_msg.setText(spm.getValue("hub_serial")+" "+getResources().getString(R.string.hub_loss));
            btn_retry.setVisibility(View.VISIBLE);
        }
        if(isSetStaticIp())
        {
            txt_msg.setText("Please wait.....");
            btn_retry.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    PrefManager spm=new PrefManager(ConnectionLostActivity.this);
                    spm.setValue("hub_connect","1");
                    SocketConnectionTest sct=new SocketConnectionTest();
                    sct.connect("http://"+spm.getValue("ip_address")+":3001", getSocket(),getApplicationContext());
                }
            }, 4000);
        }
        setSubcriber();
    }
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        UserInteractionSleep.userInteract(this);
    }
    @OnClick(R.id.btn_retry)
    public void btn_retry()
    {
        boolean flag=false;
        if (getSocket()!=null) {
            if(App.getSocket().connected()) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                flag=false;
            }
            else
            {
                flag=true;
            }
        }
        else{
            flag=true;
        }
        if(flag) {
            if (isConnected(ConnectionLostActivity.this)) {
                App.setHubsubcriber(null);
                App.setCurrentSubcriber(null);
                setTimeOut("login");
                UdpClientThread udpClientThread = new UdpClientThread(ConnectionLostActivity.this,spm.getValue("hub_serial"));
                udpClientThread.start();
            } else {
                CustomDialogShow.dispDialogWithOK(ConnectionLostActivity.this, "" + getResources().getString(R.string.network_issue));
            }
        }
    }
    @Override
    public void onBackPressed(){
    }
    @Override
    public void finish() {
        super.finish();
        instance = null;
    }
    @Override
    protected void onStart() {
        Logs.info(TAG, "onStart isAppWentToBg " + isAppWentToBg);
        applicationWillEnterForeground();
        super.onStart();
    }
    private void applicationWillEnterForeground() {
        if (isAppWentToBg) {
            App.setFlag_connection_lost_activity_state(true);
            isAppWentToBg = false;
            if(App.getSocket()!=null) {
                Logs.error(TAG+"_MainActivity","Enter in foreground"+App.getSocket().connected());
                if(App.getSocket().connected()) {
                    Intent i = new Intent(this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        Logs.info(TAG, "onStop ");
        UserInteractionSleep.stopHandler();
        applicationdidenterbackground();
    }
    public void applicationdidenterbackground() {
        if (!isWindowFocused) {
            Logs.error(TAG+"_MainActivity","Enter in background");
            isAppWentToBg = true;
            App.setFlag_connection_lost_activity_state(false);
            /*////Toast.makeText(getApplicationContext(),
                    "App is Going to Background", Toast.LENGTH_SHORT).show();*/
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        isWindowFocused = hasFocus;
        if (isBackPressed && !hasFocus) {
            isBackPressed = false;
            isWindowFocused = true;
        }
        if (hasFocus) {
            hideSystemUI(this);
        }
        super.onWindowFocusChanged(hasFocus);
    }
    private void setSubcriber() {
        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("");
                        try {
                            Thread.sleep(8000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sub.onCompleted();
                    }
                }
        );
        myObserver = new Observer<String>() {
            @Override
            public void onNext(String string) {
                Logs.error(TAG+"_WHERIM","INPROGRESSSHOW");
            }
            @Override
            public void onCompleted() {
                try {
                    JSONObject hubjson = App.getHubConnectionData();
                    Logs.error(TAG+"_HubData", "" + hubjson);
                    if (hubjson != null) {
                            String ipaddress = hubjson.getString("address");
                            spm = new PrefManager(ConnectionLostActivity.this);
                            spm.setValue("state_serial", "1");
                            spm.setValue("ip_address", ipaddress);

                            startAnimationProgress(50);
                            ConnectionLostActivity.this.finish();
                            Intent i = new Intent(ConnectionLostActivity.this, MainActivity.class);
                            startActivity(i);

                    } else {
                        progress.dismiss();
                      //  CustomDialogShow.dispDialogWithOK(ConnectionLostActivity.this, ""+getResources().getString(R.string.hub_offline));
                    }
                } catch (Exception ex) {
                    Logs.error(TAG+"_HubConnectionError", ex.getMessage());
                }
            }
            @Override
            public void onError(Throwable e) {
            }
        };
    }
    public void setTimeOut(String str)
    {
        showDialog();
        startAnimationProgress(20);
        runnable = new Runnable() {
            @Override
            public void run() {
                Observable.just("").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(myObserver);
            }
        };
        handler = new Handler();
        handler.postDelayed(runnable, 8000);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handler!=null) handler.removeCallbacks(runnable);
    }
    @Override
    protected void onResume() {
        super.onResume();
        UserInteractionSleep.siboSleepChecking(this);
        if(App.getSocket()!=null)
        {
            if(App.getSocket().connected())
            {
                App.setCallfrom("ConnectionLostActivity");
                ConnectionLostActivity.this.finish();
                Intent i = new Intent(ConnectionLostActivity.this, MainActivity.class);
                ConnectionLostActivity.this.startActivity(i);
            }
        }
    }
    @OnClick(R.id.txt_logout)
    public void txt_logout()
    {
        new LogoutFunctionality().logoutAction(ConnectionLostActivity.this);
    }

    public void showDialog()
    {
        progress=new Dialog(this,R.style.ProgressDialogTheme);
        progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress.setContentView(R.layout.custom_dialog_progress_percentage);
        txt_progress_placeholder=progress.findViewById(R.id.txt_progress_placeholder);
        txt_progress=progress.findViewById(R.id.txt_progress);
        txt_logout=progress.findViewById(R.id.txt_logout);
        txt_logout.setVisibility(View.VISIBLE);
        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LogoutFunctionality().logoutAction(ConnectionLostActivity.this);
            }
        });
        txt_progress_placeholder.setText(this.getString(R.string.trying_to_connect));
        startAnimationProgress(0);
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

}