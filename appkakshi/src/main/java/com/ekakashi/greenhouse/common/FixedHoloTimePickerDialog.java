package com.ekakashi.greenhouse.common;


import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TimePicker;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class FixedHoloTimePickerDialog extends TimePickerDialog {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FixedHoloTimePickerDialog(Context context, OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView) {
        super(context, listener, hourOfDay, minute, is24HourView);
        try {
            final Field field = this.findField(TimePickerDialog.class, TimePicker.class, "TimePicker");
            assert field != null;
            TimePicker mTimePicker = (TimePicker) field.get(this);
            final Class<?> delegateClass = Class.forName("android.widget.TimePicker$TimePickerDelegate");
            final Field delegateField =
                    this.findField(TimePicker.class, delegateClass, "mDelegate");
            assert delegateField != null;
            final Object delegate = delegateField.get(mTimePicker);
            final Class<?> spinnerDelegateClass = Class.forName("android.widget.TimePickerSpinnerDelegate");
            if (delegate.getClass() != spinnerDelegateClass) {
                delegateField.set(mTimePicker, null);
                mTimePicker.removeAllViews();
                final Constructor spinnerDelegateConstructor =
                        spinnerDelegateClass.getDeclaredConstructor(TimePicker.class, Context.class,
                                AttributeSet.class, int.class, int.class);
                spinnerDelegateConstructor.setAccessible(true);
                final Object spinnerDelegate =
                        spinnerDelegateConstructor.newInstance(mTimePicker, context, null,
                                android.R.attr.timePickerStyle, 0);
                delegateField.set(mTimePicker, spinnerDelegate);
                mTimePicker.setIs24HourView(is24HourView);
                mTimePicker.setCurrentHour(hourOfDay);
                mTimePicker.setCurrentMinute(minute);
                mTimePicker.setOnTimeChangedListener(this);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
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
