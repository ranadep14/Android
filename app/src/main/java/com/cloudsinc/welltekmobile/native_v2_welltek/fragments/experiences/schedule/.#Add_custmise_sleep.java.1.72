package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.ExperienceMainFragmentPortrait;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.NotifyService_sleep;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.ScheduleClient_sleep;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.TimeFormatChange;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;


/**
 * Created by Jaid Shaikh on 17/5/17.
 * Using for adding and updating sleep routines for selected room.
 */
public class Add_custmise_sleep extends Fragment {
    private JSONObject dataObj;
    private ScheduleClient_sleep scheduleClient;
    private View lyt;
    private int u_hr=0;
    private int u_min=0;
    private String ns = Context.NOTIFICATION_SERVICE;
    private Tracker mTracker;


    private View v;
    private Context mcontext;
    private static int hr,min;

    @BindView(R.id.Timepicker)TextView showTime;
    @BindView(R.id.btn_save)Button btn_save;
    private String room_id="";
    private String str_time="%02d";
    private ArrayList<Integer> dayslist=new ArrayList<>();
    private boolean selectDaysFlag=false;
    private String new_room_id="";
    private int set_dawn_hr=0;
    private int set_dawn_min=0;
    private String Current_time;

    @BindView(R.id.lbl_mon)TextView lbl_mon;
    @BindView(R.id.lbl_tue)TextView lbl_tue;
    @BindView(R.id.lbl_wed)TextView lbl_wed;
    @BindView(R.id.lbl_thu)TextView lbl_thu;
    @BindView(R.id.lbl_fri)TextView lbl_fri;
    @BindView(R.id.lbl_sat)TextView lbl_sat;
    @BindView(R.id.lbl_sun)TextView lbl_sun;
    @BindView(R.id.lbl_repeat)TextView lbl_repeat;



    @BindView(R.id.txt_mon)TextView txt_mon;
    @BindView(R.id.txt_tue)TextView txt_tue;
    @BindView(R.id.txt_wen)TextView txt_wen;
    @BindView(R.id.txt_thu)TextView txt_thu;
    @BindView(R.id.txt_fri)TextView txt_fri;
    @BindView(R.id.txt_sat)TextView txt_sat;
    @BindView(R.id.txt_sun)TextView txt_sun;
    @BindView(R.id.img_delete)TextView img_delete;
    @BindView(R.id.btn_cancel)TextView btn_cancel;

    @Nullable @BindView(R.id.txt_save)TextView txt_save;
    @Nullable @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @Nullable @BindView(R.id.img_back)ImageView img_back;


    private boolean sun=true;
    private boolean mon=true;
    private boolean tue=true;
    private boolean wen=true;
    private boolean thu=true;
    private boolean fir=true;
    private boolean sat=true;
    private boolean flag=false;

    private int hourOfDay=0;
    private int minOfDay=0;
    private String str_action="";
    private String sleep_id ="";
    private final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
    private boolean current_day_flag[]={false,false,false,false,false,false,false};
    private Bundle bundle;
    private final String TAG = Add_custmise_sleep.this.getClass().getSimpleName();

    public static Add_custmise_sleep newInstance() {
        return new Add_custmise_sleep();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_customize_sleep, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);
        setDaysOriginalState();
        lyt= v.findViewById(R.id.lytmainfont);

        final Typeface mFont = Typeface.createFromAsset(v.getContext().getAssets(),
                "fonts/SF-UI-Text-Regular.otf");
        final ViewGroup mContainer = (ViewGroup) lyt.getRootView();

        Calendar cal = Calendar.getInstance();
        if (!checkConfiguration()) {
            Logs.info(TAG,"Error in FilterFragment");
        }
        App application = (App)getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();

        hourOfDay= cal.get(Calendar.HOUR);
        minOfDay= cal.get(Calendar.MINUTE);
        getCurrentTime();
        try {

            room_id = App.getTemp_bundle().getString("room_id");
            Logs.info(TAG, "create_sleep_Room_id" + room_id);

        }catch (NullPointerException ex){
            Logs.error(TAG,"NullPointer_Error_in_bundle_roomId"+ex.getMessage());
        }
        catch (Exception ex){
            Logs.error(TAG,"Error_in_bundle_roomId"+ex.getMessage());
        }

        if(!App.isOrientationFlag()) {
            txt_fragment_title.setText("Set Sleep");
            img_back.setImageDrawable(Add_custmise_sleep.this.getResources().getDrawable(R.drawable.ic_left_arrow));

        }

        getTime();

        scheduleClient = new ScheduleClient_sleep(mcontext);
        scheduleClient.doBindService();

        bundle=App.getTemp_bundle();
      //  new_room_id=App.getnewDawnroom().getRoom_id();



        if (bundle.containsKey("sleepsimulation_id") && (bundle.containsKey("sleepCallfrom"))&&!bundle.containsKey("days"))
        {
            if(bundle.getString("sleepCallfrom").equals("edit")) {
                flag = true;
                sleep_id = bundle.getString("sleepsimulation_id");
                setAllValuesOfSleepSimulation(sleep_id);
            }
        }


