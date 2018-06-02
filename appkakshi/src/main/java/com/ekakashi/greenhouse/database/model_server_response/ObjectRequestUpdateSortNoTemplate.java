package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nbhung on 3/21/2018.
 */

public class ObjectRequestUpdateSortNoTemplate {

    @SerializedName("recipeId")
    private int recipeId;
    @SerializedName("templateConfigList")
    private List<TemplateConfigList> templateConfigList;

    public ObjectRequestUpdateSortNoTemplate(int recipeId, List<TemplateConfigList> templateConfigList) {
        this.recipeId = recipeId;
        this.templateConfigList = templateConfigList;
    }

    public static class TemplateConfigList {
        @SerializedName("sortNo")
        private int sortNo;
        @SerializedName("templateConfigId")
        private int templateConfigId;

        public TemplateConfigList(int sortNo, int templateConfigId) {
            this.sortNo = sortNo;
            this.templateConfigId = templateConfigId;
        }
    }
}
