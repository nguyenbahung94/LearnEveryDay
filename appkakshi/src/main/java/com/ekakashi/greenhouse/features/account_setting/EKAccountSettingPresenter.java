package com.ekakashi.greenhouse.features.account_setting;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.dao.Account;

import java.io.File;
import java.sql.SQLException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EKAccountSettingPresenter extends BasePresenter implements EKAccountSettingInterface.Presenter {

    private EKAccountSettingInterface.View mView;

    EKAccountSettingPresenter(EKAccountSettingInterface.View mView) {
        this.mView = mView;
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public boolean isEmailValid(String email) {
        String emailPattern = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}";
        return email.matches(emailPattern);
    }

    @Override
    public void doSendRequest( final String fullName, final String email, final String nickName, final File fileURL) {
        APIManager.updateUserAccount(fullName, email, nickName, fileURL, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (mView != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        Account mUser = mView.getUser();
                        mUser.setEmail(email);
                        mUser.setNickName(nickName);
                        mUser.setUserName(fullName);
                        if (fileURL != null) {
                            mUser.setImage(fileURL.getPath());
                        }
                        mView.getAccountDao().deleteAll();
                        try {
                            mView.getAccountDao().createOrUpdate(mUser);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        mView.onUpdateSuccess();
                    } else {
                        if (response.code() == Utils.Constant.ERROR_401) {
                            mView.onExpired();
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
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                if (mView != null) {
                    mView.onUpdateFail(t.getMessage());
                }
            }
        });
    }

    public void getAccountUser() {
        APIManager.getUserAccount(new Callback<Account>() {
            @Override
            public void onResponse(@NonNull Call<Account> call, @NonNull Response<Account> response) {
                if (mView != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        Account user = response.body();
                        if (user != null) {
                            mView.getAccountDao().insertOrUpdate(user);
                            mView.getAccountSuccess(user);
                        } else {
                            mView.getAccountFail();
                        }
                    } else {
                        String errorMessage = getErrorMessage(response.errorBody());
                        if (errorMessage != null && !errorMessage.isEmpty()) {
                            mView.getAccountFail();
                        } else {
                            mView.getAccountFail();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Account> call, @NonNull Throwable t) {
                if (mView != null) {
                    mView.getAccountFail();
                }
            }
        });
    }
}
