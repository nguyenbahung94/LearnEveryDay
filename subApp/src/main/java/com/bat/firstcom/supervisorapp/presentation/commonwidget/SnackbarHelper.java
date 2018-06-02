package com.bat.firstcom.supervisorapp.presentation.commonwidget;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Tung Phan on 10-Aug-17.
 */

public class SnackbarHelper {

    public void show(View parentLayout, int message, Snackbar.Callback callback){
        Snackbar snackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
        snackbar.addCallback(callback);
        snackbar.show();
    }

    public void show( View parentLayout, int message){
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).show();
    }
}
