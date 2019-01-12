package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;

/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 30/3/17.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class ActivitesRoomTabletAdapter extends RecyclerView.Adapter<ActivitesRoomTabletAdapter.ViewHolder> {

    private Context activity;

    private ArrayList<Room> chooseRoomArrayList, time, roomtype, days;

    int roomState[];
    boolean favflag[];
    String scene_id="";
    ArrayList<String> room_ids=new ArrayList<>();
    String TAG=ActivitesRoomTabletAdapter.this.getClass().getName();

    /* TextView txtsimid,txttime,txtdays,txtroomtype;
     Switch simstatus;*/
    public ActivitesRoomTabletAdapter(Context a, ArrayList<Room> chooseRoomArrayList, String scene_id) {
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
        roomState=new int[chooseRoomArrayList.size()];

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
    public ActivitesRoomTabletAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity)
                .inflate(R.layout.activities_room_item_tablet, parent, false);
        ActivitesRoomTabletAdapter.ViewHolder vh = new ActivitesRoomTabletAdapter.ViewHolder(v);
        return vh;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final ActivitesRoomTabletAdapter.ViewHolder holder, int position) {


        Room room=chooseRoomArrayList.get(position);
        holder.txt_id.setText(""+room.getRoom_id());
        holder.txt_title.setText(""+room.getRoom_title());


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


        if(scene_id.equals("A")||scene_id.equals("B")||scene_id.equals("C"))
        {
            holder.img_add_fav.setVisibility(View.INVISIBLE);
        }


        if(scene_id.equals("1"))
        {

            if(getSceneId(room.getRoom_id()).equals("1"))
            {

                holder.rb_Choice.setChecked(true);
                if(roomState[position]==0)roomState[position]=1;
                holder.rb_Choice.setEnabled(false);
            }

        }
        else {
            if (getSceneId(room.getRoom_id()).equals(scene_id)) {

                holder.rb_Choice.setChecked(true);
                if(roomState[position]==0) roomState[position]=1;
            }
        }



        holder.rb_Choice.setTag(position);
        holder.rb_Choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (Integer) view.getTag();
                 Logs.error(TAG+"_FRadioButtonPosition",""+pos);

                 Logs.error(TAG+"_FRoomIds", "........" + room_ids);
                if(((CheckBox)view).isChecked()) {

                    roomState[pos]=1;
                    if(!room_ids.contains(chooseRoomArrayList.get(pos).getRoom_id()))
                    {

                        room_ids.add(chooseRoomArrayList.get(pos).getRoom_id());
                        holder.txt_title.setTextColor(activity.getResources().getColor(R.color.main_txt_white));

                    }
                }
                else
                {
                    roomState[pos]=-1;
                    room_ids.remove(room_ids.indexOf(chooseRoomArrayList.get(pos).getRoom_id()));
                    holder.txt_title.setTextColor(activity.getResources().getColor(R.color.off_white_text));
                }

                App.setRoom_ids(room_ids);

                App.getActive_button_subcriber().getObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(App.getActive_button_subcriber().getObserver());


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

        if(roomState[position]==1)
        {
            holder.rb_Choice.setChecked(true);
        }
        else
        {
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








}