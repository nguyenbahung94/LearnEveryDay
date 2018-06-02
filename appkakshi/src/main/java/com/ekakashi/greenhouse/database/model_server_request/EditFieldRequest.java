package com.ekakashi.greenhouse.database.model_server_request;

import com.ekakashi.greenhouse.database.model_server_response.ResponseDetailField;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nbhung on 1/16/2018.
 */

public class EditFieldRequest {

    @SerializedName("name")
    private String name;
    @SerializedName("polygon")
    private List<ResponseDetailField.Polygon> polygon;
    @SerializedName("fieldType")
    private int fieldType;
    @SerializedName("id")
    private int id;
    @SerializedName("userId")
    private int userId;
    @SerializedName("location")
    private String location;

    public EditFieldRequest(String name, List<ResponseDetailField.Polygon> polygon, int fieldType, int id, int userId, String location) {
        this.name = name;
        this.polygon = polygon;
        this.fieldType = fieldType;
        this.id = id;
        this.userId = userId;
        this.location = location;
    }

    public int getUserId() {
        return userId;
    }
}
