package com.ekakashi.greenhouse.features.invite_member;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_request.InviteMemberRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nquochuy on 1/25/2018.
 */

public class InviteMemberPresenter extends BasePresenter implements InviteMemberInterface.Presenter {

    private InviteMemberInterface.View mView;

    public InviteMemberPresenter(InviteMemberInterface.View mView) {
        this.mView = mView;
    }

    @Override
    public void sendInvite(final InviteMemberRequest request) {
        APIManager.inviteMemberList(request, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (mView != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        mView.onInviteSuccess();
                    } else if (response.code() == Utils.Constant.ERROR_401) {
                        mView.tokenExpired();
                    } else {
                        String errorMessage = getErrorMessage(response.errorBody());
                        if (errorMessage != null && !errorMessage.isEmpty()) {
                            mView.onInviteFail(errorMessage);
                        } else {
                            mView.onInviteFail(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                if (mView != null) {
                    mView.onInviteFail(t.getMessage());
                }
            }
        });
    }
}
