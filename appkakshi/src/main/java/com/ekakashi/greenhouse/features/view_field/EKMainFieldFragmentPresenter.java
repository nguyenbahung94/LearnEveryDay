package com.ekakashi.greenhouse.features.view_field;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.BaseResponse;
import com.ekakashi.greenhouse.common.DateTimeNow;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ChangeStageRecipeResponse;
import com.ekakashi.greenhouse.database.model_server_response.ResponseDetailField;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.TemplateRecipe;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterField;
import com.ekakashi.greenhouse.features.weather.object_weather.ObjectDataForWidgetGraph;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EKMainFieldFragmentPresenter extends BasePresenter implements EKMainFieldFragmentInterface.Presenter {
    private EKMainFieldFragmentInterface.View view;

    EKMainFieldFragmentPresenter(EKMainFieldFragmentInterface.View view) {
        this.view = view;
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void goToNextState(int ekFieldId, int recipeId, int growingStageId) {
        APIManager.changeStageRecipe(ekFieldId, recipeId, growingStageId, new Callback<ChangeStageRecipeResponse>() {
            @Override
            public void onResponse(@NonNull Call<ChangeStageRecipeResponse> call, @NonNull Response<ChangeStageRecipeResponse> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        view.changeStageSuccess(response.body());
                    } else {
                        if (response.code() == Utils.Constant.ERROR_401) {
                            view.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                view.changeStageFailed(errorMessage);
                            } else {
                                view.changeStageFailed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<ChangeStageRecipeResponse> call, @NonNull Throwable t) {
                if (view != null) {
                    view.changeStageFailed(t.getMessage());
                }

            }
        });
    }

    @Override
    public void getWeatherData(String id) {
        APIManager.getDataForWidgetGraph(id, DateTimeNow.parseCurrentTimeToUTCDateString(), new Callback<ObjectDataForWidgetGraph>() {
            @Override
            public void onResponse(@NonNull Call<ObjectDataForWidgetGraph> call, @NonNull Response<ObjectDataForWidgetGraph> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        if (response.body() != null) {
                            view.getWeatherDataSuccess(response.body());
                        } else {
                            view.getWeatherDataFailed("empty data");
                        }
                    } else {
                        if (response.code() == Utils.Constant.ERROR_401) {
                            view.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                view.getWeatherDataFailed(errorMessage);
                            } else {
                                view.getWeatherDataFailed(BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ObjectDataForWidgetGraph> call, @NonNull Throwable t) {
                if (view != null) {
                    view.getWeatherDataFailed(t.getMessage());
                }
            }
        });
    }

    @Override
    public void getTemplateRecipe(String recipeId) {
        APIManager.getListTemplateRecipe(recipeId, new Callback<TemplateRecipe>() {
            @Override
            public void onResponse(@NonNull Call<TemplateRecipe> call, @NonNull Response<TemplateRecipe> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        if (response.body() != null && response.body().getData() != null) {
                            view.getTemplateRecipeSuccess(response.body().getData());
                        } else {
                            view.getTemplateRecipeFailed("empty data");
                        }
                    } else {
                        if (response.code() == Utils.Constant.ERROR_401) {
                            view.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                view.getTemplateRecipeFailed(errorMessage);
                            } else {
                                view.getTemplateRecipeFailed(BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TemplateRecipe> call, @NonNull Throwable t) {
                if (view != null) {
                    view.getTemplateRecipeFailed(t.getMessage());
                }
            }
        });
    }

    @Override
    public void getDetailField(int fieldId) {
        APIManager.getDetailField(new Callback<ResponseDetailField>() {
            @Override
            public void onResponse(@NonNull Call<ResponseDetailField> call, @NonNull Response<ResponseDetailField> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        view.getDetailFieldSuccess(response.body());
                    } else {
                        if (response.code() == Utils.Constant.ERROR_401) {
                            view.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                view.getDetailFailed(errorMessage);
                            } else {
                                view.getDetailFailed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseDetailField> call, @NonNull Throwable t) {
                if (view != null) {
                    view.getDetailFailed(t.getMessage());
                }

            }
        }, fieldId);
    }

    @Override
    public void getListFields() {
        APIManager.getFilterPlace( new Callback<ArrayList<FilterField>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<FilterField>> call, @NonNull Response<ArrayList<FilterField>> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        view.getListFieldSuccess(response.body());
                    } else {
                        if (response.code() == Utils.Constant.ERROR_401) {
                            view.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                view.getListFieldFailed(errorMessage);
                            } else {
                                view.getListFieldFailed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<FilterField>> call, @NonNull Throwable t) {
                if (view != null) {
                    view.getListFieldFailed(t.getMessage());
                }
            }
        });
    }

}
