package com.ekakashi.greenhouse.common;

/**
 * Created by nbhung on 1/17/2018.
 */

public class BaseResponse {
    public static String messageResponse(int code) {
        String message = "";
        switch (code) {
            case 200:
                message = "Success";
                break;
            case 201:
                message = "Created";
                break;
            case 400:
                message = "Invalid request";
                break;
            case 401:
                message = "Unauthorized";
                break;
            case 403:
                message = "Forbidden";
                break;
            case 404:
                message = "Not Found";
                break;
            case 410:
                message = "No data found for your request";
                break;
            case 500:
                message = "Internal server error";
                break;
            case 0:
                message = "Server returns empty data";
                break;
            default:
                break;
        }
        return message;
    }
}
