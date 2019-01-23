package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.wifi_setting;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.CustomSSIDAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.CustomDialogShow;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.SettingWiFi;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Ssid;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity.hideSystemUI;


/**
 * This class containing functionality related to displaying and set wireless details
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
@SuppressWarnings("ConstantConditions")
public class SettingWiFi_wireless_Setup extends Fragment implements AdapterView.OnItemSelectedListener {
    public static SettingWiFi_wireless_Setup newInstance() {
        return new SettingWiFi_wireless_Setup();
    }

    View view;



    @BindView(R.id.edt_passwrd)EditText edt_passwrd;
    @BindView(R.id.spn_ssid)Spinner spn_ssid;
    @BindView(R.id.spn_ip_type)Spinner spn_ip_type;
    @BindView(R.id.validepass)TextView validepass;
    @BindView(R.id.lin_ssid_password)LinearLayout lin_ssid_password;
    @BindView(R.id.lin_prog)LinearLayout lin_prog;
    @BindView(R.id.lin_ip_visiblity)LinearLayout lin_ip_visiblity;
    @BindView(R.id.lin_main)LinearLayout lin_main;
    @BindView(R.id.lin_wifi_config)LinearLayout lin_wifi_config;
    @BindView(R.id.edt_ip_address)EditText edt_ip_address;
    @BindView(R.id.edt_gate_way)EditText edt_gate_way;
    @BindView(R.id.edt_subnet)EditText edt_subnet;
    @BindView(R.id.edt_dns)EditText edt_dns;

    @BindView(R.id.img_refresh)ImageView img_refresh;
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;



    boolean ssid_flag=false,selectWifi=false,selectPass=false,pass_flag=false,edt_pass_flag=false;

    Observable<String> mobservable;
    Observer<String> myObserver;
    ArrayList<Ssid> ssidList=new ArrayList<>();
    ArrayList<String> ssid=new ArrayList<>();
    String emailPattern;
    Context mcontext;
    String strmac="",ssid_namr="";
    boolean registerFlag=false;
    boolean ssidFlag=false,touch_flag=false;
    @BindView(R.id.txt_save)TextView txt_save;
    String str_ip_type="",str_ip_address="",str_gateway="",str_dns="",str_subnet="";
    Boolean selectStatic =false, selectDynamic =false,ip_address_flag=false,gateway_flag=false,dns_flag=false,subnet_flag=false,refresh_flag=false;
    String TAG=this.getClass().getSimpleName();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting_wifi_wireless_setup, container, false);
        ButterKnife.bind(this,view);
        mcontext=view.getContext();
        txt_save.setVisibility(View.GONE);
        String user_name=new PrefManager(mcontext).getValue("User_Id");
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();

        setEditTextAlpha(0.5f);
        ButterKnife.bind(getActivity());
        FontUtility.setAppFont(mContainer, mFont);
        txt_fragment_title.setText("Wireless Network Setup");

        if(App.getSocket()!=null)App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_email", new JSONObject()));

        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_nearby_wifis", new JSONObject()));


        ArrayList<Ssid> arr_ssid=new ArrayList<>();
        Ssid s=new Ssid();
        s.setTitle("Select Connection");
        Ssid s0=new Ssid();
        s0.setTitle("Static");
        Ssid s1=new Ssid();
        s1.setTitle("DHCP");
        arr_ssid.add(s);
        arr_ssid.add(s0);
        arr_ssid.add(s1);
        lin_ip_visiblity.setVisibility(View.GONE);
        CustomSSIDAdapter adapter = new CustomSSIDAdapter(mcontext,arr_ssid);
        spn_ip_type.setAdapter(adapter);
        getSSIDName();
        getWirelessConfig();
        disableAllViews(lin_wifi_config,false);

        spn_ip_type.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touch_flag=true;
                return false;
            }
        });

        spn_ip_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView txt_title= view.findViewById(R.id.txt_title);
                str_ip_type=txt_title.getText().toString();

                selectDynamic=false;selectStatic=false;
                switch (str_ip_type) {
                    case "DHCP":
                        selectDynamic = true;
                        // setEditable(false);
                        lin_ip_visiblity.setVisibility(View.GONE);
                        checkSaveButton();
                        //clearFields();
                        break;
                    case "Select Connection":
                        lin_ip_visiblity.setVisibility(View.GONE);
                        break;
                    default:
                        selectStatic = true;
                        lin_ip_visiblity.setVisibility(View.VISIBLE);
                        break;
                }
                checkSaveButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edt_passwrd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                edt_pass_flag=true;
                return false;
            }
        });
        edt_passwrd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edt_pass_flag) {
                    String str = "";
                    if (s.length() == 0) {
                        selectPass = false;
                        str = getResources().getString(R.string.empty_password);

                    } else if (s.length() < 8) {
                        str = getResources().getString(R.string.password_short);
                        selectPass = false;

                    } else {
                        str = "";
                        selectPass = true;

                    }


                    validepass.setText(str);
                    checkSaveButton();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_passwrd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideSystemUI(getActivity());
                if (hasFocus) {

                    edt_passwrd.setHint("");
                }
                else
                {
                    hideKeyboard(v);
                }

            }
        });

        edt_dns.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideSystemUI(getActivity());
                if (!hasFocus) {
                    hideKeyboard(v);
                }

            }
        });

        edt_gate_way.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideSystemUI(getActivity());
                if (!hasFocus) {

                    hideKeyboard(v);
                }

            }
        });

        edt_ip_address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideSystemUI(getActivity());
                if (!hasFocus) {

                    hideKeyboard(v);
                }

            }
        });
        edt_subnet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideSystemUI(getActivity());
                if (!hasFocus) {

                    hideKeyboard(v);
                }

            }
        });

        setSubcriber();
        return view;
    }

    @OnClick(R.id.img_back)
    public void img_back()
    {
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingWiFi_wireless_Setup.this,R.id.frm_filter_main_container, SettingWiFi.newInstance());
        else ReplaceFragment.replaceFragment(SettingWiFi_wireless_Setup.this, R.id.frm_main_container, SettingWiFi.newInstance());

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



                Log.e("Wireless","I m in wireless"+string);





                if(string.equals("wifi_register")) {
                    CustomDialogShow.stopProgressDialog();
                    registerFlag=false;
                    if (App.isWifi_setup_save()) {
                        lin_main.setVisibility(View.VISIBLE);
                        lin_prog.setVisibility(View.GONE);
                        App.setWifi_setup_save(false);


                        CustomDialogShow.dispDialogWithOK(mcontext, "WI-FI register successful");
                    } else {
                        lin_main.setVisibility(View.VISIBLE);
                        lin_prog.setVisibility(View.GONE);

                        CustomDialogShow.dispDialogWithOK(mcontext, "WI-FI not registered");
                    }
                }

                if(string.equals("nearby_wifis")) {
                    try {
                        if (App.getSsidJson().getJSONArray("data").length() > 0) {
                            Log.e("WirelessSetUp", "sdvsdv");
                            img_refresh.clearAnimation();
                            getSSIDName();
                            getWirelessConfig();

                        }
                    } catch (Exception ex) {
                        Log.e("WirelessError", ex.getMessage());
                    }
                }
                if(string.equals("network_info")) {
                    try {
                        if (App.getNetworkInfoJson().getJSONArray("data").length() > 0) {
                            Log.e("WirelessSetUp", "sdvsdv");
                            getWirelessConfig();

                            edt_dns.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    dns_flag = !str_dns.equals(charSequence.toString());
                                    checkSaveButton();
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            });
                            edt_gate_way.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    gateway_flag = !str_gateway.equals(charSequence.toString());
                                    checkSaveButton();
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            });
                            edt_ip_address.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    ip_address_flag = !str_ip_address.equals(charSequence.toString());
                                    checkSaveButton();
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            });
                            edt_subnet.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    subnet_flag = !str_subnet.equals(charSequence.toString());
                                    checkSaveButton();
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            });


                        }
                    } catch (Exception ex) {
                        Log.e("WirelessError", ex.getMessage());
                    }
                }


            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);


    }

    @OnClick(R.id.img_refresh)
    public void img_refresh() {
        Animation animation = AnimationUtils.loadAnimation(mcontext, R.anim.rotate_animation);
        img_refresh.startAnimation(animation);
        refresh_flag=true;
        App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_nearby_wifis", new JSONObject()));

    }

    public void getSSIDName()
    {

        ssidList.clear();
        ssid.clear();
        Ssid s=new Ssid();
        s.setMac("");
        s.setTitle("Select SSID");
        ssidList.add(s);
        try {


            JSONObject jsonObject = App.getSsidJson();

            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                Ssid s2=new Ssid();
                s2.setMac(""+jsonObject1.getString("address"));
                s2.setTitle(""+jsonObject1.getString("ssid"));
                ssidList.add(s2);
                ssid.add(jsonObject1.getString("ssid"));
            }

        }
        catch (Exception ex){
            Logs.error(TAG,"nnnnnnnnnnnnnnnnnnnnnnnn"+ex.getMessage());
        }
        if (ssidList.size()==1)
        {
            ssidList.clear();
            Ssid s1=new Ssid();
            s1.setMac("No SSID is available");
            s1.setTitle("No SSID is available");
            ssidList.add(s1);
            ssidFlag=false;
        }
        else
        {
            ssidFlag=true;
        }

        Log.e("SSIDList",""+ssidList);
    }

    public void getWirelessConfig()
    {


        try {
            JSONObject jsonObject = App.getNetworkInfoJson();

            JSONArray jsonArray = jsonObject.getJSONArray("data");


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                if(jsonObject1.has("CML_WIFI_DATA"))
                {
                    JSONObject wifi_json=jsonObject1.getJSONObject("CML_WIFI_DATA");

                    if(wifi_json.has("CML_MODE")) {
                        if(!touch_flag) {
                            if (wifi_json.getString("CML_MODE").equals("DHCP"))
                                spn_ip_type.setSelection(2);
                            else spn_ip_type.setSelection(1);
                        }
                    }

                    str_dns=wifi_json.has("CML_DNS")?wifi_json.getString("CML_DNS"):"";
                    edt_dns.setText(""+str_dns);
                    if(wifi_json.has("CML_GATEWAY_IP")) {
                        edt_gate_way.setText("" + wifi_json.getString("CML_GATEWAY_IP"));
                        str_gateway= wifi_json.getString("CML_GATEWAY_IP");
                    }
                    str_ip_address=(wifi_json.has("CML_IP")?wifi_json.getString("CML_IP"):"");
                    edt_ip_address.setText(""+str_ip_address);
                    str_subnet=wifi_json.has("CML_SUBNET_MAX")?wifi_json.getString("CML_SUBNET_MAX"):"";
                    edt_subnet.setText(""+str_subnet);

                    if(wifi_json.has("CML_SSID")) {
                        if(!refresh_flag)App.setPosition(ssid.indexOf(""+wifi_json.getString("CML_SSID"))+1);
                    }
                }


            }



            CustomSSIDAdapter adapter = new CustomSSIDAdapter(mcontext,ssidList);
            spn_ssid.setAdapter(adapter);
            spn_ssid.setOnItemSelectedListener(this);
            if(App.getPosition()!=-1)
            {
                spn_ssid.setSelection(App.getPosition());
            }




        }
        catch (Exception ex){
            Logs.error(""+SettingWiFi_wireless_Setup.this.getClass().getName(),""+ex.getMessage());
        }
    }


    @OnClick(R.id.txt_save)
    public void txt_connect()
    {


        Log.e("WifiFlagState",""+ selectStatic +"----"+selectWifi+"-----"+selectDynamic);
       // lin_main.setVisibility(View.INVISIBLE);
        CustomDialogShow.startProgressDialog(mcontext);
        if (selectStatic) {

            try {
                JSONObject dataObj = new JSONObject();

                dataObj.put("CML_DNS", edt_dns.getText().toString());
                dataObj.put("CML_SUBNET", edt_subnet.getText().toString());
                dataObj.put("CML_IP", edt_ip_address.getText().toString());
                dataObj.put("CML_GATEWAY", edt_gate_way.getText().toString());
                dataObj.put("CML_MODE", "Static");
                dataObj.put("CML_PASSWORD", edt_passwrd.getText().toString());
                dataObj.put("CML_SSID", ssid_namr);
                dataObj.put("CML_TYPE", "wifi");

                JSONObject obj = new JSONObject();
                obj.put("data", dataObj);
                obj.put("type", "change_network");
                Log.d("Wireleststaticrequest", "" + obj);
                App.getSocket().emit("action", obj);
            } catch (Exception ex) {
            }
        }
        if(selectDynamic)
        {
            try {
                JSONObject dataObj = new JSONObject();

                dataObj.put("CML_TYPE", "wifi");
                dataObj.put("CML_MODE", "DHCP");
                dataObj.put("CML_PASSWORD", edt_passwrd.getText().toString());
                dataObj.put("CML_SSID", ssid_namr);
                JSONObject obj = new JSONObject();
                obj.put("data", dataObj);
                obj.put("type", "change_network");
                Log.d("Wirelestdynamicrequest", "" + obj);
               App.getSocket().emit("action", obj);
            } catch (Exception ex) {
            }
        }

    }
    @OnClick(R.id.txt_reconfig)
    public void txt_wifi_connect()
    {

        if(!selectWifi) {
            disableAllViews(lin_wifi_config, true);
            setEditTextAlpha(1.0f);
            selectWifi = true;
            edt_passwrd.setText("");
        }
        else
        {
            edt_passwrd.setText("temp_pass");
            disableAllViews(lin_wifi_config, false);
            setEditTextAlpha(0.5f);
            selectWifi = false;
        }
        checkSaveButton();

    }

    public void setEditTextAlpha(float alpha)
    {
        edt_ip_address.setAlpha(alpha);
        edt_subnet.setAlpha(alpha);
        edt_gate_way.setAlpha(alpha);
        edt_dns.setAlpha(alpha);
        edt_passwrd.setAlpha(alpha);
        spn_ssid.setAlpha(alpha);
        spn_ip_type.setAlpha(alpha);
        img_refresh.setAlpha(alpha);

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




    @OnClick(R.id.txt_show_pass)
    public void txt_show_pass()
    {
        if(pass_flag)
        {
            pass_flag=false;
            edt_passwrd.setTransformationMethod(new PasswordTransformationMethod());

        }
        else
        {
            pass_flag=true;
            edt_passwrd.setTransformationMethod(null);

        }
    }

    public void setEditable(boolean b)
    {
        edt_dns.setEnabled(b);
        edt_subnet.setEnabled(b);
        edt_gate_way.setEnabled(b);
        edt_ip_address.setEnabled(b);


    }

    public void checkSaveButton()
    {
        txt_save.setVisibility(View.GONE);

        if(selectWifi) {
            if (selectDynamic&&selectPass&&ssid_flag) {

                txt_save.setVisibility(View.VISIBLE);
            }
            if (selectStatic&&selectPass&&ssid_flag) {
                if (dns_flag || gateway_flag || ip_address_flag || subnet_flag) {
                    txt_save.setVisibility(View.VISIBLE);
                } else {
                    txt_save.setVisibility(View.GONE);
                }
            }
        }
    }

    private void hideKeyboard(View view) {
        System.out.println("gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        hideSystemUI(getActivity());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        App.setPosition(position);
        if (ssidList.size() > 0) {
            ssid_namr = ssidList.get(position).getTitle();
            strmac = ssidList.get(position).getMac();
            Log.e("mac_id", strmac + "" + ssid_namr);
        }
        ssid_flag = position > 0;
        checkSaveButton();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        App.setPosition(-1);
    }
}
