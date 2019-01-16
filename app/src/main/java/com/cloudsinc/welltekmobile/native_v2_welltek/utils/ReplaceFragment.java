package com.cloudsinc.welltekmobile.native_v2_welltek.utils;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
/**
 * Created by developers on 20/5/17.
 */
public class ReplaceFragment {
    public static void replaceFragment(Fragment mcontext, int frm_id, Fragment fragment)
    {
        hideKeyboard(mcontext.getActivity());
        FragmentTransaction transaction = mcontext.getFragmentManager().beginTransaction();
        transaction.replace(frm_id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public static void hideKeyboard(Activity activity)
    {
        try
        {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        catch (Exception e)
        {
            // Ignore exceptions if any
        }
    }
}
