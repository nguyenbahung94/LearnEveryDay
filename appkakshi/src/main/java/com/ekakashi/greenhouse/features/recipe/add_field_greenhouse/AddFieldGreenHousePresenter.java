package com.ekakashi.greenhouse.features.recipe.add_field_greenhouse;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_request.EditRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_request.SearchRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.database.model_server_response.SearchRecipeResponse;
import com.ekakashi.greenhouse.database.model_server_response.CategoryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ptloc on 1/24/2018.
 */

public class AddFieldGreenHousePresenter extends BasePresenter implements AddFieldGreenHouseInterface.presenter {
    private AddFieldGreenHouseInterface.View greenHouseInterface;

    public AddFieldGreenHousePresenter(AddFieldGreenHouseInterface.View greenHouseInterface) {
        this.greenHouseInterface = greenHouseInterface;
    }

    public void onDestroy() {
        greenHouseInterface = null;
    }

    @Override
    public void getCategories() {
        APIManager.getCategories(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoryResponse> call, @NonNull Response<CategoryResponse> response) {
                if (greenHouseInterface != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        if (response.body() != null) {
                            greenHouseInterface.getCategoriesSuccess(response.body().getResponse());
                        } else {
                            greenHouseInterface.getCategoriesSuccess(null);
                        }
                    } else {
                        if (response.code() == Utils.ErrorCode.ERROR_401) {
                            greenHouseInterface.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                greenHouseInterface.failed(errorMessage);
                            } else {
                                greenHouseInterface.failed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoryResponse> call, @NonNull Throwable t) {
                if (greenHouseInterface != null) {
                    greenHouseInterface.failed(t.getMessage());
                }
            }
        });
    }

    @Override
    public void searchRecipe(SearchRecipeRequest request, int currentPage, int recordPerPage) {
        APIManager.searchRecipe(request, currentPage, recordPerPage, new Callback<SearchRecipeResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchRecipeResponse> call, @NonNull Response<SearchRecipeResponse> response) {
                if (greenHouseInterface != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        greenHouseInterface.searchResult(response.body());
                    } else {
                        if (response.code() == Utils.ErrorCode.ERROR_401) {
                            greenHouseInterface.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                greenHouseInterface.failed(errorMessage);
                            } else {
                                greenHouseInterface.failed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SearchRecipeResponse> call, @NonNull Throwable t) {
                if (greenHouseInterface != null) {
                    greenHouseInterface.failed(t.getMessage());
                }
            }
        });
    }

    @Override
    public void cloneRecipe(int recipeId) {
        APIManager.cloneRecipe(recipeId, new Callback<ObjectRecipe>() {
            @Override
            public void onResponse(@NonNull Call<ObjectRecipe> call, @NonNull Response<ObjectRecipe> response) {
                if (greenHouseInterface != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        greenHouseInterface.cloneRecipeSuccess(response.body());
                    } else {
                        if (response.code() == Utils.ErrorCode.ERROR_401) {
                            greenHouseInterface.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                greenHouseInterface.failed(errorMessage);
                            } else {
                                greenHouseInterface.failed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ObjectRecipe> call, @NonNull Throwable t) {
                if (greenHouseInterface != null) {
                    greenHouseInterface.failed(t.getMessage());
                }
            }
        });
    }

    @Override
    public void editRecipeById(int id, EditRecipeRequest recipeRequest) {
        APIManager.editRecipeById(id, recipeRequest, new Callback<ObjectRecipe>() {
            @Override
            public void onResponse(@NonNull Call<ObjectRecipe> call, @NonNull Response<ObjectRecipe> response) {
                if (greenHouseInterface != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        greenHouseInterface.editRecipeSuccess(response.body());
                    } else {
                        if (response.code() == Utils.ErrorCode.ERROR_401) {
                            greenHouseInterface.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                greenHouseInterface.failed(errorMessage);
                            } else {
                                greenHouseInterface.failed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ObjectRecipe> call, @NonNull Throwable t) {
                if (greenHouseInterface != null) {
                    greenHouseInterface.failed(t.getMessage());
                }
            }
        });
    }

}
