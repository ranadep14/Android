package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.CustomChooseItemListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Room;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FilterJsonArray;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;


public class SettingAddSonosRooms extends Fragment {
    public static SettingAddSonosRooms newInstance() {
        return new SettingAddSonosRooms();
    }

    private View view;
    private Context mcontext;
    private ListView listview;
    private App app;
    Observable<String> mobservable;
    Observer<String> myObserver;
    private ArrayList<Room> arr_room_list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting_add_sonos_rooms, container, false);
        mcontext=view.getContext();
        ButterKnife.bind(this,view);
        Log.e("xc xc xc xc x ","dsssssssssssss");
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();

        FontUtility.setAppFont(mContainer, mFont);

      // getActivity().findViewById(R.id.txt_save).setVisibility(View.GONE);
       /* TextView txt_tab_title=(TextView) getActivity().findViewById(R.id.txt_tab_title);
        txt_tab_title.setText("Add Devices Rooms");*/
        app=(App)mcontext.getApplicationContext();
        listview = view.findViewById(R.id.list_add_room);


        try {

            arr_room_list=(ArrayList<Room>) FilterJsonArray.getFirlterByTag("Room_list", "");
            CustomChooseItemListAdapter customRoomItemAdapter = new CustomChooseItemListAdapter(mcontext, arr_room_list);
            listview.setAdapter(customRoomItemAdapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String str_id =((TextView)view.findViewById(R.id.txt_id)).getText().toString();
                    Log.e("Room",str_id);
                    Bundle bundle=App.getTemp_bundle();
                    bundle.putString("room_id",str_id);
                    App.setTemp_bundle(bundle);
                    ReplaceFragment.replaceFragment(SettingAddSonosRooms.this,R.id.frm_filter_main_container, AddSonosFragment.newInstance());
                }
            });
        }
        catch (Exception ex){Log.e("Error",""+ex.getMessage());}





        return view;
    }

    @OnClick(R.id.img_back)
    public void img_back()
    {
        ReplaceFragment.replaceFragment(SettingAddSonosRooms.this,R.id.frm_filter_main_container, AddSonosFragment.newInstance());
    }

}
