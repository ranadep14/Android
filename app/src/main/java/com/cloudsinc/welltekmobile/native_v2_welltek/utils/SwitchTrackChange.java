package com.cloudsinc.welltekmobile.native_v2_welltek.utils;

import android.content.Context;
import android.widget.Switch;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;

/**
 * Created by developers on 29/1/18.
 */

public class SwitchTrackChange {

    public static void changeTrackColor(Context mcontext,Switch swt_on_off)
    {
        if(swt_on_off.isChecked())
        {
            swt_on_off.setTrackDrawable(mcontext.getResources().getDrawable(R.drawable.customtrack_on));
        }
        else
        {
            swt_on_off.setTrackDrawable(mcontext.getResources().getDrawable(R.drawable.customtrack_off));
        }
    }
}
