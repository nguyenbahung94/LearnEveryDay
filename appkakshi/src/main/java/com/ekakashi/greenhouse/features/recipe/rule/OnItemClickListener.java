package com.ekakashi.greenhouse.features.recipe.rule;

import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;

/**
 * Created by ptloc on 12/26/2017.
 */

public interface OnItemClickListener {

    void OnRecipeClick(ObjectRecipe recipe, int position);

}
