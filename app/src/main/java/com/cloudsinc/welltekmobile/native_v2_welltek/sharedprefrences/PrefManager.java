package com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences;
/**
 * Created by developers on 17/5/17.
 * @purpose  - used for displaying home intro slider one time
 **/
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    boolean apply = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    // shared pref mode
    int PRIVATE_MODE = 0;
    // Shared preferences file name
    public static final String PREF_NAME = "Delos-welltek";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }
    public void removeKey(String Key) {
        Log.i("RemoveKey",""+Key);
        editor.remove(""+Key);
        editor.commit();
    }
    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, false);
    }
    public void setValue(String key, String value) {
        editor.putString(key, value);
        editor.commit();
        if(apply) {
            editor.apply();
        } else {
            editor.commit();
        }
    }
    public String getValue(String key) {
        return key.equals("demo_scene")?pref.getString(key, "true"):pref.getString(key, "No Record");
    }
    public void setValueDawn(String key, int value) {
        editor.putInt(key, value);
        if(apply) {
            editor.apply();
        } else {
            editor.commit();
        }
    }
    public int getValueDawn(String key) {
        return pref.getInt(key, -1);
    }
    public void setValueSleep(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
        if(apply) {
            editor.apply();
        } else {
            editor.commit();
        }
    }
    public int getValueSleep(String key) {
        return pref.getInt(key, -1);
    }
}