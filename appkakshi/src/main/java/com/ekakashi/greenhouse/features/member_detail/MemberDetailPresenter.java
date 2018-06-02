package com.ekakashi.greenhouse.features.member_detail;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_request.EditMemberListRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nquochuy on 1/29/2018.
 */

public class MemberDetailPresenter extends BasePresenter implements MemberDetailInterface.Presenter {

    private MemberDetailInterface.View mView;

    public MemberDetailPresenter(MemberDetailInterface.View mView) {
        this.mView = mView;
    }

    @Override
    public void editMemberList(EditMemberListRequest memberListRequest) {
        APIManager.editMemberList( memberListRequest, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (mView != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        mView.onUpdateSuccess();
                    } else if (response.code() == Utils.Constant.ERROR_401) {
                        mView.tokenExpired();
                    } else {
                        String errorMessage = getErrorMessage(response.errorBody());
                        if (errorMessage != null && !errorMessage.isEmpty()) {
                            mView.onUpdateFail(errorMessage);
                        } else {
                            mView.onUpdateFail(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                if (mView != null) {
                    mView.onUpdateFail(t.getMessage());
                }
            }
        });
    }
}
