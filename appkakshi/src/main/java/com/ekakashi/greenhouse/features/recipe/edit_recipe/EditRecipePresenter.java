package com.ekakashi.greenhouse.features.recipe.edit_recipe;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_request.AddFieldRequest;
import com.ekakashi.greenhouse.database.model_server_request.EditRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_response.EditRecipeForFieldResponse;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCreateField;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ptloc on 1/24/2018.
 */

public class EditRecipePresenter extends BasePresenter implements EditRecipeInterface.presenter {

    private EditRecipeInterface.View view;

    EditRecipePresenter(EditRecipeInterface.View view) {
        this.view = view;
    }

    public void onDestroy() {
        this.view = null;
    }

    @Override
    public void getRecipeById(int id) {
        APIManager.getRecipeById(id, new Callback<ObjectRecipe>() {
            @Override
            public void onResponse(@NonNull Call<ObjectRecipe> call, @NonNull Response<ObjectRecipe> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        view.getRecipeSuccess(response.body());
                    } else {
                        if (response.code() == Utils.ErrorCode.ERROR_401) {
                            view.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                view.failed(errorMessage);
                            } else {
                                view.failed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ObjectRecipe> call, @NonNull Throwable t) {
                if (view != null) {
                    view.failed(t.getMessage());
                }
            }
        });
    }

    @Override
    public void cloneRecipe(ObjectCreateField createField) {
        AddFieldRequest.Recipe recipe = new AddFieldRequest.Recipe(String.valueOf(createField.getRecipeId()));
        APIManager.cloneRecipeForField(String.valueOf(createField.getRecipeId()), new Callback<ObjectRecipe>() {
            @Override
            public void onResponse(@NonNull Call<ObjectRecipe> call, @NonNull Response<ObjectRecipe> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        if (response.body() != null) {
                            view.cloneSuccess(response.body());
                        } else {
                            view.failed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(0));
                        }
                    } else {
                        if (response.code() == Utils.Constant.ERROR_401) {
                            view.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                view.failed(errorMessage);
                            } else {
                                view.failed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ObjectRecipe> call, @NonNull Throwable t) {
                if (view != null) {
                    view.failed(t.getMessage());
                }
            }
        }, recipe);
    }

    @Override
    public void addRecipeForField(int fieldId, int recipeId, int currentStageId) {
        APIManager.addRecipeForField(fieldId, recipeId, currentStageId, new Callback<EditRecipeForFieldResponse>() {
            @Override
            public void onResponse(@NonNull Call<EditRecipeForFieldResponse> call, @NonNull Response<EditRecipeForFieldResponse> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        if (response.body() != null) {
                            view.addOrRemoveRecipeForFieldSuccess(response.body());
                        } else {
                            view.failed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(0));
                        }
                    } else {
                        if (response.code() == Utils.Constant.ERROR_401) {
                            view.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                view.failed(errorMessage);
                            } else {
                                view.failed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<EditRecipeForFieldResponse> call, @NonNull Throwable t) {
                if (view != null) {
                    view.failed(t.getMessage());
                }
            }
        });
    }

    @Override
    public void removeRecipeForField(int fieldId, int recipeId, int currentStageId) {
        APIManager.removeRecipeForField(fieldId, recipeId, currentStageId, new Callback<EditRecipeForFieldResponse>() {
            @Override
            public void onResponse(@NonNull Call<EditRecipeForFieldResponse> call, @NonNull Response<EditRecipeForFieldResponse> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        if (response.body() != null) {
                            view.addOrRemoveRecipeForFieldSuccess(response.body());
                        } else {
                            view.failed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(0));
                        }
                    } else {
                        if (response.code() == Utils.Constant.ERROR_401) {
                            view.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                view.failed(errorMessage);
                            } else {
                                view.failed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<EditRecipeForFieldResponse> call, @NonNull Throwable t) {
                if (view != null) {
                    view.failed(t.getMessage());
                }
            }
        });
    }

    @Override
    public void editRecipeById(int id, EditRecipeRequest recipeRequest) {
        APIManager.editRecipeById(id, recipeRequest, new Callback<ObjectRecipe>() {
            @Override
            public void onResponse(@NonNull Call<ObjectRecipe> call, @NonNull Response<ObjectRecipe> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        view.editRecipeSuccess(response.body());
                    } else {
                        if (response.code() == Utils.ErrorCode.ERROR_401) {
                            view.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                view.failed(errorMessage);
                            } else {
                                view.failed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ObjectRecipe> call, @NonNull Throwable t) {
                if (view != null) {
                    view.failed(t.getMessage());
                }
            }
        });
    }
}
