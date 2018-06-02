package com.bat.firstcom.supervisorapp.presentation.outletlist;


import com.bat.firstcom.supervisorapp.common.ErrorType;
import com.bat.firstcom.supervisorapp.datalayer.model.GetOutletResponse;
import com.bat.firstcom.supervisorapp.datalayer.repository.SupAppDataRepository;
import com.bat.firstcom.supervisorapp.domain.usecases.GetOutlets;
import com.bat.firstcom.supervisorapp.domain.usecases.GetOutletsByWeek;
import com.bat.firstcom.supervisorapp.presentation.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tung Phan on 30-Jun-17.
 */

public class OutletListPresenter extends BasePresenter<OutletListView> {

    private static final String TAG = OutletListPresenter.class.getSimpleName();

    private GetOutlets getOutlets;
    private GetOutletsByWeek getOutletsByWeek;

    @Inject
    public OutletListPresenter(SupAppDataRepository dataRepository) {
        getOutlets = new GetOutlets(dataRepository);
        getOutletsByWeek = new GetOutletsByWeek(dataRepository);
    }

    void getOutlets(int id, String token, int brand) {
        getView().showLoading();
        disposable.add(getOutlets.param(GetOutlets.Params.forGetOutlets(String.valueOf(id)
                , token, brand))
                .on(Schedulers.io(), AndroidSchedulers.mainThread())
                .execute(new GetOutletObserver()));
    }

    void getOutletsByWeek(int id, String token, int brand){
        getView().showLoading();
        disposable.add(getOutletsByWeek.param(GetOutletsByWeek.Params
                .forGetOutlets(String.valueOf(id), token, brand))
                .on(Schedulers.io(), AndroidSchedulers.mainThread())
                .execute(new GetOutletObserver()));
    }

    private final class GetOutletObserver extends DisposableObserver<GetOutletResponse> {

        @Override
        public void onComplete() {
            getView().hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            getView().hideLoading();
            getView().showErrorDialog(ErrorType.ERROR_REQUEST_OUTLET_LIST);
        }

        @Override
        public void onNext(GetOutletResponse response) {
            if (response.getSuccess()) {
                getView().updateOutletListAdapter(response.getOutletData());
            } else {
                getView().showErrorDialog(ErrorType.ERROR_RESPONSE_OUTLET_LIST);
            }
        }
    }

}
