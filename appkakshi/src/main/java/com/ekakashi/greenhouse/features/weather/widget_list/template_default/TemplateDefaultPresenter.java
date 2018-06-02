package com.ekakashi.greenhouse.features.weather.widget_list.template_default;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.BaseResponse;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.TemplateRecipe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nbhung on 3/21/2018.
 */

public class TemplateDefaultPresenter extends BasePresenter implements TemplateDefaultInterface.presenter {
    private TemplateDefaultInterface.View view;

    public TemplateDefaultPresenter(TemplateDefaultInterface.View view) {
        this.view = view;
    }

    public void onDetroy() {
        view = null;
    }

    @Override
    public void getListTemplateDefault() {
        APIManager.getListTemplateDefault(new Callback<TemplateRecipe>() {
            @Override
            public void onResponse(@NonNull Call<TemplateRecipe> call, @NonNull Response<TemplateRecipe> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        if (response.body() != null && response.body().getData() != null) {
                            view.getListSuccess(response.body().getData());
                        } else {
                            view.getListFailed(App.getsInstance().getString(R.string.txt_empty_data));
                        }
                    } else {
                        if (response.code() == Utils.ErrorCode.ERROR_401) {
                            view.TokenExpired();
                        } else {
                            String messageError = getErrorMessage(response.errorBody());
                            if (messageError != null && !messageError.isEmpty()) {
                                view.getListFailed(messageError);
                            } else {
                                view.getListFailed(BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<TemplateRecipe> call, @NonNull Throwable t) {
                if (view != null) {
                    view.getListFailed(t.getMessage());
                }
            }
        });
    }
}
