package com.cloudsinc.welltekmobile.native_v2_welltek.utils;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by developers on 13/4/17.
 */

public class FontUtility {
    public static void setAppFont(ViewGroup mContainer, Typeface mFont)
    {
        if (mContainer == null || mFont == null) return;

        final int mCount = mContainer.getChildCount();

        // Loop through all of the children.
        for (int i = 0; i < mCount; ++i)
        {
            final View mChild = mContainer.getChildAt(i);
            if (mChild instanceof TextView)
            {
                // Set the font if it is a TextView.
                ((TextView) mChild).setTypeface(mFont);
            }
            else if (mChild instanceof Button)
            {
                // Set the font if it is a TextView.
                ((Button) mChild).setTypeface(mFont);
            }
            else if (mChild instanceof EditText)
            {
                // Set the font if it is a TextView.
                ((EditText) mChild).setTypeface(mFont);
            }

            else if (mChild instanceof ViewGroup)
            {
                // Recursively attempt another ViewGroup.
                setAppFont((ViewGroup) mChild, mFont);
            }
        }
    }
}
