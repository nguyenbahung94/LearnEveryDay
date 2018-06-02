package com.bat.firstcom.supervisorapp.domain.usecases;

import com.bat.firstcom.supervisorapp.datalayer.model.GetPSTListResponse;
import com.bat.firstcom.supervisorapp.domain.repository.SupAppRepository;


import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by tung.phan on 5/17/2017.
 */

public class GetCoachingPSTList extends UseCase<GetPSTListResponse
        , GetCoachingPSTList.Params> {

    private SupAppRepository repository;

    public GetCoachingPSTList(SupAppRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    Observable<GetPSTListResponse> buildUseCaseObservable(Params params) {
        return repository.getCoachingPstList(params.authorizationToken, params.brand);
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

        public static Params forGetCoachingPSTList(String authorizationToken, int brand) {
            return new Params(authorizationToken, brand);
        }
    }
}
