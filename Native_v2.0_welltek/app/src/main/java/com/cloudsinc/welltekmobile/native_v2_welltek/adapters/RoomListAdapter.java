package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Room;
import java.util.ArrayList;

import static com.cloudsinc.welltekmobile.native_v2_welltek.adapters.ElementsRoomListAdapterPortrait.setImage;
/**
 * This class containing functionality related to displaying room list
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class RoomListAdapter extends BaseAdapter{
    private Context mContext;
    private  ArrayList<Room> roomArrayList;
    private  Fragment fragment;
    LayoutParams layoutparams;
    String TAG=this.getClass().getSimpleName();
    public RoomListAdapter(Context c, ArrayList<Room> roomArrayList, Fragment fragment) {
        mContext = c;
        this.roomArrayList=roomArrayList;
        this.fragment=fragment;
    }
    public void reload(ArrayList<Room> myDataset)
    {
        Log.e("WhereIM","I m in realod");
        this.roomArrayList=myDataset;
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return roomArrayList.size();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public View getView(int position, View grid, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (grid == null) {
            grid = inflater.inflate(R.layout.custom_room_list_item, null);
        }
            TextView txt_room_title = grid.findViewById(R.id.txt_room_title);
            TextView txt_room_id = grid.findViewById(R.id.txt_id);
            TextView txt_dawn_state = grid.findViewById(R.id.txt_dawn_state);
            LinearLayout lin_room_light = grid.findViewById(R.id.lin_room_lights);
            LinearLayout lin_room_audio = grid.findViewById(R.id.lin_room_audio);
            LinearLayout lin_room_blinds = grid.findViewById(R.id.lin_room_blinds);
            TextView txt_room_type = grid.findViewById(R.id.txt_room_type);
            ImageView img_room_back = grid.findViewById(R.id.img_room_back);
            TextView txt_no_device = grid.findViewById(R.id.txt_no_device);
            Room room=roomArrayList.get(position);
            txt_room_title.setText(room.getRoom_title());
            txt_room_id.setText(room.getRoom_id());
            txt_room_type.setText(room.getRoom_type());
            txt_dawn_state.setText(""+room.isDawn_state());
            setRoomBackImage(img_room_back,room.getRoom_type());
            boolean fag=false;
            if(room.getCnt_lights()>0) {fag=true;lin_room_light.setVisibility(View.VISIBLE); }else lin_room_light.setVisibility(View.GONE);
            if(room.getCnt_blinds()>0) { fag=true; lin_room_blinds.setVisibility(View.VISIBLE);}else lin_room_blinds.setVisibility(View.GONE);
            if(room.getCnt_audios()>0) { fag=true; lin_room_audio.setVisibility(View.VISIBLE);}else lin_room_audio.setVisibility(View.GONE);
            txt_no_device.setText(""+fag);
        return grid;
    }
    /**
     * set room backgroud according to type
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param img_room_background Imageview image will change on it.
     * @param type string
     */
    public void setRoomBackImage(ImageView img_room_background,String type)
    {
        switch(type)
        {
            case "Bedroom" :
                setImage(mContext,R.drawable.bg_masterbedroom,img_room_background,TAG);
            break;
           case "Living Room" :
              setImage(mContext,R.drawable.bg_livingroom,img_room_background,TAG);
            break;
            case "Outdoor" :
                  setImage(mContext,R.drawable.bg_masterbedroom,img_room_background,TAG);
                break;
            case "Kitchen" :
                  setImage(mContext,R.drawable.bg_kitchen,img_room_background,TAG);
                break;
            case "Bathroom" :
                  setImage(mContext,R.drawable.bg_masterbedroom,img_room_background,TAG);
                break;
            case "Family Room" :
                  setImage(mContext,R.drawable.bg_masterbedroom,img_room_background,TAG);
                break;
            case "Office" :
                  setImage(mContext,R.drawable.bg_masterbedroom,img_room_background,TAG);
                break;
            case "whole_home" :
                setImage(mContext,R.drawable.bg_masterbedroom,img_room_background,TAG);
                  /*img_room_background.setAlpha(0.3f);
                  setImage(mContext,R.drawable.bg_whole_home));*/
                break;
        }
    }
}
