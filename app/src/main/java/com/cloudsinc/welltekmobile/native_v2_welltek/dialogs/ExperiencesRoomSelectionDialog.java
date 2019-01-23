package com.cloudsinc.welltekmobile.native_v2_welltek.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.FavriotesAllRoomListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Room;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
/**
 * Created by developers on 26/12/17.
 */
public class ExperiencesRoomSelectionDialog extends DialogFragment {
    public static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.all_experiences.Favorites_Rooms_List newInstance() {
        return new com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.all_experiences.Favorites_Rooms_List();
    }
    String str_action="";
    private View v;
    Context mcontext;
    Observable<String> mobservable;
    Observer<String> myObserver;
    ArrayList<String> selected_room_list=new ArrayList<>();
    ArrayList<String> deselected_room_list=new ArrayList<>();
    ArrayList<Room> arr_room_list=new ArrayList<>();
    String scene_img="";
    @BindView(R.id.list_add_rooms)ListView listview;
    @BindView(R.id.no_text) TextView notext;
    @BindView(R.id.desc_text) TextView desc_text;
    @Nullable @BindView(R.id.no_text_avilable) TextView no_text_avilable;
    @BindView(R.id.txt_scene_title) TextView txt_scene_title;
    @BindView(R.id.cir_prog) ProgressBar cir_prog;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2) ImageView img2;
    @BindView(R.id.img3) ImageView img3;
    @BindView(R.id.img4) ImageView img4;
    @BindView(R.id.img5) ImageView img5;
    @BindView(R.id.img6) ImageView img6;
    @BindView(R.id.img7) ImageView img7;
    ArrayList<String> arr_room_list_for_deslect=new ArrayList<>();
    @BindView(R.id.btn_add) Button btn_add;
    JSONArray roomTypeJsonArray;

    LinearLayoutManager mLayoutManager;

    Observable<String> activeobservable;
    Observer<String> activeObserver;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_fav_rooms_list, container,
                false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mcontext = v.getContext();
        ButterKnife.bind(this, v);
        txt_scene_title.setText("Activities: "+""+getArguments().getString("scene_title"));
        scene_img=getArguments().getString("img_scenes");
        if(scene_img=="darwin_two"){
            desc_text.setText("An ambiance designed to make you feel Comfort mood");
            defaultvisibility();
            img7.setVisibility(View.VISIBLE);
            img6.setVisibility(View.VISIBLE);
        }
        else if(scene_img=="demo_darwin_two"){
            desc_text.setText("An ambiance designed to make you feel Comfort mood");
            defaultvisibility();
            img7.setVisibility(View.VISIBLE);
            img6.setVisibility(View.VISIBLE);
        }
        else if(scene_img=="eng_two") {
            desc_text.setText("An ambiance designed to make you feel energised.");
            defaultvisibility();
            img1.setVisibility(View.VISIBLE);
            img6.setVisibility(View.VISIBLE);
        }
        else if(scene_img=="cook_two") {
            desc_text.setText("An ambiance designed to create a cooking Environment");
            defaultvisibility();
            img1.setVisibility(View.VISIBLE);
            img5.setVisibility(View.VISIBLE);
        }
        else if(scene_img=="relax_two") {
            desc_text.setText("An ambiance designed to make you feel Relaxed");
            defaultvisibility();
            img1.setVisibility(View.VISIBLE);
            img6.setVisibility(View.VISIBLE);
        }
        else if(scene_img=="sleep_four") {
            desc_text.setText("An ambiance designed to take you in sweet dreams.");
            defaultvisibility();
            img1.setVisibility(View.VISIBLE);
            img4.setVisibility(View.VISIBLE);
            img3.setVisibility(View.VISIBLE);
            img6.setVisibility(View.VISIBLE);
        }
       else if(scene_img=="demo_dawn_four") {
            desc_text.setText("An ambiance designed to make you feel Comfort mood.");
            defaultvisibility();
            img1.setVisibility(View.VISIBLE);
            img4.setVisibility(View.VISIBLE);
            img3.setVisibility(View.VISIBLE);
            img6.setVisibility(View.VISIBLE);
        }
       else if(scene_img=="sleep_demo_four") {
            desc_text.setText("An ambiance designed to take you in sweet dreams.");
            defaultvisibility();
            img1.setVisibility(View.VISIBLE);
            img4.setVisibility(View.VISIBLE);
            img3.setVisibility(View.VISIBLE);
            img6.setVisibility(View.VISIBLE);
        }


        try {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_rooms_by_device", new JSONObject().put("type","Lighting")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setSubcriber();
        setActiveSubcriber();

        return v;
    }

    private void setActiveSubcriber() {


        activeobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("");
                        sub.onCompleted();
                    }
                }
        );
        activeObserver = new Observer<String>() {

            @Override
            public void onCompleted() {
                setAddVisibleHide();
            }

            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onNext(String string) {

            }
        };

        Subcriber s = new Subcriber();
        s.setObservable(activeobservable);
        s.setObserver(activeObserver);
        App.setActive_button_subcriber(s);
    }
    public JSONArray getRoomBySceneId()
    {

        JSONArray jsonArray = new JSONArray();
        return jsonArray;
    }
    private void defaultvisibility() {
        img7.setVisibility(View.GONE);
        img6.setVisibility(View.GONE);
        img5.setVisibility(View.GONE);
        img4.setVisibility(View.GONE);
        img3.setVisibility(View.GONE);
        img2.setVisibility(View.GONE);
        img1.setVisibility(View.GONE);
    }
    private void setSubcriber() {
        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("");
                        sub.onCompleted();
                    }
                }
        );
        myObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                setAddVisibleHide();
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(String string) {
                Log.e("get_rooms_by_device","rrrrrrrrrrrrrrrrrr"+string);
                try {
                    if (string.equals("rooms_by_device")) {
                        if (getRoomItem().size() > 0) {
                            // rel_no_data.setVisibility(View.GONE);
                            listview.setVisibility(View.VISIBLE);
                            notext.setVisibility(View.GONE);
                            cir_prog.setVisibility(View.GONE);
                            listview.setVisibility(View.VISIBLE);
                            btn_add.setVisibility(View.VISIBLE);
                            arr_room_list = getRoomItem();
                            FavriotesAllRoomListAdapter customRoomItemAdapter = new FavriotesAllRoomListAdapter(mcontext, arr_room_list, getArguments().getString("scene_id"));
                            listview.setAdapter(customRoomItemAdapter);
                            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    setAddVisibleHide();
                                }
                            });
                        } else {
                            listview.setVisibility(View.GONE);
                            cir_prog.setVisibility(View.GONE);
                            btn_add.setVisibility(View.GONE);
                            listview.setVisibility(View.GONE);
                        /*rel_no_data.setVisibility(View.VISIBLE);
                        frm_home.setVisibility(View.GONE);*/
                            notext.setVisibility(View.VISIBLE);
                            notext.setText("No Rooms Available");
                            //circular_progress_bar.setVisibility(View.GONE);
                        }
                    }
                }
                catch(Exception ex){
                        Log.e("Error", "" + ex.getMessage());
                    }

            }
        };
        Log.e("IMINSub","hhhhhhhhh");
        Subcriber s = new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setMusic_playlist_subcriber(s);
        //  }
    }
    private ArrayList<Room> getRoomItem() {
        String jsonarr = null;
        try {
            InputStream is = mcontext.getAssets().open("scene_wise_room.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonarr = new String(buffer, "UTF-8");
            Log.e("Scenesjson",jsonarr);
            JSONArray jsonArray=new JSONArray(jsonarr);
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("scene_id").equals(getArguments().getString("scene_id")))
                {
                    roomTypeJsonArray =jsonObject.getJSONArray("room_type");
                }
            }
            JSONObject jsonObject = App.getRoomDeviceData();
            JSONArray roomjsonArray = jsonObject.getJSONArray("data");
            if (roomjsonArray.length()==0) {
                cir_prog.setVisibility(View.GONE);
                notext.setVisibility(View.VISIBLE);
                notext.setText( "No Rooms are available");
            }
            else {
                btn_add.setVisibility(View.VISIBLE);
                Log.e("getRoomDeviceData", "" + jsonObject);
                arr_room_list.clear();
                arr_room_list_for_deslect.clear();
                for (int i = 0; i < roomjsonArray.length(); i++) {
                    JSONObject jsonObject1 = roomjsonArray.getJSONObject(i);
                    if (checkRoomType(jsonObject1.getString("CML_ROOM_TYPE"))) {
                        String roomname = "";
                        Room room = new Room();
                        roomname = jsonObject1.getString("CML_TITLE");
                        room.setRoom_id(jsonObject1.getString("CML_ID"));
                        room.setRoom_title("" + roomname);
                        arr_room_list.add(room);
                        arr_room_list_for_deslect.add(jsonObject1.getString("CML_ID"));
                    } else {
                        // no_text_avilable.setVisibility(View.VISIBLE);
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("jsonerror", "------------------------" + ex.getMessage());
        }



        return arr_room_list;
    }
    @OnClick(R.id.img_close)
    public void img_close()
    {
        this.dismiss();
    }
    public void setAddVisibleHide()
    {
        Log.e("WHEREIM","InAddVISIBLEHide");
        if(App.getRoom_ids().size()>0)
        {
            btn_add.setVisibility(View.VISIBLE);
            btn_add.setAlpha(1f);
            btn_add.setEnabled(true);
        }
        else
        {
            cir_prog.setVisibility(View.GONE);
            btn_add.setVisibility(View.VISIBLE);
            btn_add.setAlpha(0.5f);
            btn_add.setEnabled(false);
        }
    }
    @OnClick(R.id.btn_add)
    public void btn_add()
    {
        try {
            JSONObject jsonObject = new JSONObject();
            if(!getSelectedRoomArray().toString().equals("[]")) jsonObject.put("selectedArr", getSelectedRoomArray());
            else jsonObject.put("selectedArr", getRoomBySceneId());
            jsonObject.put("scene", ""+getArguments().getString("scene_id"));
            jsonObject.put("selectedArr", getSelectedRoomArray());

            if(getArguments().getString("scene_id")=="A" || getArguments().getString("scene_id")=="B" || getArguments().getString("scene_id")=="C")
            {
                jsonObject.put("deselectedArr",new JSONArray());}
            else {
                jsonObject.put("deselectedArr", getDeselectedRoomArray());
            }
            JSONObject resultJsonObject = new JSONObject();
            resultJsonObject.put("data", jsonObject);
            resultJsonObject.put("type", "activate_scene");
            System.out.println("activate_sceneeeeeeeeeeeeeeeeeeeee" + resultJsonObject);
            App.getSocket().emit("action", resultJsonObject);
        } catch (Exception ex) {
            Log.e("Error", "" + ex.getMessage());
        }
        App.setCurrentSubcriber(App.getCurrentSubcriber());
        this.dismiss();
    }

    public JSONArray getSelectedRoomArray()
    {
        JSONArray jsonArray = new JSONArray();
        try {
            ArrayList<String> room_ids = App.getRoom_ids();
            for (int i = 0; i < room_ids.size(); i++) {
                jsonArray.put(""+ room_ids.get(i));
                selected_room_list.add(""+ room_ids.get(i));
            }
        }
        catch (Exception ex)
        {
            Log.e("RoomIdError",""+ex.getMessage());
        }
        return jsonArray;
    }
    private JSONArray getDeselectedRoomArray() {
        JSONArray jsonArray = new JSONArray();
        try {
            ArrayList<String> room_ids = App.getRoom_ids();
            Log.e("Arrrays....",arr_room_list_for_deslect+"------");
            for (int i = 0; i < room_ids.size(); i++) {
                arr_room_list_for_deslect.remove(arr_room_list_for_deslect.indexOf(room_ids.get(i)));
            }
            for (int i = 0; i < arr_room_list_for_deslect.size(); i++) {
                jsonArray.put(""+ arr_room_list_for_deslect.get(i));
            }
        }
        catch (Exception ex)
        {
            Log.e("RoomIdError",""+ex.getMessage());
        }
        return jsonArray;
    }
    public boolean checkRoomType(String room_type)
    {
        boolean checkFlag=false;
        try {
            for (int i = 0; i < roomTypeJsonArray.length(); i++) {
                if (room_type.equals(roomTypeJsonArray.getString(i))) {
                    checkFlag=true;
                }
            }
        }
        catch (Exception ex)
        {
            Log.e("RoomCheckError",""+ex.getMessage());
        }
        return checkFlag;
    }
}
