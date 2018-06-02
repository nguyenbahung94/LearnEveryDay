package com.ekakashi.greenhouse.features.recipe.edit_basic_recipe;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_request.EditRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_response.BaseResponse;
import com.ekakashi.greenhouse.database.model_server_response.CategoryResponse;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCrop;
import com.ekakashi.greenhouse.database.model_server_response.ObjectFilterRecipe;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ptloc on 1/24/2018.
 */

public class EditBasicRecipePresenter extends BasePresenter implements EditBasicRecipeInterface.presenter {
    private EditBasicRecipeInterface.View mEditBasicRecipeInterface;

    EditBasicRecipePresenter(EditBasicRecipeInterface.View mEditBasicRecipeInterface) {
        this.mEditBasicRecipeInterface = mEditBasicRecipeInterface;
    }

    public void onDestroy() {
        mEditBasicRecipeInterface = null;
    }

    @Override
    public void editRecipeById(int id, EditRecipeRequest recipeRequest) {
        APIManager.editRecipeById(id, recipeRequest, new Callback<ObjectRecipe>() {
            @Override
            public void onResponse(@NonNull Call<ObjectRecipe> call, @NonNull Response<ObjectRecipe> response) {
                if (mEditBasicRecipeInterface != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        mEditBasicRecipeInterface.editSuccess(response.body());
                    } else {
                        if (response.code() == Utils.ErrorCode.ERROR_401) {
                            mEditBasicRecipeInterface.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                mEditBasicRecipeInterface.failed(errorMessage);
                            } else {
                                mEditBasicRecipeInterface.failed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ObjectRecipe> call, @NonNull Throwable t) {
                if (mEditBasicRecipeInterface != null) {
                    mEditBasicRecipeInterface.failed(t.getMessage());
                }
            }
        });
    }

    @Override
    public void filterData() {
        APIManager.filterData(new Callback<ObjectFilterRecipe>() {
            @Override
            public void onResponse(@NonNull Call<ObjectFilterRecipe> call, @NonNull Response<ObjectFilterRecipe> response) {
                if (mEditBasicRecipeInterface != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        mEditBasicRecipeInterface.getFilterSuccess(response.body());
                    } else {
                        if (response.code() == Utils.ErrorCode.ERROR_401) {
                            mEditBasicRecipeInterface.tokenExpired();
                        } else {
                            mEditBasicRecipeInterface.failed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ObjectFilterRecipe> call, @NonNull Throwable t) {
                if (mEditBasicRecipeInterface != null) {
                    mEditBasicRecipeInterface.failed(t.getMessage());
                }
            }
        });
    }

    @Override
    public void updateRecipePicture(MultipartBody.Part file) {
        APIManager.updateRecipePicture(file, new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (mEditBasicRecipeInterface != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        mEditBasicRecipeInterface.updatePictureSuccess(response.body());
                    } else {
                        if (response.code() == Utils.ErrorCode.ERROR_401) {
                            mEditBasicRecipeInterface.tokenExpired();
                        } else {
                            mEditBasicRecipeInterface.failed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                if (mEditBasicRecipeInterface != null) {
                    mEditBasicRecipeInterface.failed(t.getMessage());
                }
            }
        });
    }

    @Override
    public void getCategories() {
        APIManager.getCategories(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoryResponse> call, @NonNull Response<CategoryResponse> response) {
                if (mEditBasicRecipeInterface != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        if (response.body() != null) {
                            mEditBasicRecipeInterface.getCategoriesSuccess(response.body().getResponse());
                        } else {
                            mEditBasicRecipeInterface.getCategoriesSuccess(null);
                        }
                    } else {
                        if (response.code() == Utils.ErrorCode.ERROR_401) {
                            mEditBasicRecipeInterface.tokenExpired();
                        } else {
                            mEditBasicRecipeInterface.failed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoryResponse> call, @NonNull Throwable t) {
                if (mEditBasicRecipeInterface != null) {
                    mEditBasicRecipeInterface.failed(t.getMessage());
                }
            }
        });
    }

    @Override
    public void getCropsByCateId(int categoryId) {
        APIManager.getCropsByCateId(categoryId, new Callback<List<ObjectCrop>>() {
            @Override
            public void onResponse(@NonNull Call<List<ObjectCrop>> call,@NonNull Response<List<ObjectCrop>> response) {
                if (mEditBasicRecipeInterface != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        if (response.body() != null) {
                            mEditBasicRecipeInterface.getCropsSuccess(response.body());
                        } else {
                            mEditBasicRecipeInterface.getCropsSuccess(null);
                        }
                    } else {
                        if (response.code() == Utils.ErrorCode.ERROR_401) {
                            mEditBasicRecipeInterface.tokenExpired();
                        } else {
                            mEditBasicRecipeInterface.failed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ObjectCrop>> call,@NonNull Throwable t) {
                if (mEditBasicRecipeInterface != null) {
                    mEditBasicRecipeInterface.failed(t.getMessage());
                }
            }
        });
    }
}
