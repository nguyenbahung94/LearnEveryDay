package com.ekakashi.greenhouse.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public final class ManageNetworkUsage {
    /**
     * TAG.
     */
    private static final String TAG = "ManageNetworkUsage";
    /**
     * Whether there is a Wi-Fi connection.
     */
    private boolean wifiConnected;
    /**
     * Whether there is a mobile connection.
     */
    private boolean mobileConnected;
    /**
     * Singleton.
     */
    private static ManageNetworkUsage uniqueInstance;
    /**
     * Callback interface.
     */
    private ManageNetworkInterface mNetworkInterface;
    /**
     * The BroadcastReceiver that tracks network connectivity changes.
     */
    private NetworkReceiver receiver;

    /**
     * To check network connection in service, Cannot use ManageNetworkUsage.isNetworkOnline().
     * Because updating wifiConnected = false and mobileConnected = false is very slow
     * while network was disconnected.
     * In OnCreate of the first activity, applicationContext is null.
     *
     * @param applicationContext : must be application context
     * @return true: network available, else not available
     */
    public static boolean isNetworkOnlineInBackgroundThread(Context applicationContext) {
        //init singleton instance
        isNetworkOnline(applicationContext);
        //Call the method to update status of network immediately
        uniqueInstance.updateConnectedFlags(applicationContext);
        return uniqueInstance.wifiConnected | uniqueInstance.mobileConnected;
    }

    /**
     * This method is used to check network.
     * In OnCreate of the first activity, applicationContext is null.
     * * @param applicationContext : must be application Context
     *
     * @return true: network available, else not available
     */
    public static boolean isNetworkOnline(Context applicationContext) {
        if (uniqueInstance == null) {
            synchronized (ManageNetworkUsage.class) {
                if (uniqueInstance == null) {
                    if (applicationContext == null || applicationContext.getApplicationContext() == null) {
                        Log.e(TAG, "Error in isNetworkOnline(). ApplicationContext is null. " +
                                "The app was released or you called the isNetworkOnline() method in onCreate of the first activity.");
                        return false;
                    }
                    uniqueInstance = new ManageNetworkUsage(applicationContext.getApplicationContext());
                }
            }
        }
        return uniqueInstance.wifiConnected | uniqueInstance.mobileConnected;
    }

    /**
     * Constructor : register NetworkReceiver to manage network status.
     *
     * @param applicationContext : application context
     */
    private ManageNetworkUsage(Context applicationContext) {
        // Register BroadcastReceiver to track connection changes.
        this.receiver = new NetworkReceiver();
        applicationContext.registerReceiver(this.receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        updateConnectedFlags(applicationContext);
        Log.i(TAG, "registered ManageNetworkUsage");
    }

    /**
     * Checks the network connection and sets the wifiConnected and
     * mobileConnected variables accordingly.
     *
     * @param applicationContext : application context
     */
    private void updateConnectedFlags(Context applicationContext) {
        NetworkInfo activeInfo = null;
        if (applicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) != null) {
            activeInfo = ((ConnectivityManager) applicationContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        }
        if (activeInfo != null && activeInfo.isConnected()) {
            this.wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            this.mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        } else {
            this.wifiConnected = false;
            this.mobileConnected = false;
        }
        Log.i(TAG, "updateConnectedFlags: wifiConnected = "
                + wifiConnected + ", mobileConnected = " + mobileConnected);
    }

    /**
     * unregister BroadcastReceiver which manage network. This method must be
     * called in onDestroy of activity which will exit app.
     * * @param applicationContext : must be application context.
     */
    public static synchronized void unRegisterReceiver(Context applicationContext) {
        if (uniqueInstance != null && applicationContext != null && applicationContext.getApplicationContext() != null) {
            if (uniqueInstance.receiver != null) {
                try {
                    applicationContext.getApplicationContext()
                            .unregisterReceiver(uniqueInstance.receiver);
                    Log.i(TAG, "ManageNetworkUsage.unRegisterReceiver()");
                } catch (Exception e) {
                    Log.e(TAG, "ManageNetworkUsage.unRegisterReceiver()", e);
                }
            }
            uniqueInstance.mNetworkInterface = null;
        }
        uniqueInstance = null;
    }

    public static synchronized void setNetworkInterface(Context appContext, ManageNetworkInterface networkInterface) {
        //Fixed: java.lang.NullPointerException
        isNetworkOnline(appContext);
        if (uniqueInstance.mNetworkInterface == null) {
            uniqueInstance.mNetworkInterface = networkInterface;
        }
    }

    /**
     * This BroadcastReceiver intercepts the
     * android.net.ConnectivityManager.CONNECTIVITY_ACTION, which indicates a
     * connection change. It checks whether the type is TYPE_WIFI. If it is, it
     * checks whether Wi-Fi is connected and sets the wifiConnected flag in the
     * main activity accordingly.
     */
    private static class NetworkReceiver extends BroadcastReceiver {

        @Override
        public final void onReceive(Context context, Intent intent) {
            //Call the method to update status of network immediately
            if (uniqueInstance == null) return;
            uniqueInstance.updateConnectedFlags(context);
            if (uniqueInstance.wifiConnected || uniqueInstance.mobileConnected) {
                if (uniqueInstance.mNetworkInterface != null) {
                    uniqueInstance.mNetworkInterface.networkOn();
                }
            } else {
                if (uniqueInstance.mNetworkInterface != null) {
                    uniqueInstance.mNetworkInterface.networkOff();
                }
            }
            Log.i(TAG, "NetworkReceiver: wifiConnected = " + uniqueInstance.wifiConnected
                    + "; mobileConnected = " + uniqueInstance.mobileConnected);
        }
    }
}
