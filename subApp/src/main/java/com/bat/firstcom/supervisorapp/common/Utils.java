package com.bat.firstcom.supervisorapp.common;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.bat.firstcom.supervisorapp.R;
import com.bat.firstcom.supervisorapp.datalayer.model.QuestionDatum;

import java.util.List;

/**
 * Created by Tung Phan on 04-Jul-17.
 */

public final class Utils {

    private static final int RANDOM_STRING_LENGTH = 10;

    public static boolean higherThanKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean higherThanM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static InputErrorType validateUserName(String userName) {
        if (userName.trim().equalsIgnoreCase(Strings.EMPTY)) {
            return InputErrorType.EMPTY;
        }
        return InputErrorType.VALID;
    }

    public static InputErrorType validatePassword(String password) {
        if (password.trim().equalsIgnoreCase(Strings.EMPTY)) {
            return InputErrorType.EMPTY;
        }
        return InputErrorType.VALID;
    }

    //add String to input String, insert comma between them
    public static String addStringWithComma(@NonNull String input, String addString) {
        if (addString != null
                && !addString.equalsIgnoreCase(Strings.EMPTY)) {
            if (input.equalsIgnoreCase(Strings.EMPTY)) {
                input += addString;
            } else {
                input += ", " + addString;
            }
        }
        return input;
    }

    //return error message id from error code
    public static int getErrorMessageFrom(ErrorType errorType) {
        switch (errorType) {
            //Change password error
            case ERROR_INVALID_PASSWORD_OR_CONFIRMED_PASSWORD:
                return R.string.error_invalid_pass_or_confirm_pass;
            case ERROR_PASSWORD_NOT_MATCH:
                return R.string.error_pass_not_match;
            case ERROR_PASSWORD_IS_SAME_WITH_OLD_PASSWORD:
                return R.string.error_pass_is_same_with_old_pass;
            case ERROR_REQUEST_CHANGE_PASSWORD:
                return R.string.error_request_change_password;
            case ERROR_RESPONSE_CHANGE_PASSWORD:
                return R.string.error_response_change_password;
            //login screen error
            case ERROR_REQUEST_BRAND:
                return R.string.error_request_brand_list;
            case ERROR_RESPONSE_BRAND:
                return R.string.error_response_brand_list;
            case ERROR_RESPONSE_LOGIN:
                return R.string.error_login_response_faile;
            case ERROR_REQUEST_LOGIN:
                return R.string.error_login_request;
            //marking screen error
            case ERROR_REQUEST_QUESTION_LIST:
                return R.string.error_request_coaching_questions;
            case ERROR_RESPONSE_QUESTION_LIST:
                return R.string.error_response_coaching_questions;
            case ERROR_REQUEST_POST_COACHING:
                return R.string.error_request_post_marking;
            case ERROR_RESPONSE_POST_COACHING:
                return R.string.error_response_post_coaching;
            case ERROR_REQUEST_POST_COACHING_MISS_FIELD:
                return R.string.error_request_post_coaching_miss_field;
            case ERROR_REQUEST_POST_COACHING_MISS_ANSWER:
                return R.string.error_request_post_coaching_miss_answer;
            case ERROR_REQUEST_POST_CHECKING:
                return R.string.error_request_post_marking;
            case ERROR_RESPONSE_POST_CHECKING:
                return R.string.error_response_post_checking;
            case ERROR_REQUEST_POST_CHECKING_MISS_FIELD:
                return R.string.error_request_post_checking_miss_field;
            case ERROR_REQUEST_POST_CHECKING_MISS_ANSWER:
                return R.string.error_request_post_checking_miss_answer;
            //outlet screen error
            case ERROR_REQUEST_OUTLET_LIST:
                return R.string.error_request_outlet_list;
            case ERROR_RESPONSE_OUTLET_LIST:
                return R.string.error_response_outlet_list;
            case ERROR_EMPTY_OUTLET_LIST:
                return R.string.error_empty_outlet_list;
            //pst screen error
            case ERROR_REQUEST_COACHING_PST_LIST:
                return R.string.error_request_coaching_pst_list;
            case ERROR_RESPONSE_COACHING_PST_LIST:
                return R.string.error_response_coaching_pst_list;
            case ERROR_REQUEST_CHECKING_PST_LIST:
                return R.string.error_request_checking_pst_list;
            case ERROR_RESPONSE_CHECKING_PST_LIST:
                return R.string.error_response_checking_pst_list;
            default:
                return R.string.error_occured;
        }
    }


    public static boolean hasRequiredPermissions(Context context, String... permissions) {
        if (higherThanM() && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean shouldShowRequestPermission(Activity activity, String... permissions) {
        if (higherThanM() && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        permission)) {
                    return true;
                }
            }
        }
        return false;
    }

    public enum Mode {
        ALPHA, ALPHANUMERIC, NUMERIC
    }

    private static String generateRandomString(int length, Mode mode) {
        StringBuilder stringBuilder = new StringBuilder();
        String characters = getCharacterFrom(mode);
        int charactersLength = characters.length();
        for (int i = 0; i < length; i++) {
            double index = Math.random() * charactersLength;
            stringBuilder.append(characters.charAt((int) index));
        }
        return stringBuilder.toString();
    }

    private static String getCharacterFrom(Mode mode) {
        switch (mode) {
            case ALPHA:
                return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            case ALPHANUMERIC:
                return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
            case NUMERIC:
                return "1234567890";
        }
        return Strings.EMPTY;
    }

    public static String generateRandomString(Mode mode) {
        return generateRandomString(RANDOM_STRING_LENGTH, mode) + System.currentTimeMillis();
    }

    //TODO: recheck logic of the merge sort
    private static List<QuestionDatum> questionData;
    private static List<QuestionDatum> helper;
    private static int questionDataSize;

    public static List<QuestionDatum> sortListQuestionDatum(List<QuestionDatum> data) {
        questionData = data;
        questionDataSize = questionData.size();
        helper = data;
        mergesort(0, questionDataSize - 1);
        return questionData;
    }

    private static void mergesort(int low, int high) {
        // check if low is smaller than high, if not then the array is sorted
        if (low < high) {
            // Get the index of the element which is in the middle
            int middle = low + (high - low) / 2;
            // Sort the left side of the array
            mergesort(low, middle);
            // Sort the right side of the array
            mergesort(middle + 1, high);
            // Combine them both
            merge(low, middle, high);
        }
    }

    private static void merge(int low, int middle, int high) {

        // Copy both parts into the helper array
        for (int i = low; i <= high; i++) {
            helper.set(i,questionData.get(i));
        }
        int i = low;
        int j = middle + 1;
        int k = low;
        // Copy the smallest values from either the left or the right side back
        // to the original array
        while (i <= middle && j <= high) {
            if (helper.get(i).getId() <= helper.get(j).getId()) {
                questionData.set(k,helper.get(i));
                i++;
            } else {
                questionData.set(k,helper.get(j));
                j++;
            }
            k++;
        }
        // Copy the rest of the left side of the array into the target array
        while (i <= middle) {
            questionData.set(k,helper.get(i));
            k++;
            i++;
        }
        // Since we are sorting in-place any leftover elements from the right side
        // are already at the right position.

    }

}
