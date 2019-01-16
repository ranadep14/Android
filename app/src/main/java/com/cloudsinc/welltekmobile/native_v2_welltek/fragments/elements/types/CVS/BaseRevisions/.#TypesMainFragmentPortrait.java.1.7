package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.types;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * This class containing functionality related to displaying displaying categories
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class TypesMainFragmentPortrait extends Fragment {

    public static TypesMainFragmentPortrait newInstance() {
        return new TypesMainFragmentPortrait();
    }



    private Tracker mTracker;

    private View v;
    @BindView(R.id.lyt_lights)LinearLayout lyt_lights;
    @BindView(R.id.lyt_blinds)LinearLayout lyt_blinds;
    @BindView(R.id.lyt_audio)LinearLayout lyt_audio;


    String TAG="TypeFragment";
    Context mcontext;

    String str_tyep="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         v=inflater.inflate(R.layout.fragment_elements_type, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);
        if (!checkConfiguration()) {
            Logs.info(TAG,"Error in Device type Fragment");
        }
        App application = (App)getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();

        return v;
    }





    @OnClick(R.id.lyt_lights)
    public void type_light(){
        Bundle bundle =new Bundle();
        bundle.putString("DEVICE_TYPE","Lights");
        Fragment fragment= DevicesByType.newInstance();
        fragment.setArguments(bundle);
        FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
        transaction1.replace(R.id.frm_main_container, fragment);
        transaction1.addToBackStack("fragmentAllDevices");
        transaction1.commit();

    }
    @OnClick(R.id.lyt_blinds)
    public void type_blinds(){
        Bundle bundle =new Bundle();
        bundle.putString("DEVICE_TYPE","Blinds");
        Fragment fragment=DevicesByType.newInstance();
        fragment.setArguments(bundle);
        FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
        transaction1.replace(R.id.frm_main_container, fragment);
        transaction1.addToBackStack("fragmentShowControl");
        transaction1.commit();
    }
    @OnClick(R.id.lyt_audio)
    public void type_audio(){
        Bundle bundle =new Bundle();
        bundle.putString("DEVICE_TYPE","Audio");
        Fragment fragment= DevicesByType.newInstance();
        fragment.setArguments(bundle);
        FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
        transaction1.replace(R.id.frm_main_container, fragment);
        transaction1.addToBackStack("fragmentShowControl");
        transaction1.commit();
    }


    private void sendScreenImageName() {
        String name = "Type Fragment";
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