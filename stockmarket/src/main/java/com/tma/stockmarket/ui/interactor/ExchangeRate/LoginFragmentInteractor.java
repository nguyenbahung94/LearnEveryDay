package com.tma.stockmarket.ui.interactor.ExchangeRate;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.tma.stockmarket.R;
import com.tma.stockmarket.ui.model.User;
import com.tma.stockmarket.ui.model.database.DBmanager;
import com.tma.stockmarket.ui.view.activity.FragmentLoginView;
import com.tma.stockmarket.ui.view.activity.MainActivityView;


public class LoginFragmentInteractor {
    public void login(String name, String pass, MainActivityView mainActivityView, DBmanager dBmanager, SharedPreferences mSharedPreferences, FragmentLoginView fragmentLoginView, Context context) {
        if (name != null && pass != null) {
            User dataUser = dBmanager.getAccountUser(name);
            if (dataUser != null) {
                if (dataUser.getPassword().equals(pass)) {
                    Log.e("success", "success");
                    mSharedPreferences.edit().putString(context.getString(R.string.iduser), dataUser.getId()).apply();
                    mSharedPreferences.edit().putString(context.getString(R.string.fullname), dataUser.getName()).apply();
                    mSharedPreferences.edit().putString(context.getString(R.string.passuser), dataUser.getPassword()).apply();
                    mSharedPreferences.edit().putString(context.getString(R.string.sex), String.valueOf(dataUser.getSex())).apply();
                    String saveThis = Base64.encodeToString(dataUser.getImage(), Base64.DEFAULT);
                    mSharedPreferences.edit().putString(context.getString(R.string.imageuser), saveThis).apply();
                    mSharedPreferences.edit().putString(context.getString(R.string.phone), dataUser.getPhone()).apply();
                    mainActivityView.hideAndShowNavi(dataUser);
                    fragmentLoginView.success();
                } else fragmentLoginView.failed();
            } else fragmentLoginView.failed();
        } else fragmentLoginView.failed();
    }
    public void setValueShared(SharedPreferences shared, Context context) {
        shared.edit().putString(context.getString(R.string.iduser), "").apply();
        shared.edit().putString(context.getString(R.string.passuser), "").apply();
        shared.edit().putString(context.getString(R.string.fullname), "").apply();
        shared.edit().putString(context.getString(R.string.imageuser), "").apply();
        shared.edit().putString(context.getString(R.string.birthday), "").apply();
        shared.edit().putInt(context.getString(R.string.sex), 0).apply();
        shared.edit().putString(context.getString(R.string.phone), "").apply();
    }
}
