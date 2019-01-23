package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Playlist;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Radio;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import static com.google.android.gms.internal.zzagz.runOnUiThread;
/**
 * This class containing functionality related to displaying music player screen with down-up animation
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class RoomDevicesUpMusicFragment extends Fragment  {
    public static RoomDevicesUpMusicFragment newInstance() {
        return new RoomDevicesUpMusicFragment();
    }
    @BindView(R.id.txt_muisc_artist_name)TextView txt_muisc_artist_name;
    @BindView(R.id.txt_music_title)TextView txt_music_title;
    @BindView(R.id.seek_music_vol)SeekBar seek_music_vol;
    @BindView(R.id.song_progress)ProgressBar song_progress;
    @BindView(R.id.img_music_play_pause)ImageView img_music_play_pause;
    @BindView(R.id.img_music_prev)ImageView img_music_prev;
    @BindView(R.id.img_music_next)ImageView img_music_next;
    @BindView(R.id.img_album_image)ImageView img_album_image;
    @BindView(R.id.img_music_shuffule)ImageView img_music_shuffule;
    @BindView(R.id.img_music_repeat)ImageView img_music_repeat;
    TimerTask tsk=null;
    Timer timer;
    @BindView(R.id.img_music_mute)ImageView img_music_mute;
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    boolean isChronometerRunning=false;
    int song_tack_time=0;
    private View view;
    ArrayList<Playlist> sonosArrayList=new ArrayList<>();
    ArrayList<Radio> radioArrayList=new ArrayList<>();
    String device_id="";
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    LinearLayoutManager mLayoutManager;
    Context mcontext;
    String REPEAT="repeat",SHUFFLE="shuffle",SHUFFLE_NOREPEAT="shuffle_no_repeat",NORMAL="normal";
    boolean music_list_flag=false,default_music=false,radio_music=false;
    String room_id="";
    String TAG=""+this.getClass().getSimpleName();
    boolean shuffle_flag=false,repeat_flag=false,mute_status=false,play=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
        view =inflater.inflate(R.layout.fragment_music_up_screen, container, false);
        mcontext=view.getContext();
        ButterKnife.bind(this,view);
        device_id=App.getDevice().getDevice_id();
        room_id=getRoomId();
        Logs.info(TAG,"-------"+device_id);
        txt_fragment_title.setText("Currently Playing");
        seek_music_vol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setMusicVolume(device_id,seekBar.getProgress());
            }
        });
        if(!device_id.equals("whole_home")) {
            if (App.getSocket() != null) {
                try {
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_provisioned", new JSONObject()));
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_current_track", new JSONObject().put("Id",device_id)));
                } catch (Exception ex) {
                    Logs.info(TAG, "----" + ex.getMessage());
                }
            }
            setCurrentMusic();
            checkDawnState();
            checkSleepState();
        }
        else {
            if (App.getSocket() != null) {
                try {
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_zones", new JSONObject()));
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_home_current_track", new JSONObject().put("Id",device_id)));
                } catch (Exception ex) {
                    Logs.info(TAG, "----" + ex.getMessage());
                }
            }
            setWholeCurrentMusic();
        }
        try {
        } catch (Exception ex) {
            Logs.info(TAG, "----" + ex.getMessage());
        }
        setSubcriber();
        return view;
    }
    @OnClick(R.id.img_back)
    public void img_back()
    {
        App.setCallfrom("InvidualMusic");
        ReplaceFragment.replaceFragment(RoomDevicesUpMusicFragment.this,R.id.frm_main_container,RoomDevicesMusicFragment.newInstance());
    }
    private void setSubcriber() {
        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext(""+App.getRoomData());
                        sub.onCompleted();
                    }
                }
        );
        myObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(String string) {
                Logs.info(TAG,"response_string"+string);
                if(string.equals("played_next_song")||string.equals("played_previous_song"))
                {
                    song_tack_time=0;
                }
                if(!device_id.equals("whole_home")) {
                    checkDawnState();
                    checkSleepState();
                    if(string.equals("Music")||string.equals("provisioned_devices") )setCurrentMusic();
                }
                else
                {
                    if(string.contains("Whole Home")) {
                        setWholeCurrentMusic();
                    }
                }
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);
    }
    public void setWholeCurrentMusic()
    {
        boolean current_music=false;
        try {
            JSONArray jsonArray = App.getZoneJson().getJSONArray("data");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_TITLE").equals("Whole Home"))
                {
                    Logs.info(TAG+"_Sonosdata",""+jsonObject);
                    current_music=true;
                    if(jsonObject.has("mute_status")) {
                        if(jsonObject.getBoolean("mute_status")) {
                            mute_status=true;
                            img_music_mute.setImageResource(R.drawable.ic_mute_blue);
                        }
                        else
                        {
                            mute_status=false;
                            img_music_mute.setImageResource(R.drawable.ic_speaker_sound_blue);
                        }
                    }
                    seek_music_vol.setProgress(jsonObject.getInt("CML_VOLUME"));
                    if (jsonObject.getString("CML_PLAYMODE").equals("SHUFFLE"))
                    {
                        shuffle_flag=true;
                        repeat_flag=true;
                        img_music_shuffule.setImageResource(R.drawable.ic_shuffle_faint);
                        img_music_repeat.setImageResource(R.drawable.ic_repeat_faint);
                    }
                    if (jsonObject.getString("CML_PLAYMODE").equals("REPEAT_ALL"))
                    {
                        shuffle_flag=false;
                        repeat_flag=true;
                        img_music_repeat.setImageResource(R.drawable.ic_repeat_faint);
                        img_music_shuffule.setImageResource(R.drawable.shuffle_new);
                    }
                    if (jsonObject.getString("CML_PLAYMODE").equals("SHUFFLE_NOREPEAT"))
                    {
                        shuffle_flag=true;
                        repeat_flag=false;
                        img_music_shuffule.setImageResource(R.drawable.ic_shuffle_faint);
                        img_music_repeat.setImageResource(R.drawable.ic_repeat);
                    }
                    if(jsonObject.getString("CML_PLAYMODE").equals("NORMAL"))
                    {
                        shuffle_flag=false;
                        repeat_flag=false;
                        img_music_repeat.setImageResource(R.drawable.ic_repeat);
                        img_music_shuffule.setImageResource(R.drawable.shuffle_new);
                    }
                    if(jsonObject.has("CURRENT_TRACK")) {
                      final   JSONObject current_track_json = jsonObject.getJSONObject("CURRENT_TRACK");
                        if(song_tack_time>current_track_json.getInt("position")&&!txt_music_title.getText().toString().equals(current_track_json.getString("title")))song_tack_time=0;
                        if(current_track_json.has("artist"))txt_muisc_artist_name.setText(current_track_json.getString("artist").equals("null")?"":current_track_json.getString("artist"));
                        if(current_track_json.has("title")) txt_music_title.setText(""+(App.getDefault_song_hash().containsKey(current_track_json.getString("title"))?App.getDefault_song_hash().get(current_track_json.getString("title")):current_track_json.getString("title")));
                        seek_music_vol.setProgress(jsonObject.getInt("CML_VOLUME"));
                        song_tack_time=(song_tack_time>0&&song_tack_time>current_track_json.getInt("position"))?song_tack_time:current_track_json.getInt("position");
                        if(jsonObject.getString("CML_STATE").equals("playing")) {
                           startStopMusicProgress(true,current_track_json.getInt("duration"));
                        }
                        else
                        {
                            int int_song_progress = Math.round(Float.parseFloat(""+song_tack_time)/Float.parseFloat(""+current_track_json.getInt("duration"))*100f );
                            song_progress.setProgress(int_song_progress);
                            startStopMusicProgress(false,current_track_json.getInt("duration"));
                        }
                        if(jsonObject.getString("CML_STATE").equals("playing"))
                        {
                            play=true;
                            img_music_play_pause.setImageResource(R.drawable.ic_music_pause_white);
                        }
                        else
                        {
                            play=false;
                            img_music_play_pause.setImageResource(R.drawable.ic_play_white);
                        }
                        if(current_track_json.has("albumArtURL")) {
                            Glide.with(mcontext).load(current_track_json.getString("albumArtURL"))
                                    .thumbnail(Glide.with(mcontext).load(R.drawable.ic_playlist_default_white))
                                    .error(R.drawable.ic_playlist_default_white)
                                    .override(50, 50)
                                    .dontAnimate()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(img_album_image);
                        }
                        try {
                            Logs.info(TAG,"*********************"+current_track_json.getInt("queuePosition")+"*******************"+jsonObject.getJSONObject("SONOS_PLAYLIST").getInt("queueLength"));
                            if (current_track_json.getInt("queuePosition") == jsonObject.getJSONObject("SONOS_PLAYLIST").getInt("queueLength")) {
                                img_music_next.setAlpha(0.5f);
                                img_music_next.setClickable(false);
                            }
                            else
                            {
                                img_music_next.setAlpha(1f);
                                img_music_next.setClickable(true);
                            }
                            if (current_track_json.getInt("queuePosition") == 1) {
                                img_music_prev.setAlpha(0.5f);
                                img_music_prev.setClickable(false);
                            }
                            else
                            {
                                img_music_prev.setAlpha(1f);
                                img_music_prev.setClickable(true);
                            }
                        }
                        catch (Exception ex)
                        {
                            Logs.error(TAG,"nextprev disable"+ex.getMessage());
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Logs.info(TAG+"_PlayListError",ex.getMessage());
        }
    }
  public void setCurrentMusic()
  {
      boolean current_music=false;
      JSONArray jsonArray=null;
      try {
          jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
           Logs.info(TAG+"_PlayListArray",device_id+""+jsonArray);
          for (int i = 0; i < jsonArray.length(); i++) {
              final JSONObject jsonObject = jsonArray.getJSONObject(i);
              if(jsonObject.getString("Id").equals(device_id))
              {
                   Logs.info(TAG+"_Sonosdata",""+jsonObject);
                  current_music=true;
                  if(jsonObject.getString("CML_PLAYMODE").equals("NORMAL"))
                  {
                      shuffle_flag=false;
                      repeat_flag=false;
                      img_music_repeat.setImageResource(R.drawable.ic_repeat);
                      img_music_shuffule.setImageResource(R.drawable.shuffle_new);
                  }
                   if (jsonObject.getString("CML_PLAYMODE").equals("SHUFFLE"))
                  {
                      shuffle_flag=true;
                      repeat_flag=true;
                      img_music_shuffule.setImageResource(R.drawable.ic_shuffle_faint);
                      img_music_repeat.setImageResource(R.drawable.ic_repeat_faint);
                  }
                  if (jsonObject.getString("CML_PLAYMODE").equals("REPEAT_ALL"))
                  {
                      shuffle_flag=false;
                      repeat_flag=true;
                      img_music_repeat.setImageResource(R.drawable.ic_repeat_faint);
                      img_music_shuffule.setImageResource(R.drawable.shuffle_new);
                  }
                   if (jsonObject.getString("CML_PLAYMODE").equals("SHUFFLE_NOREPEAT"))
                  {
                      shuffle_flag=true;
                      img_music_shuffule.setImageResource(R.drawable.ic_shuffle_faint);
                      img_music_repeat.setImageResource(R.drawable.ic_repeat);
                  }
                  if(jsonObject.has("CURRENT_TRACK")) {
                     final JSONObject current_track_json = jsonObject.getJSONObject("CURRENT_TRACK");
                      if(jsonObject.has("mute_status")) {
                          if(jsonObject.getBoolean("mute_status")) {
                              mute_status=true;
                              img_music_mute.setImageResource(R.drawable.ic_mute_blue);
                          }
                          else
                          {
                              mute_status=false;
                              img_music_mute.setImageResource(R.drawable.ic_speaker_sound_blue);
                          }
                      }
                      if(song_tack_time>current_track_json.getInt("position")&&!txt_music_title.getText().toString().equals(current_track_json.getString("title")))song_tack_time=0;
                      if(current_track_json.has("artist"))txt_muisc_artist_name.setText(current_track_json.getString("artist"));
                      if(current_track_json.has("title")) {
                          txt_music_title.setText(current_track_json.getString("title"));
                          if(App.getDefault_song_hash().containsKey(current_track_json.getString("title")))
                          {
                              setDefaultButtonByDefaultSong(false);
                          }
                          else
                          {
                              setDefaultButtonByDefaultSong(true);
                          }
                      }
                      seek_music_vol.setProgress(jsonObject.getInt("CML_VOLUME"));
                      song_tack_time=(song_tack_time>0&&song_tack_time>current_track_json.getInt("position"))?song_tack_time:current_track_json.getInt("position");
                      if(jsonObject.getString("CML_STATE").equals("playing")) {
                          startStopMusicProgress(true,current_track_json.getInt("duration"));
                      }
                      else {
                          int int_song_progress = Math.round(Float.parseFloat(""+song_tack_time)/Float.parseFloat(""+current_track_json.getInt("duration"))*100f );
                          song_progress.setProgress(int_song_progress);
                          startStopMusicProgress(false,current_track_json.getInt("duration"));
                      }
                      if(jsonObject.getString("CML_STATE").equals("playing"))
                      {
                          play=true;
                          img_music_play_pause.setImageResource(R.drawable.ic_music_pause_white);
                      }
                      else
                      {
                          play=false;
                          img_music_play_pause.setImageResource(R.drawable.ic_play_white);
                      }
                      if(current_track_json.has("albumArtURL")) {
                          Glide.with(mcontext).load(current_track_json.getString("albumArtURL"))
                                  .thumbnail(Glide.with(mcontext).load(R.drawable.ic_playlist_default))
                                  .error(R.drawable.ic_playlist_default)
                                  .override(50, 50)
                                  .dontAnimate()
                                  .diskCacheStrategy(DiskCacheStrategy.ALL)
                                  .into(img_album_image);
                      }
                      try {
                          Logs.info(TAG,"*********************"+current_track_json.getInt("queuePosition")+"*******************"+jsonObject.getJSONObject("SONOS_PLAYLIST").getInt("queueLength"));
                          if (current_track_json.getInt("queuePosition") == jsonObject.getJSONObject("SONOS_PLAYLIST").getInt("queueLength")) {
                              img_music_next.setAlpha(0.5f);
                              img_music_next.setClickable(false);
                          }
                          else
                          {
                              img_music_next.setAlpha(1f);
                              img_music_next.setClickable(true);
                          }
                          if (current_track_json.getInt("queuePosition") == 1) {
                              img_music_prev.setAlpha(0.5f);
                              img_music_prev.setClickable(false);
                          }
                          else
                          {
                              img_music_prev.setAlpha(1f);
                              img_music_prev.setClickable(true);
                          }
                      }
                      catch (Exception ex)
                      {
                          Logs.error(TAG,"nextprev disable"+ex.getMessage());
                      }
                  }
              }
          }
      }
      catch (Exception ex)
      {
           Logs.error(TAG+"_PlayListError",ex.getMessage());
      }
  }
    @OnClick(R.id.img_music_repeat)
    public void img_new()
    {
        if(shuffle_flag&&!repeat_flag)
        {
            repeat_flag=true;
            musicAction(SHUFFLE,device_id);
        }
        else {
            if (!repeat_flag) {
                repeat_flag = true;
                musicAction(REPEAT,device_id);
            } else {
                repeat_flag = false;
                if(shuffle_flag)
                {
                    musicAction(SHUFFLE_NOREPEAT,device_id);
                }
                else
                {
                    musicAction(NORMAL,device_id);
                }
            }
        }
    }
    @OnClick(R.id.img_music_shuffule)
    public void img_music_shuffule()
    {
        if(!shuffle_flag&&repeat_flag)
        {
            shuffle_flag=true;
            musicAction(SHUFFLE,device_id);
        }
        else {
            if (!shuffle_flag) {
                shuffle_flag = true;
                musicAction(SHUFFLE_NOREPEAT,device_id);
            } else {
                shuffle_flag = false;
                if(repeat_flag)
                {
                    musicAction(REPEAT,device_id);
                }
                else
                {
                    musicAction(NORMAL,device_id);
                }
            }
        }
    }
    @OnClick(R.id.img_music_mute)
    public void img_music_mute()
    {
        if(mute_status) {
            img_music_mute.setImageResource(R.drawable.ic_un_mute);
            setMute(device_id, false);
        }
        else
        {
            img_music_mute.setImageResource(R.drawable.ic_mute_blue);
            setMute(device_id, true);
        }
    }
    @OnClick(R.id.img_music_next)
    public void img_next()
    {
        setMusicNext(device_id);
    }
    @OnClick(R.id.img_music_prev)
    public void img_music_prev()
    {
        setPrvious(device_id);
    }
    @OnClick(R.id.img_music_play_pause)
    public void music_play_pause()
    {
        playPauseMusic(play,device_id);
    }
    public void musicAction(String str_action,String device_id)
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
            if(!device_id.equals("whole_home"))jsonData.put("Id", device_id);
            JSONObject obj = new JSONObject();
            obj.put("data", jsonData);
            if(!device_id.equals("whole_home"))obj.put("type", "set_playmode");
            else obj.put("type", "home_set_playmode");
            Logs.info("SimuMusicRequestSend", "" + obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
             Logs.error(TAG+"_PlayJson", "" + ex.getMessage());
        }
    }
    public void setMusicVolume(String device_id, int music_volume)
    {
        if(!device_id.equals("whole_home")) {
            try {
                JSONObject obj = new JSONObject();
                obj.put("data", new JSONObject().put("Id", device_id).put("percent", music_volume));
                obj.put("type", "volume_song");
                App.getSocket().emit("action", obj);
                Logs.info(TAG + "_SimulationRequestSend", "" + obj);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else
        {
            try {
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_volume_song", new JSONObject().put("percent",music_volume)));
            }
            catch (Exception ex)
            {
                Logs.error(TAG,"----------"+ex.getMessage());
            }
        }
    }
    public void setMute(String device_id,boolean status)
    {
        if(!device_id.equals("whole_home")) {
            try {
                JSONObject obj = new JSONObject();
                obj.put("data", new JSONObject().put("Id", "" + device_id).put("status", status));
                obj.put("type", "mute_song");
                Logs.info(TAG + "_SimulationRequestSend", "" + obj);
                App.getSocket().emit("action", obj);
            } catch (Exception ex) {
                Logs.info(TAG + "_PlayJson", "" + ex.getMessage());
            }
        }
        else
        {
            try {
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_mute_song", new JSONObject().put("status", status)));
            }
            catch (Exception ex)
            {
                Logs.error(TAG,"----------"+ex.getMessage());
            }
        }
    }
    public void setMusicNext(String device_id)
    {
        if(!device_id.equals("whole_home")) {
            try {
                JSONObject obj = new JSONObject();
                obj.put("data", new JSONObject().put("Id", "" + device_id));
                obj.put("type", "next_song");
                Logs.info(TAG + "_SimulationRequestSend", "" + obj);
                App.getSocket().emit("action", obj);
            } catch (Exception ex) {
                Logs.info(TAG + "_PlayJson", "" + ex.getMessage());
            }
        }
        else
        {
            try {
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_next_song", new JSONObject()));
            }
            catch (Exception ex)
            {
                Logs.error(TAG,"----------"+ex.getMessage());
            }
        }
    }
    public void setPrvious(String device_id)
    {
        if(!device_id.equals("whole_home")) {
            try {
                JSONObject obj = new JSONObject();
                obj.put("data", new JSONObject().put("Id",""+device_id));
                obj.put("type", "previous_song");
                Logs.info(TAG+"_SimulationRequestSend", "" + obj);
                App.getSocket().emit("action", obj);
            } catch (Exception ex) {
                Logs.info(TAG+"_PlayJson", "" + ex.getMessage());
            }
        }
        else
        {
            try {
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_previous_song", new JSONObject()));
            }
            catch (Exception ex)
            {
                Logs.error(TAG,"----------"+ex.getMessage());
            }
        }
    }
    public void playPauseMusic(boolean play,String device_id)
    {
        if(!play)
        {
            if(!device_id.equals("whole_home")) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("data", new JSONObject().put("Id", "" + device_id));
                    obj.put("type", "play_song");
                    Logs.info(TAG + "_SimulationRequestSend", "" + obj);
                    App.getSocket().emit("action", obj);
                } catch (Exception ex) {
                    Logs.info(TAG + "_PlayJson", "" + ex.getMessage());
                }
            }
            else
            {
                try {
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_play_song", new JSONObject()));
                }
                catch (Exception ex)
                {
                    Logs.error(TAG,"----------"+ex.getMessage());
                }
            }
        }
        else
        {
            if(!device_id.equals("whole_home")) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("data", new JSONObject().put("Id", "" + device_id));
                    obj.put("type", "pause_song");
                    Logs.info(TAG + "_SimulationRequestSend", "" + obj);
                    App.getSocket().emit("action", obj);
                } catch (Exception ex) {
                    Logs.info(TAG + "_PlayJson", "" + ex.getMessage());
                }
            }
            else
            {
                try {
                    App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_pause_song", new JSONObject()));
                }
                catch (Exception ex)
                {
                    Logs.error(TAG,"----------"+ex.getMessage());
                }
            }
        }
    }
    public void checkDawnState()
    {
        if(getDawnState(room_id))
        {
            App.setCallfrom("InvidualMusic");
            ReplaceFragment.replaceFragment(RoomDevicesUpMusicFragment.this,R.id.frm_main_container,RoomDevicesMusicFragment.newInstance());
        }
    }
    public String getRoomId()
    {
        String room_id="";
        JSONObject lightjsonObject = null;
        JSONArray jsonArray=null;
        try {
            jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("Id").equals(device_id)) {
                    room_id = jsonObject.getString("room");
                }
            }
        } catch (Exception ex) {
            Logs.info(TAG+"_actualLighExcception",""+ ex.getMessage());
        }
        return room_id;
    }
    public void checkSleepState()
    {
        if(getSleepState(room_id))
        {
            App.setCallfrom("InvidualMusic");
            ReplaceFragment.replaceFragment(RoomDevicesUpMusicFragment.this,R.id.frm_main_container,RoomDevicesMusicFragment.newInstance());
        }
    }
    public boolean getDawnState(String room_id)
    {
        boolean b=false;
        try {
            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_ID").equals(room_id))
                {
                    if (jsonObject.has("DAWN_RUNNING"))b=jsonObject.getBoolean("DAWN_RUNNING");
                }
            }
        }
        catch (Exception ex)
        {
            Logs.error(TAG+"_DawnRunninError",""+ex.getMessage());
        }
        return  b;
    }
    public boolean getSleepState(String room_id)
    {
        boolean b=false;
        try {
            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_ID").equals(room_id))
                {
                    if(jsonObject.has("SLEEP_RUNNING")) b=jsonObject.getBoolean("SLEEP_RUNNING");
                }
            }
        }
        catch (Exception ex)
        {
            Logs.error(TAG+"_DawnRunninError",""+ex.getMessage());
        }
        return  b;
    }
    private JSONObject getSimulatuonObject(String type)
    {
        JSONObject simulation_json=null;
        try {
            JSONArray jsonArray = App.getAllSimulationData().getJSONArray("data");
            Logs.info(TAG+"_getAllSimulationDataaaa",""+jsonArray);
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(type.equals("dawn")) {
                    if (getDawnId().equals(jsonObject.getString("CML_ID"))) {
                        simulation_json = jsonObject;
                    }
                }
                if (type.equals("sleep")) {
                    String sleep_id=getSleepId();
                    Logs.info(TAG+"_Sleep_id",""+sleep_id);
                    if(sleep_id.contains("prepare"))
                    {
                        JSONObject datajson=new JSONObject();
                        datajson.put("Id",sleep_id);
                        datajson.put("room",room_id);
                        simulation_json=datajson;
                    }
                    else
                    {
                        if (sleep_id.equals(jsonObject.getString("CML_ID"))) {
                            simulation_json = jsonObject;
                        }
                    }
                }
            }
        }
        catch (Exception ex){
            Logs.info(TAG+"_RoomDawnError","------------------------"+ex.getMessage());
        }
        return simulation_json;
    }
    public String getDawnId()
    {
        String string_id="";
        try {
            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            Logs.info(TAG+"_getAllSimulationDataaaa",""+jsonArray);
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (room_id.equals(jsonObject.getString("CML_ID"))) {
                    if(jsonObject.has("DAWN_ID")) string_id= jsonObject.getString("DAWN_ID");
                }
            }
        }
        catch (Exception ex){
            Logs.error(TAG+"_RoomDawnError","------------------------"+ex.getMessage());
        }
        return string_id;
    }
    public String getSleepId()
    {
        String string_id="";
        try {
            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            Logs.info(TAG+"_getAllSimulationDataaaa",""+jsonArray+"----"+room_id);
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (room_id.equals(jsonObject.getString("CML_ID"))) {
                    if(jsonObject.has("SLEEP_ID")) string_id= jsonObject.getString("SLEEP_ID");
                }
            }
        }
        catch (Exception ex){
            Logs.error(TAG+"_RoomDawnError","------------------------"+ex.getMessage());
        }
        return string_id;
    }
    @Override
    public void onResume() {
        super.onResume();
        if(!App.isOrientationFlag()) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if(!App.isOrientationFlag()) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
        if(timer!=null) {
            timer.cancel();
        }
    }
    public void setDefaultButtonByDefaultSong(boolean b)
    {
        img_music_prev.setClickable(b);
        img_music_shuffule.setClickable(b);
        img_music_next.setClickable(b);
        img_music_repeat.setClickable(b);
        img_music_shuffule.setAlpha(!b?0.5f:1f);
        img_music_repeat.setAlpha(!b?0.5f:1f);
        img_music_next.setAlpha(!b?0.5f:1f);
        img_music_prev.setAlpha(!b?0.5f:1f);
    }
    private void startStopMusicProgress(final boolean b,final int value)
    {
            // This timer task will be executed every 1 sec.
        if(b) {
            if (tsk == null) {
                timer=new Timer();
                tsk = new TimerTask() {
                    @Override
                    public void run() {
                        song_tack_time++;
                        try {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    song_progress.setProgress(Math.round(Float.parseFloat(""+song_tack_time)/Float.parseFloat(""+value)*100f));
                                }
                            });
                        } catch (Exception ex) {
                            Log.e("MusicProgressError", "" + ex.getMessage());
                        }
                    }
                };
                timer.schedule(tsk, 0, 1000);
            }
        }
        else
        {
            if( timer!=null) {
                timer.cancel();
                timer = null;
                tsk = null;
            }
        }
    }
}