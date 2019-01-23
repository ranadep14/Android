package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms.RoomDevicesFragmentPortrait;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Room;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import java.util.ArrayList;
/**
 * This class containing functionality related to displaying room list
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class ElementsRoomListAdapterPortrait extends RecyclerView.Adapter<ElementsRoomListAdapterPortrait.ViewHolder>{
    private Context mContext;
    private ArrayList<Room> roomArrayList;
    private  Fragment fragment;
    private static LayoutInflater inflater=null;
    private String TAG=ElementsRoomListAdapterPortrait.this.getClass().getName();
    private FragmentManager fm;
    private Context mactivity_context;
    public ElementsRoomListAdapterPortrait(Context c, ArrayList<Room> roomArrayList, Fragment fragment, FragmentManager fm, Context mactivity_context) {
        mContext = c;
        this.roomArrayList=roomArrayList;
        this.fragment=fragment;
        this.fm=fm;
        this.mactivity_context=mactivity_context;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_room_title ;
        TextView txt_room_id ;
        LinearLayout lin_room_light ;
        LinearLayout lin_room_audio ;
        LinearLayout lin_room_blinds ;
        RelativeLayout rel_main ;
        TextView txt_room_type ;
        ImageView img_room_back;
        TextView txt_no_device ;
        public ViewHolder(View v) {
            super(v);
            txt_room_title = v.findViewById(R.id.txt_room_title);
            rel_main = v.findViewById(R.id.rel_main);
            txt_room_id = v.findViewById(R.id.txt_id);
            lin_room_light = v.findViewById(R.id.lin_room_lights);
            lin_room_audio = v.findViewById(R.id.lin_room_audio);
            lin_room_blinds = v.findViewById(R.id.lin_room_blinds);
            txt_room_type = v.findViewById(R.id.txt_room_type);
            img_room_back = v.findViewById(R.id.img_room_back);
            txt_no_device = v.findViewById(R.id.txt_no_device);
        }
    }
    public void reload(ArrayList<Room> myDataset)
    {
        this.roomArrayList=myDataset;
    }
    @Override
    public int getItemCount() {
        return roomArrayList.size();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.elements_room_list_items, parent, false);
        ElementsRoomListAdapterPortrait.ViewHolder vh = new ElementsRoomListAdapterPortrait.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Room room=roomArrayList.get(position);
        holder.txt_room_title.setText(room.getRoom_title());
        holder.txt_room_id.setText(room.getRoom_id());
        holder.txt_room_type.setText(room.getRoom_type());
        setRoomBackImage(holder.img_room_back,room.getRoom_type());
        boolean fag=false;
        if(room.getCnt_lights()>0) {fag=true;holder.lin_room_light.setVisibility(View.VISIBLE); }else holder.lin_room_light.setVisibility(View.GONE);
        if(room.getCnt_blinds()>0) { fag=true; holder.lin_room_blinds.setVisibility(View.VISIBLE);}else holder.lin_room_blinds.setVisibility(View.GONE);
        if(room.getCnt_audios()>0) { fag=true; holder.lin_room_audio.setVisibility(View.VISIBLE);}else holder.lin_room_audio.setVisibility(View.GONE);
        holder.txt_no_device.setText(""+fag);
        holder.rel_main.setTag(position);
        holder.rel_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                TextView txt_id = view.findViewById(R.id.txt_id);
                TextView txt_room_title = view.findViewById(R.id.txt_room_title);
                TextView txt_room_type = view.findViewById(R.id.txt_room_type);
                TextView txt_no_device = view.findViewById(R.id.txt_no_device);
                 Logs.info(TAG+"_RoomDevice", txt_no_device.getText().toString());
                if (txt_no_device.getText().toString().equals("false")) {
                    // CustomDialogShow.dispDialogWithOK(mcontext, "No Devices Available");
                } else {
                    Room room = new Room();
                    room.setRoom_id(txt_id.getText().toString());
                    room.setRoom_title(txt_room_title.getText().toString());
                    room.setRoom_type(txt_room_type.getText().toString());
                    room.setCnt_audios(roomArrayList.get(pos).getCnt_audios());
                    room.setCnt_blinds(roomArrayList.get(pos).getCnt_blinds());
                    room.setCnt_lights(roomArrayList.get(pos).getCnt_lights());
                    App.setRoom(room);
                    Bundle bundle = new Bundle();
                    bundle.putString("CML_TITLE", txt_room_title.getText().toString());
                    bundle.putString("CML_ID", txt_id.getText().toString());
                    bundle.putString("CML_ROOM_TYPE", txt_room_type.getText().toString());
                    Fragment fragment = new RoomDevicesFragmentPortrait();
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction1 = fm.beginTransaction();
                    transaction1.replace(R.id.frm_main_container, fragment);
                    transaction1.addToBackStack(null);
                    transaction1.commit();
                     Logs.info(TAG+"_Room_Id", "" + txt_id.getText().toString());
                }
            }
        });
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    /**
     * method is for set room icon image
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param img_room_background imageview
     * @param type string room type.
     */
    private void setRoomBackImage(ImageView img_room_background,String type)
    {
        switch(type)
        {
            case "Bedroom" :
                setImage(mactivity_context,R.drawable.bg_masterbedroom,img_room_background,TAG);
                break;
            case "Living Room" :
                setImage(mactivity_context,R.drawable.bg_livingroom,img_room_background,TAG);
                break;
            case "Outdoor" :
                setImage(mactivity_context,R.drawable.bg_masterbedroom,img_room_background,TAG);
                break;
            case "Kitchen" :
                setImage(mactivity_context,R.drawable.bg_kitchen,img_room_background,TAG);
                break;
            case "Bathroom" :
                setImage(mactivity_context,R.drawable.bg_masterbedroom,img_room_background,TAG);
                break;
            case "Family Room" :
                setImage(mactivity_context,R.drawable.bg_masterbedroom,img_room_background,TAG);
                break;
            case "whole_home" :
                setImage(mactivity_context,R.drawable.bg_masterbedroom,img_room_background,TAG);
                /*img_room_background.setAlpha(0.3f);
                setImage(R.drawable.bg_whole_home,img_room_background);*/
                break;
        }
    }
    /**
     * set image to imagview
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param id int image id
     * @param imageView Imageview
     */
    public static void setImage(Context mactivity_context,int id,ImageView imageView,String call_from)
    {
        Logs.error("RoomListAdapterPotrait",""+call_from);
        if(call_from.equals("HomeHeathByZoneFragment") ) {
            Glide.with(mactivity_context).load(id)
                    .dontAnimate()
                    .placeholder(App.isOrientationFlag() ? R.drawable.home_baground_day : R.drawable.home_main_background_day_mob)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
        else
        {
            Glide.with(mactivity_context).load(id)
                    .dontAnimate()
                    .placeholder(R.drawable.bg_masterbedroom)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }
}