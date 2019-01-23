package com.cloudsinc.welltekmobile.native_v2_welltek.utils;

import android.view.View;

import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;


/**
 * Created by developers on 26/9/17.
 */

public class BackButtonVisiblity {

    public static void setVisiblity(View v)
    {
        if(App.isOrientationFlag())v.setVisibility(View.GONE);
    }
}
