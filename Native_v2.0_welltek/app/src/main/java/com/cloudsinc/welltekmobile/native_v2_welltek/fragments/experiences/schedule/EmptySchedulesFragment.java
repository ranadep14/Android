package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.CustomSleepSimListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.CustomWakeSimListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.NonSwipeableViewPager;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.ScheduleSliderAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.ExperienceMainFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.ExperienceMainFragmentPortrait;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Room;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Simulations;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Sleepsim;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.TimeFormatChange;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by Jaid Shaikh on 10/11/17.
 * purpose : Displaying day wise simulations
 **/

public class EmptySchedulesFragment extends Fragment {


    private View lyt;
    private Tracker mTracker;

    public static EmptySchedulesFragment newInstance() {
        return new EmptySchedulesFragment();
    }
    @BindView(R.id.txt_room_title)TextView txt_room_title;
    @BindView(R.id.txt_help1)TextView txt_help1;
    @BindView(R.id.txt_help2)TextView txt_help2;
    @BindView(R.id.btn_add_wake)Button btn_add_wake;
    @BindView(R.id.btn_add_sleep)Button btn_add_sleep;
    @BindView(R.id.lyt_dawn_added)LinearLayout lyt_dawn_added;

    private View v;
    Button b1;
    String current_day="";
    Context mcontext;
    Boolean flag_status;
    private ArrayList<Simulations> simulationsArrayList=new ArrayList<>();
    private ArrayList<Sleepsim> sleepArrayList=new ArrayList<>();


    private Observable<String> mobservable;
    private Observer<String> myObserver;
    @BindView(R.id.lst_sim)ListView lst_sim;
    @BindView(R.id.lst_sleep)ListView lst_sleep;
    @BindView(R.id.ic_back)ImageView ic_back;
    @BindView(R.id.sleep_circle)ImageView sleep_circle;
    @BindView(R.id.wake_circle)ImageView wake_circle;

    NonSwipeableViewPager viewPager;
    ScheduleSliderAdapter scheduleSliderAdapter;
    Button previousDayNav,nextDayNav,todayNav;
    @Nullable
    @BindView(R.id.txt_save)TextView txt_save;
    @Nullable @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @Nullable @BindView(R.id.img_back)ImageView img_back;

    ArrayList<String> Days=new ArrayList<>();
    String room_title="";
    private  String room_id="";
    String room_id_new="";

    /***********/

