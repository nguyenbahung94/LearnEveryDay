package com.ekakashi.greenhouse.features.recipe.rule.add_rule;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ptloc on 3/20/2018.
 */

public class AddConditionPresenter extends BasePresenter implements AddConditionInterface.presenter {

    private AddConditionInterface.View view;

    public AddConditionPresenter(AddConditionInterface.View view) {
        this.view = view;
    }

    @Override
    public void getRecipeById(int recipeId) {
        APIManager.getRecipeById(recipeId, new Callback<ObjectRecipe>() {
            @Override
            public void onResponse(@NonNull Call<ObjectRecipe> call, @NonNull Response<ObjectRecipe> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        view.success(response.body());
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
