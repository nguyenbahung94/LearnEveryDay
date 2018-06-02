package com.tma.stockmarket.ui.interactor.ExchangeRate;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.tma.stockmarket.R;
import com.tma.stockmarket.ui.model.database.DBmanager;
import com.tma.stockmarket.ui.view.activity.MainActivityView;

/**
 * Created by nbhung on 8/3/2017.
 */

public class EdtProfileAccountInteractor {
    public void saveDataAccount(EditText edtFullname, EditText edtPass, EditText edtPhone, byte[] image, DBmanager dBmanager, SharedPreferences sharedPreferences, Context context, MainActivityView mainActivityView) {
        String id = sharedPreferences.getString(context.getString(R.string.iduser), "");
        Log.e("id", id);
        String sex = sharedPreferences.getString(context.getString(R.string.sex), "");
        if (dBmanager.updateUser(new String[]{"id", "name", "password", "sex", "phone"}, new String[]{id, edtFullname.getText().toString(), edtPass.getText().toString(), sex, edtPhone.getText().toString()}, "id=?", new String[]{id}, image)) {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
            mainActivityView.showScreenLogin();
        } else {
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        }

    }
}
