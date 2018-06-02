package com.bat.firstcom.supervisorapp.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tung Phan on 27-Jul-17.
 */

public class VisitorQuantity {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("QuantityText")
    @Expose
    private String quantityText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuantityText() {
        return quantityText;
    }

    public void setQuantityText(String quantityText) {
        this.quantityText = quantityText;
    }
}
