package com.ekakashi.greenhouse.common;


import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.DatePicker;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

class FixedHoloDatePickerDialog extends DatePickerDialog {
    private static final String DATE_PICKER = "mDatePicker";
    private DatePicker mDatePicker;

    FixedHoloDatePickerDialog(Context context, OnDateSetListener callBack, int year,
                              int monthOfYear, int dayOfMonth)
            throws ClassNotFoundException, IllegalAccessException, NoSuchMethodException,
            InvocationTargetException, InstantiationException {
        super(context, callBack, year, monthOfYear, dayOfMonth);

        final Field field =
                this.findField(DatePickerDialog.class, DatePicker.class, "mDatePicker");
        assert field != null;
        try {
            mDatePicker = (DatePicker) field.get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        final Class<?> delegateClass = Class.forName("android.widget.DatePicker$DatePickerDelegate");
        final Field delegateField = this.findField(DatePicker.class, delegateClass, "mDelegate");
        assert delegateField != null;
        final Object delegate = delegateField.get(mDatePicker);
        final Class<?> spinnerDelegateClass = Class.forName("android.widget.DatePickerSpinnerDelegate");
        if (delegate.getClass() != spinnerDelegateClass) {
            delegateField.set(mDatePicker, null);
            mDatePicker.removeAllViews();
            final Constructor spinnerDelegateConstructor =
                    spinnerDelegateClass.getDeclaredConstructor(DatePicker.class, Context.class,
                            AttributeSet.class, int.class, int.class);
            spinnerDelegateConstructor.setAccessible(true);
            final Object spinnerDelegate =
                    spinnerDelegateConstructor.newInstance(mDatePicker, context, null,
                            android.R.attr.datePickerStyle, 0);
            delegateField.set(mDatePicker, spinnerDelegate);
            mDatePicker.init(year, monthOfYear, dayOfMonth, this);
            mDatePicker.setCalendarViewShown(false);
            mDatePicker.setSpinnersShown(true);
        }
    }

    private Field findField(Class objectClass, Class fieldClass, String expectedName) {
        try {
            final Field field = objectClass.getDeclaredField(expectedName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException ignored) {
        }
        for (final Field field : objectClass.getDeclaredFields()) {
            if (field.getType() == fieldClass) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }
}
