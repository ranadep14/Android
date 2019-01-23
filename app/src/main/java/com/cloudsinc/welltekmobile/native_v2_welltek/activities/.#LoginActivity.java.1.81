package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.CustomDialogShow;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.SocketConnectionTest.LoginSocketConnectionTest;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.SocketConnectionTest.SocketConnectionTest;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.threads.UdpClientThread;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.PassowrdEncryptDecrypt;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ScreenOrientation;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity.hideSystemUI;
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
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.ScreenOrientation.checkScreen;
/**Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 17/5/17.
 * @purpose  - A login screen that offers login via email/password.
 */
public class  LoginActivity extends AppCompatActivity {
    private View lyt;
    private PrefManager spm;
    private App app;
    Context context;
    LoginSocketConnectionTest lct=new LoginSocketConnectionTest();
    SocketConnectionTest sct=new SocketConnectionTest();
    @BindView(R.id.edt_email)EditText edt_email;
    @BindView(R.id.login_progress)View mProgressView;
    @BindView(R.id.rel_invalid_user_pass)RelativeLayout rel_invalid_user_pass;
    // private UserLoginTask mAuthTask = null;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    @BindView(R.id.edt_password)EditText edt_password;
    @BindView(R.id.img_login_logo)ImageView img_login_logo;
    @BindView(R.id.txt_new_account)TextView txt_new_account;
    @BindView(R.id.valid_password)TextView valid_password;
    @BindView(R.id.valid_user_name)TextView valid_user_name;
    @BindView(R.id.btn_signin)Button btn_signin;
    Handler handler,handler_retry;
    Runnable runnable,runnable_retry;
    private int deviceHight=0;
    private String str_email;
    private String str_password;
    private boolean cancel ;
    private View focusView;
    String str_serial="";
    private boolean flag_email=false;
    private boolean flag_passward=false;
    ColorStateList csl;
    TextView txt_progress_placeholder,txt_progress;
    Dialog progress;
    private String modelName = "", manufacturerName = "";
    String TAG="LoginActivity";
    private Tracker mTracker;
    private String str_retry_dialog_msg="";
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ScreenOrientation.setOrientation(this);
      /*  if (!GoogleAnalytics.checkConfiguration(this)) {
            Logs.info(TAG,"Error in FilterFragment");
        }
        App application = (App)getApplication();
        mTracker = application.getDefaultTracker();
        GoogleAnalytics.sendScreenImageName(""+this.getClass().getSimpleName().toString());*/
        if (!checkConfiguration()) {
            Logs.info(TAG,"Error in Login tracker");
        }
        App application = (App)getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();
        if(App.getSocket()!=null) {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_provisioned", new JSONObject()));
        }
        initializeDataBase();
        setFilterNotification();
        UserInteractionSleep.siboSleepChecking(this);

