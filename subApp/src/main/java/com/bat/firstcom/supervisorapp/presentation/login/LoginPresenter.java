package com.bat.firstcom.supervisorapp.presentation.login;

import android.util.Log;

import com.bat.firstcom.supervisorapp.common.ErrorType;
import com.bat.firstcom.supervisorapp.datalayer.model.BrandDatum;
import com.bat.firstcom.supervisorapp.datalayer.model.GetBrandResponse;
import com.bat.firstcom.supervisorapp.datalayer.model.LoginRequest;
import com.bat.firstcom.supervisorapp.datalayer.model.LoginResponse;
import com.bat.firstcom.supervisorapp.datalayer.repository.SupAppDataRepository;
import com.bat.firstcom.supervisorapp.domain.usecases.GetBrands;
import com.bat.firstcom.supervisorapp.domain.usecases.Login;
import com.bat.firstcom.supervisorapp.presentation.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tung Phan on 30-Jun-17.
 */
//TODO: recheck all new instance of object => maybe we can apply dagger for those.
public class LoginPresenter extends BasePresenter<LoginView> {
    private static final String TAG = LoginPresenter.class.getSimpleName();
    private GetBrands getBrands;
    private Login login;

    @Inject
    public LoginPresenter(SupAppDataRepository dataRepository) {
        getBrands = new GetBrands(dataRepository);
        login = new Login(dataRepository);
    }

    void getBrands() {
        getView().showLoading();
        disposable.add(getBrands.param(null).on(Schedulers.io(), AndroidSchedulers.mainThread())
                .execute(new GetBrandsObserver()));
    }

    //Compare to backend, id of the brand is the itemPosition
    //Relogic this if the backend is changed.
    void login(int itemPosition, LoginRequest loginRequest) {
        getView().showLoading();
        disposable.add(login.param(Login.Params.forLogin(itemPosition, loginRequest))
                .on(Schedulers.io(), AndroidSchedulers.mainThread())
                .execute(new LoginObserver()));
    }

    private final class GetBrandsObserver extends DisposableObserver<GetBrandResponse> {

        @Override
        public void onComplete() {
            getView().hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            getView().hideLoading();
            getView().showErrorDialog(ErrorType.ERROR_REQUEST_BRAND);
        }

        @Override
        public void onNext(GetBrandResponse response) {
            if (response.getSuccess()) {
                getView().updateBrandSpinner(getBrandListFrom(response));
            } else {
                getView().showErrorDialog(ErrorType.ERROR_RESPONSE_BRAND);
            }
        }

        private List<String> getBrandListFrom(GetBrandResponse response) {
            List<String> brandList = new ArrayList<>();
            if (response.getBrandData().size() > 0) {
                for (BrandDatum brandDatum : response.getBrandData()) {
                    brandList.add(brandDatum.getName());
                }
            }
            return brandList;
        }
    }

    private final class LoginObserver extends DisposableObserver<LoginResponse> {

        @Override
        public void onComplete() {
            getView().hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            getView().hideLoading();
            getView().showErrorDialog(ErrorType.ERROR_REQUEST_LOGIN);
        }

        @Override
        public void onNext(LoginResponse response) {
            if (response.getSuccess()) {
                if (response.getLoginData().getProfile().isFirstLogin()) {
                    getView().startChangePasswordActivity();
                } else {
                    getView().startFeaturesActivity();
                }
                if (response.getLoginData() != null) {
                    getView().saveUserInfoToSharePref(response.getLoginData());
                }
            } else {
                getView().showErrorDialog(ErrorType.ERROR_RESPONSE_LOGIN);
            }
        }
    }

}
