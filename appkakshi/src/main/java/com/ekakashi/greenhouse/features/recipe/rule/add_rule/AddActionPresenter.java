package com.ekakashi.greenhouse.features.recipe.rule.add_rule;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.BaseResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ptloc on 2/3/2018.
 */

public class AddActionPresenter extends BasePresenter implements AddActionInterface.presenter {

    private AddActionInterface.View view;

    public AddActionPresenter(AddActionInterface.View view) {
        this.view = view;
    }

    public void onDestroy() {
        this.view = null;
    }

    @Override
    public void uploadImage(MultipartBody.Part imageFile) {
        APIManager.updateRecipePicture(imageFile, new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
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
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                if (view != null) {
                    view.failed(t.getMessage());
                }
            }
        });
    }
}