        if(checkScreen(LoginActivity.this,"tv"))
        {
            final AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this)
                    .setView(R.layout.custom_dialog)
                    .create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    // Prevent dialog close on back press button
                    return keyCode == KeyEvent.KEYCODE_BACK;
                }
            });
            dialog.show();
            TextView msgt= dialog.findViewById(R.id.title);
            msgt.setVisibility(View.GONE);
            TextView msg= dialog.findViewById(R.id.msg);
            msg.setText("This display is incompatible with this app.");
            Button diagButtonCancel = dialog.findViewById(R.id.customDialogCancel);
            diagButtonCancel.setText("NO");
            diagButtonCancel.setVisibility(View.GONE);
            diagButtonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            Button diagButtonOK = dialog.findViewById(R.id.customDialogOk);
            diagButtonOK.setText("EXIT");
            diagButtonOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LoginActivity.this.finishAffinity();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                    LoginActivity.this.startActivity(intent);
                    LoginActivity.this.finish();
                    System.exit(0);
                }
            });
        }
        else {
            Intent intent = getIntent();
            if (intent.getData() != null) {
                Uri uri = intent.getData();
                String username = uri.getQueryParameter("referrer");
                Log.e("LoginActivity", "-----------" + username + "---------");
            }
            Logs.info(TAG, "___________" + App.getCallfrom());
            if (App.getCallfrom().equals("authentication_error_409")) {
                CustomDialogShow.dispDialogWithOK(this, "" + getResources().getString(R.string.re_login));
            }
            csl = new ColorStateList(new int[][]{{}}, new int[]{R.drawable.edittext_user_line_red});
            App.setLoginData(null);
            App.setHubConnectionData(null);
            lyt = findViewById(R.id.login_lay);
            final Typeface mFont = Typeface.createFromAsset(getAssets(),
                    "fonts/SF-UI-Text-Regular.otf");
            final ViewGroup mContainer = (ViewGroup) lyt.getRootView();
            FontUtility.setAppFont(mContainer, mFont);
            ButterKnife.bind(this);
            try {
                System.out.println("dddddddddddddddddddd" + PassowrdEncryptDecrypt.decrypt("xm1otjgeyFOUSXWN/r/qFw=="));
            }catch (Exception ex)
            {
                Logs.error(TAG,"---------------"+ex.getMessage());
            }
            modelName = Build.MODEL;
            manufacturerName = Build.MANUFACTURER;
            //  //Toast.makeText(this, Build.MODEL, Toast.LENGTH_LONG).show();
            Log.i("model: ", Build.MODEL);
            Log.i("brand: ", Build.BRAND);
            Log.i("device: ", Build.DEVICE);
            Log.i("display: ", Build.DISPLAY);
            Log.i("id: ", Build.ID);
            Log.i("host: ", Build.HOST);
            Log.i("hardware: ", Build.HARDWARE);
            Log.i("manufacturer: ", Build.MANUFACTURER);
            Log.i("product: ", Build.PRODUCT);
     /*   if(modelName.equals("QUAD-CORE R40 sc3826r") && manufacturerName.equals("Allwinner")){
            //if(modelName.equals("KFSUWI") && manufacturerName.equals("Amazon")){
            //  sendData("SOUL");
            Intent i=new Intent(this,BoerHubConnection.class);
            startActivity(i);
            // App.setCallfrom("directHub");
        }*/


            String redString = getResources().getString(R.string.new_account);
            txt_new_account.setText(Html.fromHtml(redString));
            deviceHight = getScreenHeight();
            setEdittextEnable(edt_email, false);
            setEdittextEnable(edt_password, false);
            edt_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        setEdittextEnable(edt_password, true);
                    }
                    else
                    {
                        hideKeyboard(v);
                    }
                }
            });
            edt_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        setEdittextEnable(edt_email, true);
                    }
                    else
                    {
                        hideKeyboard(v);
                    }
                }
            });
            edt_password.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // other stuffs
                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.e("StringCount", "" + s.length());
                    String str = "";
                    if (s.length() == 0) {
                        flag_passward = false;
                        str = getResources().getString(R.string.empty_password);
                    } else if (s.length() < 6) {
                        str = getResources().getString(R.string.password_short);
                        flag_passward = false;
                    } else if (s.length() > 13) {
                        str = getResources().getString(R.string.password_long);
                        flag_passward = false;
                    } else {
                        str = "";
                        flag_passward = true;
                    }
                    valid_password.setText(str);
                    setButtonEnable(str);
                    // oth//er stuffs
                }
            });
            edt_email.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // other stuffs
                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String str = "";
                    if (s.length() == 0) {
                        flag_email = false;
                        str = getResources().getString(R.string.empty_user_id);
                    } else if (!validateUser(s.toString())) {
                        str = getResources().getString(R.string.valid_user_id);
                        flag_email = false;
                    } else {
                        str = "";
                        flag_email = true;
                    }
                    valid_user_name.setText(str);
                    setButtonEnable(str);
                    // other stuffs
                }
            });
            spm = new PrefManager(LoginActivity.this);
            spm.setValue("server_url", "https://delos.clouzer.com");
            if (!App.getSigned_user().equals("")) {
                String user_name = App.getSigned_user();
                if (user_name.equals("MainActivity")) user_name = "";
                if (user_name.contains("@darwin.com"))
                    user_name = user_name.replaceAll("@darwin.com", "");
                System.out.println("call_from" + user_name);
                if (validateUser(user_name)) {
                    flag_email = true;
                    edt_email.setText(user_name);
                    edt_email.setEnabled(false);
                    edt_email.setFocusable(false);
                }
            }
            System.out.println("lllllllllllll" + spm.getValue("state_serial"));
            navigateOnSerial();
        }
    }
    /**
     * for navigation depends on serial got or not
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     */
    public void navigateOnSerial()
    {
        Intent i=null;
        switch (spm.getValue("state_serial"))
        {
            case "1":
                App.setCallfrom("");
                i = new Intent(LoginActivity.this, MainActivity.class);
                break;
            case "2":
                i = new Intent(LoginActivity.this, HubConnection.class);
                break;
            default:
                app = (App) getApplicationContext();
                app.setErrorMessage(null);
                app.setContextForDialog(LoginActivity.this);
                App.setLoginSocket(null);
                App.setLoginSocket(lct.connect(spm.getValue("server_url")+"/login", App.getLoginSocket(), LoginActivity.this));
                break;
        }
        if(i!=null) {
            LoginActivity.this.finish();
            startActivity(i);
        }
    }
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        UserInteractionSleep.userInteract(this);
    }
    private void attemptLogin() {
        // Reset errors.
       /* validateEmail.setError(null);
        validatePassword.setError(null);*/
        // Store values at the time of the login attempt.
        str_email=edt_email.getText().toString().trim().toLowerCase();
        str_password=edt_password.getText().toString();
        cancel = false;
        focusView = null;
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(str_email)&&TextUtils.isEmpty(str_password) ) {
            showMessage(this,"Please enter Username and Password");
        }
        else {
            if(!validateUser(str_email))
            {
                showMessage(this,"Please enter valid Username ");
            }
            else if(!flag_passward)
            {
                showMessage(this,"Please enter valid password");
            }
            else {
                setSubcriber();
                if (isConnected(this)) {
                    if (lct.isSocketConnect(App.getLoginSocket())) {
                        if(!checkTechSupportUser()) {
                            try {
                                JSONArray action_array = new JSONArray();
                                action_array.put(0, "MOBILE_LOGIN");
                                JSONObject obj = new JSONObject();
                                obj.put("ACTION_ARRAY", action_array);
                                obj.put("username", str_email.contains("@darwin.com") ? str_email : str_email + "" + getResources().getString(R.string.domain));
                                obj.put("password", PassowrdEncryptDecrypt.encrypt(str_password));
                                Logs.info("LoginActivity", PassowrdEncryptDecrypt.encrypt(str_password) + "" + obj + "----------------" + obj);
                                App.getLoginSocket().emit("LOGIN", obj);
                                App.setLoginEmitJson(obj);
                            } catch (Exception ex) {
                                Log.e(this.getClass().getSimpleName(), "---" + ex.getMessage());
                            }
                            showDialog();
                            startAnimationProgress(20);
                            setTimeOut("login");
                        }
                        else
                        {
                            CustomDialogShow.dispDialog(LoginActivity.this, LoginActivity.class, ""+getResources().getString(R.string.tech_user));
                        }
                    } else {
                        str_retry_dialog_msg=getResources().getString(R.string.server_issue);
                        dispalyRetryDialog();
                    }
                } else {
                    str_retry_dialog_msg=getResources().getString(R.string.internet_issue);
                    dispalyRetryDialog();
                }
            }
        }
    }
    @OnClick(R.id.login_lay)
    public void closeKeyboard() {
        img_login_logo.setVisibility(View.VISIBLE);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
    @Optional
    @OnClick({R.id.btn_signin, R.id.txt_forget_pass, R.id.txt_new_account})
    public void btn_signin(View v)
    {
        Log.e("VID",""+v.getId());
        switch (v.getId())
        {
            case R.id.txt_new_account:
                System.out.print("neeeeeeeeeeeeeeeeeee");
                LoginActivity.this.finish();
                Intent ii=new Intent(this,SignUp.class);
                startActivity(ii);
                break;
            case R.id.txt_forget_pass:
                System.out.print("fffffffffffffffff");
                LoginActivity.this.finish();
                Intent i=new Intent(this,ForgetPasswordActivity.class);
                startActivity(i);
                break;
            case R.id.btn_signin:
                System.out.print("sssssssssssssssssssss");
                str_email=edt_email.getText().toString();
                str_password=edt_password.getText().toString();
                App.setLoginData(null);
                App.setHubConnectionData(null);
                attemptLogin();
                break;
        }
    }
    private void dispalyRetryDialog()
    {
       final Dialog d=new Dialog(this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.general_dialog);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setCanceledOnTouchOutside(false);
        TextView txtmsgstr= d.findViewById(R.id.txt_msg);
        txtmsgstr.setText(""+str_retry_dialog_msg);
        Button btnretry= d.findViewById(R.id.btn_retry);
        btnretry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.setLoginSocket(null);
                App.setLoginSocket(lct.connect(spm.getValue("server_url")+"/login", App.getLoginSocket(), LoginActivity.this));
                CustomDialogShow.circulerProgressDialog(LoginActivity.this,"");
                runnable_retry = new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("sssssssssssssssssssssssss"+App.getLoginSocket());
                        if (isConnected(LoginActivity.this)) {
                            if (lct.isSocketConnect(App.getLoginSocket())) {
                                if(d.isShowing())d.dismiss();
                                CustomDialogShow.stopProgressDialog();
                            }
                            else
                            {
                                if(d.isShowing())d.dismiss();
                                CustomDialogShow.stopProgressDialog();
                                str_retry_dialog_msg=getResources().getString(R.string.server_issue);
                                dispalyRetryDialog();
                            }
                        }
                        else
                        {
                            if(d.isShowing())d.dismiss();
                            CustomDialogShow.stopProgressDialog();
                            str_retry_dialog_msg=getResources().getString(R.string.internet_issue);
                            dispalyRetryDialog();
                        }
                    }
                };
                handler_retry = new Handler();
                handler_retry.postDelayed(runnable_retry, 15000);
            }
        });
        Button btncancle= d.findViewById(R.id.btncancle);
        btncancle.setVisibility(View.GONE);
        d.show();
    }
    public static boolean isEmailValid(String email) {
        String  expression="^[\\w\\-]([.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
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
            public void onNext(String response) {
                System.out.println("-----------------------"+response+"-----"+spm.getValue("clouzer_user")+"-------"+txt_progress.getText());
                if(response.equals("delos_connected")&&spm.getValue("clouzer_user").equals("yes")&&txt_progress.getText().equals("0%"))
                {
                    startAnimationProgress(30);
                    txt_progress_placeholder.setText(LoginActivity.this.getString(R.string.multi_signup_emit));
                    spm.setValue("clouzer_user","no");
                    App.setHubConnectionData(null);
                    App.setLoginData(null);
                    App.setAuthanticate(false);
                    emitNewHubRegisterRequest();
                }
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onCompleted() {
                Log.e("WHERIM","INPROGRESSSHOW");
                // Log.e("Response_string",response);
                try {
                    if(App.getHubConnectionData()!=null)
                    {
                        JSONObject hubjson = App.getHubConnectionData();
                        Log.e("HubData", "" + hubjson);
                        if (str_serial.equals(hubjson.getString("serial"))) {
                            String ipaddress = hubjson.getString("address");
                            spm.setValue("state_serial", "1");
                            spm.setValue("ip_address", ipaddress);
                            spm.setValue("hub_serial", str_serial);
                            startAnimationProgress(100);
                            App.setSocket(null);
                            App.setCallfrom("LoginActivity");
                            LoginActivity.this.finish();
                            final Intent mainIntent = new Intent(LoginActivity.this, App.isOrientationFlag()?PinInputActivity.class:MainActivity.class);
                            LoginActivity.this.startActivity(mainIntent);
                        }
                    }
                    if (App.getLoginData() != null) {
                        JSONObject loginjson = App.getLoginData();
                        System.out.println("---------------"+loginjson);
                        final JSONArray userJarr = loginjson.getJSONArray("ACTION_ARRAY");
                        if ("ERROR".equals(userJarr.getString(0))) {
                            String msg = loginjson.getString("MSG");
                            if(msg.equals("User id not present"))msg="User ID not present";
                            Log.e("LoginErrorMessage", msg);
                            progress.dismiss();
                            CustomDialogShow.dispDialogWithOK(LoginActivity.this, ""+msg);
                        }
                        else if("REGISTER_MULTI_HUB".equals(userJarr.getString(0)))
                        {
                            if(loginjson.has("MSG")) {
                                if(loginjson.getString("MSG").contains(getResources().getString(R.string.register_succeful))) {
                                    CustomDialogShow.dispDialogWithOK(LoginActivity.this, "Your new hub" + spm.getValue("signup_serial_id") + " register successfully");
                                    if (loginjson.has("HUB_LIST")) {
                                        if (loginjson.getJSONArray("HUB_LIST").length() == 1) {
                                            spm.setValue("state_serial", "1");
                                            startAnimationProgress(60);
                                            txt_progress_placeholder.setText(LoginActivity.this.getString(R.string.after_login_reponce_text));
                                            str_serial = loginjson.getJSONArray("HUB_LIST").getString(0);
                                            spm.setValue("hub_serial", str_serial);
                                            Log.e("Hub_serial", loginjson.getJSONArray("HUB_LIST").getString(0));
                                            UdpClientThread udpClientThread = new UdpClientThread(LoginActivity.this, loginjson.getJSONArray("HUB_LIST").getString(0));
                                            udpClientThread.start();
                                            App.setLoginData(null);
                                            App.setSocket(null);
                                            App.setLoginSocket(null);
                                        }
                                        if (loginjson.getJSONArray("HUB_LIST").length() > 1) {
                                            spm.setValue("state_serial", "2");
                                            startAnimationProgress(90);
                                            App.setLoginSocket(null);
                                            App.setSocket(null);
                                            spm.setValue("multiple_hub_array", loginjson.getJSONArray("HUB_LIST").toString());
                                            LoginActivity.this.finish();
                                            Intent i = new Intent(LoginActivity.this, HubConnection.class);
                                            startActivity(i);
                                            App.setLoginData(null);
                                        }
                                    }
                                }
                                else
                                {
                                    CustomDialogShow.dispDialogWithOK(LoginActivity.this,""+loginjson.getString("MSG"));
                                }
                            }
                        }
                        else if ("MOBILE_LOGIN".equals(userJarr.getString(0))) {
                            String path = "";
                            Logs.info(TAG,"jwt_token....."+loginjson.getString("jwtToken"));
                            spm.setValue("jwt_token", loginjson.getString("jwtToken"));
                            spm.setValue("password", str_password);
                            spm.setValue("image_title", "" + loginjson.getJSONObject("Info").getJSONObject("myInfo").getString("firstName") + "_" + loginjson.getJSONObject("Info").getJSONObject("myInfo").getString("lastName"));
                            spm.setValue("User_Name", "" + loginjson.getJSONObject("Info").getJSONObject("myInfo").getString("firstName") + " " + loginjson.getJSONObject("Info").getJSONObject("myInfo").getString("lastName"));
                            if (loginjson.has("USM_IMAGE_PATH")) {
                                path = loginjson.getString("USM_IMAGE_PATH").replace(" ", "%20");
                                spm.setValue("User_Prof_Image", spm.getValue("server_url") + "" + path);
                                spm.setValue("Old_Profile_image", spm.getValue("server_url") + "" + path);
                            } else {
                                if(loginjson.getJSONObject("Info").getJSONObject("myInfo").getJSONObject("profile_data").has("imagePath"))path = loginjson.getJSONObject("Info").getJSONObject("myInfo").getJSONObject("profile_data").getString("imagePath").replace(" ", "%20");
                                spm.setValue("User_Prof_Image", spm.getValue("server_url") + "/" + path);
                                spm.setValue("Old_Profile_image", spm.getValue("server_url") + "/" + path);
                            }
                            spm.setValue("Sub_Cat", loginjson.getJSONObject("Info").getJSONObject("myInfo").getJSONObject("profile_data").getString("subCategory"));
                            spm.setValue("Key_Type", loginjson.getJSONObject("Info").getJSONObject("myInfo").getJSONObject("profile_data").getString("keyType"));
                            spm.setValue("User_Id", str_email.contains("@darwin.com") ? str_email : str_email + "" + getResources().getString(R.string.domain));
                            spm.setValue("password", str_password);
                            if(loginjson.has("RECOVERY_MAIL"))spm.setValue("Recovery_Mail", loginjson.getString("RECOVERY_MAIL"));
                            spm.setValue("First_Name", loginjson.getJSONObject("Info").getJSONObject("myInfo").getString("firstName"));
                            spm.setValue("Last_Name", loginjson.getJSONObject("Info").getJSONObject("myInfo").getString("lastName"));
                            spm.setValue("CML_ID", loginjson.getJSONObject("USM_DEFAULT_WORKSPACE").getString("cmlId"));
                            spm.setValue("Socket_Id", loginjson.getString("socketId"));
                            spm.setValue("pk_val", loginjson.getString("socketId"));
                            spm.setValue("user_email", loginjson.getString("USM_EMAIL"));
                            if(loginjson.has("HUB_LIST"))
                            {
                                if(loginjson.getJSONArray("HUB_LIST").length()==1)
                                {
                                    spm.setValue("state_serial", "1");
                                    if(spm.getValue("clouzer_user").equals("yes")&&App.getCallfrom().equals("SignUpActivity"))
                                    {
                                        makeAuthanticate();
                                    }
                                    else {
                                        startAnimationProgress(50);
                                        txt_progress_placeholder.setText(LoginActivity.this.getString(R.string.after_login_reponce_text));
                                        str_serial = loginjson.getJSONArray("HUB_LIST").getString(0);
                                        spm.setValue("hub_serial", str_serial);
                                        Log.e("Hub_serial", loginjson.getJSONArray("HUB_LIST").getString(0));
                                        UdpClientThread udpClientThread = new UdpClientThread(LoginActivity.this, loginjson.getJSONArray("HUB_LIST").getString(0));
                                        udpClientThread.start();
                                        App.setLoginData(null);
                                    }
                                }
                                if(loginjson.getJSONArray("HUB_LIST").length()>1)
                                {
                                    spm.setValue("state_serial", "2");
                                    if(spm.getValue("clouzer_user").equals("yes")&&App.getCallfrom().equals("SignUpActivity"))
                                    {
                                        makeAuthanticate();
                                    }
                                    else {
                                        startAnimationProgress(80);
                                        App.setLoginSocket(null);
                                        App.setHub_ids(loginjson.getJSONArray("HUB_LIST"));
                                        spm.setValue("multiple_hub_array", loginjson.getJSONArray("HUB_LIST").toString());
                                        LoginActivity.this.finish();
                                        Intent i = new Intent(App.getContextForDialog(), HubConnection.class);
                                        startActivity(i);
                                        App.setLoginData(null);
                                    }
                                }
                            }
                        }
                    }
                } catch(JSONException e){
                    Log.e("LoginDataError", e.getMessage());
                    // e.printStackTrace();
                }
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);
    }
    private static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
        if(handler!=null) handler.removeCallbacks(runnable);
        if(handler_retry!=null) handler_retry.removeCallbacks(runnable_retry);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            hideSystemUI(this);
        }
        super.onWindowFocusChanged(hasFocus);
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
    public void setTimeOut(String str)
    {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (App.getSocket() == null) {
                    if(txt_progress.getText().toString().equals("50%")||txt_progress.getText().toString().equals("50%"))
                    {
                        LoginActivity.this.finish();
                        Intent i = new Intent(LoginActivity.this, App.isOrientationFlag()?PinInputActivity.class:ConnectionLostActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        LoginActivity.this.startActivity(i);
                    }
                }
            }
        };
        handler = new Handler();
        handler.postDelayed(runnable, 8000);
    }
    public static Drawable setTint(Drawable drawable, int color) {
        final Drawable newDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(newDrawable, color);
        return newDrawable;
    }
    public void setButtonEnable(String msg)
    {
        if(flag_passward&&flag_email)
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
    public void makeAuthanticate()
    {
        startAnimationProgress(0);
        txt_progress_placeholder.setText(LoginActivity.this.getString(R.string.multi_signup_connect));
        App.getLoginSocket().disconnect();
        App.setSocket(null);
        System.out.println("======"+spm.getValue("clouzer_user"));
        App.setSocket(new LoginSocketConnectionTest().connect(spm.getValue("server_url")+"/login", App.getSocket(), getApplicationContext()));
    }
    public static void showMessage(Context mcontext,String str_msg)
    {
        final AlertDialog dialog = new AlertDialog.Builder(mcontext)
                .setView(R.layout.custom_dialog_single)
                .create();
        View v = dialog.getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        dialog.show();
        TextView msgt= dialog.findViewById(R.id.title);
        msgt.setText("Alert");
        TextView msg= dialog.findViewById(R.id.msg);
        msg.setText(""+str_msg);
        Button diagButtonOK = dialog.findViewById(R.id.customDialogOk);
        diagButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    private void sendData(final String message) {
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            String strData;
            @Override
            public void run() {
                String serialID="";
                JSONArray jsonObject=new JSONArray();
                DatagramSocket ds = null;
                try {
                    ds = new DatagramSocket();
                    InetAddress serverAddr = InetAddress.getByName("255.255.255.255");
                    DatagramPacket dp;
                    dp = new DatagramPacket(message.getBytes(), message.length(), serverAddr, 5984);
                    ds.send(dp);
                    byte[] lMsg = new byte[1000];
                    dp = new DatagramPacket(lMsg, lMsg.length);
                    ds.receive(dp);
                    strData = new String(lMsg, 0, dp.getLength());
                    Log.d("RESPONSE is",strData);
                    JSONObject seialObj=new JSONObject(strData);
                    serialID=seialObj.getString("serial");
                    jsonObject.put(0,serialID);
                    App.setHub_ids(jsonObject);
                    //Toast.makeText(LoginActivity.this, ""+strData, Toast.LENGTH_SHORT).show();
                    Log.d("RESPONSE is------------",serialID);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (ds != null) {
                        ds.close();
                    }
                }
            }
        });
        thread.start();
    }
    public void emitNewHubRegisterRequest()
    {
        try {
            JSONArray action_array = new JSONArray();
            action_array.put(0, "REGISTER_MULTI_HUB");
            JSONObject dataObject=new JSONObject();
            dataObject.put("TN","CAL_MAIL");
            dataObject.put("CON_PARAM",new JSONObject().put("PK","CML_ID").put("PKVAL",""));
            dataObject.put("RECORD",new JSONObject());
            dataObject.put("ACTION_ARRAY",new JSONArray().put("REGISTER_MULTI_HUB"));
            JSONObject updateJson=new JSONObject();
            updateJson.put("action","UPDATE");
            updateJson.put("CON_PARAM",new JSONObject().put("ACTION_ARRAY",new JSONArray().put("REGISTER_MULTI_HUB")));
            updateJson.put("dataArray",new JSONArray().put(dataObject));
            JSONObject obj = new JSONObject();
            obj.put("action", "UPDATE");
            obj.put("username", spm.getValue("signup_userid"));
            obj.put("ACTION_ARRAY", action_array);
            obj.put("hubId", spm.getValue("signup_serial_id"));
            //  obj.put("requestId", "sync/#" + "" + App.getSocket().id() + "" + new PrefManager(this).getValue("User_Id") + "#" + System.currentTimeMillis());
            obj.put("clientInfo", new JSONObject().put("userId", spm.getValue("User_Id")));
            obj.put("moduleName", "LOGIN");
            obj.put("RECOVERY_EMAIL", spm.getValue("signup_recovery_email"));
            obj.put("topic", "DELOS_REGISTER");
            // obj.put("socketId", App.getSocket().id());
            obj.put("updateObj", updateJson);
            Log.d("REGISTER", "" + obj);
            System.out.println("Register Obj: " + obj);
            App.getSocket().emit("LOGIN", obj);
        }
        catch (Exception ex)
        {
            Log.e("newregisterCallerror",ex.getMessage());
        }
    }
    public static boolean validateUser(String username){
        String expression = "[a-zA-Z0-9._-]+@darwin.com";
        String expression1 = "[a-zA-Z0-9._-]+";
        CharSequence inputStr = username;
        Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
        Pattern pattern1 = Pattern.compile(expression1,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        Matcher matcher1 = pattern1.matcher(inputStr);
        return matcher.matches()||matcher1.matches();
    }
    public void showDialog()
    {
        progress=new Dialog(this,R.style.ProgressDialogTheme);
        progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress.setContentView(R.layout.custom_dialog_progress_percentage);
        txt_progress_placeholder=progress.findViewById(R.id.txt_progress_placeholder);
        txt_progress=progress.findViewById(R.id.txt_progress);
        txt_progress_placeholder.setText(this.getString(R.string.after_login_emit_text));
        startAnimationProgress(0);
        progress.show();
    }
    private void startAnimationProgress(int progress_int){
        ValueAnimator animator = ValueAnimator.ofInt(Integer.parseInt(txt_progress.getText().toString().replace("%","")), progress_int);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                txt_progress.setText(animation.getAnimatedValue().toString()+"%");
            }
        });
        animator.start();
    }
    public boolean checkTechSupportUser()
    {
        return (str_email.contains("tech.support"));
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
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    public void setFilterNotification()
    {
        PrefManager spm=new PrefManager(this);
        if(spm.getValue("water_filter").equals("No Record"))spm.setValue("water_filter","1");
        if(spm.getValue("air_filter").equals("No Record"))spm.setValue("air_filter","1");
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

    private void sendScreenImageName() {
        String name = "Login Activity";
        // [START screen_view_hit]
        Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName("Darwin~" + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
    }

    private boolean checkConfiguration() {
        XmlResourceParser parser = getResources().getXml(R.xml.global_tracker);

        boolean foundTag = false;
        try {
            while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlResourceParser.START_TAG) {
                    String tagName = parser.getName();
                    String nameAttr = parser.getAttributeValue(null, "name");

                    foundTag = "string".equals(tagName) && "ga_trackingId".equals(nameAttr);
                }

                if (parser.getEventType() == XmlResourceParser.TEXT) {
                    if (foundTag && parser.getText().contains("REPLACE_ME")) {
                        return false;
                    }
                }

                parser.next();
            }
        } catch (Exception e) {
            Log.w(TAG, "checkConfiguration", e);
            return false;
        }

        return true;
    }

}