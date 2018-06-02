package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ptloc on 1/2/2018.
 */

public class SearchRecipeResponse {
    @SerializedName("data")
    private List<ObjectRecipe> recipes;

    @SerializedName("paging")
    private Paging paging;

    public List<ObjectRecipe> getRecipes() {
        if (recipes == null) {
            return new ArrayList<>();
        }
        return recipes;
    }

    public Paging getPaging() {
        return paging;
    }

    public static class Paging {
        @SerializedName("currentPage")
        public int currentPage;
        @SerializedName("numOfPage")
        public int numOfPage;
        @SerializedName("recordPerPage")
        public int recordPerPage;
        @SerializedName("itemsLoaded")
        public int itemsLoaded;

        public int getCurrentPage() {
            return currentPage;
        }

        public int getNumOfPage() {
            return numOfPage;
        }

        public int getRecordPerPage() {
            return recordPerPage;
        }

        public int getItemsLoaded() {
            return itemsLoaded;
        }
    }
}
