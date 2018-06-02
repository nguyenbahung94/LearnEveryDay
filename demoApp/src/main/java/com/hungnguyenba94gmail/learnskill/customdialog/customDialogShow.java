package com.hungnguyenba94gmail.learnskill.customdialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by nbhung on 12/5/2017.
 */

public class customDialogShow {
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
}
