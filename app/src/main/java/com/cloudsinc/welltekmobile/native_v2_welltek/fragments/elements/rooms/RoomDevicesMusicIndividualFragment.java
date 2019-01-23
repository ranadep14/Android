package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.IndividualMusicListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Music;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
/**
 * This class containing functionality related to displaying songs list under particular playlist
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class RoomDevicesMusicIndividualFragment extends Fragment  {
    public static RoomDevicesMusicIndividualFragment newInstance() {
        return new RoomDevicesMusicIndividualFragment();
    }
    @BindView(R.id.rec_indivi_music)RecyclerView rec_indivi_music;
    @BindView(R.id.lin_ind_music)LinearLayout lin_ind_music;
    @BindView(R.id.txt_no_devices)TextView txt_no_devices;
    @BindView(R.id.txt_playlist)TextView txt_playlist;
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @BindView(R.id.rel_loading)RelativeLayout rel_loading;
    @Nullable
    @BindView(R.id.img_back)ImageView img_back;
    LinearLayoutManager mLayoutManager;
    ArrayList<Music> arrayList=new ArrayList<>();
    View view;
    Context mcontext;
    String device_id="";
    String playlist_id="";
    String playlist_name="";
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    @BindView(R.id.lin_dawn_running)LinearLayout lin_dawn_running;
    @BindView(R.id.lin_sleep_running)LinearLayout lin_sleep_running;
    static boolean flag_disable_all_views_for_dawn =false;
    static boolean flag_disable_all_views_for_sleep =false;
    String TAG=RoomDevicesMusicIndividualFragment.this.getClass().getSimpleName();
    boolean music_list_flag=false;
    String room_id="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
        view =inflater.inflate(R.layout.fragment_room_devices_individual_music, container, false);
        mcontext=view.getContext();
        ButterKnife.bind(this,view);
        txt_fragment_title.setText(""+getArguments().getString("playlist_title"));
       if(App.getDevice()!=null) device_id=App.getDevice().getDevice_id();
        playlist_id=getArguments().getString("playlist_id");
        playlist_name=getArguments().getString("playlist_title");
        if(!App.isOrientationFlag())img_back.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_left_arrow));
         Logs.info(TAG+"_Device_id",device_id+"----"+getRoomId());
        room_id=getRoomId();
        txt_playlist.setText(""+playlist_name);
       device_id=App.getDevice().getDevice_id();
        if(!device_id.equals("whole_home")) {
            if (App.getSocket() != null) {
                try {
                        JSONObject dataJsonObject = new JSONObject();
                        dataJsonObject.put("Id", device_id);
                        dataJsonObject.put("playLstId", playlist_id);
                        JSONObject obj = new JSONObject();
                        obj.put("data", dataJsonObject);
                        obj.put("type", "get_songs_by_playlist");
                        Logs.info(TAG + "_SimulationRequest", "" + obj);
                        App.getSocket().emit("action", obj);
                   /* }
                    else
                    {
                        App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_default_dawn_sounds", new JSONObject()));
                    }*/
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            checkDawnState();
            checkSleepState();
            disableAllViews();
        }
        else
        {
            if (App.getSocket() != null) {
                try {
                   // if(!playlist_id.equals("default_music")) {
                        JSONObject dataJsonObject = new JSONObject();
                        dataJsonObject.put("playLstId", playlist_id);
                        JSONObject obj = new JSONObject();
                        obj.put("data", dataJsonObject);
                        obj.put("type", "home_get_songs_by_playlist");
                        Logs.info(TAG + "_SimulationRequest", "" + obj);
                        App.getSocket().emit("action", obj);
                    /*}
                    else
                    {
                        App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_default_dawn_sounds", new JSONObject()));
                    }*/
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
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
                 Logs.info(TAG+"_ResonseString",""+string);
                 if(!device_id.equals("whole_home")) {
                     checkDawnState();
                     checkSleepState();
                 }
                     if (string.equals("queue")||string.equals("default_dawn_sounds")) {
                         getMusic();
                         if (arrayList.size() > 0) {
                             music_list_flag = true;
                             IndividualMusicListAdapter individualMusicListAdapter = new IndividualMusicListAdapter(mcontext, arrayList, device_id, playlist_id);
                             rec_indivi_music.setAdapter(individualMusicListAdapter);
                             rec_indivi_music.invalidate();
                             rec_indivi_music.setHasFixedSize(true);
                             mLayoutManager = new LinearLayoutManager(mcontext);
                             rec_indivi_music.setLayoutManager(mLayoutManager);
                         }
                         if (!music_list_flag) {
                             rel_loading.setVisibility(View.GONE);
                             txt_no_devices.setVisibility(View.VISIBLE);
                             lin_ind_music.setVisibility(View.GONE);
                         } else {
                             rel_loading.setVisibility(View.GONE);
                             txt_no_devices.setVisibility(View.GONE);
                             lin_ind_music.setVisibility(View.VISIBLE);
                         }
                     }
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);
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
        catch (Exception ex){ Logs.info("RoomDevicesMusicIndividual_Error",ex.getMessage());}
        return room_title;
    }
    @OnClick(R.id.img_back)
    public void img_back()
    {
        App.setCallfrom("InvidualMusic");
        if(App.isOrientationFlag())ReplaceFragment.replaceFragment(this,R.id.frm_elemenmain_container,RoomDevicesMusicFragment.newInstance());
        else  ReplaceFragment.replaceFragment(this,R.id.frm_main_container,RoomDevicesMusicFragment.newInstance());
    }
    public void getMusic()
    {
        arrayList.clear();
        try {
            JSONArray jsonArray =null;
                  if (!device_id.equals("whole_home"))
                      jsonArray = App.getMusicQueJson().getJSONArray("data");
                  else jsonArray = App.getHomeMuusicQueJson().getJSONArray("data");
                  Logs.info(TAG + "_PlayListArray", "" + jsonArray);
                  for (int i = 0; i < jsonArray.length(); i++) {
                      JSONObject jsonObject = jsonArray.getJSONObject(i);
                      Music s = new Music();
                      s.setMusic_title(jsonObject.getString("CML_TITLE"));
                      s.setMusic_id(jsonObject.getString("uri"));
                      if (jsonObject.has("Album_Art"))
                          s.setMusic_album_art(jsonObject.getString("Album_Art"));
                      if (jsonObject.has("artist"))
                          s.setMusic_artist(jsonObject.getString("artist"));
                      if (jsonObject.has("album")) s.setMusic_album(jsonObject.getString("album"));
                      arrayList.add(s);
                  }
              /*}
              else
              {
                  jsonArray = App.getSoundData().getJSONArray("data");
                  Logs.info(TAG + "_soundArray", "" + jsonArray);
                  for (int i = 0; i < jsonArray.length(); i++) {
                      JSONObject jsonObject = jsonArray.getJSONObject(i);
                      Music s = new Music();
                      s.setMusic_title(jsonObject.getString("CML_TITLE"));
                      s.setMusic_id(jsonObject.getString("CML_ID"));
                      s.setMusic_album_art("");
                      s.setMusic_artist("");
                      s.setMusic_album("");
                      arrayList.add(s);
                  }
              }*/
        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_PlayListError",ex.getMessage());
        }
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
    public String getRoomId()
    {
        String room_id="";
        JSONObject lightjsonObject = null;
        JSONArray jsonArray=null;
        try {
            if(App.isOrientationFlag()) jsonArray = App.getDevicesByRoomData().getJSONArray("data");
            else  jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("CML_ID").equals(device_id)) {
                    room_id = jsonObject.getString("room");
                }
            }
        } catch (Exception ex) {
             Logs.error(TAG+"_actualLighExcception",""+ ex.getMessage());
        }
        return room_id;
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
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("halt_dawn_simulation", dataJsonObject.put("status", false)));
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
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("halt_sleep_simulation", dataJsonObject.put("status", false)));
        }
        catch (Exception ex){
            Logs.error(TAG+"_Sleep_halt_error",""+ex.getMessage());
        }
    }
    public void disableAllViews(){
        disableRecyclerview(rec_indivi_music);
    }
    public void checkDawnState()
    {
        if(getDawnState(room_id))
        {
            lin_dawn_running.setVisibility(View.VISIBLE);
            flag_disable_all_views_for_dawn =true;
        }
        else
        {
            lin_dawn_running.setVisibility(View.GONE);
            flag_disable_all_views_for_dawn =false;
        }
    }
    public void checkSleepState()
    {
        if(getSleepState(room_id))
        {
            lin_sleep_running.setVisibility(View.VISIBLE);
            flag_disable_all_views_for_sleep =true;
        }
        else
        {
            lin_sleep_running.setVisibility(View.GONE);
            flag_disable_all_views_for_sleep =false;
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
    public void onDestroyView() {
        super.onDestroyView();
        System.gc();
        rec_indivi_music.removeAllViewsInLayout();
    }
}