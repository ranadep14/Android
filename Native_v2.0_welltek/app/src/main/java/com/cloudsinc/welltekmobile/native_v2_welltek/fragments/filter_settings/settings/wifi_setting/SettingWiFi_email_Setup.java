package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.wifi_setting;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.CustomDialogShow;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.SocketConnectionTest.LoginSocketConnectionTest;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.SettingWiFi;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.LoginActivity.isEmailValid;
import static com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.CustomDialogShow.stopOkDialog;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.ProfileSettingFragment.hideKeyboard;

/**
 * This class containing functionality related to displaying and set email id to system for system sent mail on this.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class SettingWiFi_email_Setup extends Fragment  {
    public static SettingWiFi_email_Setup newInstance() {
        return new SettingWiFi_email_Setup();
    }

    View view;
    Context mcontext;

    @BindView(R.id.txt_fragment_title)
    TextView txt_fragment_title;



    @BindView(R.id.edt_email)EditText edt_email;
    @BindView(R.id.txt_save)
    TextView txt_save;
    @BindView(R.id.validenmail)TextView validenmail;
    boolean selectEmail=false;
    String emailPattern;

    Observable<String> mobservable;
    Observer<String> myObserver;
    TextView txt_cancle;
    boolean registerFlag=false;
    LoginSocketConnectionTest lst;
    boolean btn_click_flag=false;
    boolean edt_email_flag=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting_email_setup, container, false);

        ButterKnife.bind(SettingWiFi_email_Setup.this, view);

        mcontext = view.getContext();
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();
        lst=new LoginSocketConnectionTest();

        FontUtility.setAppFont(mContainer, mFont);

        txt_fragment_title.setText("Email Setup");

        if(App.getSocket()!=null)App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_email", new JSONObject()));


        setSubcriber();
        edt_email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                edt_email_flag=true;
                return false;
            }
        });

        edt_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(edt_email,getActivity());
                }
            }
        });
        edt_email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {


            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {




                String str="";

                if(s.length()==0)
                {
                    selectEmail=false;
                    str=getResources().getString(R.string.empty_email_id);

                }
                else if(!isEmailValid(s.toString()))
                {
                    str=getResources().getString(R.string.valid_email_id);
                    selectEmail=false;

                }
                else
                {
                    str="";
                    selectEmail=true;
                }

                validenmail.setText(str);
                setSaveButton();
            }
        });


        return view;
    }


    private void setSubcriber() {


        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext(""+ App.getRoomData());
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



                Log.e("Wireless","I m in wireless");

                if(string.equals("email_set")&&btn_click_flag)
                {
                    btn_click_flag=false;
                    CustomDialogShow.stopProgressDialog();
                    stopOkDialog();
                    CustomDialogShow.dispDialogWithOK(mcontext, "Email set successfully");
                }

                if(string.equals("email"))
                {
                    try {
                        edt_email.setText(App.getEmailJson().getJSONArray("data").getJSONObject(0).getString("email"));
                    }
                    catch (Exception ex)
                    {
                        Log.e("EmailError",""+ex.getMessage());
                    }
                }



            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);


    }



    @OnClick(R.id.img_back)
    public void img_back()
    {
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingWiFi_email_Setup.this,R.id.frm_filter_main_container, SettingWiFi.newInstance());
        else ReplaceFragment.replaceFragment(SettingWiFi_email_Setup.this, R.id.frm_main_container, SettingWiFi.newInstance());
    }

    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }


    public void setSaveButton() {

        if(edt_email_flag) {
            if (selectEmail) {
                txt_save.setVisibility(View.VISIBLE);
            } else {
                txt_save.setVisibility(View.GONE);


            }
        }
    }


    @OnClick(R.id.txt_save)
    public void txt_save_email()
    {


        JSONObject jsonObject = new JSONObject();


        CustomDialogShow.startProgressDialog(mcontext);
        if (App.getSocket() != null) {

            try {

                JSONObject obj = new JSONObject();
                obj.put("data", edt_email.getText().toString());
                obj.put("type", "set_email");
                Log.d("SimulationReqUpdated", "" + obj);
                App.getSocket().emit("action", obj);
                registerFlag=true;
            } catch (Exception ex) {
                Log.e("Error", "" + ex.getMessage());
            }

        }
        btn_click_flag=true;
    }




}