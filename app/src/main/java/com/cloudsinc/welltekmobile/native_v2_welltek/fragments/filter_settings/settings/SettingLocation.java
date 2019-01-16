package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * This class containing functionality related to display location of hub
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class SettingLocation extends Fragment {
    public static SettingLocation newInstance() {
        return new SettingLocation();
    }


    private View view;

    TextView txt_cancle;
    @BindView(R.id.txtcountry)TextView txtcountry;
    @BindView(R.id.txtstate)TextView txtstate;
    @BindView(R.id.txtcity)TextView txtcity;
    @BindView(R.id.txt_save)TextView txt_save;
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;


    private Observable<String> mobservable;
    private Observer<String> myObserver;

    private Context mcontext;
    Double MyLat,MyLong;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting_location, container, false);
        mcontext = view.getContext();
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();

        FontUtility.setAppFont(mContainer, mFont);
        ButterKnife.bind(SettingLocation.this,view);
        getLocationInfo();
        setSubcriber();

        txt_fragment_title.setText("Location");
        txt_save.setVisibility(View.GONE);


       // txt_cancle=(TextView) getActivity().findViewById(R.id.txt_cancle);oe

      /*  txt_cancle.setText("Cancel");
        txt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReplaceFragment.replaceFragment(SettingLocation.this, R.id.frm_filter_main_container,SettingMainFragment.newInstance());
            }
        });*/
      /*  getActivity().findViewById(R.id.btn_setting).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.btn_back_home_health).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.txt_save).setVisibility(View.GONE);
        getActivity().findViewById(R.id.txt_cancle).setVisibility(View.GONE);
*/
        return view;
    }
    @OnClick(R.id.img_back)
    public void img_back()
    {
        if(App.isOrientationFlag())ReplaceFragment.replaceFragment(SettingLocation.this, R.id.frm_filter_main_container,SettingHome.newInstance());
        else ReplaceFragment.replaceFragment(SettingLocation.this, R.id.frm_main_container,SettingHome.newInstance());

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
                getLocationInfo();


            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);


    }

    private void getLocationInfo()
    {

        try {
            Log.d("LocationData",""+App.getHubInfo().getJSONArray("data"));
                    JSONArray jsonArray = App.getHubInfo().getJSONArray("data");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    txtcity.setText(""+jsonObject.getString("CML_ORG_CITY"));
                    txtstate.setText(""+jsonObject.getString("CML_ORG_STATE"));
                    txtcountry.setText(""+jsonObject.getString("CML_ORG_COUNTRY"));
        }
        catch (Exception ex){
            Logs.error("LocationError", "" + ex.getMessage());}
    }

}