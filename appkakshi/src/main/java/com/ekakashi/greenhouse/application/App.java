package com.ekakashi.greenhouse.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

import com.ekakashi.greenhouse.common.LocaleUtils;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.DatabaseHelper;
import com.ekakashi.greenhouse.database.model_server_response.ObjectAction;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCondition;
import com.ekakashi.greenhouse.database.model_server_response.ObjectGrowth;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRule;
import com.ekakashi.greenhouse.features.advice.AdviceDescriptionObject;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.Locale;


public class App extends Application {

    public static ObjectRecipe appRecipe;
    public static ObjectGrowth appStage;
    public static ObjectAction appAction;
    public static ObjectCondition appCondition;
    public static ObjectRule appRule;
    public static AdviceDescriptionObject descriptionObject;
    public static Integer notificationType;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private static App sInstance;

    /**
     * Access database.
     */
    private static DatabaseHelper mDatabaseHelper = null;

    //TODO: NOTE: http://ormlite.com/javadoc/ormlite-core/doc-files/ormlite_4.html#Use-With-Android
    //If you do not want to extend the OrmLiteBaseActivity and other base classes then you will need to duplicate their functionality
    public static DatabaseHelper getDatabaseHelper(Context appContext) {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = OpenHelperManager.getHelper(appContext.getApplicationContext(), DatabaseHelper.class);
        }
        return mDatabaseHelper;
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    /**
     * The method is only called when exit app.
     */
    public static void releaseDatabaseHelper() {
        if (mDatabaseHelper != null) {
            OpenHelperManager.releaseHelper();
            mDatabaseHelper = null;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // FacebookSdk.sdkInitialize(this);
        //  AppEventsLogger.activateApp(this);
        sInstance = this;
        setLocaleLanguage();
    }

    /*
    * Change application language
    * If current language in device is English or Japanese, take it
    * Else language default is Japanese
    * */
    private void setLocaleLanguage() {
        String locale = Prefs.getInstance(this).getLocale();
        if (locale.equals("")) {
            String current_language = Locale.getDefault().getLanguage();
            if (current_language.equals(Utils.Name.LOCALE_JA) || current_language.equals(Utils.Name.LOCALE_EN)) {
                LocaleUtils.setLocale(new Locale(current_language));
                Prefs.getInstance(this).saveLocale(current_language);
            } else {
                LocaleUtils.setLocale(new Locale(Utils.Name.LOCALE_JA));
                Prefs.getInstance(this).saveLocale(Utils.Name.LOCALE_JA);
            }
        } else {
            LocaleUtils.setLocale(new Locale(locale));
        }
//        LocaleUtils.setLocale(new Locale(Utils.Name.LOCALE_JA));
        LocaleUtils.updateConfig(this);
    }

    public static App getsInstance() {
        return sInstance;
    }

    public static void onDestroy() {
        appRecipe = null;
        appAction = null;
        appCondition = null;
        appStage = null;
        appRule = null;
    }

    public static void onDestroyAdvice(){
        descriptionObject = null;
    }

    public static void onDestroyNotificationType() {
        notificationType = null;
    }

}
