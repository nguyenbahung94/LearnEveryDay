package com.bat.firstcom.supervisorapp.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tung Phan on 24-Jul-17.
 */

public class GetBrandResponse {

    @SerializedName("Success")
    @Expose
    private boolean success;
    @SerializedName("Data")
    @Expose
    private List<BrandDatum> brandData;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<BrandDatum> getBrandData() {
        if(brandData == null){
            brandData = new ArrayList<>();
        }
        return brandData;
    }

    public void setBrandData(List<BrandDatum> brandData) {
        this.brandData = brandData;
    }

}
