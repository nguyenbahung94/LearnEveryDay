package com.ekakashi.greenhouse.features.recipe.stage.edit_stage;

import com.ekakashi.greenhouse.database.model_server_request.EditRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;

/**
 * Created by ptloc on 2/6/2018.
 */

public interface EditStageInterface {
    interface View{
        void editSuccess(ObjectRecipe recipe);
        void failed(String string);
        void tokenExpired();
    }

    interface presenter{
        void editRecipeById(int id, EditRecipeRequest recipeRequest);
    }
}
