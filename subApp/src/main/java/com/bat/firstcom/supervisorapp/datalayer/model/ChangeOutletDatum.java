package com.bat.firstcom.supervisorapp.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tung Phan on 27-Jul-17.
 */

public class ChangeOutletDatum {

    @SerializedName("Outlet")
    @Expose
    private Outlet outlet;
    @SerializedName("Reasons")
    @Expose
    private List<Reason> reasons = null;
    @SerializedName("VisitorQuantities")
    @Expose
    private List<VisitorQuantity> visitorQuantities = null;

    public Outlet getOutlet() {
        return outlet;
    }

    public void setOutlet(Outlet outlet) {
        this.outlet = outlet;
    }

    public List<Reason> getReasons() {
        return reasons;
    }

    public void setReasons(List<Reason> reasons) {
        this.reasons = reasons;
    }

    public List<VisitorQuantity> getVisitorQuantities() {
        if(visitorQuantities == null){
            visitorQuantities = new ArrayList<>();
        }
        return visitorQuantities;
    }

    public void setVisitorQuantities(List<VisitorQuantity> visitorQuantities) {
        this.visitorQuantities = visitorQuantities;
    }
}
