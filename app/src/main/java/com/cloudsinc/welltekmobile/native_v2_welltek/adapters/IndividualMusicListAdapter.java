package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Music;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import org.json.JSONObject;
import java.util.ArrayList;
/**
 * This class containing functionality related to displaying individual songs from play list and also load this songs
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class IndividualMusicListAdapter extends RecyclerView.Adapter<IndividualMusicListAdapter.ViewHolder> {
    private ArrayList<Music> arrayList;
    private Context mcontext;
    String device_id="";
    String TAG=IndividualMusicListAdapter.this.getClass().getName();
    String playlist_id="";
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_music_title;
        TextView txt_music_id;
        TextView txt_music_artist;
        ImageView img_music_album_art;
        RelativeLayout rel_load_music;
        public ViewHolder(View v) {
            super(v);
            txt_music_title = v.findViewById(R.id.txt_music_title);
            txt_music_id = v.findViewById(R.id.txt_music_id);
            img_music_album_art = v.findViewById(R.id.img_music_album_art); // artist name
            rel_load_music = v.findViewById(R.id.lin_load_music); // artist name
            txt_music_artist = v.findViewById(R.id.txt_music_artist_name); // artist name
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public IndividualMusicListAdapter(Context context, ArrayList<Music> myDataset, String device_id,String playlist_id) {
        arrayList = myDataset;
        mcontext=context;
        this.device_id=device_id;
        this.playlist_id=playlist_id;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public IndividualMusicListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_music_individual_items, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Music music = arrayList.get(position);
        String str_music_title=music.getMusic_title();
        String str_music_id=music.getMusic_id();
        String str_music_artist=music.getMusic_artist();
        String str_music_album=music.getMusic_album();
         Logs.info(TAG+"_PlaylistTitle",""+str_music_title+""+str_music_id+""+str_music_album);
        holder.txt_music_id.setText(""+str_music_id);
        holder.txt_music_title.setText(""+str_music_title);
        holder.txt_music_artist.setText(""+str_music_artist+" \u2022 "+str_music_album);
        Glide.with(mcontext).load(arrayList.get(position).getMusic_album_art())
                .thumbnail(Glide.with(mcontext).load(R.drawable.ic_playlist_default))
                .error(R.drawable.ic_playlist_default)
                .override(50,50)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img_music_album_art);
        holder.rel_load_music.setTag(position);
        holder.rel_load_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                  load_music(arrayList.get(pos).getMusic_id());
            }
        });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    /**
     * set image to imagview
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param song_id string song id
     *
     */
    private void load_music(String song_id)
    {
        if(!device_id.equals("whole_home")) {
            try {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("music_id", song_id);
                jsonObject1.put("Id", device_id);
                jsonObject1.put("playlist_id", playlist_id);
                JSONObject obj = new JSONObject();
                obj.put("data", jsonObject1);
                obj.put("type", "load_Song");
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
                jsonObject1.put("music_id", song_id);
                JSONObject obj = new JSONObject();
                obj.put("data", jsonObject1);
                obj.put("type", "home_load_song");
                Logs.info(TAG + "_SimulationRequest", "" + obj);
                App.getSocket().emit("action", obj);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
