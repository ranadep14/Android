package com.cloudsinc.welltekmobile.native_v2_welltek.activities;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.ChangePassword;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.PassowrdEncryptDecrypt;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ScreenOrientation;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.listeners.SocketListeners.cancleNotification;
/**
 * These is for changeing user password
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edt_new_password,edt_re_pass,edt_user_id;
    private Button btnsubmit,btn_login;
    private LoginSocketConnectionTest lct=new LoginSocketConnectionTest();
    @BindView(R.id.valid_re_pass)TextView valid_re_pass;
    @BindView(R.id.valid_password)TextView valid_password;
    private App app;
    private View lyt;
    private Observable<String> changePasswordObservable;
    private Observer<String> changePasswordObserver;
    private String str_new_password;
    private String str_security_password;
    private String str_username;
    private String TAG=ChangePasswordActivity.this.getClass().getName();
    boolean flag_repwd=false,flag_user_name=false,flag_password=false;
    private Dialog d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ScreenOrientation.setOrientation(this);
        ButterKnife.bind(this);
        app=(App)getApplicationContext();
        lyt= findViewById(R.id.lytmainfont);
        App.setFlag_connection_lost(false);
        PrefManager spm = new PrefManager(ChangePasswordActivity.this);
        UserInteractionSleep.siboSleepChecking(this);
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
        edt_new_password= findViewById(R.id.edt_new_password);
        edt_user_id= findViewById(R.id.edt_user_id);
        edt_re_pass= findViewById(R.id.edt_re_pass);
        btnsubmit= findViewById(R.id.btn_submit);
        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();
            str_security_password = uri.getQueryParameter("pass");
            str_username=uri.getQueryParameter("id");
            edt_user_id.setText(""+str_username);
            if(str_username.length()>0)flag_user_name=true;
            Logs.error(TAG+"_ChangePasswordActivity","--------------------"+str_security_password+"-------"+str_username);
        }
        final Typeface mFont = Typeface.createFromAsset(getAssets(),
                "fonts/SF-UI-Text-Regular.otf");
        final ViewGroup mContainer = (ViewGroup) lyt.getRootView();
        FontUtility.setAppFont(mContainer, mFont);
        App.setLoginData(null);
        App.setLoginSocket(lct.connect("https://delos.clouzer.com/login", App.getLoginSocket(), ChangePasswordActivity.this));
        btnsubmit.setOnClickListener(this);
        btn_login= findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        edt_re_pass.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0&&ismatch(s.toString()))
                {
                    flag_repwd=true;
                    valid_re_pass.setVisibility(View.GONE);
                }
                else
                {
                    valid_re_pass.setText(getString(R.string.valid_match_pass));
                    valid_re_pass.setVisibility(View.VISIBLE);
                    flag_repwd=false;
                }
                // other stuffs
            }
        });
        edt_new_password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Logs.error(TAG+"_StringCount",""+s.length());
                String str="";
                if(s.length()==0)
                {
                    flag_password=false;
                    str=getResources().getString(R.string.empty_password);
                }
                else if(s.length()<6)
                {
                    str=getResources().getString(R.string.password_short);
                    flag_password=false;
                }
                else if(s.length()>13)
                {
                    str=getResources().getString(R.string.password_long);
                    flag_password=false;
                }
                else
                {
                    str="";
                    flag_password=true;
                }
                if(!str.equals("")) valid_password.setVisibility(View.VISIBLE);
                else valid_password.setVisibility(View.GONE);
                valid_password.setText(str);
                // other stuffs
            }
        });
        setChangePasswordSubcriber();
    }
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        UserInteractionSleep.userInteract(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btn_submit:
                String str_re_pass=edt_re_pass.getText().toString();
                 str_new_password=edt_new_password.getText().toString();
                if(lct.isSocketConnect(App.getLoginSocket()))
                {
                    if(flag_password&&flag_repwd&&flag_user_name) {
                        try {
                            CustomDialogShow.startProgressDialog(ChangePasswordActivity.this);
                            JSONArray action_array = new JSONArray();
                            action_array.put(0, "CHANGE_PASSWORD");
                            JSONArray baton_array=new JSONArray();
                            baton_array.put(0,new JSONObject().put("Location","login_initalize").put("Timestamp",System.currentTimeMillis()));
                            JSONObject obj = new JSONObject();
                            obj.put("ACTION_ARRAY", action_array);
                            obj.put("username", "" + str_username);
                            obj.put("password", "" + PassowrdEncryptDecrypt.encrypt(str_new_password));
                            obj.put("SecurityPassword", "" + str_security_password);
                            obj.put("REDIRECT_URL", "https://welltek.clouzer.com");
                            obj.put("baton",baton_array);
                            Logs.error(TAG+"_Login", "" + obj);
                            App.getLoginSocket().emit("LOGIN", obj);
                        } catch (Exception ex) {
                            Logs.error(TAG+"_ChagngePasswordError", ex.getMessage());
                        }
                    }
                    else
                    {
                        final AlertDialog dialog = new AlertDialog.Builder(this)
                                .setView(R.layout.custom_dialog_single)
                                .create();
                        View vi = dialog.getWindow().getDecorView();
                        vi.setBackgroundResource(android.R.color.transparent);
                        dialog.show();
                        TextView msgt= dialog.findViewById(R.id.title);
                        msgt.setText("Alert");
                        TextView msg= dialog.findViewById(R.id.msg);
                        msg.setText("Confirm password and new password must be same");
                        Button diagButtonOK = dialog.findViewById(R.id.customDialogOk);
                        diagButtonOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                    }
                }
                else {
                    CustomDialogShow.dispDialog(this,ChangePassword.class,""+getResources().getString(R.string.unable_to_connect));
                }
                break;
            case  R.id.btn_login:
                Intent i=new Intent(ChangePasswordActivity.this,LoginActivity.class);
                startActivity(i);
                break;
        }
    }
    /**
     * For handling response
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *
     */
    private void setChangePasswordSubcriber() {
        changePasswordObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("");
                        sub.onCompleted();
                    }
                }
        );
        changePasswordObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(String string) {
                try {
                     JSONObject loginjson=App.getLoginData();
                    final JSONArray userJarr = loginjson.getJSONArray("ACTION_ARRAY");
                    if("ERROR".equals(userJarr.getString(0))) {
                        CustomDialogShow.stopProgressDialog();
                        CustomDialogShow.dispDialogWithOK(ChangePasswordActivity.this,""+loginjson.getString("MSG"));
                    }
                     if ("CHANGE_PASSWORD".equals(userJarr.getString(0)))
                    {
                        CustomDialogShow.stopProgressDialog();
                        App.setLoginData(null);
                        showDialog("Your password changed successfully");
                    }
                } catch (JSONException e) {
                    Logs.error(TAG,"OnNext Error...."+e.getMessage());
                }
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(changePasswordObservable);
        s.setObserver(changePasswordObserver);
        App.setCurrentSubcriber(s);
    }
    /**
     * returns true is password match
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param re_pass string is for match old password
     */
    private boolean ismatch(String re_pass)
    {
        return re_pass.equals(edt_new_password.getText().toString());
    }
    @Override
    public void onBackPressed() {
        this.finish();
    }
    private void showDialog(String str)
    {
        d=new Dialog(ChangePasswordActivity.this);
        if(d.isShowing())d.dismiss();
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.general_dialog);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setCanceledOnTouchOutside(false);
        TextView txtmsgstr= d.findViewById(R.id.txt_msg);
        txtmsgstr.setText(""+str);
        Button btnretry= d.findViewById(R.id.btn_retry);
        btnretry.setVisibility(View.GONE);
        Button btncancle= d.findViewById(R.id.btncancle);
        btncancle.setText("OK");
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordActivity.this.finish();
                Intent i = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        d.show();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            MainActivity.hideSystemUI(this);
        }
        super.onWindowFocusChanged(hasFocus);
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