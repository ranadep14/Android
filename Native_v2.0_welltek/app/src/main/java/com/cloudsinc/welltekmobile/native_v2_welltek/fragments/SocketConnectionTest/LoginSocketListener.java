package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.SocketConnectionTest;
import android.content.Context;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import org.json.JSONException;
import org.json.JSONObject;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
/**
 * This class containing functionality related litener for login socket
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class LoginSocketListener implements Emitter.Listener {
    Socket socket;
    App App;
    String action = "";
    Context mcotext;
    Socket msocket;
    Observable<String> observable;
    Observer<String> observer;
    JSONObject userjson;
    PrefManager spm;
    String TAG=LoginSocketListener.this.getClass().getSimpleName();
    public LoginSocketListener()
    {
    }
    public interface LoginCallbackClass{
        void callbackReturn();
    }
    LoginCallbackClass myCallbackClass;
    public void registerCallback(LoginCallbackClass callbackClass){
        myCallbackClass = callbackClass;
    }
    public LoginSocketListener(String action, Context mcontext, Socket msocket) {
        App = (App) mcontext.getApplicationContext();
        this.action = action;
        this.mcotext = mcontext;
        this.msocket = msocket;
        spm=new PrefManager(mcontext);
    }
    @Override
    public void call(Object... args) {
         Logs.info(TAG+"_action_string",action);
        switch (action) {
            case "LoginResponce":
                 Logs.info(TAG+"_LoginData",""+ args[0]);
                com.cloudsinc.welltekmobile.native_v2_welltek.application.App.setLoginData((JSONObject)args[0]);
                callUI("LoginResponce");
                break;
            case "error":
                 Logs.info(TAG+"_LoginCall", "Error"+args[0]);
                callUI("error");
                break;
            case "ErrorFromServer":
                 Logs.info(TAG+"_LoginCall", "Error"+args[0]);
                try {
                    com.cloudsinc.welltekmobile.native_v2_welltek.application.App.setChangePassJson(new JSONObject(""+args[0]));
                } catch (JSONException e) {
                     Logs.error(TAG+"_ChangePassordError",""+e.getMessage());
                }
                callUI("serverToClientIDBIncSync");
                break;
            case "serverToClientIDBIncSync":
                 Logs.info(TAG+"_CHangePassJson",""+ args[0]);
                try {
                    com.cloudsinc.welltekmobile.native_v2_welltek.application.App.setChangePassJson(new JSONObject(""+args[0]));
                } catch (JSONException e) {
                     Logs.error(TAG+"_ChangePassordError",""+e.getMessage());
                }
                callUI("serverToClientIDBIncSync");
                break;
            case "authenticated":
                 Logs.info(TAG+"_authenticated", "authontication");
                com.cloudsinc.welltekmobile.native_v2_welltek.application.App.setAuthanticate(true);
                callUI("authenticated");
                break;
            case Socket.EVENT_CONNECT:
                Logs.info(TAG+"_LoginCall", "connect");
                com.cloudsinc.welltekmobile.native_v2_welltek.application.App.setClouzerConnect(true);
                if (com.cloudsinc.welltekmobile.native_v2_welltek.application.App.getLoginSocket()==null) {
                    com.cloudsinc.welltekmobile.native_v2_welltek.application.App.setLoginSocket(msocket);
                }
                callUI("delos_connected");
                break;
            case Socket.EVENT_DISCONNECT:
                 Logs.info(TAG+"_LoginCall Disconnect ", "disconnect"+args[0]);
                break;
            case "connect_error":
                 Logs.info(TAG+"_LoginCall", "connect_error......."+args[0]);
                break;
            case Socket.EVENT_MESSAGE:
                 Logs.info(TAG+"_LoginCall", "message"+args[0]);
                break;
            default:
                 Logs.info(TAG+"_LoginCall", "no_action");
                break;
        }
    }
    public void callUI(String str_response)
    {
        if (com.cloudsinc.welltekmobile.native_v2_welltek.application.App.getCurrentSubcriber() != null) {
            observable = com.cloudsinc.welltekmobile.native_v2_welltek.application.App.getCurrentSubcriber().getObservable();
            observer = com.cloudsinc.welltekmobile.native_v2_welltek.application.App.getCurrentSubcriber().getObserver();
            com.cloudsinc.welltekmobile.native_v2_welltek.application.App.setSubcription(Observable.just(""+str_response).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer));
        }
        if (com.cloudsinc.welltekmobile.native_v2_welltek.application.App.getMain_activity_subcriber() != null) {
            observable = com.cloudsinc.welltekmobile.native_v2_welltek.application.App.getMain_activity_subcriber().getObservable();
            observer = com.cloudsinc.welltekmobile.native_v2_welltek.application.App.getMain_activity_subcriber().getObserver();
            com.cloudsinc.welltekmobile.native_v2_welltek.application.App.setSubcription(Observable.just(str_response).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer));
        }
    }
}