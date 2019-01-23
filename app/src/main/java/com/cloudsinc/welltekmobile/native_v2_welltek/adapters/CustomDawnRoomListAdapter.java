package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule.EmptySchedulesFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Allsimulations;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.New_RoomDawn;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.TimeFormatChange;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CustomDawnRoomListAdapter extends RecyclerView.Adapter<CustomDawnRoomListAdapter.CustomViewHolder> {
    private Context mContext;

    private List<New_RoomDawn> mNewRoomDawnList;
  //  private ArrayList<Allsimulations> simulationsArrayList = new ArrayList<>();


    //  List<Allsimulations> allsimulations;
    private static final String TAG = CustomDawnRoomListAdapter.class.getName();

    private Boolean flag_status;
    Fragment mfragment;
    ArrayList<String> days_sorted = new ArrayList<>();
    CustomAllSimumationAdapter da;
    private String roomId;

    public CustomDawnRoomListAdapter(Context c, List<New_RoomDawn> mNewRoomDawnList, Fragment mfragment) {
        mContext=c;
        this.mNewRoomDawnList = mNewRoomDawnList;
        this.mfragment=mfragment;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView no_text;
        TextView txt_id;
        TextView txt_time;
        Button btn_add;
        RecyclerView all_simulation;

        CustomViewHolder(View view) {
            super(view);
            no_text = view.findViewById(R.id.no_text);
            txt_id = view.findViewById(R.id.txt_id);
            txt_time = view.findViewById(R.id.txt_title);
            btn_add = view.findViewById(R.id.btn_add);
            all_simulation = view.findViewById(R.id.allsimulationsList);
            // allsimulations = getAllSimulations(roomId);

        }
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        New_RoomDawn newRoomDawn = mNewRoomDawnList.get(position);

        LinearLayoutManager llm = new LinearLayoutManager(mContext);

        holder.all_simulation.setLayoutManager(llm);
        holder.btn_add.setTag(position);


        roomId= newRoomDawn.getRoom_id();
        holder.txt_id.setText("" + newRoomDawn.getRoom_id());
        holder.txt_time.setText("" + newRoomDawn.getRoom_title());
        List<Allsimulations> simulationsArrayList = getAllSimulations(newRoomDawn.getRoom_id());
        Log.i("DawnRoomId",""+newRoomDawn.getRoom_id());
        Log.i("allsimDawnRoomId",""+simulationsArrayList);



        if (simulationsArrayList.size() > 0) {
            holder.no_text.setVisibility(View.GONE);
            holder.all_simulation.setVisibility(View.VISIBLE);
            if (da == null) {
                CustomAllSimumationAdapter  da = new CustomAllSimumationAdapter(mContext, simulationsArrayList, roomId, mfragment);
                holder.all_simulation.setAdapter(da);
              //  da.notifyDataSetChanged();
            } else {
                da.reload(simulationsArrayList);
                da.notifyDataSetChanged();
            }

        }else {
            holder.all_simulation.setVisibility(View.INVISIBLE);
            holder.no_text.setVisibility(View.VISIBLE);
        }


        try {
            holder.all_simulation.setOnTouchListener(new ListView.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            // Disallow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                            break;

                        case MotionEvent.ACTION_UP:
                            // Allow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }

                    // Handle ListView touch events.
                    v.onTouchEvent(event);
                    return true;
                }
            });
        } catch (IndexOutOfBoundsException e) {
            Log.e(TAG,"IndexOutOfBoundsException" +e.getMessage());
        }



        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                New_RoomDawn room=new New_RoomDawn();
                int pos=(int)v.getTag();

                room.setRoom_id(mNewRoomDawnList.get(pos).getRoom_id());
                room.setRoom_title(mNewRoomDawnList.get(pos).getRoom_title());
                room.setCnt_audios(mNewRoomDawnList.get(pos).getCnt_audios());
                room.setCnt_blinds(mNewRoomDawnList.get(pos).getCnt_blinds());

                App.setnewDawnroom(room);


                final Bundle bundle1=new Bundle();
                bundle1.putString("screen","sub");
                bundle1.putString("CML_ID", ""+ mNewRoomDawnList.get(pos).getRoom_id());
                bundle1.putString("room_id", ""+ mNewRoomDawnList.get(pos).getRoom_id());
                bundle1.putString("CML_TITLE",""+ mNewRoomDawnList.get(pos).getRoom_title());

                App.setTemp_bundle(bundle1);

                Fragment mfrag= EmptySchedulesFragment.newInstance();
                mfrag.setArguments(bundle1);
                if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(mfragment,R.id.frm_expriencesmain_container, mfrag);
                else  ReplaceFragment.replaceFragment(mfragment,R.id.frm_main_container, EmptySchedulesFragment.newInstance());

            }
        });

        // getSimulation(room_id);

        Log.i("Room_id",""+ newRoomDawn.getRoom_id());
    }




    @Override
    public int getItemCount() {
        return mNewRoomDawnList.size();
    }


    public void reload(ArrayList<New_RoomDawn> myDataset)
    {
        this.mNewRoomDawnList =myDataset;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_dawn_room_item, parent, false);

        return new CustomViewHolder(view);

    }
    private List<Allsimulations> getAllSimulations(String room_id) {

        Log.i("RoomIdDawn_getAllS ",""+room_id);
         ArrayList<Allsimulations> simulationsArrayList=new ArrayList<>();
        simulationsArrayList.clear();


       // List<Allsimulations> developerList = new ArrayList<>();
        try {
            JSONArray jsonArray = App.getAllSimulationData().getJSONArray("data");
            Log.e(TAG, "data_getSimulation" + jsonArray);
           // room_simulation.clear();


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Log.i("RoomIdDawn_Passed ",""+room_id);
                if(jsonObject.has("days")&&jsonObject.has("room")) {
                    if (jsonObject.getString("room").equals(room_id)) {
                        Log.i("DawnSimulation_id" + jsonObject.getString("Id"), " Room_id " + jsonObject.getString("room"));
                        Allsimulations simulations = new Allsimulations();
                        String str_days = "";

                        days_sorted.clear();
                        for (int j = 0; j < jsonObject.getJSONArray("days").length(); j++) {
                            days_sorted.add("" + jsonObject.getJSONArray("days").getInt(j));
                        }


                        Collections.sort(days_sorted, new Comparator<String>() {
                            @Override
                            public int compare(String o1, String o2) {
                                return o1.compareTo(o2);
                            }
                        });
                        for (int k = 0; k < days_sorted.size(); k++) {
                            str_days += "";
                            switch (days_sorted.get(k).toString()) {
                                case "0":
                                    str_days += ", Sun";
                                    break;
                                case "1":
                                    str_days += ", Mon";
                                    break;
                                case "2":
                                    str_days += ", Tue";
                                    break;
                                case "3":
                                    str_days += ", Wed";
                                    break;
                                case "4":
                                    str_days += ", Thu";
                                    break;
                                case "5":
                                    str_days += ", Fri";
                                    break;
                                case "6":
                                    str_days += ", Sat";
                                    break;


                            }

                        }


                        Log.i("RoomIdDawn_Jobj ", "" + jsonObject.getString("room"));


                        if (str_days.length() > 0) {
                            Log.i("Days",""+str_days.substring(1));
                            if (str_days.substring(1).equalsIgnoreCase(" Mon, Tue, Wed, Thu, Fri")) {
                                // //Toast.makeText(mContext, "Mon-Fri", Toast.LENGTH_SHORT).show();
                                simulations.set_days("Mon-Fri");
                            }
                            else  if (str_days.substring(1).equalsIgnoreCase(" Sun, Mon, Tue, Wed, Thu, Fri, Sat")) {
                                // //Toast.makeText(mContext, "Mon-Fri", Toast.LENGTH_SHORT).show();
                                simulations.set_days("All Days");
                            }
                            else {
                                simulations.set_days(str_days.substring(1));
                            }
                        } else {
                            simulations.set_days("");
                        }
                        String time = "";

                        if (new PrefManager(mContext).getValue("time_fomat").equals("1")) {
                          /*  time = jsonObject.getInt("hour") + ":" + jsonObject.getInt("minutes");*/


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

        } catch (Exception ex) {
            Log.e("------------jsonerror", "" + ex.getMessage());
        }
        Log.i("DawnSimulation_List",""+simulationsArrayList);
        return simulationsArrayList;
    }

}
