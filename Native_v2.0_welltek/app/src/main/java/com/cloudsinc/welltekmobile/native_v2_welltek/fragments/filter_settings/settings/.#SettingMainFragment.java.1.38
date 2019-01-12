package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.CustomExpandableListAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule.RemoveNotification;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.NotifyService;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ExpandableListDataPump;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.LogoutFunctionality;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity.bottom_menu_bar;
import static com.google.android.gms.internal.zzagz.runOnUiThread;
/**
 * The SettingMainFragment is for contain different setting componente like profile,device
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class SettingMainFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    String ns = Context.NOTIFICATION_SERVICE;
    public static SettingMainFragment newInstance() {
        return new SettingMainFragment();
    }
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    /************/
    /*** */
    TextView txt_title;
    private View view;
    private Context mcontext;
    private PrefManager spm;
    private PrefManager pref;
    @BindView(R.id.txt_user_name)TextView txt_user_name;
    @BindView(R.id.profile_image)CircleImageView profile_image;
    String TAG=SettingMainFragment.this.getClass().getName();
    GoogleApiClient googleClient;
    protected Handler myHandler;
    private Tracker mTracker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting,container, false);
        ButterKnife.bind(this,view);
        mcontext=view.getContext();
        if (!checkConfiguration()) {
            Logs.info(TAG,"Error in SettingFragment Tracker");
        }
        App application = (App)getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        sendScreenImageName();
        bottom_menu_bar.setVisibility(View.GONE);
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/SF-UI-Text-Regular.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();
        FontUtility.setAppFont(mContainer, mFont);
        txt_title= view.findViewById(R.id.txt_fragment_title);
        txt_title.setText("Settings");

        googleClient = new GoogleApiClient.Builder(mcontext)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        myHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Bundle stuff = msg.getData();
                Toast.makeText(mcontext, "------"+stuff.getString("messageText"), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        if(App.getSocket()!=null) {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_rooms", new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_zones", new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_provisioned",new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_network_info", new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_nearby_wifis", new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_info",new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_hvac_zone_provisioned", new JSONObject()));
        }
        pref=new PrefManager(mcontext);
         Logs.info(TAG+"_Profile_image",""+pref.getValue("User_Prof_Image"));
         Logs.info(TAG+"_Old_Profile_image",""+pref.getValue("Old_Profile_image"));
        Glide.with(mcontext).load(pref.getValue("User_Prof_Image"))
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_user_logo)
                .override(300,300)
                .dontAnimate()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profile_image);
        spm=new PrefManager(mcontext);
        txt_user_name.setText(new PrefManager(mcontext).getValue("User_Name"));
        expandableListView = view.findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<>();
        expandableListTitle.add("Home Settings");
        expandableListTitle.add("Device Settings");
        expandableListTitle.add("Notification Settings");
        expandableListTitle.add("Profile Settings");
        expandableListTitle.add("About");
        expandableListTitle.add("Logout");
        //expandableListTitle.add("Open Wearable App");
        expandableListAdapter = new CustomExpandableListAdapter(getActivity(), expandableListTitle, null);
        expandableListView.setAdapter(expandableListAdapter);
        int width = expandableListView.getWidth();
        expandableListView.setIndicatorBounds(width-GetDipsFromPixel(35), width-GetDipsFromPixel(5));
        expandableListView.setIndicatorBounds(width-GetDipsFromPixel(5),width-GetDipsFromPixel(5));
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //    expandableListView.setGroupIndicator(getActivity().getResources().getDrawable(R.drawable.down));
                if(groupPosition==0)
                {
                   if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingMainFragment.this,R.id.frm_filter_main_container,SettingHome.newInstance());
                    else  ReplaceFragment.replaceFragment(SettingMainFragment.this,R.id.frm_main_container,SettingHome.newInstance());
                }
                if(groupPosition==1)
                {
                    //App.clearAppJSon();
                    if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingMainFragment.this,R.id.frm_filter_main_container,DeviceSettingFragment.newInstance());
                    else  ReplaceFragment.replaceFragment(SettingMainFragment.this,R.id.frm_main_container,DeviceSettingFragment.newInstance());
                }
                if(groupPosition==2)
                {
                    if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingMainFragment.this,R.id.frm_filter_main_container,NotificationSettingFragment.newInstance());
                    else  ReplaceFragment.replaceFragment(SettingMainFragment.this,R.id.frm_main_container,NotificationSettingFragment.newInstance());
                }
                if(groupPosition==3)
                {
                    if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingMainFragment.this,R.id.frm_filter_main_container,ProfileSettingFragment.newInstance());
                    else  ReplaceFragment.replaceFragment(SettingMainFragment.this,R.id.frm_main_container,ProfileSettingFragment.newInstance());
                    // ReplaceFragment.replaceFragment(mcontext, ChangePassword.newInstance());
                }
                /*******Dawn Notification*******/
             /*   if(groupPosition==4) {
                    if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingMainFragment.this,R.id.frm_filter_main_container,DawnNotificationFragment.newInstance());
                    else  ReplaceFragment.replaceFragment(SettingMainFragment.this,R.id.frm_main_container,DawnNotificationFragment.newInstance());
                }


*/
                   if(groupPosition==4)
                   {
                       if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingMainFragment.this,R.id.frm_filter_main_container,SettingAbout.newInstance());
                       else  ReplaceFragment.replaceFragment(SettingMainFragment.this,R.id.frm_main_container,SettingAbout.newInstance());
                   }
                    if(groupPosition==5)
                {
                    final AlertDialog dialog = new AlertDialog.Builder(mcontext)
                            .setView(R.layout.custom_dialog)
                            .create();
                    View v = dialog.getWindow().getDecorView();
                    v.setBackgroundResource(android.R.color.transparent);
                    dialog.show();
                    TextView msgt= dialog.findViewById(R.id.title);
                    LinearLayout lin_main= dialog.findViewById(R.id.lin_main);
                    lin_main.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            UserInteractionSleep.userInteract(mcontext);
                            return false;
                        }
                    });
                    msgt.setText("Logout");
                    TextView msg= dialog.findViewById(R.id.msg);
                    msg.setText("Are you sure you want to log out?");
                    Button diagButtonCancel = dialog.findViewById(R.id.customDialogCancel);
                    diagButtonCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    Button diagButtonOK = dialog.findViewById(R.id.customDialogOk);
                    diagButtonOK.setText("OK");
                    diagButtonOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            new LogoutFunctionality().logoutAction(getActivity());
                        }
                    });

                }

                if(groupPosition==6)
                {
                    new SendToDataLayerThread("/my_path", spm.getValue("ip_address")).start();
                }
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
              /*  //Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();
*/
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getActivity(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return true;
            }
        });
