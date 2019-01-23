package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.SocketConnectionTest;
import android.annotation.SuppressLint;
import android.content.Context;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.listeners.SocketListeners;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import io.socket.client.IO;
import io.socket.client.Socket;
/**
 * The SocketConnectionTest is for socket connection with given address
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class SocketConnectionTest {
    String url;
    Socket socket;
    IO.Options opts;
    SSLContext context=null;
    String TAG=SocketConnectionTest.this.getClass().getSimpleName();
    public boolean isSocketConnect(Socket msoket)
    {
        socket=msoket;
        boolean f=false;
        try {
            f= socket.connected();
            socket.emit("disconnect",socket);
        }
        catch (Exception ex){
            Logs.error(TAG,""+ex.getMessage());
        }
        return f;
    }
    /** this method is for get connected socket
     *
     * @param  url is ip address
     *  @param msocket is a socket
     *  @param mcotext is a context
     *  @return Socket
     */
    public Socket connect(String url, Socket msocket, Context mcotext)  {
        this.url=url;
        this.socket=msocket;
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[] {};
                    }
                    @SuppressLint("TrustAllX509TrustManager")
                    public void checkClientTrusted(X509Certificate[] chain,
                                                   String authType) throws CertificateException {
                    }
                    @SuppressLint("TrustAllX509TrustManager")
                    public void checkServerTrusted(X509Certificate[] chain,
                                                   String authType) throws CertificateException {
                    }
                } };
        if (msocket == null) {
            String serverAddress = url;
            try {
                Logs.error(TAG,""+serverAddress);
                IO.Options options = new IO.Options();
               options.query="token=" + new PrefManager(mcotext).getValue("jwt_token")+"&userName="+new PrefManager(mcotext).getValue("User_Id") ;
              //  options.query="token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJhbmRyb2lkLmRldkBkYXJ3aW4uY29tIiwidXNlcm5hbWUiOiJhbmRyb2lkLmRldkBkYXJ3aW4uY29tIiwidXNlckVtYWlsIjoiYW5kcm9pZC5kZXZAZGFyd2luLmNvbSIsInVzZXJVSUQiOiJhbmRyb2lkLmRldkBkYXJ3aW4uY29tIiwidXNlck5hbWUiOiJhbmRyb2lkLmRldkBkYXJ3aW4uY29tIiwiZmlyc3ROYW1lIjoiSmFpZCIsImxhc3ROYW1lIjoiU2hhaWtoIiwic3RhdHVzIjoiMSIsInByb2ZpbGVfZGF0YSI6eyJkZXNpZ25hdGlvbiI6ImMiLCJmaXJzdE5hbWUiOiJKYWlkIiwiY21sSWQiOiJhbmRyb2lkLmRldkBkYXJ3aW4uY29tI1NURyNDbG91emVyI1BlcnNvbmEjV0tTI1dvcmsiLCJsYXN0TmFtZSI6IlNoYWlraCIsInN1YkNhdGVnb3J5IjoiV0tTI1dvcmsiLCJrZXlUeXBlIjoiU1RHIiwiaW1nUGF0aCI6Ii9mcy9pbWFnZXMvY29udGFjdF9pbWFnZXMvVG9ueV9Sb2JlcnRzLmpwZyIsImFkZHJlc3MiOnsiYWRkcmVzc0xpbmUiOiJLb3RocnVkIiwiY2l0eSI6IlB1bmUiLCJzdGF0ZSI6Ik1IIiwiY291bnRyeSI6IkluZGlhIn19LCJDTUxfTE9DQVRJT04iOiIiLCJDTUxfQUREUkVTU19MSU5FMiI6IiIsIkNNTF9DT05UQUNUX05PIjoiIiwiUkVDT1ZFUllfTUFJTCI6InFhLm1zdG9ybUBnbWFpbC5jb20iLCJPUkdfSUQiOiJhZG1pbkBtc3Rvcm0uY29tIzEyMzEyMzEyMzEyMzEjT1JHOjFfMSIsImlhdCI6MTUzMDUwMzM0MH0.GzNfgRfqEBHYCkZMjNiRnFNluapuOTLia6_gy2ElE7Y7uNNP2Py55xnkJtlbXbhS2iGFORICiQqQYn7uj8-pV6ay5-IQQs_qlsNYB4l8Bu_R8X6qAZwJ_vR9W-99tL25uk4hekUGynxiN3MuUgqWcKWWzJBZDF1eOPOzb3THlAhJpwGm5ehFqQWEF76f_Kr0mumkxMfARUyDsbYGGGDWkUX97yKnhv7xDL38CRa7lMpwmuHXakW7aIqDzrqnsGTdMWVy9quqtldou9kXXG0nWeou9WzCTQeDMs3txWFtdkssNl-AotdOokPDCWcoIQJ3yZIaTwuNToYbEZdr9fwGCg&userName=android.dev@darwin.com" ;
                msocket = IO.socket(serverAddress,options);
            } catch (Exception ex) {
                Logs.error(TAG,""+ex.getMessage());
            }
            assert msocket != null;
            msocket.on(Socket.EVENT_CONNECT, new SocketListeners(Socket.EVENT_CONNECT,mcotext,msocket)) ;
            //disconnect
            msocket.on(Socket.EVENT_DISCONNECT, new SocketListeners(Socket.EVENT_DISCONNECT,mcotext,msocket)) ;
            //message
            msocket.on(Socket.EVENT_MESSAGE, new SocketListeners(Socket.EVENT_MESSAGE,mcotext,msocket)) ;
            msocket.on(Socket.EVENT_PING, new SocketListeners(Socket.EVENT_MESSAGE,mcotext,msocket)) ;
            msocket.on(Socket.EVENT_PONG, new SocketListeners(Socket.EVENT_MESSAGE,mcotext,msocket)) ;
            msocket.on(Socket.EVENT_RECONNECT, new SocketListeners(Socket.EVENT_MESSAGE,mcotext,msocket)) ;
            //error
            msocket.on("error", new SocketListeners("error",mcotext,msocket) );
            msocket.on("authentication", new SocketListeners("authentication",mcotext,msocket) );
            msocket.on("action", new SocketListeners("action",mcotext,msocket) );
            msocket.on("connect_error",new SocketListeners("connect_error",mcotext,msocket));
            msocket.on("LoginResponce",new SocketListeners("LoginResponce",mcotext,msocket));
            msocket.on("LoginResponce",new LoginSocketListener("LoginResponce",mcotext,msocket));
            msocket.on("LOGIN", new LoginSocketListener("LOGIN",mcotext,msocket) );
            msocket.on("authenticated", new LoginSocketListener("authenticated",mcotext,msocket) );
            msocket.on("serverToClientIDBIncSync", new LoginSocketListener("serverToClientIDBIncSync",mcotext,msocket) );
            msocket.on("ErrorFromServer", new LoginSocketListener("ErrorFromServer",mcotext,msocket) );
            msocket.connect();
        } else if (!msocket.connected()) {
            msocket.connect();
        }
        return msocket;
    }
    public void send(String msg,Socket msocket)
    {
        socket=msocket;
        try {
            socket.emit("message",msg);
        }
        catch (Exception ex){
            Logs.error(TAG,""+ex.getMessage());
        }
    }
}