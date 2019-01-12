package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Sonos;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import org.json.JSONObject;
import java.util.List;
public class CustomMusicPlaylistAdapter extends BaseAdapter {
    private Context activity;
    private DialogFragment mdm;
    private List<Sonos> sonosArrayList;
    private static LayoutInflater inflater=null;
    private int selectedPosition = -1;
    Fragment mfrag;
    String device_id="";
    String call_from="";
    int playStateState[];
    String TAG=CustomMusicPlaylistAdapter.this.getClass().getName();
    public CustomMusicPlaylistAdapter(Context context, List<Sonos> sonosArrayList, String deviceId, DialogFragment dm, String mcall_from){
        mdm=dm;
        call_from=mcall_from;
        activity = context;
        this.sonosArrayList=sonosArrayList;
         Logs.error(TAG+"_sonosarraulist",""+sonosArrayList.size());
        playStateState=new int[sonosArrayList.size()];
        for (int i=0;i<sonosArrayList.size();i++)
        {
            playStateState[i]=0;
        }
            device_id = deviceId;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return sonosArrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // If holder not exist then locate all view from UI file.
        if (convertView == null) convertView = inflater.inflate(R.layout.music_list_item, null);
            // get all UI view
           ImageView img_play = convertView.findViewById(R.id.chb_mark);
        ImageView  img_album_image = convertView.findViewById(R.id.img_album_image);
          TextView  txt_playlist_title = convertView.findViewById(R.id.txt_playlist_title);
        TextView  txt_playlist_id = convertView.findViewById(R.id.txt_playlist_id);
        TextView  txt_playlist_url = convertView.findViewById(R.id.txt_playlist_url);
        LinearLayout  lin_playlist_item= convertView.findViewById(R.id.lin_playlist_item);
         Logs.error(TAG+"_Sonos_Track_tile",""+sonosArrayList.get(position).getSonos_track_title());
        img_play.setTag(position); // This line is important.
        txt_playlist_title.setText(sonosArrayList.get(position).getSonos_track_title());
        txt_playlist_id.setText(sonosArrayList.get(position).getSonos_id());
        txt_playlist_url.setText(sonosArrayList.get(position).getSonos_uri());
        Glide.with(activity).load(sonosArrayList.get(position).getSonos_album_art())
                .thumbnail(Glide.with(activity).load(R.drawable.ic_playlist_default))
                .error(R.drawable.ic_playlist_default)
                .override(50,50)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_album_image);
        if(sonosArrayList.get(position).isSelect_flag())
        {
            img_play.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_music_pause_black));
        }
        else
        {
            img_play.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_playlist));
        }
        lin_playlist_item.setOnClickListener(onStateChangedListener(txt_playlist_id,txt_playlist_url,img_play, position));
        //  holder.img_play.setOnClickListener(onStateChangedListener(holder.txt_music_id,holder.txt_playlist_url,holder.img_play, position));
        return convertView;
    }
    private View.OnClickListener onStateChangedListener(final TextView txt_id, final TextView txt_url, final ImageView checkBox, final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Logs.error(TAG+"_MusicLoadCall","---------------"+txt_id.getText().toString()+"----------------"+txt_url.getText().toString());
               // App.setPlayListIndex(position);
                playStateState[position]=1;
                selectedPosition = position;
                load_playlist(txt_id.getText().toString(),txt_url.getText().toString());
                mdm.dismiss();
            }
        };
    }
    public void load_playlist(String str_uri, String str_id)
    {
        try {
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("udn_id",device_id);
            jsonObject1.put("id",str_uri);
            JSONObject obj = new JSONObject();
            obj.put("data",jsonObject1);
            obj.put("type", "load_music");
             Logs.error(TAG+"_SimulationRequest", "" + obj);
            App.getSocket().emit("action", obj);
          if(call_from.equals("room_devices"))  App.setCurrentSubcriber(App.getArrsubcriber(App.getPosition()));
          if(call_from.equals("favorites"))  App.setCurrentSubcriber(App.getCurrentSubcriber());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void setSelectedFlag()
    {
        for(int i=0;i<sonosArrayList.size();i++)
        {
            playStateState[i]=0;
        }
        notifyDataSetChanged();
    }
}