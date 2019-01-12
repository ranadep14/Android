package com.cloudsinc.welltekmobile.native_v2_welltek.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.ConnectionLostActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.LoginActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.CustomDialogShow;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.SocketConnectionTest.LoginSocketConnectionTest;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.LoginActivity.isConnected;
import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.LoginActivity.validateUser;
import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity.showDialog;
import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity.startAnimationProgress;
/**
 * Created by developers on 5/12/18.
 */
public class LoginOperations {

    private boolean cancel ;
    private View focusView;
    private boolean flag_passward=false;
    private boolean flag_email=false;
    private PrefManager spm;
    Dialog progress;
    Activity mactivity;String str_email;String str_password;
    TextView txt_progress_placeholder,txt_progress;
    private String str_retry_dialog_msg="";
    Handler handler,handler_retry;
    Runnable runnable,runnable_retry;
    LoginSocketConnectionTest lct=new LoginSocketConnectionTest();
    public LoginOperations(Activity mactivity){
        this.mactivity=mactivity;
        spm = new PrefManager(mactivity);
    }
    public LoginOperations(Activity mactivity,String str_email,String str_password)
    {
        this.mactivity=mactivity;
        this.str_email=str_email;
        this.str_password=str_password;
        spm = new PrefManager(mactivity);
    }

    public void connectToSocket()
    {
        App.setLoginSocket(lct.connect(spm.getValue("server_url")+"/login", App.getLoginSocket(), mactivity));

    }
    public void attemptLogin(boolean flag_passward) {

        cancel = false;
        focusView = null;
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(str_email)&&TextUtils.isEmpty(str_password) ) {
            showMessage(mactivity,"Please enter Username and Password");
        }
        else {
            if(!validateUser(str_email))
            {
                showMessage(mactivity,"Please enter valid Username ");
            }
            else if(!flag_passward)
            {
                showMessage(mactivity,"Please enter valid password");
            }
            else {
                if (isConnected(mactivity)) {
                    if (lct.isSocketConnect(App.getLoginSocket())) {
                        if(!checkTechSupportUser()) {
                            try {
                                JSONArray action_array = new JSONArray();
                                action_array.put(0, "MOBILE_LOGIN");
                                JSONObject obj = new JSONObject();
                                obj.put("ACTION_ARRAY", action_array);
                                obj.put("username", str_email.contains("@darwin.com") ? str_email : str_email + "" + mactivity.getResources().getString(R.string.domain));
                                obj.put("password", PassowrdEncryptDecrypt.encrypt(str_password));
                                Logs.info("LoginActivity", PassowrdEncryptDecrypt.encrypt(str_password) + "" + obj + "----------------" + obj);
                                App.getLoginSocket().emit("LOGIN", obj);
                                App.setLoginEmitJson(obj);
                            } catch (Exception ex) {
                                Log.e(this.getClass().getSimpleName(), "---" + ex.getMessage());
                            }

                        }
                        else
                        {
                            showMessage(mactivity,""+mactivity.getResources().getString(R.string.tech_user));
                        }
                    } else {
                        str_retry_dialog_msg=mactivity.getResources().getString(R.string.server_issue);
                        dispalyRetryDialog();
                    }
                } else {
                    str_retry_dialog_msg=mactivity.getResources().getString(R.string.internet_issue);
                    dispalyRetryDialog();
                }
            }
        }
    }
    public boolean checkTechSupportUser()
    {
        return (str_email.contains("tech.support"));
    }


    public void showMessage(Context mcontext, String str_msg)
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


    private void dispalyRetryDialog()
    {
        final Dialog d=new Dialog(mactivity);
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
                App.setLoginSocket(lct.connect(spm.getValue("server_url")+"/login", App.getLoginSocket(), mactivity));
                CustomDialogShow.circulerProgressDialog(mactivity,"");
                runnable_retry = new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("sssssssssssssssssssssssss"+App.getLoginSocket());
                        if (isConnected(mactivity)) {
                            if (lct.isSocketConnect(App.getLoginSocket())) {
                                if(d.isShowing())d.dismiss();
                                CustomDialogShow.stopProgressDialog();
                            }
                            else
                            {
                                if(d.isShowing())d.dismiss();
                                CustomDialogShow.stopProgressDialog();
                                str_retry_dialog_msg=mactivity.getResources().getString(R.string.server_issue);
                                dispalyRetryDialog();
                            }
                        }
                        else
                        {
                            if(d.isShowing())d.dismiss();
                            CustomDialogShow.stopProgressDialog();
                            str_retry_dialog_msg=mactivity.getResources().getString(R.string.internet_issue);
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
}
