package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.all_experiences;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.MyViewPagerAdapter;
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


public class AllFavoritesFragment extends Fragment {
    public static AllFavoritesFragment newInstance() {
        return new AllFavoritesFragment();
    }
///
    @BindView(R.id.fav_pager)
    ViewPager room_pager;
    @BindView(R.id.layoutDots)
    LinearLayout dotsLayout;
    @BindView(R.id.rel_no_data)
    RelativeLayout rel_no_data;
    @BindView(R.id.rel_room_tab)
    RelativeLayout rel_room_tab;
    @BindView(R.id.txt_no_rooms_available)
    TextView txt_no_rooms_available;
    @BindView(R.id.circular_progress_bar)
    ProgressBar circular_progress_bar;
    TextView[] dots;
    ArrayList<Fragment> layouts = new ArrayList<>();
    Bundle bundle, bundle2, bundle3;
    private View v;
    private MyViewPagerAdapter myViewPagerAdapter;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    private ArrayList<Room> arr_room_list = new ArrayList<>();
    String TAG = "AllFavoritesFragment";
    Context mcontext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
        v = inflater.inflate(R.layout.fragment_all_favorites, container, false);
        mcontext = v.getContext();
        ButterKnife.bind(this, v);
        App.setTemp_bundle(new Bundle());

        App.clearSubscriber();
        App.clearFavSubscriber();
        setSubcriber();

        if (App.getSocket() != null) {
            try {

                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_rooms", new JSONObject()));


            } catch (Exception ex) {
            }
        }

        return v;
    }

    final ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            Log.e("onPageSelected", "-------" + position);
            addBottomDots(position);

            requestForDevicesByRoom(position);

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

            Log.e("onPageScrolled", "-------" + arg0 + "-----" + "------" + arg1 + "-----" + arg2);

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            Log.e("eScrollStateChanged", "-------" + arg0);


        }
    };


    public void requestForDevicesByRoom(int position) {

        if(position>=0) {
            Log.e("WhereIm", "InRoomMainFragment");
            try {
                String room_id = App.getRoomData().getJSONArray("data").getJSONObject(position).getString("CML_ID");
                String scene_id = App.getRoomData().getJSONArray("data").getJSONObject(position).getString("CML_SCENE_ID");
                Room room = new Room();
                room.setRoom_id(room_id);
                room.setRoom_scene(scene_id);
                App.setRoom(room);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            App.setCurrentSubcriber(App.getFav_subscriber().get(position));
            if(App.getSocket()!=null)App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_favorites", new JSONObject()));

        }


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

                Log.e("Response_String",string);
                if(string.equals("rooms"))getRoomTabs();

            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setFav_room_subcriber(s);


    }




    public void getRoomTabs()
    {

        try {

            JSONArray jsonArray = App.getRoomData().getJSONArray("data");

            layouts.clear();
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject json = jsonArray.getJSONObject(i);
                bundle = new Bundle();
                bundle.putString("CML_TITLE", json.getString("CML_TITLE"));
                bundle.putString("CML_ROOM_TYPE", json.getString("CML_ROOM_TYPE"));
                bundle.putString("CML_ID", json.getString("CML_ID"));


                Log.e("Room_type", json.getString("CML_ROOM_TYPE"));
                if(json.getString("CML_ROOM_TYPE").equals("Bedroom"))
                {
                    Fav_bedroom fav_one= Fav_bedroom.newInstance();
                    fav_one.setArguments(bundle);
                    layouts.add(fav_one);

                }
                else if(json.getString("CML_ROOM_TYPE").equals("Living Room"))
                {

                    Fav_livingroom fav_two=Fav_livingroom.newInstance();
                    fav_two.setArguments(bundle);
                    layouts.add(fav_two);

                }
                else if(json.getString("CML_ROOM_TYPE").equals("Kitchen"))
                {
                    Fav_kicthen fav_three = Fav_kicthen.newInstance();
                    fav_three.setArguments(bundle);
                    layouts.add(fav_three);

                }
                else {
                    Fav_other fav_other=Fav_other.newInstance();
                    fav_other.setArguments(bundle);
                    layouts.add(fav_other);
                }


            }

            if(layouts.size()>0)
            {
                rel_no_data.setVisibility(View.GONE);
                rel_room_tab.setVisibility(View.VISIBLE);
            }
            else
            {
                //Toast.makeText(getActivity(), "layout 0", Toast.LENGTH_SHORT).show();
                rel_no_data.setVisibility(View.VISIBLE);
                rel_room_tab.setVisibility(View.GONE);
                txt_no_rooms_available.setVisibility(View.VISIBLE);
                circular_progress_bar.setVisibility(View.GONE);
            }
            addBottomDots(0);

            myViewPagerAdapter = new MyViewPagerAdapter(getActivity().getSupportFragmentManager(),layouts);
            room_pager.setAdapter(myViewPagerAdapter);
            room_pager.addOnPageChangeListener(viewPagerPageChangeListener);
            requestForDevicesByRoom(0);
        }
        catch (Exception ex)
        {
            Log.e("RoomTabError",""+ex.getMessage());
        }

        // return resultarr;
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.size()];



        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(mcontext);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(mcontext.getResources().getColor(R.color.dot_dark_screen1));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(mcontext.getResources().getColor(R.color.dot_light_screen1));
    }

}