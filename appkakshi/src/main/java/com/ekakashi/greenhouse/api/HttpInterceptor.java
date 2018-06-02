package com.ekakashi.greenhouse.api;

import android.text.TextUtils;
import android.util.Log;

import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.LoginResponse;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nquochuy on 4/23/2018.
 */

/**
 * Intercepts each request call:
 * Add authorization header automatically
 * If accessToken is expired, refresh new token automatically.
 */
public class HttpInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request.Builder builder = request.newBuilder();
        builder.header("Accept", "*/*");
        builder.header("Content-Type", "application/json");

        String authorization = Prefs.getInstance(App.getsInstance()).getToken();
        setAuthHeader(builder, authorization);

        request = builder.build();
        Response response = chain.proceed(request);

        if (response.code() == Utils.ErrorCode.ERROR_401) {

            String refreshToken = Prefs.getInstance(App.getsInstance()).getRefreshToken();
            if (TextUtils.isEmpty(refreshToken)) {
                return response;
            }
            synchronized (HttpInterceptor.class) {
                if (!refreshToken()) {
                    return response;
                } else {
                    setAuthHeader(builder, Prefs.getInstance(App.getsInstance()).getToken());
                    request = builder.build();
                    return chain.proceed(request);
                }
            }
        }

        return response;
    }

    /**
     * Sets authorization header for a request
     *
     * @param builder
     * @param authorization
     */
    private void setAuthHeader(Request.Builder builder, String authorization) {
        if (authorization != null) {
            builder.header("Authorization", authorization);
        }
    }

    /**
     * Refreshes token: Call "api/refresh" api to retrieve new accessToken
     *
     * @return true if new accessToken is retrieved successfully, otherwise return false
     */
    private boolean refreshToken() {
        String refreshToken = Prefs.getInstance(App.getsInstance()).getRefreshToken();

        try {
            retrofit2.Response<LoginResponse> retrofitLoginResponse = APIManager.getAPI().refreshToken("refresh_token", "ekakashi", refreshToken).execute();
            if (retrofitLoginResponse.isSuccessful()) {
                LoginResponse response = retrofitLoginResponse.body();
                if (response == null) {
                    return false;
                }
                //TODO
                String token = String.format("%s %s", response.tokenType, response.accessToken);
                Prefs.getInstance(App.getsInstance()).saveToken(token);
                Prefs.getInstance(App.getsInstance()).saveRefreshToken(response.refreshToken);
                Prefs.getInstance(App.getsInstance()).saveUserId(response.userLogin.id);

                Log.e("RefreshToken", "true");
                return true;
            }
            Log.e("RefreshToken", "false");
            return false;
        } catch (IOException e) {
            Log.e("RefreshToken", "Get refreshToken() failed: " + e.getLocalizedMessage());
            return false;
        }
    }
}
