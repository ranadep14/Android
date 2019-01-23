package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule.Add_custmise_wake;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Sonos;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class DawnPlaylistAdapter extends BaseAdapter {

    private Context activity;
    private DialogFragment mdm;

    private List<Sonos> sonosArrayList;
    private static LayoutInflater inflater=null;
    private int selectedPosition = -1;
    Fragment mfrag;
    String device_id="";
    int soundState[];
    String call_from="";
    int playStateState[];

    int boxState[];
    public DawnPlaylistAdapter(Context context, List<Sonos> sonosArrayList, String deviceId, Fragment dm){

       // mdm=dm;

        activity = context;
        this.sonosArrayList=sonosArrayList;
        this.mfrag=dm;
       // getMusicState();
        soundState =new int[sonosArrayList.size()];
        for(int i=0;i<sonosArrayList.size();i++)
        {
            soundState[i]=0;
        }
        Log.e("Dawnsonosarraulist",""+sonosArrayList.size());
        soundState =new int[sonosArrayList.size()];
        playStateState=new int[sonosArrayList.size()];
        for (int i=0;i<sonosArrayList.size();i++)
        {
            playStateState[i]=0;
        }




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
        if (convertView == null) convertView = inflater.inflate(R.layout.dawn_playlist_item, null);
            // get all UI view
          // RadioButton img_play = convertView.findViewById(R.id.chb_mark);
        ImageView  img_album_image = convertView.findViewById(R.id.img_album_image);
        TextView  txt_playlist_title = convertView.findViewById(R.id.txt_playlist_title);
        TextView  txt_playlist_id = convertView.findViewById(R.id.txt_playlist_id);
        TextView  txt_playlist_url = convertView.findViewById(R.id.txt_playlist_url);
        LinearLayout  lin_playlist_item= convertView.findViewById(R.id.lin_playlist_item);
        final RadioButton rb_Choice= convertView.findViewById(R.id.chb_mark);
            rb_Choice.setTag(position); // This line is important.
try {

    txt_playlist_title.setText(sonosArrayList.get(position).getSonos_track_title());
    txt_playlist_id.setText(sonosArrayList.get(position).getSonos_id());
    txt_playlist_url.setText(sonosArrayList.get(position).getSonos_uri());
}catch (IndexOutOfBoundsException e)
{
    Log.e("IndexOutOfBoundsExcep",""+e.getMessage());
    ReplaceFragment.replaceFragment(mfrag,R.id.frm_expriencesmain_container, Add_custmise_wake.newInstance());
}
        try {
            Glide.with(activity).load(sonosArrayList.get(position).getSonos_album_art())
                    .thumbnail(Glide.with(activity).load(R.drawable.ic_playlist_default))
                    .error(R.drawable.ic_playlist_default)
                    .override(50, 50)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_album_image);

        }catch (IndexOutOfBoundsException e)
            {
                Log.e("IndexOutOfBoundsExcep",""+e.getMessage());
                ReplaceFragment.replaceFragment(mfrag,R.id.frm_expriencesmain_container, Add_custmise_wake.newInstance());
            }

        if(App.getTemp_bundle()!=null) {
            if (App.getTemp_bundle().containsKey("default_playlist")) {
                Log.d("App.getTemp_bundl",""+App.getTemp_bundle().getString("default_playlist"));
                if (App.getTemp_bundle().getString("default_playlist").equals(""+sonosArrayList.get(position).getSonos_uri())) {
                    rb_Choice.setChecked(true);
                    soundState[position] = 1;

                }
            } else {
                rb_Choice.setChecked(false);
                soundState[position] = 0;

            }
        }else {
            rb_Choice.setChecked(false);
            soundState[position] = 0;

        }

//            SimulaterModel simulaterModel=data.get(position);

        if (soundState[position] == 0) {
            try {
                rb_Choice.setChecked(false);
            }
            catch(Exception e){
                System.out.println("Erorrrrrrrrrrrrrrrrrrrr");
                e.printStackTrace();
            }


        } else {
            rb_Choice.setChecked(true);

        }

        rb_Choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int pos = (Integer) view.getTag();
                    Log.e("RadioButtonPosition", "" + pos);
                    if (soundState[pos] == 0) {
                        App.getTemp_bundle().putString("default_playlist", sonosArrayList.get(pos).getSonos_uri());
                        setSelectedFlag();
                        soundState[pos] = 1;

                        rb_Choice.setChecked(true);




                    } else {
                        //setSelectedFlag();
                        soundState[pos] = 0;
                        rb_Choice.setChecked(false);
                    }
                    App.getTemp_bundle().putString("playlistId", sonosArrayList.get(pos).getSonos_uri());
                    if (App.isOrientationFlag())
                        ReplaceFragment.replaceFragment(mfrag, R.id.frm_expriencesmain_container, Add_custmise_wake.newInstance());
                    else
                        ReplaceFragment.replaceFragment(mfrag, R.id.frm_main_container, Add_custmise_wake.newInstance());


                } catch (IndexOutOfBoundsException e) {

                    Log.e("default_playlist", "" + e.getMessage());
                }
            }
        });

        return convertView;
    }


    private View.OnClickListener onStateChangedListener(final TextView txt_id, final TextView txt_url, final RadioButton checkBox, final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              Log.e("MusicLoadCall","---------------"+txt_id.getText().toString()+"----------------"+txt_url.getText().toString());
               // App.setPlayListIndex(position);
                playStateState[position]=1;
                selectedPosition = position;
                load_playlist(txt_id.getText().toString(),txt_url.getText().toString());

                Bundle bundle= App.getTemp_bundle();
                bundle.putBoolean("has_track",true);
                App.setTemp_bundle(bundle);

                if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(mdm,R.id.frm_expriencesmain_container, Add_custmise_wake.newInstance());
                else  ReplaceFragment.replaceFragment(mdm,R.id.frm_main_container, Add_custmise_wake.newInstance());


                mdm.dismiss();

            }
        };
    }

    public void load_playlist(String str_uri, String str_id)
    {
        try {
         //   //Toast.makeText(activity, ""+CustomDawnSoundAdapter_new.device_id, Toast.LENGTH_SHORT).show();
            JSONObject jsonObject1=new JSONObject();
         //   jsonObject1.put("udn_id","uuid:RINCON_949F3E847F5E01400");
            jsonObject1.put("udn_id",CustomDawnSoundAdapter_new.device_id);
            jsonObject1.put("id",str_uri);
            JSONObject obj = new JSONObject();
            obj.put("data",jsonObject1);
            obj.put("type", "load_music");
            Log.e("SimulationRequest", "" + obj);
            App.getSocket().emit("action", obj);
            } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void setSelectedFlag()
    {
        for(int i=0;i<soundState.length;i++)
        {

            soundState[i]=0;

        }
       notifyDataSetChanged();
    }


    private void getMusicState() {


        try {

            Logs.info("_MusicJson_", "" + App.getDevicesByRoomData());
            JSONArray jsonArray = App.getDevicesByRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);


                if (jsonObject.getString("type").equals("Music")) {
                    Logs.info("_MusicJson", "" + jsonObject);
                    Sonos sonos = new Sonos();
                    sonos.setSonos_id(jsonObject.getString("Id"));
                    device_id=jsonObject.getString("Id");
                    //Toast.makeText(activity, ""+device_id, Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception ex){
            Logs.error("Error in DawnSonos",""+ex.getMessage());
        }
    }



}