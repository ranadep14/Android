package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;

/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 30/3/17.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Room;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FavoritesOperations;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivitesRoomListAdapter extends RecyclerView.Adapter<ActivitesRoomListAdapter.ViewHolder> {

    private Context activity;

    private ArrayList<Room> chooseRoomArrayList, time, roomtype, days;

    int roomState[];
    boolean favflag[];

    String scene_id="";
    ArrayList<String> room_ids=new ArrayList<>();
    ArrayList<String> deselected_room_ids=new ArrayList<>();
    ArrayList<String> activated_room=new ArrayList<>();
    ArrayList<String> circadian_activated_room=new ArrayList<>();
    String TAG=ActivitesRoomListAdapter.this.getClass().getSimpleName();

    /* TextView txtsimid,txttime,txtdays,txtroomtype;
     Switch simstatus;*/
    public ActivitesRoomListAdapter(Context a, ArrayList<Room> chooseRoomArrayList, String scene_id) {
        activity = a;
        this.scene_id=scene_id;
        this.chooseRoomArrayList=chooseRoomArrayList;
        setRoomIdsArray();
        roomState=new int[chooseRoomArrayList.size()];
        for(int i=0;i<chooseRoomArrayList.size();i++)
        {
            roomState[i]=0;
        }
        favflag=new boolean[chooseRoomArrayList.size()];
        for(int i=0;i<chooseRoomArrayList.size();i++)
        {
            favflag[i]=false;
        }
        System.out.println("3333333333333333333"+"---"+scene_id+"----"+chooseRoomArrayList);




    }
    public void reload(ArrayList<Room> myDataset)
    {
        this.chooseRoomArrayList=myDataset;
        favflag =new boolean[chooseRoomArrayList.size()];
        for (int i=0;i<chooseRoomArrayList.size();i++)
        {
            favflag[i]=false;
        }

    }


    public  class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        TextView txt_id,txt_title;
        CheckBox rb_Choice;
        ImageView img_add_fav;
        public ViewHolder(View v) {
            super(v);
            txt_id = v.findViewById(R.id.txt_id); // title
            txt_title = v.findViewById(R.id.txt_title); // artist name
            rb_Choice = v.findViewById(R.id.rb_Choice);
            img_add_fav = v.findViewById(R.id.img_add_fav);
        }
    }






    @Override
    public ActivitesRoomListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity)
                .inflate(R.layout.activities_room_item, parent, false);
        ActivitesRoomListAdapter.ViewHolder vh = new ActivitesRoomListAdapter.ViewHolder(v);
        return vh;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final ActivitesRoomListAdapter.ViewHolder holder, int position) {


        final Room room=chooseRoomArrayList.get(position);
        holder.txt_id.setText(""+room.getRoom_id());
        holder.txt_title.setText(""+room.getRoom_title());


        if(room.getRoom_id().equals("0") && room.getRoom_title().equals("SELECT ALL"))
        {
            holder.txt_title.setGravity(Gravity.RIGHT);
            holder.img_add_fav.setVisibility(View.GONE);
        }
        else {
            if(!App.isOrientationFlag())holder.img_add_fav.setVisibility(View.VISIBLE);
            holder.txt_title.setGravity(Gravity.LEFT);
        }

        if(!room.getFav_id().equals(""))
        {
            favflag[position]=true;
            holder.img_add_fav.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_heart_fill));
        }
        else
        {
            favflag[position]=false;
            holder.img_add_fav.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_heart_unfill));

        }


        if(scene_id.equals("A")||scene_id.equals("B")||scene_id.equals("C")||scene_id.equals("8"))
        {
            holder.img_add_fav.setVisibility(View.INVISIBLE);
        }



        if (getSceneId(room.getRoom_id()).equals(scene_id)) {

            if(!activated_room.contains(room.getRoom_id()))activated_room.add(room.getRoom_id());
            holder.rb_Choice.setChecked(true);
            if(roomState[position]==0) roomState[position]=1;
            if(scene_id.equals("1")) {
                // holder.rb_Choice.setEnabled(false);
                if(!circadian_activated_room.contains(room.getRoom_id()))circadian_activated_room.add(room.getRoom_id());
            }
        }






        holder.rb_Choice.setTag(position);
        holder.txt_title.setTag(position);
        holder.rb_Choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (Integer) view.getTag();
                Logs.error(TAG+"_FRadioButtonPosition",""+pos);

                Logs.error(TAG+"_FRoomIds", "........" + room_ids);
                if(((CheckBox)view).isChecked()) {

                    if (chooseRoomArrayList.get(pos).getRoom_id().equals("0") ) {
                        room_ids=new ArrayList<>(getRoomIds());
                        room_ids.add("selected_all");
                        roomState[pos] = 1;
                        selectAll(true);
                    }
                    else {
                        roomState[pos] = 1;
                        if(deselected_room_ids.contains(chooseRoomArrayList.get(pos).getRoom_id()))deselected_room_ids.remove(deselected_room_ids.indexOf(chooseRoomArrayList.get(pos).getRoom_id()));
                        if (!room_ids.contains(chooseRoomArrayList.get(pos).getRoom_id())) {

                            room_ids.add(chooseRoomArrayList.get(pos).getRoom_id());

                        }
                    }
                }
                else
                {

                    if (chooseRoomArrayList.get(pos).getRoom_id().equals("0")) {
                        room_ids=new ArrayList<>();
                        roomState[pos] = -1;
                        selectAll(false);
                        deselected_room_ids=new ArrayList<>();
                        deselected_room_ids.addAll(activated_room);
                    }
                    else {
                        roomState[pos] = -1;
                        Log.i("activatedarray",""+activated_room);
                        room_ids.remove(room_ids.indexOf(chooseRoomArrayList.get(pos).getRoom_id()));
                        if(room_ids.contains("0")) room_ids.remove(room_ids.indexOf("0"));
                        if(room_ids.contains("selected_all")) room_ids.remove(room_ids.indexOf("selected_all"));
                        if(activated_room.contains(chooseRoomArrayList.get(pos).getRoom_id()))deselected_room_ids.add(chooseRoomArrayList.get(pos).getRoom_id());
                    }
                }



                if(chooseRoomArrayList.get(0).getRoom_id().equals("0")) {
                    if (getRoomState()) {
                        roomState[0] = 1;
                        room_ids=new ArrayList<>(getRoomIds());
                        room_ids.add("selected_all");

                    } else {
                        roomState[0] = -1;
                    }
                }
                Log.i("deselectarray",""+deselected_room_ids);
                App.setRoom_ids(room_ids);
                App.setDeselected_room_ids(deselected_room_ids);

                App.getActive_button_subcriber().getObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(App.getActive_button_subcriber().getObserver());
                notifyDataSetChanged();

            }
        });


        holder.txt_title.setTag(position);
        holder.txt_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (Integer) view.getTag();
                Logs.error(TAG + "_txtPosition", "" + pos);

                Logs.error(TAG + "_txtRoomIds", "........" + room_ids);


                if(!scene_id.equals("1")&&!circadian_activated_room.contains(room.getRoom_id())) {

                    holder.rb_Choice.performClick();
                }
            }
        });

        holder.img_add_fav.setTag(position);
        holder.img_add_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                if(favflag[pos])
                {

                    FavoritesOperations.deleteFav(chooseRoomArrayList.get(pos).getFav_id());
                }
                else
                {
                    FavoritesOperations.addSceneRoomFav(chooseRoomArrayList.get(pos).getRoom_id(),scene_id);

                }
            }
        });


        System.out.println("******************ccccccccccccc"+roomState[position]+"ccccccccccccccccccc"+chooseRoomArrayList.get(position).getRoom_id().equals("0")+"************"+chooseRoomArrayList.get(position).getRoom_scene().equals(""+chooseRoomArrayList.size()));
        if(roomState[0]!=-1&&chooseRoomArrayList.get(position).getRoom_id().equals("0")&&chooseRoomArrayList.get(position).getRoom_scene().equals(""+chooseRoomArrayList.size()))
        {
            roomState[0]=1;
            holder.rb_Choice.setChecked(true);
        }
        if(roomState[position]==1)
        {
            holder.txt_title.setTextColor(activity.getResources().getColor(R.color.main_txt_white));

            if(scene_id.equals("1")&&circadian_activated_room.contains(room.getRoom_id())) {
                holder.rb_Choice.setClickable(false);
                holder.txt_title.setClickable(false);
            }
            holder.rb_Choice.setChecked(true);
            holder.txt_title.setClickable(true);

        }
        else
        {
            holder.txt_title.setTextColor(activity.getResources().getColor(R.color.off_white_text));

            holder.rb_Choice.setChecked(false);
        }


    }


    @Override
    public int getItemCount() {
        return chooseRoomArrayList.size();
    }



    public String getSceneId(String room_id)
    {

        String scene_id="";
        try {


            JSONArray jsonArray = App.getRoomDeviceData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("CML_ID").equals(room_id)) {
                    scene_id = jsonObject.getString("CML_SCENE_ID");
                }
            }
        }
        catch (Exception ex){ Logs.error(TAG+"_FError",ex.getMessage());}
        return scene_id;

    }

    public void setRoomIdsArray()
    {
        for(int i=0;i<chooseRoomArrayList.size();i++)
        {
            Room room=chooseRoomArrayList.get(i);
            if (getSceneId(room.getRoom_id()).equals(scene_id)) {
                if (!room_ids.contains(room.getRoom_id())) {
                    room_ids.add(room.getRoom_id());
                }
            }
        }
    }



    public ArrayList<String> getRoomIds()
    {
        ArrayList<String>  stringArrayList=new ArrayList<>();
        for(int i=0;i<chooseRoomArrayList.size();i++)
        {
            Room room=chooseRoomArrayList.get(i);
            stringArrayList.add(room.getRoom_id());
        }
        return stringArrayList;
    }


    public void selectAll(boolean b)
    {
        for (int i=0;i<chooseRoomArrayList.size();i++)
        {
            if(!scene_id.equals("1")) { roomState[i]=b?1:-1;}
            else
            {
                if (!circadian_activated_room.contains(chooseRoomArrayList.get(i).getRoom_id()))roomState[i]=b?1:-1;
            }
        }

    }



    public boolean getRoomState()
    {
        boolean b=false;
        int count=0;
        for (int i=1;i<roomState.length;i++)
        {
            if(roomState[i]==1)
            {
                count++;
            }
        }
        Logs.info(TAG,"-----------------"+count+"----------------"+(roomState.length-1));
        if(count==roomState.length-1)b=true;
        return b;
    }



}