package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule.Add_custmise_wake;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Simulations;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.SwitchTrackChange;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class CustomWakeSimListAdapter extends BaseAdapter {

    private Context activity;

    private ArrayList<Simulations> simulationArrayList;
    private static LayoutInflater inflater=null;
    Fragment fragment;
    Fragment mfragment;

    private  String room_id="";


    public CustomWakeSimListAdapter(Fragment mfrag,Context a, ArrayList<Simulations> simulationArrayList, Fragment fragment) {
        activity = a;
        this.simulationArrayList=simulationArrayList;
        this.fragment=fragment;
        this.mfragment=mfrag;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    public int getCount() {
        return simulationArrayList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;



            if (convertView == null)
                vi = inflater.inflate(R.layout.simulation_list_item, null);

            room_id = App.getTemp_bundle().getString("room_id");

            final TextView txt_time = vi.findViewById(R.id.txt_time); // title
            final TextView txt_id = vi.findViewById(R.id.txt_id); // title
            final TextView txt_room_and_days = vi.findViewById(R.id.txt_room_and_days); //
            final TextView txt_sim_id = vi.findViewById(R.id.txt_id);
            final RelativeLayout rel_visiblity = vi.findViewById(R.id.rel_visiblity);
            Button btn_edit_sim = vi.findViewById(R.id.btn_edit_sim);
            btn_edit_sim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        /*******************/
                        Log.e("Simulation_Id", "" + txt_sim_id.getText());
                        Bundle bundle = App.getTemp_bundle();
                        bundle.putString("action", "delete_or_update");
                        bundle.putString("update", "update");
                        bundle.putString("simulation_id", txt_sim_id.getText().toString());

                        bundle.putString("Callfrom", "edit");


                        bundle.putBoolean("has_lights", true);

                        if (getCnt_blinds(room_id) > 0) bundle.putBoolean("has_blinds", true);
                        else bundle.putBoolean("has_blinds", false);

                        if (getCnt_music(room_id) > 0) bundle.putBoolean("has_music", true);
                        else bundle.putBoolean("has_music", false);

                        App.setTemp_bundle(bundle);

                        if (App.isOrientationFlag())
                            ReplaceFragment.replaceFragment(mfragment, R.id.frm_expriencesmain_container, Add_custmise_wake.newInstance());
                        else
                            ReplaceFragment.replaceFragment(mfragment, R.id.frm_main_container, Add_custmise_wake.newInstance());

                    } catch (NullPointerException ex) {
                        ex.getMessage();
                        if (App.isOrientationFlag())
                            ReplaceFragment.replaceFragment(mfragment, R.id.frm_expriencesmain_container, Add_custmise_wake.newInstance());
                        else
                            ReplaceFragment.replaceFragment(mfragment, R.id.frm_main_container, Add_custmise_wake.newInstance());

                    }
                }
            });

            Switch switch_no_off = vi.findViewById(R.id.swt_on_off);
            Simulations simulations = simulationArrayList.get(position);
            final Simulations sim = simulationArrayList.get(position);
            final String day = sim.get_days();
            final String sim_day = day.substring(day.split(" ")[0].length() + 1);


            txt_time.setText("" + simulations.get_time().toLowerCase());
            txt_room_and_days.setText("" + sim_day.trim());
            txt_id.setText("" + simulations.get_sim_id());

            switch_no_off.setChecked(simulations.get_switch_state());
            if (simulations.get_switch_state()) {
                SwitchTrackChange.changeTrackColor(activity, switch_no_off);
                rel_visiblity.setVisibility(View.GONE);
                switch_no_off.setEnabled(true);
            } else {
                SwitchTrackChange.changeTrackColor(activity, switch_no_off);
                if (App.isOrientationFlag() == true) {

                    rel_visiblity.setVisibility(View.VISIBLE);
                    btn_edit_sim.setEnabled(false);
                    switch_no_off.setAlpha(1f);
                } else btn_edit_sim.setEnabled(true);

            }


            final Switch simstatus = vi.findViewById(R.id.swt_on_off);

            simstatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    SwitchTrackChange.changeTrackColor(activity, simstatus);
                    try {
                        JSONArray jsonArray = App.getSimulationData().getJSONArray("data");
                        JSONObject resultedJsonObject = null;
                        if (isChecked == true) {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                try {
                                    if (jsonObject.getString("CML_ID").equals(txt_id.getText().toString())) {
                                        resultedJsonObject = jsonObject;

                                        App.getSocket().emit("action", JsonObjectCreater.getJsonObject("trigger_dawn_simulation", resultedJsonObject.put("status", true)));

                                    }
                                } catch (Exception ex) {
                                }
                            }


                        } else {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if (jsonObject.getString("CML_ID").equals(txt_id.getText().toString())) {
                                    resultedJsonObject = jsonObject;
                                    jsonObject.put("status", false);
                                    Log.d("halt_dawn_simulation", "" + resultedJsonObject);
                                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("halt_dawn_simulation", resultedJsonObject));
                                }
                            }
                        }

                    } catch (Exception ex) {
                        Log.e("SimulationError", ex.getMessage());
                    }
                }

            });

        return vi;
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
        catch (Exception ex){ Logs.error("_FError",ex.getMessage());}
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
        catch (Exception ex){ Logs.error("_FError",ex.getMessage());}
        return temp;
    }




}