package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;

/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 30/3/17.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Room;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FavriotesAllRoomListAdapter extends BaseAdapter {

    private Context activity;
    private TextView txt_title;
    private TextView txt_id;
    private CheckBox rb_choice;
    private ArrayList<Room> chooseRoomArrayList, time, roomtype, days;
    private static LayoutInflater inflater=null;
    int roomState[];
    String TAG=FavriotesAllRoomListAdapter.this.getClass().getName();

    String scene_id="";
    ArrayList<String> room_ids=new ArrayList<>();

    /* TextView txtsimid,txttime,txtdays,txtroomtype;
     Switch simstatus;*/
    public FavriotesAllRoomListAdapter(Context a, ArrayList<Room> chooseRoomArrayList,String scene_id) {
        activity = a;
        this.scene_id=scene_id;
        this.chooseRoomArrayList=chooseRoomArrayList;
        setRoomIdsArray();
        roomState=new int[chooseRoomArrayList.size()];
        for(int i=0;i<chooseRoomArrayList.size();i++)
        {
            roomState[i]=0;
        }



        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public void reload(ArrayList<Room> myDataset)
    {
        this.chooseRoomArrayList=myDataset;

    }

    public int getCount() {
        return chooseRoomArrayList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        if (convertView == null) {
            vi = inflater.inflate(R.layout.fav_room_item, null);

            rb_choice = vi.findViewById(R.id.rb_Choice);
            txt_id = vi.findViewById(R.id.txt_id); // title
            txt_title = vi.findViewById(R.id.txt_title); // artist name

            rb_choice.setOnCheckedChangeListener(null);
            rb_choice.setTag(position);

        }
        Room room = chooseRoomArrayList.get(position);
        txt_id.setText("" + room.getRoom_id());
        txt_title.setText("" + room.getRoom_title());
        room_ids = App.getRoom_ids();




        Logs.info(TAG+"_Scene_id",scene_id+"----"+getSceneId(room.getRoom_id()));

        if(scene_id.equals("1"))
        {
            if (getSceneId(room.getRoom_id()).equals("1")) {
                if (!room_ids.contains(room.getRoom_id())) {
                    Logs.info(TAG+"_Scene_idaaaa",scene_id+"----"+getSceneId(room.getRoom_id()));
                    rb_choice.setChecked(true);
                    rb_choice.setEnabled(false);
                    room_ids.add(room.getRoom_id());
                }
            }

        }
        else {

            if (getSceneId(room.getRoom_id()).equals(scene_id)) {
                if (!room_ids.contains(room.getRoom_id())) {
                    Logs.info(TAG+"_Scene_idbbb",scene_id+"----"+"----"+room.getRoom_id()+"----"+position);
                    rb_choice.setChecked(true);
                    room_ids.add(room.getRoom_id());


                }
            }

        }

        /***************/
        //   rb_choice.setOnCheckedChangeListener(rb_choice);


        rb_choice.setTag(position);
        txt_title.setTag(position);

        rb_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (Integer) view.getTag();
                Logs.info(TAG+"_RadioButtonPosition",""+pos);

                Logs.info(TAG+"_RoomIds", "........" + room_ids);
                if(((CheckBox)view).isChecked()) {
                    if(!room_ids.contains(chooseRoomArrayList.get(pos).getRoom_id()))
                    {

                        chooseRoomArrayList.get(pos).getRoom_title();
                        txt_title.setTextColor(activity.getResources().getColor(R.color.main_txt_white));
                        room_ids.add(chooseRoomArrayList.get(pos).getRoom_id());

                    }
                }
                else
                {
                    txt_title.setTextColor(activity.getResources().getColor(R.color.off_white_text));
                    room_ids.remove(room_ids.indexOf(chooseRoomArrayList.get(pos).getRoom_id()));
                }

                App.setRoom_ids(room_ids);
                App.getActive_button_subcriber().getObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(App.getActive_button_subcriber().getObserver());

            }
        });


        return vi;
    }


    public String getSceneId(String room_id)
    {

        String scene_id="";
        try {


            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("CML_ID").equals(room_id)) {
                    scene_id = jsonObject.getString("CML_SCENE_ID");
                }
            }
        }
        catch (Exception ex){ Logs.info(TAG+"_Error",ex.getMessage());
        }
        return scene_id;

    }
    public void setRoomIdsArray() {
        for (int i = 0; i < chooseRoomArrayList.size(); i++) {
            Room room = chooseRoomArrayList.get(i);
            if (getSceneId(room.getRoom_id()).equals(scene_id)) {
                if (!room_ids.contains(room.getRoom_id())) {
                    room_ids.add(room.getRoom_id());
                }
            }

        }
    }
}


