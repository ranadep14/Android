package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.SocketConnectionTest;
import android.annotation.SuppressLint;
import android.content.Context;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import io.socket.client.IO;
import io.socket.client.Socket;
import okhttp3.OkHttpClient;
/**
 * This class containing functionality related login socket connection
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class LoginSocketConnectionTest {
    String url;
    Socket socket;
    IO.Options opts;
    SSLContext context=null;
    private static SSLContext sslContext;
    private static X509TrustManager x509TrustManager;
    private static TrustManager[] trustAllCerts;
    public boolean isSocketConnect(Socket msoket)
    {
        socket=msoket;
        boolean f=false;
        try {
            f= socket.connected();
            socket.emit("disconnect",socket);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return f;
    }
    public  Socket connect(String url, Socket msocket, Context mcotext)  {
        this.url=url;
        this.socket=msocket;
        getX509TrustManager();
        if (msocket == null) {
            String serverAddress = url;
            try {
                sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustAllCerts, null);
                String[] strArray = {"websocket"};
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .hostnameVerifier(getHostNameVerifier())
                        .sslSocketFactory(sslContext.getSocketFactory(),
                                x509TrustManager)
                        .build();
                IO.Options options = new IO.Options();
                options.callFactory = okHttpClient;
                options.webSocketFactory = okHttpClient;
                options.secure = true;
                options.path = url.contains("loginauth")?"/loginauth":"/sync";
                options.transports = strArray;
                options.forceNew = true;
                options.reconnection = true;
                options.reconnectionDelay = 2000;
                options.reconnectionDelayMax = 5000;
                options.timeout = 50000;
                msocket = IO.socket(serverAddress,options);
            } catch (Exception ex) {
                Logs.error("LoginSocketConnectionTest",""+ex.getMessage());
            }
            msocket.on(Socket.EVENT_CONNECT, new LoginSocketListener(Socket.EVENT_CONNECT,mcotext,msocket)) ;
            //disconnect
            msocket.on(Socket.EVENT_DISCONNECT, new LoginSocketListener(Socket.EVENT_DISCONNECT,mcotext,msocket)) ;
            //message msocket.on("connect_error",new LoginSocketListener("connect_error",mcotext,msocket));
            msocket.on("LoginResponce",new LoginSocketListener("LoginResponce",mcotext,msocket));
            msocket.on("LOGIN", new LoginSocketListener("LOGIN",mcotext,msocket) );
            msocket.on("error", new LoginSocketListener("error",mcotext,msocket) );
            msocket.on("authenticated", new LoginSocketListener("authenticated",mcotext,msocket) );
            msocket.on("serverToClientIDBIncSync", new LoginSocketListener("serverToClientIDBIncSync",mcotext,msocket) );
            msocket.on("ErrorFromServer", new LoginSocketListener("ErrorFromServer",mcotext,msocket) );
            msocket.on(Socket.EVENT_MESSAGE, new LoginSocketListener(Socket.EVENT_MESSAGE,mcotext,msocket)) ;
            //error
            msocket.on(Socket.EVENT_ERROR, new LoginSocketListener(Socket.EVENT_ERROR,mcotext,msocket) );
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
            ex.printStackTrace();
        }
    }
    private  HostnameVerifier getHostNameVerifier(){
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @SuppressLint("BadHostnameVerifier")
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        return hostnameVerifier;
    }
    private static void getX509TrustManager(){
        x509TrustManager = null;
        trustAllCerts= new TrustManager[]
                {
                        x509TrustManager = new X509TrustManager() {
                            @SuppressLint("TrustAllX509TrustManager")
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s)
                                    throws java.security.cert.CertificateException {
                            }
                            @SuppressLint("TrustAllX509TrustManager")
                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s)
                                    throws java.security.cert.CertificateException {
                            }
                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };
    }
}