/////
        return view;
    }

    private void deleteSleepNotification() {
       RemoveNotification.removeAllSleepNotification(getActivity());

    }
    private void deleteAllDawnSleepNotification()
    {
        Intent myIntent = new Intent(mcontext, NotifyService.class);
        PendingIntent.getBroadcast(mcontext, 0, myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT).cancel();
    }
    private void deleteDawnNotification() {

        RemoveNotification.removeAllDawnNotification(getActivity());
    }

    @OnClick(R.id.img_back)
    public void img_back()
    {
        MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
        /*if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingMainFragment.this, R.id.frm_filter_main_container,SettingMainFragment.newInstance());
        else ReplaceFragment.replaceFragment(SettingMainFragment.this, R.id.frm_main_container, HomeHeathByZoneFragment.newInstance());*/
    }
    public int GetDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }
    @Override
    public void onResume() {
        super.onResume();
       if(!App.isOrientationFlag()) ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        if(!App.isOrientationFlag())  ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        if (null != googleClient && googleClient.isConnected()) {
            googleClient.disconnect();
        }
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        expandableListView.removeAllViewsInLayout();
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    /*class NewThread extends Thread {
        String path;
        String message;

        NewThread(String p, String m) {
            path = p;
            message = m;
        }


        public void run() {

            Task<List<Node>> wearableList =
                    Wearable.getNodeClient(mcontext.getApplicationContext()).getConnectedNodes();
            try {

                List<Node> nodes = Tasks.await(wearableList);
                for (Node node : nodes) {
                    Task<Integer> sendMessageTask =
                            Wearable.getMessageClient(getActivity()).sendMessage(node.getId(), path,message.getBytes() );

                    try {

                        Integer result = Tasks.await(sendMessageTask);
                      //  sendmessage("I just sent the wearable a message " + sentMessageNumber++);

                    } catch (ExecutionException exception) {

                        //TO DO: Handle the exception//


                    } catch (InterruptedException exception) {

                    }

                }

            } catch (ExecutionException exception) {

                //TO DO: Handle the exception//

            } catch (InterruptedException exception) {

                //TO DO: Handle the exception//
            }

        }
    }*/

    @Override
    public void onStart() {
        super.onStart();
        googleClient.connect();
    }

    // Send a message when the data layer connection is successful.
    @Override
    public void onConnected(Bundle connectionHint) {
        String message = "Hello wearable\n Via the data layer";
        //Requires a new thread to avoid blocking the UI
      //  new SendToDataLayerThread("/my_path", message).start();
    }

    // Disconnect from the data layer when the Activity stops


    // Placeholders for required connection callbacks
    @Override
    public void onConnectionSuspended(int cause) { }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) { }

    class SendToDataLayerThread extends Thread {
        String path;
        String message;

        // Constructor to send a message to the data layer
        SendToDataLayerThread(String p, String msg) {
            path = p;
            message = msg;
        }

        public void run() {
            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(googleClient).await();
            for (final Node node : nodes.getNodes()) {
                MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(googleClient, node.getId(), path, message.getBytes()).await();
                if (result.getStatus().isSuccess()) {
                    Log.v("myTag", "Message: {" + message + "} sent to: " + node.getDisplayName());
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(mcontext, "Application is open in" + node.getDisplayName(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    // Log an error
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(mcontext, "Check watch connectivty", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Log.v("myTag", "");
                }
            }
        }
    }

    private void sendScreenImageName() {
        String name = "Setting Menu Fragment";
        String username=new PrefManager(mcontext).getValue("User_Id");
        // [START screen_view_hit]
        Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName(""+username+"~" + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
    }

    private boolean checkConfiguration() {
        XmlResourceParser parser = getResources().getXml(R.xml.global_tracker);

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
            Log.w(TAG, "checkConfiguration", e);
            return false;
        }

        return true;
    }
}


