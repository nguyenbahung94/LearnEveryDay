package com.bat.firstcom.supervisorapp.domain.usecases;


import com.bat.firstcom.supervisorapp.datalayer.model.LoginRequest;
import com.bat.firstcom.supervisorapp.datalayer.model.LoginResponse;
import com.bat.firstcom.supervisorapp.domain.repository.SupAppRepository;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by tung.phan on 5/11/2017.
 */

public class Login extends UseCase<LoginResponse, Login.Params> {//Login.Params

    private SupAppRepository repository;

    public Login(SupAppRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    Observable<LoginResponse> buildUseCaseObservable(Params params) {
        return repository.login(params.brand,params.loginRequest);
    }

    @Override
    Single<LoginResponse> buildUseCaseSingle(Params params) {
        return null;
    }

    @Override
    Completable buildUseCaseCompletable(Params params) {
        return null;
    }

    public static final class Params {

        private final int brand;
        private final LoginRequest loginRequest;

        private Params(int brand,LoginRequest loginRequest) {
            this.brand = brand;
            this.loginRequest = loginRequest;
        }

        public static Params forLogin(int brand,LoginRequest loginRequest) {
            return new Params(brand, loginRequest);
        }
    }
}
