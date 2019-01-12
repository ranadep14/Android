package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.listeners;
import android.content.Context;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import org.json.JSONObject;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
/**
 * This thread for scann hub over network.
 *
 * @author Nikhil Vharamble
 * @version 2.0.0
 * @since 2018-01-09
 */
public class UdpConnectionListener extends Thread {
    DatagramSocket m_socket;
    Context mcontext;
    String msg="",msgMessage;
    Observable<String> observable;
    Observer<String> observer;
    JSONObject jsonObject;
    String TAG=UdpConnectionListener.this.getClass().getSimpleName();
    App globalClass;
    public UdpConnectionListener(DatagramSocket socket, Context context, String msgMessage) {
        this.m_socket = socket;
        this.msgMessage=msgMessage;
        mcontext=context;
        globalClass=(App)mcontext.getApplicationContext();
    }
    public void run() {
        //  getResponse();
         Logs.info(TAG+"_WhereIm","UDPConnectionListeer"+msgMessage);
        try {
            byte[] data = new byte[2048]; // investigate MSG_PEEK and MSG_TRUNC in java
            DatagramPacket packet = new DatagramPacket(data, data.length);
            packet.setLength(data.length);
            // reset packet length due to incomplete UDP Packet received
            try {
                this.m_socket.receive(packet);
            }
            catch (Exception ex){
                Logs.error(TAG+"_HubError", "Receive exception:" + ex.toString());
            }
            msg = new String(data, 0, packet.getLength(), "UTF-8")
                    .replace("'", "\'")
                    .replace("\r", "\\r")
                    .replace("\n", "\\n");
            jsonObject=new JSONObject(msg);
            Logs.info(TAG+"_WhereIm","UDPConnectionListeer"+jsonObject);
            App.setHubConnectionData(jsonObject);
            if (App.getHubsubcriber() != null) {
                observable = App.getHubsubcriber().getObservable();
                observer = App.getHubsubcriber().getObserver();
                App.setSubcription(observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer));
            }
            if (App.getCurrentSubcriber() != null) {
                observable = App.getCurrentSubcriber().getObservable();
                observer = App.getCurrentSubcriber().getObserver();
                App.setSubcription(Observable.just("hub_got").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer));
            }
        } catch (Exception e) {
            Logs.error(TAG, "Receive exception:" + e.getMessage());
            return;
        }
    }
    public void getResponse()
    {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (App.getCurrentSubcriber() != null) {
            observable = App.getCurrentSubcriber().getObservable();
            observer = App.getCurrentSubcriber().getObserver();
            App.setSubcription(observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer));
        }
    }
}