package com.bat.firstcom.supervisorapp.domain.usecases;

import com.bat.firstcom.supervisorapp.datalayer.model.CoachingRequest;
import com.bat.firstcom.supervisorapp.datalayer.model.CoachingResponse;
import com.bat.firstcom.supervisorapp.domain.repository.SupAppRepository;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by tung phan on 7/25/17.
 */

public class PostCoaching extends UseCase<CoachingResponse, PostCoaching.Params> {

    private SupAppRepository repository;

    public PostCoaching(SupAppRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    Observable<CoachingResponse> buildUseCaseObservable(Params params) {
        return repository.postCoaching(params.authorizationToken
                , params.brand, params.coachingRequest);
    }

    @Override
    Single<CoachingResponse> buildUseCaseSingle(Params params) {
        return null;
    }

    @Override
    Completable buildUseCaseCompletable(Params params) {
        return null;
    }

    public static final class Params {

        private final int brand;
        private final String authorizationToken;
        private final CoachingRequest coachingRequest;

        private Params(String authorizationToken, int brand, CoachingRequest coachingRequest) {
            this.brand = brand;
            this.authorizationToken = authorizationToken;
            this.coachingRequest = coachingRequest;
        }

        public static Params forPostCoaching(String authorizationToken, int brand
                , CoachingRequest coachingRequest) {
            return new Params(authorizationToken, brand, coachingRequest);
        }
    }
}
