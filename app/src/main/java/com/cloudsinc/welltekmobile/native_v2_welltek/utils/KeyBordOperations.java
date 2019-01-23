package com.cloudsinc.welltekmobile.native_v2_welltek.utils;
import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
/**
 * Created by developers on 13/9/17.
 */
public class KeyBordOperations {
    public static void setupUI(View view, final Activity mactivity) {
        final Activity activity=mactivity;
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    showSoftwareKeyboard(false,activity);
                    //code of hide soft keyboard
                    return false;
                }
            });
        }
    }
    protected static void showSoftwareKeyboard(boolean showKeyboard, Activity mcontext){
        final Activity activity = mcontext;
        final InputMethodManager inputManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
       inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), showKeyboard ? InputMethodManager.SHOW_FORCED : InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
