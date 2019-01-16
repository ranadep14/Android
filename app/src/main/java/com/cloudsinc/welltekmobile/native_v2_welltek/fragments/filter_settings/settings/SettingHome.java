package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.PinInputDialog;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.SwitchTrackChange;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
/**
 * The SettingMainFragment is for contain different setting ,location,time and date
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class SettingHome extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private String input;
    public static SettingHome newInstance() {
        return new SettingHome();
    }
    View view;
    private Context mcontext;
    private Switch switch_demoScenes;
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @BindView(R.id.img_back)ImageView img_back;
    @BindView(R.id.switch_pin_popup)Switch switch_pin_popup;
    @BindView(R.id.txt_pin_status)TextView txt_pin_status;
    @BindView(R.id.rel_pin_input)RelativeLayout rel_pin_input;
    PinInputDialog intd_pin =null;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    PrefManager spm;
    private String TAG=this.getClass().getSimpleName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_setting, container, false);
        mcontext = view.getContext();
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();
        FontUtility.setAppFont(mContainer, mFont);
        ButterKnife.bind(SettingHome.this, view);
        switch_demoScenes= view.findViewById(R.id.switch_demoScenes);
        spm=new PrefManager(mcontext);
        txt_fragment_title.setText("Home Settings");
        SwitchTrackChange.changeTrackColor(mcontext, switch_demoScenes);
        if(new PrefManager(mcontext).getValue("demo_scene").equals("true")) {
            switch_demoScenes.setChecked(true);
            SwitchTrackChange.changeTrackColor(mcontext, switch_demoScenes);
        }
        rel_pin_input.setVisibility(App.isOrientationFlag()?View.VISIBLE:View.GONE);



        switch_demoScenes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked)
                {
                    new PrefManager(mcontext).setValue("demo_scene","true");
                    switch_demoScenes.setChecked(true);
                    final AlertDialog dialog = new AlertDialog.Builder(mcontext)
                            .setView(R.layout.demo_scene_custom_dialog)
                            .create();
                    View v = dialog.getWindow().getDecorView();
                    v.setBackgroundResource(android.R.color.transparent);
                    dialog.show();
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    Button diagButtonOK = dialog.findViewById(R.id.btn_confirm);
                    final TextView valid_password = dialog.findViewById(R.id.valid_password);
                    Button diagButtonCancel = dialog.findViewById(R.id.btn_cancle);
                    final EditText demo_pwd = dialog.findViewById(R.id.demo_pwd);
                    input=demo_pwd.getText().toString();
                    diagButtonCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            switch_demoScenes.setChecked(false);
                        }
                    });
                    diagButtonOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (demo_pwd.getText().toString().equals("darwin")){
                                new PrefManager(mcontext).setValue("demo_scene","true");
                                final AlertDialog dialogg = new AlertDialog.Builder(mcontext)
                                        .setView(R.layout.custom_dialog_single)
                                        .create();
                                View v = dialogg.getWindow().getDecorView();
                                v.setBackgroundResource(android.R.color.transparent);
                                dialogg.show();
                                dialogg.setCancelable(false);
                                dialogg.setCanceledOnTouchOutside(false);
                                Button diagButtonOK = dialogg.findViewById(R.id.customDialogOk);
                                final TextView title = dialogg.findViewById(R.id.title);
                                final TextView msg = dialogg.findViewById(R.id.msg);
                                msg.setText("Demo scene activated");
                                diagButtonOK.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialogg.dismiss();
                                    }
                                });
                                new PrefManager(mcontext).setValue("demo_scene","true");
                                dialog.dismiss();
                                switch_demoScenes.setChecked(true);
                            }
                            else {
                                new PrefManager(mcontext).setValue("demo_scene","false");
                                final AlertDialog dialog = new AlertDialog.Builder(mcontext)
                                        .setView(R.layout.custom_dialog_single)
                                        .create();
                                View v = dialog.getWindow().getDecorView();
                                v.setBackgroundResource(android.R.color.transparent);
                                dialog.show();
                                dialog.setCancelable(false);
                                dialog.setCanceledOnTouchOutside(false);
                                Button diagButtonOK = dialog.findViewById(R.id.customDialogOk);
                                final TextView title = dialog.findViewById(R.id.title);
                                final TextView msg = dialog.findViewById(R.id.msg);
                                msg.setText("Incorrect Password");
                                diagButtonOK.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                                //  valid_password.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    SwitchTrackChange.changeTrackColor(mcontext, switch_demoScenes);
                }
                else {
                    new PrefManager(mcontext).setValue("demo_scene","false");
                    SwitchTrackChange.changeTrackColor(mcontext, switch_demoScenes);
                    switch_demoScenes.setChecked(false);
                }
            }
        });
        setPinState();
        setSubcriber();
        return view;
    }
    private void setSubcriber() {
        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("" + App.getRoomData());
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
                if(string.equals("reset_pin"))
                {
                   setPinState();
                }
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);
    }

    private void setPinState()
    {
        switch_pin_popup.setOnCheckedChangeListener(null);
        intd_pin=null;
        Logs.info(TAG,"-------"+spm.getValue("pin_value"));
        if(spm.getValue("pin_value").equals("No Record"))
        {
            switch_pin_popup.setChecked(false);
            txt_pin_status.setText(mcontext.getResources().getString(R.string.turn_on_pin));
        }
        else
        {
            switch_pin_popup.setChecked(true);
            txt_pin_status.setText(mcontext.getResources().getString(R.string.turn_off_pin));
        }

        SwitchTrackChange.changeTrackColor(mcontext, switch_pin_popup);
        switch_pin_popup.setOnCheckedChangeListener(this);
    }
    public void onBackPressed() {
        //super.onBackPressed();
        //create a dialog to ask yes no question whether or not the user wants to exit
    }
    @OnClick(R.id.img_back)
    public void img_back()
    {
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingHome.this,R.id.frm_filter_main_container, SettingMainFragment.newInstance());
        else ReplaceFragment.replaceFragment(SettingHome.this, R.id.frm_main_container, SettingMainFragment.newInstance());
    }
    @OnClick(R.id.lin_wifi_network)
    public void lin_wifi_network()
    {
        if(App.isOrientationFlag())ReplaceFragment.replaceFragment(SettingHome.this,R.id.frm_filter_main_container,SettingWiFi.newInstance());
        else ReplaceFragment.replaceFragment(SettingHome.this, R.id.frm_main_container, SettingWiFi.newInstance());
    }
    @OnClick(R.id.lin_location)
    public void lin_location()
    {
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingHome.this,R.id.frm_filter_main_container,SettingLocation.newInstance());
        else ReplaceFragment.replaceFragment(SettingHome.this, R.id.frm_main_container, SettingLocation.newInstance());
    }
    @OnClick(R.id.lin_time_date)
    public void lin_time_date()
    {
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingHome.this,R.id.frm_filter_main_container,SettingTimeDate.newInstance());
        else ReplaceFragment.replaceFragment(SettingHome.this, R.id.frm_main_container, SettingTimeDate.newInstance());
    }

    public void pinInputDialog(boolean b)
    {
        if(intd_pin ==null) {
            Bundle bundle=new Bundle();
            bundle.putString("call_from","reset_pin");
            bundle.putBoolean("action_flag",b);
            intd_pin =new PinInputDialog();
            intd_pin.setCancelable(false);
            intd_pin.setArguments(bundle);
            intd_pin.show(getActivity().getFragmentManager(),"SettingHome");

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if(compoundButton.getId()==R.id.switch_pin_popup)
        {
           if(b) pinInputDialog(b);
           else {
               spm.removeKey("pin_value");
               SwitchTrackChange.changeTrackColor(mcontext, switch_pin_popup);
               setPinState();
           }

        }

    }
}