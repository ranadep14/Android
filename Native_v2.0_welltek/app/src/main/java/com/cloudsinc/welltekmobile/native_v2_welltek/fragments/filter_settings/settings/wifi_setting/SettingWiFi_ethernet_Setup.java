package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.wifi_setting;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
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
 * This class containing functionality related to displaying and set ethernet details of hub.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
@SuppressWarnings("ConstantConditions")
public class SettingWiFi_ethernet_Setup extends Fragment implements AdapterView.OnItemSelectedListener {
    public static SettingWiFi_ethernet_Setup newInstance() {
        return new SettingWiFi_ethernet_Setup();
    }

    View view;



    @BindView(R.id.lin_prog)LinearLayout lin_prog;
    @BindView(R.id.txt_save)TextView txt_save;
    @BindView(R.id.edt_ip_address)EditText edt_ip_address;
    @BindView(R.id.spn_ip_type)Spinner spn_ip_type;
    @BindView(R.id.edt_gate_way)EditText edt_gate_way;
    @BindView(R.id.edt_subnet)EditText edt_subnet;
    @BindView(R.id.edt_dns)EditText edt_dns;
    @BindView(R.id.lin_main)LinearLayout lin_main;
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @BindView(R.id.lin_mode_visiblity)LinearLayout lin_mode_visiblity;


    boolean selectEmail=false,selectPass=false,pass_flag=false;

