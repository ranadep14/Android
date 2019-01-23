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
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule.Add_custmise_sleep;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Sleepsim;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.SwitchTrackChange;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class CustomSleepSimListAdapter extends BaseAdapter {

    private Context activity;

    private ArrayList<Sleepsim> simulationArrayList;
    private static LayoutInflater inflater=null;
    Fragment fragment;
    Fragment mfragment;



    public CustomSleepSimListAdapter(Fragment mfrag,Context a, ArrayList<Sleepsim> simulationArrayList, Fragment fragment) {
        activity = a;
        this.simulationArrayList=simulationArrayList;
        this.fragment=fragment;
        this.mfragment=mfrag;


        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void clear()
    {
        simulationArrayList.clear();
        notifyDataSetChanged();
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
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.sleep_list_item, null);


        final TextView txt_time = vi.findViewById(R.id.txt_time); // title
        final TextView txt_id = vi.findViewById(R.id.txt_id); // title
        final TextView txt_room_and_days = vi.findViewById(R.id.txt_room_and_days); // artist name
        final  TextView txt_sim_id= vi.findViewById(R.id.txt_id);
        final RelativeLayout rel_visiblity= vi.findViewById(R.id.rel_visiblity);
        final Sleepsim sim=simulationArrayList.get(position);

        Button btn_edit_sim= vi.findViewById(R.id.btn_edit_sleep);
        btn_edit_sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle= App.getTemp_bundle();

                bundle.putString("sleepsimulation_id",txt_sim_id.getText().toString());
                bundle.putString("sleepCallfrom","edit");
                bundle.putString("actionn","delete_or_updatee"); bundle.putString("sleep_id",txt_sim_id.getText().toString());
                App.setTemp_bundle(bundle);


                if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(fragment,R.id.frm_expriencesmain_container, Add_custmise_sleep.newInstance());
                else  ReplaceFragment.replaceFragment(fragment,R.id.frm_main_container, Add_custmise_sleep.newInstance());



            }
        });
        Switch switch_no_off= vi.findViewById(R.id.swt_on_off);
        Sleepsim simulations=simulationArrayList.get(position);
        final String day=sim.get_days();
        final String sim_day=day.substring(day.split(" ")[0].length()+1);




        txt_time.setText(""+simulations.get_time().toLowerCase());
        txt_room_and_days.setText(""+sim_day.trim());
        txt_id.setText(""+simulations.get_sim_id());
        switch_no_off.setChecked(simulations.get_switch_state());
        if(simulations.get_switch_state()) {
            SwitchTrackChange.changeTrackColor(activity,switch_no_off);
            rel_visiblity.setVisibility(View.GONE);
            btn_edit_sim.setEnabled(true);
            switch_no_off.setAlpha(1f);
        }
        else {
            SwitchTrackChange.changeTrackColor(activity,switch_no_off);
            if(App.isOrientationFlag()==true) {
                rel_visiblity.setVisibility(View.VISIBLE);
                btn_edit_sim.setEnabled(false);
                switch_no_off.setAlpha(1f);
            }
            else btn_edit_sim.setEnabled(true);


        }


        final Switch   simstatus= vi.findViewById(R.id.swt_on_off);


        simstatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                try {
                    JSONArray jsonArray = App.getSleepSimulationData().getJSONArray("data");
                    JSONObject resultedJsonObject=null;
                    if(isChecked==true) {
                        SwitchTrackChange.changeTrackColor(activity,simstatus);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            try {
                                if (jsonObject.getString("CML_ID").equals(txt_id.getText().toString())) {
                                    resultedJsonObject = jsonObject;
                                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("trigger_sleep_simulation", resultedJsonObject.put("status", true)));

                                }

                            }
                            catch (Exception ex){}
                        }


                    }
                    else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if (jsonObject.getString("CML_ID").equals(txt_id.getText().toString())) {
                                resultedJsonObject = jsonObject;
                                jsonObject.put("status",false);
                                Log.d("halt_sleep_simulation", "" + resultedJsonObject);

                                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("halt_sleep_simulation", resultedJsonObject));
                            }
                        }
                    }


                }

                catch (Exception ex){
                    Log.e("SleepAdpSimulationError",ex.getMessage());}
            }

        });
        return vi;
    }
}