package com.ekakashi.greenhouse.features.view_information_field;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ResponseRemoveField;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nbhung on 1/16/2018.
 */

public class EKViewFieldInformationPresenter extends BasePresenter implements EKViewFieldInformationInterface.presenter {
    private EKViewFieldInformationInterface.View view;

    public EKViewFieldInformationPresenter(EKViewFieldInformationInterface.View view) {
        this.view = view;
    }

    public void onDestroy() {
        view = null;
    }

    @Override
    public void deleteField(int fieldId) {
        APIManager.removeField(new Callback<ResponseRemoveField>() {
            @Override
            public void onResponse(@NonNull Call<ResponseRemoveField> call, @NonNull Response<ResponseRemoveField> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        ResponseRemoveField responseRemoveField = response.body();
                        if (responseRemoveField != null) {
                            if (responseRemoveField.isResult()) {
                                view.deleteSuccess();
                            } else {
                                view.deleteFailed();
                            }
                        } else {
                            view.deleteFailed();
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
            public void onFailure(@NonNull Call<ResponseRemoveField> call, @NonNull Throwable t) {
                if (view != null) {
                    view.failed(t.getMessage());
                }
            }
        }, fieldId);
    }
}
