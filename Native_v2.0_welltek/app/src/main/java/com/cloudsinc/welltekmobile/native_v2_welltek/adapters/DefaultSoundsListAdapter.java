package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Playlist;
import org.json.JSONObject;
import java.util.ArrayList;
/**
 * Created by developers on 8/11/17.
 */
public class DefaultSoundsListAdapter extends RecyclerView.Adapter<DefaultSoundsListAdapter.ViewHolder> {
    private ArrayList<Playlist> arrayList;
    private Context mcontext;
    String device_id="";
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_playlist_title,txt_playlist_type,txt_playlist_id;
        ImageView img_playlist_album_art;
        LinearLayout lin_load_playlist;
        public ViewHolder(View v) {
            super(v);
            txt_playlist_title = v.findViewById(R.id.txt_playlist_title);
            txt_playlist_id = v.findViewById(R.id.txt_playlist_id);
            txt_playlist_type = v.findViewById(R.id.txt_playlist_type); // artist name
            img_playlist_album_art = v.findViewById(R.id.playlist_album_art); // artist name
            lin_load_playlist = v.findViewById(R.id.lin_load_playlist); // artist name
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public DefaultSoundsListAdapter(Context context, ArrayList<Playlist> myDataset, String device_id) {
        arrayList = myDataset;
        mcontext=context;
        this.device_id=device_id;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public DefaultSoundsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
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
        String str_album_art=sonos.getPlaylist_album_art();
        String str_playlist_title=sonos.getPlaylist_title();
        String str_playlist_type=sonos.getPlaylist_type();
        String str_playlist_id=sonos.getPlaylist_id();
        holder.txt_playlist_id.setText(str_playlist_id);
        holder.txt_playlist_title.setText(str_playlist_id);
        holder.txt_playlist_type.setText(str_playlist_type);
        Glide.with(mcontext).load(arrayList.get(position).getPlaylist_album_art())
                .thumbnail(Glide.with(mcontext).load(R.drawable.ic_playlist_default))
                .error(R.drawable.ic_playlist_default)
                .override(50,50)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img_playlist_album_art);
        holder.lin_load_playlist.setTag(position);
        holder.lin_load_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int pos=(int)v.getTag();
                load_playlist(arrayList.get(pos).getPlaylist_id());
            }
        });
        }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public void load_playlist(String playlist_id)
    {
        try {
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("udn_id",device_id);
            jsonObject1.put("id",playlist_id);
            JSONObject obj = new JSONObject();
            obj.put("data",jsonObject1);
            obj.put("type", "load_music");
            Log.e("SimulationRequest", "" + obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
