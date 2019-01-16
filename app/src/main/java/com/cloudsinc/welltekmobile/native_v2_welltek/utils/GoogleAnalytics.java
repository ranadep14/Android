package com.cloudsinc.welltekmobile.native_v2_welltek.utils;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class GoogleAnalytics {
    public static Tracker mTracker;
    public static boolean checkConfiguration(final Activity activity) {

          XmlResourceParser parser = activity.getResources().getXml(R.xml.global_tracker);

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
            Log.w("TAG", "checkConfiguration", e);
            return false;
        }

        return true;
    }

    public static void sendScreenImageName(String activityname) {
        String name = activityname;

        // [START screen_view_hit]
        Log.i("TAG", "Setting screen name: " + name);
        mTracker.setScreenName("Image~ " + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
    }


}
