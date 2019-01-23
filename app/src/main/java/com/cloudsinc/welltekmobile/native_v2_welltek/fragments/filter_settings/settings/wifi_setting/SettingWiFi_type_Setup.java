package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.wifi_setting;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.LoginActivity;
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

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * This class containing functionality related to displaying displaying categories
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class SettingWiFi_type_Setup extends Fragment {
    public static SettingWiFi_type_Setup newInstance() {
        return new SettingWiFi_type_Setup();
    }
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    View view;

    @BindView(R.id.edt_ip_address)EditText edt_ip_address;
    @BindView(R.id.edt_sub_mask)EditText edt_sub_mask;
    @BindView(R.id.edt_gate_way)EditText edt_gate_way;
    @BindView(R.id.edt_dns)EditText edt_dns;
    @BindView(R.id.spn_ip_type)Spinner spn_ip_type;
    @BindView(R.id.lin_prog)LinearLayout lin_prog;
    @BindView(R.id.lin_main)LinearLayout lin_main;
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @BindView(R.id.txt_save)TextView txt_save;

    Context mcontext;
    String str_ip_type="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting_wifi_type_setup, container, false);
        mcontext=view.getContext();
        ButterKnife.bind(this,view);
        txt_fragment_title.setText("Network Settings");
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();

        FontUtility.setAppFont(mContainer, mFont);

        ArrayList<Ssid> arr_ssid=new ArrayList<>();
        Ssid s=new Ssid();
        s.setTitle("DHCP");
        Ssid s1=new Ssid();
        s1.setTitle("Static");
        arr_ssid.add(s1);
        arr_ssid.add(s);

        CustomSSIDAdapter adapter = new CustomSSIDAdapter(mcontext,arr_ssid);
        spn_ip_type.setAdapter(adapter);
        setSubcriber();
        if (App.getSocket()!=null) {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_network_info", new JSONObject()));
        }




        txt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected(mcontext)) {
                    if(App.getSocket()!=null) {
                        setWifisetup();
                    }
                }
                else
                {
                    CustomDialogShow.dispDialogNoInternet(mcontext,LoginActivity.class);
                }
            }
        });
        Bundle bundle=App.getTemp_bundle();
        if(bundle!=null)
        {
            if(bundle.containsKey("ip_mode"))
            {
                String ip_mode=bundle.getString("ip_mode");

                Log.e("ip_mode",ip_mode);
                if(ip_mode.equals("static")) {
                    spn_ip_type.setSelection(0);
                }
                else
                {
                    spn_ip_type.setSelection(1);
                }
            }

        }
        else
        {
            spn_ip_type.setSelection(1);
        }




        /*TextView txt_back = (TextView) getActivity().findViewById(R.id.txt_cancle);
        txt_back.setText("Back");

        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReplaceFragment.replaceFragmentWithRotation(SettingWiFi_type_Setup.this, SettingMainFragment.newInstance());
            }
        });

        if(!App.isOrientationFlag()) {
            txt_save.setVisibility(View.VISIBLE);
            txt_back.setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.btn_setting).setVisibility(View.GONE);
            getActivity().findViewById(R.id.btn_back_home_health).setVisibility(View.GONE);
        }*/
       /* getActivity().findViewById(R.id.btn_setting).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.btn_back_home_health).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.txt_save).setVisibility(View.GONE);
        getActivity().findViewById(R.id.txt_cancle).setVisibility(View.GONE);*/
        spn_ip_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView txt_title= view.findViewById(R.id.txt_title);
                str_ip_type=txt_title.getText().toString();

                if (str_ip_type.equals("DHCP"))
                {
                    setEditable(false);
                    txt_save.setVisibility(View.GONE);
                    //clearFields();
                }
                else
                {
                    txt_save.setVisibility(View.VISIBLE);

                    setEditable(true);
                    setData();
                    if (App.getSocket()!=null) {
                        App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_network_info", new JSONObject()));
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    @OnClick(R.id.img_back)
    public void img_back()
    {
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingWiFi_type_Setup.this, R.id.frm_filter_main_container, SettingWiFi.newInstance());
        else ReplaceFragment.replaceFragment(SettingWiFi_type_Setup.this, R.id.frm_main_container, SettingWiFi.newInstance());
    }


    private void setSubcriber() {


        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext(""+App.getRoomData());
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
                setData();

            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);


    }

    public void setWifisetup()
    {
        lin_prog.setVisibility(View.VISIBLE);
        lin_main.setVisibility(View.INVISIBLE);

        try {
            JSONObject dataObj = new JSONObject();

            dataObj.put("dns",edt_dns.getText().toString() );
            dataObj.put("subnet", edt_sub_mask.getText().toString());
            dataObj.put("ip", edt_ip_address.getText().toString());
            dataObj.put("gateway", edt_gate_way.getText().toString());

            JSONObject obj = new JSONObject();
            obj.put("data", dataObj);
            obj.put("type", "set_static_ip");
            Log.d("SimulationReqUpdated", "" + obj);
            App.getSocket().emit("action", obj);
        }
        catch (Exception ex){}
        PrefManager spm=new PrefManager(mcontext);
        spm.setValue("ip_address", edt_ip_address.getText().toString());
       App.setSetStaticIp(true);

    }
    public void setData()
    {
        try {
            JSONObject jsonObject = App.getNetworkInfoJson().getJSONArray("data").getJSONObject(0);

            edt_dns.setText(jsonObject.getString("CML_DNS"));
            edt_gate_way.setText(jsonObject.getString("CML_GATEWAY"));
            edt_sub_mask.setText(jsonObject.getString("CML_SUBNET_MASK"));
            edt_ip_address.setText(jsonObject.getString("CML_IP"));
        }
        catch (Exception ex){}
    }

    public void setEditable(boolean b)
    {
        edt_dns.setEnabled(b);
        edt_sub_mask.setEnabled(b);
        edt_gate_way.setEnabled(b);
        edt_ip_address.setEnabled(b);


    }

    public void clearFields()
    {
        edt_dns.setText("");
        edt_sub_mask.setText("");
        edt_gate_way.setText("");
        edt_ip_address.setText("");
    }
    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
}