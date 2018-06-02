package com.ekakashi.greenhouse.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.ekakashi.greenhouse.database.model_server_response.EnumUserRoleOnField;

/**
 * Created by paduy on 11/7/2017.
 */

public class Prefs {
    private static final String SHARED_PREFERENCES_NAME = "ekakashi_greenhouse_pref";
    private static Prefs staticPrefs;
    private SharedPreferences prefs;
    //Token key
    private final String UUID_KEY = "uuid_key";
    private final String ID_ITEM = "id_item_selected";
    private final String LOCALE = "prefs_locale";
    private final String FILTER_PLACE = "filter_place";
    private final String TOKEN = "token";
    private final String REFRESH_TOKEN = "refresh_token";
    private final String CURRENT_USER_ID = "use_id";
    private final String INIT_CREEN = "init_screen";
    private final String CURRENT_FIELD = "current_id";
    private final String CALL_API_EDIT = "call_api_edit";
    private final String DEVICE_TOKEN = "device_token";
    private final String USER_ACCOUNT = "user_account";
    private final String SEND_DEVICE_TOKEN_SUCCESS = "send_device_token_failed";
    private final String BADGE_SETTING = "badge_setting";
    private final String BADGE_TIMELINE = "badge_timeline";
    private final String FIRST_TIME_LAUNCH = "first_time_aunch";
    private final String ROLEUSERONFIELD = "role_user_on_field";
    private final String RELOAD_TIMELINE = "reload_timeline";

    /**
     * Contractor.
     *
     * @param ctx : application context.
     */
    private Prefs(Context ctx) {
        prefs = ctx.getSharedPreferences(SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE);
    }

    /**
     * @param ctx : application context.
     * @return Instance of Prefs.
     */
    public static Prefs getInstance(Context ctx) {
        if (staticPrefs == null) {
            synchronized (Prefs.class) {
                if (staticPrefs == null) {
                    staticPrefs = new Prefs(ctx.getApplicationContext());
                }
            }
        }
        return staticPrefs;
    }

    public String getUserAccount() {
        return prefs.getString(USER_ACCOUNT, "");
    }

    public void saveUserAccount(String email) {
        prefs.edit().putString(USER_ACCOUNT, email).apply();
    }

    public void setFirstTimeLaunchToFalse() {
        prefs.edit().putBoolean(FIRST_TIME_LAUNCH, false).apply();
    }

    public void setUserRoleOnDetailField(EnumUserRoleOnField role) {
        int userRole = -1;
        switch (role) {
            case Owner:
                userRole = 0;
                break;
            case Administrator:
                userRole = 1;
                break;
            case Worker:
                userRole = 2;
                break;
            case Viewer:
                userRole = 3;
                break;
        }
        prefs.edit().putInt(ROLEUSERONFIELD, userRole).apply();

    }

    public EnumUserRoleOnField getUserRoleOnDetailField() {
        int userRole = prefs.getInt(ROLEUSERONFIELD, -1);
        EnumUserRoleOnField role = EnumUserRoleOnField.Viewer;
        switch (userRole) {
            case 0:
                role = EnumUserRoleOnField.Owner;
                break;
            case 1:
                role = EnumUserRoleOnField.Administrator;
                break;
            case 2:
                role = EnumUserRoleOnField.Worker;
                break;
            case 3:
                role = EnumUserRoleOnField.Viewer;
                break;
        }
        return role;
    }

    public boolean isFirstTimeLaunch() {
        return prefs.getBoolean(FIRST_TIME_LAUNCH, true);
    }

    public String getUUID() {
        return prefs.getString(UUID_KEY, null);
    }

    public void saveUUID(String uuid) {
        prefs.edit().putString(UUID_KEY, uuid).apply();
    }

    public String getIdItemSelected() {
        return prefs.getString(ID_ITEM, "-1");
    }

    public void saveIdItemSelected(String item) {
        prefs.edit().putString(ID_ITEM, item).apply();
    }

    public void saveLocale(String locale) {
        prefs.edit().putString(LOCALE, locale).apply();
    }

    public String getLocale() {
        return prefs.getString(LOCALE, Utils.Name.LOCALE_EN);
    }

    public void saveFilterPlace(String list) {
        prefs.edit().putString(FILTER_PLACE, list).apply();
    }

    public String getFilterPlace() {
        return prefs.getString(FILTER_PLACE, "");
    }

    public void saveToken(String token) {
        prefs.edit().putString(TOKEN, token).apply();
    }

    public String getToken() {
        return prefs.getString(TOKEN, "");
    }

    public void saveRefreshToken(String token) {
        prefs.edit().putString(REFRESH_TOKEN, token).apply();
    }

    public String getRefreshToken() {
        return prefs.getString(REFRESH_TOKEN, "");
    }


    public void saveUserId(int userId) {
        prefs.edit().putInt(CURRENT_USER_ID, userId).apply();
    }

    public int getUserId() {
        return prefs.getInt(CURRENT_USER_ID, -1);
    }

    public void setReadInit(Boolean isRead) {
        prefs.edit().putBoolean(INIT_CREEN, isRead).apply();
    }

    public Boolean isReadInitScreen() {
        return prefs.getBoolean(INIT_CREEN, false);
    }

    public void saveCurrentId_Field(int id) {
        if (id == -1) {
            prefs.edit().remove(CURRENT_FIELD).apply();
        } else {
            prefs.edit().putInt(CURRENT_FIELD, id).apply();
        }
    }

    public int getCurrentID_Field() {
        return prefs.getInt(CURRENT_FIELD, -1);
    }

    public void saveStatusCallApi(boolean b) {
        prefs.edit().putBoolean(CALL_API_EDIT, b).apply();
    }

    public boolean getStatusCallApi() {
        return prefs.getBoolean(CALL_API_EDIT, false);
    }

    public void saveDeviceToken(String deviceToken) {
        prefs.edit().putString(DEVICE_TOKEN, deviceToken).apply();
    }

    public String getDeviceToken() {
        return prefs.getString(DEVICE_TOKEN, "");
    }

    public void saveSendDeviceTokenStatus(boolean status) {
        prefs.edit().putBoolean(SEND_DEVICE_TOKEN_SUCCESS, status).apply();
    }

    public boolean getSendDeviceTokenStatus() {
        return prefs.getBoolean(SEND_DEVICE_TOKEN_SUCCESS, false);
    }

    public void saveBadgeSetting(int number) {
        prefs.edit().putInt(BADGE_SETTING, number).apply();
    }

    public Integer getBadgeSetting() {
        return prefs.getInt(BADGE_SETTING, 0);
    }

    public void saveReloadTimeline(boolean status) {
        prefs.edit().putBoolean(RELOAD_TIMELINE, status).apply();
    }

    public boolean getReloadTimeline() {
        return prefs.getBoolean(RELOAD_TIMELINE, false);
    }

    public void saveBadgeTimeline(int number) {
        prefs.edit().putInt(BADGE_TIMELINE, number).apply();
    }

    public Integer getBadgeTimeline() {
        return prefs.getInt(BADGE_TIMELINE, 0);
    }
}