package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.CustomDialogShow;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.SocketConnectionTest.LoginSocketConnectionTest;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ScreenOrientation;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.LoginActivity.isEmailValid;
import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.LoginActivity.showMessage;
import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.LoginActivity.validateUser;
/**
 * This activiyt is for send request for change password
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private View lyt;
    private Tracker mTracker;
    private EditText edt_user_id, edt_recovery_email;
    private Button btnsubmit,bcklogin;
    private boolean recovery_flag=false,flag_email=false;
    private LoginSocketConnectionTest lct=new LoginSocketConnectionTest();
    private Observable<String> forgetpasswordobserable;
    private Observer<String> forgetpasswordobserver;
    @BindView(R.id.valid_recovery_email)TextView valid_recovery_email;
    @BindView(R.id.valid_user_name)TextView valid_user_name;
    private String TAG="ForgetPasswordActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        ScreenOrientation.setOrientation(this);
         Logs.error(TAG+"_WhereIM",TAG);
        if (!checkConfiguration()) {
            Logs.info(TAG,"Error in Forget activity");
        }
        App application = (App)getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();
        if(App.getSocket()!=null) {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_provisioned", new JSONObject()));
        }
        UserInteractionSleep.siboSleepChecking(this);
        lyt= findViewById(R.id.forget_lay);
        final Typeface mFont = Typeface.createFromAsset(getAssets(),
                "fonts/SF-UI-Text-Regular.otf");
        final ViewGroup mContainer = (ViewGroup) lyt.getRootView();
        FontUtility.setAppFont(mContainer, mFont);
        App.setLoginData(null);
        App.setLoginSocket(lct.connect(new PrefManager(this).getValue("server_url")+"/login", App.getLoginSocket(), ForgetPasswordActivity.this));
        edt_user_id = findViewById(R.id.edt_user_id);
        edt_recovery_email = findViewById(R.id.edt_recovery_email);
        btnsubmit= findViewById(R.id.btn_submit);
        btnsubmit.setOnClickListener(this);
        bcklogin= findViewById(R.id.btn_login);
        bcklogin.setOnClickListener(this);
        edt_user_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edt_recovery_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edt_recovery_email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str="";
                if(s.length()==0)
                {
                    recovery_flag=false;
                    str=getResources().getString(R.string.empty_recovery_mail);
                }
                else if(!isEmailValid(s.toString()))
                {
                    str=getResources().getString(R.string.valid_recovery_id);
                    recovery_flag=false;
                }
                else
                {
                    str="";
                    recovery_flag=true;
                }
                valid_recovery_email.setText(str);
                setButtonEnable();
                // other stuffs
            }
        });
        edt_user_id.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str="";
                if(s.length()==0)
                {
                    flag_email=false;
                    str=getResources().getString(R.string.empty_user_id);
                }
                else if(!validateUser(s.toString()))
                {
                    str=getResources().getString(R.string.valid_user_id);
                    flag_email=false;
                }
                else
                {
                    str="";
                    flag_email=true;
                }
                valid_user_name.setText(str);
                setButtonEnable();
                // other stuffs
            }
        });
        setForgetPasswordSubscriber();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        UserInteractionSleep.userInteract(this);
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
    /**
     * enable or diable button on filling feilds.
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *
     */
    private void setButtonEnable()
    {
         Logs.error(TAG+"_ForgetPasswordFlags",recovery_flag+"----"+flag_email);
        if(recovery_flag&&flag_email)
        {
            btnsubmit.setTextColor(getResources().getColor(R.color.white));
            btnsubmit.setBackground(getResources().getDrawable(R.drawable.button_background_style));
        }
        else
        {
            btnsubmit.setTextColor(getResources().getColor(R.color.light_gray));
            btnsubmit.setBackground(getResources().getDrawable(R.drawable.btn_border_gray));
        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btn_submit:
                String str_email= edt_user_id.getText().toString();
                String str_recovery_email= edt_recovery_email.getText().toString();
                App.setUsername(""+str_email);
                if (TextUtils.isEmpty(str_recovery_email)&&TextUtils.isEmpty(str_email) ) {
                    showMessage(this,"Please enter Username and Recovery email ID");
                }
                else {
                    if (lct.isSocketConnect(App.getLoginSocket())) {
                        try {
                            CustomDialogShow.startProgressDialog(ForgetPasswordActivity.this);
                            JSONArray action_array = new JSONArray();
                            action_array.put(0, "FORGOT_PASSWORD");
                            JSONArray baton_array = new JSONArray();
                            baton_array.put(0, new JSONObject().put("Location", "login_initalize").put("Timestamp", System.currentTimeMillis()));
                            JSONObject obj = new JSONObject();
                            obj.put("ACTION_ARRAY", action_array);
                            obj.put("RECOVERY_MAIL", "" + str_recovery_email);
                            obj.put("REDIRECT_URL", "https://welltek.clouzer.com");
                            obj.put("username", str_email.contains("@darwin.com") ? str_email : str_email + "" + getResources().getString(R.string.domain));
                            obj.put("baton", baton_array);
                             Logs.error(TAG+"_FORGOT_PASSWORD", "" + obj);
                            App.getLoginSocket().emit("LOGIN", obj);
                        } catch (Exception ex) {
                             Logs.error(TAG+"_ChangePassError", ex.getMessage());
                        }
                    } else {
                        CustomDialogShow.dispDialog(this, ForgetPasswordActivity.class, ""+getResources().getString(R.string.unable_to_connect));
                    }
                }
                break;
            case R.id.btn_login:
                Intent i=new Intent(ForgetPasswordActivity.this,LoginActivity.class);
                startActivity(i);
                ForgetPasswordActivity.this.finish();
                break;
        }
    }
    /**
     *  render UI or perform action on hub response
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *
     */
    private void setForgetPasswordSubscriber() {
        forgetpasswordobserable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("");
                        sub.onCompleted();
                    }
                }
        );
        forgetpasswordobserver = new Observer<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(String string) {
                 Logs.error(TAG+"_responsestring",string);
               CustomDialogShow.stopProgressDialog();
                try {
                    JSONObject loginjson=App.getLoginData();
                     Logs.error(TAG+"_ForgetPassword",""+loginjson);
                    if(loginjson!=null) {
                        final JSONArray userJarr = loginjson.getJSONArray("ACTION_ARRAY");
                        if ("ERROR".equals(userJarr.getString(0))) {
                            CustomDialogShow.dispDialogWithOK(ForgetPasswordActivity.this, "" + loginjson.getString("MSG"));
                        }
                        if ("FORGOT_PASSWORD".equals(userJarr.getString(0))) {
                            edt_recovery_email.setText("");
                            edt_user_id.setText("");
                            CustomDialogShow.dispDialogWithOK(ForgetPasswordActivity.this, "" + loginjson.getString("MSG"));
                        }
                        App.setLoginData(null);
                    }
                  /*  else
                    {
                        CustomDialogShow.dispDialogWithOK(ForgetPasswordActivity.this, "Your session has expired...retry");
                    }
*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(forgetpasswordobserable);
        s.setObserver(forgetpasswordobserver);
        App.setCurrentSubcriber(s);
    }
    /**
     * hide keybord popup on outside edittext touch
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *
     */
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    @Override
    public void onBackPressed() {
        this.finish();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            MainActivity.hideSystemUI(this);
        }
        super.onWindowFocusChanged(hasFocus);
    }


    private void sendScreenImageName() {
        String name = "Forget Activity";
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