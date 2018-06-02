package com.ekakashi.greenhouse.features.recipe.add_field_greenhouse;

import com.ekakashi.greenhouse.database.model_server_request.EditRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_request.SearchRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.database.model_server_response.SearchRecipeResponse;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCategory;

import java.util.List;

/**
 * Created by ptloc on 1/24/2018.
 */

public interface AddFieldGreenHouseInterface {

    interface View{

        void getCategoriesSuccess(List<ObjectCategory> categories);

        void searchResult(SearchRecipeResponse response);

        void cloneRecipeSuccess(ObjectRecipe recipe);

        void editRecipeSuccess(ObjectRecipe recipe);

        void failed(String string);

        void tokenExpired();
    }

    interface presenter{

        void getCategories();

        void searchRecipe(SearchRecipeRequest request, int currentPage, int recordPerPage);

        void cloneRecipe(int recipeId);

        void editRecipeById(int id, EditRecipeRequest recipeRequest);
    }

}
