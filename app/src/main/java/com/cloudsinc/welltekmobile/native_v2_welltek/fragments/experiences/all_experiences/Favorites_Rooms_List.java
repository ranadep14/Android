package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.all_experiences;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Room;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by developers on 26/12/17.
 */

public class Favorites_Rooms_List extends Fragment {
    public static Favorites_Rooms_List newInstance() {
        return new Favorites_Rooms_List();
    }
    String str_action="";
    private View v;
    Context mcontext;
    Observable<String> mobservable;
    Observer<String> myObserver;

    ArrayList<Room> arr_room_list=new ArrayList<>();
    ArrayList<Room> selected_room_list=new ArrayList<>();
    ArrayList<Room> deselected_room_list=new ArrayList<>();
    @BindView(R.id.list_add_rooms)ListView listview;
    @BindView(R.id.no_text) TextView notext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
        v = inflater.inflate(R.layout.fragment_fav_rooms_list, container, false);
        mcontext = v.getContext();
        ButterKnife.bind(this, v);

        try {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_rooms_by_device", new JSONObject().put("type","Lighting")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setSubcriber();
        return v;
    }

    private void setSubcriber() {

        try {
            Log.e("DeviceData",""+App.getDevicesByRoomData().getJSONArray("data"));
            if (App.getRoomData().getJSONArray("data").toString().equals("[]")) {
                notext.setVisibility(View.VISIBLE);
                notext.setText( "No Rooms are available");

            }


        } catch (Exception e) {
            Log.e("Eeeeeeeeeeee",""+e.getMessage());
        }
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

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String string) {


                Log.e("SImulationDATA","rrrrrrrrrrrrrrrrrr"+string);

                try {

                    if(getRoomItem().size()>0) {
                        // rel_no_data.setVisibility(View.GONE);
                        listview.setVisibility(View.VISIBLE);
                        arr_room_list=getRoomItem();
                       /* FavriotesAllRoomListAdapter customRoomItemAdapter = new FavriotesAllRoomListAdapter(mcontext, arr_room_list,Favorites_Rooms_List.this);
                        listview.setAdapter(customRoomItemAdapter);*/

                    }
                    else
                    {
                        listview.setVisibility(View.GONE);
                        /*rel_no_data.setVisibility(View.VISIBLE);
                        frm_home.setVisibility(View.GONE);*/
                        notext.setVisibility(View.VISIBLE);
                        notext.setText("No Rooms Available");
                        //circular_progress_bar.setVisibility(View.GONE);
                    }

                }
                catch (Exception ex){Log.e("Error",""+ex.getMessage());}
            }
        };



        Log.e("IMINSub","hhhhhhhhh");
        Subcriber s = new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);
        //  }
    }

    private ArrayList<Room> getRoomItem() {
            try {

                JSONObject jsonObject = App.getRoomDeviceData();
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                Log.e("getRoomDeviceData", "" + jsonArray);
                arr_room_list.clear();
                deselected_room_list.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String roomname = "";
                        Room room = new Room();
                        roomname = jsonObject1.getString("CML_TITLE");
                        room.setRoom_id(jsonObject1.getString("CML_ID"));
                        room.setRoom_title("" + roomname);

                        arr_room_list.add(room);
                        deselected_room_list.add(room);
                }

            } catch (Exception ex) {
                Log.e("jsonerror", "------------------------" + ex.getMessage());
            }
            return arr_room_list;

        }

}

