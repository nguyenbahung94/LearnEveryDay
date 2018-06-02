package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ptloc on 2/5/2018.
 */

public class CategoryResponse {
    @SerializedName("data")
    private List<ObjectCategory> response;

    public List<ObjectCategory> getResponse() {
        if (response == null) {
            return new ArrayList<>();
        }
        return response;
    }
}
