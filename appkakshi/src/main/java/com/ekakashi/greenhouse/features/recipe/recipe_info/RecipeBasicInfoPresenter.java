package com.ekakashi.greenhouse.features.recipe.recipe_info;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.CategoryResponse;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCrop;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeBasicInfoPresenter extends BasePresenter implements RecipeBasicInfoInterface.presenter {

    private RecipeBasicInfoInterface.View view;

    public RecipeBasicInfoPresenter(RecipeBasicInfoInterface.View view) {
        this.view = view;
    }

    public void onDestroy() {
        this.view = null;
    }

    @Override
    public void getRecipe(int id) {
        APIManager.getRecipeById(id, new Callback<ObjectRecipe>() {
            @Override
            public void onResponse(@NonNull Call<ObjectRecipe> call, @NonNull Response<ObjectRecipe> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        if (response.body() != null) {
                            view.getRecipeSuccess(response.body());
                        } else {
                            view.getRecipeSuccess(null);
                        }
                    } else {
                        if (response.code() == Utils.ErrorCode.ERROR_401) {
                            view.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                view.getRecipeFailed(errorMessage);
                            } else {
                                view.getRecipeFailed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ObjectRecipe> call, @NonNull Throwable t) {
                if (view != null) {
                    view.getRecipeFailed(t.getMessage());
                }
            }
        });
    }

    @Override
    public void getCategories() {
        APIManager.getCategories(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoryResponse> call, @NonNull Response<CategoryResponse> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        if (response.body() != null) {
                            view.getCategoriesSuccess(response.body().getResponse());
                        } else {
                            view.getCategoriesSuccess(null);
                        }
                    } else {
                        if (response.code() == Utils.ErrorCode.ERROR_401) {
                            view.tokenExpired();
                        } else {
                            view.getCategoriesFailed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoryResponse> call, @NonNull Throwable t) {
                if (view != null) {
                    view.getCategoriesFailed(t.getMessage());
                }
            }
        });
    }

    @Override
    public void getCropsByCateId(int categoryId) {
        APIManager.getCropsByCateId(categoryId, new Callback<List<ObjectCrop>>() {
            @Override
            public void onResponse(@NonNull Call<List<ObjectCrop>> call,@NonNull Response<List<ObjectCrop>> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        if (response.body() != null) {
                            view.getCropsSuccess(response.body());
                        } else {
                            view.getCropsSuccess(null);
                        }
                    } else {
                        if (response.code() == Utils.ErrorCode.ERROR_401) {
                            view.tokenExpired();
                        } else {
                            view.getCropsFailed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ObjectCrop>> call,@NonNull Throwable t) {
                if (view != null) {
                    view.getCropsFailed(t.getMessage());
                }
            }
        });
    }
}
