package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.discover;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity.bottom_menu_bar;


/**
 * The DiscoverMainFragment is used to display darwin website
 *
 * @author  Jaid Shaikh
 * @version 1.0
 * @since   2017-11-8
 */

public class DiscoverMainFragment extends Fragment {

    public static DiscoverMainFragment newInstance() {
        return new DiscoverMainFragment();
    }
    private View v;
    private Tracker mTracker;


    String TAG="DiscoverFragment";
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @BindView(R.id.rel_loading)RelativeLayout prog;
    @BindView(R.id.discover_web)WebView discover_web;

    Context mcontext;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        v=inflater.inflate(R.layout.activity_discover_main, container, false);
        ButterKnife.bind(this,v);
        bottom_menu_bar.setVisibility(View.GONE);
        mcontext=v.getContext();
        if (!checkConfiguration()) {
          Log.e("Discover Activity","Error in Discover tracker");
        }
        App application = (App)getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();

        txt_fragment_title.setText("Discover");
        ButterKnife.bind(this,v);
        bottom_menu_bar.setVisibility(View.GONE);
        discover_web.setBackgroundColor(0x00000000);
        discover_web.setWebViewClient(new WebVwClientcls());
        discover_web.setWebChromeClient(new WebChromeClient());
        discover_web.getSettings().setJavaScriptEnabled(true);
        discover_web.getSettings().setSupportMultipleWindows(true);
        discover_web.getSettings().setMediaPlaybackRequiresUserGesture(false);
        discover_web.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        discover_web.loadUrl("http://delos.com.au/");

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        discover_web.destroyDrawingCache();
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!App.isOrientationFlag()) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        }

    }
    private class WebVwClientcls extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            discover_web.setBackgroundColor(0x00000000);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
           /* if(prDialog!=null){
                prDialog.dismiss();
            }*/
            discover_web.setVisibility(View.VISIBLE);
            prog.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
        }
    }
    @Optional
    @OnClick(R.id.img_back)
    public void img_back()
    {

        MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!App.isOrientationFlag()) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if(!App.isOrientationFlag()){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        }
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

    private void sendScreenImageName() {
        String name = "Discover Activity";
        String username=new PrefManager(mcontext).getValue("User_Id");
        // [START screen_view_hit]
        Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName(""+username+"~" + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
    }

}
