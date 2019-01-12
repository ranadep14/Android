package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms.RoomDevicesMusicFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Device;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Sonos;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FavoritesOperations;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.json.JSONObject;
import java.util.ArrayList;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.types.DevicesByType.checkDawnSleep;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.FavoritesOperations.getRoomId;
/**
 * This class containing functionality related to displaying sonos list
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {
    private ArrayList<Sonos> arrayList;
    private Context mcontext;
    private  FragmentManager mfm;
    private boolean shuffleflag[];
    private boolean muteflag[];
    private boolean repeatflag[];
    private boolean favflag[];
    Activity mactivity;
    int playState[],bightState[];
    String REPEAT="repeat",SHUFFLE="shuffle",SHUFFLE_NOREPEAT="shuffle_no_repeat",NORMAL="normal",call_from="";
    Fragment mfrag;
    String TAG=MusicListAdapter.this.getClass().getName();
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class ViewHolder extends RecyclerView.ViewHolder {
         TextView txt_muisc_artist_name=null;
         TextView txt_id=null;
         TextView txt_music_title=null;
         TextView txt_device_title=null;
         TextView txt_room_title=null;
         DiscreteSeekBar seek_music_vol=null;
         ImageView img_add_fav=null;
         ImageView img_music_play_pause=null;
         ImageView img_music_prev=null;
         ImageView img_music_next=null;
         ImageView img_music_mute=null;
         ImageView img_music_shuffule=null;
         ImageView img_playlist_icon=null;
         ImageView img_album_image=null;
         ImageView img_music_repeat=null;
         ImageView img_playlist=null;
         LinearLayout lin_no_playlist=null;
         LinearLayout lin_title_name=null;
         LinearLayout lin_music_bottom=null;
         Button btn_load_playlist=null;
        public ViewHolder(View v) {
            super(v);
            txt_muisc_artist_name = v.findViewById(R.id.txt_muisc_artist_name); // title
            txt_id = v.findViewById(R.id.txt_id);
              txt_music_title = v.findViewById(R.id.txt_music_title); // artist name
              seek_music_vol = v.findViewById(R.id.seek_music_vol); // artist name
              img_music_play_pause = v.findViewById(R.id.img_music_play_pause);
              txt_device_title = v.findViewById(R.id.txt_device_title);
              txt_room_title = v.findViewById(R.id.txt_room_title);
              img_music_prev = v.findViewById(R.id.img_music_prev);
              img_music_next = v.findViewById(R.id.img_music_next);
              img_album_image = v.findViewById(R.id.img_album_image);
              img_playlist = v.findViewById(R.id.img_playlist);
              img_music_mute = v.findViewById(R.id.img_music_mute);
              img_music_shuffule = v.findViewById(R.id.img_music_shuffule);
              img_music_repeat = v.findViewById(R.id.img_music_repeat);
              lin_no_playlist = v.findViewById(R.id.lin_no_playlist);
            lin_music_bottom = v.findViewById(R.id.lin_music_bottom);
            lin_title_name = v.findViewById(R.id.lin_title_name);
            img_add_fav = v.findViewById(R.id.img_add_fav);
            btn_load_playlist = v.findViewById(R.id.btn_load_playlist);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public MusicListAdapter(Context context, ArrayList<Sonos> myDataset, FragmentManager fm, Fragment mfrag,String call_from) {
        arrayList = myDataset;
        mcontext=context;
        this.mfrag=mfrag;
        this.call_from=call_from;
        mfm=fm;
         Logs.info(TAG+"_MusicArrayItem",""+myDataset);
        //mactivity=activity;
        playState=new int[arrayList.size()];
        for (int i=0;i<arrayList.size();i++)
        {
            playState[i]=0;
        }
        shuffleflag=new boolean[arrayList.size()];
        for (int i=0;i<arrayList.size();i++)
        {
            shuffleflag[i]=false;
        }
        muteflag=new boolean[arrayList.size()];
        for (int i=0;i<arrayList.size();i++)
        {
            muteflag[i]=false;
        }
        repeatflag=new boolean[arrayList.size()];
        for (int i=0;i<arrayList.size();i++)
        {
            repeatflag[i]=false;
        }
        favflag =new boolean[arrayList.size()];
        for (int i=0;i<arrayList.size();i++)
        {
            favflag[i]=false;
        }
    }
    // Create new views (invoked by the layout manager)
    @Override
    public MusicListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_music_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Sonos sonos = arrayList.get(position);
        String str_artist_name=sonos.getSonos_artist_name();
        String str_track_title=sonos.getSonos_track_title();
         Logs.info(TAG+"_Sonos_id", "" + sonos.getSonos_id());
         Logs.info(TAG+"_SonosTitleeeeeeeeeee", "" +sonos.isMute_flag()+""+str_track_title+"state"+str_artist_name+"---"+sonos.getSonos_song_play_pause_state());
        if(str_track_title.equals(mcontext.getResources().getString(R.string.song_title))&str_artist_name.equals(mcontext.getResources().getString(R.string.artist_name)))
        {
            holder.lin_music_bottom.setVisibility(View.GONE);
            holder.lin_no_playlist.setVisibility(View.VISIBLE);
            holder.lin_title_name.setVisibility(View.GONE);
        }
        else
        {
            holder.lin_music_bottom.setVisibility(View.VISIBLE);
            holder.lin_no_playlist.setVisibility(View.GONE);
            holder.lin_title_name.setVisibility(View.VISIBLE);
        }
        holder.txt_room_title.setText(sonos.getRoom_title());
        holder.txt_room_title.setVisibility(call_from.equals("device_type")?View.VISIBLE:View.GONE);
        holder.txt_muisc_artist_name.setText(str_artist_name.equals("null")?"":str_artist_name);
        holder.txt_id.setText(sonos.getSonos_id());
        holder.txt_music_title.setText(""+(App.getDefault_song_hash().containsKey(sonos.getSonos_track_title())?App.getDefault_song_hash().get(sonos.getSonos_track_title()):sonos.getSonos_track_title()));
        holder.seek_music_vol.setProgress(sonos.getSonos_volume());
        holder.txt_device_title.setText(sonos.getDevice_title());
        if(sonos.isShuffle_flag()) {
            shuffleflag[position]=true;
            holder.img_music_shuffule.setImageResource(R.drawable.shuffle_new);
        }
        else
        {
            shuffleflag[position]=false;
            holder.img_music_shuffule.setImageResource(R.drawable.ic_shuffle_faint);
        }
        if(sonos.isRepeat_flag()) {
            repeatflag[position]=true;
            holder.img_music_repeat.setImageResource(R.drawable.ic_repeat);
        }
        else
        {
            repeatflag[position]=false;
            holder.img_music_repeat.setImageResource(R.drawable.ic_repeat_faint);
        }
        if(sonos.isShuffle_flag()&&sonos.isRepeat_flag()) {
            shuffleflag[position]=true;
            repeatflag[position]=true;
            holder.img_music_repeat.setImageResource(R.drawable.shuffle_new);
            holder.img_music_repeat.setImageResource(R.drawable.ic_repeat);
        }
        if(!sonos.isShuffle_flag()&&!sonos.isRepeat_flag()) {
            shuffleflag[position]=false;
            repeatflag[position]=false;
            holder.img_music_repeat.setImageResource(R.drawable.ic_repeat_faint);
            holder.img_music_shuffule.setImageResource(R.drawable.ic_shuffle_faint);
        }
        if(sonos.isNext_flag())
        {
            holder.img_music_next.setAlpha(0.5f);
            holder.img_music_next.setClickable(false);
        }
        else
        {
            holder.img_music_next.setAlpha(1f);
            holder.img_music_next.setClickable(true);
        }
        if(App.getDefault_song_hash().containsKey(sonos.getSonos_track_title()))
        {
            holder.img_music_next.setAlpha(0.5f);
            holder.img_music_next.setClickable(false);
            holder.img_music_prev.setAlpha(0.5f);
            holder.img_music_prev.setClickable(false);
        }
        if(sonos.isPrev_flag())
        {
            holder.img_music_prev.setAlpha(0.5f);
            holder.img_music_prev.setClickable(false);
        }
        else
        {
            holder.img_music_prev.setAlpha(1f);
            holder.img_music_prev.setClickable(true);
        }
        holder.img_music_prev.setTag(position);
        holder.img_music_next.setTag(position);
        holder.seek_music_vol.setTag(position);
        holder.img_music_repeat.setTag(position);
        holder.img_music_shuffule.setTag(position);
        holder.img_music_mute.setTag(position);
        Glide.with(mcontext).load(arrayList.get(position).getSonos_album_art())
                .placeholder(R.drawable.ic_music_logo_blue)
                .override(50,50)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img_album_image);
        holder.img_add_fav.setTag(position);
        if(arrayList.get(position).getFav_id().equals(""))
        {
            favflag[position]=false;
            holder.img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
        }
        else
        {
            favflag[position]=true;
            holder.img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
        }
        holder.img_add_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                if(!checkDawnSleep(getRoomId(arrayList.get(pos).getSonos_id()),mcontext)) {
                    if (favflag[pos]) {
                        FavoritesOperations.deleteFav(arrayList.get(pos).getFav_id());
                        holder.img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
                    } else {
                        FavoritesOperations.addFav(arrayList.get(pos).getDevice_id());
                        favflag[pos] = true;
                        holder.img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                    }
                }
            }
        });
        holder.img_music_play_pause.setTag(position);
        if(sonos.getSonos_song_play_pause_state().equals("playing"))
        {
            playState[position]=1;
            holder.img_music_play_pause.setImageResource(R.drawable.ic_music_pause_blue);
        }
        else
        {
            holder.img_music_play_pause.setImageResource(R.drawable.ic_playlist);
        }
        if(sonos.isMute_flag())
        {
            muteflag[position]=true;
            holder.img_music_mute.setImageResource(R.drawable.ic_mute);
        }
        else
        {
            muteflag[position]=false;
            holder.img_music_mute.setImageResource(R.drawable.ic_un_mute);
        }
        holder.img_music_play_pause.setTag(position);
        holder.img_music_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos =(int)v.getTag();
                if(!checkDawnSleep(getRoomId(arrayList.get(pos).getSonos_id()),mcontext)) {
                    App.setMusic_change(true);
                    Logs.info(TAG + "_WhereIm", "InIMGPLAYPAuse" + pos);
                    int play = playState[pos];
                    if (play == 0) {
                        //  img_music_play_pause.setImageResource(R.drawable.ic_music_pause);
                        playState[pos] = 1;
                    } else {
                        //  img_music_play_pause.setImageResource(R.drawable.ic_music_play_white);
                        playState[pos] = 0;
                    }
                    playPauseMusic(play, arrayList.get(pos).getSonos_id());
                }
            }
        });
        holder.img_music_prev.setTag(position);
        holder.img_music_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getAlpha()==1f) {
                    int pos =(int)v.getTag();
                    if(!checkDawnSleep(getRoomId(arrayList.get(pos).getSonos_id()),mcontext)) {
                        App.setMusic_change(true);
                        setPrvious(arrayList.get(pos).getSonos_id());
                    }
                }
            }
        });
        holder.img_playlist.setTag(position);
        holder.img_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                Device device=new Device();
                device.setDevice_id(arrayList.get(pos).getSonos_id());
                App.setDevice(device);
                Bundle bundle=new Bundle();
                bundle.putString("call_from",call_from);
                App.setTemp_bundle(bundle);
              if(App.isOrientationFlag())  ReplaceFragment.replaceFragment(mfrag,R.id.frm_elemenmain_container, RoomDevicesMusicFragment.newInstance());
                else replacePortailFrag();
            }
        });
        holder.btn_load_playlist.setTag(position);
        holder.btn_load_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos =(int)v.getTag();
                if(!checkDawnSleep(getRoomId(arrayList.get(pos).getSonos_id()),mcontext)) {
                    Device device = new Device();
                    device.setDevice_id(arrayList.get(pos).getSonos_id());
                    App.setDevice(device);
                    Bundle bundle = new Bundle();
                    bundle.putString("call_from", call_from);
                    App.setTemp_bundle(bundle);
                    if (App.isOrientationFlag())
                        ReplaceFragment.replaceFragment(mfrag, R.id.frm_elemenmain_container, RoomDevicesMusicFragment.newInstance());
                    else replacePortailFrag();
                }
            }
        });
        holder.img_music_next.setTag(position);
        holder.img_music_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getAlpha()==1f) {
                    int pos =(int)v.getTag();
                    if(!checkDawnSleep(getRoomId(arrayList.get(pos).getSonos_id()),mcontext)) {
                        App.setMusic_change(true);
                        setMusicNext(arrayList.get(pos).getSonos_id());
                    }
                }
            }
        });
        holder.img_music_mute.setTag(position);
        holder.img_music_mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(Integer)view.getTag();
                if(!checkDawnSleep(getRoomId(arrayList.get(pos).getSonos_id()),mcontext)) {
                    boolean mute_status = muteflag[pos];
                    if (mute_status) {
                        holder.img_music_mute.setImageResource(R.drawable.ic_un_mute);
                        setMute(arrayList.get(pos).getSonos_id(), false);
                    } else {
                        holder.img_music_mute.setImageResource(R.drawable.ic_mute);
                        setMute(arrayList.get(pos).getSonos_id(), true);
                    }
                }
            }
        });
        holder.img_music_repeat.setTag(position);
        holder.img_music_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                if(!checkDawnSleep(getRoomId(arrayList.get(pos).getSonos_id()),mcontext)) {
                    Logs.info(TAG + "_Shuffle_repeat", repeatflag[pos] + "" + shuffleflag[pos]);
                    if (shuffleflag[pos] && !repeatflag[pos]) {
                        repeatflag[pos] = true;
                        holder.img_music_repeat.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_repeat));
                        musicAction(SHUFFLE, arrayList.get(pos).getSonos_id());
                    } else {
                        if (!repeatflag[pos]) {
                            repeatflag[pos] = true;
                            holder.img_music_repeat.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_repeat));
                            musicAction(REPEAT, arrayList.get(pos).getSonos_id());
                        } else {
                            repeatflag[pos] = false;
                            holder.img_music_repeat.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_repeat_faint));
                            musicAction(NORMAL, arrayList.get(pos).getSonos_id());
                            if (shuffleflag[pos]) {
                                musicAction(SHUFFLE_NOREPEAT, arrayList.get(pos).getSonos_id());
                            }
                        }
                    }
                }
            }
        });
        holder.img_music_shuffule.setTag(position);
        holder.img_music_shuffule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                if(!checkDawnSleep(getRoomId(arrayList.get(pos).getSonos_id()),mcontext)) {
                    if (!shuffleflag[pos] && repeatflag[pos]) {
                        shuffleflag[pos] = true;
                        holder.img_music_shuffule.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.shuffle_new));
                        musicAction(SHUFFLE, arrayList.get(pos).getSonos_id());
                    } else {
                        if (!shuffleflag[pos]) {
                            shuffleflag[pos] = true;
                            holder.img_music_shuffule.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.shuffle_new));
                            musicAction(SHUFFLE_NOREPEAT, arrayList.get(pos).getSonos_id());
                        } else {
                            shuffleflag[pos] = false;
                            holder.img_music_shuffule.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_shuffle_faint));
                            musicAction(NORMAL, arrayList.get(pos).getSonos_id());
                            if (repeatflag[pos]) {
                                musicAction(REPEAT, arrayList.get(pos).getSonos_id());
                            }
                        }
                    }
                }
            }
        });
        holder.seek_music_vol.setTag(position);
        holder.seek_music_vol.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                int pos=(Integer)seekBar.getTag();
                if(!checkDawnSleep(getRoomId(arrayList.get(pos).getSonos_id()),mcontext)) {
                    setMusicVolume(arrayList.get(pos).getSonos_id(), seekBar.getProgress());
                }
            }
        });
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    /**
     * set sonos valume
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param device_id string device id
     * @param music_volume int volume 0-100
     */
    public void setMusicVolume(String device_id, int music_volume)
    {
        try {
            JSONObject obj = new JSONObject();
            obj.put("data",new JSONObject().put("Id",device_id).put("percent",music_volume));
            obj.put("type", "volume_song");
            App.getSocket().emit("action", obj);
            Log.d("SimulationRequestSend", "" + obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * make sonos mute or unmute
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param status boolean
     * @param device_id string device_id
     */
    public void setMute(String device_id,boolean status)
    {
        try {
            JSONObject obj = new JSONObject();
            obj.put("data", new JSONObject().put("Id",""+device_id).put("status",status));
            obj.put("type", "mute_song");
            Log.d("SimulationRequestSend", "" + obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
             Logs.error(TAG+"_PlayJson", "" + ex.getMessage());
        }
    }
    /**
     * next song request
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param device_id int device id
     */
    private void setMusicNext(String device_id)
    {
        try {
            JSONObject obj = new JSONObject();
            obj.put("data", new JSONObject().put("Id",""+device_id));
            obj.put("type", "next_song");
            Log.d("SimulationRequestSend", "" + obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
             Logs.error(TAG+"_PlayJson", "" + ex.getMessage());
        }
    }
    /**
     * previous song request
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param device_id string device id
     */
    private void setPrvious(String device_id)
    {
        try {
            JSONObject obj = new JSONObject();
            obj.put("data", new JSONObject().put("Id",""+device_id));
            obj.put("type", "previous_song");
            Log.d("SimulationRequestSend", "" + obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
             Logs.info(TAG+"_PlayJson", "" + ex.getMessage());
        }
    }
    /**
     * set image to imagview
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param play int image id
     * @param device_id string device_id
     */
    private void playPauseMusic(int play,String device_id)
    {
        if(play==0)
        {
            try {
                JSONObject obj = new JSONObject();
                obj.put("data", new JSONObject().put("Id",""+device_id));
                obj.put("type", "play_song");
                Log.d("SimulationRequestSend", "" + obj);
                App.getSocket().emit("action", obj);
            } catch (Exception ex) {
                 Logs.error(TAG+"_PlayJson", "" + ex.getMessage());
            }
        }
        else
        {
            try {
                JSONObject obj = new JSONObject();
                obj.put("data", new JSONObject().put("Id",""+device_id));
                obj.put("type", "pause_song");
                Log.d("SimulationRequestSend", "" + obj);
                App.getSocket().emit("action", obj);
            } catch (Exception ex) {
                 Logs.error(TAG+"_PlayJson", "" + ex.getMessage());
            }
        }
    }
    /**
     * performs actions on sonos
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param str_action string actions like SUFFLE/REPEAT etc
     * @param device_id string sonos id
     */
    private void musicAction(String str_action,String device_id)
    {
        try {
            JSONObject jsonData=new JSONObject();
            switch (str_action)
            {
                case "shuffle":
                    jsonData.put("mode","SHUFFLE");
                    break;
                case "normal":
                    jsonData.put("mode","NORMAL");
                    break;
                case "repeat":
                    jsonData.put("mode","REPEAT_ALL");
                    break;
                case "shuffle_no_repeat":
                    jsonData.put("mode","SHUFFLE_NOREPEAT");
                    break;
            }
            jsonData.put("Id",device_id);
            JSONObject obj = new JSONObject();
            obj.put("data", jsonData);
            obj.put("type", "set_playmode");
            Log.d("SimuMusicRequestSend", "" + obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
             Logs.error(TAG+"_PlayJson", "" + ex.getMessage());
        }
    }
    /**
     * replace fragment
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     */
    private void replacePortailFrag()
    {
        FragmentTransaction fragmentTransaction = mfrag.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frm_main_container, RoomDevicesMusicFragment.newInstance());
        fragmentTransaction.addToBackStack("RoomDevicesMusicFragment");
        fragmentTransaction.commit();
    }
}
