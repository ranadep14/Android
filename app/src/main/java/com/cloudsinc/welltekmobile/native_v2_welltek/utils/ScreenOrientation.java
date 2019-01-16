package com.cloudsinc.welltekmobile.native_v2_welltek.utils;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;
import android.view.Display;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
/**
 * Created by developers on 9/8/17.
 */
public class ScreenOrientation {
    public static void setOrientation(final Activity activity)
    {
        if (checkScreen(activity,"tablet")){
            App.setOrientationFlag(true);
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        else{
            App.setOrientationFlag(false);
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    public static boolean checkScreen(Activity activity,String screen) {
        Display display = activity.getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        float width = displayMetrics.widthPixels / displayMetrics.xdpi;
        float height = displayMetrics.heightPixels / displayMetrics.ydpi;
        double screenDiagonal = Math.sqrt( width * width + height * height );
        int inch = (int) (screenDiagonal + 0.5);
        Logs.info("ScreenOrientation","------------------"+inch);
        ////Toast.makeText(activity, "inch : "+ inch, Toast.LENGTH_LONG).show();
        int screen_digonal=screen.equals("tablet")?7:32;
        return (inch >=  screen_digonal);
    }
}
