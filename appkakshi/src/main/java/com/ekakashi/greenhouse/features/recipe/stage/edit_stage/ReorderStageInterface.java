package com.ekakashi.greenhouse.features.recipe.stage.edit_stage;

import com.ekakashi.greenhouse.database.model_server_request.EditRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_response.ChangeStageRecipeResponse;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;

/**
 * Created by ptloc on 1/24/2018.
 */

public interface ReorderStageInterface {
    interface View {
        void editRecipeSuccess(ObjectRecipe recipe);

        void failed(String string);

        void tokenExpired();

        void changeRecipeStageSuccess(ChangeStageRecipeResponse response);

        void getRecipeDetailByIdSuccess(ObjectRecipe recipe);
    }

    interface presenter {
        void editRecipeById(int id, EditRecipeRequest recipeRequest);

        void changeRecipeStage(int ekFieldId, int recipeId, int growingStageId);

        void getRecipeDetailById(int recipeId);
    }
}
