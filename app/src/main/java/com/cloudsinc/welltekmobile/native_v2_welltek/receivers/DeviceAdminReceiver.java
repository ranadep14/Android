package com.cloudsinc.welltekmobile.native_v2_welltek.receivers;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
/**
 * For admin permission
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class DeviceAdminReceiver extends android.app.admin.DeviceAdminReceiver {
    public static ComponentName getComponentName(Context context) {
        return new ComponentName(context.getApplicationContext(), DeviceAdminReceiver.class);
    }
    @Override
    public void onEnabled(Context context, Intent intent) {
    }
    @Override
    public void onDisabled(Context context, Intent intent) {
    }
}
