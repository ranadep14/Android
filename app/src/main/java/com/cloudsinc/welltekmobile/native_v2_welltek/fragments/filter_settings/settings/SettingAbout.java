package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;


import android.content.Context;
import android.graphics.Paint;
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
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
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
public class SettingAbout extends Fragment {
    public static SettingAbout newInstance() {
        return new SettingAbout();
    }


    private View view;

    TextView txt_cancle;
    @BindView(R.id.txt_app_version)TextView txt_app_version;
    @BindView(R.id.txt_firm_version)TextView txt_firm_version;
    @BindView(R.id.txt_darwin_id)TextView txt_darwin_id;
    @BindView(R.id.txt_save)TextView txt_save;
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @BindView(R.id.txt_reboot_darwin)TextView txt_reboot_darwin;


    private Observable<String> mobservable;
    private Observer<String> myObserver;

    private String TAG=this.getClass().getSimpleName();
    private Context mcontext;
    Double MyLat,MyLong;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting_about, container, false);
        mcontext = view.getContext();
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();

        FontUtility.setAppFont(mContainer, mFont);
        ButterKnife.bind(SettingAbout.this,view);
        getHubInfo();
        setSubcriber();
        App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_network_info",new JSONObject()));
        txt_fragment_title.setText("About");
        txt_save.setVisibility(View.GONE);

        return view;
    }
    @OnClick(R.id.txt_reboot_darwin)
    public void txt_reboot_darwin()
    {
        if(App.getSocket()!=null)
        {
            try {
                JSONObject obj = new JSONObject();
                obj.put("data", new JSONObject());
                obj.put("type","reboot_hub" );
                Logs.info(TAG,"-------"+obj);
                App.getSocket().emit("action", obj);
            }
            catch (Exception ex)
            {
                Logs.error(TAG,"------"+ex.getMessage());
            }

        }

    }
    @OnClick(R.id.img_back)
    public void img_back()
    {
        if(App.isOrientationFlag())ReplaceFragment.replaceFragment(SettingAbout.this, R.id.frm_filter_main_container,SettingMainFragment.newInstance());
        else ReplaceFragment.replaceFragment(SettingAbout.this, R.id.frm_main_container,SettingMainFragment.newInstance());

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
                getHubInfo();


            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);


    }

    private void getHubInfo()
    {

        try {
            Log.d("LocationData",""+App.getNetworkInfoJson().getJSONArray("data"));
                    JSONArray jsonArray = App.getNetworkInfoJson().getJSONArray("data");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    txt_app_version.setText(""+mcontext.getPackageManager().getPackageInfo(mcontext.getPackageName(), 0).versionName);
                    txt_firm_version.setText(""+jsonObject.getString("CML_HUB"));
                    txt_darwin_id.setText(""+jsonObject.getString("CML_SERIAL"));
        }
        catch (Exception ex){
            Logs.error("LocationError", "" + ex.getMessage());}
    }

}