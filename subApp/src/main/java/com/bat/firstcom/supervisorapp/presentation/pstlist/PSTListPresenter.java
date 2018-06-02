package com.bat.firstcom.supervisorapp.presentation.pstlist;

import android.util.Log;

import com.bat.firstcom.supervisorapp.common.ErrorType;
import com.bat.firstcom.supervisorapp.datalayer.model.GetPSTListResponse;
import com.bat.firstcom.supervisorapp.datalayer.repository.SupAppDataRepository;
import com.bat.firstcom.supervisorapp.domain.usecases.GetCheckingPSTList;
import com.bat.firstcom.supervisorapp.domain.usecases.GetCoachingPSTList;
import com.bat.firstcom.supervisorapp.presentation.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tung Phan on 30-Jun-17.
 */

public class PSTListPresenter extends BasePresenter<PSTListView> {

    private static final String TAG = PSTListPresenter.class.getSimpleName();

    private GetCoachingPSTList getCoachingPSTList;
    private GetCheckingPSTList getCheckingPSTList;

    @Inject
    public PSTListPresenter(SupAppDataRepository dataRepository) {
        getCoachingPSTList = new GetCoachingPSTList(dataRepository);
        getCheckingPSTList = new GetCheckingPSTList(dataRepository);
    }

    void getCoachingPSTList(String token, int brand) {
        getView().showLoading();
        disposable.add(getCoachingPSTList.param(GetCoachingPSTList.Params.forGetCoachingPSTList(token, brand))
                .on(Schedulers.io(), AndroidSchedulers.mainThread())
                .execute(new GetCoachingPSTListObserver()));
    }

    void getCheckingPSTList(String token, int brand) {
        getView().showLoading();
        disposable.add(getCheckingPSTList.param(GetCheckingPSTList.Params.forGetCheckingPSTList(token, brand))
                .on(Schedulers.io(), AndroidSchedulers.mainThread())
                .execute(new GetCheckingPSTListObserver()));
    }

    private final class GetCoachingPSTListObserver extends DisposableObserver<GetPSTListResponse> {

        @Override
        public void onComplete() {
            getView().hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            getView().hideLoading();
            getView().showErrorDialog(ErrorType.ERROR_REQUEST_COACHING_PST_LIST);
        }

        @Override
        public void onNext(GetPSTListResponse response) {
            if (response.getSuccess()) {
                getView().updateCoachingPSTListAdapter(response.getPstData());
            } else {
                getView().showErrorDialog(ErrorType.ERROR_RESPONSE_COACHING_PST_LIST);
            }
        }
    }

    private final class GetCheckingPSTListObserver extends DisposableObserver<GetPSTListResponse> {

        @Override
        public void onComplete() {
            getView().hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            getView().hideLoading();
            getView().showErrorDialog(ErrorType.ERROR_REQUEST_CHECKING_PST_LIST);
        }

        @Override
        public void onNext(GetPSTListResponse response) {
            if (response.getSuccess()) {
                getView().updateCheckingPSTListAdapter(response.getPstData());
            } else {
                getView().showErrorDialog(ErrorType.ERROR_RESPONSE_CHECKING_PST_LIST);
            }
        }
    }

}
