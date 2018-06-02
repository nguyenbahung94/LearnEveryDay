package com.ekakashi.greenhouse.features.add_field_greenhouse.step4;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_request.AddFieldRequest;
import com.ekakashi.greenhouse.database.model_server_response.CloneRecipeResponse;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCreateField;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.database.model_server_response.ResponseAddField;
import com.ekakashi.greenhouse.database.model_server_response.ResponseDetailField;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HEAD;

public class EKAddFieldStepFourPresenter extends BasePresenter implements EKAddFieldStepFourInterface.Presenter {
    private EKAddFieldStepFourInterface.View view;

    public EKAddFieldStepFourPresenter(EKAddFieldStepFourInterface.View view) {
        this.view = view;
    }

    public void onDestroy() {
        view = null;
    }

    @Override
    public void addField(ObjectCreateField createField) {
        final List<AddFieldRequest.Recipes> recipes = new ArrayList<>();
        recipes.add(new AddFieldRequest.Recipes(new AddFieldRequest.Recipe(String.valueOf(createField.getRecipeId())), new AddFieldRequest.CurrentStage(String.valueOf(createField.getCurrentStageId()), createField.getSortNo())));
        List<ResponseDetailField.Polygon> tam = createField.getPolygon();
        List<AddFieldRequest.Polygon> polygon = new ArrayList<>();
        for (ResponseDetailField.Polygon ss : tam) {
            polygon.add(new AddFieldRequest.Polygon(ss.getLng(), ss.getLat()));
        }
        AddFieldRequest addFieldRequest = new AddFieldRequest(recipes, createField.getPlaceName(), createField.getPlaceType(), polygon, new AddFieldRequest.EkUser(String.valueOf(createField.getIdUser())), createField.getAddress());
        APIManager.addField(new Callback<ResponseAddField>() {
            @Override
            public void onResponse(@NonNull Call<ResponseAddField> call, @NonNull Response<ResponseAddField> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        ResponseAddField responseAddField = response.body();
                        if (responseAddField != null) {
                            Prefs.getInstance(App.getsInstance()).saveCurrentId_Field(responseAddField.getId());
                            view.success();
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
            public void onFailure(@NonNull Call<ResponseAddField> call, @NonNull Throwable t) {
                if (view != null) {
                    view.failed(t.getMessage());
                }
            }
        }, addFieldRequest);
    }

  /*  public void cloneRecipe(ObjectCreateField createField) {
        AddFieldRequest.Recipe recipe = new AddFieldRequest.Recipe(String.valueOf(createField.getRecipeId()));
        APIManager.cloneRecipeForField(String.valueOf(createField.getRecipeId()), new Callback<ObjectRecipe>() {
            @Override
            public void onResponse(@NonNull Call<ObjectRecipe> call, @NonNull Response<ObjectRecipe> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        if (response.body() != null) {
                            view.updateRecipeSuccess(response.body());
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
    }*/

}
