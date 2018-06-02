package com.ekakashi.greenhouse.features.recipe.stage.info_stage;

import com.ekakashi.greenhouse.database.model_server_request.EditRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;

/**
 * Created by ptloc on 3/8/2018.
 */

public interface InfoStageInterface {
    interface View{
        void editRecipeSuccess(ObjectRecipe recipe);

        void failed(String error);

        void tokenExpired();
    }

    interface presenter{
        void editRecipeById(int id, EditRecipeRequest recipeRequest);
    }
}
