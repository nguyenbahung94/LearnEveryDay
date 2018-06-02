package com.example.nbhung.projectdirection.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public final class NetWorkUtils {
    private NetWorkUtils() {

    }

    public static boolean isNetWorkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo acNetworkInfo = cm.getActiveNetworkInfo();
        return acNetworkInfo != null && acNetworkInfo.isConnectedOrConnecting();
    }
}
