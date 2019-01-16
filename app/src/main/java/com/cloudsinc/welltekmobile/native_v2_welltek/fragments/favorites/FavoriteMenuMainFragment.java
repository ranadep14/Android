package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.favorites;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.ExperiencesFavoritesListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.FavoritesListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.HomeHeathByZoneFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.ExperiencesFavorites;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Favorites;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity.bottom_menu_bar;
import static com.cloudsinc.welltekmobile.native_v2_welltek.adapters.FavoritesListAdapter.checkGroup;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.favorites.FavoritesMainFragment.checkDeviceExit;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.favorites.FavoritesMainFragment.checkGroupExit;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.FAVORITES;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.Constances.PROVISIONAL_DEVICES;
/**
 * Fragment for display favorite items under on portrait mode
 *
 * @author Nikhil Vharamble
 * @version 2.0.0
 * @since 2018-01-09
 */
public class FavoriteMenuMainFragment extends Fragment {
    public static FavoriteMenuMainFragment newInstance() {
        return new FavoriteMenuMainFragment();
    }
    @BindView(R.id.rec_fav)RecyclerView rec_fav;
    @BindView(R.id.rec_exp_fav)RecyclerView rec_exp_fav;
    @BindView(R.id.lin_no_data)LinearLayout lin_no_data;
    @BindView(R.id.rel_loading) RelativeLayout rel_loading;
    @BindView(R.id.lin_fav_exp)LinearLayout lin_fav_exp;
    @BindView(R.id.lin_fav_element)LinearLayout lin_fav_element;
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @BindView(R.id.textContent)WebView textContent;
    @BindView(R.id.rel_no_elements)RelativeLayout rel_no_elements;
    @BindView(R.id.rel_no_experiences)RelativeLayout rel_no_experiences;
    @BindView(R.id.rel_element_main)RelativeLayout rel_element_main;
    @BindView(R.id.rel_experiences_main)RelativeLayout rel_experiences_main;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    private View v;
    FavoritesListAdapter mAdapter;
    ExperiencesFavoritesListAdapter expmAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Favorites> arrayFavList =new ArrayList<>();
    ArrayList<ExperiencesFavorites> arrayExpFavList =new ArrayList<>();
    boolean elemen_fav_flag =false;
    boolean exp_fav_flag =false;
    String TAG="FavoritesExprFragment";
    Context mcontext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_main_favorite, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);
        bottom_menu_bar.setVisibility(View.GONE);
        String text;
        text = "<html><body><p align=\"justify\">";
        text+= "<font size=\"3\" color=\"#979797\">"+mcontext.getResources().getString(R.string.fav_place_holder_desc1)+"</font>";
        text+= "</p></body></html>";
        String text_no_element;
        text_no_element = "<html><body><p align=\"justify\">";
        text_no_element+= "<font size=\"3\" color=\"#979797\">"+mcontext.getResources().getString(R.string.no_elements)+"</font>";
        text_no_element+= "</p></body></html>";
        String text_no_experience;
        text_no_experience = "<html><body><p align=\"justify\">";
        text_no_experience+= "<font size=\"3\" color=\"#979797\">"+mcontext.getResources().getString(R.string.no_experiences)+"</font>";
        text_no_experience+= "</p></body></html>";
        textContent.loadData(text, "text/html", "utf-8");
        textContent.setBackgroundColor(mcontext.getResources().getColor(R.color.transparent));
        txt_fragment_title.setText(""+mcontext.getResources().getString(R.string.favorites_text));


        updateData();
        setFavoriteSubcriber();
        return v;
    }
    private void setFavoriteSubcriber() {
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
                updateData();
            }





        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);


    }

    private void updateData()
    {
        elemen_fav_flag=false;
        exp_fav_flag=false;
        arrayFavList.clear();
        arrayExpFavList.clear();
        getFavList();
        getExpFavList();
        if (arrayFavList.size() > 0) {

            if(mAdapter==null) {
                mAdapter = new FavoritesListAdapter(FavoriteMenuMainFragment.this, mcontext, arrayFavList, getActivity().getSupportFragmentManager(),"");
                rec_fav.setAdapter(mAdapter);
                rec_fav.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
            else
            {
                mAdapter.reload(arrayFavList,"");
                mAdapter.notifyDataSetChanged();
            }
            lin_fav_element.setVisibility(View.VISIBLE);
            rel_loading.setVisibility(View.GONE);
            rel_no_elements.setVisibility(View.GONE);
            elemen_fav_flag =true;

        } else {
            elemen_fav_flag=false;
            rel_loading.setVisibility(View.GONE);
            lin_fav_element.setVisibility(View.GONE);
            rel_no_elements.setVisibility(View.VISIBLE);

        }


        if (arrayExpFavList.size() > 0) {

            if(expmAdapter==null) {
                expmAdapter = new ExperiencesFavoritesListAdapter(FavoriteMenuMainFragment.this, mcontext, arrayExpFavList, getActivity().getSupportFragmentManager());
                rec_exp_fav.setAdapter(expmAdapter);
                rec_exp_fav.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
            else
            {
                expmAdapter.reload(arrayExpFavList);
                expmAdapter.notifyDataSetChanged();
            }
            rel_loading.setVisibility(View.GONE);
            lin_fav_exp.setVisibility(View.VISIBLE);
            rel_no_experiences.setVisibility(View.GONE);
            exp_fav_flag =true;

        } else {
            exp_fav_flag=false;
            rel_no_experiences.setVisibility(View.VISIBLE);
            rel_loading.setVisibility(View.GONE);
            lin_fav_exp.setVisibility(View.GONE);


        }

        System.out.println("-------------"+elemen_fav_flag+"------------"+exp_fav_flag);
        if(!elemen_fav_flag&&!exp_fav_flag)
        {

            rel_experiences_main.setVisibility(View.GONE);
            rel_element_main.setVisibility(View.GONE);
            rel_no_elements.setVisibility(View.GONE);
            rel_no_experiences.setVisibility(View.GONE);
            lin_fav_exp.setVisibility(View.GONE);
            lin_fav_element.setVisibility(View.GONE);
            rel_loading.setVisibility(View.GONE);
            lin_no_data.setVisibility(View.VISIBLE);
        }
        else
        {
            rel_experiences_main.setVisibility(View.VISIBLE);
            rel_element_main.setVisibility(View.VISIBLE);
            lin_no_data.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.img_back)
    public void img_back()
    {
        ReplaceFragment.replaceFragment(FavoriteMenuMainFragment.this, R.id.frm_main_container, HomeHeathByZoneFragment.newInstance());
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
                            if (jsonObject.has("SLEEP_RUNNING"))
                                favorites.setSleep_running(jsonObject.getBoolean("SLEEP_RUNNING"));
                            if (jsonObject.has("DAWN_ID"))
                                favorites.setDawn_id(jsonObject.getString("DAWN_ID"));
                            if (jsonObject.has("SLEEP_ID"))
                                favorites.setSleep_id(jsonObject.getString("SLEEP_ID"));
                            if (jsonObject.has("roomTitle"))
                                favorites.setRoom_title(jsonObject.getString("roomTitle"));
                            if (jsonObject.has("deviceId"))
                                favorites.setDevice_id(jsonObject.getString("deviceId"));
                            if(jsonObject.getString("CML_ID").contains("group")) {
                                arrayFavList.add(favorites);
                            }
                            else
                            {
                                if(checkGroupExit(jsonObject.getString("deviceId")))
                                {
                                    arrayFavList.add(favorites);
                                }
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
            Logs.error("HvacStateError",ex.getMessage());}
    }
    public void getExpFavList()
    {
        try {
            Iterator<String> keys = App.getDataStorageJson().getJSONObject(FAVORITES).keys();
            while (keys.hasNext()) {
                JSONObject jsonObject=App.getDataStorageJson().getJSONObject(FAVORITES).getJSONObject(keys.next());
                System.out.println("lllllllllllllll"+jsonObject);
                if(!jsonObject.has("deviceId")&&!jsonObject.has("CML_TITLE")&&jsonObject.has("sceneId")) {
                    ExperiencesFavorites experiencesFavorites = new ExperiencesFavorites();
                    if (jsonObject.has("sceneId"))
                        experiencesFavorites.setScene_id("" + jsonObject.getString("sceneId"));
                    experiencesFavorites.setFav_id("" + jsonObject.getString("CML_ID"));
                    if (jsonObject.has("roomId"))
                        experiencesFavorites.setRoom_id("" + jsonObject.getString("roomId"));
                    arrayExpFavList.add(experiencesFavorites);
                }
                System.out.println("rrrrrrrr");
            }
        }
        catch (Exception ex){
            Logs.error("HvacStateError",""+ex.getMessage());
        }
    }
    public void setVisiblity(LinearLayout rel_select)
    {
        rel_select.setVisibility(View.VISIBLE);
    }
    public void setActiveDectiveText(TextView txt_select,RelativeLayout rel_select)
    {
        txt_select.setText("ACTIVE");
        txt_select.setVisibility(View.VISIBLE);
        rel_select.setVisibility(View.VISIBLE);
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rec_exp_fav.removeAllViewsInLayout();
        rec_fav.removeAllViewsInLayout();
        System.gc();
    }
}