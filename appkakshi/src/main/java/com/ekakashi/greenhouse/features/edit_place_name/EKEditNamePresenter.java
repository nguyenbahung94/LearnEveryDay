package com.ekakashi.greenhouse.features.edit_place_name;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_request.EditFieldRequest;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCreateField;
import com.ekakashi.greenhouse.database.model_server_response.ResponseEditField;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nbhung on 2/7/2018.
 */

public class EKEditNamePresenter extends BasePresenter implements EKEditNameInterface.presenter {
    private EKEditNameInterface.View view;

    EKEditNamePresenter(EKEditNameInterface.View view) {
        this.view = view;
    }

    void onDestroy() {
        view = null;
    }

    @Override
    public void updateName(ObjectCreateField createField) {
        final EditFieldRequest editFieldRequest = new EditFieldRequest(createField.getPlaceName(),
                createField.getPolygon(), Integer.parseInt(createField.getPlaceType()),createField.getIdField(), Integer.parseInt(createField.getIdUser()),createField.getAddress());
        APIManager.editField(new Callback<ResponseEditField>() {
            @Override
            public void onResponse(@NonNull Call<ResponseEditField> call, @NonNull Response<ResponseEditField> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        view.success();
                    } else if (response.code() == Utils.ErrorCode.ERROR_401) {
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

            @Override
            public void onFailure(@NonNull Call<ResponseEditField> call, @NonNull Throwable t) {
                if (view != null) {
                    view.failed(t.getMessage());
                }
            }
        }, editFieldRequest);

    }
}
