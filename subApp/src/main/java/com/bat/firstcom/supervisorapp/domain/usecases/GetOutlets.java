package com.bat.firstcom.supervisorapp.domain.usecases;

import com.bat.firstcom.supervisorapp.datalayer.model.GetOutletResponse;
import com.bat.firstcom.supervisorapp.domain.repository.SupAppRepository;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Tung Phan on 25-Jul-17.
 */

public class GetOutlets extends UseCase<GetOutletResponse, GetOutlets.Params> {

    private SupAppRepository repository;

    public GetOutlets(SupAppRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    Observable<GetOutletResponse> buildUseCaseObservable(Params params) {
        return repository.getOutlets(params.pstId, params.authorizationToken, params.brand);
    }

    @Override
    Single<GetOutletResponse> buildUseCaseSingle(Params params) {
        return null;
    }

    @Override
    Completable buildUseCaseCompletable(Params params) {
        return null;
    }

    public static final class Params {

        private final String pstId;
        private final int brand;
        private final String authorizationToken;

        private Params(String pstId, String authorizationToken, int brand) {
            this.pstId = pstId;
            this.brand = brand;
            this.authorizationToken = authorizationToken;
        }

        public static Params forGetOutlets(String pstId, String authorizationToken, int brand) {
            return new Params(pstId, authorizationToken, brand);
        }
    }
}
