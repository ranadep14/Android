package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by developers on 10/11/17.
 */

public class LegalFragent extends Fragment {


    public static LegalFragent newInstance() {
        return new LegalFragent();
    }

    private View v;
    private Tracker mTracker;

    @BindView(R.id.webview)WebView discover_web;


    Context mcontext;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_legal, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);
        if (!checkConfiguration()) {
            Logs.info("Legal","Error in LegalFragment");
        }
        App application = (App)getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();
        discover_web.setBackgroundColor(0x00000000);

        discover_web.setWebViewClient(new WebVwClientcls());
        discover_web.getSettings().setJavaScriptEnabled(true);
        discover_web.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        discover_web.loadUrl("file:///android_asset/legalfile.html");
        return v;
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
            discover_web.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
        }
    }
    private void sendScreenImageName() {
        String name = "Legal Fragment";
        String username=new PrefManager(mcontext).getValue("User_Id");
        // [START screen_view_hit]
        Log.i("Legal Fragment", "Setting screen name: " + name);
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
            Log.w("Legal Fragment", "checkConfiguration", e);
            return false;
        }

        return true;
    }
}
