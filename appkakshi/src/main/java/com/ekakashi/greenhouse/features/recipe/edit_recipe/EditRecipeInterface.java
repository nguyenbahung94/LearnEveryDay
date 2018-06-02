package com.ekakashi.greenhouse.features.recipe.edit_recipe;

import com.ekakashi.greenhouse.database.model_server_request.EditRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_response.CloneRecipeResponse;
import com.ekakashi.greenhouse.database.model_server_response.EditRecipeForFieldResponse;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCreateField;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;

/**
 * Created by ptloc on 1/24/2018.
 */

public interface EditRecipeInterface {

    interface View {
        void getRecipeSuccess(ObjectRecipe recipe);

        void failed(String s);

        void tokenExpired();

        void cloneSuccess(ObjectRecipe objectRecipe);

        void addOrRemoveRecipeForFieldSuccess(EditRecipeForFieldResponse response);

        void editRecipeSuccess(ObjectRecipe recipe);
    }

    interface presenter {
        void getRecipeById(int id);

        void cloneRecipe(ObjectCreateField createField);

        void addRecipeForField(int fieldId, int recipeId, int currentStageId);

        void removeRecipeForField(int fieldId, int recipeId, int currentStageId);

        void editRecipeById(int id, EditRecipeRequest recipeRequest);
    }
}