        if (bundle.containsKey("actionn") ){
            str_action="delete_or_updatee";
            if(App.isOrientationFlag()) {
                img_delete.setVisibility(View.VISIBLE);
            }
            else {
                img_back.setImageDrawable(Add_custmise_sleep.this.getResources().getDrawable(R.drawable.ic_left_arrow));

                txt_save.setVisibility(View.VISIBLE);
                txt_save.setText("Delete");
                txt_save.setTextColor(Color.RED);
                img_delete.setVisibility(View.GONE);
                txt_fragment_title.setText("Edit Sleep");
            }


            btn_cancel.setVisibility(View.VISIBLE);
            img_delete.setVisibility(View.VISIBLE);
            lbl_repeat.setVisibility(View.GONE);
            sleep_id =bundle.getString("sleepsimulation_id");


        }

        if (bundle.containsKey("hours")&&bundle.containsKey("minutes")) {
            hr = bundle.getInt("hours");
            min = bundle.getInt("minutes");
            if(new PrefManager(mcontext).getValue("time_fomat").equals("1")) {
                showTime.setText(hr+":"+min);
            }
            else
            {
                showTime.setText(TimeFormatChange.convert12(hr+":"+min).toLowerCase());
            }

        }



        if (bundle.containsKey("days")) {

            selectDaysFlag = bundle.getIntegerArrayList("days").size() > 0;
            dayslist.clear();

            for (int i=0;i<bundle.getIntegerArrayList("days").size();i++) {
                setDays(bundle.getIntegerArrayList("days").get(i));
            }
        }
        if (!str_action.equals("delete_or_updatee"))daysadded();
        return v;
    }

    private void setAllValuesOfSleepSimulation(String sleepsimulation_id)
    {
        if (flag) {

            try {
                JSONArray jsonArray = App.getSleepSimulationData().getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (jsonObject.getString("CML_ID").equals(sleepsimulation_id)) {
                        room_id = jsonObject.getString("room");
                        if (!App.getTemp_bundle().containsKey("room_id"))
                            App.getTemp_bundle().putString("room_id", jsonObject.getString("room"));

                        dayslist.clear();
                        JSONArray jsonArray1 = jsonObject.getJSONArray("days");

                        if(jsonArray1.length()>0) {
                            for (int j = 0; j < jsonArray1.length(); j++) {
                                setDays(Integer.parseInt(jsonObject.getJSONArray("days").getString(j)));
                                for (int k = 0; k < 7; k++) {
                                    if (jsonArray1.getInt(j) == k + 1) {
                                        current_day_flag[k] = true;
                                    }

                                }
                            }
                        }
                        else {
                            /*int a =jsonObject.getInt("nonRepeatingDay");
                            //setNonRepeating(a);*/
                           // if (str_action.equals("delete_or_updatee"))daysadded();
                            setCurrentDay();
                        }

                        if ((bundle.containsKey("sleepCallfrom"))) {
                            if (bundle.getString("sleepCallfrom").equals("edit")) {
                                String time = jsonObject.getInt("hour") + ":" + jsonObject.getInt("minutes");
                                u_hr = jsonObject.getInt("hour");
                                u_min = jsonObject.getInt("minutes");

                                if (new PrefManager(mcontext).getValue("time_fomat").equals("1")) {
                                    showTime.setText(time);
                                } else {
                                    showTime.setText(TimeFormatChange.convert12(time).toLowerCase());
                                }
                            }
                        }
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();

                Log.e(TAG+  "Add_custmise_sleep","create_sleep_simulation : "+ex.getMessage());

            }
        }


    }


    private String getTime()
    {

        try {
            String strDefaultTimeZone;

            strDefaultTimeZone = App.getHubInfo().getJSONArray("data").getJSONObject(0).getString("CML_TIMEZONE");

            TimeZone tz = TimeZone.getTimeZone(strDefaultTimeZone);
            Calendar expdate = Calendar.getInstance(tz);


            String time;
            String hr,min;


            if(expdate.get(Calendar.HOUR_OF_DAY)<9) hr="0"+expdate.get(Calendar.HOUR_OF_DAY);
            else hr=""+expdate.get(Calendar.HOUR_OF_DAY);

            if(expdate.get(Calendar.MINUTE)<9) min="0"+expdate.get(Calendar.MINUTE);
            else min=""+expdate.get(Calendar.MINUTE);

            if(new PrefManager(mcontext).getValue("time_fomat").equals("1"))
            {
                time=hr+":"+min;
            }
            else
            {
                time=TimeFormatChange.convert12(hr+":"+min);
            }

            showTime.setText(""+time.toLowerCase());



        }
        catch(Exception ex)
        {
            Logs.error(TAG,"TimeError"+ex.getMessage());
        }
        return str_time;

    }


    @OnClick(R.id.Timepicker)
    public void pickTime() {

        Bundle bundle=App.getTemp_bundle();
        bundle.putIntegerArrayList("days",dayslist);
        bundle.putString("time",showTime.getText().toString());

        Logs.info(TAG,"Bundle_elem_Time: "+bundle);
        App.setTemp_bundle(bundle);
        DialogFragment dialogFragment = new TimePickersleepFragment();
        dialogFragment.show(getActivity().getSupportFragmentManager(), "Tp");

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick({R.id.txt_mon, R.id.txt_tue, R.id.txt_wen, R.id.txt_thu, R.id.txt_fri, R.id.txt_sat, R.id.txt_sun})
    public void textOnclick (View v){

        switch (v.getId()) {

            case R.id.txt_mon:


                if (mon) {
                    if (!dayslist.contains(1)) dayslist.add(1);
                    txt_mon.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                    lbl_mon.setVisibility(View.VISIBLE);
                    lbl_repeat.setVisibility(View.INVISIBLE);
                    txt_mon.setText("");
                    lbl_mon.setText("Mon");
                    mon = false;
                } else {
                    dayslist.remove(dayslist.indexOf(1));
                    txt_mon.setBackground(mcontext.getResources().getDrawable(R.drawable.grey_border_blue));
                    txt_mon.setTextColor(getResources().getColor(R.color.white));
                    lbl_mon.setVisibility(View.GONE);
                    txt_mon.setText("M");
                    mon = true;
                }

                break;

            case R.id.txt_tue:


                if (tue) {
                    if (!dayslist.contains(2)) dayslist.add(2);
                    txt_tue.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                    lbl_tue.setVisibility(View.VISIBLE);
                    lbl_repeat.setVisibility(View.INVISIBLE);
                    txt_tue.setText("");
                    lbl_tue.setText("Tue");
                    tue = false;
                } else {
                    dayslist.remove(dayslist.indexOf(2));
                    txt_tue.setBackground(mcontext.getResources().getDrawable(R.drawable.grey_border_blue));
                    txt_tue.setTextColor(getResources().getColor(R.color.white));
                    lbl_tue.setVisibility(View.GONE);
                    txt_tue.setText("T");
                    tue = true;
                }
                break;

            case R.id.txt_wen:


                if (wen) {
                    if (!dayslist.contains(3)) dayslist.add(3);
                    txt_wen.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                    lbl_wed.setVisibility(View.VISIBLE);
                    lbl_repeat.setVisibility(View.INVISIBLE);
                    txt_wen.setText("");
                    wen = false;
                } else {
                    dayslist.remove(dayslist.indexOf(3));
                    txt_wen.setBackground(mcontext.getResources().getDrawable(R.drawable.grey_border_blue));
                    txt_wen.setTextColor(getResources().getColor(R.color.white));
                    lbl_wed.setVisibility(View.GONE);
                    txt_wen.setText("W");
                    wen = true;
                }

                break;

            case R.id.txt_thu:

                if (thu) {
                    if (!dayslist.contains(4)) dayslist.add(4);
                    txt_thu.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                    lbl_thu.setVisibility(View.VISIBLE);
                    lbl_repeat.setVisibility(View.INVISIBLE);
                    txt_thu.setText("");
                    thu = false;
                } else {
                    dayslist.remove(dayslist.indexOf(4));
                    txt_thu.setBackground(mcontext.getResources().getDrawable(R.drawable.grey_border_blue));
                    txt_thu.setTextColor(getResources().getColor(R.color.white));
                    lbl_thu.setVisibility(View.GONE);
                    txt_thu.setText("T");
                    thu = true;
                }

                break;

            case R.id.txt_fri:
                if (fir) {
                    if (!dayslist.contains(5)) dayslist.add(5);
                    txt_fri.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                    lbl_fri.setVisibility(View.VISIBLE);
                    lbl_repeat.setVisibility(View.INVISIBLE);
                    txt_fri.setText("");
                    fir = false;
                } else {
                    dayslist.remove(dayslist.indexOf(5));
                    txt_fri.setBackground(mcontext.getResources().getDrawable(R.drawable.grey_border_blue));
                    txt_fri.setTextColor(getResources().getColor(R.color.white));
                    lbl_fri.setVisibility(View.GONE);
                    txt_fri.setText("F");
                    fir = true;
                }
                break;


            case R.id.txt_sat:
                if (sat) {
                    if (!dayslist.contains(6)) dayslist.add(6);
                    txt_sat.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                    lbl_sat.setVisibility(View.VISIBLE);
                    lbl_repeat.setVisibility(View.INVISIBLE);
                    txt_sat.setText("");

                    sat = false;
                } else {
                    dayslist.remove(dayslist.indexOf(6));
                    txt_sat.setBackground(mcontext.getResources().getDrawable(R.drawable.grey_border_blue));
                    txt_sat.setTextColor(getResources().getColor(R.color.white));
                    lbl_sat.setVisibility(View.GONE);
                    txt_sat.setText("S");
                    sat = true;
                }
                break;
            case R.id.txt_sun:
                if (sun) {
                    if (!dayslist.contains(0)) dayslist.add(0);
                    txt_sun.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                    lbl_sun.setVisibility(View.VISIBLE);
                    lbl_repeat.setVisibility(View.INVISIBLE);
                    txt_sun.setText("");
                    sun = false;
                } else {
                    dayslist.remove(dayslist.indexOf(0));
                    txt_sun.setBackground(mcontext.getResources().getDrawable(R.drawable.grey_border_blue));
                    txt_sun.setTextColor(getResources().getColor(R.color.white));
                    lbl_sun.setVisibility(View.GONE);
                    txt_sun.setText("S");
                    sun = true;
                }
                break;
        }



    }

    @OnClick(R.id.img_delete)
    public void imgOnclick ()
    {
        deleteSleepSimulation();
    }

    @Optional
    @OnClick(R.id.txt_save)
    public void txt_savedelete()
    {
        deleteSleepSimulation();
    }


    @Optional
    @OnClick(R.id.img_back)
    public void img_back()
    {

        if(App.isOrientationFlag())  ReplaceFragment.replaceFragment(Add_custmise_sleep.this,R.id.frm_main_container, new_WakeRoomLanding.newInstance());
        else  ReplaceFragment.replaceFragment(Add_custmise_sleep.this,R.id.frm_main_container, EmptySchedulesFragment.newInstance());


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)






    @OnClick(R.id.btn_save)
    public void btn_save() {

        if(App.getSocket()!=null) {
            Logs.info(TAG,"user_str_action"+str_action);
            if (!str_action.equals("delete_or_updatee")) {
                addSleepSimRequets();
            }
            else
            {
                updateSleepSimulation();
            }
        }

    }
    @OnClick(R.id.btn_cancel)
    public void btn_cancel() {
        Bundle bundle=new Bundle();
        bundle.putString("room_id",room_id);

        App.setTemp_bundle(bundle);

        Fragment mfrag= new_WakeRoomLanding.newInstance();
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(this,R.id.frm_expriencesmain_container, mfrag);
        else {
            App.setCallfrom("emptyschedule");
            ReplaceFragment.replaceFragment(Add_custmise_sleep.this, R.id.frm_main_container, ExperienceMainFragmentPortrait.newInstance());

        }

    }
    private String getDays(String intDay)
    {
        String daynm="";
        switch(intDay)
        {
            case "1":
                daynm="Mon";
                break;
            case "2":
                daynm="Tue";
                break;
            case "3":
                daynm="Wed";
                break;
            case "4":
                daynm="Thu";
                break;
            case "5":
                daynm="Fri";
                break;
            case "6":
                daynm="Sat";
                break;
            case "0":
                daynm="Sun";
                break;
        }
        return daynm;
    }





    private void addSleepSimRequets()
    {
        long id_stamp=System.currentTimeMillis();
        JSONObject jsonObject=new JSONObject();
        JSONArray daysjarray=new JSONArray();

        try {
            JSONArray jsonArray = App.getSleepSimulationData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject=jsonArray.getJSONObject(i);
                StringBuilder str_days= new StringBuilder();

                if(str_days.toString().equals("")) {
                    String time = "";
                    if (time .equals("")) {
                        try {

                            final Date dateObj = sdf.parse(jsonObject.getInt("hour") + ":" + jsonObject.getInt("minutes"));
                            time = new SimpleDateFormat("hh:mm a").format(dateObj);
                        } catch (final ParseException e) {
                            e.printStackTrace();
                        }


                    }
                    if (str_days.toString().equals("")) {
                        for (int j = 0; j < jsonObject.getJSONArray("days").length(); j++) {
                            str_days.append("  ").append(getDays(jsonObject.getJSONArray("days").getString(j)));
                        }
                    }

                    if ( str_days.toString().equals("") && jsonObject.getJSONArray("days").length()==0 ) {
                        for (int j = 0; j < jsonObject.getJSONArray("days").length(); j++) {
                            str_days.append("  ").append(getDays(jsonObject.getJSONArray("days").getString(j)));
                        }
                    }
                }

            }
        }
        catch (Exception ex){
            Log.e(TAG,"add_customise_getSimulationData: "+ex.getMessage());
        }

        /***************/
        final Bundle bundle=App.getTemp_bundle();
        try {

            JSONObject dataObject=new JSONObject();
            dataObject.put("CML_ID","sleep#"+id_stamp);
            dataObject.put("KEY_VAL","sleep#"+id_stamp);
            dataObject.put("Id","sleep#"+id_stamp);
            dataObject.put("ACTIVE_STATUS","1");
            dataObject.put("type","Sleep");

            if(bundle.containsKey("hours")){
                set_dawn_hr=bundle.getInt("hours");
                dataObject.put("hour",bundle.getInt("hours"));

            }
            else{
                set_dawn_hr=hourOfDay;
                dataObject.put("hour",hourOfDay);}


            if(bundle.containsKey("minutes")) {
                set_dawn_min=bundle.getInt("minutes");
                dataObject.put("minutes",bundle.getInt("minutes"));
            }
            else {
                set_dawn_min=minOfDay;
                dataObject.put("minutes",minOfDay);
            }



            dataObject.put("room",App.getnewDawnroom().getRoom_id());
            dataObject.put("status",true);

            if(dayslist.size()>0) {
                for (int i = 0; i < dayslist.size(); i++) {
                    int a = dayslist.get(i);
                    daysjarray.put(i, a);
                }
            }
            else {
                Calendar calendar = Calendar.getInstance();
                Calendar curr = Calendar.getInstance();
                curr.set(Calendar.HOUR_OF_DAY, set_dawn_hr);
                curr.set(Calendar.MINUTE,set_dawn_min);
                curr.set(Calendar.SECOND, 0);

                curr.get(Calendar.HOUR_OF_DAY);
                curr.get(Calendar.MINUTE);



                Date date= new Date();
                long time = date.getTime();
                Timestamp ts = new Timestamp(time);
                System.out.println("Current Time Stamp: " + ts);



                //  long timeee=curr.getTimeInMillis();
                long time1 = curr.getTimeInMillis();
                Timestamp ts1 = new Timestamp(time1);
                System.out.println("Current Time Stamp11: " + ts1);


                if(ts.compareTo(ts1)>0)
                {
                    int day = calendar.get(Calendar.DAY_OF_WEEK);
                    dataObject.put("nonRepeatingDay", day);
                }
                else {
                    int day = calendar.get(Calendar.DAY_OF_WEEK)-1;
                    dataObject.put("nonRepeatingDay", day);
                }

            }

            dataObject.put("days",daysjarray);
            JSONObject obj = new JSONObject();
            obj.put("data", dataObject);
            obj.put("type", "create_sleep_simulation");
            Log.d("sleepRequestSend",""+room_id);
            App.getSocket().emit("action", obj);
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_sleep_simulations", new JSONObject()));


        }
        catch (Exception ex){
            Log.e("SleepSimuRequestError",ex.getMessage());
        }

        final Bundle bundle1=new Bundle();
        bundle1.putString("screen","sub");

        bundle1.putString("CML_ID", room_id);
        bundle1.putString("CML_TITLE", getRoomTitle(room_id));


        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, bundle.getInt("hours"));
        c.set(Calendar.MINUTE, bundle.getInt("minutes")+15);
        c.set(Calendar.SECOND, 0);

        if(c.before(Calendar.getInstance())){
            c.add(Calendar.DAY_OF_MONTH,1);
        }

        JSONObject dataObject=new JSONObject();
        try {
            dataObject.put("hour",bundle.getInt("hours"));
            dataObject.put("minutes",bundle.getInt("minutes"));
            dataObject.put("Id","sleep#"+id_stamp);
            dataObject.put("ACTIVE_STATUS","1");
            dataObject.put("type","Sleep");
            dataObject.put("roomId",       App.getnewDawnroom().getRoom_id());
            dataObject.put("days",daysjarray);


            JSONObject obj = new JSONObject();
            obj.put("data", dataObject);
            obj.put("type", "set_notification");
            App.getSocket().emit("action", obj);
            Log.d("create_sleep_sim : ","" +obj);

        } catch (JSONException e) {
            e.printStackTrace();
        }
/****Adding Google Analytics Button Click*******/

        App.trackEvent("Add new Sleep Simulation", "btn_save_simulation", "Track OnClick Sleep");
/**********/

        Fragment mfrag= new_WakeRoomLanding.newInstance();
        mfrag.setArguments(bundle1);
        if(App.isOrientationFlag()) { ReplaceFragment.replaceFragment(Add_custmise_sleep.this,R.id.frm_expriencesmain_container, mfrag);}
        else {
            App.setCallfrom("emptyschedule");
            ReplaceFragment.replaceFragment(Add_custmise_sleep.this, R.id.frm_main_container, ExperienceMainFragmentPortrait.newInstance());

        }
    }




    private void updateSleepSimulation() {
        final Bundle bundle=App.getTemp_bundle();

        JSONArray daysjarray=new JSONArray();
        int updated_hrs=0;
        int updated_min=0;


        try {
            Log.i(TAG,"Update_Simulation_bundle"+bundle);

            dataObj=new JSONObject();
            dataObj.put("CML_ID",bundle.getString("sleepsimulation_id"));
            dataObj.put("KEY_VAL",bundle.getString("sleepsimulation_id"));
            dataObj.put("Id",bundle.getString("sleepsimulation_id"));
            dataObj.put("ACTIVE_STATUS","1");
            dataObj.put("type","Sleep");

            if(bundle.containsKey("hours")) {
                updated_hrs=bundle.getInt("hours");
                dataObj.put("hour",updated_hrs);
            }
            else {
                updated_hrs=u_hr;
                dataObj.put("hour",updated_hrs);
            }

            if(bundle.containsKey("minutes")){
                updated_min=bundle.getInt("minutes");
                dataObj.put("minutes",updated_min);
            }
            else{
                updated_min=u_min;
                dataObj.put("minutes",updated_min);
            }

            dataObj.put("room",room_id);
            dataObj.put("status",true);

            daysjarray=new JSONArray();
            if(dayslist.size()>0) {
                for (int i = 0; i < dayslist.size(); i++) {
                    int a = dayslist.get(i);
                    daysjarray.put(i, a);
                }
            }
            else {
                Calendar calendar = Calendar.getInstance();
                Calendar curr = Calendar.getInstance();
                curr.set(Calendar.HOUR_OF_DAY, set_dawn_hr);
                curr.set(Calendar.MINUTE,set_dawn_min);
                curr.set(Calendar.SECOND, 0);

                curr.get(Calendar.HOUR_OF_DAY);
                curr.get(Calendar.MINUTE);



                Date date= new Date();
                long time = date.getTime();
                Timestamp ts = new Timestamp(time);
                System.out.println("Current Time Stamp: " + ts);



                //  long timeee=curr.getTimeInMillis();
                long time1 = curr.getTimeInMillis();
                Timestamp ts1 = new Timestamp(time1);
                System.out.println("Current Time Stamp11: " + ts1);


                if(ts.compareTo(ts1)>0)
                {
                    int day = calendar.get(Calendar.DAY_OF_WEEK);
                    dataObj.put("nonRepeatingDay", day);
                }
                else {
                    int day = calendar.get(Calendar.DAY_OF_WEEK)-1;
                    dataObj.put("nonRepeatingDay", day);
                }


            }
            dataObj.put("days",daysjarray);
            removeNotification();

            JSONObject obj = new JSONObject();
            obj.put("data", dataObj);
            obj.put("type", "update_sleep_simulation");
            Logs.debug(TAG+"SimulationReqUpdated",""+obj);
            App.getSocket().emit("action", obj);

            final Bundle bundle11 =new Bundle();
            bundle11.putString("CML_ID", room_id);
            bundle11.putString("CML_TITLE", getRoomTitle(room_id));

            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, bundle.getInt("hours"));
            c.set(Calendar.MINUTE, bundle.getInt("minutes")+15);
            c.set(Calendar.SECOND, 0);

            if(c.before(Calendar.getInstance())){
                c.add(Calendar.DAY_OF_MONTH,1);
            }
            //   scheduleClient.setAlarmForNotification(c);

            JSONObject dataObject=new JSONObject();
            try {
                dataObject.put("hour",updated_hrs);
                dataObject.put("minutes",updated_min);
                dataObject.put("Id",bundle.getString("sleepsimulation_id"));
                dataObject.put("ACTIVE_STATUS","1");
                dataObject.put("type","Sleep");
                dataObject.put("roomId",room_id);
                dataObject.put("days",daysjarray);


                JSONObject objjj = new JSONObject();
                objjj.put("data", dataObject);
                objjj.put("type", "set_notification");
                App.getSocket().emit("action", objjj);
                Log.d("obj_create_dawn_sim : " ,""+objjj);

            } catch (JSONException e) {
                Logs.error(TAG,"Update_obj_error"+e.getMessage());

            }
/****Adding Google Analytics Button Click*******/

            App.trackEvent("Edit Update Sleep Simulation", "btn_save_simulation", "Track OnClick Sleep");
/**********/
            Fragment mfrag= new_WakeRoomLanding.newInstance();
            mfrag.setArguments(bundle11);
            if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(Add_custmise_sleep.this,R.id.frm_expriencesmain_container, mfrag);
            else  ReplaceFragment.replaceFragment(Add_custmise_sleep.this,R.id.frm_main_container, EmptySchedulesFragment.newInstance());


        }
        catch (Exception ex){
            Log.e("Simulation_UpdateError",ex.getMessage());
        }



    }

    private void setDays(int day_index) {


        switch (day_index) {

            case 1:

                if(!dayslist.contains(1))
                {
                    dayslist.add(1);
                    txt_mon.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                    txt_mon.setVisibility(View.VISIBLE);
                    txt_mon.setText("");
                    lbl_mon.setText("Mon");
                    lbl_mon.setVisibility(View.VISIBLE);
                    mon = false;
                    lbl_repeat.setVisibility(View.GONE);
                }


                break;

            case 2:

                if(!dayslist.contains(2)) {
                    dayslist.add(2);
                    txt_tue.setText("");
                    txt_tue.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                    txt_tue.setVisibility(View.VISIBLE);
                    tue = false;
                    lbl_tue.setVisibility(View.VISIBLE);
                    lbl_tue.setText("Tue");
                    lbl_repeat.setVisibility(View.GONE);
                }


                break;

            case 3:


                if(!dayslist.contains(3)) {
                    dayslist.add(3);
                    txt_wen.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                    txt_wen.setVisibility(View.VISIBLE);
                    txt_wen.setText("");
                    lbl_wed.setText("Wed");
                    lbl_wed.setVisibility(View.VISIBLE);
                    wen = false;
                    lbl_repeat.setVisibility(View.GONE);
                }


                break;

            case 4:

                if(!dayslist.contains(4)) {
                    dayslist.add(4);

                    txt_thu.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                    txt_thu.setVisibility(View.VISIBLE);
                    txt_thu.setText("");
                    lbl_thu.setText("Thu");
                    lbl_thu.setVisibility(View.VISIBLE);
                    thu = false;
                    lbl_repeat.setVisibility(View.GONE);
                }


                break;

            case 5:
                if(!dayslist.contains(5)) {
                    dayslist.add(5);
                    txt_fri.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                    txt_fri.setVisibility(View.VISIBLE);
                    txt_fri.setText("");
                    lbl_fri.setVisibility(View.VISIBLE);
                    lbl_fri.setText("Fri");
                    fir = false;
                    lbl_repeat.setVisibility(View.GONE);
                }
                break;


            case 6:
                if(!dayslist.contains(6)) {
                    dayslist.add(6);
                    txt_sat.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                    txt_sat.setVisibility(View.VISIBLE);
                    txt_sat.setText("");
                    lbl_sat.setText("Sat");
                    sat = false;
                    lbl_sat.setVisibility(View.VISIBLE);
                    lbl_repeat.setVisibility(View.GONE);
                }

                break;
            case 0:

                if(!dayslist.contains(0)) {
                    dayslist.add(0);
                    txt_sun.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                    txt_sun.setVisibility(View.VISIBLE);
                    txt_sun.setText("");
                    lbl_sun.setText("Sun");
                    sun = false;
                    lbl_sun.setVisibility(View.VISIBLE);
                    lbl_repeat.setVisibility(View.GONE);
                }

                break;

        }

    }

    public static String getRoomTitle(String room_id)
    {
        String room_title="";
        try {


            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("CML_ID").equals(room_id)) {
                    room_title = jsonObject.getString("CML_TITLE");
                }
            }
        }
        catch (Exception ex){Log.e("Error_getRoomTitle",ex.getMessage());}
        return room_title;
    }

    private void deleteSleepSimulation()
    {
        App.setCallfrom("emptyschedule");


        try {
            JSONArray jsonArray = App.getSleepSimulationData().getJSONArray("data");
            JSONObject resultedJsonObject=null;
            for (int i = 0; i <jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_ID").equals(sleep_id))
                {
                    resultedJsonObject=jsonObject;
                    if(resultedJsonObject.has("status")==true)
                    {
                        resultedJsonObject.put("status",false);
                    }
                }
            }
            removeNotification();
            Logs.debug(TAG+"delete_sleep_simulation",""+resultedJsonObject);
            if(App.getSocket()!=null)   App.getSocket().emit("action", JsonObjectCreater.getJsonObject("delete_sleep_simulation",resultedJsonObject));
            NotificationManager nMgr = (NotificationManager) mcontext.getSystemService(ns);
            nMgr.cancel(NotifyService_sleep.sleep_NOTIFICATION_id);

        }
        catch (Exception ex){
            Logs.error(TAG,"SimulationError_Delete_getSimulationData"+ex.getMessage());}

        Bundle bundle1=new Bundle();
        bundle1.putString("screen","sub");

        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(Add_custmise_sleep.this,R.id.frm_expriencesmain_container, new_WakeRoomLanding.newInstance());
        else  ReplaceFragment.replaceFragment(Add_custmise_sleep.this,R.id.frm_main_container, ExperienceMainFragmentPortrait.newInstance());

    }

    private void daysadded()
    {
        Integer non_day;
        boolean day_flag[]={false,false,false,false,false,false,false};
        TextView day_view[]={txt_mon,txt_tue,txt_wen,txt_thu,txt_fri,txt_sat,txt_sun};
        JSONArray jsonArray = null;
        try {
            jsonArray = App.getSleepSimulationData().getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);


                if (jsonObject.has("nonRepeatingDay")) {
                    non_day = jsonObject.getInt("nonRepeatingDay")-1;
                    Logs.info(TAG, "NonReapting_jsonDays" + non_day);
                    day_view[non_day].setBackground(mcontext.getResources().getDrawable(R.drawable.grey_border_blue));
                    day_view[non_day].setAlpha(0.5f);
                    day_view[non_day].setTextColor(getResources().getColor(R.color.background));
                    day_view[non_day].setClickable(false);
                }


                // JSONObject jsonObject = jsonArray.getJSONObject(i);
                else {
                    JSONArray jsonArray1 = jsonObject.getJSONArray("days");


                    for (int j = 0; j < jsonArray1.length(); j++) {

                        //setDays(Integer.parseInt(jsonObject.getJSONArray("days").getString(j)));
                        for (int k = 0; k < 7; k++) {
                            if (jsonArray1.getInt(j) == k + 1) {
                                day_flag[k] = true;
                            }

                        }
                    }
                }
            }

            for(int i=0;i<7;i++)
            {
                if(day_flag[i])
                {
                    if(str_action.equals("delete_or_updatee") && current_day_flag[i])
                    {

                        day_view[i].setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                        day_view[i].setAlpha(1f);
                        day_view[i].setTextColor(getResources().getColor(R.color.transparent));
                        day_view[i].setClickable(true);
                    }
                    else {


                        day_view[i].setBackground(mcontext.getResources().getDrawable(R.drawable.grey_border_blue));
                        day_view[i].setAlpha(0.5f);
                        day_view[i].setTextColor(getResources().getColor(R.color.background));
                        day_view[i].setClickable(false);
                    }
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void removeNotification() {

        AlarmManager alarmManager = (AlarmManager) mcontext.getSystemService(Context.ALARM_SERVICE);
        NotificationManager nMgr = (NotificationManager) mcontext.getSystemService(ns);
        Log.i("DeleteBtnSleepID",""+sleep_id);
        Log.i("DeleteBtnSleepIDBundle",""+bundle.getString("sleepsimulation_id"));
        JSONArray notif_day = new JSONArray();
        try {

            JSONArray jsonArray = App.getSleepSimulationData().getJSONArray("data");
            for (int i = 0; i <jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_ID").equals(sleep_id))
                {

                    notif_day=getNotifDay(sleep_id);

                }
            }

            Log.e("delete_Sleep_simulation",""+sleep_id);



            for(int day=0;day<notif_day.length();day++){


                int notif_day_int=0;

                notif_day_int=notif_day.getInt(day);
                int notif_day_name=notif_day_int+1;


                int dawn_id1 =new PrefManager(mcontext).getValueDawn(sleep_id + "C"+""+notif_day_name);

                Log.i("Delete*******Sleep+15  ",""+dawn_id1);

                nMgr.cancel(dawn_id1);
                Intent myIntent = new Intent(mcontext, NotifyService_sleep.class);

                PendingIntent pendingIntent = PendingIntent.getService(mcontext, dawn_id1, myIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
//                Log.i("PendingIntent-30",""+pendingIntent.describeContents());

                alarmManager.cancel(pendingIntent);

                new PrefManager(mcontext).removeKey(sleep_id + "C"+""+notif_day_name);


            }


        }catch (Exception ex){
            Log.e("eeeeerrrrrrrrr",""+ex.getMessage());
        }



    }

    // get Dawn Days
    private JSONArray getNotifDay(String dawn_id) {

        JSONArray notif_day = new JSONArray();



        try {

            if (App.getSleepSimulationData() != null && !dawn_id.equals("")) {
                JSONArray jsonArrayy = App.getSleepSimulationData().getJSONArray("data");
                Log.i("jsonArayy", "" + jsonArrayy);

                for (int i = 0; i < jsonArrayy.length(); i++) {
                    JSONObject jsonObject = jsonArrayy.getJSONObject(i);


                    if (jsonObject.getString("CML_ID").equals(dawn_id)) {
                        //  dawn_obj = jsonObject;
                        notif_day=jsonObject.getJSONArray("days");
                    }
                }
            }
        }
        catch(Exception e){
            Log.e("error_mainAity_DayArray",""+e.getMessage());
        }
        return notif_day;
    }


    private String getCurrentTime() {
        String str_time = "";
        try {

            String strDefaultTimeZone = App.getHubInfo().getJSONArray("data").getJSONObject(0).getString("CML_TIMEZONE");
            TimeZone tz = TimeZone.getTimeZone(strDefaultTimeZone);
            Calendar expdate = Calendar.getInstance(tz);


            String time;
            String hr, min;


            if (expdate.get(Calendar.HOUR_OF_DAY) < 9) hr = "0" + expdate.get(Calendar.HOUR_OF_DAY);
            else hr = "" + expdate.get(Calendar.HOUR_OF_DAY);

            if (expdate.get(Calendar.MINUTE) < 9) min = "0" + expdate.get(Calendar.MINUTE);
            else min = "" + expdate.get(Calendar.MINUTE);

            if (new PrefManager(mcontext).getValue("time_fomat").equals("1")) {
                time = hr + ":" + min;
            } else {
                time = TimeFormatChange.convert12(hr + ":" + min);
            }

            showTime.setText("" + time.toLowerCase());
            Current_time=time;

        } catch (Exception ex) {
            Logs.error(TAG,"setTimeError"+ ex.getMessage());
        }
        return str_time;


    }

    private void setCurrentDay() {
        Integer non_day;
        JSONArray jsonArray;
        TextView day_view[]={txt_mon,txt_tue,txt_wen,txt_thu,txt_fri,txt_sat,txt_sun};

        try {
            jsonArray = App.getSimulationData().getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);


                if (jsonObject.has("nonRepeatingDay")) {
                    non_day = jsonObject.getInt("nonRepeatingDay")-1;
                    day_view[non_day].setBackground(mcontext.getResources().getDrawable(R.drawable.grey_border_blue));
                    day_view[non_day].setAlpha(0.5f);
                    day_view[non_day].setTextColor(getResources().getColor(R.color.background));
                    day_view[non_day].setClickable(false);
                }

            }

        } catch (JSONException e) {
            Log.e("Error_in setCurrentDay",""+e.getMessage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!App.isOrientationFlag()) ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (!App.isOrientationFlag()) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
    }


    private void setDaysOriginalState()
    {
        dayslist.clear();
        int day_id[]={R.id.txt_mon, R.id.txt_tue, R.id.txt_wen, R.id.txt_thu, R.id.txt_fri, R.id.txt_sat, R.id.txt_sun};
        int day_lable[]={R.id.lbl_mon, R.id.lbl_tue, R.id.lbl_wed, R.id.lbl_thu, R.id.lbl_fri, R.id.lbl_sat, R.id.lbl_sun};
        for(int i=0;i<day_id.length;i++)
        {
            TextView txt_day= v.findViewById(day_id[i]);
            TextView lbl_day= v.findViewById(day_lable[i]);
            txt_day.setBackground(mcontext.getResources().getDrawable(R.drawable.grey_border_blue));
            txt_day.setTextColor(getResources().getColor(R.color.white));
            lbl_day.setVisibility(View.GONE);
        }
    }
    private void sendScreenImageName() {
        String name = "Add Sleep Fragment";
        String username=new PrefManager(mcontext).getValue("User_Id");
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