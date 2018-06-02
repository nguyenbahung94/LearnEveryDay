package com.bat.firstcom.supervisorapp.common;

/**
 * Created by tung phan on 7/24/17.
 */

public enum ErrorType {

    //login screen
    ERROR_RESPONSE_LOGIN,
    ERROR_REQUEST_LOGIN,
    ERROR_REQUEST_BRAND,
    ERROR_RESPONSE_BRAND,
    //coaching pst list screen
    ERROR_REQUEST_COACHING_PST_LIST,
    ERROR_RESPONSE_COACHING_PST_LIST,
    //checking pst list screen
    ERROR_REQUEST_CHECKING_PST_LIST,
    ERROR_RESPONSE_CHECKING_PST_LIST,
    //outlet list screen
    ERROR_REQUEST_OUTLET_LIST,
    ERROR_RESPONSE_OUTLET_LIST,
    ERROR_EMPTY_OUTLET_LIST,
    //marking screen
    ERROR_REQUEST_QUESTION_LIST,
    ERROR_RESPONSE_QUESTION_LIST,
    ERROR_REQUEST_POST_COACHING,
    ERROR_RESPONSE_POST_COACHING,
    ERROR_REQUEST_POST_COACHING_MISS_FIELD,
    ERROR_REQUEST_POST_COACHING_MISS_ANSWER,
    ERROR_REQUEST_POST_CHECKING,
    ERROR_RESPONSE_POST_CHECKING,
    ERROR_REQUEST_POST_CHECKING_MISS_FIELD,
    ERROR_REQUEST_POST_CHECKING_MISS_ANSWER,
    //report checking screen
    ERROR_REQUEST_CHECKING_REPORT,
    ERROR_RESPONSE_CHECKING_REPORT,
    //report coaching screen
    ERROR_REQUEST_COACHING_REPORT,
    ERROR_RESPONSE_COACHING_REPORT,
    //change password
    ERROR_INVALID_PASSWORD_OR_CONFIRMED_PASSWORD,
    ERROR_PASSWORD_NOT_MATCH,
    ERROR_PASSWORD_IS_SAME_WITH_OLD_PASSWORD,
    ERROR_REQUEST_CHANGE_PASSWORD,
    ERROR_RESPONSE_CHANGE_PASSWORD,
    //edit outlet
}
