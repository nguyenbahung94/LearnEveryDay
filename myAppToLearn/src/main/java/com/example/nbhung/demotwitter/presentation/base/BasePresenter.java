package com.example.nbhung.demotwitter.presentation.base;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends BaseView> {
    private V view;
    protected CompositeDisposable disposable;
    public void setCompositeDisposable(CompositeDisposable disposable) {
        this.disposable = disposable;
    }

    public CompositeDisposable getCompositeDisposable() {
        return disposable;
    }

    public void unsubcribe() {
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
