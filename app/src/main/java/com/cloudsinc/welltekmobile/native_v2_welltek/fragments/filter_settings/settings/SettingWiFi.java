package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.SocketConnectionTest.LoginSocketConnectionTest;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.wifi_setting.SettingWiFi_email_Setup;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.wifi_setting.SettingWiFi_ethernet_Setup;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.wifi_setting.SettingWiFi_wireless_Setup;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * This class containing functionality related to display wifi details
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class SettingWiFi extends Fragment {
    public static SettingWiFi newInstance() {
        return new SettingWiFi();
    }

    View view;
    Context mcontext;
    @BindView(R.id.rel_cloud_status_red)
    RelativeLayout rel_cloud_status_red;
    @BindView(R.id.rel_cloud_status_green)
    RelativeLayout rel_cloud_status_green;

    @BindView(R.id.txt_fragment_title)
    TextView txt_fragment_title;

    @BindView(R.id.rel_int_status_red)
    RelativeLayout rel_int_status_red;
    @BindView(R.id.rel_int_status_green)
    RelativeLayout rel_int_status_green;
    @BindView(R.id.txt_wireless_setup)
    TextView txt_wireless_setup;
    @BindView(R.id.txt_ethernet_setup)
    TextView txt_ethernet_setup;
    @BindView(R.id.img_wireless_enter)
    ImageView img_wireless_enter;
    @BindView(R.id.img_ethernet_enter)
    ImageView img_ethernet_enter;

    @BindView(R.id.txt_save)
    TextView txt_save;



    String TAG=SettingWiFi.this.getClass().getName();
    @BindView(R.id.lin_wireless_setup)
    LinearLayout lin_wireless_setup;
    @BindView(R.id.lin_eth_setup)
    LinearLayout lin_eth_setup;
    Observable<String> mobservable;
    Observer<String> myObserver;
    TextView txt_cancle;
    boolean registerFlag=false;
    LoginSocketConnectionTest lst;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting_wi_fi, container, false);

        ButterKnife.bind(SettingWiFi.this, view);
        txt_save.setVisibility(View.GONE);
        mcontext = view.getContext();

        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();
        lst=new LoginSocketConnectionTest();

        FontUtility.setAppFont(mContainer, mFont);

        txt_fragment_title.setText("Wi-Fi & Network");


        if(App.getSocket()!=null) {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_email", new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_network_info", new JSONObject()));
        }





        getHubInfo();
        setSubcriber();




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



                 Logs.info(TAG+"_Wireless","I m in wireless");




                try {
                    if (App.getNetworkInfoJson().getJSONArray("data").getJSONObject(0).has("CML_WIFI_DATA")) {
                        lin_wireless_setup.setVisibility(View.VISIBLE);
                    }
                    if (App.getNetworkInfoJson().getJSONArray("data").getJSONObject(0).has("CML_ETHERNET_DATA")) {
                        lin_eth_setup.setVisibility(View.VISIBLE);
                    }
                }
                catch (Exception ex)
                {
                     Logs.error(TAG+"_linvisiblityError",""+ex.getMessage());
                }







            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);


    }



    @OnClick(R.id.lin_wireless_setup)
    public void lin_wireless_setup() {

       if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingWiFi.this, R.id.frm_filter_main_container,SettingWiFi_wireless_Setup.newInstance());
        else  ReplaceFragment.replaceFragment(SettingWiFi.this, R.id.frm_main_container,SettingWiFi_wireless_Setup.newInstance());

    }
    @OnClick(R.id.lin_email_setup)
    public void lin_email_setup() {

       if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingWiFi.this, R.id.frm_filter_main_container, SettingWiFi_email_Setup.newInstance());
        else  ReplaceFragment.replaceFragment(SettingWiFi.this, R.id.frm_main_container,SettingWiFi_email_Setup.newInstance());

    }

    @OnClick(R.id.lin_eth_setup)
    public void lin_eth_setup() {

       if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingWiFi.this, R.id.frm_filter_main_container, SettingWiFi_ethernet_Setup.newInstance());
        else  ReplaceFragment.replaceFragment(SettingWiFi.this, R.id.frm_main_container,SettingWiFi_ethernet_Setup.newInstance());

    }

    public void getHubInfo() {
        try {
            JSONObject jsonObject = App.getNetworkInfoJson().getJSONArray("data").getJSONObject(0);
            Bundle bundle=new Bundle();


            if(jsonObject.has("cloudsConnected")) {
                if (jsonObject.getBoolean("cloudsConnected")) {
                    rel_cloud_status_green.setVisibility(View.VISIBLE);
                    rel_cloud_status_red.setVisibility(View.GONE);

                } else {
                    rel_cloud_status_green.setVisibility(View.GONE);
                    rel_cloud_status_red.setVisibility(View.VISIBLE);
                }
            }
            if(jsonObject.has("netConnected")) {
                if (jsonObject.getBoolean("netConnected")) {
                    rel_int_status_green.setVisibility(View.VISIBLE);
                    rel_int_status_red.setVisibility(View.GONE);
                } else {
                    rel_int_status_green.setVisibility(View.GONE);
                    rel_int_status_red.setVisibility(View.VISIBLE);
                }

            }
            if(jsonObject.has("CML_ETHERNET_DATA")) {
                if(jsonObject.getJSONObject("CML_ETHERNET_DATA").keys().hasNext()) {
                    img_ethernet_enter.setVisibility(View.VISIBLE);
                    lin_eth_setup.setClickable(true);
                    txt_ethernet_setup.setText("Ethernet Setup: Connected");
                }
                else {
                    img_ethernet_enter.setVisibility(View.GONE);
                    lin_eth_setup.setClickable(false);
                    txt_ethernet_setup.setText("Ethernet Setup: Not connected");
                }
            }
            if(jsonObject.has("CML_WIFI_DATA")) {
                if(jsonObject.getJSONObject("CML_WIFI_DATA").keys().hasNext()) {
                    /*img_wireless_enter.setVisibility(View.VISIBLE);
                    lin_wireless_setup.setClickable(true);*/
                    txt_wireless_setup.setText("Wireless Setup: Connected");
                }
                else {
                   /* img_wireless_enter.setVisibility(View.GONE);
                    lin_wireless_setup.setClickable(false);*/
                    txt_wireless_setup.setText("Wireless Setup: Not connected");
                }
            }
            App.setTemp_bundle(bundle);

        }


        catch (Exception ex) {
             Logs.error(TAG+"_SimulationError", ""+ex.getMessage());
        }

    }

    @OnClick(R.id.btn_reset)
    public void onReset() {

        final AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                .setView(R.layout.custom_dialog)
                .create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView msgt= dialog.findViewById(R.id.title);
        assert msgt != null;
        msgt.setText("Confirmation Required");

        TextView msg= dialog.findViewById(R.id.msg);
        assert msg != null;
        msg.setText("Are you sure you want to reset default network settings?");
        Button diagButtonCancel = dialog.findViewById(R.id.customDialogCancel);
        assert diagButtonCancel != null;
        diagButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });

        Button diagButtonOK = dialog.findViewById(R.id.customDialogOk);
        assert diagButtonOK != null;
        diagButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

    }

    @OnClick(R.id.img_back)
    public void img_back()
    {
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingWiFi.this,R.id.frm_filter_main_container, SettingHome.newInstance());
        else ReplaceFragment.replaceFragment(SettingWiFi.this, R.id.frm_main_container, SettingHome.newInstance());
    }

    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }


}