package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.BackButtonVisiblity;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity.bottom_menu_bar;


public class PrivacyPolicy extends Fragment {
    public static PrivacyPolicy newInstance() {
        return new PrivacyPolicy();
    }

    private View view;

    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @BindView(R.id.img_back)ImageView img_back;
    @BindView(R.id.webview)WebView discover_web;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        ButterKnife.bind(this,view);
        txt_fragment_title.setText("Privacy");
        bottom_menu_bar.setVisibility(View.GONE);
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();

        FontUtility.setAppFont(mContainer, mFont);

        BackButtonVisiblity.setVisiblity(img_back);

        discover_web.setBackgroundColor(0x00000000);

        discover_web.setWebViewClient(new WebVwClientcls());
        discover_web.getSettings().setJavaScriptEnabled(true);
        discover_web.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        discover_web.loadUrl("file:///android_asset/legalfile.html");


        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        discover_web.destroyDrawingCache();
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
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
            discover_web.setBackgroundColor(Color.TRANSPARENT);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            discover_web.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
        }
    }



    @OnClick(R.id.img_back)
    public void img_back()
    {
        MainActivity.drawerLayout.openDrawer(Gravity.LEFT);

          }

    @Override
    public void onResume() {
        super.onResume();
        if(!App.isOrientationFlag()) ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

    }

    @Override
    public void onStop() {
        super.onStop();
        if(!App.isOrientationFlag())  ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
}