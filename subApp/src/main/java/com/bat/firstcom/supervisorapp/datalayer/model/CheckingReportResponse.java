package com.bat.firstcom.supervisorapp.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tung Phan on 27-Jul-17.
 */

public class CheckingReportResponse {
    @SerializedName("Success")
    @Expose
    private boolean success;
    @SerializedName("Data")
    @Expose
    private List<ReportDatum> reportData = null;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ReportDatum> getReportData() {
        return reportData;
    }

    public void setReportData(List<ReportDatum> reportData) {
        this.reportData = reportData;
    }
}
