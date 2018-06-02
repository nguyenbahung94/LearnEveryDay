package com.ekakashi.greenhouse.features.recipe.edit_basic_recipe;

import com.ekakashi.greenhouse.database.model_server_request.EditRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_response.BaseResponse;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCategory;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCrop;
import com.ekakashi.greenhouse.database.model_server_response.ObjectFilterRecipe;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;

import java.util.List;

import okhttp3.MultipartBody;

/**
 * Created by ptloc on 1/24/2018.
 */

public interface EditBasicRecipeInterface {
    interface View {
        void editSuccess(ObjectRecipe objectRecipe);

        void failed(String string);

        void getFilterSuccess(ObjectFilterRecipe filterRecipe);

        void updatePictureSuccess(BaseResponse baseResponse);

        void getCategoriesSuccess(List<ObjectCategory> categories);

        void getCropsSuccess(List<ObjectCrop> cropList);

        void tokenExpired();
    }

    interface presenter {
        void editRecipeById(int id, EditRecipeRequest recipeRequest);

        void updateRecipePicture(MultipartBody.Part file);

        void getCategories();

        void getCropsByCateId(int categoryId);

        void filterData();
    }
}
