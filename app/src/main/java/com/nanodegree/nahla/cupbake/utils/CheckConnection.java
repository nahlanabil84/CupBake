package com.nanodegree.nahla.cupbake.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckConnection {
    private static volatile CheckConnection checkConnection = new CheckConnection();

    //private constructor.
    private CheckConnection() {
    }

    public static CheckConnection getInstance() {
        return checkConnection;
    }


    // check networkConnection.
    public static boolean setCheckConnection(Context context) {
        //Check if connected to internet, output accordingly
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


}
