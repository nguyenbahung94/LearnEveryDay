package com.bat.firstcom.supervisorapp.presentation.changepassword;


import com.bat.firstcom.supervisorapp.common.ErrorType;
import com.bat.firstcom.supervisorapp.datalayer.model.ChangePasswordRequest;
import com.bat.firstcom.supervisorapp.datalayer.model.ChangePasswordResponse;
import com.bat.firstcom.supervisorapp.datalayer.repository.SupAppDataRepository;
import com.bat.firstcom.supervisorapp.domain.usecases.ChangePassword;
import com.bat.firstcom.supervisorapp.presentation.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tung Phan on 30-Jun-17.
 */
//TODO: recheck all new instance of object => maybe we can apply dagger for those.
public class ChangePasswordPresenter extends BasePresenter<ChangePasswordView> {
    private static final String TAG = ChangePasswordPresenter.class.getSimpleName();
    private final int INVALID_PASSWORD_CODE = 300;
    private final int PASSWORD_NOT_MATCH_CODE = 301;
    private final int PASSWORD_SAME_WITH_OLD_PASS = 302;
    private final int NO_ERROR_CODE = 0;
    private ChangePassword changePassword;

    @Inject
    public ChangePasswordPresenter(SupAppDataRepository dataRepository) {
        changePassword = new ChangePassword(dataRepository);
    }

    //Compare to backend, id of the brand is the itemPosition
    //Relogic this if the backend is changed.
    void postChangePassword(String authorization, int brand, ChangePasswordRequest changePasswordRequest) {
        getView().showLoading();
        disposable.add(changePassword.param(ChangePassword.Params.forChangePassword(
                authorization, brand, changePasswordRequest))
                .on(Schedulers.io(), AndroidSchedulers.mainThread())
                .execute(new ChangePasswordObserver()));
    }

    private final class ChangePasswordObserver extends DisposableObserver<ChangePasswordResponse> {

        @Override
        public void onComplete() {
            getView().hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            getView().hideLoading();
            getView().showErrorDialog(ErrorType.ERROR_REQUEST_CHANGE_PASSWORD);
        }

        @Override
        public void onNext(ChangePasswordResponse response) {
            switch(response.getErrorCode()){
                case INVALID_PASSWORD_CODE:
                    getView().showErrorDialog(ErrorType.ERROR_INVALID_PASSWORD_OR_CONFIRMED_PASSWORD);
                    break;
                case PASSWORD_NOT_MATCH_CODE:
                    getView().showErrorDialog(ErrorType.ERROR_PASSWORD_NOT_MATCH);
                    break;
                case PASSWORD_SAME_WITH_OLD_PASS:
                    getView().showErrorDialog(ErrorType.ERROR_PASSWORD_IS_SAME_WITH_OLD_PASSWORD);
                    break;
                case NO_ERROR_CODE:
                    if(response.isSuccess()) {
                        getView().changePasswordSuccessfully();
                    }else{
                        getView().showErrorDialog(ErrorType.ERROR_RESPONSE_CHANGE_PASSWORD);
                    }
            }
        }
    }

}
