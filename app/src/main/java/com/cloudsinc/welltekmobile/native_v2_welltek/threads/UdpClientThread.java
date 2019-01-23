package com.cloudsinc.welltekmobile.native_v2_welltek.threads;
import android.content.Context;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.listeners.UdpConnectionListener;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
/**
 * This thread is for udp scanning
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class UdpClientThread extends Thread{
    String dstAddress;
    int dstPort;
    private boolean running;
    Context mcontext;
    DatagramSocket socket;
    InetAddress address;
    String TAG=UdpClientThread.this.getClass().getName();
    String msgMessage;
    public UdpClientThread(Context context, String msgMessage) {
        super();
        Logs.info(""+this.getClass().getSimpleName(),""+msgMessage);
        mcontext=context;
        this.msgMessage=msgMessage;
    }
    private class SocketConfig {
        NetworkInterface networkInterface = null;
        int port;
    }
    public NetworkInterface getActiveWifiInterface() throws SocketException {
        NetworkInterface activeInterface = null;
        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();){
            NetworkInterface intf = en.nextElement();
            if (intf.isUp() && intf.supportsMulticast() && intf.getInterfaceAddresses().size() > 0 && !intf.isLoopback() && !intf.isVirtual() && !intf.isPointToPoint()){
                if (activeInterface == null){
                    activeInterface = intf;
                }else{
                    if (!activeInterface.getName().contains("wlan") && !activeInterface.getName().contains("ap")){
                        activeInterface = intf;
                    }
                }
            }
        }
        if (activeInterface != null) {
             Logs.info(TAG, "Using Network Interface: " + activeInterface.getName());
        } else {
             Logs.info(TAG, "No active Network Interface found !");
        }
        return activeInterface;
    }
    @Override
    public void run() {
        running = true;
        SocketConfig config = new SocketConfig();
        config.port = 5984;
        try {
            MulticastSocket mcSocket = new MulticastSocket(null);
            config.networkInterface = getActiveWifiInterface();
            if (config.networkInterface == null) {
            }
            mcSocket.setNetworkInterface(config.networkInterface);
            socket = mcSocket;
            byte[] bytes = msgMessage.getBytes("UTF-8");
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length,InetAddress.getByName("255.255.255.255"),5984);
            socket.send(packet);
            UdpConnectionListener udpConnectionListener =new UdpConnectionListener(socket,mcontext,msgMessage);
            udpConnectionListener.run();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket != null){
                socket.close();
            }
        }
    }
}