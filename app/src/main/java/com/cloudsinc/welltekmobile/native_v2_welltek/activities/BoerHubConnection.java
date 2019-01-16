package com.cloudsinc.welltekmobile.native_v2_welltek.activities;


/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 31/5/17.
 * @purpose  - Hub Connection process retruns ip adsress
 **/

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.CustomDialogShow;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.threads.UdpClientThread;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ScreenOrientation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
public class BoerHubConnection extends AppCompatActivity  {




    private String str_serial;
    private PrefManager spm;


    private Observable<String> mobservable;
    private Observer<String> myObserver;

    @BindView(R.id.spn_hub_msg)
    Spinner spn_hub_msg;

 //   String[] str_serial_ids={"Welltek_00000009","IN_00000001"};
    ArrayList<String> HubList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boer_activity_hub_connection);
        ScreenOrientation.setOrientation(this);
        spm = new PrefManager(this);
        App.setHubConnectionData(null);
        App.setLoginData(null);
        Log.e("WhereIM", "BoerHubConnection");
        App.setCurrentSubcriber(null);
        ButterKnife.bind(this);
        sendData("SOUL");

        spn_hub_msg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_serial = HubList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setSubcriber();



    }



    @OnClick(R.id.btn_hub_connect)
    public void btn_hub_connect()
    {
        if (haveNetworkConnection()) {

            Log.e("serial_id",""+str_serial);
            setTimeOut();
            UdpClientThread udpClientThread = new UdpClientThread(BoerHubConnection.this, str_serial);
            udpClientThread.start();

        } else {
            CustomDialogShow.dispDialogWithOK(BoerHubConnection.this, "Please check network...");
        }
    }


    public void onBackPressed() {


    }



    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    private void setSubcriber() {


        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {

                        sub.onNext("");
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sub.onCompleted();
                    }
                }
        );





        myObserver = new Observer<String>() {

            @Override
            public void onCompleted() {
                try {
                    JSONObject hubjson = App.getHubConnectionData();
                    Log.e("HubData", "" + hubjson);

                    if (hubjson != null) {
                        if (str_serial.equals(hubjson.getString("serial"))) {
                            String ipaddress = hubjson.getString("address");
                            spm = new PrefManager(BoerHubConnection.this);
                            spm.setValue("state_serial", "1");
                            spm.setValue("ip_address", ipaddress);
                            spm.setValue("hub_serial", str_serial);

                            CustomDialogShow.stopProgressDialog();
                            BoerHubConnection.this.finish();
                            Intent i = new Intent(BoerHubConnection.this, MainActivity.class);
                            startActivity(i);

                        }
                    } else {
                        CustomDialogShow.stopProgressDialog();
                        CustomDialogShow.dispDialogWithOK(BoerHubConnection.this, "Wrong Serial......");
                    }
                } catch (Exception ex) {
                    Log.e("BoerHubConnectionError", ex.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("RXJAVA", "You should go check the sensor,  dude");
            }
            @Override
            public void onNext(String string) {

                Log.e("WHERIM","INPROGRESSSHOW");
                CustomDialogShow.startProgressDialog(BoerHubConnection.this);
            }
        };
        Subcriber s = new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setHubsubcriber(s);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Runtime.getRuntime().gc();
    }

    private void sendmail() {

        File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "logcat.txt");// new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
        Uri path = Uri.fromFile(filelocation);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
// set the type to 'email'
        emailIntent.setType("vnd.android.cursor.dir/email");
        String to[] = {"jaid.shaikh@nciportal.com"};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
// the attachment
        emailIntent.putExtra(Intent.EXTRA_STREAM, path);
// the mail subject
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Error log");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));

    }


    public void setTimeOut()
    {

        mobservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(myObserver);

    }
    private void sendData(final String message) {
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            String strData;

            @Override
            public void run() {
                DatagramSocket ds = null;
                try {
                    ds = new DatagramSocket();
                    InetAddress serverAddr = InetAddress.getByName("255.255.255.255");
                    DatagramPacket dp;
                    dp = new DatagramPacket(message.getBytes(), message.length(), serverAddr, 5984);
                    ds.send(dp);
                    byte[] lMsg = new byte[1000];
                    dp = new DatagramPacket(lMsg, lMsg.length);
                    ds.receive(dp);
                    strData = new String(lMsg, 0, dp.getLength());
                    Log.d("RESPONSE is",strData);

                } catch (Exception e) {
                    e.printStackTrace();
               } finally {
                    if (ds != null) {
                        ds.close();
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //String str=txtDisplayList.getText().toString();
                        //if (strData.trim().length() != 0){
                        HubList.clear();
                        if (strData.length() != 0){
                            Log.d("StringData",strData);
                            //txtDisplayList.setText( str+"\nFrom Server : " + strData);
                        try {
                            JSONObject jsonObject = new JSONObject(strData);
                            if (strData.contains("{"))
                             HubList.add(jsonObject.getString("serial"));

                        /*    ArrayAdapter aa = new ArrayAdapter(this,R.layout.custom_hub_spinner_item,str_serial_ids);
                            aa.setDropDownViewResource(R.layout.custom_hub_spinner_item);*/
                         ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BoerHubConnection.this, R.layout.custom_hub_spinner_item, HubList);
                            dataAdapter.setDropDownViewResource(R.layout.custom_hub_spinner_item);
                            spn_hub_msg.setAdapter(dataAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("StringData_Error",""+ e);
                        }


                            spn_hub_msg.setVisibility(View.VISIBLE);
                        }
                        else {

                            //Toast.makeText(BoerHubConnection.this, "No Hub id found..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        thread.start();
    }
}