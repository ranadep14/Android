package com.cloudsinc.welltekmobile.native_v2_welltek.activities;
/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 8/6/17.
 * @purpose  - New user sign up for Clouzer application
 **/

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.LoginActivity.validateUser;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.listeners.SocketListeners.cancleNotification;
//@DeepLink("http://clouzer.com/registration/{user_name}")
/**
 * This class containing functionality related signup new user.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class SignUp extends AppCompatActivity {
    private Observable<String> signupobserable;
    private Observer<String> signupobserver;
    private Observable<String> usermobservable;
    private Observer<String> usermyObserver;
    private View lyt;
    private Tracker mTracker;
    private Context context;
    @BindView(R.id.validenmail)
    TextView vuserid;
    @BindView(R.id.validpassword)
    TextView vpwd;
    @BindView(R.id.validRepeatpwd)
    TextView repwd;
    @BindView(R.id.validfirtsname)
    TextView validfirtsname;
    @BindView(R.id.validlastname)
    TextView validlastname;
    @BindView(R.id.validserialid)
    TextView validserialid;
    @BindView(R.id.edt_email)
    EditText userid;
    @BindView(R.id.edt_password)
    EditText password;
    @BindView(R.id.edt_Repeat_password)
    EditText re_pwd;
    @BindView(R.id.edt_last_name)
    EditText edt_last_name;
    @BindView(R.id.edt_first_name)
    EditText edt_first_name;
    @BindView(R.id.edt_serial_id)
    EditText edt_serial_id;
    @BindView(R.id.edt_recovery_email)
    EditText edt_recovery_email;
    private String TAG = "SignUp";
    private PrefManager spm;
    private SocketConnectionTest sct = new SocketConnectionTest();
    private App app;
    private String userId;
    private String recover_email;
    private String passwd;
    private String re_password;
    private String first_name;
    private String last_name;
    private String serial_id;
    private String response_serial_id = "";
    private boolean cancel;
    private View focusView;
    private String ip_address="";
    public  Dialog progress;
    private Runnable runnable;
    private Handler handler;
    public  TextView txt_progress_placeholder,txt_progress;
    boolean user_flag = false, flag_userid = false, flag_first_name = false, flag_serial_id = false, flag_last_name = false, flag_repwd = false, flag_password = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        System.out.print("ssssssssssssssssssss");
        ButterKnife.bind(this);
        if (!checkConfiguration()) {
            Logs.info(TAG,"Error in FilterFragment");
        }
        App application = (App)getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();
        if(App.getSocket()!=null) {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_provisioned", new JSONObject()));
        }
        ScreenOrientation.setOrientation(this);
        UserInteractionSleep.siboSleepChecking(this);
        App.setFlag_connection_lost(false);
        spm = new PrefManager(SignUp.this);
        if (App.getSocket()!=null)
        {
            App.getSocket().off();
            App.setSocket(null);
        }
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        cancleNotification(this,"Air");
        cancleNotification(this,"Water");
        cancleNotification(this,"Dawn");
        cancleNotification(this,"Sleep");
        spm.setValue("jwt_token","No Record");
        spm.setValue("state_serial","No Record");
        spm.setValue("hub_serial","No Record");
        spm.setValue("Simulation_info","No Record");
        spm.setValue("ip_address","No Record");
        spm.setValue("connectin_to_hub","No Record");
        spm.setValue("clouzer_user","No Record");
        showDialog();
        startAnimationProgress(0);
        txt_progress_placeholder.setText(this.getString(R.string.hub_scanning));
        setEdittextEnable(userid, false);
        setEdittextEnable(password, false);
        setEdittextEnable(re_pwd, false);
        setEdittextEnable(edt_last_name, false);
        setEdittextEnable(edt_first_name, false);
        setEdittextEnable(edt_serial_id, false);
        setEdittextEnable(edt_recovery_email, false);
        setFocusListener(userid);
        setFocusListener(password);
        setFocusListener(re_pwd);
        setFocusListener(edt_last_name);
        setFocusListener(edt_first_name);
        setFocusListener(edt_serial_id);
        setFocusListener(edt_recovery_email);
        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();
            String username =uri.getQueryParameter("emailid")/*"sfcsdsdvvds@gmail.com"*/;
            serial_id =uri.getQueryParameter("hubId")/*"Welltek-00000005"*/;
            spm.setValue("signup_recovery_email",username);
            spm.setValue("signup_serial_id",serial_id);
            recover_email = username;
            edt_serial_id.setText(serial_id);
            edt_recovery_email.setText(recover_email);
            if (username.length() > 0) {
                flag_userid = true;
            }
            Log.e(TAG, "-----------" + username + "---------" + serial_id);
        }
        if(!spm.getValue("signup_recovery_email").equals("No Record"))edt_recovery_email.setText(""+spm.getValue("signup_recovery_email"));
        if(!spm.getValue("signup_serial_id").equals("No Record"))edt_serial_id.setText(""+spm.getValue("signup_serial_id"));
        serial_id=spm.getValue("signup_serial_id");
        recover_email=spm.getValue("signup_recovery_email");
        setHubSubcriber();
        setTimeOut();
        UdpClientThread udpClientThread = new UdpClientThread(SignUp.this, serial_id);
        udpClientThread.start();
        lyt = findViewById(R.id.sign_up_form);
        final Typeface mFont = Typeface.createFromAsset(getAssets(),
                "fonts/SF-UI-Text-Regular.otf");
        final ViewGroup mContainer = (ViewGroup) lyt.getRootView();
        FontUtility.setAppFont(mContainer, mFont);
        app = (App) getApplicationContext();
        App.setLoginData(null);
        app.setErrorMessage(null);
        App.setLoginSocket(null);
        app.setContextForDialog(SignUp.this);
        System.out.println("------------------"+new PrefManager(SignUp.this).equals("yes"));
        re_pwd.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0 && ismatch(s.toString(),password) ){
                    flag_repwd = true;
                    repwd.setVisibility(View.GONE);
                } else {
                    repwd.setText(getString(R.string.valid_match_pass));
                    repwd.setVisibility(View.VISIBLE);
                    flag_repwd = false;
                }
                // other stuffs
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("StringCount", "" + s.length());
                String str = "";
                if (s.length() == 0) {
                    flag_password = false;
                    str = getResources().getString(R.string.empty_password);
                } else if (s.length() < 6) {
                    str = getResources().getString(R.string.password_short);
                    flag_password = false;
                } else if (s.length() > 13) {
                    str = getResources().getString(R.string.password_long);
                    flag_password = false;
                } else {
                    str = "";
                    flag_password = true;
                }
                if (!str.equals("")) vpwd.setVisibility(View.VISIBLE);
                else vpwd.setVisibility(View.GONE);
                vpwd.setText(str);
                if (s.length() > 0 && ismatch(s.toString(),re_pwd)) {
                    flag_repwd = true;
                    repwd.setVisibility(View.GONE);
                } else {
                    repwd.setText(getString(R.string.valid_match_pass));
                    repwd.setVisibility(View.VISIBLE);
                    flag_repwd = false;
                }
                // other stuffs
            }
        });
        userid.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = "";
                if (s.length() == 0) {
                    flag_userid = false;
                    str = getResources().getString(R.string.empty_user_id);
                } else if (!validateUser(s.toString() )){
                    str = getResources().getString(R.string.valid_user_id);
                    flag_userid = false;
                } else {
                    str = "";
                    flag_userid = true;
                }
                if (!str.equals("")) vuserid.setVisibility(View.VISIBLE);
                else vuserid.setVisibility(View.GONE);
                vuserid.setText(str);
            }
        });
        edt_first_name.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    flag_first_name = true;
                    validfirtsname.setVisibility(View.GONE);
                } else {
                    flag_first_name = false;
                    validfirtsname.setVisibility(View.VISIBLE);
                }
                // other stuffs
            }
        });
        edt_serial_id.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    flag_serial_id = true;
                    validserialid.setVisibility(View.GONE);
                } else {
                    flag_serial_id = false;
                    validserialid.setVisibility(View.VISIBLE);
                }
                // other stuffs
            }
        });
        edt_last_name.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    flag_last_name = true;
                    validlastname.setVisibility(View.GONE);
                } else {
                    flag_last_name = false;
                    validlastname.setVisibility(View.VISIBLE);
                }
                // other stuffs
            }
        });
    }
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        UserInteractionSleep.userInteract(this);
    }
    /**
     *  this method call after click on signup
     *
     *@author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *
     */
    private void attemptREGISTER() {
        vuserid.setError(null);
        vpwd.setError(null);
        first_name = edt_first_name.getText().toString();
        last_name = edt_last_name.getText().toString();
        serial_id = edt_serial_id.getText().toString();
        userId = userid.getText().toString();
        passwd = password.getText().toString();
        Log.e("Sigup_Flags", "----------------" + flag_userid + "---------" + flag_password + "----------" + flag_repwd);
        // Check for a valid password, if the user entered one.
        if (flag_userid && flag_password && flag_repwd && flag_first_name && flag_last_name) {
            if (isConnected(this)) {
                if (sct.isSocketConnect(App.getSocket())) {
                    setSignUpSubcriber();
                    // //Toast.makeText(SignUp.this, "isSocketConnect", Toast.LENGTH_SHORT).show();
                    try {
                        JSONArray action_array = new JSONArray();
                        action_array.put(0, "REGISTER_HUB_USER");
                        JSONObject obj = new JSONObject();
                        obj.put("ACTION_ARRAY", action_array);
                        obj.put("hubId", serial_id);
                        obj.put("RegisterWith", "mstorm");
                        obj.put("firstName", first_name);
                        obj.put("lastName", last_name);
                        obj.put("rPassword", PassowrdEncryptDecrypt.encrypt(re_password));
                        obj.put("topic", "DELOS_REGISTER");
                        obj.put("RECOVERY_MAIL",recover_email);
                        obj.put("username", (userId+""+getResources().getString(R.string.domain)).toLowerCase());
                        Log.d("REGISTER", "" + obj);
                        System.out.println("Register Obj: " + obj);
                        App.getSocket().emit("LOGIN", obj);
                        showDialog();
                        startAnimationProgress(20);
                        txt_progress_placeholder.setText(SignUp.this.getString(R.string.signup_in_progress));
                        //set empty
                        Log.d("inside REGISTER socket", "");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    progress.dismiss();
                    showDialogForCloseApp(""+getString(R.string.hub_offline));
                }
            } else {
                progress.dismiss();
                showDialogForCloseApp(""+getResources().getString(R.string.server_issue));
            }
        }
        // Check for a valid email address.
        else {
            /******if all true go to Next Page***********/
            final AlertDialog dialog = new AlertDialog.Builder(this)
                    .setView(R.layout.custom_dialog_single)
                    .create();
            View v = dialog.getWindow().getDecorView();
            v.setBackgroundResource(android.R.color.transparent);
            dialog.show();
            TextView msgt = dialog.findViewById(R.id.title);
            msgt.setText("Alert");
            TextView msg = dialog.findViewById(R.id.msg);
            msg.setText("Please enter all credentials");
            Button diagButtonOK = dialog.findViewById(R.id.customDialogOk);
            diagButtonOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    // setValidTextVisible();
                }
            });
        }
    }
    @OnClick(R.id.btn_signUp)
    public void btnSignUp() {
        first_name = "";
        last_name = "";
        userId = userid.getText().toString();
        passwd = password.getText().toString();
        re_password = re_pwd.getText().toString();
        attemptREGISTER();
    }
    /**
     * check internet on device
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param context  of current class
     *
     */
    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
    /**
     * set opacity to edittext
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param edt_text edittexview which will enable or diable
     * @param flag
     */
    private void setEdittextEnable(EditText edt_text, boolean flag) {
        if (flag) {
            edt_text.setAlpha(1);
        } else {
            edt_text.setAlpha(0.5f);
        }
    }
    /**
     *  set focus listener to edittextview
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *
     */
    private void setFocusListener(final EditText edt_select) {
        edt_select.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setEdittextEnable(edt_select, true);
                }
            }
        });
    }
    /**
     *  render UI or perform action on delos response
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *
     */
    private void setSignUpSubcriber() {
        Log.e("WHERIM", "INPROGRESSSHOW");
        signupobserable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("");
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sub.onCompleted();
                    }
                }
        );
        signupobserver = new Observer<String>() {
            @Override
            public void onNext(String response) {
                Log.e("WHERIM", "INPROGRESSSHOW");
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onCompleted() {
                Log.e("WhereIM", "InSignUp");
                try {
                    JSONObject loginjson = App.getLoginData();
                    Log.e("WhereIM", "InSignUp" + loginjson);
                    if (loginjson != null) {
                        final JSONArray userJarr = loginjson.getJSONArray("ACTION_ARRAY");
                        if ("ERROR".equals(userJarr.getString(0))) {
                            Log.e("WhereIM", "InSignUp");
                            if(progress.isShowing()) progress.dismiss();
                            CustomDialogShow.dispDialogWithOK(SignUp.this, "" + loginjson.getString("MSG"));
                            App.setLoginData(null);
                        } else if ("REGISTER_HUB_USER".equals(userJarr.getString(0))) {
                            Log.e("SignupWhereIm", "inLogin");
                            String path = "";
                            spm.setValue("jwt_token", loginjson.getString("jwtToken"));
                            spm.setValue("password", passwd);
                            spm.setValue("image_title", "" + loginjson.getJSONObject("Info").getJSONObject("myInfo").getString("firstName") + "_" + loginjson.getJSONObject("Info").getJSONObject("myInfo").getString("lastName"));
                            spm.setValue("User_Name", "" + loginjson.getJSONObject("Info").getJSONObject("myInfo").getString("firstName") + " " + loginjson.getJSONObject("Info").getJSONObject("myInfo").getString("lastName"));
                            if (loginjson.has("USM_IMAGE_PATH")) {
                                path = loginjson.getString("USM_IMAGE_PATH").replace(" ", "%20");
                                spm.setValue("User_Prof_Image", spm.getValue("server_url") + "" + path);
                                spm.setValue("Old_Profile_image", spm.getValue("server_url") + "" + path);
                            } else {
                                if(loginjson.getJSONObject("Info").getJSONObject("myInfo").getJSONObject("profile_data").has("imagePath")) path = loginjson.getJSONObject("Info").getJSONObject("myInfo").getJSONObject("profile_data").getString("imagePath").replace(" ", "%20");
                                spm.setValue("User_Prof_Image", spm.getValue("server_url") + "/" + path);
                                spm.setValue("Old_Profile_image", spm.getValue("server_url") + "/" + path);
                            }
                            spm.setValue("Sub_Cat", loginjson.getJSONObject("Info").getJSONObject("myInfo").getJSONObject("profile_data").getString("subCategory"));
                            spm.setValue("Key_Type", loginjson.getJSONObject("Info").getJSONObject("myInfo").getJSONObject("profile_data").getString("keyType"));
                            spm.setValue("User_Id", userId.contains("@darwin.com") ? userId : userId + "" + getResources().getString(R.string.domain));
                            spm.setValue("First_Name", loginjson.getJSONObject("Info").getJSONObject("myInfo").getString("firstName"));
                            spm.setValue("Last_Name", loginjson.getJSONObject("Info").getJSONObject("myInfo").getString("lastName"));
                            spm.setValue("CML_ID", loginjson.getJSONObject("USM_DEFAULT_WORKSPACE").getString("cmlId"));
                            spm.setValue("Socket_Id", loginjson.getString("socketId"));
                            if(loginjson.has("RECOVERY_MAIL"))spm.setValue("Recovery_Mail", loginjson.getString("RECOVERY_MAIL"));
                            spm.setValue("pk_val", loginjson.getString("socketId"));
                            spm.setValue("user_email", loginjson.getString("USM_EMAIL"));
                            if (loginjson.has("HUB_LIST")) {
                                if (loginjson.getJSONArray("HUB_LIST").length() == 1) {
                                    response_serial_id = loginjson.getJSONArray("HUB_LIST").getString(0);
                                    App.setLoginSocket(null);
                                    spm = new PrefManager(SignUp.this);
                                    spm.setValue("hub_serial", loginjson.getJSONArray("HUB_LIST").getString(0));
                                    spm = new PrefManager(SignUp.this);
                                    spm.setValue("state_serial", "1");
                                    spm.setValue("ip_address", ip_address);
                                    spm.setValue("hub_serial", serial_id);
                                    progress.dismiss();
                                    App.setSocket(null);
                                    startAnimationProgress(100);
                                    App.setHubConnectionData(null);
                                    App.setCallfrom("SignupActivity");
                                    SignUp.this.finish();
                                    Intent i = new Intent(SignUp.this, MainActivity.class);
                                    startActivity(i);
                                }
                                if (loginjson.getJSONArray("HUB_LIST").length() > 1) {
                                    progress.dismiss();
                                    spm.setValue("state_serial", "2");
                                    App.setLoginSocket(null);
                                    App.setHub_ids(loginjson.getJSONArray("HUB_LIST"));
                                    spm.setValue("multiple_hub_array", loginjson.getJSONArray("HUB_LIST").toString());
                                    startAnimationProgress(100);
                                    SignUp.this.finish();
                                    App.setMain_activity_subcriber(null);
                                    Intent i = new Intent(App.getContextForDialog(), HubConnection.class);
                                    startActivity(i);
                                    App.setLoginData(null);
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    Log.e("SignUpError", "" + e.getMessage());
                }
            }
        };
        Subcriber s = new Subcriber();
        s.setObservable(signupobserable);
        s.setObserver(signupobserver);
        App.setCurrentSubcriber(s);
    }
    /**
     *  render UI or perform action on delos response
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *
     */
    private void setCheckUserSubcriber() {
        usermobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext(""+App.getRoomData());
                        sub.onCompleted();
                    }
                }
        );
        usermyObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(String string) {
                Logs.info(TAG, "rrrrrrrrrrrrrr" + string+"rrrrrrrrrrrrr"+App.getLoginData());
                try {
                    if (string.equals("LoginResponce")) {
                        JSONObject jsonObject = App.getLoginData();
                        if (jsonObject != null) {
                            if (jsonObject.has("ACTION_ARRAY")) {
                                if (jsonObject.getJSONArray("ACTION_ARRAY").getString(0).equals("CHECK_USER")) {
                                    Logs.info(TAG, "rrrrrrrrrrrrrr" + jsonObject);
                                    spm.setValue("signup_userid", jsonObject.getString("username"));
                                    App.setSigned_user("" + jsonObject.getString("username"));
                                    user_flag = true;
                                    new PrefManager(SignUp.this).setValue("clouzer_user", "yes");
                                    App.setCallfrom("SignUpActivity");
                                    SignUp.this.finish();
                                    Intent i = new Intent(SignUp.this, LoginActivity.class);
                                    startActivity(i);
                                    App.setMain_activity_subcriber(null);
                                    App.setLoginData(null);
                                }
                                if(jsonObject.getJSONArray("ACTION_ARRAY").getString(0).equals("ERROR"))
                                {
                                    App.setLoginData(null);
                                    App.setMain_activity_subcriber(null);
                                    progress.dismiss();
                                }
                            }
                        }
                    }
                    if (string.equals("delos_connected")) {
                        try {
                            JSONArray action_array = new JSONArray();
                            action_array.put(0, "CHECK_USER");
                            JSONObject obj = new JSONObject();
                            obj.put("RECOVERY_MAIL", recover_email);
                            obj.put("topic", "DELOS_LOGIN");
                            obj.put("ACTION_ARRAY", action_array);
                            Logs.info("LoginActivity", "----------------" + obj);
                            App.getLoginSocket().emit("LOGIN", obj);
                        } catch (Exception ex) {
                            Log.e(this.getClass().getSimpleName(), "---" + ex.getMessage());
                        }
                    }
                }
                catch (Exception ex)
                {
                    Logs.error(TAG,"error...."+ex.getMessage());
                }
            }
        } ;
        Subcriber s = new Subcriber();
        s.setObservable(usermobservable);
        s.setObserver(usermyObserver);
        App.setMain_activity_subcriber(s);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            MainActivity.hideSystemUI(this);
        }
        super.onWindowFocusChanged(hasFocus);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
        if(handler!=null) handler.removeCallbacks(runnable);
    }
    /**
     *  render UI or perform action on hub response
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *
     */
    private void setHubSubcriber() {
        signupobserable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("");
                        try {
                            Thread.sleep(13000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sub.onCompleted();
                    }
                }
        );
        signupobserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                try {
                    JSONObject hubjson = App.getHubConnectionData();
                    Logs.error(TAG+"_HubData", "" + hubjson);
                    if (hubjson != null) {
                        ip_address = hubjson.getString("address");
                        spm.setValue("ip_address",ip_address);
                        App.setSocket(sct.connect("http://"+ip_address+"/login", App.getSocket(), getApplicationContext()));
                        startAnimationProgress(50);
                        txt_progress_placeholder.setText(SignUp.this.getString(R.string.check_user));
                        checkClouzerUser();
                    } else {
                        progress.dismiss();
                        showDialogForCloseApp(""+getString(R.string.hub_offline));
                    }
                } catch (Exception ex) {
                    Logs.error(TAG+"_HubConnectionError", ex.getMessage());
                }
            }
            @Override
            public void onError(Throwable e) {
                Logs.error(TAG+"_RXJAVA", "You should go check the sensor,  dude");
            }
            @Override
            public void onNext(String string) {
                Logs.error(TAG+"_WHERIM","INPROGRESSSHOW");
            }
        };
    }
    /**
     *  timeout for subcriber
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *
     */
    private void setTimeOut() {
        signupobserable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(signupobserver);
    }
    @OnClick(R.id.btn_login)
    public void btnLogin() {
        SignUp.this.finish();
        App.setMain_activity_subcriber(null);
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
    /**
     *  return true if match password with perivious password
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param editText edit text of  passord
     * @param re_pass string of repassword
     */
    private boolean ismatch(String re_pass,EditText editText) {
        return re_pass.equals(editText.getText().toString());
    }
    public void onBackPressed() {
        this.finish();
    }
    /**
     *  emit check clouzer user call
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *
     */
    private void checkClouzerUser() {
        // change here
        setCheckUserSubcriber();
        System.out.println("*********************************logincheck");
        App.setLoginSocket(new LoginSocketConnectionTest().connect("https://delos.clouzer.com/login", App.getLoginSocket(), SignUp.this));
    }
    /**
     * display dialog
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
        txt_progress=progress.findViewById(R.id.txt_progress);
        txt_progress_placeholder=progress.findViewById(R.id.txt_progress_placeholder);
        txt_progress=progress.findViewById(R.id.txt_progress);
        txt_progress_placeholder.setText(this.getString(R.string.hub_scanning));
        startAnimationProgress(0);
        progress.show();
    }
    /**
     *  start animation for percentage
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *
     */
    public  void startAnimationProgress(int progress_int){
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
     *  render UI or perform action on hub response
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *
     */
    public void showDialogForCloseApp(String str_text)
    {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(R.layout.custom_dialog_single)
                .create();
        View v = dialog.getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.onBackPressed();
        dialog.setCancelable(false);
        dialog.show();
        TextView msgt = dialog.findViewById(R.id.title);
        msgt.setVisibility(View.GONE);
        TextView msg = dialog.findViewById(R.id.msg);
        msg.setText(""+str_text);
        Button diagButtonOK = dialog.findViewById(R.id.customDialogOk);
        diagButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                SignUp.this.finish();
            }
        });
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
        String name = "Filters Fragment";
        String username=new PrefManager(this).getValue("User_Id");
        // [START screen_view_hit]
        Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName(""+username+"~" + name);
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