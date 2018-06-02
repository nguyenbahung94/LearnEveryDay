package com.bat.firstcom.supervisorapp.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tung Phan on 26-Jul-17.
 */

public class CoachingReportResponse {

    @SerializedName("Success")
    @Expose
    private boolean success;
    @SerializedName("Data")
    @Expose
    private List<ReportDatum> reportData = null;

    public boolean getSuccess() {
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
