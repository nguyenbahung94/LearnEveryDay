package com.ekakashi.greenhouse.features.recipe.rule.add_rule.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ptloc on 3/15/2018.
 */

public class EnumRule {
    @IntDef({MOMENTARY, AGGREGATION, CONTROL_DEVICE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RuleType {
    }

    public static final int MOMENTARY = 1;
    public static final int AGGREGATION = 2;
    public static final int CONTROL_DEVICE = 3;

    @IntDef({PRECIPITATION, TEMPERATURE, WINDSPEED, MOISTURE, AMOUNT_OF_SOLAR_RADIATION})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DeterminationValue {
    }

    public static final int PRECIPITATION = 0;
    public static final int TEMPERATURE = 1;
    public static final int WINDSPEED = 2;
    public static final int MOISTURE = 3;
    public static final int AMOUNT_OF_SOLAR_RADIATION = 4;

    @IntDef({TOTAL_OR_ACCUMULATION, DAILY_AVERAGE, DAYTIME_AVERAGE, NIGHTLY_AVERAGE, HIGHEST, LOWEST, DAY_DIFFERENCE,
            NUMERICAL_COMPARISON})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CountingMethod {
    }

    public static final int TOTAL_OR_ACCUMULATION = 0;
    public static final int DAILY_AVERAGE = 1;
    public static final int DAYTIME_AVERAGE = 2;
    public static final int NIGHTLY_AVERAGE = 3;
    public static final int HIGHEST = 4;
    public static final int LOWEST = 5;
    public static final int DAY_DIFFERENCE = 6;
    public static final int NUMERICAL_COMPARISON = 7;

    @IntDef({WHEN_IT_REACHES, ABOVE,
            BELOW, IN_THE_CASE_OF, BETWEEN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DeterminationStandardForAggregation {
    }

    public static final int WHEN_IT_REACHES = 1;
    public static final int ABOVE = 2;
    public static final int BELOW = 3;
    public static final int IN_THE_CASE_OF = 4;
    public static final int BETWEEN = 5;

    @IntDef({ ABOVE,
            BELOW})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DeterminationStandardForMomemtary {
    }

    public static final int GREATER_THAN = 2;
    public static final int LESS_THAN = 3;

    @IntDef({TEN_TO_TWENTY, FORTY_TO_SIXTY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AddtionalInformation {
    }

    public static final int TEN_TO_TWENTY = 0;
    public static final int FORTY_TO_SIXTY = 1;

    @IntDef({POST_TO_TIMELINE, MOVE_TO_NEXT_STAGE, GIVE_CULTIVATION_MANAGEMENT_ADVICE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ActionType {
    }

    public static final int POST_TO_TIMELINE = 1;
    public static final int MOVE_TO_NEXT_STAGE = 3;
    public static final int GIVE_CULTIVATION_MANAGEMENT_ADVICE = 4;

    @IntDef({ONE_KM_MESH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DeterminationDevice {
    }

    public static final int ONE_KM_MESH = 0;

    @IntDef({FROM_REORDER, FROM_EDIT_STAGE, FROM_SELECT_CURRENT_STAGE})
    public @interface AddStage {
    }

    public static final int FROM_REORDER = 1;
    public static final int FROM_EDIT_STAGE = 2;
    public static final int FROM_SELECT_CURRENT_STAGE = 3;
}
