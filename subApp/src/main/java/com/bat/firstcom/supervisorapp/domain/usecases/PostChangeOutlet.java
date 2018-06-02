package com.bat.firstcom.supervisorapp.domain.usecases;

import com.bat.firstcom.supervisorapp.datalayer.model.PostChangeOutletRequest;
import com.bat.firstcom.supervisorapp.datalayer.model.PostChangeOutletResponse;
import com.bat.firstcom.supervisorapp.domain.repository.SupAppRepository;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by tung phan on 7/27/17.
 */

public class PostChangeOutlet extends UseCase<PostChangeOutletResponse, PostChangeOutlet.Params> {

    private SupAppRepository repository;

    public PostChangeOutlet(SupAppRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    Observable<PostChangeOutletResponse> buildUseCaseObservable(Params params) {
        return repository.postChangeOutlet(params.authorizationToken
                , params.brand, params.postChangeOutletRequest);
    }

    @Override
    Single<PostChangeOutletResponse> buildUseCaseSingle(Params params) {
        return null;
    }

    @Override
    Completable buildUseCaseCompletable(Params params) {
        return null;
    }

    public static final class Params {

        private final int brand;
        private final String authorizationToken;
        private final PostChangeOutletRequest postChangeOutletRequest;

        private Params(String authorizationToken, int brand, PostChangeOutletRequest postChangeOutletRequest) {
            this.brand = brand;
            this.authorizationToken = authorizationToken;
            this.postChangeOutletRequest = postChangeOutletRequest;
        }

        public static Params forPostOutletChange(String authorizationToken, int brand
                , PostChangeOutletRequest postChangeOutletRequest) {
            return new Params(authorizationToken, brand, postChangeOutletRequest);
        }
    }
}
