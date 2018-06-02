package com.example.nbhung.demotwitter.common;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.nbhung.demotwitter.App;
import com.example.nbhung.demotwitter.R;
import com.example.nbhung.demotwitter.presentation.base.BaseView;

public final class Utils {
    public static int getErrorMessageFrom(ErrorType errorType) {
        switch (errorType) {
            case ERROR_LOGIN:
                return R.string.error_when_login;
            case ERROR_EMAIL_EMPTY:
                return R.string.error_email_empty;
            case ERROR_PASSWORD_EMPTY:
                return R.string.error_password_empty;
            case ERROR_LENGTH_PASSWORD:
                return R.string.error_length_to_short;
            case ERROR_EMAIL_NOT_CONTAIN_A:
                return R.string.error_email_incorrect;

            default:
                return R.string.error_have_some_error;
        }
    }

    public static boolean isConnectToNetwork() {
        ConnectivityManager cm = (ConnectivityManager) App.getAppInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (cm != null) {
            networkInfo = cm.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void hideKeyboard(View view) {
        InputMethodManager inputMethodManager
                = (InputMethodManager) App.getAppInstance().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
