package com.ekakashi.greenhouse.features.member_list;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_request.EditMemberListRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MemberListPresenter extends BasePresenter implements MemberListInterface.Presenter {

    private MemberListInterface.View mView;

    MemberListPresenter(MemberListInterface.View mView) {
        this.mView = mView;
    }

    @Override
    public void getMemberList(int fieldId) {
        APIManager.getMemberList(fieldId, new Callback<ArrayList<MemberListModel>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<MemberListModel>> call, @NonNull Response<ArrayList<MemberListModel>> response) {
                if (mView != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        mView.onMemberListSuccess(response.body());
                    } else if (response.code() == Utils.Constant.ERROR_401) {
                        mView.tokenExpired();
                    } else {
                        String errorMessage = getErrorMessage(response.errorBody());
                        if (errorMessage != null && !errorMessage.isEmpty()) {
                            mView.onMemberListFail(errorMessage);
                        } else {
                            mView.onMemberListFail(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<MemberListModel>> call, @NonNull Throwable t) {
                if (mView != null) {
                    mView.onMemberListFail(t.getMessage());
                }
            }
        });
    }

    @Override
    public void deleteMemberList(int invitationId) {
        APIManager.deleteMemberList(new EditMemberListRequest(invitationId), new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (mView != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        mView.onDeleteSuccess();
                    } else if (response.code() == Utils.Constant.ERROR_401) {
                        mView.tokenExpired();
                    } else {
                        mView.onDeleteFail();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                if (mView != null) {
                    mView.onDeleteFail();
                }
            }
        });
    }
}
