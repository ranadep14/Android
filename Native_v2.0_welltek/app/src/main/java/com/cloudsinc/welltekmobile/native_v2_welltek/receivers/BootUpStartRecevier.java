package com.cloudsinc.welltekmobile.native_v2_welltek.receivers;
/**
 * Created by developers on 31/8/18.
 */
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.LoginActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.SocketConnectionTest.SocketConnectionTest;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.NotificationSettingFragment.emitNotification;
/**
 * This broadcast receiver trigger on device boot.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class BootUpStartRecevier extends BroadcastReceiver {
    String TAG=""+this.getClass().getSimpleName();
    PrefManager spm;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    @SuppressLint({"NewApi", "UnsafeProtectedBroadcastReceiver"})
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        App.setMain_activity_subcriber(null);
        initialize(context);
        connectToHub(context);
        setSubcriber();
        if((Build.MODEL.equals("QUAD-CORE R40 sc3826r") && Build.MANUFACTURER.equals("Allwinner"))||(Build.MODEL.equals("Q8919") && Build.MANUFACTURER.equals("SIBO")))
        {
            System.out.println("BootUpStartRecevierllllllllllllllllllllllllll"+getClass().getSimpleName());
            Intent myIntent = new Intent(context, LoginActivity.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(myIntent);
        }
    }
    public void  initialize(Context mcontext) {
        try {
            spm = new PrefManager(mcontext);
        } catch (Exception ex) {
            Logs.error(TAG, "error........." + ex.getMessage());
        }
    }
    public void connectToHub(Context mcontext)
    {
        if (App.getSocket() == null) {
            final SocketConnectionTest sct = new SocketConnectionTest();
            Logs.info(TAG + "_Ip_address", "" + spm.getValue("ip_address"));
            if(!spm.getValue("ip_address").equals("No Record")) {
                sct.connect("http://" + spm.getValue("ip_address") + "/action", App.getSocket(), mcontext.getApplicationContext());
            }
            // sct.connect("http://192.168.1.14/action", App.getSocket(), getApplicationContext());
        } else {
            Observable.just("hub_connected").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(myObserver);
        }
    }
    private void setSubcriber() {
        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext(""+App.getRoomData());
                        sub.onCompleted();
                    }
                }
        );
        myObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(String string) {
                Logs.info(TAG + "WhereIM", "" + string);
                if(string.equals("hub_connected"))
                {
                    emitNotification("Water");
                    emitNotification("Air");
                    emitNotification("Dawn");
                    emitNotification("Sleep");
                }
            }
        } ;
        Subcriber s = new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setMain_activity_subcriber(s);
    }
}