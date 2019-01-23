package com.cloudsinc.welltekmobile.native_v2_welltek.dialogs;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;

import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.climate.ClimateByZoneFragment.getHvacManufature;
/**
 * This class containing functionality related to displaying dialog for thermostate
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class ThermostatDialog extends DialogFragment {
    boolean hvac_flag=false,cool_flag=false,heat_flag=false;
    String dialog_desc_string ="";
    String dialog_title="";
    String dialog_type="";
    int lower_point=0;
    int highest_point=0;
    int air_type=0;
    String hvac_id="";
    String hvac_mode_type="";
    String manufacturere="";
    String type="";
    String mode="";
    String fan_speed="";
    Context mcontext;
    boolean purification_state=false;
    @BindView(R.id.txt_thermostat_title)TextView txt_thermostat_title;
    @BindView(R.id.txt_thermostat_desc)TextView txt_thermostat_desc;
    @BindView(R.id.txt_sub_desc)TextView txt_sub_desc;
    @BindView(R.id.lin_main)LinearLayout lin_main;
    String TAG=ThermostatDialog.this.getClass().getName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.custom_thermostat_dialog, container,
                false);
        mcontext=rootView.getContext();
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        ButterKnife.bind(this,rootView);
        UserInteractionSleep.siboSleepChecking(rootView.getContext());
        lin_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                UserInteractionSleep.userInteract(mcontext);
                return false;
            }
        });
        hvac_flag=getArguments().getBoolean("hvac_flag");
        hvac_mode_type=getArguments().containsKey("hvac_mode_type")?getArguments().getString("hvac_mode_type"):"";
        heat_flag=getArguments().getBoolean("heat_flag");
        cool_flag=getArguments().getBoolean("cool_flag");
        dialog_type=getArguments().getString("dialogtype");
        purification_state=getArguments().getBoolean("purification_state");
        type=getArguments().getString("type");
        air_type=getArguments().getInt("air_type");
        mode=getArguments().getString("mode");
        fan_speed=getArguments().getString("fan_speed");
        manufacturere=getArguments().getString("manufaturer");
        highest_point=getArguments().getInt("highest_point");
        lower_point=getArguments().getInt("lower_point");
        hvac_id=getArguments().getString("hvac_id");
        if(dialog_type.equals("thermostat")||dialog_type.equals("bonair_hvac")) {
            txt_sub_desc.setVisibility(View.VISIBLE);
            if (hvac_flag) {
              if(heat_flag)  dialog_desc_string = mcontext.getResources().getString(R.string.thermostate_heating_mode);
              if(cool_flag)  dialog_desc_string = mcontext.getResources().getString(R.string.thermostate_cooling_mode);
              if(cool_flag&&heat_flag)  dialog_desc_string =hvac_mode_type.equals("heater")? mcontext.getResources().getString(R.string.thermostat_heat_mode): mcontext.getResources().getString(R.string.thermostat_auto_mode);
                dialog_title = "Turn Off Climate Control?";
            }
        }
        else
        {
            //if (hvac_flag) {
                dialog_title = purification_state?mcontext.getResources().getString(R.string.purification_dialog_on_title):mcontext.getResources().getString(R.string.purification_dialog_off_title);
              if(App.isOrientationFlag())  dialog_desc_string = purification_state?mcontext.getResources().getString(R.string.purification_dialog_on_desc):mcontext.getResources().getString(R.string.purification_dialog_off_desc);
              else dialog_desc_string = purification_state?mcontext.getResources().getString(R.string.purification_dialog_on_desc_mob):mcontext.getResources().getString(R.string.purification_dialog_off_desc_mob);
           /* }
            else
            {
                dialog_title = mcontext.getResources().getString(R.string.purification_dialog_on_title);
                dialog_desc_string = mcontext.getResources().getString(R.string.purification_dialog_on_desc);
            }*/
        }
        txt_thermostat_desc.setText(dialog_desc_string);
        txt_thermostat_title.setText(dialog_title);
        return rootView;
    }
    @OnClick(R.id.img_close)
    public void img_close()
    {
        this.dismiss();
    }
    @OnClick(R.id.btn_confirm)
    public void btn_confirm()
    {
            if(dialog_type.equals("thermostat"))hvacOnOff(!hvac_flag);
            else if(dialog_type.equals("bonair_hvac"))bonairHvacOnOff(!hvac_flag);
            else purificationOnOff();
            dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        Logs.info(TAG, "------i am in onResume");
        UserInteractionSleep.siboSleepChecking(mcontext);
    }
    @Override
    public void onStop() {
        super.onStop();
        Logs.info(TAG, "------i am in onstop");
        UserInteractionSleep.stopHandler();
    }
    public void hvacOnOff(boolean onoffstate)
    {
        App.setCallfrom("hvac");
        try
        {
            JSONObject obj = new JSONObject();
            if(onoffstate) obj.put("data", new JSONObject().put("CML_STATE", 1).put("Id", hvac_id).put("manufacturer",getHvacManufature()));
            else obj.put("data", new JSONObject().put("CML_STATE", 0).put("Id", hvac_id).put("manufacturer",getHvacManufature()));
            obj.put("type", "hvac_power");
            Log.d("SimulationRequestSend",""+obj);
            App.getSocket().emit("action", obj);
            //  App.getSocket().emit("trigger_dawn_simulation", JsonObjectCreater.getJsonObject("trigger_dawn_simulation", obj));
        }
        catch (Exception ex){
            System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
             Logs.error(TAG+"_SimulationRequestError",""+ex.getMessage());
        }
    }
    public void bonairHvacOnOff(boolean onoffstate)
    {
        App.setCallfrom("hvac");
        try
        {
            JSONObject obj = new JSONObject();
            if(onoffstate) obj.put("data", new JSONObject().put("CML_STATE", "on").put("Id", hvac_id).put("manufacturer",manufacturere));
            else obj.put("data", new JSONObject().put("CML_STATE", "off").put("Id", hvac_id).put("manufacturer",manufacturere));
            obj.put("type", "hvac_power");
            Log.d("SimulationRequestSend",""+obj);
            App.getSocket().emit("action", obj);
            //  App.getSocket().emit("trigger_dawn_simulation", JsonObjectCreater.getJsonObject("trigger_dawn_simulation", obj));
        }
        catch (Exception ex){
            System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
             Logs.error(TAG+"_SimulationRequestError",""+ex.getMessage());
        }
    }
    public void purificationOnOff()
    {
        try
        {
            JSONObject obj = new JSONObject();
            JSONObject dataJson=new JSONObject();
            dataJson.put("Id",hvac_id);
            dataJson.put("CML_PURIFICATION_STATE",purification_state);
            dataJson.put("CML_FAN_SPEED",fan_speed);
            dataJson.put("CML_TYPE",type);
            dataJson.put("CML_MODE",mode);
            dataJson.put("CML_AIR_TYPE",air_type);
            dataJson.put("CML_LOWER_POINT",lower_point);
            dataJson.put("CML_HIGHEST_POINT",highest_point);
            dataJson.put("manufacturer",manufacturere);
            obj.put("data", dataJson);
            obj.put("type", "hvac_purification");
            Log.d("SimulationRequestSend",""+obj);
            App.getSocket().emit("action", obj);
            //  App.getSocket().emit("trigger_dawn_simulation", JsonObjectCreater.getJsonObject("trigger_dawn_simulation", obj));
        }
        catch (Exception ex){
            System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
             Logs.error(TAG+"_SimulationRequestError",""+ex.getMessage());
        }
    }
}
