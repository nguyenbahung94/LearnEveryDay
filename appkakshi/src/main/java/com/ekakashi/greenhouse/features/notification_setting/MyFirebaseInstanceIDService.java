package com.ekakashi.greenhouse.features.notification_setting;

import android.util.Log;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.Prefs;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nbhung on 12/6/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Prefs.getInstance(getApplicationContext()).saveDeviceToken(refreshedToken);

        Log.e(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String deviceToken) {
        // TODO: Implement this method to send token to your app server.
        if (!Prefs.getInstance(getApplicationContext()).getToken().isEmpty()) {
            APIManager.sendRegistrationToServer( deviceToken, new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Prefs.getInstance(getApplicationContext()).saveSendDeviceTokenStatus(true);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Prefs.getInstance(getApplicationContext()).saveSendDeviceTokenStatus(false);
                }
            });
        }
    }
}
