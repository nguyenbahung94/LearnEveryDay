package com.bat.firstcom.supervisorapp.domain.usecases;

import com.bat.firstcom.supervisorapp.datalayer.model.ChangePasswordRequest;
import com.bat.firstcom.supervisorapp.datalayer.model.ChangePasswordResponse;
import com.bat.firstcom.supervisorapp.domain.repository.SupAppRepository;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Tung Phan on 28-Jul-17.
 */

public class ChangePassword extends UseCase<ChangePasswordResponse, ChangePassword.Params> {//Login.Params

    private SupAppRepository repository;

    public ChangePassword(SupAppRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    Observable<ChangePasswordResponse> buildUseCaseObservable(Params params) {
        return repository.postChangePassword(params.authorization, params.brand
                , params.changePasswordRequest);
    }

    @Override
    Single<ChangePasswordResponse> buildUseCaseSingle(Params params) {
        return null;
    }

    @Override
    Completable buildUseCaseCompletable(Params params) {
        return null;
    }

    public static final class Params {

        private final String authorization;
        private final int brand;
        private final ChangePasswordRequest changePasswordRequest;

        private Params(String authorization, int brand, ChangePasswordRequest changePasswordRequest) {
            this.authorization = authorization;
            this.brand = brand;
            this.changePasswordRequest = changePasswordRequest;
        }

        public static Params forChangePassword(String authorization
                , int brand, ChangePasswordRequest changePasswordRequest) {
            return new Params(authorization, brand, changePasswordRequest);
        }
    }
}
