package com.bat.firstcom.supervisorapp.domain.usecases;

import com.bat.firstcom.supervisorapp.datalayer.model.GetBrandResponse;
import com.bat.firstcom.supervisorapp.domain.repository.SupAppRepository;


import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by vinh.trinh on 5/17/2017.
 */

public class GetBrands extends UseCase<GetBrandResponse, Void> {

    private SupAppRepository repository;

    public GetBrands(SupAppRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    Observable<GetBrandResponse> buildUseCaseObservable(Void params) {
        return repository.getBrands();
    }

    @Override
    Single<GetBrandResponse> buildUseCaseSingle(Void params) {
       return null;
    }

    @Override
    Completable buildUseCaseCompletable(Void params) {
        return null;
    }
}
