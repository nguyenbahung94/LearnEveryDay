package com.example.nbhung94.takeapictureandroi8;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Base API response
 *
 * @Author: nthtrung@tma.com.vn
 */
public class BaseResponse {

    @SerializedName("success")
    @Expose
    protected boolean success;

    @SerializedName("code")
    @Expose
    protected int code;

    @SerializedName("message")
    @Expose
    protected String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
