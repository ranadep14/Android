package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.LoginActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.CustomDialogShow;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.SocketConnectionTest.LoginSocketConnectionTest;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.PassowrdEncryptDecrypt;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.ProfileSettingFragment.hideKeyboard;

/**
 * This class containing functionality related to change password from setting
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class ChangePassword extends Fragment {
    public static ChangePassword newInstance() {
        return new ChangePassword();
    }
    TextView txt_cancle;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    PrefManager prefManager;
    private String iClientInfo="";
    String TAG=ChangePassword.this.getClass().getSimpleName();
    private App app;
    PrefManager pref;

    LoginSocketConnectionTest lct=new LoginSocketConnectionTest();

    String sckid="";
    View view;
    Context mcontext;
    @BindView(R.id.edt_crm_pass)EditText edt_crm_pass;
    @BindView(R.id.validpassword)TextView vpwd;
    @BindView(R.id.validRepeatpwd)TextView repwd;
    @BindView(R.id.validoldpassword)TextView validoldpassword;
    @BindView(R.id.edt_old_pass)EditText edt_old_pass;
    @BindView(R.id.edt_new_pass)EditText edt_new_pass;
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @BindView(R.id.txt_save)TextView txt_save;

    Boolean newPass=false,oldPass=false,flag_password=false,flag_repwd=false;
    private String newPwd="", cnfPwd="",oldpwd="",uname="",jwt="";

    LoginSocketConnectionTest lst;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_change_password,container, false);
        mcontext=view.getContext();
        ButterKnife.bind(this,view);
        App.setChangePassJson(null);
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();
        txt_fragment_title.setText("Change Password");
        mcontext=view.getContext();
        pref=new PrefManager(mcontext);
        uname= new PrefManager(mcontext).getValue("User_Id");
        jwt= new PrefManager(mcontext).getValue("jwt_token");
        prefManager=new PrefManager(mcontext);


        // setAuthuntication();
        FontUtility.setAppFont(mContainer, mFont);

        txt_save.setVisibility(View.GONE);
        txt_save.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            CustomDialogShow.startProgressDialog(mcontext);
                                            App.setLoginSocket(null);
                                            if(isConnected(mcontext)) {
                                                if (App.getLoginSocket() == null) {


                                                    PrefManager spm=new PrefManager(mcontext);
                                                    Logs.info(TAG+"_WhereIm","ImInSynSocket"+App.getLoginSocket()+spm.getValue("server_url"));
                                                    App.setLoginSocket(lct.connect(spm.getValue("server_url") + "/loginauth", App.getLoginSocket(), mcontext));
                                                }
                                            }
                                        }
                                    }
        );

        edt_old_pass.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {


            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Logs.info(TAG+"_StringCount",""+s.length());
                String str="";
                if(s.length()==0)
                {
                    oldPass=false;
                    str=getResources().getString(R.string.empty_password);

                }
                else if(s.length()<6)
                {
                    str=getResources().getString(R.string.password_short);
                    oldPass=false;

                }
                else if(s.length()>13)
                {
                    str=getResources().getString(R.string.password_long);
                    oldPass=false;

                }
                else
                {
                    str="";
                    oldPass=true;
                }
                if(!str.equals("")) validoldpassword.setVisibility(View.VISIBLE);
                else validoldpassword.setVisibility(View.GONE);
                validoldpassword.setText(str);
                setSave();
                // other stuffs
            }
        });


        edt_crm_pass.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {


            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0&&ismatch(s.toString()))
                {
                    flag_repwd=true;
                    repwd.setVisibility(View.GONE);
                }
                else
                {
                    repwd.setText(getString(R.string.valid_match_pass));
                    repwd.setVisibility(View.VISIBLE);
                    flag_repwd=false;
                }
                setSave();
                // other stuffs
            }
        });
        edt_new_pass.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {


            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Logs.info(TAG+"_StringCount",""+s.length());
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
                if(!str.equals("")) vpwd.setVisibility(View.VISIBLE);
                else vpwd.setVisibility(View.GONE);
                vpwd.setText(str);
                setSave();
                // other stuffs
            }
        });

        edt_crm_pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(edt_crm_pass,getActivity());
                }
            }
        });
        edt_new_pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(edt_new_pass,getActivity());
                }
            }
        });
        edt_old_pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(edt_old_pass,getActivity());
                }
            }
        });



        setSubcriber();

        return view;
    }



    @SuppressLint("NewApi")
    private void Changepwd() {


        oldpwd = edt_old_pass.getText().toString();
        newPwd = edt_new_pass.getText().toString();
        cnfPwd = edt_crm_pass.getText().toString();


        if (App.isAuthanticate()) {
            if (!Objects.equals(newPwd, oldpwd) ) {


                if (Objects.equals(newPwd, cnfPwd)) {
                    try {



                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss 'GMT'Z yyyy");


                        JSONObject dataArryaJsonObj = new JSONObject();
                        dataArryaJsonObj.put("ACTION_ARRAY", new JSONArray().put("SETTING_CHANGE_PASSWORD"));
                        dataArryaJsonObj.put("oldpassword",PassowrdEncryptDecrypt.encrypt(oldpwd) );
                        dataArryaJsonObj.put("password",PassowrdEncryptDecrypt.encrypt(newPwd) );
                        dataArryaJsonObj.put("topic", "DELOS_LOGIN");
                        dataArryaJsonObj.put("username", uname);



                        Logs.info(TAG+"_ChangePAssword", "" + dataArryaJsonObj);
                        App.getLoginSocket().emit("LOGIN", dataArryaJsonObj);


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                } else {
                    CustomDialogShow.stopProgressDialog();
                    final AlertDialog dialog = new AlertDialog.Builder(mcontext)
                            .setView(R.layout.custom_dialog_single)
                            .create();
                    View vi = dialog.getWindow().getDecorView();
                    vi.setBackgroundResource(android.R.color.transparent);
                    dialog.show();
                    TextView msgt = dialog.findViewById(R.id.title);
                    msgt.setText("Alert!");

                    TextView msg = dialog.findViewById(R.id.msg);
                    msg.setText("Passwords does not match");

                    Button diagButtonOK = dialog.findViewById(R.id.customDialogOk);
                    diagButtonOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }


                    });
                }
            }
            else
            {
                CustomDialogShow.stopProgressDialog();
                CustomDialogShow.dispDialogWithOK(mcontext, "Old and new password should be difference.");

            }
        } else {

            CustomDialogShow.dispDialogWithOK(mcontext, ""+getResources().getString(R.string.server_issue));
        }
    }

    @OnClick(R.id.img_back)
    public void img_back()
    {
        if(App.isOrientationFlag())ReplaceFragment.replaceFragment(ChangePassword.this,R.id.frm_filter_main_container, ProfileSettingFragment.newInstance());
        else ReplaceFragment.replaceFragment(ChangePassword.this,R.id.frm_main_container, ProfileSettingFragment.newInstance());

    }

    public void setSave()
    {
        if(flag_repwd==true&&oldPass==true&&flag_password==true)
        {
            txt_save.setVisibility(View.VISIBLE);
        }
        else
        {
            txt_save.setVisibility(View.GONE);
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

                Logs.info(TAG+"_SubcriberResponse", "" + string + "");
                if(string.equals("delos_connected"))
                {
                    setAuthuntication();
                }
                if(string.equals("authenticated"))
                {
                    Changepwd();
                }
                if(string.equals("LoginResponce")) {

                    try
                    {
                        JSONObject jsonObject=App.getLoginData();
                        Logs.info(TAG+"_JsonObject",""+jsonObject);
                        if(jsonObject.has("CHANGE_PASSWORD_FLAG")) {
                            if (!jsonObject.getBoolean("CHANGE_PASSWORD_FLAG")) {
                                CustomDialogShow.stopProgressDialog();
                                CustomDialogShow.dispDialogWithOK(mcontext, "Enter valid password.");

                            } else {
                                CustomDialogShow.stopProgressDialog();
                                CustomDialogShow.dispDialogWithOK(mcontext, "Your password is changed.");

                                if (App.getSocket() != null) {
                                    App.getSocket().off();
                                    App.setSocket(null);
                                }

                                pref.setValue("jwt_token", "No Record");
                                pref.setValue("state_serial", "No Record");
                                pref.setValue("hub_serial", "No Record");
                                pref.setValue("Simulation_info", "No Record");
                                pref.setValue("connectin_to_hub", "No Record");
                                pref.setValue("ip_address", "No Record");
                                getActivity().finishAffinity();
                                Intent i = new Intent(mcontext, LoginActivity.class);
                                startActivity(i);
                            }
                        }
                    }

                    catch(Exception ex)
                    {
                        Logs.error(TAG+"_Error_ChangePasswordError",""+ex.getMessage());
                    }

                }
            }

        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);


    }

    public void setAuthuntication()
    {
        try
        {
            JSONObject authanticateJson=new JSONObject();
            authanticateJson.put("userId",prefManager.getValue("User_Id"));
            authanticateJson.put("token",prefManager.getValue("jwt_token"));
            Logs.info(TAG+"_AuthancticateReq0",""+authanticateJson);
            App.getLoginSocket().emit("authenticate", authanticateJson);
        }
        catch (Exception ex)
        {
            Logs.error(TAG+"_ChangePasswordCall",ex.getMessage());
        }
    }

    public boolean ismatch(String re_pass)
    {

        return re_pass.equals(edt_crm_pass.getText().toString());
    }
    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

}