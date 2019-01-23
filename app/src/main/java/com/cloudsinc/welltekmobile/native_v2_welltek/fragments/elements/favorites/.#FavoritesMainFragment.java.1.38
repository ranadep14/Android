package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.favorites;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.FavoritesListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Favorites;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import static com.cloudsinc.welltekmobile.native_v2_welltek.adapters.FavoritesListAdapter.checkGroup;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.FAVORITES;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.GROUPS;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.PROVISIONAL_DEVICES;
/**
 * This class containing functionality related to displaying favorite items includes light,sonos,scene,blinds etc.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class FavoritesMainFragment extends Fragment {
    public static FavoritesMainFragment newInstance() {
        return new FavoritesMainFragment();
    }
    @BindView(R.id.rec_fav)RecyclerView rec_fav;
    @BindView(R.id.lin_no_data)LinearLayout lin_no_data;
    @BindView(R.id.rel_loading)RelativeLayout rel_loading;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    private View v;
    private Tracker mTracker;

    FavoritesListAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Favorites> arrayFavList =new ArrayList<>();
    String TAG=FavoritesMainFragment.this.getClass().getSimpleName();
    Context mcontext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
         v=inflater.inflate(R.layout.activity_favorites_main, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);

        if (!checkConfiguration()) {
            Logs.info(TAG,"Error in Favorites Fragment");
        }
        App application = (App)getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();

        // use a linear layout manager
        updateUI();
        setSubcriber();
        return v;
    }
    private void setSubcriber() {
        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext(""+ App.getRoomData());
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
                Logs.info(TAG+"_Response_String",string);
                if(string.equals("zones")||string.equals("zones_Whole Home")||string.equals("device_updated")||string.equals("room_device")||string.equals("Music")||string.equals("Blinds")||string.equals("Lighting")) {
                    updateUI();
                }
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);
    }

    public void updateUI()
    {
        arrayFavList.clear();
        getFavList();
        if (arrayFavList.size() > 0) {
            if(mAdapter==null) {
                mAdapter = new FavoritesListAdapter(FavoritesMainFragment.this, mcontext, arrayFavList, getActivity().getSupportFragmentManager(),"");
                rec_fav.setAdapter(mAdapter);
                mLayoutManager = new LinearLayoutManager(mcontext);
                rec_fav.setLayoutManager(mLayoutManager);
            }
            else
            {
                mAdapter.reload(arrayFavList,"");
                mAdapter.notifyDataSetChanged();
            }
            rel_loading.setVisibility(View.GONE);
            lin_no_data.setVisibility(View.GONE);
        } else {
            lin_no_data.setVisibility(View.VISIBLE);
            rel_loading.setVisibility(View.GONE);
        }
    }
    public void getFavList()
    {
            arrayFavList.clear();
            try {
                Iterator<String> keys = App.getDataStorageJson().getJSONObject(FAVORITES).keys();
                while (keys.hasNext()) {
                    JSONObject jsonObject=App.getDataStorageJson().getJSONObject(FAVORITES).getJSONObject(keys.next());
                    if (jsonObject.has("deviceId"))
                    {
                        if(!jsonObject.getString("deviceId").equals("")) {
                            if(checkDeviceExit(jsonObject.getString("deviceId"))) {
                                Favorites favorites = new Favorites();
                                favorites.setFav_id(jsonObject.getString("CML_ID"));
                                if (jsonObject.has("roomId"))
                                    favorites.setRoom_id(jsonObject.getString("roomId"));
                                if (jsonObject.has("DAWN_RUNNING"))
                                    favorites.setDawn_running(jsonObject.getBoolean("DAWN_RUNNING"));
                                if (jsonObject.has("DAWN_ID"))
                                    favorites.setDawn_id(jsonObject.getString("DAWN_ID"));
                                if (jsonObject.has("SLEEP_RUNNING"))
                                    favorites.setSleep_running(jsonObject.getBoolean("SLEEP_RUNNING"));
                                if (jsonObject.has("SLEEP_ID"))
                                    favorites.setSleep_id(jsonObject.getString("SLEEP_ID"));
                                if (jsonObject.has("roomTitle"))
                                    favorites.setRoom_title(jsonObject.getString("roomTitle"));
                                if (jsonObject.has("deviceId"))
                                    favorites.setDevice_id(jsonObject.getString("deviceId"));
                               if(jsonObject.getString("deviceId").contains("group")) {
                                   arrayFavList.add(favorites);
                               }
                               else
                               {
                                   if(checkGroupExit(jsonObject.getString("deviceId")))arrayFavList.add(favorites);
                               }
                            }
                        }
                    }
                    if(jsonObject.has("CML_ID"))
                    {
                        if(jsonObject.getString("CML_ID").contains("light")||jsonObject.getString("CML_ID").contains("blind")||jsonObject.getString("CML_ID").contains("audio")) {
                            Favorites favorites = new Favorites();
                            favorites.setFav_id(jsonObject.getString("CML_ID"));
                            arrayFavList.add(favorites);
                        }
                    }
                }
            }
            catch (Exception ex){
                 Logs.error(TAG+"_HvacStateError",ex.getMessage());}
    }
    public static boolean checkDeviceExit(String str_device_id)
    {
        boolean b=false;
        try {
            Iterator<String> keys = App.getDataStorageJson().getJSONObject(PROVISIONAL_DEVICES).keys();
            while (keys.hasNext()) {
                JSONObject jsonObject=App.getDataStorageJson().getJSONObject(PROVISIONAL_DEVICES).getJSONObject(keys.next());
                if (jsonObject.getString("CML_ID").equals(str_device_id)||jsonObject.getString("Id").equals(str_device_id)) {
                    b = true;
                    break;
                }
            }
            Iterator<String> keys1 = App.getDataStorageJson().getJSONObject(GROUPS).keys();
            while (keys1.hasNext()) {
                JSONObject jsonObject=App.getDataStorageJson().getJSONObject(GROUPS).getJSONObject(keys1.next());
                if (jsonObject.getString("CML_ID").equals(str_device_id)||jsonObject.getString("Id").equals(str_device_id)) {
                    b = true;
                    break;
                }
            }
        }
        catch (Exception ex)
        {
            Logs.error("FavoritesMainFragment",""+ex.getMessage());
        }
        return b;
    }
    public static boolean checkGroupExit(String str_device_id)
    {
        boolean b=false;
        try {
            Iterator<String> keys = App.getDataStorageJson().getJSONObject(PROVISIONAL_DEVICES).keys();
            while (keys.hasNext()) {
                JSONObject jsonObject=App.getDataStorageJson().getJSONObject(PROVISIONAL_DEVICES).getJSONObject(keys.next());
                if (jsonObject.getString("CML_ID").equals(str_device_id)||jsonObject.getString("Id").equals(str_device_id)) {
                    b = checkGroup(jsonObject);
                    break;
                }
            }
            Iterator<String> keys1 = App.getDataStorageJson().getJSONObject(GROUPS).keys();
            while (keys1.hasNext()) {
                JSONObject jsonObject=App.getDataStorageJson().getJSONObject(GROUPS).getJSONObject(keys1.next());
                if (jsonObject.getString("CML_ID").equals(str_device_id)||jsonObject.getString("Id").equals(str_device_id)) {
                    b = true;
                    break;
                }
            }
        }
        catch (Exception ex)
        {
            Logs.error("FavoritesMainFragment",""+ex.getMessage());
        }
        return b;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logs.info(TAG,"I am in FavoriteMainFragemnt ondestroy");
        System.gc();
        rec_fav.removeAllViewsInLayout();
    }

    private void sendScreenImageName() {
        String name = "Favorites Fragment";
        String username=new PrefManager(mcontext).getValue("User_Id");
        // [START screen_view_hit]
        Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName(""+username+"~" + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
    }

    private boolean checkConfiguration() {
        XmlResourceParser parser = getResources().getXml(R.xml.global_tracker);

        boolean foundTag = false;
        try {
            while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlResourceParser.START_TAG) {
                    String tagName = parser.getName();
                    String nameAttr = parser.getAttributeValue(null, "name");

                    foundTag = "string".equals(tagName) && "ga_trackingId".equals(nameAttr);
                }

                if (parser.getEventType() == XmlResourceParser.TEXT) {
                    if (foundTag && parser.getText().contains("REPLACE_ME")) {
                        return false;
                    }
                }

                parser.next();
            }
        } catch (Exception e) {
            Log.w(TAG, "checkConfiguration", e);
            return false;
        }

        return true;
    }
}