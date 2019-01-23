package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

public class PinnedClass {

/*

    final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            public static boolean ghanta;



    public static void pinned(Context context){
        */
/* Following code allow the app packages to lock task in true kiosk mode *//*

        //setContentView(R.layout.activity_login);
        // get policy manager
        DevicePolicyManager myDevicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName mAdminComponentName = MyAdmin.getComponentName(context);

        // get this app package name
        ComponentName mDPM = new ComponentName(context, MyAdmin.class);

        //startLockTask();
        try{
            ghanta = myDevicePolicyManager.isDeviceOwnerApp(context.getPackageName());
            Log.d(TAG,"DeviceOwnerApp perm"+ghanta);
            if (ghanta) {
                Log.e(TAG, "Inside if....." + ghanta);
            }
        }catch (Exception e){
            Log.e(TAG,"hehehehe"+e.toString());
        }
        if (ghanta) {
            Log.e(TAG,"Inside if"+ghanta);


//where this will be your Activity

            DevicePolicyManager mDevicePolicyManager = (DevicePolicyManager)
                    context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            String[] packages = {context.getPackageName()};
            Log.d(TAG,"PAckages , "+packages);
            try {
                mDevicePolicyManager.setLockTaskPackages(mAdminComponentName, packages);
            }catch (Exception e){
                Log.e(TAG,"mDPM.getPackageName()"+mAdminComponentName.getPackageName());
                Log.e(TAG,"mDPM.getPackageName()"+e.getMessage());
                Log.e(TAG,"asdasd"+e.getStackTrace().toString());
            }
            // get this app package name

            Log.d("PackageName", context.getPackageName());
            Log.d("isLock",""+myDevicePolicyManager.isLockTaskPermitted(context.getPackageName())) ;


            System.out.println("isLockTaskPermitted ");
            //context.startLockTask();

            // mDPM is the admin package, and allow the specified packages to lock task
            //myDevicePolicyManager.setLockTaskPackages(mDPM, packages);

        } else {
            //Toast.makeText(context,"Not owner", Toast.LENGTH_LONG).show();
        }







    }
*/
}