    Observable<String> mobservable;
    Observer<String> myObserver;
    ArrayList<Ssid> ssidList=new ArrayList<>();
    String emailPattern;
    Context mcontext;
    String strmac="",ssid_namr="";
    boolean registerFlag=false;
    boolean ssidFlag=false,touch_flag=false;
    String str_ip_type="",str_ip_address="",str_gateway="",str_dns="",str_subnet="";
    Boolean selectStatic =false, selectDynamic =false,ip_address_flag=false,gateway_flag=false,dns_flag=false,subnet_flag=false;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting_wifi_ethernet_setup, container, false);
        ButterKnife.bind(this,view);
        mcontext=view.getContext();
        txt_save.setVisibility(View.GONE);
        String user_name=new PrefManager(mcontext).getValue("User_Id");
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();

        ButterKnife.bind(getActivity());
        FontUtility.setAppFont(mContainer, mFont);
        txt_fragment_title.setText("Ethernet Network Setup");

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

        if (App.getSocket()!=null) {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_network_info", new JSONObject()));
        }

        CustomSSIDAdapter adapter = new CustomSSIDAdapter(mcontext,arr_ssid);
        spn_ip_type.setAdapter(adapter);
        spn_ip_type.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touch_flag=true;
                return false;
            }
        });



        edt_dns.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }

            }
        });

        edt_gate_way.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    hideKeyboard(v);
                }

            }
        });

        edt_ip_address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    hideKeyboard(v);
                }

            }
        });
        edt_subnet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    hideKeyboard(v);
                }

            }
        });



        getWirelessConfig();

        setSubcriber();
        return view;
    }

    @OnClick(R.id.img_back)
    public void img_back()
    {
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingWiFi_ethernet_Setup.this,R.id.frm_filter_main_container, SettingWiFi.newInstance());
        else ReplaceFragment.replaceFragment(SettingWiFi_ethernet_Setup.this, R.id.frm_main_container, SettingWiFi.newInstance());

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

                        lin_main.setBackgroundColor(Color.BLACK);

                        CustomDialogShow.dispDialogWithOK(mcontext, "WI-FI register successful");
                    } else {
                        lin_main.setVisibility(View.VISIBLE);
                        lin_prog.setVisibility(View.GONE);

                        CustomDialogShow.dispDialogWithOK(mcontext, "WI-FI not registered");
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

                                    subnet_flag = !str_subnet.equals(charSequence);
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




    public void getWirelessConfig()
    {


        try {
            JSONObject jsonObject = App.getNetworkInfoJson();

            JSONArray jsonArray = jsonObject.getJSONArray("data");


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                if(jsonObject1.has("CML_ETHERNET_DATA"))
                {


                    JSONObject wifi_json=jsonObject1.getJSONObject("CML_ETHERNET_DATA");
                    if(wifi_json.has("CML_MODE")) {
                        if(!touch_flag) {
                            if (wifi_json.getString("CML_MODE").equals("DHCP")) {
                                spn_ip_type.setOnItemSelectedListener(null);
                                spn_ip_type.setSelection(2);
                                spn_ip_type.setOnItemSelectedListener(this);
                            } else {
                                spn_ip_type.setOnItemSelectedListener(null);
                                spn_ip_type.setSelection(1);
                                spn_ip_type.setOnItemSelectedListener(this);
                            }
                        }
                    }
                    edt_dns.setText(""+wifi_json.getString("CML_DNS"));
                    str_dns=wifi_json.getString("CML_DNS");

                    if(wifi_json.has("CML_GATEWAY_IP")) {
                        edt_gate_way.setText("" + wifi_json.getString("CML_GATEWAY_IP"));
                        str_gateway = wifi_json.getString("CML_GATEWAY_IP");
                    }
                    edt_ip_address.setText(""+wifi_json.getString("CML_IP"));
                    str_ip_address=wifi_json.getString("CML_IP");
                    edt_subnet.setText(""+wifi_json.getString("CML_SUBNET_MAX"));
                    str_subnet=wifi_json.getString("CML_SUBNET_MAX");
                }


            }




        }
        catch (Exception ex){
            Log.e("EthernetWifiError",""+ex.getMessage());
        }
    }



    @OnClick(R.id.txt_save)
    public void txt_save()
    {

        CustomDialogShow.startProgressDialog(mcontext);
        if (selectStatic) {

            try {
                JSONObject dataObj = new JSONObject();

                dataObj.put("CML_DNS", edt_dns.getText().toString());
                dataObj.put("CML_SUBNET", edt_subnet.getText().toString());
                dataObj.put("CML_IP", edt_ip_address.getText().toString());
                dataObj.put("CML_GATEWAY", edt_gate_way.getText().toString());
                dataObj.put("CML_MODE", "Static");
                dataObj.put("CML_TYPE", "Wired");

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

                dataObj.put("CML_DNS", edt_dns.getText().toString());
                dataObj.put("CML_SUBNET", edt_subnet.getText().toString());
                dataObj.put("CML_IP", edt_ip_address.getText().toString());
                dataObj.put("CML_GATEWAY", edt_gate_way.getText().toString());
                dataObj.put("CML_MODE", "DHCP");
                dataObj.put("CML_TYPE", "Wired");

                JSONObject obj = new JSONObject();
                obj.put("data", dataObj);
                obj.put("type", "change_network");
                Log.d("Wireleststaticrequest", "" + obj);
                App.getSocket().emit("action", obj);
            } catch (Exception ex) {
            }
        }



    }


    public void setEditable(boolean b)
    {
        edt_dns.setEnabled(b);
        edt_subnet.setEnabled(b);
        edt_gate_way.setEnabled(b);
        edt_ip_address.setEnabled(b);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            TextView txt_title= view.findViewById(R.id.txt_title);
            str_ip_type=txt_title.getText().toString();

           selectStatic=false;selectDynamic=false;
            if (str_ip_type.equals("DHCP"))
            {
                selectDynamic=true;
                setEditable(false);
                lin_mode_visiblity.setVisibility(View.GONE);
                checkSaveButton();

                //clearFields();
            }
            else
            {
                selectStatic=true;
                lin_mode_visiblity.setVisibility(View.VISIBLE);
                setEditable(true);
                checkSaveButton();

            }



    }


    public void checkSaveButton()
    {
        txt_save.setVisibility(View.GONE);
        if(selectDynamic)
        {

            txt_save.setVisibility(View.VISIBLE);
        }
        if(selectStatic)
        {
            if(dns_flag||gateway_flag||ip_address_flag||subnet_flag)
            {
               txt_save.setVisibility(View.VISIBLE);
            }
            else
            {
                txt_save.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void hideKeyboard(View view) {
        hideSystemUI(getActivity());
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
