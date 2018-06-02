package com.ekakashi.greenhouse.features.recipe.rule.add_rule;

import android.text.InputFilter;
import android.text.Spanned;

import com.ekakashi.greenhouse.features.recipe.rule.add_rule.constant.EnumRule;

/**
 * Created by ptloc on 3/16/2018.
 */

public class InputFilterMinMax implements InputFilter {
    private int min, max;
    private int measurementItem;

    public InputFilterMinMax(int measurementItem) {
        this.measurementItem = measurementItem;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            getMinMaxValue(measurementItem);
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) {
            nfe.getMessage();
        }
        return "";
    }

    private void getMinMaxValue(int measurementItem) {
        switch (measurementItem) {
            case EnumRule.PRECIPITATION:
                min = 0;
                max = 255;
                break;
            case EnumRule.TEMPERATURE:
                min = 0;
                max = 255;
                break;
            case EnumRule.MOISTURE:
                min = 0;
                max = 255;
                break;
            case EnumRule.AMOUNT_OF_SOLAR_RADIATION:
                min = 0;
                max = 255;
                break;
        }
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
