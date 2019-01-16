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
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.ExperienceMainFragmentPortrait;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Room;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.NotifyService;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.ScheduleClient;
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
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import rx.Observable;
import rx.Observer;


/**
 * Created by Jaid Shaikh on 17/5/17.
 * purpose : Allowing user to add and update customise wake simulation
 */

public class Add_custmise_wake extends Fragment {

    private ScheduleClient scheduleClient;
    private View lyt;
    private Map unsortedData = new HashMap();
    private String str = "";
    private boolean isAudio_present = false;
    private boolean blinds = false;
    private boolean lights = false;
    private String ns = Context.NOTIFICATION_SERVICE;
    private int msg_id=0;
    private int set_dawn_hr=0;
    private int set_dawn_min=0;
    private String Current_time;
    private Tracker mTracker;


    private String TAG = Add_custmise_wake.this.getClass().getSimpleName();

    private View v;
    private Context mcontext;
    private static int hr, min;

    @BindView(R.id.Timepicker)
    TextView showTime;
    @BindView(R.id.btn_save)
    Button btn_save;
    private String room_id = "";
    private String soundId = "";
    private String playlistId = "";
    private ArrayList<Integer> dayslist = new ArrayList<>();
    private ArrayList<Integer> wakeelementlist = new ArrayList<>();

    @BindView(R.id.rel_track)
    LinearLayout rel_track;
    @BindView(R.id.txt_elem_audio)
    ImageView txt_elem_audio;
    @BindView(R.id.txt_elem_lights)
    ImageView txt_elem_lights;
    @BindView(R.id.txt_elem_blinds)
    ImageView txt_elem_blinds;
    @BindView(R.id.txt_elem_tracks)
    ImageView txt_elem_tracks;

    @BindView(R.id.simpleChronometer)
    Chronometer simpleChronometer;

    @BindView(R.id.txt_room_title)
    TextView txt_room_title;


    @BindView(R.id.lbl_mon)
    TextView lbl_mon;
    @BindView(R.id.lbl_tue)
    TextView lbl_tue;
    @BindView(R.id.lbl_wed)
    TextView lbl_wed;
    @BindView(R.id.lbl_thu)
    TextView lbl_thu;
    @BindView(R.id.lbl_fri)
    TextView lbl_fri;
    @BindView(R.id.lbl_sat)
    TextView lbl_sat;
    @BindView(R.id.lbl_sun)
    TextView lbl_sun;
    @BindView(R.id.lbl_repeat)
    TextView lbl_repeat;


    @BindView(R.id.txt_mon)
    TextView txt_mon;
    @BindView(R.id.txt_tue)
    TextView txt_tue;
    @BindView(R.id.txt_wen)
    TextView txt_wen;
    @BindView(R.id.txt_thu)
    TextView txt_thu;
    @BindView(R.id.txt_fri)
    TextView txt_fri;
    @BindView(R.id.txt_sat)
    TextView txt_sat;
    @BindView(R.id.txt_sun)
    TextView txt_sun;
    @BindView(R.id.img_delete)
    TextView img_delete;
    @BindView(R.id.btn_cancel)
    TextView btn_cancel;

    @BindView(R.id.txt_no_audio)
    TextView txt_no_audio;
    @BindView(R.id.txt_no_blinds)
    TextView txt_no_blinds;
    @Nullable
    @BindView(R.id.lbl_tracks)
    TextView lbl_tracks;


    private boolean tracks = true;
    private boolean sun = true;
    private boolean mon = true;
    private boolean tue = true;
    private boolean wen = true;
    private boolean thu = true;
    private boolean fir = true;
    private boolean sat = true;
    private boolean flag = false;
    private boolean selectDaysFlag = false;
    private boolean selectTimeFlag = false;
    private boolean selectRoomFlag = false;
    private boolean selectSoundFlag = false;
    private boolean selectSnoozeFlag = false;


    private boolean allowLight=false;
    private boolean allowMusic = false;
    private boolean allowBlind = false;

    private int hourOfDay = 0, minOfDay = 0, u_hr = 0, u_min = 0;
    private String str_action = "";
    private String sim_id = "";
    private final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    private boolean current_day_flag[] = {false, false, false, false, false, false, false};
    private Bundle bundle;
    private PrefManager prefManager;
    @Nullable
    @BindView(R.id.txt_save)
    TextView txt_save;
    @Nullable
    @BindView(R.id.txt_fragment_title)
    TextView txt_fragment_title;
    @Nullable
    @BindView(R.id.img_back)
    ImageView img_back;

    public static Add_custmise_wake newInstance() {
        return new Add_custmise_wake();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_customize_wake, container, false);
        mcontext = v.getContext();
        ButterKnife.bind(this, v);
        setDaysOriginalState();
        lyt = v.findViewById(R.id.lytmainfont);

        final Typeface mFont = Typeface.createFromAsset(v.getContext().getAssets(),
                "fonts/SF-UI-Text-Regular.otf");
        final ViewGroup mContainer = (ViewGroup) lyt.getRootView();
        // pref=mcontext.getSharedPreferences("Dawn_notify",0);
        prefManager=new PrefManager(mcontext);
        if (!checkConfiguration()) {
            Logs.info(TAG,"Error in FilterFragment");
        }
        App application = (App)getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();

        Calendar cal = Calendar.getInstance();

        hourOfDay = cal.get(Calendar.HOUR);
        minOfDay = cal.get(Calendar.MINUTE);

