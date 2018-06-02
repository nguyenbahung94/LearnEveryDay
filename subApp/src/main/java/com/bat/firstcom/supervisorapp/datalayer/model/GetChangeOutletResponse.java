package com.bat.firstcom.supervisorapp.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tung Phan on 27-Jul-17.
 */

public class GetChangeOutletResponse {
    @SerializedName("Success")
    @Expose
    private boolean success;
    @SerializedName("Data")
    @Expose
    private ChangeOutletDatum changeOutletDatum;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ChangeOutletDatum getChangeOutletDatum() {
        return changeOutletDatum;
    }

    public void setChangeOutletDatum(ChangeOutletDatum changeOutletDatum) {
        this.changeOutletDatum = changeOutletDatum;
    }
}
