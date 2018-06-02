package com.bat.firstcom.supervisorapp.domain.usecases;

import com.bat.firstcom.supervisorapp.datalayer.model.GetChangeOutletResponse;
import com.bat.firstcom.supervisorapp.domain.repository.SupAppRepository;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Tung Phan on 27-Jul-17.
 */

public class GetChangeOutlet extends UseCase<GetChangeOutletResponse, GetChangeOutlet.Params> {

    private SupAppRepository repository;

    public GetChangeOutlet(SupAppRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    Observable<GetChangeOutletResponse> buildUseCaseObservable(Params params) {
        return repository.getChangeOutlet(params.outletLocationId, params.authorizationToken, params.brand);
    }

    @Override
    Single<GetChangeOutletResponse> buildUseCaseSingle(Params params) {
        return null;
    }

    @Override
    Completable buildUseCaseCompletable(Params params) {
        return null;
    }

    public static final class Params {

        private final String outletLocationId;
        private final int brand;
        private final String authorizationToken;

        private Params(String outletLocationId, String authorizationToken, int brand) {
            this.outletLocationId = outletLocationId;
            this.brand = brand;
            this.authorizationToken = authorizationToken;
        }

        public static Params forGetChangeOutlet(String outletLocationId, String authorizationToken, int brand) {
            return new Params(outletLocationId, authorizationToken, brand);
        }
    }
}
