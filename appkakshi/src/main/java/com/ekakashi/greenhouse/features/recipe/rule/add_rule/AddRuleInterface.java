package com.ekakashi.greenhouse.features.recipe.rule.add_rule;

import com.ekakashi.greenhouse.database.model_server_request.EditRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;

/**
 * Created by ptloc on 2/7/2018.
 */

public interface AddRuleInterface {
    interface View{
        void editSuccess(ObjectRecipe recipe);

        void failed(String s);

        void tokenExpired();
    }

    interface presenter{
        void editRecipeById(int recipeId, EditRecipeRequest recipeRequest);
    }
}
