package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms.RoomDevicesMusicIndividualFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Playlist;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * Created by developers on 8/11/17.
 */
public class MusicPlayListAdapter extends RecyclerView.Adapter<MusicPlayListAdapter.ViewHolder> {
    private ArrayList<Playlist> arrayList;
    private Context mcontext;
    private Fragment fragment;
    int playStateState[];
    String device_id="";
    String current_play_song_title ="";
    String room_id="";
    String TAG=MusicPlayListAdapter.this.getClass().getSimpleName();
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_playlist_title;
        TextView txt_type;
        TextView txt_playlist_id;
        ImageView img_playlist_album_art;
        ImageView img_play_pause,img_playlist_loaded;
        LinearLayout lin_load_playlist;
        public ViewHolder(View v) {
            super(v);
            txt_playlist_title = v.findViewById(R.id.txt_playlist_title);
            txt_type = v.findViewById(R.id.txt_type);
            txt_playlist_id = v.findViewById(R.id.txt_playlist_id);
            img_playlist_album_art = v.findViewById(R.id.playlist_album_art); // artist name
            img_play_pause = v.findViewById(R.id.img_play_pause); // artist name
            img_playlist_loaded = v.findViewById(R.id.img_playlist_loaded); // artist name
            lin_load_playlist = v.findViewById(R.id.lin_load_playlist); // artist name
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public MusicPlayListAdapter(Context context, ArrayList<Playlist> myDataset, String device_id,Fragment fragment,String current_play_song_title) {
        arrayList = myDataset;
        mcontext=context;
        this.fragment=fragment;
        this.device_id=device_id;
        this.room_id=room_id;
        this.current_play_song_title =current_play_song_title;
        playStateState=new int[arrayList.size()];
        for (int i=0;i<arrayList.size();i++)
        {
            playStateState[i]=0;
        }
        for (Object o : App.getDefault_song_hash().keySet()) {
            String key = o.toString();
            String value = App.getDefault_song_hash().get(key);
        }
    }
     public void reload(ArrayList<Playlist> myDataset,String str_current_song_id) {
         this.arrayList = myDataset;
         this.current_play_song_title=str_current_song_id;
         playStateState=new int[arrayList.size()];
         for (int i=0;i<arrayList.size();i++)
         {
             playStateState[i]=0;
         }
     }
    // Create new views (invoked by the layout manager)
    @Override
    public MusicPlayListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_music_playlist_items, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Playlist sonos = arrayList.get(position);
        String str_playlist_title=sonos.getPlaylist_title();
        String str_playlist_id=sonos.getPlaylist_id();
        String str_type=sonos.getType();
         Logs.info(TAG+"_PlaylistTitle",current_play_song_title+""+str_playlist_title+""+str_playlist_id+""+str_type+"----"+sonos.isLoad());
        holder.txt_playlist_id.setText(""+str_playlist_id);
        holder.txt_playlist_title.setText(""+(App.getDefault_song_hash().containsKey(str_playlist_title)?App.getDefault_song_hash().get(str_playlist_title):str_playlist_title));
        holder.txt_type.setText(""+str_type);
        if(!(App.getDefault_song_hash().containsKey(current_play_song_title))) {
            if (sonos.isLoad()) {
                holder.img_play_pause.setImageDrawable(mcontext.getResources().getDrawable((getPlayingState("whole_home")||getPlayingState("other"))?R.drawable.ic_music_pause_white:R.drawable.ic_music_play_white));
                holder.img_playlist_loaded.setVisibility(View.VISIBLE);
            } else {
                holder.img_play_pause.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_music_play_white));
                holder.img_playlist_loaded.setVisibility(View.INVISIBLE);
            }
        }
        else
        {
           if(str_playlist_title.equals(current_play_song_title))
           {
               holder.img_play_pause.setImageDrawable(mcontext.getResources().getDrawable((getPlayingState("whole_home")||getPlayingState("other"))?R.drawable.ic_music_pause_white:R.drawable.ic_music_play_white));
               holder.img_playlist_loaded.setVisibility(View.VISIBLE);
           }
           else
           {
               holder.img_play_pause.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_music_play_white));
               holder.img_playlist_loaded.setVisibility(View.INVISIBLE);
           }
        }
        Glide.with(mcontext).load(arrayList.get(position).getPlaylist_album_art())
                .thumbnail(Glide.with(mcontext).load(R.drawable.ic_music_logo_blue))
                .error(R.drawable.ic_music_logo_blue)
                .override(50,50)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img_playlist_album_art);
        holder.lin_load_playlist.setTag(position);
        holder.img_play_pause.setTag(position);
        holder.img_playlist_loaded.setTag(position);
        holder.lin_load_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int pos=(int)v.getTag();
                TextView txt_playlist_id= v.findViewById(R.id.txt_playlist_id);
                TextView txt_type= v.findViewById(R.id.txt_type);
                TextView txt_playlist_title= v.findViewById(R.id.txt_playlist_title);
                Bundle bundle=new Bundle();
                bundle.putString("play_listid",txt_playlist_id.getText().toString());
                if(!(App.getDefault_song_hash().containsKey(arrayList.get(pos).getPlaylist_title())))soundSonos(txt_playlist_id.getText().toString(),txt_type.getText().toString(),arrayList.get(pos).getPlaylist_title());
            }
        });
        holder.img_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                setSelectedFlag();
                playStateState[pos]=1;
                load_playlist(arrayList.get(pos).getPlaylist_id(),arrayList.get(pos).getType(),pos);
            }
        });
        }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public void load_playlist(String str_uri,String type,int pos)
    {
        System.out.println("ssssssssssssssssssssssssss"+str_uri+"sssssssssssssssssssss"+type+"sssss"+pos+"sssssssss"+arrayList.get(pos).isLoad()+"sssssssss"+device_id);
        if(!device_id.equals("whole_home")) {
            if(!type.equals("default_music")) {

                boolean play_flag=false;
                if (arrayList.get(pos).isLoad()) {
                    play_flag=true;
                } else {
                    play_flag=false;
                }

                if(!play_flag) {
                    try {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("udn_id", device_id);
                        jsonObject1.put("id", str_uri);
                        JSONObject obj = new JSONObject();
                        obj.put("data", jsonObject1);
                        obj.put("type", "load_playlist");
                        Logs.info(TAG + "_not_default_SimulationRequest", "" + obj);
                        App.getSocket().emit("action", obj);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else
                {

                        try {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("Id", device_id);
                            JSONObject obj = new JSONObject();
                            obj.put("data", jsonObject1);
                            obj.put("type", getPlayingState("other")?"pause_song":"play_song");
                            Logs.info(TAG + "_not_default_SimulationRequest", "" + obj);
                            App.getSocket().emit("action", obj);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                }
            }
            else
            {
                boolean play_flag=false;
                if (arrayList.get(pos).getPlaylist_title().equals(current_play_song_title)) {
                    play_flag=true;
                } else {
                    play_flag=false;
                }
                if(!play_flag) {
                    try {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("Id", device_id);
                        jsonObject1.put("sound_id", str_uri);
                        JSONObject obj = new JSONObject();
                        obj.put("data", jsonObject1);
                        obj.put("type", "play_track");
                        Logs.info(TAG + "_SimulationRequest", "" + obj);
                        App.getSocket().emit("action", obj);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    try {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("Id", device_id);
                        JSONObject obj = new JSONObject();
                        obj.put("data", jsonObject1);
                        obj.put("type", getPlayingState("other")?"pause_song":"play_song");
                        Logs.info(TAG + "_SimulationRequest", "" + obj);
                        App.getSocket().emit("action", obj);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        else
        {
            if(!type.equals("default_music")) {
                boolean play_flag=false;

                if (arrayList.get(pos).isLoad()) {
                    play_flag=true;
                } else {
                    play_flag=false;
                }
                if(!play_flag) {
                    try {
                        App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_load_playlist", new JSONObject().put("id", str_uri)));
                    } catch (Exception ex) {
                        Logs.error(TAG, "----------" + ex.getMessage());
                    }
                }
                else
                {
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject(getPlayingState("whole_home")?"home_pause_song":"home_play_song", new JSONObject()));
                }
            }
            else
            {
                boolean play_flag=false;
                if (arrayList.get(pos).isLoad()) {
                    play_flag=true;
                } else {
                    play_flag=false;
                }
                if(!play_flag) {
                    try {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("sound_id", str_uri);
                        JSONObject obj = new JSONObject();
                        obj.put("data", jsonObject1);
                        obj.put("type", "home_play_track");
                        Logs.info(TAG + "_SimulationRequest", "" + obj);
                        App.getSocket().emit("action", obj);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject(getPlayingState("whole_home")?"home_pause_song":"home_play_song", new JSONObject()));
                }
            }
        }
    }
    public void soundSonos(String sound_id,String sound_type,String str_playlist_title)
    {
            App.setCurrentSubcriber(App.getMusic_playlist_subcriber());
            Bundle bundle=new Bundle();
            bundle.putString("playlist_id",sound_id);
            bundle.putString("playlist_title",str_playlist_title);
           Fragment fragment1= RoomDevicesMusicIndividualFragment.newInstance();
           fragment1.setArguments(bundle);
           if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(fragment,R.id.frm_music,fragment1);
            else replacePortailFrag(fragment1);
    }
    public void replacePortailFrag(Fragment mfragment)
    {
        FragmentTransaction fragmentTransaction = fragment.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frm_music, mfragment);
        fragmentTransaction.addToBackStack("RoomDevicesMusicFragment");
        fragmentTransaction.commit();
    }
    public void setSelectedFlag()
    {
        for(int i=0;i<arrayList.size();i++)
        {
            playStateState[i]=0;
        }
        notifyDataSetChanged();
    }

    public boolean getPlayingState(String str_request)
    {
        boolean b=false;
        try {
            if(!str_request.equals("whole_home")) {
                JSONArray jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
                Logs.info(TAG + "_PlayListArray", device_id + "" + jsonArray);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getString("Id").equals(device_id)) {
                        return jsonObject.getString("CML_STATE").equals("playing") ? true : false;
                    }
                }
            }
            else
            {
                JSONArray jsonArray = App.getZoneJson().getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getString("CML_TITLE").equals("Whole Home")) {
                        return jsonObject.getString("CML_STATE").equals("playing") ? true : false;
                    }
                }

            }
        }
        catch (Exception ex){
            Logs.info(TAG,"--------"+ex.getMessage());
        }
        return b;
    }
}
