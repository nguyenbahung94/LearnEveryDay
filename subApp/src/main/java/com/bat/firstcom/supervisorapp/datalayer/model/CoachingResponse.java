package com.bat.firstcom.supervisorapp.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tung phan on 7/25/17.
 */

public class CoachingResponse {
    @SerializedName("Success")
    @Expose
    private boolean success;
    @SerializedName("ErrorCode")
    @Expose
    private Integer errorCode;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
