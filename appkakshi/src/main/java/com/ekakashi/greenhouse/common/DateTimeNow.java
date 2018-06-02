package com.ekakashi.greenhouse.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.ekakashi.greenhouse.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeNow {
    public static final String yyyy_MM_dd_T_HH_mm_ss_SSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final String TAG = "DateTimeNow";
    public static final String dd_MM_yyyy = "dd/MM/yyyy";

    public static String parseDateStringToLocalDateString(String strDate, String formatFrom, String formatTo) {
        return parseDateLocalToString(parseStringToDateLocal(strDate, formatFrom), formatTo);
    }

    public static Date parseStringToDateLocal(String dateString, String format) {
        try {
            return (new SimpleDateFormat(format, Locale.getDefault())).parse(dateString);
        } catch (Exception e) {
            Log.e(TAG, "parseStringToDateLocal()", e);
        }
        return null;
    }

    /**
     * Time Zone is Local default. Time will show at local so don't use time zone at here.
     *
     * @param date   : Date
     * @param format : format date
     * @return String
     */
    public static String parseDateLocalToString(Date date, String format) {
        if (date != null) {
            return (new SimpleDateFormat(format, Locale.getDefault())).format(date);
        } else {
            return "";
        }
    }

    public static String parseDateStringToUTCDateString(String strDate, String formatFrom, String formatTo) {
        return parseDateToStringUTC(parseStringToDateUTC(strDate, formatFrom), formatTo);
    }

    private static Date parseStringToDateUTC(String dateString, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            Log.e(TAG, "parseStringToDateUTC()", e);
        }
        return null;
    }

    public static String parseDateToStringUTC(Date date, String format) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return dateFormat.format(date);
        } else {
            return "";
        }
    }

    public static String parseTimeToUTCDateString(int year, int month, int day, int hour, int minute, int second) {
        Calendar c = Calendar.getInstance(Locale.getDefault());
        c.set(year, month, day, hour, minute, second); //we use current millisecond
        SimpleDateFormat simple = new SimpleDateFormat(yyyy_MM_dd_T_HH_mm_ss_SSSZ, Locale.getDefault());
        simple.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simple.format(c.getTime());
    }

    public static String getFormatDateTimeNowUTC(String format) {
        SimpleDateFormat simple = new SimpleDateFormat(format, Locale.getDefault());
        simple.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simple.format(Calendar.getInstance(Locale.getDefault()).getTime());
    }

    public static Calendar getDateTime(String strDate, String format) {
        if (TextUtils.isEmpty(strDate)) {
            return null;
        }
        Date date;
        try {
            date = (new SimpleDateFormat(format, Locale.getDefault())).parse(strDate);
        } catch (ParseException e) {
            Log.e(TAG, "getDateTime", e);
            return null;
        }
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(date.getTime());
        return calendar;
    }

    public static String parseCurrentTimeToUTCDateString() {
        Calendar c = Calendar.getInstance(Locale.getDefault());
        SimpleDateFormat simple = new SimpleDateFormat(yyyy_MM_dd_T_HH_mm_ss_SSSZ, Locale.getDefault());
        simple.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simple.format(c.getTime());
    }

    public static String getMinimumOrMaximumTimeFromDateString(String dateString, String format, boolean isMaximum) {
        if (dateString == null) {
            return null;
        }
        Date toDate = DateTimeNow.parseStringToDateLocal(dateString, format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toDate);
        return getMinimumOrMaximumTimeInDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), isMaximum);
    }

    public static String getMinimumOrMaximumTimeInDate(int year, int month, int day, boolean isMaximum) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, isMaximum ? calendar.getActualMaximum(Calendar.HOUR_OF_DAY) :
                calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, isMaximum ? calendar.getActualMaximum(Calendar.MINUTE) :
                calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, isMaximum ? calendar.getActualMaximum(Calendar.SECOND) :
                calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, isMaximum ? calendar.getActualMaximum(Calendar.MILLISECOND) :
                calendar.getActualMinimum(Calendar.MILLISECOND));
        SimpleDateFormat simple = new SimpleDateFormat(yyyy_MM_dd_T_HH_mm_ss_SSSZ, Locale.getDefault());
        simple.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simple.format(calendar.getTime());
    }

    public static String parseMillisecondToFormatDate(String milliSeconds, String formatDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(milliSeconds));
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatDate, Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    @SuppressLint("StringFormatInvalid")
    public static String compareTime(Context context, String datetime) {
        Date updateAt = DateTimeNow.parseStringToDateLocal(datetime, DateTimeNow.yyyy_MM_dd_T_HH_mm_ss_SSSZ);
        if (updateAt == null) {
            return "";
        }
        int subTime = (int) ((System.currentTimeMillis() - updateAt.getTime()));
        int day = subTime / (1000 * 60 * 60 * 24);
        int hour = subTime / (1000 * 60 * 60);
        int minute = subTime / (1000 * 60);

        if (day == 0) {
            Calendar timelineCalendar = Calendar.getInstance(Locale.getDefault());
            timelineCalendar.setTimeInMillis(updateAt.getTime());
            Calendar currentCalendar = Calendar.getInstance();
            if (minute > 60) {
                if (hour > 1) {
                    return context.getString(R.string.time_hours_ago, hour);
                } else if (hour == 1) {
                    return context.getString(R.string.time_hour_ago, hour);
                } else if (hour == -1) {
                    return context.getString(R.string.next_time_hour, hour * -1);
                } else {
                    //<-1.
                    return context.getString(R.string.next_time_hours, hour * -1);
                }
            } else {
                if (timelineCalendar.get(Calendar.MINUTE) != currentCalendar.get(Calendar.MINUTE)) {
                    if (minute > 1) {
                        return context.getString(R.string.time_minutes_ago, minute);
                    } else if (minute == 1) {
                        return context.getString(R.string.time_minute_ago, minute);
                    } else {
                        return context.getString(R.string.time_now);
                    }
                } else {
                    return context.getString(R.string.time_now);
                }
            }

        } else {
            return DateTimeNow.parseDateLocalToString(updateAt, context.getString(R.string.format_date_time));
        }
    }
}
