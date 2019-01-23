package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.filters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by developers on 10/11/17.
 */

public class OrderWaterFilterFragment extends Fragment {


    public static OrderWaterFilterFragment newInstance() {
        return new OrderWaterFilterFragment();
    }

    private View v;
    private ProgressDialog prDialog;

    @BindView(R.id.web_order_filter)
    WebView web_order_filter;

    Context mcontext;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
        v = inflater.inflate(R.layout.fragment_order_water, container, false);
        mcontext = v.getContext();
        ButterKnife.bind(this, v);

        web_order_filter.setWebViewClient(new WebVwClientcls());
        web_order_filter.getSettings().setJavaScriptEnabled(true);
        web_order_filter.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        web_order_filter.loadUrl("https://staywellshop.com");

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
            prDialog = new ProgressDialog(mcontext);
            prDialog.setMessage("Loading");
            prDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (prDialog != null) {
                prDialog.dismiss();
            }
        }
    }
}



