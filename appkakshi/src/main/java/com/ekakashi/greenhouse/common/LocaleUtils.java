package com.ekakashi.greenhouse.common;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by nquochuy on 12/5/2017.
 */

public class LocaleUtils {

    private static Locale mLocale;

    public static void setLocale(Locale locale) {
        mLocale = locale;
        if (mLocale != null) {
            Locale.setDefault(mLocale);
        }
    }

    public static void updateConfig(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        Configuration configuration = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(mLocale);
        } else {
            configuration.locale = mLocale;
        }
        context.getResources().updateConfiguration(configuration, displayMetrics);
    }


}
