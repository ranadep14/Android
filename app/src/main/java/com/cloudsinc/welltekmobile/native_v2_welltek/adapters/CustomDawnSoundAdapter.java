package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;

/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V4 on 25/8/17.
 */


import android.content.Context;
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

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Sound;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomDawnSoundAdapter extends BaseAdapter {

    private Context activity;

    private ArrayList<Sound> chooseSoundArrayList;
    private static LayoutInflater inflater=null;
    String sound_id="";
    int soundState[];
    int boxState[];
    String roomId="1510118228869";//"room#1510055904814";
    String soundId="3";
    Fragment mfragment;

    public CustomDawnSoundAdapter(Context a, ArrayList<Sound> chooseSoundArrayList, Fragment mfragment) {
        activity = a;
        this.chooseSoundArrayList=chooseSoundArrayList;
        soundState =new int[chooseSoundArrayList.size()];
        this.mfragment=mfragment;
        for(int i=0;i<chooseSoundArrayList.size();i++)
        {
            soundState[i]=0;
        }
        System.out.println("3333333333333333333"+chooseSoundArrayList);
        if(App.getTemp_bundle().containsKey("sound_id"))
        {
            sound_id=App.getTemp_bundle().getString("sound_id");
        }

        boxState=new int[chooseSoundArrayList.size()];
        for (int i = 0; i < chooseSoundArrayList.size(); i++) {
            boxState[i] = 0;


        }
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        View vi=convertView;

        if(convertView==null)
            vi = inflater.inflate(R.layout.dawn_sound_item, null);

        final TextView txt_id = vi.findViewById(R.id.txt_sound_id); // title
        final ImageView palypause = vi.findViewById(R.id.img_music_play_pause); // title
        final LinearLayout lyt_sound = vi.findViewById(R.id.lyt_sound); // title
        final TextView   txt_title = vi.findViewById(R.id.txt_sound_title); // artist name
        final RadioButton rb_Choice= vi.findViewById(R.id.rb_Choice);
        rb_Choice.setTag(position);
        lyt_sound.setTag(position);
        palypause.setTag(position);

        final Sound choose=chooseSoundArrayList.get(position);


        Log.d("getSound_id()",""+choose.getSound_id());
        txt_id.setText(""+choose.getSound_id());
        txt_title.setText(""+choose.getSound_title());
        rb_Choice.setTag(position);


        if(App.getTemp_bundle().getString("default_sound").equals(choose.getSound_id()))
        {
            rb_Choice.setChecked(true);
            soundState[position]=1;

        }
        else
        {
            rb_Choice.setChecked(false);
            soundState[position]=0;

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


        /**************


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
        if(favflag[pos])
        {

        FavoritesOperations.deleteFav(arrayList.get(pos).getFav_id());
        favflag[pos]=false;
        holder.img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
        }
        else
        {
        FavoritesOperations.addFav(arrayList.get(pos).getDevice_id());
        favflag[pos]=true;
        holder.img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));

        }
        }
        });







        ****************/

        if (boxState[position] == 0) {
           // boxState[position] = 1;
           palypause.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_playlist));


        } else {
          //  boxState[position] = 1;
            palypause.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_music_pause_black));

        }

        lyt_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (Integer) view.getTag();
                Log.e("linnPosition",""+pos);
                if (soundState[pos] == 0) {
                    App.getTemp_bundle().putString("default_sound",chooseSoundArrayList.get(pos).getSound_id());
                    setSelectedFlag();
                    soundState[pos] = 1;
                    rb_Choice.setChecked(true);

                } else {
                    setSelectedFlag();
                    soundState[pos] = 0;
                    rb_Choice.setChecked(false);

                }
                App.getTemp_bundle().putString("soundId",chooseSoundArrayList.get(pos).getSound_id());
         //       ReplaceFragment.replaceFragment(mfragment,R.id.frame_middle, AddWake.newInstance());

            }
        });
        rb_Choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (Integer) view.getTag();
                Log.e("RadioButtonPosition",""+pos);
                if (soundState[pos] == 0) {
                    App.getTemp_bundle().putString("default_sound",chooseSoundArrayList.get(pos).getSound_id());
                    setSelectedFlag();
                    soundState[pos] = 1;

                    rb_Choice.setChecked(true);

                } else {
                    setSelectedFlag();
                    soundState[pos] = 0;
                    rb_Choice.setChecked(false);
                }
                App.getTemp_bundle().putString("soundId",chooseSoundArrayList.get(pos).getSound_id());
              //  ReplaceFragment.replaceFragment(mfragment,R.id.frame_middle, AddWake.newInstance());

            }
        });



palypause.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {

        int pos = (Integer) v.getTag();

        ImageView tmp_img = (ImageView) v;
        Log.e("Favorites", "........" + pos);
        if (boxState[pos] == 0) {
            boxSelectedFlag();
        boxState[pos] = 1;

          //  soundState[pos]=1;
            tmp_img.setImageDrawable(activity.getResources()
        .getDrawable(R.drawable.ic_music_pause_black));
            JSONObject jobj=new JSONObject();
            try {

                jobj.put("roomId",App.getTemp_bundle().getString("room_id"));
                jobj.put("soundId",""+chooseSoundArrayList.get(pos).getSound_id());

                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("play_dawn_Sound", jobj));
                Log.e("testtttttttttt",""+jobj);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            JSONObject jobj=new JSONObject();
            try {

                jobj.put("roomId",App.getTemp_bundle().getString("room_id"));
                jobj.put("soundId",""+chooseSoundArrayList.get(pos).getSound_id());

                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("stopDawnSong", jobj));
                Log.e("StopTesttttttttttt",""+jobj);

            } catch (JSONException e) {
                e.printStackTrace();
            }

           // boxSelectedFlag();
      //
            //
            // boxState[pos] = 0;
        //    soundState[pos]=0;
        tmp_img.setImageDrawable(activity.getResources()
        .getDrawable(R.drawable.ic_playlist));



        }
    App.getTemp_bundle().putString("soundId",chooseSoundArrayList.get(pos).getSound_id());
   // ReplaceFragment.replaceFragment(mfragment,R.id.frame_middle, AddWake.newInstance());

        }
        });




        return vi;
    }


    private void musicplay() {



        JSONObject jobj=new JSONObject();
        try {

            jobj.put("roomId",App.getTemp_bundle().getString("room_id"));//"room#1510055904814");
            //  jobj.put("soundId",App.getTemp_bundle().getString("sound_id"));//"3");
          /*  JSONObject jobjjj=new JSONObject();
            jobjjj.put("data",jobj);*/
            jobj.put("soundId","3");//"3");
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("play_dawn_Sound", jobj));
            Log.e("testtttttttttt",""+jobj);
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_rooms", new JSONObject()));

    }

    public void setSelectedFlag()
    {
        for(int i=0;i<chooseSoundArrayList.size();i++)
        {
            soundState[i]=0;
        }
        notifyDataSetChanged();
    }public void boxSelectedFlag()
    {
        for(int i=0;i<chooseSoundArrayList.size();i++)
        {
            boxState[i]=0;
        }
        notifyDataSetChanged();
    }

}