package com.bat.firstcom.supervisorapp.domain.usecases;

import com.bat.firstcom.supervisorapp.datalayer.model.GetPSTListResponse;
import com.bat.firstcom.supervisorapp.domain.repository.SupAppRepository;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Tung Phan on 25-Jul-17.
 */

public class GetCheckingPSTList extends UseCase<GetPSTListResponse
        , GetCheckingPSTList.Params> {

    private SupAppRepository repository;

    public GetCheckingPSTList(SupAppRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    Observable<GetPSTListResponse> buildUseCaseObservable(Params params) {
        return repository.getCheckingPSTList(params.authorizationToken, params.brand);
    }

    @Override
    Single<GetPSTListResponse> buildUseCaseSingle(Params params) {
        return null;
    }

    @Override
    Completable buildUseCaseCompletable(Params params) {
        return null;
    }

    public static final class Params {

        private final int brand;
        private final String authorizationToken;

        private Params(String authorizationToken, int brand) {
            this.brand = brand;
            this.authorizationToken = authorizationToken;
        }

        public static Params forGetCheckingPSTList(String authorizationToken, int brand) {
            return new Params(authorizationToken, brand);
        }
    }
}
