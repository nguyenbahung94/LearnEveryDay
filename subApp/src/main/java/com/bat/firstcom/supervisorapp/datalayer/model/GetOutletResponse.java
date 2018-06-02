package com.bat.firstcom.supervisorapp.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tung Phan on 24-Jul-17.
 */

public class GetOutletResponse {

    @SerializedName("Success")
    @Expose
    private boolean success;
    @SerializedName("Data")
    @Expose
    private List<OutletDatum> outletData;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<OutletDatum> getOutletData() {
        return outletData;
    }

    public void setOutletData(List<OutletDatum> outletData) {
        this.outletData = outletData;
    }
}
