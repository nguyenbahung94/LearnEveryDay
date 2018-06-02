package com.ekakashi.greenhouse.features.recipe.rule.add_rule;

import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;

/**
 * Created by ptloc on 3/20/2018.
 */

public interface AddConditionInterface {
    interface View{
        void success(ObjectRecipe recipe);

        void failed(String s);

        void tokenExpired();
    }

    interface presenter{
        void getRecipeById(int recipeId);
    }
}