    Room room;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_empty_schedules, container, false);
        mcontext = v.getContext();
        ButterKnife.bind(this, v);

        Days.add("Sunday");
        Days.add("Monday");
        Days.add("Tuesday");
        Days.add("Wednesday");
        Days.add("Thursday");
        Days.add("Friday");
        Days.add("Saturday");


        lyt= v.findViewById(R.id.lytmainfont);

        final Typeface mFont = Typeface.createFromAsset(v.getContext().getAssets(),
                "fonts/SF-UI-Text-Regular.otf");
        final ViewGroup mContainer = (ViewGroup) lyt.getRootView();

        FontUtility.setAppFont(mContainer, mFont);
        if (!checkConfiguration()) {
            Logs.info("EmptyScheduleFragment","Error in Mobile Activites Fragment");
        }
        App application = (App)getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();

        room=App.getRoom();
        room_id= App.getTemp_bundle().getString("room_id");
        room_title=getRoomTitle(App.getTemp_bundle().getString("room_id"));
        txt_room_title.setText(room_title);


            /*New_RoomDawn room = new New_RoomDawn();
            room.setRoom_id(App.getTemp_bundle().getString("room_id"));
*/


        if(App.getSocket()!=null)
        {
            try {
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_dawn_simulations", new JSONObject().put("roomId",room_id)));
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_sleep_simulations", new JSONObject().put("roomId",room_id)));
            } catch (JSONException e) {
                //Toast.makeText(getActivity(), "Error in request", Toast.LENGTH_SHORT).show();
            }
        }

        viewPager= v.findViewById(R.id.scheduleViewPager);
        scheduleSliderAdapter=new ScheduleSliderAdapter(getActivity().getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(6);
        viewPager.setAdapter(scheduleSliderAdapter);

        previousDayNav = v.findViewById(R.id.left_nav);
        todayNav = v.findViewById(R.id.today);
        nextDayNav = v.findViewById(R.id.right_nav);



        Calendar tempdateNV = Calendar.getInstance();
        tempdateNV.add(Calendar.DAY_OF_YEAR,+1);
        Date dateNV=tempdateNV.getTime();
        String dayN=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dateNV.getTime());
        nextDayNav.setText(dayN);


        Calendar tempdateP = Calendar.getInstance();
        tempdateP.add(Calendar.DAY_OF_YEAR, -1);
        Date dateP=tempdateP.getTime();
        String dayP=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dateP.getTime());
        previousDayNav.setText(dayP);

        //  previousDayNav.setVisibility(View.GONE);

        previousDayNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tab = viewPager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    viewPager.setCurrentItem(tab,true);
                } else if (tab == 0) {
                    viewPager.setCurrentItem(tab,true);
                }
            }
        });

        nextDayNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tab = viewPager.getCurrentItem();
                tab++;
                viewPager.setCurrentItem(tab,true);
            }
        });



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Calendar tempdate = Calendar.getInstance();
                Date date=tempdate.getTime();
                String day=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
                updatePreviousButtonName(position-1,day);
                updateTodayButtonName(position,day);
                updateNextButtonName(position+1,day);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        setSubcriber();

        return v;
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


                Log.e("Current_day",current_day);
                if(current_day.equals("Today"))
                {
                    Calendar tempdateN = Calendar.getInstance();
                    Date dateN=tempdateN.getTime();
                    current_day=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dateN.getTime());
                }

                setSimulations();


            }
        };

        Log.e("IMINSub", "Dawn Added");
        Subcriber s = new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        // App.addSubcriber(s);
        App.setCurrentSubcriber(s);

        //  }


    }




    public void setSimulations()
    {

        int day_index=0;
        if(todayNav.getText().equals("Today"))
        {
            Calendar tempdateN = Calendar.getInstance();
            Date dateN=tempdateN.getTime();
            current_day=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dateN.getTime());
            day_index = Days.indexOf("" + current_day);
        }
        else {
            day_index = Days.indexOf("" + todayNav.getText());

        }


        Log.e("day_index",""+day_index);
        try {
            ArrayList<Simulations> sim_arr=getSimulatuonData(day_index);
            ArrayList<Sleepsim> sleep_arr=getSleepData(day_index);
            CustomWakeSimListAdapter customSimListAdapter= new CustomWakeSimListAdapter(EmptySchedulesFragment.this,mcontext, sim_arr, EmptySchedulesFragment.newInstance());
            CustomSleepSimListAdapter customSleepListAdapter = new CustomSleepSimListAdapter(EmptySchedulesFragment.this,mcontext, sleep_arr, EmptySchedulesFragment.this);

            if(sim_arr.size()>0) {
                if( sim_arr==getSimulatuonData(day_index)) {

                    txt_help1.setVisibility(View.VISIBLE);
                    txt_help1.setText("I can help you get a better night \n sleep with a Sleep Routine.");
                    txt_help2.setVisibility(View.GONE);
                }
                if(!App.isOrientationFlag()) wake_circle.setVisibility(View.VISIBLE);
                //    lyt_dawn_added.setVisibility(View.VISIBLE);
                lst_sim.setVisibility(View.VISIBLE);
                lst_sim.setEnabled(false);

                lst_sim.setVerticalScrollBarEnabled(false);
                lst_sim.setAdapter(customSimListAdapter);
                btn_add_wake.setVisibility(View.GONE);
                lst_sim.setScrollContainer(false);

            }
            else
            {
                if(!App.isOrientationFlag())lst_sim.setVisibility(View.GONE);
                wake_circle.setVisibility(View.INVISIBLE);
                lst_sim.setAdapter(null);
                btn_add_wake.setVisibility(View.VISIBLE);

            }


            System.out.println("******************"+sleep_arr.size());
            if(sleep_arr.size()>0) {
                if (sleep_arr==getSleepData(day_index)) {
                    txt_help1.setVisibility(View.VISIBLE);
                    txt_help1.setText("It looks like you don't have any \n  experiences scheduled.");
                    txt_help2.setVisibility(View.VISIBLE);
                }

                if(!App.isOrientationFlag()) sleep_circle.setVisibility(View.VISIBLE);
                ///  lyt_dawn_added.setVisibility(View.VISIBLE);
                lst_sleep.setVisibility(View.VISIBLE);
                //lst_sleep.setOnClickListener(null);
                lst_sleep.setEnabled(false);
                lst_sleep.setVerticalScrollBarEnabled(false);
                lst_sleep.setAdapter(customSleepListAdapter);
                btn_add_sleep.setVisibility(View.GONE);
                lst_sleep.setScrollContainer(false);

            }
            else
            {

                sleep_circle.setVisibility(View.INVISIBLE);
                lst_sleep.setAdapter(null);
                btn_add_sleep.setVisibility(View.VISIBLE);
            }

            if(sim_arr.size()>0 && sleep_arr.size()>0)
            {
                if(!App.isOrientationFlag()) {
                    sleep_circle.setVisibility(View.VISIBLE);
                    wake_circle.setVisibility(View.VISIBLE);
                }
                txt_help1.setVisibility(View.GONE);
                txt_help2.setVisibility(View.GONE);
            }

            else {
                if(sim_arr.size()==0 && sleep_arr.size()==0){
                    txt_help1.setVisibility(View.VISIBLE);
                    txt_help1.setText("It looks like you don't have any \n  experiences scheduled.");

                    txt_help2.setVisibility(View.VISIBLE);
                }
            }

        }
        catch (Exception ex){
            Log.e("Error",""+ex.getMessage());
        }
    }

    private ArrayList<Sleepsim> getSleepData(int day_index) {
        sleepArrayList.clear();
        try {
            JSONArray jsonArray = App.getSleepSimulationData().getJSONArray("data");

            if(jsonArray.length()>0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);


                    Sleepsim simulations = new Sleepsim();
                    String str_days = "";


                    boolean day_flag = false;
                    if (jsonObject.getJSONArray("days").length() > 0) {

                        for (int j = 0; j < jsonObject.getJSONArray("days").length(); j++) {
                            str_days = str_days + "  " + getDays(jsonObject.getJSONArray("days").getString(j));

                            Log.e("day_values", "" + jsonObject.getJSONArray("days").getInt(j));
                            if (jsonObject.getJSONArray("days").getInt(j) == day_index) {
                                day_flag = true;
                            }
                        }
                    }
                    if (jsonObject.has("nonRepeatingDay")) {

                        if (jsonObject.getInt("nonRepeatingDay") == day_index) {
                            day_flag = true;
                            str_days = str_days + "  " + getDays("" + day_index);

                        }
                    }

                    if (day_flag) {


                        simulations.set_days(str_days);
                        String time = "";


                        if (new PrefManager(mcontext).getValue("time_fomat").equals("1")) {
                            time = (jsonObject.getInt("hour") < 10 ? "0" + jsonObject.getInt("hour") : jsonObject.getInt("hour")) + ":" + (jsonObject.getInt("minutes") < 10 ? "0" + jsonObject.getInt("minutes") : jsonObject.getInt("minutes"));
                        } else {
                            time = TimeFormatChange.convert12(jsonObject.getInt("hour") + ":" + jsonObject.getInt("minutes"));
                        }

                        simulations.set_time(time);
                        simulations.set_sim_id(jsonObject.getString("CML_ID"));
                        simulations.set_switch_state(jsonObject.getBoolean("status"));
                        flag_status = jsonObject.getBoolean("status");
                        sleepArrayList.add(simulations);

                    }
                }
            }

        }
        catch (Exception ex){
            Log.e("jsonerror","------------------------"+ex.getMessage());
        }
        return sleepArrayList;
    }

    private ArrayList<Simulations> getSimulatuonData(int day_index)
    {

        simulationsArrayList.clear();
        try {
            JSONArray jsonArray = App.getSimulationData().getJSONArray("data");
            Log.e("getAllSimulationData",""+jsonArray);


            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Log.e("inside for loop", "" + jsonObject);
                Simulations simulations = new Simulations();
                String str_days = "";


                boolean day_flag=false;

                for (int j = 0; j < jsonObject.getJSONArray("days").length(); j++) {
                    //       dawn_days_sorted.add("" + jsonObject.getJSONArray("days").getInt(j));
                    Log.e("day_values", "" + jsonObject.getJSONArray("days").getInt(j));
                    if(jsonObject.getJSONArray("days").length()>0) {
                        if (jsonObject.getJSONArray("days").getInt(j) == day_index) {
                            day_flag = true;
                        }
                    }
                    str_days = str_days + "  " + getDays(jsonObject.getJSONArray("days").getString(j));
                }


                if(jsonObject.has("nonRepeatingDay")) {
                    if (jsonObject.getInt("nonRepeatingDay") == day_index) {
                        day_flag = true;
                        str_days = str_days + "  " + getDays("" + day_index);

                    }
                }

                if(day_flag) {
                    Log.e("iojoijojoi", "" + jsonObject);
                    simulations.set_days(str_days);
                    String time = "";

                    Log.e("ssssssssssssssssssss", "" + jsonArray);
                    if (new PrefManager(mcontext).getValue("time_fomat").equals("1")) {

                        time = (jsonObject.getInt("hour") < 10 ? "0" + jsonObject.getInt("hour") : jsonObject.getInt("hour")) + ":" + (jsonObject.getInt("minutes") < 10 ? "0" + jsonObject.getInt("minutes") : jsonObject.getInt("minutes"));
                    } else {
                        time = TimeFormatChange.convert12(jsonObject.getInt("hour") + ":" + jsonObject.getInt("minutes"));
                    }

                    simulations.set_time(time);
                    simulations.set_sim_id(jsonObject.getString("CML_ID"));
                    simulations.set_switch_state(jsonObject.getBoolean("status"));
                    flag_status = jsonObject.getBoolean("status");
                    simulationsArrayList.add(simulations);
                }
            }



        }
        catch (Exception ex){
            Log.e("jsonerror","------------------------"+ex.getMessage());
        }
        return simulationsArrayList;
    }

    @Optional
    @OnClick(R.id.img_back)
    public void img_back()
    {
        App.setCallfrom("emptyschedule");

        if(App.isOrientationFlag())         ReplaceFragment.replaceFragment(EmptySchedulesFragment.this,R.id.frm_main_container, ExperienceMainFragment.newInstance());
        else      ReplaceFragment.replaceFragment(EmptySchedulesFragment.this,R.id.frm_main_container, ExperienceMainFragmentPortrait.newInstance());




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
    @OnClick(R.id.btn_add_wake)
    public void btn_add_wake() {




        Bundle bundle=new Bundle();
        bundle.putString("room_id",""+room_id);


        bundle.putBoolean("has_lights",true);

        if(getCnt_blinds(room_id)>0)bundle.putBoolean("has_blinds",true);
        else bundle.putBoolean("has_blinds",false);

        if(getCnt_music(room_id)>0)bundle.putBoolean("has_music",true);
        else bundle.putBoolean("has_music",false);


        App.setTemp_bundle(bundle);
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(this,R.id.frm_expriencesmain_container, Add_custmise_wake.newInstance());
        else  ReplaceFragment.replaceFragment(this,R.id.frm_main_container, Add_custmise_wake.newInstance());


    }
    @OnClick(R.id.btn_add_sleep)
    public void btn_add_sleep() {

        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(this,R.id.frm_expriencesmain_container, Add_custmise_sleep.newInstance());
        else  ReplaceFragment.replaceFragment(this,R.id.frm_main_container, Add_custmise_sleep.newInstance());

    }
    @OnClick(R.id.ic_back)
    public void ic_back() {
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(this,R.id.frm_expriencesmain_container, new_WakeRoomLanding.newInstance());
        else  ReplaceFragment.replaceFragment(this,R.id.frm_main_container, new_WakeRoomLanding.newInstance());


    }
    /*************/


    private void updatePreviousButtonName(int position, String day) {
        previousDayNav.setVisibility(View.VISIBLE);
        Calendar tempdateP = Calendar.getInstance();
        tempdateP.add(Calendar.DAY_OF_YEAR, position);
        Date dateP=tempdateP.getTime();
        String dayP=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dateP.getTime());
        Log.e("stateChange2",String.valueOf(position)+" "+dayP);
        if (position==-1){
            previousDayNav.setVisibility(View.VISIBLE);
            previousDayNav.setText(dayP);

        }else {

            previousDayNav.setText(dayP);

        }

    }

    private void updateTodayButtonName(int position, String day) {
        Calendar tempdateT = Calendar.getInstance();
        tempdateT.add(Calendar.DAY_OF_YEAR, position);
        Date dateT=tempdateT.getTime();
        String dayT=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dateT.getTime());
        Log.e("stateChange2",String.valueOf(position)+" "+dayT);

        if (dayT.contentEquals(day)){
            todayNav.setText("Today");
            current_day ="Today";
        }else {
            current_day =dayT;
            todayNav.setText(dayT);

        }

        setSimulations();


    }

    private void updateNextButtonName(int position, String day) {
        nextDayNav.setVisibility(View.VISIBLE);

        Calendar tempdateN = Calendar.getInstance();
        tempdateN.add(Calendar.DAY_OF_YEAR, position);
        Date dateN=tempdateN.getTime();
        String dayN=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dateN.getTime());

        Log.e("stateChange2",String.valueOf(position)+" "+dayN);

        if (position==7){
            nextDayNav.setVisibility(View.VISIBLE);
            nextDayNav.setText(dayN);
        }else {

            {
                nextDayNav.setText(dayN);

            }
        }
        setSimulations();


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
        catch (Exception ex){Log.e("Error",ex.getMessage());}
        return room_title;
    }
    @Override
    public void onResume() {


        super.onResume();
        if(!App.isOrientationFlag()) ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        if(!App.isOrientationFlag()) {
            img_back.setImageDrawable(EmptySchedulesFragment.this.getResources().getDrawable(R.drawable.ic_left_arrow));
            //txt_fragment_title.setText(""+getRoomTitle(App.getTemp_bundle().getString("room_id")));
            txt_fragment_title.setText(""+room_title);
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        if(!App.isOrientationFlag()){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        }
    }

    public int getCnt_blinds(String room_id)
    {
        int temp=0;
        try {

            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("CML_ID").equals(room_id)) {
                    if(jsonObject.has("HAS_BLIND"))  temp = jsonObject.getInt("HAS_BLIND");
                }
            }
        }
        catch (Exception ex){Log.e("Error",ex.getMessage());}
        return temp;
    }
    public int getCnt_music(String room_id)
    {
        int temp=0;
        try {

            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("CML_ID").equals(room_id)) {
                    if(jsonObject.has("HAS_MUSIC"))  temp = jsonObject.getInt("HAS_MUSIC");
                }
            }
        }
        catch (Exception ex){Log.e("Error",ex.getMessage());}
        return temp;
    }

    private void sendScreenImageName() {
        String name = "Daywise Simulation Fragment";
        String username=new PrefManager(mcontext).getValue("User_Id");
        // [START screen_view_hit]
        Log.i("EmptySchduleFragment", "Setting screen name: " + name);
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
            Log.w("EmptySchedules Fragment", "checkConfiguration", e);
            return false;
        }

        return true;
    }
}