        try {

            room_id = App.getTemp_bundle().getString("room_id");
            Room room=new Room();
            room.setRoom_id(room_id);
            App.setRoom(room);
            Logs.info(TAG, "create_dawn_Room_id" + room_id);

        }catch (NullPointerException ex){
            Logs.error(TAG,"NullPointer_Error_in_bundle_roomId"+ex.getMessage());
        }
        catch (Exception ex){
            Logs.error(TAG,"Error_in_bundle_roomId"+ex.getMessage());
        }
        txt_room_title.setText("Wake Time " + "" + getRoomTitle(App.getTemp_bundle().getString("room_id")));
        getCurrentTime();

        scheduleClient = new ScheduleClient(mcontext);
        scheduleClient.doBindService();

        bundle = App.getTemp_bundle();
        Logs.info(TAG, "bundle_on_create: " + bundle);

        if (bundle.containsKey("has_music")) {

            if (bundle.getBoolean("has_music")) {
                txt_elem_audio.setClickable(true);
                txt_elem_audio.setEnabled(true);
                isAudio_present = true;
                allowMusic = true;
                txt_elem_audio.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                rel_track.setVisibility(View.VISIBLE);
                txt_elem_tracks.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));

            } else {
                txt_elem_audio.setClickable(false);
                txt_elem_audio.setEnabled(false);
                allowMusic = false;
                if(App.isOrientationFlag()){
                    txt_elem_audio.setVisibility(View.GONE);
                    txt_no_audio.setVisibility(View.VISIBLE);
                    txt_no_audio.setClickable(false);   }
                else {

                    txt_elem_audio.setBackground(mcontext.getResources().getDrawable(R.drawable.audio_new));

                }
                txt_elem_tracks.setVisibility(View.GONE);

                lbl_tracks.setVisibility(View.GONE);
                rel_track.setVisibility(View.GONE);


            }
        }

        if (bundle.containsKey("has_music_set")) {

            if (bundle.getBoolean("has_music_set")) {
                txt_elem_audio.setClickable(true);
                txt_elem_audio.setEnabled(true);
                isAudio_present = true;
                allowMusic = true;

                txt_elem_audio.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                rel_track.setVisibility(View.VISIBLE);
                txt_elem_tracks.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));

            } else {

                allowMusic = false;
                txt_elem_audio.setBackground(mcontext.getResources().getDrawable(R.drawable.audio_new));

                rel_track.setVisibility(View.GONE);
            }
        }
        if (bundle.containsKey("has_blinds")) {

            if (bundle.getBoolean("has_blinds")) {
                txt_elem_blinds.setClickable(true);
                txt_elem_blinds.setEnabled(true);
                blinds = true;
                allowBlind = true;
                txt_elem_blinds.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));

            } else {
                allowBlind = false;
                txt_elem_blinds.setClickable(false);
                txt_elem_blinds.setEnabled(false);
                txt_elem_blinds.setVisibility(View.VISIBLE);
                txt_no_blinds.setClickable(false);
                //     txt_elem_blinds.setBackground(mcontext.getResources().getDrawable(R.drawable.blinds_new));
                if (App.isOrientationFlag()) {
                    txt_elem_blinds.setVisibility(View.GONE);
                    txt_no_blinds.setVisibility(View.VISIBLE);
                    txt_no_blinds.setClickable(false);
                } else {
                    txt_elem_blinds.setVisibility(View.VISIBLE);
                    txt_elem_blinds.setBackground(mcontext.getResources().getDrawable(R.drawable.blinds_new));

                }

            }
            if (bundle.containsKey("has_lights")) {

                if (bundle.getBoolean("has_lights")) {
                    txt_elem_lights.setClickable(true);
                    txt_elem_lights.setEnabled(true);
                    lights = true;
                    allowLight = true;
                    txt_elem_lights.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                    Log.i("jjjjjjjjjjjjjjjjjj","onCreate_has_lights");


                } else {
                    allowLight = false;
                    txt_elem_lights.setClickable(false);
                    txt_elem_lights.setEnabled(false);

                    txt_elem_lights.setVisibility(View.VISIBLE);
                    if (App.isOrientationFlag()) {
                        txt_elem_lights.setVisibility(View.VISIBLE);
                        txt_elem_lights.setBackground(mcontext.getResources().getDrawable(R.drawable.light_new));

                    } else {
                        txt_elem_lights.setVisibility(View.VISIBLE);
                        txt_elem_lights.setBackground(mcontext.getResources().getDrawable(R.drawable.light_new));

                    }

                }
            }


        }

        if (bundle.containsKey("has_light_set")) {
            if (bundle.getBoolean("has_light_set")) {
                txt_elem_lights.setClickable(true);
                txt_elem_lights.setEnabled(true);
                lights = true;
                allowLight = true;
                txt_elem_lights.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                Log.i("jjjjjjjjjjjjjjjjjj","has_light_set");


            } else {
                allowLight = false;
                txt_elem_lights.setBackground(mcontext.getResources().getDrawable(R.drawable.light_new));
            }

        }


        if (bundle.containsKey("has_blinds_set")) {
            if (bundle.getBoolean("has_blinds_set")) {
                txt_elem_blinds.setClickable(true);
                txt_elem_blinds.setEnabled(true);
                blinds = true;
                allowBlind = true;
                txt_elem_blinds.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));


            } else {
                allowBlind = false;
                txt_elem_blinds.setBackground(mcontext.getResources().getDrawable(R.drawable.blinds_new));
            }

        }


        if (bundle.containsKey("has_track")) {
            tracks = true;
            if (bundle.getBoolean("has_track")) {
                txt_elem_tracks.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                rel_track.setVisibility(View.VISIBLE);
            } else {
                rel_track.setVisibility(View.GONE);
                txt_elem_tracks.setBackground(mcontext.getResources().getDrawable(R.drawable.track_new));
            }
        }
        if ((bundle.containsKey("simulation_id") && (bundle.containsKey("Callfrom")))&&!bundle.containsKey("days")) {
            if (bundle.getString("Callfrom").equals("edit")) {
                flag = true;
                sim_id = bundle.getString("simulation_id");


                if (bundle.containsKey("has_lights") && bundle.getBoolean("has_lights")) {
                    lights = true;
                    allowLight = true;
                }
                else{
                    allowLight = false;
                }
                if (bundle.containsKey("has_music") && bundle.getBoolean("has_music")) {
                    isAudio_present = true;
                    allowMusic = true;
                }
                else{
                    allowMusic = false;
                }

                if (bundle.containsKey("has_blinds") && bundle.getBoolean("has_blinds")) {
                    blinds = true;
                    allowBlind = true;
                }
                else{
                    allowBlind=false;
                }
                setAllValuesOfSimulation(sim_id);

            }
        }
        if (bundle.containsKey("update")) {
            str_action = "delete_or_update";
            flag = true;
            sim_id = bundle.getString("simulation_id");
            if (bundle.containsKey("has_lights") && bundle.getBoolean("has_lights")) {
                lights = true;
                allowLight = true;

            }
            if (bundle.containsKey("has_music") && bundle.getBoolean("has_music")) {
                isAudio_present = true;
                allowMusic = true;
            }
            if (bundle.containsKey("has_blinds") && bundle.getBoolean("has_blinds")) {
                blinds = true;
                allowBlind = true;
            }


            if (App.isOrientationFlag()) {
                img_delete.setVisibility(View.VISIBLE);
            } else {

                txt_save.setVisibility(View.VISIBLE);
                txt_save.setText("Delete");
                txt_save.setTextColor(Color.RED);
                img_delete.setVisibility(View.GONE);
                img_back.setImageDrawable(Add_custmise_wake.this.getResources().getDrawable(R.drawable.ic_left_arrow));
                txt_fragment_title.setText("Edit Wake");
            }

            btn_cancel.setVisibility(View.VISIBLE);
            lbl_repeat.setVisibility(View.GONE);
            sim_id = bundle.getString("simulation_id");

        } else {
            if (!App.isOrientationFlag()) {
                img_back.setImageDrawable(Add_custmise_wake.this.getResources().getDrawable(R.drawable.ic_left_arrow));
                txt_fragment_title.setText("Set Wake");
            }
        }

        if (bundle.containsKey("hours") && bundle.containsKey("minutes")) {
            hr = bundle.getInt("hours");
            min = bundle.getInt("minutes");
            if (new PrefManager(mcontext).getValue("time_fomat").equals("1")) {
                showTime.setText((hr < 10 ? "0" + hr : hr) + ":" + (min < 10 ? "0" + min: min));
            } else {
                showTime.setText(TimeFormatChange.convert12(hr + ":" + min).toLowerCase());
            }

        }


        if (bundle.containsKey("elements")) {


            wakeelementlist.clear();

            for (int i = 0; i < bundle.getIntegerArrayList("elements").size(); i++) {
                setElements(bundle.getIntegerArrayList("elements").get(i));
            }
        }
        if (bundle.containsKey("days")) {

            if (bundle.getIntegerArrayList("days").size() > 0) {
                selectDaysFlag = true;
                setSaveButton();
            } else {
                selectDaysFlag = false;
                setSaveButton();
            }
            dayslist.clear();

            for (int i = 0; i < bundle.getIntegerArrayList("days").size(); i++) {
                setDays(bundle.getIntegerArrayList("days").get(i));
            }
        }
        if (bundle.containsKey("soundId")) {
            soundId = bundle.getString("soundId");
            Logs.info(TAG, "Bundle_soundId: " + bundle.getString("soundId"));
        }
        if (bundle.containsKey("playlistId")) {
            playlistId = bundle.getString("playlistId");
            Logs.info(TAG, "Bundle_playlist_id" + bundle.getString("soundId"));
        }
        // onNewIntent(getActivity().getIntent());
        if (!str_action.equals("delete_or_update"))daysadded();



        setSaveGrau();

        return v;

    }


    private void setSaveGrau()
    {
        if(allowLight || allowBlind || allowMusic) {
            btn_save.setEnabled(true);
            btn_save.setClickable(true);
            btn_save.setAlpha(1);

        }
        else {
            btn_save.setEnabled(false);
            btn_save.setClickable(false);
            btn_save.setAlpha(0.5f);

        }
    }

    private void setElements(Integer elements) {
        switch (elements){
            case 1:

                wakeelementlist.add(1);
                txt_elem_lights.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                Log.i("jjjjjjjjjjjjjjjjjj","setElement(1)");

                allowLight=true;
                lights=false;
                break;
            case 2:

                wakeelementlist.add(2);
                txt_elem_blinds.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));

                allowBlind=true;
                blinds=false;

                break;
            case 3:

                wakeelementlist.add(3);
                txt_elem_audio.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                rel_track.setVisibility(View.VISIBLE);
                isAudio_present=false;
                allowMusic=true;


                break;
            case 4:


                wakeelementlist.add(4);
                txt_elem_tracks.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                tracks = false;

                break;
        }
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


    private void setAllValuesOfSimulation(String simulation_id) {
        if (flag) {

            dayslist.clear();
            try {
                JSONArray jsonArray = App.getSimulationData().getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);


                    if (jsonObject.getString("CML_ID").equals(simulation_id)) {
                        room_id = jsonObject.getString("room");

                        if (!App.getTemp_bundle().containsKey("room_id"))
                            App.getTemp_bundle().putString("room_id", jsonObject.getString("room"));

                        wakeelementlist.clear();

                        if (lights==true && jsonObject.has("allowLight") && jsonObject.getBoolean("allowLight") && allowLight) {
                            txt_elem_lights.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                            Log.i("jjjjjjjjjjjjjjjjjj","SetAllValues");

                            allowLight = true;
                            setElements(1);

                        } else {
                            txt_elem_lights.setBackground(mcontext.getResources().getDrawable(R.drawable.light_new));
                            allowLight = false;
                        }
                        if (blinds==true && jsonObject.has("allowBlind") && jsonObject.getBoolean("allowBlind") && allowBlind) {
                            txt_elem_blinds.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));

                            allowBlind = true;
                            setElements(2);

                        } else {
                            txt_elem_blinds.setBackground(mcontext.getResources().getDrawable(R.drawable.blinds_new));
                            allowBlind = false;
                        }
                        if (isAudio_present && jsonObject.has("allowMusic") && jsonObject.getBoolean("allowMusic") && allowMusic) {

                            txt_elem_audio.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                            rel_track.setVisibility(View.VISIBLE);
                            txt_elem_tracks.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
                            allowMusic = true;
                            setElements(3);


                        }else {

/******** Change Here *******/
                            txt_elem_audio.setBackground(mcontext.getResources().getDrawable(R.drawable.audio_new));
                            rel_track.setVisibility(View.GONE);
                            allowMusic = false;
                        }




                        if (jsonObject.has("soundId")) soundId = jsonObject.getString("soundId");
                        //  else soundId="5";

                        if (soundId.equals("")) {
                            if (!App.getTemp_bundle().containsKey("default_sound") || !App.getTemp_bundle().containsKey("default_playlist")) {
                                App.getTemp_bundle().putString("default_sound", soundId);
                            }
                        }
                        if (jsonObject.has("playlistId"))
                            playlistId = jsonObject.getString("playlistId");


                        //  dayslist.clear();
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
                            setCurrentDay();
                            //  if (!str_action.equals("delete_or_update"))daysadded();

                            //daysadded();
                        }



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
            } catch (JSONException e) {
                Logs.error(TAG, "SetAllValueError" + e.getMessage());
                if (App.isOrientationFlag())
                    ReplaceFragment.replaceFragment(Add_custmise_wake.this, R.id.frm_expriencesmain_container, new_WakeRoomLanding.newInstance());
                else
                    ReplaceFragment.replaceFragment(Add_custmise_wake.this, R.id.frm_main_container, new_WakeRoomLanding.newInstance());

            }

        }


    }


    @OnClick(R.id.Timepicker)
    public void pickTime() {

        App.setTemp_bundle(bundle);
        Bundle bundle = App.getTemp_bundle();
        bundle.putIntegerArrayList("days", dayslist);
        bundle.putIntegerArrayList("elements", wakeelementlist);

        bundle.putString("time", showTime.getText().toString());
        DialogFragment dialogFragment = new TimePickerFragment();
        dialogFragment.show(getActivity().getSupportFragmentManager(), "Tp");

        Logs.info(TAG,"Bundle_elem_Time: "+bundle);


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick({R.id.txt_mon, R.id.txt_tue, R.id.txt_wen, R.id.txt_thu, R.id.txt_fri, R.id.txt_sat, R.id.txt_sun})
    public void textOnclick (View v) {
        try {
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
        catch(ArrayIndexOutOfBoundsException exception){
            Logs.error(TAG,"Add_Remove days_ArrayIndexOutOfBound"+exception.getMessage());
        }
        catch(Exception ex){
            Logs.error(TAG,"Add_Remove days_Exception"+ex.getMessage());
        }




    }

    @Optional
    @OnClick(R.id.img_back)
    public void img_back()
    {
        if(App.isOrientationFlag())  ReplaceFragment.replaceFragment(Add_custmise_wake.this,R.id.frm_main_container, new_WakeRoomLanding.newInstance());
        else  ReplaceFragment.replaceFragment(Add_custmise_wake.this,R.id.frm_main_container, EmptySchedulesFragment.newInstance());


    }


    @OnClick(R.id.img_delete)
    public void imgOnclick ()
    {
        deleteSimulation();
    }

    @Optional
    @OnClick(R.id.txt_save)
    public void txt_saveDelete ()
    {
        deleteSimulation();
    }


    private Wake_SelectSound selectSound=new Wake_SelectSound();
    private Wake_SelectSound_potrait pselectSound=new Wake_SelectSound_potrait();




    @OnClick(R.id.txt_elem_audio)
    public void imgOnclickaudio (View v)
    {

        if (!wakeelementlist.contains(3)){
            wakeelementlist.add(3);
            txt_elem_audio.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
            //    ReplaceFragment.replaceFragment(this, R.id.frm_expriencesmain_container, Wake_SelectSound.newInstance());
            rel_track.setVisibility(View.VISIBLE);
            isAudio_present=false;
            allowMusic=true;
        } else {
            wakeelementlist.remove(wakeelementlist.indexOf(3));
            txt_elem_audio.setBackground(mcontext.getResources().getDrawable(R.drawable.audio_new));
            isAudio_present = true;
            rel_track.setVisibility(View.GONE);
            allowMusic=false;
        }

        Bundle bundle=App.getTemp_bundle();
        bundle.putIntegerArrayList("days",dayslist);
        bundle.putIntegerArrayList("elements", wakeelementlist);
        bundle.putString("time",showTime.getText().toString());
        bundle.putString("default_sound", soundId);
        bundle.putBoolean("has_light_set",Boolean.valueOf(allowLight));
        bundle.putBoolean("has_music_set",Boolean.valueOf(allowMusic));
        bundle.putBoolean("has_blinds_set",Boolean.valueOf(allowBlind));

        Logs.info(TAG,"Bundle_elem_audio"+bundle);

        /*************/
        setSaveGrau();
        App.setTemp_bundle(bundle);



    }



    @OnClick(R.id.txt_elem_lights)
    public void imgOnclickLights (View v)
    {

        if (!wakeelementlist.contains(1)){
            wakeelementlist.add(1);
            txt_elem_lights.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
            //    ReplaceFragment.replaceFragment(this, R.id.frm_expriencesmain_container, Wake_SelectSound.newInstance());
            Log.i("jjjjjjjjjjjjjjjjjj","imgOnclickLights");
            allowLight=true;
            lights=false;
        } else {
            wakeelementlist.remove(wakeelementlist.indexOf(1));
            txt_elem_lights.setBackground(mcontext.getResources().getDrawable(R.drawable.light_new));
            lights = true;
            allowLight=false;

        }
        Bundle bundle=App.getTemp_bundle();
        bundle.putIntegerArrayList("days",dayslist);
        bundle.putIntegerArrayList("elements", wakeelementlist);
        bundle.putString("time",showTime.getText().toString());
        bundle.putString("default_sound", soundId);
        bundle.putBoolean("has_light_set",Boolean.valueOf(allowLight));
        bundle.putBoolean("has_music_set",Boolean.valueOf(allowMusic));
        bundle.putBoolean("has_blinds_set",Boolean.valueOf(allowBlind));

        Logs.info(TAG,"Bundle_elem_blinds"+bundle);

        setSaveGrau();
        App.setTemp_bundle(bundle);
    }

    @OnClick(R.id.txt_elem_blinds)
    public void imgOnclickblinds (View v)
    {

        if (!wakeelementlist.contains(2)){
            wakeelementlist.add(2);
            txt_elem_blinds.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
            //    ReplaceFragment.replaceFragment(this, R.id.frm_expriencesmain_container, Wake_SelectSound.newInstance());

            allowBlind=true;
            blinds=false;
        } else {
            wakeelementlist.remove(wakeelementlist.indexOf(2));
            txt_elem_blinds.setBackground(mcontext.getResources().getDrawable(R.drawable.blinds_new));
            blinds = true;
            allowBlind=false;

        }
        Bundle bundle=App.getTemp_bundle();
        bundle.putIntegerArrayList("days",dayslist);
        bundle.putIntegerArrayList("elements", wakeelementlist);
        bundle.putString("time",showTime.getText().toString());
        bundle.putString("default_sound", soundId);
        bundle.putBoolean("has_light_set",Boolean.valueOf(allowLight));
        bundle.putBoolean("has_music_set",Boolean.valueOf(allowMusic));
        bundle.putBoolean("has_blinds_set",Boolean.valueOf(allowBlind));


        Logs.info(TAG,"Bundle_elem_blinds"+bundle);
        setSaveGrau();
        App.setTemp_bundle(bundle);
    }

    @OnClick(R.id.txt_elem_tracks)
    public void imgOnclick (View v)
    {

        if (!wakeelementlist.contains(4)){
            wakeelementlist.add(4);
            txt_elem_tracks.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
            //    ReplaceFragment.replaceFragment(this, R.id.frm_expriencesmain_container, Wake_SelectSound.newInstance());
            if(App.isOrientationFlag()) {
                selectSound.show(getFragmentManager(), "DawnTrackDialog");
            }
            else {
                pselectSound.show(getFragmentManager(), "DawnTrackDialogPot");
            }
            tracks = false;
        } else {
            wakeelementlist.remove(wakeelementlist.indexOf(4));
            txt_elem_tracks.setBackground(mcontext.getResources().getDrawable(R.drawable.tick_circle));
            tracks = true;
        }
        Bundle bundle=App.getTemp_bundle();
        bundle.putIntegerArrayList("days",dayslist);
        bundle.putIntegerArrayList("elements", wakeelementlist);
        bundle.putString("time",showTime.getText().toString());
        bundle.putString("default_sound", soundId);
        bundle.putBoolean("has_light_set",Boolean.valueOf(allowLight));
        bundle.putBoolean("has_music_set",Boolean.valueOf(allowMusic));
        bundle.putBoolean("has_blinds_set",Boolean.valueOf(allowBlind));


        Logs.info(TAG,"Bundle_elem_tracks"+bundle);
        setSaveGrau();
        App.setTemp_bundle(bundle);
    }


    @OnClick(R.id.btn_save)
    public void btn_save() {

        if(App.getSocket()!=null) {
            Logs.info(TAG,"user_str_action"+str_action+allowLight+allowBlind+allowMusic);
            if (!str_action.equals("delete_or_update")) {

                    addSimRequets();

            }
            else {

                    updateSimulation();

            }
        }

    }
    @OnClick(R.id.btn_cancel)
    public void btn_cancel() {
        dayslist.clear();
        Bundle bundle=new Bundle();
        bundle.putString("room_id",room_id);

        App.setTemp_bundle(bundle);

        Fragment mfrag= new_WakeRoomLanding.newInstance();

        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(Add_custmise_wake.this,R.id.frm_expriencesmain_container,mfrag);
        else   {
            App.setCallfrom("emptyschedule");
            ReplaceFragment.replaceFragment(Add_custmise_wake.this, R.id.frm_main_container, ExperienceMainFragmentPortrait.newInstance());
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



    private void addSimRequets()
    {
        long id_stamp=0;
        JSONArray daysjarray=new JSONArray();

        JSONObject jsonObject=new JSONObject();
        try {
            JSONArray jsonArray = App.getSimulationData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject=jsonArray.getJSONObject(i);
                StringBuilder str_days= new StringBuilder();


                if(str_days.toString().equals("")) {
                    for (int j = 0; j < jsonObject.getJSONArray("days").length(); j++) {
                        str_days.append("  ").append(getDays(jsonObject.getJSONArray("days").getString(j)));
                    }
                    String time = "";
                    if (time.equals("")) {
                        try {

                            final Date dateObj = sdf.parse(jsonObject.getInt("hour") + ":" + jsonObject.getInt("minutes"));
                            time = new SimpleDateFormat("hh:mm a").format(dateObj);
                        } catch (final ParseException e) {
                            e.printStackTrace();
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
            id_stamp=System.currentTimeMillis();
            JSONObject dataObject=new JSONObject();
            dataObject.put("CML_ID","dawn#"+id_stamp);
            dataObject.put("KEY_VAL","dawn#"+id_stamp);
            dataObject.put("Id","dawn#"+id_stamp);
            dataObject.put("ACTIVE_STATUS","1");
            dataObject.put("allowMusic",Boolean.valueOf(allowMusic));
            dataObject.put("allowBlind",Boolean.valueOf(allowBlind));
            dataObject.put("allowLight",Boolean.valueOf(allowLight));
            dataObject.put("type","Dawn");

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
            dataObject.put("SNOOZE_SET",false);
            dataObject.put("STOP_SET",false);

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


                //  int day=;

            }

            dataObject.put("days",daysjarray);
            if(bundle.containsKey("snoozeId")) dataObject.put("snoozeId",bundle.getString("snoozeId"));
            else dataObject.put("snoozeId","2");


            if(bundle.containsKey("soundId") || bundle.containsKey("playlistId")) {
                if (bundle.containsKey("soundId")) {
                    dataObject.put("soundId", bundle.getString("soundId"));
                    dataObject.put("playlistId", "");
                }
                if (bundle.containsKey("playlistId")) {
                    dataObject.put("playlistId", bundle.getString("playlistId"));
                    dataObject.put("soundId", "");
                }
            }
            else{
                if(isAudio_present) {
                    dataObject.put("soundId", "");
                    dataObject.put("playlistId", "");

                }

            }

            JSONObject obj = new JSONObject();
            obj.put("data", dataObject);
            obj.put("type", "create_dawn_simulation");

            App.getSocket().emit("action", obj);
            Log.d(TAG,"obj_create_dawn_sim : " +obj);
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_dawn_simulations", new JSONObject()));

        }
        catch (Exception ex){
            Logs.error(TAG+"SimulationRequestError",ex.getMessage());
        }



        final Bundle bundle1=new Bundle();
        bundle1.putString("screen","sub");

        bundle1.putString("CML_ID", room_id);
        bundle1.putString("CML_TITLE", getRoomTitle(room_id));

        /***********-30 notif**********/

        Calendar c = Calendar.getInstance();
        //  c.setTimeInMillis(id_stamp);
        c.set(Calendar.HOUR_OF_DAY, bundle.getInt("hours"));
        c.set(Calendar.MINUTE, bundle.getInt("minutes")-30);
        c.set(Calendar.SECOND, 0);

        if(c.before(Calendar.getInstance())){
            c.add(Calendar.DAY_OF_MONTH,1);
        }
        //  scheduleClient.setAlarmForNotification(c,"dawn#"+id_stamp,"");
/********* -2 notif ***********/

        Calendar cc = Calendar.getInstance();

        cc.set(Calendar.HOUR_OF_DAY, hr);
        cc.set(Calendar.MINUTE, min-2);
        cc.set(Calendar.SECOND, 0);

        if(cc.before(Calendar.getInstance())){
            cc.add(Calendar.DAY_OF_MONTH,1);
        }

        // scheduleClient.setAlarmForNotification(cc,dawn#"+id_stamp,""+dawn_room_name);

/********set_notif_call***********/
        JSONObject dataObject=new JSONObject();
        try {
            dataObject.put("hour",bundle.getInt("hours"));
            dataObject.put("minutes",bundle.getInt("minutes"));
            dataObject.put("Id","dawn#"+id_stamp);
            dataObject.put("ACTIVE_STATUS","1");
            dataObject.put("type","Dawn");
            dataObject.put("roomId",App.getnewDawnroom().getRoom_id());
            dataObject.put("days",daysjarray);

            JSONObject obj = new JSONObject();
            obj.put("data", dataObject);
            obj.put("type", "set_notification");
            App.getSocket().emit("action", obj);
            Log.d(TAG,"obj_create_dawn_sim : " +obj);

        } catch (JSONException e) {
            e.printStackTrace();
        }
/****Adding Google Analytics Button Click*******/

        App.trackEvent("Button Click", "btn_save_new_simulation", "Add New Dawn Simulation");
/**********/
        Fragment mfrag= new_WakeRoomLanding.newInstance();
        mfrag.setArguments(bundle1);

        if(App.isOrientationFlag()){ ReplaceFragment.replaceFragment(Add_custmise_wake.this,R.id.frm_expriencesmain_container, mfrag);}
        else {

            App.setCallfrom("emptyschedule");
            ReplaceFragment.replaceFragment(Add_custmise_wake.this, R.id.frm_main_container, ExperienceMainFragmentPortrait.newInstance());
        }


    }



    private void updateSimulation() {
        Logs.info("inside","updateSimulation");
        final Bundle bundle=App.getTemp_bundle();
        JSONArray daysjarray = new JSONArray();
        int updated_hrs=0;
        int updated_min=0;



        try {
            Log.i(TAG,"Update_Simulation_bundle"+bundle);
            JSONObject dataObj = new JSONObject();
            dataObj.put("CML_ID",bundle.getString("simulation_id"));
            dataObj.put("KEY_VAL",bundle.getString("simulation_id"));
            dataObj.put("Id",bundle.getString("simulation_id"));
            dataObj.put("ACTIVE_STATUS","1");
            dataObj.put("type","Dawn");

            if(bundle.containsKey("hours")) {
                updated_hrs=bundle.getInt("hours");
                dataObj.put("hour",updated_hrs);
            }
            else {
                updated_hrs=u_hr;
                dataObj.put("hour",updated_hrs);
            }

            if(bundle.containsKey("minutes")) {
                updated_min=bundle.getInt("minutes");
                dataObj.put("minutes",updated_min);
            }
            else {
                updated_min=u_min;
                dataObj.put("minutes",updated_min);
            }

            dataObj.put("room",room_id);
            dataObj.put("status",true);
            dataObj.put("SNOOZE_SET",false);
            dataObj.put("STOP_SET",false);

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
            dataObj.put("days", daysjarray);

            if(bundle.containsKey("snoozeId")) dataObj.put("snoozeId",bundle.getString("snoozeId"));
            else dataObj.put("snoozeId","3");

            dataObj.put("allowMusic",Boolean.valueOf(allowMusic));
            dataObj.put("allowBlind",Boolean.valueOf(allowBlind));
            dataObj.put("allowLight",Boolean.valueOf(allowLight));



            if(bundle.containsKey("soundId") || bundle.containsKey("playlistId")) {
                if (bundle.containsKey("soundId")) {
                    dataObj.put("soundId", bundle.getString("soundId"));
                    dataObj.put("playlistId", "");
                }


                if (bundle.containsKey("playlistId")) {
                    dataObj.put("playlistId", bundle.getString("playlistId"));
                    dataObj.put("soundId", "");
                }
            }
            else{
                if(isAudio_present) {
                    dataObj.put("soundId", "");
                    dataObj.put("playlistId", "");
                }

            }

            removeNotification();

            JSONObject obj = new JSONObject();
            obj.put("data", dataObj);
            obj.put("type", "update_dawn_simulation");
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_dawn_simulations", new JSONObject()));

            Logs.debug(TAG+"SimulationReqUpdated",""+obj);
            App.getSocket().emit("action", obj);
            Logs.debug(TAG,"obj_update_dawn_sim : " +obj);
            final Bundle bundle11 =new Bundle();
            bundle11.putString("CML_ID", room_id);
            bundle11.putString("CML_TITLE", getRoomTitle(room_id));
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, bundle.getInt("hours"));
            c.set(Calendar.MINUTE, bundle.getInt("minutes")-30);
            c.set(Calendar.SECOND, 0);

            if(c.before(Calendar.getInstance())){
                c.add(Calendar.DAY_OF_MONTH,1);
            }

            JSONObject dataObject=new JSONObject();
            try {
                dataObject.put("hour",updated_hrs);
                dataObject.put("minutes",updated_min);
                dataObject.put("Id",bundle.getString("simulation_id"));
                dataObject.put("ACTIVE_STATUS","1");
                dataObject.put("type","Dawn");
                dataObject.put("roomId",room_id);
                dataObject.put("days",daysjarray);

                JSONObject objjj = new JSONObject();
                objjj.put("data", dataObject);
                objjj.put("type", "set_notification");
                App.getSocket().emit("action", objjj);
                Logs.debug(TAG,"obj_create_dawn_sim : " +objjj);

            } catch (JSONException e) {
                Logs.error(TAG,"Update_obj_error"+e.getMessage());
            }
            /****Adding Google Analytics Button Click*******/

            App.trackEvent("Button Click", "btn_save_edit_simulation", "Update Dawn Simulation");
/**********/

            Fragment mfrag= new_WakeRoomLanding.newInstance();
            mfrag.setArguments(bundle11);

            if(App.isOrientationFlag()) { ReplaceFragment.replaceFragment(Add_custmise_wake.this,R.id.frm_expriencesmain_container, mfrag); }
            else {
                App.setCallfrom("emptyschedule");
                ReplaceFragment.replaceFragment(Add_custmise_wake.this, R.id.frm_main_container, ExperienceMainFragmentPortrait.newInstance());
            }




        }
        catch (Exception ex){
            Logs.error(TAG+"Simulation_UpdateError",ex.getMessage());
        }

    }

    private void removeNotification() {

        AlarmManager alarmManager = (AlarmManager) mcontext.getSystemService(Context.ALARM_SERVICE);
        NotificationManager nMgr = (NotificationManager) mcontext.getSystemService(ns);
        JSONArray notif_day = new JSONArray();
        try {

            JSONArray jsonArray = App.getSimulationData().getJSONArray("data");
            JSONObject resultedJsonObject=null;
            for (int i = 0; i <jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_ID").equals(sim_id))
                {

                    notif_day=getNotifDay(sim_id);

                }
            }

            Logs.info(TAG,"delete_dawn_simulation_id"+sim_id);




//Remove -30

            for(int day=0;day<notif_day.length();day++){


                int notif_day_int=0;

                notif_day_int=notif_day.getInt(day);
                int notif_day_name=notif_day_int+1;

                int dawn_id1 = new PrefManager(mcontext).getValueDawn("" + bundle.getString("simulation_id") + "A"+""+notif_day_name);

                Log.i("Delete*********-30  ",""+dawn_id1);

                nMgr.cancel(dawn_id1);
                Intent myIntent = new Intent(mcontext, NotifyService.class);

                PendingIntent pendingIntent = PendingIntent.getService(mcontext, dawn_id1, myIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.cancel(pendingIntent);

                new PrefManager(mcontext).removeKey(""+ bundle.getString("simulation_id") + "A"+""+notif_day_name);


            }
//Remove -2
            for(int day=0;day<notif_day.length();day++) {


                int notif_day_int=0;

                notif_day_int=notif_day.getInt(day);
                int notif_day_name=notif_day_int+1;


                int dawn_id = new PrefManager(mcontext).getValueDawn("" + bundle.getString("simulation_id") + "B"+""+notif_day_name);

                Log.i("Delete*********-2  ",""+dawn_id);


                nMgr.cancel(dawn_id);



                Intent myIntent1 = new Intent(mcontext, NotifyService.class);
                PendingIntent pendingIntent1 = PendingIntent.getService(mcontext, dawn_id, myIntent1,
                        PendingIntent.FLAG_UPDATE_CURRENT);


                alarmManager.cancel(pendingIntent1);

                new PrefManager(mcontext).removeKey("" + bundle.getString("simulation_id") + "B"+""+notif_day_name);
            }

        }catch (Exception ex){
            Log.e("eeeeerrrrrrrrr",""+ex.getMessage());
        }



    }

    private void setSaveButton() {



        if (selectDaysFlag && selectRoomFlag && selectSoundFlag) {

            btn_save.setClickable(true);
            btn_save.setEnabled(true);
        }
    }

    private void setDays(int day_index) {


        switch (day_index) {

            case 1:

                if(!dayslist.contains(1))
                {
                    unsortedData.put("1","");
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
                    unsortedData.put("2","");
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
                    unsortedData.put("3","");
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
                    unsortedData.put("4","");
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
                    unsortedData.put("5","");
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
                    unsortedData.put("6","");
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
                    unsortedData.put("0","");
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

        SortedMap sortedData = new TreeMap(new MapValueSort.ValueComparer(unsortedData));

        printMap(unsortedData);

        sortedData.putAll(unsortedData);
        printMap(sortedData);

    }
    private void printMap(Map data) {
        str="";
        for (Iterator iter = data.keySet().iterator(); iter.hasNext();) {
            String key = (String) iter.next();
            str+=","+data.get(key)+""+key;

        }
    }

    public static class MapValueSort {

        /**
         * inner class to do soring of the map
         **/
        public static class ValueComparer implements Comparator {
            private Map _data;

            public ValueComparer(Map data) {
                super();
                _data = data;
            }

            public int compare(Object key1, Object key2) {
                Comparable value1 = (Comparable) _data.get(key1);
                Comparable value2 = (Comparable) _data.get(key2);
                int c = value1.compareTo(value2);
                if (0 != c)
                    return c;
                Integer h1 = key1.hashCode(), h2 = key2.hashCode();
                return h1.compareTo(h2);
            }
        }
    }
    private static String getRoomTitle(String room_id)
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

    private void deleteSimulation()
    {
        App.setCallfrom("emptyschedule");
        try {
            JSONArray jsonArray = App.getSimulationData().getJSONArray("data");
            JSONObject resultedJsonObject=null;
            for (int i = 0; i <jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_ID").equals(sim_id))
                {
                    resultedJsonObject=jsonObject;

                    if(resultedJsonObject.has("status")==true)
                    {

                        resultedJsonObject.put("status",false);
                    }
                }
            }
            removeNotification();
            Logs.info(TAG+"delete_dawn_simulation_id",""+msg_id);
            if(App.getSocket()!=null)   App.getSocket().emit("action", JsonObjectCreater.getJsonObject("delete_dawn_simulation",resultedJsonObject));



        }
        catch (Exception ex){
            Logs.error(TAG,"SimulationError_Delete_getSimulationData"+ex.getMessage());}
        Bundle bundle1=new Bundle();
        bundle1.putString("screen","sub");
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(Add_custmise_wake.this,R.id.frm_expriencesmain_container, new_WakeRoomLanding.newInstance());
        else  ReplaceFragment.replaceFragment(Add_custmise_wake.this,R.id.frm_main_container, ExperienceMainFragmentPortrait.newInstance());

    }




    // get Dawn Days
    private JSONArray getNotifDay(String dawn_id) {

        JSONArray notif_day = new JSONArray();



        try {

            if (App.getSimulationData() != null && !dawn_id.equals("")) {
                JSONArray jsonArrayy = App.getSimulationData().getJSONArray("data");

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
            Log.e(TAG,"error_mainAity_DayArray"+e.getMessage());
        }
        return notif_day;
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

    private void daysadded()
    {

        Integer non_day;
        boolean day_flag[]={false,false,false,false,false,false,false};
        TextView day_view[]={txt_mon,txt_tue,txt_wen,txt_thu,txt_fri,txt_sat,txt_sun};
        JSONArray jsonArray;
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


                else {
                    JSONArray jsonArray1 = jsonObject.getJSONArray("days");
                    Logs.debug(TAG+"bundle_jsonDays", "" + jsonArray1);


                    for (int j = 0; j < jsonArray1.length(); j++) {


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
                    if(str_action.equals("delete_or_update") && current_day_flag[i])
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




    @Override
    public void onResume() {
        super.onResume();
        if(!App.isOrientationFlag()) ((AppCompatActivity)getActivity()).getSupportActionBar().hide();


    }

    @Override
    public void onStop() {
        super.onStop();
        if(!App.isOrientationFlag()){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        }
    }


    public void setTime()
    {

        try
        {


            String strDefaultTimeZone = App.getHubInfo().getJSONArray("data").getJSONObject(0).getString("CML_TIMEZONE");//defaultTimeZone.getDisplayName(false, TimeZone.SHORT);
            TimeZone tz = TimeZone.getTimeZone(strDefaultTimeZone);
            Calendar expdate = Calendar.getInstance(tz);

            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            String month_name = month_date.format(expdate.getTime());
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
            // String day_time=expdate.get(Calendar.DAY_OF_MONTH)+" | "+time;

            showTime.setText(""+time);
        }
        catch (Exception ex)
        {
            Logs.error(TAG+"HubTimeError",""+ex.getMessage());
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
        String name = "Add Wake Fragment";
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