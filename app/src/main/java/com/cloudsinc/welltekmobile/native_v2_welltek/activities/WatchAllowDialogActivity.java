package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.SettingMainFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.services.MessageService;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import org.json.JSONObject;

import static com.google.android.gms.internal.zzagz.runOnUiThread;
public class WatchAllowDialogActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    GoogleApiClient googleClient;
    String msg="";
    String TAG=this.getClass().getSimpleName();
    Button customDialogCancel,customDialogOk;
    PrefManager spm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_allow_dialog);
        customDialogCancel=findViewById(R.id.customDialogCancel);
        customDialogOk=findViewById(R.id.customDialogOk);
        spm=new PrefManager(WatchAllowDialogActivity.this);
        googleClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        msg=getIntent().getStringExtra("message");

        customDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new SendToDataLayerThread("/my_path",""+ new JSONObject().put("ip_address", spm.getValue("ip_address")).put("flic_key",spm.getValue("flic_key"))).start();
                }
                catch (Exception ex)
                {
                    Log.e(TAG,"----"+ex.getMessage());
                }
            }
        });
        customDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WatchAllowDialogActivity.this.finish();
            }
        });
    }
    @Override
    public void onStop() {
        if (null != googleClient && googleClient.isConnected()) {
            googleClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }
    @Override
    public void onStart() {
        super.onStart();
        googleClient.connect();
    }
    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

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
                            WatchAllowDialogActivity.this.finish();
                            Toast.makeText(WatchAllowDialogActivity.this, "Application is open in" + node.getDisplayName(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else {
                    // Log an error
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(WatchAllowDialogActivity.this, "Check watch connectivty", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Log.v("myTag", "");
                }
            }
        }
    }
}
