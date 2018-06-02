package com.bat.firstcom.supervisorapp.presentation.base;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Tung Phan
 * @since 06/30/2017
 */
public class BasePresenter<V extends BaseView> {

    private V view;
    protected CompositeDisposable disposable;

    public void setCompositeDisposable(CompositeDisposable disposable) {
        this.disposable = disposable;
    }

    public CompositeDisposable getCompositeDisposable() {
        return this.disposable;
    }

    public void unsubscribe() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    public void onTakeView(V view) {
        this.view = view;
    }

    public V getView() {
        return view;
    }

    public void onDestroyView() {
        this.view = null;
    }

}
