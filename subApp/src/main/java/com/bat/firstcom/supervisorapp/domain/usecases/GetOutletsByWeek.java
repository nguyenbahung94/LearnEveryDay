package com.bat.firstcom.supervisorapp.domain.usecases;

import com.bat.firstcom.supervisorapp.datalayer.model.GetOutletResponse;
import com.bat.firstcom.supervisorapp.domain.repository.SupAppRepository;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by tung phan on 7/25/17.
 */

public class GetOutletsByWeek extends UseCase<GetOutletResponse, GetOutletsByWeek.Params>{
    private SupAppRepository repository;

    public GetOutletsByWeek(SupAppRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    Observable<GetOutletResponse> buildUseCaseObservable(Params params) {
        return repository.getOutletsByWeek(params.pstid, params.authorizationToken, params.brand);
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

        private final String pstid;
        private final int brand;
        private final String authorizationToken;

        private Params(String pstid, String authorizationToken, int brand) {
            this.pstid = pstid;
            this.brand = brand;
            this.authorizationToken = authorizationToken;
        }

        public static Params forGetOutlets(String pstid, String authorizationToken, int brand) {
            return new Params(pstid, authorizationToken, brand);
        }
    }
}
