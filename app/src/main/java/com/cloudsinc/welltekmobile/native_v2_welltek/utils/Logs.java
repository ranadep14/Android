package com.cloudsinc.welltekmobile.native_v2_welltek.utils;
import android.util.Log;
/**
 * This class containing functionality related to display different types of log
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class Logs {
    /**
     * this method is for print error
     * @param requestType is for Tag
     * @param actualMsg is for actual message
     */
    public static void error( String requestType, String actualMsg){
        Log.e(requestType, actualMsg);
    }
    /*
     * this method is for print debug
     * @param requestType is for Tag
     * @param actualMsg is for actual message
     */
    public static void debug( String requestType, String actualMsg){
        Log.d(requestType, actualMsg);
    }
    /*
     * this method is for print info
     * @param requestType is for Tag
     * @param actualMsg is for actual message
     */
    public static void info( String requestType, String actualMsg){
        Log.i(requestType, actualMsg);
    }
    /*
     * this method is for print waring
     * @param requestType is for Tag
     * @param actualMsg is for actual message
     */
    public static void waring( String requestType, String actualMsg){
        Log.w(requestType, actualMsg);
    }
}
