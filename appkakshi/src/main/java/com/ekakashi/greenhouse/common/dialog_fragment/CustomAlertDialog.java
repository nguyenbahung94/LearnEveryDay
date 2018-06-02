package com.ekakashi.greenhouse.common.dialog_fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.features.device_list.YesOrNo;


/**
 * Created by nbhung on 12/4/2017.
 */

public class CustomAlertDialog {
    public static void customDialogShow(Context context, String message, final YesOrNo yesOrNo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning!");
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                yesOrNo.clickYes();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                yesOrNo.clickNo();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void dialogResetSensor(Context context, final YesOrNo yesOrNo) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") final View deleteDialogView = inflater.inflate(R.layout.custom_dialog, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
        deleteDialog.setView(deleteDialogView);
        ((TextView) deleteDialogView.findViewById(R.id.Title)).setText(R.string.reset_sensor);
        deleteDialogView.findViewById(R.id.llOne).setVisibility(View.VISIBLE);
        deleteDialogView.findViewById(R.id.btnOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                yesOrNo.clickYes();
            }
        });
        deleteDialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesOrNo.clickNo();
            }
        });

        deleteDialog.show();
    }

    public static void dialogReplaceSensor(Context context, final YesOrNo yesOrNo) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") final View deleteDialogView = inflater.inflate(R.layout.custom_dialog, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
        deleteDialog.setView(deleteDialogView);
        ((TextView) deleteDialogView.findViewById(R.id.Title)).setText(R.string.replace_sensor);
        deleteDialogView.findViewById(R.id.llTwo).setVisibility(View.VISIBLE);
        deleteDialogView.findViewById(R.id.btnOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                yesOrNo.clickYes();
            }
        });
        deleteDialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesOrNo.clickNo();
            }
        });

        deleteDialog.show();
    }


}
