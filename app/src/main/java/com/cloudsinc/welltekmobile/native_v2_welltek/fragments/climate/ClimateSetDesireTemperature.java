package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.climate;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.CustomZoneListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.ThermostatDialog;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.HomeHeathByZoneFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Zone;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.SwitchTrackChange;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.TimeFormatChange;
import com.github.lzyzsd.circleprogress.DonutProgress;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import static com.google.android.gms.internal.zzagz.runOnUiThread;
/**
 * The ClimateByZoneFragment is display and control climate in home and also ON/OFF purification
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class ClimateSetDesireTemperature extends Fragment implements AdapterView.OnItemSelectedListener,CompoundButton.OnCheckedChangeListener {
    private View lyt;
    public static ClimateSetDesireTemperature newInstance() {
        return new ClimateSetDesireTemperature();
    }
    ArrayList<Zone> arr_zone_title=new ArrayList<>();
    /*@Nullable @BindView(R.id.txt_comfortrange)TextView txt_comfortrange;*/
    @Nullable @BindView(R.id.txt_purification_state)TextView txt_purification_state;
    @Nullable @BindView(R.id.txt_humidity)TextView txt_humidity;
    @Nullable @BindView(R.id.img_meter_indicator)ImageView img_meter_indicator;
    @Nullable @BindView(R.id.txt_temp)TextView txt_temp;
    @Nullable @BindView(R.id.txt_degree_sign)TextView txt_degree_sign;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    @Nullable @BindView(R.id.img_thermostat_point)ImageView img_thermostat_point;
    @Nullable @BindView(R.id.seek_set_point)SeekBar seek_set_desire_point;
    @Nullable @BindView(R.id.txt_lower)TextView txt_lower;
    @Nullable @BindView(R.id.txt_higher)TextView txt_higher;
    @Nullable @BindView(R.id.img_increase)ImageView img_increase;
    @Nullable @BindView(R.id.img_decrease)ImageView img_decrease;
    @Nullable @BindView(R.id.rel_disable)RelativeLayout rel_disable;
    @Nullable @BindView(R.id.rel_thermostat_control_panel)RelativeLayout rel_thermostat_control_panel;
    String TAG=ClimateSetDesireTemperature.this.getClass().getName();
    int current_temp=10;
    double current_sensor_temp = 10;
    int lower_point =0;
    int highest_point=0;
    int str_air_type=0;
    int position=0;
    int hvac_point=0,desire_temp=0;
    boolean hvac_flag=false,temp_flag=false;
    String comfort_raneg="";
    int humidity=0;
    @Nullable @BindView(R.id.img_mode)ImageView img_mode;
    @Nullable @BindView(R.id.rel_thermostat)RelativeLayout rel_thermostat;
    @Nullable @BindView(R.id.img_thermostat_desire_temp)ImageView img_thermostat_desire_temp;
    @Nullable @BindView(R.id.lin_zone_selection)LinearLayout lin_zone_selection;
    @Nullable @BindView(R.id.rel_no_data)RelativeLayout rel_no_data;
    @Nullable @BindView(R.id.txt_date_time)TextView txt_time;
    /*@Nullable @BindView(R.id.txt_comfort_title)TextView txt_comfort_title;*/
    @Nullable @BindView(R.id.lin_comfort_band)LinearLayout lin_comfort_band;
    @BindView(R.id.rel_loading)RelativeLayout rel_loading;
    @Nullable @BindView(R.id.img_hvac_power)ImageView img_hvac_power;
    @Nullable @BindView(R.id.rel_date_zone)RelativeLayout rel_date_zone;
    @Nullable @BindView(R.id.donut_progress)DonutProgress donut_progress;
    @Nullable @BindView(R.id.spn_zone)Spinner spn_zone;
    @Nullable @BindView(R.id.swt_purification_power)Switch swt_purification_power;
    @BindView(R.id.txt_air_purification) TextView txt_air_purification;
    View thumbView;

    boolean heat_mode_flag=false,cool_mode_flag=false,cool_auto_flag=false,hvac_state=false,purification_state=true;
    String device_id="";
    String hvac_id="";
    String str_type="";
    String str_mode="";
    String str_fan_speed="";
    String str_room_temp="-1";
    String manufaturer="";
    boolean bonair_status=true;
    private Timer timer_sun=null;
    private TimerTask tsk_sun=null;
    Context mcontext;
    JSONArray bonairTypeJsonArray=new JSONArray();
    private View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
        v=inflater.inflate(R.layout.fragment_climate_set_desire_temperature, container, false);
        thumbView=LayoutInflater.from(getActivity()).inflate(R.layout.thumb_view,null,false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);

        timer_sun = new Timer();
        startTimeTimer();
        lyt= v.findViewById(R.id.lytmainfont);
        SwitchTrackChange.changeTrackColor(mcontext,swt_purification_power);
       /* txt_comfortrange.setVisibility(View.GONE);
        txt_comfort_title.setVisibility(View.GONE);*/
        txt_humidity.setVisibility(View.VISIBLE);
        final Typeface mFont = Typeface.createFromAsset(v.getContext().getAssets(),
                "fonts/SF-UI-Text-Regular.otf");
        final ViewGroup mContainer = (ViewGroup) lyt.getRootView();
        FontUtility.setAppFont(mContainer, mFont);
        seek_set_desire_point.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.setThumb(getThumb(progress+10));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setPoint(seekBar.getProgress()+10);
            }
        });

        if (App.getSocket()!=null) {
            try {
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_hvac_zone_provisioned",new JSONObject()));
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_provisioned", new JSONObject()));
            } catch (Exception ex) {
                Logs.error(TAG+"SimulationRequestError", "" + ex.getMessage());
            }
        }
        setSubcriber();
        getHumidity();
        getZoneTitle();
        if(App.getPosition()==-1)App.setPosition(0);
        getSetPoint(App.getPosition());
        return v;
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
                System.out.println("*******************************"+string);
                if(string.equals("hub_soft_resetted")||string.equals("HVAC")||string.equals("hvac_zone_provisioned")) {
                    getHumidity();
                    getZoneTitle();
                    getSetPoint(App.getPosition());
                }
            }
        } ;
        Subcriber s = new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);
    }
    @Optional @OnClick(R.id.img_increase)
    public void img_increase()
    {
        if (desire_temp<32) {
            setPoint(desire_temp+1);
        }
    }
    @Optional @OnClick(R.id.img_decrease)
    public void img_decrease()
    {
        if (desire_temp>10) {
            setPoint(desire_temp-1);
        }
    }
    public void getSetPoint(int position)
    {
        if(position==-1)position=0;
         Logs.info(TAG+"_BonairPosition",""+position);
        String flag_state="off";
        cool_mode_flag = false;heat_mode_flag=false;
        try {
             Logs.info(TAG+"_ProvisionLog",""+App.getProvisionalDevicesData());
            if (App.getHvacZoneProvisionedJson().getJSONArray("data").toString().equals("[]")||arr_zone_title.size()==0) {
                txt_air_purification.setVisibility(View.GONE);
                swt_purification_power.setVisibility(View.GONE);
                rel_no_data.setVisibility(View.VISIBLE);
                lin_comfort_band.setVisibility(View.GONE);
                rel_loading.setVisibility(View.GONE);
            } else {
                JSONArray jsonArray = App.getHvacZoneProvisionedJson().getJSONArray("data");
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                     Logs.info(TAG+"_BonairJsonObject", arr_zone_title.get(position).getZone_id()+"--" + jsonObject);
                    if (arr_zone_title.get(position).getZone_id().equals(jsonObject.getString("Id"))) {
                        hvac_id = jsonObject.getString("Id");
                         Logs.info(TAG+"_FoundBonairJsonObject",""+jsonObject);
                        manufaturer = jsonObject.getString("manufacturer");
                        if (jsonObject.has("CML_SET_POINT"))
                            desire_temp = (int) jsonObject.getDouble("CML_SET_POINT");
                        else
                            desire_temp=0;
                        if(jsonObject.has("CML_STATE"))flag_state = jsonObject.getString("CML_STATE");
                        if (jsonObject.has("CML_HIGHEST_POINT"))
                            highest_point = jsonObject.getInt("CML_HIGHEST_POINT");
                        if (jsonObject.has("CML_AIR_TYPE"))
                            str_air_type = jsonObject.getInt("CML_AIR_TYPE");
                        if (jsonObject.has("CML_LOWER_POINT"))
                            lower_point = jsonObject.getInt("CML_LOWER_POINT");
                        if (jsonObject.has("CML_TYPE"))
                            str_type = jsonObject.getString("CML_TYPE");
                        if (jsonObject.has("CML_MODE"))
                            str_mode = jsonObject.getString("CML_MODE");
                        if (jsonObject.has("CML_SET_POINT"))
                            current_sensor_temp = jsonObject.getDouble("CML_SET_POINT");
                        if (jsonObject.has("CML_FAN_SPEED"))
                            str_fan_speed = jsonObject.getString("CML_FAN_SPEED");
                        if (jsonObject.has("CML_ROOM_TEMPERATURE"))
                            str_room_temp = jsonObject.getString("CML_ROOM_TEMPERATURE");
                        if(jsonObject.has("CML_PURIFICATION_STATE")) purification_state = jsonObject.getBoolean("CML_PURIFICATION_STATE");
                        if (jsonObject.has("CML_STATUS"))
                            bonair_status = jsonObject.getBoolean("CML_STATUS");
                        if (jsonObject.has("CML_BONAIRE_TYPE"))
                            bonairTypeJsonArray = jsonObject.getJSONArray("CML_BONAIRE_TYPE");
                       // startHvacTempRotation();
                    }
                    // str_mode=jsonObject.getString("CML_MODE");
                }
            }
            if(bonairTypeJsonArray.length()>0)
            {
                ArrayList<String> arrayList=new ArrayList<>();
                for (int i=0;i<bonairTypeJsonArray.length();i++)
                {
                    arrayList.add(bonairTypeJsonArray.getString(i));
                }
                System.out.println("*****"+arrayList);
                if(arrayList.contains("heat")&&!arrayList.contains("cool"))
                {
                }
                else
                {
                    ReplaceFragment.replaceFragment(ClimateSetDesireTemperature.this, R.id.frm_main_container, ClimateByZoneFragment.newInstance());
                }
            }
            if(desire_temp>=10) {
                seek_set_desire_point.setProgress(desire_temp - 10);
                seek_set_desire_point.setThumb(getThumb(desire_temp));
            }
            else {
                seek_set_desire_point.setProgress(desire_temp);
                seek_set_desire_point.setThumb(getThumb(desire_temp));
            }
            current_temp=(int) Math.round(Double.parseDouble(str_room_temp));
            setRotator();
            if(current_temp >= 0&&current_temp<=99) {
                if(current_temp<10)
                {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) txt_temp.getLayoutParams();
                    params.setMargins(0, 0, 0, 0);
                    txt_temp.setLayoutParams(params);
                }
                txt_temp.setText("" + current_temp);
                if(App.isOrientationFlag())txt_temp.setTextSize(100f);
                else txt_temp.setTextSize(60f);
                img_mode.setVisibility(View.VISIBLE);
                txt_degree_sign.setVisibility(View.VISIBLE);
                img_thermostat_point.setVisibility(View.VISIBLE);
            }
            else {
                ViewGroup.MarginLayoutParams params =
                        (ViewGroup.MarginLayoutParams)txt_temp.getLayoutParams();
                params.leftMargin = 90 - 100;   // -100
                txt_temp.setLayoutParams(params);
                txt_temp.setText("NA");
                if(App.isOrientationFlag())txt_temp.setTextSize(80f);
                else txt_temp.setTextSize(60f);
                txt_degree_sign.setVisibility(View.GONE);
                img_thermostat_point.setVisibility(View.GONE);
            }
            if(current_temp>=0&&current_temp<=40)
            {
                img_thermostat_point.setVisibility(View.VISIBLE);
            }
            else
            {
                img_thermostat_point.setVisibility(View.GONE);
            }
            swt_purification_power.setOnCheckedChangeListener(null);
            swt_purification_power.setChecked(purification_state);
            swt_purification_power.setOnCheckedChangeListener(this);
            SwitchTrackChange.changeTrackColor(mcontext,swt_purification_power);
             Logs.info(TAG+"_Hvac_info", highest_point + "----" + lower_point + "----" );
            if (lower_point == 0) lower_point = 19;
            if (highest_point == 0) highest_point = 22;
            if (str_type.equals("heat")) {
                heat_mode_flag = true;
            }
            if (str_type.equals("cool")) {
                cool_mode_flag = true;
            }
            if (str_type.equals("none")) {
                cool_auto_flag = true;
                cool_mode_flag = true;
                heat_mode_flag = true;
            }
           Logs.info("Temp_points","----"+desire_temp+"------"+current_temp);
                if(desire_temp>current_temp)
                {
                    img_mode.setVisibility(View.VISIBLE);
                    img_mode.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heating_pink));
                }
                else
                {
                    img_mode.setVisibility(View.GONE);
                }
             Logs.info(TAG+"_Hvac_highest_lowest",highest_point+"------"+lower_point+"------"+hvac_point);
            comfort_raneg="" + lower_point + "\u00B0-" + highest_point + "\u00B0";
            if(flag_state.equals("on"))
            {
                hvac_state=true;
                disableAllViews(rel_thermostat_control_panel,true);
                setThermostatOnOffColor(R.color.white);
                img_decrease.setColorFilter(mcontext.getResources().getColor(R.color.dialer_color), PorterDuff.Mode.SRC_IN);
                img_increase.setColorFilter(mcontext.getResources().getColor(R.color.dialer_color), PorterDuff.Mode.SRC_IN);
                img_thermostat_point.setAlpha(1f);
                img_thermostat_desire_temp.setAlpha(1f);
                donut_progress.setAlpha(1f);
                img_mode.setAlpha(1f);
                seek_set_desire_point.setClickable(true);
                seek_set_desire_point.setFocusable(true);
                seek_set_desire_point.setEnabled(true);
                seek_set_desire_point.setAlpha(1.0f);
                swt_purification_power.setAlpha(1f);
                swt_purification_power.setEnabled(true);
            }
            else
            {
                hvac_state=false;
                disableAllViews(rel_thermostat_control_panel,false);
                setThermostatOnOffColor(R.color.fragment_blue_light);
                img_decrease.setColorFilter(mcontext.getResources().getColor(R.color.fragment_blue_light), PorterDuff.Mode.SRC_IN);
                img_increase.setColorFilter(mcontext.getResources().getColor(R.color.fragment_blue_light), PorterDuff.Mode.SRC_IN);
                img_mode.setAlpha(0.5f);
                img_thermostat_point.setAlpha(0.5f);
                img_thermostat_desire_temp.setAlpha(0.5f);
                donut_progress.setAlpha(0.5f);
                seek_set_desire_point.setClickable(false);
                seek_set_desire_point.setFocusable(false);
                seek_set_desire_point.setEnabled(false);
                seek_set_desire_point.setAlpha(0.5f);
                swt_purification_power.setAlpha(0.5f);
                swt_purification_power.setEnabled(false);
            }
            if(!bonair_status)
            {
                disableAllViews(rel_thermostat_control_panel,false);
                setThermostatOnOffColor(R.color.fragment_blue_light);
                img_decrease.setColorFilter(mcontext.getResources().getColor(R.color.fragment_blue_light), PorterDuff.Mode.SRC_IN);
                img_increase.setColorFilter(mcontext.getResources().getColor(R.color.fragment_blue_light), PorterDuff.Mode.SRC_IN);
                img_mode.setAlpha(0.5f);
                img_thermostat_point.setAlpha(0.5f);
                img_thermostat_desire_temp.setAlpha(0.5f);
                donut_progress.setAlpha(0.5f);
                seek_set_desire_point.setClickable(false);
                seek_set_desire_point.setFocusable(false);
                seek_set_desire_point.setEnabled(false);
                seek_set_desire_point.setAlpha(0.5f);
                swt_purification_power.setAlpha(0.5f);
                swt_purification_power.setEnabled(false);
                img_hvac_power.setAlpha(0.5f);
                img_hvac_power.setClickable(false);
            }
            else
            {
                img_hvac_power.setAlpha(1f);
                img_hvac_power.setClickable(true);
                if(hvac_state)
                {
                    disableAllViews(rel_thermostat_control_panel,true);
                    setThermostatOnOffColor(R.color.white);
                    img_decrease.setColorFilter(mcontext.getResources().getColor(R.color.dialer_color), PorterDuff.Mode.SRC_IN);
                    img_increase.setColorFilter(mcontext.getResources().getColor(R.color.dialer_color), PorterDuff.Mode.SRC_IN);
                    img_thermostat_point.setAlpha(1f);
                    img_thermostat_desire_temp.setAlpha(1f);
                    donut_progress.setAlpha(1f);
                    img_mode.setAlpha(1f);
                    seek_set_desire_point.setClickable(true);
                    seek_set_desire_point.setFocusable(true);
                    seek_set_desire_point.setEnabled(true);
                    seek_set_desire_point.setAlpha(1.0f);
                    swt_purification_power.setAlpha(1f);
                    swt_purification_power.setEnabled(true);
                }
            }
            if(highest_point>lower_point)
            {
                double progress;
                if(App.isOrientationFlag()) progress=(highest_point-lower_point)*2;
                else progress=(highest_point-lower_point)*0.45;
                if(progress==0) donut_progress.setProgress(0.3f);
                else donut_progress.setProgress((float) progress);
                 Logs.info(TAG+"_ComforttBand",""+progress);
                setComfortRotation(lower_point);
            }
             Logs.info(TAG+"_hvac_flag",""+hvac_flag);
            if(hvac_flag)
            {
                lin_comfort_band.setVisibility(View.VISIBLE);
                lin_zone_selection.setVisibility(View.VISIBLE);
                rel_no_data.setVisibility(View.GONE);
                swt_purification_power.setVisibility(View.VISIBLE);
                txt_air_purification.setVisibility(View.VISIBLE);
                rel_loading.setVisibility(View.GONE);
            }
            else
            {
                txt_air_purification.setVisibility(View.GONE);
                lin_zone_selection.setVisibility(View.GONE);
                swt_purification_power.setVisibility(View.GONE);
                lin_comfort_band.setVisibility(View.GONE);
                rel_no_data.setVisibility(View.VISIBLE);
                rel_loading.setVisibility(View.GONE);
            }
        }
        catch(Exception ex)
        {
             Logs.info(TAG+"_SetPointError", "" + ex.getMessage());
        }
    }
    public void setHvacPoint(double value,String str_action)
    {
        try
        {
            JSONObject obj = new JSONObject();
            if(lower_point<10) lower_point=10;
            if(highest_point>32) highest_point=32;
            if(str_action.equals("increment")) obj.put("data", new JSONObject().put("CML_LOWER_POINT",lower_point+1).put("CML_HIGHEST_POINT",highest_point+1).put("CML_SET_POINT",value).put("Id",hvac_id).put("manufacturer",manufaturer).put("CML_ROOM_TEMPERATURE",str_room_temp));
            if(str_action.equals("decrement")) obj.put("data", new JSONObject().put("CML_LOWER_POINT",lower_point-1).put("CML_HIGHEST_POINT",highest_point-1).put("CML_SET_POINT",value).put("Id",hvac_id).put("manufacturer",manufaturer).put("CML_ROOM_TEMPERATURE",str_room_temp));
            obj.put("type", "set_comfort_band");
            Log.d("SimulationRequestSend", "" + obj);
            App.getSocket().emit("action", obj);
        }
        catch (Exception ex){
            System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
             Logs.info(TAG+"_SimulationRequestError",""+ex.getMessage());
        }
    }
    private void setRotator() {
        double degree = current_temp*6.75;
        img_thermostat_point.setRotation((float) degree);
        img_thermostat_point.setRotation((float) degree);
        degree=desire_temp*6.75;
        img_thermostat_desire_temp.setRotation((float) degree);
        img_thermostat_desire_temp.setRotation((float) degree);
    }
    private void setComfortRotation(int lower_point) {
        double degree = lower_point*6.75;
        donut_progress.setRotation((float)degree);
    }
    public void setThermostatOnOffColor(int color_id)
    {
        img_meter_indicator.setColorFilter(mcontext.getResources().getColor(color_id), PorterDuff.Mode.SRC_IN);
        txt_humidity.setTextColor(mcontext.getResources().getColor(color_id));
       /* txt_comfortrange.setTextColor(mcontext.getResources().getColor(color_id));
        txt_comfort_title.setTextColor(mcontext.getResources().getColor(color_id));*/
    }
    /**
     * hvac ON/OFF
     * @param onoffstate
     */
    public void hvacOnOff(boolean onoffstate)
    {
        App.setCallfrom("hvac");
        try
        {
            JSONObject obj = new JSONObject();
            if(onoffstate) obj.put("data", new JSONObject().put("CML_STATE", "on").put("Id", hvac_id).put("manufacturer",manufaturer));
            else obj.put("data", new JSONObject().put("CML_STATE", "off").put("Id", hvac_id).put("manufacturer",manufaturer));
            obj.put("type", "hvac_power");
            Log.d("SimulationRequestSend",""+obj);
            App.getSocket().emit("action", obj);
            //  App.getSocket().emit("trigger_dawn_simulation", JsonObjectCreater.getJsonObject("trigger_dawn_simulation", obj));
        }
        catch (Exception ex){
            System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
             Logs.info(TAG+"_SimulationRequestError",""+ex.getMessage());
        }
    }
    public void getHumidity()
    {
        String hvac_name="";
        int int_mode=192;
        try {
            JSONArray jsonArray = App.getZoneJson().getJSONArray("data");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_TITLE").equals("Whole Home"))
                {
                     Logs.info(TAG+"_HomeZoneObject",""+jsonObject);
                    //hvac_name=jsonObject.getString("CML_TITLE");
                    humidity=(int)Math.round(jsonObject.getDouble("CML_RELATIVE_HUMIDITY"));
                }
            }
        }
        catch (Exception ex)
        {
             Logs.info(TAG+"_SetAQIError",""+ex.getMessage());
        }
        txt_humidity.setText(""+humidity+"%  Humidity");
    }
    private void startTimeTimer()
    {
        if (tsk_sun == null) {
            tsk_sun = new TimerTask() {
                @Override
                public void run() {
                    Log.e("sun_movement", "-----------------------");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            setTime();

                        }
                    });

                }
            };
            timer_sun.schedule(tsk_sun, 0, 1000);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(timer_sun!=null){
            timer_sun.cancel();
            timer_sun = null;
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
            String time="";
            String hr="",min="";
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
            String day_time=expdate.get(Calendar.DAY_OF_MONTH)+" | "+time;
            txt_time.setText(""+month_name.toUpperCase()+" "+day_time);
        }
        catch (Exception ex)
        {
            Log.e("HubTimeError",""+ex.getMessage());
        }
    }
    public void getZoneTitle()
    {
        try
        {
            arr_zone_title.clear();
            JSONArray jsonArray=App.getHvacZoneProvisionedJson().getJSONArray("data");
            if (App.getHvacZoneProvisionedJson().getJSONArray("data").toString().equals("[]"))
            {
                hvac_flag = false;
            }
             Logs.info(TAG+"_HvacProvisionalJson",""+jsonArray);
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("manufacturer").equals("Bonaire")) {
                    Zone zone = new Zone();
                    zone.setZone_id(jsonObject.getString("CML_ID"));
                    zone.setZone_title(jsonObject.getString("CML_TITLE"));
                    arr_zone_title.add(zone);
                }
            }
             Logs.info(TAG+"_bonairzonearrsize",""+arr_zone_title.size());
            if(arr_zone_title.size()>0) {
                CustomZoneListAdapter customZoneListAdapter = new CustomZoneListAdapter(mcontext, arr_zone_title);
                spn_zone.setAdapter(customZoneListAdapter);
                if (App.getPosition() == -1) App.setPosition(0);
                if(App.getPosition()<arr_zone_title.size()) spn_zone.setSelection(App.getPosition(), false);
                spn_zone.setOnItemSelectedListener(this);
                hvac_flag = true;
            }
            else
            {
                hvac_flag = false;
            }
        }
        catch (Exception ex)
        {
             Logs.info(TAG+"_ZoneTitleError",""+ex.getMessage());
        }
    }
    @Optional @OnClick(R.id.img_hvac_power)
    public void img_hvac_power()
    {
        if(hvac_state) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("hvac_flag", hvac_state);
            bundle.putBoolean("heat_flag", heat_mode_flag);
            bundle.putBoolean("cool_flag", cool_mode_flag);
            bundle.putString("hvac_id", hvac_id);
            bundle.putString("fan_speed",str_fan_speed );
            bundle.putString("mode",str_mode );
            bundle.putString("type",str_type );
            bundle.putInt("air_type",str_air_type );
            bundle.putInt("highest_point",highest_point );
            bundle.putInt("lower_point",lower_point );
            bundle.putString("dialogtype", "bonair_hvac");
            bundle.putString("hvac_mode_type", "heater");
            bundle.putString("manufaturer", manufaturer);
            ThermostatDialog dialog = new ThermostatDialog();
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "Thermostat_Dialog");
        }
        else
        {
            hvacOnOff(true);
        }
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
    @Optional @OnClick(R.id.img_back)
    public void img_back()
    {
        ReplaceFragment.replaceFragment(ClimateSetDesireTemperature.this,R.id.frm_main_container, HomeHeathByZoneFragment.newInstance());
    }
    @Override
    public void onResume() {
        super.onResume();
        if(!App.isOrientationFlag())((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        if(!App.isOrientationFlag())((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
        this.position=position1;
         Logs.info(TAG+"_ItemSelectPosition",""+position);
        device_id=arr_zone_title.get(position).getZone_id();
        App.setPosition(position);
        getSetPoint(position);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    public Drawable getThumb(int progress) {
        ((TextView) thumbView.findViewById(R.id.txtProgress)).setText(progress + " ");
        thumbView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap bitmap = Bitmap.createBitmap(thumbView.getMeasuredWidth(), thumbView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        thumbView.layout(0, 30, thumbView.getMeasuredWidth(), thumbView.getMeasuredHeight());
        thumbView.draw(canvas);
        return new BitmapDrawable(getResources(), bitmap);
    }
    public void setPoint(int point)
    {
        try
        {
            JSONObject obj = new JSONObject();
            obj.put("data", new JSONObject().put("CML_SET_POINT",point).put("CML_LOWER_POINT",lower_point).put("CML_HIGHEST_POINT",highest_point).put("Id",hvac_id).put("manufacturer",manufaturer).put("CML_ROOM_TEMPERATURE",str_room_temp));
            obj.put("type", "set_hvac_setpoint");
            Log.d("SimulationRequestSend",""+obj);
            App.getSocket().emit("action", obj);
            //  App.getSocket().emit("trigger_dawn_simulation", JsonObjectCreater.getJsonObject("trigger_dawn_simulation", obj));
        }
        catch (Exception ex){
            Logs.info(TAG+"_SimulationRequestError",ex.getMessage());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.swt_purification_power) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("hvac_flag", hvac_state);
            bundle.putBoolean("heat_flag", heat_mode_flag);
            bundle.putBoolean("cool_flag", cool_mode_flag);
            bundle.putString("fan_speed", str_fan_speed);
            bundle.putInt("highest_point", highest_point);
            bundle.putInt("lower_point", lower_point);
            bundle.putString("mode", str_mode);
            bundle.putString("type", str_type);
            bundle.putString("hvac_id", hvac_id);
            bundle.putInt("air_type", str_air_type);
            bundle.putString("dialogtype", "purification");
            bundle.putBoolean("purification_state", isChecked);
            bundle.putString("manufaturer", manufaturer);
            ThermostatDialog dialog = new ThermostatDialog();
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "Thermostat_Dialog");
            swt_purification_power.setChecked(!isChecked);
            SwitchTrackChange.changeTrackColor(mcontext,swt_purification_power);
        }
    }

}
