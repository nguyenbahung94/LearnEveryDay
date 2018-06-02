package com.bat.firstcom.supervisorapp.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tung Phan on 27-Jul-17.
 */

public class CheckingResponse {

    @SerializedName("Success")
    @Expose
    private boolean success;
    @SerializedName("ErrorCode")
    @Expose
    private int errorCode;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
