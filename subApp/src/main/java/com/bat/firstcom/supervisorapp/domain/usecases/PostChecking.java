package com.bat.firstcom.supervisorapp.domain.usecases;

import com.bat.firstcom.supervisorapp.datalayer.model.CheckingRequest;
import com.bat.firstcom.supervisorapp.datalayer.model.CheckingResponse;
import com.bat.firstcom.supervisorapp.domain.repository.SupAppRepository;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Tung Phan on 27-Jul-17.
 */

public class PostChecking extends UseCase<CheckingResponse, PostChecking.Params> {

    private SupAppRepository repository;

    public PostChecking(SupAppRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    Observable<CheckingResponse> buildUseCaseObservable(Params params) {
        return repository.postChecking(params.authorizationToken
                , params.brand, params.checkingRequest);
    }

    @Override
    Single<CheckingResponse> buildUseCaseSingle(Params params) {
        return null;
    }

    @Override
    Completable buildUseCaseCompletable(Params params) {
        return null;
    }

    public static final class Params {

        private final int brand;
        private final String authorizationToken;
        private final CheckingRequest checkingRequest;

        private Params(String authorizationToken, int brand, CheckingRequest checkingRequest) {
            this.brand = brand;
            this.authorizationToken = authorizationToken;
            this.checkingRequest = checkingRequest;
        }

        public static Params forPostChecking(String authorizationToken, int brand
                , CheckingRequest checkingRequest) {
            return new Params(authorizationToken, brand, checkingRequest);
        }
    }
}
