package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.device_settings.DeviceSettingSelectRoom;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.device_settings.DeviceSettingSelectZone;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ProvisionDevicesCall;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * This class containing functionality related to update all type of devices like sensor,hvac,lights,blinds,sonos.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class SettingDevicesLights extends Fragment {
    public static SettingDevicesLights newInstance() {
        return new SettingDevicesLights();
    }

    private View view;


    @BindView(R.id.txt_room)TextView txt_room;
    @BindView(R.id.txt_dev_name)TextView txt_device_name;
    @BindView(R.id.txt_install_date)TextView txt_install_date;
    @BindView(R.id.txt_scenes)TextView txt_scenes;
    @BindView(R.id.edt_DevName)EditText edt_DevName;
    @BindView(R.id.lin_other_devices)LinearLayout lin_other_devices;
    @BindView(R.id.lin_water_air)LinearLayout lin_water_air;
    @BindView(R.id.lin_sensor)LinearLayout lin_sensor;
    @BindView(R.id.lin_zone)LinearLayout lin_zone;
    @BindView(R.id.lin_scence)LinearLayout lin_scence;
    @BindView(R.id.txt_zone)TextView txt_zone;
    @BindView(R.id.txt_air_water_name)TextView txt_air_water_name;
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @BindView(R.id.txt_save)TextView txt_save;
    String TAG=SettingDevicesLights.this.getClass().getSimpleName();



    //sensor components
    @BindView(R.id.txt_sensor_name)TextView txt_sensor_name;
    @BindView(R.id.lin_hvac_zone)LinearLayout lin_hvac_zone;
    @BindView(R.id.txt_hvac_zone)TextView txt_hvac_zone;
    @BindView(R.id.txt_sensor_room)TextView txt_sensor_room;
    @BindView(R.id.lin_sensor_room)LinearLayout lin_sensor_room;


    @BindView(R.id.roomlyt)LinearLayout lin_room;


    private SimpleDateFormat sdf;
    private Bundle bundle;



    private JSONArray scenceJsonArray=new JSONArray();

    private ArrayList<Integer> scenceArrayList=new ArrayList<>();

    private JSONObject devicejsonobject;
    private Context mcontext;
    private String room_id,zone_id;
    private String device_id;
    private String str_device_type="",str_room_type="",picked_installation_date="";
    private String str_scene="",device_name="",room_type="",str_installation_date="",str_zone="",room_title="",str_sensor_room="",str_hvac_zone="",str_room="";
    Calendar c = Calendar.getInstance();
    private Date inputDate;
    private boolean music_flag=false;
    private DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private boolean selectRoomFlag=false;
    private boolean selectDevicenmFlag=true;
    private boolean selectScence=false;


    private  boolean zoneSelectFlag=false;
    private  boolean flag=false;
    String str_scences;
    private JSONArray roomSceneJson;
    String hub_type="";
    String zone_type="";
    private  boolean bedroomFlag=false;
    String str_country="";
    String str_filter_zone="";
    boolean scene_flag=false,room_flag=false,zone_flag=false,hvac_zone_flag=false,install_date_flag=false,sensor_room_flag=false;


    private Observable<String> mobservable;
    private Observer<String> myObserver;
    String install_date="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting_devices_lights, container, false);
        ButterKnife.bind(this,view);
       // hub_type=App.getHubInfo().getString("")
        mcontext=view.getContext();
        device_id= App.getDevice().getDevice_id();
        txt_save.setVisibility(View.GONE);
        try {
            JSONObject jsonObject=App.getHubInfo().getJSONArray("data").getJSONObject(0);
            hub_type = jsonObject.getString("CML_HUB_SUBSCRIPTION");
            str_country=jsonObject.getString("CML_ORG_COUNTRY");
           // System.out.println("ccccccccccccccccccccccc"+getCountryCode(jsonObject.getString("CML_ORG_COUNTRY"))+"ccccccccccccccccccccc"+jsonObject.getString("CML_ORG_COUNTRY"));
            sdf=new SimpleDateFormat("dd/MM/yyyy");
        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_Hub_type",""+ex.toString());
        }
         Logs.info(TAG+"_AssingZOne", App.getDevice().getAssign_room()+"-----"+hub_type);
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();
         Logs.info(TAG+"_WhereIM","IMINDeviceSettingLight"+ App.getDevice().getDevice_type());
        FontUtility.setAppFont(mContainer, mFont);


        str_device_type= App.getDevice().getDevice_type();
        room_type=getRoomType(App.getDevice().getAssign_room());
        txt_fragment_title.setText("Device Settings");


         Logs.info(TAG+"_Device",""+str_device_type+""+ App.getDevice().getAssign_room());
        String str_room_id= App.getDevice().getAssign_room();

        try {
            if(str_room_id.equals(null)||str_room_id.isEmpty()) {
                 Logs.info(TAG+"_SettingDevicesLights1",""+ App.getDevice().getAssign_room());
                txt_zone.setText(getString(R.string.selectzone));
                txt_room.setText(getString(R.string.selectroom));
                zoneSelectFlag=false;
                selectRoomFlag=false;
            }
            else
            {
                 Logs.info(TAG+"_SettingDevicesLights",""+ App.getDevice().getAssign_room());
                str_filter_zone=getZoneTitle(App.getDevice().getAssign_room());
                txt_zone.setText(str_filter_zone);
                txt_room.setText(getRoomTitle(App.getDevice().getAssign_room()));

            }

            inputDate = inputFormat.parse(getInstallDate());
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(inputDate);
            int day_of_month=calendar.get(Calendar.DAY_OF_MONTH);
            int month=calendar.get(Calendar.MONTH);
            int year=calendar.get(Calendar.YEAR);
            picked_installation_date=day_of_month<10?("0"+day_of_month):day_of_month+ "/" + (month<10?("0"+month):month )+ "/" + year;
            txt_install_date.setText(""+(DateFormat.getDateInstance(DateFormat.MEDIUM,getLocaleByCountryName(str_country)).format(calendar.getTime())));
            install_date=(DateFormat.getDateInstance(DateFormat.MEDIUM,getLocaleByCountryName(str_country)).format(inputDate));
        }
        catch (Exception ex){
             Logs.error(TAG+"_DateError",""+ex.getMessage());}
        switch (str_device_type) {
            case "Water":
            case "Air":
                lin_other_devices.setVisibility(View.GONE);
                lin_sensor.setVisibility(View.GONE);
                lin_water_air.setVisibility(View.VISIBLE);
                lin_zone.setVisibility(View.VISIBLE);
                break;
            case "Sensor":
                lin_other_devices.setVisibility(View.GONE);
                lin_sensor.setVisibility(View.VISIBLE);
                lin_water_air.setVisibility(View.GONE);
                lin_scence.setVisibility(View.GONE);
                if (hub_type.equals("Basic")) {
                    Logs.info(TAG + "_hvac_flag", "" + checkHVAC());
                    if (checkHVAC()) {
                        zone_type = "hvac_zone";

                    } else {
                        zone_type = "zone";
                    }
                    lin_hvac_zone.setVisibility(View.VISIBLE);
                    lin_sensor_room.setVisibility(View.GONE);
                } else {
                    lin_hvac_zone.setVisibility(View.GONE);
                    lin_sensor_room.setVisibility(View.VISIBLE);
                }
                break;
            default:
                zoneSelectFlag = true;
                lin_water_air.setVisibility(View.GONE);
                lin_hvac_zone.setVisibility(View.GONE);
                lin_other_devices.setVisibility(View.VISIBLE);
                break;
        }

        if (str_device_type.equals("Music")) {
            music_flag = true;
        }
        Bundle temp_bundle= App.getTemp_bundle();
        if(temp_bundle.keySet().size()>0 ) {

            if (temp_bundle.containsKey("installDate")) {
                txt_install_date.setText(App.getTemp_bundle().getString("installDate"));
            }
            if (temp_bundle.containsKey("select_flag")) {
                flag = App.getTemp_bundle().getBoolean("select_flag");
            }
            if (temp_bundle.containsKey("room_id")) {

                selectRoomFlag=true;
                room_id = temp_bundle.getString("room_id");
                room_type=getRoomType(room_id);
                String room_title=getRoomTitle(room_id);
                 Logs.info(TAG+"_room_id", room_id+"-----"+ App.getDevice().getDevice_type()+"----"+getRoomType(App.getDevice().getAssign_room()));
                txt_room.setText(room_title);
                room_flag= !room_title.equals(str_room);
                if(lin_other_devices.getVisibility()==View.VISIBLE)checkOtherDevicesChangeSave();
                txt_sensor_room.setText(room_title);
                sensor_room_flag= !room_title.equals(str_sensor_room);
                if(str_device_type.equals("Sensor") )checkHVAcChangeSave();
                if(App.getDevice().getDevice_type().equals("HVAC"))
                {
                        lin_scence.setVisibility(View.GONE);
                }
                if(App.getDevice().getDevice_type().equals("Music"))
                {
                    if(getRoomType(room_id).equals("Bedroom"))
                    {
                        lin_scence.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        lin_scence.setVisibility(View.GONE);
                    }

                }


            }
            if (temp_bundle.containsKey("zone_id")) {
                zone_id = temp_bundle.getString("zone_id");
                 Logs.info(TAG+"_zone_id", zone_id);
                str_zone=getZoneTitle(zone_id);
                txt_zone.setText(str_zone);
                txt_hvac_zone.setText(str_zone);
                zoneSelectFlag=true;
                zone_flag= !str_zone.equals(str_filter_zone);
                hvac_zone_flag= !str_zone.equals(str_hvac_zone);
                if(str_device_type.equals("Sensor") )checkHVAcChangeSave();
               if(str_device_type.equals("Water") || str_device_type.equals("Air")) checkFilterChangeSave();

            }
            if (temp_bundle.containsKey("device_name")) {
                device_name = temp_bundle.getString("device_name");
                edt_DevName.setText(device_name);
                txt_sensor_name.setText(device_name);

            }

            if (temp_bundle.containsKey("Scence")) {

                selectScence=true;
                 Logs.info(TAG+"_Scence", "" + temp_bundle.getIntegerArrayList("Scence"));
                for (int i = 0; i < temp_bundle.getIntegerArrayList("Scence").size(); i++) {
                    try {
                        scenceJsonArray.put(i, "" + temp_bundle.getIntegerArrayList("Scence").get(i));

                    } catch (Exception ex) {
                         Logs.error(TAG+"_Error", "" + ex.getMessage());
                    }
                }
                scenceArrayList = temp_bundle.getIntegerArrayList("Scence");
                if(str_device_type.equals("Music")) {
                    if(scenceArrayList.size()>1)
                    {
                        scenceArrayList.clear();
                        scenceArrayList.add(5);
                    }

                }
                setSecence();
                scene_flag= !(txt_scenes.getText().toString().equals(str_scene));
                if(lin_other_devices.getVisibility()==View.VISIBLE)checkOtherDevicesChangeSave();

            }
        }

        else {
             Logs.info(TAG+"_WhereIM","IN device json");
            setValue(App.getDevice().getDevice_id());
            room_id= App.getDevice().getAssign_room();
            zone_id= App.getDevice().getAssign_room();
        }









         Logs.info(TAG+"_DeviceLight","aaaaaaaaaaaaaaaaaaaaaaaaaa"+ App.getDevice().getAssign_room());
         Logs.info(TAG+"_Device",""+getRoomTitle(App.getDevice().getAssign_room())+"- "+ App.getDevice().getDevice_type());


        final String zone_title=getZoneTitle(App.getDevice().getAssign_room());

        txt_device_name.setText(" "+App.getDevice().getDevice_title());

        if(!zone_title.equals("")) txt_air_water_name.setText(" "+App.getDevice().getDevice_title());
        else txt_air_water_name.setText(" "+App.getDevice().getDevice_title());
        edt_DevName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



                if(s.length()==0)
                {
                    selectDevicenmFlag=false;
                    checkAllFields();
                }





            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        txt_install_date.setPaintFlags(txt_install_date.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);




        txt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateDevice();

            }
        });


        checkAllFields();
        setSubcriber();


        return view;
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


                 Logs.info(TAG+"_WhereIm","InRoomListDisplay"+string+"------"+App.getDevice().getDevice_type());
                if(string.equals(App.getDevice().getDevice_type())) setValue(App.getDevice().getDevice_id());

            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);


    }
    @OnClick(R.id.txt_install_date)
    public void txt_clock()
    {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        c.add(Calendar.DATE,0);


        DatePickerDialog datePickerDialog = new DatePickerDialog(mcontext,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        try {

                            Calendar calendar=Calendar.getInstance();
                            calendar.setTime(sdf.parse(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year));
                            int month=monthOfYear + 1;
                            picked_installation_date=dayOfMonth<10?("0"+dayOfMonth):dayOfMonth+ "/" + (month<10?("0"+month):month )+ "/" + year;
                            String picked_date=(DateFormat.getDateInstance(DateFormat.MEDIUM, getLocaleByCountryName(str_country)).format(calendar.getTime()));
                            App.getTemp_bundle().putString("installDate", picked_date);
                            txt_install_date.setText(picked_date);
                           install_date_flag= !picked_date.equals(install_date);
                           checkFilterChangeSave();
                            Logs.info(TAG,"**********************"+picked_date+"***************"+install_date);
                        }
                        catch (Exception ex)
                        {
                            Logs.error(TAG,"datepickererror....."+ex.getMessage());
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();

    }
    public void checkFilterChangeSave()
    {
        if(zone_flag||install_date_flag)
        {
            txt_save.setVisibility(View.VISIBLE);
        }
        else
        {
            txt_save.setVisibility(View.GONE);
        }
    }
    public void checkHVAcChangeSave()
    {
        if(sensor_room_flag||hvac_zone_flag)
        {
            txt_save.setVisibility(View.VISIBLE);
        }
        else
        {
            txt_save.setVisibility(View.GONE);
        }
    }
    public void checkOtherDevicesChangeSave()
    {
        Logs.info(TAG,"******************************************************"+scene_flag+"------"+room_flag);
        if(scene_flag||room_flag)
        {
           txt_save.setVisibility(View.VISIBLE);
        }
        else
        {
            txt_save.setVisibility(View.GONE);
        }
    }
    @OnClick(R.id.lin_scence)
    public void onScenes()
    {


        try {


             Logs.info(TAG+"_RoomType",room_type+"------------"+room_title);
            Bundle bundle= App.getTemp_bundle();
            bundle.putString("device_name",edt_DevName.getText().toString());
            bundle.putString("room_type",room_type);
            bundle.putString("room_title",room_title);
            bundle.putIntegerArrayList("Scence",scenceArrayList);

            App.setTemp_bundle(bundle);

            if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingDevicesLights.this,R.id.frm_filter_main_container,  SettingDevicesScenesList.newInstance());
            else ReplaceFragment.replaceFragment(SettingDevicesLights.this, R.id.frm_main_container, SettingDevicesScenesList.newInstance());


        }
        catch (Exception ex){
            ex.printStackTrace();
        }


    }
    @OnClick(R.id.roomlyt)
    public void txt_room()
    {
        Bundle bundle= App.getTemp_bundle();
        bundle.putString("device_name",edt_DevName.getText().toString());
        bundle.putString("room_id",room_id);
        App.setTemp_bundle(bundle);
        if(!App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingDevicesLights.this,R.id.frm_main_container,  DeviceSettingSelectRoom.newInstance());
        else ReplaceFragment.replaceFragment(SettingDevicesLights.this, R.id.frm_filter_main_container, DeviceSettingSelectRoom.newInstance());

    }

    @OnClick(R.id.lin_sensor_room)
    public void lin_sensor_room()
    {
        Bundle bundle= App.getTemp_bundle();
        bundle.putString("device_name",edt_DevName.getText().toString());
        bundle.putString("room_id",room_id);
        App.setTemp_bundle(bundle);
        if(!App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingDevicesLights.this,R.id.frm_main_container,  DeviceSettingSelectRoom.newInstance());
        else ReplaceFragment.replaceFragment(SettingDevicesLights.this, R.id.frm_filter_main_container, DeviceSettingSelectRoom.newInstance());

    }
    @OnClick(R.id.lin_zone)
    public void lin_zone()
    {
        Bundle bundle= App.getTemp_bundle();
        bundle.putString("device_name",edt_DevName.getText().toString());
        bundle.putString("zone_id",App.getDevice().getAssign_room());
        bundle.putString("device_type","non_sensor");
        App.setTemp_bundle(bundle);
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingDevicesLights.this,R.id.frm_filter_main_container,  DeviceSettingSelectZone.newInstance());
        else ReplaceFragment.replaceFragment(SettingDevicesLights.this, R.id.frm_main_container, DeviceSettingSelectZone.newInstance());

    }

    @OnClick(R.id.lin_hvac_zone)
    public void lin_hvac_zone()
    {
        Bundle bundle= App.getTemp_bundle();
        bundle.putString("device_name",edt_DevName.getText().toString());
        bundle.putString("zone_id",App.getDevice().getAssign_room());
      if(zone_type.equals("zone"))  bundle.putString("device_type","non_sensor");
      else bundle.putString("device_type","sensor");
        App.setTemp_bundle(bundle);
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingDevicesLights.this,R.id.frm_filter_main_container,  DeviceSettingSelectZone.newInstance());
        else ReplaceFragment.replaceFragment(SettingDevicesLights.this, R.id.frm_main_container, DeviceSettingSelectZone.newInstance());

    }





    @OnClick(R.id.img_back)
    public void img_back()
    {
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingDevicesLights.this, R.id.frm_filter_main_container,DeviceSettingFragment.newInstance());
        else ReplaceFragment.replaceFragment(SettingDevicesLights.this, R.id.frm_main_container, DeviceSettingFragment.newInstance());
    }



    public void deleteDevice()
    {

        try {


            JSONObject obj = new JSONObject();
            obj.put("data", App.getSelectedDeviceJson());
            obj.put("type", "unprovision_device");
             Logs.info(TAG+"_SimulationRequest",""+obj);
            App.getSocket().emit("action", obj);

            Log.d("DeviceUpdatJson",""+obj);

            final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setView(R.layout.custom_dialog)
                    .create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            TextView msgt= dialog.findViewById(R.id.title);
            msgt.setText("Alert");

            TextView msg= dialog.findViewById(R.id.msg);
            LinearLayout lin_main= dialog.findViewById(R.id.lin_main);
            lin_main.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    UserInteractionSleep.userInteract(mcontext);
                    return false;
                }
            });
            msg.setText("Are you sure you want to Unprovision Device?");
            Button diagButtonCancel = dialog.findViewById(R.id.customDialogCancel);
            diagButtonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                }
            });

            Button diagButtonOK = dialog.findViewById(R.id.customDialogOk);
            diagButtonOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ////////////////
                    final AlertDialog dialogone = new AlertDialog.Builder(getActivity())
                            .setView(R.layout.custom_dialog_single)
                            .create();
                    dialogone.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogone.show();
                    TextView msgt= dialogone.findViewById(R.id.title);
                    msgt.setText("Alert");

                    TextView msg= dialogone.findViewById(R.id.msg);
                    msg.setText("Device unprovisioned successfully");


                    Button diagButtonOK = dialogone.findViewById(R.id.customDialogOk);
                    diagButtonOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ProvisionDevicesCall.getProvisional();
                            if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingDevicesLights.this,R.id.frm_filter_main_container,  SettingMainFragment.newInstance());
                            else ReplaceFragment.replaceFragment(SettingDevicesLights.this, R.id.frm_main_container, DeviceSettingFragment.newInstance());
                            dialogone.dismiss();

                        }
                    });
                    dialog.dismiss();
                }

            });



        }
        catch (Exception ex){

             Logs.error(TAG+"_SimulationRequest",ex.getMessage());
        }
    }

    @SuppressLint("NewApi")
    public void setValue(String device_id)
    {
        Bundle bundle= App.getTemp_bundle();
        try {
            JSONArray jsonArray = App.getProvisionalDevicesData().getJSONArray("data");

            for (int i = 0; i <jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_ID").equals(device_id))
                {
                    devicejsonobject=jsonObject;
                    App.setSelectedDeviceJson(devicejsonobject);
                     Logs.info(TAG+"_device_id_litht","Got.....device"+getZoneTitle(jsonObject.getString("room")));
                    device_name=jsonObject.getString("CML_TITLE");

                    edt_DevName.setText(""+device_name);
                    txt_sensor_name.setText(""+device_name);
                   /* if (bundle==null) {*/
                    String room_title=  getRoomTitle(jsonObject.getString("room"));
                    String zone_title=  getZoneTitle(jsonObject.getString("room"));
                    String hvac_zone_title=  getZoneTitle(jsonObject.getString("room"));
                    str_hvac_zone=hvac_zone_title;
                    str_sensor_room=room_title;
                    str_room=room_title;
                    if(zone_title==null|| Objects.equals(zone_title, ""))
                    {
                        // txt_room.setText(mcontext.getResources().getString(R.string.selectroom));


                    }
                    else
                    {


                        bundle.putString("room",room_title);
                        bundle.putString("room_id",jsonObject.getString("room"));
                        txt_room.setText(room_title);
                    }

                    if(hvac_zone_title==null|| Objects.equals(hvac_zone_title, ""))
                    {
                        // txt_room.setText(mcontext.getResources().getString(R.string.selectroom));

                    }
                    else
                    {

                        bundle.putString("room",hvac_zone_title);
                        bundle.putString("room_id",jsonObject.getString("room"));
                        txt_hvac_zone.setText(hvac_zone_title);
                    }

                    if(room_title==null|| Objects.equals(room_title, ""))
                    {


                    }
                    else
                    {
                        bundle.putString("room",room_title);
                        bundle.putString("room_id",jsonObject.getString("room"));
                        txt_room.setText(room_title);
                        txt_sensor_room.setText(room_title);
                    }
                    if(jsonObject.getJSONArray("scenes")==null) {
                        txt_scenes.setText("Please select Scenes");
                        str_scene="Please select Scenes";
                    }
                    else
                    {

                        scenceArrayList.clear();
                        for (int j = 0; j < jsonObject.getJSONArray("scenes").length(); j++) {
                            scenceArrayList.add(Integer.parseInt(jsonObject.getJSONArray("scenes").getString(j)));
                        }
                        bundle.putIntegerArrayList("Scence",scenceArrayList);

                         Logs.info(TAG+"_Scence",""+scenceArrayList);
                        if(str_device_type.equals("Music")) {
                            if(scenceArrayList.size()>1)
                            {
                                scenceArrayList.clear();
                                scenceArrayList.add(5);
                            }

                        }
                        bedroomFlag = getRoomType(jsonObject.getString("room")).equals("Bedroom");

                        if(!bedroomFlag)
                        {
                            if(scenceArrayList.size()>0) {
                                if(scenceArrayList.contains(5))  scenceArrayList.remove(scenceArrayList.indexOf(5));
                                if(scenceArrayList.contains(6))  scenceArrayList.remove(scenceArrayList.indexOf(6));
                            }
                        }
                        setSecence();
                        str_scene=txt_scenes.getText().toString();
                    }
                    // }
                    App.setTemp_bundle(bundle);
                }
            }


        }
        catch (Exception ex){
             Logs.error(TAG+"_SimulationError",ex.getMessage());}
    }

    public String getRoomTitle(String str_room_id)
    {
        String temp_room_title="";

        if(str_room_id!=null) {
            if (!str_room_id.equals("")) {

                try {
                    JSONArray jsonArray = App.getRoomData().getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject.getString("CML_ID").equals(str_room_id)) {
                             Logs.info(TAG+"_device_id_litht", "Got.....room");
                            temp_room_title = jsonObject.getString("CML_TITLE");
                            room_title = jsonObject.getString("CML_TITLE");
                            if(!App.getTemp_bundle().containsKey("room_id")) {

                                room_type=jsonObject.getString("CML_ROOM_TYPE");
                                if(App.getDevice().getDevice_type().equals("HVAC"))
                                {
                                    lin_scence.setVisibility(View.GONE);
                                }
                                if(App.getDevice().getDevice_type().equals("Music"))
                                {
                                    if(jsonObject.getString("CML_ROOM_TYPE").equals("Bedroom"))
                                    {
                                        lin_scence.setVisibility(View.VISIBLE);
                                    }
                                    else
                                    {
                                        lin_scence.setVisibility(View.GONE);
                                    }

                                }

                            }


                        }
                    }


                } catch (Exception ex) {
                     Logs.error(TAG+"_SimulationError", ex.getMessage());
                }

                try {
                    JSONArray jsonArray = App.getZoneJson().getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject.getString("CML_ID").equals(str_room_id)) {
                             Logs.info(TAG+"_device_id_litht", "Got.....room");
                            temp_room_title = jsonObject.getString("CML_TITLE");
                            room_title = jsonObject.getString("CML_TITLE");

                        }
                    }


                } catch (Exception ex) {
                     Logs.error(TAG+"_SimulationError", ex.getMessage());
                }
            }
        }
        return temp_room_title;
    }

    public String getZoneTitle(String str_room_id)
    {

         Logs.info(TAG+"_device_id_litht","Got.....zone"+str_room_id+"-----"+str_device_type);
        String room_title="";
        try {
            JSONArray jsonArray=null;
             Logs.info(TAG+"_device_id_litht","Got.....zone"+ App.getZoneJson().getJSONArray("data"));
            if(checkHVAC()) {
                if (str_device_type.equals("Sensor"))
                    jsonArray = App.getHvacZoneProvisionedJson().getJSONArray("data");
                else jsonArray = App.getZoneJson().getJSONArray("data");
            }
            else
            {
                jsonArray = App.getZoneJson().getJSONArray("data");
            }
            for (int i = 0; i <jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_ID").equals(str_room_id))
                {
                     Logs.info(TAG+"_device_id_litht","Got.....zone");
                    room_title=jsonObject.getString("CML_TITLE");

                }
            }


        }
        catch (Exception ex){
             Logs.error(TAG+"_SimulationError",""+ex.getMessage());}
        return room_title;
    }

    public String getInstallDate()
    {
        String install_date="";
        try {
            JSONArray jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
            for (int i = 0; i <jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_ID").equals(device_id))
                {
                    //   Logs.info(TAG+"_Date","I M In Date"+jsonObject.getString("installationDate"));
                    if(jsonObject.has("installationDate")) install_date= jsonObject.getString("installationDate");
                    else install_date= jsonObject.getString("CREATED_ON");


                }
            }


        }
        catch (Exception ex){
             Logs.error(TAG+"_InstallationTimeError",ex.getMessage());}
        return install_date;
    }


    public void updateDevice()
    {


        final AlertDialog dialog = new AlertDialog.Builder(mcontext)
                .setView(R.layout.custom_dialog)
                .create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView msgt= dialog.findViewById(R.id.title);
        msgt.setText("ALERT");

        TextView msg= dialog.findViewById(R.id.msg);
        msg.setText("Do you want to save changes?");
        Button diagButtonCancel = dialog.findViewById(R.id.customDialogCancel);
        diagButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button diagButtonOK = dialog.findViewById(R.id.customDialogOk);

        diagButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upadateDevice();
                hideKeyboard();
                dialog.dismiss();

            }
        });

    }

    public static String getRoomType(String str_room_id) {
        String room_type = "";

        if (str_room_id != null) {
            if (!str_room_id.equals("")) {

                try {
                    JSONArray jsonArray = App.getRoomData().getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject.getString("CML_ID").equals(str_room_id)) {
                             Logs.info("SettingDevicesLights_device_id_litht", "Got.....room");

                            room_type = jsonObject.getString("CML_ROOM_TYPE");



                        }
                    }


                } catch (Exception ex) {
                     Logs.info("SettingDevicesLights_SimulationError", ex.getMessage());
                }
            }
        }
        return room_type;
    }

    public void upadateDevice()
    {
        try {


            JSONObject obj = new JSONObject();
            if (str_device_type.equals("Water")||str_device_type.equals("Air")) {



                Logs.info(TAG,"--------------------"+picked_installation_date);
                Calendar c = Calendar.getInstance();

                inputDate=new SimpleDateFormat( "dd/MM/yyyy" ).parse(picked_installation_date);
                String created_and_modified_date = inputFormat.format(inputDate);
                c.setTime(inputFormat.parse(created_and_modified_date));
                c.add(Calendar.DATE, 180);

                String expriry_date=inputFormat.format(c.getTime());

                obj.put("data", App.getSelectedDeviceJson().put("installationDate", created_and_modified_date).put("room",zone_id));
            }
            else if(str_device_type.equals("Sensor"))
            {
                if(hub_type.equals("Basic"))  obj.put("data", App.getSelectedDeviceJson().put("room", zone_id).put("scenes", scenceJsonArray).put("CML_TITLE", edt_DevName.getText().toString()));
                else  obj.put("data", App.getSelectedDeviceJson().put("room", room_id).put("scenes", scenceJsonArray).put("CML_TITLE", edt_DevName.getText().toString()));
            }
            else
            {
                obj.put("data", App.getSelectedDeviceJson().put("room", room_id).put("scenes", scenceJsonArray).put("CML_TITLE", edt_DevName.getText().toString())).put("group", "");

            }

            obj.put("type", "update_device");
             Logs.info(TAG+"_SimulationRequest",""+obj);
            App.getSocket().emit("action", obj);

            Log.d("DeviceUpdatJson",""+obj);

            if(!App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingDevicesLights.this,R.id.frm_main_container,  DeviceSettingFragment.newInstance());
            else ReplaceFragment.replaceFragment(SettingDevicesLights.this, R.id.frm_filter_main_container, DeviceSettingFragment.newInstance());

        }
        catch (Exception ex){
             Logs.error(TAG+"_UpddateDevice",ex.getMessage());
        }
    }

    private void setSecence()
    {

        ArrayList<Integer> arrscences=getFilterScene();
         Logs.info(TAG+"_SceneArray",""+arrscences);
        str_scences = "";
        if(arrscences==null) {
            str_scences=str_scences+"Please select scence";
        }

        for(int i=0;i<arrscences.size();i++) {


            if(i==0)
            {
                switch (arrscences.get(i)) {
                    case 1:
                        str_scences = "Circadian";
                        break;
                    case 3:
                        str_scences = mcontext.getResources().getString(R.string.energise);
                        break;
                    case 4:
                        str_scences = "Play";
                        break;
                    case 2:
                        str_scences = "Relax";
                        break;
                    case 5:
                        str_scences = "Dawn";
                        break;
                    case 6:
                        str_scences = "Sleep";
                        break;
                    case 7:
                        str_scences = "Cooking";
                        break;
                }
            }
            else {
                switch (arrscences.get(i)) {
                    case 1:
                        str_scences = str_scences + ", " + "Circadian";
                        break;
                    case 3:
                        str_scences = str_scences + ", " + mcontext.getResources().getString(R.string.energise);
                        break;
                    case 4:
                        str_scences = str_scences + ", " + "Play";
                        break;
                    case 2:
                        str_scences = str_scences + ", " + "Relax";
                        break;
                    case 5:
                        str_scences = str_scences + ", " + "Dawn";
                        break;
                    case 6:
                        str_scences = str_scences + ", " + "Sleep";
                        break;
                    case 7:
                        str_scences = str_scences + ", " + "Cooking";
                        break;


                }
            }
            txt_scenes.setText(str_scences);
        }
    }

    public ArrayList<Integer> getFilterScene()
    {
        ArrayList<Integer> storeArrayList=new ArrayList<>();


        String jsonarr = null;
        try {
            InputStream is = mcontext.getAssets().open("scenes_ids.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonarr = new String(buffer, "UTF-8");
             Logs.info(TAG+"_Scenesjson", jsonarr);

            JSONArray jsonArray = new JSONArray(jsonarr);

            Logs.info(TAG+"_rrrrrrrrrrrrrrrr", room_type + "mmmmmmmmmm" + App.getDevice().getDevice_type());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray jsonArray1 = jsonObject.getJSONArray("roomType");
                JSONArray jsonArray2 = jsonObject.getJSONArray("type");

                for (int j = 0; j < jsonArray2.length(); j++) {

                    for (int k = 0; k < jsonArray1.length(); k++) {
                        if (jsonArray1.getString(k).equals(room_type) && jsonArray2.getString(j).equals(App.getDevice().getDevice_type())) {
                           if(!jsonObject.getString("Id").equals("A")&&!jsonObject.getString("Id").equals("B")&&!jsonObject.getString("Id").equals("C")&&!jsonObject.getString("Id").equals("8")) storeArrayList.add(Integer.parseInt(jsonObject.getString("Id")));
                        }
                    }

                }

            }


        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_FiterSCeneError",""+ex.getMessage());
        }



        return storeArrayList;
    }
    private void hideKeyboard() {
        InputMethodManager inputMethodManager =(InputMethodManager)mcontext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }





    public void checkAllFields()
    {
         Logs.info(TAG+"_WherMI","InCheckAllFields"+selectDevicenmFlag+"----"+selectRoomFlag+"----"+selectScence);
        String device_type= App.getDevice().getDevice_type();
        if(device_type.equals("Water")||device_type.equals("Air")) {
            if (zoneSelectFlag ) {
               // txt_save.setVisibility(View.VISIBLE);
            }
        }
        if(device_type.equals("Lighting")||device_type.equals("Music")) {
            if ( selectDevicenmFlag &&selectRoomFlag&&selectScence) {
              //  txt_save.setVisibility(View.VISIBLE);
            }
        }
        if(device_type.equals("Sensor")||device_type.equals("HVAC")) {
             Logs.info(TAG+"_SettingDeviceLightsRooe",""+room_type);
            if(!room_type.equals("Bedroom")) {
                if (selectDevicenmFlag &&( selectRoomFlag || selectScence) ){
                   // txt_save.setVisibility(View.VISIBLE);
                }
            }
            else
            {
                if ( selectDevicenmFlag &&selectRoomFlag&&selectScence) {
                   // txt_save.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    public Locale getLocaleByCountryName(String countryName) {

        Locale locale=null;
        Locale isoCountries[] = Locale.getAvailableLocales();
        for (Locale country : isoCountries) {
           if(country.getDisplayName().contains(countryName)) {
               locale =  new Locale("en",country.getCountry());
           }


        }
        return locale;

       /* // Get all country codes in a string array.
        String[] isoCountryCodes = Locale.getISOCountries();
        Map<String, String> countryMap = new HashMap<>();

        // Iterate through all country codes:
        for (String code : isoCountryCodes) {
            // Create a locale using each country code
            Locale locale = new Locale("", code);
            // Get country name for each code.
            String name = locale.getDisplayCountry();
            // Map all country names and codes in key - value pairs.
            countryMap.put(name, code);
        }
        // Get the country code for the given country name using the map.
        // Here you will need some validation or better yet
        // a list of countries to give to user to choose from.
        String countryCode = countryMap.get(countryName); // "NL" for Netherlands.*/

      //  return countryCode;

    }

    public boolean checkHVAC()
    {
        boolean flag=false;
        try {
            JSONArray jsonArray=App.getProvisionalDevicesData().getJSONArray("data");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("type").equals("HVAC")) {
                    flag=true;
                }

            }

        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_HVACErrror",""+ex.getMessage());
        }

        return  flag;
    }

}