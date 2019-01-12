package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;

/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V4 on 25/8/17.
 */


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule.Add_custmise_wake;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule.Wake_PlaylistSound;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Sonos;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Sound;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomDawnSoundAdapter_new extends BaseAdapter {

    private Context activity;

    private ArrayList<Sound> chooseSoundArrayList;
    private static LayoutInflater inflater = null;
    String sound_id = "";
    int soundState[];
    int boxState[];

    Fragment mfragment;
    //private String device_id = "";
    public static String device_id = "";

    public CustomDawnSoundAdapter_new(Context a, ArrayList<Sound> chooseSoundArrayList, Fragment mfragment) {
        activity = a;
        this.chooseSoundArrayList = chooseSoundArrayList;
        soundState = new int[chooseSoundArrayList.size()];
        this.mfragment = mfragment;
        for (int i = 0; i < chooseSoundArrayList.size(); i++) {
            soundState[i] = 0;
        }
        System.out.println("33333333" + chooseSoundArrayList);
        if (App.getTemp_bundle().containsKey("sound_id")) {
            sound_id = App.getTemp_bundle().getString("sound_id");
        }

        boxState = new int[chooseSoundArrayList.size()];
        for (int i = 0; i < chooseSoundArrayList.size(); i++) {
            boxState[i] = 0;

        }
        //  App.getTemp_bundle().putString("default_sound","");
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getMusicState();
    }

    static class ViewHolder {
        private TextView txt_id;
        private ImageView palypause;
        private LinearLayout lyt_sound;
        private TextView txt_title;
        private RadioButton rb_Choice;


    }

    public void reload(ArrayList chooseSoundArrayList) {
        this.chooseSoundArrayList = chooseSoundArrayList;
    }

    public int getCount() {
        return chooseSoundArrayList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        // View vi=convertView;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dawn_sound_item, null);
            holder = new ViewHolder();
            holder.txt_id = convertView.findViewById(R.id.txt_sound_id); // title
            holder.palypause = convertView.findViewById(R.id.img_music_play_pause); // title
            holder.lyt_sound = convertView.findViewById(R.id.lyt_sound); // title
            holder.txt_title = convertView.findViewById(R.id.txt_sound_title); // artist name
            holder.rb_Choice = convertView.findViewById(R.id.rb_Choice);
            holder.rb_Choice.setTag(position);
            holder.lyt_sound.setTag(position);
            holder.palypause.setTag(position);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Sound choose = chooseSoundArrayList.get(position);


        Log.d("getSound_id()", "" + choose.getSound_id());
        holder.txt_id.setText("" + choose.getSound_id());
        holder.txt_title.setText("" + choose.getSound_title());
        holder.rb_Choice.setTag(position);


        if (position == 6) {

            holder.palypause.setImageDrawable(activity.getResources()
                    .getDrawable(R.drawable.ic_playlist_default_white));
        }
        if (position == 5 && App.getTemp_bundle().getBoolean("playlist_selected") != true) {

            if (!checkChecked()) {
                holder.rb_Choice.setChecked(true);
                soundState[position] = 1;
            }

        } else {
            holder.rb_Choice.setChecked(false);
        }


        if (App.getTemp_bundle() != null) {
            if (App.getTemp_bundle().containsKey("default_sound") || App.getTemp_bundle().containsKey("applied_dawn_sound")) {
                Log.d("App.getTemp_bundl", "" + App.getTemp_bundle().getString("default_sound"));
                if (App.getTemp_bundle().getString("default_sound").equals("" + choose.getSound_id())) {
                    holder.rb_Choice.setChecked(true);
                    soundState[position] = 1;

                }
            } else {
                holder.rb_Choice.setChecked(false);
                soundState[position] = 0;

            }
        } else {
            holder.rb_Choice.setChecked(false);
            soundState[position] = 0;

        }

//            SimulaterModel simulaterModel=data.get(position);

        if (soundState[position] == 0) {
            try {
                holder.rb_Choice.setChecked(false);
            } catch (Exception e) {
                System.out.println("Erorrrrrrrrrrrrrrrrrrrr");
                e.printStackTrace();
            }


        } else {
            holder.rb_Choice.setChecked(true);

        }

        if (boxState[position] == 0) {
            holder.palypause.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_playlist_white));
            //if()

        } else {
            holder.palypause.setImageDrawable(activity.getResources().getDrawable(R.drawable.play_alarm_sound_waves));

        }


        holder.rb_Choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (Integer) view.getTag();
                Log.e("RadioButtonPosition", "" + pos);
                if (soundState[pos] == 0) {

                    App.getTemp_bundle().putString("default_sound", chooseSoundArrayList.get(pos).getSound_id());
                    setSelectedFlag();
                    soundState[pos] = 1;

                    holder.rb_Choice.setChecked(true);

                } else {
                    setSelectedFlag();
                    soundState[pos] = 0;
                    holder.rb_Choice.setChecked(false);
                }


                if (chooseSoundArrayList.get(pos).getSound_id().equals("6")) {


                    Bundle bundlee = App.getTemp_bundle();
                    bundlee.putBoolean("playlist_selected", true);
                    App.setTemp_bundle(bundlee);
                    holder.palypause.setOnClickListener(null);
                    holder.palypause.setClickable(false);
                    holder.palypause.setEnabled(false);
                    //device_id = "uuid:RINCON_949F3E847F5E01400";

                    FragmentManager fm = ((AppCompatActivity) activity).getSupportFragmentManager();
                    Wake_PlaylistSound dFragment = new Wake_PlaylistSound();
                    Bundle bundle = new Bundle();
                    bundle.putString("device_id", device_id);
                    dFragment.setArguments(bundle);
                    dFragment.show(fm, "Music");

                }
                if(chooseSoundArrayList.get(pos).getSound_id().equals("6")) {
                    App.getTemp_bundle().putString("soundId", "5");
                }
                else {
                    App.getTemp_bundle().putString("soundId", chooseSoundArrayList.get(pos).getSound_id());

                }
                if (App.isOrientationFlag())
                    ReplaceFragment.replaceFragment(mfragment, R.id.frm_expriencesmain_container, Add_custmise_wake.newInstance());
                else
                    ReplaceFragment.replaceFragment(mfragment, R.id.frm_main_container, Add_custmise_wake.newInstance());

            }
        });


        holder.palypause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = (Integer) v.getTag();

                ImageView tmp_img = (ImageView) v;
                Log.e("Sound Position", "........" + pos);
                if (boxState[pos] == 0) {
                    boxSelectedFlag();
                    boxState[pos] = 1;


                    App.setSound_test_flag(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            App.setSound_test_flag(false);
                        }
                    }, 10000);
                    tmp_img.setImageDrawable(activity.getResources()
                            .getDrawable(R.drawable.play_alarm_sound_waves));
                    JSONObject jobj = new JSONObject();
                    try {

                        jobj.put("roomId", App.getTemp_bundle().getString("room_id"));
                        jobj.put("soundId", "" + chooseSoundArrayList.get(pos).getSound_id());

                        App.getSocket().emit("action", JsonObjectCreater.getJsonObject("play_dawn_Sound", jobj));
                        Log.e("testtttttttttt", "" + jobj);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {


                    JSONObject jobj = new JSONObject();
                    try {

                        jobj.put("roomId", App.getTemp_bundle().getString("room_id"));
                        jobj.put("soundId", "" + chooseSoundArrayList.get(pos).getSound_id());

                        App.getSocket().emit("action", JsonObjectCreater.getJsonObject("stopDawnSong", jobj));
                        Log.e("StopTesttttttttttt", "" + jobj);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    boxSelectedFlag();
                    boxState[pos] = 0;
                    tmp_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_playlist_white));


                }
                App.getTemp_bundle().putString("soundId", chooseSoundArrayList.get(pos).getSound_id());

    /*if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(mfragment,R.id.frm_expriencesmain_container, Add_custmise_wake.newInstance());
    else  ReplaceFragment.replaceFragment(mfragment,R.id.frm_main_container, Add_custmise_wake.newInstance());
*/
            }
        });


        return convertView;
    }


    public void setSelectedFlag() {
        for (int i = 0; i < chooseSoundArrayList.size(); i++) {
            soundState[i] = 0;
        }
        notifyDataSetChanged();
    }

    public void boxSelectedFlag() {
        for (int i = 0; i < chooseSoundArrayList.size(); i++) {
            boxState[i] = 0;
        }
        notifyDataSetChanged();
    }

    public boolean checkChecked() {
        boolean flag = false;
        for (int i = 0; i < chooseSoundArrayList.size(); i++) {
            if (soundState[i] == 1) flag = true;
        }
        return flag;
    }


    private void getMusicState() {


        try {

            Logs.info("_MusicJson_dddddddddddddd", "" + App.getDevicesByRoomData());
            JSONArray jsonArray = App.getDevicesByRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);


                if (jsonObject.getString("type").equals("Music")) {
                    Logs.info("_MusicJson", "" + jsonObject);
                    Sonos sonos = new Sonos();
                    sonos.setSonos_id(jsonObject.getString("Id"));
                    device_id=jsonObject.getString("Id");
              //      //Toast.makeText(activity, ""+device_id, Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception ex){
        Logs.error("Error in DawnSonos",""+ex.getMessage());
        }
    }
}