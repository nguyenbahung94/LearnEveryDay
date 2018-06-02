package com.example.nbhung.demotwitter.common;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private static final String SHARED_PREFERENCES_NAME = "my_app_clean";
    private static Prefs prefsInstance;
    private SharedPreferences preferences;
    private static final String SAVE_USER_NAME = "save_user_name";
    private static final String SAVE_PASSWORD = "save_password";
    private static final String IS_SHOW_NOTI = "show_notification";

    private Prefs(Context context) {
        this.preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static Prefs getPrefsInstance(Context context) {
        if (prefsInstance == null) {
            synchronized (Prefs.class) {
                if (prefsInstance == null) {
                    prefsInstance = new Prefs(context.getApplicationContext());
                }
            }
        }
        return prefsInstance;
    }

    public void saveUserName(String userName) {
        preferences.edit().putString(SAVE_USER_NAME, userName).apply();
    }

    public String getUserName() {
        return preferences.getString(SAVE_USER_NAME, "");
    }

    public void savePassWord(String passWord) {
        preferences.edit().putString(SAVE_PASSWORD, passWord).apply();
    }

    public String getPassWord() {
        return preferences.getString(SAVE_PASSWORD, "");
    }

    public void setIsShowNoti(boolean value) {
        preferences.edit().putBoolean(IS_SHOW_NOTI, value).apply();
    }

    public boolean IsShowNotification() {
        return preferences.getBoolean(IS_SHOW_NOTI, false);
    }

}
