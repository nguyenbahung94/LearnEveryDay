package com.ekakashi.greenhouse.features.recipe.recipe_info;

import com.ekakashi.greenhouse.database.model_server_response.ObjectCategory;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCrop;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;

import java.util.List;

public interface RecipeBasicInfoInterface {
    interface View {
        void initPresenter();

        void tokenExpired();

        void getRecipeSuccess(ObjectRecipe recipe);

        void getRecipeFailed(String error);

        void getCategoriesSuccess(List<ObjectCategory> categories);

        void getCropsSuccess(List<ObjectCrop> cropList);

        void getCategoriesFailed(String error);

        void getCropsFailed(String error);

    }

    interface presenter {
        void getRecipe(int id);

        void getCategories();

        void getCropsByCateId(int categoryId);
    }
}
