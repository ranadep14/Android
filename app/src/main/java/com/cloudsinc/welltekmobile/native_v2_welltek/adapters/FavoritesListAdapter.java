package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.SceneDialog;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.WholeHomeSceneDialog;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms.RoomDevicesMusicFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule.RemoveNotification;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Device;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Favorites;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FavoritesOperations;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.SwitchTrackChange;
import com.xw.repo.BubbleSeekBar;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.GROUPS;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.PROVISIONAL_DEVICES;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.ROOMS;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.ZONES;
/**
 * This class containing functionality related to displaying favorite list and perform action on it.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 *
 */
public class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesListAdapter.ViewHolder>{
    private ArrayList<Favorites> arrayList;
    private Context mcontext;
    private Fragment mfrag;
    private int open_flag[];
    private int close_flag[];
    private String response_string="";
    private boolean check_whole_light_state_flag=false,check_whole_light_status_flag=false;
    private int check_whole_light_flag=-1;
    private int playState[];
    private FragmentManager mfm;
    private String TAG=FavoritesListAdapter.this.getClass().getSimpleName();
    private Dialog dawn_sleep_dialog;
    private static Typeface typeface;
    private int whole_home_light[];
    private int bool_state=-1;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView txt_room_title,txt_audio_room_title,txt_audio_title,txt_group_title,txt_group_room_title,txt_device_room_title,txt_blind_room_title,txt_device_title,txt_whole_music_title,txt_whole_muisc_artist_name;
        private ImageView img_whole_album_image;
        private Switch swt_on_off;
        private Switch swt_on_off_dummy;
        private Switch whole_on_off;
        private Switch swt_all_light_on_off;
        private Switch group_on_off;
        private Switch group_on_off_dummy;
        private ImageView img_brightness;
        private ImageView img_all_fav;
        private ImageView img_add_group_fav;
        private ImageView img_light_option;
        private ImageView img_group_option;
        private ImageView whole_img_option;
        private SeekBar seek_brightness;
        private SeekBar seek_all_light_brightness;
        private SeekBar seek_group_brightness;
        private Button btn_blind_on_off;
        private Button btn_whole_close;
        private Button btn_whole_open;
        private LinearLayout lin_music;
        private LinearLayout lin_light;
        private LinearLayout lin_whole_home_light;
        private LinearLayout lin_whole_home_music;
        private LinearLayout lin_whole_home_blind;
        private LinearLayout lin_light_group;
        private TextView txt_title ;
        private TextView txt_type ;
        private Button btn_open;
        private Button btn_close;
        private Button btn_stop;
        private Button btn_whole_stop;
        private TextView txt_all_light_title ;
        private BubbleSeekBar seek_blinds ;
        private ProgressBar circular_whole_home_on_off;
        private TextView txt_muisc_artist_name=null;
        private TextView txt_id=null;
        private TextView txt_music_title=null;
        private DiscreteSeekBar seek_music_vol=null;
        private ImageView img_add_light_fav =null;
        private ImageView img_add_blind_fav =null;
        private ImageView img_add_music_fav =null;
        private ImageView img_whole_light_add_fav =null;
        private ImageView img_whole_blind_add_fav =null;
        private ImageView img_whole_music_add_fav =null;
        private ImageView img_whole_music_prev =null;
        private ImageView img_whole_music_play_pause =null;
        private ImageView img_whole_music_next =null;
        private ImageView img_whole_playlist =null;
        private ImageView img_music_play_pause=null;
        private ImageView img_music_prev=null;
        private ImageView img_music_next=null;
        private ImageView img_music_mute=null;
        private ImageView img_music_shuffule=null;
        private ImageView img_album_image=null;
        private ImageView img_music_repeat=null;
        private ImageView img_playlist=null;
        private LinearLayout lin_no_playlist=null;
        private LinearLayout lin_title_name=null;
        private LinearLayout lin_room_all_light=null;
        private LinearLayout lin_music_bottom=null;
        private LinearLayout lin_blind=null;
        private LinearLayout lin_light_offline=null;
        private LinearLayout lin_whole_home_light_offline=null;
        private LinearLayout lin_light_group_offline=null;
        private RelativeLayout rel_visiblity=null;
        private Button btn_load_playlist=null;
        public ViewHolder(View v) {
            super(v);
            this.txt_room_title = itemView.findViewById(R.id.txt_room_title);
            this.txt_audio_room_title = itemView.findViewById(R.id.txt_audio_room_title);
            this.txt_audio_title = itemView.findViewById(R.id.txt_audio_title);
            this.txt_blind_room_title = itemView.findViewById(R.id.txt_blind_room_title);
            this.txt_group_title = itemView.findViewById(R.id.txt_group_title);
            this.txt_group_room_title = itemView.findViewById(R.id.txt_group_room_title);
            this.txt_device_room_title = itemView.findViewById(R.id.txt_device_room_title);
            this.txt_all_light_title = itemView.findViewById(R.id.txt_all_light_title);
            this.txt_device_title = itemView.findViewById(R.id.txt_device_title);
            this.swt_on_off = itemView.findViewById(R.id.swt_on_off);
            this.swt_on_off_dummy = itemView.findViewById(R.id.swt_on_off_dummy);
            this.swt_all_light_on_off = itemView.findViewById(R.id.swt_all_light_on_off);
            this.group_on_off = itemView.findViewById(R.id.group_on_off);
            this.group_on_off_dummy = itemView.findViewById(R.id.group_on_off_dummy);
            this.seek_brightness = itemView.findViewById(R.id.seek_brightness);
            this.seek_all_light_brightness = itemView.findViewById(R.id.seek_all_light_brightness);
            this.seek_group_brightness = itemView.findViewById(R.id.seek_group_brightness);
            this.btn_blind_on_off = itemView.findViewById(R.id.btn_blind_on_off);
            this.img_brightness = itemView.findViewById(R.id.img_brightness);
            this.img_add_light_fav = itemView.findViewById(R.id.img_add_light_fav);
            this.img_add_blind_fav = itemView.findViewById(R.id.img_add_blind_fav);
            this.img_light_option = itemView.findViewById(R.id.img_light_option);
            this.img_group_option = itemView.findViewById(R.id.img_group_option);
            this.img_add_music_fav = itemView.findViewById(R.id.img_add_music_fav);
            this.img_all_fav = itemView.findViewById(R.id.img_all_fav);
            this.img_add_group_fav = itemView.findViewById(R.id.img_add_group_fav);
            this.img_whole_album_image = itemView.findViewById(R.id.img_whole_album_image);
            this.lin_music = itemView.findViewById(R.id.lin_music);
            this.lin_blind = itemView.findViewById(R.id.lin_blind);
            this.lin_light = itemView.findViewById(R.id.lin_light);
            this.lin_whole_home_light_offline = itemView.findViewById(R.id.lin_whole_home_light_offline);
            this.lin_light_offline = itemView.findViewById(R.id.lin_light_offline);
            this.lin_light_group_offline = itemView.findViewById(R.id.lin_light_group_offline);
            this.lin_light_group = itemView.findViewById(R.id.lin_light_group);
            this.txt_id = v.findViewById(R.id.txt_id); // title
            this.txt_title = v.findViewById(R.id.txt_title); // artist name
            this.txt_type = v.findViewById(R.id.txt_type); // artist name
            this.btn_open = v.findViewById(R.id.btn_open); // artist name
            this.btn_close = v.findViewById(R.id.btn_close); // artist name
            this.btn_stop = v.findViewById(R.id.btn_stop); // artist name
            this.btn_whole_stop = v.findViewById(R.id.btn_whole_stop); // artist name
            this.seek_blinds = v.findViewById(R.id.seek_blind); // artist name
            this.txt_muisc_artist_name = v.findViewById(R.id.txt_muisc_artist_name); // title
            this.txt_music_title = v.findViewById(R.id.txt_music_title); // artist name
            this.seek_music_vol = v.findViewById(R.id.seek_music_vol); // artist name
            this.circular_whole_home_on_off = v.findViewById(R.id.circular_whole_home_on_off); // artist name
            this.img_music_play_pause = v.findViewById(R.id.img_music_play_pause);
            this.img_music_prev = v.findViewById(R.id.img_music_prev);
            this.img_music_next = v.findViewById(R.id.img_music_next);
            this.img_album_image = v.findViewById(R.id.img_album_image);
            this.img_playlist = v.findViewById(R.id.img_playlist);
            this.img_music_mute = v.findViewById(R.id.img_music_mute);
            this.img_music_shuffule = v.findViewById(R.id.img_music_shuffule);
            this.img_music_repeat = v.findViewById(R.id.img_music_repeat);
            this.lin_no_playlist = v.findViewById(R.id.lin_no_playlist);
            this.rel_visiblity = v.findViewById(R.id.rel_visiblity);
            this.lin_music_bottom = v.findViewById(R.id.lin_music_bottom);
            this.lin_title_name = v.findViewById(R.id.lin_title_name);
            this.lin_room_all_light = v.findViewById(R.id.lin_room_all_light);
            this.btn_load_playlist = v.findViewById(R.id.btn_load_playlist);
            //whole home
            this.img_whole_light_add_fav =v.findViewById(R.id.img_whole_light_add_fav);
            this.img_whole_blind_add_fav =v.findViewById(R.id.img_whole_blind_add_fav);
            this.img_whole_music_add_fav =v.findViewById(R.id.img_whole_music_add_fav);
            this.img_whole_music_prev =v.findViewById(R.id.img_whole_music_prev);
            this.img_whole_music_play_pause =v.findViewById(R.id.img_whole_music_play_pause);
            this.img_whole_music_next =v.findViewById(R.id.img_whole_music_next);
            this.img_whole_playlist =v.findViewById(R.id.img_whole_playlist);
            this.lin_whole_home_light=v.findViewById(R.id.lin_whole_home_light);
            this.lin_whole_home_music=v.findViewById(R.id.lin_whole_home_music);
            this.lin_whole_home_blind=v.findViewById(R.id.lin_whole_home_blind);
            this.btn_whole_close=v.findViewById(R.id.btn_whole_close);
            this.btn_whole_open=v.findViewById(R.id.btn_whole_open);
            this.txt_whole_music_title=v.findViewById(R.id.txt_whole_music_title);
            this.txt_whole_muisc_artist_name=v.findViewById(R.id.txt_whole_muisc_artist_name);
            this.whole_on_off=v.findViewById(R.id.whole_on_off);
            this.whole_img_option =v.findViewById(R.id.whole_img_option);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public FavoritesListAdapter(Fragment mfrag,Context context, ArrayList<Favorites> myDataset,FragmentManager fm,String response_string) {
        arrayList = myDataset;
        this.mfrag=mfrag;
        mcontext=context;
        this.response_string=response_string;
        mfm=fm;
        open_flag =new int[arrayList.size()];
        for(int i=0;i<arrayList.size();i++)
        {
            open_flag[i]=0;
        }
        close_flag =new int[arrayList.size()];
        for(int i=0;i<arrayList.size();i++)
        {
            close_flag[i]=0;
        }
        playState=new int[arrayList.size()];
        for(int i=0;i<arrayList.size();i++)
        {
            playState[i]=0;
        }
        whole_home_light=new int[arrayList.size()];
        for(int i=0;i<arrayList.size();i++)
        {
            whole_home_light[i]=-1;
        }
    }
    // Create new views (invoked by the layout manager)
    @Override
    public FavoritesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_favorites_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.lin_light_group.setVisibility(View.GONE);
        holder.lin_light.setVisibility(View.GONE);
        holder.lin_blind.setVisibility(View.GONE);
        holder.lin_music.setVisibility(View.GONE);
        holder.lin_whole_home_light.setVisibility(View.GONE);
        holder.lin_whole_home_blind.setVisibility(View.GONE);
        holder.lin_whole_home_music.setVisibility(View.GONE);
        String string_id = arrayList.get(position).getFav_id();
        if (!(string_id.contains("light") || string_id.contains("audio") || string_id.contains("blind"))) {
            try {
                JSONObject jsonObject = App.getDataStorageJson().getJSONObject(PROVISIONAL_DEVICES).getJSONObject(arrayList.get(position).getDevice_id());
                String device_type = jsonObject.getString("type");
                boolean check_light_state_flag = false;
                boolean check_light_status_flag = false;
                if (device_type.equals("Lighting")) {
                    if(checkGroup(jsonObject)) {
                        holder.lin_light_group.setVisibility(View.GONE);
                        holder.lin_light.setVisibility(View.VISIBLE);
                        holder.lin_blind.setVisibility(View.GONE);
                        holder.lin_music.setVisibility(View.GONE);
                        holder.swt_on_off.setOnCheckedChangeListener(null);
                        holder.swt_on_off_dummy.setOnCheckedChangeListener(null);
                        holder.swt_on_off.setChecked(jsonObject.getBoolean("CML_POWER"));
                        holder.swt_on_off_dummy.setChecked(jsonObject.getBoolean("CML_POWER"));
                        SwitchTrackChange.changeTrackColor(mcontext, holder.swt_on_off);
                        if (!jsonObject.getBoolean("CML_POWER")) {
                            check_light_state_flag = false;
                            holder.img_light_option.setColorFilter(mcontext.getResources().getColor(R.color.fragment_blue_light), PorterDuff.Mode.SRC_IN);
                            holder.img_light_option.setEnabled(false);
                        } else {
                            check_light_state_flag = true;
                            holder.img_light_option.setColorFilter(mcontext.getResources().getColor(R.color.familyname_select_color), PorterDuff.Mode.SRC_IN);
                            holder.img_light_option.setEnabled(true);
                        }

                        holder.seek_brightness.setProgress(jsonObject.getInt("CML_BRIGHTNESS"));
                        holder.txt_device_title.setText(jsonObject.getString("CML_TITLE"));
                        holder.txt_room_title.setText(getRoomTitle("" + jsonObject.getString("room")));
                        holder.txt_device_room_title.setText(getRoomTitle(jsonObject.getString("room")));
                        if (jsonObject.getBoolean("CML_STATUS")) {
                            check_light_status_flag = true;
                            disableAllViews(holder.lin_light_offline, true);
                            holder.rel_visiblity.setVisibility(View.GONE);
                        } else {
                            check_light_status_flag = false;
                            disableAllViews(holder.lin_light_offline, false);
                            holder.rel_visiblity.setVisibility(View.VISIBLE);
                        }
                        if (check_light_state_flag && check_light_status_flag) {
                            holder.img_light_option.setEnabled(true);
                        } else {
                            holder.img_light_option.setEnabled(false);
                        }
                        holder.img_light_option.setEnabled(true);
                    }
                }
                if (device_type.equals("Blinds")) {
                    if(checkGroup(jsonObject)) {
                        holder.lin_light.setVisibility(View.GONE);
                        holder.lin_blind.setVisibility(View.VISIBLE);
                        holder.lin_music.setVisibility(View.GONE);
                        holder.rel_visiblity.setVisibility(View.GONE);
                        holder.txt_title.setText(jsonObject.getString("CML_TITLE"));
                        holder.txt_type.setText("ind");
                        holder.txt_blind_room_title.setText(getRoomTitle(jsonObject.getString("room")));
                        if (jsonObject.has("CML_SET_POINT")) {
                            holder.seek_blinds.setOnProgressChangedListener(null);
                            if (!jsonObject.get("CML_SET_POINT").toString().equals("null"))
                                holder.seek_blinds.setProgress(jsonObject.getInt("CML_SET_POINT"));
                        }
                        if (jsonObject.has("CML_POWER")) {
                            if (jsonObject.getBoolean("CML_POWER")) {
                                open_flag[position] = 1;
                                setButtonSelection(holder.btn_open, holder.btn_open, holder.btn_close);
                            } else {
                                close_flag[position] = 0;
                                open_flag[position] = 0;
                                setButtonSelection(holder.btn_close, holder.btn_open, holder.btn_close);
                            }
                        }
                    }
                }
                if (device_type.equals("Music")) {
                    holder.lin_light.setVisibility(View.GONE);
                    holder.lin_blind.setVisibility(View.GONE);
                    holder.lin_music.setVisibility(View.VISIBLE);
                    holder.rel_visiblity.setVisibility(View.GONE);
                    Logs.info(TAG + "_MusicStat", jsonObject.getString("CML_STATE"));
                    if (jsonObject.getString("CML_STATE").equals("playing")) {
                        playState[position] = 1;
                        holder.img_music_play_pause.setImageResource(R.drawable.ic_music_pause_blue);
                    } else {
                        playState[position] = 0;
                        holder.img_music_play_pause.setImageResource(R.drawable.ic_playlist);
                    }
                    holder.txt_audio_room_title.setText(getRoomTitle(jsonObject.getString("room")));
                    holder.txt_audio_title.setText(jsonObject.getString("CML_TITLE"));
                    JSONObject current_track_json = jsonObject.getJSONObject("CURRENT_TRACK");
                    if (current_track_json.has("title")) {
                        holder.txt_music_title.setText(current_track_json.getString("title"));
                        String artist_name = current_track_json.getString("artist");
                        holder.txt_muisc_artist_name.setText(artist_name.equals("null") ? "" : artist_name);
                    } else {
                        holder.txt_music_title.setText(mcontext.getResources().getString(R.string.play_song_text));
                        holder.txt_music_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    }
                    holder.seek_music_vol.setProgress(jsonObject.getInt("CML_VOLUME"));
                    if (current_track_json.has("albumArtURL")) {
                        Glide.with(mcontext).load(current_track_json.getString("albumArtURL"))
                                .thumbnail(Glide.with(mcontext).load(R.drawable.ic_playlist_default))
                                .error(R.drawable.ic_playlist_default)
                                .override(50, 50)
                                .dontAnimate()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.img_album_image);
                    }
                }
            }
            catch (Exception ex) {
                Logs.error(TAG,""+ex.getMessage());
            }



                try {

                        JSONObject jsonObject=App.getDataStorageJson().getJSONObject(GROUPS).getJSONObject(arrayList.get(position).getDevice_id());
                            String group_type = jsonObject.getString("groupType");
                            if (group_type.equals("Lighting")) {
                                boolean check_light_state_flag = false;
                                boolean check_light_status_flag = false;
                                holder.lin_light_group.setVisibility(View.VISIBLE);
                                holder.lin_light.setVisibility(View.GONE);
                                holder.lin_blind.setVisibility(View.GONE);
                                holder.lin_music.setVisibility(View.GONE);
                                holder.group_on_off_dummy.setOnCheckedChangeListener(null);
                                if (jsonObject.has("CML_POWER")) {
                                    holder.group_on_off.setChecked(jsonObject.getBoolean("CML_POWER"));
                                    holder.group_on_off_dummy.setChecked(jsonObject.getBoolean("CML_POWER"));
                                    SwitchTrackChange.changeTrackColor(mcontext, holder.group_on_off);
                                    if (!jsonObject.getBoolean("CML_POWER")) {
                                        check_light_state_flag = false;
                                        holder.img_group_option.setColorFilter(mcontext.getResources().getColor(R.color.fragment_blue_light), PorterDuff.Mode.SRC_IN);
                                    } else {
                                        check_light_state_flag = true;
                                        holder.img_group_option.setColorFilter(mcontext.getResources().getColor(R.color.familyname_select_color), PorterDuff.Mode.SRC_IN);
                                    }
                                }
                                if (jsonObject.has("CML_BRIGHTNESS"))
                                    holder.seek_group_brightness.setProgress(jsonObject.getInt("CML_BRIGHTNESS"));
                                if (jsonObject.has("CML_TITLE"))
                                    holder.txt_group_title.setText(jsonObject.getString("CML_TITLE"));
                                if (jsonObject.has("room"))
                                    holder.txt_group_room_title.setText(getRoomTitle(jsonObject.getString("room")));
                                if (jsonObject.getBoolean("CML_STATUS")) {
                                    check_light_status_flag = true;
                                    disableAllViews(holder.lin_light_group_offline, true);
                                    holder.rel_visiblity.setVisibility(View.GONE);
                                } else {
                                    check_light_status_flag = false;
                                    disableAllViews(holder.lin_light_group_offline, false);
                                    holder.rel_visiblity.setVisibility(View.VISIBLE);
                                }
                                if (check_light_state_flag && check_light_status_flag) {
                                    holder.img_group_option.setEnabled(true);
                                } else {
                                    holder.img_group_option.setEnabled(false);
                                }
                                holder.img_group_option.setEnabled(true);
                            }
                            if (group_type.equals("Blinds")) {
                                holder.lin_light.setVisibility(View.GONE);
                                holder.lin_blind.setVisibility(View.VISIBLE);
                                holder.lin_music.setVisibility(View.GONE);
                                holder.rel_visiblity.setVisibility(View.GONE);
                                holder.txt_type.setText("group");
                                holder.txt_title.setText(jsonObject.getString("CML_TITLE"));
                                holder.txt_blind_room_title.setText(getRoomTitle(jsonObject.getString("room")));
                                if (jsonObject.has("CML_SET_POINT")) {
                                    holder.seek_blinds.setOnProgressChangedListener(null);
                                    if (!jsonObject.get("CML_SET_POINT").toString().equals("null"))
                                        holder.seek_blinds.setProgress(jsonObject.getInt("CML_SET_POINT"));
                                }
                                if (jsonObject.has("CML_POWER")) {
                                    if (jsonObject.getBoolean("CML_POWER")) {
                                        open_flag[position] = 1;
                                        setButtonSelection(holder.btn_open, holder.btn_open, holder.btn_close);
                                    } else {
                                        close_flag[position] = 0;
                                        open_flag[position] = 0;
                                        setButtonSelection(holder.btn_close, holder.btn_open, holder.btn_close);
                                    }
                                }

                            }



                } catch (Exception ex) {
                    Logs.error(TAG + "_GroupError", ex.getMessage());
                }

            holder.img_add_light_fav.setTag(position);
            holder.img_add_blind_fav.setTag(position);
            holder.img_add_music_fav.setTag(position);
            holder.img_all_fav.setTag(position);
            holder.img_add_group_fav.setTag(position);
            holder.img_add_light_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        int pos = (int) v.getTag();
                        FavoritesOperations.deleteFav(arrayList.get(pos).getFav_id());
                }
            });
            holder.img_add_blind_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    if (!checkDawnSleep(pos))
                        FavoritesOperations.deleteFav(arrayList.get(pos).getFav_id());
                }
            });
            holder.img_add_music_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    if (!checkDawnSleep(pos))
                        FavoritesOperations.deleteFav(arrayList.get(pos).getFav_id());
                }
            });
            holder.img_all_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    if (!checkDawnSleep(pos))
                        FavoritesOperations.deleteFav(arrayList.get(pos).getFav_id());
                }
            });
            holder.img_add_group_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    if (!checkDawnSleep(pos))
                        FavoritesOperations.deleteFav(arrayList.get(pos).getFav_id());
                }
            });
            holder.img_light_option.setTag(position);
            holder.img_light_option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    if (!checkDawnSleep(pos)) {
                        SceneDialog dFragment = new SceneDialog();
                        App.setCallfrom("favorite");
                        Bundle bundle = new Bundle();
                        bundle.putString("type", "light");
                        bundle.putString("scene_id", getLightSceneId(arrayList.get(pos).getDevice_id()));
                        bundle.putString("device_id", arrayList.get(pos).getDevice_id());
                        bundle.putString("room_type", getRoomType(arrayList.get(pos).getRoom_id()));
                        dFragment.setArguments(bundle);
                        dFragment.show(mfm, "individual");
                    }
                }
            });
            holder.img_group_option.setTag(position);
            holder.img_group_option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    if (!checkDawnSleep(pos)) {
                        SceneDialog dFragment = new SceneDialog();
                        Bundle bundle = new Bundle();
                        bundle.putString("type", "light");
                        App.setCallfrom("favorite");
                        bundle.putString("scene_id", getGroupSceneId(arrayList.get(pos).getDevice_id()));
                        bundle.putString("device_id", arrayList.get(pos).getDevice_id());
                        bundle.putString("room_type", getRoomType(arrayList.get(pos).getRoom_id()));
                        dFragment.setArguments(bundle);
                        dFragment.show(mfm, "group");
                    }
                }
            });
        /* Light Devices functionality */
            holder.seek_brightness.setTag(position);
            holder.seek_brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int pos = (int) seekBar.getTag();
                    setLightBrightness(arrayList.get(pos).getDevice_id(), seekBar.getProgress());
                }
            });
            holder.seek_group_brightness.setTag(position);
            holder.seek_group_brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int pos = (int) seekBar.getTag();
                    setGroupBrightness(arrayList.get(pos).getDevice_id(), seekBar.getProgress());
                }
            });
            holder.seek_all_light_brightness.setTag(position);
            holder.seek_all_light_brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int pos = (int) seekBar.getTag();
                    changeRoomBrightness(seekBar.getProgress(), arrayList.get(pos).getDevice_id());
                }
            });
            holder.swt_on_off_dummy.setTag(position);
            holder.swt_on_off_dummy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Logs.info(TAG + "_WhereIm", "InLightONOff");
                    holder.swt_on_off_dummy.setChecked(!isChecked);
                    SwitchTrackChange.changeTrackColor(mcontext, holder.swt_on_off_dummy);
                    Logs.info(TAG + "_Light_id", "" + arrayList.get((int) buttonView.getTag()).getDevice_id());
                    setLightState(arrayList.get((int) buttonView.getTag()).getDevice_id(), isChecked);
                }
            });
            holder.swt_all_light_on_off.setTag(position);
            holder.swt_all_light_on_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Logs.info(TAG + "_WhereIm", "InLightONOff");
                    // light_on_off.setChecked(!isChecked);
                    Logs.info(TAG + "_Light_id", "" + arrayList.get((int) buttonView.getTag()).getDevice_id());
                    changeRoomPower(arrayList.get((int) buttonView.getTag()).getDevice_id(), isChecked);
                }
            });
            holder.group_on_off_dummy.setTag(position);
            holder.group_on_off_dummy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    holder.group_on_off_dummy.setChecked(!isChecked);
                    SwitchTrackChange.changeTrackColor(mcontext, holder.group_on_off_dummy);
                    Logs.info(TAG + "_Light_id", "" + arrayList.get((int) buttonView.getTag()).getDevice_id());
                    setGroupState(arrayList.get((int) buttonView.getTag()).getDevice_id(), isChecked);
                }
            });
            /****************Blind devices functionality********************/
            holder.seek_blinds.setTag(position);
            holder.btn_open.setTag(position);
            holder.btn_close.setTag(position);
            holder.btn_stop.setTag(position);
            holder.img_music_play_pause.setTag(position);
            holder.seek_blinds.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
                @Override
                public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                }
                @Override
                public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                }
                @Override
                public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                    Logs.info(TAG + "_WhereIm", "In ProgressOnFinally");
                    setBlindPoint(progress, (int) holder.seek_blinds.getTag(), holder.txt_type.getText().toString());
                }
            });
            holder.btn_open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                /*if(open_flag[(int) holder.btn_open.getTag()]==0) {*/
                    if (!checkDawnSleep((int) holder.btn_close.getTag())) {
                        setBlindState(true, (int) holder.btn_open.getTag(), holder.txt_type.getText().toString());
                    }
                    // }
                }
            });
            holder.btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
               /* if(open_flag[(int) holder.btn_close.getTag()]==1) {*/
                    if (!checkDawnSleep((int) holder.btn_close.getTag())) {
                        setBlindState(false, (int) holder.btn_close.getTag(), holder.txt_type.getText().toString());
                    }
                    //}
                }
            });
            holder.btn_stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!checkDawnSleep((int) holder.btn_stop.getTag())) {
                        stopBlinds((int) holder.btn_stop.getTag(), holder.txt_type.getText().toString());
                    }
                }
            });
            /*********************Music devices functionality*****************************/
            holder.img_music_play_pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.setMusic_change(true);
                    int pos = (int) v.getTag();
                    Logs.info(TAG + "_WhereIm", "InIMGPLAYPAuse" + pos + "---------" + playState[pos] + "--------" + arrayList.get(pos).getDevice_id() + "------" + getMusicId(arrayList.get(pos).getDevice_id()));
                    int play = playState[pos];
                    if (play == 0) {
                        //  img_music_play_pause.setImageResource(R.drawable.ic_music_pause);
                        playState[pos] = 1;
                    } else {
                        //  img_music_play_pause.setImageResource(R.drawable.ic_music_play_white);
                        playState[pos] = 0;
                    }
                    if (!checkDawnSleep(pos))
                        playPauseMusic(play, getMusicId(arrayList.get(pos).getDevice_id()));
                }
            });
            holder.img_music_prev.setTag(position);
            holder.img_music_prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.setMusic_change(true);
                    int pos = (Integer) v.getTag();
                    if (!checkDawnSleep(pos))
                        setPrvious(getMusicId(arrayList.get(pos).getDevice_id()));
                }
            });
            holder.img_playlist.setTag(position);
            holder.img_playlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (Integer) v.getTag();
                    Bundle bundle = new Bundle();
                    bundle.putString("call_from", "favorites");
                    App.setTemp_bundle(bundle);
                    Device device = new Device();
                    device.setDevice_id(getMusicId(arrayList.get(pos).getDevice_id()));
                    App.setDevice(device);
                    if (!checkDawnSleep(pos)) {
                        if (App.isOrientationFlag())
                            ReplaceFragment.replaceFragment(mfrag, R.id.frm_elemenmain_container, RoomDevicesMusicFragment.newInstance());
                        else replacePortailFrag();
                    }
                }
            });
            holder.btn_load_playlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (Integer) v.getTag();
                    Bundle bundle = new Bundle();
                    bundle.putString("call_from", "favorites");
                    App.setTemp_bundle(bundle);
                    Device device = new Device();
                    device.setDevice_id(getMusicId(arrayList.get(pos).getDevice_id()));
                    App.setDevice(device);
                    if (!checkDawnSleep(pos)) {
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
                    App.setMusic_change(true);
                    int pos = (Integer) v.getTag();
                    if (!checkDawnSleep(pos))
                        setMusicNext(getMusicId(arrayList.get(pos).getDevice_id()));
                }
            });
            ///****************************disable component*************************///
            holder.seek_brightness.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Logs.info(TAG + "_WhereIm", "ImInBrightnessTouch");
                    boolean b = false;
                    int pos = (int) holder.seek_brightness.getTag();
                    b = checkDawnSleep(pos);
                    return b;
                }
            });
            holder.seek_group_brightness.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Logs.info(TAG + "_WhereIm", "ImInBrightnessTouch");
                    boolean b = false;
                    int pos = (int) holder.seek_group_brightness.getTag();
                    b = checkDawnSleep(pos);
                    return b;
                }
            });
            holder.seek_blinds.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    holder.seek_blinds.correctOffsetWhenContainerOnScrolling();
                    boolean b = false;
                    int pos = (int) holder.seek_blinds.getTag();
                    b = checkDawnSleep(pos);
                    return b;
                }
            });
            holder.swt_on_off_dummy.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    boolean b = false;
                    int pos = (int) holder.swt_on_off_dummy.getTag();
                    if (!checkDawnSleep(pos)) holder.swt_on_off_dummy.setClickable(true);
                    else holder.swt_on_off_dummy.setClickable(false);
                    return b;
                }
            });
            holder.group_on_off_dummy.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    boolean b = false;
                    int pos = (int) holder.group_on_off_dummy.getTag();
                    if (!checkDawnSleep(pos)) holder.group_on_off_dummy.setClickable(true);
                    else holder.group_on_off_dummy.setClickable(false);
                    return b;
                }
            });
        }
        else
        {
            boolean whole_on_off_flag=false;
                try {
                    Iterator<String> keys = App.getDataStorageJson().getJSONObject(ZONES).keys();
                    while (keys.hasNext()) {
                        JSONObject jsonObject=App.getDataStorageJson().getJSONObject(ZONES).getJSONObject(keys.next());
                        if (jsonObject.getString("CML_TITLE").equals("Whole Home")) {
                            // Light value set
                            if (arrayList.get(position).getFav_id().contains("light")) {
                                holder.lin_whole_home_light.setVisibility(View.VISIBLE);
                                if (jsonObject.has("CML_LIGHT_POWER")) {
                                  // if((check_whole_light_flag!=(jsonObject.getBoolean("CML_LIGHT_POWER")?1:0)) ){
                                        whole_on_off_flag=jsonObject.getBoolean("CML_LIGHT_POWER");
                                        whole_home_light[position]=jsonObject.getBoolean("CML_LIGHT_POWER") ? 1 : 0;
                                        check_whole_light_flag = jsonObject.getBoolean("CML_LIGHT_POWER") ? 1 : 0;
                                  //  }
                                    }
                                if (jsonObject.has("CML_STATUS")) {
                                    holder.rel_visiblity.setVisibility(jsonObject.getBoolean("CML_STATUS") ? View.GONE : View.VISIBLE);
                                    disableAllViews(holder.lin_whole_home_light_offline, jsonObject.getBoolean("CML_STATUS"));
                                    check_whole_light_status_flag = jsonObject.getBoolean("CML_STATUS");
                                }
                            }
                            // Blind value set
                            if (arrayList.get(position).getFav_id().contains("blind")) {
                                holder.lin_whole_home_blind.setVisibility(View.VISIBLE);
                                if (jsonObject.has("CML_BLIND_POWER")) {
                                    if (jsonObject.getBoolean("CML_BLIND_POWER")) {
                                        setButtonSelection(holder.btn_whole_open, holder.btn_whole_open, holder.btn_whole_close);
                                    } else {
                                        setButtonSelection(holder.btn_whole_close, holder.btn_whole_open, holder.btn_whole_close);
                                    }
                                }
                            }
                            // Music value set
                            if (arrayList.get(position).getFav_id().contains("audio")) {
                                holder.lin_whole_home_music.setVisibility(View.VISIBLE);
                                if (jsonObject.has("CML_STATE")) {
                                    if (jsonObject.getString("CML_STATE").equals("playing")) {
                                        playState[position] = 1;
                                        holder.img_whole_music_play_pause.setImageResource(R.drawable.ic_music_pause_blue);
                                    } else {
                                        playState[position] = 0;
                                        holder.img_whole_music_play_pause.setImageResource(R.drawable.ic_playlist);
                                    }
                                }
                                if (jsonObject.has("CURRENT_TRACK")) {
                                    JSONObject current_track_json = jsonObject.getJSONObject("CURRENT_TRACK");
                                    if (current_track_json.has("title")) {
                                        holder.img_whole_music_prev.setAlpha(1f);
                                        holder.img_whole_music_next.setAlpha(1f);
                                        holder.img_whole_music_play_pause.setAlpha(1f);
                                        holder.img_whole_music_prev.setEnabled(true);
                                        holder.img_whole_music_next.setEnabled(true);
                                        holder.img_whole_music_play_pause.setEnabled(true);
                                        holder.txt_whole_music_title.setText(current_track_json.getString("title"));
                                        String artist_name=current_track_json.getString("artist");
                                        holder.txt_whole_muisc_artist_name.setText(artist_name.equals("null")?"":artist_name);
                                    } else {
                                        holder.img_whole_music_prev.setAlpha(0.5f);
                                        holder.img_whole_music_next.setAlpha(0.5f);
                                        holder.img_whole_music_play_pause.setAlpha(0.5f);
                                        holder.img_whole_music_prev.setEnabled(false);
                                        holder.img_whole_music_next.setEnabled(false);
                                        holder.img_whole_music_play_pause.setEnabled(false);
                                        holder.txt_whole_music_title.setText(mcontext.getResources().getString(R.string.play_song_text));
                                        holder.txt_whole_music_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                                    }
                                    if (current_track_json.has("albumArtURL")) {
                                        Glide.with(mcontext).load(current_track_json.getString("albumArtURL"))
                                                .thumbnail(Glide.with(mcontext).load(R.drawable.ic_playlist_default))
                                                .placeholder(R.drawable.ic_playlist_default)
                                                .error(R.drawable.ic_playlist_default)
                                                .override(50, 50)
                                                .dontAnimate()
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(holder.img_whole_album_image);
                                    }
                                }
                            }
                            break;
                        }
                    }
                } catch (Exception ex) {
                    Logs.info(TAG + "_SetAQIError", "" + ex.getMessage());
                }
           // }
            holder.whole_on_off.setOnCheckedChangeListener(null);
            holder.whole_on_off.setChecked(whole_home_light[position] == 1);
            SwitchTrackChange.changeTrackColor(mcontext, holder.whole_on_off);
            check_whole_light_state_flag = holder.whole_on_off.isChecked();
            if (check_whole_light_state_flag && check_whole_light_status_flag) {
                holder.whole_img_option.setColorFilter(mcontext.getResources().getColor(R.color.familyname_select_color), PorterDuff.Mode.SRC_IN);
                holder.whole_img_option.setEnabled(true);
            } else {
                holder.whole_img_option.setColorFilter(mcontext.getResources().getColor(R.color.fragment_blue_light), PorterDuff.Mode.SRC_IN);
                holder.whole_img_option.setEnabled(false);
            }
            holder.whole_img_option.setEnabled(true);
            if(whole_home_light[position]!=-1&&bool_state!=whole_home_light[position]) {
                holder.circular_whole_home_on_off.setVisibility(View.GONE);
                holder.whole_on_off.setVisibility(View.VISIBLE);
                bool_state=-1;
            }
            holder.whole_on_off.setTag(position);
            holder.whole_on_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    bool_state=!isChecked?1:0;
                    int pos=(int)buttonView.getTag();
                    whole_home_light[pos]=-1;
                    Logs.info(TAG + "_WhereIm", "InWholeLightONOff");
                    Switch swt_on_off=(Switch)buttonView;
                    View view=(View) buttonView.getParent();
                    ViewHolder vh = new ViewHolder(view);
                    vh.circular_whole_home_on_off.setVisibility(View.VISIBLE);
                    swt_on_off.setVisibility(View.GONE);
                    setWholeHomeState(swt_on_off.isChecked());
                }
            });
            holder.img_whole_light_add_fav.setTag(position);
            holder.img_whole_blind_add_fav.setTag(position);
            holder.img_whole_music_add_fav.setTag(position);
            holder.img_whole_light_add_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    FavoritesOperations.deleteFav(arrayList.get(pos).getFav_id());
                }
            });
            holder.img_whole_blind_add_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    FavoritesOperations.deleteFav(arrayList.get(pos).getFav_id());
                }
            });
            holder.img_whole_music_add_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    FavoritesOperations.deleteFav(arrayList.get(pos).getFav_id());
                }
            });
            holder.whole_on_off.setTag(position);
            holder.btn_whole_open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                /*if(open_flag[(int) holder.btn_open.getTag()]==0) {*/
                        setWholeHomeBlindState(true);
                    // }
                }
            });
            holder.btn_whole_stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_stopblind", new JSONObject()));
                    }
                    catch (Exception ex)
                    {
                        Logs.error(TAG,"----------"+ex.getMessage());
                    }
                }
            });
            holder.btn_whole_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                       setWholeHomeBlindState(false);
                }
            });
            holder.whole_img_option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WholeHomeSceneDialog dFragment = new WholeHomeSceneDialog();
                    dFragment.show(mfm,"individual");
                }
            });
          holder.img_whole_music_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_next_song", new JSONObject()));
                    }
                    catch (Exception ex)
                    {
                        Logs.error(TAG,"----------"+ex.getMessage());
                    }
                }
            });
          holder.img_whole_music_play_pause.setTag(position);
          holder.img_whole_music_play_pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    try {
                        App.getSocket().emit("action", JsonObjectCreater.getJsonObject(playState[pos]==1?"home_pause_song":"home_play_song", new JSONObject()));
                        if(playState[pos]==1)playState[pos]=0;
                        else playState[pos]=1;
                    }
                    catch (Exception ex)
                    {
                        Logs.error(TAG,"----------"+ex.getMessage());
                    }
                }
            });
          holder.img_whole_music_prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_previous_song", new JSONObject()));
                    }
                    catch (Exception ex)
                    {
                        Logs.error(TAG,"----------"+ex.getMessage());
                    }
                }
            });
           holder.img_whole_playlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Device device=new Device();
                    device.setDevice_id("whole_home");
                    App.setDevice(device);
                    Bundle bundle=new Bundle();
                    bundle.putString("call_from","favorites");
                    App.setTemp_bundle(bundle);
                    if (App.isOrientationFlag())
                        ReplaceFragment.replaceFragment(mfrag, R.id.frm_elemenmain_container, RoomDevicesMusicFragment.newInstance());
                    else replacePortailFrag();
                }
            });
        }
        }
    public void reload(ArrayList<Favorites> myDataset,String response_string) {
        this.arrayList = myDataset;
        this.response_string=response_string;
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    /**
     * Change light state make ON/OFF
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param id string
     * @param state boolean
     *
     */
    public void setLightState(String id, boolean state) {
        try {
            Logs.info(TAG + "_LightPosition", "" + getLighJson(id) + "----" + id);
            JSONObject dataJson = new JSONObject();
            dataJson.put("state", state);
            dataJson.put("light", getLighJson(id));
            JSONObject obj = new JSONObject();
            obj.put("data", dataJson);
            obj.put("type", "light_power");
            Logs.info(TAG + "_light_state_request", "" + obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * make room state ON/OFF
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *@param state String
     * @param room_id boolean
     */
    public void changeRoomPower(String room_id, boolean state) {
        try {
            JSONObject dataJson = new JSONObject();
            dataJson.put("state", state);
            dataJson.put("room", room_id);
            JSONObject obj = new JSONObject();
            obj.put("data", dataJson);
            obj.put("type", "room_power");
            Logs.info(TAG + "_light_state_request", "" + obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * turn light group ON/OFF
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *@param state
     * @param id
     */
    public void setGroupState(String id, boolean state) {
        try {
            JSONObject dataJson = new JSONObject();
            dataJson.put("state", state);
            //  dataJson.put("group",id);
            dataJson.put("group", getGroupJson(id));
            JSONObject obj = new JSONObject();
            obj.put("data", dataJson);
            obj.put("type", "group_power");
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * turn whole home Light ON/OFF
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param state boolean
     *
     */
    public void setWholeHomeState(boolean state) {
        try {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_power", new JSONObject().put("type","Lighting").put("state",state)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * get group json from cml_id
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     *
     */
    public JSONObject getGroupJson(String id) {
        JSONObject grjsonObject = null;
        try {
            Iterator<String> keys = App.getDataStorageJson().getJSONObject(GROUPS).keys();
            while (keys.hasNext()) {
                JSONObject jsonObject=App.getDataStorageJson().getJSONObject(GROUPS).getJSONObject(keys.next());
                if (jsonObject.getString("CML_ID").equals(id)) {
                    grjsonObject = jsonObject;
                }
            }
        } catch (Exception ex) {
            Logs.error(TAG + "_actualLighExcception", "" + ex.getMessage());
        }
        return grjsonObject;
    }
    /**
     * change room brightness
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param room_id string
     * @param value int
     *
     */
    public void changeRoomBrightness(int value, String room_id) {
        try {
            JSONObject dataJson = new JSONObject();
            dataJson.put("brightness", value);
            dataJson.put("room", room_id);
            JSONObject obj = new JSONObject();
            obj.put("data", dataJson);
            obj.put("type", "room_brightness");
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void setLightBrightness(String id, int bright) {
        try {
            JSONObject dataJson = new JSONObject();
            dataJson.put("brightness", bright);
            dataJson.put("light", getLighJson(id));
            JSONObject obj = new JSONObject();
            obj.put("data", dataJson);
            obj.put("type", "light_brightness");
            Logs.info(TAG + "_light_brighness_request", "" + obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void setGroupBrightness(String id, int bright) {
        try {
            JSONObject dataJson = new JSONObject();
            dataJson.put("brightness", bright);
            dataJson.put("group", id);
            JSONObject obj = new JSONObject();
            obj.put("data", dataJson);
            obj.put("type", "group_brightness");
            Logs.info(TAG + "_group_brighness_request", "" + obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public JSONObject getLighJson(String id) {
        JSONObject lightjsonObject = null;
        try {
            Iterator<String> keys = App.getDataStorageJson().getJSONObject(PROVISIONAL_DEVICES).keys();
            while (keys.hasNext()) {
                JSONObject jsonObject=App.getDataStorageJson().getJSONObject(PROVISIONAL_DEVICES).getJSONObject(keys.next());
                if (jsonObject.has("CML_ID")) {
                    if (jsonObject.getString("CML_ID").equals(id)) {
                        lightjsonObject = jsonObject;
                    }
                }
            }
        } catch (Exception ex) {
            Logs.error(TAG + "_actualLighExcception", "" + ex.getMessage());
        }
        return lightjsonObject;
    }
    public void setButtonSelection(Button btn_selection, Button btn_open, Button btn_close) {
        btn_open.setBackground(mcontext.getResources().getDrawable(R.drawable.btn_border_white));
        btn_close.setBackground(mcontext.getResources().getDrawable(R.drawable.btn_border_white));
        btn_selection.setBackground(mcontext.getResources().getDrawable(R.drawable.btn_fill_color));
    }
    public void setBlindPoint(int value, int pos, String blind_type) {
        try {
            if (blind_type.equals("ind")) {
                JSONObject obj = new JSONObject();
                obj.put("data", new JSONObject().put("CML_SET_POINT", value).put("Id", getBlindsId(arrayList.get(pos).getDevice_id())).put("CML_ID", arrayList.get(pos).getDevice_id()));
                obj.put("type", "set_blind_setpoint");
                Log.d("SimulationRequestSend", "" + obj);
                App.getSocket().emit("action", obj);
            } else {
                JSONObject obj = new JSONObject();
                obj.put("data", new JSONObject().put("setpoint", value).put("group", arrayList.get(pos).getDevice_id()));
                obj.put("type", "group_setpoint");
                Log.d("SimulationRequestSend", "" + obj);
                App.getSocket().emit("action", obj);
            }
            //  App.getSocket().emit("trigger_dawn_simulation", JsonObjectCreater.getJsonObject("trigger_dawn_simulation", obj));
        } catch (Exception ex) {
            Logs.error(TAG + "_SimulationRequestError", "" + ex.getMessage());
        }
    }
    public void stopBlinds(int pos, String type) {
        try {
            JSONObject obj = new JSONObject();
            if (type.equals("group"))
                obj.put("data", new JSONObject().put("group", getBlindGroupId(arrayList.get(pos).getDevice_id())));
            else
                obj.put("data", new JSONObject().put("Id", getBlindId(arrayList.get(pos).getDevice_id())));
            if (type.equals("group")) obj.put("type", "group_stop");
            else obj.put("type", "stop_blinds");
            Log.d("SimulationRequestSend", "" + obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            Logs.error(TAG + "_BlindStopException", ex.getMessage());
        }
    }
    public void setBlindState(boolean value, int pos, String blind_type) {
        try {
            if (!blind_type.equals("group")) {
                JSONObject obj = new JSONObject();
                obj.put("data", new JSONObject().put("CML_POWER", value).put("Id", getBlindId(arrayList.get(pos).getDevice_id())));
                obj.put("type", "blind_power");
                Log.d("SimulationRequestSend", "" + obj);
                App.getSocket().emit("action", obj);
            } else {
                JSONObject obj = new JSONObject();
                obj.put("data", new JSONObject().put("state", value).put("group", getBlindGroupJson(arrayList.get(pos).getDevice_id())));
                obj.put("type", "group_power");
                Log.d("SimulationRequestSend", "" + obj);
                App.getSocket().emit("action", obj);
            }
            //  App.getSocket().emit("trigger_dawn_simulation", JsonObjectCreater.getJsonObject("trigger_dawn_simulation", obj));
        } catch (Exception ex) {
            Logs.error(TAG + "_SimulationRequestError", "" + ex.getMessage());
        }
    }
    public void setWholeHomeBlindState(boolean value) {
        try {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_power", new JSONObject().put("type","Blinds").put("state",value)));
        }
        catch (Exception ex)
        {
            Logs.error(TAG,"----------"+ex.getMessage());
        }
    }

    public void setMusicNext(String device_id) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("data", new JSONObject().put("Id", device_id));
            obj.put("type", "next_song");
            Log.d("SimulationRequestSend", "" + obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            Logs.info(TAG + "_PlayJson", "" + ex.getMessage());
        }
    }
    public void setPrvious(String device_id) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("data", new JSONObject().put("Id", device_id));
            obj.put("type", "previous_song");
            Log.d("SimulationRequestSend", "" + obj);
            App.getSocket().emit("action", obj);
        } catch (Exception ex) {
            Logs.error(TAG + "_PlayJson", "" + ex.getMessage());
        }
    }
    public void playPauseMusic(int play, String device_id) {
        if (play == 0) {
            try {
                JSONObject obj = new JSONObject();
                obj.put("data", new JSONObject().put("Id", device_id));
                obj.put("type", "play_song");
                Log.d("SimulationRequestSend", "" + obj);
                App.getSocket().emit("action", obj);
            } catch (Exception ex) {
                Logs.error(TAG + "_PlayJson", "" + ex.getMessage());
            }
        } else {
            try {
                JSONObject obj = new JSONObject();
                obj.put("data", new JSONObject().put("Id", device_id));
                obj.put("type", "pause_song");
                Log.d("SimulationRequestSend", "" + obj);
                App.getSocket().emit("action", obj);
            } catch (Exception ex) {
                Logs.error(TAG + "_PlayJson", "" + ex.getMessage());
            }
        }
    }
    public String getMusicId(String device_id) {
        String id = "";
        try {
            Iterator<String> keys = App.getDataStorageJson().getJSONObject(PROVISIONAL_DEVICES).keys();
            while (keys.hasNext()) {
                JSONObject jsonObject=App.getDataStorageJson().getJSONObject(PROVISIONAL_DEVICES).getJSONObject(keys.next());
                if (jsonObject.has("CML_ID")) {
                    if (jsonObject.getString("CML_ID").equals(device_id)) {
                        Logs.info(TAG + "_MusicJson", "" + jsonObject);
                        id = jsonObject.getString("Id");
                    }
                }
            }
        } catch (Exception ex) {
            Logs.info(TAG + "_Exception", "" + ex.getMessage());
        }
        return id;
    }
    public String getBlindsId(String device_id) {
        String id = "";
        try {
            Iterator<String> keys = App.getDataStorageJson().getJSONObject(PROVISIONAL_DEVICES).keys();
            while (keys.hasNext()) {
                JSONObject jsonObject=App.getDataStorageJson().getJSONObject(PROVISIONAL_DEVICES).getJSONObject(keys.next());
                if (jsonObject.has("CML_ID")) {
                    if (jsonObject.getString("CML_ID").equals(device_id)) {
                        Logs.info(TAG + "_BlindJson", "" + jsonObject);
                        id = jsonObject.getString("Id");
                    }
                }
            }
        } catch (Exception ex) {
            Logs.error(TAG + "_Exception", "" + ex.getMessage());
        }
        return id;
    }
    public String getBlindId(String device_id) {
        String id = "";
        try {
            Iterator<String> keys = App.getDataStorageJson().getJSONObject(PROVISIONAL_DEVICES).keys();
            while (keys.hasNext()) {
                JSONObject jsonObject1=App.getDataStorageJson().getJSONObject(PROVISIONAL_DEVICES).getJSONObject(keys.next());
                if (jsonObject1.has("CML_ID")) {
                    if (jsonObject1.getString("CML_ID").equals(device_id)) {
                        Logs.info(TAG + "_BlindGroupJson", "" + jsonObject1);
                        id = jsonObject1.getString("Id");
                    }
                }
            }
        } catch (Exception ex) {
            Logs.error(TAG + "_Exception", "" + ex.getMessage());
        }
        return id;
    }
    public String getBlindGroupId(String device_id) {
        String id = "";
        try {
            Iterator<String> keys = App.getDataStorageJson().getJSONObject(PROVISIONAL_DEVICES).keys();
            while (keys.hasNext()) {
                JSONObject jsonObject1=App.getDataStorageJson().getJSONObject(PROVISIONAL_DEVICES).getJSONObject(keys.next());
                if (jsonObject1.has("CML_ID")) {
                    if (jsonObject1.getString("CML_ID").equals(device_id)) {
                        Logs.info(TAG + "_BlindGroupJson", "" + jsonObject1);
                        id = jsonObject1.getString("Id");
                    }
                }
            }
        } catch (Exception ex) {
            Logs.error(TAG + "_Exception", "" + ex.getMessage());
        }
        return id;
    }
    public String getLightSceneId(String device_id) {
        String id = "";
        try {
            Iterator<String> keys = App.getDataStorageJson().getJSONObject(PROVISIONAL_DEVICES).keys();
            while (keys.hasNext()) {
                JSONObject jsonObject1=App.getDataStorageJson().getJSONObject(PROVISIONAL_DEVICES).getJSONObject(keys.next());
                if (jsonObject1.has("CML_ID")) {
                    if (jsonObject1.getString("CML_ID").equals(device_id)) {
                        Logs.info(TAG + "_LightDeviceJson", "" + jsonObject1);
                        id = jsonObject1.getString("CML_SCENE_ID");
                    }
                }
            }
        } catch (Exception ex) {
            Logs.error(TAG + "_Exception", "" + ex.getMessage());
        }
        return id;
    }
    public String getGroupSceneId(String device_id) {
        String id = "";
        try {
            Iterator<String> keys = App.getDataStorageJson().getJSONObject(GROUPS).keys();
            while (keys.hasNext()) {
                JSONObject jsonObject1=App.getDataStorageJson().getJSONObject(GROUPS).getJSONObject(keys.next());
                if (jsonObject1.getString("CML_ID").equals(device_id)) {
                    Logs.info(TAG + "_LightDeviceJson", "" + jsonObject1);
                    id = jsonObject1.getString("CML_SCENE_ID");
                }
            }
        } catch (Exception ex) {
            Logs.error(TAG + "_Exception", "" + ex.getMessage());
        }
        return id;
    }
    public JSONObject getBlindGroupJson(String group_id) {
        JSONObject jsonObject = null;
        try {
            Iterator<String> keys = App.getDataStorageJson().getJSONObject(GROUPS).keys();
            while (keys.hasNext()) {
                JSONObject jsonObject1=App.getDataStorageJson().getJSONObject(GROUPS).getJSONObject(keys.next());
                if (jsonObject1.getString("CML_ID").equals(group_id)) {
                    Logs.info(TAG + "_BlindGroupJson", "" + jsonObject1);
                    jsonObject = jsonObject1;
                }
            }
        } catch (Exception ex) {
            Logs.error(TAG + "_Exception", "" + ex.getMessage());
        }
        return jsonObject;
    }
    public static String getRoomTitle(String room_id) {
        String room_title = "";
        try {
            if (App.getDataStorageJson().getJSONObject(ROOMS) != null) {
                    JSONObject jsonObject=App.getDataStorageJson().getJSONObject(ROOMS).getJSONObject(room_id);
                    if (jsonObject.getString("CML_ID").equals(room_id)) {
                        room_title = jsonObject.getString("CML_TITLE");
                    }
            }
        } catch (Exception ex) {
            Logs.error("FavoriteListAdapeter_Error", ex.getMessage());
        }
        return room_title;
    }
    public static String getRoomType(String room_id) {
        String room_title = "";
        try {
            if (App.getDataStorageJson().getJSONObject(ROOMS) != null) {
                Iterator<String> keys = App.getDataStorageJson().getJSONObject(ROOMS).keys();
                while (keys.hasNext()) {
                    JSONObject jsonObject=App.getDataStorageJson().getJSONObject(ROOMS).getJSONObject(keys.next());
                    if (jsonObject.getString("CML_ID").equals(room_id)) {
                        room_title = jsonObject.getString("CML_ROOM_TYPE");
                    }
                }
            }
        } catch (Exception ex) {
            Logs.info("FavoritelIstAdapeter" + "_Error", ex.getMessage());
        }
        return room_title;
    }
    public boolean checkDawnSleep(int pos) {
        boolean flag = false;
        if (arrayList.get(pos).isDawn_running()) {
            showDialog(mcontext.getResources().getString(R.string.dawn_is_running),pos,"dawn");
            flag = true;
        }
        if (arrayList.get(pos).isSleep_running()) {
            showDialog(arrayList.get(pos).getSleep_id().contains("prepare")?mcontext.getResources().getString(R.string.prepare_for_sleep_is_running):mcontext.getResources().getString(R.string.sleep_is_running),pos,"sleep");
            flag = true;
        }
        return flag;
    }
    public void replacePortailFrag() {
        FragmentTransaction fragmentTransaction = mfrag.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frm_main_container, RoomDevicesMusicFragment.newInstance());
        fragmentTransaction.addToBackStack("RoomDevicesMusicFragment");
        fragmentTransaction.commit();
    }
    public void disableAllViews(View v, boolean f) {
        v.setEnabled(f);
        if (v instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
                View view = ((ViewGroup) v).getChildAt(i);
                disableAllViews(view, f);
            }
        }
    }
    public  void showDialog(String msg,final int i,final String type)
    {
        if(dawn_sleep_dialog!=null)
        {
            if(dawn_sleep_dialog.isShowing())
            {
                dawn_sleep_dialog.dismiss();
            }
        }
        dawn_sleep_dialog=new Dialog(mcontext);
        if(dawn_sleep_dialog.isShowing())dawn_sleep_dialog.dismiss();
        dawn_sleep_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dawn_sleep_dialog.setContentView(R.layout.general_dialog);
        dawn_sleep_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dawn_sleep_dialog.setCanceledOnTouchOutside(false);
        TextView txtmsgstr= dawn_sleep_dialog.findViewById(R.id.txt_msg);
        txtmsgstr.setTypeface(setFontForDialog(mcontext));
        txtmsgstr.setText(""+msg);
        Button btnretry= dawn_sleep_dialog.findViewById(R.id.btn_retry);
        btnretry.setTypeface(setFontForDialog(mcontext));
        btnretry.setText("Cancel");
        Button btncancle= dawn_sleep_dialog.findViewById(R.id.btncancle);
        btncancle.setVisibility(View.VISIBLE);
        btncancle.setText("OK");
        btncancle.setTypeface(setFontForDialog(mcontext));
        btnretry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dawn_sleep_dialog!=null) dawn_sleep_dialog.dismiss();
                dawn_sleep_dialog=null;
            }
        });
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject dataJsonObject=getSimulatuonObject(type,i);
                    //  jsonObject.put("status",true);
                    App.getSocket().emit("action", type.equals("dawn")?JsonObjectCreater.getJsonObject("stop_dawn_simulation", dataJsonObject):JsonObjectCreater.getJsonObject("stop_sleep_simulation", dataJsonObject));
                    RemoveNotification.removeSpecificDawnNotification(mcontext,dataJsonObject.getString("CML_ID"));
                }
                catch (Exception ex){}
                dawn_sleep_dialog.dismiss();
            }
        });
        dawn_sleep_dialog.show();
    }
    private JSONObject getSimulatuonObject(String type,int pos)
    {
        JSONObject simulation_json=null;
        try {
            JSONArray jsonArray = App.getAllSimulationData().getJSONArray("data");
            Logs.info(TAG+"_getAllSimulationDataaaa",""+jsonArray);
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(type.equals("dawn")) {
                    if(jsonObject.has("CML_ID")) {
                        if (arrayList.get(pos).getDawn_id().equals(jsonObject.getString("CML_ID"))) {
                            simulation_json = jsonObject;
                        }
                    }
                }
                if (type.equals("sleep")) {
                    String sleep_id=arrayList.get(pos).getSleep_id();
                    Logs.info(TAG+"_Sleep_id",""+sleep_id);
                    if(sleep_id.contains("prepare"))
                    {
                        JSONObject datajson=new JSONObject();
                        datajson.put("Id",sleep_id);
                        datajson.put("room",arrayList.get(pos).getRoom_id());
                        simulation_json=datajson;
                    }
                    else
                    {
                        if(jsonObject.has("CML_ID")) {
                            if (sleep_id.equals(jsonObject.getString("CML_ID"))) {
                                simulation_json = jsonObject;
                            }
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
    private static Typeface setFontForDialog(Context mcontext)
    {
        typeface = Typeface.createFromAsset(mcontext.getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        return typeface;
    }


    public static boolean checkGroup(JSONObject jsonObject)
    {
        boolean b = false;
        try {

            if (jsonObject.has("group")) {
                if (jsonObject.getString("group").equals("")) {
                    b = true;
                }
            }
            else
            {
                b=true;
            }
        }
        catch (Exception ex)
        {
            Log.e("FavoriteListAdapter",ex.getMessage());
        }
        return b;
    }
}