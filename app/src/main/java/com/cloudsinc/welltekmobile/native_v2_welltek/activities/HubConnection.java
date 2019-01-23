package com.cloudsinc.welltekmobile.native_v2_welltek.activities;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.CustomDialogShow;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.threads.UdpClientThread;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ScreenOrientation;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;

import org.json.JSONArray;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.LoginActivity.isConnected;
/**
 * This class containing functionality related to displaying hub list and connect to user selected hub
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class HubConnection extends AppCompatActivity  {
    private Button connect, b1;
    //private LinearLayout b1;
    private View hublyt;
    private App globalClass;
    private String str_serial;
    private Context mcontext;
    private PrefManager spm;
    private Observable<String> hubconnectobserable;
    private Observer<String> hubconnectobserver;
    @BindView(R.id.spn_hub_msg)
    Spinner spn_hub_msg;
    @BindView(R.id.btn_hub_connect)
     Button btn_hub_connect;
    private String TAG=HubConnection.this.getClass().getSimpleName();
    private String[] str_serial_ids;
    private  Dialog progress;
    private  TextView txt_progress_placeholder,txt_progress;
    private Runnable runnable;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_connection);
        ScreenOrientation.setOrientation(this);
        UserInteractionSleep.siboSleepChecking(this);
        spm = new PrefManager(this);
        App.setHubConnectionData(null);
        App.setLoginData(null);
        App.setCallfrom("HubConnection");
         Logs.error(TAG+"_FWhereIM", "HubConnection");
        App.setCurrentSubcriber(null);
        ButterKnife.bind(this);
        try{str_serial_ids = getStringArray(new JSONArray(spm.getValue("multiple_hub_array")));}catch (Exception ex) {Logs.error(TAG,"multiple hub serial serror"+ex.getMessage());}
        Logs.error(TAG+"_Fhub_ids",""+str_serial_ids);
            ArrayAdapter aa = new ArrayAdapter(this,R.layout.custom_hub_spinner_item,str_serial_ids);
            aa.setDropDownViewResource(R.layout.custom_hub_spinner_item);
            //Setting the ArrayAdapter data on the Spinner
            spn_hub_msg.setAdapter(aa);
            spn_hub_msg.setSelection(0);
            str_serial = str_serial_ids[0];
            spn_hub_msg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    str_serial = str_serial_ids[position];
                    if(!str_serial.equals("Please select Hub Id"))
                    {
                        setButtonEnable(true);
                    }
                    else
                    {
                        setButtonEnable(false);
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            setHubConnectionSubcriber();
    }
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        UserInteractionSleep.userInteract(this);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            MainActivity.hideSystemUI(this);
        }
        super.onWindowFocusChanged(hasFocus);
    }
    @OnClick(R.id.btn_login)
    public void btn_login()
    {
        spm.setValue("state_serial","No Record");
        spm.setValue("multiple_hub_array","No Record");
        App.setLoginData(null);
        HubConnection.this.finish();
        Intent i=new Intent(HubConnection.this,LoginActivity.class);
        startActivity(i);
    }
    @OnClick(R.id.btn_hub_connect)
    public void btn_hub_connect()
    {
        if (isConnected(HubConnection.this)) {
             Logs.error(TAG+"_Fserial_id","-------"+str_serial);
            //setTimeOut();
            showDialog();
            startAnimationProgress(20);
            txt_progress_placeholder.setText(this.getString(R.string.hub_scanning));
            UdpClientThread udpClientThread = new UdpClientThread(HubConnection.this, str_serial);
            udpClientThread.start();
            runnable = new Runnable() {
                @Override
                public void run() {
                    if (App.getSocket() == null) {
                        spm = new PrefManager(HubConnection.this);
                        spm.setValue("state_serial", "1");
                        spm.setValue("hub_serial", str_serial);
                        App.setCallfrom("HubConnection");
                        HubConnection.this.finish();
                        Intent i = new Intent(HubConnection.this, App.isOrientationFlag()?PinInputActivity.class:ConnectionLostActivity.class);
                        HubConnection.this.startActivity(i);
                    }

                }
            };

            handler = new Handler();
            handler.postDelayed(runnable, 15000);
        } else {
            CustomDialogShow.dispDialogWithOK(HubConnection.this, "Please check network...");
        }
    }
    public void onBackPressed() {
    }
    /**
     *  render UI or perform action on hub response
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *
     */
    private void setHubConnectionSubcriber() {
        hubconnectobserable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("");
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sub.onCompleted();
                    }
                }
        );
        hubconnectobserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                checkHub();
            }
            @Override
            public void onError(Throwable e) {
                Log.d("RXJAVA", "You should go check the sensor,  dude");
            }
            @Override
            public void onNext(String string) {
                checkHub();
                 Logs.error(TAG+"_FWHERIM","INPROGRESSSHOW");
            }
        };
        Subcriber s = new Subcriber();
        s.setObservable(hubconnectobserable);
        s.setObserver(hubconnectobserver);
        App.setHubsubcriber(s);
    }
    /**
     *  function returns string array of hub serial
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param jsonArray jsonarray of serial ids
     *
     */
    private String[] getStringArray(JSONArray jsonArray) {
        String[] stringArray = null;
        if (jsonArray != null) {
            int length = jsonArray.length();
            stringArray = new String[length+1];
           stringArray[0]="Please select Hub Id";
            for (int i = 0; i <length; i++) {
                stringArray[i+1] = jsonArray.optString(i);
            }
        }
        return stringArray;
    }
    /**
     *  show dialog
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *
     */
    private void showDialog()
    {
        progress=new Dialog(this,R.style.ProgressDialogTheme);
        progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress.setContentView(R.layout.custom_dialog_progress_percentage);
        RelativeLayout rel_main=progress.findViewById(R.id.rel_main);
        rel_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                UserInteractionSleep.userInteract(HubConnection.this);
                return false;
            }
        });
        txt_progress=progress.findViewById(R.id.txt_progress);
        txt_progress_placeholder=progress.findViewById(R.id.txt_progress_placeholder);
        txt_progress_placeholder.setText(this.getString(R.string.hub_scanning));
        startAnimationProgress(0);
        progress.show();
    }
    /**
     *  start progress animation with darwin loading icon
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *
     */
    private   void startAnimationProgress(int progress_int){
        ValueAnimator animator = ValueAnimator.ofInt(Integer.parseInt(txt_progress.getText().toString().replace("%","")), progress_int);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                txt_progress.setText(animation.getAnimatedValue().toString()+"%");
            }
        });
        animator.start();
    }
    /**
     *  enable or diable button on user choice
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *
     */
    private void setButtonEnable(boolean flag)
    {
        btn_hub_connect.setClickable(flag);
        btn_hub_connect.setEnabled(flag);
        if(flag)
        {
            btn_hub_connect.setTextColor(getResources().getColor(R.color.white));
            btn_hub_connect.setBackground(getResources().getDrawable(R.drawable.button_background_style));
        }
        else
        {
            btn_hub_connect.setTextColor(getResources().getColor(R.color.light_gray));
            btn_hub_connect.setBackground(getResources().getDrawable(R.drawable.btn_border_gray));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handler!=null) handler.removeCallbacks(runnable);

    }

    /**
     *  checking got or not any hub serial
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *
     */
    private void checkHub()
    {
        try {
            JSONObject hubjson = App.getHubConnectionData();
            Logs.error(TAG+"------------------------", "" + hubjson);
            if (hubjson != null) {
                if (str_serial.equals(hubjson.getString("serial"))) {
                    if(handler!=null) handler.removeCallbacks(runnable);
                    String ipaddress = hubjson.getString("address");
                    spm = new PrefManager(HubConnection.this);
                    spm.setValue("state_serial", "1");
                    spm.setValue("ip_address", ipaddress);
                    spm.setValue("hub_serial", str_serial);
                    startAnimationProgress(100);
                    progress.dismiss();
                    App.setHubConnectionData(null);
                    HubConnection.this.finish();
                    Intent i = new Intent(HubConnection.this, App.isOrientationFlag()?PinInputActivity.class:MainActivity.class);
                    HubConnection.this.startActivity(i);
                }
            } else {
                progress.dismiss();
                CustomDialogShow.dispDialogWithOK(HubConnection.this, "Wrong Serial......");
            }
        } catch (Exception ex) {
            Logs.error(TAG+"_FHubConnectionError", ex.getMessage());
        }
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

}