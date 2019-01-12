package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.MusicPlayListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.favorites.FavoritesMainFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.types.DevicesByType;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule.RemoveNotification;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.favorites.FavoriteMenuMainFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Playlist;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Radio;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import com.mikhaellopez.circularimageview.CircularImageView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity.hideSystemUI;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.FavoritesOperations.getDeviceTitle;
/**
 * This class containing functionality related to displaying play list UI for selected sonos
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class RoomDevicesMusicFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    public static RoomDevicesMusicFragment newInstance() {
        return new RoomDevicesMusicFragment();
    }
    @BindView(R.id.rec_default_music)RecyclerView rec_default_music;
    @BindView(R.id.rec_radio_stations)RecyclerView rec_radio_stations;
    @BindView(R.id.rec_playlists)RecyclerView rec_playlists;
    @BindView(R.id.rel_loading)
    RelativeLayout rel_loading;
    @BindView(R.id.txt_room_title)TextView txt_room_title;
    @BindView(R.id.img_room_type)ImageView img_room_type;
    @BindView(R.id.img_whole_home)CircularImageView img_whole_home;
    @BindView(R.id.lin_default_music)LinearLayout lin_default_music;
    @BindView(R.id.lin_playlists)LinearLayout lin_playlists;
    @BindView(R.id.lin_radio_stations)LinearLayout lin_radio_stations;
    @BindView(R.id.lin_music_panel)LinearLayout lin_music_panel;
    @BindView(R.id.txt_no_devices)TextView txt_no_devices;
    @BindView(R.id.txt_muisc_artist_name)TextView txt_muisc_artist_name;
    @BindView(R.id.txt_music_title)TextView txt_music_title;
    @BindView(R.id.seek_music_vol)SeekBar seek_music_vol;
    @BindView(R.id.img_music_play_pause)ImageView img_music_play_pause;
    @BindView(R.id.img_music_prev)ImageView img_music_prev;
    @BindView(R.id.img_music_next)ImageView img_music_next;
    @BindView(R.id.img_album_image)ImageView img_album_image;
    @BindView(R.id.img_music_shuffule)ImageView img_music_shuffule;
    @BindView(R.id.img_music_repeat)ImageView img_music_repeat;
    @BindView(R.id.img_music_mute)ImageView img_music_mute;
    @BindView(R.id.lin_no_playlist)LinearLayout lin_no_playlist;
    @BindView(R.id.lin_volume)LinearLayout lin_volume;
    @BindView(R.id.lin_controls)LinearLayout lin_controls;
    @BindView(R.id.img_up_music)ImageView img_up_music;
    @Nullable @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @Nullable @BindView(R.id.img_back)ImageView img_back;
    @BindView(R.id.lin_dawn_running)LinearLayout lin_dawn_running;
    @BindView(R.id.lin_sleep_running)LinearLayout lin_sleep_running;
    static boolean flag_disable_all_views_for_dawn =false;
    static boolean flag_disable_all_views_for_sleep =false;
    String TAG=RoomDevicesMusicFragment.this.getClass().getSimpleName();
    private View view;
    ArrayList<Playlist> sonosArrayList=new ArrayList<>();
    ArrayList<Radio> radioArrayList=new ArrayList<>();
    String device_id="";
    String str_current_song_id="";
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    LinearLayoutManager mLayoutManager;
    Context mcontext;
    String room_id;
    String REPEAT="repeat",SHUFFLE="shuffle",SHUFFLE_NOREPEAT="shuffle_no_repeat",NORMAL="normal";
    boolean music_list_flag=false,default_music=false,radio_music=false;
    boolean shuffle_flag=false,repeat_flag=false,mute_status=false,play=false;
    private MusicPlayListAdapter musicPlayListAdapter;
    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
        view =inflater.inflate(R.layout.fragment_room_devices_music, container, false);
        mcontext=view.getContext();
        ButterKnife.bind(this,view);
        hideSystemUI(getActivity());
        device_id=App.getDevice().getDevice_id();
        txt_music_title.setSelected(true);
        txt_muisc_artist_name.setSelected(true);

       if(!App.isOrientationFlag()) {
           txt_fragment_title.setText(""+(device_id.equals("whole_home")?"All Audio":getDeviceTitle(device_id)));
           img_back.setImageDrawable(mcontext.getDrawable(R.drawable.ic_left_arrow));
       }
         Logs.info(TAG,"Im here");
         Logs.info(TAG+"_Device_id",device_id);
        if(App.isOrientationFlag())
        {
            img_up_music.setVisibility(View.GONE);
            img_album_image.setVisibility(View.VISIBLE);
        }
        else
        {
            img_up_music.setVisibility(View.VISIBLE);
            img_album_image.setVisibility(View.GONE);
        }
         room_id=getRoomId();
       if(!device_id.equals("whole_home")) {
           txt_room_title.setText("" + getRoomTitle(room_id));
           setTypeImage(getRoomType(room_id));
       if(App.getSocket()!=null) {
           try {
                   App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_default_dawn_sounds", new JSONObject()));
                   JSONObject obj = new JSONObject();
                   JSONObject jsonObject = new JSONObject();
                   jsonObject.put("Id", device_id);
                   obj.put("data", jsonObject);
                   obj.put("type", "get_playlists");
                   Logs.info(TAG + "_SimulationRequest", "" + obj);
                   App.getSocket().emit("action", obj);
                   JSONObject obj1 = new JSONObject();
                   obj1.put("data", new JSONObject().put("Id", device_id));
                   obj1.put("type", "get_radio_playlist");
                   Logs.info(TAG + "_SimulationRequest", "" + obj1);
                   App.getSocket().emit("action", obj1);
           } catch (Exception ex) {
               ex.printStackTrace();
           }
        }
           checkDawnState();
           checkSleepState();
           disableRecyclerview(rec_playlists);
       }
       else
       {
           img_room_type.setVisibility(View.GONE);
           img_whole_home.setVisibility(View.VISIBLE);
           txt_room_title.setText("Whole Home");
           setTypeImage("whole_home");
           App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_default_dawn_sounds", new JSONObject()));
           App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_zones", new JSONObject()));
         if(!App.getCallfrom().equals("InvidualMusic")) {
             App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_get_playlists", new JSONObject()));
         }
         else
         {
             App.setCallfrom("");
         }
           setWholeCurrentMusic();
       }
        setSubcriber();
        return view;
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
               setData(string);
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setMusic_playlist_subcriber(s);
    }
    public void setData(String string)
    {
        if(!device_id.equals("whole_home")) {
            checkDawnState();
            checkSleepState();
            Logs.info(TAG + "_MusicResponseString", string);
            if (string.equals("Music") || string.equals("playlist") || string.equals("device_updated")) {
                setCurrentMusic();
            }
            if (string.equals("playlist") ||string.equals("Music")||string.equals("default_dawn_sounds")) {
                getMusicPlaylist();
                if (sonosArrayList.size() > 0) {
                    if(musicPlayListAdapter==null) {
                        music_list_flag = true;
                        musicPlayListAdapter = new MusicPlayListAdapter(mcontext, sonosArrayList, device_id, RoomDevicesMusicFragment.this, str_current_song_id);
                        rec_playlists.setAdapter(musicPlayListAdapter);
                        rec_playlists.invalidate();
                        rec_playlists.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(mcontext);
                        rec_playlists.setLayoutManager(mLayoutManager);
                        lin_playlists.setVisibility(View.VISIBLE);
                        rel_loading.setVisibility(View.GONE);
                    }
                    else
                    {
                        musicPlayListAdapter.reload(sonosArrayList,str_current_song_id);
                        musicPlayListAdapter.notifyDataSetChanged();

                    }
                }
                if (!music_list_flag) {
                    rel_loading.setVisibility(View.GONE);
                    txt_no_devices.setVisibility(View.VISIBLE);
                    lin_default_music.setVisibility(View.GONE);
                    lin_playlists.setVisibility(View.GONE);
                }
                else
                {
                    rel_loading.setVisibility(View.GONE);
                    txt_no_devices.setVisibility(View.GONE);
                    lin_default_music.setVisibility(View.GONE);
                    lin_playlists.setVisibility(View.VISIBLE);
                }
            }
        }
        else
        {
            if(string.contains("Whole Home")) {
                setWholeCurrentMusic();
            }
            if ((string.equals("home_playlists")||string.contains("Whole Home")||string.contains("default_dawn_sounds"))) {
                getMusicPlaylist();
                if (sonosArrayList.size() > 0) {
                    music_list_flag = true;
                    MusicPlayListAdapter musicPlayListAdapter = new MusicPlayListAdapter(mcontext, sonosArrayList, device_id, RoomDevicesMusicFragment.this,str_current_song_id);
                    rec_playlists.setAdapter(musicPlayListAdapter);
                    rec_playlists.invalidate();
                    rec_playlists.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(mcontext);
                    rec_playlists.setLayoutManager(mLayoutManager);
                    lin_playlists.setVisibility(View.VISIBLE);
                    rel_loading.setVisibility(View.GONE);
                }
                if (!music_list_flag) {
                    rel_loading.setVisibility(View.GONE);
                    txt_no_devices.setVisibility(View.VISIBLE);
                    lin_default_music.setVisibility(View.GONE);
                    lin_playlists.setVisibility(View.GONE);
                }
                else
                {
                    rel_loading.setVisibility(View.GONE);
                    txt_no_devices.setVisibility(View.GONE);
                    lin_default_music.setVisibility(View.GONE);
                    lin_playlists.setVisibility(View.VISIBLE);
                }
            }
        }
    }
   @OnClick(R.id.img_back)
   public void img_back()
  {
      if(App.isOrientationFlag()) {
          if(App.getTemp_bundle().containsKey("call_from")) {
              String call_from = App.getTemp_bundle().getString("call_from");
               Logs.info(TAG+"_call_from",""+call_from);
              switch (call_from) {
                  case "device_type":
                      ReplaceFragment.replaceFragment(this, R.id.frm_elemenmain_container, DevicesByType.newInstance());
                      break;
                  case "room_devices":
                      ReplaceFragment.replaceFragment(this, R.id.frm_elemenmain_container, RoomDevicesFragment.newInstance());
                      break;
                  default:
                      ReplaceFragment.replaceFragment(this, R.id.frm_elemenmain_container, FavoritesMainFragment.newInstance());
                      break;
              }
          }
      }
      else {
          if(App.getTemp_bundle().containsKey("call_from")) {
              String call_from = App.getTemp_bundle().getString("call_from");
               Logs.info(TAG+"_call_from",""+call_from);
              switch (call_from) {
                  case "device_type":
                      Bundle bundle = new Bundle();
                      bundle.putString("DEVICE_TYPE", "Audio");
                      Fragment fragment = DevicesByType.newInstance();
                      fragment.setArguments(bundle);
                      ReplaceFragment.replaceFragment(this, R.id.frm_main_container, fragment);
                      break;
                  case "room_devices":
                      ReplaceFragment.replaceFragment(this, R.id.frm_main_container, RoomDevicesFragmentPortrait.newInstance());
                      break;
                  default:
                      ReplaceFragment.replaceFragment(this, R.id.frm_main_container, FavoriteMenuMainFragment.newInstance());
                      break;
              }
          }
      }
  }
  public void setCurrentMusic()
  {
      boolean current_music=false;
      JSONArray  jsonArray=null;
      try {
           jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
           Logs.info(TAG+"_PlayListArray",device_id+""+jsonArray);
          for (int i = 0; i < jsonArray.length(); i++) {
              JSONObject jsonObject = jsonArray.getJSONObject(i);
              if(jsonObject.getString("Id").equals(device_id))
              {
                   Logs.info(TAG+"_Sonosdata",""+jsonObject);
                  current_music=true;
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
                      JSONObject current_track_json = jsonObject.getJSONObject("CURRENT_TRACK");
                      if (current_track_json.getString("title").equals(mcontext.getResources().getString(R.string.song_title)) && current_track_json.getString("artist").equals(mcontext.getResources().getString(R.string.artist_name))) {
                          lin_controls.setVisibility(View.GONE);
                      } else {
                          lin_controls.setVisibility(View.VISIBLE);
                      }
                      try {
                          if(App.getDefault_song_hash().containsKey(current_track_json.getString("title")))
                          {
                              setDefaultButtonByDefaultSong(false);
                          }
                          else
                          {
                              setDefaultButtonByDefaultSong(true);
                          }
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
                      String artist_name=""+current_track_json.getString("artist");
                      if(App.isOrientationFlag()) {
                          txt_muisc_artist_name.setText(artist_name.equals("null") ? "" : artist_name);
                          txt_music_title.setText(current_track_json.getString("title"));
                      }
                      else {
                          txt_music_title.setText(current_track_json.getString("title") + "•" + (artist_name.equals("null") ? "" : artist_name));
                      }
                      str_current_song_id=current_track_json.getString("title");
                      seek_music_vol.setOnSeekBarChangeListener(null);
                      seek_music_vol.setProgress(jsonObject.getInt("CML_VOLUME"));
                      seek_music_vol.setOnSeekBarChangeListener(this);
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
                                  .placeholder(R.drawable.ic_playlist_default_white)
                                  .override(50, 50)
                                  .dontAnimate()
                                  .diskCacheStrategy(DiskCacheStrategy.ALL)
                                  .into(img_album_image);
                      }
                  }
              }
          }
          if(current_music)
          {
              lin_music_panel.setVisibility(View.VISIBLE);
          }
          else
          {
              lin_music_panel.setVisibility(View.GONE);
          }
      }
      catch (Exception ex)
      {
           Logs.error(TAG+"_PlayListError",ex.getMessage());
      }
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
                        JSONObject current_track_json = jsonObject.getJSONObject("CURRENT_TRACK");
                        if (current_track_json.has("title") && current_track_json.has("artist")) {
                            current_music=true;
                            String artist_name=""+current_track_json.getString("artist");
                            if(App.isOrientationFlag()) {
                                txt_muisc_artist_name.setText(artist_name.equals("null") ? "" : artist_name);
                                txt_music_title.setText(current_track_json.getString("title"));
                            }
                            else {
                                txt_music_title.setText(current_track_json.getString("title") + " • " + (artist_name.equals("null") ? "" : artist_name));
                            }
                            str_current_song_id= current_track_json.getString("title");
                        } else {
                            current_music=false;
                        }
                        seek_music_vol.setOnSeekBarChangeListener(null);
                        seek_music_vol.setProgress(jsonObject.getInt("CML_VOLUME"));
                        seek_music_vol.setOnSeekBarChangeListener(this);
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
            if(current_music)
            {
                lin_music_panel.setVisibility(View.VISIBLE);
            }
            else
            {
                lin_music_panel.setVisibility(View.GONE);
            }
        }
        catch (Exception ex)
        {
            Logs.info(TAG+"_PlayListError",ex.getMessage());
        }
    }
  public void getMusicPlaylist()
  {
      sonosArrayList.clear();
      boolean default_sound_state=false;
      HashMap<String,String> default_song_hash=new HashMap<>();
      try {
          if(App.getSoundData()!=null) {
              JSONArray jsonArray = App.getSoundData().getJSONArray("data");
              Logs.info(TAG + "_defaultsoundArray", "" + jsonArray);
              for (int i = 0; i < jsonArray.length(); i++) {
                  JSONObject jsonObject = jsonArray.getJSONObject(i);
                  Playlist s = new Playlist();
                  s.setPlaylist_title(jsonObject.getString("CML_TITLE"));
                  s.setPlaylist_id(jsonObject.getString("CML_ID"));
                  s.setType("default_music");
                  s.setPlaylist_album_art("");
                  sonosArrayList.add(s);
                  if(getDefaultSoundState(jsonObject.getString("CML_TITLE"))) {
                      default_sound_state=true;
                  }
                  default_song_hash.put(jsonObject.getString("CML_TITLE"),jsonObject.getString("CML_TITLE").replace(".m4a","").replace("_"," ").replace(".mp3",""));
              }
              App.setDefault_song_hash(default_song_hash);
          }
          if(App.getMusicPlaylistJson()!=null) {
              JSONArray jsonArray1 = App.getMusicPlaylistJson().getJSONArray("data");
              Logs.info(TAG + "_PlayListArray", "" + jsonArray1);
              for (int i = 0; i < jsonArray1.length(); i++) {
                  JSONObject jsonObject = jsonArray1.getJSONObject(i);
                  Playlist s = new Playlist();
                  s.setPlaylist_title(jsonObject.getString("CML_TITLE"));
                  s.setPlaylist_id(jsonObject.getString("Id"));
                  if (jsonObject.has("Name")) s.setType(jsonObject.getString("Name"));
                  if (jsonObject.has("Album_Art"))
                      s.setPlaylist_album_art(jsonObject.getString("Album_Art"));
                 if(!default_sound_state) {
                     if (jsonObject.has("selectedFlag")) {
                         s.setLoad(jsonObject.getBoolean("selectedFlag"));
                     } else {
                         s.setLoad(false);
                     }
                 }
                 else
                 {
                     s.setLoad(false);
                 }
                  sonosArrayList.add(s);
              }
          }
      }
      catch (Exception ex)
      {
           Logs.info(TAG+"_PlayListError",ex.getMessage());
      }
  }
    public void setTypeImage(String type)
    {
        switch (type)
        {
            case "Bedroom":
                img_room_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_bedroom_type));
                break;
            case "Living Room":
                img_room_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_living_room_type));
                break;
            case "Bathroom":
                img_room_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_bedroom_type));
                break;
            case "Outdoor":
                img_room_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_bedroom_type));
                break;
            case "Kitchen":
                img_room_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_kitchen_type));
                break;
            case "whole_home":
                img_room_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.bg_whole_home));
                break;
        }
    }
    public static String getRoomType(String room_id)
    {
        String room_title="";
        try {
            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("CML_ID").equals(room_id)) {
                    room_title = jsonObject.getString("CML_ROOM_TYPE");
                }
            }
        }
        catch (Exception ex){ Logs.info("RoomDevicesmusic_Error",ex.getMessage());}
        return room_title;
    }
    public static String getRoomTitle(String room_id)
    {
        String room_title="";
        try {
            JSONArray jsonArray = App.getRoomData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("CML_ID").equals(room_id)) {
                    room_title = jsonObject.getString("CML_TITLE");
                }
            }
        }
        catch (Exception ex){ Logs.info("RoomDevices_Music"+"_Error",ex.getMessage());}
        return room_title;
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
        if(!device_id.equals("whole_home")) {
            setMusicNext(device_id);
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
    @OnClick(R.id.img_music_prev)
    public void img_music_prev()
    {
        if(!device_id.equals("whole_home")) {
            setPrvious(device_id);
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
    @OnClick(R.id.img_music_play_pause)
    public void music_play_pause()
    {
        if(!device_id.equals("whole_home")) {
            playPauseMusic(play, device_id);
        }
        else
        {
            try {
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject(play?"home_pause_song":"home_play_song", new JSONObject()));
            }
            catch (Exception ex)
            {
                Logs.error(TAG,"----------"+ex.getMessage());
            }
        }
    }
    public void musicAction(String str_action,String device_id)
    {
        try {
                JSONObject jsonData = new JSONObject();
                switch (str_action) {
                    case "shuffle":
                        jsonData.put("mode", "SHUFFLE");
                        break;
                    case "normal":
                        jsonData.put("mode", "NORMAL");
                        break;
                    case "repeat":
                        jsonData.put("mode", "REPEAT_ALL");
                        break;
                    case "shuffle_no_repeat":
                        jsonData.put("mode", "SHUFFLE_NOREPEAT");
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
             Logs.info(TAG+"_PlayJson", "" + ex.getMessage());
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
    }
    @OnClick(R.id.img_up_music)
    public void img_up_music()
    {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom, R.anim.enter_from_top, R.anim.exit_to_top);
        fragmentTransaction.replace(R.id.frm_main_container, RoomDevicesUpMusicFragment.newInstance());
        fragmentTransaction.commit();
    }
    @OnClick(R.id.btn_dawn_cancle)
    public void btn_dawn_cancle()
    {
        ReplaceFragment.replaceFragment(this,R.id.frm_elemenmain_container,RoomsMainFragment.newInstance());
    }
    @OnClick(R.id.btn_sleep_cancle)
    public void btn_sleep_cancle()
    {
        ReplaceFragment.replaceFragment(this,R.id.frm_elemenmain_container,RoomsMainFragment.newInstance());
    }
    @OnClick(R.id.btn_dawn_confirm)
    public void btn_dawn_confirm()
    {
        try {
            JSONObject dataJsonObject=getSimulatuonObject("dawn");
            //  jsonObject.put("status",true);
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("stop_dawn_simulation", dataJsonObject.put("status", false)));
            RemoveNotification.removeSpecificDawnNotification(mcontext,dataJsonObject.getString("CML_ID"));
        }
        catch (Exception ex){}
    }
    @OnClick(R.id.btn_sleep_confirm)
    public void btn_sleep_confirm()
    {
        Logs.info(TAG+"_WhereIM","IMInSleepConfirm");
        try {
            JSONObject dataJsonObject=getSimulatuonObject("sleep");
            //  jsonObject.put("status",true);
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("stop_sleep_simulation", dataJsonObject.put("status", false)));
            RemoveNotification.removeSpecificDawnNotification(mcontext,dataJsonObject.getString("CML_ID"));
        }
        catch (Exception ex){
            Logs.error(TAG+"_Sleep_halt_error",""+ex.getMessage());
        }
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
    public void checkDawnState()
    {
        if(getDawnState(room_id))
        {
            backNavigate();
            /*lin_dawn_running.setVisibility(View.VISIBLE);
            flag_disable_all_views_for_dawn =true;
            disableAllViews(lin_music_panel);*/
        }
       /* else
        {
            lin_dawn_running.setVisibility(View.GONE);
            flag_disable_all_views_for_dawn =false;
            disableAllViews(lin_music_panel);
        }*/
    }
    public void checkSleepState()
    {
        if(getSleepState(room_id))
        {
            backNavigate();
            /*lin_sleep_running.setVisibility(View.VISIBLE);
            flag_disable_all_views_for_sleep =true;
            disableAllViews(lin_music_panel);*/
        }
        /*else
        {
            *//*lin_sleep_running.setVisibility(View.GONE);
            flag_disable_all_views_for_sleep =false;
            disableAllViews(lin_music_panel);*//*
        }*/
    }
    public void backNavigate()
    {
        if(App.isOrientationFlag()) {
            if(App.getTemp_bundle().containsKey("call_from")) {
                String call_from = App.getTemp_bundle().getString("call_from");
                Logs.info(TAG+"_call_from",""+call_from);
                switch (call_from) {
                    case "device_type":
                        ReplaceFragment.replaceFragment(this, R.id.frm_elemenmain_container, DevicesByType.newInstance());
                        break;
                    case "room_devices":
                        ReplaceFragment.replaceFragment(this, R.id.frm_elemenmain_container, RoomDevicesFragment.newInstance());
                        break;
                    default:
                        ReplaceFragment.replaceFragment(this, R.id.frm_elemenmain_container, FavoritesMainFragment.newInstance());
                        break;
                }
            }
        }
        else {
            if(App.getTemp_bundle().containsKey("call_from")) {
                String call_from = App.getTemp_bundle().getString("call_from");
                Logs.info(TAG+"_call_from",""+call_from);
                switch (call_from) {
                    case "device_type":
                        Bundle bundle = new Bundle();
                        bundle.putString("DEVICE_TYPE", "Audio");
                        Fragment fragment = DevicesByType.newInstance();
                        fragment.setArguments(bundle);
                        ReplaceFragment.replaceFragment(this, R.id.frm_main_container, fragment);
                        break;
                    case "room_devices":
                        ReplaceFragment.replaceFragment(this, R.id.frm_main_container, RoomDevicesFragmentPortrait.newInstance());
                        break;
                    default:
                        ReplaceFragment.replaceFragment(this, R.id.frm_main_container, FavoriteMenuMainFragment.newInstance());
                        break;
                }
            }
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
    public  void disableRecyclerview(RecyclerView rec) {
        rec.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return flag_disable_all_views_for_dawn || flag_disable_all_views_for_sleep;
            }
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(seekBar.getId()==R.id.seek_music_vol)
        {
            setMusicVolume(device_id,seekBar.getProgress());
        }
    }
    public boolean getMusicPositionFlag(String device_id,int pos)
    {
        boolean music_flag=false;
        try {
            JSONArray jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.getString("Id").equals(device_id))
                {
                    if(jsonObject.has("SLEEP_RUNNING")) {
                      if(jsonObject.getJSONObject("SONOS_PLAYLIST").getInt("queueLength")==pos)  music_flag=true;
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Logs.error(TAG+"_DawnRunninError",""+ex.getMessage());
        }
        return music_flag;
    }
    public void disableAllViews(View v){
        v.setEnabled(!(flag_disable_all_views_for_sleep||flag_disable_all_views_for_dawn));
        if(v instanceof ViewGroup){
            for (int i = 0; i < ((ViewGroup)v).getChildCount();  i++) {
                View view = ((ViewGroup)v).getChildAt(i);
                disableAllViews(view);
            }
        }
    }

    public boolean getDefaultSoundState(String sound_title)
    {
        boolean b=false;
        try {
            JSONArray jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
            Logs.info(TAG + "_PlayListArray", device_id + "" + jsonArray);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("Id").equals(device_id)) {
                    if(sound_title.equals(jsonObject.getJSONObject("CURRENT_TRACK").getString("title")))return true;
                }
            }
        }
        catch (Exception ex)
        {
            Logs.error(TAG,"--------"+ex.getMessage());
        }
        return b;
    }
    public void setDefaultButtonByDefaultSong(boolean b)
    {
        img_music_prev.setClickable(b);
        img_music_shuffule.setClickable(b);
        img_music_prev.setClickable(b);
        img_music_repeat.setClickable(b);
        img_music_shuffule.setAlpha(!b?0.5f:1f);
        img_music_repeat.setAlpha(!b?0.5f:1f);
        img_music_next.setAlpha(!b?0.5f:1f);
        img_music_prev.setAlpha(!b?0.5f:1f);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.gc();
        rec_default_music.removeAllViewsInLayout();
        rec_playlists.removeAllViewsInLayout();
        rec_radio_stations.removeAllViewsInLayout();
    }
}