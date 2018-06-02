package com.bat.firstcom.supervisorapp.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tung Phan on 24-Jul-17.
 */

public class GetPSTListResponse {
    @SerializedName("Success")
    @Expose
    private boolean success;
    @SerializedName("Data")
    @Expose
    private List<PSTDatum> pstData;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<PSTDatum> getPstData() {
        return pstData;
    }

    public void setPstData(List<PSTDatum> pstData) {
        this.pstData = pstData;
    }
}
